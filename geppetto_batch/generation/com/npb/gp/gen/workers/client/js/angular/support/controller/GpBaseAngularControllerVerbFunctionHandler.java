package com.npb.gp.gen.workers.client.js.angular.support.controller;

import javax.annotation.Resource;

import com.npb.gp.GpBatchGen;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;
import com.npb.gp.gen.workers.client.js.angular.GpAngularControllerGenWorker;

/**
 * 
 * @author Dan Castillo
 * Date Created: 03/14/2014</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate the code in the functions,</br>
 * based on activity verbs utilizing the micro_flows as a guide</p>
 */

public class GpBaseAngularControllerVerbFunctionHandler {
	
	private GpAngularControllerGenWorker the_worker;
	private GpGenMicroFlowDao micro_flow_dao;
	public GpAngularControllerGenDto the_dto =  new GpAngularControllerGenDto();
	
	public GpAngularControllerGenWorker getThe_worker() {
		return the_worker;
	}

	@Resource(name="GpAngularControllerGenWorker")
	public void setThe_worker(GpAngularControllerGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public GpGenMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}
	@Resource(name="GpGenMicroFlowDao")
	public void setMicro_flow_dao(GpGenMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}
	
	public void get_function_signiture(GpVerb verb) throws Exception{}	
	
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception{}
	
	public void gp_validate(GpVerb verb) throws Exception{}		
	
	public void gp_server_post(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub

		//String serverName = GpBatchGen.getManager().get_project().getName();
		this.the_dto.gp_server_post_code = "//this is where the post code goes" + "\n";
		
		

	}
	public void gp_takePhoto(GpVerb verb, GpActivity activity) throws Exception {
		this.the_dto.gp_take_photo = "//this is where the post code goes" + "\n";
	}
	
	public void gp_display_server_response(GpVerb verb) throws Exception{}
	
	public void gp_confirm(GpVerb verb) throws Exception{}
	
	public void gp_transition(GpVerb verb) throws Exception{}
	
	public void gp_server_response(GpVerb verb) throws Exception {
		this.the_dto.gp_server_response_code = "//this is where the server response code goes";
				
	}
		
	public void gp_end(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_end_code = "//this is where the end code goes \n";
		
		this.the_dto.gp_end_code += "return deferred.promise; \n";

	}
	public void gp_video_recorder(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_video_recorder = "//this is where the post code goes" + "\n";
		
	}
	

}
