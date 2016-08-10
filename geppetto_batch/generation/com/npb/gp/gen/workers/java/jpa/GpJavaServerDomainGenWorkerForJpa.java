package com.npb.gp.gen.workers.java.jpa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.npb.gp.gen.workers.server.java.spring.support.domain.GpNounAttributeForJpa;

/**
* Modified Date: 08-1-2016</br>
* Modified By: Kumaresan Perumal</br>
*   <p>
*   I added these statements
*    1 . id_attrib.setAnnotations("@Id \n @GeneratedValue"); is for setting id and generated value annotation
*    2 . an_attrib.setAnnotations("@Column"); is to annotate a filed
*   </p>
*/

@Component("GpJavaServerDomainGenWorkerForJpa")
public class GpJavaServerDomainGenWorkerForJpa extends GpGenBaseWorker {

	private Path template_group_path;
	private Path core_domain_path;
	private String app_base_package_name;
	private String domain_core_package_name;
	private GpServerJavaSpringGenService spring_gen_service;


	public Path getTemplate_group_path() {
		return template_group_path;
	}

	public void setTemplate_group_path(Path template_group_path) {
		this.template_group_path = template_group_path;
	}

	public Path getCore_domain_path() {
		return core_domain_path;
	}

	public void setCore_domain_path(Path core_domain_path) {
		this.core_domain_path = core_domain_path;
	}

	public String getDomain_core_package_name() {
		return domain_core_package_name;
	}

