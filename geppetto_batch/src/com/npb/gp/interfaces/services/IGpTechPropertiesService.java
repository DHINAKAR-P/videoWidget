package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpUser;
import com.npb.gp.domain.core.GpTechProperties;


/** 
*@author Dan Castillo</br>
* Creation Date: 06/18/2014</br>
* @since .35</p>  
*  
* this interface will be implemented by a service that handles deals with tech properties</p>
*  
* 
* 
*/
public interface IGpTechPropertiesService {
	public void create_tech_property(GpTechProperties aproperty,
										GpUser user) throws Exception;
	
	public void update_tech_property(GpTechProperties aproperty,
										GpUser user) throws Exception;
	
	public void delete_tech_property(long property_id,
										GpUser user) throws Exception;
	
	public GpTechProperties search_for_property_by_id(long property_id,
											GpUser user) throws Exception;
	
	public ArrayList<GpTechProperties> search_for_property_by_type(
						String property_type, GpUser user) throws Exception;

	
	public ArrayList<GpTechProperties> get_all_tech_properties(
												GpUser user) throws Exception;

}
