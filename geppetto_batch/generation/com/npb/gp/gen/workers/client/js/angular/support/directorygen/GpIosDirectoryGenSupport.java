package com.npb.gp.gen.workers.client.js.angular.support.directorygen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.dao.mysql.GpIsoLanguagesDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.workers.client.js.angular.GpAngularDirectoryGenWorker;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/10/2015</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate the directories needed to support code</br>
 * generation for AngularJS for IOS</p>
 */
@Component("GpIosDirectoryGenSupport")
public class GpIosDirectoryGenSupport {
	
	private GpAngularDirectoryGenWorker gen_worker;
	String file_separator;
	HashMap<String,GpArchitypeConfigurations> base_configs;
	private GpIsoLanguagesDao languages_dao;
	
	public void set_gen_worker(GpAngularDirectoryGenWorker worker){
		this.gen_worker = worker;
	}
	
	@Resource(name="GpIsoLanguagesDao")
	public void setLanguages_dao(GpIsoLanguagesDao languages_dao) {
		this.languages_dao = languages_dao;
	}
	
	public GpIsoLanguagesDao getLanguages_dao() {
		return languages_dao;
	}
	
	public void build_directories(String file_sep, 
			HashMap<String,GpArchitypeConfigurations> base_configs) throws Exception{

		this.file_separator = file_sep;
		this.base_configs = base_configs;
		
		this.setup_base_os();
		this.setup_phone_directories();
		this.setup_tablet_directories();
	}
	private void setup_tablet_directories() throws Exception{
		String[] tokens;

		//tablet base
		String gen_client_tablet_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_TABLET_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_tablet_directory_name, null);


		Path ios_tablet_root_directory_path = Paths.get(gen_worker
				.getIos_root_directory_path().toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(ios_tablet_root_directory_path);
		gen_worker.setIos_tablet_root_directory_path(ios_tablet_root_directory_path);

		//set up  APP root directory
		String gen_client_app_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_root_directory_name, null);

