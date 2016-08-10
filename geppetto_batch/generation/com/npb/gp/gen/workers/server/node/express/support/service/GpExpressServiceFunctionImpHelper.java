package com.npb.gp.gen.workers.server.node.express.support.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;

@Component("GpExpressServiceFunctionImpHelper")
public class GpExpressServiceFunctionImpHelper {
	private GpExpressServiceGenSupport the_gen_support;

	public ServiceFunctionDescription get_all_values_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_name = "get_all_" + the_noun.getName();
		serviceFunctionDescription.function_parameters = null;
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription get_nound_by_id_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_name = "get_" + the_noun.getName() + "_by_id";
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription search_for_update_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_name = "search_" + the_noun.getName() + "_for_update";
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription;
		if(a_verb == null)
			daoFunctionDescription = dao_map.get("GpSearchForUpdate");
		else
			daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription create_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_name = "create_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription update_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = null;
		serviceFunctionDescription.function_callback_parameters = null;
		serviceFunctionDescription.function_name = "update_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription delete_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = null;
		serviceFunctionDescription.function_callback_parameters = null;
		serviceFunctionDescription.function_name = "delete_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}

	public ServiceFunctionDescription search_implementation(GpVerb a_verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		ServiceFunctionDescription serviceFunctionDescription = new ServiceFunctionDescription();
		serviceFunctionDescription.callback_paramaters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_callback_parameters = the_noun.getName().toLowerCase();
		serviceFunctionDescription.function_name = "search_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(a_verb.getAction_on_data());
		serviceFunctionDescription.dao_call = daoFunctionDescription.function_name;
		serviceFunctionDescription.dao_function_parameters = daoFunctionDescription.function_parameters;
		serviceFunctionDescription.function_parameters = daoFunctionDescription.function_parameters;
		return serviceFunctionDescription;
	}
	
	public GpExpressServiceGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	public void setThe_gen_support(GpExpressServiceGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}

}
