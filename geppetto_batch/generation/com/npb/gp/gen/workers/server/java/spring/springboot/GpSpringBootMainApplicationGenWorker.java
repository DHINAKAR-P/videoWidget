package com.npb.gp.gen.workers.server.java.spring.springboot;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
/**
 *
 *
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>
 *
 * This worker generates main application class that begins the spring boot application
 *
 */
@Component("GpSpringBootMainApplicationGenWorker")
public class GpSpringBootMainApplicationGenWorker extends GpGenJavaServerSpringBaseWorker {

	private Path main_application_template_group_path;
	private String main_application_template_name;
	private Path controller_path;
	private String main_app_package_name;

	@Override
	/**
	 * Generates DAOS for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

	}

	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
		this.do_generation(gen_manager);

	}

	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
	}

	@SuppressWarnings("unchecked")
	public void do_generation(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_main_application-GpMainApplicationSpringConfGenWorker", "gen_processing");

		this.generate_spring_boot_main_application();

	}

	private void generate_spring_boot_main_application() throws Exception{

		STGroupDir webxmlGroup = new STGroupDir(this.main_application_template_group_path.toString() , '$', '$');

		ST webxmlST = webxmlGroup.getInstanceOf(this.main_application_template_name);

		Path main_app_path = (Path) derived_configs
				.get(GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();

		this.main_app_package_name =  this.derived_configs.get(
	 			GpGenConstants.APP_BASE_PACKAGE).getValue() ;

		String file_name = super.the_project.getName() ;

		webxmlST.add("app_name", super.the_project.getName() );

		webxmlST.add("package_name",this.main_app_package_name);

		String basepackage = super.derived_configs.get(
	 			GpGenConstants.APP_BASE_PACKAGE).getValue();
		List<String> base_package_list = new ArrayList<>();
		List<GpModuleProperties> modules = super.get_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();

		  for(GpModuleProperties module : modules){
		   String package_module = module.getServer_meta_data().getJava().getSpringboot().getBase_package();
		   base_package_list.add("\""+package_module +".*" +"\""+",");
		  }
		base_package_list.add("\""+basepackage+".*"+"\"");
		webxmlST.add("basepackage", base_package_list);

		String file_extension = ".java";

		String the_path_string = main_app_path.toString()
		+ this.file_separator + file_name + file_extension
									+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, webxmlST);

	}
	private void set_up_paths_and_templates(){

		this.set_up_paths_and_templates_spring_boot_main_application();

	}
	private void set_up_paths_and_templates_spring_boot_main_application(){
		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String main_app_file_template_location = this.base_configs
			.get("server_java_spring_boot_main_app_template_location").getValue();

		tokens = this.tokenize_string(
				main_app_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "application.properties" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations main_app_file_template_location_path_config
												= this.derived_configs.get(config_name);
		main_app_file_template_location_path_config = null;

		if(main_app_file_template_location_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;

			for(String tok : tokens){

				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;

			}

			String template_name =  this.base_configs.get(
					"server_java_spring_boot_main_app_template_name").getValue();

			this.main_application_template_name = template_name;

			Path main_app_file_template_path =
					Paths.get(core_template_location_temp);

			main_app_file_template_location_path_config = new GpArchitypeConfigurations();

			main_app_file_template_location_path_config.setName(config_name);

			main_app_file_template_location_path_config.setObject_value(main_app_file_template_path);

			this.derived_configs.put(config_name, main_app_file_template_location_path_config);

		}

		this.main_application_template_group_path =
		   (Path)main_app_file_template_location_path_config
			  										.getObject_value();
		}

}
