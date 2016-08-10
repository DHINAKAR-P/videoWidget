package com.npb.gp.gen.workers.server.java.spring.support.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.java.GpJavaException;
import com.npb.gp.gen.domain.java.GpJavaInputParms;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.domain.java.GpJavaMethodName;
import com.npb.gp.gen.domain.java.GpJavaReturnParm;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.GpSpringServiceGenWorker;

/**
 * 
 * @author Dan Castillo Date Created: 12/27/2014</br>
 * @since .2</p>
 * 
 *        Contains the logic to generate the methods, based on activity
 *        verbs,</br> in a Spring based Service- it is designed to be called by
 *        the </br> worker that handles the Spring Service generation</p>
 */
@Component("GpServiceGenSupport")
public class GpServiceGenSupport {
	private GpFlowControl the_flow;
	private GpServiceVerbMethodSignitures signiture_hlpr;
	private GpServiceVerbMethodImplementations implementation_hlpr;
	private GpSpringServiceGenWorker the_worker;

	public GpSpringServiceGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpSpringServiceGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpServiceVerbMethodSignitures getSigniture_hlpr() {
		return signiture_hlpr;
	}

	@Resource(name = "GpServiceVerbMethodSignitures")
	public void setSigniture_hlpr(GpServiceVerbMethodSignitures signiture_hlpr) {
		this.signiture_hlpr = signiture_hlpr;
	}

	public GpServiceVerbMethodImplementations getImplementation_hlpr() {
		return implementation_hlpr;
	}

	@Resource(name = "GpServiceVerbMethodImplementations")
	public void setImplementation_hlpr(
			GpServiceVerbMethodImplementations implementation_hlpr) {
		this.implementation_hlpr = implementation_hlpr;
	}

	public void set_flow(GpFlowControl the_flow) {
		this.the_flow = the_flow;

	}

	public ArrayList<GpServiceVerbGenInfo> get_verb_method_implmentation(
			GpActivity activity) {
		this.implementation_hlpr.setThe_worker(this.the_worker);
		ArrayList<GpServiceVerbGenInfo> implementations = new ArrayList<GpServiceVerbGenInfo>();

		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb a_verb : the_verbs) {
			if (a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.get_all_values_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if (a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.get_nound_by_id_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if (a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.search_for_update_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if (a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.create_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpCreate");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")) {
				GpServiceVerbGenInfo implicit_verb = this.implementation_hlpr
						.search_for_update_implementation(a_verb, activity);
				implementations.add(implicit_verb);
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.update_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}
			if (a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.delete_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpDelete");
				continue;
			}
			if (a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")) {
				GpServiceVerbGenInfo impl = this.implementation_hlpr
						.search_implementation(a_verb, activity);
				implementations.add(impl);
				generated_verbs.add("GpSearch");
				continue;
			}

		}

		return implementations;
	}

	public HashMap<String, GpJavaMethodDescription> get_method_signitures(
			GpActivity activity) {

		HashMap<String, GpJavaMethodDescription> the_methods = new HashMap<String, GpJavaMethodDescription>();

		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<String> generated_verbs = new ArrayList<String>();
		GpJavaMethodDescription method_sig_info;
		for (GpVerb a_verb : the_verbs) {
			if (a_verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")) {
				method_sig_info = this.signiture_hlpr
						.get_all_values_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpGetAllValues");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")) {
				method_sig_info = this.signiture_hlpr
						.get_nound_by_id_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpGetNounById");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")) {
				method_sig_info = this.signiture_hlpr
						.search_for_update_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")) {
				method_sig_info = this.signiture_hlpr
						.create_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpCreate");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")) {
				method_sig_info = this.signiture_hlpr
						.search_for_update_method_signiture(activity);
				the_methods.put("GpSearchForUpdate", method_sig_info);
				method_sig_info = this.signiture_hlpr
						.update_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")) {
				method_sig_info = this.signiture_hlpr
						.delete_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpDelete");
				continue;
			}

			if (a_verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")) {
				method_sig_info = this.signiture_hlpr
						.search_method_signiture(activity);
				the_methods.put(a_verb.getAction_on_data(), method_sig_info);
				generated_verbs.add("GpSearch");
				continue;
			}
		}

		return the_methods;

	}

}
