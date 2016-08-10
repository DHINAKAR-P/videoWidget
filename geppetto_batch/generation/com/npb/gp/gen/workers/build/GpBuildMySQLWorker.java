package com.npb.gp.gen.workers.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.DockerInfo;
import com.npb.gp.gen.json.mappers.dockerinfo.DockerImage;

@Component("GpBuildMySQLWorker")
public class GpBuildMySQLWorker {
	public void start_gradle_build_script(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		
		String docker_json = gen_manager.get_user().getDocker_json();
		if(docker_json != null){
			JSONArray json_array = new JSONArray(docker_json);
			String mysql_path = "";
			String cont_name = "";
			for(int i=0;i<json_array.length();i++){
				JSONObject json = json_array.getJSONObject(i);
				cont_name = json.getString("contname");
				if(cont_name.startsWith("mysql")){
					mysql_path = json.getString("userdir");
					break;
				}
			}
			String s, err = null;
	        Process p;
	        try {      	
	        	System.out.println("Running gradle script for moving sql scripts");
	        	String command = "gradle -b mysql.gradle deployMySqlScripts -P mysqlFolder=" + mysql_path + " "
	        					+ "-P cont_name="+ cont_name;
	        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
	            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path + "/" +the_project.getName()+"_"+the_project.getCreatedby()));	            
	            BufferedReader br = new BufferedReader(
	                new InputStreamReader(p.getInputStream()));
	            while ((s = br.readLine()) != null) {
	            	System.out.println("" + s);
	            	if (s.contains("Scripts moved!")) {
	            		
	            	} 
	            }
	            p.waitFor();
	            if(p.exitValue() == 0) { 
	            	System.out.println ("Gradle script correctly executed for moving mysql scripts");
	            	gen_manager.update_job_status(project_id, user_id, username, "gen_build_mysql","Mysql build done!","");
	            } else {
	            	System.out.println ("There was an error running the gradle script for moving mysql scripts");
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
	        	gen_manager.update_job_status(project_id, user_id, username, "gen_error","", err);
	        	throw new Exception("Something broke");
	        }
		}
		else {
			throw new Exception("Docker json is not present for user");
		}
	}
}
