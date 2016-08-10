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
 * is that thingss will change and its best to introduce a layer of abstraction to </br>
 * changes</p>
 *   
 * 
 */
public class GpLegacyDaoVerbGenInfo extends GpBaseVerbGenInfo {
	
	public String mapper_reference;
	public String mapper_type;
	public String mapper_creation;
	public String parameter_assignment;
	public String key_holder_type;
	public String key_holder_reference;
	public String key_holder_creation;
	public String special_mapper_refrence;
	public String jdbc_template_type;
	public String jdbc_template_input_parms;
	public String key_holder_value_assignment;
	public String return_reference_check;
	public String set_SQL_query;
	public String SQL_special_mapper_refrence;
	public String type_query;

}
