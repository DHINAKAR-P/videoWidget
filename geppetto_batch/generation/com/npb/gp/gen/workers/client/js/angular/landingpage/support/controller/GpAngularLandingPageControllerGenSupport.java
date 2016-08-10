package com.npb.gp.gen.workers.client.js.angular.landingpage.support.controller;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.workers.client.js.angular.landingpage.GpAngularLandingPageControllerGenWorker;

@Component("GpAngularLandingPageControllerGenSupport")
public class GpAngularLandingPageControllerGenSupport {
	private GpAngularLandingPageControllerGenWorker the_worker;
	private boolean IsThereALoginActivity;
	
	public String get_controller_code(){
		if(IsThereALoginActivity)
			return "if (Settings.global.loggedIn) {\n\t"
					+ "$location.path('"+ the_worker.getThe_landing_page_worker().getThe_html_worker().getRoute_url()+"');"
					+ "}\nelse\n\t$location.path('Login');";
		return "$location.path('"+ the_worker.getThe_landing_page_worker().getThe_html_worker().getRoute_url()+"');";
	}
	
	public String get_controller_code_for_desktop(){
		if(IsThereALoginActivity)
			return "if (authFactory.global.loggedIn) {\n\t"
					+ "$location.path('/"+ the_worker.getThe_landing_page_worker().getThe_html_worker().getRoute_url_desktop()+"');"
					+ "}\nelse\n\t$location.path('/login');";
		return "$location.path('/"+ the_worker.getThe_landing_page_worker().getThe_html_worker().getRoute_url_desktop()+"');";
	}
	
	public GpAngularLandingPageControllerGenWorker getThe_worker() {
		return the_worker;
	}
	
	public void setThe_worker(GpAngularLandingPageControllerGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public void setIsThereALoginActivity(boolean isThereALoginActivity) {
		IsThereALoginActivity = isThereALoginActivity;
	}

}
