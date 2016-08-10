package com.npb.gp.gen.workers.server.node.express;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.java.springboot.support.domain.GpSpringBootNounAttribute;

@Component("GpSequelizeModelGenWorker")
public class GpSequelizeModelGenWorker extends GpGenNodeServerExpressBaseWorker{
	private GpDataAccessMySQLGenWorker the_worker;
	private Path template_path;
	private String template_name;
	private List<String> generated_nouns;
	private Map<Long, JSONArray> json_relations;
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		// TODO Auto-generated method stub
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		generated_nouns = new ArrayList<>();
	}
	
	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("server_nodejs_express_sequelize_model_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_sequelize_model_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		template_name =  this.base_configs.get(
				"server_nodejs_express_sequelize_model_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_path =   (Path) package_path_config.getObject_value();
	}

	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path models_path = getThe_worker().getGen_service().getThe_directory_worker().getServer_models_root_path();
		GpNoun noun = activity.getPrimary_noun();
		if(noun != null && !generated_nouns.contains(noun.getName())){
			STGroupDir st_Group = new STGroupDir(this.template_path.toString() , '$', '$');
			ST st = st_Group.getInstanceOf(this.template_name);
			st.add("noun_name", noun.getName());
			st.add("attribs_def", get_attrib_defs(noun));
			String file_name = noun.getName();
			String file_extension = ".js";
			String the_path_string = models_path.toString() + this.file_separator + file_name + file_extension;
			Path test_write_path = Paths.get(the_path_string);
			super.write_file(test_write_path, st);
			generated_nouns.add(noun.getName());
		}
	}
	
	private String get_attrib_defs(GpNoun noun) throws JSONException{
		String attrib_defs = "id: {\n"
							+ "\ttype : DataTypes.INTEGER,\n"
							+ "\tprimaryKey : true,\n"
							+ "\tautoIncrement : true\n"
							+ "},\n";
		//Map<Long, JSONArray> map_relationship = this.handle_relationships(the_nouns);
		List<GpNounAttribute> attribs = noun.getNounattributes();
		for(GpNounAttribute attr : attribs){
			String sub_type = attr.getSubtype();
			if(sub_type.equals(GpNounConstants.SUB_TYPE_NUMBER)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.INTEGER,\n";
				continue;
			}
			
			if(sub_type.equals(GpNounConstants.SUB_TYPE_TEXT)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.STRING,\n";
				continue;
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_CURRENCY) || sub_type.equals(GpNounConstants.SUB_TYPE_DECIMAL)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.DECIMAL(10,4),\n";
				continue;
			
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_BOOL)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.STRING(1),\n";
				continue;
			
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_DATE)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.DATE,\n";
				continue;			
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_PICTURE) 
					|| sub_type.equals(GpNounConstants.SUB_TYPE_SOUND) 
					|| sub_type.equals(GpNounConstants.SUB_TYPE_VIDEO)){
				attrib_defs += attr.getName().toLowerCase() + ": DataTypes.STRING,\n";
				continue;
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
				continue;
			}
			if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
		}
		JSONArray parent_noun_json_array = this.json_relations.get(noun.getId());
		if(parent_noun_json_array != null){
			for(int i = 0; i < parent_noun_json_array.length(); i++){
				JSONObject json_parent = parent_noun_json_array.getJSONObject(i);
				String type = json_parent.getString("type");//would it help?
				String rel_type = json_parent.getString("Relationship_type");
				String name = json_parent.getString("Noun_name");
				if(rel_type.equals("child")){
					attrib_defs += name + "_id: DataTypes.INTEGER,\n";
				}
			}
		}
		attrib_defs = attrib_defs.substring(0,attrib_defs.length()-2);
		return attrib_defs;
	}
	
	public GpDataAccessMySQLGenWorker getThe_worker() {
		return the_worker;
	}
	
	public void setThe_worker(GpDataAccessMySQLGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public void set_relations(Map<Long, JSONArray> json_relationships) {
		this.json_relations = json_relationships;
	}
}
