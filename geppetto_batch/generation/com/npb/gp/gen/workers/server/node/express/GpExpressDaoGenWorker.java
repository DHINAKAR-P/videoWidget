package com.npb.gp.gen.workers.server.node.express;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.node.express.support.dao.GpExpressDaoGenSupport;

@Component("GpExpressDaoGenWorker")
public class GpExpressDaoGenWorker extends GpGenNodeServerExpressBaseWorker{
	private Path template_group_path;
	private String file_extension = ".js";
	private GpExpressDaoGenSupport the_gen_support;
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		the_gen_support.setThe_worker(this);
	}

	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_dao_file_template_location = this.base_configs
				.get("server_nodejs_express_dao_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_dao_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_dao_template_name").getValue();
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
		Path daos_path = getGen_service().getThe_directory_worker().getServer_daos_root_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("daos", the_gen_support.get_the_methods(activity));
		st.add("activity_name", activity.getName());
		st.add("noun_name", activity.getPrimary_noun().getName());
		String the_path_string = daos_path + this.file_separator + activity.getName() + "Dao" + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	public GpExpressDaoGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	@Resource(name="GpExpressDaoGenSupport")
	public void setThe_gen_support(GpExpressDaoGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
}
