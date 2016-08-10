package com.npb.gp.gen.workers.server.node.express;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleServerNodeExpress;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;

@Component("GpExpressMainFileWorker")
public class GpExpressMainFileWorker extends GpGenNodeServerExpressBaseWorker {
	private Path template_path;
	private String template_name;
	private String file_extension = ".js";
	private String file_name = "main";
	private List<String> vars_declarations;
	private String code_to_add_before_app_configs;
	private List<String> app_configs;

	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
		vars_declarations = new ArrayList<>();
		app_configs = new ArrayList<>();
		code_to_add_before_app_configs = "";
	}
	
	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("server_nodejs_express_main_js_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_main_js_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		template_name =  this.base_configs.get(
				"server_nodejs_express_main_js_template_name").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_path =   (Path) package_path_config.getObject_value();
	}

	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path server_root_path = getGen_service().getThe_directory_worker().getServer_root_path();
		STGroupDir st_Group = new STGroupDir(this.template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.template_name);
		st.add("project_name", the_project.getName() + "_" + the_project.getCreatedby());
		List<GpModuleProperties> modules = getGen_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : modules){
			GpModuleServerNodeExpress module_node_express = module.getServer_meta_data().getNode().getExpress();
			String[] vars = module_node_express.getVars();
			for(int i=0;i<vars.length;i++){
				vars_declarations.add(vars[i]);
			}
			String[] code_to_add_before_app_config = module_node_express.getCode_to_add_before_app_configs();
			for(int i=0;i<code_to_add_before_app_config.length;i++){
				code_to_add_before_app_configs += code_to_add_before_app_config[i] + "\n";
			}
			String[] app_configurations = module_node_express.getApp_configs();
			for(int i=0;i<app_configurations.length;i++){
				app_configs.add(app_configurations[i]);
			}
		}
		st.add("code_to_add_before_app_configs", code_to_add_before_app_configs);
		st.add("vars_declarations", get_string_var_declarations());
		st.add("app_configs", get_string_app_configs());
		String the_path_string = server_root_path.toString() + this.file_separator + file_name + file_extension;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	public String get_string_app_configs(){
		String string_app_configs = "";
		for(String app_config : app_configs){
			string_app_configs += app_config + "\n";
		}
		return string_app_configs;
	}
	
	public String get_string_var_declarations(){
		String string_var_declarations = "";
		for(String var : vars_declarations){
			string_var_declarations += var + "\n";
		}
		return string_var_declarations;
	}
}
