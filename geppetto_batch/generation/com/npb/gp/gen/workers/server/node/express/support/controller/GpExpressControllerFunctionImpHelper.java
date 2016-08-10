package com.npb.gp.gen.workers.server.node.express.support.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;

@Component("GpExpressControllerFunctionImpHelper")
public class GpExpressControllerFunctionImpHelper {
	private GpExpressControllerGenSupport the_gen_support;
	
	public void setThe_gen_support(GpExpressControllerGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	
	public GpExpressControllerGenSupport getThe_gen_support() {
		return the_gen_support;
	}

	public ControllerFunctionDescription get_all_values_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		
		GpNoun the_noun = activity.getPrimary_noun();
		
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		
		controllerFunctionDescription.function_name = "get_all_" + the_noun.getName();
		
		controllerFunctionDescription.variable_statements = null;
		
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		
		controllerFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		
		String return_statements = "res.json("+ the_noun.getName().toLowerCase() +");";
		
		controllerFunctionDescription.return_statements = return_statements;
		
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription get_nound_by_id_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "get_" + the_noun.getName() + "_by_id";
		String variable_statements = "var " + the_noun.getName() + "Id = parseInt(req.params[0], 10);";
		controllerFunctionDescription.variable_statements = variable_statements;
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		String return_statements = "res.json("+ the_noun.getName().toLowerCase() +");";
		controllerFunctionDescription.return_statements = return_statements;
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription search_for_update_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "search_" + the_noun.getName() + "_for_update";
		String variable_statements = "var " + the_noun.getName() + "Id = parseInt(req.params[0], 10);";
		controllerFunctionDescription.variable_statements = variable_statements;
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription;
		if(a_verb == null)
			serviceFunctionDescription = the_map.get("GpSearchForUpdate");
		else
			serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		String return_statements = "res.json("+ the_noun.getName().toLowerCase() +");";
		controllerFunctionDescription.return_statements = return_statements;
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription create_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "create_" + the_noun.getName();
		
		String variable_statements = "var " + the_noun.getName() + " = req.body;";
		controllerFunctionDescription.variable_statements = variable_statements;
		
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		
		String return_statements = "res.status(201);\n"
								+ "res.json("+ the_noun.getName().toLowerCase() +");";
		controllerFunctionDescription.return_statements = return_statements;
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription update_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "update_" + the_noun.getName();
		String variable_statements = "var " + the_noun.getName() + " = req.body;";
		controllerFunctionDescription.variable_statements = variable_statements;
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = null;
		String return_statements = "res.end();";
		controllerFunctionDescription.return_statements = return_statements;
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription delete_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "delete_" + the_noun.getName();
		
		String variable_statements = "var " + the_noun.getName() + "Id = parseInt(req.params[0], 10);";
		controllerFunctionDescription.variable_statements = variable_statements;
		
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = null;
		
		String return_statements = "res.status(204);\n"
								+ "res.end();";
		controllerFunctionDescription.return_statements = return_statements;
		
		return controllerFunctionDescription;
	}

	public ControllerFunctionDescription search_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ControllerFunctionDescription controllerFunctionDescription = new ControllerFunctionDescription();
		controllerFunctionDescription.function_name = "search_" + the_noun.getName();
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String variable_statements = "";
		for(GpNounAttribute attr : attrs){
			variable_statements += "var " + attr.getName().toLowerCase() + " = req.query." + attr.getName().toLowerCase() + " || \"%%\";\n"; 
		}
		 
		controllerFunctionDescription.variable_statements = variable_statements;
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(a_verb.getAction_on_data());
		controllerFunctionDescription.service_call = serviceFunctionDescription.function_name;
		controllerFunctionDescription.service_parameters = serviceFunctionDescription.function_parameters;
		controllerFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		String return_statements = "res.json(" + the_noun.getName().toLowerCase() + ");";
		controllerFunctionDescription.return_statements = return_statements;
		return controllerFunctionDescription;
	}
}
