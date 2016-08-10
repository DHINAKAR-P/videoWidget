package com.npb.gp.gen.workers.server.node.express.support.controller;

import javax.annotation.Resource;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressControllerGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 *        Contains the logic to generate the code in the functions,</br> based
 *        on activity verbs utilizing the micro_flows as a guide</p>
 */

public class GpBaseExpressControllerVerbFunctionHandler {

	private GpExpressControllerGenWorker the_worker;
	private GpGenMicroFlowDao micro_flow_dao;
	public ControllerFunctionDescription the_dto = new ControllerFunctionDescription();

	

	public GpExpressControllerGenWorker getThe_worker() {
		return the_worker;
	}
	
	@Resource(name = "GpExpressControllerGenWorker")
	public void setThe_worker(GpExpressControllerGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpGenMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}

	@Resource(name = "GpGenMicroFlowDao")
	public void setMicro_flow_dao(GpGenMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}

	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {}

	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {}

	public void gp_service_call(GpVerb verb,GpActivity activity) throws Exception {}

	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {}

	public void gp_end(GpVerb verb) throws Exception {}

	public void gp_variable_statement(GpVerb verb, GpActivity activity) throws Exception {}
	

	

}
