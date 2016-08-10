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
 * action on data: GpUpdate</p>
 */
@Component("GpExpressServiceUpdateHandler")
public class GpExpressServiceUpdateHandler extends 
                     GpBaseExpressServiceVerbFunctionHandler implements
                     IGpExpressServiceVerbGenSupport {
	
	String function_name = "update";
	private GpExpressServiceSearchForUpdateHandler the_search_for_update_handler;
	private GpExpressServiceGenSupport the_gen_support;
	private List<ServiceFunctionDescription> the_implicit_verbs;

	@Override
	public ServiceFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			the_implicit_verbs = new ArrayList<>();
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
				this.handle_implicit_verbs(verb, activity);
			return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}

	}

	private void handle_implicit_verbs(GpVerb verb, GpActivity activity) throws Exception {
		//search for update implicit
		the_implicit_verbs.add(this.gp_search_for_update(verb, activity));
		//
	}
	private ServiceFunctionDescription gp_search_for_update(GpVerb verb, GpActivity activity) throws Exception {
		ServiceFunctionDescription search_for_update = the_search_for_update_handler.handle_implicit_verb(verb, activity);
		search_for_update.verb_action_on_data = GpBaseVerbsConstants.GpSearchForUpdate;
		return search_for_update;
	}
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity)throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "update_" + the_noun.getName();
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(verb.getAction_on_data());
		the_dto.function_parameters = daoFunctionDescription.function_parameters;
	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
		
	@Override
	public void gp_dao_call(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_callback_parameters = null;
		the_dto.function_name = "update_" + the_noun.getName();
		
		Map<String, DaoFunctionDescription> dao_map = getThe_gen_support().getThe_worker().getGen_service().getThe_dao_worker().getThe_gen_support().get_the_map_methods(activity);
		DaoFunctionDescription daoFunctionDescription = dao_map.get(verb.getAction_on_data());
		the_dto.dao_call = daoFunctionDescription.function_name;
		the_dto.dao_function_parameters = daoFunctionDescription.function_parameters;
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		the_dto.callback_paramaters = null;
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {}

	public GpExpressServiceSearchForUpdateHandler getThe_search_for_update_handler() {
		return the_search_for_update_handler;
	}
	
	@Resource(name = "GpExpressServiceSearchForUpdateHandler")
	public void setThe_search_for_update_handler(GpExpressServiceSearchForUpdateHandler the_search_for_update_handler) {
		this.the_search_for_update_handler = the_search_for_update_handler;
	}
	
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
	
	public List<ServiceFunctionDescription> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
}