package com.npb.gp.gen.workers.client.js.angular.landingpage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.dao.mysql.GpIsoLanguagesDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;

@Component("GpAngularLandingPageHtmlGenWorker")
public class GpAngularLandingPageHtmlGenWorker extends GpGenJSClientAngularBaseWorker{
	private Path template_path;
	private String template_name;
	private Path desktop_template_path;
	private String desktop_template_name;
	private Path nielsen_desktop_template_path;
	private String nielsen_desktop_template_name;
	private String file_name = "landing_page";
	private String file_extension = ".html";
	private GpIsoLanguagesDao languages_dao;
	private String route_url;
	private String route_url_desktop;
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		set_up_paths_and_templates_android();
		this.set_up_paths_and_templates_for_desktop();
		set_up_paths_and_templates_for_nielsen_desktop();
	}
	private void set_up_paths_and_templates_android() {
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_neilsen_android_landing_page_html_template_location").getValue();
		System.out.println("server_nodejs_express_index_models_template_location : " +
				server_nodejs_express_index_models_template_location);
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_landing_page_html_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		template_name =  this.base_configs.get(
				"client_angular_neilsen_android_landing_page_html_template_name").getValue();
		System.out.println(" template name : " + template_name);
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_path =   (Path) package_path_config.getObject_value();
	}
	private void set_up_paths_and_templates_for_nielsen_desktop() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_nielsen_desktop_landing_page_html_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_desktop_landing_page_html_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		nielsen_desktop_template_name =  this.base_configs.get(
				"client_angular_nielsen_desktop_landing_page_html_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.nielsen_desktop_template_path =   (Path) package_path_config.getObject_value();
	}
	private void set_up_paths_and_templates_for_desktop() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_desktop_landing_page_html_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_desktop_landing_page_html_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		desktop_template_name =  this.base_configs.get(
				"client_angular_desktop_landing_page_html_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.desktop_template_path =   (Path) package_path_config.getObject_value();
	}

	private void set_up_paths_and_templates() {
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("client_angular_landing_page_html_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_landing_page_html_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		template_name =  this.base_configs.get(
				"client_angular_landing_page_html_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_path =   (Path) package_path_config.getObject_value();
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		this.generate_mobile_landing_pages();
		if(get_generation_service().getDirectory_gen_worker().desktop_created){
			System.out.println("At Landing page template name is :  " + get_generation_service().getTemplateName());
			if (get_generation_service().getTemplateName().equals("Geppetto PC")) {
				System.out.println("Geppetto PC landing page generated !! "+ get_generation_service().getTemplateName());
				this.generate_desktop();
			} else if (get_generation_service().getTemplateName().equals("Nielsen PC")) {
				System.out.println("Nielsen PC landing page generated !! "+ get_generation_service().getTemplateName());
				this.generate_nielsen_desktop();
			} else if(get_generation_service().getTemplateName().equals("IBM PC")){
				System.out.println("IBM landing page generated !!" + get_generation_service().getTemplateName());
				this.generate_nielsen_desktop();
			}else{
				this.generate_desktop();
			}
		
		
		}
			
	}
	private void generate_nielsen_desktop() throws Exception{
		GpAngularMenuGenDto landing_page_dto = new GpAngularMenuGenDto();
		landing_page_dto.activity = "system";
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		landing_page_dto.viewLocation = primary_language_suffix + "/" + file_name + "-" + primary_language_suffix + file_extension;
		landing_page_dto.viewName = file_name + "-" + primary_language_suffix;
		landing_page_dto.viewUrl = file_name + "-" + primary_language_suffix;
		
		get_generation_service().getHtml_worker().getThe_dto_desktop().add(landing_page_dto);
		GpAngularMenuGenDto application_level_controller_dto = new GpAngularMenuGenDto();
		application_level_controller_dto.activity = "system";
		application_level_controller_dto.controller = "controller: '"+ GpAngularLandingPageControllerGenWorker.file_name +"', cache: false";
		application_level_controller_dto.viewLocation = "";
		application_level_controller_dto.viewName = "app_level";
		application_level_controller_dto.viewUrl = "app_level";
		application_level_controller_dto.no_menu = true;
		application_level_controller_dto.no_template = true;
		get_generation_service().getHtml_worker().getThe_dto_desktop().add(application_level_controller_dto);
		
		Path desktop_views_path = get_generation_service().getDirectory_gen_worker().getWindows_app_views_directory_path();
		STGroupDir st_Group = new STGroupDir(this.nielsen_desktop_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.nielsen_desktop_template_name);
		String the_path_string = desktop_views_path.toString() + this.file_separator + primary_language_suffix + this.file_separator + file_name + "-" + primary_language_suffix + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		get_generation_service().getAppjs_worker().setDefault_route_desktop("app_level");
		route_url_desktop = file_name + "-" + primary_language_suffix;
	}
	
	private void generate_mobile_landing_pages() throws Exception{
		if(get_generation_service().getDirectory_gen_worker().android_created){
			if(get_generation_service().getMobileTemplateName().equals("Nielsen Mobile")){
				this.generate_nielsen_template_for_android_mobile();
				System.out.println("Mobile Nielsen#####");
			}else if(get_generation_service().getMobileTemplateName().equals("Geppetto Mobile")){
				this.generate_android_mobile();
			}else if(get_generation_service().getMobileTemplateName().equals("IBM Mobile")){
				this.generate_nielsen_template_for_android_mobile();
				System.out.println("Mobile IBM#####");
			}	
		}
			
		if(get_generation_service().getDirectory_gen_worker().ios_created)
			this.generate_ios_mobile();
	}
	
	private void generate_nielsen_template_for_android_mobile() throws Exception{
		GpAngularMenuGenDto menu_gen_dto = new GpAngularMenuGenDto();
		menu_gen_dto.activity = "system";
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		menu_gen_dto.viewLocation = primary_language_suffix + "/" + file_name + "-" + primary_language_suffix + file_extension;
		menu_gen_dto.viewName = file_name + "-" + primary_language_suffix;
		menu_gen_dto.viewUrl = file_name + "-" + primary_language_suffix;
		get_generation_service().getHtml_worker().getThe_dto_android_phone().add(menu_gen_dto);
		GpAngularMenuGenDto application_level_controller_dto = new GpAngularMenuGenDto();
		application_level_controller_dto.activity = "system";
		application_level_controller_dto.controller = ",\ncontroller: '"+ GpAngularLandingPageControllerGenWorker.file_name +"', cache: false";
		application_level_controller_dto.viewLocation = "";
		application_level_controller_dto.viewName = "app_level";
		application_level_controller_dto.viewUrl = "app_level";
		application_level_controller_dto.no_menu = true;
		application_level_controller_dto.no_template = true;
		get_generation_service().getAppjs_worker().setDefault_route_mobile("app_level");
		get_generation_service().getHtml_worker().getThe_dto_android_phone().add(application_level_controller_dto);
		Path android_mobile_views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path();
		STGroupDir st_Group = new STGroupDir(this.template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.template_name);
					
	    HashMap<String, String> project_templates = the_project.getProject_templates();
		if (project_templates != null
				&& project_templates.get("SITE_NAME") != null
				&& project_templates.get("SITE_NAME").trim().length() != 0) {
			st.add("project_name", project_templates.get("SITE_NAME"));
		} else {
			st.add("project_name", the_project.getName());
		}		
		
		System.out.println("Mobile landing page template name : " + this.template_name);
		System.out.println("Mobile landing path :" + this.template_path);
		String the_path_string = android_mobile_views_path.toString() + this.file_separator + primary_language_suffix + this.file_separator + file_name + "-" + primary_language_suffix + file_extension;
		route_url = "/app/" + file_name + "-" + primary_language_suffix;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	private void generate_desktop() throws Exception{
		GpAngularMenuGenDto landing_page_dto = new GpAngularMenuGenDto();
		landing_page_dto.activity = "system";
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		landing_page_dto.viewLocation = primary_language_suffix + "/" + file_name + "-" + primary_language_suffix + file_extension;
		landing_page_dto.viewName = file_name + "-" + primary_language_suffix;
		landing_page_dto.viewUrl = file_name + "-" + primary_language_suffix;
		get_generation_service().getHtml_worker().getThe_dto_desktop().add(landing_page_dto);
		GpAngularMenuGenDto application_level_controller_dto = new GpAngularMenuGenDto();
		application_level_controller_dto.activity = "system";
		application_level_controller_dto.controller = "controller: '"+ GpAngularLandingPageControllerGenWorker.file_name +"', cache: false";
		application_level_controller_dto.viewLocation = "";
		application_level_controller_dto.viewName = "app_level";
		application_level_controller_dto.viewUrl = "app_level";
		application_level_controller_dto.no_menu = true;
		application_level_controller_dto.no_template = true;
		get_generation_service().getHtml_worker().getThe_dto_desktop().add(application_level_controller_dto);
		Path desktop_views_path = get_generation_service().getDirectory_gen_worker().getWindows_app_views_directory_path();
		STGroupDir st_Group = new STGroupDir(this.desktop_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.desktop_template_name);
		String the_path_string = desktop_views_path.toString() + this.file_separator + primary_language_suffix + this.file_separator + file_name + "-" + primary_language_suffix + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		get_generation_service().getAppjs_worker().setDefault_route_desktop("app_level");
		route_url_desktop = file_name + "-" + primary_language_suffix;
	}
	
	private void generate_ios_mobile() throws Exception{
		GpAngularMenuGenDto landing_page_dto = new GpAngularMenuGenDto();
		landing_page_dto.activity = "system";
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		landing_page_dto.viewLocation = primary_language_suffix + "/" + file_name + "-" + primary_language_suffix + file_extension;
		landing_page_dto.viewName = file_name + "-" + primary_language_suffix;
		landing_page_dto.viewUrl = file_name + "-" + primary_language_suffix;
		get_generation_service().getHtml_worker().getThe_dto_ios_phone().add(landing_page_dto);
		GpAngularMenuGenDto application_level_controller_dto = new GpAngularMenuGenDto();
		application_level_controller_dto.activity = "system";
		application_level_controller_dto.controller = ",\ncontroller: '"+ GpAngularLandingPageControllerGenWorker.file_name +"', cache: false";
		application_level_controller_dto.viewLocation = "";
		application_level_controller_dto.viewName = "app_level";
		application_level_controller_dto.viewUrl = "app_level";
		application_level_controller_dto.no_menu = true;
		application_level_controller_dto.no_template = true;
		get_generation_service().getHtml_worker().getThe_dto_ios_phone().add(application_level_controller_dto);
		Path ios_mobile_views_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_views_directory_path();
		STGroupDir st_Group = new STGroupDir(this.template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.template_name);
		String the_path_string = ios_mobile_views_path.toString() + this.file_separator + primary_language_suffix + this.file_separator + file_name + "-" + primary_language_suffix + file_extension;
		route_url = "/app/" + file_name + "-" + primary_language_suffix;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	private void generate_android_mobile() throws Exception{
		GpAngularMenuGenDto menu_gen_dto = new GpAngularMenuGenDto();
		menu_gen_dto.activity = "system";
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		menu_gen_dto.viewLocation = primary_language_suffix + "/" + file_name + "-" + primary_language_suffix + file_extension;
		menu_gen_dto.viewName = file_name + "-" + primary_language_suffix;
		menu_gen_dto.viewUrl = file_name + "-" + primary_language_suffix;
		get_generation_service().getHtml_worker().getThe_dto_android_phone().add(menu_gen_dto);
		GpAngularMenuGenDto application_level_controller_dto = new GpAngularMenuGenDto();
		application_level_controller_dto.activity = "system";
		application_level_controller_dto.controller = ",\ncontroller: '"+ GpAngularLandingPageControllerGenWorker.file_name +"', cache: false";
		application_level_controller_dto.viewLocation = "";
		application_level_controller_dto.viewName = "app_level";
		application_level_controller_dto.viewUrl = "app_level";
		application_level_controller_dto.no_menu = true;
		application_level_controller_dto.no_template = true;
		get_generation_service().getAppjs_worker().setDefault_route_mobile("app_level");
		get_generation_service().getHtml_worker().getThe_dto_android_phone().add(application_level_controller_dto);
		Path android_mobile_views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path();
		STGroupDir st_Group = new STGroupDir(this.template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.template_name);
		String the_path_string = android_mobile_views_path.toString() + this.file_separator + primary_language_suffix + this.file_separator + file_name + "-" + primary_language_suffix + file_extension;
		route_url = "/app/" + file_name + "-" + primary_language_suffix;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	@Resource(name = "GpIsoLanguagesDao")
	public void setLanguages_dao(GpIsoLanguagesDao languages_dao) {
		this.languages_dao = languages_dao;
	}
	
	public GpIsoLanguagesDao getLanguages_dao() {
		return languages_dao;
	}
	
	public String getRoute_url() {
		return route_url;
	}
	
	public String getRoute_url_desktop() {
		return route_url_desktop;
	}
}
