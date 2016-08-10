package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.base_verbs.GpBaseVerbMapper;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.dao.IGpBaseVerbsDao;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/15/2015</br>
 * @since .75</p>  
 *
 *The purpose of this is to provide standard CRUD/search of the base_verbs table</p>
 *
 *
 */
@Repository("GpBaseVerbsDao")
public class GpBaseVerbsDao implements IGpBaseVerbsDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;

	@Value("${get_all_base_verbs.sql}")
	private String get_all_base_verbs_sql;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }

	
	@Override
	public ArrayList<GpVerb> get_all_base_verbs() throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		GpBaseVerbMapper verb_mapper = new GpBaseVerbMapper(); 
		
		
		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(this.get_all_base_verbs_sql, parameters, verb_mapper);
		if(verb_list.size() < 1){
		throw new Exception("no verbs found");
		}
		//System.out.println("######### - In GpBaseVerbsDao -  get_all_base_verbs is: " + verb_list.size() + " #######################");
		
		return (ArrayList<GpVerb>) verb_list;

	}

}
