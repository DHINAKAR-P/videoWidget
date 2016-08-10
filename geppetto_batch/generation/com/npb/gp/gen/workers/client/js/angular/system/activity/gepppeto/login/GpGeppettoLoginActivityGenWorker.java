package com.npb.gp.gen.workers.client.js.angular.system.activity.gepppeto.login;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;

@Component("GpGeppettoLoginActivityGenWorker")
public class GpGeppettoLoginActivityGenWorker extends GpGenJSClientAngularBaseWorker{
	private Path rest_url_service_template_path;
	private String rest_url_service_template_name;
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		// TODO Auto-generated method stub
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
	}
	
	private void set_up_paths_and_templates() {
		// TODO Auto-generated method stub
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("system_activity_geppetto_login_rest_url_service_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_geppetto_login_rest_url_service_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		rest_url_service_template_name =  this.base_configs.get(
				"system_activity_geppetto_login_rest_url_service_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.rest_url_service_template_path =   (Path) package_path_config.getObject_value();
	}

	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		if(get_generation_service().getDirectory_gen_worker().android_created)
			this.create_rest_url_service_file_android_phone(gen_manager);
		if(get_generation_service().getDirectory_gen_worker().ios_created)
			this.create_rest_url_service_file_ios_phone(gen_manager);
	}
	
	private void create_rest_url_service_file_android_phone(IGpGenManager gen_manager) throws Exception{
		String server_url = this.base_configs.get("server_host_name").getValue();
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		String app_port = "";
		if(get_generation_service().back_isJavaSpring || get_generation_service().back_isJavaSpringBootJpa){
			String docker_json = gen_manager.get_user().getDocker_json();
			if(docker_json != null){
				JSONArray json_array = new JSONArray(docker_json);
				for(int i=0;i<json_array.length();i++){
					JSONObject json = json_array.getJSONObject(i);
					String cont_name = json.getString("contname");
					if(cont_name.startsWith("tomcat")){
						app_port = json.getString("port");
						 break;
					}
				}
			}
			else{
				app_port = "SERVER_PORT";
			}
		}
		else if(get_generation_service().back_isJavaScriptNodeJSExpress){
			app_port = GpServerNodeExpressGenService.server_port;
		}
		Path services_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_services_directory_path();
		STGroupDir st_Group = new STGroupDir(this.rest_url_service_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.rest_url_service_template_name);
		st.add("server_url", server_url);
		st.add("app_port", app_port);
		st.add("project_name", project_name);
		String the_path_string = services_path.toString() + this.file_separator + "restURLService.js";
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	private void create_rest_url_service_file_ios_phone(IGpGenManager gen_manager) throws Exception{
		String server_url = this.base_configs.get("server_host_name").getValue();
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		String app_port = "";
		if(get_generation_service().back_isJavaSpring || get_generation_service().back_isJavaSpringBootJpa){
			String docker_json = gen_manager.get_user().getDocker_json();
			if(docker_json != null){
				JSONArray json_array = new JSONArray(docker_json);
				for(int i=0;i<json_array.length();i++){
					JSONObject json = json_array.getJSONObject(i);
					String cont_name = json.getString("contname");
					if(cont_name.startsWith("tomcat")){
						app_port = json.getString("port");
						 break;
					}
				}
			}
			else{
				app_port = "SERVER_PORT";
			}
		}
		else if(get_generation_service().back_isJavaScriptNodeJSExpress){
			app_port = GpServerNodeExpressGenService.server_port;
		}
		Path services_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_services_directory_path();
		STGroupDir st_Group = new STGroupDir(this.rest_url_service_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.rest_url_service_template_name);
		st.add("server_url", server_url);
		st.add("app_port", app_port);
		st.add("project_name", project_name);
		String the_path_string = services_path.toString() + this.file_separator + "restURLService.js";
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
}
