package com.npb.gp.gen.dao.mysql.support.microflow;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpMicroFlow;

public class GpBaseMicroFlowMapper implements RowMapper<GpMicroFlow> {

	@Override
	public GpMicroFlow mapRow(ResultSet rs, int arg1) throws SQLException {
		GpMicroFlow flow = new GpMicroFlow();
		flow.setAction(rs.getString("micro_flow_step_name"));
		flow.setComponent_type(rs.getString("component_name"));
		flow.setId(rs.getLong("id")); 
		flow.setSequence_id(rs.getLong("sequence_id"));
		return flow;
	}
	
}
