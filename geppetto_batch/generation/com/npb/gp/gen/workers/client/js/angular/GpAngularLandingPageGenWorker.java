package com.npb.gp.gen.workers.client.js.angular;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.landingpage.GpAngularLandingPageControllerGenWorker;
import com.npb.gp.gen.workers.client.js.angular.landingpage.GpAngularLandingPageHtmlGenWorker;

@Component("GpAngularLandingPageGenWorker")
public class GpAngularLandingPageGenWorker extends GpGenJSClientAngularBaseWorker{
	private GpAngularLandingPageHtmlGenWorker the_html_worker;
	private GpAngularLandingPageControllerGenWorker the_controller_worker;
	
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		the_html_worker.prep_derived_values(the_project, base_configs, derived_configs);
		the_html_worker.set_generation_service(get_generation_service());
		the_controller_worker.prep_derived_values(the_project, base_configs, derived_configs);
		the_controller_worker.set_generation_service(get_generation_service());
		the_controller_worker.setThe_landing_page_worker(this);
		
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		
		the_html_worker.generate_code(the_project, base_configs, derived_configs, the_flow, gen_manager);
		the_controller_worker.generate_code(the_project, base_configs, derived_configs, the_flow, gen_manager);
	}
	
	public GpAngularLandingPageHtmlGenWorker getThe_html_worker() {
		return the_html_worker;
	}
	
	public GpAngularLandingPageControllerGenWorker getThe_controller_worker() {
		return the_controller_worker;
	}
	
	@Resource(name = "GpAngularLandingPageControllerGenWorker")
	public void setThe_controller_worker(GpAngularLandingPageControllerGenWorker the_controller_worker) {
		this.the_controller_worker = the_controller_worker;
	}
	
	@Resource(name = "GpAngularLandingPageHtmlGenWorker")
	public void setThe_html_worker(GpAngularLandingPageHtmlGenWorker the_html_worker) {
		this.the_html_worker = the_html_worker;
	}

}