		Path app_root_directory_path = Paths.get(ios_tablet_root_directory_path.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_root_directory_path);
		gen_worker.setIos_tablet_app_root_directory_path(app_root_directory_path);

		
		this.setup_standard_app_directories(app_root_directory_path, "tablet");
		
		
		//set up LIB root directory
		String gen_client_lib_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_LIB_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_lib_root_directory_name, null);

		Path lib_root_directory_path = Paths.get(ios_tablet_root_directory_path.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(lib_root_directory_path);
		gen_worker.setIos_tablet_app_lib_root_directory_path(lib_root_directory_path);
		
		this.setup_standard_lib_directories(lib_root_directory_path, "tablet");
		
	}

	private void setup_phone_directories() throws Exception{
		String[] tokens;
		
		//set up phone base
		String gen_client_phone_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_PHONE_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_phone_directory_name, null);


		Path ios_phone_root_directory_path 
							= Paths.get(gen_worker
								.getIos_root_directory_path().toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(ios_phone_root_directory_path);
		gen_worker.setIos_phone_root_directory_path(ios_phone_root_directory_path);
		
		//set up phone APP root directory - for phones
		String gen_client_app_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_root_directory_name, null);

		Path app_root_directory_path = Paths.get(ios_phone_root_directory_path.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_root_directory_path);
		gen_worker.setIos_phone_app_root_directory_path(app_root_directory_path);

		
		this.setup_standard_app_directories(app_root_directory_path, "phone");
		
		
		//set up LIB root directory - for phones
		String gen_client_lib_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_LIB_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_lib_root_directory_name, null);

		Path lib_root_directory_path = Paths.get(ios_phone_root_directory_path.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(lib_root_directory_path);
		gen_worker.setIos_phone_app_lib_root_directory_path(lib_root_directory_path);
		this.setup_standard_lib_directories(lib_root_directory_path, "phone");


	}


	private void setup_base_os() throws Exception{
		String[] tokens;
		
		//set up OS base directory
		String gen_client_ios_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_IOS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_ios_root_directory_name, null);

		Path ios_root_directory_path 
			= Paths.get(gen_worker
							.getAngular_root_directory_path().toString()
										+ this.file_separator + tokens[0]);
		
		Files.createDirectory(ios_root_directory_path);
		gen_worker.setIos_root_directory_path(ios_root_directory_path);
		 			
	}

	private void setup_standard_lib_directories(Path base_directory, String device_type) throws Exception{
		
		String[] tokens;
		
		//set up LIB child directory css
		String gen_client_app_lib_css_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_CSS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_css_directory_name, null);

		Path app_lib_css_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_css_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_lib_css_directory_path(app_lib_css_directory_path);	
		}else{
			gen_worker.setIos_tablet_app_lib_css_directory_path(app_lib_css_directory_path);
		}

		//set up LIB child directory fonts
		String gen_client_app_lib_fonts_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_FONTS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_fonts_directory_name, null);

		Path app_lib_fonts_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_fonts_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_lib_fonts_directory_path(app_lib_fonts_directory_path);
		}else{
			gen_worker.setIos_tablet_app_lib_fonts_directory_path(app_lib_fonts_directory_path);
		}

		//set up LIB child directory js
		String gen_client_app_lib_js_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_JS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_js_directory_name, null);

		Path app_lib_js_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_js_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_lib_js_directory_path(app_lib_js_directory_path);
		}else{
			gen_worker.setIos_tablet_app_lib_js_directory_path(app_lib_js_directory_path);
		}

	}
	
	private void setup_standard_app_directories(Path base_directory, String device_type) throws Exception{
		String[] tokens;

		//set up APP child directory: css
		String gen_client_app_css_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_CSS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_css_directory_name, null);

		Path app_css_directory_path = Paths.get(base_directory.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_css_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_css_directory_path(app_css_directory_path);	
		}else{
			gen_worker.setIos_tablet_app_css_directory_path(app_css_directory_path);
		}
		//set up APP child directory: fonts
		String gen_client_app_fonts_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_FONTS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_fonts_directory_name, null);
		
		Path app_fonts_directory_path = Paths.get(base_directory.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_fonts_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_fonts_directory_path(app_fonts_directory_path);	
		}else{
			gen_worker.setIos_tablet_app_fonts_directory_path(app_fonts_directory_path);
			
		}
		//set up APP child directory: views
		String gen_client_app_views_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_VIEWS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_views_directory_name, null);
													
		Path app_views_directory_path = Paths.get(base_directory.toString() + this.file_separator + tokens[0]);
		Files.createDirectory(app_views_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_views_directory_path(app_views_directory_path);
		}else{
			gen_worker.setIos_tablet_app_views_directory_path(app_views_directory_path);
		}
		
		this.setup_language_folder_structure(app_views_directory_path);

		//set up APP child directory: js
		String gen_client_app_root_js_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_JS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_root_js_directory_name, null);

		Path app_js_root_directory_path = Paths.get(base_directory.toString()
												+ this.file_separator + tokens[0]);
		Files.createDirectory(app_js_root_directory_path);
		if(device_type.equals("phone")){
			gen_worker.setIos_phone_app_root_js_directory_path(app_js_root_directory_path);	
		}else{
			gen_worker.setIos_tablet_app_root_js_directory_path(app_js_root_directory_path);
		}
		//set up app js child directory - controllers
		String gen_client_app_controller_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_CONTROLLER_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_controller_directory_name, null);
		
		Path app_controller_directory_path = null;
		if(device_type.equals("phone")){
			app_controller_directory_path = Paths.get(
					gen_worker.getIos_phone_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_controller_directory_path);
			gen_worker.setIos_phone_app_controller_directory_path(app_controller_directory_path);
		}else{
			app_controller_directory_path = Paths.get(
					gen_worker.getIos_tablet_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_controller_directory_path);
			gen_worker.setIos_tablet_app_controller_directory_path(app_controller_directory_path);
		}
		//set up app js child directory - directives
		String gen_client_app_directives_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_DIRECTIVES_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_directives_directory_name, null);

		Path app_directives_directory_path = null;
		if(device_type.equals("phone")){
			app_directives_directory_path = Paths.get(
					gen_worker.getIos_phone_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_directives_directory_path);
			gen_worker.setIos_phone_app_directives_directory_path(app_directives_directory_path);
		}else{
			app_directives_directory_path = Paths.get(
					gen_worker.getIos_tablet_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_directives_directory_path);
			gen_worker.setIos_tablet_app_directives_directory_path(app_directives_directory_path);
		}
		
		//set up app js child directory - services
		String gen_client_app_services_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_SERVICES_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_services_directory_name, null);

		Path app_services_directory_path = null;
		if(device_type.equals("phone")){
			app_services_directory_path = Paths.get(
					gen_worker.getIos_phone_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_services_directory_path);
			gen_worker.setIos_phone_app_services_directory_path(app_services_directory_path);
		}else{
			app_services_directory_path = Paths.get(
					gen_worker.getIos_tablet_app_root_js_directory_path()
									.toString() + this.file_separator + tokens[0]);
			Files.createDirectory(app_services_directory_path);
			gen_worker.setIos_tablet_app_services_directory_path(app_services_directory_path);
		}
	}
	
	private void setup_language_folder_structure(Path base_directory) throws Exception{
		long main_human_language = gen_worker.the_project.getDefault_human_language();
		List<String> secondary_languages = gen_worker.the_project.getOther_human_languages();
		Path app_main_language_directory_path = Paths.get(base_directory.toString() + this.file_separator + languages_dao.find_by_id(main_human_language).getPart_1());
		Files.createDirectory(app_main_language_directory_path);
		for(String secondary_language : secondary_languages){
			Path app_secondary_language_directory_path = Paths.get(base_directory.toString() + this.file_separator + languages_dao.find_by_id(Long.valueOf(secondary_language)).getPart_1());
			Files.createDirectory(app_secondary_language_directory_path);
		}
	}

}
