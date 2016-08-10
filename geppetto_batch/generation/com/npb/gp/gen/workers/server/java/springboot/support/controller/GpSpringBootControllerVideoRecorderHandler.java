package com.npb.gp.gen.workers.server.java.springboot.support.controller;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.controller.IGpSpringBootControllerVerbGenSupport;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;


/**
 * 
 * @author Dhinakar P
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpRecordVideo</p>
 */

@Component("GpSpringBootControllerVideoRecorderHandler")
public class GpSpringBootControllerVideoRecorderHandler 
extends GpBaseSpringBootControllerVerbFunctionHandler
						implements IGpSpringBootControllerVerbGenSupport {
	
	String function_name = "recordVideo";
	private GpSpringBootControllerUpdateHandler the_update_handler;
	private GpSpringBootControllerCreateHandler the_create_handler;
	private List<GpControllerSpringBootVerbGenInfo> the_implicit_verbs;
	
	
	public GpSpringBootControllerCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	@Resource(name = "GpSpringBootControllerCreateHandler")
	public void setThe_create_handler(GpSpringBootControllerCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}
	
	public GpSpringBootControllerUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	@Resource(name = "GpSpringBootControllerUpdateHandler")
	public void setThe_update_handler(GpSpringBootControllerUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}
	public void setThe_implicit_verbs(List<GpControllerSpringBootVerbGenInfo> the_implicit_verbs) {
		this.the_implicit_verbs = the_implicit_verbs;
	}
	public List<GpControllerSpringBootVerbGenInfo> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
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
			System.out.println("---GpController----"+GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpController+"---------vrb id-"+verb.getId());
			the_implicit_verbs = new ArrayList<>();
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
			.find_by_verb_id_and_component_type(verb.getId(),GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpController);
		
	if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
				
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity,service_methods);
					}else if(mcr_flow.getAction().equals("GpDeclareNoun")){
						//System.out.println("--GpDeclareNoun---start");
						this.gp_declare_noun( verb,activity,service_methods);
						//System.out.println("--GpDeclareNoun---end--");
					}else if(mcr_flow.getAction().equals("GpServiceCall")){
						//System.out.println("--GpServiceCall---start");
						this.gp_service_call(verb, activity, service_methods);
						//System.out.println("--GpServiceCall---end");
					}else if(mcr_flow.getAction().equals("GpReturn")){
						//System.out.println("--GpReturn---start");
						this.gp_return(verb, activity, service_methods);
						//System.out.println("--GpReturn---end");
					}else if(mcr_flow.getAction().equals("GpEnd")){
						//System.out.println("--GPEnd---start");
						this.gp_end(verb);
						//System.out.println("--GpEnd---end");
					}
					
				}
				
				this.handle_implicit_verbs(verb, activity,service_methods);
			return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}

	}
	
	
	private void handle_implicit_verbs(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		//search for update implicit
		the_implicit_verbs.add(this.gp_update(verb, activity,service_methods));
		the_implicit_verbs.add(this.gp_create(verb, activity,service_methods));
		//
	}
	
	private GpControllerSpringBootVerbGenInfo gp_create(GpVerb verb, GpActivity activity,
			HashMap<String, GpServiceVerbGenInfo> service_methods) {
		GpControllerSpringBootVerbGenInfo create = the_create_handler.handle_implicit_verb(verb, activity,service_methods);
		create.verb_action_on_data = GpBaseVerbsConstants.GpCreate;
		return create;
	}
	
	private GpControllerSpringBootVerbGenInfo gp_update(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		GpControllerSpringBootVerbGenInfo search_for_update = the_update_handler.handle_implicit_verb(verb, activity,service_methods);
		search_for_update.verb_action_on_data = GpBaseVerbsConstants.GpUpdate;
		return search_for_update;
	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		this.get_function_signiture( verb,activity,service_methods);
		this.the_dto.gp_start_code = "//this is where the start code goes" + "\n";
	}
	@Override
	public void gp_declare_noun(GpVerb verb, GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		GpJavaMethodDescription method_description =  service_methods
				.get("GpCreate").signiture_helper;
		//System.out.println("signature --------in declare noun---"+verb.getAction_on_data());
		
		String set_video_method = null;
		for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
			//System.out.println("in for loop===");
			if(noun.getSubtype().equals("Video")){
				//System.out.println("---noun.getName() --"  + noun.getName());
				String attr_name = noun.getName().toLowerCase();
				attr_name = attr_name.substring(0, 1).toUpperCase() + attr_name.substring(1);
				set_video_method ="set"+attr_name;
			}
			System.out.println("---set_video_method----"+set_video_method);
		}
		
		
		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();
				the_dto.declare_noun = the_dto.return_type
						+ " " + "a_" + activity.getPrimary_noun().getName()+" =new "+activity.getPrimary_noun().getName()+"();"+
						"\n"+ "a_"+activity.getPrimary_noun().getName()+"."+ set_video_method +"(uploadfile.getBytes())";
				//System.out.println("--the_dto.declare_noun----"+the_dto.declare_noun); 
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
		System.out.println("verb.getAction_on_data()- service call-" + verb.getAction_on_data());
		GpJavaMethodDescription method_description =  service_methods
				.get("GpCreate").signiture_helper;

		// START - SERVICE CALL
				int i = 0;
				if (method_description.getInput_parms().size() == 0) {
					the_dto.service_call = activity.getPrimary_noun().getName()
							+ "_list = " + activity.getName() + "_" + "service" + "."
							+ method_description.getName() + "()";
					
					for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
						if(noun.getSubtype().equals("Video")){
							the_dto.declare_noun +="."+"set("+activity.getPrimary_noun().getName()+")";
						}
					} 
				} else {
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
										+ activity.getPrimary_noun().getName() + "_id"; 
								// how do you know the parm name is ID?
							} else if (parm.name.equals("GpUser")) {
								parm_string = parm_string + "super.getUser()";
							} else if (parm.name.equals("GpPrimaryNoun")) {
								parm_string = parm_string + "a_"
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
		the_dto.return_reference = "return " + "a_"	+ activity.getPrimary_noun().getName();
		
	}
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity,HashMap<String, GpServiceVerbGenInfo> service_methods) throws Exception {
		
		if(verb.getDescription().equals("")){
			the_dto.method_notes = "The description has been left blank";
		}else{
			the_dto.method_notes = verb.getDescription() +" of "+ activity.getPrimary_noun().getName();
		}
		the_dto.method_response_clazz_name = activity.getPrimary_noun().getName();
		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "POST";
		the_dto.request_map_value = "uploadVideo";
		the_dto.reference_request_map_value = "uploadVideo";
		the_dto.method_name = "uploadVideo";
		the_dto.input_parms = "@RequestParam( \"uploadfile\") MultipartFile uploadfile";
		//System.out.println("verb action-----------"+verb.getAction_on_data());
		//System.out.println("servie method----"+service_methods.get(verb.getAction_on_data())); 
		//System.out.println("sig-------"+service_methods.get(verb.getAction_on_data()).signiture_helper); 
		GpJavaMethodDescription method_description = service_methods.get("GpCreate").signiture_helper;
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
