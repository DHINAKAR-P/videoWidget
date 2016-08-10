package com.npb.gp.domain.core;


/**
 * 
 * @author Dan Castillo
 * Date Created: 06/11/2014</br>
 * @since .01</p> 
 *
 * The purpose of this class is to hold configurations that are needed to</br>
 *  generate an application. The data encapsulated in the configurations is</br>
 *  manipulated based on the logic encapsulated in the concept of an Architecture</br>
 *  type or architype for short.</p>
 *  
 *  Please note that the concept of this class has been around since the</br>
 *  Cancun/Caracas versions of Geppetto </p>
 *  
 *  
 */

public class GpArchitypeConfigurations {

	/**
	 * "dynamic" - are created by the logic that generates an application
	 */
	public static String CONFIGTYPE_DYNAMIC = "dynamic";
	/**
	 * "static" - are stored in a table
	 */
	
	public static String CONFIGTYPE_STATIC = "static";
	private long id;
	private String name;
	private String label;
	private String description;
	private String value;
	private String type;
	private String sub_type;
	private Object object_value;
	
	
	
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSub_type() {
		return sub_type;
	}
	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}
	
	public Object getObject_value() {
		return object_value;
	}
	public void setObject_value(Object object_value) {
		this.object_value = object_value;
	}
	
	
	
	

}
