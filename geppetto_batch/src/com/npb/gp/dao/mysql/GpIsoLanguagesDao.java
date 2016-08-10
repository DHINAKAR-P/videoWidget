package com.npb.gp.dao.mysql;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.isolanguages.IsoLanguagesMapper;
import com.npb.gp.domain.core.GpIsoLanguage;

@Repository("GpIsoLanguagesDao")
public class GpIsoLanguagesDao {
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${find_language_by_id.sql}")
	private String find_language_by_id;
	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }
	
	public GpIsoLanguage find_by_id(long language_id) throws Exception{
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", language_id);
		IsoLanguagesMapper isoLanguagesMapper = new IsoLanguagesMapper();
		GpIsoLanguage gpIsoLanguage = this.namedParameterJdbcTemplate.queryForObject(find_language_by_id, parameters, isoLanguagesMapper);
		return gpIsoLanguage;
	}
}
