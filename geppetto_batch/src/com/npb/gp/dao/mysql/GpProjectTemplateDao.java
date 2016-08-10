package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.projecttemplate.GenProjectTemplateMapper;
import com.npb.gp.dao.mysql.support.projecttemplate.SelectGpProjectTemplateMapper;
import com.npb.gp.dao.mysql.support.projecttemplate.SelectGpTemplateMapper;
import com.npb.gp.domain.core.GpIsoLanguage;
import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.domain.core.GpProjectTemplateComponent;
import com.npb.gp.domain.core.TemplateBaseInfo;
import com.npb.gp.interfaces.dao.IGpProjectTemplateDao;

@Repository("GpProjectTemplateDao")
public class GpProjectTemplateDao implements IGpProjectTemplateDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${sql.projectTemplate.find}")
	private String sqlFind;
	
	@Value("${sql.projectTemplate.find.byProject}")
	private String sqlFindByProject;
	
	@Value("${sql.projectTemplate.find.byTemplate}")
	private String sqlFindByTemplate;
	
	
	private String templateBaseInfo;

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				dataSource);
	}

	@Override
	public HashMap<String, String> find_by_project_id(long project_id)
			throws Exception {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);

		String sql_project_template_find = "select ptc.project_id, ptc.project_template_id, ptc.template_component_value, tcbi.label from geppetto.project_template_components ptc"
				+ " INNER JOIN geppetto.template_components_base_info tcbi on ptc.template_component_id=tcbi.template_component_id where ptc.project_id=:project_id";

		GenProjectTemplateMapper the_mapper = new GenProjectTemplateMapper();

		ArrayList<GpProjectTemplateComponent> dto_list = (ArrayList<GpProjectTemplateComponent>) this.namedParameterJdbcTemplate
				.query(sql_project_template_find, parameters, the_mapper);
		HashMap<String, String> map = new HashMap<String, String>();
		if (dto_list != null) {
			for (GpProjectTemplateComponent row : dto_list) {
				if (row.getLabel() != null
						&& row.getLabel().trim().length() != 0) {
					map.put(row.getLabel().trim().toUpperCase()
							.replace(" ", "_"), row.getTemplateComponentValue());
				}
			}
		}
		return map;
	}
	
	

	public List<GpProjectTemplate> find(long id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_template_id", id);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		List<GpProjectTemplate> dto_list = this.namedParameterJdbcTemplate
				.query(sqlFind, parameters, selectMapper);
		if (dto_list.size() < 1) {
			//log.warn("Project Template for ID '" + id + "' was not found");
			throw new Exception("Project Template for ID '" + id
					+ "' was not found");
		}
		
		return (ArrayList<GpProjectTemplate>) dto_list;
		//return (GpProjectTemplate) dto_list.get(0);
	}
	

	public List<GpProjectTemplate> findByProject(long projectId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", projectId);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		return this.namedParameterJdbcTemplate.query(sqlFindByProject,
				parameters, selectMapper);
	}
	
	
	
	/*public List<GpProjectTemplate> findByTemplate(long templateId)
			throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", templateId);
		SelectGpProjectTemplateMapper selectMapper = new SelectGpProjectTemplateMapper();

		return this.namedParameterJdbcTemplate.query(sqlFindByTemplate,
				parameters, selectMapper);
	}*/
	
	

	public TemplateBaseInfo findByTemplate(long id) throws Exception {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("template_id", id);
		
		System.out.println(" template id : " + id);
		SelectGpTemplateMapper selectMapper = new SelectGpTemplateMapper();
		
		this.templateBaseInfo = "SELECT * FROM template_base_information WHERE template_id=:template_id";
		TemplateBaseInfo temp_base_info = this.namedParameterJdbcTemplate.queryForObject(templateBaseInfo, parameters, selectMapper);
				
		return temp_base_info;
	}
	
	
	
	
	
	
	
	
}