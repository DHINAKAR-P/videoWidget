package com.npb.gp.dao.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.job.Insert_Requested_Job;
import com.npb.gp.dao.mysql.support.job.JobMapper;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpJob;
import com.npb.gp.gen.dao.mysql.support.baseconfig.GpBaseConfigMapper;
import com.npb.gp.interfaces.dao.IGpJobDao;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 06/14/2014</br>
 * @since .35
 *        </p>
 *
 *        The purpose of this class is to interact with the db for the basic
 *        search</br>
 *        and CRUD operations for a a generation job - it also finds and locks a
 *        record that</br>
 *        represents a generation request
 *        </p>
 *
 *
 */

@Repository("GpJobDao")
public class GpJobDao implements IGpJobDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${update_status_job.sql}")
	private String update_status_job_sql;

	@Value("${update_status_message_job.sql}")
	private String update_status_message_job_sql;

	@Value("${find_job_by_project_id.sql}")
	private String find_job_by_project_id_sql;

	@Value("${find_job_by_status.sql}")
	private String find_job_by_status_sql;
	
	@Value("${update_stack_trace_job.sql}")
	private String update_stack_trace_job_sql;
	@Value("${update_claimed_job.sql}")
	private String update_claimed_job_sql;
	
	@Value("${insert_job.sql}")
	private String insert_job_sql;
	
	private Insert_Requested_Job insert_job;
	

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public void insert(long project_id, long user_id, String username,
			String status_info,String stacktrace,String statusMessage) {
		Date created_date = new Date();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("project_id", project_id);
		paramMap.put("user_id", user_id);
		paramMap.put("user_name", username);
		paramMap.put("status", status_info);
		paramMap.put("status_message", statusMessage);
		paramMap.put("stack_trace", stacktrace);
		paramMap.put("claimed", "");
		paramMap.put("created_date", created_date);
		
		Insert_Requested_Job.SQL_INSERT_JOB = insert_job_sql;
		this.insert_job = new Insert_Requested_Job(this.dataSource);
		this.insert_job.updateByNamedParam(paramMap);
		
		// TODO Auto-generated method stub

	}
	
	@Override
	public void insert(long project_id, long user_id, String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long project_id, long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update_status(long project_id, long user_id, String username, String status_info) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		parameters.addValue("user_id", user_id);
		parameters.addValue("user_name", username);
		parameters.addValue("status_info", status_info);

		this.namedParameterJdbcTemplate.update(update_status_job_sql, parameters);
	}

	@Override
	public void update_message(long project_id, long user_id, String username, String status_message) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		parameters.addValue("user_id", user_id);
		parameters.addValue("user_name", username);
		parameters.addValue("status_message", status_message);

		this.namedParameterJdbcTemplate.update(update_status_message_job_sql, parameters);
	}

	@Override
	public void update_stacktrace(long project_id, long user_id, String username, String stacktrace) {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);
		parameters.addValue("user_id", user_id);
		parameters.addValue("user_name", username);
		parameters.addValue("stack_trace", stacktrace);

		this.namedParameterJdbcTemplate.update(update_stack_trace_job_sql, parameters);
	}

	@Override
	public void delete(long project_id, long user_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public long find_and_lock() throws Exception {
		// TODO Auto-generated method stub

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("job_status", "gen_requested");

		JobMapper job_mapper = new JobMapper();
		List<GpJob> job_list = this.namedParameterJdbcTemplate.query(find_job_by_status_sql, parameters, job_mapper);
		if (job_list.size() < 1) {
			throw new Exception("There is no requested jobs");
		}

		return job_list.get(0).getProject_id();
	}
	
	@Override
	public void update_claimed(long id , String claimed ) {
		//System.out.println("update claimed worked #### :");
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		/**parameters.addValue("project_id", project_id);
		//parameters.addValue("user_id", user_id);**/
		claimed = "t";
		parameters.addValue("id", id);
		parameters.addValue("claimed", "t");

		this.namedParameterJdbcTemplate.update(update_claimed_job_sql, parameters);
	}
	public List<GpJob> find_jobs() throws Exception {
		// TODO Auto-generated method stub

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("job_status", "gen_requested");
		parameters.addValue("claimed", "f");

		JobMapper job_mapper = new JobMapper();
		List<GpJob> job_list = this.namedParameterJdbcTemplate.query(find_job_by_status_sql, parameters, job_mapper);
		

		for (GpJob gpJob : job_list) {			
			//long id = gpJob.getId();
			/*long project_id = gpJob.getProject_id();
			long user_id = gpJob.getUser_id();*/
			String claimed = "";	
			this.update_claimed(gpJob.getId(), claimed);
		}
		
		if (job_list.size() < 1) {
			System.out.println("There is no requested jobs");
		}

		return job_list;
	}

	public GpJob find_by_project_id(long project_id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id);

		JobMapper job_mapper = new JobMapper();
		List<GpJob> job_list = this.namedParameterJdbcTemplate.query(find_job_by_project_id_sql, parameters,
				job_mapper);
		if (job_list.size() < 1) {
			throw new Exception("JOB was not found for project_id:  " + project_id);
		}
		// System.out.println("######### - In GpJobDao - load_configs is: " +
		// config_list.size() + " #######################");

		return job_list.get(0);
	}

	public GpJob find_by_id(long job_id) throws Exception {

		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("job_id", job_id);

		sql = "select gp_job.id as JOB_ID, gp_job.project_id as JOB_PROJECT_ID, " + "" + " from geppetto.gpconfigs "
				+ " where type = :type ";

		GpBaseConfigMapper configs_mapper = new GpBaseConfigMapper();
		List<GpArchitypeConfigurations> config_list = this.namedParameterJdbcTemplate.query(sql, parameters,
				configs_mapper);
		if (config_list.size() < 1) {
			throw new Exception("job_id " + job_id + " was not found");
		}
		System.out.println("######### - In GpGenBaseConfigDao -  load_configs is: " + config_list.size()
				+ " #######################");
		HashMap<String, GpArchitypeConfigurations> the_map = new HashMap<String, GpArchitypeConfigurations>();

		for (GpArchitypeConfigurations a_config : config_list) {
			the_map.put(a_config.getName(), a_config);
		}

		return null;
	}
}