package com.npb.gp.gen.workers.server.java.springboot.support.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootControllerGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 *  Contains the logic to generate the methods, based on activity
 *  verbs,</br> in a Spring based controller - it is designed to be called
 *  by the </br> worker that handles the Spring Controller generation</p>
 */
@Component("GpControllerGenSpringBootSupport")
public class GpControllerGenSpringBootSupport {
	private GpSpringBootControllerGenWorker the_worker;
	private GpFlowControl the_flow;
	private GpSpringBootControllerGetAllValuesHandler get_all_values_handler;
	private GpSpringBootControllerCreateHandler create_handler;
	private GpSpringBootControllerUpdateHandler update_handler;
	private GpSpringBootControllerDeleteHandler delete_handler;
	private GpSpringBootControllerSearchHandler search_handler;
	private GpSpringBootControllerSearchForUpdateHandler search_for_update_handler;
	private GpSpringBootControllerGetNounByIdHandler get_noun_by_id_handler;
	private GpSpringBootControllerGetNounByParentIdHandler get_noun_by_parent_id_handler;
	private GpSpringBootControllerDeleteByParentIdHandler delete_noun_by_parent_id_handler;
	private GpSpringBootControllerTakePhotoHandler takePhoto_handler;
	private GpSpringBootControllerVideoRecorderHandler videoRecorder_handler;

