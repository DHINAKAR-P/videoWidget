package com.npb.gp.gen.workers.server.java.spring;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.npb.gp.dao.mysql.GpAuthorization_DefinitionsDao;
import com.npb.gp.dao.mysql.GpBaseVerbsDao;
import com.npb.gp.dao.mysql.GpTechPropertyDao;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GenClientDeviceTypesDao;
import com.npb.gp.gen.dao.mysql.GpGenBaseConfigDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.gradle.GpGradleGenWorker;


/**
 *
 * @author Kumaresan Perumal
 * Date Created: 03/21/2016</br>
 * @since .01</p>
 *
 * loads and manipulates the initial set of information needed to carry out</br>
 * the application generation</p>
 *
 *
 */

@Component("GpLegacyDirectoryGenWorker")
public class GpLegacyDirectoryGenWorker extends GpGenJavaServerSpringBaseWorker{

	private Log log = LogFactory.getLog(getClass());


	private String file_separator = System.getProperty("file.separator");


	public void handle_java_devlang_server_configs(
							GpProject the_project,
							Path root_server_path,
							HashMap<String,GpArchitypeConfigurations> base_configs,
							HashMap<String,GpArchitypeConfigurations> derived_configs, IGpGenManager gen_manager) throws Exception{
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_src_directory-GpBaseConfigurationService", "gen_processing");
		Path source_path = Paths.get(root_server_path.toString() + this.file_separator + "src");
		Files.createDirectory(source_path);

		//gen_manager.update_job_status(project_id, user_id, username, "src_directory_generated-GpBaseConfigurationService", "gen_processing");

		//this code expects the entity to be domain name[name].[extension]
		//an example would be familyhomebuyers.com
		String package_1 = "geppetto";
		//gen_manager.update_job_status(project_id, user_id, username, "gen_base_source_path_directory-GpBaseConfigurationService", "gen_processing");
		Path base_source_path = Paths.get(source_path.toString() + this.file_separator + package_1);
		Files.createDirectory(base_source_path);
		//gen_manager.update_job_status(project_id, user_id, username, "base_source_path_directory_genrated-GpBaseConfigurationService", "gen_processing");

		String package_2 = the_project.getName();
		//gen_manager.update_job_status(project_id, user_id, username, "gen_entity_name_path_directory-GpBaseConfigurationService", "gen_processing");
		Path entity_name_path = Paths.get(base_source_path.toString() + this.file_separator + package_2);
		Files.createDirectory(entity_name_path);
		//gen_manager.update_job_status(project_id, user_id, username, "entity_name_path_directory_genrated-GpBaseConfigurationService", "gen_processing");

		Path src_project_name_path = Paths.get(entity_name_path.toString() + this.file_separator
				+ "app");
		String app_base_package = package_1 + "." + package_2 + "." + "app";

		GpArchitypeConfigurations package_config = new GpArchitypeConfigurations();

		package_config.setName(GpGenConstants.SERVER_SOURCE_ROOT_PATH);
		package_config.setValue(app_base_package);
		derived_configs.put(GpGenConstants.APP_BASE_PACKAGE, package_config);


		//gen_manager.update_job_status(project_id, user_id, username, "gen_src_project_name_path_directory-GpBaseConfigurationService", "gen_processing");
		Files.createDirectory(src_project_name_path);
		GpArchitypeConfigurations a_config = new GpArchitypeConfigurations();

		a_config.setName(GpGenConstants.SERVER_SOURCE_ROOT_PATH);
		a_config.setObject_value(src_project_name_path);
		derived_configs.put(GpGenConstants.SERVER_SOURCE_ROOT_PATH, a_config);

		//gen_manager.update_job_status(project_id, user_id, username, "src_project_name_path_directory_generated-GpBaseConfigurationService", "gen_processing");
	}


}
