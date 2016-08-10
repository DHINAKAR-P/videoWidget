package com.npb.gp.dao.mysql.support.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpProject;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/08/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to support the GpProjectDao by provding mapping</p>
 * 
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex
 *
 *
 */
public class GpProject_Mapper implements RowMapper<GpProject> {
	
	public GpProject mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpProject the_project  = new GpProject();
		
		the_project.setId(rs.getLong("PROJECT_ID"));
		the_project.setName(rs.getString("PROJECT_NAME"));
		the_project.setLabel(rs.getString("PROJECT_LABEL"));
		the_project.setDescription(rs.getString("PROJECT_DESCRIPTION"));
		the_project.setDefault_module_label(rs.getString("PROJECT_DEFAULT_MODULE_LABEL"));
		the_project.setDefault_module_id(rs.getLong("PROJECT_DEFAULT_MODULE_ID"));
		the_project.setNotes(rs.getString("PROJECT_NOTES"));
		
		
		the_project.setClient_os_types(this.handle_value_field(
										rs.getString("PROJECT_CLIENT_OS_TYPES")));
		
		the_project.setClient_device_types(this.handle_value_field(
									rs.getString("PROJECT_CLIENT_DEVICE_TYPES")));
		
		the_project.setClient_dev_languages(this.handle_value_field(
									rs.getString("PROJECT_CLIENT_DEV_LANGUAGES")));
		
		the_project.setClient_dev_frameworks(this.handle_value_field(
				rs.getString("PROJECT_CLIENT_DEV_FRAMEWORKS")));
		
		the_project.setClient_widget_frameworks(this.handle_value_field(
								rs.getString("PROJECT_CLIENT_WIDGET_FRAMEWORKS")));
		
		the_project.setMobile_css_framework(rs.getLong("MOBILE_CSS_FRAMEWORK"));
		the_project.setDesktop_css_framework(rs.getLong("DESKTOP_CSS_FRAMEWORK"));
		//TODO: Not in DB
		//the_project.setClient_css_frameworks(this.handle_value_field(
			//						rs.getString("PROJECT_CLIENT_CSS_FRAMEWORKS")));
		
		
		the_project.setApp_ui_template(this.handle_value_field(
									rs.getString("PROJECT_APP_UI_TEMPLATE")));
		
		the_project.setClient_code_pattern(rs.getLong("PROJECT_CLIENT_CODE_PATTERN"));
		
		the_project.setServer_code_pattern(rs.getLong("PROJECT_SERVER_CODE_PATTERN"));
		
		the_project.setServer_dev_lang(rs.getLong("PROJECT_SERVER_DEV_LANG"));
		
		the_project.setServer_dev_framework(rs.getLong("PROJECT_SERVER_DEV_FRAMEWORK"));
		
		the_project.setServer_run_time(rs.getLong("PROJECT_SERVER_RUN_TIME"));
		
		the_project.setServer_os(rs.getLong("PROJECT_SERVER_OS"));
		
		the_project.setServer_dbms(rs.getLong("PROJECT_SERVER_DBMS"));
		
		the_project.setServer_deployment_environment(
						rs.getLong("PROJECT_SERVER_DEPLOYMENT_ENVIRONMENT"));
		
		
		the_project.setGlobal_extensions(this.handle_value_field(
									rs.getString("PROJECT_GLOBAL_EXTENSIONS")));

		
		the_project.setUi_navigation_styles(this.handle_value_field(
									rs.getString("PROJECT_UI_NAVIGATION_STYLES")));
		
		the_project.setSupported_browsers(this.handle_value_field(
										rs.getString("PROJECT_SUPPORTED_BROWSERS")));
		
		
		the_project.setDefault_human_language(rs.getLong(
											"PROJECT_DEFAULT_HUMAN_LANGUAGE"));
		
		
		the_project.setOther_human_languages(this.handle_value_field(
								rs.getString("PROJECT_OTHER_HUMAN_LANGUAGES")));
		
		
		the_project.setEntity(rs.getString("PROJECT_ENTITY"));
		
		the_project.setEnforce_documentation(new Boolean(
				rs.getString("PROJECT_ENFORCE_DOCUMENTATION")).booleanValue());
		
		the_project.setWidget_count(rs.getLong("PROJECT_WIDGET_COUNT"));
		
		the_project.setGeneration_type(rs.getString("PROJECT_GENERATION_TYPE"));
		
		the_project.setAuthorization_type(rs.getString("PROJECT_AUTHORIZATION_TYPE"));
		the_project.setAuthorizations(rs.getString("PROJECT_AUTHORIZATIONS"));

		
		
		the_project.setCreatedate(rs.getTimestamp("PROJECT_CREATED_DATE"));
		the_project.setCreatedby(rs.getLong("PROJECT_CREATED_BY"));
		the_project.setLastmodifieddate(rs.getTimestamp("PROJECT_LAST_MODIFIED_DATE"));
		the_project.setLastmodifiedby(rs.getLong("PROJECT_LAST_MODIFIED_BY"));
		
		
		
		return the_project;
	}
	
	
	private ArrayList<String> handle_value_field(String the_string){
		if(the_string == null){
			return new ArrayList<String>();
		}
		
		String[] temp = GpGenericRecordParserBuilder
				.parseDelimitedString(the_string);
		ArrayList<String> values = new ArrayList<String>(); 
		for(String str  : temp){
			if(!str.isEmpty())
				values.add(str);
		}
		
		return values;
	}

}
