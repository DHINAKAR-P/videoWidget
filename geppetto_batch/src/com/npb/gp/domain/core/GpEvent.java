package com.npb.gp.domain.core;

import java.sql.Date;


/**
 * @author Dan Castillo</br>
 * Date Created: 07/21/2013</br>
 * @since .35</p>
 * 
 * 
 * The purpose of this class is to represent an event</p> 
 * 
 * Please note a version of this class exited in Geppetto since version .2</p>
 * 
 * 
 */ 

public class GpEvent {
	
	private long id;
	private String name;
	private  String description;
	private String label;
	private GpVerb averb;
	
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
		
	public GpVerb getAverb() {
		return averb;
	}
	public void setAverb(GpVerb averb) {
		this.averb = averb;
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
