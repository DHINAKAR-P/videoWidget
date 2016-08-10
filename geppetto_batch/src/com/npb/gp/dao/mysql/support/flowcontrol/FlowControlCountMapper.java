package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class FlowControlCountMapper implements RowMapper<Long> {
	@Override
	public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		long count = rs.getLong("COUNT");
		
		Long the_count = new Long(count);

		return the_count;
	}

}
