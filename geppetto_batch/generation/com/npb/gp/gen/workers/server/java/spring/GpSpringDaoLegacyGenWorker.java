package com.npb.gp.gen.workers.server.java.spring;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.dao.mysql.support.activity_gen.InsertActivity;
import com.npb.gp.dao.mysql.support.activity_gen.UpdateActivity;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.GpLegacyDaoVerbGenInfo;
import com.npb.gp.gen.util.dto.GpResourceBundleReference;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.spring.support.legacydao.GpLegacyDaoGenSupport;
import com.npb.gp.gen.workers.server.rdms.mysql.GpMySqlDMLWorker;
/**
 * 
 * @author Dan Castillo
 * Date Created: 12/30/2014</br>
 * @since .2</p> 
 * 
 * Generates the code for a Java Spring DAO layer that uses the Spring template</br>
 * and mapper approach </p> 
 */
@Component("GpSpringDaoLegacyGenWorker")
public class GpSpringDaoLegacyGenWorker extends GpGenJavaServerSpringBaseWorker {
	private Path template_group_path;
	private Path dao_path;
	private String dao_package_name;
	
	private GpLegacyDaoGenSupport dao_support;
	private GpMySqlDMLWorker dml_worker;
	private GpSpringResourceBundleGenWorker bundle_worker;
	private ArrayList<GpBaseSqlDto> sql_stmts;
	private String create_bundle_sql = "";
	private String update_bundle_sql = "";
	private String insert_mapper_type;
	private String update_mapper_type;
	private String insert_mapper_reference;
	private String update_mapper_reference;
	private boolean read_only_verbs = false;  //need to see if Activity only has read only verbs
	private GpSpringDaoLegacySupportGenWorker mapper_worker;
	
	
	public GpLegacyDaoGenSupport getDao_support() {
		return dao_support;
	}
	
	@Resource(name="GpLegacyDaoGenSupport")
	public void setDao_support(GpLegacyDaoGenSupport dao_support) {
		this.dao_support = dao_support;
	}
	
	public GpMySqlDMLWorker getDml_worker() {
		return dml_worker;
	}
	@Resource(name="GpMySqlDMLWorker")
	public void setDml_worker(GpMySqlDMLWorker dml_worker) {
		this.dml_worker = dml_worker;
	}
	
	


	public String getCreate_bundle_sql() {
		return create_bundle_sql;
	}

	public void setCreate_bundle_sql(String create_bundle_sql) {
		this.create_bundle_sql = create_bundle_sql;
	}

	public String getUpdate_bundle_sql() {
		return update_bundle_sql;
	}

	public void setUpdate_bundle_sql(String update_bundle_sql) {
		this.update_bundle_sql = update_bundle_sql;
	}

	public GpSpringResourceBundleGenWorker getBundle_worker() {
		return bundle_worker;
	}

	@Resource(name="GpSpringResourceBundleGenWorker")
	public void setBundle_worker(GpSpringResourceBundleGenWorker bundle_worker) {
		this.bundle_worker = bundle_worker;
	}
	
	

	public GpSpringDaoLegacySupportGenWorker getMapper_worker() {
		return mapper_worker;
	}
	@Resource(name="GpSpringDaoLegacySupportGenWorker")
	public void setMapper_worker(GpSpringDaoLegacySupportGenWorker mapper_worker) {
		this.mapper_worker = mapper_worker;
	}

	public ArrayList<GpBaseSqlDto> getSql_stmts() {
		return sql_stmts;
	}

	public void setSql_stmts(ArrayList<GpBaseSqlDto> sql_stmts) {
		this.sql_stmts = sql_stmts;
	}

	
	
	public String getInsert_mapper_type() {
		return insert_mapper_type;
	}

	public void setInsert_mapper_type(String insert_mapper_type) {
		this.insert_mapper_type = insert_mapper_type;
	}

	public String getUpdate_mapper_type() {
		return update_mapper_type;
	}

	public void setUpdate_mapper_type(String update_mapper_type) {
		this.update_mapper_type = update_mapper_type;
	}

	public String getInsert_mapper_reference() {
		return insert_mapper_reference;
	}

	public void setInsert_mapper_reference(String insert_mapper_reference) {
		this.insert_mapper_reference = insert_mapper_reference;
	}

