package com.npb.gp.gen.workers.server.java.spring.support.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaException;
import com.npb.gp.gen.domain.java.GpJavaInputParms;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.domain.java.GpJavaMethodName;
import com.npb.gp.gen.domain.java.GpJavaReturnParm;

/**
 * 
 * @author Dan Castillo Date Created: 12/30/2014</br>
 * @since .2</p>
 *
 *        Holds the method signatures for the standard Geppetto verbs on the
 *        Service </br> layer. In the future this information will probably be
 *        held in the DB</p>
 * 
 * 
 */
@Component("GpServiceVerbMethodSignitures")
public class GpServiceVerbMethodSignitures {

	public GpJavaMethodDescription get_all_values_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpArrayList";
		ret_parm.container = true;
		ret_parm.base_name = "GpPrimaryNoun";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "ArrayList<"
				+ an_activity.getPrimary_noun().getName() + ">";

		// NEW WAY - METHOD NAME
		full_signiture.setName("get_all_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "get_all_"
				+ an_activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		// DONT SET IT AS IT ALLREADY IS 0 LENGTH
		full_description = full_description + "()";

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription get_nound_by_id_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " "
				+ an_activity.getPrimary_noun().getName();

		// NEW WAY - METHOD NAME
		full_signiture.setName("get_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "get_"
				+ an_activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(long id, ";

		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";

		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription search_for_update_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " "
				+ an_activity.getPrimary_noun().getName();

		// NEW WAY - METHOD NAME
		full_signiture.setName(an_activity.getPrimary_noun().getName()
				.toLowerCase()
				+ "_search_for_update");
		full_description = full_description + " "
				+ an_activity.getPrimary_noun().getName().toLowerCase()
				+ "_search_for_update";

		// NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(long id, ";

		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";

		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription create_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " "
				+ an_activity.getPrimary_noun().getName();

		// NEW WAY - METHOD NAME
		full_signiture.setName("create_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "create_"
				+ an_activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpPrimaryNoun";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "("
				+ an_activity.getPrimary_noun().getName() + " "
				+ an_activity.getPrimary_noun().getName() + ", ";

		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";

		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription update_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " "
				+ an_activity.getPrimary_noun().getName();

		// NEW WAY - METHOD NAME
		full_signiture.setName("update_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "update_"
				+ an_activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpPrimaryNoun";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "("
				+ an_activity.getPrimary_noun().getName() + " "
				+ an_activity.getPrimary_noun().getName() + ", ";

		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";

		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription delete_method_signiture(
			GpActivity an_activity) {

		// HashMap<String, GpJavaMethodDescription> method_info
		// / = new HashMap<String, GpJavaMethodDescription>();

		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpString";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "String ";

		// NEW WAY - METHOD NAME
		full_signiture.setName("delete_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "delete_"
				+ an_activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(" + "long " + "id" + ", ";

		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";

		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;

	}

	public GpJavaMethodDescription search_method_signiture(
			GpActivity activity) {
		// NEW WAY - visibility
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public";

		// NEW WAY - RETURN PARM
				GpDataType ret_parm = new GpDataType();
				ret_parm.name = "GpArrayList";
				ret_parm.container = true;
				ret_parm.base_name = "GpPrimaryNoun";
				full_signiture.setReturn_parm(ret_parm);
				full_description = full_description + " " + "ArrayList<"
						+ activity.getPrimary_noun().getName() + ">";

		// NEW WAY - METHOD NAME
		full_signiture.setName("search_"
				+ activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "search_"
				+ activity.getPrimary_noun().getName().toLowerCase();

		// NEW WAY - INPUT PARMS
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();
		full_description += "(";
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		for(GpNounAttribute attribute : attribs){
			GpDataType in_parm_one = new GpDataType();
			in_parm_one.name = "GpString";
			in_parm_one.container = false;
			in_parm_one.base_name = attribute.getName().toLowerCase();
			in_parms.add(in_parm_one);
			full_description = full_description + "String "
					+ " " + attribute.getName().toLowerCase() + ", ";
		}
		full_description = full_description.substring(0, full_description.length()-2);
		full_description += ")";
		full_signiture.setInput_parms(in_parms);

		// NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception";

		full_signiture.setDescription(full_description);
		// method_info.put("new_way", full_signiture);

		return full_signiture;
	}

}
