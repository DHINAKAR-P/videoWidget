package com.npb.gp.domain.core;

public class GpMenu {
	private String activity_name;
	private String activity_label;
	private GpMenuProperties[] gpMenuProperties;
	private boolean isModule;
	
	public void setModule(boolean isModule) {
		this.isModule = isModule;
	}
	
	public boolean isModule() {
		return isModule;
	}
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	public String getActivity_label() {
		return activity_label;
	}
	public void setActivity_label(String activity_label) {
		this.activity_label = activity_label;
	}
	public GpMenuProperties[] getGpMenuProperties() {
		return gpMenuProperties;
	}
	public void setGpMenuProperties(GpMenuProperties[] gpMenuProperties) {
		this.gpMenuProperties = gpMenuProperties;
	}
	
}
