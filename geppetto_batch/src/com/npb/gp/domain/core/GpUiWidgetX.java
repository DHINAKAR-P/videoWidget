package com.npb.gp.domain.core;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Dan Castillo</br> Date Created: 03/05/2015</br>
 * @since .75</p>
 * 
 * 
 *        This class is being used to take the place of the GpUiWidget class
 *        that has been used in Geppetto since version .1. The reason for this
 *        class is that Geppetto changed front end technology from Flex/Blaze to
 *        HTML5/REST/Angular and I did not want to restrict the developers of
 *        the Screen Designer component with an old data model.
 * 
 *        Modified Date: 19/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        added few variables and removed parent
 * 
 *        Modified Date: 24/03/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *        removed the font_size for temporarily
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpUiWidgetX {

	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private long parentid;
	// private String parent;
	private String parent_name; // why do we need this?
	private long screen_id;
	private long number_of_children;
	private String type;
	private String supports_label; // why do we need this?
	private String is_container;
	private String ui_technology;
	private String data_binding_context; // = "notbound"; notbound, primarynoun,
											// secondarynoun
	private String verb_binding_context; // = "notbound"; notbound, bound
	private long noun_id;
	private long noun_attribute_id;
	private String extended_attributes; // if this is populated then there are
										// more attributes for the UIWidget
	// private HashMap<String, Object> extended_attribs; // why do we need this
	// private ArrayList<GpUiWidgetX> children;
	// private ArrayList<GpEvent> events;
	private String events;
	private String event_verb_combo;
	private String verb_target;
	private String extra_verb_info;
	private Map<String, Object> children;
	public long width;
	public long height;
	public long x;
	public long y;
	// private String fontSize;
	public long portraitX; // = -1;
	public long portrait_width; // = -1;
	public long portrait_height;
	public long portraitY; // = -1;
	public long landscapeX;// = -1;
	public long landscapeY; // = -1;
	public long landscape_width;
	public long landscape_height;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private String image_src;
	private String target_url;
	private String card_header;
	private String card_footer;
	
	public void setCard_footer(String card_footer) {
		this.card_footer = card_footer;
	}
	public void setCard_header(String card_header) {
		this.card_header = card_header;
	}
	public String getCard_footer() {
		return card_footer;
	}
	public String getCard_header() {
		return card_header;
	}
	
	public String getTarget_url() {
		return target_url;
	}
	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	public String getImage_src() {
		return image_src;
	}
	public void setImage_src(String image_src) {
		this.image_src = image_src;
	}
	public long getId() {
		return id;
	}

	public long getPortrait_width() {
		return portrait_width;
	}
	public void setPortrait_width(long portrait_width) {
		this.portrait_width = portrait_width;
	}
	public long getPortrait_height() {
		return portrait_height;
	}
	public void setPortrait_height(long portrait_height) {
		this.portrait_height = portrait_height;
	}
	public long getLandscape_width() {
		return landscape_width;
	}
	public void setLandscape_width(long landscape_width) {
		this.landscape_width = landscape_width;
	}
	public long getLandscape_height() {
		return landscape_height;
	}
	public void setLandscape_height(long landscape_height) {
		this.landscape_height = landscape_height;
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

	/*
	 * public String getParent() { return parent; }
	 * 
	 * public void setParent(String parent) { this.parent = parent; }
	 */

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public long getScreen_id() {
		return screen_id;
	}

	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
	}

	public long getNumber_of_children() {
		return number_of_children;
	}

	public void setNumber_of_children(long number_of_children) {
		this.number_of_children = number_of_children;
	}

	public String getSupports_label() {
		return supports_label;
	}

	public void setSupports_label(String supports_label) {
		this.supports_label = supports_label;
	}

	public String getIs_container() {
		return is_container;
	}

	public void setIs_container(String is_container) {
		this.is_container = is_container;
	}

	public String getUi_technology() {
		return ui_technology;
	}

	public void setUi_technology(String ui_technology) {
		this.ui_technology = ui_technology;
	}

	public String getData_binding_context() {
		return data_binding_context;
	}

	public void setData_binding_context(String data_binding_context) {
		this.data_binding_context = data_binding_context;
	}

	public String getVerb_binding_context() {
		return verb_binding_context;
	}

	public void setVerb_binding_context(String verb_binding_context) {
		this.verb_binding_context = verb_binding_context;
	}

	public long getNoun_attribute_id() {
		return noun_attribute_id;
	}

	public void setNoun_attribute_id(long noun_attribute_id) {
		this.noun_attribute_id = noun_attribute_id;
	}

	public long getNoun_id() {
		return noun_id;
	}

	public void setNoun_id(long noun_id) {
		this.noun_id = noun_id;
	}

	public String getExtended_attributes() {
		return extended_attributes;
	}

	public void setExtended_attributes(String extended_attributes) {
		this.extended_attributes = extended_attributes;
	}

	/*
	 * public ArrayList<GpEvent> getEvents() { return events; }
	 * 
	 * public void setEvents(ArrayList<GpEvent> events) { this.events = events;
	 * }
	 */

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) {
		this.events = events;
	}

	public String getEvent_verb_combo() {
		return event_verb_combo;
	}

	public void setEvent_verb_combo(String event_verb_combo) {
		this.event_verb_combo = event_verb_combo;
	}

	public String getVerb_target() {
		return verb_target;
	}

	public void setVerb_target(String verb_target) {
		this.verb_target = verb_target;
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	/*
	 * public String getFontSize() { return fontSize; }
	 * 
	 * public void setFontSize(String fontSize) { this.fontSize = fontSize; }
	 */

	public long getPortraitX() {
		return portraitX;
	}

	public void setPortraitX(long portraitX) {
		this.portraitX = portraitX;
	}

	public long getPortraitY() {
		return portraitY;
	}

	public void setPortraitY(long portraitY) {
		this.portraitY = portraitY;
	}

	public long getLandscapeX() {
		return landscapeX;
	}

	public void setLandscapeX(long landscapeX) {
		this.landscapeX = landscapeX;
	}

	public long getLandscapeY() {
		return landscapeY;
	}

	public void setLandscapeY(long landscapeY) {
		this.landscapeY = landscapeY;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}

	public Map<String, Object> getChildren() {
		return children;
	}

	public void setChildren(Map<String, Object> children) {
		this.children = children;
	}

	public long getParentid() {
		return parentid;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
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
	
	public String getExtra_verb_info() {
		return extra_verb_info;
	}
	
	public void setExtra_verb_info(String extra_verb_info) {
		this.extra_verb_info = extra_verb_info;
	}
}
