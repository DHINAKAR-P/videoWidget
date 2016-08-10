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
import com.npb.gp.gen.json.mappers.modules.GpModuleNodePackage;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeJsExpressPackage;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;

@Component("GpPackageGenWorker")
public class GpPackageGenWorker extends GpGenNodeServerExpressBaseWorker{
	private Path template_group_path;
	private String file_name = "package";
	private String file_extension = ".json";
	private List<GpModuleNodePackage> packages_to_install;
	 
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		packages_to_install = new ArrayList<>();
		this.set_up_paths_and_templates();
	}

	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path server_root_path = getGen_service().getThe_directory_worker().getServer_root_path();
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("projectName", the_project.getName());
		List<GpModuleProperties> modules = getGen_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties module : modules){
			GpModuleNodePackage[] packages = module.getServer_meta_data().getNode().getExpress().getNode_packages();
			for(int i=0;i<packages.length;i++){
				packages_to_install.add(packages[i]);
			}
		}
		st.add("packages_to_install", packages_to_install);
		String the_path_string = server_root_path + this.file_separator + file_name + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	private void set_up_paths_and_templates(){
		//Get template name and path
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_package_template_location = this.base_configs
				.get("server_nodejs_express_package_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_package_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_package_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_package_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
	}
	
	public void add_package_to_install(GpModuleNodePackage package_to_install){
		packages_to_install.add(package_to_install);
	}
}
