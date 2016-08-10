package com.npb.gp.dao.mysql.support.projecttemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplate;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/30/2015</br>
 * @since 1.0</p>
 *
 *        The purpose of this class is to support the GpProjectTemplateDao by
 *        providing mapping</p>
 * 
 */
public class SelectGpProjectTemplateMapper implements
		RowMapper<GpProjectTemplate> {

	public GpProjectTemplate mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpProjectTemplate wrapper = new GpProjectTemplate();

		wrapper.setProjectTemplateId(rs.getLong("PROJECT_TEMPLATE_ID"));
		wrapper.setProjectId(rs.getLong("PROJECT_ID"));
		wrapper.setTemplateId(rs.getLong("TEMPLATE_ID"));
		wrapper.setColor(rs.getString("COLOR"));
		wrapper.setName(rs.getString("NAME"));
		wrapper.setLabel(rs.getString("LABEL"));
		wrapper.setDescription(rs.getString("DESCRIPTION"));
		wrapper.setDevice(rs.getString("DEVICE"));

		return wrapper;
	}
}
