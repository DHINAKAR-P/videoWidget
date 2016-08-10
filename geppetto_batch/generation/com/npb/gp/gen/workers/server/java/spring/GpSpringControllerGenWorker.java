package com.npb.gp.gen.workers.server.java.spring;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.util.dto.GpControllerVerbGenInfo;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.spring.support.controller.GpControllerGenSupport;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaException;
import com.npb.gp.gen.domain.java.GpJavaInputParms;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.domain.java.GpJavaMethodName;
import com.npb.gp.gen.domain.java.GpJavaReturnParm;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;

/**
 * 
 * @author Dan Castillo
 * Date Created: 11/26/2014</br>
 * @since .2</p> 
 * 
 * Generates the code for a Java Spring based controller </p> 
 */
@Component("GpSpringControllerGenWorker")
public class GpSpringControllerGenWorker extends GpGenJavaServerSpringBaseWorker {
	private Path template_group_path;
	private Path controller_path;
	private String controller_package_name;

	private GpControllerGenSupport controller_support;
	private GpFlowControl the_flow;
	
	
	
	
	public String getController_package_name() {
		return controller_package_name;
	}
	public void setController_package_name(String controller_package_name) {
		this.controller_package_name = controller_package_name;
	}
	public Path getController_path() {
		return controller_path;
	}
	public void setController_path(Path controller_path) {
		this.controller_path = controller_path;
	}
	public GpControllerGenSupport getController_support() {
		return controller_support;
	}
	@Resource(name="GpControllerGenSupport")
	public void setController_support(GpControllerGenSupport controller_support) {
		this.controller_support = controller_support;
	}

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
		ArrayList<GpVerb> the_verbs = (ArrayList<GpVerb>)this.derived_configs.get(
										GpGenConstants.VERB_DEFINITIONS).getObject_value();
		
		/*
		for(GpVerb the_verb : the_verbs){
			System.out.println("OJOJOJOJOJ - the_verb.getName() is:" + the_verb.getName());
			
		}
		*/
		ArrayList<GpAuthorization> the_auths = (ArrayList<GpAuthorization>)this.derived_configs.get(
				GpGenConstants.AUTHORIZATION_DEFINITIONS).getObject_value();
		/*
		for(GpAuthorization auth : the_auths){
			System.out.println("XXXXXXXXXXXXXXXXXXX - auth.getName() is:" + auth.getName());
		}
		*/
		/*
		for(GpVerb verb : activity.getTheverbs()){
			System.out.println("&&&&&&&&&&&&&& VERB NAME IS: " + verb.getName()
					+ " ACTION ON DATA IS:  " + verb.getAction_on_data());
			for(GpAuthorization auth : verb.getAuthorizations()){
				System.out.println("&&&&&&&&&&&&&& auth NAME IS: " + auth.getName());
			}
		}
		*/
		this.set_up_paths_and_templates();
		this.do_generation(activity);
	
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

	
	private void do_generation(GpActivity activity) throws Exception{
		
		 String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();

		//String domain_core_package_name = this.derived_configs.get("domain_core_package_name").getValue();
		
		ST st = super.read_template_group(this.template_group_path, "output");
	
		st.add("package_name", this.controller_package_name);
		
		ArrayList<String> import_list = this.set_up_imports(activity);
		st.add("the_imports", import_list);
		String class_name = this.capitalize(activity.getName()) + "Controller";
		st.add("class_name", class_name);
		st.add("end_point_mapping", activity.getName());
		
		st.add("the_services", this.set_up_service_references(activity));
		
		st.add("copy_right_range", copy_right_range);
		
		st.add("the_gen_verbs", this.set_up_verbs(activity));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
		
		
		
		
		String the_path_string = controller_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		
		
	}
	
	private ArrayList<GpControllerVerbGenInfo> set_up_verbs(GpActivity activity) throws Exception{
		
		ArrayList<GpControllerVerbGenInfo> the_verbs_method_info =
										this.controller_support
										.prep_controller_verb_generation(
										activity, get_generation_service(),
																this.the_flow);
		
		return the_verbs_method_info;
	}
	
	private ArrayList<GpTypeAndReference> set_up_service_references(GpActivity activity) throws Exception{
		/*
		 * as of 12/23/2014 - this will only return a list containing 1 item
		 * since an activity only has one primary noun and therefore only 
		 * needs to handle on service - but the time may come when there are
		 * more than one service for an activity - Dan Castillo
		 * 
		 * 
		 */
		ArrayList<GpTypeAndReference> service_list = new ArrayList<GpTypeAndReference>();

		String service_data_type = super.get_generation_service()
				.getService_gen_worker().get_data_type_for_activity(activity);

		String service_object_reference_name  = activity.getName() + "_" + "service";

		//String the_reference = service_data_type + " " + service_object_reference_name;
		GpTypeAndReference dto = new GpTypeAndReference();
		dto.type = service_data_type;
		dto.reference = service_object_reference_name;
		
		service_list.add(dto);
		
		return service_list;
	}
	
	private ArrayList<String> set_up_imports(GpActivity activity) throws Exception{
		
		ArrayList<String> import_list = new ArrayList<String>();

		 String domain_core_package_name = this.derived_configs
				 			.get("domain_core_package_name").getValue();

		//set up primary noun import
		 if(activity.getPrimary_noun() != null){
			 domain_core_package_name = domain_core_package_name + 
				 		"." + activity.getPrimary_noun().getName();
		 
		 import_list.add(domain_core_package_name);
		 }
		
		//set up service import
		 String service_package_name = super.get_generation_service()
					.getService_gen_worker().get_package_name_for_activity(activity);		 
		 
		 
		 import_list.add(service_package_name); 
		
		
		return import_list;
		
	}
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
									"gen_base_java_server_controller_directory").getValue();
		
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
			this.controller_path = controller_path;

			this.controller_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			controller_path_config = new GpArchitypeConfigurations();
			
			controller_path_config.setName(config_name);
			controller_path_config.setObject_value(controller_path);
			this.derived_configs.put(config_name, controller_path_config);
				
		}
		
		/*
		 * 
		 * second determine if the location of the template that will be used 
		 * for this code generation have been derived previously if not
		 * derive it
		 * 
		 */

		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		//System.out.println("root_code_template_location is: " + root_code_template_location);
		
		String server_java_spring_controller_template_location = this.base_configs
					.get("server_java_spring_controller_template_location").getValue();
		
		//System.out.println("server_java_spring_controller_template_location is: " + server_java_spring_controller_template_location);
		
		tokens = this.tokenize_string(
				server_java_spring_controller_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_controller_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		//System.out.println("XXXXXXXXXXXXXXXXXXX config_name is: " + config_name);
		
		GpArchitypeConfigurations server_java_spring_controller_template_path_config 
														= this.derived_configs.get(config_name);

		if(server_java_spring_controller_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_controller_template_name").getValue();
			
			Path server_java_spring_controller_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_controller_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_controller_template_path_config.setName(config_name);
			server_java_spring_controller_template_path_config.setObject_value(server_java_spring_controller_template_path);
			this.derived_configs.put(config_name, server_java_spring_controller_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_controller_template_path_config.getObject_value(); 
		this.controller_path = (Path)controller_path_config.getObject_value();

		//
	}

}
