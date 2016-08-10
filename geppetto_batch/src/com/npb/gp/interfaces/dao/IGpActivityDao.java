package com.npb.gp.interfaces.dao;

import java.util.List;
import com.npb.gp.domain.core.GpActivity;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 02/26/2014</br>
 * @since .35</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the Activity functions</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun", back then Activity</br>
 *used to be known as operation</p>
 *
 * Modified Date: 10/04/2014</br>
 * Modified By:  Dan Castillo</p>
 *
 * added find_all_base_by_projectid(long projectid) method to handle searches
 * where you only want to obtain the parent (base) noun by none of its children 
 *
 */
public interface IGpActivityDao {

	public void insert (GpActivity activity);
	public void update (GpActivity activity);
	public void delete (long activity_id);
	public GpActivity find_by_id(long activity_id) throws Exception;
	public List<GpActivity> find_all_by_projectid(long projectid) throws Exception;
	public List<GpActivity> find_all_base_by_projectid(long projectid) throws Exception;
	
	
}
