package com.npb.gp.gen.workers.server.node.express.support.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.interfaces.express.controller.IGpExpressControllerVerbGenSupport;

@Component("GpExpressControllerDeleteByParentIdHandler")
public class GpExpressControllerDeleteByParentIdHandler extends
GpBaseExpressControllerVerbFunctionHandler implements
IGpExpressControllerVerbGenSupport {
private GpExpressControllerGenSupport the_gen_support;
	
	
	public void setThe_gen_support(GpExpressControllerGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	
	public GpExpressControllerGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	public ControllerFunctionDescription handle_implicit_verb(GpVerb verb,
			GpActivity activity) throws Exception {
		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(
							GpBaseVerbsConstants.GpDeleteByParentId, 
							GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpController);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpVariable_statement")){
						this.gp_variable_statement( verb,activity);
					}else if(mcr_flow.getAction().equals("GpServiceCall")){
						this.gp_service_call(verb, activity);
					}else if(mcr_flow.getAction().equals("GpReturn")){
						this.gp_return(verb, activity);
					}else if(mcr_flow.getAction().equals("GpEnd")){
						this.gp_end(verb);
					}
				}
			return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			//System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ControllerFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {
		return null;
	}
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "delete_" + the_noun.getName() + "_by_parent_id";
		
	}
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	@Override
	public void gp_service_call(GpVerb verb,GpActivity activity) throws Exception {
		
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(GpBaseVerbsConstants.GpDeleteByParentId);
		the_dto.service_call = serviceFunctionDescription.function_name;
		the_dto.service_parameters = serviceFunctionDescription.function_parameters;
		the_dto.function_callback_parameters = null;
		
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		String return_statements = "res.status(204);\n"	+ "res.end();";
		the_dto.return_statements = return_statements;
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		
	}
	
	@Override
	public void gp_variable_statement(GpVerb verb,GpActivity activity) throws JSONException{
		GpNoun the_noun = activity.getPrimary_noun();
		JSONArray json_array_parents = this.the_gen_support.getThe_worker().getGen_service().getRelationships_map().get(the_noun.getId());
		String variable_statements = "";
		if(json_array_parents != null){
			for(int i=0;i<json_array_parents.length();i++){
				JSONObject json_parent = json_array_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				variable_statements += "var " + name +" = req.query." + name + " || \"%%\";\n";
			}
		}
		the_dto.variable_statements = variable_statements;
	}

}
