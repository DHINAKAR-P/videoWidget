package com.npb.gp.domain.core;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 06/18/2013</br>
 * @since .35</p>  
 *
 * The purpose of this class is to be the base class that defines a UI Widget</br>
 * A widget can be a button, Grid, anything that the user can see on a screen</br>
 * The class should always be extended to account for properties/behaviors</br>
 * that are specific to an instance of a Widget - For example a Grid has columns</br>
 * where as a TextBox does not</p>
 * 
 * Please note - in previous versions of Geppetto there was a class that was</br>
 * similar to this one and was called GpUiAttribute</p>
 * 
 * 
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex
 * 
 * 
 * Modified Date: 03/12/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * added the <b>screen_id</b> field:</p>
 * 
 * Originally I was handling all of the relationships between a screen and widget</br>
 * by using the parent_id and this suffices in most cases since a screen is a type</br>
 * of widget. The only issue is when dealing with the database, the parent_id, of a widget</br>
 * will not always be the screen where that widget belongs because a parent can be other widgets</br>
 * when this happens, because I don't know and don't want to spend the time figuring out</br>
 * how to do a recursive SQL statement I added the screen_id so that the join between</br>
 * the screen table and the widget table on screen_id will return all widgets for a screen</br>
 * using one query. One of the effects to this approach is that the screen_id and parent_id</br>
 * for non-nested widgets will be the same , but will differ for Grid Columns, and widgets</br>
 * that are contained with in widget containers. like tabs, accordions or border controls</p>

 * 
 */


public class GpUiWidget {
	private long id=0;
	private String name;
	private String description;
	private Boolean supports_label;
	private Boolean is_container;
	private String label;
	private String uitechnology; //jquery, jquery_mobile, flex, AIR
	private String notes;
	private String width;
	private String height;
	private long x;
	private long y;
	private String data_binding_context = "notbound"; //notbound, primarynoun, secondarynoun
	private String verb_binding_context = "notbound"; //notbound, bound
	private long noun_attribute_id;
	private long noun_id; 
	private String type;
	private long parent_id;
	private long screen_id;		//this is used to facilitate with the SQl as the parent
	private String parent_name = "";  // this is used during creates when there may not be an id
	private long number_of_children;
	private String extended_attributes; //if this is populated then there are more attributes for the UIWidget 
	private HashMap<String, Object> extended_attribs;
	//private ArrayList<GpUiWidgetBacking> backing_children;
	private ArrayList<GpUiWidget> children;
	private ArrayList<GpEvent> supported_events;	////is this really necessary?
	private ArrayList<GpEvent> events;
	private String event_verb_combo;
	private String verb_target;
	
	
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	
	
	public long portrait_x = -1;
	public long portrait_y = -1;
	public long portrait_width = -1;
	public long portrait_height = -1;
	
	public long landscape_x = -1;
	public long landscape_y = -1;
	public long landscape_width = -1;
	public long landscape_height = -1;

	
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
	
	
	
	public Boolean getSupports_label() {
		return supports_label;
	}
	public void setSupports_label(Boolean supports_label) {
		this.supports_label = supports_label;
	}
	
	
	public Boolean getIs_container() {
		return is_container;
	}
	public void setIs_container(Boolean is_container) {
		this.is_container = is_container;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUitechnology() {
		return uitechnology;
	}
	public void setUitechnology(String uitechnology) {
		this.uitechnology = uitechnology;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getVerb_binding_context() {
		return verb_binding_context;
	}
	public void setVerb_binding_context(String verb_binding_context) {
		this.verb_binding_context = verb_binding_context;
	}
	public String getData_binding_context() {
		return data_binding_context;
	}
	public void setData_binding_context(String data_binding_context) {
		this.data_binding_context = data_binding_context;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
		
	public long getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(long screen_id) {
		this.screen_id = screen_id;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public long getNumber_of_children() {
		return number_of_children;
	}
	public void setNumber_of_children(long number_of_children) {
		this.number_of_children = number_of_children;
	}
	
	
	public ArrayList<GpEvent> getSupported_events() {
		return supported_events;
	}
	public void setSupported_events(ArrayList<GpEvent> supported_events) {
		this.supported_events = supported_events;
	}
	public ArrayList<GpEvent> getEvents() {
		return events;
	}
	public void setEvents(ArrayList<GpEvent> events) {
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
	public HashMap<String, Object> getExtended_attribs() {
		return extended_attribs;
	}
	public void setExtended_attribs(HashMap<String, Object> extended_attribs) {
		this.extended_attribs = extended_attribs;
	}
	public String getExtended_attributes() {
		return extended_attributes;
	}
	public void setExtended_attributes(String extended_attributes) {
		this.extended_attributes = extended_attributes;
	}
	
	
	/** the next two setter and getters need to be changed and remove the 
	 * next setters and getters this class should not use GpUiWidgetBacking
	 * that should be in the Backing class 
	 * @return
	 */
	public ArrayList<GpUiWidget> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<GpUiWidget> children) {
		this.children = children;
	}
	/*
	public ArrayList<GpUiWidgetBacking> getChildren() {
		return backing_children;
	}
	public void setChildren(ArrayList<GpUiWidgetBacking> backing_children) {
		this.backing_children = backing_children;
	}
	*/
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
	public long getPortrait_x() {
		return portrait_x;
	}
	public void setPortrait_x(long portrait_x) {
		this.portrait_x = portrait_x;
	}
	public long getPortrait_y() {
		return portrait_y;
	}
	public void setPortrait_y(long portrait_y) {
		this.portrait_y = portrait_y;
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
	public long getLandscape_x() {
		return landscape_x;
	}
	public void setLandscape_x(long landscape_x) {
		this.landscape_x = landscape_x;
	}
	public long getLandscape_y() {
		return landscape_y;
	}
	public void setLandscape_y(long landscape_y) {
		this.landscape_y = landscape_y;
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
	
	

}
