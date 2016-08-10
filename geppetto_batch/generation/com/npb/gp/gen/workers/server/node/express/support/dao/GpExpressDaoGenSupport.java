package com.npb.gp.gen.workers.server.node.express.support.dao;

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
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressDaoGenWorker;

@Component("GpExpressDaoGenSupport")
public class GpExpressDaoGenSupport {
	private GpExpressDaoGenWorker the_worker;

	private GpExpressDaoFunctionImpHelper the_function_impl_helper;

	private GpExpressDaoGetAllValuesHandler the_get_all_values_handler;
	private GpExpressDaoCreateHandler the_create_handler;
	private GpExpressDaoUpdateHandler the_update_handler;
	private GpExpressDaoDeleteHandler the_delete_handler;
	private GpExpressDaoSearchHandler the_search_handler;
	private GpExpressDaoSearchForUpdateHandler the_search_for_update_handler;
	private GpExpressDaoGetNounByIdHandler the_get_noun_by_id_handler;
	private GpExpressDaoGetNounByParentIdHandler the_get_noun_by_parent_id_handler;
	private GpExpressDaoDeleteByParentIdHandler the_delete_by_parent_id_handler;

	public List<DaoFunctionDescription> get_the_methods(GpActivity activity) throws Exception{
		List<DaoFunctionDescription> the_methods = new ArrayList<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				DaoFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				DaoFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				DaoFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				DaoFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				DaoFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				the_methods.addAll(this.the_update_handler.getThe_implicit_verbs());
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				DaoFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				DaoFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_methods.add(impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			DaoFunctionDescription impl_get_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_get_by_parent_id);
			DaoFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_methods.add(impl_delete_by_parent_id);
		}
		
		return the_methods;
	}

	public Map<String, DaoFunctionDescription> get_the_map_methods(GpActivity activity) throws Exception{
		Map<String, DaoFunctionDescription> the_map = new HashMap<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetAllValues)){
				DaoFunctionDescription impl = this.the_get_all_values_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpGetNounById)){
				DaoFunctionDescription impl = this.the_get_noun_by_id_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				DaoFunctionDescription impl = this.the_search_for_update_handler
							.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpCreate)){
				DaoFunctionDescription impl = this.the_create_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpUpdate)){
				DaoFunctionDescription impl = this.the_update_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				for(DaoFunctionDescription implicit : this.the_update_handler.getThe_implicit_verbs()){
					the_map.put(implicit.verb_action_on_data, implicit);
				}
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpDelete)){
				DaoFunctionDescription impl = this.the_delete_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
			if(a_verb.getAction_on_data().equals(GpBaseVerbsConstants.GpSearch)){
				DaoFunctionDescription impl = this.the_search_handler
									.handle_verb(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			DaoFunctionDescription impl_get_by_parent_id = this.the_get_noun_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,impl_get_by_parent_id);
			DaoFunctionDescription impl_delete_by_parent_id = this.the_delete_by_parent_id_handler
					.handle_implicit_verb(null, activity);
			the_map.put(GpBaseVerbsConstants.GpDeleteByParentId,impl_delete_by_parent_id);
		}
		return the_map;
	}

	public GpExpressDaoGetAllValuesHandler getThe_get_all_values_handler() {
		return the_get_all_values_handler;
	}
	@Resource(name = "GpExpressDaoGetAllValuesHandler")
	public void setThe_get_all_values_handler(
			GpExpressDaoGetAllValuesHandler the_get_all_values_handler) {
		this.the_get_all_values_handler = the_get_all_values_handler;
	}

	public GpExpressDaoCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	@Resource(name = "GpExpressDaoCreateHandler")
	public void setThe_create_handler(GpExpressDaoCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}

	public GpExpressDaoUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	@Resource(name ="GpExpressDaoUpdateHandler")
	public void setThe_update_handler(GpExpressDaoUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}

	public GpExpressDaoDeleteHandler getThe_delete_handler() {
		return the_delete_handler;
	}
	@Resource(name = "GpExpressDaoDeleteHandler")
	public void setThe_delete_handler(GpExpressDaoDeleteHandler the_delete_handler) {
		this.the_delete_handler = the_delete_handler;
	}

	public GpExpressDaoSearchHandler getThe_search_handler() {
		return the_search_handler;
	}
	@Resource(name = "GpExpressDaoSearchHandler")
	public void setThe_search_handler(GpExpressDaoSearchHandler the_search_handler) {
		this.the_search_handler = the_search_handler;
	}

	public GpExpressDaoSearchForUpdateHandler getThe_search_for_update_handler() {
		return the_search_for_update_handler;
	}
	@Resource(name = "GpExpressDaoSearchForUpdateHandler")
	public void setThe_search_for_update_handler(
			GpExpressDaoSearchForUpdateHandler the_search_for_update_handler) {
		this.the_search_for_update_handler = the_search_for_update_handler;
	}

	public GpExpressDaoGetNounByIdHandler getThe_get_noun_by_id_handler() {
		return the_get_noun_by_id_handler;
	}
	@Resource(name = "GpExpressDaoGetNounByIdHandler")
	public void setThe_get_noun_by_id_handler(
			GpExpressDaoGetNounByIdHandler the_get_noun_by_id_handler) {
		this.the_get_noun_by_id_handler = the_get_noun_by_id_handler;
	}

	public GpExpressDaoGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpExpressDaoGenWorker the_worker) {
		this.the_worker = the_worker;
		the_function_impl_helper.setThe_gen_support(this);
		the_get_noun_by_parent_id_handler.setThe_gen_support(this);
		the_delete_by_parent_id_handler.setThe_gen_support(this);
		the_create_handler.setThe_gen_support(this);
		the_delete_handler.setThe_gen_support(this);
		the_update_handler.setThe_gen_support(this);
	}

	public GpExpressDaoFunctionImpHelper getThe_function_impl_helper() {
		return the_function_impl_helper;
	}

	@Resource(name="GpExpressDaoFunctionImpHelper")
	public void setThe_function_impl_helper(GpExpressDaoFunctionImpHelper the_function_impl_helper) {
		this.the_function_impl_helper = the_function_impl_helper;
	}
	
	public GpExpressDaoGetNounByParentIdHandler getThe_get_noun_by_parent_id_handler() {
		return the_get_noun_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressDaoGetNounByParentIdHandler")
	public void setThe_get_noun_by_parent_id_handler(
			GpExpressDaoGetNounByParentIdHandler the_get_noun_by_parent_id_handler) {
		this.the_get_noun_by_parent_id_handler = the_get_noun_by_parent_id_handler;
	}
	
	public GpExpressDaoDeleteByParentIdHandler getThe_delete_by_parent_id_handler() {
		return the_delete_by_parent_id_handler;
	}
	
	@Resource(name="GpExpressDaoDeleteByParentIdHandler")
	public void setThe_delete_by_parent_id_handler(
			GpExpressDaoDeleteByParentIdHandler the_delete_by_parent_id_handler) {
		this.the_delete_by_parent_id_handler = the_delete_by_parent_id_handler;
	}
}
