package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;



public class Noun_with_attributes_mapper implements RowMapper<GpDto_noun_and_attributes> {
	
	public GpDto_noun_and_attributes mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpDto_noun_and_attributes dto = new GpDto_noun_and_attributes();
		
		/* NOUN  START */
		dto.setNoun_id(rs.getLong("NOUN_ID"));
		dto.setNoun_name(rs.getString("NOUN_NAME"));
		dto.setNoun_label(rs.getString("NOUN_LABEL"));
		dto.setNoun_description(rs.getString("NOUN_DESCRIPTION"));
		dto.setNoun_projectid(rs.getLong("NOUN_PROJECT_ID"));
		dto.setNoun_moduleid(rs.getLong("NOUN_MODULE_ID"));
		dto.setNoun_cache_enabled(rs.getBoolean("NOUN_CACHE_ENABLED"));

		
		dto.setNoun_notes(rs.getString("NOUN_NOTES"));
		dto.setNoun_createdby(rs.getLong("NOUN_CREATED_BY"));
		dto.setNoun_createdate(rs.getTimestamp("NOUN_CREATED_DATE"));
		dto.setNoun_lastmodifiedby(rs.getLong("NOUN_LAST_MODIFIED_BY"));
		dto.setNoun_lastmodifieddate(rs.getTimestamp("NOUN_LAST_MODIFIED_DATE"));
		dto.setNoun_default_activity_id(rs.getLong("NOUN_DEFAULT_ACTIVITY_ID"));
		/* NOUN  END */
	
		
		
		
		/* ATTRIBUTES START */
		dto.setAttribute_id(rs.getLong("NOUN_ATTRIBUTE_ID"));
		dto.setAttribute_nounid(rs.getLong("NOUN_ATTRIBUTE_NOUN_ID"));
		dto.setAttribute_name(rs.getString("NOUN_ATTRIBUTE_NAME"));
		dto.setAttribute_label(rs.getString("NOUN_ATTRIBUTE_LABEL"));
		dto.setAttribute_description(rs.getString("NOUN_ATTRIBUTE_DESCRIPTION"));
		dto.setAttribute_type(rs.getString("NOUN_ATTRIBUTE_TYPE"));
		dto.setAttribute_sub_type(rs.getString("NOUN_ATTRIBUTE_SUB_TYPE"));
		dto.setAttribute_sub_type_modifier(rs.getString("NOUN_ATTRIBUTE_SUB_TYPE_MODIFIER"));
		dto.setAttribute_part_of_unique_key(rs.getBoolean("NOUN_ATTRIBUTE_PART_OF_UNIQUE_KEY"));
		dto.setAttribute_data_length(rs.getString("NOUN_ATTRIBUTE_DATA_LENGTH"));
		dto.setAttribute_relationships(rs.getString("NOUN_ATTRIBUTE_RELATIONSHIPS"));
		dto.setAttribute_notes(rs.getString("NOUN_ATTRIBUTE_NOTES"));
		dto.setAttribute_createdby(rs.getLong("NOUN_ATTRIBUTE_CREATED_BY"));
		dto.setAttribute_createdate(rs.getTimestamp("NOUN_ATTRIBUTE_CREATED_DATE"));
		dto.setAttribute_lastmodifiedby(rs.getLong("NOUN_ATTRIBUTE_LAST_MODIFIED_BY"));
		dto.setAttribute_lastmodifieddate(rs.getTimestamp("NOUN_ATTRIBUTE_LAST_MODIFIED_DATE"));
		
		/* ATTRIBUTES  END */
		
		
		
		return dto;
	}

}
