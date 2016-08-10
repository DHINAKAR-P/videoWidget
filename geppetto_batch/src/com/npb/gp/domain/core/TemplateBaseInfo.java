package com.npb.gp.domain.core;

public class TemplateBaseInfo {
	
	private long template_id;
	private String name;
	private String description;
	private String label;
	private String color;
	private String device;
	
	
	public long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(long template_id) {
		this.template_id = template_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	

}
