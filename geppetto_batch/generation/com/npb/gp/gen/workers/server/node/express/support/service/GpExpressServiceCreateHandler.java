package com.npb.gp.gen.workers.server.node.express.support.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
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
 * action on data: GpCreate</p>
 */

@Component("GpExpressServiceCreateHandler")
public class GpExpressServiceCreateHandler 
extends GpBaseExpressServiceVerbFunctionHandler
						implements IGpExpressServiceVerbGenSupport {
	
	String function_name = "create";
	private GpExpressServiceGenSupport the_gen_support;
	
	public GpExpressServiceGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	public void setThe_gen_support(GpExpressServiceGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
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
		the_dto.function_callback_parameters = the_noun.getName().toLowerCase();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(verb.getAction_on_data());
		the_dto.dao_call = daoFunctionDescription.function_name;
		the_dto.dao_function_parameters = daoFunctionDescription.function_parameters;
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.callback_paramaters = the_noun.getName().toLowerCase();
	}
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "create_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(verb.getAction_on_data());
		the_dto.function_parameters = daoFunctionDescription.function_parameters;
		
	}
}
