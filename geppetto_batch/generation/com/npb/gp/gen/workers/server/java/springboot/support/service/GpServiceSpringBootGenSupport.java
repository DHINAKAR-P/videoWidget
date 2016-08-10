package com.npb.gp.gen.workers.server.java.springboot.support.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootServiceGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/28/2016</br>
 * @since 1.0</p> 
 * 
 *        Contains the logic to generate the methods, based on activity
 *        verbs,</br> in a Spring boot based Service- it is designed to be called by
 *        the </br> worker that handles the Spring boot Service generation</p>
 */

@Component("GpServiceSpringBootGenSupport")
public class GpServiceSpringBootGenSupport {
	
	private GpFlowControl the_flow;
	private GpServiceSpringBootVerbMethodSignitures signiture_hlpr;
	/*private GpServiceSpringBootVerbMethodImplementations implementation_hlpr;*/
	private GpSpringBootServiceGenWorker the_worker;
	
	private GpSpringBootServiceGetAllValuesHandler service_get_all_values_handler;
	private GpSpringBootServiceCreateHandler service_create_handler;
	private GpSpringBootServiceUpdateHandler service_update_handler;
	private GpSpringBootServiceDeleteHandler service_delete_handler;
	private GpSpringBootServiceSearchHandler service_search_handler;
	private GpSpringBootServiceSearchForUpdateHandler service_search_for_update_handler;
	private GpSpringBootServiceGetNounByIdHandler service_get_noun_by_id_handler;
	private GpSpringBootServiceGetNounByParentIdHandler service_get_noun_by_parent_id_handler;
	private GpSpringBootServiceDeleteByParentIdHandler service_delete_noun_by_parent_id_handler;
	private GpSpringBootServiceTakePhotoHandler service_take_photo_handler;
	private GpSpringBootServiceVideoRecorderHandler service_video_recorder_handler;

