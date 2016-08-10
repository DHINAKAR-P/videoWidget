package com.npb.gp.gen.workers.server.java.spring.support.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaException;
import com.npb.gp.gen.domain.java.GpJavaInputParms;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.domain.java.GpJavaMethodName;
import com.npb.gp.gen.domain.java.GpJavaReturnParm;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.GpSpringServiceGenWorker;
import com.npb.gp.gen.util.dto.GpTypeAndReference;

@Component("GpServiceVerbMethodImplementations")
public class GpServiceVerbMethodImplementations {

	private GpServiceVerbMethodSignitures verb_signiture_hlpr;
	private GpSpringServiceGenWorker the_worker;

	public GpSpringServiceGenWorker getThe_worker() {
		return the_worker;
	}

	public void setThe_worker(GpSpringServiceGenWorker the_worker) {
		this.the_worker = the_worker;
	}

	public GpServiceVerbMethodSignitures getVerb_signiture_hlpr() {
		return verb_signiture_hlpr;
	}

	@Resource(name = "GpServiceVerbMethodSignitures")
	public void setVerb_signiture_hlpr(
			GpServiceVerbMethodSignitures verb_signiture_hlpr) {
		this.verb_signiture_hlpr = verb_signiture_hlpr;
	}

	public GpServiceVerbGenInfo delete_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpDelete");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
					.delete_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// //set up the implementation - handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				if (return_parm.name.equals("GpPrimaryNoun")) {
					the_dto.return_type = activity.getPrimary_noun().getName();
					the_dto.return_reference = "the_"
							+ activity.getPrimary_noun().getName();
				} else if (return_parm.name.equals("GpString")) {
					the_dto.return_type = "String";
					the_dto.return_reference = "method_return_message";
				}

			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName();
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			} else {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";

				i = 0;
				for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
					if (parm.name.equals("GpLong")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", id";
						} else {
							the_dto.dao_call = the_dto.dao_call + "id";
						}

					} else if (parm.name.equals("GpUser")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", user";
						} else {
							the_dto.dao_call = the_dto.dao_call + "user";
						}

					} else if (parm.name.equals("GpPrimaryNoun")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", "
									+ activity.getPrimary_noun().getName();
						} else {
							the_dto.dao_call = the_dto.dao_call
									+ activity.getPrimary_noun().getName();
						}

					}
					i++;
				}
			}
			the_dto.dao_call = the_dto.dao_call + ")";

			return the_dto;

		} catch (Exception e) {

		}

		return null;

	}

	public GpServiceVerbGenInfo update_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpUpdate");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
					.update_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				if (return_parm.name.equals("GpPrimaryNoun")) {
					the_dto.return_type = activity.getPrimary_noun().getName();
					the_dto.return_reference = "the_"
							+ activity.getPrimary_noun().getName();
				}

			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName();
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			} else {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";

				i = 0;
				for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
					if (parm.name.equals("GpLong")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", id";
						} else {
							the_dto.dao_call = the_dto.dao_call + "id";
						}

					} else if (parm.name.equals("GpUser")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", user";
						} else {
							the_dto.dao_call = the_dto.dao_call + "user";
						}

					} else if (parm.name.equals("GpPrimaryNoun")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", "
									+ activity.getPrimary_noun().getName();
						} else {
							the_dto.dao_call = the_dto.dao_call
									+ activity.getPrimary_noun().getName();
						}

					}
					i++;
				}
			}
			the_dto.dao_call = the_dto.dao_call + ")";

			return the_dto;

		} catch (Exception e) {

		}

		return null;

	}

	public GpServiceVerbGenInfo create_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpCreate");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
					.create_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				if (return_parm.name.equals("GpPrimaryNoun")) {
					the_dto.return_type = activity.getPrimary_noun().getName();
					the_dto.return_reference = "the_"
							+ activity.getPrimary_noun().getName();
				}

			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName();
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			} else {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";

				i = 0;
				for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
					if (parm.name.equals("GpLong")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", id";
						} else {
							the_dto.dao_call = the_dto.dao_call + "id";
						}

					} else if (parm.name.equals("GpUser")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", user";
						} else {
							the_dto.dao_call = the_dto.dao_call + "user";
						}

					} else if (parm.name.equals("GpPrimaryNoun")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", "
									+ activity.getPrimary_noun().getName();
						} else {
							the_dto.dao_call = the_dto.dao_call
									+ activity.getPrimary_noun().getName();
						}

					}
					i++;
				}
			}
			the_dto.dao_call = the_dto.dao_call + ")";

			return the_dto;

		} catch (Exception e) {

		}

		return null;
	}

	public GpServiceVerbGenInfo search_for_update_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpSearchForUpdate");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
					.search_for_update_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				if (return_parm.name.equals("GpPrimaryNoun")) {
					the_dto.return_type = activity.getPrimary_noun().getName();
					the_dto.return_reference = "the_"
							+ activity.getPrimary_noun().getName();
				}

			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName();
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			} else {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";

				i = 0;
				for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
					if (parm.name.equals("GpLong")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", id";
						} else {
							the_dto.dao_call = the_dto.dao_call + "id";
						}

					} else if (parm.name.equals("GpUser")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", user";
						} else {
							the_dto.dao_call = the_dto.dao_call + "user";
						}

					}
					i++;
				}
			}
			the_dto.dao_call = the_dto.dao_call + ")";

			return the_dto;

		} catch (Exception e) {

		}

		return null;
	}

	public GpServiceVerbGenInfo get_nound_by_id_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpGetNounById");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription method_sig_info = this.verb_signiture_hlpr
					.get_nound_by_id_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";
			the_dto.method_signiture = method_sig_info.getDescription();
			the_dto.method_name = method_sig_info.getName();

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				if (return_parm.name.equals("GpPrimaryNoun")) {
					the_dto.return_type = activity.getPrimary_noun().getName();
					the_dto.return_reference = "the_"
							+ activity.getPrimary_noun().getName();
				}

			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName();
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			} else {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";

				i = 0;
				for (GpDataType parm : dao_method_sig_info.getInput_parms()) {
					if (parm.name.equals("GpLong")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", id";
						} else {
							the_dto.dao_call = the_dto.dao_call + "id";
						}

					} else if (parm.name.equals("GpUser")) {
						if (i > 0) {
							the_dto.dao_call = the_dto.dao_call + ", user";
						} else {
							the_dto.dao_call = the_dto.dao_call + "user";
						}

					}
					i++;
				}
			}
			the_dto.dao_call = the_dto.dao_call + ")";

			return the_dto;

		} catch (Exception e) {

		}

		return null;

	}

	public GpServiceVerbGenInfo get_all_values_implementation(GpVerb verb,
			GpActivity activity) {

		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpGetAllValues");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription service_method_sig_info = this.verb_signiture_hlpr
					.get_all_values_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";

			the_dto.method_signiture = service_method_sig_info.getDescription();
			the_dto.input_parms = "";

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : service_method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = service_method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				the_dto.return_type = return_parm.name;
			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName() + "_list";
					}
				}
			}

			// set up the implementation - DAO call
			if (dao_method_sig_info.getInput_parms().size() == 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName();
				the_dto.dao_call = the_dto.dao_call + "()";
			}
			the_dto.return_reference = the_dto.return_reference;
			/*
			 * if(service_method_sig_info.getInput_parms().size() == 0){
			 * the_dto.dao_call = the_dto.return_reference + " = " + dao_ref +
			 * "." + the_dto.method_name; the_dto.dao_call = the_dto.dao_call +
			 * "()"; the_dto.return_reference = the_dto.return_reference; }
			 */

			return the_dto;

		} catch (Exception e) {

		}

		return null;

	}

	public GpServiceVerbGenInfo search_implementation(GpVerb a_verb,
			GpActivity activity) {
		try {
			HashMap<String, GpJavaMethodDescription> dao_sigs = this.the_worker
					.get_generation_service().getDao_legacy_worker()
					.get_method_signitures(activity);

			GpJavaMethodDescription dao_method_sig_info = dao_sigs
					.get("GpSearch");

			GpServiceVerbGenInfo the_dto = new GpServiceVerbGenInfo();
			ArrayList<GpTypeAndReference> dao_list = null;
			String dao_ref = "";
			if (this.the_worker.activity_references.size() == 1) {
				dao_list = this.the_worker.activity_references
						.get("all_dao_references");
				dao_ref = dao_list.get(0).reference;
			} else {
				// TBD as for now you would only have one DAO
			}

			// setup the method signature
			GpJavaMethodDescription service_method_sig_info = this.verb_signiture_hlpr
					.search_method_signiture(activity);

			the_dto.authorization = "auths not ready at this time";

			the_dto.method_signiture = service_method_sig_info.getDescription();
			the_dto.input_parms = "";

			// START SET UP EXCEPTION
			// service_exceptions =
			// (GpJavaException)method_sig_info.get("method_exception");
			int i = 0;
			for (String exc : service_method_sig_info.getExceptions()) {
				if (i == 0) {
					the_dto.exceptions = "throws " + exc;
				} else {
					the_dto.exceptions = the_dto.exceptions + ", " + exc;
				}
				i++;
			}

			// handle return type
			GpDataType return_parm = service_method_sig_info.getReturn_parm();
			if (!return_parm.container) {
				the_dto.return_type = return_parm.name;
			} else {
				if (return_parm.name.equals("GpArrayList")) {
					the_dto.return_type = "ArrayList<";
					if (return_parm.base_name.equals("GpPrimaryNoun")) {
						the_dto.return_type = the_dto.return_type
								+ activity.getPrimary_noun().getName() + ">";
						the_dto.return_reference = activity.getPrimary_noun()
								.getName() + "_list";
					}
				}
			}
			
			if (dao_method_sig_info.getInput_parms().size() > 0) {
				the_dto.dao_call = the_dto.return_reference + " = " + dao_ref
						+ "." + dao_method_sig_info.getName() + "(";
				ArrayList<GpDataType> input_params =  dao_method_sig_info.getInput_parms();
				for(GpDataType input_param : input_params){
					the_dto.dao_call += input_param.base_name + ",";
				}
				the_dto.dao_call = the_dto.dao_call.substring(0,the_dto.dao_call.length()-1);
				the_dto.dao_call += ")";
			}
			the_dto.return_reference = the_dto.return_reference;

			return the_dto;

		} catch (Exception e) {

		}

		return null;
	}

}
