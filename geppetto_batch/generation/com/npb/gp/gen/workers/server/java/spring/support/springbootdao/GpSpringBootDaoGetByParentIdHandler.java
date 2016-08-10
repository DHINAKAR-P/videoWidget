package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.dao.IGpSpringBootDaoVerbGenSupport;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;

@Component("GpSpringBootDaoGetByParentIdHandler")
public class GpSpringBootDaoGetByParentIdHandler extends
GpBaseSpringBootDaoVerbFunctionHandler implements
IGpSpringBootDaoVerbGenSupport {
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
	@Override
	public GpSpringBootDaoVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		return null;

	}
	
	public GpSpringBootDaoVerbGenInfo handle_implicit_verb(GpVerb verb,
			GpActivity activity) throws Exception {

		try {
			this.the_dto.signiture_helper = 
					this.dao_signiture_hlpr.get_nound_by_parent_id_method_signiture(activity,this.the_worker.getRelationships_map());
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
					.find_microflow_by_component_name_and_base_verb_name(GpBaseVerbsConstants.GpGetNounByParentId,
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
	public void gp_jpa_query(GpVerb verb,GpActivity activity) throws Exception {
		JSONArray json_array_parents = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
		String parameter_assignment = "";
		if(json_array_parents != null){
			for(int i=0;i<json_array_parents.length();i++){
				JSONObject json_parent = json_array_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				parameter_assignment += ".setParameter(\""+ name +"\", "+ name +" == 0 ? \"%%\" : "+ name +")";
			}
		}
		the_dto.parameter_assignment = parameter_assignment;
		
		ArrayList<GpBaseSqlDto> sql_stmts = this.the_worker.getSpring_boot_dml_worker().get_dml_statements_for_activity(activity);
		for (GpBaseSqlDto stmt_dto : sql_stmts) {
			if (stmt_dto.verb_action_on_data.equals(GpBaseVerbsConstants.GpGetNounByParentId)) {
				the_dto.local_resource_bundle_reference = stmt_dto.local_string_reference;
			}
		}
	}
	
	@Override
	public void gp_return(GpVerb verb,GpActivity activity)throws Exception {
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
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
					the_dto.return_reference_check = ".size() < 1";
				}
			}
		}

	}
	
	@Override
	public void gp_end(GpVerb verb) throws Exception {
		this.the_dto.gp_end_code = "//this is where the end code goes";
	}
	
	@Override
	public void gp_array_list_declare(GpVerb verb,GpActivity activity) throws Exception {
		the_dto.clazz_name = activity.getPrimary_noun().getName();
		the_dto.return_list_name = the_dto.clazz_name+"_"+"list";
		JSONArray json_parents = this.the_worker.getRelationships_map().get(activity.getPrimary_noun().getId());
		boolean is_one_to_many = false;
		if(json_parents != null){
			for(int i=0;i<json_parents.length(); i++){
				JSONObject json_parent = json_parents.getJSONObject(i);
				String type = json_parent.getString("type");
				if(type.equals("ONE_TO_MANY"))
					is_one_to_many = true;
			}
		}
		if(is_one_to_many)
			the_dto.return_list_size = the_dto.clazz_name +"_"+"list";
		else
			the_dto.return_list_size = the_dto.clazz_name+"_"+"list.get(0)";
	}

	@Override
	public void gp_exception(GpVerb verb,GpActivity activity) throws Exception {
		the_dto.exeception_message = "\"no " + activity.getPrimary_noun().getName() + " found\"";
	}
	
	@Override
	public void get_function_signiture(GpVerb verb,GpActivity activity) throws Exception {
		
		GpJavaMethodDescription method_sig_info = this.the_dto.signiture_helper;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpGetNounByParentId;
		the_dto.authorization = "auths not ready at this time";
		the_dto.method_signiture = method_sig_info.getDescription();
		the_dto.method_name = method_sig_info.getName();
		
		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_sig_info.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
	}

}
