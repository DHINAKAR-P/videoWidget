package com.npb.gp.gen.workers.server.node.express.support.routes;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ExpressRouterDescription;

@Component("GpExpressRouteFunctionImpHelper")
public class GpExpressRouteFunctionImpHelper {
	private GpExpressRoutesGenSupport the_gen_support;

	public ExpressRouterDescription get_all_values_implementation(GpVerb a_verb, GpActivity activity) throws Exception  {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "get";
		routerDescription.route_path = "\"/" + the_noun.getName() + "\"";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription get_nound_by_id_implementation(GpVerb a_verb, GpActivity activity) throws Exception  {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "get";
		routerDescription.route_path = "/^\\/" + the_noun.getName() + "\\/(\\d+)$/";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription search_for_update_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "get";
		routerDescription.route_path = "/^\\/" + the_noun.getName() + "\\/(\\d+)$/";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription;
		if(a_verb == null)
			controllerFunctionDescription = the_map.get("GpSearchForUpdate");
		else
			controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription create_implementation(GpVerb a_verb, GpActivity activity) throws Exception  {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "post";
		routerDescription.route_path = "\"/" + the_noun.getName() + "\"";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription update_implementation(GpVerb a_verb, GpActivity activity) throws Exception  {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "put";
		routerDescription.route_path = "\"/" + the_noun.getName() + "\"";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription delete_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "delete";
		routerDescription.route_path = "/^\\/" + the_noun.getName() + "\\/(\\d+)$/";
		routerDescription.string_route_path = the_noun.getName();
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}

	public ExpressRouterDescription search_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "get";
		routerDescription.route_path = "\"/" + the_noun.getName() + "/search" + "\"";
		routerDescription.string_route_path = the_noun.getName() + "/search";
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(a_verb.getAction_on_data());
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}
	
	public ExpressRouterDescription get_noun_by_parent_id_implementation(GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "get";
		routerDescription.route_path = "\"/" + the_noun.getName() + "/get_noun_by_parent_id" + "\"";
		routerDescription.string_route_path = the_noun.getName() + "/get_noun_by_parent_id";
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(GpBaseVerbsConstants.GpGetNounByParentId);
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}
	
	public ExpressRouterDescription delete_by_parent_id_implementation(GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ExpressRouterDescription routerDescription = new ExpressRouterDescription();
		routerDescription.http_method = "delete";
		routerDescription.route_path = "\"/" + the_noun.getName() + "/delete_by_parent_id" + "\"";
		routerDescription.string_route_path = the_noun.getName() + "/delete_by_parent_id";
		Map<String, ControllerFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_controller_worker().getThe_gen_support().get_the_map_methods(activity);
		ControllerFunctionDescription controllerFunctionDescription = the_map.get(GpBaseVerbsConstants.GpDeleteByParentId);
		routerDescription.controller_call = controllerFunctionDescription.function_name;
		return routerDescription;
	}
	
	public GpExpressRoutesGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	public void setThe_gen_support(GpExpressRoutesGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
}
