package com.npb.gp.domain.core;
/**
 * Date Created: November 06 2010
 * @author Dan Castillo
 * 
 *
 *The purpose of this class is to serve as the base class that holds
 * user roles. It is basically a way to implement a hack. The hack
 * is necessary because at this time I don't want to spend time 
 * figuring out how to implement ROLE/Group hierachies in Spring 3 security
 *  
 *
 * Modified Date: 09/07/2012
 * Modified By:  Dan Castillo
 * 
 * started to use it in version .35 
 *
 *  NOTE:
 *  The statement above applies to the use of ACLs in Spring Security to secure
 *   business data - In other words if a generated application needs to  
 *   use ACLs to implement business data security then they need to hire a 
 *   developer - as of now ACLs are out of scope for Geppetto
 *   
 *   Geppetto does need some form of business data security in order to 
 *   restrict access to screens, menu items, fields in forms, etc. Since we are not
 *   using Spring ACL's I have come up with a rudimentary approach to solve this problem.
 *   
 *    The approach involves using this class in combination with the GpAuthority descendant 
 *    class. 
 *    
 *    The idea is to assign an integer value to each ROLE and then simply compare 
 *    the max allowed ROLE on a resource to the ROLE of the user If the ROLE of the 
 *    user is greater than the ROLE needed to access the resource permission is 
 *    granted 
 *    
 *    The decision logic is contained in the isPermitted() method of the 
 *    GpAuthority class
 * 
 */



public class GpBaseAuthority {
	public static int  ROLE_ADMIN = 100;
	public static int  ROLE_USER_OWNER = 2;
	public static int  ROLE_USER = 1;

	
	public int getRoleHierArchyPosition(String role_string) {
	
		if(role_string.equals("ROLE_ADMIN")) {
			return GpBaseAuthority.ROLE_ADMIN;
		}else if(role_string.equals("ROLE_USER_OWNER")) {
			return GpBaseAuthority.ROLE_USER_OWNER;
		}else if(role_string.equals("ROLE_USER")) {
			return GpBaseAuthority.ROLE_USER;
		} else {
			return 0;
		}
	}

}
