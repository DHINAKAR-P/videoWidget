package com.npb.gp.gen.workers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpModuleResource;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.dao.mysql.GpGenModuleDao;
import com.npb.gp.gen.services.GpBaseGenerationService;


/**
 * Date Created: 18/09/2015
 * @author Reinaldo
 *	The purpose of this class is to manage the generation for not_default activities 
 *
 */

@Component("GpNotDefaultActivityGenWorker")
public class GpNotDefaultActivityGenWorker extends GpGenBaseWorker {
	
	private File module_base_directory;
	private File module_final_directory;			
	
	private GpGenDirectoryWorker directory_worker;	
	
	private GpGenModuleDao module_dao;			

	private List<GpModuleProperties> module_properties_list = new ArrayList<GpModuleProperties>();

	public GpGenModuleDao getModule_dao() {
		return module_dao;
	}
	@Resource(name="GpGenModuleDao")
	public void setModule_dao(GpGenModuleDao module_dao) {
		this.module_dao = module_dao;
	}
	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}
	@Resource(name="GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}
	
	public File getModule_base_directory() {
		return module_base_directory;
	}
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.directory_worker.prep_derived_values(the_project, base_configs, derived_configs);
	}
	public void setModule_base_directory(String... module_base_directory) {
		String base_directory = "";
		for (int i = 0; i < module_base_directory.length; i++) {
			String directory = module_base_directory[i];
			if(i < module_base_directory.length -1)
				base_directory += directory + this.file_separator ;
			else
				base_directory += directory;
		}		
		this.module_base_directory = new File(base_directory);
	}

	public File getModule_final_directory() {
		return module_final_directory;
	}
	
	public void setModule_final_directory(String... module_final_directory) {
		String final_directory = "";
		
		for (int i = 0; i < module_final_directory.length; i++) {
			String directory = module_final_directory[i];
			if(i < module_final_directory.length -1)
				final_directory += directory + this.file_separator ;
			else
				final_directory += directory;
		}
		Path path = Paths.get(final_directory);			
		this.module_final_directory = new File(path.toString());
	}
		

	public void import_client_module(GpActivity activity, GpBaseGenerationService gen_service) throws Exception{
		
		GpModule module = module_dao.load_module(activity.getModuleid());						
		
		Path path = Paths.get(module.getBase_location());															
										
		if(gen_service.front_isJavaScriptAngular && gen_service.back_isJavaSpring){
			this.setModule_base_directory(path.toString(), "code", "java", "spring","client");
		}
		if(gen_service.front_isJavaScriptAngular && gen_service.back_isJavaScriptNodeJSExpress){
			//System.out.println("Module node express");
			this.setModule_base_directory(path.toString(), "code", "node", "express","client");
		}
		if(gen_service.front_isJavaScriptAngular && gen_service.back_isJavaSpringBootJpa){
			//System.out.println("Module Springboot");
			this.setModule_base_directory(path.toString(), "code", "java", "springboot","client");
		}
		
		this.directory_worker.handle_module_import(this.module_base_directory, this.module_final_directory);				
				
	}
	
	public void import_server_module(GpActivity activity, GpBaseGenerationService gen_service) throws Exception{
		GpModule module = module_dao.load_module(activity.getModuleid());						
		Path path = Paths.get(module.getBase_location());
		
		File dependencies_file = new File(path + this.file_separator + "dependencies.gcd");
		if(dependencies_file.exists()){
			ObjectMapper mapper = new ObjectMapper();
			GpModuleProperties module_properties = mapper.readValue(dependencies_file, GpModuleProperties.class);
			module_properties.setActivity_name(activity.getName());
			module_properties_list.add(module_properties);
		}
		
		if(gen_service.back_isJavaSpring){
			return;
		}
		if(gen_service.back_isJavaScriptNodeJSExpress){
			this.setModule_base_directory(path.toString(), "code", "node", "express", "server");
		}
		if(gen_service.back_isJavaSpringBootJpa){
			this.setModule_base_directory(path.toString(), "code", "java", "springboot","server");
		}
		//System.out.println(this.module_base_directory.toString());
		//System.out.println(this.module_final_directory.toString());
		this.directory_worker.handle_module_import(this.module_base_directory, this.module_final_directory);
	}
	public List<GpModuleProperties> getModule_properties_list() {
		return module_properties_list;
	}
	public void setModule_properties_list(
			List<GpModuleProperties> module_properties_list) {
		this.module_properties_list = module_properties_list;
	}

}