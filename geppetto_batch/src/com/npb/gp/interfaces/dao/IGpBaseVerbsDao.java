package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpVerb;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/15/2015</br>
 * @since .75</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the functions that handle base verbs -see GpVerb class for description of verbs</p>
 *
 *
 */

public interface IGpBaseVerbsDao {
	
	public ArrayList<GpVerb> get_all_base_verbs() throws Exception;

}
