package com.npb.gp.domain.core;

import java.sql.Date;

/**
 * @author Dan Castillo<br>
 * Date Created: 03/11/2013
 * @since .35</p>
 * 
 *
 * Represents the ISO 639 language codes</p>
 * 
 */


public class GpLanguage {
	
	private long id;
	private String lang_three_letter_code;
	private String lang_two_letter_code;
	private String langname;
	private String label;
	
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
	public String getLang_three_letter_code() {
		return lang_three_letter_code;
	}
	public void setLang_three_letter_code(String lang_three_letter_code) {
		this.lang_three_letter_code = lang_three_letter_code;
	}
	public String getLang_two_letter_code() {
		return lang_two_letter_code;
	}
	public void setLang_two_letter_code(String lang_two_letter_code) {
		this.lang_two_letter_code = lang_two_letter_code;
	}
	public String getLangname() {
		return langname;
	}
	public void setLangname(String langname) {
		this.langname = langname;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
