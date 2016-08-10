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

import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpTypeAndReference;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.springboot.support.controller.GpControllerGenSpringBootSupport;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p> 
 * 
 * Generates the code for a Java Spring boot Jpa based controller </p> 
 */

@Component("GpSpringBootControllerGenWorker")
public class GpSpringBootControllerGenWorker extends GpGenJavaServerSpringBaseWorker {
	
	private Path template_group_path;
	
	private Path controller_path;
	
	private String controller_package_name;

	private GpControllerGenSpringBootSupport controller_spring_boot_support;
	
	private GpFlowControl the_flow;
	private GpNounDao noun_dao;
	private Map<Long, JSONArray> relationships_map;
		
	public GpControllerGenSpringBootSupport getController_spring_boot_support() {
		return controller_spring_boot_support;
	}
	
	@Resource(name="GpControllerGenSpringBootSupport")
	public void setController_spring_boot_support(
			GpControllerGenSpringBootSupport controller_spring_boot_support) {
		this.controller_spring_boot_support = controller_spring_boot_support;
	}
	
	
	public String getController_package_name() {
		return controller_package_name;
	}
	
	public void setController_package_name(String controller_package_name) {
		this.controller_package_name = controller_package_name;
	}
	
	public Path getController_path() {
		return controller_path;
	}
	
	public void setController_path(Path controller_path) {
		this.controller_path = controller_path;
	}
	
	
	@Override
	/**
	 * Generates Controllers for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{
		
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.the_flow = the_flow;
		this.set_up_paths_and_templates();
		//this.generate_core_domain();

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
		@SuppressWarnings("unchecked")
		ArrayList<GpVerb> the_verbs = (ArrayList<GpVerb>)this.derived_configs.get(
										GpGenConstants.VERB_DEFINITIONS).getObject_value();
		
		@SuppressWarnings("unchecked")
		ArrayList<GpAuthorization> the_auths = (ArrayList<GpAuthorization>)this.derived_configs.get(
				GpGenConstants.AUTHORIZATION_DEFINITIONS).getObject_value();
	
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
		this.controller_spring_boot_support.setThe_worker(this);
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

	
	private void do_generation(GpActivity activity) throws Exception{
		
		 String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();
		 		
		 ST st = super.read_template_group(this.template_group_path, "output");
		
    	if(activity.getDescription() == null || activity.getDescription().equals("")){
    		st.add("controller_notes", "The description has been left blank");
    	}else{
    	st.add("controller_notes", activity.getDescription() +" of " +  activity.getName());
    	}
		
    	st.add("package_name", this.controller_package_name);
		
		ArrayList<String> import_list = this.set_up_imports(activity);
		st.add("the_imports", import_list);
		String class_name = this.capitalize(activity.getName()) + "Controller";
		st.add("class_name", class_name);
		st.add("end_point_mapping", activity.getName());
		
		st.add("the_services", this.set_up_service_references(activity));
		
		st.add("copy_right_range", copy_right_range);
		
		st.add("the_gen_verbs", this.set_up_verbs(activity));
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
				
		String the_path_string = controller_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
				
	}
	
	private ArrayList<GpControllerSpringBootVerbGenInfo> set_up_verbs(GpActivity activity) throws Exception{
		
		ArrayList<GpControllerSpringBootVerbGenInfo> the_verbs_method_info =
										this.controller_spring_boot_support
										.prep_controller_verb_generation(
										activity, get_spring_boot_gen_service(),
																this.the_flow);
		
		return the_verbs_method_info;
	}
	
	private ArrayList<GpTypeAndReference> set_up_service_references(GpActivity activity) throws Exception{
		
		ArrayList<GpTypeAndReference> service_list = new ArrayList<GpTypeAndReference>();
        
		String service_data_type = super.get_spring_boot_gen_service()
				.getSpring_boot_service_worker().get_data_type_for_activity(activity);

		String service_object_reference_name  = activity.getName() + "_" + "service";

		GpTypeAndReference dto = new GpTypeAndReference();
		dto.type = service_data_type;
		dto.reference = service_object_reference_name;
		
		service_list.add(dto);
		
		return service_list;
	}
	
	private ArrayList<String> set_up_imports(GpActivity activity) throws Exception{
		
		ArrayList<String> import_list = new ArrayList<String>();

		 String domain_core_package_name = this.derived_configs
				 			.get("domain_core_package_name").getValue();
		 
		 for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
				if(noun.getSubtype().equals("Picture")||noun.getSubtype().equals("Video")){
					import_list.add("org.springframework.web.multipart.MultipartFile");
					System.out.println("Multipart import Added"); 
				}
			}

		//set up primary noun import
		 if(activity.getPrimary_noun() != null){
			 domain_core_package_name = domain_core_package_name + 
				 		"." + activity.getPrimary_noun().getName();
		 
		 import_list.add(domain_core_package_name);
		 }
		
		//set up service import

		 String service_package_name = super.get_spring_boot_gen_service()
				 .getSpring_boot_service_worker().get_package_name_for_activity(activity);
		 		 
		 import_list.add(service_package_name); 		
		return import_list;
		
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
									"gen_base_java_server_controller_directory").getValue();
		
		tokens = this.tokenize_string(
								gen_base_domain_directory_java_server, null);
		
		config_name = this.build_name_from_tokens(tokens);
				
		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		
		GpArchitypeConfigurations controller_path_config = this.derived_configs.get(config_name);
		
		if(controller_path_config == null){
			
			//create the directories and store the path
			Path controller_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] );
			
			Files.createDirectory(controller_path);
			
			this.controller_path = controller_path;

			this.controller_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue() + "." + tokens[0];

			controller_path_config = new GpArchitypeConfigurations();
			
			controller_path_config.setName(config_name);
			controller_path_config.setObject_value(controller_path);
			this.derived_configs.put(config_name, controller_path_config);
				
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
		
		String server_java_spring_boot_controller_template_location = this.base_configs
					.get("server_java_spring_boot_controller_template_location").getValue();
		
		tokens = this.tokenize_string(
				server_java_spring_boot_controller_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_controller_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_boot_controller_template_path_config 
														= this.derived_configs.get(config_name);
		

		if(server_java_spring_boot_controller_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_boot_controller_template_name").getValue();
			
			Path server_java_spring_boot_controller_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_boot_controller_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_boot_controller_template_path_config.setName(config_name);
			server_java_spring_boot_controller_template_path_config.setObject_value(server_java_spring_boot_controller_template_path);
			this.derived_configs.put(config_name, server_java_spring_boot_controller_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_boot_controller_template_path_config.getObject_value(); 
		this.controller_path = (Path)controller_path_config.getObject_value();

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
