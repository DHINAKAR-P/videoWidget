package com.npb.gp.gen.util.dto;
/**
 * 
 * @author Dan Castillo
 * Date Created: 01/08/2015</br>
 * @since .2</p>
 * 
 * the purpose of this class is to be used as a pass through as the base class</br>
 * probably has enough information to get the job done - but because this is the </br>
 * first pass at  producing a code generator to be used by the public the assumption</br>
 * is that things will change and its best to introduce a layer of abstraction to </br>
 * changes</p>
 * 
 * Modified Date: 09-1-2016 and 30-12-2015</br>
 * Modified By: Kumaresan Perumal</br>
 * <p>
 * I commented mapper, key holder, jdbc template sql variables.
 * and added the new variables called 
 *  1. executeStatement
 *  2. local_string_reference
 *  3. return_list_size
 *  4. clazz_name
 *  5. return_list_name
 * </p>
 */

public class GpJpaDaoVerbGenInfo extends GpBaseVerbGenInfo {
	
	
	public String parameter_assignment;
	public String return_reference_check;
	public String type_query;
	public String return_reference;
	public String execute_statement;
	public String local_string_reference;
	public String return_list_size;
	public String return_list_name;
	public String clazz_name;
	//public String semi_colon;
	
}
