package com.npb.gp.gen.workers.client.js.angular;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.dao.mysql.TemplateDependenciesDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleMobileInfo;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
@Component("GpAngularAppJsGenWorker")
public class GpAngularAppJsGenWorker extends GpGenJSClientAngularBaseWorker{
	private Path template_group_path;  //path to the template used to generate appjs file
	private Path template_group_path_desktop;
	private String file_name = "app";
	private String file_extension = ".js";
	private String code_to_add_desktop;
	private String code_to_add_android_phone;
	private String code_to_add_android_tablet;
	private String code_to_add_ios_phone;
	private String code_to_add_ios_tablet;
	private String app_name;
	private String code_to_add_inside_config;
	private List<String> config_properties = new ArrayList<>();
	private Map<String, String> angular_dependencies_map = new HashMap<String, String>();
	private String mobile_run_parameters = "";
	private String code_to_add_inside_app_js_run_mobile;
	private String code_to_add_inside_app_js_run_ionic_ready_mobile;
	private String default_route_desktop;
	private String default_route_mobile;
	private TemplateDependenciesDao templateDependenciesDao;

	public TemplateDependenciesDao getTemplateDependenciesDao() {
		return templateDependenciesDao;
	}

	@Resource(name = "TemplateDependenciesDao")
	public void setTemplateDependenciesDao(
			TemplateDependenciesDao templateDependenciesDao) {
		this.templateDependenciesDao = templateDependenciesDao;
	}

	public void setDefault_route_desktop(String default_route_desktop) {
		this.default_route_desktop = default_route_desktop;
	}
	public void setDefault_route_mobile(String default_route_mobile) {
		this.default_route_mobile = default_route_mobile;
	}
	public Map<String, String> getAngular_dependencies_map() {
		return angular_dependencies_map;
	}

	public void add_dependencies(String dependency, String device_type_os, String device_screen_type){
		String angular_dependencies = angular_dependencies_map.get(device_type_os + "-" + device_screen_type);
		if(angular_dependencies == null)
			angular_dependencies = "";
		angular_dependencies += ",'" + dependency + "'";
		angular_dependencies_map.put(device_type_os + "-" + device_screen_type, angular_dependencies);
	}

	public void add_code_inside_config(String code){
		if(code_to_add_inside_config != null)
			code_to_add_inside_config += code;
		else
			code_to_add_inside_config = code;
	}

	public void add_config_property(String property){
		config_properties.add(property);
	}

	public String get_config_properties_with_cuotes(){
		String properties = "";
		for(String property : config_properties){
			properties += ",'" + property + "',";
		}
		if(!properties.isEmpty())
			properties = properties.substring(0,properties.length()-1);
		return properties;
	}

	public String get_config_properties_without_cuotes(){
		String properties = "";
		for(String property : config_properties){
			properties += "," + property + ",";
		}
		if(!properties.isEmpty())
			properties = properties.substring(0,properties.length()-1);
		return properties;
	}

	public void add_code(String code_to_add, String device){
		if (device.equals(GpGenConstants.GpClientOS_WINDOWS + "_" + GpGenConstants.GpClientScreenType_desktop)) {
			if(this.code_to_add_desktop == null){
				this.code_to_add_desktop = code_to_add;
			}
			else{
				this.code_to_add_desktop += code_to_add;
			}
		} else if (device.equals(GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_phone)) {
			if(this.code_to_add_android_phone == null){
				this.code_to_add_android_phone = code_to_add;
			}
			else{
				this.code_to_add_android_phone += code_to_add;
			}
		} else if (device.equals(GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_tablet)) {
			if(this.code_to_add_android_tablet == null){
				this.code_to_add_android_tablet = code_to_add;
			}
			else{
				this.code_to_add_android_tablet += code_to_add;
			}
		} else if (device.equals(GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_phone)) {
			if(this.code_to_add_ios_phone == null){
				this.code_to_add_ios_phone = code_to_add;
			}
			else{
				this.code_to_add_ios_phone += code_to_add;
			}
		} else if (device.equals(GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_tablet)) {
			if(this.code_to_add_ios_tablet == null){
				this.code_to_add_ios_tablet = code_to_add;
			}
			else{
				this.code_to_add_ios_tablet += code_to_add;
			}
		}
	}

	public void generate_code(GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, Map<Long, GpClientDeviceTypes> map, IGpGenManager gen_manager)	throws Exception{


	System.out.println("####### $$$$$$ ****** In GpAngularAppJsGenWorker - generate_code_by_activity");

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		app_name = the_project.getName();
		this.set_up_path_for_gen_templates();
		this.do_generation(gen_manager);
	}

	public void do_generation(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		if(get_generation_service().getDirectory_gen_worker().android_created){
			generate_appjs_for_android_tablet(get_generation_service().getHtml_worker().getThe_dto_android_tablet());
			generate_appjs_for_android_phone(get_generation_service().getHtml_worker().getThe_dto_android_phone());
		}
		if(get_generation_service().getDirectory_gen_worker().ios_created){
			generate_appjs_for_ios_tablet(get_generation_service().getHtml_worker().getThe_dto_ios_tablet());
			generate_appjs_for_ios_phone(get_generation_service().getHtml_worker().getThe_dto_ios_phone());
		}
		if(get_generation_service().getDirectory_gen_worker().desktop_created){
			generate_appjs_for_desktop(get_generation_service().getHtml_worker().getThe_dto_desktop());
		}
	}
	
