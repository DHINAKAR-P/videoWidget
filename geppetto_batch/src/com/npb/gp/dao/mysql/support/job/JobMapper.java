package com.npb.gp.dao.mysql.support.job;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpJob;

public class JobMapper implements RowMapper<GpJob> {
	@Override
	public GpJob mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpJob the_job = new GpJob();
		the_job.setId(rs.getLong("JOB_ID"));
		the_job.setProject_id(rs.getLong("JOB_PROJECT_ID"));
		the_job.setUser_id(rs.getLong("JOB_USER_ID"));
		the_job.setUsername(rs.getString("JOB_USER_NAME"));
		the_job.setStatus(rs.getString("JOB_STATUS"));
		the_job.setStatus_message(rs.getString("JOB_MESSAGE"));
		the_job.setStack_trace(rs.getString("JOB_STACK_TRACE"));
		the_job.setClaimed(rs.getString("JOB_CLAIMED"));

		return the_job;
	}
}