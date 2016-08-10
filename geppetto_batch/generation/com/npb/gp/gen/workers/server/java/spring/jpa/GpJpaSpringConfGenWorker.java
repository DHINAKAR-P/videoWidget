package com.npb.gp.gen.workers.server.java.spring.jpa;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;

/**
 *
 * @author Dan Castillo
 * Date Created: 01/28/2015</br>
 * @since .2</p>
 *
 * Generates the spring config file</br>
 * <b>Please note: this class depends on the GpResourceBundleWorker</br>
 * to execute first as that class creates the WEBINF PATH/Directory</br>
 * the reason for the dependency is that at this time - 01/28/2015</br
 * I amd the choice to not allow code generation unless you a project contains</br>
 * at least one activity, one noun, and one verb must added to a project</br>
 * before code can be generated. Once a project has a noun then</br>
 * the code generator will generate a resource bundle and therefore the associated</br>
 * WEBINF directory which this class will depend on - Dan Castillo - 01/28/2015</p>

 *
 *
 */

@Component("GpJpaSpringConfGenWorker")
public class GpJpaSpringConfGenWorker extends GpGenJavaServerSpringBaseWorker {

	private Path spring_conf_template_group_path;
	private String spring_conf_template_name;

	private Path jdbc_conf_template_group_path;
	private String jdbc_conf_template_name;


	private Path webxml_conf_template_group_path;
	private String webxml_conf_template_name;

	private Path spring_database_template_path;
	private String spring_database_template_name;


	@Override
	/**
	 * Generates DAOS for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

	}


	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{


	//System.out.println("In GpLegacySpringConf - generate_code_by_activity");

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;


		this.set_up_paths_and_templates();
		this.do_generation(gen_manager);

	}

	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
	}
	@SuppressWarnings("unchecked")
	public void do_generation(IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_spring_config-GpLegacySpringConfGenWorker", "gen_processing");
		this.generate_spring_config();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_jdbc_config-GpLegacySpringConfGenWorker", "gen_processing");
		this.generate_jdbc_config();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_webxml_config-GpLegacySpringConfGenWorker", "gen_processing");
		this.generate_webxml_config();

		this.generate_spring_database();
	}

	private void generate_spring_database() throws Exception{
		Path web_inf_path = (Path) derived_configs
				.get(GpGenConstants.WEB_INF_PATH).getObject_value();
		STGroupDir st_Group = new STGroupDir(this.spring_database_template_path.toString() , '$', '$');
		ST st = st_Group.getInstanceOf(this.spring_database_template_name);
		String the_path_string = web_inf_path.toString() + this.file_separator + "spring-database.xml";
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}

	private void generate_webxml_config() throws Exception{


		String file_name_location = this.webxml_conf_template_group_path.toString()
												+ "\\" + this.webxml_conf_template_name;

		STGroupDir webxmlGroup = new STGroupDir(this.webxml_conf_template_group_path.toString() , '$', '$');
		//System.out.println("$$$$$$$$$$$$$$$ this.webxml_conf_template_group_path.toString()  "
		//		+ this.webxml_conf_template_group_path.toString() );

		//ST webxmlST = webxmlGroup.getInstanceOf(file_name_location);
		ST webxmlST = webxmlGroup.getInstanceOf(this.webxml_conf_template_name);

		Path web_inf_path = (Path) derived_configs
						.get(GpGenConstants.WEB_INF_PATH).getObject_value();


		String file_name = "web";

		webxmlST.add("appName", super.the_project.getName().toUpperCase());
		webxmlST.add("springServletName",this.the_project.getName().toLowerCase());
/*
		if(get_generation_service().getNot_default_activity_worker().getModule_properties_list() != null){
			List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			String code_to_add_to_web_xml = "";
			String file_to_import_to_web_xml = "";
			for(GpModuleProperties properties : list){
				String[] prop_code_to_add_to_web_xml = properties.getCode_to_add_to_web_xml();
				for(int i=0;i<prop_code_to_add_to_web_xml.length;i++){
					code_to_add_to_web_xml += "\n" + prop_code_to_add_to_web_xml[i];
				}
				String[] prop_files_to_import_to_web_xml = properties.getFiles_to_import_to_web_xml();
				for(int i=0;i<prop_files_to_import_to_web_xml.length;i++){
					file_to_import_to_web_xml += "," +prop_files_to_import_to_web_xml[i];
				}
			}
			webxmlST.add("configs_to_add", code_to_add_to_web_xml);
			webxmlST.add("configs_to_import", file_to_import_to_web_xml);
		}
*/
		String file_extension = ".xml";


