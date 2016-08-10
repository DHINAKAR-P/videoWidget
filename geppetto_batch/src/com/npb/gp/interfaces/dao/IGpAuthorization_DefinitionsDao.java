package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpAuthorization;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 11/28/2014</br>
 * @since .75</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the Authorizations - see GpAuthorization class for a description of this concept</p>
 *
 *
 */

public interface IGpAuthorization_DefinitionsDao {
	
	public GpAuthorization find_by_id(long auth_id) throws Exception;
	
	public ArrayList<GpAuthorization> get_all_authorizations() throws Exception;

}
