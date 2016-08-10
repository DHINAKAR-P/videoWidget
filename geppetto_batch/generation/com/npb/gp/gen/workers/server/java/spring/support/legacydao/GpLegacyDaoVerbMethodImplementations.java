package com.npb.gp.gen.workers.server.java.spring.support.legacydao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.GpLegacyDaoVerbGenInfo;
import com.npb.gp.gen.util.dto.GpResourceBundleReference;
import com.npb.gp.gen.workers.server.java.spring.GpSpringDaoLegacyGenWorker;

@Component("GpLegacyDaoVerbMethodImplementations")
public class GpLegacyDaoVerbMethodImplementations {
	
	private GpLegacyDaoVerbMethodSignitures dao_signiture_hlpr;
	private GpSpringDaoLegacyGenWorker the_worker;
	
	
	public GpLegacyDaoVerbMethodSignitures getDao_signiture_hlpr() {
		return dao_signiture_hlpr;
	}
	
	@Resource(name="GpLegacyDaoVerbMethodSignitures")
	public void setDao_signiture_hlpr(
			GpLegacyDaoVerbMethodSignitures dao_signiture_hlpr) {
		this.dao_signiture_hlpr = dao_signiture_hlpr;
	}
	public GpSpringDaoLegacyGenWorker getThe_worker() {
		return the_worker;
	}
	public void setThe_worker(GpSpringDaoLegacyGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public GpLegacyDaoVerbGenInfo delete_implementation(GpVerb verb,
													GpActivity activity){
		try{
				GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
				//setup the method signature
				GpJavaMethodDescription method_sig_info =
						this.dao_signiture_hlpr.delete_method_signiture(activity);

				the_dto.verb_action_on_data = "GpDelete";
				the_dto.authorization = "auths not ready at this time";
				the_dto.method_signiture = method_sig_info.getDescription();
				the_dto.method_name = method_sig_info.getName();
				the_dto.parameter_assignment = "parameters.put(\"id\", " + "id);";
				
				the_dto.special_mapper_refrence = "this.";

				ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();
				for(GpBaseSqlDto stmt_dto : sql_stmts ){
					if(stmt_dto.verb_action_on_data.equals("GpDelete")){
						the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
					}
				}
				the_dto.jdbc_template_type = "namedParameterJdbcTemplate.update(" + the_dto.local_resource_bundle_reference +", " +"parameters);";
				
				
				//START SET UP EXCEPTION
				//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
				int i = 0;
				for(String exc : method_sig_info.getExceptions()){
					if(i == 0){
						the_dto.exceptions = "throws " + exc;	
					}else{
						the_dto.exceptions = the_dto.exceptions + ", " + exc;
					}
					i++;
				}
				the_dto.return_reference = "\"{\\\"status\\\":\\\"success\\\"}\"";
				return the_dto;

			
		}catch (Exception e){
			
		}
		
		
		
		
		return null;
		
		
	}

	public GpLegacyDaoVerbGenInfo update_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.update_method_signiture(activity);
			the_dto.verb_action_on_data = "GpUpdate";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.parameter_assignment = "parameters.put(\"id\", " 
							+ activity.getPrimary_noun().getName() + "." + "getId()" + ");\n";
			
			ArrayList<GpNounAttribute> attribs = activity
					.getPrimary_noun().getNounattributes();
			String getter_method = "";
			for(GpNounAttribute attrib : attribs){
				getter_method =  activity.getPrimary_noun().getName() 
								+ "." + "get" + attrib.getName() + "()";
				if(the_dto.parameter_assignment != null){
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N');\n";
					}
					else{
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ");\n";
					}
				}else{
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N');\n";
					}
					else{
						the_dto.parameter_assignment = "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ");\n";
					}
				}
			}
			the_dto.SQL_special_mapper_refrence = this.the_worker.getUpdate_mapper_reference();
			the_dto.set_SQL_query = ".setSql("+ the_worker.getUpdate_bundle_sql() +");";
			
			the_dto.special_mapper_refrence = this.the_worker.getUpdate_mapper_reference();
			the_dto.jdbc_template_type = ".updateByNamedParam";
			the_dto.jdbc_template_input_parms = "(parameters);";

			//START SET UP EXCEPTION
			//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for(String exc : method_sig_info.getExceptions()){
				if(i == 0){
					the_dto.exceptions = "throws " + exc;	
				}else{
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
					i++;
				}
			
			the_dto.return_reference = activity.getPrimary_noun().getName();
			return the_dto;
			
			}catch (Exception e){
			
			}
			return null;
			
		
		}
	
	
	
	public GpLegacyDaoVerbGenInfo create_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.create_method_signiture(activity);
			the_dto.verb_action_on_data = "GpCreate";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			
			ArrayList<GpNounAttribute> attribs = activity
									.getPrimary_noun().getNounattributes();
			
			String getter_method = "";
			for(GpNounAttribute attrib : attribs){
				getter_method = "";
				getter_method =  activity.getPrimary_noun().getName() 
								+ "." + "get" + attrib.getName() + "()";
				if(the_dto.parameter_assignment != null){
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N');\n";
					}
					else{
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ");\n";
					}
				}else{
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + " ? 'Y':'N');\n";
					}
					else{	
						the_dto.parameter_assignment = "parameters.put(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ");\n";
					}
				}
			}
			the_dto.SQL_special_mapper_refrence = this.the_worker.getInsert_mapper_reference();
			the_dto.set_SQL_query = ".setSql("+ the_worker.getCreate_bundle_sql() +");";
			
			the_dto.key_holder_type = "KeyHolder";
			the_dto.key_holder_reference = " keyHolder";
			the_dto.key_holder_creation = " = new GeneratedKeyHolder();";
			
			the_dto.special_mapper_refrence = this.the_worker.getInsert_mapper_reference();
			the_dto.jdbc_template_type = ".updateByNamedParam";
			the_dto.jdbc_template_input_parms = "(parameters, " + "keyHolder);";
			the_dto.key_holder_value_assignment =  activity
						.getPrimary_noun().getName() + ".setId(keyHolder.getKey().longValue());"; 
			//activity.setId(keyHolder.getKey().longValue());
			
			//the_dto.parameter_assignment = "parameters.addValue(\"id\", project_id);";
			//START SET UP EXCEPTION
			//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for(String exc : method_sig_info.getExceptions()){
				if(i == 0){
					the_dto.exceptions = "throws " + exc;	
				}else{
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
					i++;
				}
			
			the_dto.return_reference = activity.getPrimary_noun().getName();
			return the_dto;
			
			}catch (Exception e){
			
			}
			return null;
	}
 		
	
	public GpLegacyDaoVerbGenInfo search_for_update_implementation(GpVerb verb,
																GpActivity activity){
		
		/* except for the method name for now search for update is really
		 * only a search by ID at this layer 
		 * Dan Castillo - 01/17/2015 
		 * 
		 */

		try{
			GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.search_for_update_method_signiture(activity);
			the_dto.verb_action_on_data = "GpSearchForUpdate";
			the_dto.type_query = "queryForObject";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.mapper_type = activity.getPrimary_noun().getName() + "Mapper";
			the_dto.mapper_reference = "the_mapper";
			the_dto.mapper_creation = the_dto.mapper_type + " " + the_dto.mapper_reference
					+" =   new "  + the_dto.mapper_type +"()";
			the_dto.parameter_assignment = "parameters.addValue(\"id\", id);";
			//
			the_dto.exeception_message = "\"no " + activity.getPrimary_noun().getName() + " found\"";
			
			ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();

			for(GpBaseSqlDto stmt_dto : sql_stmts ){
				if(stmt_dto.verb_action_on_data.equals("GpSearchForUpdate")){
					the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
				}
			}

			//START SET UP EXCEPTION
			//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for(String exc : method_sig_info.getExceptions()){
				if(i == 0){
					the_dto.exceptions = "throws " + exc;	
				}else{
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
					i++;
				}
			
			//handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if(!return_parm.container){
				if(return_parm.name.equals("GpPrimaryNoun")){
					the_dto.return_reference = "the_" + activity.getPrimary_noun().getName();
					the_dto.return_reference_check = " == null";
					the_dto.return_type = activity.getPrimary_noun().getName();

				}
			}else{
				if(return_parm.name.equals("GpArrayList")){
					the_dto.return_type = "ArrayList<";
					if(return_parm.base_name.equals("GpPrimaryNoun")){
						the_dto.return_type = the_dto.return_type + activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun().getName() + "_list";
					}
				}
			}
			
			return the_dto;
			
			}catch (Exception e){
			
			}
			return null;

		
	}

	
	public GpLegacyDaoVerbGenInfo get_nound_by_id_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.get_nound_by_id_method_signiture(activity);
			the_dto.verb_action_on_data = "GpGetNounById";
			the_dto.type_query = "queryForObject";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.mapper_type = activity.getPrimary_noun().getName() + "Mapper";
			the_dto.mapper_reference = "the_mapper";
			the_dto.mapper_creation = the_dto.mapper_type + " " + the_dto.mapper_reference
					+" =   new "  + the_dto.mapper_type +"()";
			the_dto.parameter_assignment = "parameters.addValue(\"id\", id);";
			//
			the_dto.exeception_message = "\"no " + activity.getPrimary_noun().getName() + " found\"";
			
			ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();

			for(GpBaseSqlDto stmt_dto : sql_stmts ){
				if(stmt_dto.verb_action_on_data.equals("GpGetNounById")){
					the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
				}
			}
			
			//START SET UP EXCEPTION
			//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for(String exc : method_sig_info.getExceptions()){
				if(i == 0){
					the_dto.exceptions = "throws " + exc;	
				}else{
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
					i++;
				}
			
			//handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if(!return_parm.container){
				if(return_parm.name.equals("GpPrimaryNoun")){
					the_dto.return_reference = "the_" + activity.getPrimary_noun().getName();
					the_dto.return_reference_check = " == null";
					the_dto.return_type = activity.getPrimary_noun().getName();
				}
			}else{
				if(return_parm.name.equals("GpArrayList")){
					the_dto.return_type = "ArrayList<";
					if(return_parm.base_name.equals("GpPrimaryNoun")){
						the_dto.return_type = the_dto.return_type + activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun().getName() + "_list";
					}
				}
			}
			
			return the_dto;
			
			}catch (Exception e){
			
			}
			return null;

		
		
		
	}


	public GpLegacyDaoVerbGenInfo get_all_values_implementation(GpVerb verb,
														GpActivity activity){

		try{
			GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.get_all_values_method_signiture(activity);
			the_dto.verb_action_on_data = "GpGetAllValues";
			the_dto.authorization = "auths not ready at this time";
			the_dto.type_query = "query";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.mapper_type = activity.getPrimary_noun().getName() + "Mapper";
			the_dto.mapper_reference = "the_mapper";
			the_dto.mapper_creation = the_dto.mapper_type + " " + the_dto.mapper_reference
					+" =   new "  + the_dto.mapper_type +"()";
			the_dto.exeception_message = "\"no " + activity.getPrimary_noun().getName() + " found\"";
			
			ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();

			for(GpBaseSqlDto stmt_dto : sql_stmts ){
				if(stmt_dto.verb_action_on_data.equals("GpGetAllValues")){
					the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
				}
			}
			
			//START SET UP EXCEPTION
			//service_exceptions = (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for(String exc : method_sig_info.getExceptions()){
				if(i == 0){
					the_dto.exceptions = "throws " + exc;	
				}else{
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
					i++;
				}
			
			//handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if(!return_parm.container){
				the_dto.return_type = return_parm.name;
			}else{
				if(return_parm.name.equals("GpArrayList")){
					the_dto.return_type = "ArrayList<";
					if(return_parm.base_name.equals("GpPrimaryNoun")){
						the_dto.return_type = the_dto.return_type + activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun().getName() + "_list";
					}
					the_dto.return_reference_check = ".size() < 1";
				}
			}
			
			
			return the_dto;
			
			

			
			}catch (Exception e){
			
			}
		
		
			return null;

		
		
		
	}

	public GpLegacyDaoVerbGenInfo search_implementation(
			GpVerb a_verb, GpActivity activity) {
		GpLegacyDaoVerbGenInfo the_dto = new GpLegacyDaoVerbGenInfo();
		GpJavaMethodDescription method_sig_info = this.dao_signiture_hlpr
				.search__method_signiture(activity);
		the_dto.verb_action_on_data = "GpSearch";
		the_dto.type_query = "query";
		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();
		the_dto.mapper_type = activity.getPrimary_noun().getName() + "Mapper";
		the_dto.mapper_reference = "the_mapper";
		the_dto.mapper_creation = the_dto.mapper_type + " " + the_dto.mapper_reference
				+" =   new "  + the_dto.mapper_type +"()";
		the_dto.exeception_message = "\"null\"";
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();

		String getter_method;
		for (GpNounAttribute attrib : attribs) {
			getter_method = attrib.getName().toLowerCase();
			if (the_dto.parameter_assignment != null) {
				if(attrib.getSubtype().equals("true/false")){
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ "parameters.addValue(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + ".equals(\"%%\") ? " + attrib.getName().toLowerCase() + ":"+ attrib.getName().toLowerCase() +".equals(\"true\") ? \"Y\":\"N\");\n";
				}
				else{
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ "parameters.addValue(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + ");\n";
				}
					
			} else {
					the_dto.parameter_assignment = "parameters.addValue(\""
							+ attrib.getName().toLowerCase() + "\"" + ", " + getter_method
							+ ");\n";
			}
		}
		ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();
		for(GpBaseSqlDto stmt_dto : sql_stmts ){
			if(stmt_dto.verb_action_on_data.equals("GpSearch")){
				the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
			}
		}
		
		GpDataType return_parm = method_sig_info.getReturn_parm();
		if(!return_parm.container){
			the_dto.return_type = return_parm.name;
		}else{
			if(return_parm.name.equals("GpArrayList")){
				the_dto.return_type = "ArrayList<";
				if(return_parm.base_name.equals("GpPrimaryNoun")){
					the_dto.return_type = the_dto.return_type + activity.getPrimary_noun().getName() + ">";
					the_dto.return_reference = activity.getPrimary_noun().getName() + "_list";
				}
				the_dto.return_reference_check = " == null";
			}
		}
		// TODO Auto-generated method stub
		return the_dto;
	}


}
