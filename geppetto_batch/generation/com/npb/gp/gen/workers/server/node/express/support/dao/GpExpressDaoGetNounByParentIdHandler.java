package com.npb.gp.gen.workers.server.node.express.support.dao;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.interfaces.express.dao.IGpExpressDaoVerbGenSupport;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;

@Component("GpExpressDaoGetNounByParentIdHandler")
public class GpExpressDaoGetNounByParentIdHandler extends
GpBaseExpressdDaoVerbFunctionHandler implements
IGpExpressDaoVerbGenSupport {
	
	private GpExpressDaoGenSupport the_gen_support;

	public void setThe_gen_support(GpExpressDaoGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	public GpExpressDaoGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	@Override
	public DaoFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {
		return null;
	}
	
	public DaoFunctionDescription handle_implicit_verb(GpVerb verb, GpActivity activity){
		try {
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(GpBaseVerbsConstants.GpGetNounByParentId, 
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
			//System.out.println("########## verb name is: " + verb.getName());
			e.printStackTrace();
			return null;
		}
	}
	

	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "get_" + the_noun.getName() + "_by_parent_id_";
		JSONArray json_array_parents = this.the_gen_support.getThe_worker().getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		String function_parameters_string = "";
		if(json_array_parents != null){
			for(int i=0;i<json_array_parents.length();i++){
				JSONObject json_parent = json_array_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				function_parameters_string += name +",";
			}
		}
		the_dto.function_parameters = function_parameters_string; //no parameters for this
	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	@Override
	public void gp_verb_key(GpVerb verb, GpActivity activity) throws Exception {
		the_dto.verb_key_name = GpSqlQueriesGenSupport.key_name_for_get_by_parent_id;
	}
	
	@Override
	public void gp_sql_query(GpVerb verb,GpActivity activity) throws Exception {
		JSONArray json_array_parents = this.the_gen_support.getThe_worker().getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		String query_replacements_string = "";
		if(json_array_parents != null){
			query_replacements_string += "replacements: {";
			for(int i=0;i<json_array_parents.length();i++){
				JSONObject json_parent = json_array_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				query_replacements_string += "\n\t" + name +": "+ name +",";
			}
			query_replacements_string = query_replacements_string.substring(0,query_replacements_string.length()-1);
			query_replacements_string += "\n},";
		}
		the_dto.query_replacements = query_replacements_string; 
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
