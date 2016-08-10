package com.npb.gp.domain.core;

import java.sql.Date;

/**
 * @author Dan Castillo</br>
 * Date Created: 10/31/2012</br>
 * @since .35</p>
 *
 *This class represents datatypes that are shown to the user</p>
 *  
 * <b>Modified Date:</b>12/12/2012</br>
 * <b>Modified By:</b> Dan Castillo</br>
 * <b>Modification Description:</b></p>
 * 
 *added <b>techinicallanguagetype</b> field:</b></br> - the purpose of this field is to specify the computer languages</br>
 *supported by Geppetto currently supported languages that are targeted for a 1.0 release are:</br>
 *Java, ActionScript 3.0, Java Script, and Python 3.x</br></p>
 * 
 * added <b>subtype</b> field:</br> - the purpose of this field is to modify the meaning of the type field</br>
 * For example:</br>
 * if the type field has a value of standard then the subtype field will contain</br>
 * the values of the standard datatypes allowed by the technicallanguagetype</br>
 * For java typical dattypes are:</p>
 * <li>string</li>
 * <li>boolean</li>
 * <li>date</li></br>
 * 
 * added <b>defaultsubtypevalue</b> field:</br> - the purpose of this field is to 
 * contain the default value for a given data type</br>
 * For example:</br>
 * if the type is a string/text the default value is 256 which internally is used in various ways</br>
 * one area where this is used is the column size for the field in the DB</p>
 *  
 * added <b>allowed_subtype_value_type</b> field:</br> - the purpose of this field is to enforce valid values for the<b>defaultsubtypevalue</b><br>
 * For example:</br>
 * if the subtype is string then defaultsubtypevalue is 256 BUT the user can change the value</br>
 * if the user changes the value, the value entered has to be an INT (i.e. 4096) what the <b>allowed_subtype_value_type</b> field does</br>
 * is allow the validation to make sure the user entered an int and not something that can't be used</p>
 *
 *  <b>***  PLEASE NOTE: ***</b></br>
 *  values for the TYPE field can be found in the GpNounAttributeTypeConstants class
 *  
 *  
 */


public class GpNounAttributeType {
	
	
	private long id;
	private String technicallanguagetype;
	private String type;	//high level distinction between a user defined type, lists, and basic language types
	private String subtype;  //if type is standard then this can be text, boolean etc
	private String description;
	private String label;
	private String defaultlength;
	private String defaultsubtypevalue;
	private String allowed_subtype_value_type;  //indicates what sort of type (string, int) a value is permitted to have
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lasmodifiedby;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public String getTechnicallanguagetype() {
		return technicallanguagetype;
	}
	public void setTechnicallanguagetype(String technicallanguagetype) {
		this.technicallanguagetype = technicallanguagetype;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getDefaultsubtypevalue() {
		return defaultsubtypevalue;
	}
	public void setDefaultsubtypevalue(String defaultsubtypevalue) {
		this.defaultsubtypevalue = defaultsubtypevalue;
	}
	public String getAllowed_subtype_value_type() {
		return allowed_subtype_value_type;
	}
	public void setAllowed_subtype_value_type(String allowed_subtype_value_type) {
		this.allowed_subtype_value_type = allowed_subtype_value_type;
	}
	public String getDefaultlength() {
		return defaultlength;
	}
	public void setDefaultlength(String defaultlength) {
		this.defaultlength = defaultlength;
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
	public long getLasmodifiedby() {
		return lasmodifiedby;
	}
	public void setLasmodifiedby(long lasmodifiedby) {
		this.lasmodifiedby = lasmodifiedby;
	}
	
	
	


}
