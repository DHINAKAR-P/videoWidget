package com.npb.gp.dao.mysql.support.activity_gen;

import java.util.Date;

public class GpDtoActivity_and_Screens {
	//information for the activity which is the primary noun
	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private long projectid;
	private long moduleid;
	private String module_type; 
	private long primary_noun_id;
	private String secondary_nouns;
	private String activity_types;
	private Date created_date;
	private long created_by;
	private Date last_modified_date;
	private long last_modified_by;
	
	//information for the screen which is the secondary noun
	private long screen_id;
	private String screen_name;
	private String screen_description;
	private String screen_label;
	private String screen_client_device_type;
	private String screen_client_device_type_label;
	private String screen_client_device_type_os_name;
	private long screen_human_language_id;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public long getProjectid() {
		return projectid;
	}
	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
	public long getModuleid() {
		return moduleid;
	}
	public void setModuleid(long moduleid) {
		this.moduleid = moduleid;
	}
	public String getModule_type() {
		return module_type;
	}
	public void setModule_type(String module_type) {
		this.module_type = module_type;
	}
	public long getPrimary_noun_id() {
		return primary_noun_id;
	}
	public void setPrimary_noun_id(long primary_noun_id) {
		this.primary_noun_id = primary_noun_id;
	}
	public long getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
	}
	public String getSecondary_nouns() {
		return secondary_nouns;
	}
	public void setSecondary_nouns(String secondary_nouns) {
		this.secondary_nouns = secondary_nouns;
	}
	
	public String getActivity_types() {
		return activity_types;
	}
	public void setActivity_types(String activity_types) {
		this.activity_types = activity_types;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getScreen_description() {
		return screen_description;
	}
	public void setScreen_description(String screen_description) {
		this.screen_description = screen_description;
	}
	public String getScreen_label() {
		return screen_label;
	}
	public void setScreen_label(String screen_label) {
		this.screen_label = screen_label;
	}
	public String getScreen_client_device_type() {
		return screen_client_device_type;
	}
	public void setScreen_client_device_type(String screen_client_device_type) {
		this.screen_client_device_type = screen_client_device_type;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public long getCreated_by() {
		return created_by;
	}
	public void setCreated_by(long created_by) {
		this.created_by = created_by;
	}
	public Date getLast_modified_date() {
		return last_modified_date;
	}
	public void setLast_modified_date(Date last_modified_date) {
		this.last_modified_date = last_modified_date;
	}
	public long getLast_modified_by() {
		return last_modified_by;
	}
	public void setLast_modified_by(long last_modified_by) {
		this.last_modified_by = last_modified_by;
	}
	public String getScreen_client_device_type_label() {
		return screen_client_device_type_label;
	}
	public void setScreen_client_device_type_label(
			String screen_client_device_type_label) {
		this.screen_client_device_type_label = screen_client_device_type_label;
	}
	
	
	public String getScreen_client_device_type_os_name() {
		return screen_client_device_type_os_name;
	}
	public void setScreen_client_device_type_os_name(
			String screen_client_device_type_os_name) {
		this.screen_client_device_type_os_name = screen_client_device_type_os_name;
	}
	public long getScreen_human_language_id() {
		return screen_human_language_id;
	}
	public void setScreen_human_language_id(long screen_human_language_id) {
		this.screen_human_language_id = screen_human_language_id;
	}
	
	
	
	
	
}
