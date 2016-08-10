package com.npb.gp.gen.workers.server.java.spring.springboot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;

/**
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>
 * 
 *  <p> This class generates a configuration for spring boot </p>
 *
 */

@Component("GpSpringBootServletConfigGenWorker")
public class GpSpringBootServletConfigGenWorker extends GpGenJavaServerSpringBaseWorker {

	private Path servlet_config_path;
	private Path template_group_path;
	private String config_package_name;
	private GpFlowControl the_flow;
	
	
	@Override
	/**
	 * Generates Controllers for all activities in the project
	 */
	public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
	
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.the_flow = the_flow;
		this.set_up_paths_and_templates();
		//this.generate_core_domain();

	}

	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.the_flow = the_flow;
		@SuppressWarnings("unchecked")
		ArrayList<GpVerb> the_verbs = (ArrayList<GpVerb>)this.derived_configs.get(
										GpGenConstants.VERB_DEFINITIONS).getObject_value();
		
		@SuppressWarnings("unchecked")
		ArrayList<GpAuthorization> the_auths = (ArrayList<GpAuthorization>)this.derived_configs.get(
				GpGenConstants.AUTHORIZATION_DEFINITIONS).getObject_value();
		
		this.set_up_paths_and_templates();
		this.do_generation(gen_manager);
	
	}
	
	public void do_generation(IGpGenManager genmanager) throws Exception{
		
		 		
		ST st = super.read_template_group(this.template_group_path, "output");
		
		st.add("package_name", this.config_package_name);
		
		String class_name = this.capitalize(super.the_project.getName()) + "Config";
	    
		st.add("class_name", class_name);
	
	    GpArchitypeConfigurations activities_prop = super.derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);
		
		ArrayList<GpActivity> the_activities = 
				(ArrayList<GpActivity>) activities_prop.getObject_value();
		
		st.add("bundleInfo", this.set_up_resource_bundle_info(the_activities));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
				
		String the_path_string = servlet_config_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		
		Path test_write_path = Paths.get(the_path_string);
		
		super.write_file(test_write_path, st);
				
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

	
	@SuppressWarnings("unused")
	private void set_up_paths_and_templates() throws Exception {
		/*
		 * first see if the location where the code will be generated to
		 * has already been derived - if not create a new config for it 
		 */
		String config_name = "";
		String[] tokens;
		Path server_root_path = (Path)this.derived_configs.get(
					GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();
		
		String gen_base_domain_directory_java_server =  this.base_configs.get(
									"gen_base_java_server_config_directory").getValue();
		
		tokens = this.tokenize_string(
								gen_base_domain_directory_java_server, null);
		config_name = this.build_name_from_tokens(tokens);
		
		
		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		
		GpArchitypeConfigurations controller_path_config = this.derived_configs.get(config_name);
		
		if(controller_path_config == null){
			
			//create the directories and store the path
			Path controller_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			
			Files.createDirectory(controller_path);
			
			this.servlet_config_path = controller_path;

			this.config_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			controller_path_config = new GpArchitypeConfigurations();
			
			controller_path_config.setName(config_name);
			controller_path_config.setObject_value(controller_path);
			this.derived_configs.put(config_name, controller_path_config);
				
		}
		
		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		String server_java_spring_boot_controller_template_location = this.base_configs
					.get("server_java_spring_boot_configs_template_location").getValue();
		
		tokens = this.tokenize_string(
				server_java_spring_boot_controller_template_location, null);
		
		
		config_name = this.build_name_from_tokens(tokens) + "_config_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_controller_template_path_config 
														= this.derived_configs.get(config_name);
		
		if(server_java_spring_controller_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_boot_configs_template_name").getValue();
		
			Path server_java_spring_controller_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_controller_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_controller_template_path_config.setName(config_name);
			server_java_spring_controller_template_path_config.setObject_value(server_java_spring_controller_template_path);
			this.derived_configs.put(config_name, server_java_spring_controller_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_controller_template_path_config.getObject_value(); 
		this.servlet_config_path = (Path)controller_path_config.getObject_value();
			}
	
	private String set_up_resource_bundle_info(ArrayList<GpActivity> act_list){
		String bundle_name = "";
		
		String the_bundle_info = " ";
		String first_part = "new ClassPathResource( "+"\"sql_queries/";
		String last_part = "\"),";
		
		String first_config_part = "new ClassPathResource( "+"\"sql_queries/";
		String last_config_part = "\"),";
		
		for(GpActivity activity : act_list){
			if(activity.HasSQLgeneration()){
				bundle_name = super.get_spring_boot_gen_service().getSpring_boot_resource_bundle_worker().get_sql_bundle_name(activity);	
				the_bundle_info = the_bundle_info + first_part + bundle_name  + last_part;
				
			}
		}
		
		List<GpModuleProperties> list = get_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();
		System.out.println("list :" + list.size());
		for(GpModuleProperties propertie : list){
			String [] sql_queries = propertie.getServer_meta_data().getJava().getSpringboot().getSql_queries();
			String [] config_properties = propertie.getServer_meta_data().getJava().getSpringboot().getConfig_properties();
			if(sql_queries != null){
				for(String sql_querie : sql_queries){
					the_bundle_info = the_bundle_info + first_part + sql_querie + last_part;
				}
			}
			if(config_properties != null){
				for (String config_property : config_properties) {
					the_bundle_info = the_bundle_info + first_config_part + config_property + last_config_part ;
				}
			}
		}
		
		
		return the_bundle_info;
	}
	
}
