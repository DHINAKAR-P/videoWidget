package com.npb.gp.gen.interfaces.dao;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpMicroFlow;

/**
 * 
 * @author Dan Castillo
 * Date Created: 02/17/2015</br>
 * @since .2</p> 
 * 
 * classes implementing this interface access the information needed to </br>
 * determine the MICRO FLOW of the generated code<p>
 * 
 */

public interface IGpGenMicroFlowDao {
	
	public void insert(GpMicroFlow flow )throws Exception;
	public ArrayList<GpMicroFlow> find_by_flow_control_id(long flow_id)throws Exception;
	public ArrayList<GpMicroFlow> find_by_verb_id(long verb_id)throws Exception;
	public GpMicroFlow find_by_id(long id) throws Exception;
	public void update(GpMicroFlow flow) throws Exception;
	public void delete(long id) throws Exception;
	public ArrayList<GpMicroFlow> find_microflow_by_component_name_and_base_verb_name(String base_verb_name, String component_name)throws Exception;
	
	public ArrayList<GpMicroFlow> find_by_verb_id_and_component_type(long verb_id, String component_type)
			throws Exception ;

}
