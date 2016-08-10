package com.npb.gp.gen.util.dto;
/**
 * 
 * @author Dan Castillo
 * Date Created: 01/06/2015</br>
 * @since .2</p> 
 * 
 * this class is used to facilitate sending information for resource bundle</br>
 * references to the StringTemplate which is the template library that is being</br>
 * the resource bundle references are used within then generated code  </p> 
 */

public class GpResourceBundleReference {
	
	public String resource_bundle_key;
	public String resource_bundle_value;
	public String local_string_reference;
	public String verb_action_on_data;
	public String the_java_type = "String";

}
