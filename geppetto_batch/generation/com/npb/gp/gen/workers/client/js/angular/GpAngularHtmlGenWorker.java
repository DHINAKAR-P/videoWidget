package com.npb.gp.gen.workers.client.js.angular;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.util.stream.*;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.dao.mysql.GpIsoLanguagesDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenSupport;
/**
 *
 * @author Dan Castillo
 * Date Created: 02/07/2015</br>
 * @since .2</p>
 *
 *  worker to handle the generation of HTML files that are used in an AngularJS app </p>
 *
 *
 */
@Component("GpAngularHtmlGenWorker")
public class GpAngularHtmlGenWorker extends GpGenJSClientAngularBaseWorker {

	private Path template_group_path;  //path to the template used to generate index file
	private Path html_gen_target_path; //this is where the generated html files are suppose to end up
	Map<Long, GpClientDeviceTypes> map_client_device_types;
	private GpScreenXDao screen_dao;
	private GpIsoLanguagesDao languages_dao;
	private List<GpAngularMenuGenDto> the_dto_android_tablet = new ArrayList<GpAngularMenuGenDto>();
	private List<GpAngularMenuGenDto> the_dto_android_phone = new ArrayList<GpAngularMenuGenDto>();
	private List<GpAngularMenuGenDto> the_dto_ios_phone = new ArrayList<GpAngularMenuGenDto>();
	private List<GpAngularMenuGenDto> the_dto_ios_tablet = new ArrayList<GpAngularMenuGenDto>();
	private List<GpAngularMenuGenDto> the_dto_desktop = new ArrayList<GpAngularMenuGenDto>();
	private Map<String, String> code_to_add_to_controllers = new HashMap<String, String>();
	private GpAngularJsHtmlGenSupport gen_support;
	private ArrayList<GpActivity> the_activities;

	public ArrayList<GpActivity> getThe_activities() {
		return the_activities;
	}

	public Map<String, String> getCode_to_add_to_controllers() {
		return code_to_add_to_controllers;
	}
	public Path getTemplate_group_path() {
		return template_group_path;
	}

	public Path getHtml_gen_target_path() {
		return html_gen_target_path;
	}

	public Map<Long, GpClientDeviceTypes> getMap_client_device_types() {
		return map_client_device_types;
	}

	public List<GpAngularMenuGenDto> getThe_dto_android_tablet() {
		return the_dto_android_tablet;
	}

	public List<GpAngularMenuGenDto> getThe_dto_android_phone() {
		return the_dto_android_phone;
	}

	public List<GpAngularMenuGenDto> getThe_dto_ios_phone() {
		return the_dto_ios_phone;
	}

	public List<GpAngularMenuGenDto> getThe_dto_ios_tablet() {
		return the_dto_ios_tablet;
	}

	public List<GpAngularMenuGenDto> getThe_dto_desktop() {
		return the_dto_desktop;
	}

	public GpAngularJsHtmlGenSupport getGen_support() {
		return gen_support;
	}

	@Resource(name="GpAngularJsHtmlGenSupport")
	public void setGen_support(GpAngularJsHtmlGenSupport gen_support) {
		this.gen_support = gen_support;
	}
	@Resource(name="GpIsoLanguagesDao")
	public void setLanguages_dao(GpIsoLanguagesDao languages_dao) {
		this.languages_dao = languages_dao;
	}

