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
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>
 *
 * Generates the spring boot config file</br>
 * <b>Please note: this class depends on the GpResourceBundleWorker</br>
 * to execute first as that class creates the GEPPETTO PATH/Directory</br>
 * the reason for the dependency is that at this time - 01/28/2015</br
 * I amd the choice to not allow code generation unless you a project contains</br>
 * at least one activity, one noun, and one verb must added to a project</br>
 * before code can be generated. Once a project has a noun then</br>
 * the code generator will generate a resource bundle and therefore the associated</br>
 * GEPPETTO directory which this class will depend on - Dan Castillo - 01/28/2015</p>

 *
 *
 */

@Component("GpSpringBootConfGenWorker")
public class GpSpringBootConfGenWorker extends GpGenJavaServerSpringBaseWorker {

	private Path main_application_template_group_path;
	private String main_application_template_name;

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


	public void do_generation(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_config-GpSpringBootConfGenWorker", "gen_processing");

		this.generate_application_properties(gen_manager);
	}


	private void generate_application_properties(IGpGenManager gen_manager) throws Exception{

		String file_name_location = this.main_application_template_group_path.toString()
				+ this.file_separator + this.main_application_template_name;

		STGroupDir webxmlGroup = new STGroupDir(this.main_application_template_group_path.toString() , '$', '$');

		ST webxmlST = webxmlGroup.getInstanceOf(main_application_template_name);

		Path spring_boot_web_path = (Path) derived_configs
						.get(GpGenConstants.SPRING_BOOT_RESOURCE_PATH).getObject_value();

		String file_name = "application";
		webxmlST.add("app_name", super.the_project.getName() + "_" + gen_manager.get_user().getId());
		webxmlST.add("driverClassName", "com.mysql.jdbc.Driver");
		webxmlST.add("url", "jdbc:mysql://mydb:3306/"
							+super.the_project.getName().toLowerCase()
							+ "?useUnicode=true&characterEncoding=UTF-8");
		webxmlST.add("username", "root");
		webxmlST.add("password", "tang3456");

		String basepackage = super.derived_configs.get(
	 			GpGenConstants.APP_BASE_PACKAGE).getValue();

		List<String> base_package_list = new ArrayList<>();

		 // List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker()
		//		  .getModule_properties_list();


		  List<GpModuleProperties> modules = super.get_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();

		  for(GpModuleProperties module : modules){
		   String package_module = module.getServer_meta_data().getJava().getSpringboot().getBase_package();
		   base_package_list.add(package_module + ".*"+",");
		  }
		  base_package_list.add(basepackage+".*");
		  webxmlST.add("basepackage", base_package_list);

		//webxmlST.add("basepackage", basepackage + ".*");

		  String file_extension = ".properties";

		String the_path_string = spring_boot_web_path.toString()
		+ this.file_separator + file_name + file_extension
									+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, webxmlST);


	}


	private void set_up_paths_and_templates(){
		this.set_up_paths_and_templates_application_properties();
	}

	private void set_up_paths_and_templates_application_properties(){
		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String main_app_file_template_location = this.base_configs
			.get("server_java_spring_boot_app_properties_template_location").getValue();

		tokens = this.tokenize_string(
				main_app_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "main_application" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations main_app_file_template_location_path_config
												= this.derived_configs.get(config_name);

		if(main_app_file_template_location_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;

			for(String tok : tokens){

				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;

			}

			String template_name =  this.base_configs.get(
					"server_java_spring_boot_app_properties_template_name").getValue();

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
