package com.npb.gp.gen.workers.server.node.express.support.controller;

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
import com.npb.gp.gen.domain.js.node.express.ControllerFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.interfaces.express.controller.IGpExpressControllerVerbGenSupport;
/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpGetNounById</p>
 */
@Component("GpExpressControllerGetNounByIdHandler")
public class GpExpressControllerGetNounByIdHandler extends
GpBaseExpressControllerVerbFunctionHandler implements
IGpExpressControllerVerbGenSupport {
	private GpExpressControllerGenSupport the_gen_support;
	
	
	public void setThe_gen_support(GpExpressControllerGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	
	public GpExpressControllerGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	@Override
	public ControllerFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpController);
			

			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpVariable_statement")){
						this.gp_variable_statement( verb,activity);
					}else if(mcr_flow.getAction().equals("GpService_call")){
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
	public void gp_variable_statement(GpVerb verb, GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		String variable_statements = "var " + the_noun.getName() + "_id = parseInt(req.params[0], 10);";
		the_dto.variable_statements = variable_statements;
		
	}
	

	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	
	@Override
	public void gp_service_call(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		Map<String, ServiceFunctionDescription> the_map = the_gen_support.getThe_worker().getGen_service().getThe_service_worker().getThe_gen_support().get_the_map_methods(activity);
		ServiceFunctionDescription serviceFunctionDescription = the_map.get(verb.getAction_on_data());
		the_dto.service_call = serviceFunctionDescription.function_name;
		the_dto.service_parameters = serviceFunctionDescription.function_parameters;
		the_dto.function_callback_parameters = the_noun.getName().toLowerCase();
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		String return_statements = "res.json("+ the_noun.getName().toLowerCase() +");";
		the_dto.return_statements = return_statements;
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {}

	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "get_" + the_noun.getName() + "_by_id";
	}

}
