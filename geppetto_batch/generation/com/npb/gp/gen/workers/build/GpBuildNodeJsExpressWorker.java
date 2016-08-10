package com.npb.gp.gen.workers.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenBaseWorker;

@Component("GpBuildNodeJsExpressWorker")
public class GpBuildNodeJsExpressWorker extends GpGenBaseWorker{
	
	private String template_name;
	private Path template_path;

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
		this.template_name = "git_ignore.stg";
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String gradle_ecliplse_project_desktop_path = root_code_template_location
				+ this.file_separator + "project_generation"
				+ this.file_separator + "node"
				+ this.file_separator + "express";
		this.template_path =  Paths.get(gradle_ecliplse_project_desktop_path + this.file_separator + template_name);
	}
	
	public void deploy_and_create_project(IGpGenManager gen_manager) throws Exception{
		this.start_gradle_build_script(gen_manager);
		this.generate_git_ignore_file(gen_manager);
	}

	public void start_gradle_build_script(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		String s, err = null;
        Process p;
        String docker_json = gen_manager.get_user().getDocker_json();
        String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
        
		if(docker_json != null){
			JSONArray json_array = new JSONArray(docker_json);
			String tomcat_path = "";
			String cont_name = "";
			String server_url = this.base_configs.get("server_host_name").getValue();
			String server_port = "";
			for(int i=0;i<json_array.length();i++){
				JSONObject json = json_array.getJSONObject(i);
				cont_name = json.getString("contname");
				if(cont_name.startsWith("nodejs")){
					tomcat_path = json.getString("userdir");
					server_port = json.getString("port");
					break;
				}
			}
	        try {
	        	System.out.println("Running gradle script for Node express app");
	        	String command = "gradle -b node_express.gradle deployNodeApp "
	        			+ "-P nodeFolder=" + tomcat_path + " "
						+ "-P cont_name="+ cont_name;
	        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue()
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
	            	System.out.println ("Gradle script correctly executed for node app");
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
	
	private void generate_git_ignore_file(IGpGenManager gen_manager) throws Exception {
		String base_generation_directory = base_configs.get("base_generation_directory").getValue();
		ST st = super.read_template_group(this.template_path, "output");
		String project_name = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();
		String git_ignore_path = base_generation_directory 
				+ this.file_separator + project_name 
				+ this.file_separator + "projects" 
				+ this.file_separator + "desktop" 
				+ this.file_separator + "node"
				+ this.file_separator + "express"
				+ this.file_separator + project_name 
				+ this.file_separator + ".gitignore";
		Path test_write_path = Paths.get(git_ignore_path);
		super.write_file(test_write_path, st);
	}
}
