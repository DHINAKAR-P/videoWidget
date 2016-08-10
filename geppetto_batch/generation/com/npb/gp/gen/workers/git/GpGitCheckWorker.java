package com.npb.gp.gen.workers.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenBaseWorker;

@Component("GpGitCheckWorker")
public class GpGitCheckWorker extends GpGenBaseWorker{

	public void start_git_check(IGpGenManager gen_manager) throws Exception {
		gen_manager.update_job_status(gen_manager.get_project().getId(), gen_manager.get_user().getId(), gen_manager.get_user().getUsername(), "gen_github", "gen_processing","");
		System.out.println ("Gradle script for git check started");
		the_project = gen_manager.get_project();
		String generate_code_path = gen_manager.getBase_configs().get("base_generation_directory").getValue();
		generate_code_path = generate_code_path + "/"+the_project.getName()+"_"+the_project.getCreatedby();
		Process p;
		String s;
		String command = "gradle -b git_check.gradle cGR";
		p = Runtime.getRuntime().exec(command,null,new File(generate_code_path));
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String url = "";
        while ((s = br.readLine()) != null) {
        	System.out.println("" + s);
        	if(s.toString().startsWith("https"))
        		url = s.toString();
        }
        p.waitFor();
        if(p.exitValue() == 0) {
        	System.out.println ("Gradle script for git check correctly executed");
        	gen_manager.update_job_status(gen_manager.get_project().getId(), gen_manager.get_user().getId(), gen_manager.get_user().getUsername(), "gen_github_finished", url,"");
        }
	}
}
