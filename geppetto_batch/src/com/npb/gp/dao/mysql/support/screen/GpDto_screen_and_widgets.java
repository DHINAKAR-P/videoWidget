package com.npb.gp.dao.mysql.support.screen;

import java.util.Date;

/**
 * 
 * @author
 *
 *         Modified Date: 26/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *         Modified type to String for the screen_is_orientation_locked and the
 *         widget_is_container variables
 *
 */

public class GpDto_screen_and_widgets {

	/* SCREEN ATTRIBUTES START */
	private long screen_id;
	private String screen_name;
	private String screen_description;
	private String screen_label;
	private String screen_notes;
	private String screen_width;
	private String screen_height;
	private long screen_client_device_type_id;
	private String screen_client_device_type;
	private String screen_client_device_type_label;
	private String screen_client_device_type_os_name;
	private long screen_human_language_id;
	private String screen_type;
	private long screen_projectid;
	private long screen_activity_id;
	private String screen_current_orientation;
	private String screen_is_orientation_locked;
	private String screen_landscape_image_name;
	private String screen_portrait_image_name;
	private long screen_primary_noun_id;
	private String screen_secondary_noun_ids;

	private Date screen_createdate;
	private long screen_createdby;
	private Date screen_lastmodifieddate;
	private long screen_lastmodifiedby;

	/* SCREEN ATTRIBUTES END */

	/* WIDGETS ATTRIBUTES START */

	private long widget_id;
	private String widget_name;
	private String widget_description;
	private String widget_label;
	private String widget_supports_label;
	private String widget_is_container;
	private long widget_parent_id;
	private String widget_parent_name;
	private String widget_type;
	private String widget_width;
	private String widget_height;
	private long widget_x;
	private long widget_y;
	public long widget_landscape_x;
	public long widget_landscape_y;
	public String widget_landscape_width;
	public String widget_landscape_height;

	public long widget_portrait_x = -1;
	public long widget_portrait_y = -1;
	public String widget_portrait_width;
	public String widget_portrait_height;

	private String widget_data_binding_context;
	private long widget_noun_id;
	private long widget_noun_attribute_id;
	private String widget_verb_binding_context;
	private String widget_verb_target;
	private long widget_number_of_children;
	private String widget_extended_attributes;
	private String widget_event_verb_combo;
	private String widget_notes;
	private Date widget_createdate;
	private long widget_createdby;
	private Date widget_lastmodifieddate;
	private long widget_lastmodifiedby;

	private boolean already_added;

	public boolean isAlready_added() {
		return already_added;
	}

	public void setAlready_added(boolean already_added) {
		this.already_added = already_added;
	}

	public long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
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

	public String getScreen_notes() {
		return screen_notes;
	}

	public void setScreen_notes(String screen_notes) {
		this.screen_notes = screen_notes;
	}

	public String getScreen_width() {
		return screen_width;
	}

	public void setScreen_width(String screen_width) {
		this.screen_width = screen_width;
	}

	public String getScreen_height() {
		return screen_height;
	}

	public void setScreen_height(String screen_height) {
		this.screen_height = screen_height;
	}

	public long getScreen_client_device_type_id() {
		return screen_client_device_type_id;
	}

	public void setScreen_client_device_type_id(
			long screen_client_device_type_id) {
		this.screen_client_device_type_id = screen_client_device_type_id;
	}

	public String getScreen_client_device_type() {
		return screen_client_device_type;
	}

