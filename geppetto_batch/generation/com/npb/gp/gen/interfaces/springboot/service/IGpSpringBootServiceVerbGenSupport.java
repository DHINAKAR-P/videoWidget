package com.npb.gp.gen.interfaces.springboot.service;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p> 
 * 
 * classes implementing this interface have the responsibility</br>
 * for the logic that creates the code for the verbs that</br>
 * represent functions in Spring boot servicer</br>
 * 
 * Dan Castillo:(Comments for future implementation)
 * 
 * please note: at this time, 03/14/2015, except for the handle_verb</br>
 * method, the methods in classes implementing this interface should</br>
 * probably be made private and therefore have no business in an interface</br>
 * however, it may be that in the future they will be used - Dan Castillo</p>
 * 
 */

public interface IGpSpringBootServiceVerbGenSupport {
	
//	public void get_function_signiture(GpVerb verb,GpActivity activity,HashMap<String, GpJavaMethodDescription> service_methods) throws Exception;
	public GpServiceVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception;
//	public void gp_start(GpVerb verb) throws Exception;
//	public void gp_end(GpVerb verb) throws Exception;
//	public void gp_validate(GpVerb verb) throws Exception;
//	public void gp_server_post(GpVerb verb, GpActivity activity) throws Exception;
//	public void gp_server_response(GpVerb verb) throws Exception;
//	public void gp_display_server_response(GpVerb verb) throws Exception;
//	public void gp_confirm(GpVerb verb) throws Exception;
//	public void gp_transition(GpVerb verb) throws Exception;

}
