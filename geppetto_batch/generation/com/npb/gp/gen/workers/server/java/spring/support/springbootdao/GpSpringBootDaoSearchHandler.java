package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.GpDataType;
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
 * Contains the logic to generate the function that handles the verb</br>
 * action on data: GpSearch</p>
 */
@Component("GpSpringBootDaoSearchHandler")
public class GpSpringBootDaoSearchHandler 
		extends GpBaseSpringBootDaoVerbFunctionHandler implements
			IGpSpringBootDaoVerbGenSupport{
	
	String function_name = "search";
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
					this.dao_signiture_hlpr.search__method_signiture(activity);
			the_dto.parameter_assignment = null;
			
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_by_verb_id_and_component_type(verb.getId(),
							GpBaseFlowConstants.Java_Spring_Flow_Component_type_GpDao);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpJpaQuery")){
						this.gp_jpa_query(verb, activity);
					}else if(mcr_flow.getAction().equals("GpArrayListDeclare")){
						this.gp_array_list_declare(verb, activity);
					}else if(mcr_flow.getAction().equals("GpException")){
						this.gp_exception(verb, activity);
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
		this.get_function_signiture(verb,activity);
		this.the_dto.gp_start_code = "//this is where the start code goes";
	}
	
	@Override
	public void gp_declare_noun(GpVerb verb, GpActivity activity
			) throws Exception {
		
	}
	
	@Override
	public void gp_jpa_query(GpVerb verb,GpActivity activity
			) throws Exception {
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();

		String getter_method;
		for (GpNounAttribute attrib : attribs) {
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
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
		
		ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSpring_boot_dml_worker().get_dml_statements_for_activity(activity);
		for(GpBaseSqlDto stmt_dto : sql_stmts ){
			if(stmt_dto.verb_action_on_data.equals(GpBaseVerbsConstants.GpSearch)){
				the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
			}
		}
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
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

	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes";
	}
		
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
		the_dto.verb_action_on_data = "GpSearch";
		the_dto.type_query = "query";
		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();
		
	}
	
	@Override
	public void gp_array_list_declare(GpVerb verb,GpActivity activity) throws Exception {
		the_dto.clazz_name = activity.getPrimary_noun().getName();
		the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
	}
	
	@Override
	public void gp_exception(GpVerb verb,GpActivity activity) throws Exception {
		
		the_dto.return_list_size = the_dto.clazz_name +"_"+"list";
		the_dto.exeception_message = "\"null\"";
		
	}


	
	
}
