package com.npb.gp.gen.workers.server.java.spring;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaException;
import com.npb.gp.gen.domain.java.GpJavaInputParms;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.domain.java.GpJavaMethodName;
import com.npb.gp.gen.domain.java.GpJavaReturnParm;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.spring.support.service.GpServiceGenSupport;

/**
 * 
 * @author Dan Castillo
 * Date Created: 12/22/2014</br>
 * @since .2</p> 
 * 
 * Generates the code for a Java Spring based service </p> 
 */
@Component("GpSpringServiceGenWorker")
public class GpSpringServiceGenWorker extends GpGenJavaServerSpringBaseWorker {
	private Path template_group_path;
	private Path service_path;
	private String service_package_name;
	
	private GpServiceGenSupport service_gen_support;
	private GpFlowControl the_flow;

	public HashMap<String, ArrayList<GpTypeAndReference>> activity_references
						 = new HashMap<String, ArrayList<GpTypeAndReference>>(); 
	//private GpControllerGenSupport controller_support;

	
	
	
	public GpServiceGenSupport getService_gen_support() {
		return service_gen_support;
	}
	public String getService_package_name() {
		return service_package_name;
	}
	public void setService_package_name(String service_package_name) {
		this.service_package_name = service_package_name;
	}
	public Path getService_path() {
		return service_path;
	}
	public void setService_path(Path service_path) {
		this.service_path = service_path;
	}
	@Resource(name="GpServiceGenSupport")
	public void setService_gen_support(GpServiceGenSupport service_gen_support) {
		this.service_gen_support = service_gen_support;
	}
	
	@Override
	/**
	 * Generates Controllers for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{
		
	}

	
	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

		
		//System.out.println("In GpSpringServiceGenWorker - generate_code_by_activity");
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.the_flow = the_flow;
		
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
		 
		this.service_gen_support.setThe_worker(this);
		String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();

		//String domain_core_package_name = this.derived_configs.get("domain_core_package_name").getValue();

		ST st = super.read_template_group(this.template_group_path, "output");
		
		st.add("package_name", this.service_package_name);
		
		ArrayList<String> import_list = this.set_up_imports(activity);
		st.add("the_imports", import_list);
		st.add("copy_right_range", copy_right_range);
		
		String class_name = this.get_data_type_for_activity(activity);
		st.add("class_name", class_name);
		st.add("base_class", "extends GpBaseService");
		
		st.add("the_daos", this.set_up_dao_references(activity));
		
		
		st.add("the_gen_verbs", this.set_up_verbs(activity));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
		
		
		
		
		String the_path_string = service_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

	}
	
	private ArrayList<GpServiceVerbGenInfo> set_up_verbs(GpActivity an_activity){
		
		ArrayList<GpServiceVerbGenInfo> impls = this.service_gen_support.get_verb_method_implmentation(an_activity);
		
		return impls;
		
	}

	
	
	@Override
	public String get_data_type_for_activity(GpActivity an_activity)
															throws Exception{
		String service_data_type = super.capitalize(an_activity.getName()) 
																+ "Service";
		
		return service_data_type;


	}
	
	@Override
	 public String get_package_name_for_activity(GpActivity an_activity) 
			 												throws Exception{
		 
		 String class_name = this.get_data_type_for_activity(an_activity);
		 String package_name = this.service_package_name + "." + class_name;
		 
		 return package_name;
		 
		 
	 }
	
	@Override
	public HashMap<String,  GpJavaMethodDescription> get_method_signitures(GpActivity an_activity) 
			 																	throws Exception{
		return this.service_gen_support.get_method_signitures(an_activity);
	}
	
	
	private void get_method_implementations(GpActivity activity) throws Exception{
		
	}
	
	
	private ArrayList<GpTypeAndReference> set_up_dao_references(GpActivity activity) throws Exception{
		/*
		 * as of 12/30/2014 - this will only return a list containing 1 item
		 * since an activity only has one primary noun and therefore only 
		 * needs to handle one DAO - but the time may come when there are
		 * more than one DAO for an activity - Dan Castillo
		 * 
		 * 
		 */
		ArrayList<GpTypeAndReference> dao_list = new ArrayList<GpTypeAndReference>();

		String dao_data_type = super.get_generation_service()
				.getDao_legacy_worker().get_data_type_for_activity(activity);

		String dao_object_reference_name  = activity.getName() + "_" + "dao";

		GpTypeAndReference dto = new GpTypeAndReference();
		dto.type = dao_data_type;
		dto.reference = dao_object_reference_name;
		
		dao_list.add(dto);
		this.activity_references.put("all_dao_references", dao_list);
		return dao_list;
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
		 
		 String dao_package_name = null;
		 if(GpServerJavaSpringGenService.flag.equals("green")){
			 //set up JPA DAO import
			  dao_package_name = super.get_generation_service()
			 			.getJpa_worker().get_package_name_for_activity(activity);
			 	//System.out.println("service class null import dao_package_name ### : "+dao_package_name);
		
		 }else if(GpServerJavaSpringGenService.flag.equals("red")){
				 //set up DAO import
			  dao_package_name = super.get_generation_service()
						 .getDao_legacy_worker().get_package_name_for_activity(activity);
				 //System.out.println("service class null import dao_package_name :$$ "+dao_package_name);
		 }else{
				// System.out.println("----");
		 }
		
		 import_list.add(dao_package_name); 
		
		 String gep_user_import = this.derived_configs
	 				.get("domain_core_package_name").getValue() + ".GpUser";
		 		 
		 import_list.add(gep_user_import);
		
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
		
		
		//System.out.println("In GpSpringServiceGenWorker - set_up_paths_and_templates "
		//		+ " server_root_path.toString() is: " + server_root_path.toString());
		
		String gen_base_service_directory_java_server =  this.base_configs.get(
									"gen_base_java_server_service_directory").getValue();
		
		tokens = this.tokenize_string(
								gen_base_service_directory_java_server, null);
		
		config_name = this.build_name_from_tokens(tokens);
		//System.out.println("In GpSpringServiceGenWorker - after the tokens config_name is: " + config_name);

		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations service_path_config = this.derived_configs.get(config_name);
		if(service_path_config == null){
			
			//create the directories and store the path
			Path service_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			Files.createDirectory(service_path);
			this.service_path = service_path;
			

			this.service_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			service_path_config = new GpArchitypeConfigurations();
			
			service_path_config.setName(config_name);
			service_path_config.setObject_value(service_path);
			this.derived_configs.put(config_name, service_path_config);
				
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
		//System.out.println("In GpSpringServiceGenWorker - - root_code_template_location is: " + root_code_template_location);
		
		String server_java_spring_service_template_location = this.base_configs
					.get("server_java_spring_service_template_location").getValue();
		
		//System.out.println("In GpSpringServiceGenWorker - - server_java_spring_controller_template_location is: " + server_java_spring_controller_template_location);
		
		tokens = this.tokenize_string(
				server_java_spring_service_template_location, null);

		String template_path_config_name = "";
		config_name = this.build_name_from_tokens(tokens) + "_service_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_service_template_path_config 
														= this.derived_configs.get(config_name);

		
		if(server_java_spring_service_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_service_template_name").getValue();
			
			
			Path server_java_spring_service_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_service_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_service_template_path_config.setName(config_name);
			server_java_spring_service_template_path_config.setObject_value(server_java_spring_service_template_path);
			this.derived_configs.put(config_name, server_java_spring_service_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_service_template_path_config.getObject_value(); 
		this.service_path = (Path)service_path_config.getObject_value();

	}

		

}
