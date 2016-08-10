package com.npb.gp.gen.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.GpJobDao;
import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.dao.mysql.GpProjectDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.dao.mysql.GpProjectTemplateDao;
import com.npb.gp.dao.mysql.GpUserDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpJob;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpActivityGenService;
import com.npb.gp.gen.services.GpBaseConfigurationService;
import com.npb.gp.gen.services.GpModelGenerationService;
import com.npb.gp.gen.services.GpPostGenerationService;

/**
 *
 * @author Dan Castillo Date Created: 06/11/2014</br>
 * @since .01</p>
 *
 *        The purpose of this class is to manage the lifecycle of the
 *        application </br> generation. It is intended that as the Geppetto
 *        systems matures, there will </br> other classes that will manage the
 *        code generation, that would presumably be</br> more advanced versions
 *        of this class</p>
 *
 *
 *        Modified Date: 09/28/2015</br> Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *
 */
@Service("GpDefaultGenerationManager")
@Scope("prototype")
public class GpDefaultGenerationManager implements IGpGenManager{

	private GpBaseConfigurationService default_base_config_service;
	private GpProject the_project;
	private GpUser the_user;
	private GpModelGenerationService model_gen_service;
	private GpActivityGenService activity_gen_service;
	private GpPostGenerationService postGenerationService;
	private Map<Long,GpClientDeviceTypes> client_device_types;
	private HashMap<String, GpArchitypeConfigurations> base_configs;
	private HashMap<String, GpArchitypeConfigurations> derived_configs = new HashMap<String, GpArchitypeConfigurations>();
	private GpJobDao job_dao;
	private GpProjectDao project_dao;
	private GpScreenXDao screen_dao;
	private GpNounDao noun_dao;
	private GpProjectTemplateDao project_template_dao;
	private GpUserDao user_dao;

	@Override
	public void accept_control(GpJob the_job) throws Exception {
		// TODO Auto-generated method stuB
		// System.out.println("In GpDefaultGenerationManager - accept_control");

		// read the table that has the generation request and obtain the
		// project id and user id, and use those to read the project info
		if (!this.take_action(the_job)) {
			return;
		}

		try {

			this.update_job_status(the_project.getId(), the_user.getId(),
					the_user.getUsername(),
					"gen_started","Generating...", "");

			this.setClient_device_types(this.default_base_config_service
					.get_client_device_types(this));

			this.base_configs = this.default_base_config_service
					.get_base_generation_configurations(this);

			this.default_base_config_service.execute_base_configurations(this);
			this.activity_gen_service.setPostGenerationService(postGenerationService);
			this.activity_gen_service.generate(this);

			String run_config = this.base_configs
					.get("run_config").getValue();
			long user_id = get_user().getId();
			String username = get_user().getUsername();
			if(!run_config.equals("DEV")){
				if(this.get_user().getDocker_json()!=null){
					update_job_status(the_job.getProject_id(), user_id, username, "gen_building", "Building", "gen_processed");
					this.postGenerationService.set_activity_service(this.activity_gen_service);
					this.postGenerationService.generate(this);
				}
				else{
					this.update_job_status(the_project.getId(), the_user.getId(),the_user.getUsername(),"gen_error","","no docker containers for this user");
				}
			}
			else{
				update_job_status(the_job.getProject_id(), user_id, username, "gen_finished","", "gen_processed");
			}
		} catch (Exception e) {
			System.err.println("Exception while generation");
			e.printStackTrace(System.out);
			this.update_job_status(the_project.getId(), the_user.getId(),
					the_user.getUsername(),
					"gen_error","",
					e.getLocalizedMessage());
		}
	}