	public String getUpdate_mapper_reference() {
		return update_mapper_reference;
	}

	public void setUpdate_mapper_reference(String update_mapper_reference) {
		this.update_mapper_reference = update_mapper_reference;
	}

	@Override
	/**
	 * Generates DAOS for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		
	}


	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

	
	//System.out.println("In GpSpringDaoLegacyGenWorker - generate_code_by_activity");
	
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
	
	
		this.set_up_paths_and_templates();
		this.do_generation(activity);
	
	}

	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		
		this.set_up_paths_and_templates();
	}
	
	private void do_generation(GpActivity activity) throws Exception{
		

		//super.get_generation_service().getMysql_dml_worker().ge
		this.checK_for_update_verbs(activity);
		String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();

		//String domain_core_package_name = this.derived_configs.get("domain_core_package_name").getValue();

		ST st = super.read_template_group(this.template_group_path, "output");
		
		st.add("package_name", this.dao_package_name);
		
		ArrayList<String> import_list = this.set_up_imports(activity);
		st.add("the_imports", import_list);
		String class_name = this.capitalize(activity.getName()) + "Dao";
		st.add("class_name", class_name);
		
		
		this.dml_worker = super.get_generation_service().getMysql_dml_worker();
		sql_stmts = this.dml_worker
								.get_dml_statements_for_activity(activity);
		this.bundle_worker.set_up_resource_bundle_references(activity, sql_stmts);
		
		this.bundle_worker = super.get_generation_service()
													.getResource_bundle_worker();
		
		this.bundle_worker.generate_sql_bundle(activity, sql_stmts);
		
		st.add("resource_bundle_info",	sql_stmts);

		/*am breaking up the if to make it more readable
		 *so that I dont have one LONG IF - Dan - 01/08/2015
		 *   
		 */
		if(!this.read_only_verbs){
			st.add("update_mapper_type", this.update_mapper_type );
			st.add("update_mapper_reference", this.update_mapper_reference + ";");
			
			st.add("insert_mapper_type", this.insert_mapper_type);
			st.add("insert_mapper_reference", this.insert_mapper_reference + ";");

		}

		/*am breaking up the if to make it more readable
		 * so that I don't have one LONG IF - Dan - 01/08/2015
		 *   
		 */
		if(!this.read_only_verbs){
			for(GpBaseSqlDto sql_dto : sql_stmts){
				if(sql_dto.verb_action_on_data.equals("GpCreate")){
					create_bundle_sql = sql_dto.local_string_reference;
				}else if(sql_dto.verb_action_on_data.equals("GpUpdate")){
					update_bundle_sql = sql_dto.local_string_reference;
				}
				
			}
			/* TODO: check this, maybe it's not needed
			st.add("insert_sql", this.insert_mapper_type + "." +"SQL_INSERT_" 
			+ activity.getPrimary_noun().getName().toUpperCase() + " = " + create_bundle_sql + ";");

			st.add("update_sql", this.update_mapper_type + "." +"SQL_UPDATE_" 
			+ activity.getPrimary_noun().getName().toUpperCase() + " = " + update_bundle_sql + ";");
			*/
			
			String update_reference_creation_value = "this."+ this.update_mapper_reference
					+ " = new "+ this.update_mapper_type + "(this.dataSource);";
			st.add("update_reference_creation", update_reference_creation_value);
			
			String insert_reference_creation_value = "this."+ this.insert_mapper_reference
					+ " = new "+ this.insert_mapper_type + "(this.dataSource);";
			st.add("insert_reference_creation", insert_reference_creation_value);
			
		}
		

		st.add("the_read_verbs", this.set_up_read_only_verbs(activity));
		
		st.add("the_update_verbs", this.set_up_update_verbs(activity));
		
		st.add("copy_right_range", copy_right_range);
		
		
		/*
		 * 
	        this.insert_activity = new InsertActivity(this.dataSource);
	        this.update_activity = new UpdateActivity(this.dataSource);
	        
	            	$update_reference_creation$
	    $insert_reference_creation$

	        
		 */
		
		//st.add("the_gen_verbs", this.set_up_verbs(activity));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
		
		
		
		
		String the_path_string = dao_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		

