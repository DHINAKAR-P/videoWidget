package com.npb.gp.domain.core;

import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Dan Castillo</br>
 * Creation Date: 04/20/2013</br>
 * @since .35</p>
 * 
 * 
 * The purpose of this class is to encapsulate actions that can be</br>
 * performed on a noun - In theory it should be used to stand for</br> 
 * the semantics of the action  as defined by the  English language</p>
 *
 *Please note that this the latest version of this class - Geppetto has had</br>
 *this concept since version .2 - see note below: </p>
 * 
 * <b>ORIGINAL COMMENT:</b>
 * At this time this is a very new concept to Geppetto and is expected to</br>
 * be modified with future versions - 
 * Dan Castillo - 5/30/2011</p>
 * 
 * 
 * 
 * Modified Date: 12/16/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * 
 * added the  "action_on_data" field that encapsulate what the verb is </br>
 * is meant to do with data</p>
 * 
 * 

 * 
 * 
 * 
 * Modified Date: 11/28/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * 
 * added the  "authorizations" field that encapsulate the permissions of who</br>
 * can execute the verb</p>
 * 
 * 
 * 
 */

public class GpVerb {
	
	private long id;
	private String name;
	private String description;
	private String label;
	private String notes;
	private String action_on_data;
	private String screen_id;
	private String actual_information;
	private ArrayList<GpAuthorization> authorizations;
	
	private String multi_user_status;
	private String multi_user_info;
	private Date createdate;
	private long createdby;
	private Date lastmodifieddate;
	private long lastmodifiedby;
	
	public String getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(String screen_id) {
		this.screen_id = screen_id;
	}
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	public String getAction_on_data() {
		return action_on_data;
	}
	public void setAction_on_data(String action_on_data) {
		this.action_on_data = action_on_data;
	}
	public void set_an_authorization(GpAuthorization an_auth){
		if(this.authorizations == null){
			this.authorizations = new ArrayList<GpAuthorization>();
		}
		this.authorizations.add(an_auth);
	}
		
	public ArrayList<GpAuthorization> getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(ArrayList<GpAuthorization> authorizations) {
		this.authorizations = authorizations;
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
	public String getActual_information() {
		return actual_information;
	}
	public void setActual_information(String actual_information) {
		this.actual_information = actual_information;
	}
}
