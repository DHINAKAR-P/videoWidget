package com.npb.gp.dao.mysql.support.menudetail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMenuDetail;

public class MenuDetailMapper implements RowMapper<GpMenuDetail>{

	@Override
	public GpMenuDetail mapRow(ResultSet rs, int rownum) throws SQLException {
		
		GpMenuDetail gpMenuDetail = new GpMenuDetail();
		gpMenuDetail.setId(rs.getLong("MENU_ID"));
		gpMenuDetail.setActivity_id(rs.getLong("activity_id"));
		gpMenuDetail.setAuth_id(rs.getLong("auth_id"));
		gpMenuDetail.setDescription(rs.getString("description"));
		gpMenuDetail.setLabel(rs.getString("label"));
		gpMenuDetail.setMenu_tree(rs.getString("menu_tree"));
		gpMenuDetail.setName(rs.getString("name"));
		gpMenuDetail.setProject_id(rs.getLong("project_id"));
		return gpMenuDetail;
	}

}
