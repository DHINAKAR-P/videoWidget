package com.npb.gp.gen.workers.server.node.express.support.controller;

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
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressControllerGenWorker;

@Component("GpExpressControllerGenSupport")
public class GpExpressControllerGenSupport {
	private GpExpressControllerGenWorker the_worker;

	private GpExpressControllerGetAllValuesHandler the_get_all_values_handler;
	private GpExpressControllerCreateHandler the_create_handler;
	private GpExpressControllerUpdateHandler the_update_handler;
	private GpExpressControllerDeleteHandler the_delete_handler;
	private GpExpressControllerSearchHandler the_search_handler;
	private GpExpressControllerSearchForUpdateHandler the_search_for_update_handler;
	private GpExpressControllerGetNounByIdHandler the_get_noun_by_id_handler;
	private GpExpressControllerGetNounByParentIdHandler the_get_noun_by_parent_id_handler;
	private GpExpressControllerFunctionImpHelper the_function_impl_helper;
	private GpExpressControllerDeleteByParentIdHandler the_delete_by_parent_id_handler;

	public List<ControllerFunctionDescription> get_the_methods(GpActivity activity) throws Exception {
		List<ControllerFunctionDescription> the_methods = new ArrayList<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				ControllerFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				ControllerFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				ControllerFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				System.out.println("search for update is handler: ");
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				ControllerFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				ControllerFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				the_methods.addAll(this.the_update_handler.getThe_implicit_verbs());
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				ControllerFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				ControllerFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ControllerFunctionDescription impl_get_noun_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_get_noun_by_parent_id);
			ControllerFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_delete_by_parent_id);
		}
		return the_methods;
	}

	public Map<String, ControllerFunctionDescription> get_the_map_methods(GpActivity activity) throws Exception {
		Map<String, ControllerFunctionDescription> the_map = new HashMap<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				ControllerFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				ControllerFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				System.out.println("search for update of controller");
				ControllerFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				ControllerFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				ControllerFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				for(ControllerFunctionDescription implicit : this.the_update_handler.getThe_implicit_verbs()){
					the_map.put(implicit.verb_action_on_data, implicit);
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				ControllerFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				ControllerFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ControllerFunctionDescription impl_get_noun_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,impl_get_noun_by_parent_id);
			ControllerFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpDeleteByParentId, impl_delete_by_parent_id);
		}
		return the_map;
	}

	public GpExpressControllerGetAllValuesHandler getThe_get_all_values_handler() {
		return the_get_all_values_handler;
	}
	@Resource(name = "GpExpressControllerGetAllValuesHandler")
	public void setThe_get_all_values_handler(
			GpExpressControllerGetAllValuesHandler the_get_all_values_handler) {
		this.the_get_all_values_handler = the_get_all_values_handler;
	}

	public GpExpressControllerCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	@Resource(name = "GpExpressControllerCreateHandler")
	public void setThe_create_handler(
			GpExpressControllerCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}

	public GpExpressControllerUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	@Resource(name = "GpExpressControllerUpdateHandler")
	public void setThe_update_handler(
			GpExpressControllerUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}

	public GpExpressControllerDeleteHandler getThe_delete_handler() {
		return the_delete_handler;
	}
	@Resource(name = "GpExpressControllerDeleteHandler")
	public void setThe_delete_handler(
			GpExpressControllerDeleteHandler the_delete_handler) {
		this.the_delete_handler = the_delete_handler;
	}

	public GpExpressControllerSearchHandler getThe_search_handler() {
		return the_search_handler;
	}
	@Resource(name = "GpExpressControllerSearchHandler")
	public void setThe_search_handler(
			GpExpressControllerSearchHandler the_search_handler) {
		this.the_search_handler = the_search_handler;
	}

	public GpExpressControllerSearchForUpdateHandler getThe_search_for_update_handler() {
		return the_search_for_update_handler;
	}
	@Resource(name = "GpExpressControllerSearchForUpdateHandler")
	public void setThe_search_for_update_handler(
			GpExpressControllerSearchForUpdateHandler the_search_for_update_handler) {
		this.the_search_for_update_handler = the_search_for_update_handler;
	}

	public GpExpressControllerGetNounByIdHandler getThe_get_noun_by_id_handler() {
		return the_get_noun_by_id_handler;
	}
	@Resource(name = "GpExpressControllerGetNounByIdHandler")
	public void setThe_get_noun_by_id_handler(
			GpExpressControllerGetNounByIdHandler the_get_noun_by_id_handler) {
		this.the_get_noun_by_id_handler = the_get_noun_by_id_handler;
	}

	public GpExpressControllerGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpExpressControllerGenWorker the_worker) {
		this.the_worker = the_worker;
		the_function_impl_helper.setThe_gen_support(this);
		the_get_noun_by_parent_id_handler.setThe_gen_support(this);
		the_delete_by_parent_id_handler.setThe_gen_support(this);
		the_create_handler.setThe_gen_support(this);
		the_delete_handler.setThe_gen_support(this);
		the_get_all_values_handler.setThe_gen_support(this);
		the_get_noun_by_id_handler.setThe_gen_support(this);
		the_search_for_update_handler.setThe_gen_support(this);
		the_search_handler.setThe_gen_support(this);
		the_update_handler.setThe_gen_support(this);
	}

	public GpExpressControllerFunctionImpHelper getThe_function_impl_helper() {
		return the_function_impl_helper;
	}

	@Resource(name="GpExpressControllerFunctionImpHelper")
	public void setThe_function_impl_helper(GpExpressControllerFunctionImpHelper the_function_impl_helper) {
		this.the_function_impl_helper = the_function_impl_helper;
	}
	
	public GpExpressControllerGetNounByParentIdHandler getThe_get_noun_by_parent_id_handler() {
		return the_get_noun_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressControllerGetNounByParentIdHandler")
	public void setThe_get_noun_by_parent_id_handler(
			GpExpressControllerGetNounByParentIdHandler the_get_noun_by_parent_id_handler) {
		this.the_get_noun_by_parent_id_handler = the_get_noun_by_parent_id_handler;
	}
	
	public GpExpressControllerDeleteByParentIdHandler getThe_delete_by_parent_id_handler() {
		return the_delete_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressControllerDeleteByParentIdHandler")
	public void setThe_delete_by_parent_id_handler(
			GpExpressControllerDeleteByParentIdHandler the_delete_by_parent_id_handler) {
		this.the_delete_by_parent_id_handler = the_delete_by_parent_id_handler;
	}
}
