package com.npb.gp.dao.mysql.support.job;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class Insert_Requested_Job extends SqlUpdate {
	public static String SQL_INSERT_JOB = "";

	public Insert_Requested_Job(DataSource dataSource) {
		super(dataSource, SQL_INSERT_JOB);
		
		super.declareParameter(new SqlParameter("user_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("project_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("user_name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("status", Types.VARCHAR));
		super.declareParameter(new SqlParameter("status_message", Types.VARCHAR));
		super.declareParameter(new SqlParameter("stack_trace", Types.VARCHAR));
		super.declareParameter(new SqlParameter("claimed", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date",Types.TIMESTAMP));
	}
}