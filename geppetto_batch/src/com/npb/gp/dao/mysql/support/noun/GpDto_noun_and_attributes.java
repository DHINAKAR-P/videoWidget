package com.npb.gp.dao.mysql.support.noun;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNoun;

public class GpDto_noun_and_attributes {

	/* NOUN  START */
	private long noun_id;
	private String noun_name;
	private String noun_description;
	private String noun_label;
	private long noun_projectid;
	private long noun_moduleid;
	private boolean noun_cache_enabled;
	
	private String noun_notes;
	private Date noun_createdate;
	private long noun_createdby;
	private Date noun_lastmodifieddate;
	private long noun_lastmodifiedby;
	private long noun_default_activity_id;

	
	
	
	
	
	/* ATTRIBUTES START  */
	private long attribute_id;
	private long attribute_nounid;
	private String attribute_name;
	private String attribute_description;
	private String attribute_label;
	private String attribute_type;
	private String attribute_sub_type;
	private String attribute_sub_type_modifier;
	private boolean attribute_part_of_unique_key;
	private String attribute_data_length;
	private String attribute_relationships;
	
	
	
	private String attribute_notes;
	private Date attribute_createdate;
	private long attribute_createdby;
	private Date attribute_lastmodifieddate;
	private long attribute_lastmodifiedby;
	
	
	public long getNoun_default_activity_id() {
		return noun_default_activity_id;
	}
	public void setNoun_default_activity_id(long noun_default_activity_id) {
		this.noun_default_activity_id = noun_default_activity_id;
	}
	public long getNoun_id() {
		return noun_id;
	}
	public void setNoun_id(long noun_id) {
		this.noun_id = noun_id;
	}
	public String getNoun_name() {
		return noun_name;
	}
	public void setNoun_name(String noun_name) {
		this.noun_name = noun_name;
	}
	public String getNoun_description() {
		return noun_description;
	}
	public void setNoun_description(String noun_description) {
		this.noun_description = noun_description;
	}
	public String getNoun_label() {
		return noun_label;
	}
	public void setNoun_label(String noun_label) {
		this.noun_label = noun_label;
	}
	public long getNoun_projectid() {
		return noun_projectid;
	}
	public void setNoun_projectid(long noun_projectid) {
		this.noun_projectid = noun_projectid;
	}
	public long getNoun_moduleid() {
		return noun_moduleid;
	}
	public void setNoun_moduleid(long noun_moduleid) {
		this.noun_moduleid = noun_moduleid;
	}
	public boolean isNoun_cache_enabled() {
		return noun_cache_enabled;
	}
	public void setNoun_cache_enabled(boolean noun_cache_enabled) {
		this.noun_cache_enabled = noun_cache_enabled;
	}
	public String getNoun_notes() {
		return noun_notes;
	}
	public void setNoun_notes(String noun_notes) {
		this.noun_notes = noun_notes;
	}
	public Date getNoun_createdate() {
		return noun_createdate;
	}
	public void setNoun_createdate(Date noun_createdate) {
		this.noun_createdate = noun_createdate;
	}
	public long getNoun_createdby() {
		return noun_createdby;
	}
	public void setNoun_createdby(long noun_createdby) {
		this.noun_createdby = noun_createdby;
	}
	public Date getNoun_lastmodifieddate() {
		return noun_lastmodifieddate;
	}
	public void setNoun_lastmodifieddate(Date noun_lastmodifieddate) {
		this.noun_lastmodifieddate = noun_lastmodifieddate;
	}
	public long getNoun_lastmodifiedby() {
		return noun_lastmodifiedby;
	}
	public void setNoun_lastmodifiedby(long noun_lastmodifiedby) {
		this.noun_lastmodifiedby = noun_lastmodifiedby;
	}
	public long getAttribute_id() {
		return attribute_id;
	}
	public void setAttribute_id(long attribute_id) {
		this.attribute_id = attribute_id;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public String getAttribute_description() {
		return attribute_description;
	}
	public void setAttribute_description(String attribute_description) {
		this.attribute_description = attribute_description;
	}
	public String getAttribute_label() {
		return attribute_label;
	}
	public void setAttribute_label(String attribute_label) {
		this.attribute_label = attribute_label;
	}
	public String getAttribute_type() {
		return attribute_type;
	}
	public void setAttribute_type(String attribute_type) {
		this.attribute_type = attribute_type;
	}
	public String getAttribute_sub_type() {
		return attribute_sub_type;
	}
	public void setAttribute_sub_type(String attribute_sub_type) {
		this.attribute_sub_type = attribute_sub_type;
	}
	public String getAttribute_sub_type_modifier() {
		return attribute_sub_type_modifier;
	}
	public void setAttribute_sub_type_modifier(String attribute_sub_type_modifier) {
		this.attribute_sub_type_modifier = attribute_sub_type_modifier;
	}
	public boolean isAttribute_part_of_unique_key() {
		return attribute_part_of_unique_key;
	}
	public void setAttribute_part_of_unique_key(boolean attribute_part_of_unique_key) {
		this.attribute_part_of_unique_key = attribute_part_of_unique_key;
	}
	public String getAttribute_data_length() {
		return attribute_data_length;
	}
	public void setAttribute_data_length(String attribute_data_length) {
		this.attribute_data_length = attribute_data_length;
	}
	public String getAttribute_relationships() {
		return attribute_relationships;
	}
	public void setAttribute_relationships(String attribute_relationships) {
		this.attribute_relationships = attribute_relationships;
	}
	public String getAttribute_notes() {
		return attribute_notes;
	}
	public void setAttribute_notes(String attribute_notes) {
		this.attribute_notes = attribute_notes;
	}
	public Date getAttribute_createdate() {
		return attribute_createdate;
	}
	public void setAttribute_createdate(Date attribute_createdate) {
		this.attribute_createdate = attribute_createdate;
	}
	public long getAttribute_createdby() {
		return attribute_createdby;
	}
	public void setAttribute_createdby(long attribute_createdby) {
		this.attribute_createdby = attribute_createdby;
	}
	public Date getAttribute_lastmodifieddate() {
		return attribute_lastmodifieddate;
	}
	public void setAttribute_lastmodifieddate(Date attribute_lastmodifieddate) {
		this.attribute_lastmodifieddate = attribute_lastmodifieddate;
	}
	public long getAttribute_lastmodifiedby() {
		return attribute_lastmodifiedby;
	}
	public void setAttribute_lastmodifiedby(long attribute_lastmodifiedby) {
		this.attribute_lastmodifiedby = attribute_lastmodifiedby;
	}
	
	
	
	public long getAttribute_nounid() {
		return attribute_nounid;
	}
	public void setAttribute_nounid(long attribute_nounid) {
		this.attribute_nounid = attribute_nounid;
	}
	public GpNoun get_noun_from_result_set(List<GpDto_noun_and_attributes> the_noun_attrib_set){
		
		GpNoun the_noun = new GpNoun();
		ArrayList<GpNounAttribute> noun_attribs = new ArrayList<GpNounAttribute>();
		
		the_noun = this.noun_processing(the_noun_attrib_set.get(0));
		for(GpDto_noun_and_attributes noun_and_attribs : the_noun_attrib_set){
			GpNounAttribute an_attribute = this.attribute_processing(noun_and_attribs);
			noun_attribs.add(an_attribute);
			
		}
		the_noun.setNounattributes(noun_attribs);
		return the_noun;

	}
	public ArrayList<GpNoun> get_noun_list_from_resultset(List<GpDto_noun_and_attributes> the_noun_attrib_list){
		
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
				the_noun_list.add(current_noun);
				found = false;
			}
			
		}
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

		return the_noun_list;
	}
	
	public  GpNoun noun_processing(GpDto_noun_and_attributes noun_and_attribs){
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
		noun.setDefault_activity_id(noun_and_attribs.getNoun_default_activity_id());

		
		return noun;
	}
	
	public GpNounAttribute attribute_processing(GpDto_noun_and_attributes noun_and_attribs){
		
		GpNounAttribute noun_attrib = new GpNounAttribute();
		
		noun_attrib.setId(noun_and_attribs.getAttribute_id());
		noun_attrib.setName(noun_and_attribs.getAttribute_name());
		noun_attrib.setLabel(noun_and_attribs.getAttribute_label());
		noun_attrib.setDescription(noun_and_attribs.getAttribute_description());
		noun_attrib.setDatalength(noun_and_attribs.getAttribute_data_length());
		noun_attrib.setType(noun_and_attribs.getAttribute_type());
		noun_attrib.setSubtype(noun_and_attribs.getAttribute_sub_type());
		noun_attrib.setSubtypemodifiervalue(noun_and_attribs
								.getAttribute_sub_type_modifier());
		noun_attrib.setNotes(noun_and_attribs.getAttribute_notes());
		noun_attrib.setRelationships(noun_and_attribs.getAttribute_relationships());
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
