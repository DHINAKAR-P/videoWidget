package com.npb.gp.dao.mysql.support.noun;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpNoun;


public class NounBaseMapper implements RowMapper<GpNoun> {

	public GpNoun mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpNoun noun = new GpNoun();

		noun.setId(rs.getLong("NOUN_ID"));
		noun.setName(rs.getString("NOUN_NAME"));
		noun.setLabel(rs.getString("NOUN_LABEL"));
		noun.setDescription(rs.getString("NOUN_DESCRIPTION"));
		noun.setProjectid(rs.getLong("NOUN_PROJECT_ID"));
		noun.setModuleid(rs.getLong("NOUN_MODULE_ID"));
		noun.setCacheenabled(rs.getString("NOUN_CACHE_ENABLED"));
		noun.setNotes(rs.getString("NOUN_NOTES"));
		
		noun.setCreatedby(rs.getLong("NOUN_CREATED_BY"));
		noun.setCreatedate(rs.getTimestamp("NOUN_CREATED_DATE"));
		noun.setLastmodifiedby(rs.getLong("NOUN_LAST_MODIFIED_BY"));
		noun.setLastmodifieddate(rs.getTimestamp("NOUN_LAST_MODIFIED_DATE"));
		
		
		return noun;
	}
}
