package com.npb.gp.gen.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpDevLanguages;
import com.npb.gp.dao.mysql.GpAuthorization_DefinitionsDao;
import com.npb.gp.dao.mysql.GpBaseVerbsDao;
import com.npb.gp.dao.mysql.GpTechPropertyDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GenClientDeviceTypesDao;
import com.npb.gp.gen.dao.mysql.GpGenBaseConfigDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.interfaces.services.IGpBaseConfigurationService;
import com.npb.gp.gen.services.support.base_configuration.GpDeletingFileVisitor;
import com.npb.gp.gen.workers.gradle.GpGradleGenWorker;


/**
 *
 * @author Dan Castillo
 * Date Created: 06/11/2014</br>
 * @since .01</p>
 *
 * loads and manipulates the initial set of information needed to carry out</br>
 * the application generation</p>
 *
 *		  Modified Date: 09/28/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *
 */
@Service("GpBaseConfigurationService")
public class GpBaseConfigurationService implements IGpBaseConfigurationService {

	private Log log = LogFactory.getLog(getClass());
	private GpGenBaseConfigDao config_dao;
	private GpTechPropertyDao tech_properties_dao;
	private GpVerbsDao verbs_dao;
	private GpBaseVerbsDao base_verb_dao;
	private GpAuthorization_DefinitionsDao auth_dao;
	private GenClientDeviceTypesDao clientDeviceTypesDao;

	private String file_separator = System.getProperty("file.separator");

	private GpGradleGenWorker gradle_worker;

	public GpGradleGenWorker getGradle_worker() {
		return gradle_worker;
	}
	@Resource(name="GpGradleGenWorker")
	public void setGradle_worker(GpGradleGenWorker gradle_worker) {
		this.gradle_worker = gradle_worker;
	}
	public GpBaseVerbsDao getBase_verb_dao() {
		return base_verb_dao;
	}
	@Resource(name="GpBaseVerbsDao")
	public void setBase_verb_dao(GpBaseVerbsDao base_verb_dao) {
		this.base_verb_dao = base_verb_dao;
	}

	public GpTechPropertyDao getTech_properties_dao() {
		return tech_properties_dao;
	}

	@Resource(name="GpTechPropertyDao")
	public void setTech_properties_dao(GpTechPropertyDao tech_properties_dao) {
		this.tech_properties_dao = tech_properties_dao;
	}


	public GpGenBaseConfigDao getConfig_dao() {
		return config_dao;
	}


	@Resource(name="GpGenBaseConfigDao")
	public void setConfig_dao(GpGenBaseConfigDao config_dao) {
		this.config_dao = config_dao;
	}


	public GpVerbsDao getVerbs_dao() {
		return verbs_dao;
	}

	@Resource(name="GpVerbsDao")
	public void setVerbs_dao(GpVerbsDao verbs_dao) {
		this.verbs_dao = verbs_dao;
	}

	public GpAuthorization_DefinitionsDao getAuth_dao() {
		return auth_dao;
	}

	@Resource(name="GpAuthorization_DefinitionsDao")
	public void setAuth_dao(GpAuthorization_DefinitionsDao auth_dao) {
		this.auth_dao = auth_dao;
	}

	@Override
	public HashMap<String, GpArchitypeConfigurations> get_base_generation_configurations(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "loading_generation_configurations-GpBaseConfigurationService", "gen_processing");

		log.debug("In GpBaseConfigurationService - get_generation_configurations");
		HashMap<String, GpArchitypeConfigurations> the_map
								= this.config_dao.load_configs("config");

		//gen_manager.update_job_status(project_id, user_id, username, "generation_configurations_loaded-GpBaseConfigurationService", "gen_processing");

