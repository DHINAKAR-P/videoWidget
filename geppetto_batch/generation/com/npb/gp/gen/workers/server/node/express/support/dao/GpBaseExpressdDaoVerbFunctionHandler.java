package com.npb.gp.gen.workers.server.node.express.support.dao;

import javax.annotation.Resource;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressDaoGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 *        Contains the logic to generate the code in the functions,</br> based
 *        on activity verbs utilizing the micro_flows as a guide</p>
 */

public class GpBaseExpressdDaoVerbFunctionHandler {

	private GpExpressDaoGenWorker the_worker;
	private GpGenMicroFlowDao micro_flow_dao;
	public DaoFunctionDescription the_dto = new DaoFunctionDescription();

	

	public GpExpressDaoGenWorker getThe_worker() {
		return the_worker;
	}
	
	@Resource(name = "GpExpressDaoGenWorker")
	public void setThe_worker(GpExpressDaoGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpGenMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}

	@Resource(name = "GpGenMicroFlowDao")
	public void setMicro_flow_dao(GpGenMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}

	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
	
	}

	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
	}

	public void gp_sql_query(GpVerb verb,GpActivity activity) throws Exception {
	}

	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
				
	}

	public void gp_end(GpVerb verb) throws Exception {
		
	}

	public void gp_verb_key(GpVerb verb, GpActivity activity)throws Exception {
	}

}
