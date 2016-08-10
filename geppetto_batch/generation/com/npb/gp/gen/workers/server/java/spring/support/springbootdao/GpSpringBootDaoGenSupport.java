package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

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
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/29/2016</br>
 * @since 1.0</p>  
 * 
 * Contains the logic to generate the methods, based on activity verbs,</br>
 * in a Hibernate JPA Dao - it is designed to be called by</br>
 * the  worker that handles the Hibernate JPA Dao  generation</p> 
 */

@Component("GpSpringBootDaoGenSupport")
public class GpSpringBootDaoGenSupport {
	
	private GpFlowControl the_flow;
	private GpSpringBootDaoVerbMethodSignitures method_signitures_hlpr;
	//private GpSpringBootDaoVerbMethodImplementations implementation_hlpr;
	private GpSpringBootJpaDaoGenWorker the_worker;
	
	private GpSpringBootDaoGetAllValuesHandler dao_get_all_values_handler;
	private GpSpringBootDaoCreateHandler dao_create_handler;
	private GpSpringBootDaoUpdateHandler dao_update_handler;
	private GpSpringBootDaoDeleteHandler dao_delete_handler;
	private GpSpringBootDaoSearchHandler dao_search_handler;
	private GpSpringBootDaoSearchForUpdateHandler dao_search_for_update_handler;
	private GpSpringBootDaoGetNounByIdHandler dao_get_noun_by_id_handler;
	private GpSpringBootDaoGetByParentIdHandler dao_get_by_parent_id_handler;
	private GpSpringBootDaoDeleteByParentIdHandler dao_delete_by_parent_id_handler;
	private GpSpringBootDaoTakePhotoHandler dao_take_photo_handler;
	private GpSpringBootDaoVideoRecorderHandler dao_video_recorder_handler;
	

