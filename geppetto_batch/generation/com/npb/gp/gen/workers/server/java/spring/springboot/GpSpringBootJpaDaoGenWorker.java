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
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.workers.server.java.spring.support.springbootdao.GpSpringBootDaoGenSupport;
import com.npb.gp.gen.workers.server.rdms.mysql.GpMySqlDMLWorker;

/**
 * 
 * @author Kumaresan Perumal
 * Date Created: 02/18/2016</br>
 * @since 1.0</p>
 *  
 * Generates the code for DAO layer based Java Spring boot JPA
 * </p> 
 * 
 */

@Component("GpSpringBootJpaDaoGenWorker")
public class GpSpringBootJpaDaoGenWorker extends GpGenJavaServerSpringBaseWorker {
	
	private Path template_group_path;
	private Path dao_path;
	private String dao_package_name;
	private GpSpringBootResourceBundleGenWorker bundle_worker;
	private ArrayList<GpBaseSqlDto> sql_stmts;
	private String create_bundle_sql = "";
	private String update_bundle_sql = "";
	private GpNounDao noun_dao;
	private Map<Long, JSONArray> relationships_map;
	
	public GpSpringBootResourceBundleGenWorker getBundle_worker() {
		return bundle_worker;
	}
	@Resource(name = "GpSpringBootResourceBundleGenWorker")
	public void setBundle_worker(GpSpringBootResourceBundleGenWorker bundle_worker) {
		this.bundle_worker = bundle_worker;
	}

	private boolean read_only_verbs = false;  //need to see if Activity only has read only verbs
	
	private GpSpringBootDaoGenSupport spring_boot_dao_support;
	
	public GpSpringBootDaoGenSupport getSpring_boot_dao_support() {
		return spring_boot_dao_support;
	}
	@Resource(name = "GpSpringBootDaoGenSupport")
	public void setSpring_boot_dao_support(
			GpSpringBootDaoGenSupport spring_boot_dao_support) {
		this.spring_boot_dao_support = spring_boot_dao_support;
	}

	private GpMySqlDMLWorker spring_boot_dml_worker;
	
	
	public String getDao_package_name() {
		return dao_package_name;
	}

	public void setDao_package_name(String dao_package_name) {
		this.dao_package_name = dao_package_name;
	}
	
	public GpMySqlDMLWorker getSpring_boot_dml_worker() {
		return spring_boot_dml_worker;
	}
	
	@Resource( name = "GpMySqlDMLWorker")
	public void setSpring_boot_dml_worker(
			GpMySqlDMLWorker spring_boot_dml_worker) {
		this.spring_boot_dml_worker = spring_boot_dml_worker;
	}
	public String getCreate_bundle_sql() {
		return create_bundle_sql;
	}

	public void setCreate_bundle_sql(String create_bundle_sql) {
		this.create_bundle_sql = create_bundle_sql;
	}

	public String getUpdate_bundle_sql() {
		return update_bundle_sql;
	}

	public void setUpdate_bundle_sql(String update_bundle_sql) {
		this.update_bundle_sql = update_bundle_sql;
	}
	
	public ArrayList<GpBaseSqlDto> getSql_stmts() {
		return sql_stmts;
	}

	public void setSql_stmts(ArrayList<GpBaseSqlDto> sql_stmts) {
		this.sql_stmts = sql_stmts;
	}
	
	public Map<Long, JSONArray> getRelationships_map() {
		return relationships_map;
	}

