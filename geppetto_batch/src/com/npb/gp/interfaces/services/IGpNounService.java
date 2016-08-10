package com.npb.gp.interfaces.services;

import java.util.ArrayList;

import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpUser;



/** 
*
*@author Dan Castillo</br>
* Creation Date: 04/08/2014</br>
* @since .35</p>  
*  
* this interface will be implemented by a service that handles nouns</p>
* 
* please note that a form of the implementing class has been in use since version .10 of the</br>
* Geppetto system. The .10 version is also known as "Cancun"</p>
* 
* Modified Date: 10/22/2014</br>
* Modified By:  Dan Castillo</p>
* 
* removed all references to the "backing" types - as these were legacy from
* the early days of Geppetto when the ui was Flex
*  
* 
*/

public interface IGpNounService {
	public void create_noun(GpNoun anoun, GpUser user) throws Exception;
	public void update_noun(GpNoun anoun, GpUser user) throws Exception;
	public void delete_noun(long noun_id, GpUser user) throws Exception;
	public GpNoun search_for_noun_by_noun_id(long noun_id, GpUser user) throws Exception;
	public ArrayList<GpNoun> search_for_nouns_by_project_id(long project_id, GpUser user) throws Exception;

}
