package com.npb.gp.gen.workers.client.sqlite;

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
import com.npb.gp.gen.constants.GpEventVerbConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpSqlQueriesDto;

@Component("GpSqliteQueriesGenSupport")
public class GpSqliteQueriesGenSupport {
	public static String key_name_for_get_all = "get_all_";
	public static String key_name_for_get_by_id = "get_by_id_";
	public static String key_name_for_get_by_parent_id = "get_by_parent_id_";
	public static String key_name_for_create = "create_";
	public static String key_name_for_search_for_update = "search_for_update_";
	public static String key_name_for_update = "update_";
	public static String key_name_for_delete = "delete_";
	public static String key_name_for_delete_by_parent_id = "delete_by_parent_id_";
	public static String key_name_for_search = "search_";
	public GpBaseSqlDto get_all_values_dml_stmt(GpActivity activity) {
		String table_name = activity.getPrimary_noun().getName();
		String get_all_clause = "SELECT * FROM " + table_name;
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_get_all + table_name;
		the_dto.sql_statement = get_all_clause;
		the_dto.bundle_ready_sql_statement = get_all_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpGetAllValues;
		return the_dto;
	}

	public GpBaseSqlDto get_nound_by_id_dml_stmt(GpActivity activity) {
		String table_name = activity.getPrimary_noun().getName();
		String get_by_id_clause = "SELECT * FROM " + table_name + " WHERE id=:id";
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_get_by_id + table_name;
		the_dto.sql_statement = get_by_id_clause;
		the_dto.bundle_ready_sql_statement = get_by_id_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpGetNounById;
		return the_dto;
	}
	
	public GpBaseSqlDto get_nound_by_parent_id_dml_stmt(GpActivity activity,Map<Long, JSONArray> relationships_map) throws Exception {
		GpNoun noun = activity.getPrimary_noun();
		String table_name = noun.getName();
		String parents_stmts = this.handle_parent_attrib(relationships_map, noun, GpBaseVerbsConstants.GpGetNounByParentId);
		if(parents_stmts.isEmpty()){
			throw new Exception("no paren/relationship for get_nound_by_parent_id_dml_stmt");
		}
		String get_by_parent_id_clause = "SELECT * FROM " + table_name + " WHERE ";
		get_by_parent_id_clause += parents_stmts;
		get_by_parent_id_clause = get_by_parent_id_clause.substring(0,get_by_parent_id_clause.length()-4);
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_get_by_parent_id + table_name;
		the_dto.sql_statement = get_by_parent_id_clause;
		the_dto.bundle_ready_sql_statement = get_by_parent_id_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpGetNounByParentId;
		return the_dto;
	}

	public GpBaseSqlDto search_for_update_dml_stmt(GpActivity activity) {
		String table_name = activity.getPrimary_noun().getName();
		String search_for_update_clause = "SELECT * FROM " + table_name + " WHERE id=:id";
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_search_for_update + table_name;
		the_dto.sql_statement = search_for_update_clause;
		the_dto.bundle_ready_sql_statement = search_for_update_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpSearchForUpdate;
		return the_dto;
	}

	public GpBaseSqlDto create_dml_stmt(GpActivity activity, Map<Long, JSONArray> relationships_map) throws JSONException {
		GpNoun noun = activity.getPrimary_noun();
		String table_name = noun.getName();
		String create_clause = "INSERT INTO " + table_name  + " (";
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun().getNounattributes();
		for(GpNounAttribute attrib : attribs){
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
			create_clause += attrib.getName().toLowerCase() + ",";
		}
		create_clause += this.handle_parent_attrib(relationships_map, noun, GpEventVerbConstants.CREATE);
		create_clause = create_clause.substring(0,create_clause.length()-1);
		create_clause += ")";
		create_clause += " values (";
		for(GpNounAttribute attrib : attribs){
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
			create_clause += ":" + attrib.getName().toLowerCase() + ",";
		}
		create_clause += this.handle_parent_attrib(relationships_map, noun, GpEventVerbConstants.CREATE + "param");
		create_clause = create_clause.substring(0,create_clause.length()-1);
		create_clause += ")";
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_create + table_name;
		the_dto.sql_statement = create_clause;
		the_dto.bundle_ready_sql_statement = create_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpCreate;
		return the_dto;
	}

