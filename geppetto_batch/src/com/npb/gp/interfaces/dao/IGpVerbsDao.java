package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpVerb;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 06/13/2014</br>
 * @since .75</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the functions that handle verbs -see GpVerb class for description of verbs</p>
 *
 * Modified Date: 02/24/2015</br>
 * Modified By:  Dan Castillo</p>
 * 
 * added the  get_verbs_by_activity_id method
 *
 */

public interface IGpVerbsDao {
	
	public GpVerb find_by_id(long verb_id)throws Exception;
	
	public ArrayList<GpVerb> get_verbs_by_activity_id(long activity_id) throws Exception;
	
	public ArrayList<GpVerb> get_all_verbs() throws Exception;
	


}