		String the_path_string = web_inf_path.toString() + this.file_separator
		+ this.file_separator + file_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, webxmlST);


	}

	private void generate_jdbc_config() throws Exception{

		String file_name_location = this.jdbc_conf_template_group_path.toString()
				+ this.file_separator + this.jdbc_conf_template_name;

		STGroupDir webxmlGroup = new STGroupDir(this.jdbc_conf_template_group_path.toString() , '$', '$');

		//ST webxmlST = webxmlGroup.getInstanceOf(file_name_location);
		ST webxmlST = webxmlGroup.getInstanceOf(this.jdbc_conf_template_name);

		Path web_inf_path = (Path) derived_configs
						.get(GpGenConstants.WEB_INF_PATH).getObject_value();


		String file_name = "jdbc";


		webxmlST.add("driverClassName", "com.mysql.jdbc.Driver");
		webxmlST.add("url", "jdbc:mysql://localhost:3306/"
							+super.the_project.getName().toLowerCase()
							+ "?useUnicode=true&characterEncoding=UTF-8");
		webxmlST.add("username", "gepuser");
		webxmlST.add("password", "tang3456");



		String file_extension = ".properties";


		String the_path_string = web_inf_path.toString() + this.file_separator +"resources"
		+ this.file_separator + file_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, webxmlST);



	}
	@SuppressWarnings("unchecked")
	private void generate_spring_config() throws Exception{

		String file_name_location = spring_conf_template_group_path.toString()
												+ this.file_separator + this.spring_conf_template_name;

		//System.out.println("@@@@@ file_name_location is: " + file_name_location );
		STGroupDir webxmlGroup = new STGroupDir(spring_conf_template_group_path.toString(), '$', '$');

		//System.out.println("@@@ webxmlGroup is: " + webxmlGroup);

		//ST webxmlST = webxmlGroup.getInstanceOf(file_name_location);

		ST webxmlST = webxmlGroup.getInstanceOf(this.spring_conf_template_name);

		//System.out.println("@@@ webxmlST is: " + webxmlST);
		Path web_inf_path = (Path) derived_configs
						.get(GpGenConstants.WEB_INF_PATH).getObject_value();
		String file_name = this.the_project.getName().toLowerCase() + "-servlet";

		String basepackage = super.derived_configs.get(
	 			GpGenConstants.APP_BASE_PACKAGE).getValue();

		webxmlST.add("basepackage", basepackage + ".*");
/*
		if(get_generation_service().getNot_default_activity_worker().getModule_properties_list() != null){
			List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			String package_to_import = "";
			for(GpModuleProperties properties : list){
				package_to_import += "<context:component-scan base-package=\"" + properties.getBase_package() + ".*\" />\n";
			}
			webxmlST.add("packages_from_components", package_to_import);
		}

*/

		GpArchitypeConfigurations activities_prop = super.derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);


		ArrayList<GpActivity> the_activities =
				(ArrayList<GpActivity>) activities_prop.getObject_value();


		webxmlST.add("bundleInfo", this.set_up_resource_bundle_info(the_activities));
		webxmlST.add("dbprops", this.get_db_props());
		webxmlST.add("webconfigs", this.setup_third_party_web_configurations());


		String file_extension = ".xml";


		String the_path_string = web_inf_path.toString()
		+ this.file_separator + file_name + file_extension
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, webxmlST);


	}


	private String get_db_props(){
		String db_props = "<beans:property name=\"driverClassName\">\n"
				+"\t <beans:value>${jdbc.driverClassName}</beans:value>\n"
				+ "</beans:property>\n"
				+ " <beans:property name=\"url\">\n"
				+ "\t<beans:value>${jdbc.url}</beans:value>\n"
				+ " </beans:property>\n"
				+ "<beans:property name=\"username\">\n"
				+ "\t<beans:value>${jdbc.username}</beans:value>\n"
				+ "</beans:property>\n"
				+ "<beans:property name=\"password\">\n"
				+"\t<beans:value>${jdbc.password}</beans:value>\n"
				+"</beans:property>";

		return db_props;


	}
	private String set_up_resource_bundle_info(ArrayList<GpActivity> act_list){
		String bundle_name = "";

		String the_bundle_info = "<context:property-placeholder location=\"/WEB-INF/resources/jdbc.properties\" order=\"1\" ignore-unresolvable=\"true\"/>\n";
		String first_part = "<context:property-placeholder location=\"/WEB-INF/resources/sql_queries/";
		String last_part = "ignore-unresolvable=\"true\"/>\n";

		String first_config_part = "<context:property-placeholder location=\"/WEB-INF/resources/";
		String last_config_part = "ignore-unresolvable=\"true\"/>\n";

		int bundle_order = 2;
		for(GpActivity activity : act_list){
			if(activity.HasSQLgeneration()){
				bundle_name = super.get_generation_service().getResource_bundle_worker().get_sql_bundle_name(activity);
				the_bundle_info = the_bundle_info + first_part + bundle_name + "\""+ " order=\"" + bundle_order + "\" " + last_part;
				bundle_order++;
			}
		}
/*
		List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties propertie : list){
			String [] sql_queries = propertie.getSql_queries();
			String [] config_properties = propertie.getConfig_properties();
			for(String sql_querie : sql_queries){
				the_bundle_info = the_bundle_info + first_part + sql_querie + "\""+ " order=\"" + bundle_order + "\" " + last_part;
				bundle_order++;
			}
			for (String config_property : config_properties) {
				the_bundle_info = the_bundle_info + first_config_part + config_property + "\""+ " order=\"" + bundle_order + "\" " + last_config_part;
				bundle_order++;
			}
		}

*/
		return the_bundle_info;
	}

	private String setup_third_party_web_configurations() {
		String third_party_web_config = "";
/*
		List<GpModuleProperties> list = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
		for(GpModuleProperties property : list){
			List<Map<String, String>> web_configs = property.getWeb_configurations();
			for (Map<String, String> web_config : web_configs) {
				for (Entry<String, String> config : web_config.entrySet()) {
					third_party_web_config += config.getValue() + "\n";
				}
			}
		}
*/
		return third_party_web_config;
	}

	private void set_up_paths_and_templates(){
		this.set_up_paths_and_templates_spring_conf();
		this.set_up_paths_and_templates_jdbc_conf();
		this.set_up_paths_and_templates_webxlm_file();
		this.set_up_paths_and_templates_spring_database_file();
	}

	private void set_up_paths_and_templates_spring_database_file(){
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_index_models_template_location = this.base_configs
				.get("server_java_spring_spring_database_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_index_models_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_spring_database_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		spring_database_template_name =  this.base_configs.get(
				"server_java_spring_spring_database_template_name_location").getValue();
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.spring_database_template_path =   (Path) package_path_config.getObject_value();
	}

	private void set_up_paths_and_templates_webxlm_file(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String spring_legacy_webxml_file_template_location = this.base_configs
			.get("spring_legacy_webxml_file_template_location").getValue();


		tokens = this.tokenize_string(
				spring_legacy_webxml_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_webxml_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations spring_legacy_webxml_file_template_location_path_config
												= this.derived_configs.get(config_name);

		if(spring_legacy_webxml_file_template_location_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"spring_legacy_webxml_file_template_name").getValue();

			this.webxml_conf_template_name = template_name;

			Path webxml_conf_template_path =
					Paths.get(core_template_location_temp);

			spring_legacy_webxml_file_template_location_path_config = new GpArchitypeConfigurations();

			spring_legacy_webxml_file_template_location_path_config.setName(config_name);
			spring_legacy_webxml_file_template_location_path_config.setObject_value(webxml_conf_template_path);
			this.derived_configs.put(config_name, spring_legacy_webxml_file_template_location_path_config);

		}


		this.webxml_conf_template_group_path =
		   (Path)spring_legacy_webxml_file_template_location_path_config
			  										.getObject_value();


	}
	private void set_up_paths_and_templates_jdbc_conf(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String jdbc_conf_file_template_location = this.base_configs
			.get("jdbc_conf_file_template_location").getValue();


		tokens = this.tokenize_string(
				jdbc_conf_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_jdbc.properties" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;


		GpArchitypeConfigurations jdbc_conf_file_template_location_path_config
												= this.derived_configs.get(config_name);

		if(jdbc_conf_file_template_location_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"jdbc_conf_file_template_name").getValue();

			this.jdbc_conf_template_name = template_name;

			Path jdbc_conf_file_template_path =
					Paths.get(core_template_location_temp);
										//+ this.file_separator + template_name);
			jdbc_conf_file_template_location_path_config = new GpArchitypeConfigurations();

			jdbc_conf_file_template_location_path_config.setName(config_name);
			jdbc_conf_file_template_location_path_config.setObject_value(jdbc_conf_file_template_path);
			this.derived_configs.put(config_name, jdbc_conf_file_template_location_path_config);

		}

		this.jdbc_conf_template_group_path =
		   (Path)jdbc_conf_file_template_location_path_config
			  										.getObject_value();


	}


	private void set_up_paths_and_templates_spring_conf(){

		String config_name = "";
		String[] tokens;

		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String spring_jpa_config_file_template_location = this.base_configs
			.get("spring_jpa_config_file_template_location").getValue();

		tokens = this.tokenize_string(
				spring_jpa_config_file_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "-servlet.xml" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations spring_legacy_config_file_template_location_path_config
												= this.derived_configs.get(config_name);

		if(spring_legacy_config_file_template_location_path_config  == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"spring_jpa_config_file_template_name").getValue();

			this.spring_conf_template_name = template_name;

			Path spring_legacy_config_file_template_path =
					Paths.get(core_template_location_temp);
										//+ this.file_separator + template_name);
			spring_legacy_config_file_template_location_path_config = new GpArchitypeConfigurations();

			spring_legacy_config_file_template_location_path_config.setName(config_name);
			spring_legacy_config_file_template_location_path_config.setObject_value(spring_legacy_config_file_template_path);
			this.derived_configs.put(config_name, spring_legacy_config_file_template_location_path_config);

		}

		this.spring_conf_template_group_path =
		   (Path)spring_legacy_config_file_template_location_path_config
			  												.getObject_value();
	}
}