		//for(String key: the_map.keySet()){
		//System.out.println(key  +" :: "+ the_map.get(key).getValue());
		//}
		//System.out.println("");
		return the_map;
	}

	public Map<Long, GpClientDeviceTypes> get_client_device_types(IGpGenManager gen_manager) {
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "loading_client_device_types-GpBaseConfigurationService", "gen_processing");

		Map<Long, GpClientDeviceTypes> client_devices_types_list = this.clientDeviceTypesDao.load_client_devices_types();

		//gen_manager.update_job_status(project_id, user_id, username, "client_device_types_loaded-GpBaseConfigurationService", "gen_processing");

		return client_devices_types_list;
	}


	public void execute_base_configurations(IGpGenManager gen_manager) throws Exception{

		//System.out.println("In GpBaseConfigurationService - execute_base_configurations");
		HashMap<String, GpArchitypeConfigurations> base_configs = gen_manager.getBase_configs();
		HashMap<String, GpArchitypeConfigurations> derived_configs = gen_manager.getDerived_configs();
		Path p1 = Paths.get(base_configs.get("base_generation_directory").getValue());



		GpProject the_project = gen_manager.get_project();
		GpUser user = gen_manager.get_user();
		String project_folder = the_project.getName() + "_" + user.getId();


		Path p2 = Paths.get(p1.toString() + this.file_separator + project_folder);
		Path directoryToDelete = Paths.get(p2.toString());

		if(Files.exists(directoryToDelete)){
			GpDeletingFileVisitor delFileVisitor = new GpDeletingFileVisitor(project_folder);
	        Files.walkFileTree(directoryToDelete, delFileVisitor);
		}
		else{
	        Files.createDirectories(p2);
		}
        this.gradle_worker.prep_derived_values(the_project, base_configs, derived_configs);
        this.gradle_worker.generate_gradle_scripts(user, gen_manager);

		String gen_server_directory_name = base_configs.get("gen_server_directory_name").getValue();
		Path p3 = Paths.get(p2.toString() + this.file_separator + gen_server_directory_name);
		Files.createDirectory(p3);

		GpArchitypeConfigurations gen_server_root_path_config = new GpArchitypeConfigurations();
		gen_server_root_path_config.setName(GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH);
		gen_server_root_path_config.setObject_value(p3);
		derived_configs.put(GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH, gen_server_root_path_config);

		String gen_client_directory_name = base_configs.get(GpGenConstants.GEN_CLIENT_DIRECTORY_NAME_PATH).getValue();
		Path p4 = Paths.get(p2.toString() + this.file_separator + gen_client_directory_name);
		Files.createDirectory(p4);

		GpArchitypeConfigurations gen_client_root_path_config = new GpArchitypeConfigurations();
		gen_client_root_path_config.setName(GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH);
		gen_client_root_path_config.setObject_value(p4);
		derived_configs.put(GpGenConstants.GEN_CLIENT_DIRECTORY_NAME_PATH, gen_client_root_path_config);

		ArrayList<GpTechProperties> tech_property_list =
								this.tech_properties_dao.get_all_properties();

		GpArchitypeConfigurations tech_property_list_config = new GpArchitypeConfigurations();

		tech_property_list_config.setName(GpGenConstants.TECH_PROPERTY_LIST);
		tech_property_list_config.setObject_value(tech_property_list);

		base_configs.put(GpGenConstants.TECH_PROPERTY_LIST, tech_property_list_config);

		this.handle_base_base_verbs(derived_configs, gen_manager);
		this.handle_base_authorizations(derived_configs, gen_manager);

	}



	private void handle_base_base_verbs(
			HashMap<String,GpArchitypeConfigurations> derived_configs, IGpGenManager gen_manager) throws Exception{

		GpProject the_project = gen_manager.get_project();
		GpUser user = gen_manager.get_user();

		//gen_manager.update_job_status(the_project.getId(), user.getId(), user.getUsername(), "loading_base_verbs-GpBaseConfigurationService", "gen_processing");

		//ArrayList<GpVerb> the_verbs = this.verbs_dao.get_all_verbs();
		ArrayList<GpVerb> the_verbs = this.base_verb_dao.get_all_base_verbs();

		//gen_manager.update_job_status(the_project.getId(), user.getId(), user.getUsername(), "base_verbs_loaded-GpBaseConfigurationService", "gen_processing");

		GpArchitypeConfigurations verb_defs = new GpArchitypeConfigurations();

		verb_defs.setName(GpGenConstants.VERB_DEFINITIONS);
		verb_defs.setObject_value(the_verbs);
		derived_configs.put(GpGenConstants.VERB_DEFINITIONS, verb_defs);

	}

	private void handle_base_authorizations(
			HashMap<String,GpArchitypeConfigurations> derived_configs, IGpGenManager gen_manager) throws Exception{

		GpProject the_project = gen_manager.get_project();
		GpUser user = gen_manager.get_user();

		//gen_manager.update_job_status(the_project.getId(), user.getId(), user.getUsername(), "loading_authorizations-GpBaseConfigurationService", "gen_processing");

		ArrayList<GpAuthorization> the_auths = this.auth_dao
												.get_all_authorizations();

		//gen_manager.update_job_status(the_project.getId(), user.getId(), user.getUsername(), "authorizations_loaded-GpBaseConfigurationService", "gen_processing");

		GpArchitypeConfigurations auths_defs = new GpArchitypeConfigurations();

		auths_defs.setName(GpGenConstants.AUTHORIZATION_DEFINITIONS);
		auths_defs.setObject_value(the_auths);
		derived_configs.put(GpGenConstants.AUTHORIZATION_DEFINITIONS, auths_defs);


	}
	public GenClientDeviceTypesDao getClientDeviceTypesDao() {
		return clientDeviceTypesDao;
	}
	@Resource(name="GenClientDeviceTypesDao")
	public void setClientDeviceTypesDao(GenClientDeviceTypesDao clientDeviceTypesDao) {
		this.clientDeviceTypesDao = clientDeviceTypesDao;
	}
}
