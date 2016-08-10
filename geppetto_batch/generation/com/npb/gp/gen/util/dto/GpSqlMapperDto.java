package com.npb.gp.gen.util.dto;
/**
 * 
 * @author Dan Castillo
 * Date Created: 01/24/2015</br>
 * @since .2</p> 
 * 
 * DTO to hold info needed to generate the attribute assignments</br>
 * in a legacy Spring SQL MAPPER </p> 
 */

public class GpSqlMapperDto {
	
	public String attribute_name;
	public String sql_type;
	public String primary_noun_reference; 
	public String setter_method;

}
