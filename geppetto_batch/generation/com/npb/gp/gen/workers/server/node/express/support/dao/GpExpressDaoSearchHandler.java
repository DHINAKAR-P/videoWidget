package com.npb.gp.gen.workers.server.node.express.support.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.interfaces.express.dao.IGpExpressDaoVerbGenSupport;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpSearch</p>
 */
@Component("GpExpressDaoSearchHandler")
public class GpExpressDaoSearchHandler 
		extends GpBaseExpressdDaoVerbFunctionHandler implements
			IGpExpressDaoVerbGenSupport{
	
	String function_name = "search";
	private GpExpressDaoGenSupport the_gen_support;

	public void setThe_gen_support(GpExpressDaoGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	public GpExpressDaoGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	@Override
	public DaoFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity
		) throws Exception {
		try {	
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpDao);
			
		
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpVerbKey")){
						this.gp_verb_key( verb,activity);
					}else if(mcr_flow.getAction().equals("GpSqlQuery")){
						this.gp_sql_query(verb, activity);
					}else if(mcr_flow.getAction().equals("GpReturn")){
						this.gp_return(verb, activity);						
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
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String function_parameters = "";
		String replacements = "";
		for(GpNounAttribute attr : attrs){
			replacements += "\n\t" + attr.getName().toLowerCase() + ": " + attr.getName().toLowerCase() + ",";
			function_parameters += attr.getName().toLowerCase() + ",";
		}
		the_dto.function_name = "search_" + the_noun.getName();
		the_dto.function_parameters = function_parameters;
	}
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	@Override
	public void gp_verb_key(GpVerb verb, GpActivity activity) throws Exception {
		the_dto.verb_key_name = GpSqlQueriesGenSupport.key_name_for_search;
	}
	
	@Override
	public void gp_sql_query(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		String function_parameters = "";
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String replacements = "";
		for(GpNounAttribute attr : attrs){
			replacements += "\n\t" + attr.getName().toLowerCase() + ": " + attr.getName().toLowerCase() + ",";
			function_parameters += attr.getName().toLowerCase() + ",";
		}
		replacements = replacements.substring(0,replacements.length()-1);
		the_dto.query_replacements = "replacements: {"
				+ replacements 
				+ "\n},"; 
		
		the_dto.sequelizeQueryType = "SELECT";
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.callback_return_parameters = the_noun.getName().toLowerCase();
		the_dto.query_return_parameters = the_noun.getName().toLowerCase();
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {}
		

}