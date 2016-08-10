package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpUser;


/** 
*@author Dan Castillo</br>
* Creation Date: 11/09/2014</br>
* @since .35</p>  
*  
* this interface will be implemented by a service that handles deals with Activities</br>
* see GpActiviy note for an explanation of the concept of an activity</p>
* 
* Note - the creation date is our of synch with the implementing classes, because </br>
* I simply forgot to do this and instead went straight for the implementing class - Dan castillo</p>
* 
*/


public interface IGpActivityService {
	
	public void create_activity(GpActivity activity, GpUser the_user) throws Exception;
	public GpActivity search_for_update(long activity_id) throws Exception;
	public void update_activity(GpActivity activity, GpUser the_user);
	
	public ArrayList<GpActivity> get_all_activities_for_project(
							long project_id, GpUser the_user)throws Exception;

}
