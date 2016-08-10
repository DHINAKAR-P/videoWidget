package com.npb.gp.gen.workers.java.springboot;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;
import com.npb.gp.gen.workers.GpGenBaseWorker;
import com.npb.gp.gen.workers.server.java.springboot.support.domain.GpSpringBootNounAttribute;


/**
* @author Kumaresan Perumal
* Date Created: 02/18/2016</br>
* @since 1.0</p>
*
*   <p>The class generates a spring boot JPA based domain (bean)</p>
*
*/

@Component("GpJavaServerSpringBootDomainGenWorker")
public class GpJavaServerSpringBootDomainGenWorker extends GpGenBaseWorker {

	private Path template_group_path;
	private Path core_domain_path;
	private String app_base_package_name;
	private String domain_core_package_name;
	private GpServerJavaSpringGenService spring_gen_service;
	private GpServerJavaSpringBootGenService spring_boot_gen_service;

	public void set_spring_boot_generation_service(GpServerJavaSpringBootGenService gen_service){
		this.spring_boot_gen_service = gen_service;
	}


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
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, GpNoun> map_nouns = new HashMap<>();
		for(GpNoun noun : nouns){
			map_nouns.put(noun.getId(), noun);
		}
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long child_noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						//parent
						JSONArray parent_nouns = relationships_map.get(noun.getId());
						if(parent_nouns == null)
							parent_nouns = new JSONArray();
						JSONObject json_parent = new JSONObject();
						json_parent.put("Noun_name", map_nouns.get(child_noun_id).getName());
						json_parent.put("Relationship_type", "parent");
						json_parent.put("type", "ONE_TO_ONE");
						parent_nouns.put(json_parent);
						relationships_map.put(noun.getId(), parent_nouns);
						//child
						JSONArray child_noun_json_array = relationships_map.get(child_noun_id);
						if(child_noun_json_array == null)
							child_noun_json_array = new JSONArray();
						JSONObject json_child = new JSONObject();
						json_child.put("Noun_name", map_nouns.get(noun.getId()).getName());
						json_child.put("Relationship_type", "child");
						json_child.put("type", "ONE_TO_ONE");
						child_noun_json_array.put(json_child);
						relationships_map.put(child_noun_id, child_noun_json_array);
					}
					continue;
				}
				if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(type.equals("Noun")){
							long child_noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
							//parent
							JSONArray parent_nouns = relationships_map.get(noun.getId());
							if(parent_nouns == null)
								parent_nouns = new JSONArray();
							JSONObject json_parent = new JSONObject();
							json_parent.put("Noun_name", map_nouns.get(child_noun_id).getName());
							json_parent.put("Relationship_type", "parent");
							json_parent.put("type", "ONE_TO_MANY");
							parent_nouns.put(json_parent);
							relationships_map.put(noun.getId(), parent_nouns);
							//child
							JSONArray child_noun_json_array = relationships_map.get(child_noun_id);
							if(child_noun_json_array == null)
								child_noun_json_array = new JSONArray();
							JSONObject json_child = new JSONObject();
							json_child.put("Noun_name", map_nouns.get(noun.getId()).getName());
							json_child.put("Relationship_type", "child");
							json_child.put("type", "ONE_TO_MANY");
							child_noun_json_array.put(json_child);
							relationships_map.put(child_noun_id, child_noun_json_array);
							continue;
						}
					}
					
					
				}
			}
		}
		return relationships_map;
	}
	
	List<GpSpringBootNounAttribute> get_implicit_columns(){
		List<GpSpringBootNounAttribute> implicit_columns = new ArrayList<>();
		//ID
		GpSpringBootNounAttribute id_attrib = new GpSpringBootNounAttribute();
		id_attrib.setAnnotations("@Id \n "
				+ "\t@GeneratedValue");
		id_attrib.setSwagger_field_value("The standard id attribute - System generated");
		id_attrib.setName("Id");
		id_attrib.setSubtype("long");
		implicit_columns.add(id_attrib);
		
		//Created_date
		
		GpSpringBootNounAttribute created_date_attrib = new GpSpringBootNounAttribute();
		created_date_attrib.setAnnotations("@Column");
		created_date_attrib.setSwagger_field_value("created date");
		created_date_attrib.setName("Created_date");
		created_date_attrib.setSubtype("String");
		implicit_columns.add(created_date_attrib);
		
		//Created_by
		GpSpringBootNounAttribute created_by_attrib = new GpSpringBootNounAttribute();
		created_by_attrib.setAnnotations("@Column");
		created_by_attrib.setSwagger_field_value("created by value");
		created_by_attrib.setName("Created_by");
		created_by_attrib.setSubtype("long");
		implicit_columns.add(created_by_attrib);
		
		//Updated_date
		GpSpringBootNounAttribute updated_date_attrib = new GpSpringBootNounAttribute();
		updated_date_attrib.setAnnotations("@Column");
		updated_date_attrib.setSwagger_field_value("updated date");
		updated_date_attrib.setName("Updated_date");
		updated_date_attrib.setSubtype("String");
		implicit_columns.add(updated_date_attrib);
		
		//Created_by
		GpSpringBootNounAttribute updated_by_attrib = new GpSpringBootNounAttribute();
		updated_by_attrib.setAnnotations("@Column");
		updated_by_attrib.setSwagger_field_value("updated by value");
		updated_by_attrib.setName("Updated_by");
		updated_by_attrib.setSubtype("long");
		implicit_columns.add(updated_by_attrib);
		
		return implicit_columns;
	}

	private void do_generation(ArrayList<GpNoun> the_nouns, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_domain_java_files-GpJavaServerDomainGenWorker", "gen_processing");
		if(the_nouns != null){
			Map<Long, JSONArray> map_relationship = this.handle_relationships(the_nouns);
			for(GpNoun a_noun : the_nouns){
				ArrayList<String> the_imports = new ArrayList<String>();
				ArrayList<GpNounAttribute> attrib_list =  a_noun.getNounattributes();
				ArrayList<GpSpringBootNounAttribute> attrib_to_gen = new ArrayList<GpSpringBootNounAttribute>();
				//implicit values
				attrib_to_gen.addAll(get_implicit_columns());
				//
				for(GpNounAttribute the_attrib: attrib_list){
					GpSpringBootNounAttribute an_attrib = new GpSpringBootNounAttribute();
					//swagger field annotations
					an_attrib.setSwagger_field_value(the_attrib.getDescription());
					String attr_name = the_attrib.getName().toLowerCase();
					attr_name = attr_name.substring(0, 1).toUpperCase() + attr_name.substring(1);
					an_attrib.setName(attr_name);
					String sub_type = the_attrib.getSubtype();

					if(sub_type.equals(GpNounConstants.SUB_TYPE_NUMBER)){
						an_attrib.setSubtype("long");
						an_attrib.setAnnotations("@Column");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_TEXT)){
						an_attrib.setSubtype("String");
						an_attrib.setAnnotations("@Column");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_CURRENCY) || sub_type.equals(GpNounConstants.SUB_TYPE_DECIMAL)){
						an_attrib.setSubtype("float");
						an_attrib.setAnnotations("@Column");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_BOOL)){
						an_attrib.setSubtype("boolean");
						an_attrib.setAnnotations("@Column");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_DATE)){
						an_attrib.setSubtype("String");
						an_attrib.setAnnotations("@Column");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_PICTURE) || sub_type.equals(GpNounConstants.SUB_TYPE_SOUND)){
						//an_attrib.setSubtype("String");
						//an_attrib.setAnnotations("@Column");
						an_attrib.setSubtype("byte[]");
						an_attrib.setAnnotations("@Column \n\t @Lob");
						the_imports.add("javax.persistence.Lob");
					}else if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
						continue;
					}
					else if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
						JSONObject relationship_json = new JSONObject(the_attrib.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(!type.equals("Noun")){
							if(!the_imports.contains("java.util.List"))
								the_imports.add("java.util.List");
							if(!the_imports.contains("javax.persistence.Transient"))
								the_imports.add("javax.persistence.Transient");
							if(type.equals(GpNounConstants.SUB_TYPE_TEXT) || type.equals(GpNounConstants.SUB_TYPE_DATE)
									|| type.equals(GpNounConstants.SUB_TYPE_PICTURE) || type.equals(GpNounConstants.SUB_TYPE_SOUND)){
								an_attrib.setSubtype("List<String>");
								an_attrib.setAnnotations("@Transient");
							}else if(type.equals(GpNounConstants.SUB_TYPE_NUMBER)){
								an_attrib.setSubtype("List<Long>");
								an_attrib.setAnnotations("@Transient");
							}else if(sub_type.equals(GpNounConstants.SUB_TYPE_CURRENCY) || sub_type.equals(GpNounConstants.SUB_TYPE_DECIMAL)){
								an_attrib.setSubtype("List<Float>");
								an_attrib.setAnnotations("@Transient");
							}else if(sub_type.equals(GpNounConstants.SUB_TYPE_BOOL)){
								an_attrib.setSubtype("List<Boolean>");
								an_attrib.setAnnotations("@Transient");
							}
						}else{
							continue;
						}
					}
					attrib_to_gen.add(an_attrib);
				}
				//parent noun
				JSONArray parent_noun_json_array = map_relationship.get(a_noun.getId());
				if(parent_noun_json_array != null){
					for(int i = 0; i < parent_noun_json_array.length(); i++){
						JSONObject json_parent = parent_noun_json_array.getJSONObject(i);
						String type = json_parent.getString("type");
						String rel_type = json_parent.getString("Relationship_type");
						String name = json_parent.getString("Noun_name");
						if(rel_type.equals("parent")){
							GpSpringBootNounAttribute an_attrib = new GpSpringBootNounAttribute();
							an_attrib.setSwagger_field_value("Parent noun");
							an_attrib.setName(name);
							if(type.equals("ONE_TO_MANY")){
								an_attrib.setSubtype("List<"+ name + ">");
								if(!the_imports.contains("java.util.List"))
									the_imports.add("java.util.List");
							}
							else{
								an_attrib.setSubtype(name);
							}
							an_attrib.setAnnotations("@Transient");
							attrib_to_gen.add(an_attrib);
							if(!the_imports.contains("javax.persistence.Transient"))
								the_imports.add("javax.persistence.Transient");
						}else{
							GpSpringBootNounAttribute an_attrib = new GpSpringBootNounAttribute();
							an_attrib.setSwagger_field_value("parent id");
							an_attrib.setName(name + "_id");
							an_attrib.setSubtype("long");
							an_attrib.setAnnotations("@Column");
							attrib_to_gen.add(an_attrib);
						}
					}
				}

				 String copy_right_range =  this.base_configs.get(
													"copy_right_range").getValue();

				 ST st = super.read_template_group(this.template_group_path, "output");
				 st.add("package_name", this.domain_core_package_name);
				 st.add("thenoun", a_noun.getName());
				 st.add("the_imports", the_imports);
				 st.add("thenames", attrib_to_gen);
				 st.add("copy_right_range", copy_right_range);

				 // here i give a swagger model annotation for  model
				 st.add("swagger_model_value", a_noun.getName());
				 st.add("swagger_modle_description", a_noun.getDescription());

				 String file_extension =  this.base_configs
						 					.get("java_extension").getValue();

				 String the_path_string = core_domain_path.toString()
						 + this.file_separator + a_noun.getName() + file_extension
						 									+  this.file_separator;
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

		String server_java_domain_core_spring_boot_template_location = this.base_configs
					.get("server_java_domain_core_spring_boot_template_location").getValue();


		tokens = this.tokenize_string(
				server_java_domain_core_spring_boot_template_location, null);

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
					"server_java_domain_core_spring_boot_template_name").getValue();

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
