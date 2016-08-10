package com.npb.gp.gen.workers.server.java.springboot.support.domain;

import java.util.Date;
/**
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p> 
 *  
 *This is one of the key model classes for the system, its purpose is to
 *encapsulate the information that will lead to model classes in the 
 *generated code. The class captures the "field" information for what eventually
 *becomes part of the generated model classes. The class is used in conjunction with the 
 *GpNoun class.
 *
 * Please note that this class was originally part of the POC for Geppetto
 * which is designated as version .1 and was used in version .2 and .3 
 * I decided to have a fresh start for  version .35 and beyond. </p>
 *
 * 
 * 
 * <b>Modified Date:&nbsp;&nbsp;</b>12/12/2012</br>
 * <b>Modified By:</b> Dan Castillo</br>
 * <b>Modification Description:</b></br>
 * added subtypemodifiervalue field - the purpose of this field is to provide more information</br>
 * about the noun attribute - for example if the noun attribute type is a list</br>
 * it will contain the list type (list of ints, or string, or dates or other types)</p>
 * 
 * 
 * <b>Modified Date:&nbsp;&nbsp;</b>02/04/2013</br>
 * <b>Modified By:</b> Dan Castillo</br>
 * <b>Modification Description:</b></br>
 * added ispart_of_unique_key field - the purpose of this field is to indicate whether</br>
 * or not the nounattribute is used as part of an index that identifies that makes</br>
 * noun unique in a list of nouns of the same type</br>
 * I.E. - if the noun is CAR then the nounattribute for VIN_NUMBER would have its</br>
 * ispart_of_unique_key property set to true 
 * 
 * Here added some fields for swagger framework annotations and Jpa .
 *
 */

public class GpSpringBootNounAttribute {
	
	private long id;  
	private long nounid; 
	private String name;
	private String label; 
	private  String technicalname; //i doont think I need this any more - Dan 4/8/2014
	private  String description;
	private  String type;
	private  String subtype;
	private String subtypemodifiervalue;
	private boolean ispart_of_unique_key;
	private String datalength;
	private String notes;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	//For Jpa annotations
	private String annotations;
	/// annotations for swagger 
	private String swagger_model_value;
	private String swagger_modle_description;
	private String swagger_field_value;
	private String access_specifier;
	private String long_data_type_description;
	
	

	public String getLong_data_type_description() {
		return long_data_type_description;
	}
	public void setLong_data_type_description(String long_data_type_description) {
		this.long_data_type_description = long_data_type_description;
	}
	public String getAccess_specifier() {
		return access_specifier;
	}
	public void setAccess_specifier(String access_specifier) {
		this.access_specifier = access_specifier;
	}
	public String getSwagger_model_value() {
		return swagger_model_value;
	}
	public void setSwagger_model_value(String swagger_model_value) {
		this.swagger_model_value = swagger_model_value;
	}
	public String getSwagger_modle_description() {
		return swagger_modle_description;
	}
	public void setSwagger_modle_description(String swagger_modle_description) {
		this.swagger_modle_description = swagger_modle_description;
	}
	public String getSwagger_field_value() {
		return swagger_field_value;
	}
	public void setSwagger_field_value(String swagger_field_value) {
		this.swagger_field_value = swagger_field_value;
	}
	public String getAnnotations() {
		return annotations;
	}
	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNounid() {
		return nounid;
	}
	public void setNounid(long nounid) {
		this.nounid = nounid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getTechnicalname() {
		return technicalname;
	}
	public void setTechnicalname(String technicalname) {
		this.technicalname = technicalname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getSubtypemodifiervalue() {
		return subtypemodifiervalue;
	}
	public void setSubtypemodifiervalue(String subtypemodifiervalue) {
		this.subtypemodifiervalue = subtypemodifiervalue;
	}
	
	public boolean isIspart_of_unique_key() {
		return ispart_of_unique_key;
	}
	public void setIspart_of_unique_key(boolean ispart_of_unique_key) {
		this.ispart_of_unique_key = ispart_of_unique_key;
	}
	public String getDatalength() {
		return datalength;
	}
	public void setDatalength(String datalength) {
		this.datalength = datalength;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public long getCreatedby() {
		return createdby;
	}
	public void setCreatedby(long createdby) {
		this.createdby = createdby;
	}
	public Date getLastmodifieddate() {
		return lastmodifieddate;
	}
	public void setLastmodifieddate(Date lastmodifieddate) {
		this.lastmodifieddate = lastmodifieddate;
	}
	public long getLastmodifiedby() {
		return lastmodifiedby;
	}
	public void setLastmodifiedby(long lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	
	

}