package com.npb.gp.gen.json.mappers.modules;

public class GpModuleServerJavaSpringboot {
	private String base_package;
	private String[] sql_queries;
	private String[] gradle_dependencies;
	private String[] config_properties;
	
	public String getBase_package() {
		return base_package;
	}
	public void setBase_package(String base_package) {
		this.base_package = base_package;
	}
	public String[] getSql_queries() {
		return sql_queries;
	}
	public void setSql_queries(String[] sql_queries) {
		this.sql_queries = sql_queries;
	}
	public String[] getGradle_dependencies() {
		return gradle_dependencies;
	}
	public void setGradle_dependencies(String[] gradle_dependencies) {
		this.gradle_dependencies = gradle_dependencies;
	}
	
	public String[] getConfig_properties() {
		return config_properties;
	}
	
	public void setConfig_properties(String[] config_properties) {
		this.config_properties = config_properties;
	}
	
}
