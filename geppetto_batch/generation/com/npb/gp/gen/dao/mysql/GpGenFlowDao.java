package com.npb.gp.gen.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.flowcontrol.FlowControlCountMapper;
import com.npb.gp.dao.mysql.support.flowcontrol.FlowControlMapper;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.dao.IGpGenFlowDao;

/**
 * 
 * @author Dan Castillo
 * Date Created: 11/19/2014</br>
 * @since .2</p> 
 * 
 *
 * this DAO allows to search and add FLOW information at this time 11/19/2014</br>
 * the FLOW concept is new to Geppetto and is only being used in the Generation</br>
 * portion of the solution and only the insert and find_by_id verbs are needed<p>
 *
 */
@Repository("GpGenFlowDao")
public class GpGenFlowDao implements IGpGenFlowDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	@Value("${get_flow_by_id.sql}")
	private String get_flow_by_id_sql;

	
	@Value("${get_count_of_seq_by_type.sql}")
	private String get_count_of_seq_by_type_sql;

	@Value("${get_count_for_one_seq.sql}")
	private String get_count_for_one_seq_sql;
	
	@Value("${get_a_server_flow.sql}")
	private String get_a_server_flow_sql;

	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }

	
	@Override
	public void insert(GpFlowControl flow) throws Exception {
		// TODO Auto-generated method stub

	}
	
	@Override
	public GpFlowControl find_a_client_flow(long master_flow_id,
						long sequence_id, long sub_sequence_id)throws Exception{
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("master_flow_id", master_flow_id);
		parameters.addValue("sequence_id", sequence_id);
		parameters.addValue("sub_sequence_id", sub_sequence_id);
		parameters.addValue("type", GpGenConstants.GpGenerationType_Client);
		

		FlowControlMapper flow_mapper = new FlowControlMapper();
		
		ArrayList<GpFlowControl> flow_list = (ArrayList<GpFlowControl>) this
						.namedParameterJdbcTemplate.query(
							this.get_a_server_flow_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 1){
		throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenFlowDao -  find_a_client_flow is: " + flow_list.size() + " #######################");
		

		
		return flow_list.get(0);

	}

	
	@Override
	public GpFlowControl find_a_server_flow(long master_flow_id,
						long sequence_id, long sub_sequence_id)throws Exception{
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("master_flow_id", master_flow_id);
		parameters.addValue("sequence_id", sequence_id);
		parameters.addValue("sub_sequence_id", sub_sequence_id);
		parameters.addValue("type", GpGenConstants.GpGenerationType_Server);
		

		FlowControlMapper flow_mapper = new FlowControlMapper();
		
		ArrayList<GpFlowControl> flow_list = (ArrayList<GpFlowControl>) this
						.namedParameterJdbcTemplate.query(
							this.get_a_server_flow_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 1){
		throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenFlowDao -  find_a_server_flow is: " + flow_list.size() + " #######################");
		

		
		return flow_list.get(0);

	}


	@Override
	public ArrayList<GpFlowControl> find_flow_by_id(long master_flow_id)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("master_flow_id", master_flow_id);

		FlowControlMapper flow_mapper = new FlowControlMapper();
		
		ArrayList<GpFlowControl> flow_list = (ArrayList<GpFlowControl>) this
						.namedParameterJdbcTemplate.query(
							this.get_flow_by_id_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 1){
			//throw new Exception("no flow  found");
			flow_list = new ArrayList<GpFlowControl>();
		}
		//System.out.println("######### - In GpGenFlowDao -  find_flow_by_id is: " + flow_list.size() + " #######################");
		

		
		return flow_list;
	}
	
	@Override
	public Long get_count_of_seq_by_type(long master_flow_id, String type)
															throws Exception{
		
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("master_flow_id", master_flow_id);
		parameters.addValue("type", type);

		FlowControlCountMapper flow_mapper = new FlowControlCountMapper();
		
		List<Long> the_count = this
						.namedParameterJdbcTemplate.query(
							this.get_count_of_seq_by_type_sql, parameters, flow_mapper);
		
		if(the_count.size() < 1){
		throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenFlowDao -  get_count_of_seq_by_type is: " + the_count.size() + " #######################");
		

		return (Long)the_count.get(0);
		
	}
	@Override
	public Long get_count_for_one_seq(long master_flow_id, String type,
											long sequence_id)throws Exception{
		
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("master_flow_id", master_flow_id);
		parameters.addValue("type", type);
		parameters.addValue("sequence_id", sequence_id);

		FlowControlCountMapper flow_mapper = new FlowControlCountMapper();
		
		List<Long> the_count = this
						.namedParameterJdbcTemplate.query(
							this.get_count_for_one_seq_sql, parameters, flow_mapper);
		
		if(the_count.size() < 1){
		throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenFlowDao -  get_count_for_one_seq is: " + the_count.size() + " #######################");
		

		return (Long)the_count.get(0);

	}

}
