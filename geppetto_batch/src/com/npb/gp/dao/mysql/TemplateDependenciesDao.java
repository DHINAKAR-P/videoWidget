package com.npb.gp.dao.mysql;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.tempdependencies.GpTemplateDependenciesMapper;
import com.npb.gp.domain.core.GpTemplateDependencies;
import com.npb.gp.interfaces.dao.IGpTemplateDependencies;

/**
 * 
 * 		   @author Kumaresan Perumal</br>
 *         Date Created: 05/10/2016</br>
 * 		   @since 1.0
 *        </p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br>
 *        and CRUD operations for a a generation job - it also finds and locks a
 *        record that</br>
 *        represents a generation request
 *        </p>
 *
 *
 */

@Repository("TemplateDependenciesDao")
public class TemplateDependenciesDao implements IGpTemplateDependencies{
	
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public List<GpTemplateDependencies> getDependenciesByTechType(String cssName, String templateName){
		
		String sqlCss = " SELECT template_dependencies.id , "
				+ "template_dependencies.dependencies, "
				+ "template_dependencies.technology_type, "
				+ "template_dependencies.template_name, "
				+ "template_dependencies.component_type "
				+ "FROM template_dependencies "
				+ "WHERE technology_type=:technology_type "
				+ "AND template_name=:template_name";
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("technology_type", cssName);
		parameters.addValue("template_name", templateName);

		GpTemplateDependenciesMapper templateDependenciesMapper = new GpTemplateDependenciesMapper();
		List<GpTemplateDependencies> dependenciesList = this.namedParameterJdbcTemplate.query(sqlCss, parameters, templateDependenciesMapper);
		return dependenciesList;
		
	}
	@Override
	public String getDependenciesByComponentType(String techType, String templateName,String
			componentType){
		String extDep = "";
		// if we do not give space, it will be a problem.
		String sqlJs = " SELECT template_dependencies.id, "
				+ "template_dependencies.dependencies, "
				+ "template_dependencies.technology_type, "
				+ "template_dependencies.template_name, "
				+ "template_dependencies.component_type "
				+ "FROM template_dependencies "
				+ "WHERE technology_type=:technology_type "
				+ "AND template_name=:template_name AND "
				+ "component_type=:component_type ";
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("technology_type", techType);
		parameters.addValue("template_name", templateName);
		parameters.addValue("component_type", componentType);
		

		GpTemplateDependenciesMapper templateDependenciesMapper = new GpTemplateDependenciesMapper();
		List<GpTemplateDependencies> dependenciesList = this.namedParameterJdbcTemplate.query(sqlJs, parameters, templateDependenciesMapper);
		if (dependenciesList.size() > 0) {
			GpTemplateDependencies temp = dependenciesList.get(0);
			String str[] = temp.getDependencies().split(";");
			if (componentType.equals("header") || componentType.equals("footer")) {
				for (String string : str) {
					extDep = string;
				}
			} else if (componentType.equals("app_module_dep")) {
				for (String string : str) {
					extDep += "," + string;
				}
			}
		}
		//System.out.println(" dependencies : " + extDep);
		return extDep;
		
		
	}
	

}
