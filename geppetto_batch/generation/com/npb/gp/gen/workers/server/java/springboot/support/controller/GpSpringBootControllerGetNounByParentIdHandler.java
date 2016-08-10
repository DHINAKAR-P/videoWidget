package com.npb.gp.gen.workers.server.java.springboot.support.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.controller.IGpSpringBootControllerVerbGenSupport;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;

@Component("GpSpringBootControllerGetNounByParentIdHandler")
public class GpSpringBootControllerGetNounByParentIdHandler extends
GpBaseSpringBootControllerVerbFunctionHandler implements
IGpSpringBootControllerVerbGenSupport{

	String function_name = "get_by_parent";
	private boolean is_one_to_many;
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
			return null;
	}
	
	public GpControllerSpringBootVerbGenInfo handle_implicit_verb(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {

		try {
			JSONArray json_parents = this.getThe_worker().getRelationships_map().get(activity.getPrimary_noun().getId());
			is_one_to_many = false;
			if(json_parents != null){
				for(int i=0;i<json_parents.length(); i++){
					JSONObject json_parent = json_parents.getJSONObject(i);
					String type = json_parent.getString("type");
					if(type.equals("ONE_TO_MANY"))
						is_one_to_many = true;
				}
			}
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(GpBaseVerbsConstants.GpGetNounByParentId,
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
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		this.get_function_signiture( verb,activity,service_methods);
		this.the_dto.gp_start_code = "//this is where the start code goes" + "\n";
	}
	@Override
	public void gp_declare_noun(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		GpJavaMethodDescription method_description =  service_methods
				.get(GpBaseVerbsConstants.GpGetNounByParentId).signiture_helper;
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
		GpJavaMethodDescription method_description =  service_methods
				.get(GpBaseVerbsConstants.GpGetNounByParentId).signiture_helper;
						
		// START - SERVICE CALL
				int i = 0;
				if (method_description.getInput_parms().size() == 0) {
					the_dto.service_call = activity.getPrimary_noun().getName()
							+ "_list = " + activity.getName() + "_" + "service" + "."
							+ method_description.getName() + "()";
				} else {
					if(is_one_to_many)
						the_dto.service_call = activity.getPrimary_noun().getName() + "_list = " 
						+ activity.getName() + "_" + "service" + "."
						+ method_description.getName() + "(";
					else	
						the_dto.service_call = "a_" + activity.getPrimary_noun().getName()
							+ " = " + activity.getName() + "_" + "service" + "."
							+ method_description.getName() + "(";
					String parm_string = "";
					for (GpDataType parm : method_description.getInput_parms()) {
						if (i > 0) {
							parm_string = parm_string + ", ";
						}
						if (!parm.container) {
							if (parm.name.equals("GpLong")) {
								parm_string = parm_string
										+ parm.base_name; 
								// how do you know the parm name is ID?
							} else if (parm.name.equals("GpUser")) {
								parm_string = parm_string + "super.getUser()";
							} else if (parm.name.equals("GpPrimaryNoun")) {
								parm_string = parm_string + "the_"
										+ activity.getPrimary_noun().getName();
							}
						}
						i++;
					}
					parm_string = parm_string + ")";
					the_dto.service_call = the_dto.service_call + parm_string;

				}
				// END - SERVICE CALL
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods)throws Exception {
		if(is_one_to_many)
			the_dto.return_reference = "return " + activity.getPrimary_noun().getName() + "_list";
		else
			the_dto.return_reference = "return " + "a_"	+ activity.getPrimary_noun().getName();
		
	}
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		
		the_dto.method_notes = "get by parent id of "+ activity.getPrimary_noun().getName();
		the_dto.method_response_clazz_name = activity.getPrimary_noun().getName();
		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.request_map_value = "get_" + activity.getPrimary_noun().getName() + "_by_parent_id";
		the_dto.reference_request_map_value = "get_" + activity.getPrimary_noun().getName() + "_by_parent_id";
		the_dto.method_name = "get_" + activity.getPrimary_noun().getName() + "_by_parent_id";
		String input_params_string = "";
		JSONArray json_parent = getThe_worker().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_parent != null){
			for(int i=0;i<json_parent.length();i++){
				JSONObject json = json_parent.getJSONObject(i);
				String name = json.getString("Noun_name") + "_id";
				input_params_string += "@RequestParam(required = false, value = \""
						+ name
						+ "\", defaultValue = \"0\") long "
						+ name + ",";
			}
			input_params_string = input_params_string.substring(0,
					input_params_string.length() - 1);
		}
		
		the_dto.input_parms = input_params_string;
		GpJavaMethodDescription method_description = service_methods.get(GpBaseVerbsConstants.GpGetNounByParentId).signiture_helper;
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
