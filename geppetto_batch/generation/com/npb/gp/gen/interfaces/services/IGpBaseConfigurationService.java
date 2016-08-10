package com.npb.gp.gen.interfaces.services;

import java.util.HashMap;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
/**
 * 
 * @author Dan Castillo
 * Date Created: 06/11/2014</br>
 * @since .01</p> 
 * 
 * the classes that implement this interface are responsible for loading</br>
 * and manipulating the initial set of information needed to carry out</br>
 * the application generation</p>
 * 
 *
 */
public interface IGpBaseConfigurationService {
	
	public HashMap<String,GpArchitypeConfigurations> 
								get_base_generation_configurations(IGpGenManager gen_manager) throws Exception;
	
	public void execute_base_configurations(IGpGenManager gen_manager) throws Exception;

}
