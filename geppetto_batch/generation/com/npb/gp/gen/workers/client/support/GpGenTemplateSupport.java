package com.npb.gp.gen.workers.client.support;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npb.gp.dao.mysql.GpMenuDetailDao;
import com.npb.gp.domain.core.GpMenu;
import com.npb.gp.domain.core.GpMenuDetail;
import com.npb.gp.domain.core.GpMenuProperties;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpModuleResource;
import com.npb.gp.gen.json.mappers.modules.GpModuleAngularViews;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenDirectoryWorker;
import com.npb.gp.gen.workers.client.GpGenTemplateWorker;

@Component("GpGenTemplateSupport")
public class GpGenTemplateSupport {
	private GpGenTemplateWorker the_worker;
	private GpGenDirectoryWorker directory_worker;
	private String geppetto_theme_path;
	protected String file_separator = System.getProperty("file.separator");
	private GpMenuDetailDao menuDetailDao;
	private List<GpMenu> menu;

	@Resource(name = "GpMenuDetailDao")
	public void setMenuDetailDao(GpMenuDetailDao menuDetailDao) {
		this.menuDetailDao = menuDetailDao;
	}

	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}

	@Resource(name = "GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}

	public GpGenTemplateWorker getThe_worker() {
		return the_worker;
	}

	@Resource(name = "GpGenTemplateWorker")
	public void setThe_worker(GpGenTemplateWorker the_worker) {
		this.the_worker = the_worker;
	}

	public void init() throws Exception {
		geppetto_theme_path = getThe_worker().getRoot_template_path()
				+ this.file_separator + "themes" + this.file_separator
				+ "Geppetto";
		init_menu();
	}

	public void init_menu() throws Exception {
		List<GpMenuDetail> list_menu = this.menuDetailDao
				.find_by_project_id(getThe_worker().the_project.getId());
		List<GpAngularMenuGenDto> menu_from_gen_angular = getThe_worker()
				.getGenService().getHtml_worker().getThe_dto_desktop();
		ObjectMapper mapper = new ObjectMapper();
		menu = new ArrayList<GpMenu>();
		List<String> list_for_language = new ArrayList<String>();
		for (GpMenuDetail gpMenuDetail : list_menu) {
			GpMenu gpMenu = new GpMenu();
			gpMenu.setActivity_name(gpMenuDetail.getName());
			gpMenu.setActivity_label(gpMenuDetail.getLabel());
			GpMenuProperties[] menu_properties = mapper.readValue(
					gpMenuDetail.getMenu_tree(), GpMenuProperties[].class);
			List<GpMenuProperties> menu_properties_list = Arrays
					.asList(menu_properties);
			List<GpMenuProperties> menu_validated = new ArrayList<GpMenuProperties>();
			for (GpMenuProperties menu_propertie_from_list : menu_properties_list) {
				for (GpAngularMenuGenDto menuGenDto : menu_from_gen_angular) {
					if (menu_propertie_from_list.getName().equals(
							menuGenDto.viewName)
							&& !list_for_language
									.contains(menu_propertie_from_list
											.getName().split("-")[0])) {
						menu_propertie_from_list
								.setName(menu_propertie_from_list.getName()
										.split("-")[0]);
						list_for_language.add(menu_propertie_from_list
								.getName().split("-")[0]);
						menu_validated.add(menu_propertie_from_list);
					}
				}
			}
			gpMenu.setGpMenuProperties(menu_validated
					.toArray(new GpMenuProperties[menu_validated.size()]));
			List<GpModuleProperties> views_to_import_from_components = getThe_worker()
					.getGenService().getNot_default_activity_worker()
					.getModule_properties_list();
			for (GpModuleProperties properties : views_to_import_from_components) {
				if (properties.getActivity_name().equals(
						gpMenu.getActivity_name())) {
					gpMenu.setModule(true);
					List<GpModuleAngularViews> resources = new ArrayList<GpModuleAngularViews>();
					for (GpModuleAngularViews resource : properties.getClient_meta_data().getJava_script().getAngular_js().getResources()) {
						if (resource.isMenuVisibility()) {
							resources.add(resource);
						}
					}
					List<GpMenuProperties> menuProperties_from_component = new ArrayList<>();
					for (GpModuleAngularViews resource : resources) {
						
						if (!list_for_language.contains(resource.getViewUrl())) {
							GpMenuProperties view_from_component = new GpMenuProperties();
							view_from_component.setName(resource.getViewUrl());
							view_from_component.setLabel(resource.getViewName());
							menuProperties_from_component
									.add(view_from_component);
							list_for_language.add(resource.getViewUrl());
						}
					}
					gpMenu.setGpMenuProperties(menuProperties_from_component
							.toArray(new GpMenuProperties[menuProperties_from_component
									.size()]));
				}
			}
			menu.add(gpMenu);
			if(gpMenuDetail.getName().contains("-"))
				list_for_language.add(gpMenuDetail.getName().split("-")[0]);
		}
	}

	public ST handle_header_generation_for_geppetto_theme() {
		STGroupDir webxmlGroup = new STGroupDir(
				this.geppetto_theme_path.toString(), '$', '$');
		ST st = webxmlGroup.getInstanceOf("header_template_v1");
		HashMap<String, String> project_templates = getThe_worker().the_project
				.getProject_templates();
		// set site name
		if (project_templates != null
				&& project_templates.get("SITE_NAME") != null
				&& project_templates.get("SITE_NAME").trim().length() != 0) {
			st.add("project_name", project_templates.get("SITE_NAME"));
		} else {
			st.add("project_name", getThe_worker().the_project.getName());
		}
		// set site logo
		if (project_templates != null
				&& project_templates.get("SITE_LOGO") != null
				&& project_templates.get("SITE_LOGO").trim().length() != 0) {
			st.add("site_logo", project_templates.get("SITE_LOGO"));
		} else {
			st.add("site_logo", "app/img/lego.svg");
		}
		return st;
	}

	public ST handle_footer_generation_for_geppetto_theme() {
		STGroupDir webxmlGroup = new STGroupDir(
				this.geppetto_theme_path.toString(), '$', '$');
		ST st = webxmlGroup.getInstanceOf("footer_template_v1");
		HashMap<String, String> project_templates = getThe_worker().the_project
				.getProject_templates();
		// Get In Touch
		if (project_templates != null
				&& project_templates.get("GET_IN_TOUCH") != null
				&& project_templates.get("GET_IN_TOUCH").trim().length() != 0) {
			st.add("get_in_touch", project_templates.get("GET_IN_TOUCH"));
		} else {
			st.add("get_in_touch", "");
		}

		// About
		if (project_templates != null && project_templates.get("ABOUT") != null
				&& project_templates.get("ABOUT").trim().length() != 0) {
			st.add("about", project_templates.get("ABOUT"));
		} else {
			st.add("about", "");
		}

		// Social
		if (project_templates != null
				&& project_templates.get("SOCIAL") != null
				&& project_templates.get("SOCIAL").trim().length() != 0) {
			st.add("social", project_templates.get("SOCIAL"));
		} else {
			st.add("social", "");
		}

		// Contact US
		if (project_templates != null
				&& project_templates.get("CONTACT") != null
				&& project_templates.get("CONTACT").trim().length() != 0) {
			st.add("contact_us", project_templates.get("CONTACT"));
		} else {
			st.add("contact_us", "");
		}

		// Footer
		if (project_templates != null
				&& project_templates.get("FOOTER") != null
				&& project_templates.get("FOOTER").trim().length() != 0) {
			st.add("footer", project_templates.get("FOOTER"));
		} else {
			st.add("footer", "");
		}

		return st;
	}

	public ST handle_menu_generation_for_geppetto_theme() {
		String template_menu_path = geppetto_theme_path + this.file_separator
				+ "menu_template_v1.stg";
		ST st = read_template_group(Paths.get(template_menu_path), "output");
		String menu_string = "";
		for (GpMenu menuitem : menu) {
			if(menuitem.getGpMenuProperties().length > 0){
				menu_string += "<li class=\"dropdown\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">\n";
				menu_string += menuitem.getActivity_label()
						+ "<span class=\"caret\"></span></a>\n";
				menu_string += "<ul class=\"dropdown-menu\">\n";
				for (int i = 0; i < menuitem.getGpMenuProperties().length; i++) {
					GpMenuProperties menuProperties = menuitem
							.getGpMenuProperties()[i];
					String label;
					if(menuProperties.getLabel() == null){
						label = menuProperties.getName();  
					}
					else{ 
						label = menuProperties.getLabel();
					}
					if(menuitem.isModule()){
						menu_string += "<li><a href=\"#/"
								+ menuProperties.getName() + "\">"
								+ label + "</a></li>\n";
					}
					else{
						menu_string += "<li><a ng-click=\"handle_url('"
								+ menuProperties.getName() + "')\">"
								+ label + "</a></li>\n";
					}
					System.out.println("name : " + menuProperties.getName());
					System.out.println("label : "+ menuProperties.getLabel());
				}
				menu_string += "</ul></li>\n";
			}
		}
		st.add("the_menu_items", menu_string);
		return st;
	}

	public ST handle_menu_controller_generation_for_geppetto_theme() {
		STGroupDir webxmlGroup = new STGroupDir(
				this.geppetto_theme_path.toString(), '$', '$');
		ST st = webxmlGroup.getInstanceOf("menu_controller_template_v1");
		the_worker.getGenService().add_controller_to_import_for_desktop("MenuCtrl");
		return st;
	}

	public void handle_css_generation_for_geppetto_theme() throws Exception {
		String css_template_path = geppetto_theme_path + this.file_separator
				+ "css";

		String css_client_desktop_path = getThe_worker().getGenService()
				.getDirectory_gen_worker().getWindows_app_css_directory_path()
				.toString();
		directory_worker.copy_directory(new File(css_template_path), new File(
				css_client_desktop_path));
		// import this css on your index
		getThe_worker().getGenService().getIndex_worker()
				.getList_css_imports_for_desktop().add("app.css");
	}

	public ST read_template_group(Path file_path, String root_template) {

		STGroup group = new STGroupFile(file_path.toString(), '$', '$');
		ST st = group.getInstanceOf(root_template);

		return st;
	}
}
