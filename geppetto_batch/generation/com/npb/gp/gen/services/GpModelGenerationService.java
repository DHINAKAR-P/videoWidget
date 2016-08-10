package com.npb.gp.gen.services;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.interfaces.services.IGpBaseModelGenService;
import com.npb.gp.gen.workers.java.GpJavaServerDomainGenWorker;
/**
 * 
 * @author Dan Castillo
 * Date Created: 06/21/2014</br>
 * @since .01</p> 
 * 
 * Handles generation of Domain models</p> 
 */
@Service("GpModelGenerationService")
public class GpModelGenerationService extends GpBaseGenerationService implements
															IGpBaseModelGenService {

	private GpJavaServerDomainGenWorker java_server_worker;
	
	
	
	
	public GpJavaServerDomainGenWorker getJava_server_worker() {
		return java_server_worker;
	}
	@Resource(name="GpJavaServerDomainGenWorker")
	public void setJava_server_worker(GpJavaServerDomainGenWorker java_server_worker) {
		this.java_server_worker = java_server_worker;
	}

	@Override
	public HashMap<String, GpArchitypeConfigurations> get_generation_configurations()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		
		//How do you know its Java? ==> YOU MUST READ THE PROJECT TABLES!
		//this.java_server_worker.generate_code(gen_manager.get_project(), 
		//		gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		//this.model_worker.generate_code(gen_manager.get_project(), configs, configs);

	}

	
}
