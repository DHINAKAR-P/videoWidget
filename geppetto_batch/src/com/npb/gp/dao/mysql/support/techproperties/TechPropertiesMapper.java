package com.npb.gp.dao.mysql.support.techproperties;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpTechProperties;

public class TechPropertiesMapper implements RowMapper<GpTechProperties> {
	
	public GpTechProperties mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			
		GpTechProperties tech_prop = new GpTechProperties();
		tech_prop.setId(rs.getLong("PROPERTY_ID"));
		tech_prop.setName(rs.getString("PROPERTY_NAME"));
		tech_prop.setLabel(rs.getString("PROPERTY_LABEL"));
		tech_prop.setDescription(rs.getString("PROPERTY_DESCRIPTION"));
		tech_prop.setVersion(rs.getString("PROPERTY_VERSION"));
		tech_prop.setRelease_status(rs.getString("PROPERTY_RELEASE_STATUS"));
		tech_prop.setLicense_status(rs.getString("PROPERTY_LICENSE_STATUS"));
		tech_prop.setType(rs.getString("PROPERTY_TYPE"));
		tech_prop.setNotes(rs.getString("PROPERTY_NOTES"));
    	
		
		return tech_prop;
	}

}
