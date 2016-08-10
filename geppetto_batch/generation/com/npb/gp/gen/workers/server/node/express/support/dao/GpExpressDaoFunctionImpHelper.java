package com.npb.gp.gen.workers.server.node.express.support.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.js.node.express.DaoFunctionDescription;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;

@Component("GpExpressDaoFunctionImpHelper")
public class GpExpressDaoFunctionImpHelper {
	private GpExpressDaoGenSupport the_gen_support;

	public void setThe_gen_support(GpExpressDaoGenSupport the_gen_support) {
		this.the_gen_support = the_gen_support;
	}
	public GpExpressDaoGenSupport getThe_gen_support() {
		return the_gen_support;
	}
	
	public DaoFunctionDescription get_all_values_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "get_all_" + the_noun.getName();
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_get_all;
		daoFunctionDescription.callback_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.function_parameters = null; //no parameters for this
		daoFunctionDescription.query_replacements = ""; // no replacements for this
		daoFunctionDescription.query_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.sequelizeQueryType = "SELECT";
		return daoFunctionDescription;
	}

	public DaoFunctionDescription get_nound_by_id_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "get_" + the_noun.getName() +"_by_id";
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_get_by_id;
		daoFunctionDescription.callback_return_parameters = the_noun.getName().toLowerCase() + "[0]";
		daoFunctionDescription.function_parameters = the_noun.getName() + "Id" + ","; 
		daoFunctionDescription.query_replacements = "replacements: { id: " + the_noun.getName() + "Id" + "},"; // no replacements for this
		daoFunctionDescription.query_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.sequelizeQueryType = "SELECT";
		return daoFunctionDescription;
	}
	public DaoFunctionDescription search_for_update_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "search_" + the_noun.getName() +"_for_update";
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_search_for_update;
		daoFunctionDescription.callback_return_parameters = the_noun.getName().toLowerCase() + "[0]";
		daoFunctionDescription.function_parameters = the_noun.getName() + "Id" + ","; 
		daoFunctionDescription.query_replacements = "replacements: {\n"
				+ "\tid: " + the_noun.getName() + "Id" 
				+ "\n},"; 
		daoFunctionDescription.query_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.sequelizeQueryType = "SELECT";
		return daoFunctionDescription;
	}
	public DaoFunctionDescription create_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "create_" + the_noun.getName();
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_create;
		daoFunctionDescription.callback_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.function_parameters = the_noun.getName() + ","; 
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String replacements = "";
		for(GpNounAttribute attr : attrs){
			replacements += "\n\t" + attr.getName().toLowerCase() + " : " + the_noun.getName() + "." + attr.getName().toLowerCase() + ",";
		}
		replacements = replacements.substring(0,replacements.length()-1);
		daoFunctionDescription.query_replacements = "replacements: {"
				+ replacements 
				+ "\n},"; 
		daoFunctionDescription.query_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.sequelizeQueryType = "INSERT";
		return daoFunctionDescription;
	}
	public DaoFunctionDescription update_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "update_" + the_noun.getName();
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_update;
		daoFunctionDescription.callback_return_parameters = null;
		daoFunctionDescription.function_parameters = the_noun.getName() + ","; 
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String replacements = "\n\tid : " + the_noun.getName() + ".id,";
		for(GpNounAttribute attr : attrs){
			replacements += "\n\t" + attr.getName().toLowerCase() + " : " + the_noun.getName() + "." + attr.getName().toLowerCase() + ",";
		}
		replacements = replacements.substring(0,replacements.length()-1);
		daoFunctionDescription.query_replacements = "replacements: {"
				+ replacements 
				+ "\n},"; 
		daoFunctionDescription.query_return_parameters = null;
		daoFunctionDescription.sequelizeQueryType = "UPDATE";
		return daoFunctionDescription;
	}
	public DaoFunctionDescription delete_implementation(GpVerb a_verb, GpActivity activity) {
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		GpNoun the_noun = activity.getPrimary_noun();
		daoFunctionDescription.function_name = "delete_" + the_noun.getName();
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_delete;
		daoFunctionDescription.callback_return_parameters = null;
		daoFunctionDescription.function_parameters = the_noun.getName() + "Id" + ","; 
		daoFunctionDescription.query_replacements = "replacements: {\n"
				+ "\tid: " + the_noun.getName() + "Id" 
				+ "\n},"; 
		daoFunctionDescription.query_return_parameters = null;
		daoFunctionDescription.sequelizeQueryType = "DELETE";
		return daoFunctionDescription;
	}
	public DaoFunctionDescription search_implementation(GpVerb a_verb, GpActivity activity) {
		GpNoun the_noun = activity.getPrimary_noun();
		List<GpNounAttribute> attrs = the_noun.getNounattributes();
		String replacements = "";
		String function_parameters = "";
		for(GpNounAttribute attr : attrs){
			replacements += "\n\t" + attr.getName().toLowerCase() + ": " + attr.getName().toLowerCase() + ",";
			function_parameters += attr.getName().toLowerCase() + ",";
		}
		replacements = replacements.substring(0,replacements.length()-1);
		DaoFunctionDescription daoFunctionDescription = new DaoFunctionDescription();
		daoFunctionDescription.function_name = "search_" + the_noun.getName();
		daoFunctionDescription.verb_key_name = GpSqlQueriesGenSupport.key_name_for_search;
		daoFunctionDescription.callback_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.function_parameters = function_parameters;
		daoFunctionDescription.query_replacements = "replacements: {"
				+ replacements 
				+ "\n},"; 
		daoFunctionDescription.query_return_parameters = the_noun.getName().toLowerCase();
		daoFunctionDescription.sequelizeQueryType = "SELECT";
		return daoFunctionDescription;
	}
}