	private void generate_appjs_for_android_tablet(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			st.add("app_name", app_name);
			st.add("the_states", the_dto);
			if(!the_dto.isEmpty())
				st.add("default_view", "$urlRouterProvider.otherwise('/app/"+ the_dto.get(0).viewName +"');");
			st.add("code_to_add", this.code_to_add_android_tablet);
			String angular_dependencies = angular_dependencies_map.get(GpGenConstants.GpClientOS_ANDROID + "-" + GpGenConstants.GpClientScreenType_tablet);
			if(angular_dependencies == null)
				angular_dependencies = "";
			st.add("angular_dependencies", angular_dependencies);
			String views_path = get_generation_service().getDirectory_gen_worker().getAndroid_tablet_app_root_js_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);

	}
	private void generate_appjs_for_android_phone(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			st.add("app_name", app_name);
			st.add("the_states", the_dto);
			if(default_route_mobile != null)
				st.add("default_view", "$urlRouterProvider.otherwise('"+ default_route_mobile +"');");
			else if(!the_dto.isEmpty())
				st.add("default_view", "$urlRouterProvider.otherwise('/app/"+ the_dto.get(0).viewName +"');");
			st.add("code_to_add", this.code_to_add_android_phone);
			String angular_dependencies = angular_dependencies_map.get(GpGenConstants.GpClientOS_ANDROID + "-" + GpGenConstants.GpClientScreenType_phone);
			if(angular_dependencies == null)
				angular_dependencies = "";
			List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			if(code_to_add_inside_app_js_run_mobile == null){
				code_to_add_inside_app_js_run_mobile = "";
			}
			if(code_to_add_inside_app_js_run_ionic_ready_mobile == null){
				code_to_add_inside_app_js_run_ionic_ready_mobile = "";
			}
			String code_to_add_inside_app_js_run_ionic_ready_mobile_2 = "";
			for(GpModuleProperties module : modules){
				GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
				if(mobile_info != null){
					String[] module_angular_dependencies = mobile_info.getAngular_dependencies();
					for(String angular_dependencie : module_angular_dependencies){
						angular_dependencies += ",'" + angular_dependencie + "'";
					}
					String[] run_paramaters = mobile_info.getApp_js_run_parameters();
					for(String run_paramater : run_paramaters){
						this.mobile_run_parameters += "," + run_paramater;
					}
					code_to_add_inside_app_js_run_mobile += "\n" + mobile_info.getCode_to_add_inside_app_js_run();
					code_to_add_inside_app_js_run_ionic_ready_mobile_2 += "\n" + mobile_info.getCode_to_add_inside_app_js_ionic_ready();
				}
 			}
			st.add("angular_dependencies", angular_dependencies);
			st.add("run_parameters", this.mobile_run_parameters);
			st.add("code_to_add_inside_app_js_run", this.code_to_add_inside_app_js_run_mobile);
			st.add("code_to_add_inside_app_js_ionic_ready", this.code_to_add_inside_app_js_run_ionic_ready_mobile + code_to_add_inside_app_js_run_ionic_ready_mobile_2);
			String views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_root_js_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
	}
	private void generate_appjs_for_ios_tablet(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			st.add("app_name", app_name);
			st.add("the_states", the_dto);
			if(!the_dto.isEmpty())
				st.add("default_view", "$urlRouterProvider.otherwise('/app/"+ the_dto.get(0).viewName +"');");
			st.add("code_to_add", this.code_to_add_ios_tablet);
			String angular_dependencies = angular_dependencies_map.get(GpGenConstants.GpClientOS_IOS + "-" + GpGenConstants.GpClientScreenType_tablet);
			if(angular_dependencies == null)
				angular_dependencies = "";
			st.add("angular_dependencies", angular_dependencies);
			String views_path = get_generation_service().getDirectory_gen_worker().getIos_tablet_app_root_js_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
	}
	private void generate_appjs_for_ios_phone(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			st.add("the_states", the_dto);
			st.add("app_name", app_name);
			if(default_route_mobile != null)
				st.add("default_view", "$urlRouterProvider.otherwise('"+ default_route_mobile +"');");
			else if(!the_dto.isEmpty())
				st.add("default_view", "$urlRouterProvider.otherwise('/app/"+ the_dto.get(0).viewName +"');");
			st.add("code_to_add", this.code_to_add_ios_phone);
			String angular_dependencies = angular_dependencies_map.get(GpGenConstants.GpClientOS_IOS + "-" + GpGenConstants.GpClientScreenType_phone);
			if(angular_dependencies == null)
				angular_dependencies = "";
			List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			if(code_to_add_inside_app_js_run_mobile == null){
				code_to_add_inside_app_js_run_mobile = "";
			}
			if(code_to_add_inside_app_js_run_ionic_ready_mobile == null){
				code_to_add_inside_app_js_run_ionic_ready_mobile = "";
			}
			String code_to_add_inside_app_js_run_ionic_ready_mobile_2 = "";
			for(GpModuleProperties module : modules){
				GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
				if(mobile_info != null){
					String[] module_angular_dependencies = mobile_info.getAngular_dependencies();
					for(String angular_dependencie : module_angular_dependencies){
						angular_dependencies += ",'" + angular_dependencie + "'";
					}
					//String[] run_paramaters = mobile_info.getApp_js_run_parameters();
					String[] run_paramaters = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info().getApp_js_run_parameters();
					for(String run_paramater : run_paramaters){
						this.mobile_run_parameters += "," + run_paramater;
					}
					//code_to_add_inside_app_js_run_mobile += "\n" + mobile_info.getCode_to_add_inside_app_js_run();
					//code_to_add_inside_app_js_run_ionic_ready_mobile_2 += "\n" + mobile_info.getCode_to_add_inside_app_js_ionic_ready();
					code_to_add_inside_app_js_run_mobile += "\n" + module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info().getCode_to_add_inside_app_js_run();
					code_to_add_inside_app_js_run_ionic_ready_mobile_2 += "\n" + module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info().getCode_to_add_inside_app_js_ionic_ready();
				}
 			}
			st.add("angular_dependencies", angular_dependencies);
			st.add("run_parameters", this.mobile_run_parameters);
			st.add("code_to_add_inside_app_js_run", this.code_to_add_inside_app_js_run_mobile);
			st.add("code_to_add_inside_app_js_ionic_ready", this.code_to_add_inside_app_js_run_ionic_ready_mobile + code_to_add_inside_app_js_run_ionic_ready_mobile_2);
			String views_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_root_js_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);

	}
	private void generate_appjs_for_desktop(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path_desktop, "output");
			st.add("app_name", app_name);
			st.add("the_states", the_dto);
			String angular_dependencies_from_components = "";
			List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			String angular_dependencies = angular_dependencies_map.get(GpGenConstants.GpClientOS_WINDOWS + "-" + GpGenConstants.GpClientScreenType_desktop);
			if(angular_dependencies == null)
				angular_dependencies = "";
			if(!list.isEmpty()){
				for(GpModuleProperties properties : list){
					String []array_angular_dependencies = properties.getClient_meta_data().getJava_script().getAngular_js().getAngular_dependencies();
					for(String angular_dependencie : array_angular_dependencies){
						angular_dependencies_from_components += ",'" + angular_dependencie + "'";
					}
					if(this.code_to_add_desktop != null)
						this.code_to_add_desktop += "\n" + properties.getClient_meta_data().getJava_script().getAngular_js().getCode_to_add_to_app_js();
					else
						this.code_to_add_desktop = properties.getClient_meta_data().getJava_script().getAngular_js().getCode_to_add_to_app_js();
					add_code_inside_config(properties.getClient_meta_data().getJava_script().getAngular_js().getCode_to_add_inside_app_js_config());
					String config_properties[] = properties.getClient_meta_data().getJava_script().getAngular_js().getAngular_config_properties();
					if(config_properties != null){
						for(int i=0;i<config_properties.length;i++){
							add_config_property(config_properties[i]);
						}
					}
				}
			}
			String templateNameSelectedByUser = this.get_generation_service().getTemplateName();
			//System.out.println("templateNameSelectedByUser : " + templateNameSelectedByUser);
			String appModuleDependencies = 	this.templateDependenciesDao.getDependenciesByComponentType
					("js", templateNameSelectedByUser,"app_module_dep");
			st.add("app_config_parameters", get_config_properties_with_cuotes());
			st.add("app_config_parameters2", get_config_properties_without_cuotes());
			st.add("angular_dependencies", angular_dependencies + angular_dependencies_from_components + appModuleDependencies);
			if(default_route_desktop != null)
				st.add("default_view", ".otherwise('"+ default_route_desktop +"');");
			else if(!the_dto.isEmpty())
				st.add("default_view", ".otherwise( {templateUrl : 'app/views/"+ the_dto.get(0).viewLocation + "'" + the_dto.get(0).controller+"});");
			st.add("code_to_add", this.code_to_add_desktop);
			st.add("code_to_add_inside_config", code_to_add_inside_config);
			String views_path = get_generation_service().getDirectory_gen_worker().getWindows_app_root_js_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
	}

	private void set_up_path_for_gen_templates(){
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String angular_index_file_template_location = this.base_configs
				.get("angular_controller_template_location").getValue();

		tokens = this.tokenize_string(
								angular_index_file_template_location, null);
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
		}

		String template_name =  "app_js_v_1.stg";
		String template_name_desktop =  "app_desktop_js_v_1.stg";
		template_group_path =
				Paths.get(core_template_location_temp + this.file_separator + template_name);
		template_group_path_desktop =
				Paths.get(core_template_location_temp + this.file_separator + template_name_desktop);
	}
}
