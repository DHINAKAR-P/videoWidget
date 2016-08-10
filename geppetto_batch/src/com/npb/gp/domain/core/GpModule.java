package com.npb.gp.domain.core;

import java.sql.Date;
import java.util.ArrayList;


import com.npb.gp.domain.core.GpActivity;


/**
 * @author Dan Castillo</br>
 * Date Created: 04/20/2013</br>
 * @since .35</p>
 *  
 *This is one of the key model classes for the system, its purpose is to</br>
 *is to encapsulates the information needed by a module.</p>
 * A module loosely represents the main functional areas of a software system. It is</br>
 * envisioned that most systems created with Geppetto will be used by small</br>
 *business, as such, those businesses would have a need to share information</br>
 *with a general ledger or a CRM system. The concept of a module allows the user</br>
 *to aggregate these disparate use cases in one app composed of these various</br>
 *components or modules.</p>
 *
 *All apps created by Gepetto will have a "default" module which will house the</br>
 *core functionality of the app being worked on.
 *
 *Modified Date: 28/09/2015</br>
 *Modified By: Reinaldo
 *</p>
 *<p>
 *Added base_location attribute
 *</p>
 *
 */

public class GpModule {
	
	private long id;
	private String name;
	private String label;
	private  String description;
	private String notes;
	private ArrayList<GpActivity> theactivities;

	private String multi_user_status;
	private String multi_user_info;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	private String base_location;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	public ArrayList<GpActivity> getTheactivities() {
		return theactivities;
	}
	public void setTheactivities(ArrayList<GpActivity> theactivities) {
		this.theactivities = theactivities;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public String getBase_location() {
		return base_location;
	}
	public void setBase_location(String base_location) {
		this.base_location = base_location;
	}	

}
