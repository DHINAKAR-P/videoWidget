package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/29/2016</br>
 * @since 1.0</p>  
 *
 * Holds the method signatures for the standard Geppetto verbs on the DAO </br>
 * layer. In the future this information will probably be held in the DB</p>
 * 
 * 
 */

@Component("GpSpringBootDaoVerbMethodSignitures")
public class GpSpringBootDaoVerbMethodSignitures {

	
	
	
	public GpJavaMethodDescription
				get_all_values_method_signiture(GpActivity an_activity){

		//HashMap<String, GpJavaMethodDescription> method_info 
		//= new HashMap<String, GpJavaMethodDescription>();
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpArrayList";
		ret_parm.container = true;
		ret_parm.base_name = "GpPrimaryNoun";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "ArrayList<" 
							+ an_activity.getPrimary_noun().getName() + ">";

		

		//NEW WAY - METHOD NAME
		full_signiture.setName("get_all_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "get_all_"
					+ an_activity.getPrimary_noun().getName().toLowerCase()	;

		
		//NEW WAY - INPUT PARMS
		//DONT SET IT AS IT ALLREADY IS 0 LENGTH
		full_description = full_description + "()"	;
		
		

		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);
		
		return full_signiture;
		
	}
	
	public GpJavaMethodDescription
			get_nound_by_id_method_signiture(GpActivity an_activity){
		
		//HashMap<String, GpJavaMethodDescription> method_info 
		//	= new HashMap<String, GpJavaMethodDescription>();

		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 
		
		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " 
						+ an_activity.getPrimary_noun().getName();
		
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("get_"
				+ an_activity.getPrimary_noun().getName().toLowerCase());
		full_description = full_description + " " + "get_"
					+ an_activity.getPrimary_noun().getName().toLowerCase()	;

	
		//NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(long id, "	;
		
		
		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)"	;
		
