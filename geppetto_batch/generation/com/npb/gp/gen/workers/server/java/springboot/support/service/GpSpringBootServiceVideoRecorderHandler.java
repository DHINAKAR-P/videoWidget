package com.npb.gp.gen.workers.server.java.springboot.support.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.service.IGpSpringBootServiceVerbGenSupport;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootServiceGenWorker;

/**
 * 
 * @author Dhinakar P
 * Date Created: 02/28/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generates the function that handles the verb</br>
 * action on data: GpRecordVideo</p>
 */

@Component("GpSpringBootServiceVideoRecorderHandler")
public class GpSpringBootServiceVideoRecorderHandler 
extends GpBaseSpringBootServiceVerbFunctionHandler
						implements IGpSpringBootServiceVerbGenSupport {
	
	private GpServiceSpringBootVerbMethodSignitures verb_signiture_hlpr;
	private List<GpServiceVerbGenInfo> the_implicit_verbs;
	private GpSpringBootServiceUpdateHandler the_update_handler;
	private GpSpringBootServiceCreateHandler the_create_handler;
	
	String function_name = "videoRecorder";
	private GpSpringBootServiceGenWorker the_worker;

	public GpSpringBootServiceGenWorker getThe_worker() {
		return the_worker;
	}
	@Resource(name = "GpSpringBootServiceGenWorker")
	public void setThe_worker(GpSpringBootServiceGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	public GpServiceSpringBootVerbMethodSignitures getVerb_signiture_hlpr() {
		return verb_signiture_hlpr;
	}

	@Resource(name = "GpServiceSpringBootVerbMethodSignitures")
	public void setVerb_signiture_hlpr(
			GpServiceSpringBootVerbMethodSignitures verb_signiture_hlpr) {
		this.verb_signiture_hlpr = verb_signiture_hlpr;
	}
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	@Override
	public GpServiceVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {
		return null;

	}
	
	public void handle_implicit_verbs(GpVerb verb, GpActivity activity) throws Exception {
		the_implicit_verbs = new ArrayList<>();
		//search for update implicit
		the_implicit_verbs.add(this.gp_update(verb, activity));
		the_implicit_verbs.add(this.gp_create(verb, activity));
		//
	}

	private GpServiceVerbGenInfo gp_create(GpVerb verb, GpActivity activity) {
		GpServiceVerbGenInfo create = the_create_handler.handle_implicit_verb(verb, activity);
		create.verb_action_on_data = GpBaseVerbsConstants.GpCreate;
		return create;
	}
	private GpServiceVerbGenInfo gp_update(GpVerb verb, GpActivity activity) throws Exception {
		GpServiceVerbGenInfo search_for_update = the_update_handler.handle_implicit_verb(verb, activity);
		search_for_update.verb_action_on_data = GpBaseVerbsConstants.GpUpdate;
		return search_for_update;
	}
	public GpSpringBootServiceUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	@Resource(name = "GpSpringBootServiceUpdateHandler")
	public void setThe_update_handler(GpSpringBootServiceUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}
	
	public GpSpringBootServiceCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	@Resource(name = "GpSpringBootServiceCreateHandler")
	public void setThe_create_handler(GpSpringBootServiceCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}
	
	public void setThe_implicit_verbs(List<GpServiceVerbGenInfo> the_implicit_verbs) {
		this.the_implicit_verbs = the_implicit_verbs;
	}
	public List<GpServiceVerbGenInfo> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
}
