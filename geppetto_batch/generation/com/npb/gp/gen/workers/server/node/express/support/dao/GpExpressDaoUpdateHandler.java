package com.npb.gp.gen.workers.server.node.express.support.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.domain.js.node.express.ServiceFunctionDescription;
import com.npb.gp.gen.interfaces.express.dao.IGpExpressDaoVerbGenSupport;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;
/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/27/2016</br>
 * @since 1.0</p>  
 * 
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpUpdate</p>
 */
@Component("GpExpressDaoUpdateHandler")
public class GpExpressDaoUpdateHandler extends 
                     GpBaseExpressdDaoVerbFunctionHandler implements
                     IGpExpressDaoVerbGenSupport {
	private List<DaoFunctionDescription> the_implicit_verbs;
	private GpExpressDaoSearchForUpdateHandler the_search_for_update_handler;
	String function_name = "update";
	@Override
	public DaoFunctionDescription handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			the_implicit_verbs = new ArrayList<>();
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
	
	private DaoFunctionDescription gp_search_for_update(GpVerb verb, GpActivity activity) throws Exception {
		DaoFunctionDescription search_for_update = the_search_for_update_handler.handle_implicit_verb(verb, activity);
		search_for_update.verb_action_on_data = GpBaseVerbsConstants.GpSearchForUpdate;
		return search_for_update;
	}
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		the_dto.function_name = "update_" + the_noun.getName();
		the_dto.function_parameters = the_noun.getName() + ","; 
	}
	
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		this.get_function_signiture(verb, activity);
	}
	
	@Override
	public void gp_verb_key(GpVerb verb, GpActivity activity) throws Exception {
		the_dto.verb_key_name = GpSqlQueriesGenSupport.key_name_for_update;
	}
	
	@Override
	public void gp_sql_query(GpVerb verb,GpActivity activity) throws Exception {
		GpNoun the_noun = activity.getPrimary_noun();
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String replacements = "\n\tid : " + the_noun.getName() + ".id,";
		for(GpNounAttribute attr : attrs){
			if(attr.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attr.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST))
				continue;
			replacements += "\n\t" + attr.getName().toLowerCase() + " : " + the_noun.getName() + "." + attr.getName().toLowerCase() + ",";
		}
		JSONArray json_array_parents = this.the_gen_support.getThe_worker().getGen_service().getRelationships_map().get(activity.getPrimary_noun().getId());
		if(json_array_parents != null){
			for(int i=0;i<json_array_parents.length();i++){
				JSONObject json_parent = json_array_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				replacements += "\n\t" + name +" : " + the_noun.getName() + "." + name +",";
			}
		}
		replacements = replacements.substring(0,replacements.length()-1);
		the_dto.query_replacements = "replacements: {"
				+ replacements 
				+ "\n},"; 
		
		the_dto.sequelizeQueryType = "UPDATE";
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		the_dto.callback_return_parameters = null;
		the_dto.query_return_parameters = null;
	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {}


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
	
	public List<DaoFunctionDescription> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
	
	public GpExpressDaoSearchForUpdateHandler getThe_search_for_update_handler() {
		return the_search_for_update_handler;
	}
	
	@Resource(name = "GpExpressDaoSearchForUpdateHandler")
	public void setThe_search_for_update_handler(GpExpressDaoSearchForUpdateHandler the_search_for_update_handler) {
		this.the_search_for_update_handler = the_search_for_update_handler;
	}
	
}