package com.npb.gp.dao.mysql.support.tempdependencies;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpTemplateDependencies;



public class GpTemplateDependenciesMapper implements RowMapper<GpTemplateDependencies> {
	@Override
	public  GpTemplateDependencies mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpTemplateDependencies templateDependencies = new GpTemplateDependencies();
			
		templateDependencies.setId(rs.getLong("id"));
		templateDependencies.setDependencies(rs.getString("dependencies"));
		templateDependencies.setTechnology_type(rs.getString("technology_type"));
		templateDependencies.setTemplate_name(rs.getString("template_name"));
		templateDependencies.setComponent_type(rs.getString("component_type"));
				
		return templateDependencies;
		
		
	}

}
