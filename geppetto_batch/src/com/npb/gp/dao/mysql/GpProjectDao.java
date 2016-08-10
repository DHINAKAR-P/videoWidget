package com.npb.gp.dao.mysql;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import com.npb.gp.dao.mysql.support.project.GpProject_Mapper;
import com.npb.gp.dao.mysql.support.project.InsertProject;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.interfaces.dao.IGpProjectDao;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/08/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to interact with the db for the basic search</br>
 *and CRUD operations for a project</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun"</p>
 *
 *
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex
 *
 */
@Repository("GpProjectDao")
public class GpProjectDao implements IGpProjectDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	private InsertProject insert_project;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	InsertProject.SQL_INSERT_PROJECT = " insert into projects "
    			+ " (name, label, description, default_module_id,  "
    			+ "  default_module_label, notes, "
    			+ " created_date, created_by,  last_modified_date, last_modified_by) values "
    			
    			+ " (:name, :label, :description, :default_module_id,  " 
    			+ "  :default_module_label, :notes, "
    			+ " :created_date, :created_by, :last_modified_date, :last_modified_by)"; 
    	
    	
    	
    	this.insert_project = new InsertProject(dataSource);
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

	
	@Override
	public void insert(GpProject aproject) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("name", aproject.getName());
        paramMap.put("label", aproject.getLabel());
        paramMap.put("description", aproject.getDescription());
        paramMap.put("default_module_id", aproject.getDefault_module().getId());
        paramMap.put("default_module_label", aproject.getDefault_module().getLabel());

        paramMap.put("notes", aproject.getNotes());
        paramMap.put("created_date", new Date());
        paramMap.put("created_by", aproject.getCreatedby());
        paramMap.put("last_modified_date", new Date());
        paramMap.put("last_modified_by", aproject.getLastmodifiedby());

		KeyHolder keyHolder = new GeneratedKeyHolder();
        this.insert_project.updateByNamedParam(paramMap,keyHolder);
        aproject.setId(keyHolder.getKey().longValue());


	}

	@Override
	public void update(GpProject aproject) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GpProject aproject) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GpProject find_by_id(long project_id) throws Exception {
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		//Why is this here? GpProjectDao_SQL.properties?
		sql = "select projects.id as PROJECT_ID, projects.name as PROJECT_NAME, "
			+ " projects.label as PROJECT_LABEL, projects.description as PROJECT_DESCRIPTION, "
			+ " projects.default_module_id as PROJECT_DEFAULT_MODULE_ID, "
			+ " projects.default_module_label as PROJECT_DEFAULT_MODULE_LABEL, projects.notes as PROJECT_NOTES, "
			+ " projects.client_os_types as PROJECT_CLIENT_OS_TYPES, projects.client_device_types as PROJECT_CLIENT_DEVICE_TYPES, "
			+ " projects.client_dev_languages as PROJECT_CLIENT_DEV_LANGUAGES, projects.client_dev_frameworks as PROJECT_CLIENT_DEV_FRAMEWORKS, "
			+ " projects.client_widget_frameworks as PROJECT_CLIENT_WIDGET_FRAMEWORKS, projects.mobile_css_framework as MOBILE_CSS_FRAMEWORK, projects.desktop_css_framework as DESKTOP_CSS_FRAMEWORK,"
			+ " projects.app_ui_template as PROJECT_APP_UI_TEMPLATE, projects.client_code_pattern as PROJECT_CLIENT_CODE_PATTERN, "
			+ " projects.server_code_pattern as PROJECT_SERVER_CODE_PATTERN, projects.server_dev_lang as PROJECT_SERVER_DEV_LANG, "
			+ " projects.server_dev_framework as PROJECT_SERVER_DEV_FRAMEWORK, projects.server_run_time as PROJECT_SERVER_RUN_TIME, "
			+ " projects.server_os as PROJECT_SERVER_OS, projects.server_dbms as PROJECT_SERVER_DBMS, "
			+ " projects.server_deployment_environment as PROJECT_SERVER_DEPLOYMENT_ENVIRONMENT, "
			+ " projects.global_extensions as PROJECT_GLOBAL_EXTENSIONS, "
			
			+ " projects.ui_navigation_styles as PROJECT_UI_NAVIGATION_STYLES, "
			+ " projects.supported_browsers as PROJECT_SUPPORTED_BROWSERS, "
			+ " projects.default_human_language as PROJECT_DEFAULT_HUMAN_LANGUAGE, "
			+ " projects.other_human_languages as PROJECT_OTHER_HUMAN_LANGUAGES, "
			+ " projects.entity as PROJECT_ENTITY, "
			+ " projects.enforce_documentation as PROJECT_ENFORCE_DOCUMENTATION, "
			+ " projects.widget_count as PROJECT_WIDGET_COUNT, "
			+ "projects.generation_type as PROJECT_GENERATION_TYPE, "
			+ " projects.authorization_type as PROJECT_AUTHORIZATION_TYPE, "
			+ " projects.authorizations as PROJECT_AUTHORIZATIONS, "

			
			
			+ " projects.created_by as PROJECT_CREATED_BY, projects.created_date as PROJECT_CREATED_DATE,  "
			+ " projects.last_modified_by as PROJECT_LAST_MODIFIED_BY, projects.last_modified_date as PROJECT_LAST_MODIFIED_DATE "
			+ " from geppetto.projects "
			+ " where geppetto.projects.id = :project_id";

		
		GpProject_Mapper project_mapper = new GpProject_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(sql, parameters, project_mapper);
		if(dto_list.size() < 1){
			throw new Exception("project_id number " + project_id + " was not found");
		}
		//System.out.println("######### - In GpProjectDao -  find_by_id dto_list.size() is: " + dto_list.size() + " #######################");
		
		
		
		return (GpProject) dto_list.get(0);
		
	}

	@Override
	public ArrayList<GpProject> find_by_user_id(long user_id)
														throws Exception {
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("created_by", user_id);

		
		sql = "select projects.id as PROJECT_ID, projects.name as PROJECT_NAME, "
				+ " projects.label as PROJECT_LABEL, projects.description as PROJECT_DESCRIPTION, "
				+ " projects.default_module_id as PROJECT_DEFAULT_MODULE_ID, "
				+ " projects.default_module_label as PROJECT_DEFAULT_MODULE_LABEL, projects.notes as PROJECT_NOTES, "
				+ " projects.client_os_types as PROJECT_CLIENT_OS_TYPES, projects.client_device_types as PROJECT_CLIENT_DEVICE_TYPES, "
				+ " projects.client_dev_languages as PROJECT_CLIENT_DEV_LANGUAGES, projects.client_dev_frameworks as PROJECT_CLIENT_DEV_FRAMEWORKS, "
				+ " projects.client_widget_frameworks as PROJECT_CLIENT_WIDGET_FRAMEWORKS, projects.mobile_css_framework as MOBILE_CSS_FRAMEWORK, projects.desktop_css_framework as DESKTOP_CSS_FRAMEWORK,"
				+ " projects.app_ui_template as PROJECT_APP_UI_TEMPLATE, projects.client_code_pattern as PROJECT_CLIENT_CODE_PATTERN, "
				+ " projects.server_code_pattern as PROJECT_SERVER_CODE_PATTERN, projects.server_dev_lang as PROJECT_SERVER_DEV_LANG, "
				+ " projects.server_dev_framework as PROJECT_SERVER_DEV_FRAMEWORK, projects.server_run_time as PROJECT_SERVER_RUN_TIME, "
				+ " projects.server_os as PROJECT_SERVER_OS, projects.server_dbms as PROJECT_SERVER_DBMS, "
				+ " projects.server_deployment_environment as PROJECT_SERVER_DEPLOYMENT_ENVIRONMENT, "
				+ " projects.global_extensions as PROJECT_GLOBAL_EXTENSIONS, "
				
				+ " projects.ui_navigation_styles as PROJECT_UI_NAVIGATION_STYLES, "
				+ " projects.supported_browsers as PROJECT_SUPPORTED_BROWSERS, "
				+ " projects.default_human_language as PROJECT_DEFAULT_HUMAN_LANGUAGE, "
				+ " projects.other_human_languages as PROJECT_OTHER_HUMAN_LANGUAGES, "
				+ " projects.entity as PROJECT_ENTITY, "
				+ " projects.enforce_documentation as PROJECT_ENFORCE_DOCUMENTATION, "
				+ " projects.widget_count as PROJECT_WIDGET_COUNT, "
				+ " projects.generation_type as PROJECT_GENERATION_TYPE, "
				+ " projects.authorization_type as PROJECT_AUTHORIZATION_TYPE, "
				+ " projects.authorizations as PROJECT_AUTHORIZATIONS, "

				
				
				+ " projects.created_by as PROJECT_CREATED_BY, projects.created_date as PROJECT_CREATED_DATE,  "
				+ " projects.last_modified_by as PROJECT_LAST_MODIFIED_BY, projects.last_modified_date as PROJECT_LAST_MODIFIED_DATE "
				+ " from geppetto.projects "
				+ " where geppetto.projects.created_by = :created_by";

		
		GpProject_Mapper project_mapper = new GpProject_Mapper();
		List<GpProject> dto_list = this.namedParameterJdbcTemplate.query(sql, parameters, project_mapper);
		if(dto_list.size() < 1){
			throw new Exception("user_id number " + user_id + " was not found");
		}
		System.out.println("######### - In GpProjectDao -  find_by_user_id dto_list.size() is: " + dto_list.size() + " #######################");
		
		return (ArrayList<GpProject>) dto_list;
	}

}
