package com.npb.gp.gen.workers.server.java.spring.springboot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.springboot.support.service.GpServiceSpringBootGenSupport;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>  
 * 
 * Generates the code for a Java Spring boot based service </p> 
 */

@Component("GpSpringBootServiceGenWorker")
public class GpSpringBootServiceGenWorker extends GpGenJavaServerSpringBaseWorker {
	private Path template_group_path;
	private Path service_path;
	private String service_package_name;
	private GpNounDao noun_dao;
	private Map<Long, JSONArray> relationships_map;
	private GpServiceSpringBootGenSupport service_spring_boot_gen_support;
	private GpFlowControl the_flow;

	public HashMap<String, ArrayList<GpTypeAndReference>> activity_references
						 = new HashMap<String, ArrayList<GpTypeAndReference>>(); 
	
	public String getService_package_name() {
		return service_package_name;
	}
	
	public GpServiceSpringBootGenSupport getService_spring_boot_gen_support() {
		return service_spring_boot_gen_support;
	}
	@Resource(name = "GpServiceSpringBootGenSupport")
	public void setService_spring_boot_gen_support(
			GpServiceSpringBootGenSupport service_spring_boot_gen_support) {
		this.service_spring_boot_gen_support = service_spring_boot_gen_support;
	}
	public void setService_package_name(String service_package_name) {
		this.service_package_name = service_package_name;
	}
	public Path getService_path() {
		return service_path;
	}
	public void setService_path(Path service_path) {
		this.service_path = service_path;
	}
	
	
	@Override
	/**
	 * Generates Controllers for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{
		
	}

	
	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.the_flow = the_flow;
		
		this.set_up_paths_and_templates();
		this.do_generation(activity);
		
	}
	

	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		
		this.set_up_paths_and_templates();
		List<GpNoun> nouns = noun_dao.find_by_project_id(the_project.getId());
		relationships_map = this.handle_relationships(nouns);
		this.service_spring_boot_gen_support.setThe_worker(this);
		
	}
	
	private void do_generation(GpActivity activity) throws Exception{
		 
		
		String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();

		ST st = super.read_template_group(this.template_group_path, "output");
		
		st.add("package_name", this.service_package_name);
		
		ArrayList<String> import_list = this.set_up_imports(activity);
		
		st.add("the_imports", import_list);
		
		st.add("copy_right_range", copy_right_range);
		
		String class_name = this.get_data_type_for_activity(activity);
		st.add("class_name", class_name);
		st.add("base_class", "extends GpBaseService");
		
		st.add("the_daos", this.set_up_dao_references(activity));
		
		
		st.add("the_gen_verbs", this.set_up_verbs(activity));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
		
		String the_path_string = service_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);

	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						JSONArray parent_nouns = relationships_map.get(noun_id);
						if(parent_nouns == null)
							parent_nouns = new JSONArray();
						JSONObject json_parent = new JSONObject();
						json_parent.put("type", "ONE_TO_ONE");
						json_parent.put("Noun_name", noun.getName());
						json_parent.put("Noun_id", noun.getId());
						parent_nouns.put(json_parent);
						relationships_map.put(noun_id, parent_nouns);
					}
					continue;
				}
				if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(type.equals("Noun")){
							long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
							JSONArray parent_nouns = relationships_map.get(noun_id);
							if(parent_nouns == null)
								parent_nouns = new JSONArray();
							JSONObject json_parent = new JSONObject();
							json_parent.put("type", "ONE_TO_MANY");
							json_parent.put("Noun_name", noun.getName());
							json_parent.put("Noun_id", noun.getId());
							parent_nouns.put(json_parent);
							relationships_map.put(noun_id, parent_nouns);
						}
					}
					continue;
				}
			}
		}
		return relationships_map;
	}
	
	private ArrayList<GpServiceVerbGenInfo> set_up_verbs(GpActivity an_activity) throws Exception{
		
		ArrayList<GpServiceVerbGenInfo> impls = this.service_spring_boot_gen_support.
				get_verb_method_implmentation(an_activity);
		return impls;
		
	}

	
	
	@Override
	public String get_data_type_for_activity(GpActivity an_activity)
															throws Exception{
		String service_data_type = super.capitalize(an_activity.getName()) 
																+ "Service";
		
		return service_data_type;

	}
	
	@Override
	 public String get_package_name_for_activity(GpActivity an_activity) 
			 												throws Exception{
		 
		 String class_name = this.get_data_type_for_activity(an_activity);
		 String package_name = this.service_package_name + "." + class_name;
		 
		 return package_name;
		 
		 
	 }
	
	public HashMap<String,  GpServiceVerbGenInfo> _get_method_signitures(GpActivity an_activity) 
			 																	throws Exception{
		return this.service_spring_boot_gen_support.get_method_signitures(an_activity);
	}
	
	@Override
	public HashMap<String,  GpJavaMethodDescription> get_method_signitures(GpActivity an_activity) 
			 																	throws Exception{
		return null;
		//return this.service_spring_boot_gen_support.get_method_signitures(an_activity);
	}
	
	
	private void get_method_implementations(GpActivity activity) throws Exception{
		
	}
	
	
	private ArrayList<GpTypeAndReference> set_up_dao_references(GpActivity activity) throws Exception{
		
		ArrayList<GpTypeAndReference> dao_list = new ArrayList<GpTypeAndReference>();

		String dao_data_type  = super.get_spring_boot_gen_service()
				.getSpring_boot_dao_gen_worker().get_data_type_for_activity(activity);

		String dao_object_reference_name  = activity.getName() + "_" + "dao";

		GpTypeAndReference dto = new GpTypeAndReference();
		dto.type = dao_data_type;
		dto.reference = dao_object_reference_name;
		
		dao_list.add(dto);
		
		//child services
		
		GpRelationshipInfo rel_info = this.get_spring_boot_gen_service().getMap_services_relationships().get(activity.getId());
		if(rel_info != null){
			List<GpChildRelationshipInfo> children = rel_info.getChilds();
			for(GpChildRelationshipInfo child : children){
				GpTypeAndReference dto_child = new GpTypeAndReference();
				String type_and_reference = child.getActivity().getName() + "Service";
				dto_child.type = type_and_reference;
				dto_child.reference = type_and_reference;
				dao_list.add(dto_child);
			}
		}
		this.activity_references.put("all_dao_references", dao_list);
		return dao_list;
	}
	
	private ArrayList<String> set_up_imports(GpActivity activity) throws Exception{
		
		ArrayList<String> import_list = new ArrayList<String>();

		 String domain_core_package_name = this.derived_configs
				 			.get("domain_core_package_name").getValue();
		 
		 //set up primary noun import
		 if(activity.getPrimary_noun() != null){
			 String domain_core_package_name_primary_noun = domain_core_package_name + 
				 		"." + activity.getPrimary_noun().getName();
		
			 import_list.add(domain_core_package_name_primary_noun); 
		 }
		 JSONArray json_array_parents = this.relationships_map.get(activity.getPrimary_noun().getId());
		 if(json_array_parents != null){
			 for(int i = 0 ; i < json_array_parents.length(); i++){
				 JSONObject json_parent = json_array_parents.getJSONObject(i);
				 String name = json_parent.getString("Noun_name");
				 String domain_core_package_name_noun_parent = domain_core_package_name + "."
				 		+ name; 
				 import_list.add(domain_core_package_name_noun_parent);
			 }
		 }
		 String dao_package_name = null;
		 
		 dao_package_name = super.get_spring_boot_gen_service()
				 .getSpring_boot_dao_gen_worker().get_package_name_for_activity(activity);
				
		
		 import_list.add(dao_package_name); 
		
		 String gep_user_import = this.derived_configs
	 				.get("domain_core_package_name").getValue() + ".GpUser";
		 		 
		 import_list.add(gep_user_import);
		 
		return import_list;
		
	}

	private void set_up_paths_and_templates() throws Exception {
		
		String config_name = "";
		String[] tokens;
		Path server_root_path = (Path)this.derived_configs.get(
					GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();
		
		String gen_base_service_directory_java_server =  this.base_configs.get(
									"gen_base_java_server_service_directory").getValue();
		
		tokens = this.tokenize_string(
								gen_base_service_directory_java_server, null);
		
		config_name = this.build_name_from_tokens(tokens);
		
		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations service_path_config = this.derived_configs.get(config_name);
		if(service_path_config == null){
			
			//create the directories and store the path
			Path service_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			Files.createDirectory(service_path);
			this.service_path = service_path;
			

			this.service_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			service_path_config = new GpArchitypeConfigurations();
			
			service_path_config.setName(config_name);
			service_path_config.setObject_value(service_path);
			this.derived_configs.put(config_name, service_path_config);
				
			GpArchitypeConfigurations services_package = new GpArchitypeConfigurations();
			services_package.setName(GpGenConstants.APP_SERVICES_PACKAGE);
			services_package.setValue(service_package_name);
			this.derived_configs.put(GpGenConstants.APP_SERVICES_PACKAGE, services_package);
		}
		
		
		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		
		String server_java_spring_boot_service_template_location = this.base_configs
					.get("server_java_spring_boot_service_template_location").getValue();
		
		tokens = this.tokenize_string(
				server_java_spring_boot_service_template_location, null);

		String template_path_config_name = "";
		config_name = this.build_name_from_tokens(tokens) + "_service_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_service_template_path_config 
														= this.derived_configs.get(config_name);

		
		if(server_java_spring_service_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_boot_service_template_name").getValue();
			
			
			Path server_java_spring_service_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_service_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_service_template_path_config.setName(config_name);
			server_java_spring_service_template_path_config.setObject_value(server_java_spring_service_template_path);
			this.derived_configs.put(config_name, server_java_spring_service_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_service_template_path_config.getObject_value(); 
		this.service_path = (Path)service_path_config.getObject_value();

	}

	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}
		
	public Map<Long, JSONArray> getRelationships_map() {
		return relationships_map;
	}
}
