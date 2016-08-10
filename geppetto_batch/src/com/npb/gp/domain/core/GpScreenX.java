package com.npb.gp.domain.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Dan Castillo</br> Date Created: 03/05/2015</br>
 * @since .75</p>
 * 
 * 
 *        This class is being used to take the place of the GpScreen class that
 *        has been used in Geppetto since version .1. The reason for this class
 *        is that Geppetto changed front end technology from Flex/Blaze to
 *        HTML5/REST/Angular and I did not want to restrict the developers of
 *        the Screen Designer component with an old data model.
 * 
 * 
 *        Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Added new variable as the components
 * 
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        added few variables and removed parent
 * 
 *        Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        Modifed the secondary_noun_ids to Map
 * 
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpScreenX {

	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private String type;
	private String width;
	private String height;
	private long client_device_type_id;
	private String client_device_type;
	private String client_device_type_name;
	private String client_device_type_label;
	private String client_device_type_description;
	private String client_device_screen_size;
	private String client_device_resolution;
	private String client_device_ppcm;
	private String client_device_type_os_name;
	private String client_device_type_os_version;
	private String landscape_image_name;
	private String portrait_image_name;
	private String orientation;
	private String orientation_locked;
	private String client_language_type;
	private String client_library_type;
	private long activity_id;
	private long projectid;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private long primary_noun_id;
	private long human_language_id;
	// private ArrayList<GpNoun> secondary_noun_ids;
	// private ArrayList<GpUiWidgetX> children;
	private Map<String, GpUiWidgetX> children;
	private Map<String, Long> secondary_noun_ids;
	private ArrayList<GpUiWidgetX> components;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public long getClient_device_type_id() {
		return client_device_type_id;
	}

	public void setClient_device_type_id(long client_device_type_id) {
		this.client_device_type_id = client_device_type_id;
	}

	public String getClient_device_type() {
		return client_device_type;
	}

	public void setClient_device_type(String client_device_type) {
		this.client_device_type = client_device_type;
	}

	public String getClient_device_type_name() {
		return client_device_type_name;
	}

	public void setClient_device_type_name(String client_device_type_name) {
		this.client_device_type_name = client_device_type_name;
	}

	public String getClient_device_type_label() {
		return client_device_type_label;
	}

	public void setClient_device_type_label(String client_device_type_label) {
		this.client_device_type_label = client_device_type_label;
	}

	public String getClient_device_type_description() {
		return client_device_type_description;
	}

	public void setClient_device_type_description(
			String client_device_type_description) {
		this.client_device_type_description = client_device_type_description;
	}

	public String getClient_device_screen_size() {
		return client_device_screen_size;
	}

	public void setClient_device_screen_size(String client_device_screen_size) {
		this.client_device_screen_size = client_device_screen_size;
	}

	public String getClient_device_resolution() {
		return client_device_resolution;
	}

	public void setClient_device_resolution(String client_device_resolution) {
		this.client_device_resolution = client_device_resolution;
	}

	public String getClient_device_ppcm() {
		return client_device_ppcm;
	}

	public void setClient_device_ppcm(String client_device_ppcm) {
		this.client_device_ppcm = client_device_ppcm;
	}

	public String getClient_device_type_os_name() {
		return client_device_type_os_name;
	}

	public void setClient_device_type_os_name(String client_device_type_os_name) {
		this.client_device_type_os_name = client_device_type_os_name;
	}

	public String getClient_device_type_os_version() {
		return client_device_type_os_version;
	}

	public void setClient_device_type_os_version(
			String client_device_type_os_version) {
		this.client_device_type_os_version = client_device_type_os_version;
	}

	public String getLandscape_image_name() {
		return landscape_image_name;
	}

	public void setLandscape_image_name(String landscape_image_name) {
		this.landscape_image_name = landscape_image_name;
	}

	public String getPortrait_image_name() {
		return portrait_image_name;
	}

	public void setPortrait_image_name(String portrait_image_name) {
		this.portrait_image_name = portrait_image_name;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getOrientation_locked() {
		return orientation_locked;
	}

	public void setOrientation_locked(String orientation_locked) {
		this.orientation_locked = orientation_locked;
	}

	public String getClient_language_type() {
		return client_language_type;
	}

	public void setClient_language_type(String client_language_type) {
		this.client_language_type = client_language_type;
	}

	public long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}

	public long getProjectid() {
		return projectid;
	}

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}

	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}

	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}

	public long getLastmodifiedby() {
		return lastmodifiedby;
	}

	public void setLastmodifiedby(long lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	public long getPrimary_noun_id() {
		return primary_noun_id;
	}

	public void setPrimary_noun_id(long primary_noun_id) {
		this.primary_noun_id = primary_noun_id;
	}

	public long getHuman_language_id() {
		return human_language_id;
	}

	public void setHuman_language_id(long human_language_id) {
		this.human_language_id = human_language_id;
	}

	public Map<String, GpUiWidgetX> getChildren() {
		return children;
	}

	public void setChildren(Map<String, GpUiWidgetX> children) {
		this.children = children;
	}

	public String getClient_library_type() {
		return client_library_type;
	}

	public void setClient_library_type(String client_library_type) {
		this.client_library_type = client_library_type;
	}

	public Map<String, Long> getSecondary_noun_ids() {
		return secondary_noun_ids;
	}

	public void setSecondary_noun_ids(Map<String, Long> secondary_noun_ids) {
		this.secondary_noun_ids = secondary_noun_ids;
	}

	public ArrayList<GpUiWidgetX> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<GpUiWidgetX> components) {
		this.components = components;
	}

	/*
	 * public ArrayList<GpUiWidgetX> getChildren() { return children; }
	 * 
	 * public void setChildren(ArrayList<GpUiWidgetX> children) { this.children
	 * = children; }
	 * 
	 * public ArrayList<GpNoun> getSecondary_noun_ids() { return
	 * secondary_noun_ids; }
	 * 
	 * public void setSecondary_noun_ids(ArrayList<GpNoun> secondary_noun_ids) {
	 * this.secondary_noun_ids = secondary_noun_ids; }
	 */

}
