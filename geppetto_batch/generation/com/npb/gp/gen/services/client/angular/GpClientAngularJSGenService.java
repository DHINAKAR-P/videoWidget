package com.npb.gp.gen.services.client.angular;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.dao.mysql.GpProjectTemplateDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpModuleResource;
import com.npb.gp.domain.core.GpProjectTemplate;
import com.npb.gp.domain.core.TemplateBaseInfo;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.dao.mysql.GpGenFlowDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.interfaces.services.IGpClientAngularJSGenService;
import com.npb.gp.gen.json.mappers.modules.GpModuleAngularViews;
import com.npb.gp.gen.json.mappers.modules.GpModuleMobileInfo;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.util.dto.angular.GpAngularMenuGenDto;
import com.npb.gp.gen.workers.GpGenDirectoryWorker;
import com.npb.gp.gen.workers.GpNotDefaultActivityGenWorker;
import com.npb.gp.gen.workers.client.GenNielsenTemplateWorker;
import com.npb.gp.gen.workers.client.GpGenIBMTemplateWorker;
import com.npb.gp.gen.workers.client.GpGenTemplateWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularAppJsGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularControllerGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularDirectoryGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularHtmlGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularIndexGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularLandingPageGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularLibsJsGenWorker;
import com.npb.gp.gen.workers.client.js.angular.GpAngularMenuGenWorker;
import com.npb.gp.gen.workers.client.js.angular.system.activity.gepppeto.login.GpGeppettoLoginActivityGenWorker;

/**
 *
 * @author Dan Castillo Date Created: 02/02/2015</br>
 * @since .2
 *        </p>
 *
 *        this class directs the generation of an AngularJS client app
 *        </p>
 *
 *
 *        Modified Date: 09/29/2015</br>
 *        Modified By: Suresh Palanisamy</br>
 *        <p>
 *        Modified the code for updating the job status for each step of
 *        application generation.
 *        </p>
 *
 */
@Service("GpClientAngularJSGenService")
public class GpClientAngularJSGenService extends GpBaseGenerationService implements IGpClientAngularJSGenService {

	private GpGenFlowDao flow_dao;
	private GpAngularIndexGenWorker index_worker;
	private GpAngularControllerGenWorker controller_worker;
	private GpAngularDirectoryGenWorker directory_gen_worker;
	private GpAngularHtmlGenWorker html_worker;
	private GpAngularMenuGenWorker menu_worker;
	private GpAngularAppJsGenWorker appjs_worker;
	private List<String> controllers_to_import_desktop;
	private List<String> controllers_to_import_android_phone;
	private List<String> controllers_to_import_android_tablet;
	private List<String> controllers_to_import_ios_phone;
	private List<String> controllers_to_import_ios_tablet;
	private GpGenDirectoryWorker directory_worker;
	private GpNotDefaultActivityGenWorker not_default_activity_worker;
	private GpGenTemplateWorker template_Worker;
	private GpAngularLibsJsGenWorker libs_worker;
	private GpAngularLandingPageGenWorker the_landing_page_worker;
	private GpGeppettoLoginActivityGenWorker the_geppetto_login_activity_worker;
	
