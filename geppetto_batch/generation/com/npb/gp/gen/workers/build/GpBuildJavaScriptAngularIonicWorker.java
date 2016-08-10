package com.npb.gp.gen.workers.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.post.build.GpBuildService;
import com.npb.gp.gen.workers.GpGenBaseWorker;

@Component("GpBuildJavaScriptAngularIonicWorker")
public class GpBuildJavaScriptAngularIonicWorker extends GpGenBaseWorker{

	private GpBuildService the_service;
	private Path template_path_congig_xml;
	private Path template_path_package_json;
	private Path template_path_ionic_project;
	private Path template_path_project_desc;
	private Path template_path_git_ignore;
	private String project_name_without_special_characters;
	private Path template_path_ios_data_json;
	private List<String> cordova_plugins = new ArrayList<>();
	public void start_gradle_build_script(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		String s, err = null;
        Process p;
        try {
        	System.out.println("Running gradle script for ionic apps");
        	String command = "gradle -b ionic_apps.gradle deployIonicApss";
        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path + "/"+the_project.getName()+"_"+the_project.getCreatedby()));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	System.out.println("" + s);
            }

            p.waitFor();
            if(p.exitValue() == 0) {
            	System.out.println ("Gradle script correctly executed for ionic apps");
            	gen_manager.update_job_status(project_id, user_id, username, "gen_ionic_finished", "gen_processed","");
            } else {
            	System.out.println ("There was an error running the gradle script for ionic apps");
            	InputStream error = p.getErrorStream();
            	BufferedReader reader = new BufferedReader(new InputStreamReader(error));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                System.out.println(out.toString());   //Prints the string content read from input stream
                reader.close();
                err = out.toString();
            	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
            	p.destroy();
            	throw new Exception("Something broke");
            }
        } catch (Exception e) {
        	err = e.toString();
        	e.printStackTrace();
        	System.out.println ("There was an error running the gradle script for ionic apps");
        	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
        	throw new Exception("Something broke");
        }
	}
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		// TODO Auto-generated method stub
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_template_path();
	}

	private void set_template_path() {
		String template_name_package_json = "package_json.stg";
		String template_name_config_xml = "config_xml.stg";
		String template_name_ionic_project = "ionic_project.stg";
		String template_name_project_desc = "project_desc.stg";
		String template_name_git_ignore = "git_ignore.stg";
		String template_name_ios_data_json = "ios_data_json.stg";
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String gradle_ecliplse_project_desktop_path = root_code_template_location
				+ this.file_separator + "project_generation"
				+ this.file_separator + "thym"
				+ this.file_separator + "templates";
		this.template_path_congig_xml =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_config_xml);
		this.template_path_ionic_project =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_ionic_project);
		this.template_path_package_json =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_package_json);
		this.template_path_project_desc =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_project_desc);
		this.template_path_git_ignore = Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_git_ignore);
		this.template_path_ios_data_json = Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name_ios_data_json);
	}
	
	public void setThe_service(GpBuildService the_service) {
		this.the_service = the_service;
	}

	public void build_ionic_apps(IGpGenManager gen_manager) throws Exception {
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		gen_manager.update_job_status(project_id, user_id, username, "gen_mobile", "Building Mobile apps","");
		
		this.create_project_folder(gen_manager);
		this.generate_template_files(gen_manager);
		this.start_gradle_build_script(gen_manager);
	}

	private void generate_template_files(IGpGenManager gen_manager) throws Exception {
		String app_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		this.project_name_without_special_characters = app_name.replaceAll("[^a-zA-Z0-9]+","");
		if(the_service.getActivity_service().getClient_angular_gen_service().getDirectory_gen_worker().android_created){
			this.generate_config_xml(gen_manager,app_name,"android");
			this.generate_package_json(gen_manager,app_name,"android");
			this.generate_ionic_project(gen_manager,app_name,"android");
			this.generate_project_des(gen_manager,app_name,"android");
			this.generate_git_ignore(gen_manager,app_name,"android");
		}
		if(the_service.getActivity_service().getClient_angular_gen_service().getDirectory_gen_worker().ios_created){
			this.generate_config_xml(gen_manager,app_name,"ios");
			this.generate_package_json(gen_manager,app_name,"ios");
			this.generate_ionic_project(gen_manager,app_name,"ios");
			this.generate_project_des(gen_manager,app_name,"ios");
			this.generate_git_ignore(gen_manager,app_name,"ios");
			this.generate_data_json_file(gen_manager,app_name,"ios");
		}
	}

	private void generate_data_json_file(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = "data";
		String file_extension = ".json";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_ios_data_json, "output");
		st.add("app_name", app_name);
		st.add("app_name_no_sc", this.project_name_without_special_characters);
		st.add("email", gen_manager.get_user().getUsername());
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_git_ignore(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = ".gitignore";
		String file_extension = "";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_git_ignore, "output");
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_project_des(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = ".project";
		String file_extension = "";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_project_desc, "output");
		st.add("app_name", app_name);
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_ionic_project(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = "ionic";
		String file_extension = ".project";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_ionic_project, "output");
		st.add("app_name", app_name);
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_package_json(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = "package";
		String file_extension = ".json";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_package_json, "output");
		List<GpModuleProperties> modules = this.the_service.getActivity_service().getServer_java_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();
		
		
		for(GpModuleProperties module : modules){
			String plugins_cordova[] = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info().getCordova_plugins();
			if(plugins_cordova != null){
				for(int i = 0 ;i < plugins_cordova.length; i++){
					if(!cordova_plugins.contains(plugins_cordova[i]))
						cordova_plugins.add(plugins_cordova[i]);
				}
			}
		}
		st.add("app_name", app_name);
		st.add("cordova_plugins", cordova_plugins);
		if(platform.equals("ios"))
			st.add("ios", true);
		else
			st.add("ios", false);
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_config_xml(IGpGenManager gen_manager, String app_name, String platform) throws Exception {
		String file_name = "config";
		String file_extension = ".xml";
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_congig_xml, "output");
		st.add("app_name", app_name);
		if(platform.equals("ios")){
			st.add("ios", true);
			st.add("app_name_no_sc", project_name_without_special_characters);
		}
		else{
			st.add("ios", false);
			st.add("app_name_no_sc", app_name);
		}
		String the_path_string = base_generation_directory + this.file_separator + app_name + this.file_separator + "projects" + this.file_separator + "mobile" + this.file_separator + "thym" + this.file_separator + platform + this.file_separator + app_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void create_project_folder(IGpGenManager gen_manager) throws Exception {
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		String s, err = null;
        Process p;
        try {
        	System.out.println("Running gradle script for creating ionic projects");
        	String root_code_template_location = this.base_configs
    				.get("root_code_template_location").getValue();
    		String template_ionic_project_files = root_code_template_location
    				+ this.file_separator + "project_generation"
    				+ this.file_separator + "thym"
    				+ this.file_separator + "code";
        	String command = "gradle -b ionic_apps.gradle createIonicProjects -P project_template_folder=" + template_ionic_project_files;
        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path + "/"+the_project.getName()+"_"+the_project.getCreatedby()));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	System.out.println("" + s);
            }

            p.waitFor();
            if(p.exitValue() == 0) {
            	System.out.println ("Gradle script correctly executed for project gen ionic apps");
            } else {
            	System.out.println ("There was an error running the gradle script for ionic apps");
            	InputStream error = p.getErrorStream();
            	BufferedReader reader = new BufferedReader(new InputStreamReader(error));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                System.out.println(out.toString());   //Prints the string content read from input stream
                reader.close();
                err = out.toString();
            	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
            	p.destroy();
            	throw new Exception("Something broke");
            }
        } catch (Exception e) {
        	err = e.toString();
        	e.printStackTrace();
        	System.out.println ("There was an error running the gradle script for ionic apps");
        	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
        	throw new Exception("Something broke");
        }
	}
	
	public void add_plugin_cordova(String cordova_plugin){
		cordova_plugins.add(cordova_plugin);
	}

}
