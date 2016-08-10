package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpUser;

/**
 * 
 * @author Reinaldo</br>
 * Date Created: 16/09/2015</br>
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the functions that handle users -see GpUser class for description of user</p>
 *
 */
public interface IGpUserDao {
	
	public GpUser find_user_by_id(long user_id)throws Exception;


}
