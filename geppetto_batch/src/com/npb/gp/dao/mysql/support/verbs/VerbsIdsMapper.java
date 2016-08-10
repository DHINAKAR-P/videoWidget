package com.npb.gp.dao.mysql.support.verbs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpVerb;

public class VerbsIdsMapper implements RowMapper<Long>{

	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getLong("VERB_ID");
	}

}
