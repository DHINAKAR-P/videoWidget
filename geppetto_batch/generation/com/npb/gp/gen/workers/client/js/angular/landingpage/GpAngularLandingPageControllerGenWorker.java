package com.npb.gp.gen.workers.client.js.angular.landingpage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularLandingPageGenWorker;
import com.npb.gp.gen.workers.client.js.angular.landingpage.support.controller.GpAngularLandingPageControllerGenSupport;

@Component("GpAngularLandingPageControllerGenWorker")
public class GpAngularLandingPageControllerGenWorker extends GpGenJSClientAngularBaseWorker{
	private GpAngularLandingPageControllerGenSupport the_gen_support;
	private GpAngularLandingPageGenWorker the_landing_page_worker;
	private Path template_group_path;
	private Path desktop_template_group_path;
	public static String file_name = "AplicationLevelCtrl";
	private String file_extension = ".js";
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		this.set_up_paths_and_templates_for_desktop();
		the_gen_support.setThe_worker(this);
	}
	
	private void set_up_paths_and_templates_for_desktop() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_desktop_landing_page_controller_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_desktop_landing_page_controller_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"client_angular_desktop_landing_page_controller_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.desktop_template_group_path =   (Path) package_path_config.getObject_value();
	}

	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_landing_page_controller_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_landing_page_controller_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"client_angular_landing_page_controller_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		this.generate_mobile_controllers();
		if(get_generation_service().getDirectory_gen_worker().desktop_created)
			this.generate_desktop();
	}
	
	private void generate_mobile_controllers() throws Exception{
		if(get_generation_service().getDirectory_gen_worker().android_created){
			this.generate_android_mobile();
			get_generation_service().getControllers_to_import_android_phone().add(GpAngularLandingPageControllerGenWorker.file_name);
		}
		if(get_generation_service().getDirectory_gen_worker().ios_created){
			this.generate_ios_mobile();
			get_generation_service().getControllers_to_import_ios_phone().add(GpAngularLandingPageControllerGenWorker.file_name);
		}
	}
	
	private void generate_desktop() throws Exception {
		String dependent_services_for_desktop = get_generation_service().getController_worker().getDependent_services_for_desktop();
		Path desktop_controller_path = get_generation_service().getDirectory_gen_worker().getWindows_app_controller_directory_path();
		ST st = super.read_template_group(this.desktop_template_group_path, "output");
		st.add("the_code", the_gen_support.get_controller_code_for_desktop());
		st.add("controller_name", file_name);
		st.add("dependent_services", get_generation_service().getController_worker().getGen_support().get_dependent_services(dependent_services_for_desktop));
		st.add("dependent_services_f", get_generation_service().getController_worker().getGen_support().get_dependent_services_for_function(dependent_services_for_desktop));
		String the_path_string = desktop_controller_path + this.file_separator + file_name + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
		get_generation_service().getControllers_to_import_for_desktop().add(GpAngularLandingPageControllerGenWorker.file_name);
	}

	private void generate_ios_mobile() throws Exception{
		String dependent_services_for_ios = get_generation_service().getController_worker().getDependent_services_for_ios_phone();
		Path ios_controller_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_controller_directory_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("the_code", the_gen_support.get_controller_code());
		st.add("controller_name", file_name);
		st.add("dependent_services", get_generation_service().getController_worker().getGen_support().get_dependent_services(dependent_services_for_ios));
		st.add("dependent_services_f", get_generation_service().getController_worker().getGen_support().get_dependent_services_for_function(dependent_services_for_ios));
		String the_path_string = ios_controller_path + this.file_separator + file_name + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	private void generate_android_mobile() throws Exception{
		String dependent_services_for_android = get_generation_service().getController_worker().getDependent_services_for_android_phone();
		Path ios_controller_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_controller_directory_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("the_code", the_gen_support.get_controller_code());
		st.add("controller_name", file_name);
		st.add("dependent_services", get_generation_service().getController_worker().getGen_support().get_dependent_services(dependent_services_for_android));
		st.add("dependent_services_f", get_generation_service().getController_worker().getGen_support().get_dependent_services_for_function(dependent_services_for_android));
		String the_path_string = ios_controller_path + this.file_separator + file_name + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	public GpAngularLandingPageControllerGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	@Resource(name = "GpAngularLandingPageControllerGenSupport")
	public void setThe_gen_support(GpAngularLandingPageControllerGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	public void setThe_landing_page_worker(GpAngularLandingPageGenWorker the_landing_page_worker) {
		this.the_landing_page_worker = the_landing_page_worker;
	}
	public GpAngularLandingPageGenWorker getThe_landing_page_worker() {
		return the_landing_page_worker;
	}
}
