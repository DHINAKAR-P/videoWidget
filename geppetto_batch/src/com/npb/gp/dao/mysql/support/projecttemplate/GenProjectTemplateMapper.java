package com.npb.gp.dao.mysql.support.projecttemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplateComponent;

public class GenProjectTemplateMapper implements
		RowMapper<GpProjectTemplateComponent> {

	public GpProjectTemplateComponent mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpProjectTemplateComponent template = new GpProjectTemplateComponent();

		template.setProjectId(rs.getLong("PROJECT_ID"));
		template.setProjectTemplateId(rs.getLong("PROJECT_TEMPLATE_ID"));
		template.setLabel(rs.getString("LABEL"));
		template.setTemplateComponentValue(rs
				.getString("TEMPLATE_COMPONENT_VALUE"));

		return template;
	}
}