	public void setScreen_client_device_type(String screen_client_device_type) {
		this.screen_client_device_type = screen_client_device_type;
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

	public String getScreen_type() {
		return screen_type;
	}

	public void setScreen_type(String screen_type) {
		this.screen_type = screen_type;
	}

	public long getScreen_projectid() {
		return screen_projectid;
	}

	public void setScreen_projectid(long screen_projectid) {
		this.screen_projectid = screen_projectid;
	}

	public long getScreen_activity_id() {
		return screen_activity_id;
	}

	public void setScreen_activity_id(long screen_activity_id) {
		this.screen_activity_id = screen_activity_id;
	}

	public String getScreen_current_orientation() {
		return screen_current_orientation;
	}

	public void setScreen_current_orientation(String screen_current_orientation) {
		this.screen_current_orientation = screen_current_orientation;
	}

	public String isScreen_is_orientation_locked() {
		return screen_is_orientation_locked;
	}

	public void setScreen_is_orientation_locked(
			String screen_is_orientation_locked) {
		this.screen_is_orientation_locked = screen_is_orientation_locked;
	}

	public String getScreen_landscape_image_name() {
		return screen_landscape_image_name;
	}

	public void setScreen_landscape_image_name(
			String screen_landscape_image_name) {
		this.screen_landscape_image_name = screen_landscape_image_name;
	}

	public String getScreen_portrait_image_name() {
		return screen_portrait_image_name;
	}

	public void setScreen_portrait_image_name(String screen_portrait_image_name) {
		this.screen_portrait_image_name = screen_portrait_image_name;
	}

	public long getScreen_primary_noun_id() {
		return screen_primary_noun_id;
	}

	public void setScreen_primary_noun_id(long screen_primary_noun_id) {
		this.screen_primary_noun_id = screen_primary_noun_id;
	}

	public String getScreen_secondary_noun_ids() {
		return screen_secondary_noun_ids;
	}

	public void setScreen_secondary_noun_ids(String screen_secondary_noun_ids) {
		this.screen_secondary_noun_ids = screen_secondary_noun_ids;
	}

	public Date getScreen_createdate() {
		return screen_createdate;
	}

	public void setScreen_createdate(Date screen_createdate) {
		this.screen_createdate = screen_createdate;
	}

	public long getScreen_createdby() {
		return screen_createdby;
	}

	public void setScreen_createdby(long screen_createdby) {
		this.screen_createdby = screen_createdby;
	}

	public Date getScreen_lastmodifieddate() {
		return screen_lastmodifieddate;
	}

	public void setScreen_lastmodifieddate(Date screen_lastmodifieddate) {
		this.screen_lastmodifieddate = screen_lastmodifieddate;
	}

	public long getScreen_lastmodifiedby() {
		return screen_lastmodifiedby;
	}

	public void setScreen_lastmodifiedby(long screen_lastmodifiedby) {
		this.screen_lastmodifiedby = screen_lastmodifiedby;
	}

	public long getWidget_id() {
		return widget_id;
	}

	public void setWidget_id(long widget_id) {
		this.widget_id = widget_id;
	}

	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widget_name) {
		this.widget_name = widget_name;
	}

	public String getWidget_description() {
		return widget_description;
	}

	public void setWidget_description(String widget_description) {
		this.widget_description = widget_description;
	}

	public String getWidget_label() {
		return widget_label;
	}

	public void setWidget_label(String widget_label) {
		this.widget_label = widget_label;
	}

	public String getWidget_supports_label() {
		return widget_supports_label;
	}

	public void setWidget_supports_label(String widget_supports_label) {
		this.widget_supports_label = widget_supports_label;
	}

	public String isWidget_is_container() {
		return widget_is_container;
	}

	public void setWidget_is_container(String widget_is_container) {
		this.widget_is_container = widget_is_container;
	}

	public long getWidget_parent_id() {
		return widget_parent_id;
	}

	public void setWidget_parent_id(long widget_parent_id) {
		this.widget_parent_id = widget_parent_id;
	}

	public String getWidget_parent_name() {
		return widget_parent_name;
	}

	public void setWidget_parent_name(String widget_parent_name) {
		this.widget_parent_name = widget_parent_name;
	}

	public String getWidget_type() {
		return widget_type;
	}

	public void setWidget_type(String widget_type) {
		this.widget_type = widget_type;
	}

	public String getWidget_width() {
		return widget_width;
	}

	public void setWidget_width(String widget_width) {
		this.widget_width = widget_width;
	}

	public String getWidget_height() {
		return widget_height;
	}

	public void setWidget_height(String widget_height) {
		this.widget_height = widget_height;
	}

	public long getWidget_x() {
		return widget_x;
	}

	public void setWidget_x(long widget_x) {
		this.widget_x = widget_x;
	}

	public long getWidget_y() {
		return widget_y;
	}

	public void setWidget_y(long widget_y) {
		this.widget_y = widget_y;
	}

	public long getWidget_landscape_x() {
		return widget_landscape_x;
	}

	public void setWidget_landscape_x(long widget_landscape_x) {
		this.widget_landscape_x = widget_landscape_x;
	}

