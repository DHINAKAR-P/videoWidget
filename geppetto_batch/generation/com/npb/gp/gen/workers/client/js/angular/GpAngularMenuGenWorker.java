package com.npb.gp.gen.workers.client.js.angular;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.dao.mysql.GpMenuDetailDao;
import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuProperties;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpModuleResource;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleAngularViews;
import com.npb.gp.gen.json.mappers.modules.GpModuleMobileInfo;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenSupport;
import com.npb.gp.gen.services.client.angular.GpClientAngularJSGenService;
@Component("GpAngularMenuGenWorker")
public class GpAngularMenuGenWorker extends GpGenJSClientAngularBaseWorker{
	private Path template_group_path;
	private String file_name = "menu";
	private String file_extension = ".html";
	private String menu_code;
	private String menu_code_end;
	private GpMenuDetailDao menuDetailDao;
	private List<GpMenuDetail> list_menu;
	private Path template_nielsen_group_path;
	private GpClientAngularJSGenService genService;
	

	public GpClientAngularJSGenService getGenService() {
		return genService;
	}
	@Resource(name = "GpClientAngularJSGenService")
	public void setGenService(GpClientAngularJSGenService genService) {
		this.genService = genService;
	}
	@Resource(name="GpMenuDetailDao")
	public void setMenuDetailDao(GpMenuDetailDao menuDetailDao) {
		this.menuDetailDao = menuDetailDao;
	}

	public void generate_code(GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, Map<Long, GpClientDeviceTypes> map, IGpGenManager gen_manager)	throws Exception{


	System.out.println("####### $$$$$$ ****** In GpAngularMenuGenWorker");

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_menu_code();
		this.set_up_path_for_gen_templates();
		this.set_up_path_for_gen_nielsen_templates();
		this.do_generation(gen_manager);

	}

	private void set_menu_code() throws Exception{
		menu_code = ".controller('MainCtrl', ['$scope', function($scope) {" + "\n";
		menu_code += "\t" + "$scope.hideSidemenuBackButton = true;" + "\n";
		menu_code += "\t" + "var top_menu_items;" + "\n";
		menu_code += "\t" + "top_menu_items = $scope.menu_items = " + "\n";

		menu_code_end = "var getByParentId = function(id) {" + "\n";
		menu_code_end += "\t" + "for (var i in top_menu_items) {" + "\n";
		menu_code_end += "\t\t" + "if (top_menu_items[i].id == id) {" + "\n";
		menu_code_end += "\t\t\t" + "return top_menu_items[i].screens;" + "\n";
		menu_code_end += "\t\t" + "}" + "\n";
		menu_code_end += "\t" + "}" + "\n";
		menu_code_end += "" + "}" + "\n";
		menu_code_end += "" + "$scope.showSubcategories = function(category) {" + "\n";
		menu_code_end += "\t" + "$scope.menu_items = getByParentId(category.id);" + "\n";
		menu_code_end += "\t" + "$scope.hideSidemenuBackButton = false;" + "\n";
		menu_code_end += "" + "};" + "\n";
		menu_code_end += "" + "$scope.showTopLevelCategories = function () {" + "\n";
		menu_code_end += "\t" + "$scope.menu_items = top_menu_items;" + "\n";
		menu_code_end += "\t" + "$scope.hideSidemenuBackButton = true;" + "\n";
		menu_code_end += "" + "};" + "\n";
		menu_code_end += "" + "}])" + "\n";

		list_menu = this.menuDetailDao.find_by_project_id(the_project.getId());
	}

	public void do_generation(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		if(get_generation_service().getDirectory_gen_worker().android_created){
			//gen_manager.update_job_status(project_id, user_id, username, "gen_android_tablet_menu-GpAngularMenuGenWorker", "gen_processing");
			generate_menu_for_android_tablet(get_generation_service().getHtml_worker().getThe_dto_android_tablet());

			if(get_generation_service().getMobileTemplateName().equals("Nielsen Mobile") ||
					get_generation_service().getMobileTemplateName().equals("IBM Mobile")){
				System.out.println("Nielsen Mobile menu generated ###### ");
				generate_menu_for_android_phone_nielsen(get_generation_service().getHtml_worker().getThe_dto_android_phone());
		}else{
			System.out.println("Ordinary Mobile menu generated ###### ");
			generate_menu_for_android_phone(get_generation_service().getHtml_worker().getThe_dto_android_phone());
		}
		
		}

		if(get_generation_service().getDirectory_gen_worker().ios_created){
			//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_tablet_menu-GpAngularMenuGenWorker", "gen_processing");
			generate_menu_for_ios_tablet(get_generation_service().getHtml_worker().getThe_dto_ios_tablet());

			//gen_manager.update_job_status(project_id, user_id, username, "gen_ios_phone_menu-GpAngularMenuGenWorker", "gen_processing");
			generate_menu_for_ios_phone(get_generation_service().getHtml_worker().getThe_dto_ios_phone());
		}
	}
	
