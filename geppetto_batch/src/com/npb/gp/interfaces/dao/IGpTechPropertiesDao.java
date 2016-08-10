package com.npb.gp.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpTechProperties;

public interface IGpTechPropertiesDao {

	public void insert(GpTechProperties property, long user_id);
	
	public void update(GpTechProperties property, long user_id);
	
	public void delete(long property_id, long user_id);
		
	public GpTechProperties find_by_id(long property_id)throws Exception;
	
	public ArrayList<GpTechProperties> find_by_property_type(
											long property_type)throws Exception;

	
	public ArrayList<GpTechProperties> get_all_properties()throws Exception;

}
