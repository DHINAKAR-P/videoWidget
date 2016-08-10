package com.npb.gp.gen.util.dto;

import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.workers.server.java.springboot.support.service.GpServiceSpringBootVerbMethodSignitures;

/**
 * 
 * @author Dan Castillo
 * Date Created: 12/30/2014</br>
 * @since .2</p>
 * 
 * the purpose of this class is to be used as a pass through as the base class</br>
 * probably has enough information to get the job done - but because this is the </br>
 * first pass at a producing a code generator to be used by the public the assumption</br>
 * is that things will change and its best to introduce a layer of abstraction to </br>
 * changes</p>
 * 
 * @author Kumaresan Perumal
 * Date Created: 03/09/2016</br>
 * @since .2</p>
 * <p>Here i added three variables for micro flow controll (gp_server_post_code,
 * gp_end_code,gp_end_code,gp_start_code)</p>
 *   
 * 
 */
public class GpServiceVerbGenInfo extends GpBaseVerbGenInfo {
	public GpJavaMethodDescription signiture_helper;
	public String dao_call;
	public String gp_server_post_code;
	public String gp_end_code;
	public String	gp_start_code;
	public String post_dao;
	 

}