	public ArrayList<GpServiceVerbGenInfo> get_verb_method_implmentation(
			GpActivity activity) throws Exception {
	//	this.implementation_hlpr.setThe_worker(this.the_worker);
		ArrayList<GpServiceVerbGenInfo> implementations = new ArrayList<GpServiceVerbGenInfo>();
		
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb a_verb : the_verbs) {
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)) {
				GpServiceVerbGenInfo impl = this.service_get_all_values_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)) {
				GpServiceVerbGenInfo impl = this.service_get_noun_by_id_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)) {
				GpServiceVerbGenInfo impl = this.service_search_for_update_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate) && !generated_verbs.contains(GpBaseVerbsConstants.GpCreate)) {
				GpServiceVerbGenInfo impl = this.service_create_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
				continue;
			}
	
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)) {
				
				GpServiceVerbGenInfo impl = this.service_update_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				for(GpServiceVerbGenInfo implicit_verb : this.service_update_handler.getThe_implicit_verbs()){
					if(generated_verbs.contains(implicit_verb.verb_action_on_data)){
						implementations.add(implicit_verb);
						generated_verbs.add(implicit_verb.verb_action_on_data);
					}
				}
				
				continue;
			}
	
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && !generated_verbs.contains(GpBaseVerbsConstants.GpDelete)) {
				GpServiceVerbGenInfo impl = this.service_delete_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearch)) {
				GpServiceVerbGenInfo impl = this.service_search_handler
						.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && !generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)) {
				this.service_take_photo_handler.handle_implicit_verbs(a_verb, activity);
				for(GpServiceVerbGenInfo implicit : this.service_take_photo_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						implementations.add(implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && !generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)) {
				this.service_video_recorder_handler.handle_implicit_verbs(a_verb, activity);
				for(GpServiceVerbGenInfo implicit : this.service_video_recorder_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						implementations.add(implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
				continue;
			}

		}
		JSONArray json_parent = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			GpServiceVerbGenInfo impl_get_noun_by_parent_id = this.service_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			implementations.add(impl_get_noun_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			GpServiceVerbGenInfo impl_delete_noun_by_parent_id = this.service_delete_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			implementations.add(impl_delete_noun_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}

		return implementations;
	}

	public HashMap<String, GpServiceVerbGenInfo> get_method_signitures(
			GpActivity activity) throws Exception {

		HashMap<String, GpServiceVerbGenInfo> the_map = new HashMap<String, GpServiceVerbGenInfo>();

		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb a_verb : the_verbs) {
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)) {
				GpServiceVerbGenInfo impl = this.service_get_all_values_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
				continue;
			}

			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)) {
				GpServiceVerbGenInfo impl = this.service_get_noun_by_id_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
				continue;
			}

			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)) {
				GpServiceVerbGenInfo impl = this.service_search_for_update_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
				continue;
			}

			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate) && !generated_verbs.contains(GpBaseVerbsConstants.GpCreate)) {
				GpServiceVerbGenInfo impl = this.service_create_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)) {
				GpServiceVerbGenInfo impl = this.service_update_handler
						.handle_verb(a_verb, activity);
				the_map.put(GpBaseVerbsConstants.GpUpdate, impl);
				for(GpServiceVerbGenInfo implicit : this.service_update_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data, implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				continue;
			}

			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && !generated_verbs.contains(GpBaseVerbsConstants.GpDelete)) {
				GpServiceVerbGenInfo impl = this.service_delete_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
				continue;
			}

			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearch)) {
				GpServiceVerbGenInfo impl = this.service_search_handler
						.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
				continue;
			}
			
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && !generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)) {
				this.service_take_photo_handler.handle_implicit_verbs(a_verb, activity);
				for(GpServiceVerbGenInfo implicit : this.service_take_photo_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data, implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
				continue;
			}
			if (a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && !generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)) {
				this.service_video_recorder_handler.handle_implicit_verbs(a_verb, activity);
				for(GpServiceVerbGenInfo implicit : this.service_video_recorder_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data, implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
				continue;
			}
			
		}
		if(this.the_worker.getRelationships_map() != null && activity.getPrimary_noun() != null){
			JSONArray json_parent = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
			if(json_parent != null){
				GpServiceVerbGenInfo impl_get_noun_by_parent_id = this.service_get_noun_by_parent_id_handler
						.handle_implicit_verb(null, activity);
				the_map.put(GpBaseVerbsConstants.GpGetNounByParentId, impl_get_noun_by_parent_id);
				GpServiceVerbGenInfo impl_delete_noun_by_parent_id = this.service_delete_noun_by_parent_id_handler
						.handle_implicit_verb(null, activity);
				the_map.put(GpBaseVerbsConstants.GpDeleteByParentId, impl_delete_noun_by_parent_id);
			}
		}
		return the_map;
	}
	

	public GpSpringBootServiceGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpSpringBootServiceGenWorker the_worker) {
		this.the_worker = the_worker;
		this.service_get_noun_by_parent_id_handler.setThe_worker(the_worker);
		this.service_get_all_values_handler.setThe_worker(the_worker);
	}

	public GpServiceSpringBootVerbMethodSignitures getSigniture_hlpr() {
		return signiture_hlpr;
	}

	@Resource(name = "GpServiceSpringBootVerbMethodSignitures")
	public void setSigniture_hlpr(GpServiceSpringBootVerbMethodSignitures signiture_hlpr) {
		this.signiture_hlpr = signiture_hlpr;
	}
