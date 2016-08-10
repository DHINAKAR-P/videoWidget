package com.npb.gp.dao.mysql;

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

import com.npb.gp.dao.mysql.support.verbs.VerbsIdsMapper;
import com.npb.gp.dao.mysql.support.verbs.VerbsMapper;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.interfaces.dao.IGpVerbsDao;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 11/28/2014</br>
 * @since .75</p>  
 *
 *The purpose of this is to provide standard CRUD/search of the verbs table</p>
 *
 *
 * Modified Date: 02/24/2015</br>
 * Modified By:  Dan Castillo</p>
 * 
 * added the  get_verbs_by_activity_id method
 *
 *
 */
@Repository("GpVerbsDao")
public class GpVerbsDao implements IGpVerbsDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private GpScreenXDao screen_dao;
	
	@Value("${get_all_verbs.sql}")
	private String get_all_verbs_sql;
	
	@Value("${find_by_verb_id.sql}")
	private String find_by_verb_id_sql;
	

	@Value("${find_by_activity_id.sql}")
	private String find_by_activity_id_sql;
	
	@Value("${find_by_client_device_type.sql}")
	private String find_by_client_device_type;
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}
	@Resource(name="GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}
	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);    	
    }
    @Override
    public ArrayList<GpVerb> get_verbs_by_activity_id(long activity_id) throws Exception{
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		
		VerbsMapper verb_mapper = new VerbsMapper();
		
		
		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(this.find_by_activity_id_sql, parameters, verb_mapper);
		if(verb_list.size() < 1){
				verb_list = new ArrayList<GpVerb>(); 
			//throw new Exception("no verbs found");
		}
		//System.out.println("######### - In GpVerbsDao -  get_verbs_by_activity_id is: " + verb_list.size() + " #######################");
		
		return (ArrayList<GpVerb>) verb_list;

    }
    
    public List<GpVerb> get_other_verbs_by_activity_id(long activity_id, List<GpVerb> verbs) throws Exception{
    	List<Long> screens_ids = this.screen_dao.find_all_by_activity_id(activity_id);
    	if(screens_ids == null || screens_ids.isEmpty())
    		return null;
    	
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		
		String sql_in_clause = "";
		if(verbs != null && !verbs.isEmpty()){
			sql_in_clause += "(";
		}
		for(GpVerb verb : verbs){
			sql_in_clause += verb.getId() + ",";
		}
		if(verbs != null && !verbs.isEmpty()){
			sql_in_clause = sql_in_clause.substring(0,sql_in_clause.length()-1);
			sql_in_clause += ")";
		}
		
		String sql_like_clause = "";
		for(Long screen_id : screens_ids){
			sql_like_clause += "verbs.screen_id LIKE '%"+ screen_id +"%' OR ";
		}
		sql_like_clause = sql_like_clause.substring(0,sql_like_clause.length()-3);
		String query_stmt = "select verbs.id as VERB_ID, verbs.name as VERB_NAME, "
				+ "verbs.label as VERB_LABEL,  verbs.description as VERB_DESCRIPTION, "
				+ "verbs.notes as VERB_NOTES, verbs.action_on_data as VERB_ACTION_ON_DATA, "
				+ "verbs.activity_id as VERB_ACTIVITY_ID, verbs.authorizations as VERB_AUTHORIZATIONS, "
				+ "verbs.actual_information as VERB_INFO, "
				+ "verbs.screen_id as VERB_SCREEN_ID "
				+ "from geppetto.verbs ";
		if(!sql_like_clause.isEmpty()){
			query_stmt += "where ("+ sql_like_clause +") " ;
		}
		if(!sql_in_clause.isEmpty()){
			query_stmt += "AND (verbs.id not in " + sql_in_clause + ")";
		}
			
		//System.out.println("query_stmt " + query_stmt);
		VerbsMapper verb_mapper = new VerbsMapper();
		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(query_stmt, parameters, verb_mapper);
		//System.out.println("verb_list SIZE " + verb_list.size());
		return verb_list;
    }
    
    
    public List<Long> get_verbs_by_client_device_type(long activity_id, String client_device_type, String client_device_type_os_name) throws Exception{
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("activity_id", activity_id);
		parameters.addValue("client_device_type", client_device_type);
		parameters.addValue("client_device_type_os_name", client_device_type_os_name);
		//System.out.println(activity_id);
		//System.out.println(client_device_type);
		//System.out.println(client_device_type_os_name);
		
		List<Long> verb_list = this.namedParameterJdbcTemplate.queryForList(this.find_by_client_device_type, parameters, Long.class);
		
		return verb_list;

    }
    
	@Override
	public GpVerb find_by_id(long verb_id) throws Exception {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("verb_id", verb_id);

		VerbsMapper verb_mapper = new VerbsMapper();
		
		
		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(this.find_by_verb_id_sql, parameters, verb_mapper);
		if(verb_list.size() < 1){
		throw new Exception("no verbs found");
		}
		//System.out.println("######### - In GpVerbsDao -  find_by_id is: " + verb_list.size() + " #######################");
		
		
		return verb_list.get(0);

	}

	@Override
	public ArrayList<GpVerb> get_all_verbs() throws Exception {
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();

		VerbsMapper verb_mapper = new VerbsMapper();
		
		
		List<GpVerb> verb_list = this.namedParameterJdbcTemplate.query(this.get_all_verbs_sql, parameters, verb_mapper);
		if(verb_list.size() < 1){
		throw new Exception("no verbs found");
		}
		//System.out.println("######### - In GpVerbsDao -  get_all_verbs is: " + verb_list.size() + " #######################");
		
		return (ArrayList<GpVerb>) verb_list;
	}

}
