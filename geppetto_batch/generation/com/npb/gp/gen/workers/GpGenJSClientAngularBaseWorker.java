package com.npb.gp.gen.workers;

import com.npb.gp.gen.services.client.angular.GpClientAngularJSGenService;

/**
 * 
 * @author Dan Castillo
 * Date Created: 02/04/2015</br>
 * @since .2</p> 
 * 
 * adds JS and Angular specific logic for workers that handle JS client</br> 
 * code</p> 
 */
public class GpGenJSClientAngularBaseWorker extends GpGenBaseWorker {
	
	private GpClientAngularJSGenService gen_service;
	
	/**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	public GpClientAngularJSGenService get_generation_service(){
		return this.gen_service;
	}

	/**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	public void set_generation_service(GpClientAngularJSGenService gen_service){
		this.gen_service = gen_service;
	}


}
