package com.npb.gp.gen.interfaces.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;

import com.npb.gp.domain.core.GpFlowControl;


/**
 * 
 * @author Dan Castillo
 * Date Created: 11/19/2014</br>
 * @since .2</p> 
 * 
 * classes implementing this interface access the information needed to </br>
 * determine the FLOW of the generated code<p>
 * 
 */

public interface IGpGenFlowDao {
	
	public void insert(GpFlowControl flow )throws Exception;
	public ArrayList<GpFlowControl> find_flow_by_id(long master_flow_id)throws Exception;
	
	public Long get_count_of_seq_by_type(long master_flow_id, String type)throws Exception;
	
	public Long get_count_for_one_seq(long master_flow_id,
							String type, long sequence_id)throws Exception;
	
	public GpFlowControl find_a_server_flow(long master_flow_id,
					long sequence_id, long sub_sequence_id)throws Exception;
	
	public GpFlowControl find_a_client_flow(long master_flow_id,
			long sequence_id, long sub_sequence_id)throws Exception;


}
