package com.npb.gp.gen.workers.server.node.express;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpSqlQueriesDto;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;

@Component("GpSqlQueriesGenWorker")
public class GpSqlQueriesGenWorker extends GpGenNodeServerExpressBaseWorker {
	private GpDataAccessMySQLGenWorker the_worker;
	private GpSqlQueriesGenSupport the_gen_support;
	private Path template_group_path;
	private String file_extension = ".properties";
	private GpNounDao noun_dao;
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
		String server_nodejs_express_sql_queries_template_location = this.base_configs
				.get("server_nodejs_express_sql_queries_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_sql_queries_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_sql_queries_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_sql_queries_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
	}

	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path sql_queries_path = getThe_worker().getGen_service().getThe_directory_worker().getServer_sql_queries_root_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("queries", get_dml_statements_for_activity(activity));
		String the_path_string = sql_queries_path + this.file_separator + activity.getName() + "_SQL" + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	public List<GpBaseSqlDto> get_dml_statements_for_activity(GpActivity activity) throws Exception{
		List<GpNoun> nouns = noun_dao.find_by_project_id(the_project.getId());
		Map<Long, JSONArray> relationships_map = this.getThe_worker().getGen_service().getRelationships_map();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<GpBaseSqlDto> dto_list = new ArrayList<>();
		List<String> generated_verbs = new ArrayList<String>();
		for(GpVerb verb : the_verbs){
			if(verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				GpBaseSqlDto dto = this.the_gen_support.get_all_values_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				GpBaseSqlDto dto = this.the_gen_support.get_nound_by_id_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				GpBaseSqlDto dto = this.the_gen_support.search_for_update_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if(verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				GpBaseSqlDto dto = this.the_gen_support.create_dml_stmt(activity, relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpCreate");
				continue;
			}

			if(verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")){
				GpBaseSqlDto implicit_dto = this.the_gen_support.search_for_update_dml_stmt(activity);
				dto_list.add(implicit_dto);
				GpBaseSqlDto dto = this.the_gen_support.update_dml_stmt(activity,relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}
			if(verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				GpBaseSqlDto dto = this.the_gen_support.delete_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpDelete");
				continue;
			}
			if(verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				GpBaseSqlDto dto = this.the_gen_support.search_dml_stmt(activity, relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpSearch");
				continue;
			}
			
		}
		
		JSONArray json_parents = relationships_map.get(activity.getPrimary_noun().getId());
		if(json_parents != null){
			//get by parent id
			GpBaseSqlDto dto_get_byparent_id = this.the_gen_support.get_nound_by_parent_id_dml_stmt(activity, relationships_map);
			dto_list.add(dto_get_byparent_id);
			//delete by parent id
			GpBaseSqlDto dto_delete_by_parent_id = this.the_gen_support.delete_nound_by_parent_id_dml_stmt(activity, relationships_map);
			dto_list.add(dto_delete_by_parent_id);
		}
		return dto_list;
	}
	
	public GpDataAccessMySQLGenWorker getThe_worker() {
		return the_worker;
	}
	public void setThe_worker(GpDataAccessMySQLGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	public GpSqlQueriesGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	@Resource(name="GpSqlQueriesGenSupport")
	public void setThe_gen_support(GpSqlQueriesGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	
	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}
	
}
