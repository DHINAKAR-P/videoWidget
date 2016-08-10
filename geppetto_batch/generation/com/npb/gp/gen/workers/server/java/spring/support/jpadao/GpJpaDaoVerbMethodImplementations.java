package com.npb.gp.gen.workers.server.java.spring.support.jpadao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.GpJpaDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.jpa.GpSpringJpaDaoGenWorker;

/** 
*   Modified Date: 22/1/2016</br>
*   Modified By: Kumaresan Perumal</br>
*        <p>
*   I added the new statements in the following methods: 
*   on delete_implementation method called
*       1. .setParameter(); statement
*       2. execute_statement variable
*       
*   on update_implementation method called
*        1. .setParameter(); statement
*        2. execute_statement variable
*        
*   on create_implementation method called
*        1. .setParameter(); statement
*        2. execute_statement variable
*        3. commented mapper and resource bundle statements 
*        
*   on search_for_update_implementation method called
*        1. .setParameter(); statement
*        2. commented mapper and resource bundle statements 
*        3. return_list_size is variable
*        4. clazz_name is variable
*        
*   on search_for_update_implementation method called
*        1. .setParameter(); statement
*        2. commented mapper and resource bundle statements 
*        3. return_list_size is variable
*        4. clazz_name is variable
*        
*   on get_nound_by_id_implementation method called
*        1. .setParameter(); statement
*        2. commented mapper and resource bundle statements 
*        3. return_list_size is variable
*        4. clazz_name is variable
*        
*   on get_all_values_implementation method called
*        1. .setParameter(); statement
*        2. commented mapper and resource bundle statements 
*        3. return_list_size is variable
*        4. clazz_name is variable
*        
*   on search_implementation method called
*        1. .setParameter(); statement
*        2. commented mapper and resource bundle statements 
*        3. return_list_size is variable
*        4. clazz_name is variable
*        5. .concat("%") String method
*        </p>

*/


@Component("GpJpaDaoVerbMethodImplementations")
public class GpJpaDaoVerbMethodImplementations {
	
	private GpJpaDaoVerbMethodSignitures dao_signiture_hlpr;
	private GpSpringJpaDaoGenWorker the_worker;
	
	
	public GpJpaDaoVerbMethodSignitures getDao_signiture_hlpr() {
		return dao_signiture_hlpr;
	}
	
