package com.npb.gp.interfaces.dao;

import java.util.List;

import com.npb.gp.domain.core.GpTemplateDependencies;

public interface IGpTemplateDependencies {

	public List<GpTemplateDependencies> getDependenciesByTechType(String cssName, String templateName);

	public String getDependenciesByComponentType(String jsName, String templateName,String componentType);

	
	
	
	
}
