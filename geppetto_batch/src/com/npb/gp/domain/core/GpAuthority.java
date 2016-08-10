package com.npb.gp.domain.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Created: October 30 2010
 * @author Dan Castillo
 * 
 * The purpose of this class is determine if a users role or group
 * has a higher authorization level than the authorization level needed
 * It is basically imposing a very simple  hierarchy 
 * E.G. if something needs ROLE_USER access but the user has ROLE_ADMIN access
 * the the user is authorized. If on the other hand the something needs
 * ROLE_ADMIN access but the user only has ROLE_USER in this case the user
 * is not authorized.
 * 
 *  The algorithm for grating access can be more complex if needed, by 
 *  simply adding code/rules to the isPermitted(String authstring) method.
 *
 *
 * Modified Date: 09/07/2012
 * Modified By:  Dan Castillo
 * 
 * started to use it in version .35 cleaned up logging statements
 */

public class GpAuthority extends GpBaseAuthority {
	
	private final Log logger = LogFactory.getLog(getClass());
	private long id;
	private String username;
	private String authority;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
	public boolean isPermitted(String authstring) {
		logger.debug("GpAuthority- In isPermitted " + " authstring is:" 
														+ authstring);
		logger.debug("GpAuthority- In isPermitted " + " this.authority is:" 
				+ this.authority);
		
		int roleNeeded = super.getRoleHierArchyPosition(authstring);
		
		if(roleNeeded <= super.getRoleHierArchyPosition(this.authority)) {
			logger.debug("GpAuthority- In isPermitted " + "return code is: true");
			return true;
		}
		logger.debug("GpAuthority- In isPermitted " + "return code is: false");
		return false;
	}

	
	

}
