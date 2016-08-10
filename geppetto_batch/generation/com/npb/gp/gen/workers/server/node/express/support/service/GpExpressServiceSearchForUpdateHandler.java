package com.npb.gp.gen.workers.server.node.express.support.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.interfaces.express.service.IGpExpressServiceVerbGenSupport;
/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpSearchForUpdate</p>
 */
@Component("GpExpressServiceSearchForUpdateHandler")
public class GpExpressServiceSearchForUpdateHandler extends
GpBaseExpressServiceVerbFunctionHandler implements
IGpExpressServiceVerbGenSupport {
	
	private GpExpressServiceGenSupport the_gen_support;
	
	public GpExpressServiceGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	public void setThe_gen_support(GpExpressServiceGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}

	@Override
	public ServiceFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpService);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpDaoCall")){
						this.gp_dao_call(verb, activity);
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
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	
	@Override
	public void gp_dao_call(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription;
		daoFunctionDescription = dao_map.get(GpBaseVerbsConstants.GpSearchForUpdate);
		the_dto.dao_call = daoFunctionDescription.function_name;
		the_dto.dao_function_parameters = daoFunctionDescription.function_parameters;
		the_dto.function_parameters = daoFunctionDescription.function_parameters;
		the_dto.function_callback_parameters = the_noun.getName().toLowerCase();
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.callback_paramaters = the_noun.getName().toLowerCase();
		
		Map<Long, GpRelationshipInfo> map_relations = this.the_gen_support.getThe_worker().getGen_service().getRelation_between_activities();
		if(map_relations != null){
			String custom_code = "";
			GpRelationshipInfo rel_info = map_relations.get(activity.getId());
			if(rel_info != null){
				List<GpChildRelationshipInfo> childs = rel_info.getChilds();
				custom_code += "var json_"+ the_noun.getName().toLowerCase() +" = JSON.parse(JSON.stringify("+ the_noun.getName().toLowerCase() +"));\n";
				custom_code += "var cont = 0;\n";
				for(GpChildRelationshipInfo child: childs){
					ServiceFunctionDescription node_service_descript = the_gen_support.getThe_get_noun_by_parent_id_handler().handle_implicit_verb(null, child.getActivity());
					custom_code += child.getActivity().getPrimary_noun().getName() + "_service." + node_service_descript.function_name + "(";
					String function_parameters[] = node_service_descript.function_parameters.split(","); 
					for(int i = 0; i< function_parameters.length; i++){
						String noun_with_id_suffix = activity.getPrimary_noun().getName() + "_id";
						if(noun_with_id_suffix.equals(function_parameters[i])){
							custom_code += "json_"+ the_noun.getName().toLowerCase() +".id,";
						}
						else{
							custom_code += "'%%',";
						}
					}
					custom_code += "function ("+child.getActivity().getPrimary_noun().getName().toLowerCase()+"){\n";
					custom_code += "\t" + "cont++;\n";
					custom_code += "\t" + "json_"+ the_noun.getName().toLowerCase() +"."+child.getActivity().getPrimary_noun().getName().toLowerCase()+" = "+child.getActivity().getPrimary_noun().getName().toLowerCase()+";\n";
					custom_code += "\t" + "if(cont == 1*"+ childs.size() +")\n";
					custom_code += "\t\t" + "callback(json_"+ the_noun.getName().toLowerCase() +");\n";
					custom_code += "});\n";
				}
				
			}
			if(!custom_code.isEmpty())
				the_dto.custom_code = custom_code;
		}
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {}
	
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "search_" + the_noun.getName() + "_for_update";
	}
	
	public ServiceFunctionDescription handle_implicit_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(GpBaseVerbsConstants.GpSearchForUpdate,
							GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpService);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpDaoCall")){
						this.gp_dao_call(verb, activity);
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

}