	/**
	 * Reads the job_table to determine if there is a project that needs to be
	 * generated - if there is it uses the projectid to read the project
	 * information and uses the userid to obtain a user
	 *
	 *
	 * @return boolean - which indicates whether or not there is work to do
	 */
	private boolean take_action(GpJob the_job) {

		try {

			this.the_project = this.find_project(the_job.getProject_id());
			/*
			 * for now hard code user - in the future you use the user id you
			 * get from the job and use it to call the user table and obtain a
			 * fully loaded user - BUT the user tables are not ready at this
			 * time - Dan - 06/13/14
			 */
			// this.the_user = new GpUser();
			// this.the_user.setId(the_job.getUser_id());

			/* Getting user data from the database */
			this.the_user = user_dao.find_user_by_id(the_job.getUser_id());
			//this.update_job_status(the_project.getId(), the_user.getId(),
				//	the_user.getUsername(),
					//"gen_requested-GpDefaultGenerationManager",
					//"gen_processing");

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			this.update_job_status(the_project.getId(), the_user.getId(),
					the_user.getUsername(),
					"gen_error","",
					e.getLocalizedMessage());
		}

		return false;

	}

	@Override
	public void update_job_status(long project_id, long user_id,
			String username, String status, String status_message, String stacktrace) {
		this.job_dao.insert(project_id, user_id, username,status,stacktrace,status_message);

		/*this.job_dao.update_status(project_id, user_id, username, status_info);
		this.job_dao.update_message(project_id, user_id, username,
				status_info.split("-")[0]);
		this.job_dao.update_stacktrace(project_id, user_id, username,
				stacktrace);*/
	}

