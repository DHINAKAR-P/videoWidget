package com.npb.gp.gen.services.server.java;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GpGenFlowDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpActivityGenService;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.workers.GpNotDefaultActivityGenWorker;
import com.npb.gp.gen.workers.java.GpJavaServerDomainGenWorker;
import com.npb.gp.gen.workers.java.jpa.GpJavaServerDomainGenWorkerForJpa;
import com.npb.gp.gen.workers.server.java.spring.GpJavaLibsGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpLegacyBaseClassesGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpLegacyDirectoryGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpLegacySpringConfGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringControllerGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringDaoLegacySupportGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringResourceBundleGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringServiceGenWorker;
import com.npb.gp.gen.workers.server.java.spring.GpSpringDaoLegacyGenWorker;
import com.npb.gp.gen.workers.server.java.spring.jpa.GpJavaLibsGenWorkerForJpa;
import com.npb.gp.gen.workers.server.java.spring.jpa.GpJpaBaseClassesGenWorker;
import com.npb.gp.gen.workers.server.java.spring.jpa.GpJpaSpringConfGenWorker;
import com.npb.gp.gen.workers.server.java.spring.jpa.GpSpringJpaDaoGenWorker;
import com.npb.gp.gen.workers.server.rdms.mysql.GpMySqlDMLWorker;
import com.npb.gp.gen.workers.server.sql.GpSqlDDLWorker;

/**
 *
 * @author Dan Castillo
 * Date Created: 11/24/2014</br>
 * @since .2</p>
 *
 * this class directs the generation of a java server Spring app</p>
 *
 * 		  Modified Date: 09/29/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 */

@Service("GpServerJavaSpringGenService")
public class GpServerJavaSpringGenService extends GpBaseGenerationService {

	public static String server_port = "8080";

	private GpActivityGenService activity_service;
	private GpGenFlowDao flow_dao;
	private GpSpringControllerGenWorker controller_gen_worker;
	private GpSpringServiceGenWorker service_gen_worker;
	private GpJavaServerDomainGenWorker domain_gen_worker;
	private GpSpringDaoLegacyGenWorker dao_legacy_worker;

	private GpSpringJpaDaoGenWorker jpa_worker;
	private GpJavaServerDomainGenWorkerForJpa domain_worker_for_jpa;
	private GpJpaSpringConfGenWorker spring_config_worker_for_jpa;
	private GpJpaBaseClassesGenWorker jpa_base_classes_worker;
	private GpJavaLibsGenWorkerForJpa libs_worker_for_jpa;

	private GpSpringResourceBundleGenWorker resource_bundle_worker;
	private GpSqlDDLWorker the_ddl_worker;
	private GpMySqlDMLWorker mysql_dml_worker;
	private GpLegacyBaseClassesGenWorker legacy_base_classes_worker;
	private GpLegacySpringConfGenWorker spring_config_worker;
	private GpNotDefaultActivityGenWorker not_default_activity_worker;
	private GpJavaLibsGenWorker libs_worker;
	private GpLegacyDirectoryGenWorker legacy_directory_gen_worker;
	private String file_separator = System.getProperty("file.separator");
	public static String flag = "green";

