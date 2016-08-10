package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import javax.annotation.Resource;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/29/2016</br>
 * @since 1.0</p> 
 * 
 *        Contains the logic to generate the code in the functions,</br> based
 *        on activity verbs utilizing the micro_flows as a guide</p>
 */

public class GpBaseSpringBootDaoVerbFunctionHandler {

	private GpSpringBootJpaDaoGenWorker the_worker;
	private GpGenMicroFlowDao micro_flow_dao;
	public GpSpringBootDaoVerbGenInfo the_dto = new GpSpringBootDaoVerbGenInfo();

	
	public GpSpringBootJpaDaoGenWorker getThe_worker() {
		return the_worker;
	}
	
	public void setThe_worker(GpSpringBootJpaDaoGenWorker the_worker) {
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

	public void gp_jpa_query(GpVerb verb,GpActivity activity) throws Exception {
	}
	
	public void gp_execute_st(GpVerb verb,GpActivity activity) throws Exception {
	}

	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		
		this.the_dto.gp_server_post_code = "//this is where the post code goes"	+ "\n";
	}

	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes \n";
		this.the_dto.gp_end_code += "return deferred.promise; \n";
	}

	public void gp_declare_noun(GpVerb verb, GpActivity activity) throws Exception {
	}
	public void gp_exception(GpVerb verb, GpActivity activity) throws Exception {
	}
	public void gp_array_list_declare(GpVerb verb, GpActivity activity)	throws Exception {
	}

}
