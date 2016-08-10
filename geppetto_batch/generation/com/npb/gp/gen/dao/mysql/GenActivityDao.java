package com.npb.gp.gen.dao.mysql;


import java.util.ArrayList;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;




import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.interfaces.dao.IGpActivityDao;
//import com.npb.gp.dao.mysql.GpActivityTestDao.NounMapper;
//import com.npb.gp.dao.mysql.GpActivityTestDao.NounMapper;
import com.npb.gp.dao.mysql.support.activity_gen.*;
import com.npb.gp.dao.mysql.support.noun.GpDto_noun_and_attributes;
import com.npb.gp.dao.mysql.support.noun.Noun_with_attributes_mapper;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 02/26/2014</br>
 * @since .35</p>  
 *
 *The purpose of this class is to interact with the db for the basic search</br>
 *and CRUD operations for an activity</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun", back then Activity</br
 *used to be known as operation</p>
 *
 *
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex

 *
 *
 */
@Repository("GenActivityDao")
public class GenActivityDao implements IGpActivityDao {

	private Log log = LogFactory.getLog(getClass());
	private DataSource dataSource;
	
	@Value("${xfind_all_base_by_projectid.sql}")
	private String xfind_all_base_by_projectid_sql;

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private InsertActivity insert_activity;
	private UpdateActivity update_activity;
	//private ActivitySearch activity_search;

	    @Resource(name="dataSource")
	    public void setDataSource(DataSource dataSource) {
	        this.dataSource = dataSource;
	        UpdateActivity.SQL_UPDATE_ACTIVITY = "update activities set"
	        	+ " name=:name, label=:label, description=:description,"
	        	+ " projectid=:projectid, moduleid=:moduleid, notes=:notes, "
	        	+ " primary_noun_id=:primary_noun_id, secondary_nouns=:secondary_nouns,"
	        	+ " activity_types=:activity_types,   last_modified_date=:last_modified_date,"
	        	+ " last_modified_by=:last_modified_by where id=:id" ;

	        InsertActivity.SQL_INSERT_ACTIVITY = "insert into activities "
		        	+ " (name, label, description, projectid, moduleid, notes, "
		        	+ " primary_noun_id, secondary_nouns, activity_types, "
		        	+ "created_date, created_by, last_modified_date,"
		        	+ " last_modified_by) values " 

		        	+ " (:name, :label, :description, :projectid, :moduleid, :notes," 
					+ " :primary_noun_id, :secondary_nouns, :activity_types, "
					+ " :created_date, :created_by, :last_modified_date,"
					+ " :last_modified_by)";
 
	        
	        this.insert_activity = new InsertActivity(this.dataSource);
	        this.update_activity = new UpdateActivity(this.dataSource);
	        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }

	    public DataSource getDataSource() {
	        return dataSource;
	    }
	
