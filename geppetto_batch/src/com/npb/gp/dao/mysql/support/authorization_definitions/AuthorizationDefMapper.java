package com.npb.gp.dao.mysql.support.authorization_definitions;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpAuthorization;


public class AuthorizationDefMapper implements RowMapper<GpAuthorization> {
	@Override
	public GpAuthorization mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpAuthorization the_auth = new GpAuthorization();
		the_auth.setId(rs.getLong("AUTH_ID"));
		the_auth.setName(rs.getString("AUTH_NAME"));
		the_auth.setLabel(rs.getString("AUTH_LABEL"));
		the_auth.setDescription(rs.getString("AUTH_DESCRIPTION"));
		the_auth.setNotes(rs.getString("AUTH_NOTES"));
		
		return the_auth;

		
	}


}
