package com.npb.gp.gen.json.mappers.modules;

public class GpModuleAngularViews {
	private String viewName;
	private String viewUrl;
	private boolean menuVisibility;
	private String viewLocation;
	private String controllerName;
	private boolean no_menu;
	private boolean no_template;
	
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public boolean isMenuVisibility() {
		return menuVisibility;
	}
	public void setMenuVisibility(boolean menuVisibility) {
		this.menuVisibility = menuVisibility;
	}
	public String getViewLocation() {
		return viewLocation;
	}
	public void setViewLocation(String viewLocation) {
		this.viewLocation = viewLocation;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public boolean isNo_menu() {
		return no_menu;
	}
	public void setNo_menu(boolean no_menu) {
		this.no_menu = no_menu;
	}
	public boolean isNo_template() {
		return no_template;
	}
	public void setNo_template(boolean no_template) {
		this.no_template = no_template;
	}
	
}
