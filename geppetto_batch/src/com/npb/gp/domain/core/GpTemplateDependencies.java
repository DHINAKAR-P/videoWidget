package com.npb.gp.domain.core;

public class GpTemplateDependencies {
	
	private long id;
	private String dependencies;
	private String technology_type;
	private String template_name;
	private String component_type;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDependencies() {
		return dependencies;
	}
	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}
	public String getTechnology_type() {
		return technology_type;
	}
	public void setTechnology_type(String technology_type) {
		this.technology_type = technology_type;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getComponent_type() {
		return component_type;
	}
	public void setComponent_type(String component_type) {
		this.component_type = component_type;
	}
	@Override
	public String toString() {
		return "GpTemplateDependencies [id=" + id + ", dependencies="
				+ dependencies + ", technology_type=" + technology_type
				+ ", template_name=" + template_name + ", component_type="
				+ component_type + "]";
	}
	
	

}