	public ST handle_footer_generation_for_geppetto_theme(ST st) {
		
		HashMap<String, String> project_templates = genService.getMenu_worker().the_project.getProject_templates();
		System.out.println("size : " + project_templates.size());
		/*for (Map.Entry<String, String> entry : project_templates.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}*/
		if (project_templates != null
				&& project_templates.get("SITE_NAME") != null
				&& project_templates.get("SITE_NAME").trim().length() != 0) {
			st.add("project_name", project_templates.get("SITE_NAME"));
		} else {
			st.add("project_name", genService.getMenu_worker().the_project.getName());
		}
		// set site logo
		if (project_templates != null
				&& project_templates.get("SITE_LOGO") != null
				&& project_templates.get("SITE_LOGO").trim().length() != 0) {
			st.add("site_logo", project_templates.get("SITE_LOGO"));
		} else {
			st.add("site_logo", "app/img/lego.svg");
		}
		
		// Get In Touch
		if (project_templates != null
				&& project_templates.get("DISCOVER") != null
				&& project_templates.get("DISCOVER").trim().length() != 0) {
			//st.add("get_in_touch", project_templates.get("DISCOVER"));
		} else {
			//st.add("get_in_touch", "");
		}

		// About
		if (project_templates != null && project_templates.get("ABOUT_IBM") != null
				&& project_templates.get("ABOUT_IBM").trim().length() != 0) {
			//st.add("about", project_templates.get("ABOUT_IBM"));			
		} else {
			//st.add("about", "");
		}

		// Social
		if (project_templates != null
				&& project_templates.get("CONNECT_WITH_US") != null
				&& project_templates.get("CONNECT_WITH_US").trim().length() != 0) {
			//st.add("social", project_templates.get("CONNECT_WITH_US"));
		} else {
			//st.add("social", "");
		}

		// Contact US
		if (project_templates != null
				&& project_templates.get("INFORMATION_FOR") != null
				&& project_templates.get("INFORMATION_FOR").trim().length() != 0) {
			//st.add("contact_us", project_templates.get("INFORMATION_FOR"));
		} else {
			//st.add("contact_us", "");
		}

		// Footer
		if (project_templates != null
				&& project_templates.get("FOOTER") != null
				&& project_templates.get("FOOTER").trim().length() != 0) {
			//st.add("footer", project_templates.get("FOOTER"));
		} else {
			//st.add("footer", "");
		}
		
		//Copy Right
		if (project_templates != null
				&& project_templates.get("COPY_RIGHT") != null
				&& project_templates.get("COPY_RIGHT").trim().length() != 0) {
			st.add("copy_right", project_templates.get("COPY_RIGHT"));
		} else {
			st.add("copy_right", "");
		}

		return st;
	}
	private void generate_menu_for_android_phone_nielsen(List<GpAngularMenuGenDto> the_dto) throws Exception{
		ST st = super.read_template_group(template_nielsen_group_path, "output");
		String views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path().toString();
		String the_path_string = views_path + this.file_separator + file_name + file_extension;
	    st = this.handle_footer_generation_for_geppetto_theme(st);
				
		Path the_path = Paths.get(the_path_string);
		write_file(the_path, st);
		ObjectMapper mapper = new ObjectMapper();
		String json_string = "[" + "\n";
		for(GpMenuDetail gpMenuDetail : list_menu){
			json_string += "\n\t" + "{" + "\n";
			json_string += "\t\t" + "is_first_level: true," + "\n";
			json_string += "\t\t" + "name: \""+ gpMenuDetail.getName() +"\"," + "\n";
			json_string += "\t\t" + "label: \""+ gpMenuDetail.getLabel() +"\"," + "\n";
			json_string += "\t\t" + "id: "+ gpMenuDetail.getActivity_id() +"," + "\n";
			json_string += "\t\t" + "screens: [";
			GpMenuProperties[] menu_properties = mapper.readValue(gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
			List<GpMenuProperties> menu_properties_list = Arrays.asList(menu_properties);
			boolean HaveScreens = false;
			for(GpMenuProperties menu_propertie_from_list : menu_properties_list){
				for(GpAngularMenuGenDto menuGenDto : the_dto){
					if(menu_propertie_from_list.getName().equals(menuGenDto.viewName)){
						json_string += "\n\t\t\t" + "{" + "\n";
						json_string += "\t\t\t\t" + "id: "+ menu_propertie_from_list.getId() +"," + "\n";
						json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
						json_string += "\t\t\t\t" + "name: \""+ menu_propertie_from_list.getName() +"\"," + "\n";
						String label;
						if(menu_propertie_from_list.getLabel() == null)
							label = menu_propertie_from_list.getName();
						else
							label = menu_propertie_from_list.getLabel();
						json_string += "\t\t\t\t" + "label: \""+ label +"\"," + "\n";
						json_string += "\t\t\t" + "},";
						HaveScreens = true;
					}
				}
			}
			if(HaveScreens){
				json_string = json_string.substring(0,json_string.length()-1);
			}
			json_string += "\t\t" + "]";
			json_string += "\t" + "},";
		}
		List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : modules){
			GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
			if(mobile_info != null && mobile_info.getViews() != null && mobile_info.getViews().length != 0){
				json_string += "\n\t" + "{" + "\n";
				json_string += "\t\t" + "is_first_level: true," + "\n";
				json_string += "\t\t" + "name: \""+ module.getName() +"\"," + "\n";
				json_string += "\t\t" + "label: \""+ module.getName() +"\"," + "\n";
				json_string += "\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
				json_string += "\t\t" + "screens: [";
				
				boolean HaveScreens = false;
				List<GpModuleAngularViews> views = Arrays.asList(mobile_info.getViews());
				for(GpModuleAngularViews view : views){
					if(view.isMenuVisibility()){
						json_string += "\n\t\t\t" + "{" + "\n";
						json_string += "\t\t\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
						json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
						json_string += "\t\t\t\t" + "name: \""+ view.getViewUrl() +"\"," + "\n";
						json_string += "\t\t\t\t" + "label: \""+ view.getViewName() +"\"," + "\n";
						json_string += "\t\t\t" + "},";
						HaveScreens = true;
					}
				}
				
				if(HaveScreens){
					json_string = json_string.substring(0,json_string.length()-1);
				}
				json_string += "\t\t" + "]";
				json_string += "\t" + "},";
			}
		}
			
		json_string = json_string.substring(0,json_string.length()-1);
		json_string += "\n];\n";
		get_generation_service().getAppjs_worker().add_code(menu_code + json_string + menu_code_end, GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_phone);
}
	private void generate_menu_for_android_tablet(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			String views_path = get_generation_service().getDirectory_gen_worker().getAndroid_tablet_app_views_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
			ObjectMapper mapper = new ObjectMapper();
			String json_string = "[" + "\n";
			for(GpMenuDetail gpMenuDetail : list_menu){
				json_string += "\n\t" + "{" + "\n";
				json_string += "\t\t" + "is_first_level: true," + "\n";
				json_string += "\t\t" + "name: \""+ gpMenuDetail.getName() +"\"," + "\n";
				json_string += "\t\t" + "label: \""+ gpMenuDetail.getLabel() +"\"," + "\n";
				json_string += "\t\t" + "id: "+ gpMenuDetail.getActivity_id() +"," + "\n";
				json_string += "\t\t" + "screens: [";
				GpMenuProperties[] menu_properties = mapper.readValue(gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
				List<GpMenuProperties> menu_properties_list = Arrays.asList(menu_properties);
				for(GpMenuProperties menu_propertie_from_list : menu_properties_list){
					for(GpAngularMenuGenDto menuGenDto : the_dto){
						if(menu_propertie_from_list.getName().equals(menuGenDto.viewName)){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ menu_propertie_from_list.getId() +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ menu_propertie_from_list.getName() +"\"," + "\n";
							json_string += "\t\t\t\t" + "label: \""+ menu_propertie_from_list.getLabel() +"\"," + "\n";
							json_string += "\t\t\t" + "},";
						}
					}
				}
				json_string = json_string.substring(0,json_string.length()-1);
				json_string += "\t\t" + "]";
				json_string += "\t" + "},";
			}
			json_string = json_string.substring(0,json_string.length()-1);
			json_string += "\n];\n";
			get_generation_service().getAppjs_worker().add_code(menu_code + json_string + menu_code_end, GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_tablet);
	}
	private void generate_menu_for_android_phone(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			String views_path = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_views_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
			ObjectMapper mapper = new ObjectMapper();
			String json_string = "[" + "\n";
			for(GpMenuDetail gpMenuDetail : list_menu){
				json_string += "\n\t" + "{" + "\n";
				json_string += "\t\t" + "is_first_level: true," + "\n";
				json_string += "\t\t" + "name: \""+ gpMenuDetail.getName() +"\"," + "\n";
				json_string += "\t\t" + "label: \""+ gpMenuDetail.getLabel() +"\"," + "\n";
				json_string += "\t\t" + "id: "+ gpMenuDetail.getActivity_id() +"," + "\n";
				json_string += "\t\t" + "screens: [";
				GpMenuProperties[] menu_properties = mapper.readValue(gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
				List<GpMenuProperties> menu_properties_list = Arrays.asList(menu_properties);
				boolean HaveScreens = false;
				for(GpMenuProperties menu_propertie_from_list : menu_properties_list){
					for(GpAngularMenuGenDto menuGenDto : the_dto){
						if(menu_propertie_from_list.getName().equals(menuGenDto.viewName)){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ menu_propertie_from_list.getId() +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ menu_propertie_from_list.getName() +"\"," + "\n";
							String label;
							if(menu_propertie_from_list.getLabel() == null)
								label = menu_propertie_from_list.getName();
							else
								label = menu_propertie_from_list.getLabel();
							json_string += "\t\t\t\t" + "label: \""+ label +"\"," + "\n";
							json_string += "\t\t\t" + "},";
							HaveScreens = true;
						}
					}
				}
				if(HaveScreens){
					json_string = json_string.substring(0,json_string.length()-1);
				}
				json_string += "\t\t" + "]";
				json_string += "\t" + "},";
			}
			List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			for(GpModuleProperties module : modules){
				GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
				if(mobile_info != null && mobile_info.getViews() != null && mobile_info.getViews().length!=0){
					json_string += "\n\t" + "{" + "\n";
					json_string += "\t\t" + "is_first_level: true," + "\n";
					json_string += "\t\t" + "name: \""+ module.getName() +"\"," + "\n";
					json_string += "\t\t" + "label: \""+ module.getName() +"\"," + "\n";
					json_string += "\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
					json_string += "\t\t" + "screens: [";
					boolean HaveScreens = false;
					List<GpModuleAngularViews> views = Arrays.asList(mobile_info.getViews());
					for(GpModuleAngularViews view : views){
						if(view.isMenuVisibility()){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ view.getViewUrl() +"\"," + "\n";
							json_string += "\t\t\t\t" + "label: \""+ view.getViewName() +"\"," + "\n";
							json_string += "\t\t\t" + "},";
							HaveScreens = true;
						}
					}
					if(HaveScreens){
						json_string = json_string.substring(0,json_string.length()-1);
					}
					json_string += "\t\t" + "]";
					json_string += "\t" + "},";
				}
			}
				
			json_string = json_string.substring(0,json_string.length()-1);
			json_string += "\n];\n";
			get_generation_service().getAppjs_worker().add_code(menu_code + json_string + menu_code_end, GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_phone);
	}
	private void generate_menu_for_ios_tablet(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			String views_path = get_generation_service().getDirectory_gen_worker().getIos_tablet_app_views_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
			ObjectMapper mapper = new ObjectMapper();
			String json_string = "[" + "\n";
			for(GpMenuDetail gpMenuDetail : list_menu){
				json_string += "\n\t" + "{" + "\n";
				json_string += "\t\t" + "is_first_level: true," + "\n";
				json_string += "\t\t" + "name: \""+ gpMenuDetail.getName() +"\"," + "\n";
				json_string += "\t\t" + "label: \""+ gpMenuDetail.getLabel() +"\"," + "\n";
				json_string += "\t\t" + "id: "+ gpMenuDetail.getActivity_id() +"," + "\n";
				json_string += "\t\t" + "screens: [";
				GpMenuProperties[] menu_properties = mapper.readValue(gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
				List<GpMenuProperties> menu_properties_list = Arrays.asList(menu_properties);
				for(GpMenuProperties menu_propertie_from_list : menu_properties_list){
					for(GpAngularMenuGenDto menuGenDto : the_dto){
						if(menu_propertie_from_list.getName().equals(menuGenDto.viewName)){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ menu_propertie_from_list.getId() +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ menu_propertie_from_list.getName() +"\"," + "\n";
							json_string += "\t\t\t\t" + "label: \""+ menu_propertie_from_list.getLabel() +"\"," + "\n";
							json_string += "\t\t\t" + "},";
						}
					}
				}
				json_string = json_string.substring(0,json_string.length()-1);
				json_string += "\t\t" + "]";
				json_string += "\t" + "},";
			}
			json_string = json_string.substring(0,json_string.length()-1);
			json_string += "\n];\n";
			get_generation_service().getAppjs_worker().add_code(menu_code + json_string + menu_code_end, GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_tablet);
	}
	private void generate_menu_for_ios_phone(List<GpAngularMenuGenDto> the_dto) throws Exception{
			ST st = super.read_template_group(template_group_path, "output");
			String views_path = get_generation_service().getDirectory_gen_worker().getIos_phone_app_views_directory_path().toString();
			String the_path_string = views_path + this.file_separator + file_name + file_extension;
			Path the_path = Paths.get(the_path_string);
			write_file(the_path, st);
			ObjectMapper mapper = new ObjectMapper();
			String json_string = "[" + "\n";
			for(GpMenuDetail gpMenuDetail : list_menu){
				json_string += "\n\t" + "{" + "\n";
				json_string += "\t\t" + "is_first_level: true," + "\n";
				json_string += "\t\t" + "name: \""+ gpMenuDetail.getName() +"\"," + "\n";
				json_string += "\t\t" + "label: \""+ gpMenuDetail.getLabel() +"\"," + "\n";
				json_string += "\t\t" + "id: "+ gpMenuDetail.getActivity_id() +"," + "\n";
				json_string += "\t\t" + "screens: [";
				GpMenuProperties[] menu_properties = mapper.readValue(gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
				List<GpMenuProperties> menu_properties_list = Arrays.asList(menu_properties);
				boolean HaveScreens = false;
				for(GpMenuProperties menu_propertie_from_list : menu_properties_list){
					for(GpAngularMenuGenDto menuGenDto : the_dto){
						if(menu_propertie_from_list.getName().equals(menuGenDto.viewName)){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ menu_propertie_from_list.getId() +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ menu_propertie_from_list.getName() +"\"," + "\n";
							json_string += "\t\t\t\t" + "label: \""+ menu_propertie_from_list.getLabel() +"\"," + "\n";
							json_string += "\t\t\t" + "},";
							HaveScreens = true;
						}
					}
				}
				if(HaveScreens)
					json_string = json_string.substring(0,json_string.length()-1);
				json_string += "\t\t" + "]";
				json_string += "\t" + "},";
			}
			List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			for(GpModuleProperties module : modules){
				GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
				if(mobile_info != null && mobile_info.getViews() != null && mobile_info.getViews().length!=0){
					json_string += "\n\t" + "{" + "\n";
					json_string += "\t\t" + "is_first_level: true," + "\n";
					json_string += "\t\t" + "name: \""+ module.getName() +"\"," + "\n";
					json_string += "\t\t" + "label: \""+ module.getName() +"\"," + "\n";
					json_string += "\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
					json_string += "\t\t" + "screens: [";
					boolean HaveScreens = false;
					List<GpModuleAngularViews> views = Arrays.asList(mobile_info.getViews());
					for(GpModuleAngularViews view : views){
						if(view.isMenuVisibility()){
							json_string += "\n\t\t\t" + "{" + "\n";
							json_string += "\t\t\t\t" + "id: "+ new Random().nextInt(1000) +"," + "\n";
							json_string += "\t\t\t\t" + "is_first_level: false," + "\n";
							json_string += "\t\t\t\t" + "name: \""+ view.getViewUrl() +"\"," + "\n";
							json_string += "\t\t\t\t" + "label: \""+ view.getViewName() +"\"," + "\n";
							json_string += "\t\t\t" + "},";
							HaveScreens = true;
						}
					}
					if(HaveScreens){
						json_string = json_string.substring(0,json_string.length()-1);
					}
					json_string += "\t\t" + "]";
					json_string += "\t" + "},";
				}
			}
			
			json_string = json_string.substring(0,json_string.length()-1);
			json_string += "\n];\n";
			get_generation_service().getAppjs_worker().add_code(menu_code + json_string + menu_code_end, GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_phone);
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

		String template_name =  "menu_template_v_1.stg";
		String template_name_desktop =  "menu_desktop_template_v_1.stg";
		template_group_path =
				Paths.get(core_template_location_temp + this.file_separator + template_name);
	}
	private void set_up_path_for_gen_nielsen_templates(){
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

		String template_name =  "menu_template_nielsen_v_1.stg";
		String template_name_desktop =  "menu_desktop_template_v_1.stg";
		template_nielsen_group_path =
				Paths.get(core_template_location_temp + this.file_separator + template_name);
	}
}