	private GpProjectTemplateDao project_template;
	private GenNielsenTemplateWorker nielsen_template_worker;
	private GpGenIBMTemplateWorker ibm_template_worker;
	private String templateName = "";
	private String mobileTemplateName = "";
	private String iosTemplateName = "";
	
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	void find_template(IGpGenManager gen_manager) throws Exception {
		long projectId = gen_manager.get_project().getId();

		List<GpProjectTemplate> listProjectTemplate = project_template.findByProject(projectId);
		this.setTemplateName("Geppetto PC");
		this.setMobileTemplateName("Geppetto Mobile");
		this.setIosTemplateName("Geppetto Tablet");
		for (GpProjectTemplate gpProjectTemplate : listProjectTemplate) {
			if (gpProjectTemplate.getDevice().equals("PC")) {
				long templateId = gpProjectTemplate.getTemplateId();
				TemplateBaseInfo projectTemplate = project_template.findByTemplate(templateId); 
				this.setTemplateName(projectTemplate.getName());
			}else if (gpProjectTemplate.getDevice().equals("Mobile")) {
				long templateId = gpProjectTemplate.getTemplateId();
				TemplateBaseInfo projectTemplate = project_template.findByTemplate(templateId); 
				this.setMobileTemplateName(projectTemplate.getName());
			} else if (gpProjectTemplate.getDevice().equals("Tablet")) {
				long templateId = gpProjectTemplate.getTemplateId();
				TemplateBaseInfo projectTemplate = project_template.findByTemplate(templateId);
				this.setIosTemplateName(projectTemplate.getName());
			}
		}
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public void generate(IGpGenManager gen_manager) throws Exception {
		this.check_project_type(gen_manager);
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		this.set_up_directories(gen_manager);
		this.prep_workers(gen_manager);
		this.find_template(gen_manager);

		GpArchitypeConfigurations activities_prop = gen_manager.getDerived_configs()
				.get(GpGenConstants.PROJECT_ACTIVITIES);

		ArrayList<GpActivity> the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		this.import_info_components();
		this.set_up_landing_page_activity(gen_manager);//TODO: should change this
		boolean first_time = true;
		for (GpActivity an_activity : the_activities) {

			if (an_activity.getModule_type() != null
					&& an_activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {

				Path base_path = Paths.get(gen_manager.getBase_configs().get("base_generation_directory").getValue());
				String project_folder = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();

				this.not_default_activity_worker.setModule_final_directory(base_path.toString(), project_folder,
						"client");

				this.not_default_activity_worker.import_client_module(an_activity, this);
				
				if(first_time){
					this.the_geppetto_login_activity_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
						gen_manager.getDerived_configs(), null, gen_manager);
					first_time = false;
				}

			} else {
				// pages do not have a flow
				this.html_worker.generate_code_by_activity(an_activity, gen_manager.get_project(),
						gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null,
						gen_manager.getClient_device_types(), gen_manager);
				this.process_flow(gen_manager, an_activity);
			}
		}
		this.libs_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);


		this.generate_theme(gen_manager);
		this.menu_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager.getClient_device_types(), gen_manager);

