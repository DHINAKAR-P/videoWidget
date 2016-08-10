package com.npb.gp.gen.workers.server.sql;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.npb.gp.gen.workers.server.sql.support.ddl.GpSqlDMLGenSupport;

@Component("GpSqlDDLWorker")
public class GpSqlDDLWorker extends GpGenBaseWorker{
	private Path template_group_path;
	private GpSqlDMLGenSupport ddl_gen_support;
	private String  character_set = "utf8";
	private String  collation = "utf8_general_ci";
	
	 
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		
	}
	
	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_rdbms_mysql_template_location = this.base_configs
				.get("server_rdbms_mysql_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_rdbms_mysql_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_sql_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_rdbms_mysql_ddl_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
	}

	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager,List<GpModuleProperties> modules) throws Exception {
		this.do_generation_for_project(the_project, gen_manager, modules);
	}
	
	@SuppressWarnings("unchecked")
	private void do_generation_for_project(GpProject the_project, IGpGenManager gen_manager, List<GpModuleProperties> modules) throws Exception {
		
		ST st = super.read_template_group(this.template_group_path, "output");
		String drop_db_statement = this.ddl_gen_support.get_drop_db_statement(this.the_project);
		st.add("drop_db_statement", drop_db_statement);
		String create_db_statement = this.ddl_gen_support.get_create_db_statement(this.the_project,character_set,collation);
		st.add("create_db_statement", create_db_statement);
		this.write_db_creation_script(st);
		
		ST st_tables = super.read_template_group(this.template_group_path, "output");
		
		String db_name = the_project.getName().toLowerCase();
		st_tables.add("select_database", "USE " + db_name + ";");
		st_tables.add("create_table_statements", this.get_tables_ddl_statements());
		st_tables.add("component_table_statements", this.setup_component_table_statements(this.the_project, modules));
		this.write_table_creation_scripts(st_tables);
		
	}
	
	private List<GpBaseSqlDto> get_tables_ddl_statements() throws Exception {
		return this.ddl_gen_support.get_table_create_statement_based_on_project(the_project);
	}

	private String setup_component_table_statements(GpProject the_project,List<GpModuleProperties> modules) {
		String component_table_statements = "";
		if(modules != null){
			//String db_name = the_project.getName().toLowerCase();
			for(GpModuleProperties property : modules){
				String[] table_statements = property.getTable_statements();
				if(table_statements!= null){
					for (String statement : table_statements) {
						//statement = statement.replace("$project_name$", db_name);
						component_table_statements += statement + "\n";
					}
				}
			}
		}
		return component_table_statements;
	}
	
	private void write_db_creation_script(ST st) throws Exception{	
		Path server_root_path = (Path) this.derived_configs.get(
				GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH).getObject_value(); 
		String the_path_string = server_root_path.toString() + this.file_separator + "db_creation_script.sql";
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	private void write_table_creation_scripts(ST st) throws Exception{
		Path server_root_path = (Path) this.derived_configs.get(
				GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH).getObject_value(); 		
		String the_path_string = server_root_path.toString() + this.file_separator + "tables_db_script.sql";
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	public void setCharacter_set(String character_set) {
		this.character_set = character_set;
	}
	
	public void setCollation(String collation) {
		this.collation = collation;
	}
	
	@Resource(name="GpSqlDMLGenSupport")
	public void setDdl_gen_support(GpSqlDMLGenSupport ddl_gen_support) {
		this.ddl_gen_support = ddl_gen_support;
	}
	
	public GpSqlDMLGenSupport getDdl_gen_support() {
		return ddl_gen_support;
	}
	
}
