package com.npb.gp.domain.core;

public class GpClientDeviceTypes {
	private long id;
	public final static String COLUMN_id = "id";
	private String client_device_type;
	public final static String COLUMN_client_device_type = "client_device_type";
	private String client_device_type_name;
	public final static String COLUMN_client_device_type_name = "client_device_type_name";
	private String client_device_type_label;
	public final static String COLUMN_client_device_type_label = "client_device_type_label";
	private String client_device_type_description;
	public final static String COLUMN_client_device_type_description = "client_device_type_description";
	private String client_device_screen_size;
	public final static String COLUMN_client_device_screen_size = "client_device_screen_size";
	private String client_device_resolution;
	public final static String COLUMN_client_device_resolution = "client_device_resolution";
	private String client_device_type_os_name;
	public final static String COLUMN_client_device_type_os_name = "client_device_type_os_name";
	private String client_device_type_os_version;
	public final static String COLUMN_client_device_type_os_version = "client_device_type_os_version";
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
	
}
