package com.npb.gp.gen.workers.client.js.angular;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import bsh.This;

import com.npb.gp.dao.mysql.TemplateDependenciesDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpTemplateDependencies;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleMobileInfo;
import com.npb.gp.gen.services.client.angular.GpClientAngularJSGenService;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
/**
 * 
 * @author Dan Castillo
 * Date Created: 02/04/2015</br>
 * @since .2</p> 
 * 
 *  worker to handle the generation of an index.html file that uses AngularJS</p>
 *  
 *   
 */
@Component("GpAngularIndexGenWorker")
public class GpAngularIndexGenWorker extends GpGenJSClientAngularBaseWorker {
	
	private Path template_group_path;  //path to the template used to generate index file
	private Path index_gen_target_path; //this is where the genetated index file is suppose to end up
	private List<String> list_css_imports_for_desktop = new ArrayList<String>();
	private String code_to_add_between_head_and_body_DESKTOP;
	private TemplateDependenciesDao templateDependenciesDao;	
	
	public TemplateDependenciesDao getTemplateDependenciesDao() {
		return templateDependenciesDao;
	}
	
	@Resource(name="TemplateDependenciesDao")
	public void setTemplateDependenciesDao(
			TemplateDependenciesDao templateDependenciesDao) {
		this.templateDependenciesDao = templateDependenciesDao;
	}
	
