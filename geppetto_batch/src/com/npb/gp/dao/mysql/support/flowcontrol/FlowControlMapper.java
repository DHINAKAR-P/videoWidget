package com.npb.gp.dao.mysql.support.flowcontrol;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpFlowControl;


public class FlowControlMapper implements RowMapper<GpFlowControl> {
	
	@Override
	public GpFlowControl mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpFlowControl flow = new GpFlowControl();
		flow.setId(rs.getLong("id"));
		flow.setMaster_flow_id(rs.getLong("master_flow_id"));
		flow.setComponent_type(rs.getString("component_type"));
		flow.setLabel(rs.getString("label"));
		flow.setDescription(rs.getString("description"));
		flow.setType(rs.getString("type"));
		flow.setSequence_id(rs.getLong("sequence_id"));
		flow.setSub_sequence_id(rs.getLong("sub_sequence_id"));
		flow.setActivity_id(rs.getLong("activity_id"));
		flow.setMaster_flow_id(rs.getLong("micro_flow_id"));
		
		
		return flow;
		
	}

}
