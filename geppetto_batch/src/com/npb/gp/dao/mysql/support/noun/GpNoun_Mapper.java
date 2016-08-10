package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.npb.gp.domain.core.GpNoun;



public class GpNoun_Mapper implements RowMapper<GpNoun> {
	@Override
	public  GpNoun mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpNoun noun = new GpNoun();
		
		noun.setId(rs.getLong("NOUN_ID"));
		noun.setName(rs.getString("NOUN_NAME"));
		noun.setLabel(rs.getString("NOUN_LABEL"));
		noun.setDescription(rs.getString("NOUN_DESCRIPTION"));
		noun.setDefault_activity_id(rs.getLong("NOUN_DEFAULT_ACTIVITY_ID"));
		return noun;
		
		
	}

}