	public ArrayList<GpControllerSpringBootVerbGenInfo> prep_controller_verb_generation(
			GpActivity activity, GpServerJavaSpringBootGenService the_service,
			GpFlowControl the_flow) throws Exception {
		
		ArrayList<GpControllerSpringBootVerbGenInfo> the_verbs_methods = new ArrayList<GpControllerSpringBootVerbGenInfo>();
		//Firts we create method signatures for methods
		HashMap<String, GpServiceVerbGenInfo> service_methods = the_service
				.getSpring_boot_service_worker()._get_method_signitures(activity);
		List<String> generated_verbs = new ArrayList<String>();
			
		for (GpVerb verb : activity.getTheverbs()) {
			if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.get_all_values_handler.handle_verb(verb, activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.get_noun_by_id_handler.handle_verb(
						verb, activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)) {
				GpControllerSpringBootVerbGenInfo the_dto = this
						.search_for_update_handler.handle_verb(verb, activity,
								service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate) && !generated_verbs.contains(GpBaseVerbsConstants.GpCreate)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.create_handler.handle_verb(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.update_handler.handle_verb(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				List<GpControllerSpringBootVerbGenInfo> implicit_verbs = this.update_handler.getThe_implicit_verbs();
				for(GpControllerSpringBootVerbGenInfo implicit_verb : implicit_verbs){
					if(!generated_verbs.contains(implicit_verb.verb_action_on_data))
						the_verbs_methods.add(implicit_verb);
				}
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && !generated_verbs.contains(GpBaseVerbsConstants.GpDelete)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.delete_handler.handle_verb(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
			} else if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearch)) {
				GpControllerSpringBootVerbGenInfo the_dto = this
						.search_handler.handle_verb(verb, activity,
								service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
			} else if(verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && !generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)){
				GpControllerSpringBootVerbGenInfo the_dto = this
						.takePhoto_handler.handle_verb(verb, activity,
								service_methods);
				for(GpControllerSpringBootVerbGenInfo implicit_verb : this.takePhoto_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit_verb.verb_action_on_data)){
						the_verbs_methods.add(implicit_verb);
						generated_verbs.add(implicit_verb.verb_action_on_data);
					}
				}
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
			}
			else if(verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && !generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)){
				GpControllerSpringBootVerbGenInfo the_dto = this
						.videoRecorder_handler.handle_verb(verb, activity,
								service_methods);
				for(GpControllerSpringBootVerbGenInfo implicit_verb : this.videoRecorder_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit_verb.verb_action_on_data)){
						the_verbs_methods.add(implicit_verb);
						generated_verbs.add(implicit_verb.verb_action_on_data);
					}
				}
				the_verbs_methods.add(the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
			}
			
		}
		JSONArray json_parent = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			GpControllerSpringBootVerbGenInfo the_dto_get_noun_by_parent_id_handler = this
					.get_noun_by_parent_id_handler.handle_implicit_verb(null, activity, service_methods);
			the_verbs_methods.add(the_dto_get_noun_by_parent_id_handler);
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			GpControllerSpringBootVerbGenInfo the_dto_delete_noun_by_parent_id_handler = this
					.delete_noun_by_parent_id_handler.handle_implicit_verb(null, activity, service_methods);
			the_verbs_methods.add(the_dto_delete_noun_by_parent_id_handler);
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}
		return the_verbs_methods;
	}
	
		
	public Map<String,GpControllerSpringBootVerbGenInfo> get_the_map_methods(
			GpActivity activity, GpServerJavaSpringBootGenService the_service,
			GpFlowControl the_flow) throws Exception {
		
		Map<String,GpControllerSpringBootVerbGenInfo> the_map = new HashMap<>();
		//Firts we create method signatures for methods
		HashMap<String, GpServiceVerbGenInfo> service_methods = the_service
				.getSpring_boot_service_worker()._get_method_signitures(activity);
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb verb : activity.getTheverbs()) {

			if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.get_all_values_handler.handle_verb(verb, activity, service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.get_noun_by_id_handler.handle_verb(
						verb, activity, service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)  && generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)) {
				GpControllerSpringBootVerbGenInfo the_dto = this
						.search_for_update_handler.handle_verb(verb, activity,
								service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)  && generated_verbs.contains(GpBaseVerbsConstants.GpCreate)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.create_handler.handle_verb(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.update_handler.handle_verb(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				for(GpControllerSpringBootVerbGenInfo implicit : this.update_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data, implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && generated_verbs.contains(GpBaseVerbsConstants.GpDelete)) {
				GpControllerSpringBootVerbGenInfo the_dto = 
						this.delete_handler.handle_verb(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
			}  if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && generated_verbs.contains(GpBaseVerbsConstants.GpSearch)) {
				GpControllerSpringBootVerbGenInfo the_dto = this
						.search_handler.handle_verb(verb, activity,
								service_methods);
				the_map.put(verb.getAction_on_data(),the_dto);
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
			}
			 if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)) {
					GpControllerSpringBootVerbGenInfo the_dto = this
							.takePhoto_handler.handle_verb(verb, activity,
									service_methods);
					the_map.put(verb.getAction_on_data(),the_dto);
					generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
					for(GpControllerSpringBootVerbGenInfo implicit_verb : this.takePhoto_handler.getThe_implicit_verbs()){
						if(!generated_verbs.contains(implicit_verb.verb_action_on_data)){
							the_map.put(implicit_verb.verb_action_on_data,implicit_verb);
							generated_verbs.add(implicit_verb.verb_action_on_data);
						}
					}
				}

			 if (verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)) {
					GpControllerSpringBootVerbGenInfo the_dto = this
							.videoRecorder_handler.handle_verb(verb, activity,
									service_methods);
					the_map.put(verb.getAction_on_data(),the_dto);
					generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
					for(GpControllerSpringBootVerbGenInfo implicit_verb : this.videoRecorder_handler.getThe_implicit_verbs()){
						if(!generated_verbs.contains(implicit_verb.verb_action_on_data)){
							the_map.put(implicit_verb.verb_action_on_data,implicit_verb);
							generated_verbs.add(implicit_verb.verb_action_on_data);
						}
					}
				}
		}
		if(this.the_worker.getRelationships_map() != null && activity.getPrimary_noun() != null){
			JSONArray json_parent = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
			if(json_parent != null){
				GpControllerSpringBootVerbGenInfo the_dto_get_noun_by_parent_id_handler = this
						.get_noun_by_parent_id_handler.handle_implicit_verb(null, activity, service_methods);
				the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,the_dto_get_noun_by_parent_id_handler);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
				GpControllerSpringBootVerbGenInfo delete_noun_by_parent_id_handler = this
						.delete_noun_by_parent_id_handler.handle_implicit_verb(null, activity, service_methods);
				the_map.put(GpBaseVerbsConstants.GpDeleteByParentId,delete_noun_by_parent_id_handler);
				generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
			}
		}
		return the_map;
	}
	
	public GpSpringBootControllerGetAllValuesHandler getGet_all_values_handler() {
		return get_all_values_handler;
	}
	
	@Resource(name = "GpSpringBootControllerGetAllValuesHandler")
	public void setGet_all_values_handler(
			GpSpringBootControllerGetAllValuesHandler get_all_values_handler) {
		this.get_all_values_handler = get_all_values_handler;
	}

	public GpSpringBootControllerCreateHandler getCreate_handler() {
		return create_handler;
	}
	
	@Resource(name = "GpSpringBootControllerCreateHandler")
	public void setCreate_handler(GpSpringBootControllerCreateHandler create_handler) {
		this.create_handler = create_handler;
	}

	public GpSpringBootControllerUpdateHandler getUpdate_handler() {
		return update_handler;
	}
	
	@Resource(name = "GpSpringBootControllerUpdateHandler")
	public void setUpdate_handler(GpSpringBootControllerUpdateHandler update_handler) {
		this.update_handler = update_handler;
	}

	public GpSpringBootControllerDeleteHandler getDelete_handler() {
		return delete_handler;
	}
	
	@Resource(name = "GpSpringBootControllerDeleteHandler")
	public void setDelete_handler(GpSpringBootControllerDeleteHandler delete_handler) {
		this.delete_handler = delete_handler;
	}

	public GpSpringBootControllerSearchHandler getSearch_handler() {
		return search_handler;
	}
	
	@Resource(name = "GpSpringBootControllerSearchHandler")
	public void setSearch_handler(GpSpringBootControllerSearchHandler search_handler) {
		this.search_handler = search_handler;
	}

	public GpSpringBootControllerSearchForUpdateHandler getSearch_for_update_handler() {
		return search_for_update_handler;
	}
	
	@Resource(name = "GpSpringBootControllerSearchForUpdateHandler")
	public void setSearch_for_update_handler(
			GpSpringBootControllerSearchForUpdateHandler search_for_update_handler) {
		this.search_for_update_handler = search_for_update_handler;
	}

	public GpSpringBootControllerGetNounByIdHandler getGet_noun_by_id_handler() {
		return get_noun_by_id_handler;
	}
	
	@Resource(name = "GpSpringBootControllerGetNounByIdHandler")
	public void setGet_noun_by_id_handler(
			GpSpringBootControllerGetNounByIdHandler get_noun_by_id_handler) {
		this.get_noun_by_id_handler = get_noun_by_id_handler;
	}

	public void set_flow(GpFlowControl the_flow) {
		this.the_flow = the_flow;
	}
	
	public GpSpringBootControllerGetNounByParentIdHandler getGet_noun_by_parent_id_handler() {
		return get_noun_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootControllerGetNounByParentIdHandler")
	public void setGet_noun_by_parent_id_handler(
			GpSpringBootControllerGetNounByParentIdHandler get_noun_by_parent_id_handler) {
		this.get_noun_by_parent_id_handler = get_noun_by_parent_id_handler;
	}
	
	public void setThe_worker(GpSpringBootControllerGenWorker the_worker) {
		this.the_worker = the_worker;
		this.get_noun_by_parent_id_handler.setThe_worker(the_worker);
		this.delete_noun_by_parent_id_handler.setThe_worker(the_worker);
	}
	
	public GpSpringBootControllerGenWorker getThe_worker() {
		return the_worker;
	}

	public GpSpringBootControllerDeleteByParentIdHandler getDelete_noun_by_parent_id_handler() {
		return delete_noun_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootControllerDeleteByParentIdHandler")
	public void setDelete_noun_by_parent_id_handler(
			GpSpringBootControllerDeleteByParentIdHandler delete_noun_by_parent_id_handler) {
		this.delete_noun_by_parent_id_handler = delete_noun_by_parent_id_handler;
	}
	public GpSpringBootControllerTakePhotoHandler getTakePhoto_handler() {
		return takePhoto_handler;
	}
	
	@Resource(name = "GpSpringBootControllerTakePhotoHandler")
	public void setTakePhoto_handler(GpSpringBootControllerTakePhotoHandler takePhoto_handler) {
		this.takePhoto_handler = takePhoto_handler;
	}


	public GpSpringBootControllerVideoRecorderHandler getVideoRecorder_handler() {
		return videoRecorder_handler;
	}

	@Resource(name = "GpSpringBootControllerVideoRecorderHandler")
	public void setVideoRecorder_handler(GpSpringBootControllerVideoRecorderHandler videoRecorder_handler) {
		this.videoRecorder_handler = videoRecorder_handler;
	}
}
