package com.npb.gp.gen.interfaces.dao;

import java.util.HashMap;

import com.npb.gp.domain.core.GpArchitypeConfigurations;




/**
 * 
 * @author Dan Castillo
 * Date Created: 06/11/2014</br>
 * @since .01</p> 
 * 
 * classes implementing this interface access the base information needed</br>
 * to start the code generation</p>
 *
 */
public interface IGpGenBaseConfigDao {
	
	public HashMap<String, GpArchitypeConfigurations>
								load_configs(String type) throws Exception;

}