		this.appjs_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager.getClient_device_types(), gen_manager);

		this.generate_index_files(gen_manager);
	}

	private void generate_theme(IGpGenManager gen_manager) throws Exception {
		if(templateName==null || templateName.isEmpty()){
			this.template_Worker.generate_code(gen_manager.get_project(),
					gen_manager.getBase_configs(),
					gen_manager.getDerived_configs(), null, gen_manager);
			return;
		}
		System.out.println( " template  Name : " + templateName);
		
	if (this.templateName.equals("Geppetto PC")) {
		this.template_Worker.generate_code(gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	} else if (this.templateName.equals("Geppetto Tablet")) {

	} else if (this.templateName.equals("Geppetto Mobile")) {

	} else if (this.templateName.equals("Nielsen PC") || this.templateName.equals("IBM PC")) {
		this.nielsen_template_worker.generate_code(
				gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	} else if (this.templateName.equals("Nielsen Tablet")) {
		this.nielsen_template_worker.generate_code(
				gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	} else if (this.templateName.equals("Nielsen Mobile")) {
		this.nielsen_template_worker.generate_code(
				gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	} else if (this.templateName.equals("IBM Mobile")) {
		this.ibm_template_worker.generate_code(gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	} else if (this.templateName.equals("IBM Tablet")) {
		this.ibm_template_worker.generate_code(gen_manager.get_project(),
				gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	}else{
		System.out.println("Theme is not generated @@");
	}
	
					
}

	private void import_info_components() {
		List<GpModuleProperties> views_to_import_from_components = getNot_default_activity_worker()
				.getModule_properties_list();
		List<GpAngularMenuGenDto> the_dto = getHtml_worker().getThe_dto_desktop();
		for (GpModuleProperties properties : views_to_import_from_components) {
			List<GpModuleAngularViews> resources = Arrays.asList(properties.getClient_meta_data().getJava_script().getAngular_js().getResources());
			for (GpModuleAngularViews resource : resources) {
				GpAngularMenuGenDto angularMenuGenDto = new GpAngularMenuGenDto();
				angularMenuGenDto.viewName = resource.getViewName();
				angularMenuGenDto.viewUrl = resource.getViewUrl();
				angularMenuGenDto.menuVisibility = resource.isMenuVisibility();
				angularMenuGenDto.viewLocation = resource.getViewLocation();
				angularMenuGenDto.no_template = resource.isNo_template();
				angularMenuGenDto.no_menu = resource.isNo_menu();
				if(angularMenuGenDto.no_menu)
					angularMenuGenDto.controller = "controller: '" + resource.getControllerName() + "'";
				else
					angularMenuGenDto.controller = ",\ncontroller: '" + resource.getControllerName() + "'";
				the_dto.add(angularMenuGenDto);
				if (!controllers_to_import_desktop.contains("" + resource.getControllerName())) {
					controllers_to_import_desktop.add("" + resource.getControllerName());
				}
				if(properties.getName().equals("Geppetto Login")){//TODO: should change this
					getController_worker().add_dependent_services_for_desktop(",authFactory");
					getThe_landing_page_worker().getThe_controller_worker().getThe_gen_support().setIsThereALoginActivity(true);
				}
			}
		}

		//MOBILE

		for (GpModuleProperties properties : views_to_import_from_components) {
			GpModuleMobileInfo gpModuleMobileInfo = properties.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
			if(gpModuleMobileInfo != null){
				List<GpModuleAngularViews> resources = Arrays.asList(gpModuleMobileInfo.getViews());
				for (GpModuleAngularViews resource : resources) {
					GpAngularMenuGenDto angularMenuGenDto = new GpAngularMenuGenDto();
					angularMenuGenDto.viewName = resource.getViewName();
					angularMenuGenDto.viewUrl = resource.getViewUrl();
					angularMenuGenDto.menuVisibility = resource.isMenuVisibility();
					angularMenuGenDto.viewLocation = resource.getViewLocation();
					if(resource.getControllerName()!=null)
						angularMenuGenDto.controller = ",\ncontroller: '" + resource.getControllerName() + "'";
					angularMenuGenDto.no_menu = resource.isNo_menu();
					getHtml_worker().getThe_dto_android_phone().add(angularMenuGenDto);
					getHtml_worker().getThe_dto_ios_phone().add(angularMenuGenDto);
					if (!controllers_to_import_android_phone.contains("" + resource.getControllerName())) {
						controllers_to_import_android_phone.add("" + resource.getControllerName());
						controllers_to_import_ios_phone.add("" + resource.getControllerName());
					}
				}
				System.out.println(properties.getName());
				if(properties.getName().equals("Geppetto Login")){ //TODO: should change this
					getController_worker().add_dependent_services_for_android_phone(",Settings");
					getController_worker().add_dependent_services_for_ios_phone(",Settings");
				}
			}
		}


	}

	/**
	 * The directory worker is a special type of worker all it does is set up
	 * the directory structure for the rest of the workers - previously all
	 * workers were responsible for setting up their own directories but that
	 * led to a lot of repeating code. - Dan Castillo 03/07/2015
	 *
	 * @param gen_manager
	 * @throws Exception
	 */
	private void set_up_directories(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		this.directory_gen_worker.set_generation_service(this);
		//gen_manager.update_job_status(project_id, user_id, username,
			//	"setting_up_dirctories_in_client-GpClientAngularJSGenService", "gen_processing");
		this.directory_gen_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	}

	private void set_up_landing_page_activity(IGpGenManager gen_manager) throws Exception{
		this.directory_gen_worker.set_up_paths_for_landing_page();
		this.the_landing_page_worker.generate_code(
					gen_manager.get_project(), gen_manager.getBase_configs(),
					gen_manager.getDerived_configs(), null, gen_manager);		
		
	}

	private void generate_index_files(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "index_gen_started-GpClientAngularJSGenService",
			//	"gen_processing");
		this.index_worker.generate_code(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs(), null, gen_manager);
	}

	private void prep_workers(IGpGenManager gen_manager) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		// index worker
		//gen_manager.update_job_status(project_id, user_id, username, "loading_index_worker-GpClientAngularJSGenService",
			//	"gen_processing");
		this.index_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		this.index_worker.set_generation_service(this);

		// controller worker
		//gen_manager.update_job_status(project_id, user_id, username,
			//	"loading_controller_worker-GpClientAngularJSGenService", "gen_processing");
		this.controller_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		this.controller_worker.set_generation_service(this);

		// html worker
		//gen_manager.update_job_status(project_id, user_id, username, "loading_html_worker-GpClientAngularJSGenService",
			//	"gen_processing");
		this.html_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		this.html_worker.set_generation_service(this);

		this.menu_worker.set_generation_service(this);

		this.appjs_worker.set_generation_service(this);
		
		if(this.templateName.equals("Nielsen PC")){
			this.nielsen_template_worker.setGenService(this);
		}else if(this.templateName.equals("Geppetto PC")){
			this.template_Worker.setGenService(this);
		}else if(this.templateName.equals("Nielsen Mobile")){
		}else if(this.templateName.equals("Nielsen Tablet")){
		}else if(this.templateName.equals("IBM PC")){
			this.ibm_template_worker.setGenService(this);
		}else if(this.templateName.equals("IBM Tablet")){
		}else if(this.templateName.equals("IBM Mobile")){
		}
		
		controllers_to_import_desktop = new ArrayList<String>();
		controllers_to_import_android_phone = new ArrayList<String>();
		controllers_to_import_android_tablet = new ArrayList<String>();
		controllers_to_import_ios_phone = new ArrayList<String>();
		controllers_to_import_ios_tablet = new ArrayList<String>();


		this.libs_worker.set_generation_service(this);

		this.the_landing_page_worker.set_generation_service(this);
		this.the_landing_page_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());

		this.the_geppetto_login_activity_worker.set_generation_service(this);
		this.the_geppetto_login_activity_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(),
				gen_manager.getDerived_configs());
	}

	private void process_flow(IGpGenManager gen_manager, GpActivity an_activity) throws Exception {

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		long master_flow_id = an_activity.getMaster_flow_id();
		ArrayList<GpFlowControl> a_flow = this.flow_dao.find_flow_by_id(master_flow_id);

		ArrayList<GpFlowControl> client_flow = new ArrayList<GpFlowControl>();
		for (GpFlowControl a_flow_comp : a_flow) {
			if (a_flow_comp.getType().equals(GpGenConstants.GpGenerationType_Client)) {
				client_flow.add(a_flow_comp);
			}

		}

		int i = 1;
		for (GpFlowControl flow_comp : client_flow) {
			Long the_count_for_seq = this.flow_dao.get_count_for_one_seq(master_flow_id,
					GpGenConstants.GpGenerationType_Client, i);

			if (the_count_for_seq == 0) {
				/*
				 * this means that it is a child of a previous sequence and was
				 * handled in the the while loop below and does not need to be
				 * handled again it would be more elegant if it was handled in
				 * the loop above that creates the server flows that this FOR
				 * loop iterates through _ Dan Castillo 11/26/2014
				 *
				 *
				 */
				continue;
			}
			int x = 1;
			while (x <= the_count_for_seq) {

				GpFlowControl the_flow = this.flow_dao.find_a_client_flow(master_flow_id, i, x);
				if (the_flow.getComponent_type().equals(GpBaseFlowConstants.Javascript_Angular_Flow_Component_type_GpController)) {

					//gen_manager.update_job_status(project_id, user_id, username,
						//	"gen_controller_in_client_side-GpClientAngularJSGenService", "gen_processing");

					this.controller_worker.generate_code_by_activity(an_activity, gen_manager.get_project(),
							gen_manager.getBase_configs(), gen_manager.getDerived_configs(), the_flow, gen_manager);
					x++;
					controllers_to_import_android_phone.add("" + an_activity.getName());
					controllers_to_import_android_tablet.add("" + an_activity.getName());
					controllers_to_import_desktop.add("" + an_activity.getName());
					controllers_to_import_ios_phone.add("" + an_activity.getName());
					controllers_to_import_ios_tablet.add("" + an_activity.getName());

					html_worker.set_controllers_to_views(an_activity.getName());

					//gen_manager.update_job_status(project_id, user_id, username,
						//	"controller_in_client_side_generated-GpClientAngularJSGenService", "gen_processing");

					continue;
				}
				x++;
			}

			i++;

		}
	}

	// setters ang getters

	public GpAngularLibsJsGenWorker getLibs_worker() {
		return libs_worker;
	}

	@Resource(name = "GpAngularLibsJsGenWorker")
	public void setLibs_worker(GpAngularLibsJsGenWorker libs_worker) {
		this.libs_worker = libs_worker;
	}

	public GpGenTemplateWorker getTemplate_Worker() {
		return template_Worker;
	}

	@Resource(name = "GpGenTemplateWorker")
	public void setTemplate_Worker(GpGenTemplateWorker template_Worker) {
		this.template_Worker = template_Worker;
	}

	public GpNotDefaultActivityGenWorker getNot_default_activity_worker() {
		return not_default_activity_worker;
	}

	@Resource(name = "GpNotDefaultActivityGenWorker")
	public void setNot_default_activity_worker(GpNotDefaultActivityGenWorker not_default_activity_worker) {
		this.not_default_activity_worker = not_default_activity_worker;
	}

	protected String file_separator = System.getProperty("file.separator");

	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}

	@Resource(name = "GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}

	public List<String> getControllers_to_import_for_desktop() {
		return controllers_to_import_desktop;
	}

	public void add_controller_to_import_for_desktop(String controller_name) {
		this.controllers_to_import_desktop.add(controller_name);
	}

	public List<String> getControllers_to_import_android_phone() {
		return controllers_to_import_android_phone;
	}

	public List<String> getControllers_to_import_android_tablet() {
		return controllers_to_import_android_tablet;
	}

	public List<String> getControllers_to_import_ios_phone() {
		return controllers_to_import_ios_phone;
	}

	public List<String> getControllers_to_import_ios_tablet() {
		return controllers_to_import_ios_tablet;
	}
	public GpAngularMenuGenWorker getMenu_worker() {
		return menu_worker;
	}

	@Resource(name = "GpAngularAppJsGenWorker")
	public void setAppjs_worker(GpAngularAppJsGenWorker appjs_worker) {
		this.appjs_worker = appjs_worker;
	}

	public GpAngularAppJsGenWorker getAppjs_worker() {
		return appjs_worker;
	}

	@Resource(name = "GpAngularMenuGenWorker")
	public void setMenu_worker(GpAngularMenuGenWorker menu_worker) {
		this.menu_worker = menu_worker;
	}

	public GpAngularHtmlGenWorker getHtml_worker() {
		return html_worker;
	}

	@Resource(name = "GpAngularHtmlGenWorker")
	public void setHtml_worker(GpAngularHtmlGenWorker html_worker) {
		this.html_worker = html_worker;
	}

	public GpAngularControllerGenWorker getController_worker() {
		return controller_worker;
	}

	@Resource(name = "GpAngularControllerGenWorker")
	public void setController_worker(GpAngularControllerGenWorker controller_worker) {
		this.controller_worker = controller_worker;
	}

	public GpAngularIndexGenWorker getIndex_worker() {
		return index_worker;
	}

	@Resource(name = "GpAngularIndexGenWorker")
	public void setIndex_worker(GpAngularIndexGenWorker index_worker) {
		this.index_worker = index_worker;
	}

	public GpAngularDirectoryGenWorker getDirectory_gen_worker() {
		return directory_gen_worker;
	}

	@Resource(name = "GpAngularDirectoryGenWorker")
	public void setDirectory_gen_worker(GpAngularDirectoryGenWorker directory_gen_worker) {
		this.directory_gen_worker = directory_gen_worker;
	}

	public GpGenFlowDao getFlow_dao() {
		return flow_dao;
	}

	@Resource(name = "GpGenFlowDao")
	public void setFlow_dao(GpGenFlowDao flow_dao) {
		this.flow_dao = flow_dao;
	}

	public GpAngularLandingPageGenWorker getThe_landing_page_worker() {
		return the_landing_page_worker;
	}

	@Resource(name = "GpAngularLandingPageGenWorker")
	public void setThe_landing_page_worker(GpAngularLandingPageGenWorker the_landing_page_worker) {
		this.the_landing_page_worker = the_landing_page_worker;
	}

	public GpGeppettoLoginActivityGenWorker getThe_geppetto_login_activity_worker() {
		return the_geppetto_login_activity_worker;
	}

	@Resource(name = "GpGeppettoLoginActivityGenWorker")
	public void setThe_geppetto_login_activity_worker(
			GpGeppettoLoginActivityGenWorker the_geppetto_login_activity_worker) {
		this.the_geppetto_login_activity_worker = the_geppetto_login_activity_worker;
	}
	
	public GenNielsenTemplateWorker getNielsen_template_worker() {
		return nielsen_template_worker;
	}
	
	@Resource(name = "GenNielsenTemplateWorker")
	public void setNielsen_template_worker(
			GenNielsenTemplateWorker nielsen_template_worker) {
		this.nielsen_template_worker = nielsen_template_worker;
	}
	
	public GpGenIBMTemplateWorker getIbm_template_worker() {
		return ibm_template_worker;
	}
	@Resource(name= "GpGenIBMTemplateWorker")
	public void setIbm_template_worker(GpGenIBMTemplateWorker ibm_template_worker) {
		this.ibm_template_worker = ibm_template_worker;
	}

	public GpProjectTemplateDao getProject_template() {
		return project_template;
	}
	
	@Resource(name = "GpProjectTemplateDao")
	public void setProject_template(GpProjectTemplateDao project_template) {
		this.project_template = project_template;
	}
	public String getIosTemplateName() {
		return iosTemplateName;
	}

	public void setIosTemplateName(String iosTemplateName) {
		this.iosTemplateName = iosTemplateName;
	}

	public String getMobileTemplateName() {
		return mobileTemplateName;
	}

	public void setMobileTemplateName(String mobileTemplateName) {
		this.mobileTemplateName = mobileTemplateName;
	}


}
