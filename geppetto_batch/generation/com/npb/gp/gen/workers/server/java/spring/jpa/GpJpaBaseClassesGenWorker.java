package com.npb.gp.gen.workers.server.java.spring.jpa;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.GpBatchGen;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.java.GpJavaServerDomainGenWorker;
import com.npb.gp.gen.workers.java.jpa.GpJavaServerDomainGenWorkerForJpa;
import com.npb.gp.gen.workers.server.java.spring.GpSpringControllerGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringServiceGenWorker;

/**
 *
 * @author Dan Castillo
 * Date Created: 01/26/2015</br>
 * @since .2</p>
 *
 * Generates the code for several classes that are use as base classes</br>
 * that the generated code extends the classes are:</br>
 * <li>GpUser</li>
 * <li>GpBaseAuthority</li>
 * <li>GpAuthority</li>
 * <li>GpBaseController</li>
 * <li>GpService</li>
 *
 *
 *
 */
@Component("GpJpaBaseClassesGenWorker")
public class GpJpaBaseClassesGenWorker extends
							GpGenJavaServerSpringBaseWorker {


	private Path user_template_group_path;
	private Path base_authority_template_group_path;
	private Path authority_template_group_path;
	private Path base_controller_template_group_path;
	private Path base_service_template_group_path;

	private Path user_class_path;
	private Path base_authority_class_path;
	private Path authority_class_path;
	private Path base_controller_class_path;
	private Path base_service_class_path;


	private GpSpringControllerGenWorker controller_gen_worker;
	private GpSpringServiceGenWorker service_gen_worker;
	//private GpJavaServerDomainGenWorker domain_gen_worker;
	private GpJavaServerDomainGenWorkerForJpa domain_worker_for_jpa;


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


	System.out.println("In GpLegacyBaseClassesGenWorker - generate_code_by_activity");

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

		//gen_manager.update_job_status(project_id, user_id, username, "gen_controller_classes-GpLegacyBaseClassesGenWorker", "gen_processing");
		this.generate_base_controler();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_user_classes-GpLegacyBaseClassesGenWorker", "gen_processing");
		this.generate_user_classes();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_service_classes-GpLegacyBaseClassesGenWorker", "gen_processing");		
		this.generate_base_service();
	}

	private void generate_base_controler() throws Exception{

		this.controller_gen_worker = super.get_generation_service().getController_gen_worker();
		Path controller_path = this.controller_gen_worker.getController_path();

		ST st_controller = super.read_template_group(this.base_controller_template_group_path, "output");


		st_controller.add("package_name", this.controller_gen_worker.getController_package_name());

		String class_name = "GpBaseController";
		st_controller.add("class_name", class_name);

		//getDomain_gen_worker()
		GpJavaServerDomainGenWorkerForJpa domain_worker_for_jpa = super.
				get_generation_service().getDomain_worker_for_jpa();

	/*	GpJavaServerDomainGenWorker domain_worker = super.
				get_generation_service().getDomain_gen_worker();*/

		ArrayList<String> imports = new ArrayList<String>();
		imports.add(domain_worker_for_jpa.getDomain_core_package_name() + ".GpUser");
		st_controller.add("the_imports", imports);

		String file_extension =  this.base_configs
				.get("java_extension").getValue();


		String the_path_string = controller_path.toString()
		+ this.file_separator + class_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st_controller);

	}

	private void generate_base_service() throws Exception{
		this.service_gen_worker = super.get_generation_service().getService_gen_worker();
		Path service_path =  this.service_gen_worker.getService_path();

		ST st = super.read_template_group(this.base_service_template_group_path, "output");
		st.add("package_name", this.service_gen_worker.getService_package_name());

		String class_name = "GpBaseService";
		st.add("class_name", class_name);

		String file_extension =  this.base_configs
				.get("java_extension").getValue();


		String the_path_string = service_path.toString()
		+ this.file_separator + class_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);



	}

	private void generate_user_classes() throws Exception{

		Path core_domain_path = null;

		this.domain_worker_for_jpa = super.get_generation_service().getDomain_worker_for_jpa();
		core_domain_path = this.domain_worker_for_jpa.getCore_domain_path();


		//first gen the user class
		ST st_user = super.read_template_group(this.user_template_group_path, "output");

		st_user.add("package_name", this.domain_worker_for_jpa.getDomain_core_package_name());

		String class_name = "GpUser";
		st_user.add("class_name", class_name);

		String file_extension =  this.base_configs
				.get("java_extension").getValue();

		String the_path_string = core_domain_path.toString()
		+ this.file_separator + class_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st_user);


		//second authority   class
		ST st_auth = super.read_template_group(this.authority_template_group_path, "output");

		st_auth.add("package_name", this.domain_worker_for_jpa.getDomain_core_package_name());

		class_name = "GpAuthority";
		st_auth.add("class_name", class_name);


		String auth_path_string = core_domain_path.toString()
		+ this.file_separator + class_name + file_extension
									+  this.file_separator;
		Path auth_write_path = Paths.get(auth_path_string);
		super.write_file(auth_write_path, st_auth);


		//third gen base authority  class
		ST st_base_auth = super.read_template_group(this.base_authority_template_group_path, "output");

		st_base_auth.add("package_name", this.domain_worker_for_jpa.getDomain_core_package_name());

		class_name = "GpBaseAuthority";
		st_base_auth.add("class_name", class_name);


		String base_auth_path_string = core_domain_path.toString()
		+ this.file_separator + class_name + file_extension
									+  this.file_separator;
		Path base_auth_write_path = Paths.get(base_auth_path_string);
		super.write_file(base_auth_write_path, st_base_auth);


	}


	private void set_up_paths_and_templates(){
		this.handle_path_template_for_controler();
		this.handle_path_template_for_user();
		this.handle_path_template_for_authority();
		this.handle_path_template_for_base_authority();
		this.handle_path_template_for_service();
	}

	private void handle_path_template_for_service(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String server_java_spring_legacy_base_service_template_location = this.base_configs
			.get("server_java_spring_legacy_base_service_template_location").getValue();


		tokens = this.tokenize_string(
				server_java_spring_legacy_base_service_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_gp_base_service_class" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations server_java_spring_legacy_base_service_template_path_config
												= this.derived_configs.get(config_name);

		if(server_java_spring_legacy_base_service_template_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"server_java_spring_legacy_base_service_template_name").getValue();

			Path server_java_spring_legacy_base_service_template_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);
			server_java_spring_legacy_base_service_template_path_config = new GpArchitypeConfigurations();

			server_java_spring_legacy_base_service_template_path_config.setName(config_name);
			server_java_spring_legacy_base_service_template_path_config.setObject_value(server_java_spring_legacy_base_service_template_path);
			this.derived_configs.put(config_name, server_java_spring_legacy_base_service_template_path_config);

		}

		this.base_service_template_group_path =
		   (Path)server_java_spring_legacy_base_service_template_path_config
			  												.getObject_value();

	}

	private void handle_path_template_for_base_authority(){
		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String server_java_spring_jpa_base_user_authority_template_location = this.base_configs
			.get("server_java_spring_jpa_base_user_authority_template_location").getValue();

		tokens = this.tokenize_string(
				server_java_spring_jpa_base_user_authority_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_gp_base_authority_class" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations server_java_spring_legacy_base_user_authority_template_path_config
												= this.derived_configs.get(config_name);

		if(server_java_spring_legacy_base_user_authority_template_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"server_java_spring_jpa_base_user_authority_template_name").getValue();

			Path server_java_spring_legacy_base_user_authority_template_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);
			server_java_spring_legacy_base_user_authority_template_path_config = new GpArchitypeConfigurations();

			server_java_spring_legacy_base_user_authority_template_path_config.setName(config_name);
			server_java_spring_legacy_base_user_authority_template_path_config.setObject_value(server_java_spring_legacy_base_user_authority_template_path);
			this.derived_configs.put(config_name, server_java_spring_legacy_base_user_authority_template_path_config);

		}

		this.base_authority_template_group_path =
		   (Path)server_java_spring_legacy_base_user_authority_template_path_config
			  												.getObject_value();

	}

	private void handle_path_template_for_authority(){
		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String spring_jpa_user_authority_template_location = this.base_configs
			.get("spring_jpa_user_authority_template_location").getValue();

		tokens = this.tokenize_string(
				spring_jpa_user_authority_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_gp_authority_class" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations spring_legacy_user_authority_template_path_config
												= this.derived_configs.get(config_name);

		if(spring_legacy_user_authority_template_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"spring_jpa_user_authority_template_name").getValue();

			Path spring_legacy_user_authority_template_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);
			spring_legacy_user_authority_template_path_config = new GpArchitypeConfigurations();

			spring_legacy_user_authority_template_path_config.setName(config_name);
			spring_legacy_user_authority_template_path_config.setObject_value(spring_legacy_user_authority_template_path);
			this.derived_configs.put(config_name, spring_legacy_user_authority_template_path_config);

		}

		this.authority_template_group_path =
		   (Path)spring_legacy_user_authority_template_path_config
			  												.getObject_value();

	}

	private void handle_path_template_for_user(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String server_java_spring_jpa_gp_user_template_location = this.base_configs
			.get("server_java_spring_jpa_gp_user_template_location").getValue();

		tokens = this.tokenize_string(
				server_java_spring_jpa_gp_user_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_gp_user_class" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations server_java_spring_legacy_gp_user_template_path_config
												= this.derived_configs.get(config_name);

		if(server_java_spring_legacy_gp_user_template_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"server_java_spring_jpa_gp_user_template_name").getValue();

			Path server_java_spring_legacy_gp_user_templatee_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);
			server_java_spring_legacy_gp_user_template_path_config = new GpArchitypeConfigurations();

			server_java_spring_legacy_gp_user_template_path_config.setName(config_name);
			server_java_spring_legacy_gp_user_template_path_config.setObject_value(server_java_spring_legacy_gp_user_templatee_path);
			this.derived_configs.put(config_name, server_java_spring_legacy_gp_user_template_path_config);

		}

		this.user_template_group_path =
		   (Path)server_java_spring_legacy_gp_user_template_path_config
			  												.getObject_value();

	}

	private void handle_path_template_for_controler(){


		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String server_java_spring_legacy_base_controller_template_location = this.base_configs
			.get("server_java_spring_legacy_base_controller_template_location").getValue();


		tokens = this.tokenize_string(
				server_java_spring_legacy_base_controller_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_controller_base_class" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations server_java_spring_legacy_base_controller_template_path_config
												= this.derived_configs.get(config_name);

		if(server_java_spring_legacy_base_controller_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"server_java_spring_legacy_base_controller_template_name").getValue();

			Path server_java_spring_legacy_base_controller_template_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);
			server_java_spring_legacy_base_controller_template_path_config = new GpArchitypeConfigurations();

			server_java_spring_legacy_base_controller_template_path_config.setName(config_name);
			server_java_spring_legacy_base_controller_template_path_config.setObject_value(server_java_spring_legacy_base_controller_template_path);
			this.derived_configs.put(config_name, server_java_spring_legacy_base_controller_template_path_config);

		}

		this.base_controller_template_group_path =
		   (Path)server_java_spring_legacy_base_controller_template_path_config
			  												.getObject_value();
	}




}