	public HashMap<String,  GpSpringBootDaoVerbGenInfo>
					get_method_signitures(GpActivity an_activity) throws Exception{
		
		HashMap<String,GpSpringBootDaoVerbGenInfo> the_map =
				new HashMap<String,GpSpringBootDaoVerbGenInfo>();
		
		ArrayList<GpVerb> the_verbs = an_activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)){
				the_map.put(a_verb.getAction_on_data(),this.dao_get_all_values_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)){
				the_map.put(a_verb.getAction_on_data(),this.dao_get_noun_by_id_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)){
				the_map.put(a_verb.getAction_on_data(),this.dao_search_for_update_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
				continue;
			}
			
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate) && !generated_verbs.contains(GpBaseVerbsConstants.GpCreate)){
				the_map.put(a_verb.getAction_on_data(),this.dao_create_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)){
				the_map.put(GpBaseVerbsConstants.GpUpdate,this.dao_update_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_update_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data, implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && !generated_verbs.contains(GpBaseVerbsConstants.GpDelete)){
				the_map.put(a_verb.getAction_on_data(),this.dao_delete_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearch)){
				the_map.put(a_verb.getAction_on_data(),this.dao_search_handler
						.handle_verb(a_verb, an_activity));
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
				continue;
			}	
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && !generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)){
				this.dao_take_photo_handler.handle_implicit_verbs(a_verb, an_activity);
				generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_take_photo_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data,implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && !generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)){
				this.dao_video_recorder_handler.handle_implicit_verbs(a_verb, an_activity);
				generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_video_recorder_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						the_map.put(implicit.verb_action_on_data,implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;
			}
			
		}
		JSONArray json_parent = this.the_worker.getRelationships_map().get(an_activity.getPrimary_noun().getId());
		if(json_parent != null){
			the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,this.dao_get_by_parent_id_handler
					.handle_implicit_verb(null, an_activity));
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			the_map.put(GpBaseVerbsConstants.GpDeleteByParentId,this.dao_delete_by_parent_id_handler
					.handle_implicit_verb(null, an_activity));
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}
		return the_map;	
	}
	
	public ArrayList<GpSpringBootDaoVerbGenInfo> get_verb_method_implmentation(
															GpActivity activity) throws Exception{
		ArrayList<GpSpringBootDaoVerbGenInfo> implementations = new ArrayList<GpSpringBootDaoVerbGenInfo>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();

		for(GpVerb a_verb : the_verbs){
			
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetAllValues)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_get_all_values_handler
							.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetAllValues);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById) && !generated_verbs.contains(GpBaseVerbsConstants.GpGetNounById)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpGetNounById);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearchForUpdate)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_search_for_update_handler
							.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearchForUpdate);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate) && !generated_verbs.contains(GpBaseVerbsConstants.GpCreate)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_create_handler
									.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpCreate);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate) && !generated_verbs.contains(GpBaseVerbsConstants.GpUpdate)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_update_handler
									.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpUpdate);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_update_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						implementations.add(implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;	
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete) && !generated_verbs.contains(GpBaseVerbsConstants.GpDelete)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_delete_handler
									.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpDelete);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch) && !generated_verbs.contains(GpBaseVerbsConstants.GpSearch)){
				GpSpringBootDaoVerbGenInfo impl = this.dao_search_handler
									.handle_verb(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add(GpBaseVerbsConstants.GpSearch);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto) && !generated_verbs.contains(GpBaseVerbsConstants.GpTakePhoto)){
				this.dao_take_photo_handler.handle_implicit_verbs(a_verb, activity);
				generated_verbs.add(GpBaseVerbsConstants.GpTakePhoto);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_take_photo_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						implementations.add(implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo) && !generated_verbs.contains(GpBaseVerbsConstants.GpRecordVideo)){
				this.dao_video_recorder_handler.handle_implicit_verbs(a_verb, activity);
				generated_verbs.add(GpBaseVerbsConstants.GpRecordVideo);
				for(GpSpringBootDaoVerbGenInfo implicit : this.dao_video_recorder_handler.getThe_implicit_verbs()){
					if(!generated_verbs.contains(implicit.verb_action_on_data)){
						implementations.add(implicit);
						generated_verbs.add(implicit.verb_action_on_data);
					}
				}
				continue;
			}
			
		}
		JSONArray json_parent = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			//get_by_parent_id
			GpSpringBootDaoVerbGenInfo impl_get_by_parent_id = this.dao_get_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			implementations.add(impl_get_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			//delete by parent id
			GpSpringBootDaoVerbGenInfo impl_delete_by_parent_id = this.dao_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			implementations.add(impl_delete_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}
		return implementations;
		
	}
	
	public GpSpringBootJpaDaoGenWorker getThe_worker() {
		return the_worker;
	}

	@Resource(name="GpSpringBootJpaDaoGenWorker")
	public void setThe_worker(GpSpringBootJpaDaoGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpSpringBootDaoVerbMethodSignitures getMethod_signitures_hlpr() {
		return method_signitures_hlpr;
	}

	@Resource(name="GpSpringBootDaoVerbMethodSignitures")
	public void setMethod_signitures_hlpr(
			GpSpringBootDaoVerbMethodSignitures method_signitures_hlpr) {
		this.method_signitures_hlpr = method_signitures_hlpr;
	}


	public void set_flow(GpFlowControl the_flow){
		this.the_flow = the_flow;
	}


	public GpSpringBootDaoGetAllValuesHandler getDao_get_all_values_handler() {
		return dao_get_all_values_handler;
	}
	@Resource(name = "GpSpringBootDaoGetAllValuesHandler")
	public void setDao_get_all_values_handler(
			GpSpringBootDaoGetAllValuesHandler dao_get_all_values_handler) {
		this.dao_get_all_values_handler = dao_get_all_values_handler;
	}

	public GpSpringBootDaoCreateHandler getDao_create_handler() {
		return dao_create_handler;
	}

	@Resource(name = "GpSpringBootDaoCreateHandler")
	public void setDao_create_handler(
			GpSpringBootDaoCreateHandler dao_create_handler) {
		this.dao_create_handler = dao_create_handler;
	}

	public GpSpringBootDaoUpdateHandler getDao_update_handler() {
		return dao_update_handler;
	}
	
	@Resource(name = "GpSpringBootDaoUpdateHandler")
	public void setDao_update_handler(
			GpSpringBootDaoUpdateHandler dao_update_handler) {
		this.dao_update_handler = dao_update_handler;
	}

	public GpSpringBootDaoDeleteHandler getDao_delete_handler() {
		return dao_delete_handler;
	}
	
	@Resource(name = "GpSpringBootDaoDeleteHandler")
	public void setDao_delete_handler(
			GpSpringBootDaoDeleteHandler dao_delete_handler) {
		this.dao_delete_handler = dao_delete_handler;
	}

	public GpSpringBootDaoSearchHandler getDao_search_handler() {
		return dao_search_handler;
	}
	
	@Resource(name = "GpSpringBootDaoSearchHandler")
	public void setDao_search_handler(
			GpSpringBootDaoSearchHandler dao_search_handler) {
		this.dao_search_handler = dao_search_handler;
	}

	public GpSpringBootDaoSearchForUpdateHandler getDao_search_for_update_handler() {
		return dao_search_for_update_handler;
	}
	
	@Resource(name = "GpSpringBootDaoSearchForUpdateHandler")
	public void setDao_search_for_update_handler(
			GpSpringBootDaoSearchForUpdateHandler dao_search_for_update_handler) {
		this.dao_search_for_update_handler = dao_search_for_update_handler;
	}

	public GpSpringBootDaoGetNounByIdHandler getDao_get_noun_by_id_handler() {
		return dao_get_noun_by_id_handler;
	}
	
	@Resource(name = "GpSpringBootDaoGetNounByIdHandler")
	public void setDao_get_noun_by_id_handler(
			GpSpringBootDaoGetNounByIdHandler dao_get_noun_by_id_handler) {
		this.dao_get_noun_by_id_handler = dao_get_noun_by_id_handler;
	}
	
	public GpSpringBootDaoGetByParentIdHandler getDao_get_by_parent_id_handler() {
		return dao_get_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootDaoGetByParentIdHandler")
	public void setDao_get_by_parent_id_handler(GpSpringBootDaoGetByParentIdHandler dao_get_by_parent_id_handler) {
		this.dao_get_by_parent_id_handler = dao_get_by_parent_id_handler;
	}
	
	public GpSpringBootDaoDeleteByParentIdHandler getDao_delete_by_parent_id_handler() {
		return dao_delete_by_parent_id_handler;
	}
	
	@Resource(name = "GpSpringBootDaoDeleteByParentIdHandler")
	public void setDao_delete_by_parent_id_handler(
			GpSpringBootDaoDeleteByParentIdHandler dao_delete_by_parent_id_handler) {
		this.dao_delete_by_parent_id_handler = dao_delete_by_parent_id_handler;
	}
	@Resource(name = "GpSpringBootDaoTakePhotoHandler")
	public void setDao_take_photo_handler(GpSpringBootDaoTakePhotoHandler dao_take_photo_handler) {
		this.dao_take_photo_handler = dao_take_photo_handler;
	}
	public GpSpringBootDaoTakePhotoHandler getDao_take_photo_handler() {
		return dao_take_photo_handler;
	}

	public GpSpringBootDaoVideoRecorderHandler getDao_video_recordet_handler() {
		return dao_video_recorder_handler;
	}

	@Resource(name = "GpSpringBootDaoVideoRecorderHandler")
	public void setDao_video_recordet_handler(GpSpringBootDaoVideoRecorderHandler dao_video_recordet_handler) {
		this.dao_video_recorder_handler = dao_video_recordet_handler;
	}
}
