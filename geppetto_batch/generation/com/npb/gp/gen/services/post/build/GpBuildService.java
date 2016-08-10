package com.npb.gp.gen.services.post.build;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.workers.build.GpBuildJavaScriptAngularIonicWorker;
import com.npb.gp.gen.workers.build.GpBuildJavaSpringBootWorker;
import com.npb.gp.gen.workers.build.GpBuildJavaSpringWorker;
import com.npb.gp.gen.workers.build.GpBuildMySQLWorker;
/**
 * 
 * @author Adonay
 *	Run the gradle script depending on the languages and frameworks
 */
import com.npb.gp.gen.workers.build.GpBuildNodeJsExpressWorker;
@Component("GpBuildService")
public class GpBuildService extends GpBaseGenerationService{
	GpBuildJavaSpringWorker the_build_java_spring_worker;
	GpBuildJavaScriptAngularIonicWorker the_build_ionic_apps_worker;
	GpBuildNodeJsExpressWorker the_node_js_express_build_worker;
	GpBuildJavaSpringBootWorker the_spring_boot_build_worker;
	GpBuildMySQLWorker the_mysql_worker;
	
	private void prepare_workers(IGpGenManager gen_manager) throws Exception{
		the_spring_boot_build_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(), gen_manager.getBase_configs());
		the_spring_boot_build_worker.setThe_service(this);
		the_build_ionic_apps_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(), gen_manager.getBase_configs());
		the_build_ionic_apps_worker.setThe_service(this);
		the_node_js_express_build_worker.prep_derived_values(gen_manager.get_project(), gen_manager.getBase_configs(), gen_manager.getBase_configs());
		
	}
	
	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		prepare_workers(gen_manager);
		// TODO Auto-generated method stub
		super.check_project_type(gen_manager);
		if(DB_isMySQL){
			the_mysql_worker.start_gradle_build_script(gen_manager);
		}
		if(back_isJavaSpring){//should delete this
			//the_build_java_spring_worker.start_gradle_build_script(gen_manager);
		}
		if(back_isJavaSpringBootJpa){
			the_spring_boot_build_worker.buildWar(gen_manager);
		}
		if(back_isJavaScriptNodeJSExpress){//should implement this for docker
			the_node_js_express_build_worker.deploy_and_create_project(gen_manager);
		}
		if(front_isJavaScriptAngular){//this needs to be tested on docker
			the_build_ionic_apps_worker.build_ionic_apps(gen_manager);
		}
	}

	//Getters and Setters
	
	public GpBuildJavaSpringBootWorker getThe_spring_boot_build_worker() {
		return the_spring_boot_build_worker;
	}
	
	@Resource(name="GpBuildJavaSpringBootWorker")
	public void setThe_spring_boot_build_worker(GpBuildJavaSpringBootWorker the_spring_boot_build_worker) {
		this.the_spring_boot_build_worker = the_spring_boot_build_worker;
	}
	
	public GpBuildJavaSpringWorker getThe_build_java_spring_worker() {
		return the_build_java_spring_worker;
	}

	@Resource(name="GpBuildJavaSpringWorker")
	public void setThe_build_java_spring_worker(GpBuildJavaSpringWorker the_build_java_spring_worker) {
		this.the_build_java_spring_worker = the_build_java_spring_worker;
	}

	public GpBuildJavaScriptAngularIonicWorker getThe_build_ionic_apps_worker() {
		return the_build_ionic_apps_worker;
	}

	@Resource(name="GpBuildJavaScriptAngularIonicWorker")
	public void setThe_build_ionic_apps_worker(GpBuildJavaScriptAngularIonicWorker the_build_ionic_apps_worker) {
		this.the_build_ionic_apps_worker = the_build_ionic_apps_worker;
	}	
	
	public GpBuildNodeJsExpressWorker getThe_node_js_express_build_worker() {
		return the_node_js_express_build_worker;
	}
	
	@Resource(name="GpBuildNodeJsExpressWorker")
	public void setThe_node_js_express_build_worker(GpBuildNodeJsExpressWorker the_node_js_express_build_worker) {
		this.the_node_js_express_build_worker = the_node_js_express_build_worker;
	}
	
	public GpBuildMySQLWorker getThe_mysql_worker() {
		return the_mysql_worker;
	}
	
	@Resource(name="GpBuildMySQLWorker")
	public void setThe_mysql_worker(GpBuildMySQLWorker the_mysql_worker) {
		this.the_mysql_worker = the_mysql_worker;
	}
	
}
