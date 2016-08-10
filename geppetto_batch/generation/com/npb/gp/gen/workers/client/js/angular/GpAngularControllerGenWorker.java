package com.npb.gp.gen.workers.client.js.angular;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GpGenMicroFlowDao;
import com.npb.gp.gen.domain.js.node.express.ExpressRouterDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.DockerInfo;
import com.npb.gp.gen.json.mappers.dockerinfo.DockerImage;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;
import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;
import com.npb.gp.gen.util.dto.GpControllerVerbGenInfo;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.support.controller.GpAngularJsControllerGenSupport;
/**
 *
 * @author Dan Castillo
 * Date Created: 02/07/2015</br>
 * @since .2</p>
 *
 *  worker to handle the generation of AngularJS controllers for a Geppetto app</p>
 *
 *
 */
@Component("GpAngularControllerGenWorker")
public class GpAngularControllerGenWorker extends
							GpGenJSClientAngularBaseWorker {

	public String project_name;
	public String created_by;
	public String server_url;
	public String server_port;
	public Map<String, String> map_url_services;
	private Path template_group_path;  //path to the template used to generate controller file
	private GpAngularJsControllerGenSupport gen_support;
	private GpGenMicroFlowDao micro_flow_dao;
	private GpVerbsDao verbs_dao;
	private String dependent_services_for_desktop;
	private String dependent_services_for_android_phone;
	private String dependent_services_for_android_tablet;
	private String dependent_services_for_ios_phone;
	private String dependent_services_for_ios_tablet;

	public void add_dependent_services_for_desktop(String dependent_services){
		if(dependent_services_for_desktop == null)
			dependent_services_for_desktop = dependent_services;
		else
			dependent_services_for_desktop += dependent_services;
	}
	public void add_dependent_services_for_android_phone(String dependent_services){
		if(dependent_services_for_android_phone == null)
			dependent_services_for_android_phone = dependent_services;
		else
			dependent_services_for_android_phone += dependent_services;
	}
	public void add_dependent_services_for_android_tablet(String dependent_services){
		if(dependent_services_for_android_tablet == null)
			dependent_services_for_android_tablet = dependent_services;
		else
			dependent_services_for_android_tablet += dependent_services;
	}
	public void add_dependent_services_for_ios_phone(String dependent_services){
		if(dependent_services_for_ios_phone == null)
			dependent_services_for_ios_phone = dependent_services;
		else
			dependent_services_for_ios_phone += dependent_services;
	}
	public void add_dependent_services_for_ios_tablet(String dependent_services){
		if(dependent_services_for_ios_tablet == null)
			dependent_services_for_ios_tablet = dependent_services;
		else
			dependent_services_for_ios_tablet += dependent_services;
	}

	public GpAngularJsControllerGenSupport getGen_support() {
		return gen_support;
	}
	@Resource(name="GpAngularJsControllerGenSupport")
	public void setGen_support(GpAngularJsControllerGenSupport gen_support) {
		this.gen_support = gen_support;
	}
	public GpVerbsDao getVerbs_dao() {
		return verbs_dao;
	}
	@Resource(name="GpVerbsDao")
	public void setVerbs_dao(GpVerbsDao verbs_dao) {
		this.verbs_dao = verbs_dao;
	}

	public GpGenMicroFlowDao getMicro_flow_dao() {
		return micro_flow_dao;
	}

	@Resource(name="GpGenMicroFlowDao")
	public void setMicro_flow_dao(GpGenMicroFlowDao micro_flow_dao) {
		this.micro_flow_dao = micro_flow_dao;
	}


	@Override
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

		//System.out.println("In GpAngularControllerGenWorker - generate_code");


		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;


		this.set_up_paths();
		//this.do_generation();

	}


	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{


		//System.out.println("In GpAngularControllerGenWorker - generate_code_by_activity");



		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;


		this.set_up_paths();
		this.do_generation(activity, gen_manager);

	}
	public void do_generation(GpActivity activity, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();


		this.prep_values_for_verb_workers(activity, gen_manager);

		if(super.get_generation_service().getDirectory_gen_worker().android_created){
			//gen_manager.update_job_status(project_id, user_id, username, "gen_android_phone_angular_controller-GpAngularControllerGenWorker", "gen_processing");
			this.handle_gen_for_android_for_phone(activity);
			//gen_manager.update_job_status(project_id, user_id, username, "gen_android_tablet_angular_controller-GpAngularControllerGenWorker", "gen_processing");
			this.handle_gen_for_android_for_tablet(activity);
		}
		if(super.get_generation_service().getDirectory_gen_worker().ios_created){
			//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_phone_angular_controller-GpAngularControllerGenWorker", "gen_processing");
			this.handle_gen_for_ios_phone(activity);
			//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_tablet_angular_controller-GpAngularControllerGenWorker", "gen_processing");
			this.handle_gen_for_ios_tablet(activity);
		}
		if(super.get_generation_service().getDirectory_gen_worker().desktop_created){
			//gen_manager.update_job_status(project_id, user_id, username, "gen_windows_angular_controller-GpAngularControllerGenWorker", "gen_processing");
			this.handle_gen_for_windows(activity);
		}
	}

	private void prep_values_for_verb_workers(GpActivity activity, IGpGenManager gen_manager) throws Exception{
		project_name = the_project.getName();
		created_by = the_project.getCreatedby() + "";
		server_url = this.base_configs.get("server_host_name").getValue();
		map_url_services = new HashMap<>();
		if(get_generation_service().getActivity_service().back_isJavaSpring){
			server_port = GpServerJavaSpringGenService.server_port;
			GpServerJavaSpringGenService java_spring_service = get_generation_service().getActivity_service().getServer_java_spring_gen_service();
			Map<String,GpControllerVerbGenInfo> map_routes_java_spring = java_spring_service.getController_gen_worker().getController_support().get_map_controller(activity, java_spring_service);
			Set<String> set = map_routes_java_spring.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				map_url_services.put(verb_action, map_routes_java_spring.get(verb_action).method_name);
			}
		}
		if(get_generation_service().getActivity_service().back_isJavaScriptNodeJSExpress){
			String docker_json = gen_manager.get_user().getDocker_json();
			if(docker_json != null){
				JSONArray json_array = new JSONArray(docker_json);
				for(int i=0;i<json_array.length();i++){
					JSONObject json = json_array.getJSONObject(i);
					String cont_name = json.getString("contname");
					if(cont_name.startsWith("nodejs")){
						 server_port = json.getString("port");
						 break;
					}
				}
			}
			else{
				server_port = "SERVER_PORT";
			}
			Map<String,ExpressRouterDescription> map_routes_node = get_generation_service().getActivity_service().getServer_node_express_gen_service().getThe_routes_worker().getThe_gen_support().get_the_routes_map(activity);
			Set<String> set = map_routes_node.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				map_url_services.put(verb_action, map_routes_node.get(verb_action).string_route_path);
			}
		}
		if(get_generation_service().getActivity_service().back_isJavaSpringBootJpa){
			String docker_json = gen_manager.get_user().getDocker_json();
			if(docker_json != null){
				JSONArray json_array = new JSONArray(docker_json);
				for(int i=0;i<json_array.length();i++){
					JSONObject json = json_array.getJSONObject(i);
					String cont_name = json.getString("contname");
					if(cont_name.startsWith("tomcat")){
						 server_port = json.getString("port");
						 break;
					}
				}
			}
			else{
				server_port = "SERVER_PORT";
			}

			GpServerJavaSpringBootGenService java_spring_boot_service = get_generation_service().getActivity_service().getServer_java_spring_boot_gen_service();
			Map<String,GpControllerSpringBootVerbGenInfo> map_routes_java_spring = java_spring_boot_service.getSpring_boot_controller().getController_spring_boot_support().get_the_map_methods(activity, java_spring_boot_service, null);
			Set<String> set = map_routes_java_spring.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				System.out.println(verb_action);
				map_url_services.put(verb_action, map_routes_java_spring.get(verb_action).reference_request_map_value);
			}
		}
	}

	private void handle_gen_for_ios_tablet(GpActivity activity) throws Exception {
		Path tablet_path = super.get_generation_service()
				.getDirectory_gen_worker().getIos_tablet_app_controller_directory_path();
		ST st = this.set_up_template_code_for_ios_tablet(activity);

		String file_extension =  "js";

		String the_path_string = tablet_path.toString()
			+ this.file_separator + activity.getName() +"." + file_extension
													+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	private ST set_up_template_code_for_ios_tablet(GpActivity activity) throws Exception {
		ArrayList<GpAngularControllerGenDto>  the_verb_code = this.gen_support
				.get_verb_method_implmentation(activity,GpGenConstants.GpClientOS_IOS,GpGenConstants.GpClientScreenType_tablet);

		String noun_attributes = this.gen_support.get_noun_attributes(activity);

		ST st = super.read_template_group(this.template_group_path, "output");


		st.add("app_name", this.the_project.getName());
		st.add("controller_name", activity.getName());
		st.add("copy_right_range", "copy_right_range");
		if(activity.getPrimary_noun() != null){
			String noun_string = "$scope." + activity.getPrimary_noun().getName() + " = {\n";
			noun_string += noun_attributes + "\n";
			noun_string += "};";
			st.add("noun", noun_string);
		}
		String code_to_add = get_generation_service().getHtml_worker().getCode_to_add_to_controllers().get(activity.getName() + "-" + GpGenConstants.GpClientOS_IOS + "-" + GpGenConstants.GpClientScreenType_tablet);
		if(code_to_add != null)
			st.add("code_from_screens",code_to_add);
		st.add("dependent_services", this.gen_support.get_dependent_services(dependent_services_for_ios_tablet));
		st.add("dependent_services_f", this.gen_support.get_dependent_services_for_function(dependent_services_for_ios_tablet));
		st.add("the_gen_verbs", the_verb_code);

		return st;
	}
	private void handle_gen_for_ios_phone(GpActivity activity) throws Exception {
		Path phone_path = super.get_generation_service()
				.getDirectory_gen_worker().getIos_phone_app_controller_directory_path();
				//.getAndroid_phone_app_controller_directory_path();


		ST st = this.set_up_template_code_for_ios_phone(activity);

		String file_extension =  "js";

		String the_path_string = phone_path.toString()
			+ this.file_separator + activity.getName() +"." + file_extension
													+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

	}
	private ST set_up_template_code_for_ios_phone(GpActivity activity) throws Exception {
		ArrayList<GpAngularControllerGenDto>  the_verb_code = this.gen_support
				.get_verb_method_implmentation(activity,GpGenConstants.GpClientOS_IOS,GpGenConstants.GpClientScreenType_phone);

		String noun_attributes = this.gen_support.get_noun_attributes(activity);

		ST st = super.read_template_group(this.template_group_path, "output");


		st.add("app_name", this.the_project.getName());
		st.add("controller_name", activity.getName());
		st.add("copy_right_range", "copy_right_range");
		if(activity.getPrimary_noun() != null){
			String noun_string = "$scope." + activity.getPrimary_noun().getName() + " = {\n";
			noun_string += noun_attributes + "\n";
			noun_string += "};";
			st.add("noun", noun_string);
		}
		String code_to_add = get_generation_service().getHtml_worker().getCode_to_add_to_controllers().get(activity.getName() + "-" + GpGenConstants.GpClientOS_IOS + "-" + GpGenConstants.GpClientScreenType_phone);
		if(code_to_add != null)
			st.add("code_from_screens",code_to_add);
		st.add("dependent_services", this.gen_support.get_dependent_services(dependent_services_for_ios_phone));
		st.add("dependent_services_f", this.gen_support.get_dependent_services_for_function(dependent_services_for_ios_phone));
		st.add("the_gen_verbs", the_verb_code);

		return st;
	}
	private void handle_gen_for_android_for_tablet(GpActivity activity) throws Exception {
		Path tablet_path = super.get_generation_service()
				.getDirectory_gen_worker()
				.getAndroid_tablet_app_controller_directory_path();
		ST st = this.set_up_template_code_for_android_tablet(activity);

		String file_extension =  "js";

		String the_path_string = tablet_path.toString()
			+ this.file_separator + activity.getName() +"." + file_extension
													+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	private ST set_up_template_code_for_android_tablet(GpActivity activity) throws Exception {
		ArrayList<GpAngularControllerGenDto>  the_verb_code = this.gen_support
				.get_verb_method_implmentation(activity,GpGenConstants.GpClientOS_ANDROID,GpGenConstants.GpClientScreenType_tablet);

		String noun_attributes = this.gen_support.get_noun_attributes(activity);

		ST st = super.read_template_group(this.template_group_path, "output");


		st.add("app_name", this.the_project.getName());
		st.add("controller_name", activity.getName());
		st.add("copy_right_range", "copy_right_range");
		if(activity.getPrimary_noun() != null){
			String noun_string = "$scope." + activity.getPrimary_noun().getName() + " = {\n";
			noun_string += noun_attributes + "\n";
			noun_string += "};";
			st.add("noun", noun_string);
		}
		String code_to_add = get_generation_service().getHtml_worker().getCode_to_add_to_controllers().get(activity.getName() + "-" + GpGenConstants.GpClientOS_ANDROID + "-" + GpGenConstants.GpClientScreenType_tablet);
		if(code_to_add != null)
			st.add("code_from_screens",code_to_add);
		st.add("dependent_services", this.gen_support.get_dependent_services(dependent_services_for_android_tablet));
		st.add("dependent_services_f", this.gen_support.get_dependent_services_for_function(dependent_services_for_android_tablet));
		st.add("the_gen_verbs", the_verb_code);

		return st;
	}
	private void handle_gen_for_android_for_phone(GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		Path phone_path = super.get_generation_service()
				.getDirectory_gen_worker()
				.getAndroid_phone_app_controller_directory_path();


		ST st = this.set_up_template_code_for_android_phone(activity);

		String file_extension =  "js";

		String the_path_string = phone_path.toString()
			+ this.file_separator + activity.getName() +"." + file_extension
													+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	private ST set_up_template_code_for_android_phone(GpActivity activity) throws Exception {

		ArrayList<GpAngularControllerGenDto>  the_verb_code = this.gen_support
				.get_verb_method_implmentation(activity,GpGenConstants.GpClientOS_ANDROID,GpGenConstants.GpClientScreenType_phone);

		String noun_attributes = this.gen_support.get_noun_attributes(activity);

		ST st = super.read_template_group(this.template_group_path, "output");


		st.add("app_name", this.the_project.getName());
		st.add("controller_name", activity.getName());
		st.add("copy_right_range", "copy_right_range");
		if(activity.getPrimary_noun() != null){
			String noun_string = "$scope." + activity.getPrimary_noun().getName() + " = {\n";
			noun_string += noun_attributes + "\n";
			noun_string += "};";
			st.add("noun", noun_string);
		}
		String code_to_add = get_generation_service().getHtml_worker().getCode_to_add_to_controllers().get(activity.getName() + "-" + GpGenConstants.GpClientOS_ANDROID + "-" + GpGenConstants.GpClientScreenType_phone);
		if(code_to_add != null)
			st.add("code_from_screens",code_to_add);
		st.add("dependent_services", this.gen_support.get_dependent_services(dependent_services_for_android_phone));
		st.add("dependent_services_f", this.gen_support.get_dependent_services_for_function(dependent_services_for_android_phone));
		st.add("the_gen_verbs", the_verb_code);

		return st;
	}
	private ST set_up_base_template_code(GpActivity activity) throws Exception{

		ArrayList<GpAngularControllerGenDto>  the_verb_code = this.gen_support
									.get_verb_method_implmentation(activity,GpGenConstants.GpClientOS_WINDOWS,GpGenConstants.GpClientScreenType_desktop);

		String noun_attributes = this.gen_support.get_noun_attributes(activity);

		ST st = super.read_template_group(this.template_group_path, "output");


		st.add("app_name", this.the_project.getName());
		st.add("controller_name", activity.getName());
		st.add("copy_right_range", "copy_right_range");
		if(activity.getPrimary_noun() != null){
			String noun_string = "$scope." + activity.getPrimary_noun().getName() + " = {\n";
			noun_string += noun_attributes + "\n";
			noun_string += "};";
			st.add("noun", noun_string);
		}
		String code_to_add = get_generation_service().getHtml_worker().getCode_to_add_to_controllers().get(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
		if(code_to_add == null){
			code_to_add = "";
		}
		code_to_add += "\n";
		code_to_add += "$scope.$on('$viewContentLoaded', function(event) {" + "\n";
		code_to_add += "\t" + "var biggestHeight = 0;" + "\n";
		code_to_add += "\t" + "var height = 0;" + "\n";
		code_to_add += "\t" + "$(\".screen\").find('*').each(function(){" + "\n";
		code_to_add += "\t\t" + "height = $(this).position().top + $(this).height() + 100;" + "\n";
		code_to_add += "\t\t" + "if (height > biggestHeight ) {" + "\n";
		code_to_add += "\t\t\t" + "biggestHeight = height;" + "\n";
		code_to_add += "\t\t" + "}" + "\n";
		code_to_add += "\t" + "});" + "\n";
		code_to_add += "\t" + "$(\".screen\").height(biggestHeight);" + "\n";
		code_to_add += "" + "});" + "\n";
		st.add("code_from_screens",code_to_add);
		//Not in DB
		st.add("dependent_services", this.gen_support.get_dependent_services(dependent_services_for_desktop));
		st.add("dependent_services_f", this.gen_support.get_dependent_services_for_function(dependent_services_for_desktop));
		st.add("the_gen_verbs", the_verb_code);

		return st;

	}



	private void handle_gen_for_windows(GpActivity activity) throws Exception{
		Path app_controler_path = super.get_generation_service()
				.getDirectory_gen_worker().getWindows_app_controller_directory_path();
				//.getAndroid_phone_app_controller_directory_path();


		ST st = this.set_up_base_template_code(activity);

		String file_extension =  "js";

		String the_path_string = app_controler_path.toString()
			+ this.file_separator + activity.getName() +"." + file_extension
													+  this.file_separator;

		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);


	}
	private void set_up_paths() throws Exception{
		this.gen_support.setThe_worker(this);
		this.set_up_path_for_gen_templates();
		this.set_paths_for_generated_code();

	}

	private void set_paths_for_generated_code() throws Exception{


	}

	private void set_up_path_for_gen_templates(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String angular_index_file_template_location = this.base_configs
				.get("angular_controller_template_location").getValue();

		tokens = this.tokenize_string(
								angular_index_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens)
				+ "_angular_controller" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations angular_controller_template_location_path_config
											= this.derived_configs.get(config_name);

		if(angular_controller_template_location_path_config  == null){
			//create and store the path to the angularJS index.html file template
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;

				String template_name =  this.base_configs.get(
						"angular_controller_template_name").getValue();

				Path angular_controller_template_path =
						Paths.get(core_template_location_temp + this.file_separator + template_name);


				angular_controller_template_location_path_config = new GpArchitypeConfigurations();

				angular_controller_template_location_path_config.setName(config_name);
				angular_controller_template_location_path_config.setObject_value(angular_controller_template_path);
				this.derived_configs.put(config_name, angular_controller_template_location_path_config);

			}
		}

		this.template_group_path =
				(Path)angular_controller_template_location_path_config
														.getObject_value();

	}
	public String getDependent_services_for_desktop() {
		return dependent_services_for_desktop;
	}
	public String getDependent_services_for_android_phone() {
		return dependent_services_for_android_phone;
	}
	public String getDependent_services_for_android_tablet() {
		return dependent_services_for_android_tablet;
	}
	public String getDependent_services_for_ios_phone() {
		return dependent_services_for_ios_phone;
	}
	public String getDependent_services_for_ios_tablet() {
		return dependent_services_for_ios_tablet;
	}




}
