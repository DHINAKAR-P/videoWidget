package com.npb.gp.gen.json.mappers.modules;

public class GpModuleAngularJS {
	private GpModuleAngularViews[] resources;
	private String[] libraries;
	private String[] angular_dependencies;
	private String[] angular_config_properties;
	private String code_to_add_inside_app_js_config;
	private String code_between_head_and_body;
	private String[] css_libraries;
	private GpModuleMobileInfo mobile_info;
	private String[] libraries_before_app_js;
	private String code_to_add_to_app_js;
	
	public GpModuleAngularViews[] getResources() {
		return resources;
	}
	public void setResources(GpModuleAngularViews[] resources) {
		this.resources = resources;
	}
	public String[] getLibraries() {
		return libraries;
	}
	public void setLibraries(String[] libraries) {
		this.libraries = libraries;
	}
	public String[] getAngular_dependencies() {
		return angular_dependencies;
	}
	public void setAngular_dependencies(String[] angular_dependencies) {
		this.angular_dependencies = angular_dependencies;
	}
	public String[] getAngular_config_properties() {
		return angular_config_properties;
	}
	public void setAngular_config_properties(String[] angular_config_properties) {
		this.angular_config_properties = angular_config_properties;
	}
	public String getCode_to_add_inside_app_js_config() {
		return code_to_add_inside_app_js_config;
	}
	public void setCode_to_add_inside_app_js_config(String code_to_add_inside_app_js_config) {
		this.code_to_add_inside_app_js_config = code_to_add_inside_app_js_config;
	}
	public String getCode_between_head_and_body() {
		return code_between_head_and_body;
	}
	public void setCode_between_head_and_body(String code_between_head_and_body) {
		this.code_between_head_and_body = code_between_head_and_body;
	}
	public String[] getCss_libraries() {
		return css_libraries;
	}
	public void setCss_libraries(String[] css_libraries) {
		this.css_libraries = css_libraries;
	}
	public GpModuleMobileInfo getMobile_info() {
		return mobile_info;
	}
	public void setMobile_info(GpModuleMobileInfo mobile_info) {
		this.mobile_info = mobile_info;
	}
	public String[] getLibraries_before_app_js() {
		return libraries_before_app_js;
	}
	public void setLibraries_before_app_js(String[] libraries_before_app_js) {
		this.libraries_before_app_js = libraries_before_app_js;
	}
	public String getCode_to_add_to_app_js() {
		return code_to_add_to_app_js;
	}
	public void setCode_to_add_to_app_js(String code_to_add_to_app_js) {
		this.code_to_add_to_app_js = code_to_add_to_app_js;
	}
	
}
