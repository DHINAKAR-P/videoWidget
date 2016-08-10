package com.npb.gp.gen.workers.server.java.springboot.support.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootControllerGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 *        Contains the logic to generate the code in the functions,</br> based
 *        on activity verbs utilizing the micro_flows as a guide</p>
 */

public class GpBaseSpringBootControllerVerbFunctionHandler {

	private GpSpringBootControllerGenWorker the_worker;
	private GpGenMicroFlowDao micro_flow_dao;
	public GpControllerSpringBootVerbGenInfo the_dto = new GpControllerSpringBootVerbGenInfo();

	public GpSpringBootControllerGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpSpringBootControllerGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpGenMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}

	@Resource(name = "GpGenMicroFlowDao")
	public void setMicro_flow_dao(GpGenMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}

	public void get_function_signiture(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
	
	}

	public void gp_start(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
	}

	public void gp_service_call(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
	}

	public void gp_return(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods)throws Exception {
		
		this.the_dto.gp_server_post_code = "//this is where the post code goes"	+ "\n";
	}

	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes \n";
		this.the_dto.gp_end_code += "return deferred.promise; \n";
	}

	public void gp_declare_noun(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods)
			throws Exception {
	}

}
