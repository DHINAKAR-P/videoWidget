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

import com.npb.gp.dao.mysql.support.menudetail.MenuDetailMapper;
import com.npb.gp.domain.core.GpMenuDetail;
@Repository("GpMenuDetailDao")
public class GpMenuDetailDao {
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${find_menu_by_project_id.sql}")
	private String find_menu_by_project_id;
	
	@Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }
	
	public List<GpMenuDetail> find_by_project_id(long project_id)throws Exception{
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);						 

		MenuDetailMapper menu_mapper = new MenuDetailMapper();
		List<GpMenuDetail> menu_list = this.namedParameterJdbcTemplate.query(find_menu_by_project_id, parameters, menu_mapper);
		return menu_list;
	}
}
