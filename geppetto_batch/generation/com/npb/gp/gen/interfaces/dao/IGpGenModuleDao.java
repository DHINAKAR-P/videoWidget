package com.npb.gp.gen.interfaces.dao;

import com.npb.gp.domain.core.GpModule;


/**
 * 
 * @author Reinaldo
 * Date Created: 18/09/2015</br>
 * 
 * classes implementing this interface access the information needed to </br>
 * determine the Module of the generated code<p>
 * 
 * 
 */
public interface IGpGenModuleDao {
	
	public GpModule load_module(long id) throws Exception;

}