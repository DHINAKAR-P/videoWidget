package com.npb.gp.gen.workers.server.node.express;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.node.express.GpNodeJsExpressRouterInfo;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleNodeRouters;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.node.express.support.routes.GpExpressRoutesGenSupport;

@Component("GpExpressRoutesGenWorker")
public class GpExpressRoutesGenWorker extends GpGenNodeServerExpressBaseWorker{
	private Path template_group_path;
	private Path template_index_routers_path;
	private Path template_router_path;
	private String file_extension = ".js";
	private String file_name = "routes";
	private GpExpressRoutesGenSupport the_gen_support;
	
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
				.get("server_nodejs_express_routes_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_routes_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_routes_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
		this.set_up_path_and_template_for_index_routers();
		this.set_up_path_and_template_for_router_file();
	}
	
	private void set_up_path_and_template_for_router_file() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_dao_file_template_location = this.base_configs
				.get("server_nodejs_express_router_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "router_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_router_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_router_path =   (Path) package_path_config.getObject_value();
	}

	private void set_up_path_and_template_for_index_routers(){
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_dao_file_template_location = this.base_configs
				.get("server_nodejs_express_index_routers_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "index_routers_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_index_routers_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_index_routers_path =   (Path) package_path_config.getObject_value();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		
		GpArchitypeConfigurations activities_prop = super.derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);
		ArrayList<GpActivity> the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		List<String> list_activities = new ArrayList<>();
		
		for(GpActivity activity : the_activities){
			if (activity.getModule_type() != null && activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				
			}
			else {
				this.generate_code_by_activity(activity, the_project, base_configs, derived_configs, the_flow, gen_manager);
				list_activities.add(activity.getName());
			}
		}
		List<GpModuleProperties> modules = getGen_service().getNot_default_activity_worker().getModule_properties_list();
		List<GpModuleNodeRouters> routers_list = new ArrayList<>();
		for(GpModuleProperties module : modules){
			GpModuleNodeRouters[] routers = module.getServer_meta_data().getNode().getExpress().getRouters();
			for(int i=0;i<routers.length;i++){
				routers_list.add(routers[i]);
			}
		}
		Path routers_path = getGen_service().getThe_directory_worker().getServer_routes_routers_path();
		ST st = super.read_template_group(this.template_index_routers_path, "output");
		st.add("activities", list_activities);
		st.add("routers_from_modules", routers_list);
		String the_path_string = routers_path + this.file_separator + "index" + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);	
		//routes file
		Path routes_path = getGen_service().getThe_directory_worker().getServer_routes_root_path();
		st = super.read_template_group(this.template_group_path, "output");
		st.add("activities", list_activities);	
		st.add("routers_from_modules", routers_list);
		the_path_string = routes_path + this.file_separator + file_name + file_extension;
		the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);	
	}
	
	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path routers_path = getGen_service().getThe_directory_worker().getServer_routes_routers_path();
		ST st = super.read_template_group(this.template_router_path, "output");
		if(activity.getPrimary_noun() != null)
			st.add("controller", "var controller = require(\"../../controllers/" + activity.getName()+ "Controller\")");
		st.add("routes", the_gen_support.get_the_routes(activity));
		String the_path_string = routers_path + this.file_separator + activity.getName() + "Router" + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	public GpExpressRoutesGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	@Resource(name="GpExpressRoutesGenSupport")
	public void setThe_gen_support(GpExpressRoutesGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}

}