/*
	public GpServiceSpringBootVerbMethodImplementations getImplementation_hlpr() {
		return implementation_hlpr;
	}

	@Resource(name = "GpServiceSpringBootVerbMethodImplementations")
	public void setImplementation_hlpr(
			GpServiceSpringBootVerbMethodImplementations implementation_hlpr) {
		this.implementation_hlpr = implementation_hlpr;
	}
*/
	public void set_flow(GpFlowControl the_flow) {
		this.the_flow = the_flow;

	}
	public GpSpringBootServiceGetAllValuesHandler getService_get_all_values_handler() {
		return service_get_all_values_handler;
	}
	@Resource(name = "GpSpringBootServiceGetAllValuesHandler")
	public void setService_get_all_values_handler(
			GpSpringBootServiceGetAllValuesHandler service_get_all_values_handler) {
		this.service_get_all_values_handler = service_get_all_values_handler;
	}

	public GpSpringBootServiceCreateHandler getService_create_handler() {
		return service_create_handler;
	}
	@Resource(name = "GpSpringBootServiceCreateHandler")
	public void setService_create_handler(
			GpSpringBootServiceCreateHandler service_create_handler) {
		this.service_create_handler = service_create_handler;
	}

	public GpSpringBootServiceUpdateHandler getService_update_handler() {
		return service_update_handler;
	}
	@Resource(name = "GpSpringBootServiceUpdateHandler")
	public void setService_update_handler(
			GpSpringBootServiceUpdateHandler service_update_handler) {
		this.service_update_handler = service_update_handler;
	}

	public GpSpringBootServiceDeleteHandler getService_delete_handler() {
		return service_delete_handler;
	}
	@Resource(name = "GpSpringBootServiceDeleteHandler")
	public void setService_delete_handler(
			GpSpringBootServiceDeleteHandler service_delete_handler) {
		this.service_delete_handler = service_delete_handler;
	}

	public GpSpringBootServiceSearchHandler getService_search_handler() {
		return service_search_handler;
	}
	@Resource(name = "GpSpringBootServiceSearchHandler")
	public void setService_search_handler(
			GpSpringBootServiceSearchHandler service_search_handler) {
		this.service_search_handler = service_search_handler;
	}

	public GpSpringBootServiceSearchForUpdateHandler getService_search_for_update_handler() {
		return service_search_for_update_handler;
	}
	@Resource(name = "GpSpringBootServiceSearchForUpdateHandler")
	public void setService_search_for_update_handler(
			GpSpringBootServiceSearchForUpdateHandler service_search_for_update_handler) {
		this.service_search_for_update_handler = service_search_for_update_handler;
	}

	public GpSpringBootServiceGetNounByIdHandler getService_get_noun_by_id_handler() {
		return service_get_noun_by_id_handler;
	}
	@Resource(name = "GpSpringBootServiceGetNounByIdHandler")
	public void setService_get_noun_by_id_handler(
			GpSpringBootServiceGetNounByIdHandler service_get_noun_by_id_handler) {
		this.service_get_noun_by_id_handler = service_get_noun_by_id_handler;
	}
	
	public GpSpringBootServiceGetNounByParentIdHandler getService_get_noun_by_parent_id_handler() {
		return service_get_noun_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootServiceGetNounByParentIdHandler")
	public void setService_get_noun_by_parent_id_handler(
			GpSpringBootServiceGetNounByParentIdHandler service_get_noun_by_parent_id_handler) {
		this.service_get_noun_by_parent_id_handler = service_get_noun_by_parent_id_handler;
	}
	
	public GpSpringBootServiceDeleteByParentIdHandler getService_delete_noun_by_parent_id_handler() {
		return service_delete_noun_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootServiceDeleteByParentIdHandler")
	public void setService_delete_noun_by_parent_id_handler(
			GpSpringBootServiceDeleteByParentIdHandler service_delete_noun_by_parent_id_handler) {
		this.service_delete_noun_by_parent_id_handler = service_delete_noun_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootServiceTakePhotoHandler")
	public void setService_take_photo_handler(GpSpringBootServiceTakePhotoHandler service_take_photo_handler) {
		this.service_take_photo_handler = service_take_photo_handler;
	}
	
	public GpSpringBootServiceTakePhotoHandler getService_take_photo_handler() {
		return service_take_photo_handler;
	}

	public GpSpringBootServiceVideoRecorderHandler getService_video_recorder_handler() {
		return service_video_recorder_handler;
	}

	@Resource(name = "GpSpringBootServiceVideoRecorderHandler")
	public void setService_video_recorder_handler(GpSpringBootServiceVideoRecorderHandler service_video_recorder_handler) {
		this.service_video_recorder_handler = service_video_recorder_handler;
	}

}
