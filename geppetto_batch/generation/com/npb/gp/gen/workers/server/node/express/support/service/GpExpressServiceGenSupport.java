package com.npb.gp.gen.workers.server.node.express.support.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressServiceGenWorker;

@Component("GpExpressServiceGenSupport")
public class GpExpressServiceGenSupport {
	
	private GpExpressServiceGenWorker the_worker;
	private GpExpressServiceFunctionImpHelper the_function_impl_helper;
	private GpExpressServiceGetAllValuesHandler the_get_all_values_handler;
	private GpExpressServiceCreateHandler the_create_handler;
	private GpExpressServiceUpdateHandler the_update_handler;
	private GpExpressServiceDeleteHandler the_delete_handler;
	private GpExpressServiceSearchHandler the_search_handler;
	private GpExpressServiceSearchForUpdateHandler the_search_for_update_handler;
	private GpExpressServiceGetNounByIdHandler the_get_noun_by_id_handler;
	private GpExpressServiceGetNounByParentIdHandler the_get_noun_by_parent_id_handler;
	private GpExpressServiceDeleteByParentIdHandler the_delete_by_parent_id_handler;
	
	public List<ServiceFunctionDescription> get_the_methods(GpActivity activity) throws Exception{
		List<ServiceFunctionDescription> the_methods = new ArrayList<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				ServiceFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				ServiceFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				ServiceFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				ServiceFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				ServiceFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				the_methods.addAll(this.the_update_handler.getThe_implicit_verbs());
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				ServiceFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				ServiceFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ServiceFunctionDescription impl_get_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_get_by_parent_id);
			ServiceFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_delete_by_parent_id);
		}
		return the_methods;
	}
	
	public Map<String, ServiceFunctionDescription> get_the_map_methods(GpActivity activity) throws Exception{
		Map<String, ServiceFunctionDescription> the_map = new HashMap<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				ServiceFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				ServiceFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				ServiceFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				ServiceFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				ServiceFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				for(ServiceFunctionDescription implicit : this.the_update_handler.getThe_implicit_verbs()){
					the_map.put(implicit.verb_action_on_data, implicit);
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				ServiceFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				ServiceFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ServiceFunctionDescription impl_get_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,impl_get_by_parent_id);
			ServiceFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpDeleteByParentId,impl_delete_by_parent_id);
		}
		return the_map;
	}
	
	public GpExpressServiceGetAllValuesHandler getThe_get_all_values_handler() {
		return the_get_all_values_handler;
	}
@Resource(name = "GpExpressServiceGetAllValuesHandler")
	public void setThe_get_all_values_handler(
			GpExpressServiceGetAllValuesHandler the_get_all_values_handler) {
		this.the_get_all_values_handler = the_get_all_values_handler;
		this.the_get_all_values_handler.setThe_gen_support(this);
	}

	public GpExpressServiceCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
@Resource(name = "GpExpressServiceCreateHandler")
	public void setThe_create_handler(
			GpExpressServiceCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}

	public GpExpressServiceUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
@Resource(name = "GpExpressServiceUpdateHandler")
	public void setThe_update_handler(
			GpExpressServiceUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}

	public GpExpressServiceDeleteHandler getThe_delete_handler() {
		return the_delete_handler;
	}
@Resource(name = "GpExpressServiceDeleteHandler")
	public void setThe_delete_handler(
			GpExpressServiceDeleteHandler the_delete_handler) {
		this.the_delete_handler = the_delete_handler;
	}

	public GpExpressServiceSearchHandler getThe_search_handler() {
		return the_search_handler;
	}
@Resource(name = "GpExpressServiceSearchHandler")
	public void setThe_search_handler(
			GpExpressServiceSearchHandler the_search_handler) {
		this.the_search_handler = the_search_handler;
	}

	public GpExpressServiceSearchForUpdateHandler getThe_search_for_update_handler() {
		return the_search_for_update_handler;
	}
@Resource(name = "GpExpressServiceSearchForUpdateHandler")
	public void setThe_search_for_update_handler(
			GpExpressServiceSearchForUpdateHandler the_search_for_update_handler) {
		this.the_search_for_update_handler = the_search_for_update_handler;
	}

	public GpExpressServiceGetNounByIdHandler getThe_get_noun_by_id_handler() {
		return the_get_noun_by_id_handler;
	}
@Resource(name = "GpExpressServiceGetNounByIdHandler")
	public void setThe_get_noun_by_id_handler(
			GpExpressServiceGetNounByIdHandler the_get_noun_by_id_handler) {
		this.the_get_noun_by_id_handler = the_get_noun_by_id_handler;
	}

	public GpExpressServiceGenWorker getThe_worker() {
		return the_worker;
	}
	//@Resource(name = "GpExpressServiceGenWorker")
	public void setThe_worker(GpExpressServiceGenWorker the_worker) {
		this.the_worker = the_worker;
		this.the_function_impl_helper.setThe_gen_support(this);
		this.the_get_noun_by_parent_id_handler.setThe_gen_support(this);
		this.the_delete_by_parent_id_handler.setThe_gen_support(this);
		this.the_create_handler.setThe_gen_support(this);
		this.the_delete_handler.setThe_gen_support(this);
		this.the_get_all_values_handler.setThe_gen_support(this);
		this.the_get_noun_by_id_handler.setThe_gen_support(this);
		this.the_search_for_update_handler.setThe_gen_support(this);
		this.the_search_handler.setThe_gen_support(this);
		this.the_update_handler.setThe_gen_support(this);
	}
	
	public GpExpressServiceFunctionImpHelper getThe_function_impl_helper() {
		return the_function_impl_helper;
	}
	
	@Resource(name="GpExpressServiceFunctionImpHelper")
	public void setThe_function_impl_helper(GpExpressServiceFunctionImpHelper the_function_impl_helper) {
		this.the_function_impl_helper = the_function_impl_helper;
	}
	
	public GpExpressServiceGetNounByParentIdHandler getThe_get_noun_by_parent_id_handler() {
		return the_get_noun_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressServiceGetNounByParentIdHandler")
	public void setThe_get_noun_by_parent_id_handler(
			GpExpressServiceGetNounByParentIdHandler the_get_noun_by_parent_id_handler) {
		this.the_get_noun_by_parent_id_handler = the_get_noun_by_parent_id_handler;
	}
	
	public GpExpressServiceDeleteByParentIdHandler getThe_delete_by_parent_id_handler() {
		return the_delete_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressServiceDeleteByParentIdHandler")
	public void setThe_delete_by_parent_id_handler(
			GpExpressServiceDeleteByParentIdHandler the_delete_by_parent_id_handler) {
		this.the_delete_by_parent_id_handler = the_delete_by_parent_id_handler;
	}
}
