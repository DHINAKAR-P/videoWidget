package com.npb.gp.constants;
/**
 * @author Dan Castillo</br>
 * Date Created: 11/27/2013</br>
 * @since .35</p>
 *
 *This class holds available contexts</p>
 *At this time a context is very closely align with a verb, meaning that the context</br>
 *is the string literal that is used by the system to do logic operations (IF)</br>
 *To aid in understanding this concept, I will use an example to explain</p>
 *The example assumes the following</br>
 *<li>the application has user interaction</LI></p>
 *</p>
 * The example is the action of updating a noun. When a noun needs updating</br>
 *there will be some sort of screen where the user can interact with the information</br>
 *also there will be operations that need to take place on the server in order to </br>
 *ensure that the update is atomic
 *  
 */

public class GpContexts {
	
	public static String GpCreate = "create"; 
	public static String GpUpdate = "update";
	public static String GpSearchForUpdate = "search_for_update";
	public static String GpDelete = "delete";
	public static String GpDndStandard = "dnd_standard";
	public static String GpDndParent = "dnd_parent";

}