	public void setDomain_core_package_name(String domain_core_package_name) {
		this.domain_core_package_name = domain_core_package_name;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Generates Controllers for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{


		System.out.println("In GpJavaServerDomainGenWorker - generate_code");
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
		//this.generate_core_domain(gen_manager);

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

	public void set_spring_generation_service(GpServerJavaSpringGenService gen_service){
		this.spring_gen_service = gen_service;
	}
	public void generate_core_domain(IGpGenManager gen_manager) throws Exception{

		ArrayList<GpNoun> the_nouns =  super.the_project.getProject_nouns();
		this.do_generation(the_nouns, gen_manager);

	}
	/**
	 * 	 Allows the caller to generate Java code for selected nouns
	 * @param the_nouns - the nouns that need to be code gened
	 * @throws Exception
	 */
	public void generate_portions_of_core_domain(ArrayList<GpNoun> the_nouns, IGpGenManager gen_manager) throws Exception{
		this.do_generation(the_nouns, gen_manager);

	}

	private void handle_imports(ArrayList<String> the_imports ){

	}

	private void do_generation(ArrayList<GpNoun> the_nouns, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_domain_java_files-GpJavaServerDomainGenWorker", "gen_processing");
		if(the_nouns != null){
			for(GpNoun a_noun : the_nouns){
				ArrayList<String> the_imports = new ArrayList<String>();
				ArrayList<GpNounAttribute> attrib_list =  a_noun.getNounattributes();
				ArrayList<GpNounAttributeForJpa> attrib_to_gen = new ArrayList<GpNounAttributeForJpa>();
				//implicit id
				GpNounAttributeForJpa id_attrib = new GpNounAttributeForJpa();
				id_attrib.setAnnotations("@Id \n @GeneratedValue");

				id_attrib.setName("Id");
				id_attrib.setSubtype("long");
				attrib_to_gen.add(id_attrib);
				//
				for(GpNounAttribute the_attrib: attrib_list){
					GpNounAttributeForJpa an_attrib = new GpNounAttributeForJpa();
					an_attrib.setAnnotations("@Column");

					an_attrib.setName(the_attrib.getName());
					String sub_type = the_attrib.getSubtype();

					if(sub_type.equals("whole number")){
						an_attrib.setSubtype("long");
					}else if(sub_type.equals("Text")){
						an_attrib.setSubtype("String");
					}else if(sub_type.equals("currency")){
						an_attrib.setSubtype("float");
					}else if(sub_type.equals("true/false")){
						an_attrib.setSubtype("boolean");
					}else if(sub_type.equals("Date")){
						an_attrib.setSubtype("String");
					}
					attrib_to_gen.add(an_attrib);
				}

				 String copy_right_range =  this.base_configs.get(
													"copy_right_range").getValue();

				 ST st = super.read_template_group(this.template_group_path, "output");
				 st.add("package_name", this.domain_core_package_name);
				 st.add("thenoun", a_noun.getName());
				 st.add("the_imports", the_imports);
				 st.add("thenames", attrib_to_gen);
				 st.add("copy_right_range", copy_right_range);

				 String file_extension =  this.base_configs
						 					.get("java_extension").getValue();



				 String the_path_string = core_domain_path.toString()
						 + this.file_separator + a_noun.getName() + file_extension
						 									+  this.file_separator;
				 //System.out.println(the_path_string);
				 Path test_write_path = Paths.get(the_path_string);
				 super.write_file(test_write_path, st);

			}
		}
	}

	private void set_up_paths_and_templates() throws Exception {

		/*
		 * first see if the location where the code will be generated to
		 * has already been derived - if not create a new config for it
		 */
		String config_name = "";
		String[] tokens;
		Path server_root_path = (Path)this.derived_configs.get(
					GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();


		String gen_base_domain_directory_java_server =  this.base_configs.get(
									"gen_base_domain_directory_java_server").getValue();

		tokens = this.tokenize_string(
								gen_base_domain_directory_java_server, null);

		config_name = this.build_name_from_tokens(tokens);


		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations domain_core_path_config = this.derived_configs.get(config_name);
		if(domain_core_path_config == null){

			//create the directories and store the path
			Path domain_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			Files.createDirectory(domain_path);


			Path domain_core_config_path = Paths.get(domain_path.toString() + this.file_separator
					+ tokens[1]);
			Files.createDirectory(domain_core_config_path);

			this.domain_core_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0] + "." + tokens[1];

			domain_core_path_config = new GpArchitypeConfigurations();

			domain_core_path_config.setName(config_name);
			domain_core_path_config.setObject_value(domain_core_config_path);

			this.derived_configs.put(config_name, domain_core_path_config);


			GpArchitypeConfigurations domain_core_package_name_config = new GpArchitypeConfigurations();

			domain_core_package_name_config.setName("domain_core_package_name");
			domain_core_package_name_config.setValue(this.domain_core_package_name);
			this.derived_configs.put(domain_core_package_name_config.getName(), domain_core_package_name_config);


		}

		/*
		 *
		 * second determine if the location of the template that will be used
		 * for this code generation have been derived previously if not
		 * derive it
		 *
		 */

		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();

		String server_java_domain_core_jpa_template_location = this.base_configs
					.get("server_java_domain_core_jpa_template_location").getValue();


		tokens = this.tokenize_string(
				server_java_domain_core_jpa_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;


		GpArchitypeConfigurations server_java_domain_core_template_path_config
																= this.derived_configs.get(config_name);
		if(server_java_domain_core_template_path_config == null){
			//create and store the path to the domain templates

			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}

			String template_name =  this.base_configs.get(
					"server_java_domain_core_jpa_template_name").getValue();

			Path server_java_domain_core_template_path =
					Paths.get(core_template_location_temp
										+ this.file_separator + template_name);

			server_java_domain_core_template_path_config = new GpArchitypeConfigurations();

			server_java_domain_core_template_path_config.setName(config_name);
			server_java_domain_core_template_path_config.setObject_value(server_java_domain_core_template_path);
			this.derived_configs.put(config_name, server_java_domain_core_template_path_config);

		}

		this.template_group_path = (Path)server_java_domain_core_template_path_config .getObject_value();
		this.core_domain_path = (Path)domain_core_path_config.getObject_value();

	}

}
