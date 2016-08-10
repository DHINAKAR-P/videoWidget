package com.npb.gp.gen.dao.mysql.support.baseconfig;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpArchitypeConfigurations;



public class GpBaseConfigMapper implements RowMapper<GpArchitypeConfigurations> {

	public  GpArchitypeConfigurations mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpArchitypeConfigurations a_config = new GpArchitypeConfigurations();
		
		a_config.setName(rs.getString("CONFIGS_NAME"));
		a_config.setValue(rs.getString("CONFIGS_VALUE"));
		
		
		return a_config;
		
	}
}