	@Override
	@SuppressWarnings("unchecked")
	public void generate(IGpGenManager gen_manager) throws Exception {

		//System.out.println("In GpServerJavaSpringGenService - generate - 1");
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
		Path directoryToDelete = Paths.get(p2.toString());
		String gen_server_directory_name = base_configs.get("gen_server_directory_name").getValue();
		Path p3 = Paths.get(p2.toString() + this.file_separator + gen_server_directory_name);

		//HERE WE CALL LEGACY GEN WORKER TO CREATE DIRECTORY STRUCTURE
		//gen_manager.update_job_status(project_id, user_id, username, "legacy_directory_gen_worker-GpServerJavaSpringGenService", "gen_processing");
		legacy_directory_gen_worker.handle_java_devlang_server_configs(the_project,p3 , base_configs, derived_configs, gen_manager);

		//gen_manager.update_job_status(project_id, user_id, username, "preparing_workers-GpServerJavaSpringGenService", "gen_processing");
		this.prep_workers(gen_manager);

		//gen_manager.update_job_status(project_id, user_id, username, "workers_prepared-GpServerJavaSpringGenService", "gen_processing");

		GpArchitypeConfigurations activities_prop = gen_manager
				.getDerived_configs().get(GpGenConstants.PROJECT_ACTIVITIES);


		ArrayList<GpActivity> the_activities =
					(ArrayList<GpActivity>) activities_prop.getObject_value();


		for(GpActivity an_activity : the_activities){

			if (an_activity.getModule_type() != null && an_activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {

				//gen_manager.update_job_status(project_id, user_id, username, "gen_not_default_activity-GpServerJavaSpringGenService", "gen_processing");

				Path base_path = Paths.get(gen_manager.getBase_configs().get("base_generation_directory").getValue());
				String project_folder = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();


				this.not_default_activity_worker.setModule_final_directory(base_path.toString(),project_folder,"server");

				this.not_default_activity_worker.import_server_module(an_activity, this);

				//gen_manager.update_job_status(project_id, user_id, username, "not_default_activity_generated-GpServerJavaSpringGenService", "gen_processing");

			} else {
				if(an_activity.getPrimary_noun() != null)
					this.process_flow(gen_manager, an_activity);
				//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
			}
			//TODO: System level activity
		}


		this.process_global_components(gen_manager);



	}

	private void process_global_components(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		/* flow is not relevant in this case*/
		//gen_manager.update_job_status(project_id, user_id, username, "mysql_ddl_gen_started-GpServerJavaSpringGenService", "gen_processing");
		this.the_ddl_worker.generate_code(gen_manager.get_project(),
										gen_manager.getBase_configs(),
										gen_manager.getDerived_configs(), null, gen_manager,getNot_default_activity_worker().getModule_properties_list());


		if(flag.equals("green")){

			//gen_manager.update_job_status(project_id, user_id, username, "domain_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.domain_worker_for_jpa.generate_core_domain(gen_manager);

		}else if (flag.equals("red")){
			/* flow is not relevant in this case*/
			//Legacy
			//gen_manager.update_job_status(project_id, user_id, username, "domain_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.domain_gen_worker.generate_core_domain(gen_manager);

		}else {

		}

		if(flag.equals("green")){

			//gen_manager.update_job_status(project_id, user_id, username, "legacy_base_classes_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.jpa_base_classes_worker.do_generation(gen_manager);

		}else if (flag.equals("red")){

			/* flow is not relevant in this case*/
			//gen_manager.update_job_status(project_id, user_id, username, "legacy_base_classes_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.legacy_base_classes_worker.do_generation(gen_manager);

		}else{
			System.out.println("....");
		}

		if(flag.equals("green")){

			//gen_manager.update_job_status(project_id, user_id, username, "spring_config_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.spring_config_worker_for_jpa.do_generation(gen_manager);

		}else if(flag.equals("red")){
			/* flow is not relevant in this case*/
			//gen_manager.update_job_status(project_id, user_id, username, "spring_config_gen_started-GpServerJavaSpringGenService", "gen_processing");
			this.spring_config_worker.do_generation(gen_manager);

		}else{
			System.out.println("....");
		}

		if(flag.equals("green")){

			this.libs_worker_for_jpa.move_libs();

		}else if(flag.equals("red")){

			this.libs_worker.move_libs();

		}else{
		System.out.println("....");
		}

		//this.eclipse_project_worker.generate_eclipse_project(gen_manager.get_project());
	}
	private void prep_workers(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		if(flag.equals("green")){

			//JPA Domain
			//gen_manager.update_job_status(project_id, user_id, username, "loading_domain_gen_worker-GpServerJavaSpringGenService", "gen_processing");
			this.domain_worker_for_jpa.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
			this.domain_worker_for_jpa.set_spring_generation_service(this);

		}else if(flag.equals("red")){

			//Domain
			//gen_manager.update_job_status(project_id, user_id, username, "loading_domain_gen_worker-GpServerJavaSpringGenService", "gen_processing");
			this.domain_gen_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
			this.domain_gen_worker.set_spring_generation_service(this);

		}else {
			//Spring boot
		}


		//CONTROLLERS
		//gen_manager.update_job_status(project_id, user_id, username, "loading_controller_gen_worker-GpServerJavaSpringGenService", "gen_processing");

		this.controller_gen_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());


		this.controller_gen_worker.set_generation_service(this);

		//SERVICES
		//gen_manager.update_job_status(project_id, user_id, username, "loading_service_gen_worker-GpServerJavaSpringGenService", "gen_processing");

		this.service_gen_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());
		this.service_gen_worker.set_generation_service(this);

