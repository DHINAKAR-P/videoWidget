package com.npb.gp.dao.mysql.support.activity_gen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpNoun;

public class NounMapper implements RowMapper<GpNoun> {

	@Override
	public GpNoun mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpNoun noun = new GpNoun();
    	noun.setId(rs.getLong("id"));
    	noun.setName(rs.getString("name"));
    	noun.setLabel(rs.getString("label"));
    	noun.setDescription(rs.getString("description"));
        return noun;
	}

}
