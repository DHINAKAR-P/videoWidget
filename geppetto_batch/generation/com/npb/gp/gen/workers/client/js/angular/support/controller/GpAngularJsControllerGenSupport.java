package com.npb.gp.gen.workers.client.js.angular.support.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;
import com.npb.gp.gen.workers.client.js.angular.GpAngularControllerGenWorker;

/**
 * 
 * @author Dan Castillo
 * Date Created: 03/14/2014</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate the functions, based on activity verbs,</br>
 * in an AngularJS controller - it is designed to be called by the </br>
 * worker that handles the AngularJs controller generation</p> 
 */
@Component("GpAngularJsControllerGenSupport")
public class GpAngularJsControllerGenSupport {
	
	private GpAngularControllerGenWorker the_worker;
	private GpAngularGetAllValuesHandler get_all_values_handler;
	private GpAngularCreateHandler create_handler;
	private GpAngularUpdateHandler update_handler;
	private GpAngularDeleteHandler delete_handler;
	private GpAngularSearchHandler search_handler;
	private GpAngularSearchForUpdateHandler search_for_update_handler;
	private GpAngularGetNounByIdHandler get_noun_by_id_handler;
	private GpAngularComponentVerbHandler component_verb_handler;
	private String angular_controller_default_services = "$scope,$rootScope,$location,$window,$q,$http";
	private GpVerbsDao verbs_dao;
	private GpAngularTakePhotoHandler takePhoto_handler;
	private GpAngularVideoRecorderHandler video_recorder_handler;
	

	public String get_dependent_services_for_function(String other_dependencies){
		
		//System.out.println("In GpAngularJsControllerGenSupport - get_dependent_services");
		//TODO: this field is not in the database
		
		
		if(other_dependencies == null)
			other_dependencies = "";
		String[] temp = GpGenericRecordParserBuilder
				.parseDelimitedString(angular_controller_default_services + other_dependencies + ";");
		String dependent_services = "";
		for(String test : temp){
			dependent_services += " " + test + ",";
		}
		dependent_services = dependent_services.substring(0,dependent_services.length()-1);
		return dependent_services;
	}
	
	public String get_dependent_services(String other_dependencies){
		
		//System.out.println("In GpAngularJsControllerGenSupport - get_dependent_services");
		//TODO: this field is not in the database
		if(other_dependencies == null)
			other_dependencies = "";
		String[] temp = GpGenericRecordParserBuilder
				.parseDelimitedString(angular_controller_default_services + other_dependencies + ";");
		String dependent_services = "";
		for(String test : temp){
			dependent_services += " '" + test + "',";
		}
		dependent_services = dependent_services.substring(0,dependent_services.length()-1);
		return dependent_services;
	}
	
	public String get_noun_attributes(GpActivity activity) {

		String noun_attributes = "";		
		noun_attributes = noun_attributes + "id: '',\n";
		int i = 0;
		if(activity.getPrimary_noun() != null){
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun()
					.getNounattributes();
			for (GpNounAttribute a : attributes) {
				if (i == attributes.size() - 1) {
					noun_attributes = noun_attributes + "" + a.getName().toLowerCase() + " : ''";				
				}else{
					noun_attributes = noun_attributes + "" + a.getName().toLowerCase() + " : '', \n";
				}			
				i++;
			}		
			}
		return noun_attributes;
	}	
	
