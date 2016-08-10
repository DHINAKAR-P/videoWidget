package com.npb.gp.gen.workers.server.node.express;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
     
@Component("GpConfigGenWorker")
public class GpConfigGenWorker extends GpGenNodeServerExpressBaseWorker{
	private JSONObject the_json_object;
	private String file_name = "config.json";
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		the_json_object = new JSONObject();
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Path config_path = getGen_service().getThe_directory_worker().getServer_config_root_path();
		File file = new File(config_path + this.file_separator + file_name);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(the_json_object.toString());
		writer.close();
	}
	
	public void add_json_value_jsonObject(String key, JSONObject jsonObject) throws JSONException{
		the_json_object.put( key, jsonObject);
	}
	
	public String getFile_name() {
		return file_name;
	}
}
