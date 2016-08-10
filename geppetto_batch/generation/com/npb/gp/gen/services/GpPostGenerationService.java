package com.npb.gp.gen.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.post.build.GpBuildService;
import com.npb.gp.gen.services.post.git.GpGitService;
import com.npb.gp.gen.services.post.project.GpProjectGenerationService;
@Component("GpPostGenerationService")
public class GpPostGenerationService extends GpBaseGenerationService  {
	private GpProjectGenerationService the_project_service;
	private GpGitService the_git_service;
	private GpBuildService the_build_service;
	
	private void prep_services(){
		this.the_build_service.set_activity_service(getActivity_service());
	}
	
	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		this.prep_services();
		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();
		// TODO Auto-generated method stub
		the_build_service.generate(gen_manager);
		the_project_service.generate(gen_manager); 
		the_git_service.generate(gen_manager);
		gen_manager.update_job_status(project_id, user_id, username,"gen_finished", "build_and_deployment_script_executed_successfully!", "");
	}
	
	public GpGitService getThe_git_service() {
		return the_git_service;
	}
	@Resource(name="GpGitService")
	public void setThe_git_service(GpGitService the_git_service) {
		this.the_git_service = the_git_service;
	}
	public GpProjectGenerationService getThe_project_service() {
		return the_project_service;
	}
	@Resource(name="GpProjectGenerationService")
	public void setThe_project_service(
			GpProjectGenerationService the_project_service) {
		this.the_project_service = the_project_service;
	}

	public GpBuildService getThe_build_service() {
		return the_build_service;
	}
	@Resource(name="GpBuildService")
	public void setThe_build_service(GpBuildService the_build_service) {
		this.the_build_service = the_build_service;
	}
	
}