		//deal with mappers
		this.mapper_worker.setDao_path(this.dao_path);
		this.mapper_worker.prep_derived_values(the_project, base_configs, derived_configs);
		this.mapper_worker.set_up_paths_and_templates(activity, this.dao_path);
		this.mapper_worker.setDao_package_name(this.dao_package_name);
		this.mapper_worker.set_generation_service(get_generation_service());
		this.mapper_worker.do_generation(activity, this.dao_path);

	}
	
	
	private ArrayList<GpLegacyDaoVerbGenInfo> set_up_read_only_verbs(GpActivity an_activity){
		
		ArrayList<GpLegacyDaoVerbGenInfo> read_only_verbs 
									= new ArrayList<GpLegacyDaoVerbGenInfo>();
		
		ArrayList<GpLegacyDaoVerbGenInfo> all_verbs 
					= dao_support.get_verb_method_implmentation(an_activity);
		
		for(GpLegacyDaoVerbGenInfo dto : all_verbs){
			if(this.is_read_only_verb(dto.verb_action_on_data)){
				read_only_verbs.add(dto);
			}
		}
		return read_only_verbs;
	}
	
	private ArrayList<GpLegacyDaoVerbGenInfo> set_up_update_verbs(GpActivity an_activity){

		ArrayList<GpLegacyDaoVerbGenInfo> update_only_verbs 
									= new ArrayList<GpLegacyDaoVerbGenInfo>();
		
		ArrayList<GpLegacyDaoVerbGenInfo> all_verbs 
					= dao_support.get_verb_method_implmentation(an_activity);
		for(GpLegacyDaoVerbGenInfo dto : all_verbs){
			if(!this.is_read_only_verb(dto.verb_action_on_data)){
				update_only_verbs.add(dto);
			}
		}

		return update_only_verbs;
	}
	
	private void checK_for_update_verbs(GpActivity activity){
		
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpUpdate")){
				this.read_only_verbs = false;
				break;
			}else if(a_verb.getAction_on_data().equals("GpDelete")){
				this.read_only_verbs = false;
				break;
			}else if(a_verb.getAction_on_data().equals("GpCreate")){
				this.read_only_verbs = false;
				break;
			}
		}
		
		
	}
	private boolean is_read_only_verb(String action_on_data){
		
		if(action_on_data.equals("GpUpdate")){
			return false;
		}else if(action_on_data.equals("GpDelete")){
			return false;
		}else if(action_on_data.equals("GpCreate")){
			return false;
		}else{
			return true;
		}
		
	}
	
	@Override
	 public String get_data_type_for_activity(GpActivity an_activity)
				throws Exception{

		String dao_data_type = super.capitalize(an_activity.getName()) 
																	+ "Dao";
		return dao_data_type;



	 }	
	@Override
	public String get_package_name_for_activity(GpActivity an_activity) 
			 												throws Exception{
		 
		 String class_name = this.get_data_type_for_activity(an_activity);
		 String package_name = this.dao_package_name + "." + class_name;
		 return package_name;

	 }

	
	@Override
	public HashMap<String,  GpJavaMethodDescription> 
					get_method_signitures(GpActivity an_activity)	throws Exception{
	
		return this.dao_support.get_method_signitures(an_activity);
	
	}
	
	
	private ArrayList<String> set_up_imports(GpActivity activity) throws Exception{
		
		ArrayList<String> import_list = new ArrayList<String>();

		 String domain_core_package_name = this.derived_configs
				 			.get("domain_core_package_name").getValue();

		//set up primary noun import
		 if(activity.getPrimary_noun() != null){
			 domain_core_package_name = domain_core_package_name + 
					 		"." + activity.getPrimary_noun().getName();
		 }
		 import_list.add(domain_core_package_name); 
		
		 String gep_user_import = this.derived_configs
		 				.get("domain_core_package_name").getValue() + ".GpUser";
		 
		 import_list.add(gep_user_import);
		 
		 //this.set_up_mapper_paths_and_templates(activity);

		 //set up imports for mappers
		 if(activity.getPrimary_noun() != null){
			 String mapper = this.dao_package_name + ".support" 
						+ "." + activity.getPrimary_noun().getName()+ "."  + activity.getPrimary_noun().getName() + "Mapper"; 
			 import_list.add(mapper);
		 
			this.insert_mapper_type = activity.getPrimary_noun().getName() 
															+ "InsertMapper";
			this.insert_mapper_reference =  activity.getPrimary_noun()
									.getName() + "_insert_mapper";
			
			String insert_mapper_import = this.dao_package_name + ".support"
					+ "."  + activity.getPrimary_noun().getName() + "." + this.insert_mapper_type;
			import_list.add(insert_mapper_import); 
			
			this.update_mapper_type = activity.getPrimary_noun().getName()
																+ "UpdateMapper";
			this.update_mapper_reference =  activity.getPrimary_noun()
					.getName() + "_update_mapper";
	
			String update_mapper_import = this.dao_package_name + ".support" 
					+ "."  + activity.getPrimary_noun().getName() + "." + this.update_mapper_type;
			import_list.add(update_mapper_import); 
		}
		
		return import_list;
		
	}

	private void set_up_paths_and_templates() throws Exception {
		
		/*
		 * first see if the location where the code will be generated to
		 * has already been derived - if not create a new config for it 
		 */
		String config_name = "";
		String[] tokens;
		Path server_root_path = (Path)this.derived_configs.get(
					GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();
		
		
		//System.out.println("In GpSpringServiceGenWorker - set_up_paths_and_templates "
		//		+ " server_root_path.toString() is: " + server_root_path.toString());
		
		String gen_base_java_server_dao_directory =  this.base_configs.get(
									"gen_base_java_server_dao_directory").getValue();
		
		tokens = this.tokenize_string(
				gen_base_java_server_dao_directory, null);
		
		
		
		
		config_name = this.build_name_from_tokens(tokens);
		//System.out.println("In GpSpringServiceGenWorker - after the tokens config_name is: " + config_name);

		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations dao_path_config = this.derived_configs.get(config_name);
		if(dao_path_config == null){
			
			//create the directories and store the path
			@SuppressWarnings("unchecked")
			ArrayList<GpTechProperties> tech_property_list = 
					(ArrayList<GpTechProperties>) ((GpArchitypeConfigurations)
					this.base_configs
					.get(GpGenConstants.TECH_PROPERTY_LIST)).getObject_value();
			String db_type = "";
			for(GpTechProperties prop : tech_property_list){
				if(this.the_project.getServer_dbms() == prop.getId() ){
					db_type =  prop.getName().toLowerCase();	
				}
				
			}
			Path dao_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] +  this.file_separator + db_type);
			Files.createDirectories(dao_path);
			this.dao_path = dao_path;
			
			GpTechProperties tech_prop = super
					.get_tech_property(this.the_project.getServer_dbms());
			
			this.dao_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue()+ "." + tokens[0] +"." + tech_prop.getName().toLowerCase() ;

			dao_path_config = new GpArchitypeConfigurations();
			
			dao_path_config.setName(config_name);
			dao_path_config.setObject_value(dao_path);
			this.derived_configs.put(config_name, dao_path_config);
				
		}
		
		/*
		 * 
		 * second determine if the location of the template that will be used 
		 * for this code generation have been derived previously if not
		 * derive it
		 * 
		 */

		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		//System.out.println("In GpSpringServiceGenWorker - - root_code_template_location is: " + root_code_template_location);
		
		String server_java_spring_legacy_dao_template_location = this.base_configs
					.get("server_java_spring_legacy_dao_template_location").getValue();
		
		//System.out.println("In GpSpringServiceGenWorker - - server_java_spring_controller_template_location is: " + server_java_spring_controller_template_location);
		
		tokens = this.tokenize_string(
				server_java_spring_legacy_dao_template_location, null);

		//String template_path_config_name = "";
		config_name = this.build_name_from_tokens(tokens) + "_legacy_dao_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_legacy_dao_template_path_config 
														= this.derived_configs.get(config_name);
		
		if(server_java_spring_legacy_dao_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_legacy_dao_template_name").getValue();
			
			//System.out.println("In GpSpringServiceGenWorker - OJOJOJOJOJOJOJOJOJOJOJO the template_name is: " + template_name);
			
			Path server_java_spring_service_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_legacy_dao_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_legacy_dao_template_path_config.setName(config_name);
			server_java_spring_legacy_dao_template_path_config.setObject_value(server_java_spring_service_template_path);
			this.derived_configs.put(config_name, server_java_spring_legacy_dao_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_legacy_dao_template_path_config.getObject_value(); 
		this.dao_path = (Path)dao_path_config.getObject_value();

	}
}