	public GpIsoLanguagesDao getLanguages_dao() {
		return languages_dao;
	}

	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}

	@Resource(name="GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}


	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, Map<Long, GpClientDeviceTypes> map, IGpGenManager gen_manager) throws Exception{


	System.out.println("####### $$$$$$ ****** In GpAngularHtmlGenWorker - generate_code_by_activity");

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		map_client_device_types = map;
		GpArchitypeConfigurations activities_prop = gen_manager.getDerived_configs()
				.get(GpGenConstants.PROJECT_ACTIVITIES);
		the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		getGen_support().getBootstrap_manager().setBase_configs(base_configs);
		this.set_up_paths();
		this.do_generation(activity, gen_manager);

	}

	public void do_generation(GpActivity activity, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		List<String> list_screens_names = new ArrayList<String>();

		//TODO: GET THE SELECTED LANGUAGE FOR MOBILE
		//selected_language = "en";
		//We don't have the selected language yet so we set it to the primary language
		String selected_language = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		//
		String file_name;
		String file_extension =  ".html";
		ArrayList<GpScreenX> screen_list = this.screen_dao.find_all_base_by_activity_id(activity.getId());

		String views_path = "";
		for(GpScreenX screen : screen_list){
			String language_descriptor = languages_dao.find_by_id(screen.getHuman_language_id()).getPart_1();
			GpAngularMenuGenDto angularMenuGenDto = new GpAngularMenuGenDto();
			STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
			ST st = webxmlGroup.getInstanceOf("angular_screen_template_file_v_1");
			angularMenuGenDto.menuVisibility = true;
			angularMenuGenDto.viewName = screen.getName();
			angularMenuGenDto.viewUrl = screen.getName();
			angularMenuGenDto.activity = activity.getName();
			angularMenuGenDto.viewLocation = language_descriptor + "/" + screen.getName() + ".html";
			file_name = screen.getName();
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_desktop)){
				//gen_manager.update_job_status(project_id, user_id, username, "gen_desktop_screen-GpAngularHtmlGenWorker", "gen_processing");
				System.out.println("desktop");
				st = webxmlGroup.getInstanceOf("angular_screen_desktop_template_file_v_1");
				String templateNameSelectedByUser = this.get_generation_service().getTemplateName();
				
				HashMap<String, String> map = this.gen_support.handle_html_gen_windows(screen, activity,
						templateNameSelectedByUser);
				st.add("header", map.get("header"));
				st.add("body", map.get("body"));
				st.add("footer", map.get("footer"));
				views_path = get_generation_service().getDirectory_gen_worker().getWindows_app_views_directory_path().toString();
				the_dto_desktop.add(angularMenuGenDto);
			}
			else if(screen.getClient_device_type_os_name().trim().equals(GpGenConstants.GpClientOS_ANDROID)){
				if(!language_descriptor.equals(selected_language))
					continue;
				//gen_manager.update_job_status(project_id, user_id, username, "gen_android_screen-GpAngularHtmlGenWorker", "gen_processing");
				System.out.println("android");
				HashMap<String, String> map = this.gen_support.handle_html_gen_android(screen, activity);
				st.add("body", map.get("body"));
				if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_phone)){
					views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path().toString();
					the_dto_android_phone.add(angularMenuGenDto);
				}
				if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_tablet)){
					views_path = get_generation_service().getDirectory_gen_worker().getAndroid_tablet_app_views_directory_path().toString();
					the_dto_android_tablet.add(angularMenuGenDto);
				}
			}else if(screen.getClient_device_type_os_name().trim().equals(GpGenConstants.GpClientOS_IOS)){
				if(!language_descriptor.equals(selected_language))
					continue;
				//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_screen-GpAngularHtmlGenWorker", "gen_processing");
				System.out.println("ios");
				HashMap<String, String> map = this.gen_support.handle_html_gen_ios(screen, activity);
				st.add("body", map.get("body"));
				if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_phone)){
					views_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_views_directory_path().toString();
					the_dto_ios_phone.add(angularMenuGenDto);
				}
				if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_tablet)){
					views_path = get_generation_service().getDirectory_gen_worker().getIos_tablet_app_views_directory_path().toString();
					the_dto_ios_tablet.add(angularMenuGenDto);
				}
			}
			String the_path_string = views_path + this.file_separator + language_descriptor
					+ this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			st.add("screen_name", screen.getName());
			write_file(the_path, st);
			list_screens_names.add(screen.getName());
		}
		check_language_in_all_screens(screen_list, the_project, list_screens_names, activity);
	}

	private void check_language_in_all_screens(ArrayList<GpScreenX> screenXs, GpProject the_project, List<String> list_screens_names, GpActivity activity) throws Exception{
		String primary_language_suffix = languages_dao.find_by_id(the_project.getDefault_human_language()).getPart_1();
		List<String> secondary_languages_suffix = new ArrayList<String>();
		List<String> secondaty_languages_id = the_project.getOther_human_languages();
		for(String secondaty_language_id : secondaty_languages_id){
			secondary_languages_suffix.add(languages_dao.find_by_id(Long.valueOf(secondaty_language_id)).getPart_1());
		}
		List<String> screens_checked = new ArrayList<String>();
		for(GpScreenX screen : screenXs){
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_desktop)){
				String screen_name = screen.getName();
				String split[] = screen_name.split("-");
				String screen_name_without_language_suffix = split[0];
				if(!screens_checked.contains(screen_name_without_language_suffix)){
					if(!list_screens_names.contains(screen_name_without_language_suffix+ "-" + primary_language_suffix)){
						//generate default screen
						generate_default_language_screen(screen, screen_name_without_language_suffix, primary_language_suffix,activity);
					}
					for(String secondary_language_suffix : secondary_languages_suffix){
						if(!list_screens_names.contains(screen_name_without_language_suffix + "-" + secondary_language_suffix)){
							//generate default screen
							generate_default_language_screen(screen, screen_name_without_language_suffix, secondary_language_suffix,activity);
						}
					}
					screens_checked.add(screen_name_without_language_suffix);
				}
			}
		}
	}

	private void generate_default_language_screen(GpScreenX screen, String file_name, String language_descriptor, GpActivity activity) throws Exception{
		GpAngularMenuGenDto angularMenuGenDto = new GpAngularMenuGenDto();
		angularMenuGenDto.viewName = file_name + "-" + language_descriptor;
		angularMenuGenDto.viewUrl = file_name + "-" + language_descriptor;
		angularMenuGenDto.activity = activity.getName();
		angularMenuGenDto.viewLocation = language_descriptor + "/" + file_name + "-" + language_descriptor + ".html";
		angularMenuGenDto.menuVisibility = false;
		if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_desktop)){
			STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
			ST st = webxmlGroup.getInstanceOf("angular_language_screen_desktop_v_1");
			String views_path = get_generation_service().getDirectory_gen_worker().getWindows_app_views_directory_path().toString();
			String the_path_string = views_path + this.file_separator + language_descriptor
					+ this.file_separator + file_name + "-" + language_descriptor + ".html";
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
			the_dto_desktop.add(angularMenuGenDto);
			return;
		}
		if(screen.getClient_device_type_os_name().trim().equals(GpGenConstants.GpClientOS_ANDROID)){
			String views_path = "";
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_phone)){
				views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path().toString();
				the_dto_android_phone.add(angularMenuGenDto);
			}
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_tablet)){
				views_path = get_generation_service().getDirectory_gen_worker().getAndroid_tablet_app_views_directory_path().toString();
				the_dto_android_tablet.add(angularMenuGenDto);
			}
			STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
			ST st = webxmlGroup.getInstanceOf("angular_language_screen_v_1");
			String the_path_string = views_path + this.file_separator + language_descriptor
					+ this.file_separator + file_name + "-" + language_descriptor + ".html";
			Path the_path = Paths.get(the_path_string);
			st.add("screen_name", file_name + "-" + language_descriptor);
			write_file(the_path, st);
			return;
		}
		if(screen.getClient_device_type_os_name().trim().equals(GpGenConstants.GpClientOS_IOS)){
			String views_path = "";
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_phone)){
				views_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_views_directory_path().toString();
				the_dto_ios_phone.add(angularMenuGenDto);
			}
			if(screen.getClient_device_type().equals(GpGenConstants.GpClientScreenType_tablet)){
				views_path = get_generation_service().getDirectory_gen_worker().getIos_tablet_app_views_directory_path().toString();
				the_dto_ios_tablet.add(angularMenuGenDto);
			}
			STGroupDir webxmlGroup = new STGroupDir(template_group_path.toString() , '$', '$');
			ST st = webxmlGroup.getInstanceOf("angular_language_screen_v_1");
			String the_path_string = views_path + this.file_separator + language_descriptor
					+ this.file_separator + file_name + "-" + language_descriptor + ".html";
			Path the_path = Paths.get(the_path_string);
			st.add("screen_name", file_name + "-" + language_descriptor);
			write_file(the_path, st);
			return;
		}
	}

	private void test_streams(ArrayList<GpUiWidgetX> widget_list){
		System.out.println("@@@@@@@@@@@@@@ IN test_strems @@@@@@@@@@");

		 Comparator<GpUiWidgetX> byPortraitY = (e1, e2) -> Long.compare(
		            e1.getPortraitY(), e2.getPortraitY());

		 Comparator<GpUiWidgetX> byPortraitX = (e1, e2) -> Long.compare(
		            e1.getPortraitX(), e2.getPortraitX());

		 List<GpUiWidgetX> hold =  widget_list.stream()
				 .sorted(byPortraitY)
				 .sorted(byPortraitX)
				 .collect(Collectors.toList());

		 for(GpUiWidgetX wid : hold){
			 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX());
		 }


