package com.npb.gp.gen.workers.gradle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.constants.GpDevLanguages;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.npb.gp.gen.workers.GpGenDirectoryWorker;

/**
 * Date Created: 15/09/2015
 * @author Reinaldo
 *	Generates the files used by gradle
 *
 *		  Modified Date: 09/28/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *
 */
@Component("GpGradleGenWorker")
public class GpGradleGenWorker extends GpGenBaseWorker {

	private Path gradle_base_path;
	private Path gradle_gen_path;
	private Path template_group_path;

	private GpGenDirectoryWorker directory_worker;

	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}
	@Resource(name="GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}


	public void generate_gradle_scripts(GpUser user, IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gen_gradle_script-GpGradleGenWorker", "gen_processing");

		this.directory_worker.copy_directory(gradle_base_path.toFile(), gradle_gen_path.toFile());
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gradle_script_genrated-GpGradleGenWorker", "gen_processing");


		handle_gradle_property_gen(user, gen_manager);
		handle_upload_apk_gen(user, gen_manager);
		handle_ios_gradle_property_gen(user, gen_manager);
		handle_ios_generate_sh_file(gen_manager);
		handle_exec_sh_file(gen_manager);
	}


	public void handle_gradle_property_gen(GpUser user, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gen_gradle_properties-GpGradleGenWorker", "gen_processing");

		STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
		ST st = webxmlGroup.getInstanceOf("gradle_properties_file_v_1");

		String the_path_string = this.gradle_gen_path.toString() + this.file_separator + "gradle.properties";

		String project_name = the_project.getName() + "_" + gen_manager.get_user().getId();
		st.add("project_name", project_name);
		st.add("web_archive_name", project_name + ".war");
		st.add("web_archive_version", "0.0.1-SNAPSHOT");//need to change
		st.add("email_receiver", user.getUsername());
		st.add("working_dir_name", project_name + "/" + project_name + "_android" );
		st.add("android_proj_name", project_name + "_android");
		st.add("desti_dir_name", project_name + "_android/www");
		st.add("sharing_user", user.getUsername());
		st.add("ios_project_dir_path", project_name);
	    st.add("github_username", base_configs.get("GeppettoGitHubUsername").getValue());
        st.add("github_password", base_configs.get("GeppettoGitHubPassword").getValue());
		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gradle_properties_genrated-GpGradleGenWorker", "gen_processing");
	}

public void handle_upload_apk_gen(GpUser user, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gen_gradle_properties-GpGradleGenWorker", "gen_processing");

		STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
		ST st = webxmlGroup.getInstanceOf("upload_apk_file_v_1");

		String the_path_string = this.gradle_gen_path.toString() + this.file_separator + "uploadAPK.sh";

		String project_name = the_project.getName() + "_" + gen_manager.get_user().getId();
		st.add("project_name", "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + "android" + this.file_separator + project_name);
		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gradle_properties_genrated-GpGradleGenWorker", "gen_processing");
	}


	public void handle_ios_gradle_property_gen(GpUser user, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "gen_ios_gradle_properties-GpGradleGenWorker", "gen_processing");

		STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
		ST st = webxmlGroup.getInstanceOf("gradle_ios_properties_file_v_1");

		String the_path_string = this.gradle_gen_path.toString() + file_separator + "ios_build" + file_separator + "gradle.properties";

		String project_name = the_project.getName() + "_" + gen_manager.get_user().getId();
		String project_name_without_special_characters = the_project.getName() + gen_manager.get_user().getId();
		project_name_without_special_characters = project_name_without_special_characters.replaceAll("[^a-zA-Z0-9]+","");
		st.add("project_name", project_name);
		st.add("project_name_no_sc", project_name_without_special_characters);
		st.add("desti_dir_name", project_name + "/www");
		st.add("sharing_user", user.getUsername());

		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);

		//gen_manager.update_job_status(project_id, user.getId(), user.getUsername(), "ios_gradle_properties_genrated-GpGradleGenWorker", "gen_processing");
	}

	public void handle_ios_generate_sh_file(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_generate_sh_file-GpGradleGenWorker", "gen_processing");

		STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
		ST st = webxmlGroup.getInstanceOf("generate_sh_file_v_1");

		String the_path_string = this.gradle_gen_path.toString() + file_separator + "ios_build" + file_separator + "generate.sh";

		String project_name = the_project.getName() + "_" + gen_manager.get_user().getId();

		st.add("project_name", project_name);

		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);

		//gen_manager.update_job_status(project_id, user_id, username, "ios_generate_sh_file_genrated-GpGradleGenWorker", "gen_processing");
	}

	public void handle_exec_sh_file(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_generate_sh_file-GpGradleGenWorker", "gen_processing");

		STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
		ST st = webxmlGroup.getInstanceOf("exec_sh_file_v_1");

		String the_path_string = this.gradle_gen_path.toString() + file_separator + "ios_build" + file_separator + "exec.sh";

		String project_name = the_project.getName() + "_" + gen_manager.get_user().getId();

		st.add("project_name", project_name);

		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);

		//gen_manager.update_job_status(project_id, user_id, username, "ios_generate_sh_file_genrated-GpGradleGenWorker", "gen_processing");
	}


	@Override
	public void prep_derived_values(GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs)
			throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
	}


	private void set_up_paths_and_templates() {

		this.gradle_base_path = Paths.get(this.base_configs.get("root_code_template_location").getValue() + this.file_separator + "gradle" + this.file_separator + "BuildScript");

		this.gradle_gen_path = Paths.get(this.base_configs.get("base_generation_directory").getValue()
				+ this.file_separator + this.the_project.getName() + "_" + this.the_project.getCreatedby());

		String root_code_template_location = this.base_configs.get("root_code_template_location").getValue();

		this.template_group_path = Paths.get(root_code_template_location + this.file_separator + "server" + this.file_separator + "gradle");
	}
}
