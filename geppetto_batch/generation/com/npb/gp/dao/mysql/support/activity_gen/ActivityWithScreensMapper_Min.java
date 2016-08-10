package com.npb.gp.dao.mysql.support.activity_gen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ActivityWithScreensMapper_Min implements RowMapper<GpDtoActivity_and_Screens> {

	@Override
	public  GpDtoActivity_and_Screens mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		GpDtoActivity_and_Screens dto = new GpDtoActivity_and_Screens();

		dto.setId(rs.getLong("ACTIVITY_ID"));
		dto.setName(rs.getString("ACTIVITY_NAME"));
		dto.setLabel(rs.getString("ACTIVITY_LABEL"));
		dto.setDescription(rs.getString("ACTIVITY_DESCRIPTION"));
		dto.setPrimary_noun_id(rs.getLong("ACTIVITY_PRIMARY_NOUN_ID"));
		dto.setSecondary_nouns(rs.getString("ACTIVITY_SECONDARY_NOUNS"));
		//dto.setProjectid(rs.getLong("projectid"));
		//dto.setNotes(rs.getString("notes"));
		//dto.setCreated_date(rs.getTimestamp("created_date"));
		//dto.setCreated_by(rs.getLong("created_by"));
		//dto.setLast_modified_date(rs.getTimestamp("last_modified_date"));
		//dto.setLast_modified_by(rs.getLong("last_modified_by"));
		//dto.setSecondary_nouns(rs.getString("secondary_nouns"));
		dto.setScreen_id(rs.getLong("SCREEN_ID"));
		dto.setScreen_label(rs.getString("SCREEN_LABEL"));
		dto.setScreen_name(rs.getString("SCREEN_NAME"));
		dto.setScreen_description(rs.getString("SCREEN_DESCRIPTION")); 
        return dto;
        
        

	}

}
