package com.npb.gp.gen.workers.server.node.express.support.routes;

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
import com.npb.gp.gen.domain.js.node.express.ExpressRouterDescription;
import com.npb.gp.gen.workers.server.node.express.GpExpressRoutesGenWorker;

@Component("GpExpressRoutesGenSupport")
public class GpExpressRoutesGenSupport {
	private GpExpressRoutesGenWorker the_worker;
	private GpExpressRouteFunctionImpHelper the_function_impl_helper;
	
	public List<ExpressRouterDescription> get_the_routes(GpActivity activity) throws Exception{
		List<ExpressRouterDescription> the_routes = new ArrayList<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.get_all_values_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.get_nound_by_id_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.search_for_update_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.create_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpCreate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")){
				if(!generated_verbs.contains("GpSearchForUpdate")){
					ExpressRouterDescription implicit_verb = this.the_function_impl_helper
							.search_for_update_implementation(null, activity);
					the_routes.add(implicit_verb);
					generated_verbs.add("GpSearchForUpdate");
				}
				ExpressRouterDescription impl = this.the_function_impl_helper
									.update_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.delete_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpDelete");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.search_implementation(a_verb, activity);
				the_routes.add(impl);
				generated_verbs.add("GpSearch");
				continue;
			}
		}
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ExpressRouterDescription impl_get_noun_by_parent_id = this.the_function_impl_helper
					.get_noun_by_parent_id_implementation(activity);
			the_routes.add(impl_get_noun_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			
			ExpressRouterDescription impl_delete_by_parent_id = this.the_function_impl_helper
					.delete_by_parent_id_implementation(activity);
			the_routes.add(impl_delete_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}
		return the_routes;
	}
	
	public Map<String,ExpressRouterDescription> get_the_routes_map(GpActivity activity) throws Exception{
		Map<String,ExpressRouterDescription> the_map = new HashMap<>();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.get_all_values_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.get_nound_by_id_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				ExpressRouterDescription impl = this.the_function_impl_helper
							.search_for_update_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.create_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpCreate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")){
				if(!generated_verbs.contains("GpSearchForUpdate")){
					ExpressRouterDescription implicit_verb = this.the_function_impl_helper
							.search_for_update_implementation(null, activity);
					the_map.put("GpSearchForUpdate", implicit_verb);
					generated_verbs.add("GpSearchForUpdate");
				}
				ExpressRouterDescription impl = this.the_function_impl_helper
									.update_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.delete_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpDelete");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				ExpressRouterDescription impl = this.the_function_impl_helper
									.search_implementation(a_verb, activity);
				the_map.put(a_verb.getAction_on_data(), impl);
				generated_verbs.add("GpSearch");
				continue;
			}
		}
		
		JSONArray json_parent = this.the_worker.getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			ExpressRouterDescription impl_get_noun_by_parent_id = this.the_function_impl_helper
					.get_noun_by_parent_id_implementation(activity);
			the_map.put(GpBaseVerbsConstants.GpGetNounByParentId,impl_get_noun_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpGetNounByParentId);
			
			ExpressRouterDescription impl_delete_by_parent_id = this.the_function_impl_helper
					.delete_by_parent_id_implementation(activity);
			the_map.put(GpBaseVerbsConstants.GpDeleteByParentId,impl_delete_by_parent_id);
			generated_verbs.add(GpBaseVerbsConstants.GpDeleteByParentId);
		}
		return the_map;
	}
	
	public void setThe_worker(GpExpressRoutesGenWorker the_worker) {
		this.the_worker = the_worker;
		this.the_function_impl_helper.setThe_gen_support(this);
	}
	
	public GpExpressRoutesGenWorker getThe_worker() {
		return the_worker;
	}
	
	public GpExpressRouteFunctionImpHelper getThe_function_impl_helper() {
		return the_function_impl_helper;
	}
	
	@Resource(name="GpExpressRouteFunctionImpHelper")
	public void setThe_function_impl_helper(GpExpressRouteFunctionImpHelper the_function_impl_helper) {
		this.the_function_impl_helper = the_function_impl_helper;
	}

}
