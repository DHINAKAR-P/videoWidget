package com.npb.gp.gen.workers.build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenBaseWorker;
/**
 *
 * @author Adonay
 *	Run the gradle script for deploying the apps to tomcat and Ionic.
 * 	For a server with Java + Spring and a client with Javascript + Angular
 */
@Component("GpBuildJavaSpringWorker")
public class GpBuildJavaSpringWorker extends GpGenBaseWorker{

	public void start_gradle_build_script(IGpGenManager gen_manager) throws Exception{
		GpProject the_project = gen_manager.get_project();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		String s, err = null;
        Process p;
        String app_link = "http://45.55.156.148:8080/" + the_project.getName() + "_" + user_id;
        try {
        	System.out.println("Running gradle script for deployJavaSpringDesktopApp");
        	String command = "gradle -q deployJavaSpringDesktopApp";
        	String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
            p = Runtime.getRuntime().exec(command,null,new File(generate_code_path + "/"+the_project.getName()+"_"+the_project.getCreatedby()));
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
            	//gen_manager.update_job_status(project_id, user_id, username, "Deploying backend and desktop code...", "gen_processing");
            	System.out.println("" + s);
            	if (s.contains("Backend Java Spring and Desktop code deployed!")) {
            		//gen_manager.update_job_status(project_id, user_id, username, "Backend and Desktop code deployed!", "gen_processing");
            	}
            }

            p.waitFor();
            if(p.exitValue() == 0) {
            	System.out.println ("Gradle script correctly executed for java spring");
            	//gen_manager.update_job_status(project_id, user_id, username, "Backend done!" + app_link, "gen_processed");
            } else {
            	System.out.println ("There was an error running the gradle script for java spring");
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
            	//gen_manager.update_job_status(project_id, user_id, username, "gen_error-GpActivityGenService", err);
            	p.destroy();
            	throw new Exception("Something broke");
            }
        } catch (Exception e) {
        	err = e.toString();
        	e.printStackTrace();
        	System.out.println ("There was an error running the gradle script");
        	//gen_manager.update_job_status(project_id, user_id, username, "gen_error-GpActivityGenService", err);
        	throw new Exception("Something broke");
        }
	}

}
