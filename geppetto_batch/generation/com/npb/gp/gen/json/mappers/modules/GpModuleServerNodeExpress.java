package com.npb.gp.gen.json.mappers.modules;

public class GpModuleServerNodeExpress {
	private GpModuleNodePackage[] node_packages;
	private String[] vars;
	private String[] code_to_add_before_app_configs;
	private String[] app_configs;
	private GpModuleNodeRouters[] routers;
	
	public GpModuleNodePackage[] getNode_packages() {
		return node_packages;
	}
	public void setNode_packages(GpModuleNodePackage[] node_packages) {
		this.node_packages = node_packages;
	}
	public String[] getVars() {
		return vars;
	}
	public void setVars(String[] vars) {
		this.vars = vars;
	}
	public String[] getCode_to_add_before_app_configs() {
		return code_to_add_before_app_configs;
	}
	public void setCode_to_add_before_app_configs(String[] code_to_add_before_app_configs) {
		this.code_to_add_before_app_configs = code_to_add_before_app_configs;
	}
	public String[] getApp_configs() {
		return app_configs;
	}
	public void setApp_configs(String[] app_configs) {
		this.app_configs = app_configs;
	}
	public GpModuleNodeRouters[] getRouters() {
		return routers;
	}
	public void setRouters(GpModuleNodeRouters[] routers) {
		this.routers = routers;
	}
	
}