	public long getWidget_landscape_y() {
		return widget_landscape_y;
	}

	public void setWidget_landscape_y(long widget_landscape_y) {
		this.widget_landscape_y = widget_landscape_y;
	}

	public String getWidget_landscape_width() {
		return widget_landscape_width;
	}

	public void setWidget_landscape_width(String widget_landscape_width) {
		this.widget_landscape_width = widget_landscape_width;
	}

	public String getWidget_landscape_height() {
		return widget_landscape_height;
	}

	public void setWidget_landscape_height(String widget_landscape_height) {
		this.widget_landscape_height = widget_landscape_height;
	}

	public long getWidget_portrait_x() {
		return widget_portrait_x;
	}

	public void setWidget_portrait_x(long widget_portrait_x) {
		this.widget_portrait_x = widget_portrait_x;
	}

	public long getWidget_portrait_y() {
		return widget_portrait_y;
	}

	public void setWidget_portrait_y(long widget_portrait_y) {
		this.widget_portrait_y = widget_portrait_y;
	}

	public String getWidget_portrait_width() {
		return widget_portrait_width;
	}

	public void setWidget_portrait_width(String widget_portrait_width) {
		this.widget_portrait_width = widget_portrait_width;
	}

	public String getWidget_portrait_height() {
		return widget_portrait_height;
	}

	public void setWidget_portrait_height(String widget_portrait_height) {
		this.widget_portrait_height = widget_portrait_height;
	}

	public String getWidget_data_binding_context() {
		return widget_data_binding_context;
	}

	public void setWidget_data_binding_context(
			String widget_data_binding_context) {
		this.widget_data_binding_context = widget_data_binding_context;
	}

	public long getWidget_noun_id() {
		return widget_noun_id;
	}

	public void setWidget_noun_id(long widget_noun_id) {
		this.widget_noun_id = widget_noun_id;
	}

	public long getWidget_noun_attribute_id() {
		return widget_noun_attribute_id;
	}

	public void setWidget_noun_attribute_id(long widget_noun_attribute_id) {
		this.widget_noun_attribute_id = widget_noun_attribute_id;
	}

	public String getWidget_verb_binding_context() {
		return widget_verb_binding_context;
	}

	public void setWidget_verb_binding_context(
			String widget_verb_binding_context) {
		this.widget_verb_binding_context = widget_verb_binding_context;
	}

	public String getWidget_verb_target() {
		return widget_verb_target;
	}

	public void setWidget_verb_target(String widget_verb_target) {
		this.widget_verb_target = widget_verb_target;
	}

	public long getWidget_number_of_children() {
		return widget_number_of_children;
	}

	public void setWidget_number_of_children(long widget_number_of_children) {
		this.widget_number_of_children = widget_number_of_children;
	}

	public String getWidget_extended_attributes() {
		return widget_extended_attributes;
	}

	public void setWidget_extended_attributes(String widget_extended_attributes) {
		this.widget_extended_attributes = widget_extended_attributes;
	}

	public String getWidget_event_verb_combo() {
		return widget_event_verb_combo;
	}

	public void setWidget_event_verb_combo(String widget_event_verb_combo) {
		this.widget_event_verb_combo = widget_event_verb_combo;
	}

	public String getWidget_notes() {
		return widget_notes;
	}

	public void setWidget_notes(String widget_notes) {
		this.widget_notes = widget_notes;
	}

	public Date getWidget_createdate() {
		return widget_createdate;
	}

	public void setWidget_createdate(Date widget_createdate) {
		this.widget_createdate = widget_createdate;
	}

	public long getWidget_createdby() {
		return widget_createdby;
	}

	public void setWidget_createdby(long widget_createdby) {
		this.widget_createdby = widget_createdby;
	}

	public Date getWidget_lastmodifieddate() {
		return widget_lastmodifieddate;
	}

	public void setWidget_lastmodifieddate(Date widget_lastmodifieddate) {
		this.widget_lastmodifieddate = widget_lastmodifieddate;
	}

	public long getWidget_lastmodifiedby() {
		return widget_lastmodifiedby;
	}

	public void setWidget_lastmodifiedby(long widget_lastmodifiedby) {
		this.widget_lastmodifiedby = widget_lastmodifiedby;
	}

	/* WIDGETS ATTRIBUTES END */

}
