package com.npb.gp.gen.util.dto.springboot;

import com.npb.gp.gen.util.dto.GpBaseVerbGenInfo;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p> 
 * 
 * 
 * <p> The swagger annotation needs these values 
 * to print out the annotation such as values,dscriptions,
 *  response class name and method type.</p>
 *   
 * 
 */

public class GpControllerSpringBootVerbGenInfo extends GpBaseVerbGenInfo {
	public String method_value;
	public String method_notes;
	public String method_response_clazz_name;
	public String http_method;
	
	public String controller_value;
	public 	String controller_notes;
	
	public String scope_service;
	public String function_name;
	public String function_parms;
	public String gp_start_code;
	
	public String gp_validate_code;
	public String gp_confirm_code;
	public String gp_server_post_code;
	public String gp_server_response_code;
	public String gp_display_server_response_code;
	public String gp_transition_code;
	
	public String gp_end_code;
	
	public String instantiate_noun_name; 
	public String instantiate_noun_name_reference;

	public String gp_instantiate_noun;
	public String declare_noun;
	public String request_map_value;
	public String reference_request_map_value;
	public String accept_type;
	
}
