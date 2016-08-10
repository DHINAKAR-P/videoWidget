package com.npb.gp.gen.dao.mysql.support.module;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpModuleProperties;

/**
 * 
 * @author Reinaldo</br>
 * Date Created: 08/09/2015</br>
 *
 *standard mapper used in get data from the module table</p>
 *
 *
 */

public class GpModuleMapper implements RowMapper<GpModule>{

	public GpModule mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpModule module = new GpModule();
		
		module.setId(rs.getLong("MODULE_ID"));
		module.setName(rs.getString("MODULE_NAME"));
		module.setLabel(rs.getString("MODULE_LABEL"));
		module.setDescription(rs.getString("MODULE_DESCRIPTION"));
		module.setNotes(rs.getString("MODULE_NOTES"));
		module.setCreatedate(rs.getDate("CREATED_DATE"));
		module.setCreatedby(rs.getLong("CREATED_BY"));
		module.setLastmodifieddate(rs.getDate("LAST_MODIFIED_BY"));
		module.setLastmodifiedby(rs.getLong("LAST_MODIFIED_BY"));
		module.setBase_location(rs.getString("BASE_LOCATION"));
		
		return module;
		
	}
}