		if (flag.equals("green")) {

			this.jpa_worker.prep_derived_values(gen_manager.get_project(),
					gen_manager.getBase_configs(),
					gen_manager.getDerived_configs());
			this.jpa_worker.set_generation_service(this);

		} else if (flag.equals("red")) {

			//Legacy - DAOs
			//gen_manager.update_job_status(project_id, user_id, username, "loading_dao_legacy_worker-GpServerJavaSpringGenService", "gen_processing");
			this.dao_legacy_worker.prep_derived_values(
									gen_manager.get_project(),
									gen_manager.getBase_configs(),
									gen_manager.getDerived_configs());
			this.dao_legacy_worker.set_generation_service(this);

		} else {
			System.out.println("dog is waiting for spring boot application !!");
		}

		//Resource Bundle
		//gen_manager.update_job_status(project_id, user_id, username, "loading_resource_bundle_worker-GpServerJavaSpringGenService", "gen_processing");

		this.resource_bundle_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());

		this.resource_bundle_worker.set_generation_service(this);

		//MySQL - DDL
		//gen_manager.update_job_status(project_id, user_id, username, "loading_mysql_ddl_worker-GpServerJavaSpringGenService", "gen_processing");

		this.the_ddl_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());

		//MySQL - DML
		//gen_manager.update_job_status(project_id, user_id, username, "loading_mysql_dml_worker-GpServerJavaSpringGenService", "gen_processing");

		this.mysql_dml_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());

		this.mysql_dml_worker.set_generation_service(this);

		if(flag.equals("green")){

			//gen_manager.update_job_status(project_id, user_id, username, "loading_legacy_base_classes_worker-GpServerJavaSpringGenService", "gen_processing");
			this.jpa_base_classes_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
			this.jpa_base_classes_worker.set_generation_service(this);

		}else if (flag.equals("red")){

			//base classes worker
			//gen_manager.update_job_status(project_id, user_id, username, "loading_legacy_base_classes_worker-GpServerJavaSpringGenService", "gen_processing");

			this.legacy_base_classes_worker.prep_derived_values(
											gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());

			this.legacy_base_classes_worker.set_generation_service(this);

		}else{
			System.out.println("...");
		}

		if(flag.equals("green")){

			//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_config_worker-GpServerJavaSpringGenService", "gen_processing");
			this.spring_config_worker_for_jpa.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());

			this.spring_config_worker_for_jpa.set_generation_service(this);

		}else if(flag.equals("red")){
			//spring config worker
			//gen_manager.update_job_status(project_id, user_id, username, "loading_spring_config_worker-GpServerJavaSpringGenService", "gen_processing");
			this.spring_config_worker.prep_derived_values(
												gen_manager.get_project(),
												gen_manager.getBase_configs(),
												gen_manager.getDerived_configs());
			this.spring_config_worker.set_generation_service(this);

		}else{
			System.out.println("--");
		}

		/*
		//eclipse project worker
		gen_manager.update_job_status(project_id, user_id, username, "loading_eclipse_project_worker-GpServerJavaSpringGenService", "gen_processing");

		this.eclipse_project_worker.prep_derived_values(gen_manager.get_project(),
											gen_manager.getBase_configs(),
											gen_manager.getDerived_configs());*/

		//this.eclipse_project_worker.set_generation_service(this);

		if(flag.equals("green")){

			this.libs_worker_for_jpa.prep_derived_values(gen_manager.get_project(),
					gen_manager.getBase_configs(),
					gen_manager.getDerived_configs());
			this.libs_worker_for_jpa.set_generation_service(this);

		}else if(flag.equals("red")){

			this.libs_worker.prep_derived_values(gen_manager.get_project(),
					gen_manager.getBase_configs(),
					gen_manager.getDerived_configs());
			this.libs_worker.set_generation_service(this);

		}else{
			System.out.println("....");
		}


	}

	private void process_flow(IGpGenManager gen_manager, GpActivity an_activity) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		long master_flow_id = an_activity.getMaster_flow_id();
		ArrayList<GpFlowControl> a_flow = null;;
		try{
			a_flow = this.flow_dao.find_flow_by_id(master_flow_id);
		}catch(Exception e){
			if(a_flow == null){ //this only happens during dev
				return;
			}
		}


		ArrayList<GpFlowControl> server_flow = new ArrayList<GpFlowControl>();
		for(GpFlowControl a_flow_comp : a_flow){
			if(a_flow_comp.getType().equals(GpGenConstants.GpGenerationType_Server)){
				server_flow.add(a_flow_comp);
			}

		}


		int i = 1;
		for(GpFlowControl flow_comp : server_flow){
			Long the_count_for_seq = this
					.flow_dao.get_count_for_one_seq(master_flow_id,
							GpGenConstants.GpGenerationType_Server, i);


			if(the_count_for_seq == 0){
				/*this means that it is a child of a previous sequence and was handled in the
				 * the while loop below and does not need to be handled again it would
				 * be more elegant if it was handled in the loop above that  creates
				 * the server flows that this FOR loop iterates through _ Dan Castillo 11/26/2014
				 *
				 *
				 */
				continue;
			}
			//System.out.println("The NUMBER OF SUB_STEPS for FOR STEP " + i + " is: " + the_count_for_seq
			//		+ " the flow_comp is: " + flow_comp.getComponent_type());
			int x = 1;
			while(x <= the_count_for_seq){

				GpFlowControl the_flow =  this.flow_dao.find_a_server_flow(master_flow_id, i, x);
				//System.out.println("the_flow.getComponent_type() is: " + the_flow.getComponent_type());
				if(the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpController)){
					//System.out.println("GOT ME A CONTROLLER");
					// NOW SEND IT TO THE CONTROLLER WORKER!

					//this.controller_gen_worker.generate_code_by_activity(an_activity);
					//this.call_generate_on_workers(an_activity,
					//						gen_manager.get_project(),
					//						gen_manager.getBase_configs(),
					//						gen_manager.getDerived_configs());
					//gen_manager.update_job_status(project_id, user_id, username, "gen_controller_in_server_side-GpServerJavaSpringGenService", "gen_processing");

					this.controller_gen_worker.generate_code_by_activity(
																an_activity,
													gen_manager.get_project(),
													gen_manager.getBase_configs(),
													gen_manager.getDerived_configs(),
													the_flow, gen_manager);

					//gen_manager.update_job_status(project_id, user_id, username, "controller_generated_in_server_side-GpServerJavaSpringGenService", "gen_processing");

					x++;
					continue;
				}

				if(the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpService)){
					// NOW SEND IT TO THE SERVICE WORKER!

					//gen_manager.update_job_status(project_id, user_id, username, "gen_service_in_server_side-GpServerJavaSpringGenService", "gen_processing");

					this.service_gen_worker.generate_code_by_activity(
									 an_activity, gen_manager.get_project(),
											  gen_manager.getBase_configs(),
								gen_manager.getDerived_configs(), the_flow, gen_manager);

					//gen_manager.update_job_status(project_id, user_id, username, "service_generated_in_server_side-GpServerJavaSpringGenService", "gen_processing");

					x++;
					continue;
				}
				if(flag.equals("green")){

					/////////////////////////////// $$ THIS IS JPA WORKER $$
					//this is dan's code. it was commented by kumaresan on 01-feb-16
					// i need to add this line for jpa generation. $$$$$
					///if(the_flow.getComponent_type().equals(GpGenConstants.Component_type_GpJpa)){

					if(the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao)){
						//gen_manager.update_job_status(project_id, user_id, username, "gen_dao_legacy_in_server_side-GpServerJavaSpringGenService", "gen_processing");
						this.jpa_worker.generate_code_by_activity(
								 				an_activity, gen_manager.get_project(),
								 				gen_manager.getBase_configs(),
								 				gen_manager.getDerived_configs(), the_flow, gen_manager);
						//gen_manager.update_job_status(project_id, user_id, username, "dao_legacy_generated_in_server_side-GpServerJavaSpringGenService", "gen_processing");
						x++;
						continue;
					}
					}else if(flag.equals("red")){
					 //////////////////////////////////////////$$ THIS IS LEGACY WORKER $$
					 //this is kumaresan's code::01-feb-16
					 ///if(the_flow.getComponent_type().equals(GpGenConstants.Component_type_GpJpa)){

						if(the_flow.getComponent_type().equals(GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao)){
							//gen_manager.update_job_status(project_id, user_id, username, "gen_dao_legacy_in_server_side-GpServerJavaSpringGenService", "gen_processing");
							this.dao_legacy_worker.generate_code_by_activity(
									 				an_activity, gen_manager.get_project(),
									 				gen_manager.getBase_configs(),
									 				gen_manager.getDerived_configs(), the_flow, gen_manager);

							//gen_manager.update_job_status(project_id, user_id, username, "dao_legacy_generated_in_server_side-GpServerJavaSpringGenService", "gen_processing");
							x++;
							continue;
						}

					}else {
						System.out.println("waiting for spring boot");
						}

				x++;
			}

			i++;

		}

	}


	//Getters and Setters

	public GpSpringJpaDaoGenWorker getJpa_worker() {
		return jpa_worker;
	}

	@Resource(name="GpSpringJpaDaoGenWorker")
	public void setJpa_worker(GpSpringJpaDaoGenWorker jpa_worker) {
		this.jpa_worker = jpa_worker;
	}

	public GpJpaSpringConfGenWorker getSpring_config_worker_for_jpa() {
		return spring_config_worker_for_jpa;
	}

	@Resource(name="GpJpaSpringConfGenWorker")
	public void setSpring_config_worker_for_jpa(GpJpaSpringConfGenWorker spring_config_worker_for_jpa) {
		this.spring_config_worker_for_jpa = spring_config_worker_for_jpa;
	}

	public GpJavaServerDomainGenWorkerForJpa getDomain_worker_for_jpa() {
		return domain_worker_for_jpa;
	}

	@Resource(name="GpJavaServerDomainGenWorkerForJpa")
	public void setDomain_worker_for_jpa(GpJavaServerDomainGenWorkerForJpa domain_worker_for_jpa) {
		this.domain_worker_for_jpa = domain_worker_for_jpa;
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
	public GpLegacySpringConfGenWorker getSpring_config_worker() {
		return spring_config_worker;
	}
	@Resource(name="GpLegacySpringConfGenWorker")
	public void setSpring_config_worker(
			GpLegacySpringConfGenWorker spring_config_worker) {
		this.spring_config_worker = spring_config_worker;
	}
	public GpLegacyBaseClassesGenWorker getLegacy_base_classes_worker() {
		return legacy_base_classes_worker;
	}
	@Resource(name="GpLegacyBaseClassesGenWorker")
	public void setLegacy_base_classes_worker(
			GpLegacyBaseClassesGenWorker legacy_base_classes_worker) {
		this.legacy_base_classes_worker = legacy_base_classes_worker;
	}

	/*
	 * this is only used by the dao_legacy_worker
	 * I only put it here for consistency - Dan Castillo 01/23/15
	 *
	 */
	private GpSpringDaoLegacySupportGenWorker legacy_dao_mapper_worker;


	public GpSpringDaoLegacySupportGenWorker getLegacy_dao_mapper_worker() {
		return legacy_dao_mapper_worker;
	}
	public void setLegacy_dao_mapper_worker(
			GpSpringDaoLegacySupportGenWorker legacy_dao_mapper_worker) {
		this.legacy_dao_mapper_worker = legacy_dao_mapper_worker;
	}
	public GpMySqlDMLWorker getMysql_dml_worker() {
		return mysql_dml_worker;
	}
	@Resource(name="GpMySqlDMLWorker")
	public void setMysql_dml_worker(GpMySqlDMLWorker mysql_dml_worker) {
		this.mysql_dml_worker = mysql_dml_worker;
	}

	public GpSqlDDLWorker getThe_ddl_worker() {
		return the_ddl_worker;
	}

	@Resource(name="GpSqlDDLWorker")
	public void setThe_ddl_worker(GpSqlDDLWorker the_ddl_worker) {
		this.the_ddl_worker = the_ddl_worker;
	}

	public GpSpringResourceBundleGenWorker getResource_bundle_worker() {
		return resource_bundle_worker;
	}

	@Resource(name="GpSpringResourceBundleGenWorker")
	public void setResource_bundle_worker(
			GpSpringResourceBundleGenWorker resource_bundle_worker) {
		this.resource_bundle_worker = resource_bundle_worker;
	}

	public GpSpringControllerGenWorker getController_gen_worker() {
		return controller_gen_worker;
	}



	@Resource(name="GpSpringControllerGenWorker")
	public void setController_gen_worker(
			GpSpringControllerGenWorker controller_gen_worker) {
		this.controller_gen_worker = controller_gen_worker;
	}



	public GpJavaServerDomainGenWorker getDomain_gen_worker() {
		return domain_gen_worker;
	}

	@Resource(name="GpJavaServerDomainGenWorker")
	public void setDomain_gen_worker(GpJavaServerDomainGenWorker domain_gen_worker) {
		this.domain_gen_worker = domain_gen_worker;
	}


	public GpSpringServiceGenWorker getService_gen_worker() {
		return service_gen_worker;
	}

	@Resource(name="GpSpringServiceGenWorker")
	public void setService_gen_worker(GpSpringServiceGenWorker service_gen_worker) {
		this.service_gen_worker = service_gen_worker;
	}




	public GpSpringDaoLegacyGenWorker getDao_legacy_worker() {
		return dao_legacy_worker;
	}

	@Resource(name="GpSpringDaoLegacyGenWorker")
	public void setDao_legacy_worker(GpSpringDaoLegacyGenWorker dao_legacy_worker) {
		this.dao_legacy_worker = dao_legacy_worker;
	}


	public GpGenFlowDao getFlow_dao() {
		return flow_dao;
	}

	@Resource(name="GpGenFlowDao")
	public void setFlow_dao(GpGenFlowDao flow_dao) {
		this.flow_dao = flow_dao;
	}


	@Override
	public HashMap<String, GpArchitypeConfigurations> get_generation_configurations()
			throws Exception {


		return null;
	}

	public GpJpaBaseClassesGenWorker getJpa_base_classes_worker() {
		return jpa_base_classes_worker;
	}

	@Resource(name="GpJpaBaseClassesGenWorker")
	public void setJpa_base_classes_worker(GpJpaBaseClassesGenWorker jpa_base_classes_worker) {
		this.jpa_base_classes_worker = jpa_base_classes_worker;
	}

	public GpJavaLibsGenWorkerForJpa getLibs_worker_for_jpa() {
		return libs_worker_for_jpa;
	}

	@Resource(name="GpJavaLibsGenWorkerForJpa")
	public void setLibs_worker_for_jpa(GpJavaLibsGenWorkerForJpa libs_worker_for_jpa) {
		this.libs_worker_for_jpa = libs_worker_for_jpa;
	}
	public GpLegacyDirectoryGenWorker getLegacy_directory_gen_worker() {
		return legacy_directory_gen_worker;
	}

	@Resource(name = "GpLegacyDirectoryGenWorker")
	public void setLegacy_directory_gen_worker(
			GpLegacyDirectoryGenWorker legacy_directory_gen_worker) {
		this.legacy_directory_gen_worker = legacy_directory_gen_worker;
	}

}