	/**
	 * Generates DAOS for all activities in the project
	 */
	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager) 
			 													throws Exception{
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

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
	}
	
	private void do_generation(GpActivity activity) throws Exception{
		
		this.checK_for_update_verbs(activity);
		String copy_right_range =  this.base_configs.get(
					"copy_right_range").getValue();
		
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("package_name", this.dao_package_name);
		this.setDao_package_name(this.dao_package_name);		
		ArrayList<String> import_list = this.set_up_imports(activity);
		st.add("the_imports", import_list);
		String class_name = this.capitalize(activity.getName()) + "Dao";
		st.add("class_name", class_name);
		
		this.spring_boot_dml_worker = super.get_spring_boot_gen_service().getThe_dml_worker();
		sql_stmts = this.spring_boot_dml_worker
								.get_dml_statements_for_activity(activity);
		this.bundle_worker.set_up_resource_bundle_references(activity, sql_stmts);
		
		this.bundle_worker = super.get_spring_boot_gen_service()
				.getSpring_boot_resource_bundle_worker();
				
		this.bundle_worker.generate_sql_bundle(activity, sql_stmts);
		st.add("resource_bundle_info",	sql_stmts);
		
		if(!this.read_only_verbs){
			for(GpBaseSqlDto sql_dto : sql_stmts){
				if(sql_dto.verb_action_on_data.equals("GpCreate")){
					create_bundle_sql = sql_dto.local_string_reference;
				}else if(sql_dto.verb_action_on_data.equals("GpUpdate")){
					update_bundle_sql = sql_dto.local_string_reference;
				}
			}
		}

		st.add("the_read_verbs", this.set_up_read_only_verbs(activity));
		
		st.add("the_update_verbs", this.set_up_update_verbs(activity));
		
		st.add("copy_right_range", copy_right_range);
		
		String file_extension =  this.base_configs
					.get("java_extension").getValue();
		String the_path_string = dao_path.toString() 
		+ this.file_separator + class_name + file_extension 
									+  this.file_separator;
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
	}
	
	private ArrayList<GpSpringBootDaoVerbGenInfo> set_up_read_only_verbs(GpActivity an_activity) throws Exception{
				
		ArrayList<GpSpringBootDaoVerbGenInfo> read_only_verbs 
									= new ArrayList<GpSpringBootDaoVerbGenInfo>();
		
		ArrayList<GpSpringBootDaoVerbGenInfo> all_verbs 
					= spring_boot_dao_support.get_verb_method_implmentation(an_activity);
		
		for(GpSpringBootDaoVerbGenInfo dto : all_verbs){
			if(this.is_read_only_verb(dto.verb_action_on_data)){
				read_only_verbs.add(dto);
			
			}
		}

		
		return read_only_verbs;
	}
	
	private ArrayList<GpSpringBootDaoVerbGenInfo> set_up_update_verbs(GpActivity an_activity) throws Exception{
		
		ArrayList<GpSpringBootDaoVerbGenInfo> update_only_verbs 
									= new ArrayList<GpSpringBootDaoVerbGenInfo>();
		
		ArrayList<GpSpringBootDaoVerbGenInfo> all_verbs 
					= spring_boot_dao_support.get_verb_method_implmentation(an_activity);
		for(GpSpringBootDaoVerbGenInfo dto : all_verbs){
			if(!this.is_read_only_verb(dto.verb_action_on_data)){
				update_only_verbs.add(dto);
			}
		}
		
		return update_only_verbs;
	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					//System.out.println("handle_relationships");
					//System.out.println(noun.getName());
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						JSONArray parent_nouns = relationships_map.get(noun_id);
						if(parent_nouns == null){
							//System.out.println("null json array parents");
							parent_nouns = new JSONArray();
						}
						JSONObject json_parent = new JSONObject();
						json_parent.put("type", "ONE_TO_ONE");
						json_parent.put("Noun_name", noun.getName());
						parent_nouns.put(json_parent);
						//System.out.println(parent_nouns.length());
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
							if(parent_nouns == null){
								//System.out.println("null json array parents");
								parent_nouns = new JSONArray();
							}
							JSONObject json_parent = new JSONObject();
							json_parent.put("type", "ONE_TO_MANY");
							json_parent.put("Noun_name", noun.getName());
							parent_nouns.put(json_parent);
							//System.out.println(parent_nouns.length());
							relationships_map.put(noun_id, parent_nouns);
						}
					}
					continue;
				}
			}
		}
		return relationships_map;
	}
	
	private void checK_for_update_verbs(GpActivity activity){
		
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		for(GpVerb a_verb : the_verbs){
			if(a_verb.getAction_on_data().equals("GpUpdate")){
				this.read_only_verbs = false;
				break;
			}else if(a_verb.getAction_on_data().equals("GpDelete")){
				this.read_only_verbs = false;
				break;
			}else if(a_verb.getAction_on_data().equals("GpCreate")){
				this.read_only_verbs = false;
				break;
			}
		}
		
		
	}
	private boolean is_read_only_verb(String action_on_data){
		
		if(action_on_data.equals(GpBaseVerbsConstants.GpUpdate)){
			return false;
		}else if(action_on_data.equals(GpBaseVerbsConstants.GpDelete)){
			return false;
		}else if(action_on_data.equals(GpBaseVerbsConstants.GpCreate)){
			return false;
		}else if(action_on_data.equals(GpBaseVerbsConstants.GpDeleteByParentId)){
			return false;
		}else{
			return true;
		}
		
	}
	
	@Override
	 public String get_data_type_for_activity(GpActivity an_activity)
				throws Exception{

		String dao_data_type = super.capitalize(an_activity.getName()) 	+ "Dao";
		return dao_data_type;
	 }	
	
	@Override
	public String get_package_name_for_activity(GpActivity an_activity) 
			 												throws Exception{
		 
		 String class_name = this.get_data_type_for_activity(an_activity);
		 String package_name = this.dao_package_name + "." + class_name;
		 return package_name;

	 }

	
	@Override
	public HashMap<String,  GpJavaMethodDescription> get_method_signitures(GpActivity an_activity)	throws Exception{
		return null;
	}
	
	public HashMap<String,  GpSpringBootDaoVerbGenInfo> _get_method_signitures(GpActivity an_activity)	throws Exception{
		return this.spring_boot_dao_support.get_method_signitures(an_activity);
	}
	
	
	private ArrayList<String> set_up_imports(GpActivity activity) throws Exception{
		
		ArrayList<String> import_list = new ArrayList<String>();
		
		 String domain_core_package_name = this.derived_configs
				 			.get("domain_core_package_name").getValue();
		//set up primary noun import
		 if(activity.getPrimary_noun() != null){
			 domain_core_package_name = domain_core_package_name + 
					 		"." + activity.getPrimary_noun().getName();
		 }
		 import_list.add(domain_core_package_name); 
		
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
		
		String gen_base_java_server_dao_directory =  this.base_configs.get(
									"gen_base_java_server_dao_directory").getValue();
		
		tokens = this.tokenize_string(
				gen_base_java_server_dao_directory, null);
		
		config_name = this.build_name_from_tokens(tokens);
		
		config_name = config_name + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		GpArchitypeConfigurations dao_path_config = this.derived_configs.get(config_name);
		if(dao_path_config == null){
			
			//create the directories and store the path
			@SuppressWarnings("unchecked")
			ArrayList<GpTechProperties> tech_property_list = 
					(ArrayList<GpTechProperties>) ((GpArchitypeConfigurations)
					this.base_configs
					.get(GpGenConstants.TECH_PROPERTY_LIST)).getObject_value();
			String db_type = "";
			for(GpTechProperties prop : tech_property_list){
				if(this.the_project.getServer_dbms() == prop.getId() ){
					db_type =  prop.getName().toLowerCase();	
				}
				
			}
			Path dao_path = Paths.get(server_root_path.toString() + this.file_separator
					+ tokens[0] +  this.file_separator + db_type);
			Files.createDirectories(dao_path);
			this.dao_path = dao_path;
			
			GpTechProperties tech_prop = super
					.get_tech_property(this.the_project.getServer_dbms());
			
			this.dao_package_name =  this.derived_configs.get(
		 			GpGenConstants.APP_BASE_PACKAGE).getValue()+ "." + tokens[0] +"." + tech_prop.getName().toLowerCase() ;
			dao_path_config = new GpArchitypeConfigurations();
			dao_path_config.setName(config_name);
			dao_path_config.setObject_value(dao_path);
			this.derived_configs.put(config_name, dao_path_config);
				
		}
		
		
		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		
		String server_java_spring_boot_jpa_dao_template_location = this.base_configs
					.get("server_java_spring_boot_jpa_dao_template_location").getValue();
		
		tokens = this.tokenize_string(
				server_java_spring_boot_jpa_dao_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_spring_boot_jpa_dao_" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;;
		
		GpArchitypeConfigurations server_java_spring_boot_jpa_dao_template_path_config 
														= this.derived_configs.get(config_name);
		
		if(server_java_spring_boot_jpa_dao_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_spring_boot_jpa_dao_template_name").getValue();
		
			Path server_java_spring_service_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_spring_boot_jpa_dao_template_path_config = new GpArchitypeConfigurations();
			
			server_java_spring_boot_jpa_dao_template_path_config.setName(config_name);
			server_java_spring_boot_jpa_dao_template_path_config.setObject_value(server_java_spring_service_template_path);
			this.derived_configs.put(config_name, server_java_spring_boot_jpa_dao_template_path_config);

		}
		
		this.template_group_path =   (Path)server_java_spring_boot_jpa_dao_template_path_config.getObject_value(); 
		this.dao_path = (Path)dao_path_config.getObject_value();

	}
	
	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}
}
