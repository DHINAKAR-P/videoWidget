package com.npb.gp.gen.dao.mysql.support.clientdevicetype;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;

public class GpBaseClientDeciceTypeMapper implements RowMapper<GpClientDeviceTypes>{

	@Override
	public GpClientDeviceTypes mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		GpClientDeviceTypes clientDeviceTypes = new GpClientDeviceTypes();
		clientDeviceTypes.setId(rs.getLong(GpClientDeviceTypes.COLUMN_id));
		clientDeviceTypes.setClient_device_resolution(rs.getString(GpClientDeviceTypes.COLUMN_client_device_resolution));
		clientDeviceTypes.setClient_device_screen_size(rs.getString(GpClientDeviceTypes.COLUMN_client_device_screen_size));
		clientDeviceTypes.setClient_device_type(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type));
		clientDeviceTypes.setClient_device_type_description(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type_description));
		clientDeviceTypes.setClient_device_type_label(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type_label));
		clientDeviceTypes.setClient_device_type_name(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type_name));
		clientDeviceTypes.setClient_device_type_os_name(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type_os_name));
		clientDeviceTypes.setClient_device_type_os_version(rs.getString(GpClientDeviceTypes.COLUMN_client_device_type_os_version));
		return clientDeviceTypes;
	}

}
