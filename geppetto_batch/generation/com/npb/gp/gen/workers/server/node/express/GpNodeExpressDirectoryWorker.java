package com.npb.gp.gen.workers.server.node.express;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;

@Component("GpNodeExpressDirectoryWorker")
public class GpNodeExpressDirectoryWorker extends GpGenNodeServerExpressBaseWorker{
	private Path server_config_root_path;
	private Path server_controllers_root_path;
	private Path server_daos_root_path;
	private Path server_models_root_path;
	private Path server_routes_root_path;
	private Path server_services_root_path;
	private Path server_sql_queries_root_path;
	private Path server_routes_routers_path;
	private Path server_root_path;
	private Path server_client_root_path;
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths();
	}
	
	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		this.create_config_folder(server_root_path);
		this.create_controllers_folder(server_root_path);
		this.create_daos_folder(server_root_path);
		this.create_models_folder(server_root_path);
		this.create_routes_folder(server_root_path);
		this.create_services_folder(server_root_path);
		this.create_sql_queries_folder(server_root_path);
		this.create_client_folder(server_root_path);
	}
	
	private void create_client_folder(Path server_root_path2) throws Exception {
		String gen_server_node_express_client_directory_name = "client";
		Path client_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_client_directory_name);
		Files.createDirectory(client_path);
		this.server_client_root_path = client_path;
	}

	private void set_up_paths(){
		server_root_path = (Path)this.derived_configs.get(
				GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH).getObject_value();
	}

	private void create_config_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_config_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_CONFIG_DIRECTORY_NAME).getValue();
		Path config_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_config_directory_name);
		Files.createDirectory(config_path);
		this.server_config_root_path = config_path;
	}
	
	private void create_controllers_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_controllers_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_CONTROLLERS_DIRECTORY_NAME).getValue();
		Path controllers_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_controllers_directory_name);
		Files.createDirectory(controllers_path);
		this.server_controllers_root_path = controllers_path;
	}
	
	private void create_daos_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_daos_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_DAOS_DIRECTORY_NAME).getValue();
		Path daos_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_daos_directory_name);
		Files.createDirectory(daos_path);
		this.server_daos_root_path = daos_path;
	}
	
	private void create_models_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_models_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_MODELS_DIRECTORY_NAME).getValue();
		Path models_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_models_directory_name);
		Files.createDirectory(models_path);
		this.server_models_root_path = models_path;
	}
	
	private void create_routes_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_routes_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_ROUTES_DIRECTORY_NAME).getValue();
		Path routes_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_routes_directory_name);
		Files.createDirectory(routes_path);
		this.server_routes_root_path = routes_path;
		this.create_routes_routers_folder(routes_path);
	}
	
	private void create_routes_routers_folder(Path routes_root_path) throws Exception{
		String gen_server_node_express_routers_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_ROUTERS_DIRECTORY_NAME).getValue();
		Path routers_path = Paths.get(routes_root_path.toString() + this.file_separator
				+  gen_server_node_express_routers_directory_name);
		Files.createDirectory(routers_path);
		this.server_routes_routers_path = routers_path;
	}
	
	private void create_services_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_services_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_SERVICES_DIRECTORY_NAME).getValue();
		Path services_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_services_directory_name);
		Files.createDirectory(services_path);
		this.server_services_root_path = services_path;
	}
	
	private void create_sql_queries_folder(Path server_root_path) throws Exception{
		String gen_server_node_express_sql_queries_directory_name = 
				this.base_configs.get(
						GpGenConstants.GEN_SERVER_NODE_EXPRESS_SQL_QUERIES_DIRECTORY_NAME).getValue();
		Path sql_queries_path = Paths.get(server_root_path.toString() + this.file_separator
				+  gen_server_node_express_sql_queries_directory_name);
		Files.createDirectory(sql_queries_path);
		this.server_sql_queries_root_path = sql_queries_path;
	}
	
	public void copy_directory(File base_directory, File final_directory)
			throws Exception {

		try {
			if (base_directory.isDirectory()) {
				if (!final_directory.exists())
					final_directory.mkdir();

				String[] chidren = base_directory.list();
				for (int i = 0; i < chidren.length; i++) {
					copy_directory(new File(base_directory, chidren[i]),
							new File(final_directory, chidren[i]));
				}
			} else {
				Files.copy(base_directory.toPath(), final_directory.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Path getServer_config_root_path() {
		return server_config_root_path;
	}

	public void setServer_config_root_path(Path server_config_root_path) {
		this.server_config_root_path = server_config_root_path;
	}

	public Path getServer_controllers_root_path() {
		return server_controllers_root_path;
	}

	public void setServer_controllers_root_path(Path server_controllers_root_path) {
		this.server_controllers_root_path = server_controllers_root_path;
	}

	public Path getServer_daos_root_path() {
		return server_daos_root_path;
	}

	public void setServer_daos_root_path(Path server_daos_root_path) {
		this.server_daos_root_path = server_daos_root_path;
	}

	public Path getServer_models_root_path() {
		return server_models_root_path;
	}

	public void setServer_models_root_path(Path server_models_root_path) {
		this.server_models_root_path = server_models_root_path;
	}

	public Path getServer_routes_root_path() {
		return server_routes_root_path;
	}

	public void setServer_routes_root_path(Path server_routes_root_path) {
		this.server_routes_root_path = server_routes_root_path;
	}

	public Path getServer_services_root_path() {
		return server_services_root_path;
	}

	public void setServer_services_root_path(Path server_services_root_path) {
		this.server_services_root_path = server_services_root_path;
	}

	public Path getServer_sql_queries_root_path() {
		return server_sql_queries_root_path;
	}
	
	public Path getServer_client_root_path() {
		return server_client_root_path;
	}

	public void setServer_sql_queries_root_path(Path server_sql_queries_root_path) {
		this.server_sql_queries_root_path = server_sql_queries_root_path;
	}

	public Path getServer_routes_routers_path() {
		return server_routes_routers_path;
	}

	public void setServer_routes_routers_path(Path server_routes_routers_path) {
		this.server_routes_routers_path = server_routes_routers_path;
	}

	public Path getServer_root_path() {
		return server_root_path;
	}

	public void setServer_root_path(Path server_root_path) {
		this.server_root_path = server_root_path;
	}
	
	
}