	@Resource(name="GpJpaDaoVerbMethodSignitures")
	public void setDao_signiture_hlpr(
			GpJpaDaoVerbMethodSignitures dao_signiture_hlpr) {
		this.dao_signiture_hlpr = dao_signiture_hlpr;
	}
	public GpSpringJpaDaoGenWorker getThe_worker() {
		return the_worker;
	}
	public void setThe_worker(GpSpringJpaDaoGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public GpJpaDaoVerbGenInfo delete_implementation(GpVerb verb,
													GpActivity activity){
		try{
				GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
				//setup the method signature
				GpJavaMethodDescription method_sig_info =
						this.dao_signiture_hlpr.delete_method_signiture(activity);

				the_dto.verb_action_on_data = "GpDelete";
				the_dto.authorization = "auths not ready at this time";
				the_dto.method_signiture = method_sig_info.getDescription();
				the_dto.method_name = method_sig_info.getName();
				the_dto.parameter_assignment = ".setParameter(\"id\", " + "id)";
				
				//the_dto.special_mapper_refrence = "this.";
				the_dto.execute_statement = "query.executeUpdate();";
				ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();
				for(GpBaseSqlDto stmt_dto : sql_stmts ){
					if(stmt_dto.verb_action_on_data.equals("GpDelete")){
						the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
						
					}
				}
				the_dto.local_string_reference = the_dto.local_resource_bundle_reference;
				
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

	public GpJpaDaoVerbGenInfo update_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
			
			
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.update_method_signiture(activity);
			the_dto.verb_action_on_data = "GpUpdate";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.parameter_assignment = ".setParameter(\"id\", " 
							+ activity.getPrimary_noun().getName() + "." + "getId()" + ")\n";
			
			ArrayList<GpNounAttribute> attribs = activity
					.getPrimary_noun().getNounattributes();
			String getter_method = "";
			for(GpNounAttribute attrib : attribs){
				getter_method =  activity.getPrimary_noun().getName() 
								+ "." + "get" + attrib.getName() + "()";
				if(the_dto.parameter_assignment != null){
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N')\n";
					}
					else{
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ")\n";
					}
				}else{
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N')\n";
					}
					else{
						the_dto.parameter_assignment = ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ")\n";
					}
				}
			}
			the_dto.execute_statement = "query.executeUpdate();";
			ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();
			for(GpBaseSqlDto stmt_dto : sql_stmts ){
				if(stmt_dto.verb_action_on_data.equals("GpUpdate")){
					the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
					
				}
			}
			the_dto.local_string_reference = the_dto.local_resource_bundle_reference;
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
	
	
	
	public GpJpaDaoVerbGenInfo create_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
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
								+ ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + "? 'Y':'N')\n";
					}
					else{
						the_dto.parameter_assignment = the_dto.parameter_assignment 
								+ ".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ")\n";
					}
				}else{
					if(attrib.getSubtype().equals("true/false")){
						the_dto.parameter_assignment = 
								".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + " ? 'Y':'N')\n";
					}
					else{	
						the_dto.parameter_assignment =
								".setParameter(\"" + attrib.getName().toLowerCase() +"\"" + ", " 
								+ getter_method + ")\n";
					}
				}
			}
	
			//the_dto.execute_statement = "query.executeUpdate();";
			the_dto.execute_statement = "int insertedId = query.executeUpdate();"+
					"String lastIndex=\"select last_insert_id()\";"+
					"Query sql = entityManager.createNativeQuery(lastIndex);"+
					"BigInteger lastid = (BigInteger) sql.getSingleResult();"+
					"long finalvalue = Long.valueOf(lastid.toString());"+
					activity.getPrimary_noun().getName()+".setId(finalvalue);"+
					"System.out.println(\"create data---\"+insertedId);";
					
			ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSql_stmts();
			for(GpBaseSqlDto stmt_dto : sql_stmts ){
				if(stmt_dto.verb_action_on_data.equals("GpCreate")){
					the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
					
				}
			}
			the_dto.local_string_reference = the_dto.local_resource_bundle_reference;
			
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
 		
	
	public GpJpaDaoVerbGenInfo search_for_update_implementation(GpVerb verb,
																GpActivity activity){
		
		/* except for the method name for now search for update is really
		 * only a search by ID at this layer 
		 * Dan Castillo - 01/17/2015 
		 * 
		 */

		try{
			GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
			//setup the method signature
			// the method name is sealdetails_search_for_update
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.search_for_update_method_signiture(activity);
			the_dto.verb_action_on_data = "GpSearchForUpdate";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.parameter_assignment = ".setParameter(\"id\", id);";

			the_dto.clazz_name = activity.getPrimary_noun().getName();
			the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
			the_dto.return_list_size = the_dto.clazz_name+"_"+"list.get(0)";
			
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

	
	public GpJpaDaoVerbGenInfo get_nound_by_id_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
			//setup the method signature  get_sealdetails
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.get_nound_by_id_method_signiture(activity);
			the_dto.verb_action_on_data = "GpGetNounById";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.parameter_assignment = ".setParameter(\"id\", id);";
			
			the_dto.clazz_name = activity.getPrimary_noun().getName();
			the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
			the_dto.return_list_size = the_dto.clazz_name+"_"+"list.get(0)";
			
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


	public GpJpaDaoVerbGenInfo get_all_values_implementation(GpVerb verb,
														GpActivity activity){
		
		try{
			GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
			//setup the method signature
			GpJavaMethodDescription method_sig_info =
			this.dao_signiture_hlpr.get_all_values_method_signiture(activity);
			the_dto.verb_action_on_data = "GpGetAllValues";
			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();
			the_dto.exeception_message = "\"no " + activity.getPrimary_noun().getName() + " found\"";
			
			the_dto.clazz_name = activity.getPrimary_noun().getName();
			the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
			the_dto.return_list_size = the_dto.clazz_name +"_"+ "list";
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

	public GpJpaDaoVerbGenInfo search_implementation(
			GpVerb a_verb, GpActivity activity) {
		
		// method name is search_sealdetails 

		GpJpaDaoVerbGenInfo the_dto = new GpJpaDaoVerbGenInfo();
		GpJavaMethodDescription method_sig_info = this.dao_signiture_hlpr
				.search__method_signiture(activity);
		
		the_dto.verb_action_on_data = "GpSearch";
		the_dto.type_query = "query";
		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();
		the_dto.clazz_name = activity.getPrimary_noun().getName();
		the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
		the_dto.return_list_size = the_dto.clazz_name +"_"+"list";
		//the_dto.semi_colon = ";";
		the_dto.exeception_message = "\"null\"";
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();

		String getter_method;
		for (GpNounAttribute attrib : attribs) {
			getter_method = attrib.getName().toLowerCase();
			if (the_dto.parameter_assignment != null) {
				if(attrib.getSubtype().equals("true/false")){
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ ".setParameter(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + ".equals(\"%%\") ? " + 
							attrib.getName().toLowerCase() + ":"+ attrib.getName().toLowerCase() +
							".equals(\"true\") ? \"Y\":\"N\")\n";
				}
				else{   
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ ".setParameter(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + ".concat(\"%\"))\n";
				}
					
			} else {
					the_dto.parameter_assignment = 
							".setParameter(\""
							+ attrib.getName().toLowerCase() + "\"" + ", " + getter_method + ".concat(\"%\"))\n";
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
