package com.npb.gp.gen.workers.client.js.angular.support.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GCD.GCDConstants;
import com.npb.gp.gen.interfaces.angularjs.IGpAngularControllerVerbGenSupport;
import com.npb.gp.gen.json.mappers.gcd.GCDJsonKeys;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;

@Component("GpAngularComponentVerbHandler")
public class GpAngularComponentVerbHandler extends GpBaseAngularControllerVerbFunctionHandler 
			implements IGpAngularControllerVerbGenSupport{
	private JSONObject json;
	private String verb_name;
	private String verb_type;
	@Override
	public GpAngularControllerGenDto handle_verb(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("VERB COMPONENT");
		String json_string = verb.getActual_information();
		json = new JSONObject(json_string);
		this.handle_json_values();
		this.gp_start(verb, activity);
		this.gp_server_post(verb, activity);
		this.gp_end(verb);
		return this.the_dto;
	}
	
	private void handle_json_values() throws JSONException{
		verb_name = json.getString(GCDJsonKeys.VERB_NAME);
		verb_type = json.getString(GCDJsonKeys.VERB_TYPE);
	}
	
	@Override
	public void get_function_signiture(GpVerb verb) throws Exception {

		this.the_dto.scope_service = "$scope";
		this.the_dto.function_name = verb_name;
		String input_parameters_string = "";
		JSONArray input_parameters = json.getJSONArray(GCDJsonKeys.VERB_INPUT_PARAMETERS);
		for(int i=0;i<input_parameters.length();i++){
			JSONObject input_parameter = input_parameters.getJSONObject(i);
			String input_parameter_name = input_parameter.getString(GCDJsonKeys.VERB_INPUT_PARAMETER_NAME);
			input_parameters_string += input_parameter_name + ",";
		}
		input_parameters_string = input_parameters_string.substring(0,input_parameters_string.length()-1);
		this.the_dto.function_parms = input_parameters_string;
	
	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		this.get_function_signiture(verb);
		this.the_dto.gp_start_code = "//this is where the start code goes";
	}
	
	@Override
	public void gp_server_post(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		if(verb_type.equals(GCDConstants.ANGULAR_CLIENT_SERVICE)){
			//the verb is used by an angular service
			//import it to the controller
			if(json.has(GCDJsonKeys.VERB_CLIENT_SPECIFICATION)){
				JSONObject verb_client_specification = json.getJSONObject(GCDJsonKeys.VERB_CLIENT_SPECIFICATION);
				String name_of_the_angular_service = verb_client_specification.getString(GCDJsonKeys.ANGULAR_SERVICE_NAME);
				this.getThe_worker().add_dependent_services_for_desktop("," + name_of_the_angular_service);
				JSONObject angular_service_specification = verb_client_specification.getJSONObject(GCDJsonKeys.ANGULAR_SERVICE_SPECIFICATION);
				String angular_service_method_name = angular_service_specification.getString(GCDJsonKeys.ANGULAR_SERVICE_METHOD_NAME);
				this.the_dto.gp_server_post_code = name_of_the_angular_service 
						+ "." + angular_service_method_name
						+ "(" + this.the_dto.function_parms + ");";
						
			}
		}
		
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "";
	}
	

}
