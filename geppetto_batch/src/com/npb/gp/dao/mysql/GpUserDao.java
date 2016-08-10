package com.npb.gp.dao.mysql;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.user.UserMapper;
import com.npb.gp.domain.core.GpUser;
import com.npb.gp.interfaces.dao.IGpUserDao;

/**
 * 
 * @author Reinaldo</br>
 * Date Created: 16/09/2015</br> 
 *
 *The purpose of this is to provide standard CRUD/search of the</br>
 * user table</p>
 *
 */
@Repository("GpUserDao")
public class GpUserDao implements IGpUserDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
		
	@Value("${find_user_by_id.sql}")
	private String find_user_by_id_sql;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;    	    
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }

	@Override
	public GpUser find_user_by_id(long user_id) throws Exception {
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("user_id", user_id);

		UserMapper user_mapper = new UserMapper();
		
		
		List<GpUser> user_list = this.namedParameterJdbcTemplate.query(this.find_user_by_id_sql, parameters, user_mapper);
		if (user_list.size() < 1) {
			throw new Exception("no user found");
		}
		
		
		return user_list.get(0);

	}
}