		full_signiture.setInput_parms(in_parms);

		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;

	}
	
	
	public GpJavaMethodDescription
			search_for_update_method_signiture(GpActivity an_activity){

		//HashMap<String, GpJavaMethodDescription> method_info 
		//= new HashMap<String, GpJavaMethodDescription>();
		
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " 
						+ an_activity.getPrimary_noun().getName();

		
		//NEW WAY - METHOD NAME
		full_signiture.setName(an_activity.getPrimary_noun()
						.getName().toLowerCase() + "_search_for_update"); 
		full_description = full_description + " "  
								+ an_activity.getPrimary_noun()
								.getName().toLowerCase() + "_search_for_update"; 

		
	
		//NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(long id, "	;
		
		
		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)"	;
		
		full_signiture.setInput_parms(in_parms);

		
		
		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;

	}
	
	public GpJavaMethodDescription
			create_method_signiture(GpActivity an_activity){
		
		//HashMap<String, GpJavaMethodDescription> method_info
		//		= new HashMap<String, GpJavaMethodDescription>();
	
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " 
						+ an_activity.getPrimary_noun().getName();
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("create_" + an_activity
				.getPrimary_noun().getName().toLowerCase()); 
		full_description = full_description + " "  
								+ "create_" + an_activity
								.getPrimary_noun().getName().toLowerCase(); 

		
		//NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpPrimaryNoun";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(" + an_activity.getPrimary_noun().getName()
				+ " " + an_activity.getPrimary_noun().getName() + ", ";
		
		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)"	;
		
		full_signiture.setInput_parms(in_parms);
		
		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;
	
	}

	
	
	public GpJavaMethodDescription
					update_method_signiture(GpActivity an_activity){
		
		
		//HashMap<String, GpJavaMethodDescription> method_info
		//			= new HashMap<String, GpJavaMethodDescription>();
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 
		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpPrimaryNoun";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " 
						+ an_activity.getPrimary_noun().getName();
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("update_" + an_activity
				.getPrimary_noun().getName().toLowerCase()); 
		full_description = full_description + " "  
								+ "update_" + an_activity
								.getPrimary_noun().getName().toLowerCase(); 

		
		//NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpPrimaryNoun";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(" + an_activity.getPrimary_noun().getName()
				+ " " + an_activity.getPrimary_noun().getName() + ", ";
		
		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";
		
		full_signiture.setInput_parms(in_parms);
		

		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;

	}

	
	public GpJavaMethodDescription
						delete_method_signiture(GpActivity an_activity){
		
		//HashMap<String, GpJavaMethodDescription> method_info
		///			= new HashMap<String, GpJavaMethodDescription>();
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpString";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "String ";
		
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("delete_" + an_activity
				.getPrimary_noun().getName().toLowerCase()); 
		full_description = full_description + " "  
								+ "delete_" + an_activity
								.getPrimary_noun().getName().toLowerCase(); 
		
		
		
		//NEW WAY - INPUT PARMS
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		GpDataType in_parm_one = new GpDataType();
		in_parm_one.name = "GpLong";
		in_parm_one.container = false;
		in_parm_one.base_name = "";
		in_parms.add(in_parm_one);
		full_description = full_description + "(" + "long "	+ "id"  + ", ";
		
		GpDataType in_parm_two = new GpDataType();
		in_parm_two.name = "GpUser";
		in_parm_two.container = false;
		in_parm_two.base_name = "";
		in_parms.add(in_parm_two);
		full_description = full_description + "GpUser user)";
		
		full_signiture.setInput_parms(in_parms);
		
		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;
		
	}
	
	/* TBD AS OF 12/30/2014 */
	public GpJavaMethodDescription
					search_method_signiture(GpActivity an_activity){
		
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		
		return full_signiture;
		
	}

	public GpJavaMethodDescription search__method_signiture(
			GpActivity activity) {
	
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpArrayList";
		ret_parm.container = true;
		ret_parm.base_name = "GpPrimaryNoun";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "ArrayList<" 
							+ activity.getPrimary_noun().getName() + ">";
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("search_" + activity
				.getPrimary_noun().getName().toLowerCase()); 
		full_description = full_description + " "  
								+ "search_" + activity
								.getPrimary_noun().getName().toLowerCase(); 

		
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun()
				.getNounattributes();
		full_description += "(";
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>(); 
		for(GpNounAttribute attribute : attribs){
			if(attribute.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attribute.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST))
				continue;
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
		
		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;
	}

	public GpJavaMethodDescription get_nound_by_parent_id_method_signiture(GpActivity an_activity, Map<Long, JSONArray> map) throws JSONException {
		JSONArray json_parents = map.get(an_activity.getPrimary_noun().getId());
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 
		boolean is_one_to_many = false;
		if(json_parents != null){
			for(int i=0;i<json_parents.length(); i++){
				JSONObject json_parent = json_parents.getJSONObject(i);
				String type = json_parent.getString("type");
				if(type.equals("ONE_TO_MANY"))
					is_one_to_many = true;
			}
		}
		
		//NEW WAY - RETURN PARM
		if(is_one_to_many){
			GpDataType ret_parm = new GpDataType();
			ret_parm.name = "GpArrayList";
			ret_parm.container = true;
			ret_parm.base_name = "GpPrimaryNoun";
			full_signiture.setReturn_parm(ret_parm);
			full_description = full_description + " " + "ArrayList<" 
								+ an_activity.getPrimary_noun().getName() + ">";
		}
		else{
			GpDataType ret_parm = new GpDataType();
			ret_parm.name = "GpPrimaryNoun";
			ret_parm.container = false;
			ret_parm.base_name = "";
			full_signiture.setReturn_parm(ret_parm);
			full_description = full_description + " " 
							+ an_activity.getPrimary_noun().getName();
		}
		
		
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("get_"
				+ an_activity.getPrimary_noun().getName().toLowerCase() 
				+ "_by_parent_id");
		full_description = full_description + " " + "get_"
					+ an_activity.getPrimary_noun().getName().toLowerCase()	
					+ "_by_parent_id";

	
		//NEW WAY - INPUT PARMS
		full_description += "(";
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		
		if(json_parents != null){
			for(int i=0;i<json_parents.length(); i++){
				JSONObject json_parent = json_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				GpDataType in_parm_one = new GpDataType();
				in_parm_one.name = "GpLong";
				in_parm_one.container = false;
				in_parm_one.base_name = name;
				in_parms.add(in_parm_one);
				full_description += "long " + name + ",";
			}
		}
		full_description = full_description.substring(0,full_description.length()-1);
		full_description += ")";
		
		full_signiture.setInput_parms(in_parms);

		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;
	}

	public GpJavaMethodDescription delete_nound_by_parent_id_method_signiture(GpActivity an_activity,
			Map<Long, JSONArray> relationships_map) throws JSONException {
		//HashMap<String, GpJavaMethodDescription> method_info
		///			= new HashMap<String, GpJavaMethodDescription>();
		
		//NEW WAY - visibility 
		GpJavaMethodDescription full_signiture = new GpJavaMethodDescription();
		String full_description = "";
		full_signiture.setVisibility("GpPublic");
		full_description = full_description + "public"; 

		
		//NEW WAY - RETURN PARM
		GpDataType ret_parm = new GpDataType();
		ret_parm.name = "GpString";
		ret_parm.container = false;
		ret_parm.base_name = "";
		full_signiture.setReturn_parm(ret_parm);
		full_description = full_description + " " + "String ";
		
		
		//NEW WAY - METHOD NAME
		full_signiture.setName("delete_" + an_activity
				.getPrimary_noun().getName().toLowerCase() + "_by_parent_id"); 
		full_description = full_description + " "  
								+ "delete_" + an_activity
								.getPrimary_noun().getName().toLowerCase() + "_by_parent_id"; 
		
		
		
		//NEW WAY - INPUT PARMS
		full_description += "(";
		ArrayList<GpDataType> in_parms = new ArrayList<GpDataType>();
		JSONArray json_parents = relationships_map.get(an_activity.getPrimary_noun().getId());
		if(json_parents != null){
			for(int i=0;i<json_parents.length(); i++){
				JSONObject json_parent = json_parents.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				name = name + "_id";
				GpDataType in_parm_one = new GpDataType();
				in_parm_one.name = "GpLong";
				in_parm_one.container = false;
				in_parm_one.base_name = name;
				in_parms.add(in_parm_one);
				full_description += "long " + name + ",";
			}
		}
		full_description = full_description.substring(0,full_description.length()-1);
		full_description += ")";
		full_signiture.setInput_parms(in_parms);
		
		//NEW WAY - EXCEPTIONS
		ArrayList<String> exceps = new ArrayList<String>();  
		exceps.add("Exception");
		full_signiture.setExceptions(exceps);
		full_description = full_description + " " + "throws Exception"	;
		
		
		full_signiture.setDescription(full_description);
		//method_info.put("new_way", full_signiture);

		
		return full_signiture;
	}


}
