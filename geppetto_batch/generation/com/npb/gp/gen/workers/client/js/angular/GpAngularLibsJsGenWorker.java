package com.npb.gp.gen.workers.client.js.angular;

import java.io.File;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenDirectoryWorker;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;

@Component("GpAngularLibsJsGenWorker")
public class GpAngularLibsJsGenWorker extends GpGenJSClientAngularBaseWorker{
	private GpGenDirectoryWorker directory_worker;
	private String core_template_location_temp;
	public GpGenDirectoryWorker getDirectory_worker() {
		return directory_worker;
	}
	@Resource(name="GpGenDirectoryWorker")
	public void setDirectory_worker(GpGenDirectoryWorker directory_worker) {
		this.directory_worker = directory_worker;
	}
	
	@Override
	public void generate_code(GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs,
			GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		String[] tokens;
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		
		String angular_index_file_template_location = this.base_configs
				.get("angular_controller_template_location").getValue();
		
		tokens = this.tokenize_string(
								angular_index_file_template_location, null);
		core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
		}
		if(get_generation_service().getDirectory_gen_worker().desktop_created)
			handle_libs_for_desktop(gen_manager);
		if(get_generation_service().getDirectory_gen_worker().android_created){
			handle_libs_for_android_phone();
			handle_libs_for_android_tablet();
		}
		if(get_generation_service().getDirectory_gen_worker().ios_created){
			handle_libs_for_ios_phone();
			handle_libs_for_ios_tablet();
		}
	}
	
	private void handle_libs_for_ios_tablet() throws Exception {
		String base_directory = core_template_location_temp + this.file_separator + "libs" + this.file_separator + "mobile";
		String final_directory = get_generation_service().getDirectory_gen_worker().getIos_tablet_app_lib_root_directory_path().toString();
		this.directory_worker.copy_directory(new File(base_directory), new File(final_directory));
	}
	private void handle_libs_for_ios_phone() throws Exception{
		String base_directory = core_template_location_temp + this.file_separator + "libs" + this.file_separator + "mobile";
		String final_directory = get_generation_service().getDirectory_gen_worker().getIos_phone_app_lib_root_directory_path().toString();
		this.directory_worker.copy_directory(new File(base_directory), new File(final_directory));
	}
	private void handle_libs_for_android_tablet() throws Exception{
		String base_directory = core_template_location_temp + this.file_separator + "libs" + this.file_separator + "mobile";
		String final_directory = get_generation_service().getDirectory_gen_worker().getAndroid_tablet_app_lib_root_directory_path().toString();
		this.directory_worker.copy_directory(new File(base_directory), new File(final_directory));
	}
	private void handle_libs_for_desktop(IGpGenManager gen_manager) throws Exception{
		//Creating lib directory
		String base_directory = core_template_location_temp + this.file_separator + "libs" + this.file_separator + "desktop";
		String final_directory = get_generation_service().getDirectory_gen_worker().getWindows_app_lib_root_directory_path().toString();
		this.directory_worker.copy_directory(new File(base_directory), new File(final_directory));
	}
	
	private void handle_libs_for_android_phone() throws Exception{
		String base_directory = core_template_location_temp + this.file_separator + "libs" + this.file_separator + "mobile";
		String final_directory = get_generation_service().getDirectory_gen_worker().getAndroid_phone_app_lib_root_directory_path().toString();
		this.directory_worker.copy_directory(new File(base_directory), new File(final_directory));
	}
}
