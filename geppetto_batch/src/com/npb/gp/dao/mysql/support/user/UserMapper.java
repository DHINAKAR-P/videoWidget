package com.npb.gp.dao.mysql.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpUser;
/**
 * 
 * @author Reinaldo</br>
 * Date Created: 16/09/2015</br> 
 *
 *standard mapper used in get data from the user table</p>
 *
 *
 */
public class UserMapper implements RowMapper<GpUser> {

	@Override
	public GpUser mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpUser user = new GpUser();
		user.setId(rs.getLong("USER_ID"));
		user.setUsername(rs.getString("USER_NAME"));
		user.setLanguagepreference(rs.getString("LANGUAGE_PREFERENCE"));
		user.setStartdate(rs.getTimestamp("START_DATE"));
		user.setLicenseid(rs.getString("LICENSE_ID"));
		user.setLastaccess(rs.getTimestamp("LAST_ACCESS"));
		user.setMustresetpassword(rs.getString("MUST_RESET_PASSWORD"));
		user.setAccestype(rs.getString("ACCESS_TYPE"));
		user.setNewuser(rs.getBoolean("NEW_USER"));
		user.setScreenname(rs.getString("SCREEN_NAME"));
		user.setDocker_json(rs.getString("DOCKER_JSON"));
		return user;
	}

}
