package com.npb.gp.gen.workers.server.java.springboot.support.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.service.IGpSpringBootServiceVerbGenSupport;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootServiceGenWorker;
/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/28/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generates the function that handles the verb</br>
 * action on data: GpGetNounById</p>
 */
@Component("GpSpringBootServiceGetNounByIdHandler")
public class GpSpringBootServiceGetNounByIdHandler extends
GpBaseSpringBootServiceVerbFunctionHandler implements
IGpSpringBootServiceVerbGenSupport {
	private GpServiceSpringBootVerbMethodSignitures verb_signiture_hlpr;

	private GpSpringBootServiceGenWorker the_worker;

	public GpSpringBootServiceGenWorker getThe_worker() {
		return the_worker;
	}

	@Resource(name = "GpSpringBootServiceGenWorker")
	public void setThe_worker(GpSpringBootServiceGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpServiceSpringBootVerbMethodSignitures getVerb_signiture_hlpr() {
		return verb_signiture_hlpr;
	}

	@Resource(name = "GpServiceSpringBootVerbMethodSignitures")
	public void setVerb_signiture_hlpr(
			GpServiceSpringBootVerbMethodSignitures verb_signiture_hlpr) {
		this.verb_signiture_hlpr = verb_signiture_hlpr;
	}
	@Override
	public GpServiceVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			this.the_dto.signiture_helper = this.verb_signiture_hlpr
					.get_nound_by_id_method_signiture(activity);
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpService);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpDeclareNoun")){
						this.gp_declare_noun(verb,activity);
					}else if(mcr_flow.getAction().equals("GpDaoCall")){
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
			System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity
			) throws Exception {
		this.get_function_signiture(verb,activity);
		this.the_dto.gp_start_code = "//this is where the start code goes";
	}
	
	@Override
	public void gp_declare_noun(GpVerb verb, GpActivity activity
			) throws Exception {
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
		// handle return type
		GpDataType return_parm = method_sig_info.getReturn_parm();
		if (!return_parm.container) {
			if (return_parm.name.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();
				the_dto.return_reference = "the_"
						+ activity.getPrimary_noun().getName();
			}

		} else {
			if (return_parm.name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";
				if (return_parm.base_name.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
					the_dto.return_reference = activity.getPrimary_noun()
							.getName();
				}
			}
		}
		
		
	}
	@Override
	public void gp_service_call(GpVerb verb,GpActivity activity) throws Exception {
		HashMap<String, GpSpringBootDaoVerbGenInfo> dao_sigs = this.the_worker
				.get_spring_boot_gen_service().getSpring_boot_dao_gen_worker()
				._get_method_signitures(activity);

		GpJavaMethodDescription dao_method_sig_info = dao_sigs
				.get(GpBaseVerbsConstants.GpGetNounById).signiture_helper;

		// GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
		ArrayList<GpTypeAndReference> dao_list = null;
		String dao_ref = "";
		if (this.the_worker.activity_references.size() == 1) {
			dao_list = this.the_worker.activity_references
					.get("all_dao_references");
			dao_ref = dao_list.get(0).reference;
		} else {
			// TBD as for now you would only have one DAO
		}
		// set up the implementation - DAO call
		if (dao_method_sig_info.getInput_parms().size() == 0) {
			the_dto.dao_call = the_dto.return_reference + " = " + dao_ref + "."
					+ dao_method_sig_info.getName();
			the_dto.dao_call = the_dto.dao_call + "()";
		} else {
			the_dto.dao_call = the_dto.return_reference + " = " + dao_ref + "."
					+ dao_method_sig_info.getName() + "(";

			int i = 0;
			for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
				if (parm.name.equals("GpLong")) {
					if (i > 0) {
						the_dto.dao_call = the_dto.dao_call + ", id";
					} else {
						the_dto.dao_call = the_dto.dao_call + "id";
					}

				} else if (parm.name.equals("GpUser")) {
					if (i > 0) {
						the_dto.dao_call = the_dto.dao_call + ", user";
					} else {
						the_dto.dao_call = the_dto.dao_call + "user";
					}

				}
				i++;
			}
		}
		the_dto.dao_call = the_dto.dao_call + ")";
		
		String post_dao = "";
		Map<Long, GpRelationshipInfo> map_relations = this.the_worker.get_spring_boot_gen_service().getMap_services_relationships();	
		if(map_relations != null){
			GpRelationshipInfo rel_info = map_relations.get(activity.getId());
			if(rel_info != null){
				List<GpChildRelationshipInfo> childs = rel_info.getChilds();
				for(GpChildRelationshipInfo child: childs){
					GpJavaMethodDescription java_method_descript = this.verb_signiture_hlpr.get_nound_by_parent_id_method_signiture(child.getActivity(), this.the_worker.getRelationships_map());  
					post_dao += the_dto.return_reference + ".set" + child.getNoun().getName() 
							+ "("
							+ child.getActivity().getName() + "Service." 
							+ java_method_descript.getName() + "(";
					ArrayList<GpDataType> input_parms = java_method_descript.getInput_parms();
					for(GpDataType input_param : input_parms){
						
						String noun_with_id_suffix = activity.getPrimary_noun().getName() + "_id";
						if(input_param.base_name.equals(noun_with_id_suffix)){
							post_dao += the_dto.return_reference + ".getId(),";
						}
						else{
							post_dao += "0,";
						}
					}
					post_dao = post_dao.substring(0,post_dao.length()-1);
					post_dao += ")"
							+ ");\n";
				}
				
			}
		}
		the_dto.post_dao = post_dao;
	}

	@Override
	public void gp_return(GpVerb verb, GpActivity activity) throws Exception {

		this.the_dto.gp_server_post_code = "//this is where the post code goes"+ "\n";
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes";
	}

	
	@Override
	public void get_function_signiture(GpVerb verb, GpActivity activity)
			throws Exception {

		// setup the method signature
		GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
				.get_nound_by_id_method_signiture(activity);

		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();

		// START SET UP EXCEPTION
		// service_exceptions =
		// (GpJavaException)method_sig_info.get("method_exception");
		int i = 0;
		for (String exc : method_sig_info.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}

	}

}
