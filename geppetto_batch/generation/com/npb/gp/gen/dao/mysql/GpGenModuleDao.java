package com.npb.gp.gen.dao.mysql;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpModule;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.gen.dao.mysql.support.baseconfig.GpBaseConfigMapper;
import com.npb.gp.gen.dao.mysql.support.module.GpModuleMapper;
import com.npb.gp.gen.interfaces.dao.IGpGenModuleDao;

/**
 * 
 * @author Reinaldo</br>
 * Date Created: 18/09/2015</br>
 * 
 *
 *The purpose of this is to provide standard CRUD/search of the</br>
 * module table</p>
 *
 *
 */
@Repository("GpGenModuleDao")
public class GpGenModuleDao implements IGpGenModuleDao{

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get_module_by_id.sql}")
	private String get_module_by_id_sql;

	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	
    }
	
	
	@Override
	public GpModule load_module(long id) throws Exception {
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("module_id", id);			
				

		GpModuleMapper module_mapper = new GpModuleMapper();
		List<GpModule> m_list = this.namedParameterJdbcTemplate
				.query(get_module_by_id_sql, parameters, module_mapper);
		if (m_list.size() < 1) {
			throw new Exception("module_id " + id + " was not found");
		}
		
		
		return m_list.get(0);

	}

}