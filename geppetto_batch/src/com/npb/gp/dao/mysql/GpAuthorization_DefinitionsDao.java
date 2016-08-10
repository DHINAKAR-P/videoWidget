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

import com.npb.gp.dao.mysql.support.authorization_definitions.AuthorizationDefMapper;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.interfaces.dao.IGpAuthorization_DefinitionsDao;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 11/28/2014</br>
 * @since .75</p>  
 *
 *The purpose of this is to provide standard CRUD/search of the</br>
 * authorization_definitions table</p>
 *
 *
 */
@Repository("GpAuthorization_DefinitionsDao")
public class GpAuthorization_DefinitionsDao implements
		IGpAuthorization_DefinitionsDao {
	
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	@Value("${get_all_authorizations.sql}")
	private String get_all_authorizations_sql;

	@Value("${find_by_authorzation_id.sql}")
	private String find_by_authorzation_id_sql;

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }


	@Override
	public GpAuthorization find_by_id(long auth_id) throws Exception {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", auth_id);

		AuthorizationDefMapper auth_mapper = new AuthorizationDefMapper();
		
		
		List<GpAuthorization> auth_list = this.namedParameterJdbcTemplate
				.query(this.find_by_authorzation_id_sql, parameters, auth_mapper);
		if(auth_list.size() < 1){
		throw new Exception("no authorization found");
		}
		//System.out.println("######### - In GpAuthorization_DefinitionsDao"
		//		+ " -  find_by_id is: " + auth_list.size() + " #######################");
		
		
		return auth_list.get(0);
	}

	@Override
	public ArrayList<GpAuthorization> get_all_authorizations() throws Exception {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();

		AuthorizationDefMapper auth_mapper = new AuthorizationDefMapper();
		
		
		List<GpAuthorization> auth_list = this.namedParameterJdbcTemplate
				.query(this.get_all_authorizations_sql, parameters, auth_mapper);
		if(auth_list.size() < 1){
		throw new Exception("no authorizations found");
		}
		//System.out.println("######### - In GpAuthorization_DefinitionsDao -"
		//		+ "  get_all_authorzations is: " + auth_list.size() + " #######################");
		
		
		return (ArrayList<GpAuthorization>) auth_list;

	}

}
