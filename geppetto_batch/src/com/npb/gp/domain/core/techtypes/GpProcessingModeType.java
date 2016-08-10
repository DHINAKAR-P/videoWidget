package com.npb.gp.domain.core.techtypes;

import java.sql.Date;

/**
 * @author Dan Castillo</br>
 * Date Created: 03/10/2013
 * @since .35</p>
 *
 * A processing mode is a high level construct representing the type of processing</br>
 * a software system executes. For example, a system that handles user input is</br>
 * considered to have a processing type of "HUMAN", one that executes BATCH is </br>
 * said to be of type BATCH. The attempt is to have processing modes loosely </br>
 * align with BPMN 2.0 Activity types.</p>
 * 
 * 
 * 
 *  
 */

public class GpProcessingModeType {
	
	private long id;
	private String label;
	private String name;
	private String description;
	private String notes;
	
	
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	
	

}
