package com.npb.gp.gen.json.mappers.modules;

public class GpModuleMobileInfo {
	private String[] js_libraries;
	private String[] other_js_to_import;
	private GpModuleAngularViews[] views;
	private String[] angular_dependencies;
	private String[] app_js_run_parameters;
	private String code_to_add_inside_app_js_run;
	private String code_to_add_inside_app_js_ionic_ready;
	private String[] css_to_import;
	private String[] cordova_plugins;
	
	public String[] getJs_libraries() {
		return js_libraries;
	}
	public void setJs_libraries(String[] js_libraries) {
		this.js_libraries = js_libraries;
	}
	public String[] getOther_js_to_import() {
		return other_js_to_import;
	}
	public void setOther_js_to_import(String[] other_js_to_import) {
		this.other_js_to_import = other_js_to_import;
	}
	public GpModuleAngularViews[] getViews() {
		return views;
	}
	public void setViews(GpModuleAngularViews[] views) {
		this.views = views;
	}
	public String[] getAngular_dependencies() {
		return angular_dependencies;
	}
	public void setAngular_dependencies(String[] angular_dependencies) {
		this.angular_dependencies = angular_dependencies;
	}
	public String[] getApp_js_run_parameters() {
		return app_js_run_parameters;
	}
	public void setApp_js_run_parameters(String[] app_js_run_parameters) {
		this.app_js_run_parameters = app_js_run_parameters;
	}
	public String getCode_to_add_inside_app_js_run() {
		return code_to_add_inside_app_js_run;
	}
	public void setCode_to_add_inside_app_js_run(String code_to_add_inside_app_js_run) {
		this.code_to_add_inside_app_js_run = code_to_add_inside_app_js_run;
	}
	public String getCode_to_add_inside_app_js_ionic_ready() {
		return code_to_add_inside_app_js_ionic_ready;
	}
	public void setCode_to_add_inside_app_js_ionic_ready(String code_to_add_inside_app_js_ionic_ready) {
		this.code_to_add_inside_app_js_ionic_ready = code_to_add_inside_app_js_ionic_ready;
	}
	public String[] getCss_to_import() {
		return css_to_import;
	}
	public void setCss_to_import(String[] css_to_import) {
		this.css_to_import = css_to_import;
	}
	public String[] getCordova_plugins() {
		return cordova_plugins;
	}
	public void setCordova_plugins(String[] cordova_plugins) {
		this.cordova_plugins = cordova_plugins;
	}
	
}
