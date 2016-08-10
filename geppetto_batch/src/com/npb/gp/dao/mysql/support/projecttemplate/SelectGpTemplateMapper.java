package com.npb.gp.dao.mysql.support.projecttemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.domain.core.TemplateBaseInfo;

/**
 * 
 * @author Dheeraj Singh</br> Date Created: 12/29/2015</br>
 * @since 1.0</p>
 *
 *        The purpose of this class is to support the GpTemplateDao by providing
 *        mapping</p>
 * 
 */
public class SelectGpTemplateMapper implements RowMapper<TemplateBaseInfo> {

	public TemplateBaseInfo mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		TemplateBaseInfo wrapper = new TemplateBaseInfo();

		wrapper.setTemplate_id(rs.getLong("TEMPLATE_ID"));
		wrapper.setName(rs.getString("NAME"));
		wrapper.setDescription(rs.getString("DESCRIPTION"));
		wrapper.setLabel(rs.getString("LABEL"));
		wrapper.setColor(rs.getString("COLOR"));
		wrapper.setDevice(rs.getString("DEVICE"));

		return wrapper;
	}
}
