package com.npb.gp.gen.util.dto.springboot;

import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.util.dto.GpBaseVerbGenInfo;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p> 
 * 
 * the purpose of this class is to be used as a pass through as the base class</br>
 * probably has enough information to get the job done - but because this is the </br>
 * first pass at  producing a code generator to be used by the public the assumption</br>
 * is that things will change and its best to introduce a layer of abstraction to </br>
 * changes</p>
 * 
 * </p>
 */

public class GpSpringBootDaoVerbGenInfo extends GpBaseVerbGenInfo {
	
	
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
	public String mapper_type;
	public Object mapper_creation;
	public String mapper_reference;
	public String gp_server_post_code;
	public String gp_end_code;
	public String gp_start_code;
	public GpJavaMethodDescription signiture_helper;
}