	//get rid of this - since you always pull the screens
	//well you will have non-visible - call it screens for now
	//these will be objects that you need to communicate with services
	//or do logic - or run in the background - see the Android non
	public void find_activites_with_screens(long activityid){
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", activityid);
		//String sql = "select * from geppetto.nouns where id IN(:ids)";
		String sql = "select activities.id as ACTIVITY_ID, activities.name ACTIVITY_NAME, activities.label ACTIVITY_LABEL,"
		+ " geppetto.screens.id as SCREEN_ID, geppetto.screens.label as SCREEN_LABEL from geppetto.activities"
		+ " left join geppetto.screens on geppetto.activities.id = geppetto.screens.activityid WHERE activities.id IN "
		+ " (SELECT id FROM geppetto.activities WHERE id = :id)"; 
		
		
		ActivityWithScreensMapper the_mapper = new ActivityWithScreensMapper();
		List<GpDtoActivity_and_Screens> the_activity_list = this.namedParameterJdbcTemplate.query(sql, parameters, the_mapper);
		System.out.println("In find_activites_with_screens the_activity_list.size() is: " + the_activity_list.size());
		
	}
	@Override
	public ArrayList<GpActivity> find_all_base_by_projectid(long projectid) throws Exception{
		//System.out.println("In find_all_base_by_projectid -1");
		//String sql;
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		/*
		sql =
			"select geppetto.activities.id as ACTIVITY_ID, geppetto.activities.name as ACTIVITY_NAME, "
					+ " geppetto.activities.projectid as PROJECT_ID, "
					+ " geppetto.activities.label ACTIVITY_LABEL, geppetto.activities.description as ACTIVITY_DESCRIPTION, "
					+ " geppetto.activities.secondary_nouns as ACTIVITY_SECONDARY_NOUNS," 
					+ " geppetto.activities.primary_noun_id as ACTIVITY_PRIMARY_NOUN_ID, "
					+ " geppetto.activities.moduleid as MODULE_ID, "
					+ " geppetto.activities.notes as NOTES, "
					+ " geppetto.activities.primary_noun_id as PRIMARY_NOUN_ID, "
					+ " geppetto.activities.activity_types as ACTIVITY_TYPES, "

					+ " geppetto.activities.created_date as CREATE_DATE, "
					+ " geppetto.activities.created_by as CREATED_BY, "
					+ " geppetto.activities.last_modified_date as LAST_MODIFIED_DATE, "
					+ " geppetto.activities.last_modified_by as LAST_MODIFIED_BY "
					
					+ " from geppetto.activities "
					+ " where projectid = :projectid"
					; 
						
		*/
		GenActivityBaseMapper the_mapper = new GenActivityBaseMapper();

		//System.out.println("$$$$$$$$$$$$$$ this.xfind_all_base_by_projectid_sql is: " + this.xfind_all_base_by_projectid_sql);
		
		ArrayList<GpActivity> dto_list = (ArrayList<GpActivity>) this.namedParameterJdbcTemplate.query(this.xfind_all_base_by_projectid_sql, parameters, the_mapper);
		//System.out.println("In find_all_base_by_projectid - dto_list is: " + dto_list.size());
		if(dto_list.size() < 1){
			throw new Exception("no activities for project id   " + projectid + " were found");
			//throw new Exception("no activities for project were found", new Throwable("99"));
		}

		
		return dto_list;
	}
	
	@Override
	public ArrayList<GpActivity> find_all_by_projectid(long projectid) throws Exception {
		// TODO Auto-generated method stub
		
		String sql;
		MapSqlParameterSource parameters;
		/** handle secondary nouns - screens  */
		parameters = new MapSqlParameterSource();
		parameters.addValue("projectid", projectid);
		sql = 
				
		"select geppetto.activities.id as ACTIVITY_ID, geppetto.activities.name as ACTIVITY_NAME, "
		+ " geppetto.activities.label ACTIVITY_LABEL, geppetto.activities.description as ACTIVITY_DESCRIPTION, "
		
		+ " geppetto.activities.secondary_nouns as ACTIVITY_SECONDARY_NOUNS," 
		+ " geppetto.activities.primary_noun_id as ACTIVITY_PRIMARY_NOUN_ID, "

		+ " geppetto.screens.id as SCREEN_ID, geppetto.screens.label as SCREEN_LABEL," 		
		+ " geppetto.screens.name as SCREEN_NAME, geppetto.screens.description as SCREEN_DESCRIPTION "

		+ " from geppetto.activities "
		+ " left join geppetto.screens on geppetto.activities.id = geppetto.screens.activityid WHERE activities.id IN "
		+ " (SELECT id FROM geppetto.activities WHERE projectid = :projectid)"; 
		
		
		ActivityWithScreensMapper_Min activity_and_screen_mapper = new ActivityWithScreensMapper_Min();
		List<GpDtoActivity_and_Screens> dto_list = this.namedParameterJdbcTemplate.query(sql, parameters, activity_and_screen_mapper);
		if(dto_list.size() < 1){
			//throw new Exception("no activities for project id   " + projectid + " were found");
			throw new Exception("no activities for project were found", new Throwable("99"));
		}
		
		//System.out.println("In find_by_id the_activity_list.size() is: " + dto_list.size());
		
		ArrayList<GpActivity> activity_list = new ArrayList<GpActivity>(); 
		GpDtoActivity_and_Screens current_dto = new GpDtoActivity_and_Screens();
		for(GpDtoActivity_and_Screens a_dto: dto_list){
			if(current_dto.getName() == null){
				current_dto.setName(a_dto.getName());	
			}
			if(!current_dto.getName().equals(a_dto.getName())){  //you have a new  activity
				current_dto.setName(a_dto.getName());
			}
			GpActivity the_activity = new GpActivity();
			the_activity.setId(a_dto.getId());
			the_activity.setName(a_dto.getName());
			the_activity.setLabel(a_dto.getLabel());
			the_activity.setDescription(a_dto.getDescription());
			activity_list.add(the_activity);
			
		}
		
		return activity_list;
	}

