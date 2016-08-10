package com.npb.gp.domain.core;

import java.util.List;

/**
 * @author Dheeraj Singh<br>
 *         Date Created: 12/30/2015<br>
 * @since 1.0</p>
 * 
 *        The purpose this class is to encapsulate the project template
 *        information.
 * 
 */

public class GpProjectTemplate {
	private long projectTemplateId;
	private long projectId;
	private long templateId;

	private String color;
	private String name;
	private String description;
	private String device;
	private String label;

	private List<GpProjectTemplateComponent> templateComponents;

	public long getProjectTemplateId() {
		return projectTemplateId;
	}

	public void setProjectTemplateId(long projectTemplateId) {
		this.projectTemplateId = projectTemplateId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<GpProjectTemplateComponent> getTemplateComponents() {
		return templateComponents;
	}

	public void setTemplateComponents(
			List<GpProjectTemplateComponent> templateComponents) {
		this.templateComponents = templateComponents;
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

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "GpProjectTemplate [projectTemplateId=" + projectTemplateId
				+ ", projectId=" + projectId + ", templateId=" + templateId
				+ ", color=" + color + ", name=" + name + ", description="
				+ description + ", device=" + device + ", label=" + label
				+ ", templateComponents=" + templateComponents + "]";
	}

}
