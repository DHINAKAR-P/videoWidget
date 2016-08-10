package com.npb.gp.gen.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.interfaces.services.IGpBaseGenerationService;

/**
 * 
 * @author Dan Castillo
 * Date Created: 6/21/2014</br>
 * @since .35</p> 
 * 
 * The purpose of this class is to serve as the base class for generations services</br>
 * 
 *
 *		  Modified Date: 09/29/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *        
 *         Modified Date: 03/09/2016</br>
 *        Modified By: Kumaresan Perumal</br>
 *        <p>
 *       Here i added the code for spring boot jpa code generation
 *        </p>
 *
 *
 */
public class GpBaseGenerationService implements IGpBaseGenerationService {
	protected final Log logger = LogFactory.getLog(getClass());
	//protected IGpGenManager gen_manager;
	private GpActivityGenService activity_service;
	
	@Override
	public HashMap<String, GpArchitypeConfigurations> get_generation_configurations()
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		// TODO Auto-generated method stub

	}
	
	public void set_activity_service(GpActivityGenService activity_service) {
		this.activity_service = activity_service;
	}
	
	public GpActivityGenService getActivity_service() {
		return activity_service;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<GpTechProperties> get_tech_properties(IGpGenManager gen_manager){
		
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		
		
		ArrayList<GpTechProperties> tech_props;
		
		HashMap<String, GpArchitypeConfigurations> base_configs 
		= gen_manager.getBase_configs();

		GpArchitypeConfigurations tech_props_configs = base_configs.get(GpGenConstants.TECH_PROPERTY_LIST);
		tech_props = (ArrayList<GpTechProperties>)tech_props_configs.getObject_value();
		
		
		return  tech_props;
	}
	
	public GpTechProperties get_server_dev_language(GpProject the_project,
									ArrayList<GpTechProperties> tech_props ){
		
		for(GpTechProperties prop : tech_props){
			if(prop.getId() == the_project.getServer_dev_lang()){
				return prop;
			}
		}

		
		return null;
	}
	
	public GpTechProperties get_server_dev_framework(GpProject the_project,
											ArrayList<GpTechProperties> tech_props ){
		
		for(GpTechProperties prop : tech_props){
			if(prop.getId() == the_project.getServer_dev_framework()){
				return prop;
			}
		}

		return null;
	}
	
	
	public GpTechProperties get_client_dev_language(GpProject the_project,
			ArrayList<GpTechProperties> tech_props ){

		for(GpTechProperties prop : tech_props){
			//if(prop.getId() == the_project.getClient_dev_languages()){
			//	return prop;
			//}
		}


		return null;
	}

	public GpTechProperties get_client_dev_framework(GpProject the_project,
						ArrayList<GpTechProperties> tech_props ){
	
		for(GpTechProperties prop : tech_props){
			//if(prop.getId() == the_project.getClient_dev_frameworks()){
			//	return prop;
		//	}
		}
		
		return null;
	}
	
	//backend
	public boolean back_isJavaSpring;
	public boolean back_isJavaScriptNodeJSExpress;
	public boolean back_isJavaSpringBootJpa;
	//front
	public boolean front_isJavaScriptAngular;
	// DB Manager
	public boolean DB_isMySQL;
		
	public void check_project_type(IGpGenManager gen_manager){
		this.check_back(gen_manager);
		this.check_front(gen_manager);
		this.check_db_m(gen_manager);
	}
	private void check_db_m(IGpGenManager gen_manager) {
		// TODO Auto-generated method stub
		ArrayList<GpTechProperties> tech_props = this.get_tech_properties(gen_manager);
		long id_dbms = gen_manager.get_project().getServer_dbms();
		for(GpTechProperties prop : tech_props){
			if(prop.getId() == id_dbms){
				if(prop.getName().equals(GpGenConstants.GpDB_MYSQL)){
					DB_isMySQL = true;
				}
			}
		}
	}

	private void check_front(IGpGenManager gen_manager){
		ArrayList<GpTechProperties> tech_props = this
				.get_tech_properties(gen_manager);

		ArrayList<String> client_dev_langs = gen_manager.get_project().getClient_dev_languages();
		ArrayList<String> client_dev_frameworks =  gen_manager.get_project().getClient_dev_frameworks();
		if(client_dev_langs.isEmpty()){
			front_isJavaScriptAngular = true;
			return;
		}
		else{
			for(String client_dev_lang_id : client_dev_langs){
				long lang_id = new Long(client_dev_lang_id).longValue();
				for(GpTechProperties prop : tech_props){
					if(prop.getId() == lang_id){
						if(prop.getName().equals(GpGenConstants.GpServerDevLanguage_JS)){
							if(client_dev_frameworks.isEmpty()){
								front_isJavaScriptAngular = true;
								return;
							}
							else{
								for(String client_dev_framework_id : client_dev_frameworks){
									long framework_id = new Long(client_dev_framework_id).longValue();
									for(GpTechProperties prop2 : tech_props){
										if(prop2.getId() == framework_id){
											if(prop2.getName().equals(GpGenConstants.GpSeverClientDevFramework_AngularJS)){
												front_isJavaScriptAngular = true;
												return;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void check_back(IGpGenManager gen_manager){
		ArrayList<GpTechProperties> tech_props = this
				.get_tech_properties(gen_manager);

		GpTechProperties server_dev_lang_prop = this
			.get_server_dev_language(gen_manager.get_project(), tech_props);
		
		GpTechProperties server_dev_framework = this
			.get_server_dev_framework(gen_manager.get_project(), tech_props);
		//System.out.println("server_dev_lang_prop " + server_dev_lang_prop.getName());
		//System.out.println("server_dev_framework " + server_dev_framework.getName());
		if(server_dev_lang_prop.getName().equals(GpGenConstants.GpServerDevLanguage_JAVA_7) 
				|| server_dev_lang_prop.getName().equals(GpGenConstants.GpServerDevLanguage_JAVA_8)){
			if(server_dev_framework.getName().equals(GpGenConstants.GpSeverDevFramework_Spring)){
				back_isJavaSpring = true;
				return;
			}
		}
		if(server_dev_lang_prop.getName().equals(GpGenConstants.GpServerDevLanguage_Node)){
			if(server_dev_framework.getName().equals(GpGenConstants.GpServerDevFramework_Express)){
				back_isJavaScriptNodeJSExpress = true;
				return;
			}
		}
		if(server_dev_lang_prop.getName().equals(GpGenConstants.GpServerDevLanguage_JAVA_7) 
				|| server_dev_lang_prop.getName().equals(GpGenConstants.GpServerDevLanguage_JAVA_8)){
			if(server_dev_framework.getName().equals(GpGenConstants.GpServerDevFramework_Spring_Boot)){
				back_isJavaSpringBootJpa = true;
				return;
			}
		}
	}
}