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

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressOtherServices;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.node.express.support.service.GpExpressServiceGenSupport;

@Component("GpExpressServiceGenWorker")
public class GpExpressServiceGenWorker extends GpGenNodeServerExpressBaseWorker{
	private Path template_group_path;
	private String file_extension = ".js";
	private GpExpressServiceGenSupport the_gen_support;
	
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
				.get("server_nodejs_express_service_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_service_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_service_template_name").getValue();
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
		Path services_path = getGen_service().getThe_directory_worker().getServer_services_root_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("services", the_gen_support.get_the_methods(activity));
		st.add("other_services_to_import", this.handle_imports(activity));
		st.add("activity_name", activity.getName());
		String the_path_string = services_path + this.file_separator + activity.getName() + "Service" + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	private List<GpNodeExpressOtherServices> handle_imports(GpActivity activity) {
		List<GpNodeExpressOtherServices> other_services_to_import = new ArrayList<>();
		Map<Long, GpRelationshipInfo> map_relations = this.getGen_service().getRelation_between_activities();
		if(map_relations != null){
			GpRelationshipInfo rel_info = map_relations.get(activity.getId());
			if(rel_info != null){
				List<GpChildRelationshipInfo> childs = rel_info.getChilds();
				for(GpChildRelationshipInfo child: childs){
					GpNodeExpressOtherServices expressOtherServices = new GpNodeExpressOtherServices();
					expressOtherServices.activity_name = child.getActivity().getName();
					expressOtherServices.noun_name = child.getNoun().getName();
					other_services_to_import.add(expressOtherServices);
				}
				
			}
		}
		return other_services_to_import;
	}


	public GpExpressServiceGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	@Resource(name="GpExpressServiceGenSupport")
	public void setThe_gen_support(GpExpressServiceGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
}