	@Override
	public GpActivity find_by_id(long activity_id) throws Exception {
		
		//System.out.println("The activity_id is: " + activity_id);
		
		String sql;
		MapSqlParameterSource parameters;
		/** handle secondary nouns - screens  */
		parameters = new MapSqlParameterSource();
		parameters.addValue("id", activity_id);
		sql = 
		"select activities.id as ACTIVITY_ID, activities.name ACTIVITY_NAME,"
		+ " activities.label ACTIVITY_LABEL, activities.description, "
		+ "activities.secondary_nouns, activities.activity_types, activities.primary_noun_id," 
		+ " activities.notes, activities.projectid, activities.created_date, activities.created_by, " 
		+ "activities.last_modified_date, activities.last_modified_by, "
		+ " geppetto.screens.id as SCREEN_ID, geppetto.screens.label as SCREEN_LABEL," 
		+ " geppetto.screens.name as SCREEN_NAME, geppetto.screens.description as SCREEN_DESCRIPTION,"
		+ " geppetto.screens.client_device_type as SCREEN_CLIENT_DEVICE_TYPE, "
		+ " geppetto.screens.client_device_type_label as SCREEN_CLIENT_DEVICE_TYPE_LABEL, "
		+ " geppetto.screens.client_device_type_os_name as SCREEN_CLIENT_DEVICE_TYPE_OS_NAME, "
		+ " geppetto.screens.human_language_id as HUMAN_LANGUAGE_ID from geppetto.activities"
		+ " left join geppetto.screens on geppetto.activities.id = geppetto.screens.activityid WHERE activities.id IN "
		+ " (SELECT id FROM geppetto.activities WHERE id = :id)"; 
		
		
		ActivityWithScreensMapper activity_and_screen_mapper = new ActivityWithScreensMapper();
		List<GpDtoActivity_and_Screens> dto_list = this.namedParameterJdbcTemplate.query(sql, parameters, activity_and_screen_mapper);
		if(dto_list.size() < 1){
			throw new Exception("activity id number " + activity_id + " was not found");
		}
		
		//System.out.println("In find_by_id the_activity_list.size() is: " + dto_list.size());
		
		
		
		
	 //OJO HERE YOU SHOULD PREPARE THE GpBaseActivity off of the   dto_list.get(0)
		GpActivity the_activity = new GpActivity();
		//ArrayList<GpScreen> screen_list = new ArrayList<GpScreen>(); 
		ArrayList<GpScreen> phone_screen_list = new ArrayList<GpScreen>();
		ArrayList<GpScreen> tablet_screen_list = new ArrayList<GpScreen>();
		ArrayList<GpScreen> pc_screen_list = new ArrayList<GpScreen>();
		
		//the_activity.setThescreens(screen_list);
		the_activity.setPhone_screens(phone_screen_list);
		the_activity.setTablet_screens(tablet_screen_list);
		the_activity.setPc_screens(pc_screen_list);
		
		String[] secondary_noun_ids = null;
		long primary_noun_id = 0;
		//GpBaseActivity the_activity = dto_list.get(0); --> you cant to this
 		 int i=0;
		for(GpDtoActivity_and_Screens a_dto: dto_list){
			if(i == 0){
				//GpBaseActivity the_activity = new GpBaseActivity();
				the_activity.setId(a_dto.getId());
				the_activity.setName(a_dto.getName());
				the_activity.setLabel(a_dto.getLabel());
				the_activity.setDescription(a_dto.getDescription());

				String activity_types[] = GpGenericRecordParserBuilder.parseDelimitedString(a_dto.getActivity_types());
				ArrayList<String> types = new ArrayList<String>();
				for(String type : activity_types){
					types.add(type);
				}
				the_activity.setActivity_types(types);
				
				//System.out.println("$$$$$$$$$$$$$$$  a_dto.getPrimary_noun_id() is: " + a_dto.getPrimary_noun_id());
				//if(a_dto.getPrimary_noun_id() > 0){
			//		GpNoun temp_noun = new GpNoun();
			//		temp_noun.setId(a_dto.getPrimary_noun_id());
					
				//}
				//the_activity.setPrimary_noun_id(a_dto.getPrimary_noun_id());
				primary_noun_id = a_dto.getPrimary_noun_id();
				the_activity.setNotes(a_dto.getNotes());
				the_activity.setCreatedate(a_dto.getCreated_date());
				the_activity.setCreatedby(a_dto.getCreated_by());
				the_activity.setLastmodifieddate(a_dto.getLast_modified_date());
				the_activity.setLastmodifiedby(a_dto.getLast_modified_by());
				the_activity.setProjectid(a_dto.getProjectid());

				if(a_dto.getSecondary_nouns().length() > 0){
					secondary_noun_ids = GpGenericRecordParserBuilder.parseDelimitedString(a_dto.getSecondary_nouns());	
				}
			
				
				
			}
			if(a_dto.getScreen_id() !=0){
				GpScreen a_screen = new GpScreen();
				a_screen.setId(a_dto.getScreen_id());
				a_screen.setLabel(a_dto.getScreen_label());
				a_screen.setName(a_dto.getScreen_name());
				a_screen.setDescription(a_dto.getScreen_description());
				a_screen.setClient_device_type(a_dto.getScreen_client_device_type());
				a_screen.setClient_device_type_label(a_dto.getScreen_client_device_type_label());
				a_screen.setClient_device_type_os_name(a_dto.getScreen_client_device_type_os_name());
				a_screen.setHuman_language_id(a_dto.getScreen_human_language_id());
				
				if(a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Phone)){	
					phone_screen_list.add(a_screen);	
				}else if(a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Tablet)){
					tablet_screen_list.add(a_screen);				
				}else if(a_screen.getClient_device_type().equals(GpBaseConstants.GpDeviceType_Pc)){
					pc_screen_list.add(a_screen);
				}
			}
			
