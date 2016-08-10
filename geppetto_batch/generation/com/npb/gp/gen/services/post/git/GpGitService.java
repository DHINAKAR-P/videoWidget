package com.npb.gp.gen.services.post.git;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.workers.git.GpGitCheckWorker;

@Component("GpGitService")
public class GpGitService extends GpBaseGenerationService{
	private GpGitCheckWorker the_git_check_worker;

	public GpGitCheckWorker getThe_git_check_worker() {
		return the_git_check_worker;
	}
	@Resource(name="GpGitCheckWorker")
	public void setThe_git_check_worker(GpGitCheckWorker the_git_check_worker) {
		this.the_git_check_worker = the_git_check_worker;
	}
	
	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		this.create_repository(gen_manager);
	}
	
	private void create_repository(IGpGenManager gen_manager) throws Exception{
		the_git_check_worker.start_git_check(gen_manager);
	}
}
