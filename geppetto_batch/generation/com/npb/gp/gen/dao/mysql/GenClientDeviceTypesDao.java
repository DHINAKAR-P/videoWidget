package com.npb.gp.gen.dao.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.gen.dao.mysql.support.baseconfig.GpBaseConfigMapper;
import com.npb.gp.gen.dao.mysql.support.clientdevicetype.GpBaseClientDeciceTypeMapper;
import com.npb.gp.gen.interfaces.dao.IGpGenClientDeviceTypesDao;

@Repository("GenClientDeviceTypesDao")
public class GenClientDeviceTypesDao implements IGpGenClientDeviceTypesDao{
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	
    }

	@Override
	public Map<Long, GpClientDeviceTypes> load_client_devices_types() {
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		sql ="SELECT "+ GpClientDeviceTypes.COLUMN_id +", "+ GpClientDeviceTypes.COLUMN_client_device_type +", "
				+ GpClientDeviceTypes.COLUMN_client_device_type_name +", "+ GpClientDeviceTypes.COLUMN_client_device_type_label +", "
				+ GpClientDeviceTypes.COLUMN_client_device_type_description +", "+ GpClientDeviceTypes.COLUMN_client_device_screen_size 
				+ ", "+ GpClientDeviceTypes.COLUMN_client_device_resolution +", "+ GpClientDeviceTypes.COLUMN_client_device_type_os_name +", "
				+ GpClientDeviceTypes.COLUMN_client_device_type_os_version + " FROM geppetto.client_device_types"; 
				

		GpBaseClientDeciceTypeMapper mapper = new GpBaseClientDeciceTypeMapper();
		List<GpClientDeviceTypes> list = this.namedParameterJdbcTemplate.query(sql, parameters, mapper);
		Map<Long, GpClientDeviceTypes> map = new HashMap<Long, GpClientDeviceTypes>();
		for (GpClientDeviceTypes row : list){
			map.put(row.getId(), row);
		}
		return map;
	}
}
