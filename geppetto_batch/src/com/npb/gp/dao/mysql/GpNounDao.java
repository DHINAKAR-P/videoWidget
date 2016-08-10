package com.npb.gp.dao.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.npb.gp.dao.mysql.support.noun.GpDto_noun_and_attributes;
import com.npb.gp.dao.mysql.support.noun.GpNoun_Mapper;
import com.npb.gp.dao.mysql.support.noun.InsertNoun;
import com.npb.gp.dao.mysql.support.noun.InsertNounAttribute;
import com.npb.gp.dao.mysql.support.noun.NounBaseMapper;
import com.npb.gp.dao.mysql.support.noun.Noun_with_attributes_mapper;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.interfaces.dao.IGpNounDao;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/08/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to interact with the db for the basic search</br>
 *and CRUD operations for a noun</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun"</p>
 *
 *
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex

 *
 */
@Repository("GpNounDao")
public class GpNounDao implements IGpNounDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private InsertNoun insert_noun;
	private InsertNounAttribute insert_noun_attribute;

	
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource) {
    	this.dataSource = dataSource;
    	
    	InsertNoun.SQL_INSERT_NOUN = " insert into nouns "
    			+ " (name, label, description, projectid, moduleid, "
    			+ " cache_enabled,  notes, "
    			+ " created_date, created_by,  last_modified_date, last_modified_by) values "
    			
    			+ " (:name, :label, :description, :projectid, :moduleid, " 
    			+ " :cache_enabled, :notes, "
    			+ " :created_date, :created_by, :last_modified_date, :last_modified_by)"; 
    	
    	
    	
    	InsertNounAttribute.SQL_INSERT_ATTRIBUTE = " insert into nounattributes "
    			+ " (name, label, description, nounid, type, "
    			+ " sub_type, sub_type_modifier, part_of_unique_key, "
    			+ " data_length, relationships,  notes, "
    			+ " created_date, created_by,  last_modified_date, last_modified_by) values "
    			
    			+ " (:name, :label, :description, :nounid, :type, " 
    			+ " :sub_type, :sub_type_modifier, :part_of_unique_key, "
    			+ " :data_length, :relationships, :notes, "
    			+ " :created_date, :created_by, :last_modified_date, :last_modified_by)";
    	
    	
    	
    	this.insert_noun = new InsertNoun(this.dataSource);
    	this.insert_noun_attribute = new InsertNounAttribute(this.dataSource);
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	
    }

    
    
	@Override
	public void insert(GpNoun anoun) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("name", anoun.getName());
        paramMap.put("label", anoun.getLabel());
        paramMap.put("description", anoun.getDescription());
        paramMap.put("projectid", anoun.getProjectid());

        paramMap.put("moduleid", anoun.getProjectid()); //add this to the model class
        paramMap.put("cache_enabled", "N");		//add this to the model class
        paramMap.put("notes", anoun.getNotes());
        paramMap.put("created_date", new Date());
        paramMap.put("created_by", anoun.getCreatedby());
        paramMap.put("last_modified_date", new Date());
        paramMap.put("last_modified_by", anoun.getLastmodifiedby());

		KeyHolder keyHolder = new GeneratedKeyHolder();
        this.insert_noun.updateByNamedParam(paramMap,keyHolder);
        anoun.setId(keyHolder.getKey().longValue());

		
	}

	@Override
	public void update(GpNoun anoun) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GpNoun anoun) {
		// TODO Auto-generated method stub

	}

	@Override
	public GpNoun find_by_id(long noun_id) throws Exception {
		// TODO Auto-generated method stub
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", noun_id);
		
		sql ="select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
			+ " nouns.description as NOUN_DESCRIPTION, nouns.projectid as NOUN_PROJECT_ID, "
			+ " nouns.moduleid as NOUN_MODULE_ID, nouns.cache_enabled as NOUN_CACHE_ENABLED, "
			+ " nouns.notes as NOUN_NOTES, nouns.created_by as NOUN_CREATED_BY, nouns.created_date as NOUN_CREATED_DATE, "
			+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, nouns.last_modified_date as NOUN_LAST_MODIFIED_DATE, "
			+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, "
			+ " nouns.default_activity_id as NOUN_DEFAULT_ACTIVITY_ID, "
			+ " nounattributes.id as NOUN_ATTRIBUTE_ID, nounattributes.name as NOUN_ATTRIBUTE_NAME, "
			+ " nounattributes.nounid as NOUN_ATTRIBUTE_NOUN_ID, "
			+ " nounattributes.label as NOUN_ATTRIBUTE_LABEL, nounattributes.description as NOUN_ATTRIBUTE_DESCRIPTION, "
			+ " base_noun_attr_types.type as NOUN_ATTRIBUTE_TYPE, base_noun_attr_types.sub_type as NOUN_ATTRIBUTE_SUB_TYPE, "
			+ " base_noun_attr_types.sub_type_modifier as NOUN_ATTRIBUTE_SUB_TYPE_MODIFIER, "
			+ " nounattributes.part_of_unique_key as NOUN_ATTRIBUTE_PART_OF_UNIQUE_KEY, "
			+ " nounattributes.data_length as NOUN_ATTRIBUTE_DATA_LENGTH, "
			+ " nounattributes.relationships as NOUN_ATTRIBUTE_RELATIONSHIPS, "
			+ " nounattributes.notes as NOUN_ATTRIBUTE_NOTES, "
			+ " nounattributes.created_by as NOUN_ATTRIBUTE_CREATED_BY, nounattributes.created_date as NOUN_ATTRIBUTE_CREATED_DATE, "
			+ " nounattributes.last_modified_by as NOUN_ATTRIBUTE_LAST_MODIFIED_BY, "
			+ " nounattributes.last_modified_date as NOUN_ATTRIBUTE_LAST_MODIFIED_DATE "
			+ " from geppetto.nouns "
			+ " left join geppetto.nounattributes on geppetto.nouns.id = geppetto.nounattributes.nounid "
			+ " join geppetto.base_noun_attr_types on geppetto.nounattributes.base_attr_type_id = geppetto.base_noun_attr_types.id "
			+ " where geppetto.nouns.id=:id"; 


		Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
		List<GpDto_noun_and_attributes> dto_list = this.namedParameterJdbcTemplate.query(sql, parameters, noun_and_attribute_mapper);
		if(dto_list.size() < 1){
			throw new Exception("noun_id number " + noun_id + " was not found");
		}
		GpDto_noun_and_attributes test = new GpDto_noun_and_attributes();
		 
		
		return test.get_noun_from_result_set(dto_list);
	}

	
	@Override
	public List<GpNoun> find_all_base_by_projectid(long projectid)
			throws Exception {
		System.out.println("In GpNounDao find_all_base_by_projectid -1 ");
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);

		sql ="select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
				+ " nouns.description as NOUN_DESCRIPTION, nouns.projectid as NOUN_PROJECT_ID, "
				+ " nouns.moduleid as NOUN_MODULE_ID, nouns.cache_enabled as NOUN_CACHE_ENABLED, "
				+ " nouns.notes as NOUN_NOTES, nouns.created_by as NOUN_CREATED_BY, nouns.created_date as NOUN_CREATED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, nouns.last_modified_date as NOUN_LAST_MODIFIED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY "
				+ " from geppetto.nouns "
				+ " where nouns.projectid = :projectid";

		//NounBaseMapper
		NounBaseMapper noun_base_mapper = new NounBaseMapper();
		List<GpNoun> the_noun_list = this.namedParameterJdbcTemplate.query(sql, parameters, noun_base_mapper);
		if(the_noun_list.size() < 1){
			return new ArrayList<GpNoun>();
		}

		return the_noun_list;
	}

	
	@Override
	public ArrayList<GpNoun> find_by_project_id(long project_id) throws Exception {
		
		String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", project_id);
		/*
		sql = " select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
			+ " nouns.description as NOUN_DESCRIPTION  "
			+ " from geppetto.nouns "
			+ " where  nouns.projectid =  :id";
		*/
		
		sql ="select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
				+ " nouns.description as NOUN_DESCRIPTION, nouns.projectid as NOUN_PROJECT_ID, "
				+ " nouns.moduleid as NOUN_MODULE_ID, nouns.cache_enabled as NOUN_CACHE_ENABLED, "
				+ " nouns.notes as NOUN_NOTES, nouns.created_by as NOUN_CREATED_BY, nouns.created_date as NOUN_CREATED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, nouns.last_modified_date as NOUN_LAST_MODIFIED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, "
				+ " nouns.default_activity_id as NOUN_DEFAULT_ACTIVITY_ID, "
				+ " nounattributes.id as NOUN_ATTRIBUTE_ID, nounattributes.name as NOUN_ATTRIBUTE_NAME, "
				+ " nounattributes.nounid as NOUN_ATTRIBUTE_NOUN_ID, "
				+ " nounattributes.label as NOUN_ATTRIBUTE_LABEL, nounattributes.description as NOUN_ATTRIBUTE_DESCRIPTION, "
				+ " base_noun_attr_types.type as NOUN_ATTRIBUTE_TYPE, base_noun_attr_types.sub_type as NOUN_ATTRIBUTE_SUB_TYPE, "
				+ " base_noun_attr_types.sub_type_modifier as NOUN_ATTRIBUTE_SUB_TYPE_MODIFIER, "
				+ " nounattributes.part_of_unique_key as NOUN_ATTRIBUTE_PART_OF_UNIQUE_KEY, "
				+ " nounattributes.data_length as NOUN_ATTRIBUTE_DATA_LENGTH, "
				+ " nounattributes.relationships as NOUN_ATTRIBUTE_RELATIONSHIPS, "
				+ " nounattributes.notes as NOUN_ATTRIBUTE_NOTES, "
				+ " nounattributes.created_by as NOUN_ATTRIBUTE_CREATED_BY, nounattributes.created_date as NOUN_ATTRIBUTE_CREATED_DATE, "
				+ " nounattributes.last_modified_by as NOUN_ATTRIBUTE_LAST_MODIFIED_BY, "
				+ " nounattributes.last_modified_date as NOUN_ATTRIBUTE_LAST_MODIFIED_DATE "
				+ " from geppetto.nouns "
				+ " left join geppetto.nounattributes on geppetto.nouns.id = geppetto.nounattributes.nounid "
				+ " join geppetto.base_noun_attr_types on geppetto.nounattributes.base_attr_type_id = geppetto.base_noun_attr_types.id "
				+ " where geppetto.nouns.projectid=:id "; 

		
		
		Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
		List<GpDto_noun_and_attributes> the_noun_attrib_list = this.namedParameterJdbcTemplate.query(sql, parameters, noun_and_attribute_mapper);
		if(the_noun_attrib_list.size() < 1){
			System.out.println("nouns not found for project id:  " + project_id);
		}

		GpDto_noun_and_attributes test = new GpDto_noun_and_attributes();

		return test.get_noun_list_from_resultset(the_noun_attrib_list);
	}

	@Override
	public void insert_a_noun_attribute(GpNounAttribute anttribute,
												long noun_id, long user_id) {

		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("name", anttribute.getName());
        paramMap.put("label", anttribute.getLabel());
        paramMap.put("description", anttribute.getDescription());
        paramMap.put("nounid", anttribute.getNounid());
        paramMap.put("type", anttribute.getType());
        paramMap.put("sub_type", anttribute.getSubtype());
        paramMap.put("sub_type_modifier", anttribute.getSubtypemodifiervalue());
        paramMap.put("part_of_unique_key", anttribute.isIspart_of_unique_key());
        paramMap.put("data_length", anttribute.getDatalength());
        paramMap.put("relationships", ""); //add this to the class
        
        
        paramMap.put("notes", anttribute.getNotes());
        paramMap.put("created_date", new Date());
        paramMap.put("created_by", anttribute.getCreatedby());
        paramMap.put("last_modified_date", new Date());
        paramMap.put("last_modified_by", anttribute.getLastmodifiedby());

		KeyHolder keyHolder = new GeneratedKeyHolder();
        this.insert_noun_attribute.updateByNamedParam(paramMap,keyHolder);
        anttribute.setId(keyHolder.getKey().longValue());


	}




}
