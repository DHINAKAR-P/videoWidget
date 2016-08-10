package com.npb.gp.gen.workers.server.java.springboot.support.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.controller.IGpSpringBootControllerVerbGenSupport;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpSearch</p>
 */
@Component("GpSpringBootControllerSearchHandler")
public class GpSpringBootControllerSearchHandler 
		extends GpBaseSpringBootControllerVerbFunctionHandler implements
			IGpSpringBootControllerVerbGenSupport{
	
	String function_name = "search";
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	@Override
	public GpControllerSpringBootVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpController);
					
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity,service_methods);
					}else if(mcr_flow.getAction().equals("GpDeclareNoun")){
						this.gp_declare_noun(verb, activity,service_methods);
					}else if(mcr_flow.getAction().equals("GpServiceCall")){
						this.gp_service_call(verb, activity, service_methods);
					}else if(mcr_flow.getAction().equals("GpReturn")){
						this.gp_return(verb, activity, service_methods);
					}
					else if(mcr_flow.getAction().equals("GpEnd")){
						this.gp_end(verb);
					}
				}
			return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}
	}
	
		
	@Override
	public void gp_start(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		this.get_function_signiture(verb,activity,service_methods);
		this.the_dto.gp_start_code = "//this is where the start code goes";
	}
	
	@Override
	public void gp_declare_noun(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		GpJavaMethodDescription method_description = service_methods
				.get(verb.getAction_on_data()).signiture_helper;
		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();
				the_dto.declare_noun = the_dto.return_type
						+ " " + "a_" + activity.getPrimary_noun().getName();
			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";
				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.declare_noun = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference
	}
	
	@Override
	public void gp_service_call(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		GpJavaMethodDescription method_description = service_methods
				.get(verb.getAction_on_data()).signiture_helper;
		// START - SERVICE CALL
		if (method_description.getInput_parms().size() > 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			ArrayList<GpDataType> input_params = method_description
					.getInput_parms();
			for (GpDataType input_param : input_params) {
				the_dto.service_call += input_param.base_name + ",";
			}
			the_dto.service_call = the_dto.service_call.substring(0,
					the_dto.service_call.length() - 1);
			the_dto.service_call += ")";
		}
		// END - SERVICE CALL

	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods)throws Exception {
		
		the_dto.return_reference = "return "+ activity.getPrimary_noun().getName() + "_list";
		
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes";
	}
		
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {

		if (verb.getDescription().equals("")) {
			the_dto.method_notes = "The description has been left blank";
		} else {
			the_dto.method_notes = verb.getDescription() + " of "
					+ activity.getPrimary_noun().getName();
		}
		the_dto.method_response_clazz_name = activity.getPrimary_noun()
				.getName();
		the_dto.accept_type = ",headers=\"Accept=application/json\"";

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.request_map_value = "search_" + activity.getPrimary_noun().getName();
		the_dto.reference_request_map_value = "search_" + activity.getPrimary_noun().getName();
		the_dto.method_name = "search_" + activity.getPrimary_noun().getName();
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();
		String input_params_string = "";
		for (GpNounAttribute attribute : attribs) {
			if(attribute.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attribute.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST))
				continue;
			input_params_string += "@RequestParam(required = false, value = \""
					+ attribute.getName().toLowerCase()
					+ "\", defaultValue = \"%%\") String "
					+ attribute.getName().toLowerCase() + ",";
		}
		input_params_string = input_params_string.substring(0,
				input_params_string.length() - 1);
		the_dto.input_parms = input_params_string;

		GpJavaMethodDescription method_description = service_methods
				.get(verb.getAction_on_data()).signiture_helper;

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION
	}
}
