package com.npb.gp.domain.core;

/**
 * @author Dheeraj Singh<br>
 *         Date Created: 12/31/2015<br>
 * @since 1.0</p>
 * 
 *        The purpose this class is to encapsulate the project template
 *        component.
 * 
 */

public class GpProjectTemplateComponent {
	private long projectTemplateComponentId;
	private long projectId;
	private long projectTemplateId;

	private long templateId;
	private long templateComponentId;

	private String name;
	private String description;
	private String label;
	private String templateSection;

	private String templateComponentValue;
	private String elementType;

	public long getProjectTemplateComponentId() {
		return projectTemplateComponentId;
	}

	public void setProjectTemplateComponentId(long projectTemplateComponentId) {
		this.projectTemplateComponentId = projectTemplateComponentId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public long getProjectTemplateId() {
		return projectTemplateId;
	}

	public void setProjectTemplateId(long projectTemplateId) {
		this.projectTemplateId = projectTemplateId;
	}

	public long getTemplateComponentId() {
		return templateComponentId;
	}

	public void setTemplateComponentId(long templateComponentId) {
		this.templateComponentId = templateComponentId;
	}

	public String getTemplateComponentValue() {
		return templateComponentValue;
	}

	public void setTemplateComponentValue(String templateComponentValue) {
		this.templateComponentValue = templateComponentValue;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
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

	public String getTemplateSection() {
		return templateSection;
	}

	public void setTemplateSection(String templateSection) {
		this.templateSection = templateSection;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
}
