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

import com.npb.gp.dao.mysql.support.techproperties.TechPropertiesMapper;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.interfaces.dao.IGpTechPropertiesDao;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 06/18/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to interact with the db for the basic search</br>
 *and CRUD operations for a  tech property - </p>
 *
 *
 */
@Repository("GpTechPropertyDao")
public class GpTechPropertyDao implements IGpTechPropertiesDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	@Value("${get_all_properties.sql}")
	private String get_all_properties_sql;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }

	@Override
	public void insert(GpTechProperties property, long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GpTechProperties property, long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long property_id, long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public GpTechProperties find_by_id(long property_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GpTechProperties> find_by_property_type(long property_type)
														throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<GpTechProperties> get_all_properties()throws Exception{
		
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		//parameters.addValue("project_id", project_id);
		
		/*
		sql ="select tech_properties.id as PROPERTY_ID, tech_properties.name as PROPERTY_NAME, "
			+ " tech_properties.label as PROPERTY_LABEL,  tech_properties.description as PROPERTY_DESCRIPTION, "
			+ " tech_properties.release_status as PROPERTY_RELEASE_STATUS, "
			+ " tech_properties.license_status as PROPERTY_LICENSE_STATUS, "
			+ " tech_properties.type as PROPERTY_TYPE,  tech_properties.notes as PROPERTY_NOTES, "
			+ " tech_properties.version as PROPERTY_VERSION"
			+ " from geppetto.tech_properties";
			
			*/
 
		TechPropertiesMapper property_mapper = new TechPropertiesMapper();
		
		
		List<GpTechProperties> property_list = this.namedParameterJdbcTemplate.query(this.get_all_properties_sql, parameters, property_mapper);
		if(property_list.size() < 1){
		throw new Exception("no tech properties found");
		}
		//System.out.println("######### - In GpTechPropertyDao -  get_all_properties is: " + property_list.size() + " #######################");
		
		
		//return config_list.get(0);
		return (ArrayList<GpTechProperties>) property_list;

	}

}
