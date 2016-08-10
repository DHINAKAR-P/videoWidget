package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpEventVerbConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.dao.IGpSpringBootDaoVerbGenSupport;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/29/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generates the function that handles the verb</br>
 * action on data: GpCreate</p>
 */

@Component("GpSpringBootDaoCreateHandler")
public class GpSpringBootDaoCreateHandler 
extends GpBaseSpringBootDaoVerbFunctionHandler
						implements IGpSpringBootDaoVerbGenSupport {
	
	String function_name = "create";
	private GpSpringBootDaoVerbMethodSignitures dao_signiture_hlpr;
	private GpSpringBootJpaDaoGenWorker the_worker;

	
	public GpSpringBootDaoVerbMethodSignitures getDao_signiture_hlpr() {
		return dao_signiture_hlpr;
	}
	
	@Resource(name="GpSpringBootDaoVerbMethodSignitures")
	public void setDao_signiture_hlpr(
			GpSpringBootDaoVerbMethodSignitures dao_signiture_hlpr) {
		this.dao_signiture_hlpr = dao_signiture_hlpr;
	}
	public GpSpringBootJpaDaoGenWorker getThe_worker() {
		return the_worker;
	}
	
	@Resource(name = "GpSpringBootJpaDaoGenWorker")
	public void setThe_worker(GpSpringBootJpaDaoGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	@Override
	public GpSpringBootDaoVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {
				
		try {
			this.the_dto.signiture_helper = 
					this.dao_signiture_hlpr.create_method_signiture(activity);
			the_dto.parameter_assignment = "";
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao);
			
		
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpJpaQuery")){
						this.gp_jpa_query(verb, activity);
					}else if(mcr_flow.getAction().equals("GpQueryExecuteSt")){
						this.gp_execute_st(verb, activity);
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
		this.get_function_signiture( verb,activity);
		this.the_dto.gp_start_code = "//this is where the start code goes" + "\n";
	}
	
	@Override
	public void gp_jpa_query(GpVerb verb,GpActivity activity) throws Exception {
		
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun().getNounattributes();
		String getter_method = "";
		for (GpNounAttribute attrib : attribs) {
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
			
			String noun_capital = attrib.getName().toLowerCase();
			noun_capital = noun_capital.substring(0, 1).toUpperCase() + noun_capital.substring(1);
			getter_method = "";
			getter_method = activity.getPrimary_noun().getName() + "." + "get"+ noun_capital + "()";
								
			if (the_dto.parameter_assignment != null) {
				if (attrib.getSubtype().equals("true/false")) {
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ ".setParameter(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + "? 'Y':'N')\n";
				} else {
					the_dto.parameter_assignment = the_dto.parameter_assignment
							+ ".setParameter(\"" + attrib.getName().toLowerCase() + "\""
							+ ", " + getter_method + ")\n";
				}
			} else {
				if (attrib.getSubtype().equals("true/false")) {
					the_dto.parameter_assignment = ".setParameter(\""
							+ attrib.getName().toLowerCase() + "\"" + ", " + getter_method
							+ " ? 'Y':'N')\n";
				} else {
					the_dto.parameter_assignment = ".setParameter(\""
							+ attrib.getName().toLowerCase() + "\"" + ", " + getter_method
							+ ")\n";
				}
			}
		}
		the_dto.parameter_assignment += handle_parent_attr(activity.getPrimary_noun());
		the_dto.parameter_assignment += handle_implicit_attrs();
		ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSpring_boot_dml_worker().get_dml_statements_for_activity(activity);
		for(GpBaseSqlDto stmt_dto : sql_stmts ){
			if(stmt_dto.verb_action_on_data.equals(GpBaseVerbsConstants.GpCreate)){
				the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
				break;
			}
		}
		the_dto.local_string_reference = the_dto.local_resource_bundle_reference;
	}
	
	private String handle_implicit_attrs() {
		String parameter_assignment = "";
		parameter_assignment += ".setParameter(\"created_by\", user == null ? 0:user.getId())\n";
		parameter_assignment += ".setParameter(\"updated_by\", user == null ? 0:user.getId())\n";
		return parameter_assignment;
	}

	private String handle_parent_attr(GpNoun noun) throws JSONException{
		String parameter_assignment = "";
		JSONArray parent_nouns = this.the_worker.getRelationships_map().get(noun.getId());
		if(parent_nouns != null){
			//System.out.println(noun.getName() + " size " + parent_nouns.length());
			for(int i = 0; i < parent_nouns.length(); i++){
				JSONObject json_parent = parent_nouns.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				String name_id = name + "_id";
				String relationship_type = json_parent.getString("type");
				if(relationship_type.equals("ONE_TO_ONE")){
					parameter_assignment += ".setParameter(\""
							+ name_id + "\"" + ", " + noun.getName() + "." + "get"
							+ name + "_id()"
							+ ")\n";
					continue;
				}
				if(relationship_type.equals("ONE_TO_MANY")){
					parameter_assignment += ".setParameter(\""
							+ name_id + "\"" + ", " + noun.getName() + "." + "get"
							+ name + "_id()"
							+ ")\n";
					continue;
				}
			}
		}
		return parameter_assignment;
	}
	
	@Override
	public void gp_execute_st(GpVerb verb,GpActivity activity)throws Exception {
		//the_dto.execute_statement = "query.executeUpdate();";
		the_dto.execute_statement = "int insertedId = query.executeUpdate();"+
				"\n\t\tString lastIndex=\"select last_insert_id()\";"+
				"\n\t\tQuery sql = entityManager.createNativeQuery(lastIndex);"+
				"\n\t\tBigInteger new_id = (BigInteger) sql.getSingleResult();";
		
		the_dto.execute_statement +=	 "\n\t\t"+
				activity.getPrimary_noun().getName()+".setId(new_id.longValue());"+
				"\n\t\tSystem.out.println(\"create data---\"+insertedId);";
		
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun().getNounattributes();
		
		for (GpNounAttribute attrib : attribs) {
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_PICTURE)){
				String noun_attr_capital = attrib.getName().toLowerCase();
				noun_attr_capital = noun_attr_capital.substring(0, 1).toUpperCase() + noun_attr_capital.substring(1);
				String setter_method = activity.getPrimary_noun().getName() + "." + "set"+ noun_attr_capital + "(null);";
				the_dto.execute_statement += "\n\t\t" + setter_method;
			}
		}
		
		
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		the_dto.return_reference = activity.getPrimary_noun().getName();
	}
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		//setup the method signature
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpCreate;
		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();
		int i = 0;
		for(String exc : method_sig_info.getExceptions()){
			if(i == 0){
				the_dto.exceptions = "throws " + exc;	
			}else{
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
				i++;
			}
	}

	public GpSpringBootDaoVerbGenInfo handle_implicit_verb(GpVerb verb, GpActivity activity) {
		try {
			this.the_dto.signiture_helper = 
					this.dao_signiture_hlpr.create_method_signiture(activity);
			the_dto.parameter_assignment = "";
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(GpBaseVerbsConstants.GpCreate,
							GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao);
			
		
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpJpaQuery")){
						this.gp_jpa_query(verb, activity);
					}else if(mcr_flow.getAction().equals("GpQueryExecuteSt")){
						this.gp_execute_st(verb, activity);
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
