package com.npb.gp.domain.core;


/**
 * @author Dan Castillo</br>
 * Creation Date: 02/11/2014
 * @since version .35</p>
 *  
 * 
 * The purpose of this class is to represent a mobile device</p>
 * 
 * 
 * 
 */

public class GpMobileDeviceType {
	
	private long  id;
	private String client_device_type;
	private String client_device_type_name;
	private String client_device_type_label;
	private String client_device_type_description;
	private String client_device_screen_size;
	private String client_device_resolution;
	private String client_device_type_os_name;
	private String client_device_type_os_version;
	private String client_device_ppcm;
	private String landscape_image_name;
	private String portrait_image_name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getClient_device_ppcm() {
		return client_device_ppcm;
	}
	public void setClient_device_ppcm(String client_device_ppcm) {
		this.client_device_ppcm = client_device_ppcm;
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

	
	
	

}
