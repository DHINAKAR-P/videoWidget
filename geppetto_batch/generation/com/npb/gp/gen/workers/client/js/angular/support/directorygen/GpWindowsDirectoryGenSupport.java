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
 * Date Created: 03/11/2015</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate the directories needed to support code</br>
 * generation for AngularJS for Windows/Desktop</br>
 * *** Please note that I use Desktop and Windows interchangeably ****</p>
 */
@Component("GpWindowsDirectoryGenSupport")
public class GpWindowsDirectoryGenSupport {
	
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
	}
	/* windows and desktop are synonymous - Dan Castillo 03/11/2015 */
	private void setup_base_os() throws Exception{
		String[] tokens;
		
		//set up OS base directory
		String gen_client_windows_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_DESKTOP_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_windows_root_directory_name, null);

		Path windows_root_directory_path 
			= Paths.get(gen_worker
							.getAngular_root_directory_path().toString()
										+ this.file_separator + tokens[0]);
		
		Files.createDirectory(windows_root_directory_path);
		gen_worker.setWindows_root_directory_path(windows_root_directory_path);
		
		//set up APP root directory
		String gen_client_app_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_root_directory_name, null);

		Path app_root_directory_path = Paths.get(windows_root_directory_path.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_root_directory_path);
		gen_worker.setWindows_app_root_directory_path(app_root_directory_path);

		
		this.setup_standard_app_directories(app_root_directory_path);

		//set up LIB root directory
		String gen_client_lib_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_LIB_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_lib_root_directory_name, null);

		Path lib_root_directory_path = Paths.get(windows_root_directory_path.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(lib_root_directory_path);
		gen_worker.setWindows_app_lib_root_directory_path(lib_root_directory_path);
		
		this.setup_standard_lib_directories(lib_root_directory_path);
	}

	private void setup_standard_lib_directories(Path base_directory) throws Exception{
		
		String[] tokens;
		
		//set up LIB child directory css
		String gen_client_app_lib_css_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_CSS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_css_directory_name, null);

		Path app_lib_css_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_css_directory_path);
		gen_worker.setWindows_app_lib_css_directory_path(app_lib_css_directory_path);

		//set up LIB child directory fonts
		String gen_client_app_lib_fonts_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_FONTS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_fonts_directory_name, null);

		Path app_lib_fonts_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_fonts_directory_path);
		gen_worker.setWindows_app_lib_fonts_directory_path(app_lib_fonts_directory_path);


		//set up LIB child directory js
		String gen_client_app_lib_js_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_LIB_JS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_lib_js_directory_name, null);

		Path app_lib_js_directory_path = Paths.get(base_directory.toString() + this.file_separator
				+ tokens[0]);
		Files.createDirectory(app_lib_js_directory_path);
		gen_worker.setWindows_app_lib_js_directory_path(app_lib_js_directory_path);


	}
	
	private void setup_standard_app_directories(Path base_directory) throws Exception{
		String[] tokens;

		//set up APP child directory: css
		String gen_client_app_css_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_CSS_DIRECTORY_NAME).getValue();
		
		tokens = gen_worker.tokenize_string(
				gen_client_app_css_directory_name, null);

		Path app_css_directory_path = Paths.get(base_directory.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_css_directory_path);
		gen_worker.setWindows_app_css_directory_path(app_css_directory_path);
		
		
		//set up APP child directory: fonts
		String gen_client_app_fonts_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_APP_FONTS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_fonts_directory_name, null);
		
		Path app_fonts_directory_path = Paths.get(base_directory.toString()
											+ this.file_separator + tokens[0]);
		Files.createDirectory(app_fonts_directory_path);
		gen_worker.setWindows_app_fonts_directory_path(app_fonts_directory_path);
		
		//set up APP child directory: views
		String gen_client_app_views_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_VIEWS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_views_directory_name, null);
													
		Path app_views_directory_path = Paths.get(base_directory.toString() + this.file_separator + tokens[0]);
		Files.createDirectory(app_views_directory_path);
		gen_worker.setWindows_app_views_directory_path(app_views_directory_path);

		this.setup_language_folder_structure(app_views_directory_path);
		
		//set up APP child directory: js
		String gen_client_app_root_js_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_ROOT_JS_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_root_js_directory_name, null);

		Path app_js_root_directory_path = Paths.get(base_directory.toString()
												+ this.file_separator + tokens[0]);
		Files.createDirectory(app_js_root_directory_path);
		gen_worker.setWindows_app_root_js_directory_path(app_js_root_directory_path);

		
		
		//set up app js child directory - controllers
		String gen_client_app_controller_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_CONTROLLER_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_controller_directory_name, null);
		
		Path app_controller_directory_path = Paths.get(
				gen_worker.getWindows_app_root_js_directory_path()
								.toString() + this.file_separator + tokens[0]);
		Files.createDirectory(app_controller_directory_path);
		gen_worker.setWindows_app_controller_directory_path(app_controller_directory_path);
		
		//set up app js child directory - directives
		String gen_client_app_directives_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_DIRECTIVES_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_directives_directory_name, null);

		Path app_directives_directory_path = Paths.get(
				gen_worker.getWindows_app_root_js_directory_path()
								.toString() + this.file_separator + tokens[0]);
		Files.createDirectory(app_directives_directory_path);
		gen_worker.setWindows_app_directives_directory_path(app_directives_directory_path);

		
		//set up app js child directory - services
		String gen_client_app_services_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_APP_SERVICES_DIRECTORY_NAME).getValue();

		tokens = gen_worker.tokenize_string(
				gen_client_app_services_directory_name, null);

		Path app_services_directory_path = Paths.get(
				gen_worker.getWindows_app_root_js_directory_path()
								.toString() + this.file_separator + tokens[0]);
		Files.createDirectory(app_services_directory_path);

		gen_worker.setWindows_app_services_directory_path(app_services_directory_path);
					
		
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
