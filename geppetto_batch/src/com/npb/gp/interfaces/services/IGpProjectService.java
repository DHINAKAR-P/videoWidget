package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;


/** 
*@author Dan Castillo</br>
* Creation Date: 04/14/2014</br>
* @since .35</p>  
*  
* this interface will be implemented by a service that handles deals with projects</p>
* 
* 
* Modified Date: 10/22/2014</br>
* Modified By:  Dan Castillo</p>
* 
* removed all references to the "backing" types - as these were legacy from
* the early days of Geppetto when the ui was Flex
* 
* 
*/

public interface IGpProjectService {
	public void create_project(GpProject aproject, GpUser user) throws Exception;
	public void update_project(GpProject aproject, GpUser user) throws Exception;
	public void delete_project(long screen_id, GpUser user) throws Exception;
	public GpProject search_for_update(long project_id, GpUser user) throws Exception;
	public GpProject search_for_project(long project_id, GpUser user) throws Exception;
	public ArrayList<GpProject> search_project_by_user_id(GpUser user) throws Exception;
}
