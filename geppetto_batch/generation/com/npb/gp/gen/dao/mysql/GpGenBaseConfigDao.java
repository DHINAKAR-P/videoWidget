package com.npb.gp.gen.dao.mysql;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.gen.dao.mysql.support.baseconfig.GpBaseConfigMapper;
import com.npb.gp.gen.interfaces.dao.IGpGenBaseConfigDao;

@Repository("GpGenBaseConfigDao")
public class GpGenBaseConfigDao implements IGpGenBaseConfigDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	
    }

	
	@Override
	public HashMap<String, GpArchitypeConfigurations> load_configs(String type)
																throws Exception {
		
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("type", type);
		
		
		sql ="select gpconfigs.name as CONFIGS_NAME, gpconfigs.value as CONFIGS_VALUE "
			+ " from geppetto.gpconfigs "
			+ " where type = :type "; 
				

		GpBaseConfigMapper configs_mapper = new GpBaseConfigMapper();
		List<GpArchitypeConfigurations> config_list = this.namedParameterJdbcTemplate.query(sql, parameters, configs_mapper);
		if(config_list.size() < 1){
		throw new Exception("type " + type + " was not found");
		}
		HashMap<String, GpArchitypeConfigurations> the_map 
							= new HashMap<String, GpArchitypeConfigurations>();
		
		for(GpArchitypeConfigurations a_config : config_list){
			the_map.put(a_config.getName(), a_config);
		}
		return the_map;
	}

	
}
