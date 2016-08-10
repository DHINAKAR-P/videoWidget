package com.npb.gp.domain.core;


/**
 * @author Reinaldo</br>
 * Creation Date: 29/09/2015</br>
 * 
 * The purpose of this class is to define the resources of a module object</br>
 *
 *
 */
public class GpModuleResource {
	
	private String viewName;
	private String viewUrl;
	private Boolean menuVisibility;
	private String viewLocation;
	private String controllerName;
	private Boolean no_menu;
	private Boolean no_template;
	
	public Boolean getNo_template() {
		return no_template;
	}
	
	public void setNo_template(Boolean no_template) {
		this.no_template = no_template;
	}
	
	public Boolean getNo_menu() {
		return no_menu;
	}
	public void setNo_menu(Boolean no_menu) {
		this.no_menu = no_menu;
	}
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
	public Boolean getMenuVisibility() {
		return menuVisibility;
	}
	public void setMenuVisibility(Boolean menuVisibility) {
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

}