//		 widget_list.stream()
//		.sorted(byPortraitY)
//		.sorted(byPortraitX)
//		.forEach(e -> System.out.println("Type: "+ e.getType() + " Portrait Y: " + e.getPortraitY() + " Portrait X: " + e.getPortraitX()));
//

	/*List<GpUiWidgetX> hold =  widget_list.stream()
		.sorted(byPortraitY)
		.sorted(byPortraitX)
		.collect(toList());
        */


		/*
		 *
		 * employees.stream().sorted(byEmployeeNumber)
            .forEach(e -> System.out.println(e));
		 *
		 *
		 *
		 * Comparator<Employee> byEmployeeNumber = (e1, e2) -> Integer.compare(
            e1.getEmployeeNumber(), e2.getEmployeeNumber());
		 *
		 * employees
            .stream()
            .sorted((e1, e2) -> e1.getHireDate()
                    .compareTo(e2.getHireDate()))
            .forEach(e -> System.out.println(e));
		 *
		 */

	}

	public void set_controllers_to_views(String activity_name){
		for(GpAngularMenuGenDto angularMenuGenDto : the_dto_android_phone){
			if(angularMenuGenDto.activity != null && angularMenuGenDto.activity.equals(activity_name)){
				angularMenuGenDto.controller = ",\ncontroller: '"+activity_name + "'";
			}
		}
		for(GpAngularMenuGenDto angularMenuGenDto : the_dto_android_tablet){
			if(angularMenuGenDto.activity != null && angularMenuGenDto.activity.equals(activity_name)){
				angularMenuGenDto.controller = ",\ncontroller: '"+activity_name + "'";
			}
		}
		for(GpAngularMenuGenDto angularMenuGenDto : the_dto_desktop){
			if(angularMenuGenDto.activity != null && angularMenuGenDto.activity.equals(activity_name)){
				angularMenuGenDto.controller = activity_name;
				angularMenuGenDto.controller = ",\ncontroller: '"+activity_name + "'";
			}
		}
		for(GpAngularMenuGenDto angularMenuGenDto : the_dto_ios_phone){
			if(angularMenuGenDto.activity != null && angularMenuGenDto.activity.equals(activity_name)){
				angularMenuGenDto.controller = activity_name;
				angularMenuGenDto.controller = ",\ncontroller: '"+activity_name + "'";
			}
		}
		for(GpAngularMenuGenDto angularMenuGenDto : the_dto_ios_tablet){
			if(angularMenuGenDto.activity != null && angularMenuGenDto.activity.equals(activity_name)){
				angularMenuGenDto.controller = activity_name;
				angularMenuGenDto.controller = ",\ncontroller: '"+activity_name + "'";
			}
		}
	}

	private void set_up_paths() throws Exception{
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
				.get("angular_index_file_template_location").getValue();

		tokens = this.tokenize_string(
								angular_index_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens)
				+ "_angular_screen_template_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations angular_index_file_template_location_path_config
											= this.derived_configs.get(config_name);

		if(angular_index_file_template_location_path_config  == null){
			//create and store the path to the angularJS index.html file template
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
				Path angular_index_file_template_path =
						Paths.get(core_template_location_temp);


				angular_index_file_template_location_path_config = new GpArchitypeConfigurations();

				angular_index_file_template_location_path_config.setName(config_name);
				angular_index_file_template_location_path_config.setObject_value(angular_index_file_template_path);
				this.derived_configs.put(config_name, angular_index_file_template_location_path_config);

			}
		}

		this.template_group_path =
				(Path)angular_index_file_template_location_path_config
														.getObject_value();
	}



}
