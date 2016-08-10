package com.npb.gp.gen.workers.server.java.spring;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.GpResourceBundleReference;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
/**
 * 
 * @author Dan Castillo
 * Date Created: 01/10/2015</br>
 * @since .2</p> 
 * 
 * worker that generates resource bundles </p> 
 */
@Component("GpSpringResourceBundleGenWorker")
public class GpSpringResourceBundleGenWorker extends
		GpGenJavaServerSpringBaseWorker {

	private Path template_group_path;
	private Path resources_path;
	private Path standard_resource_bundle_template_path;
	private Path sql_queries_bundle_path;

	
	public String get_sql_bundle_name(GpActivity activity){
		
		String base_name = this.capitalize(activity.getName());
		
		String bundle_name = base_name + "_SQL.properties";
		
		return bundle_name;
		
		
	}
	
	public void generate_sql_bundle(GpActivity activity, ArrayList<GpBaseSqlDto> sql_list) throws Exception{
		
		this.do_sql_generation(activity, sql_list);
		//GpBaseSqlDto t = new GpBaseSqlDto();
		
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

	
	private void do_sql_generation(GpActivity activity, ArrayList<GpBaseSqlDto> sql_list) throws Exception{
		
		ST st = super.read_template_group(this
						.standard_resource_bundle_template_path, "output");
		
		st.add("bundle_items", sql_list);
		
		String file_extension = ".properties"; //this.base_configs
				//.get("java_extension").getValue();
		String bundle_file_name = this.capitalize(activity.getName()) + "_SQL";
	
	
	
		String the_path_string = this.sql_queries_bundle_path.toString() 
		+ this.file_separator + bundle_file_name + file_extension 
									+  this.file_separator;
		
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file(test_write_path, st);
		activity.setHasSQLgeneration(true);

	}

	/**
	 * sets up the references that the DAO will use to refer to the 
	 * Resoruce Bundles that it needs
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	public ArrayList<GpResourceBundleReference> set_up_resource_bundle_references(GpActivity activity) throws Exception{
		
		ArrayList<GpResourceBundleReference> the_refs = new ArrayList<GpResourceBundleReference>();
				
				
		for(GpVerb verb : activity.getTheverbs()){
			GpResourceBundleReference a_ref = new GpResourceBundleReference();
			String primary_noun_name = activity.getPrimary_noun()
												.getName().toLowerCase();
			a_ref.verb_action_on_data = verb.getAction_on_data();
			if(verb.getAction_on_data().equals("GpCreate")){
				
				a_ref.resource_bundle_key  = "${"+ "create_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "create_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpGetAllValues")){
				
				a_ref.resource_bundle_key  = "${"+ "get_all_" 
											+ primary_noun_name + ".sql";
				a_ref.local_string_reference = "get_all_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpSearchForUpdate")){
				
				a_ref.resource_bundle_key  = "${"+ primary_noun_name
											+ "_search_for_update" + ".sql";
				a_ref.local_string_reference = primary_noun_name + "_search_for_update" +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpGetNounById")){
				
				a_ref.resource_bundle_key  = "${"+ "get_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "get_"+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpUpdate")){
				
				a_ref.resource_bundle_key  = "${"+ "update_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "update_"
												+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpDelete")){
				
				a_ref.resource_bundle_key  = "${"+ "delete_"
												+ primary_noun_name+ ".sql";
				a_ref.local_string_reference = "delete_"
												+ primary_noun_name +"_sql";
				the_refs.add(a_ref);	
			}else if(verb.getAction_on_data().equals("GpSearch")){

				/*  TBD as of 12/30/2014  */
				continue;
			}
			
		}
		return the_refs;
		
	}
	
	/**
	 * sets up the references that the DAO will use to refer to the 
	 * Resoruce Bundles that it needs
	 * @param activity
	 * @return
	 * @throws Exception
	 */
	public void set_up_resource_bundle_references(GpActivity activity, ArrayList<GpBaseSqlDto> sql_list) throws Exception{
		
				
		for(GpBaseSqlDto sql_dto : sql_list){
			String primary_noun_name = activity.getPrimary_noun()
												.getName().toLowerCase();
			if(sql_dto.verb_action_on_data.equals("GpCreate")){
				
				sql_dto.resource_bundle_key  = "create_"
												+ primary_noun_name + ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "create_"
													+ primary_noun_name + ".sql";
				
				sql_dto.local_string_reference = "create_"+ primary_noun_name +"_sql";
				
			}else if(sql_dto.verb_action_on_data.equals("GpGetAllValues")){
				
				sql_dto.resource_bundle_key  = "get_all_" 
											+ primary_noun_name + ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "get_all_" 
										+ primary_noun_name + ".sql";
				sql_dto.local_string_reference = "get_all_"+ primary_noun_name +"_sql";
			}else if(sql_dto.verb_action_on_data.equals("GpSearchForUpdate")){
				
				sql_dto.resource_bundle_key  = primary_noun_name
											+ "_search_for_update" + ".sql";
				
				sql_dto.local_bundle_reference = "${"+ primary_noun_name
												+ "_search_for_update" + ".sql";
				sql_dto.local_string_reference = primary_noun_name + "_search_for_update" +"_sql";
			}else if(sql_dto.verb_action_on_data.equals("GpGetNounById")){
				
				sql_dto.resource_bundle_key  = "get_"
												+ primary_noun_name+ ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "get_"
												+ primary_noun_name+ ".sql";
				sql_dto.local_string_reference = "get_"+ primary_noun_name +"_sql";
			}else if(sql_dto.verb_action_on_data.equals("GpUpdate")){
				
				sql_dto.resource_bundle_key  = "update_"
												+ primary_noun_name+ ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "update_"
												+ primary_noun_name+ ".sql";
				sql_dto.local_string_reference = "update_"
												+ primary_noun_name +"_sql";
			}else if(sql_dto.verb_action_on_data.equals("GpDelete")){
				
				sql_dto.resource_bundle_key  = "delete_"
												+ primary_noun_name+ ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "delete_"
												+ primary_noun_name+ ".sql";
				sql_dto.local_string_reference = "delete_"
												+ primary_noun_name +"_sql";
			}else if(sql_dto.verb_action_on_data.equals("GpSearch")){
				
				sql_dto.resource_bundle_key  = "search_" 
											+ primary_noun_name + ".sql";
				
				sql_dto.local_bundle_reference = "${"+ "search_" 
										+ primary_noun_name + ".sql";
				sql_dto.local_string_reference = "search_"+ primary_noun_name +"_sql";
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
		//Path server_root_path = (Path)this.derived_configs.get(
		//			GpGenConstants.SERVER_SOURCE_ROOT_PATH).getObject_value();
		
		
		//System.out.println("In GpSpringServiceGenWorker - set_up_paths_and_templates "
		//		+ " server_root_path.toString() is: " + server_root_path.toString());
		//gen_server_directory_name

		
		Path gen_server_directory_path = (Path)this.derived_configs.get(
				GpGenConstants.GEN_SERVER_DIRECTORY_NAME_PATH).getObject_value();
		
		//System.out.println("$$$$$$In GpSpringResourceBundleGenWorker - set_up_paths_and_templates "
		//		+ " test is: " + gen_server_directory_path.toString());
		String WebModule = "WebContent";	
		String web_inf_directory = WebModule + this.file_separator + this.base_configs.get(
									"gen_base_java_server_webapp_webinf").getValue();

		String resources_directory =  this.base_configs.get(
				"gen_base_java_server_resources_directory").getValue();

		String sql_queries_directory =  this.base_configs.get(
				"gen_base_java_server_sql_queries_directory").getValue();

		
		//System.out.println("$$$$$$In GpSpringResourceBundleGenWorker - set_up_paths_and_templates "
		//		+ " web_inf_directory is: " + web_inf_directory);

		Path web_inf_path = Paths.get(gen_server_directory_path.toString() + this.file_separator
				+ web_inf_directory);
		Files.createDirectories(web_inf_path);
		
		GpArchitypeConfigurations web_inf_path_config = new GpArchitypeConfigurations();
		web_inf_path_config.setName(GpGenConstants.WEB_INF_PATH);
		web_inf_path_config.setObject_value(web_inf_path);
		derived_configs.put(GpGenConstants.WEB_INF_PATH, web_inf_path_config);

		
		Path resources_path = Paths.get(gen_server_directory_path.toString() + this.file_separator
				+ web_inf_directory + this.file_separator + resources_directory );
		Files.createDirectories(resources_path);

		
		GpArchitypeConfigurations resources_path_config = new GpArchitypeConfigurations();
		resources_path_config.setName(GpGenConstants.RESOURCES_PATH);
		resources_path_config.setObject_value(resources_path);
		derived_configs.put(GpGenConstants.RESOURCES_PATH, resources_path_config);


		Path sql_queries_path = Paths.get(gen_server_directory_path.toString() + this.file_separator
				+ web_inf_directory + this.file_separator + resources_directory + this.file_separator + sql_queries_directory );
		Files.createDirectories(sql_queries_path);

		GpArchitypeConfigurations sql_queries_path_config = new GpArchitypeConfigurations();
		sql_queries_path_config.setName(GpGenConstants.SQL_QUERIES_PATH);
		sql_queries_path_config.setObject_value(sql_queries_path);
		derived_configs.put(GpGenConstants.SQL_QUERIES_PATH, sql_queries_path_config);
		
		


		/*
		 * 
		 * second determine if the location of the template that will be used 
		 * for this code generation have been derived previously if not
		 * derive it
		 * 
		 */
		
		String root_code_template_location = this.base_configs
								.get("root_code_template_location").getValue();
		
		String server_java_standard_resource_bundle_template_location = this.base_configs
					.get("server_java_standard_resource_bundle_template_location").getValue();
		
		
		tokens = this.tokenize_string(
				server_java_standard_resource_bundle_template_location, null);

		config_name = this.build_name_from_tokens(tokens) + "_standard_resource_bundle" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;
		
	
		GpArchitypeConfigurations server_java_standard_resource_bundle_template_path_config 
														= this.derived_configs.get(config_name);
		
		if(server_java_standard_resource_bundle_template_path_config == null){
			//create and store the path to the domain templates
			String core_template_location_temp = root_code_template_location;
			for(String tok : tokens){
				core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
			}
			
			String template_name =  this.base_configs.get(
					"server_java_standard_resource_bundle_template_name").getValue();
			
			Path server_java_standard_resource_bundle_template_path = 
					Paths.get(core_template_location_temp 
										+ this.file_separator + template_name);
			server_java_standard_resource_bundle_template_path_config = new GpArchitypeConfigurations();
			
			server_java_standard_resource_bundle_template_path_config.setName(config_name);
			server_java_standard_resource_bundle_template_path_config.setObject_value(server_java_standard_resource_bundle_template_path);
			this.derived_configs.put(config_name, server_java_standard_resource_bundle_template_path_config);

		}
		
		this.standard_resource_bundle_template_path =  
				(Path)server_java_standard_resource_bundle_template_path_config
																.getObject_value(); 
		this.sql_queries_bundle_path = (Path)sql_queries_path_config.getObject_value();
	
	}

}
