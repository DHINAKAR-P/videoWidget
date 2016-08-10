package com.npb.gp.gen.workers.client;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.client.angular.GpClientAngularJSGenService;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.npb.gp.gen.workers.client.support.GpGenIBMTemplateSupport;

@Component("GpGenIBMTemplateWorker")
public class GpGenIBMTemplateWorker extends GpGenBaseWorker{
	private GpGenIBMTemplateSupport gen_support;
	
	private String root_template_path;
	private GpClientAngularJSGenService genService;
	private Path views_path;
	public GpClientAngularJSGenService getGenService() {
		return genService;
	}
	@Resource(name="GpClientAngularJSGenService")
	public void setGenService(GpClientAngularJSGenService genService) {
		this.genService = genService;
	}
	
	
	public GpGenIBMTemplateSupport getGen_support() {
		return gen_support;
	}
	
	@Resource(name= "GpGenIBMTemplateSupport")
	public void setGen_support(GpGenIBMTemplateSupport gen_support) {
		this.gen_support = gen_support;
	}
	public String getRoot_template_path() {
		return root_template_path;
	}
	@Override
	public void generate_code(GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs,
			GpFlowControl the_flow,IGpGenManager gen_manager) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		set_up_paths_and_init();
		if(getGenService().getDirectory_gen_worker().desktop_created)
			this.do_generation();
	}
	
	public void set_up_paths_and_init() throws Exception{
		views_path = getGenService().getDirectory_gen_worker().getWindows_app_views_directory_path();
		root_template_path = this.base_configs
				.get("root_code_template_location").getValue();
		gen_support.setThe_worker(this);
		gen_support.init();
	}
	
	public void do_generation() throws Exception{
		generate_css();
		generate_menu();
		generate_header();
		generate_footer();
		generate_asset();
		generate_ibm_js();
		generate_fotorama_js();
	}
	
	public void generate_header() throws Exception{
		//if theme == Geppetto_THEME?
		ST st = gen_support.handle_header_generation_for_geppetto_theme();
		String the_string_path = views_path.toString() + this.file_separator + "header.html";
		Path the_path = Paths.get(the_string_path);
		this.write_file(the_path, st);
	}
	public void generate_footer() throws Exception{
		//if theme == Geppetto_THEME?
		ST st = gen_support.handle_footer_generation_for_geppetto_theme();
		String the_string_path = views_path.toString() + this.file_separator + "footer.html";
		Path the_path = Paths.get(the_string_path);
		this.write_file(the_path, st);
	}
	public void generate_menu() throws Exception{
		//if theme == Geppetto_THEME?
		ST st = gen_support.handle_menu_generation_for_geppetto_theme();
		String the_string_path = views_path.toString() + this.file_separator + "menu.html";
		Path the_path = Paths.get(the_string_path);
		this.write_file(the_path, st);
		ST controller_menu_st = gen_support.handle_menu_controller_generation_for_geppetto_theme();
		Path controller_path = Paths.get(getGenService().getDirectory_gen_worker().getWindows_app_controller_directory_path() + this.file_separator + "MenuCtrl.js");
		this.write_file(controller_path, controller_menu_st);
	}
	public void generate_css() throws Exception{
		//if theme == Geppetto_THEME
		gen_support.handle_css_generation_for_geppetto_theme();
	}
	
	public void generate_asset() throws Exception{
		gen_support.handle_asset_generation_for_ibm_theme();
	}
	
	public void generate_ibm_js() throws Exception{
		gen_support.handle_generate_ibm_js_for_ibm_theme();
	}
	
	public void generate_fotorama_js() throws Exception{
		gen_support.handle_generate_fotorama_js_for_ibm_theme();
	}
}