	public GpBaseSqlDto update_dml_stmt(GpActivity activity, Map<Long, JSONArray> relationships_map) throws JSONException {
		GpNoun noun = activity.getPrimary_noun();
		String table_name = noun.getName();
		String update_clause = "UPDATE " + table_name  + " SET";
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun().getNounattributes();
		for(GpNounAttribute attrib : attribs){
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
			update_clause += " " + attrib.getName().toLowerCase() + "=:" + attrib.getName().toLowerCase() + ",";
		}
		update_clause += this.handle_parent_attrib(relationships_map, noun, GpEventVerbConstants.UPDATE);
		update_clause = update_clause.substring(0,update_clause.length()-1);
		update_clause += " WHERE id=:id";
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_update + table_name;
		the_dto.sql_statement = update_clause;
		the_dto.bundle_ready_sql_statement = update_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpUpdate;
		return the_dto;
	}

	public GpBaseSqlDto delete_dml_stmt(GpActivity activity) {
		String table_name = activity.getPrimary_noun().getName();
		String delete_clause = "DELETE FROM " + table_name + " WHERE id" + "=:id";
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_delete + table_name;
		the_dto.sql_statement = delete_clause;
		the_dto.bundle_ready_sql_statement = delete_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpDelete;
		return the_dto;
	}

	public GpBaseSqlDto search_dml_stmt(GpActivity activity, Map<Long, JSONArray> relationships_map) throws JSONException {
		GpNoun noun = activity.getPrimary_noun();
		String table_name = noun.getName();
		String search_clause = "SELECT * FROM " + table_name + " WHERE";
		ArrayList<GpNounAttribute> attribs = activity.getPrimary_noun().getNounattributes();
		for(GpNounAttribute attrib : attribs){
			if(attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_NOUN) || attrib.getSubtype().equals(GpNounConstants.SUB_TYPE_LIST)){
				continue;
			}
			search_clause += " " + attrib.getName().toLowerCase() + " LIKE :" + attrib.getName().toLowerCase() + " AND";
		}
		search_clause += this.handle_parent_attrib(relationships_map, noun, GpEventVerbConstants.SEARCH);
		search_clause = search_clause.substring(0,search_clause.length()-4);
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_search + table_name;
		the_dto.sql_statement = search_clause;
		the_dto.bundle_ready_sql_statement = search_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpSearch;
		return the_dto;
	}
	
	private String handle_parent_attrib(Map<Long, JSONArray> relationships_map, GpNoun noun, String verb_method) throws JSONException {
		String stmt = "";
		//System.out.println(verb_method + " " + relationships_map);
		JSONArray parent_nouns = relationships_map.get(noun.getId());
		if(parent_nouns != null){
			for(int i = 0; i < parent_nouns.length(); i++){
				JSONObject json_parent = parent_nouns.getJSONObject(i);
				String name = json_parent.getString("Noun_name") + "_id";
				String relationship_type = json_parent.getString("type");
				//if(relationship_type.equals("ONE_TO_ONE")){
					if(verb_method.equals(GpBaseVerbsConstants.GpUpdate))
						stmt += " " + name + "=:" + name + ",";
					if(verb_method.equals(GpBaseVerbsConstants.GpCreate))
						stmt += name + ",";
					if(verb_method.equals(GpBaseVerbsConstants.GpCreate + "param"))
						stmt += ":" + name + ",";
					if(verb_method.equals(GpBaseVerbsConstants.GpSearch))
						stmt += " " + name + " LIKE :" + name + " AND ";
					if(verb_method.equals(GpBaseVerbsConstants.GpGetNounByParentId))
						stmt += name + " LIKE :" + name + " AND ";
					if(verb_method.equals(GpBaseVerbsConstants.GpDeleteByParentId))
						stmt += name + " LIKE :" + name + " AND ";
					//continue;
				//}
				//if(relationship_type.equals("ONE_TO_MANY")){
					//stmt += "";
				//}
			}
		}
		return stmt;
	}

	public GpBaseSqlDto delete_nound_by_parent_id_dml_stmt(GpActivity activity,
			Map<Long, JSONArray> relationships_map) throws JSONException {
		GpNoun noun = activity.getPrimary_noun();
		String table_name = noun.getName();
		String delete_clause = "DELETE FROM " + table_name + " WHERE ";
		delete_clause += handle_parent_attrib(relationships_map, noun, GpBaseVerbsConstants.GpDeleteByParentId);
		delete_clause = delete_clause.substring(0,delete_clause.length()-4);
		GpBaseSqlDto the_dto = new GpBaseSqlDto();
		the_dto.local_string_reference = key_name_for_delete_by_parent_id + table_name;
		the_dto.sql_statement = delete_clause;
		the_dto.bundle_ready_sql_statement = delete_clause;
		the_dto.verb_action_on_data = GpBaseVerbsConstants.GpDeleteByParentId;
		return the_dto;
	}
}
