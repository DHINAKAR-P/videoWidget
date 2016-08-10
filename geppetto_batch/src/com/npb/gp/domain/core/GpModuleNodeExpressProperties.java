package com.npb.gp.domain.core;

import com.npb.gp.domain.core.node.express.GpNodeJsExpressRouterInfo;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeJsExpressPackage;

public class GpModuleNodeExpressProperties {
	GpNodeJsExpressPackage node_packages[];
	String vars[];
	String code_to_add_before_app_configs[];
	String app_configs[];
	GpNodeJsExpressRouterInfo routers[];
	
	public GpNodeJsExpressPackage[] getNode_packages() {
		return node_packages;
	}
	public void setNode_packages(GpNodeJsExpressPackage[] node_packages) {
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
	public GpNodeJsExpressRouterInfo[] getRouters() {
		return routers;
	}
	public void setRouters(GpNodeJsExpressRouterInfo[] routers) {
		this.routers = routers;
	}
	
}
