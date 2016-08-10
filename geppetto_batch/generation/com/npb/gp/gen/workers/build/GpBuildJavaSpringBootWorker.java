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

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.DockerInfo;
import com.npb.gp.gen.json.mappers.dockerinfo.DockerImage;
import com.npb.gp.gen.services.post.build.GpBuildService;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Component("GpBuildJavaSpringBootWorker")
public class GpBuildJavaSpringBootWorker extends GpGenBaseWorker{
	private String build_gradle_geppetto_path;
	private Path template_path;
	private Path template_path_build_gradle_for_user;
	private Path template_path_git_ignore;
	private String file_extension = ".gradle";
	private String file_name = "geppetto_build";
	private String template_name;
	private GpBuildService the_service;

	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		// TODO Auto-generated method stub
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_template_path();
	}



	public void buildWar(IGpGenManager gen_manager) throws Exception {
		this.create_project_folder(gen_manager);
		this.generate_build_gradle_file(gen_manager);
		this.start_gradle_build_script(gen_manager);
		this.generate_build_gradle_file_for_user(gen_manager);
		this.generate_git_ignore_file(gen_manager);
	}

	private void generate_git_ignore_file(IGpGenManager gen_manager) throws Exception {
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path_git_ignore, "output");
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		String git_ignore_path = base_generation_directory + this.file_separator + project_name + this.file_separator + "projects" + this.file_separator + "desktop" + this.file_separator + "springboot" + this.file_separator + project_name + this.file_separator + ".gitignore";
		Path test_write_path = Paths.get(git_ignore_path);
		super.write_file(test_write_path, st);
	}



	private void generate_build_gradle_file_for_user(IGpGenManager gen_manager) throws Exception {
		File file = new File(build_gradle_geppetto_path);
		if(file.delete()){
			String base_generation_directory = base_configs.get("base_generation_directory").getValue();
			ST st = super.read_template_group(this.template_path_build_gradle_for_user, "output");
			List<GpModuleProperties> modules = this.the_service.getActivity_service().getServer_java_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();
			List<String> gradle_dependencies_list = new ArrayList<>();
			for(GpModuleProperties module : modules){
				String gradle_dependencies[] = module.getServer_meta_data().getJava().getSpringboot().getGradle_dependencies();
				if(gradle_dependencies != null)
					gradle_dependencies_list.addAll(Arrays.asList(gradle_dependencies));
			}
			st.add("dependencies", gradle_dependencies_list);
			String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
			String the_path_string = base_generation_directory + this.file_separator + project_name + this.file_separator + "projects" + this.file_separator + "desktop" + this.file_separator + "springboot" + this.file_separator + project_name + this.file_separator + "build" + file_extension;
			Path test_write_path = Paths.get(the_path_string);
			super.write_file(test_write_path, st);
		}
		else{
			throw new Exception("Could not delete geppetto_build.gradle");
		}
	}



	public void start_gradle_build_script(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		String docker_json = gen_manager.get_user().getDocker_json();
		if(docker_json != null){
			JSONArray json_array = new JSONArray(docker_json);
			String tomcat_path = "";
			String cont_name = "";
			String server_url = this.base_configs.get("server_host_name").getValue();
			String server_port = "";
			for(int i=0;i<json_array.length();i++){
				JSONObject json = json_array.getJSONObject(i);
				cont_name = json.getString("contname");
				if(cont_name.startsWith("tomcat")){
					tomcat_path = json.getString("userdir");
					server_port = json.getString("port");
					break;
				}
			}
			String s, err = null;
	        Process p;
	        try {
	        	System.out.println("Running gradle script for Java SpringBoot app");
	        	String command = "gradle -b geppetto_build.gradle deployJavaSpringBootDesktopApp "
	        			+ "-P tomcatUserFolder=" + tomcat_path + " "
						+ "-P cont_name="+ cont_name;
	        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue()
	        			+ this.file_separator + project_name
	        			+ this.file_separator + "projects"
	        			+ this.file_separator + "desktop"
	        			+ this.file_separator + "springboot"
	        			+ this.file_separator + project_name;
	            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path));
	            BufferedReader br = new BufferedReader(
	                new InputStreamReader(p.getInputStream()));
	            while ((s = br.readLine()) != null) {
	            	//gen_manager.update_job_status(project_id, user_id, username, "Deploying springboot app...", "gen_processing");
	            	System.out.println("" + s);
	            }

	            p.waitFor();
	            if(p.exitValue() == 0) {
	            	System.out.println ("Gradle script correctly executed for springboot");
	            	String app_url = server_url + ":" + server_port + "/" + project_name;
	            	gen_manager.update_job_status(project_id, user_id, username, "gen_desktop_app",app_url, "");
	            } else {
	            	System.out.println ("There was an error running the gradle script for spring boot app");
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
	            	gen_manager.update_job_status(project_id, user_id, username, "gen_error","", err);
	            	p.destroy();
	            	throw new Exception("Something broke");
	            }
	        } catch (Exception e) {
	        	err = e.toString();
	        	e.printStackTrace();
	        	System.out.println ("There was an error running the gradle script");
	        	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
	        	throw new Exception("Something broke");
	        }
		}
		else {
			throw new Exception("Docker json is not present for user");
		}
	}

	private void generate_build_gradle_file(IGpGenManager gen_manager) throws Exception {
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path, "output");
		List<GpModuleProperties> modules = this.the_service.getActivity_service().getServer_java_spring_boot_gen_service().getNot_default_activity_worker().getModule_properties_list();
		List<String> gradle_dependencies_list = new ArrayList<>();
		for(GpModuleProperties module : modules){
			String gradle_dependencies[] = module.getServer_meta_data().getJava().getSpringboot().getGradle_dependencies();
			if(gradle_dependencies != null)
				gradle_dependencies_list.addAll(Arrays.asList(gradle_dependencies));
		}
		st.add("dependencies", gradle_dependencies_list);
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		build_gradle_geppetto_path = base_generation_directory + this.file_separator + project_name + this.file_separator + "projects" + this.file_separator + "desktop" + this.file_separator + "springboot" + this.file_separator + project_name + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(build_gradle_geppetto_path);
		super.write_file(test_write_path, st);
	}

	private void set_template_path(){
		this.template_name = "build_gradle.stg";
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String gradle_ecliplse_project_desktop_path = root_code_template_location
				+ this.file_separator + "gradle"
				+ this.file_separator + "UserGradleScripts"
				+ this.file_separator + "java"
				+ this.file_separator + "springboot";
		this.template_path =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name);
		this.template_path_build_gradle_for_user = Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + "build_gradle_for_user.stg");
		this.template_path_git_ignore = Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + "git_ignore.stg");
	}

	private void create_project_folder(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		String s, err = null;
        Process p;
        try {
        	System.out.println("Running gradle script for creating eclipse spring boot project");
        	String command = "gradle -b spring_boot_project.gradle cSBP";
        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path + "/"+the_project.getName()+"_"+the_project.getCreatedby()));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	//gen_manager.update_job_status(project_id, user_id, username, "Creating eclipse spring boot project...", "gen_processing");
            	System.out.println("" + s);
            	if (s.contains("SpringBoot project created!")) {
            		//gen_manager.update_job_status(project_id, user_id, username, "SpringBoot project created!", "gen_processing");
            	}
            }

            p.waitFor();
            if(p.exitValue() == 0) {
            	System.out.println ("Gradle script correctly executed for creating eclipse springboot project");
            	//gen_manager.update_job_status(project_id, user_id, username, "SpringBoot project created!", "gen_processed");
            } else {
            	System.out.println ("There was an error running the gradle script for spring boot app");
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
        	System.out.println ("There was an error running the gradle script for spring boot app");
        	gen_manager.update_job_status(project_id, user_id, username, "gen_error", "", err);
        	throw new Exception("Something broke");
        }
	}

	public void setThe_service(GpBuildService the_service) {
		this.the_service = the_service;
	}
}
