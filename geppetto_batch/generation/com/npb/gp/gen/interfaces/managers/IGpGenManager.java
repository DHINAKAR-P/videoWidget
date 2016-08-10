package com.npb.gp.gen.interfaces.managers;

import java.util.HashMap;
import java.util.Map;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpJob;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpUser;

/**
 * 
 * @author Dan Castillo
 * Date Created: 06/11/2014</br>
 * @since .01</p> 
 * 
 *  The interface is intended to be implemented by the main class that handles</br>
 *  code generation for an entire project</p>
 *  
 *  Please note that the concept of this interface and the classes that implement it</br>
 *  has been around since the Cancun/Caracas versions of Geppetto back then the</br>
 *  classes that implemented it were "GpGenApplicationManagerFlex40" or </br>
 *  "GpGenApplicationManagerFlex30"</p>
 *  
 */
public interface IGpGenManager{
	public void accept_control(GpJob the_job) throws Exception;
	public GpProject get_project();
	public GpUser get_user();
	public HashMap<String, GpArchitypeConfigurations> getBase_configs();
	public HashMap<String, GpArchitypeConfigurations> getDerived_configs();
	public Map<Long,GpClientDeviceTypes> getClient_device_types();
	public void update_job_status(long project_id, long user_id, String username, String status_info, String status_message, String stacktrace);
}
