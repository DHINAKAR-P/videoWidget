package com.npb.gp.gen.dao.mysql;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.flowcontrol.FlowControlMapper;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.gen.dao.mysql.support.microflow.GpBaseMicroFlowMapper;
import com.npb.gp.gen.dao.mysql.support.microflow.GpMicroFlowMapper;
import com.npb.gp.gen.interfaces.dao.IGpGenMicroFlowDao;
/**
 * 
 * @author Dan Castillo
 * Date Created: 02/17/2015</br>
 * @since .2</p> 
 * 
 *
 * this DAO allows to search and add MicroFLOW information at this time 02/17/2015</br>
 * the Micro FLOW concept is new to Geppetto and is only being used in the Generation</br>
 * portion of the solution <p>
 *
 */
@Repository("GpGenMicroFlowDao")
public class GpGenMicroFlowDao implements IGpGenMicroFlowDao {
	
	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	@Value("${get_flow_by_verb_id.sql}")
	private String get_flow_by_verb_id_sql;

	
	@Value("${get_flow_by_flow_control_id.sql}")
	private String get_flow_by_flow_control_id_sql;

	@Value("${get_flow_by_id.sql}")
	private String get_flow_by_id_sql;
	
	@Value("${get_flow_by_verb_id_and_component_type.sql}")
	private String get_flow_by_verb_id_and_component_type_sql;
	
	@Value("${get_microflow_by_base_base_verb_name_and_component_name.sql}")
	private String get_microflow_by_base_base_verb_name_and_component_name;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	

	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }



	@Override
	public void insert(GpMicroFlow flow) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<GpMicroFlow> find_by_flow_control_id(long flow_id)
			throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("flow_control_id", flow_id);

		GpMicroFlowMapper flow_mapper = new GpMicroFlowMapper();
		
		ArrayList<GpMicroFlow> flow_list = (ArrayList<GpMicroFlow>) this
						.namedParameterJdbcTemplate.query(
							this.get_flow_by_flow_control_id_sql, parameters, flow_mapper);
								
		if(flow_list.size() < 1){
			throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenMicroFlowDao -  find_by_flow_control_id is: " + flow_list.size() + " #######################");
		
		return flow_list;
	}

	@Override
	public ArrayList<GpMicroFlow> find_by_verb_id(long verb_id)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("verb_id", verb_id);

		GpMicroFlowMapper flow_mapper = new GpMicroFlowMapper();
		
		ArrayList<GpMicroFlow> flow_list = (ArrayList<GpMicroFlow>) this
						.namedParameterJdbcTemplate.query(
							this.get_flow_by_verb_id_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 1){
			//throw new Exception("no flow  found");
			flow_list = new ArrayList<GpMicroFlow>();
		}
		//System.out.println("######### - In GpGenMicroFlowDao - find_by_verb_id is: " + flow_list.size() + " #######################");
		
		return flow_list;

	
	}

	@Override
	public GpMicroFlow find_by_id(long id) throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);

		GpMicroFlowMapper flow_mapper = new GpMicroFlowMapper();
		
		ArrayList<GpMicroFlow> flow_list = (ArrayList<GpMicroFlow>) this
						.namedParameterJdbcTemplate.query(
							this.get_flow_by_id_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 1){
			throw new Exception("no flow  found");
		}
		//System.out.println("######### - In GpGenMicroFlowDao - find_by_id is: " + flow_list.size() + " #######################");
		
		return flow_list.get(0);

	
	}
	@Override
	public ArrayList<GpMicroFlow> find_by_verb_id_and_component_type(long verb_id, String component_type)
			throws Exception {

		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("verb_id", verb_id);
		parameters.addValue("component_type", component_type);

		GpMicroFlowMapper flow_mapper = new GpMicroFlowMapper();
		
		ArrayList<GpMicroFlow> flow_list = (ArrayList<GpMicroFlow>) this
						.namedParameterJdbcTemplate.query(
							this.get_flow_by_verb_id_and_component_type_sql, parameters, flow_mapper);
		
		if(flow_list.size() < 0){
			//throw new Exception("no flow  found");
			flow_list = new ArrayList<GpMicroFlow>();
		}
		//System.out.println("######### - In GpGenMicroFlowDao - find_by_verb_id is: " + flow_list.size() + " #######################");
		
		return flow_list;

	
	}
	@Override
	public void update(GpMicroFlow flow) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long id) throws Exception {
		// TODO Auto-generated method stub

	}



	@Override
	public ArrayList<GpMicroFlow> find_microflow_by_component_name_and_base_verb_name(String base_verb_name,
			String component_name) throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("base_verb_name", base_verb_name);
		parameters.addValue("component_name", component_name);
		GpBaseMicroFlowMapper flow_mapper = new GpBaseMicroFlowMapper();
		
		ArrayList<GpMicroFlow> flow_list = (ArrayList<GpMicroFlow>) this
				.namedParameterJdbcTemplate.query(
					this.get_microflow_by_base_base_verb_name_and_component_name, parameters, flow_mapper);
		
		
		return flow_list;
	}

}
