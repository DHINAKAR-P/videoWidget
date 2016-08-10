package com.npb.gp.gen.interfaces.services;

import java.util.HashMap;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;


/**
 * 
 * @author Dan Castillo
 * Date Created: 06/21/2014</br>
 * @since .01</p> 
 * 
 * implemented by the base service generation class</p> 
 */

public interface IGpBaseGenerationService {
	
	public HashMap<String,GpArchitypeConfigurations> 
					get_generation_configurations() throws Exception;
	
	public void generate(IGpGenManager gen_manager) throws Exception;

}
