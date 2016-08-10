package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpScreen;

/** 
*@author Dan Castillo</br>
* Creation Date: 03/11/2014</br>
* @since .35</p>  
*  
* this interface will be implemented by a service that handles screens using</br>
* the Android OS</p> 
* The reason for created OS specifics Interfaces and implementations is purely</br>
* for flexibility in the future</p>
* 
* Modified Date: 10/22/2014</br>
* Modified By:  Dan Castillo</p>
* 
* removed all references to the "backing" types - as these were legacy from
* the early days of Geppetto when the ui was Flex
* 
*/

public interface IGpAndroidScreenService {
	public void create_screen(GpScreen ascreen) throws Exception;
	public void update_screen(GpScreen ascreen) throws Exception;
	public void delete_screen(long screen_id) throws Exception;
	public GpScreen search_for_screen_by_screen_id(long screen_id) throws Exception;
	public ArrayList<GpScreen> search_for_screens_by_project_id(long project_id) throws Exception;
	public ArrayList<GpScreen> search_for_screens_by_activity_id(long activity_id) throws Exception;
}