	private GpProject find_project(long project_id) throws Exception {

		GpProject the_project = new GpProject();
		try {
			the_project = this.project_dao.find_by_id(project_id);

			GpModule thedefaultmod = new GpModule();
			the_project.setDefault_module(thedefaultmod);

			thedefaultmod.setLabel("dat not nite huga!");
			thedefaultmod.setName("defaultmod");
			thedefaultmod.setId(12368127368261L);
			thedefaultmod.setDescription("Default Mod testing");
			thedefaultmod.setMulti_user_status("inuse");
			thedefaultmod.setMulti_user_info("Locked by:\t Grace Castillo\n"
					+ "Contact number:\t 212-580-6680");

			ArrayList<GpNoun> noun_list = (ArrayList<GpNoun>) this.noun_dao
					.find_by_project_id(project_id);

			the_project.setProject_nouns(noun_list);

			HashMap<String, String> template_list = this.project_template_dao
					.find_by_project_id(project_id);

			the_project.setProject_templates(template_list);
			// ArrayList<GpActivity> activity_list;

			/*
			 * activity_list = (ArrayList<GpActivity>)this.activity_dao
			 * .find_all_base_by_projectid(project_id);
			 *
			 * // ArrayList<GpNoun> noun_list = (ArrayList<GpNoun>)
			 * this.noun_dao .find_by_project_id(project_id);
			 *
			 *
			 *
			 * ArrayList<GpScreen> screen_list = (ArrayList<GpScreen>)
			 * this.screen_dao .find_all_base_by_projectid(project_id);
			 */

			/*
			 * this.merge_project_components(the_project, activity_list,
			 * noun_list, screen_list);
			 *
			 * thedefaultmod.setTheactivities(activity_list);
			 */

			return the_project;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private void merge_project_components(GpProject the_project,
			ArrayList<GpActivity> activity_list, ArrayList<GpNoun> noun_list,
			ArrayList<GpScreen> screen_list) {

		for (GpActivity activity : activity_list) {
			ArrayList<GpNoun> sec_noun_list = activity.getSecondary_nouns();
			// activity.getPrimary_noun() -- OJO you should deal with the
			// primary noun as well
			for (GpNoun sec_noun : sec_noun_list) {
				for (GpNoun noun : noun_list) {
					if (noun.getId() == sec_noun.getId()) {
						sec_noun.setName(noun.getName());
						sec_noun.setDescription(noun.getDescription());
						sec_noun.setLabel(noun.getLabel());

					}
				}
			}
		}

		for (GpActivity activity : activity_list) {
			activity.setPc_screens(new ArrayList<GpScreen>());
			activity.setTablet_screens(new ArrayList<GpScreen>());
			activity.setPhone_screens(new ArrayList<GpScreen>());
			for (GpScreen screen : screen_list) {
				if (screen.getActivity_id() == activity.getId()) {
					if (screen.getType()
							.equals(GpBaseConstants.GpDeviceType_Pc)) {
						activity.getPc_screens().add(screen);
					} else if (screen.getType().equals(
							GpBaseConstants.GpDeviceType_Tablet)) {
						activity.getTablet_screens().add(screen);
					} else if (screen.getType().equals(
							GpBaseConstants.GpDeviceType_Phone)) {
						activity.getPhone_screens().add(screen);
					}
				}
			}
		}
		the_project.setProject_nouns(noun_list);
		the_project.setProject_screens(screen_list);

	}

	//Setters and Getters

	public Map<Long, GpClientDeviceTypes> getClient_device_types() {
		return client_device_types;
	}

	public void setClient_device_types(
			Map<Long, GpClientDeviceTypes> client_device_types) {
		this.client_device_types = client_device_types;
	}

	public GpPostGenerationService getPostGenerationService() {
		return postGenerationService;
	}
	@Resource(name="GpPostGenerationService")
	public void setPostGenerationService(
			GpPostGenerationService postGenerationService) {
		this.postGenerationService = postGenerationService;
	}

	public GpUserDao getUser_dao() {
		return user_dao;
	}

	@Resource(name="GpUserDao")
	public void setUser_dao(GpUserDao user_dao) {
		this.user_dao = user_dao;
	}

	public GpActivityGenService getActivity_gen_service() {
		return activity_gen_service;
	}

	@Resource(name="GpActivityGenService")
	public void setActivity_gen_service(GpActivityGenService activity_gen_service) {
		this.activity_gen_service = activity_gen_service;
	}

	public GpProjectDao getProject_dao() {
		return project_dao;
	}
	@Resource(name="GpProjectDao")
	public void setProject_dao(GpProjectDao project_dao) {
		this.project_dao = project_dao;
	}

	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}
	@Resource(name="GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}


	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}



	public HashMap<String, GpArchitypeConfigurations> getBase_configs() {
		return base_configs;
	}
	public void setBase_configs(
			HashMap<String, GpArchitypeConfigurations> base_configs) {
		this.base_configs = base_configs;
	}
	public HashMap<String, GpArchitypeConfigurations> getDerived_configs() {
		return derived_configs;
	}
	public void setDerived_configs(
			HashMap<String, GpArchitypeConfigurations> derived_configs) {
		this.derived_configs = derived_configs;
	}
	public GpUser get_user(){
		return this.the_user;
	}
	public GpProject get_project(){
		return this.the_project;

	}
	public GpBaseConfigurationService getDefault_base_config_service() {
		return default_base_config_service;
	}

	public GpModelGenerationService getModel_gen_service() {
		return model_gen_service;
	}

	@Resource(name="GpModelGenerationService")
	public void setModel_gen_service(GpModelGenerationService model_gen_service) {
		this.model_gen_service = model_gen_service;
	}
	@Resource(name="GpBaseConfigurationService")
	public void setDefault_base_config_service(
			GpBaseConfigurationService default_base_config_service) {
		this.default_base_config_service = default_base_config_service;
	}

	public GpJobDao getJob_dao() {
		return job_dao;
	}

	@Resource(name="GpJobDao")
	public void setJob_dao(GpJobDao job_dao) {
		this.job_dao = job_dao;
	}

	public GpProjectTemplateDao getProject_template_dao() {
		return project_template_dao;
	}

	@Resource(name="GpProjectTemplateDao")
	public void setProject_template_dao(GpProjectTemplateDao project_template_dao) {
		this.project_template_dao = project_template_dao;
	}
}
