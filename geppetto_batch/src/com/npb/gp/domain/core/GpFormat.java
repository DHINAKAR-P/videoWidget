package com.npb.gp.domain.core;

/**
 * @author Dan Castillo
 * Date Created: 10/31/2012
 * @since .35</p>
 *
 *This class represents formats that are used by the generated code
 *  
 */

public class GpFormat {
	
	private long id;
	private String visualrepresentation;
	private String internalrepresentation;
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVisualrepresentation() {
		return visualrepresentation;
	}
	public void setVisualrepresentation(String visualrepresentation) {
		this.visualrepresentation = visualrepresentation;
	}
	public String getInternalrepresentation() {
		return internalrepresentation;
	}
	public void setInternalrepresentation(String internalrepresentation) {
		this.internalrepresentation = internalrepresentation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
