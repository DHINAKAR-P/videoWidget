package com.npb.gp.gen.workers.server.sql.support.ddl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;

@Component("GpSqlDMLGenSupport")
public class GpSqlDMLGenSupport {
	
	private GpNounDao noun_dao;
	

	public String get_drop_db_statement(GpProject the_project) {
		String db_name = the_project.getName().toLowerCase();
		String drop_ddl_statement = "DROP DATABASE IF EXISTS " + db_name + ";";
		return drop_ddl_statement;
	}

	public String get_create_db_statement(GpProject the_project, String character_set, String collation) {
		String db_name = the_project.getName().toLowerCase();
		String create_ddl_statement = "CREATE DATABASE " + db_name +"\n"
				+ "DEFAULT CHARACTER SET " + character_set + "\n"
				+ "COLLATE " + collation + ";";
		return create_ddl_statement;
	}

	public List<GpBaseSqlDto> get_table_create_statement_based_on_project(GpProject the_project) throws Exception {
		List<GpBaseSqlDto> sql_statements = new ArrayList<>();
		List<GpNoun> nouns = noun_dao.find_by_project_id(the_project.getId());
		Map<Long, JSONArray> relationships_map = this.handle_relationships(nouns);
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			String create_db_clause  = "CREATE TABLE " + noun.getName() + "(\n";
			create_db_clause += get_implicit_columns();
			for(GpNounAttribute attr : attribs){
				create_db_clause += this.get_attribs_statements(attr);
			}
			create_db_clause += this.handle_parent_attrib(relationships_map,noun);
			create_db_clause = create_db_clause + "PRIMARY KEY (id)\n";
			create_db_clause = create_db_clause + ")ENGINE=InnoDB DEFAULT CHARSET=utf8";
			GpBaseSqlDto sqlDto = new GpBaseSqlDto();
			sqlDto.sql_statement = create_db_clause;
			sql_statements.add(sqlDto);
		}
		return sql_statements;
	}
	
	public String get_implicit_columns(){
		String implicit_columns_string = "";
		implicit_columns_string += "id bigint(19) NOT NULL auto_increment,\n";
		implicit_columns_string += "created_date datetime NOT NULL DEFAULT NOW(),\n";
		implicit_columns_string += "created_by bigint(19) NOT NULL DEFAULT 0,\n";
		implicit_columns_string += "updated_date datetime NOT NULL DEFAULT NOW(),\n";
		implicit_columns_string += "updated_by bigint(19) NOT NULL DEFAULT 0,\n";
		return implicit_columns_string;
	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						JSONArray parent_nouns = relationships_map.get(noun_id);
						if(parent_nouns == null)
							parent_nouns = new JSONArray();
						JSONObject json_parent = new JSONObject();
						json_parent.put("type", "ONE_TO_ONE");
						json_parent.put("Noun_name", noun.getName());
						parent_nouns.put(json_parent);
						relationships_map.put(noun_id, parent_nouns);
					}
					continue;
				}
				if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(type.equals("Noun")){
							long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
							JSONArray parent_nouns = relationships_map.get(noun_id);
							if(parent_nouns == null)
								parent_nouns = new JSONArray();
							JSONObject json_parent = new JSONObject();
							json_parent.put("type", "ONE_TO_MANY");
							json_parent.put("Noun_name", noun.getName());
							parent_nouns.put(json_parent);
							relationships_map.put(noun_id, parent_nouns);
						}
					}
					continue;
				}
			}
		}
		return relationships_map;
	}
	
	private String handle_parent_attrib(Map<Long, JSONArray> relationships_map, GpNoun noun) throws JSONException {
		String stmt = "";
		JSONArray parent_nouns = relationships_map.get(noun.getId());
		if(parent_nouns != null){
			for(int i = 0; i < parent_nouns.length(); i++){
				JSONObject json_parent = parent_nouns.getJSONObject(i);
				String name = json_parent.getString("Noun_name");
				String relationship_type = json_parent.getString("type");
				if(relationship_type.equals("ONE_TO_ONE")){
					stmt += name + "_id bigint(19) NOT NULL UNIQUE,\n";
					continue;
				}
				if(relationship_type.equals("ONE_TO_MANY")){
					stmt += name + "_id bigint(19) NOT NULL,\n";
					continue;
				}
			}
		}
		return stmt;
	}

	private String get_attribs_statements(GpNounAttribute attr) throws Exception {
		String sub_type = attr.getSubtype();
		if(sub_type.equals(GpNounConstants.SUB_TYPE_NUMBER)){
			return attr.getName().toLowerCase() + " bigint(19),\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_TEXT)){
			return attr.getName().toLowerCase() + " varchar(255),\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_CURRENCY) || sub_type.equals(GpNounConstants.SUB_TYPE_DECIMAL)){
			return attr.getName().toLowerCase() + " decimal(25,4),\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_BOOL)){
			return attr.getName().toLowerCase()	+ " char(1),\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_DATE)){
			return attr.getName().toLowerCase() + " Date,\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_PICTURE) || sub_type.equals(GpNounConstants.SUB_TYPE_SOUND)
				|| sub_type.equals(GpNounConstants.SUB_TYPE_VIDEO)){
			return attr.getName().toLowerCase() + " MEDIUMBLOB,\n";
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){//nothing to add to DB
			return "";		
		}
		if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){//nothing to add to DB
			return "";
		}
		throw new Exception("No attribute type for this attribute " + attr.getName());
	}

	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}
	
}