	public void add_code_between_head_and_body_desktop(String code){
		if(code_to_add_between_head_and_body_DESKTOP != null)
			code_to_add_between_head_and_body_DESKTOP += code;
		else 
			code_to_add_between_head_and_body_DESKTOP = code;
	}
	@Override
	public void set_generation_service(GpClientAngularJSGenService gen_service){
		super.set_generation_service(gen_service);
	}
	@Override
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		
		//System.out.println("In GpAngularIndexGenWorker - generate_code");
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
	
	
		this.set_up_paths();
		this.do_generation(gen_manager);
		
	}
	
	public List<String> getList_css_imports_for_desktop() {
		return list_css_imports_for_desktop;
	}


	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

	
	//System.out.println("In GpAngularIndexGenWorker - generate_code_by_activity");
	
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
	
	
		this.set_up_paths();
		this.do_generation(gen_manager);
	
	}
	
	@SuppressWarnings("unchecked")
	public void do_generation(IGpGenManager gen_manager) throws Exception{
		
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		
		GpArchitypeConfigurations tech_property_list_config 
				= super.base_configs.get(GpGenConstants.TECH_PROPERTY_LIST);
		
		 ArrayList<GpTechProperties> tech_property_list =
				 (ArrayList<GpTechProperties>)tech_property_list_config
			 										.getObject_value();
		 for(GpTechProperties prop : tech_property_list){
			if(prop.getType().trim().equalsIgnoreCase(GpGenConstants.GpClientOS)){
				if(prop.getName().trim().equalsIgnoreCase(GpGenConstants.GpClientOS_ANDROID) && super.get_generation_service().getDirectory_gen_worker().android_created){
					//gen_manager.update_job_status(project_id, user_id, username, "gen_android_index-GpAngularIndexGenWorker", "gen_processing");
					this.handle_gen_for_android();
				}else if(prop.getName().trim().equalsIgnoreCase(GpGenConstants.GpClientOS_IOS) && super.get_generation_service().getDirectory_gen_worker().ios_created){
					//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_index-GpAngularIndexGenWorker", "gen_processing");
					this.handle_gen_for_ios();
				}else if(prop.getName().trim().equalsIgnoreCase(GpGenConstants.GpClientOS_WINDOWS) && super.get_generation_service().getDirectory_gen_worker().desktop_created){
					//gen_manager.update_job_status(project_id, user_id, username, "gen_windows_index-GpAngularIndexGenWorker", "gen_processing");
					this.handle_gen_for_windows();
				}
			}
		}
		String file_name_location = this.template_group_path.toString() 
				+ this.file_separator + "angular_index_file_v_1_3_12";
		STGroupDir webxmlGroup = new STGroupDir(this.template_group_path.toString() , '$', '$');
		
		//ST st = webxmlGroup.getInstanceOf(file_name_location);
		ST st = webxmlGroup.getInstanceOf("angular_index_file_v_1_3_12");
		
		st.add("appName", this.the_project.getName().toLowerCase());
		
		String file_extension =  "html";//this.base_configs
				//.get("java_extension").getValue();
		
		
		String the_path_string = index_gen_target_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);


		
	}

	private void handle_gen_for_android() throws Exception{
		
		Path phone_path = super.get_generation_service()
								.getDirectory_gen_worker()
									.getAndroid_phone_root_directory_path();
		
		Path tablet_path = super.get_generation_service()
								.getDirectory_gen_worker()
									.getAndroid_tablet_root_directory_path();
									

		
		String file_name_location = this.template_group_path.toString() 
				+ this.file_separator + "angular_index_file_v_1_3_12";
		
		STGroupDir webxmlGroup = new STGroupDir(this.template_group_path.toString() , '$', '$');
		
		//ST st = webxmlGroup.getInstanceOf(file_name_location);
		ST st = webxmlGroup.getInstanceOf("angular_index_file_v_1_3_12");
		
		st.add("appName", this.the_project.getName());
		st.add("css_imports", get_css_to_import_for_android());
		st.add("js_imports", get_js_to_import_for_android());
		String file_extension =  "html";//this.base_configs
				//.get("java_extension").getValue();
		
		
		String the_path_string = phone_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

		the_path_string = tablet_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
	
		test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

		
	}
	
	private String get_css_to_import_for_android(){
		String css_imports = "";
		css_imports += "\n<link href=\"lib/ionic/css/ionic.css\" rel=\"stylesheet\">";
		List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : modules){
			GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
			if(mobile_info != null){
				String[] css_to_import = mobile_info.getCss_to_import();
				for(int i = 0 ; i < css_to_import.length; i++){
					css_imports += "\n<link href=\""+ css_to_import[i] +"\" rel=\"stylesheet\">";
				}
			}
		}
		return css_imports;
	}
	private String get_js_to_import_for_android(){
		String js_imports = "";
		//ionic js --> TODO: get path from DB?
		js_imports += "\n<script src=\"lib/ionic/js/ionic.bundle.js\"></script>";
		js_imports += "<script src=\"lib/ngCordova/dist/ng-cordova.min.js\"></script>";
		js_imports += "\n<script src=\"cordova.js\"></script>";
		//app.js
		js_imports += "\n<script src=\"app/js/app.js\"></script>";
		//jquery
		js_imports += "\n<script src=\"lib/jquery/jquery-2.1.3.min.js\"></script>";
		//datepicker
		js_imports += "\n<script src=\"lib/ionic-datepicker/dist/ionic-datepicker.bundle.min.js\"></script>";
		
		//Controllers
		for(String controller_name : get_generation_service().getControllers_to_import_android_phone()){
			//System.out.println(controller_name);
			if(!controller_name.equals("null")){
				js_imports += "\n<script src=\"app/js/controllers/"+ controller_name +".js\"></script>";
			}
		}
		List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : modules){
			GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
			if(mobile_info != null){
				String [] libs = mobile_info.getJs_libraries();
				for(int i = 0 ; i < libs.length; i++){
					js_imports += "\n<script src=\"lib/js/"+ libs[i] +"\"></script>";
				}
				String [] other_js_imports = mobile_info.getOther_js_to_import();
				for(int i = 0 ; i < other_js_imports.length; i++){
					js_imports += "\n<script src=\""+ other_js_imports[i] +"\"></script>";
				}
			}
		}
		return js_imports;
	}
	
	private String get_css_to_import_for_desktop(){
		List<String> css_imported = new ArrayList<>();
		String css_imports = "";
		css_imports += "\n<link href=\"lib/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">";
		css_imports += "\n<link href=\"lib/angular-ui-grid/ui-grid.min.css\" rel=\"stylesheet\">";
		css_imports += "\n<link href=\"lib/bootstrap-datepicker/bootstrap-datepicker.css\" rel=\"stylesheet\">";
		for(String css_to_import : list_css_imports_for_desktop){
			if(!css_imported.contains(css_to_import)){
				css_imports += "\n<link href=\"app/css/" + css_to_import + "\" rel=\"stylesheet\">";
				css_imported.add(css_to_import);
			}
			
		}
		
		//Kumaresan Perumal 05/12/2016
		//Getting external CSS dependencies for templates from database 
		List<GpTemplateDependencies> templateDependenciesObjectList = this.templateDependenciesDao.getDependenciesByTechType("css", get_generation_service().getTemplateName());
		if (templateDependenciesObjectList.size() > 0) {
			GpTemplateDependencies temp = templateDependenciesObjectList.get(0);
			String str[] = temp.getDependencies().split(";");
			for (String string : str) {
				if(!css_imported.contains(string)){
					css_imports += "\n" + string;
					css_imported.add(css_imports);
				}
				
			}
		}

		//css_imports += "\n<link href=\"css/app.css\" rel=\"stylesheet\">"; TODO: app.css?
		List<GpModuleProperties> cssLibraries = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties property : cssLibraries){
			String libraries[] = property.getClient_meta_data().getJava_script().getAngular_js().getCss_libraries();
			if(libraries == null || libraries.length==0)
				continue;
			for(String library : libraries){
				String css_import = "<link href=\""+ library +"\" rel=\"stylesheet\">";
				if(!css_imported.contains(css_import)){
					css_imports += "\n" + css_import;
					css_imported.add(css_import);
				}
				
			}
		}
		return css_imports;
	}
	
	private String get_js_to_import_for_desktop(){
		String js_imports = "";
		List<String> libs_added = new ArrayList<>();
		List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties propertie : list){
			String libraries_before_appjs[] = propertie.getClient_meta_data().getJava_script().getAngular_js().getLibraries_before_app_js();
			if(libraries_before_appjs != null){
				for(String librarie : libraries_before_appjs){
					if(!libs_added.contains(librarie)){
						js_imports += "\n<script src=\""+ librarie +"\"></script>";
						libs_added.add(librarie);
					}
				}
			}
		}
		//app.js
		js_imports += "\n<script src=\"lib/jquery/jquery-2.1.3.min.js\"></script>";
		js_imports += "\n<script src=\"lib/angular/angular.min.js\"></script>";
		js_imports += "\n<script src=\"lib/angular/angular-route.min.js\"></script>";
		js_imports += "\n<script src=\"lib/angular-ui-grid/ui-grid.min.js\"></script>";
		js_imports += "\n<script src=\"lib/bootstrap/js/bootstrap.min.js\"></script>";
		js_imports += "\n<script src=\"lib/bootstrap-datepicker/bootstrap-datepicker.js\"></script>";
		js_imports += "\n<script src=\"lib/ckeditor/ckeditor.js\"></script>";
		js_imports += "\n<script src=\"app/js/app.js\"></script>";
		
		//Kumaresan Perumal 05/12/2016
		//Getting external JS dependencies for templates from database
		List<GpTemplateDependencies> templateDependenciesObjectList = this.templateDependenciesDao.getDependenciesByTechType("js", get_generation_service().getTemplateName());
		System.out.println("templateDependenciesObjectList size : " + templateDependenciesObjectList.size());
		if (templateDependenciesObjectList.size() > 0) {
			GpTemplateDependencies temp = templateDependenciesObjectList.get(0);
			String str[] = temp.getDependencies().split(";");
			for (String string : str) {
				if(!libs_added.contains(string)){
					js_imports += "\n" + string;
					libs_added.add(string);
				}
				
			}
		}
		
		//Controllers
		for(String controller_name : get_generation_service().getControllers_to_import_for_desktop()){
			if(!libs_added.contains(controller_name)){
				js_imports += "\n<script src=\"app/js/controllers/"+ controller_name +".js\"></script>";
				libs_added.add(controller_name);
			}
			
		}
		for(GpModuleProperties propertie : list){
			String libraries[] = propertie.getClient_meta_data().getJava_script().getAngular_js().getLibraries();
			for(String librarie : libraries){
				String script = "<script src=\""+ librarie +"\"></script>";
				if(!libs_added.contains(script)){
					js_imports += "\n"+ script;
					libs_added.add(librarie);
				}
				
			}
		}
		return js_imports;
	}
	
	
	private void handle_gen_for_ios() throws Exception{
		
		Path phone_path = super.get_generation_service()
								.getDirectory_gen_worker()
									.getIos_phone_root_directory_path();
		
		Path tablet_path = super.get_generation_service()
								.getDirectory_gen_worker()
									.getIos_tablet_root_directory_path();
									

		
		String file_name_location = this.template_group_path.toString() 
				+ this.file_separator + "angular_index_file_v_1_3_12";
		STGroupDir webxmlGroup = new STGroupDir(this.template_group_path.toString() , '$', '$');
		
		//ST st = webxmlGroup.getInstanceOf(file_name_location);
		ST st = webxmlGroup.getInstanceOf("angular_index_file_v_1_3_12");
		
		st.add("appName", this.the_project.getName());
		//same imports as android for now
		st.add("css_imports", get_css_to_import_for_android());
		st.add("js_imports", get_js_to_import_for_android());
		String file_extension =  "html";//this.base_configs
				//.get("java_extension").getValue();
		
		
		String the_path_string = phone_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

		the_path_string = tablet_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
	
		test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		
	}
	
	private void handle_gen_for_windows() throws Exception{

		
		Path phone_path = super.get_generation_service()
								.getDirectory_gen_worker()
									.getWindows_root_directory_path();
		
		
		String file_name_location = this.template_group_path.toString() 
				+ this.file_separator + "angular_index_desktop_file_v_1_3_12";
		STGroupDir webxmlGroup = new STGroupDir(this.template_group_path.toString() , '$', '$');
		
		//ST st = webxmlGroup.getInstanceOf(file_name_location);
		ST st = webxmlGroup.getInstanceOf("angular_index_desktop_file_v_1_3_12");
		
		st.add("appName", this.the_project.getName());
		st.add("css_imports", get_css_to_import_for_desktop());
		st.add("js_imports", get_js_to_import_for_desktop());
		List<GpModuleProperties> module_list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : module_list){
			add_code_between_head_and_body_desktop(module.getClient_meta_data().getJava_script().getAngular_js().getCode_between_head_and_body());
		}
		st.add("code_between_head_and_body", code_to_add_between_head_and_body_DESKTOP);
		String file_extension =  "html";//this.base_configs
				//.get("java_extension").getValue();
		
		String the_path_string = phone_path.toString() + this.file_separator 
		+ this.file_separator + "index."+ file_extension 
									+  this.file_separator;
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	
	private void set_up_paths() throws Exception{
		
		this.set_up_path_for_gen_templates();
		this.set_paths_for_generated_code();
		
	}

	private void set_paths_for_generated_code() throws Exception{
		
		String config_name = "";
		String[] tokens;
		
		Path server_root_path = (Path)this.derived_configs.get(
					GpGenConstants.GEN_CLIENT_DIRECTORY_NAME_PATH).getObject_value();
		
		
		
		String gen_client_angular_index_file_directory =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = this.tokenize_string(
				gen_client_angular_index_file_directory, null);
		
		config_name = this.build_name_from_tokens(tokens);

		//config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		config_name = GpGenConstants.GEN_CLIENT_ANGULAR_ROOT_DIRECTORY_NAME
								+ GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		GpArchitypeConfigurations index_file_path_config = this.derived_configs.get(config_name);
		if(index_file_path_config == null){
			
			//create the directories and store the path
			Path index_file_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			Files.createDirectory(index_file_path);
			this.index_gen_target_path = index_file_path;
			

			//this.service_package_name =  this.derived_configs.get(
		 	//		GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			index_file_path_config = new GpArchitypeConfigurations();
			
			index_file_path_config.setName(config_name);
			index_file_path_config.setObject_value(index_file_path);
			this.derived_configs.put(config_name, index_file_path_config);
				
		}else{
			this.index_gen_target_path = (Path)index_file_path_config.getObject_value();
		}
		

		
	}
	
	private void set_up_path_for_gen_templates(){

		
		String config_name = "";
		String[] tokens;
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		
		String angular_index_file_template_location = this.base_configs
				.get("angular_index_file_template_location").getValue();
		
		tokens = this.tokenize_string(
								angular_index_file_template_location, null);
		
		config_name = this.build_name_from_tokens(tokens) 
				+ "_angular_index_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		
		GpArchitypeConfigurations angular_index_file_template_location_path_config 
											= this.derived_configs.get(config_name);
		
		if(angular_index_file_template_location_path_config  == null){
			//create and store the path to the angularJS index.html file template
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
				
				String template_name =  this.base_configs.get(
						"angular_index_file_template_name").getValue();
				
				//Path angular_index_file_template_path = 
				//		Paths.get(core_template_location_temp 
				//							+ this.file_separator + template_name);
				Path angular_index_file_template_path = 
						Paths.get(core_template_location_temp); 

				
				angular_index_file_template_location_path_config = new GpArchitypeConfigurations();
				
				angular_index_file_template_location_path_config.setName(config_name);
				angular_index_file_template_location_path_config.setObject_value(angular_index_file_template_path);
				this.derived_configs.put(config_name, angular_index_file_template_location_path_config);

			}
		}
		
		this.template_group_path =  
				(Path)angular_index_file_template_location_path_config
														.getObject_value();

	}
	


}
