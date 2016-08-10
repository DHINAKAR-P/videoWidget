package com.npb.gp.gen.workers.server.java.spring.jpa;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.workers.GpGenDirectoryWorker;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;

@Component("GpJavaLibsGenWorkerForJpa")
public class GpJavaLibsGenWorkerForJpa extends GpGenJavaServerSpringBaseWorker{
	
	private GpGenDirectoryWorker directory_worker;
	
	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}
	@Resource(name="GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}
	
	public void move_libs() throws Exception{
			
			//System.err.println("$$ common libs worker of JPA works $$ ");
			
			String base_directory2 = this.base_configs.get("root_code_template_location").getValue() + this.file_separator + "server" + this.file_separator + "java" + this.file_separator + "lib";
					
			Path web_inf_path = (Path) this.derived_configs
					.get(GpGenConstants.WEB_INF_PATH).getObject_value();
			
			String directory_libs = web_inf_path.toString() + this.file_separator + "lib";
			
			this.directory_worker.copy_directory(new File(base_directory2), new File(directory_libs));
			//LOCAL CALL
			this.move_libs_jpa();
	}
	
	@Override
	public void prep_derived_values(GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs)
			throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
	}
	
	public void move_libs_jpa() throws Exception{
		
		//System.err.println("$$ JPA libs worker works $$");
		
		Path web_inf_path = (Path) this.derived_configs
				.get(GpGenConstants.WEB_INF_PATH).getObject_value();
		
		String directory_libs = web_inf_path.toString() + this.file_separator + "lib";
		
		String base_directory_jpa_source = this.base_configs.get("root_code_template_location").getValue() + this.file_separator + "server" + this.file_separator + "java" + this.file_separator + "jpa";
		
		this.directory_worker.copy_directory(new File(base_directory_jpa_source), new File(directory_libs));
	
	}
}
