package com.npb.gp.interfaces.dao;

import java.util.ArrayList;


import com.npb.gp.domain.core.GpProject;


/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/15/2014</br>
 * @since .35</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the project functions</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun"</p>
 *
 *
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex

 *
 */

public interface IGpProjectDao {

	public void insert(GpProject aproject) throws Exception;
	public void update(GpProject aproject) throws Exception;
	public void delete(GpProject aproject) throws Exception;
	public GpProject find_by_id(long project_id) throws Exception;
	public ArrayList<GpProject> find_by_user_id(long user_id) throws Exception;

}
