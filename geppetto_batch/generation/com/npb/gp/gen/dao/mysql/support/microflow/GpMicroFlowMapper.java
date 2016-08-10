package com.npb.gp.gen.dao.mysql.support.microflow;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMicroFlow;

public class GpMicroFlowMapper implements RowMapper<GpMicroFlow> {
	
	@Override
	public GpMicroFlow mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpMicroFlow flow = new GpMicroFlow();
		
		flow.setAction(rs.getString("action"));
		flow.setCode_id(rs.getLong("code_id"));
		flow.setComponent_type(rs.getString("component_type"));
		flow.setDescription(rs.getString("description"));
		flow.setFlow_control_id(rs.getLong("flow_control_id"));
		flow.setId(rs.getLong("id"));
		flow.setMaster_flow_id(rs.getLong("master_flow_id")); 
		flow.setSequence_id(rs.getLong("sequence_id"));
		flow.setVerb_id(rs.getLong("verb_id"));
		
		
		return flow;
	}

}
