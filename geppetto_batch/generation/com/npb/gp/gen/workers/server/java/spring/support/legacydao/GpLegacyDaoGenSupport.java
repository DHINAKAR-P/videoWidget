package com.npb.gp.gen.workers.server.java.spring.support.legacydao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpLegacyDaoVerbGenInfo;
import com.npb.gp.gen.util.dto.GpResourceBundleReference;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.GpSpringDaoLegacyGenWorker;

/**
 * 
 * @author Dan Castillo
 * Date Created: 12/30/2014</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate the methods, based on activity verbs,</br>
 * in a Spring template and mapper approach - it is designed to be called by</br>
 * the  worker that handles the Spring Legacy Dao  generation</p> 
 */
@Component("GpLegacyDaoGenSupport")
public class GpLegacyDaoGenSupport {
	private GpFlowControl the_flow;
	private GpLegacyDaoVerbMethodSignitures method_signitures_hlpr;
	private GpLegacyDaoVerbMethodImplementations implementation_hlpr;
	private GpSpringDaoLegacyGenWorker the_worker;
	
	
	
	
	
	public GpLegacyDaoVerbMethodImplementations getImplementation_hlpr() {
		return implementation_hlpr;
	}

	@Resource(name="GpLegacyDaoVerbMethodImplementations")
	public void setImplementation_hlpr(
			GpLegacyDaoVerbMethodImplementations implementation_hlpr) {
		this.implementation_hlpr = implementation_hlpr;
	}

	public GpSpringDaoLegacyGenWorker getThe_worker() {
		return the_worker;
	}

	@Resource(name="GpSpringDaoLegacyGenWorker")
	public void setThe_worker(GpSpringDaoLegacyGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpLegacyDaoVerbMethodSignitures getMethod_signitures_hlpr() {
		return method_signitures_hlpr;
	}

	@Resource(name="GpLegacyDaoVerbMethodSignitures")
	public void setMethod_signitures_hlpr(
			GpLegacyDaoVerbMethodSignitures method_signitures_hlpr) {
		this.method_signitures_hlpr = method_signitures_hlpr;
	}


	public void set_flow(GpFlowControl the_flow){
		this.the_flow = the_flow;
	}

	
	public HashMap<String,  GpJavaMethodDescription>
					get_method_signitures(GpActivity an_activity) throws Exception{
		
		HashMap<String,GpJavaMethodDescription> the_methods =
				new HashMap<String,GpJavaMethodDescription>();
		
		ArrayList<GpVerb> the_verbs = an_activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				the_methods.put(a_verb.getAction_on_data(),
							this.method_signitures_hlpr
							.get_all_values_method_signiture(an_activity));
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.get_nound_by_id_method_signiture(an_activity));
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.search_for_update_method_signiture(an_activity));
				generated_verbs.add("GpSearchForUpdate");
				continue;
				
			}
			
			if(a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.create_method_signiture(an_activity));
				generated_verbs.add("GpCreate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")){
				the_methods.put("GpSearchForUpdate",
						this.method_signitures_hlpr
						.search_for_update_method_signiture(an_activity));
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.update_method_signiture(an_activity));
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.delete_method_signiture(an_activity));
				generated_verbs.add("GpDelete");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				the_methods.put(a_verb.getAction_on_data(),
						this.method_signitures_hlpr
						.search__method_signiture(an_activity));
				generated_verbs.add("GpSearch");
				continue;
			}	
		}
		return the_methods;	
	}
	
	public ArrayList<GpLegacyDaoVerbGenInfo> get_verb_method_implmentation(
															GpActivity activity){
		this.implementation_hlpr.setThe_worker(this.the_worker);
		ArrayList<GpLegacyDaoVerbGenInfo> implementations = new ArrayList<GpLegacyDaoVerbGenInfo>();

		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
							.get_all_values_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
							.get_nound_by_id_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
							.search_for_update_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
									.create_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpCreate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")){
				GpLegacyDaoVerbGenInfo implicit_verb = this.implementation_hlpr
							.search_for_update_implementation(a_verb, activity);
				implementations.add(implicit_verb);
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
									.update_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
									.delete_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpDelete");
				continue;
			}
			if(a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				GpLegacyDaoVerbGenInfo impl = this.implementation_hlpr
									.search_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearch");
				continue;
			}
		}
		return implementations;
		
	}
	
	/**
	 * sets up the references that the DAO will use to refer to the 
	 * Resoruce Bundles that it needs
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	/*
	public ArrayList<GpResourceBundleReference> set_up_resource_bundle_references(GpActivity activity) throws Exception{
		
		ArrayList<GpResourceBundleReference> the_refs = new ArrayList<GpResourceBundleReference>();
				
				
		for(GpVerb verb : activity.getTheverbs()){
			GpResourceBundleReference a_ref = new GpResourceBundleReference();
			String primary_noun_name = activity.getPrimary_noun()
												.getName().toLowerCase();
			a_ref.verb_action_on_data = verb.getAction_on_data();
			if(verb.getAction_on_data().equals("GpCreate")){
				
				a_ref.resource_bundle_key  = "${"+ "create_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "create_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpGetAllValues")){
				
				a_ref.resource_bundle_key  = "${"+ "get_all_" 
											+ primary_noun_name + ".sql";
				a_ref.local_string_reference = "get_all_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpSearchForUpdate")){
				
				a_ref.resource_bundle_key  = "${"+ primary_noun_name
											+ "_search_for_update" + ".sql";
				a_ref.local_string_reference = primary_noun_name + "_search_for_update" +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpGetNounById")){
				
				a_ref.resource_bundle_key  = "${"+ "get_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "get_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpUpdate")){
				
				a_ref.resource_bundle_key  = "${"+ "update_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "update_"
												+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpDelete")){
				
				a_ref.resource_bundle_key  = "${"+ "delete_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "delete_"
												+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpSearch")){

				/*  TBD as of 12/30/2014  */
				//continue;
		//	}
			
	//	}
	//	return the_refs;
		
//	}
	
}
