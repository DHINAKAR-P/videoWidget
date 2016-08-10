package com.npb.gp.domain.core;

import com.npb.gp.domain.core.GpUiWidget;
import java.util.Date;
import java.util.ArrayList;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/20/2013</br>
 * @since .10</p>  
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun"</p>
 *
 *The class represents the concept of a screen. A screen is any UI component</br>
 *that contains other UI components or widgets. In a strict sense a UI component</br>
 *may contain another UI component and NOT be considered a screen. An example of this</br>
 *is the accordion UI widget which can contain labels, text boxes, drop downs, etc.</p>
 *
 * Therefore, the more accurate definition for a screen is as the root container</br>
 * for all UI components for a particular function.</p>
 * 
 * 
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex
 *
 *
 */

public class GpScreen {
	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private String width;
	private String height;
	private ArrayList<GpUiWidget> components; //this is an issue that comes about using the backing beans -change it in the future - dan castillo 1/2/2014 
	private ArrayList<GpUiWidget> landscape_components; //used for devices that change their orientation 
	private long client_device_type_id;		  //number it the db identifying this device 
	private String client_device_type;		  //high level identifier for the type of device 
											  //see GpBaseConstants.GpDeviceType_Pc , GpBaseConstants.GpDeviceType_Tablet, GpBaseConstants.GpDeviceType_Phone
	private String client_device_type_name;   //this is the brand name Nexus seven, Samsung Galaxy or Generic Android Phone, or PC(covers traditional Windows and Linux none mobile)
	private String client_device_type_label;  //use this for the info that is shown to the user I.E - FOR ANDROID It can say Jelly Bean
	private String client_device_type_description;
	private String client_device_screen_size;	//tablest can be 7 inch or 10.1 inch, phone can by 4 or 5.5 in the case of the samsungs
	private String client_device_resolution;
	private String client_device_ppcm;
	private String client_device_type_os_name;	//Android, IOS, Windows Phone, for PC - Windows 7, windows 8, Linux RedHat, Linux Ubuntu
	private String client_device_type_os_version;
	private String landscape_image_name;
	private String portrait_image_name;
	private String orientation;
	private boolean orientation_locked; 
	
	private long client_language_type;		//equates to technical language  - I.E ActionScript, JavaScript
	private long client_library_type;		// equates to jQuery, DOJO, with Angular JS you know need a library subtype
	private long activity_id;
	private long primary_noun_id;
	private ArrayList<String> secondary_noun_ids;
	private long human_language_id;
	/* The type attribute was added after I realized that
	 * a screen is really a specialized version of UIWIDGET
	 * and should be have some of the attributes of the GpUiWidgetBacking
	 * class. Do not confuse this attribute with the GpScreenTypeBacking
	 * class as that class deals with a device screen and not a uiwidget type
	 * - Dan Castillo 12/3/2013
	 *
	 */
	private String type;
	private long projectid;
	
	
	private String multi_user_status;
	private String multi_user_info;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	
	
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
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public ArrayList<GpUiWidget> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<GpUiWidget> components) {
		this.components = components;
	}
	
	
	
	public ArrayList<GpUiWidget> getLandscape_components() {
		return landscape_components;
	}
	public void setLandscape_components(
			ArrayList<GpUiWidget> landscape_components) {
		this.landscape_components = landscape_components;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
	
	public long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}
	
	
	public long getPrimary_noun_id() {
		return primary_noun_id;
	}
	public void setPrimary_noun_id(long primary_noun_id) {
		this.primary_noun_id = primary_noun_id;
	}
	public ArrayList<String> getSecondary_noun_ids() {
		return secondary_noun_ids;
	}
	public void setSecondary_noun_ids(ArrayList<String> secondary_noun_ids) {
		this.secondary_noun_ids = secondary_noun_ids;
	}
	
	public long getHuman_language_id() {
		return human_language_id;
	}
	public void setHuman_language_id(long human_language_id) {
		this.human_language_id = human_language_id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public long getProjectid() {
		return projectid;
	}
	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
	public String getMulti_user_status() {
		return multi_user_status;
	}
	public void setMulti_user_status(String multi_user_status) {
		this.multi_user_status = multi_user_status;
	}
	public String getMulti_user_info() {
		return multi_user_info;
	}
	public void setMulti_user_info(String multi_user_info) {
		this.multi_user_info = multi_user_info;
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
	public String getClient_device_type() {
		return client_device_type;
	}
	public void setClient_device_type(String client_device_type) {
		this.client_device_type = client_device_type;
	}
	
	
	public long getClient_device_type_id() {
		return client_device_type_id;
	}
	public void setClient_device_type_id(long client_device_type_id) {
		this.client_device_type_id = client_device_type_id;
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
	
	public boolean isOrientation_locked() {
		return orientation_locked;
	}
	public void setOrientation_locked(boolean orientation_locked) {
		this.orientation_locked = orientation_locked;
	}
	public long getClient_language_type() {
		return client_language_type;
	}

	public void setClient_language_type(long client_language_type) {
		this.client_language_type = client_language_type;
	}
	public long getClient_library_type() {
		return client_library_type;
	}
	public void setClient_library_type(long client_library_type) {
		this.client_library_type = client_library_type;
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

	
	
	

}