	public ArrayList<GpAngularControllerGenDto> 
							get_verb_method_implmentation(GpActivity activity, String client_device_type_os_name, String client_device_type) throws Exception{
		
		//System.out.println("In GpAngularJsControllerGenSupport - get_verb_method_implmentation");
		ArrayList<GpAngularControllerGenDto> dto_list = new ArrayList<GpAngularControllerGenDto> ();
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		List<GpVerb> component_verbs = this.verbs_dao.get_other_verbs_by_activity_id(activity.getId(), the_verbs);
		if(component_verbs != null && !component_verbs.isEmpty()){
			//System.out.println("Component verbs added");
			the_verbs.addAll(component_verbs);
		}
		List<Long> verbs_for_this_client = verbs_dao.get_verbs_by_client_device_type(activity.getId(), client_device_type, client_device_type_os_name);
		for(GpVerb a_verb : the_verbs){
			if(verbs_for_this_client.contains(a_verb.getId())){
				if(a_verb.getAction_on_data().equals("GpGetAllValues")){
					GpAngularControllerGenDto dto = this.get_all_values_handler
															.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpGetNounById")){
					GpAngularControllerGenDto dto = this.get_noun_by_id_handler
															.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpSearchForUpdate")){
					GpAngularControllerGenDto dto = this.search_for_update_handler
															.handle_verb(a_verb, activity);
					
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpCreate")){
					
					GpAngularControllerGenDto dto = this.create_handler
														.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpUpdate")){
					GpAngularControllerGenDto implicit_dto = this.search_for_update_handler
							.handle_verb(a_verb, activity);
	
					dto_list.add(implicit_dto);
					GpAngularControllerGenDto dto = this.update_handler
														.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpDelete")){
					GpAngularControllerGenDto dto = this.delete_handler
														.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				if(a_verb.getAction_on_data().equals("GpSearch")){
					GpAngularControllerGenDto dto = this.search_handler
											.handle_verb(a_verb, activity);
					dto_list.add(dto);
				}
				if(a_verb.getAction_on_data().equals("GpCustom")){
					//dont do anything for now - 03/14/2015
					continue;
				}
				System.out.println("----dhina- Action---"+a_verb.getAction_on_data());
				if(a_verb.getAction_on_data().equals("GpTakePhoto")){
					GpAngularControllerGenDto dto = this.takePhoto_handler
							.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				System.out.println("----video_recorder_handler- Action---"+a_verb.getAction_on_data());
				if(a_verb.getAction_on_data().equals("GpRecordVideo")){
					GpAngularControllerGenDto dto = this.video_recorder_handler
							.handle_verb(a_verb, activity);
					dto_list.add(dto);
					continue;
				}
				
			}
			if(a_verb.getAction_on_data().equals("GpComponentVerb")){
				//verb from a component
				GpAngularControllerGenDto dto = this.component_verb_handler
						.handle_verb(a_verb, activity);
				dto_list.add(dto);
				continue;
			}
		}
		return dto_list;
		
	}
	
	
	public GpVerbsDao getVerbs_dao() {
		return verbs_dao;
	}
	
	public GpAngularComponentVerbHandler getComponent_verb_handler() {
		return component_verb_handler;
	}
	
	@Resource(name="GpAngularComponentVerbHandler")
	public void setComponent_verb_handler(GpAngularComponentVerbHandler component_verb_handler) {
		this.component_verb_handler = component_verb_handler;
	}
	
	@Resource(name="GpVerbsDao")
	public void setVerbs_dao(GpVerbsDao verbs_dao) {
		this.verbs_dao = verbs_dao;
	}
	public GpAngularGetNounByIdHandler getGet_noun_by_id_handler() {
		return get_noun_by_id_handler;
	}
	@Resource(name="GpAngularGetNounByIdHandler")
	public void setGet_noun_by_id_handler(
			GpAngularGetNounByIdHandler get_noun_by_id_handler) {
		this.get_noun_by_id_handler = get_noun_by_id_handler;
	}
	public GpAngularSearchForUpdateHandler getSearch_for_update_handler() {
		return search_for_update_handler;
	}
	@Resource(name="GpAngularSearchForUpdateHandler")
	public void setSearch_for_update_handler(
			GpAngularSearchForUpdateHandler search_for_update_handler) {
		this.search_for_update_handler = search_for_update_handler;
	}
	public GpAngularSearchHandler getSearch_handler() {
		return search_handler;
	}
	@Resource(name="GpAngularSearchHandler")
	public void setSearch_handler(GpAngularSearchHandler search_handler) {
		this.search_handler = search_handler;
	}
	public GpAngularDeleteHandler getDelete_handler() {
		return delete_handler;
	}
	@Resource(name="GpAngularDeleteHandler")
	public void setDelete_handler(GpAngularDeleteHandler delete_handler) {
		this.delete_handler = delete_handler;
	}
	public GpAngularUpdateHandler getUpdate_handler() {
		return update_handler;
	}
	@Resource(name="GpAngularUpdateHandler")
	public void setUpdate_handler(GpAngularUpdateHandler update_handler) {
		this.update_handler = update_handler;
	}
	public GpAngularCreateHandler getCreate_handler() {
		return create_handler;
	}
	@Resource(name="GpAngularCreateHandler")
	public void setCreate_handler(GpAngularCreateHandler create_handler) {
		this.create_handler = create_handler;
	}
	
	public GpAngularGetAllValuesHandler getGet_all_values_handler() {
		return get_all_values_handler;
	}
	@Resource(name="GpAngularGetAllValuesHandler")
	public void setGet_all_values_handler(
			GpAngularGetAllValuesHandler get_all_values_handler) {
		this.get_all_values_handler = get_all_values_handler;
	}
	public GpAngularControllerGenWorker getThe_worker() {
		return the_worker;
	}
	
	public GpAngularTakePhotoHandler getTakePhoto_handler() {
		return takePhoto_handler;
	}

	@Resource(name="GpAngularTakePhotoHandler")
	public void setTakePhoto_handler(GpAngularTakePhotoHandler takePhoto_handler) {
		this.takePhoto_handler = takePhoto_handler;
	}

	public void setThe_worker(GpAngularControllerGenWorker the_worker) {
		this.the_worker = the_worker;
		this.create_handler.setThe_worker(the_worker);
		this.search_handler.setThe_worker(the_worker);
		this.delete_handler.setThe_worker(the_worker);
		this.update_handler.setThe_worker(the_worker);
		this.search_for_update_handler.setThe_worker(the_worker);
		this.component_verb_handler.setThe_worker(the_worker);
	}

	public GpAngularVideoRecorderHandler getVideo_recorder_handler() {
		return video_recorder_handler;
	}
	@Resource(name="GpAngularVideoRecorderHandler")
	public void setVideo_recorder_handler(GpAngularVideoRecorderHandler video_recorder_handler) {
		this.video_recorder_handler = video_recorder_handler;
	}

}
