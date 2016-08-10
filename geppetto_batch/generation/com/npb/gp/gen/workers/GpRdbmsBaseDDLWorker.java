package com.npb.gp.gen.workers;

import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;

/**
 * 
 * @author Dan Castillo
 * Date Created: 01/10/2015</br>
 * @since .2</p> 
 * 
 * Generates the any DDL SQL statements needed by an application </p> 
 */
public class GpRdbmsBaseDDLWorker extends GpGenBaseWorker {
	private GpServerJavaSpringGenService gen_service;
	
	private GpServerJavaSpringBootGenService spring_boot_gen_service;
	
	public void set_spring_boot_generation_service(GpServerJavaSpringBootGenService spring_boot_gen_service){
		this.spring_boot_gen_service = spring_boot_gen_service;
	}
	
	public GpServerJavaSpringBootGenService get_spring_boot_generation_service(){
		return this.spring_boot_gen_service;
	}
	/**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	public void set_generation_service(GpServerJavaSpringGenService gen_service){
		this.gen_service = gen_service;
	}

	/**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	public GpServerJavaSpringGenService get_generation_service(){
		return this.gen_service;
	}

}