			i++;
			/*
			System.out.println("\n a_screen.getId() is: " + a_screen.getId()
					+"\n a_screen.getLabel() is: " + a_screen.getLabel()
					+ "\n a_screen.getName() is: " + a_screen.getName()
					+ "\n a_screen.getDescription() is: " + a_screen.getDescription()
					+ "\n a_screen.getClient_device_type() is: " + a_screen.getClient_device_type()
					+ "\n a_screen.getClient_device_type_label() is: " + a_screen.getClient_device_type_label()
					+ "\n  a_screen.getClient_device_type_os_name() is: " + a_screen.getClient_device_type_os_name()
					+ "\n a_screen.getHuman_language_id() is: " + a_screen.getHuman_language_id());
		*/
		}
	
		/** handle primary and secondary nouns */
		/*  OJO DONT DO THIS IF THE    the_activity_list.get(0).getSecondary_nouns().length() <= 0* --> THIS MEANS THAT THERE ARE NO SECONDARY NOUNS */
		//System.out.println("*************************************** secondary_noun_ids is:  " + secondary_noun_ids);
		ArrayList<Long> nounids = new ArrayList<Long>(); //these have to come from the secondary noun field above
		if(primary_noun_id > 0){
			//System.out.println("$$$$$$$$$ ADDING THE PRIMARY NOUNID " + primary_noun_id);
			nounids.add(new Long(primary_noun_id)); //you always want to get the primary noun		
		}
		
		//System.out.println("$$$$$$$$$ BEFORE  secondary_noun_ids - nounids.size() IS: " + nounids.size());
		if(secondary_noun_ids != null){
			for(String noun_id : secondary_noun_ids){
				nounids.add(new Long(noun_id));
			}
		}
		
		//System.out.println("$$$$$$$$$ BEFORE  final check - nounids.size() IS: " + nounids.size());
		if(nounids.size() == 0){
			System.out.println("there are no nouns - going to return");
			the_activity.setSecondary_nouns(new ArrayList<GpNoun>(
												new ArrayList<GpNoun>()));
			return the_activity;
		}
		
		
		parameters = new MapSqlParameterSource();
		parameters.addValue("ids", nounids);
		
		sql ="select nouns.id as NOUN_ID, nouns.name as NOUN_NAME, nouns.label as NOUN_LABEL,"
				+ " nouns.description as NOUN_DESCRIPTION, nouns.projectid as NOUN_PROJECT_ID, "
				+ " nouns.moduleid as NOUN_MODULE_ID, nouns.cache_enabled as NOUN_CACHE_ENABLED, "
				+ " nouns.notes as NOUN_NOTES, nouns.created_by as NOUN_CREATED_BY, nouns.created_date as NOUN_CREATED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, nouns.last_modified_date as NOUN_LAST_MODIFIED_DATE, "
				+ " nouns.last_modified_by as NOUN_LAST_MODIFIED_BY, "
				+ " nounattributes.id as NOUN_ATTRIBUTE_ID, nounattributes.name as NOUN_ATTRIBUTE_NAME, nounattributes.nounid as NOUN_ATTRIBUTE_NOUN_ID, "
				+ " nounattributes.label as NOUN_ATTRIBUTE_LABEL, nounattributes.description as NOUN_ATTRIBUTE_DESCRIPTION, "
				+ " nounattributes.type as NOUN_ATTRIBUTE_TYPE, nounattributes.sub_type as NOUN_ATTRIBUTE_SUB_TYPE, "
				+ " nounattributes.sub_type_modifier as NOUN_ATTRIBUTE_SUB_TYPE_MODIFIER, "
				+ " nounattributes.part_of_unique_key as NOUN_ATTRIBUTE_PART_OF_UNIQUE_KEY, "
				+ " nounattributes.data_length as NOUN_ATTRIBUTE_DATA_LENGTH, "
				+ " nounattributes.relationships as NOUN_ATTRIBUTE_RELATIONSHIPS, "
				+ " nounattributes.notes as NOUN_ATTRIBUTE_NOTES, "
				+ " nounattributes.created_by as NOUN_ATTRIBUTE_CREATED_BY, nounattributes.created_date as NOUN_ATTRIBUTE_CREATED_DATE, "
				+ " nounattributes.last_modified_by as NOUN_ATTRIBUTE_LAST_MODIFIED_BY, "
				+ " nounattributes.last_modified_date as NOUN_ATTRIBUTE_LAST_MODIFIED_DATE "
				+ " from geppetto.nouns "
				+ " left join geppetto.nounattributes on geppetto.nouns.id = geppetto.nounattributes.nounid "
				+ " where geppetto.nouns.id in (select id from geppetto.nouns where id IN (:ids)) "; 


			Noun_with_attributes_mapper noun_and_attribute_mapper = new Noun_with_attributes_mapper();
			List<GpDto_noun_and_attributes> the_noun_attrib_list = this.namedParameterJdbcTemplate.query(sql, parameters, noun_and_attribute_mapper);
			if(the_noun_attrib_list.size() < 1){
				throw new Exception("nouns not found for activity ");
			}else{
				System.out.println("&&&&&&&&&&&&& BINGO! &&&&&&&&&&&&&&&&&&&&"
						+ "\n the size of the nounids is: " + nounids.size());
			}
		
			ArrayList<GpNoun> the_noun_list  = new ArrayList<GpNoun>();
			ArrayList<Long> processed = new ArrayList<Long>();
			GpNoun current_noun = null;
			boolean found = false;
			//looking and adding distinct nouns
			for(GpDto_noun_and_attributes noun_and_attribs : the_noun_attrib_list){
				found = false;
				for(Long an_id : processed){
					if(noun_and_attribs.getNoun_id() == an_id.longValue()){
						found = true;
					} 
					
				}
				if(!found){
					current_noun = this.noun_processing(noun_and_attribs);
					processed.add(new Long((current_noun.getId())));
					//if(current_noun.getId() == primary_noun_id){
					//	the_activity.setPrimary_noun(current_noun);
					//}else{
						the_noun_list.add(current_noun);	
					//}
					
					found = false;
				}
				
			}
			
			
			//System.out.println("the size of the_noun_list is: " + the_noun_list.size());
			//now handle the noun attributes
			for(GpNoun a_noun : the_noun_list){
				ArrayList<GpNounAttribute> noun_attribs = 
										new ArrayList<GpNounAttribute>();
				for(GpDto_noun_and_attributes noun_and_attribs : the_noun_attrib_list){
					if(noun_and_attribs.getNoun_id() == a_noun.getId()){
						if(noun_and_attribs.getAttribute_id() > 0){
							GpNounAttribute attrib = this.attribute_processing(noun_and_attribs);
							noun_attribs.add(attrib);
						}
					}
				}
				a_noun.setNounattributes(noun_attribs);
			}
			
			/* wow what a HACK! */
			ArrayList<GpNoun> secondary_nouns = new ArrayList<GpNoun>();
			for(GpNoun a_noun : the_noun_list){
				if(a_noun.getId() == primary_noun_id){
					the_activity.setPrimary_noun(current_noun);
				}else{
					secondary_nouns.add(a_noun);
				}
			}
			the_activity.setSecondary_nouns(new ArrayList<GpNoun>(secondary_nouns));
			
			
		return the_activity;
	}

	@Override
	public void insert(GpActivity activity) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("name", activity.getName());
        paramMap.put("label", activity.getLabel());
        paramMap.put("description", activity.getDescription());
        paramMap.put("projectid", activity.getProjectid());
        paramMap.put("moduleid", activity.getModuleid());
        paramMap.put("notes", activity.getNotes());
        
        
        String the_types = GpGenericRecordParserBuilder.buildDelimitedString(activity.getActivity_types());
        paramMap.put("activity_types", the_types);
        
        String secondary_nouns = "";
        ArrayList<String> noun_id_strings = new ArrayList<String>();
       
        
        if(activity.getSecondary_nouns().size() > 0){
           
           for(GpNoun anoun : activity.getSecondary_nouns()){
           	String a_string = new Long(anoun.getId()).toString();
           	noun_id_strings.add(a_string);
           }
           secondary_nouns = GpGenericRecordParserBuilder.buildDelimitedString(noun_id_strings);
       }
        
        paramMap.put("secondary_nouns", secondary_nouns);
        paramMap.put("primary_noun_id", activity.getPrimary_noun().getId());
        paramMap.put("created_date", new Date());
        paramMap.put("created_by", activity.getCreatedby());
        paramMap.put("last_modified_date", new Date());
        paramMap.put("last_modified_by", activity.getLastmodifiedby());
        
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.insert_activity.updateByNamedParam(paramMap,keyHolder);
        activity.setId(keyHolder.getKey().longValue());
        //System.out.println("The activity id is: " + activity.getId());
        //log.info("New contact inserted with id: " + contact.getId());
	}

	@Override
	public void update(GpActivity activity) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", activity.getId());
		paramMap.put("name", activity.getName());
        paramMap.put("label", activity.getLabel());
        paramMap.put("description", activity.getDescription());
        paramMap.put("projectid", activity.getProjectid());
        paramMap.put("moduleid", activity.getModuleid());
        paramMap.put("notes", activity.getNotes());
        
        
        String the_types = GpGenericRecordParserBuilder.buildDelimitedString(activity.getActivity_types());
        paramMap.put("activity_types", the_types);
        
        ArrayList<String> noun_id_strings = new ArrayList<String>(); 
        
        for(GpNoun anoun : activity.getSecondary_nouns()){
        	String a_string = new Long(anoun.getId()).toString();
        	noun_id_strings.add(a_string);
        }
        String secondary_nouns = GpGenericRecordParserBuilder.buildDelimitedString(noun_id_strings);
        
        paramMap.put("secondary_nouns", secondary_nouns);
        paramMap.put("primary_noun_id", activity.getPrimary_noun().getId());
        paramMap.put("last_modified_date", new Date());
        paramMap.put("last_modified_by", activity.getLastmodifiedby());
        
        
        this.update_activity.updateByNamedParam(paramMap);

	}

	@Override
	public void delete(long activity_id) {
		// TODO Auto-generated method stub

	}

	private GpNoun noun_processing(GpDto_noun_and_attributes noun_and_attribs){
		GpNoun noun = new GpNoun();
		ArrayList<GpNounAttribute> noun_attribs = 
				new ArrayList<GpNounAttribute>();

		
		noun.setId(noun_and_attribs.getNoun_id());
		noun.setName(noun_and_attribs.getNoun_name());
		noun.setDescription(noun_and_attribs.getNoun_description());
		noun.setLabel(noun_and_attribs.getNoun_label());
		noun.setNotes(noun_and_attribs.getNoun_notes());

		noun.setCreatedate(noun_and_attribs.getNoun_createdate());
		noun.setCreatedby(noun_and_attribs.getNoun_createdby());
		noun.setLastmodifieddate(noun_and_attribs.getNoun_lastmodifieddate());
		noun.setLastmodifiedby(noun_and_attribs.getNoun_lastmodifiedby());
		noun.setModuleid(noun_and_attribs.getNoun_moduleid()); //this is probably superfluous but it might make things easier later
		noun.setProjectid(noun_and_attribs.getNoun_projectid()); //this is probably superfluous but it might make things easier later
		noun.setNounattributes(noun_attribs);


		
		return noun;
	}
	
	private GpNounAttribute attribute_processing(GpDto_noun_and_attributes noun_and_attribs){
		
		GpNounAttribute noun_attrib = new GpNounAttribute();
		
		noun_attrib.setId(noun_and_attribs.getAttribute_id());
		noun_attrib.setNounid(noun_and_attribs.getAttribute_nounid());
		noun_attrib.setName(noun_and_attribs.getAttribute_name());
		noun_attrib.setLabel(noun_and_attribs.getAttribute_label());
		noun_attrib.setDescription(noun_and_attribs.getAttribute_description());
		noun_attrib.setDatalength(noun_and_attribs.getAttribute_data_length());
		noun_attrib.setType(noun_and_attribs.getAttribute_type());
		noun_attrib.setSubtype(noun_and_attribs.getAttribute_sub_type());
		noun_attrib.setSubtypemodifiervalue(noun_and_attribs
								.getAttribute_sub_type_modifier());
		noun_attrib.setNotes(noun_and_attribs.getAttribute_notes());
		//noun_attrib_dto.getAttribute_relationships(); --> you dont have this in the core model yet 4/29/2014
		
		noun_attrib.setCreatedate(noun_and_attribs.getAttribute_createdate());
		noun_attrib.setCreatedby(noun_and_attribs.getAttribute_createdby());
		noun_attrib.setLastmodifieddate(noun_and_attribs
								.getAttribute_lastmodifieddate());
		
		noun_attrib.setLastmodifiedby(noun_and_attribs
								.getAttribute_lastmodifiedby());

		
		
		return noun_attrib;
		
	}
	
}
