package com.npb.gp.gen.workers;

import java.util.ArrayList;
import java.util.HashMap;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;

/**
 * 
 * @author Dan Castillo
 * Date Created: 12/22/2014</br>
 * @since .2</p> 
 * 
 * adds java and spring specific logic for workers that handle Java server</br> 
 *  Spring code</p> 
 */

public class GpGenJavaServerSpringBaseWorker extends GpGenBaseWorker {
	
	private GpServerJavaSpringGenService gen_service;
	private GpServerJavaSpringBootGenService spring_boot_gen_service;
	
	public void set_spring_boot_gen_service(GpServerJavaSpringBootGenService spring_boot_gen_service){
		this.spring_boot_gen_service = spring_boot_gen_service;
	}
	
	public GpServerJavaSpringBootGenService get_spring_boot_gen_service(){
		return this.spring_boot_gen_service;
	}

	/**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	public GpServerJavaSpringGenService get_generation_service(){
		return this.gen_service;
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
	 public String get_data_type_for_activity(GpActivity an_activity)
			 											throws Exception{
		 
		 return "a_type";
		 
		 
	 }

	 /**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */

	 public String get_package_name_for_activity(GpActivity an_activity) 
			 												throws Exception{
		 
		 return "a_name";
		 
		 
	 }


	 /**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */

	 public HashMap<String, GpJavaMethodDescription> 
	 				get_method_signitures(GpActivity an_activity)	throws Exception{
		 
		 
		 return null;
		 
		 
	 }

}
