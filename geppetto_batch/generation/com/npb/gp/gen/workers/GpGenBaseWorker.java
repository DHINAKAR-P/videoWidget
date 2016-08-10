package com.npb.gp.gen.workers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;










import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;


/**
 *
 * @author Dan Castillo
 * Date Created: 5/29/2010</br>
 * @since .01</p>
 *
 * The purpose of this class is to serve as a utility base class</br>
 * for the workers that do the code generation</p>
 *
 * Modified Date: 06/21/2014</br>
 * Modified By:  Dan Castillo</p>
 *
 *  started using this in version .35 - which includes the first version</br>
 *  of the code generation BATCH framework and added the generate_code method</p>
 * Please note that when this class was created I used a camel case naming</br>
 * convention, since then I have switched to using underscores "_" to</br>
 * separate the tokens in a name</p>
 *
 */

public class GpGenBaseWorker {
	 protected final Log logger = LogFactory.getLog(getClass());
	 protected String file_separator = System.getProperty("file.separator");
	 //protected ArrayList<GpTechProperties> tech_property_list;
	 protected HashMap<String,GpArchitypeConfigurations> base_configs;
	 protected HashMap<String, GpArchitypeConfigurations> derived_configs;
	 public GpProject the_project;


	public GpTechProperties get_tech_property(long prop_id){
		GpArchitypeConfigurations tech_property_list_config
				= this.base_configs.get(GpGenConstants.TECH_PROPERTY_LIST);

		@SuppressWarnings("unchecked")
		ArrayList<GpTechProperties> tech_prop_list
				= (ArrayList<GpTechProperties>) tech_property_list_config
														.getObject_value();

		for(GpTechProperties tech_prop : tech_prop_list){
			if(tech_prop.getId() == prop_id){
				return tech_prop;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<GpTechProperties> get_tech_property_list(){

		GpArchitypeConfigurations tech_property_list_config
				= this.base_configs.get(GpGenConstants.TECH_PROPERTY_LIST);

		return (ArrayList<GpTechProperties>) tech_property_list_config
														.getObject_value();
	 }

	 /*
	 * This utility method capitalizes a string
	 *
	 * @param  s  - String to be capitalized
	 * @return      The capitalized string
	 */
	 public  String capitalize(String s) {
	    	s = Character.toUpperCase(s.charAt(0))+s.substring(1);
	        return s;
	 }

	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{
		 this.base_configs = base_configs;
		 this.derived_configs = derived_configs;
		 this.the_project = the_project;


	 }

	 public void generate_code_by_activity(GpActivity activity, GpProject the_project,
				HashMap<String,GpArchitypeConfigurations> base_configs,
				HashMap<String, GpArchitypeConfigurations> derived_configs,
										GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{

	 }

	 /**
	  * this is meant to be overwritten by child classes
	  * @throws Exception
	  */
	 public void prep_derived_values(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{
		 this.base_configs = base_configs;
		 this.derived_configs = derived_configs;
		 this.the_project = the_project;
	 }

	 public String[] tokenize_string(String the_string, String the_regex){

		 if(the_regex == null){
			 the_regex = new String("\\.");
		 }
		String[] tokens = null;
		tokens = the_string.split(the_regex);

		 return tokens;
	 }

	 public String build_name_from_tokens(String[] tokens){

		 String the_name = "";
		 int i = 0;
		 for(String token : tokens){
			 if(i == 0){
				 the_name = token;
			 }else{
				 the_name = the_name + "_" + token;
			 }
			 i++;
		 }


		 return the_name;
	 }

	 public ST read_template_group(Path file_path, String root_template ){

		 STGroup group = new STGroupFile(file_path.toString(), '$', '$');
		 ST st = group.getInstanceOf(root_template);

		 return st;
	 }

	 public void write_file(Path file_path, ST st) throws Exception{
		 Path newFile = Files.createFile(file_path);
		 //Files a_file = new Files(file_path.toString(), "test.txt");
		 try(BufferedWriter writer = Files.newBufferedWriter(
			             newFile, Charset.forName("UTF-8"))){
				 	      writer.write(st.render());
				 	      writer.flush();
				 	    }catch(IOException exception){
				 	      System.out.println("Error writing to file " + file_path.toString());
				 	      exception.printStackTrace();
				 	    }
	 }


}
