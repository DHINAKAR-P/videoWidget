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
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleNodePackage;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeJsExpressPackage;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;

@Component("GpDataAccessMySQLGenWorker")
public class GpDataAccessMySQLGenWorker extends GpGenNodeServerExpressBaseWorker{
	private GpSqlQueriesGenWorker the_sql_queries_worker;
	private GpSequelizeModelGenWorker the_model_worker;
	private final String key_name_config_json = "database"; 
	private final String database_key_for_username = "username";
	private final String database_key_for_password = "password";
	private final String database_key_for_db_name = "name";
	private final String database_key_for_host = "host";
	private final String database_key_for_dialect = "dialect";
	private Path index_models_template_path;
	private String index_models_template_name;
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		the_sql_queries_worker.prep_derived_values(the_project, base_configs, derived_configs);
		the_sql_queries_worker.setThe_worker(this);
		the_model_worker.prep_derived_values(the_project, base_configs, derived_configs);
		the_model_worker.setThe_worker(this);
		the_model_worker.set_relations(this.handle_relationships(the_project.getProject_nouns()));
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		this.export_values_to_other_files();
		this.generate_sql_queries(gen_manager);
		this.generate_models(gen_manager);
	}

	private void export_values_to_other_files() throws JSONException {
		// Required packages to install - adding to the package.json file
		this.add_required_packages_to_package_json_file();
		// Add database value to config.json file
		this.add_db_value_to_config_json();
	}
	
	private void add_db_value_to_config_json() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(database_key_for_username, "root");
		jsonObject.put(database_key_for_password, "tang3456");
		jsonObject.put(database_key_for_db_name, the_project.getName().toLowerCase());
		jsonObject.put(database_key_for_host, "mydb");
		jsonObject.put(database_key_for_dialect, "mysql");
		getGen_service().getThe_config_worker().add_json_value_jsonObject(key_name_config_json, jsonObject);
	}
	
	private void add_required_packages_to_package_json_file(){
		GpModuleNodePackage mysql_package = new GpModuleNodePackage(); 
		mysql_package.setName("mysql");
		mysql_package.setVersion("^2.10.2");
		getGen_service().getThe_package_worker().add_package_to_install(mysql_package);
		GpModuleNodePackage properties_reader_package = new GpModuleNodePackage();
		properties_reader_package.setName("properties-reader");
		properties_reader_package.setVersion("0.0.15");
		getGen_service().getThe_package_worker().add_package_to_install(properties_reader_package);
		GpModuleNodePackage sequelize_package = new GpModuleNodePackage(); 
		sequelize_package.setName("sequelize");
		sequelize_package.setVersion("^3.17.3");
		getGen_service().getThe_package_worker().add_package_to_install(sequelize_package);
	}

	private void set_up_paths_and_templates() {
		this.set_up_path_and_template_for_index_models();
	}
	
	private void set_up_path_and_template_for_index_models(){
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("server_nodejs_express_index_models_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_index_models_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		index_models_template_name =  this.base_configs.get(
				"server_nodejs_express_index_models_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.index_models_template_path =   (Path) package_path_config.getObject_value();
	}
	
	private void generate_models(IGpGenManager gen_manager) throws Exception{
		this.generate_index_model_file();
		GpArchitypeConfigurations activities_prop = derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);
		ArrayList<GpActivity> the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		for(GpActivity activity : the_activities){
			the_model_worker.generate_code_by_activity(activity, the_project, base_configs, derived_configs, null, gen_manager);
		}
	}
	
	private void generate_index_model_file() throws Exception{
		Path models_path = getGen_service().getThe_directory_worker().getServer_models_root_path();
		STGroupDir st_Group = new STGroupDir(this.index_models_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.index_models_template_name);
		st.add("config_file_name", getGen_service().getThe_config_worker().getFile_name());
		st.add("db_key_name_config_json", key_name_config_json);
		st.add("key_for_name", database_key_for_db_name);
		st.add("key_for_username", database_key_for_username);
		st.add("key_for_password", database_key_for_password);
		String file_name = "index";
		String file_extension = ".js";
		String the_path_string = models_path.toString() + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	@SuppressWarnings("unchecked")
	private void generate_sql_queries(IGpGenManager gen_manager) throws Exception{
		GpArchitypeConfigurations activities_prop = derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);
		ArrayList<GpActivity> the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		for(GpActivity activity : the_activities){
			the_sql_queries_worker.generate_code_by_activity(activity, the_project, base_configs, derived_configs, null, gen_manager);
		}
	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, GpNoun> map_nouns = new HashMap<>();
		for(GpNoun noun : nouns){
			map_nouns.put(noun.getId(), noun);
		}
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long child_noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						//child
						JSONArray child_noun_json_array = relationships_map.get(child_noun_id);
						if(child_noun_json_array == null)
							child_noun_json_array = new JSONArray();
						JSONObject json_child = new JSONObject();
						json_child.put("Noun_name", map_nouns.get(noun.getId()).getName());
						json_child.put("Relationship_type", "child");
						json_child.put("type", "ONE_TO_ONE");
						child_noun_json_array.put(json_child);
						relationships_map.put(child_noun_id, child_noun_json_array);
					}
					continue;
				}
				if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(type.equals("Noun")){
							long child_noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
							//child
							JSONArray child_noun_json_array = relationships_map.get(child_noun_id);
							if(child_noun_json_array == null)
								child_noun_json_array = new JSONArray();
							JSONObject json_child = new JSONObject();
							json_child.put("Noun_name", map_nouns.get(noun.getId()).getName());
							json_child.put("Relationship_type", "child");
							json_child.put("type", "ONE_TO_MANY");
							child_noun_json_array.put(json_child);
							relationships_map.put(child_noun_id, child_noun_json_array);
							continue;
						}
					}
					
					
				}
			}
		}
		return relationships_map;
	}

	public GpSqlQueriesGenWorker getThe_sql_queries_worker() {
		return the_sql_queries_worker;
	}
	@Resource(name="GpSqlQueriesGenWorker")
	public void setThe_sql_queries_worker(GpSqlQueriesGenWorker the_sql_queries_worker) {
		this.the_sql_queries_worker = the_sql_queries_worker;
	}

	public GpSequelizeModelGenWorker getThe_model_worker() {
		return the_model_worker;
	}
	@Resource(name="GpSequelizeModelGenWorker")
	public void setThe_model_worker(GpSequelizeModelGenWorker the_model_worker) {
		this.the_model_worker = the_model_worker;
	}

	public String getKey_name_config_json() {
		return key_name_config_json;
	}

	public String getDatabase_key_for_username() {
		return database_key_for_username;
	}

	public String getDatabase_key_for_password() {
		return database_key_for_password;
	}

	public String getDatabase_key_for_db_name() {
		return database_key_for_db_name;
	}

	public String getDatabase_key_for_host() {
		return database_key_for_host;
	}

	public String getDatabase_key_for_dialect() {
		return database_key_for_dialect;
	}
	
	
}
