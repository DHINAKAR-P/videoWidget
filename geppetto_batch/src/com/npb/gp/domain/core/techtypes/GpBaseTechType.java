package com.npb.gp.domain.core.techtypes;

import java.sql.Date;

/**
 * @author Dan Castillo</br>
 * Date Created: 03/10/2013
 * @since .35</p>
 *
 *represents the base information that is needed for technical things that</br>
 *are a type of a thing</p>
 * 
 * 
 * 
 */

public class GpBaseTechType {

	private long id;
	private String label;
	private String name;
	private String description;
	private String version;
	private String release_status;
	private String license_status;
	private String notes;
	private String type;
	
	
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	public String getRelease_status() {
		return release_status;
	}
	public void setRelease_status(String release_status) {
		this.release_status = release_status;
	}
	public String getLicense_status() {
		return license_status;
	}
	public void setLicense_status(String license_status) {
		this.license_status = license_status;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
