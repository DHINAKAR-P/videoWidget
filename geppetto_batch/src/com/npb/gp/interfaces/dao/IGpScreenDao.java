package com.npb.gp.interfaces.dao;

import java.util.ArrayList;
import java.util.List;

import com.npb.gp.domain.core.GpScreen;
import com.npb.gp.domain.core.GpUiWidget;




/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/11/2014</br>
 * @since .35</p>  
 *
 *The purpose of this interface is to declare the standard db operations required</br>
 *for the screen functions</p>
 *
 *please note that a form of this class has been in use since version .10 of the</br>
 *Geppetto system. The .10 version is also known as "Cancun"</p>
 *
 *
 * Modified Date: 10/22/2014</br>
 * Modified By:  Dan Castillo</p>
 * 
 * removed all references to the "backing" types - as these were legacy from
 * the early days of Geppetto when the ui was Flex

 *
 * Modified Date: 10/04/2014</br>
 * Modified By:  Dan Castillo</p>
 *
 * added find_all_base_by_projectid(long projectid) method to handle searches
 * where you only want to obtain the parent (base) noun by none of its children 
 *
 */

public interface IGpScreenDao {
	
	public void insert(GpScreen ascreen);
	public void update(GpScreen ascreen);
	public void delete(GpScreen ascreen);
	public GpScreen find_by_id(long screen_id)throws Exception;
	public ArrayList<GpScreen> find_by_activity_id(long activity_id) throws Exception;
	public ArrayList<GpScreen> find_all_base_by_activity_id(long activity_id) throws Exception;
	public ArrayList<GpScreen> find_by_project_id(long project_id)throws Exception;
	public List<GpScreen> find_all_base_by_projectid(long projectid) throws Exception;
	public void insert_screen_widgets(GpScreen ascreen);
	public void insert_a_widget(GpUiWidget awidget, long screen_id, long user_id);
	
	
	
	

}
