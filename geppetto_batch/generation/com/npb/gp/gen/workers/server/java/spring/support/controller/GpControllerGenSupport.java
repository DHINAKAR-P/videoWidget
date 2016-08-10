package com.npb.gp.gen.workers.server.java.spring.support.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.util.dto.GpControllerVerbGenInfo;

/**
 * 
 * @author Dan Castillo Date Created: 12/26/2014</br>
 * @since .2</p>
 * 
 *        Contains the logic to generate the methods, based on activity
 *        verbs,</br> in a Spring based controller - it is designed to be called
 *        by the </br> worker that handles the Spring Controller generation</p>
 */
@Component("GpControllerGenSupport")
public class GpControllerGenSupport {

	public ArrayList<GpControllerVerbGenInfo> prep_controller_verb_generation(
			GpActivity activity, GpServerJavaSpringGenService the_service,
			GpFlowControl the_flow) throws Exception {

		ArrayList<GpControllerVerbGenInfo> the_verbs_methods = new ArrayList<GpControllerVerbGenInfo>();

		HashMap<String, GpJavaMethodDescription> service_methods = the_service
				.getService_gen_worker().get_method_signitures(activity);
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb verb : activity.getTheverbs()) {

			if (verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")) {

				GpControllerVerbGenInfo the_dto = this.handle_get_all_values(
						verb, activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpGetAllValues");
			} else if (verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")) {
				GpControllerVerbGenInfo the_dto = this.handle_get_noun_by_id(
						verb, activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpGetNounById");
			} else if (verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")) {
				GpControllerVerbGenInfo the_dto = this
						.handle_search_for_update(verb, activity,
								service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpSearchForUpdate");
			} else if (verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")) {
				GpControllerVerbGenInfo the_dto = this.handle_create(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpCreate");
			} else if (verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")) {
				GpControllerVerbGenInfo implicit_dto = this.handle_search_for_update(verb,
						activity, service_methods);
				the_verbs_methods.add(implicit_dto);
				GpControllerVerbGenInfo the_dto = this.handle_update(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
			} else if (verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")) {
				GpControllerVerbGenInfo the_dto = this.handle_delete(verb,
						activity, service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpDelete");
			} else if (verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")) {
				GpControllerVerbGenInfo the_dto = this
						.handle_search_(verb, activity,
								service_methods);
				the_verbs_methods.add(the_dto);
				generated_verbs.add("GpSearch");
			}

		}

		return the_verbs_methods;
	}
	
	public Map<String,GpControllerVerbGenInfo> get_map_controller(
			GpActivity activity, GpServerJavaSpringGenService the_service) throws Exception {

		Map<String,GpControllerVerbGenInfo> the_map = new HashMap<>();

		HashMap<String, GpJavaMethodDescription> service_methods = the_service
				.getService_gen_worker().get_method_signitures(activity);
		List<String> generated_verbs = new ArrayList<String>();
		for (GpVerb verb : activity.getTheverbs()) {

			if (verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")) {

				GpControllerVerbGenInfo the_dto = this.handle_get_all_values(
						verb, activity, service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpGetAllValues");
			} else if (verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")) {
				GpControllerVerbGenInfo the_dto = this.handle_get_noun_by_id(
						verb, activity, service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpGetNounById");
			} else if (verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")) {
				GpControllerVerbGenInfo the_dto = this
						.handle_search_for_update(verb, activity,
								service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpSearchForUpdate");
			} else if (verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")) {
				GpControllerVerbGenInfo the_dto = this.handle_create(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpCreate");
			} else if (verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")) {
				GpControllerVerbGenInfo implicit_dto = this.handle_search_for_update(verb,
						activity, service_methods);
				the_map.put("GpSearchForUpdate", implicit_dto);
				GpControllerVerbGenInfo the_dto = this.handle_update(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
			} else if (verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")) {
				GpControllerVerbGenInfo the_dto = this.handle_delete(verb,
						activity, service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpDelete");
			} else if (verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")) {
				GpControllerVerbGenInfo the_dto = this
						.handle_search_(verb, activity,
								service_methods);
				the_map.put(verb.getAction_on_data(), the_dto);
				generated_verbs.add("GpSearch");
			}

		}

		return the_map;
	}

	private GpControllerVerbGenInfo handle_search_(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {
		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();
		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.method_name = "search_"
				+ activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = the_dto.method_name;
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();
		String input_params_string = "";
		for (GpNounAttribute attribute : attribs) {
			input_params_string += "@RequestParam(required = false, value = \""
					+ attribute.getName().toLowerCase() + "\", defaultValue = \"%%\") String "
					+ attribute.getName().toLowerCase() + ",";
		}
		input_params_string = input_params_string.substring(0,
				input_params_string.length() - 1);
		the_dto.input_parms = input_params_string;

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "a_" + activity.getPrimary_noun().getName();

			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// END - set up the return type reference

		// START - SERVICE CALL
		// GpJavaMethodName service_method_name =
		// (GpJavaMethodName)the_method_info.get("method_name");
		if (method_description.getInput_parms().size() > 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			ArrayList<GpDataType> input_params =  method_description.getInput_parms();
			for(GpDataType input_param : input_params){
				the_dto.service_call += input_param.base_name + ",";
			}
			the_dto.service_call = the_dto.service_call.substring(0,the_dto.service_call.length()-1);
			the_dto.service_call += ")";
		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return "
				+ activity.getPrimary_noun().getName() + "_list";

		// START - RETURN

		return the_dto;
	}

	private GpControllerVerbGenInfo handle_delete(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "DELETE";
		the_dto.method_name = "delete_" + activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = "delete_" + activity.getPrimary_noun().getName() + "/{"+ activity.getPrimary_noun().getName() + "_id" +"}";
		the_dto.input_parms = "@PathVariable(value=" + "\""
				+ activity.getPrimary_noun().getName() + "_id" + "\")"
				+ "long " + activity.getPrimary_noun().getName() + "_id";

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "the_" + activity.getPrimary_noun().getName()
						+ " = new " + the_dto.return_type + "()";
			} else if (method_description.getReturn_parm().name
					.equals("GpString")) {
				the_dto.return_type_reference_set_up = "String service_return_msg = \"\"";
				the_dto.return_type = "String ";
			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		i = 0;
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		} else {
			the_dto.service_call = "service_return_msg" + " = "
					+ activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			String parm_string = "";
			for (GpDataType parm : method_description.getInput_parms()) {
				if (i > 0) {
					parm_string = parm_string + ", ";
				}
				if (!parm.container) {
					if (parm.name.equals("GpLong")) {
						parm_string = parm_string
								+ activity.getPrimary_noun().getName() + "_id"; // how
																				// do
																				// you
																				// know
																				// the
																				// parm
																				// name
																				// is
																				// ID?
					} else if (parm.name.equals("GpUser")) {
						parm_string = parm_string + "super.getUser()";
					}
				}
				i++;
			}
			parm_string = parm_string + ")";
			the_dto.service_call = the_dto.service_call + parm_string;

		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return " + "service_return_msg";

		// START - RETURN

		return the_dto;

	}

	private GpControllerVerbGenInfo handle_update(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "PUT";
		the_dto.method_name = "update_" + activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = the_dto.method_name;
		the_dto.input_parms = "@RequestBody "
				+ activity.getPrimary_noun().getName() + " the_"
				+ activity.getPrimary_noun().getName();

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "a_" + activity.getPrimary_noun().getName();

			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		i = 0;
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		} else {
			the_dto.service_call = "a_" + activity.getPrimary_noun().getName()
					+ " = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			String parm_string = "";
			for (GpDataType parm : method_description.getInput_parms()) {
				if (i > 0) {
					parm_string = parm_string + ", ";
				}
				if (!parm.container) {
					if (parm.name.equals("GpLong")) {
						parm_string = parm_string
								+ activity.getPrimary_noun().getName() + "_id"; // how
																				// do
																				// you
																				// know
																				// the
																				// parm
																				// name
																				// is
																				// ID?
					} else if (parm.name.equals("GpUser")) {
						parm_string = parm_string + "super.getUser()";
					} else if (parm.name.equals("GpPrimaryNoun")) {
						parm_string = parm_string + "the_"
								+ activity.getPrimary_noun().getName();
					}
				}
				i++;
			}
			parm_string = parm_string + ")";
			the_dto.service_call = the_dto.service_call + parm_string;

		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return " + "a_"
				+ activity.getPrimary_noun().getName();

		// START - RETURN

		return the_dto;
	}

	private GpControllerVerbGenInfo handle_create(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "POST";
		the_dto.method_name = "create_" + activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = the_dto.method_name;
		the_dto.input_parms = "@RequestBody "
				+ activity.getPrimary_noun().getName() + " the_"
				+ activity.getPrimary_noun().getName();

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "a_" + activity.getPrimary_noun().getName();

			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		i = 0;
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		} else {
			the_dto.service_call = "a_" + activity.getPrimary_noun().getName()
					+ " = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			String parm_string = "";
			for (GpDataType parm : method_description.getInput_parms()) {
				if (i > 0) {
					parm_string = parm_string + ", ";
				}
				if (!parm.container) {
					if (parm.name.equals("GpLong")) {
						parm_string = parm_string
								+ activity.getPrimary_noun().getName() + "_id"; // how
																				// do
																				// you
																				// know
																				// the
																				// parm
																				// name
																				// is
																				// ID?
					} else if (parm.name.equals("GpUser")) {
						parm_string = parm_string + "super.getUser()";
					} else if (parm.name.equals("GpPrimaryNoun")) {
						parm_string = parm_string + "the_"
								+ activity.getPrimary_noun().getName();
					}
				}
				i++;
			}
			parm_string = parm_string + ")";
			the_dto.service_call = the_dto.service_call + parm_string;

		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return " + "a_"
				+ activity.getPrimary_noun().getName();

		// START - RETURN

		return the_dto;

	}

	private GpControllerVerbGenInfo handle_search_for_update(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		/*
		 * 
		 * for now there is no is difference in get a noun by id and search for
		 * update at the controller level - at the Service level there might be
		 * depending on how locking is implemented - Dan Castillo 12/17/2014
		 */
		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.method_name = "search_for_update_" + activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = "search_for_update_" + activity.getPrimary_noun().getName() + "/{"+ activity.getPrimary_noun().getName() + "_id" +"}";
		the_dto.input_parms = "@PathVariable(value=" + "\""
				+ activity.getPrimary_noun().getName() + "_id" + "\")"
				+ " long " + activity.getPrimary_noun().getName() + "_id";

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get("GpSearchForUpdate"));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "the_" + activity.getPrimary_noun().getName()
						+ " = new " + the_dto.return_type + "()";
			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		i = 0;
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		} else {
			the_dto.service_call = "the_"
					+ activity.getPrimary_noun().getName() + " = "
					+ activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			String parm_string = "";
			for (GpDataType parm : method_description.getInput_parms()) {
				if (i > 0) {
					parm_string = parm_string + ", ";
				}
				if (!parm.container) {
					if (parm.name.equals("GpLong")) {
						parm_string = parm_string
								+ activity.getPrimary_noun().getName() + "_id"; // how
																				// do
																				// you
																				// know
																				// the
																				// parm
																				// name
																				// is
																				// ID?
					} else if (parm.name.equals("GpUser")) {
						parm_string = parm_string + "super.getUser()";
					}
				}
				i++;
			}
			parm_string = parm_string + ")";
			the_dto.service_call = the_dto.service_call + parm_string;

		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return " + "the_"
				+ activity.getPrimary_noun().getName();

		// START - RETURN

		return the_dto;

	}

	private GpControllerVerbGenInfo handle_get_noun_by_id(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.method_name = "get_" + activity.getPrimary_noun().getName()
				+ "_by_id";
		the_dto.request_mapping_value = the_dto.method_name;
		the_dto.input_parms = "@RequestParam(" + "\""
				+ activity.getPrimary_noun().getName() + "_id" + "\")"
				+ "long " + activity.getPrimary_noun().getName() + "_id";

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// START SET UP EXCEPTION
		int i = 0;
		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		if (!method_description.getReturn_parm().container) {
			if (method_description.getReturn_parm().name
					.equals("GpPrimaryNoun")) {
				the_dto.return_type = activity.getPrimary_noun().getName();

				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + "the_" + activity.getPrimary_noun().getName()
						+ " = new " + the_dto.return_type + "()";
			}

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		i = 0;
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		} else {
			the_dto.service_call = "the_"
					+ activity.getPrimary_noun().getName() + " = "
					+ activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "(";
			String parm_string = "";
			for (GpDataType parm : method_description.getInput_parms()) {
				if (i > 0) {
					parm_string = parm_string + ", ";
				}
				if (!parm.container) {
					if (parm.name.equals("GpLong")) {
						parm_string = parm_string
								+ activity.getPrimary_noun().getName() + "_id"; // how
																				// do
																				// you
																				// know
																				// the
																				// parm
																				// name
																				// is
																				// ID?
					} else if (parm.name.equals("GpUser")) {
						parm_string = parm_string + "super.getUser()";
					}
				}
				i++;
			}
			parm_string = parm_string + ")";
			the_dto.service_call = the_dto.service_call + parm_string;

		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return " + "the_"
				+ activity.getPrimary_noun().getName();

		// START - RETURN

		return the_dto;
	}

	private GpControllerVerbGenInfo handle_get_all_values(GpVerb verb,
			GpActivity activity,
			HashMap<String, GpJavaMethodDescription> service_methods) {

		GpControllerVerbGenInfo the_dto = new GpControllerVerbGenInfo();

		the_dto.authorization = "auths not ready at this time";
		the_dto.request_method_type = "GET";
		the_dto.method_name = "get_all_" + activity.getPrimary_noun().getName();
		the_dto.request_mapping_value = the_dto.method_name;
		the_dto.input_parms = "";

		// GpJavaInputParms service_method_input_parms;
		// GpJavaException service_exceptions;
		// GpJavaReturnParm service_return_type;

		// HashMap<String, GpJavaMethodDescription> the_method_info =
		// service_methods.get(verb.getAction_on_data());
		GpJavaMethodDescription method_description = ((GpJavaMethodDescription) service_methods
				.get(verb.getAction_on_data()));

		// service_method_input_parms =
		// ((GpJavaInputParms)the_method_info.get("method_input_parms"));

		// START SET UP EXCEPTION
		// service_exceptions =
		// (GpJavaException)the_method_info.get("method_exception");
		int i = 0;

		for (String exc : method_description.getExceptions()) {
			if (i == 0) {
				the_dto.exceptions = "throws " + exc;
			} else {
				the_dto.exceptions = the_dto.exceptions + ", " + exc;
			}
			i++;
		}
		// END SET UP EXCEPTION

		// START - set up the return type reference
		// service_return_type =
		// ((GpJavaReturnParm)the_method_info.get("method_return_parm"));

		if (!method_description.getReturn_parm().container) {

		} else {
			if (method_description.getReturn_parm().name.equals("GpArrayList")) {
				the_dto.return_type = "ArrayList<";

				if (method_description.getReturn_parm().base_name
						.equals("GpPrimaryNoun")) {
					the_dto.return_type = the_dto.return_type
							+ activity.getPrimary_noun().getName() + ">";
				}
				the_dto.return_type_reference_set_up = the_dto.return_type
						+ " " + activity.getPrimary_noun().getName() + "_list"
						+ " = new " + the_dto.return_type + "()";
			}
		}

		// END - set up the return type reference

		// START - SERVICE CALL
		// GpJavaMethodName service_method_name =
		// (GpJavaMethodName)the_method_info.get("method_name");
		if (method_description.getInput_parms().size() == 0) {
			the_dto.service_call = activity.getPrimary_noun().getName()
					+ "_list = " + activity.getName() + "_" + "service" + "."
					+ method_description.getName() + "()";
		}
		// END - SERVICE CALL

		// START - RETURN

		the_dto.return_reference = "return "
				+ activity.getPrimary_noun().getName() + "_list";

		// START - RETURN

		return the_dto;
	}

}
