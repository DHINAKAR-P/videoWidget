package com.npb.gp.gen.services;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GenActivityDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.interfaces.services.IGpActivityGenService;
import com.npb.gp.gen.services.client.angular.GpClientAngularJSGenService;
import com.npb.gp.gen.services.post.build.GpBuildService;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;
import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;

/**
 *
 * @author Dan Castillo
 * Date Created: 11/16/2014</br>
 * @since .2</p>
 *
 * Handles generation of Activities see GpActivity for a description of the concept </p>
 *
 * Modified Date: 02/04/2015</br>
 * Modified By:  Dan Castillo</p>
 *
 * added the process_client_based_generation method in order to accommodate</br>
 * client code generation</p>
 *
 *
 * 		  Modified Date: 09/29/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *
 *         Modified Date: 03/09/2016</br>
 *        Modified By: Kumaresan Perumal</br>
 *        <p>
 *       Here i added the code for spring boot jpa code generation.
 *        </p>
 *
 *
 */
@Service("GpActivityGenService")
public class GpActivityGenService extends GpBaseGenerationService implements
														IGpActivityGenService {
	private GenActivityDao activity_dao;
	private GpNounDao noun_dao;
	private GpServerJavaSpringGenService server_java_spring_gen_service;
	private GpClientAngularJSGenService client_angular_gen_service;
	private GpServerNodeExpressGenService server_node_express_gen_service;
	private GpVerbsDao verbs_dao;
	private GpServerJavaSpringBootGenService server_java_spring_boot_gen_service;
	private GpPostGenerationService postGenerationService;
	
	public void setPostGenerationService(GpPostGenerationService postGenerationService) {
		this.postGenerationService = postGenerationService;
	}
	
	public GpPostGenerationService getPostGenerationService() {
		return postGenerationService;
	}

	public GpServerJavaSpringBootGenService getServer_java_spring_boot_gen_service() {
		return server_java_spring_boot_gen_service;
	}

	@Resource(name = "GpServerJavaSpringBootGenService")
	public void setServer_java_spring_boot_gen_service(
			GpServerJavaSpringBootGenService server_java_spring_boot_gen_service) {
		this.server_java_spring_boot_gen_service = server_java_spring_boot_gen_service;
	}

	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		//System.out.println("In GpActivityGenService - generate -1");
		super.check_project_type(gen_manager);
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "loading_activities-GpActivityGenService", "gen_processing");
		ArrayList<GpActivity> the_activities = this.activity_dao
								.find_all_base_by_projectid(the_project.getId());
		//gen_manager.update_job_status(project_id, user_id, username, "activities_loaded-GpActivityGenService", "gen_processing");

		//gen_manager.update_job_status(project_id, user_id, username, "loading_nouns_by_activity-GpActivityGenService", "gen_processing");
		this.handle_activtiy_primary_noun(the_activities, gen_manager);

		//gen_manager.update_job_status(project_id, user_id, username, "loading_verbs_by_activity-GpActivityGenService", "gen_processing");
		this.handle_verbs_and_auths(the_activities, gen_manager);

		GpArchitypeConfigurations prop_activities = new GpArchitypeConfigurations();
		prop_activities.setName(GpGenConstants.PROJECT_ACTIVITIES);
		prop_activities.setObject_value(the_activities);

		gen_manager.getDerived_configs().put(GpGenConstants
										.PROJECT_ACTIVITIES, prop_activities);

		if(the_project.getGeneration_type().equals(
									GpGenConstants.GpGenerationType_Server)){
			this.process_server_based_generation(gen_manager, the_project);

		}else if(the_project.getGeneration_type().equals(
									GpGenConstants.GpGenerationType_Client)){

		}else if(the_project.getGeneration_type().equals(
									GpGenConstants.GpGenerationType_Mixed)){

			/*by convention always start with the server side - Dan Castillo 11/25/2014  */
			this.process_server_based_generation(gen_manager, the_project);
			this.process_client_based_generation(gen_manager, the_project);


		}

	}

	private void process_client_based_generation(IGpGenManager gen_manager,
			GpProject the_project) throws Exception{
		if (super.front_isJavaScriptAngular) {
			this.client_angular_gen_service.set_activity_service(this);
			this.client_angular_gen_service.generate(gen_manager);
		}
	}

	private void process_server_based_generation(IGpGenManager gen_manager,
									GpProject the_project) throws Exception{

		if (super.back_isJavaSpring) {
			this.server_java_spring_gen_service.set_activity_service(this);
			this.server_java_spring_gen_service.generate(gen_manager);
		}
		if (super.back_isJavaScriptNodeJSExpress) {
			this.server_node_express_gen_service.set_activity_service(this);
			this.server_node_express_gen_service.generate(gen_manager);
		}

		if (super.back_isJavaSpringBootJpa) {
			this.server_java_spring_boot_gen_service.set_activity_service(this);
			this.server_java_spring_boot_gen_service.generate(gen_manager);
		}
	}

	private void handle_activtiy_primary_noun(ArrayList<GpActivity> the_activities,
														IGpGenManager gen_manager) throws Exception{

		for(GpActivity the_activity : the_activities){
			if(the_activity.getPrimary_noun() == null){  //user may not have added a primary noun yet - Dan
				System.out.println("primary noun null");
				continue;
			}

			long project_id = gen_manager.get_project().getId();
			long user_id = gen_manager.get_user().getId();
			String username = gen_manager.get_user().getUsername();

			//gen_manager.update_job_status(project_id, user_id, username, "loading_primary_noun_by_activity-GpActivityGenService", "gen_processing");

			GpNoun primary_noun = this.noun_dao.find_by_id(the_activity.getPrimary_noun().getId());
			the_activity.setPrimary_noun(primary_noun);

			//gen_manager.update_job_status(project_id, user_id, username, "primary_noun_by_activity_loaded-GpActivityGenService", "gen_processing");
		}


	}




	/**
	 * The purpose of this method is to match the ID's of a verb with the name of the verb
	 * and to match up the id of an authorization with the name of the authorization
	 * the names are used in the code generation process to assign security groups to
	 * verbs - the ID's are used used so that the names don't have to be hard coded
	 * @param the_activities
	 * @param gen_manager
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void handle_verbs_and_auths(ArrayList<GpActivity> the_activities,
													IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "loading_verbs_and_authorization-GpActivityGenService", "gen_processing");

		GpArchitypeConfigurations auth_configs = gen_manager.getDerived_configs().get(GpGenConstants.AUTHORIZATION_DEFINITIONS);
		ArrayList<GpAuthorization> the_auths = (ArrayList<GpAuthorization>)auth_configs.getObject_value();

		for(GpActivity act : the_activities){
			ArrayList<GpVerb> verb_list = this.verbs_dao.get_verbs_by_activity_id(act.getId());
			if(verb_list.size() < 1){
				continue;
			}
			act.setTheverbs(verb_list);
			for(GpVerb act_verb : verb_list){
				ArrayList<GpAuthorization> auth_list = act_verb.getAuthorizations();
				for(GpAuthorization base_auth : the_auths){
					for(GpAuthorization act_auth : auth_list){
						if(base_auth.getId() == act_auth.getId()){
							act_auth.setName(base_auth.getName());
						}
					}
				}
			}
		}

		//gen_manager.update_job_status(project_id, user_id, username, "verbs_and_authorizations_loaded-GpActivityGenService", "gen_processing");
	}

	//Getters and Setters

	public GpServerNodeExpressGenService getServer_node_express_gen_service() {
		return server_node_express_gen_service;
	}
	@Resource(name="GpServerNodeExpressGenService")
	public void setServer_node_express_gen_service(GpServerNodeExpressGenService server_node_express_gen_service) {
		this.server_node_express_gen_service = server_node_express_gen_service;
	}


	public GpVerbsDao getVerbs_dao() {
		return verbs_dao;
	}
	@Resource(name="GpVerbsDao")
	public void setVerbs_dao(GpVerbsDao verbs_dao) {
		this.verbs_dao = verbs_dao;
	}

	public GpClientAngularJSGenService getClient_angular_gen_service() {
		return client_angular_gen_service;
	}

	@Resource(name="GpClientAngularJSGenService")
	public void setClient_angular_gen_service(
			GpClientAngularJSGenService client_angular_gen_service) {
		this.client_angular_gen_service = client_angular_gen_service;
	}


	public GenActivityDao getActivity_dao() {
		return activity_dao;
	}


	@Resource(name="GenActivityDao")
	public void setActivity_dao(GenActivityDao activity_dao) {
		this.activity_dao = activity_dao;
	}



	public GpNounDao getNoun_dao() {
		return noun_dao;
	}

	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}


	public GpServerJavaSpringGenService getServer_java_spring_gen_service() {
		return server_java_spring_gen_service;
	}

	@Resource(name="GpServerJavaSpringGenService")
	public void setServer_java_spring_gen_service(
			GpServerJavaSpringGenService server_java_spring_gen_service) {
		this.server_java_spring_gen_service = server_java_spring_gen_service;
	}
}
