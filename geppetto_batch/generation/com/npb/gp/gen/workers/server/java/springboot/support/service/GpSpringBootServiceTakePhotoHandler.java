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
 * action on data: GpTakePhoto</p>
 */

@Component("GpSpringBootServiceTakePhotoHandler")
public class GpSpringBootServiceTakePhotoHandler 
extends GpBaseSpringBootServiceVerbFunctionHandler
						implements IGpSpringBootServiceVerbGenSupport {
	
	private GpServiceSpringBootVerbMethodSignitures verb_signiture_hlpr;
	private List<GpServiceVerbGenInfo> the_implicit_verbs;
	private GpSpringBootServiceUpdateHandler the_update_handler;
	private GpSpringBootServiceCreateHandler the_create_handler;
		
	public GpSpringBootServiceCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	@Resource(name = "GpSpringBootServiceCreateHandler")
	public void setThe_create_handler(GpSpringBootServiceCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}
	public GpSpringBootServiceUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	@Resource(name = "GpSpringBootServiceUpdateHandler")
	public void setThe_update_handler(GpSpringBootServiceUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}
	public void setThe_implicit_verbs(List<GpServiceVerbGenInfo> the_implicit_verbs) {
		this.the_implicit_verbs = the_implicit_verbs;
	}
	public List<GpServiceVerbGenInfo> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
	
	private GpSpringBootServiceGenWorker the_worker;

	public GpSpringBootServiceGenWorker getThe_worker() {
		return the_worker;
	}
	@Resource(name = "GpSpringBootServiceGenWorker")
	public void setThe_worker(GpSpringBootServiceGenWorker the_worker) {
		this.the_worker = the_worker;
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
		GpServiceVerbGenInfo update = the_update_handler.handle_implicit_verb(verb, activity);
		update.verb_action_on_data = GpBaseVerbsConstants.GpUpdate;
		return update;
	}
	
	
}
