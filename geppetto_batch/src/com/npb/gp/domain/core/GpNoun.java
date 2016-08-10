package com.npb.gp.domain.core;

import java.util.Date;
import java.util.ArrayList;


/**
 * @author Dan Castillo</br>
 * Date Created: 10/31/2012
 * @since .35</p>
 *  
 *This is one of the key model classes for the system, its purpose is to</br>
 *encapsulate the information that will lead to model classes in the </br>
 *generated code. Any information that is used in the creation RDBMS tables</br>
 *and basic queries is also derived from the information in this class </p>
 *
 * Please note that this class was originally part of the POC for Geppetto</br>
 * which is designated as version .1 and was used in version .2 and .3</br> 
 * I decided to have a fresh start for  version .35 and beyond. </p>
 * 
 * 
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex
 * 
 * 
 * 
 * Modified Date: 04/24/2013</br>
 * Modified By:  Dan Castillo</p>
 * 
 * added the <b>activity_based_type</b> field:</p>
 * the value of this field is determined by the Activity that is currently</br>
 * acting on this noun; depending on where it is used, the value of this</br>
 * field can be "primary noun" or "secondary noun". If we use U.S STATE as an</br>
 * example the value for the activity is and address, in this case the value</br>
 * would be "secondary noun". The value would change to "primary noun" in an</br>
 * activity that was manipulating information about U.S STATES</p>
 *
 */

public class GpNoun {
	
	private long id;
	private String name;
	private  String description;
	private String label;
	private  String technicalname;
	private ArrayList<GpNounAttribute> nounattributes;
	private String notes;
	private long projectid;
	private long moduleid;
	private ArrayList<GpActivity> activitiesusedin;
	private String cacheenabled;
	
	private String multi_user_status;
	private String multi_user_info;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private long default_activity_id;
	private ArrayList<GpNoun> children;
	private ArrayList<GpNoun> parents;
	private String cacheable;
	
	private String activity_based_type;   //see javdoc above  
	
	
	public long getDefault_activity_id() {
		return default_activity_id;
	}
	
	public void setDefault_activity_id(long default_activity_id) {
		this.default_activity_id = default_activity_id;
	}

	public String getActivity_based_type() {
		return activity_based_type;
	}
	public void setActivity_based_type(String activity_based_type) {
		this.activity_based_type = activity_based_type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMulti_user_status() {
		return multi_user_status;
	}
	public void setMulti_user_status(String multi_user_status) {
		this.multi_user_status = multi_user_status;
	}
	public String getMulti_user_info() {
		return multi_user_info;
	}
	public void setMulti_user_info(String multi_user_info) {
		this.multi_user_info = multi_user_info;
	}
	public ArrayList<GpNoun> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<GpNoun> children) {
		this.children = children;
	}
	public ArrayList<GpNoun> getParents() {
		return parents;
	}
	public void setParents(ArrayList<GpNoun> parents) {
		this.parents = parents;
	}
	public String getCacheable() {
		return cacheable;
	}
	public void setCacheable(String cacheable) {
		this.cacheable = cacheable;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name.substring(0,1).toUpperCase() + name.substring(1);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTechnicalname() {
		return technicalname;
	}
	public void setTechnicalname(String technicalname) {
		this.technicalname = technicalname;
	}
	
	public ArrayList<GpNounAttribute> getNounattributes() {
		return nounattributes;
	}
	public void setNounattributes(ArrayList<GpNounAttribute> nounattributes) {
		this.nounattributes = nounattributes;
	}
	public String getCacheenabled() {
		return cacheenabled;
	}
	public void setCacheenabled(String cacheenabled) {
		this.cacheenabled = cacheenabled;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public long getProjectid() {
		return projectid;
	}
	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}
	
	
	
	public long getModuleid() {
		return moduleid;
	}
	public void setModuleid(long moduleid) {
		this.moduleid = moduleid;
	}
	public ArrayList<GpActivity> getActivitiesusedin() {
		return activitiesusedin;
	}
	public void setActivitiesusedin(ArrayList<GpActivity> activitiesusedin) {
		this.activitiesusedin = activitiesusedin;
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
