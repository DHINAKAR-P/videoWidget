package com.npb.gp.gen.services.server.java.springboot;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GpGenFlowDao;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpActivityGenService;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.GpNotDefaultActivityGenWorker;
import com.npb.gp.gen.workers.java.springboot.GpJavaServerSpringBootDomainGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpJavaLibsGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpJavaSpringBootLibsGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootBaseClassesGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootConfGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootControllerGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootDirectoryGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootMainApplicationGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootResourceBundleGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootServiceGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootServletConfigGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootSwaggerConfigGenWorker;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootSwaggerLibsGenWorker;
import com.npb.gp.gen.workers.server.rdms.mysql.GpMySqlDMLWorker;
import com.npb.gp.gen.workers.server.sql.GpSqlDDLWorker;

/**
 *
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>
 *
 * this class directs the generation of a java server Spring boot app using JPA</p>
 *
 */

@Service("GpServerJavaSpringBootGenService")
public class GpServerJavaSpringBootGenService extends GpBaseGenerationService
									 {

	private GpActivityGenService activity_service;
	private GpGenFlowDao flow_dao;

	private GpSpringBootDirectoryGenWorker spring_boot_directory_gen_worker;
	private GpSpringBootControllerGenWorker spring_boot_controller;
	private GpJavaServerSpringBootDomainGenWorker spring_boot_domain_gen_worker;
	private GpSpringBootServiceGenWorker spring_boot_service_worker;
	private GpSpringBootJpaDaoGenWorker spring_boot_dao_gen_worker;
	private GpSpringBootBaseClassesGenWorker spring_boot_base_gen_worker;
	private GpSpringBootResourceBundleGenWorker spring_boot_resource_bundle_worker;
	private GpSpringBootMainApplicationGenWorker spring_boot_main_application_gen_worker;
	private GpSpringBootServletConfigGenWorker spring_boot_servlet_config_gen_worker;
	private GpSpringBootSwaggerConfigGenWorker spring_boot_swagger_config;
	private GpSqlDDLWorker the_ddl_worker;
	private GpMySqlDMLWorker the_dml_worker;
	private GpSpringBootConfGenWorker spring_boot_config_worker;
	private GpSpringBootSwaggerLibsGenWorker spring_boot_swagger_lib_worker;
	private GpJavaSpringBootLibsGenWorker spring_boot_jar_lib_worker;
	private String file_separator = System.getProperty("file.separator");
	//private GpSpringBootEclipseProjectWorker eclipse_project_worker;
	private GpNotDefaultActivityGenWorker not_default_activity_worker;
	private GpJavaLibsGenWorker libs_worker;
	private Map<Long, GpRelationshipInfo> map_services_relationships;

	public static String flag = "yellow";


	@Override
	public HashMap<String, GpArchitypeConfigurations> get_generation_configurations()
			throws Exception {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void generate(IGpGenManager gen_manager) throws Exception {
		this.check_project_type(gen_manager);
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		HashMap<String, GpArchitypeConfigurations> base_configs = gen_manager.getBase_configs();
		HashMap<String, GpArchitypeConfigurations> derived_configs = gen_manager.getDerived_configs();
		Path p1 = Paths.get(base_configs.get("base_generation_directory").getValue());

		GpProject the_project = gen_manager.get_project();
		GpUser user = gen_manager.get_user();
		String project_folder_handler = the_project.getName() + "_" + user.getId();

		Path p2 = Paths.get(p1.toString() + this.file_separator + project_folder_handler);
		String gen_server_directory_name = base_configs.get("gen_server_directory_name").getValue();
		Path p3 = Paths.get(p2.toString() + this.file_separator + gen_server_directory_name);

		// WE CALL SPRING BOOT DIRECTORY GEN WORKER TO CREATE THE DIRECTORY STRUCTURE
		//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_directory_gen_worker-GpServerJavaSpringBootGenService", "gen_processing");
		spring_boot_directory_gen_worker.handle_java_devlang_server_configs(the_project, p3, base_configs, derived_configs, gen_manager);

		//gen_manager.update_job_status(project_id, user_id, username, "preparing_workers-GpServerJavaSpringBootGenService", "gen_processing");
		this.prep_workers(gen_manager);

		//gen_manager.update_job_status(project_id, user_id, username, "workers_prepared-GpServerJavaSpringBootGenService", "gen_processing");

		GpArchitypeConfigurations activities_prop = gen_manager
				.getDerived_configs().get(GpGenConstants.PROJECT_ACTIVITIES);

		ArrayList<GpActivity> the_activities =
					(ArrayList<GpActivity>) activities_prop.getObject_value();
		map_services_relationships = this.handle_relations(the_activities);
		for(GpActivity an_activity : the_activities){

			if (an_activity.getModule_type() != null && an_activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				Path base_path = Paths.get(gen_manager.getBase_configs().get("base_generation_directory").getValue());
				String project_folder = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
				this.not_default_activity_worker.setModule_final_directory(base_path.toString(),project_folder,"server");
				this.not_default_activity_worker.import_server_module(an_activity, this);
			} else {
				if(an_activity.getPrimary_noun() != null)
					this.process_flow(gen_manager, an_activity);

			}

		}
		this.process_global_components(gen_manager);



	}

	private Map<Long, GpRelationshipInfo> handle_relations(ArrayList<GpActivity> the_activities) throws Exception {
		Map<Long, GpRelationshipInfo> map = new HashMap<>();
		List<GpActivity> the_activities2 = new ArrayList<>();
		the_activities2.addAll(the_activities);
		for(GpActivity activity : the_activities){
			if (activity.getModule_type() != null && activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				continue;
			}
			if(this.getSpring_boot_service_worker().getRelationships_map() != null && activity.getPrimary_noun() != null){
				JSONArray json_array_parent = this.getSpring_boot_service_worker().getRelationships_map().get(activity.getPrimary_noun().getId());
				GpNoun noun = activity.getPrimary_noun();
				if(json_array_parent != null){
					for(int i =0; i<json_array_parent.length();i++){
						JSONObject json_parent = json_array_parent.getJSONObject(i);
						long noun_id = json_parent.getLong("Noun_id");
						for(GpActivity activity2 : the_activities2){
							if (activity2.getModule_type() != null && activity2.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
								continue;
							}
							if(activity2.getPrimary_noun().getId() == noun_id){
								GpRelationshipInfo rel_info = map.get(activity2.getId());
								if(rel_info == null)
									rel_info = new GpRelationshipInfo();
								GpChildRelationshipInfo child = new GpChildRelationshipInfo(); 
								child.setNoun(noun);
								child.setActivity(activity);
								rel_info.add_child(child);
								map.put(activity2.getId(), rel_info);
							}
						}
					}
				}
			}
		}
		return map;
	}

	private void process_global_components(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();


		// flow is not relevant in this case

			//gen_manager.update_job_status(project_id, user_id, username, "mysql_ddl_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.the_ddl_worker.generate_code(gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs(), null, gen_manager,getNot_default_activity_worker().getModule_properties_list());

			//HERE SHOULD COME SPRING BOOT DAO CLASSS
			//gen_manager.update_job_status(project_id, user_id, username, "domain_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
			this.spring_boot_domain_gen_worker.generate_core_domain(gen_manager);

			//HERE SPRING BOOT BASE CLASS WORKER
			//gen_manager.update_job_status(project_id, user_id, username, "JPA_base_classes_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
			this.spring_boot_base_gen_worker.do_generation(gen_manager);

			//HERE SHOULD COME SPRING BOOT CONFIGURATION
			//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_config_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
			this.spring_boot_config_worker.do_generation(gen_manager);

			//HERE SHOULD COME SPRING BOOT MAIN APPLICATION CLASS
			//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_main_application_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
		    this.spring_boot_main_application_gen_worker.do_generation(gen_manager);

		   //HERE SHOULD COME SPRING BOOT CONFIG CLASS GEN WORKER
			//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_servlet_config_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
		    this.spring_boot_servlet_config_gen_worker.do_generation(gen_manager);

		    //gen_manager.update_job_status(project_id, user_id, username, "spring_boot_swagger_config_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
		    this.spring_boot_swagger_config.do_generation(gen_manager);

		  //HERE SHOULD COME SPRING BOOT SWAGGER LIB CLASS GEN WORKER
			//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_swagger_lib_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
		    this.spring_boot_swagger_lib_worker.move_swagger_libs();

		    //HERE SHOULD COME SPRING BOOT JAR LIB CLASS GEN WORKER
			//gen_manager.update_job_status(project_id, user_id, username, "spring_boot_jar_lib_gen_started-GpServerJavaSpringBootGenService", "gen_processing");
		    //this.spring_boot_jar_lib_worker.move_libs();


	}


	private void prep_workers(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		this.not_default_activity_worker.prep_derived_values(
				gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		//SPRING BOOT DOMAIN WORKER CLASS SHOULD COME HERE
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_domain_gen_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_domain_gen_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
		this.spring_boot_domain_gen_worker.set_spring_boot_generation_service(this);

		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_controller_gen_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_controller.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
		this.spring_boot_controller.set_spring_boot_gen_service(this);

		//SERVICES
		//SPRING BOOT
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_service_gen_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_service_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
		this.spring_boot_service_worker.set_spring_boot_gen_service(this);

		/// SPRING BOOT ANNOTATED DAO CLASS SHOULD COME HERE
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_dao_gen_worker-GpServerJavaSpringBootGenService", "gen_processing");

		this.spring_boot_dao_gen_worker.prep_derived_values(gen_manager.get_project(),
					gen_manager.getBase_configs(),
					gen_manager.getDerived_configs());
		this.spring_boot_dao_gen_worker.set_spring_boot_gen_service(this);


		//Resource Bundle
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_resource_bundle_worker-GpServerJavaSpringBootGenService", "gen_processing");

		this.spring_boot_resource_bundle_worker.prep_derived_values(gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
		this.spring_boot_resource_bundle_worker.set_spring_boot_gen_service(this);

		//MySQL - DDL
		//gen_manager.update_job_status(project_id, user_id, username, "loading_mysql_ddl_worker-GpServerJavaSpringGenService", "gen_processing");

		this.the_ddl_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());
		the_dml_worker.prep_derived_values(
				gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		//SPRING BOOT BASE CLASS WORKER
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_base_classes_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_base_gen_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
		this.spring_boot_base_gen_worker.set_spring_boot_gen_service(this);

		//SPRING BOOT CONFIG WORKER HERE
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_config_worker-GpServerJavaSpringGenService", "gen_processing");
		this.spring_boot_config_worker.prep_derived_values(gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());

		this.spring_boot_config_worker.set_spring_boot_gen_service(this);

			//SPRING BOOT MAIN APPLICATION GEN WORKER
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_main_application_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_main_application_gen_worker.prep_derived_values(gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());

		this.spring_boot_main_application_gen_worker.set_spring_boot_gen_service(this);

		//SPRING BOOT CONFIG GEN WORKER
		//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_config_worker-GpServerJavaSpringBootGenService", "gen_processing");
		this.spring_boot_servlet_config_gen_worker.prep_derived_values(gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
	    this.spring_boot_servlet_config_gen_worker.set_spring_boot_gen_service(this);

	    //SPRING BOOT SWAGGER CONFIG GEN WORKER
	  	//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_swagger_config_worker-GpServerJavaSpringBootGenService", "gen_processing");
	  	this.spring_boot_swagger_config.prep_derived_values(gen_manager.get_project(),
	  												gen_manager.getBase_configs(),
	  												gen_manager.getDerived_configs());
	    this.spring_boot_swagger_config.set_spring_boot_gen_service(this);

	    //SPRING BOOT SWAGGER LIB GEN WORKER
	  	//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_swagger_lib_worker-GpServerJavaSpringBootGenService", "gen_processing");
	  	this.spring_boot_swagger_lib_worker.prep_derived_values(gen_manager.get_project(),
	  												gen_manager.getBase_configs(),
	  												gen_manager.getDerived_configs());
	    this.spring_boot_swagger_lib_worker.set_spring_boot_gen_service(this);

	   //SPRING BOOT JAR LIB GEN WORKER
	  	//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_boot_jar_lib_worker-GpServerJavaSpringBootGenService", "gen_processing");
	  	this.spring_boot_jar_lib_worker.prep_derived_values(gen_manager.get_project(),
	  												gen_manager.getBase_configs(),
	  												gen_manager.getDerived_configs());
	    this.spring_boot_jar_lib_worker.set_spring_boot_gen_service(this);

	    /*this.eclipse_project_worker.prep_derived_values(gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

        this.eclipse_project_worker.set_generation_service(this); */

	    }

	private void process_flow(IGpGenManager gen_manager, GpActivity an_activity) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		long master_flow_id = an_activity.getMaster_flow_id();
		ArrayList<GpFlowControl> a_flow = null;
		try {
			a_flow = this.flow_dao.find_flow_by_id(master_flow_id);
		} catch (Exception e) {
			if (a_flow == null) { // this only happens during dev
				return;
			}
		}

		ArrayList<GpFlowControl> server_flow = new ArrayList<GpFlowControl>();
		for (GpFlowControl a_flow_comp : a_flow) {
			if (a_flow_comp.getType().equals(
					GpGenConstants.GpGenerationType_Server)) {
				server_flow.add(a_flow_comp);
			}

		}

		int i = 1;
		for (GpFlowControl flow_comp : server_flow) {
			Long the_count_for_seq = this.flow_dao.get_count_for_one_seq(
					master_flow_id, GpGenConstants.GpGenerationType_Server, i);

			if (the_count_for_seq == 0) {

				continue;
			}

			int x = 1;
			while (x <= the_count_for_seq) {

				GpFlowControl the_flow = this.flow_dao.find_a_server_flow(
						master_flow_id, i, x);

				//CONTROLLER CLASS BEGINS
				// HERE THE CONTROLLER FOR SPRING BOOT SWAGGER ANNOTATIONS
					if (the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpController)) {
						//gen_manager.update_job_status(project_id,user_id,username,"gen_controller_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
						this.spring_boot_controller.generate_code_by_activity(
								an_activity, gen_manager.get_project(),
								gen_manager.getBase_configs(),
								gen_manager.getDerived_configs(), the_flow,
								gen_manager);
						//gen_manager.update_job_status(project_id,user_id,username,"controller_generated_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
						x++;
						continue;
					}


				///// SERVICE CLASS BEGINS $$$$
					if (the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpService)) {
				// NOW SEND IT TO THE SERVICE WORKER!
					//gen_manager.update_job_status(project_id,user_id,username,"gen_service_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
					this.spring_boot_service_worker.generate_code_by_activity(
							an_activity, gen_manager.get_project(),
							gen_manager.getBase_configs(),
							gen_manager.getDerived_configs(), the_flow,
							gen_manager);
					//gen_manager.update_job_status(project_id,user_id,username,"service_generated_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
					x++;
					continue;
				}

					// DAO CLASS BEGINS

					if (the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao)) {
						//gen_manager.update_job_status(project_id,user_id,username,"gen_jpa_dao_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
						this.spring_boot_dao_gen_worker.generate_code_by_activity(an_activity,
								gen_manager.get_project(),
								gen_manager.getBase_configs(),
								gen_manager.getDerived_configs(), the_flow,
								gen_manager);
						//gen_manager.update_job_status(project_id,user_id,username,"gen_jpadao_generated_in_server_side-GpServerJavaSpringBootGenService","gen_processing");
						x++;
						continue;
						}
				x++;
			}

			i++;

		}
		
	}


	public GpSpringBootControllerGenWorker getSpring_boot_controller() {
		return spring_boot_controller;
	}

	@Resource(name ="GpSpringBootControllerGenWorker")
	public void setSpring_boot_controller(
			GpSpringBootControllerGenWorker spring_boot_controller) {
		this.spring_boot_controller = spring_boot_controller;
	}

	public GpJavaLibsGenWorker getLibs_worker() {
		return libs_worker;
	}

	@Resource(name="GpJavaLibsGenWorker")
	public void setLibs_worker(GpJavaLibsGenWorker libs_worker) {
		this.libs_worker = libs_worker;
	}

	public GpNotDefaultActivityGenWorker getNot_default_activity_worker() {
		return not_default_activity_worker;
	}

	@Resource(name="GpNotDefaultActivityGenWorker")
	public void setNot_default_activity_worker(
			GpNotDefaultActivityGenWorker not_default_activity_worker) {
		this.not_default_activity_worker = not_default_activity_worker;
	}

	/*public GpEclipseWebApplicationGenWorker getEclipse_project_worker() {
		return eclipse_project_worker;
	}
	@Resource(name="GpEclipseWebApplicationGenWorker")
	public void setEclipse_project_worker(
			GpEclipseWebApplicationGenWorker eclipse_project_worker) {
		this.eclipse_project_worker = eclipse_project_worker;
	}*/

	@Override
	public void set_activity_service(GpActivityGenService activity_service) {
		this.activity_service = activity_service;

	}

	public GpSqlDDLWorker getThe_ddl_worker() {
		return the_ddl_worker;
	}

	@Resource(name="GpSqlDDLWorker")
	public void setThe_ddl_worker(GpSqlDDLWorker the_ddl_worker) {
		this.the_ddl_worker = the_ddl_worker;
	}

	public GpGenFlowDao getFlow_dao() {
		return flow_dao;
	}

	@Resource(name="GpGenFlowDao")
	public void setFlow_dao(GpGenFlowDao flow_dao) {
		this.flow_dao = flow_dao;
	}

	public GpJavaServerSpringBootDomainGenWorker getSpring_boot_domain_gen_worker() {
		return spring_boot_domain_gen_worker;
	}
	@Resource(name = "GpJavaServerSpringBootDomainGenWorker")
	public void setSpring_boot_domain_gen_worker(
			GpJavaServerSpringBootDomainGenWorker spring_boot_domain_gen_worker) {
		this.spring_boot_domain_gen_worker = spring_boot_domain_gen_worker;
	}
	public GpSpringBootServiceGenWorker getSpring_boot_service_worker() {
		return spring_boot_service_worker;
	}

	@Resource(name = "GpSpringBootServiceGenWorker")
	public void setSpring_boot_service_worker(
			GpSpringBootServiceGenWorker spring_boot_service_worker) {
		this.spring_boot_service_worker = spring_boot_service_worker;
	}

	public GpSpringBootResourceBundleGenWorker getSpring_boot_resource_bundle_worker() {
		return spring_boot_resource_bundle_worker;
	}

	@Resource(name = "GpSpringBootResourceBundleGenWorker")
	public void setSpring_boot_resource_bundle_worker(
			GpSpringBootResourceBundleGenWorker spring_boot_resource_bundle_worker) {
		this.spring_boot_resource_bundle_worker = spring_boot_resource_bundle_worker;
	}

	public GpSpringBootBaseClassesGenWorker getSpring_boot_base_gen_worker() {
		return spring_boot_base_gen_worker;
	}

	@Resource(name = "GpSpringBootBaseClassesGenWorker")
	public void setSpring_boot_base_gen_worker(
			GpSpringBootBaseClassesGenWorker spring_boot_base_gen_worker) {
		this.spring_boot_base_gen_worker = spring_boot_base_gen_worker;
	}

	public GpSpringBootJpaDaoGenWorker getSpring_boot_dao_gen_worker() {
		return spring_boot_dao_gen_worker;
	}

	@Resource(name = "GpSpringBootJpaDaoGenWorker")
	public void setSpring_boot_dao_gen_worker(
			GpSpringBootJpaDaoGenWorker spring_boot_dao_gen_worker) {
		this.spring_boot_dao_gen_worker = spring_boot_dao_gen_worker;
	}

	public GpSpringBootConfGenWorker getSpring_boot_config_worker() {
		return spring_boot_config_worker;
	}

	@Resource(name  = "GpSpringBootConfGenWorker")
	public void setSpring_boot_config_worker(
			GpSpringBootConfGenWorker spring_boot_config_worker) {
		this.spring_boot_config_worker = spring_boot_config_worker;
	}
	public GpSpringBootMainApplicationGenWorker getSpring_boot_main_application_gen_worker() {
		return spring_boot_main_application_gen_worker;
	}

	@Resource(name = "GpSpringBootMainApplicationGenWorker")
	public void setSpring_boot_main_application_gen_worker(
			GpSpringBootMainApplicationGenWorker spring_boot_main_application_gen_worker) {
		this.spring_boot_main_application_gen_worker = spring_boot_main_application_gen_worker;
	}
	public GpSpringBootServletConfigGenWorker getSpring_boot_servlet_config_gen_worker() {
		return spring_boot_servlet_config_gen_worker;
	}

	@Resource(name = "GpSpringBootServletConfigGenWorker")
	public void setSpring_boot_servlet_config_gen_worker(
			GpSpringBootServletConfigGenWorker spring_boot_servlet_config_gen_worker) {
		this.spring_boot_servlet_config_gen_worker = spring_boot_servlet_config_gen_worker;
	}

	public GpSpringBootSwaggerConfigGenWorker getSpring_boot_swagger_config() {
		return spring_boot_swagger_config;
	}

	@Resource(name = "GpSpringBootSwaggerConfigGenWorker")
	public void setSpring_boot_swagger_config(
			GpSpringBootSwaggerConfigGenWorker spring_boot_swagger_config) {
		this.spring_boot_swagger_config = spring_boot_swagger_config;
	}

	public GpSpringBootSwaggerLibsGenWorker getSpring_boot_swagger_lib_worker() {
		return spring_boot_swagger_lib_worker;
	}

	@Resource(name = "GpSpringBootSwaggerLibsGenWorker")
	public void setSpring_boot_swagger_lib_worker(
			GpSpringBootSwaggerLibsGenWorker spring_boot_swagger_lib_worker) {
		this.spring_boot_swagger_lib_worker = spring_boot_swagger_lib_worker;
	}

	public GpJavaSpringBootLibsGenWorker getSpring_boot_jar_lib_worker() {
		return spring_boot_jar_lib_worker;
	}

	@Resource(name = "GpJavaSpringBootLibsGenWorker")
	public void setSpring_boot_jar_lib_worker(
			GpJavaSpringBootLibsGenWorker spring_boot_jar_lib_worker) {
		this.spring_boot_jar_lib_worker = spring_boot_jar_lib_worker;
	}

	public GpSpringBootDirectoryGenWorker getSpring_boot_directory_gen_worker() {
		return spring_boot_directory_gen_worker;
	}

	@Resource(name = "GpSpringBootDirectoryGenWorker")
	public void setSpring_boot_directory_gen_worker(
			GpSpringBootDirectoryGenWorker spring_boot_directory_gen_worker) {
		this.spring_boot_directory_gen_worker = spring_boot_directory_gen_worker;
	}

	public GpMySqlDMLWorker getThe_dml_worker() {
		return the_dml_worker;
	}

	@Resource(name = "GpMySqlDMLWorker")
	public void setThe_dml_worker(GpMySqlDMLWorker the_dml_worker) {
		this.the_dml_worker = the_dml_worker;
	}

	public Map<Long, GpRelationshipInfo> getMap_services_relationships() {
		return map_services_relationships;
	}
}
