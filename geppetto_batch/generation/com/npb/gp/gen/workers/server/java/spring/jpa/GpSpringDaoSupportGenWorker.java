package com.npb.gp.gen.workers.server.java.spring.jpa;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpTechProperties;
import com.npb.gp.gen.workers.GpGenJavaServerSpringBaseWorker;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpSqlMapperDto;
 
/**
 * @author Dan Castillo
 * Date Created: 01/20/2015</br>
 * @since .2</p> 
 * 
 * Generates the code for a Java Spring DAO layer that uses the Spring template</br>
 * and mapper approach - specifically this calss handles the SUPPORT classes</br>
 * that are the MAPPERS</br>
 * Please note that this class is always a slave to the LegacyDao generation classes</br>
 * and therefore are only meant to be called by that generation worker</p> 
 * 
 */

@Component("GpSpringDaoSupportGenWorker")
public class GpSpringDaoSupportGenWorker extends
		GpGenJavaServerSpringBaseWorker {
	
	private Path read_template_group_path;
	private Path update_template_group_path;
	private Path mapper_directory_path;
	private String dao_package_name;
	private Path dao_path;
	
	
	private String insert_mapper_type;
	private String update_mapper_type;
	private String read_maper_type;
	private String insert_mapper_reference;
	private String update_mapper_reference;
	private String base_mapper_package;
	private Map<String, Boolean> generated_nouns = new HashMap<String, Boolean>();
	private String rdbms_in_use;
	
	
	
	public Path getDao_path() {
		return dao_path;
	}


	public void setDao_path(Path dao_path) {
		this.dao_path = dao_path;
	}


	public String getDao_package_name() {
		return dao_package_name;
	}


	public void setDao_package_name(String dao_package_name) {
		this.dao_package_name = dao_package_name;
	}


	public String get_mapper_package(){
		return null;
	}

	
	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		
		if(this.dao_path == null){
			throw new Exception("DAO_PATH is null - call create_mapper_directory() first ");
		}
		
		
	}
	
	
	
	public void do_generation(GpActivity activity, Path dao_path) throws Exception{
		
		if(this.read_template_group_path == null 
				|| this.update_template_group_path == null ){
			
			this.create_mapper_directory(activity, dao_path);
			
		}

		if(this.dao_package_name == null){
			throw new Exception("DAO_PACKAGE_NAME is null - call setDao_package_name() first ");
		}
		if(activity.getPrimary_noun() != null){
			boolean is_generated = false;
			if(generated_nouns.get(activity.getPrimary_noun().getName()) != null)
				is_generated = true;
			if(!is_generated){
				this.generate_update_mapper(activity);
				this.generate_insert_mapper(activity);
				this.generate_read_mapper(activity);
				generated_nouns.put(activity.getPrimary_noun().getName(), true);
			}
		}
	}
	
	public String get_insert_mapper_type(GpActivity activity){
		this.insert_mapper_type = activity.getPrimary_noun().getName() 
				+ "InsertMapper";
		return this.insert_mapper_type;
		
	}
	public String get_update_mapper_type(GpActivity activity){
		
		this.update_mapper_type = activity.getPrimary_noun().getName()
				+ "UpdateMapper";
		
		return this.update_mapper_type;
		
	}
	public String get_read_mapper_type(GpActivity activity){
		this.read_maper_type = activity.getPrimary_noun().getName() + "Mapper";
		
		return this.read_maper_type;
	}
	
	
	public Path create_mapper_directory(GpActivity activity, Path dao_path)throws Exception {
		
		this.dao_path = dao_path;
		if(activity.getPrimary_noun() != null) {
			Path mapper_path = Paths.get(dao_path.toString() + this.file_separator
					+ "support" +  this.file_separator + activity.getPrimary_noun().getName());
			Files.createDirectories(mapper_path);
			this.mapper_directory_path = mapper_path;
			
			return mapper_path;
		}
		return null;
	}
	
	/**
	 * Because this class is a SLAVE to the DAO gen worker this
	 * method is only meant to be called from the Legacy Dao Gen worker
	 * @param activity
	 * @throws Exception
	 */
	
	public void set_up_paths_and_templates(GpActivity activity, Path dao_path) throws Exception {
		/*
		 * first see if the location where the code will be generated to
		 * has already been derived - if not create a new config for it 
		 */
		if(this.mapper_directory_path == null){
			this.create_mapper_directory(activity, dao_path);
		}
		
		
		/*
		 * 
		 * second determine if the location of the template that will be used 
		 * for this code generation have been derived previously if not
		 * derive it
		 * 
		 */

		this.set_up_update_mapper_template_path();
		this.set_up_read_mapper_template_path();
	}

	private void generate_read_mapper(GpActivity activity) throws Exception{
		
		ST st = super.read_template_group(this.read_template_group_path, "output");
		
		String copy_right_range =  this.base_configs.get(
				"copy_right_range").getValue();
		GpTechProperties tech_prop = super
					.get_tech_property(this.the_project.getServer_dbms());

		this.base_mapper_package = this.dao_package_name  +"."
				+"support"	+ "." 
						+ activity.getPrimary_noun().getName();		
		
		st.add("package_name", this.base_mapper_package);
		st.add("class_name", this.get_read_mapper_type(activity));
		
		String domain_core_package_name = super.get_generation_service()
					.getDomain_gen_worker().getDomain_core_package_name();
		
		st.add("primary_noun_import", "import " + domain_core_package_name 
						+ "." + activity.getPrimary_noun().getName() +";");

		st.add("primary_noun", activity.getPrimary_noun().getName());
		String primary_noun_reference = "the_"+ activity.getPrimary_noun().getName();
		st.add("primary_noun_reference", primary_noun_reference);
		
		String file_extension =  this.base_configs
				.get("java_extension").getValue();
	
	
		String the_path_string = this.mapper_directory_path.toString() 
		+ this.file_separator + this.get_read_mapper_type(activity)	+ file_extension +  this.file_separator;
		
	
				ArrayList<GpNounAttribute> attribs = activity.
						getPrimary_noun().getNounattributes();
		ArrayList<GpSqlMapperDto> dto_list = new ArrayList<GpSqlMapperDto>();
		
		for(GpNounAttribute attrib : attribs){
			GpSqlMapperDto dto = new GpSqlMapperDto();
			dto.primary_noun_reference = primary_noun_reference;
			dto.attribute_name = attrib.getName();
			dto.setter_method = "set_" + attrib.getName() +"(rs.get" ;
			if(attrib.getSubtype().equals("whole number")){
				dto.sql_type = "BIGINT";
				dto.setter_method = dto.setter_method + "Long(\"" + dto.attribute_name + "\"))";
			}else if(attrib.getSubtype().equals("Text")){
				dto.sql_type = "VARCHAR";
				dto.setter_method = dto.setter_method + "String(\"" + dto.attribute_name + "\"))";
			}else if(attrib.getSubtype().equals("currency")){
				dto.sql_type = "DECIMAL";
				dto.setter_method = dto.setter_method + "BigDecimal(\"" + dto.attribute_name + "\"))";
			}else if(attrib.getSubtype().equals("true/false")){
				dto.sql_type = "CHAR";
				dto.setter_method = "set_" + attrib.getName() +"(new Boolean(rs.get" + "String(\"" + dto.attribute_name + "\").equals(\"Y\") ? true:false))";
			}else if(attrib.getSubtype().equals("Date")){
				dto.sql_type = "TIMESTAMP";
				dto.setter_method = dto.setter_method + "Timestamp(\"" + dto.attribute_name + "\"))";
			}
			dto_list.add(dto);
		}
		GpSqlMapperDto dto_for_implicit_id = new GpSqlMapperDto();
		dto_for_implicit_id.primary_noun_reference = primary_noun_reference;
		dto_for_implicit_id.attribute_name = "Id";
		dto_for_implicit_id.setter_method = "set_Id(rs.getLong(\"Id\"))" ;
		dto_list.add(dto_for_implicit_id);
		st.add("the_parms", dto_list);
		
		Path test_write_path = Paths.get(the_path_string);
		super.write_file( test_write_path, st);


		
	}

	private void generate_insert_mapper(GpActivity activity) throws Exception{
		
		ST st = super.read_template_group(this.update_template_group_path, "output");
		
		String copy_right_range =  this.base_configs.get(
				"copy_right_range").getValue();
		
		
		GpTechProperties tech_prop = super
				.get_tech_property(this.the_project.getServer_dbms());

		this.base_mapper_package = this.dao_package_name 
			 + "."+ "support"	+ "." 
					+ activity.getPrimary_noun().getName();

		
		//this.base_mapper_package = this.dao_package_name + "."+"support" 
		//		+ "." + activity.getPrimary_noun().getName().toLowerCase();
		
		
		
		st.add("package_name", this.base_mapper_package);
		st.add("class_name", this.get_insert_mapper_type(activity));
		st.add("insert_or_update", "INSERT");
		st.add("primary_noun", activity.getPrimary_noun().getName().toUpperCase());
		//
		
		ArrayList<GpNounAttribute> attribs = activity.
									getPrimary_noun().getNounattributes();
		ArrayList<GpSqlMapperDto> dto_list = new ArrayList<GpSqlMapperDto>();
		
		for(GpNounAttribute attrib : attribs){
			GpSqlMapperDto dto = new GpSqlMapperDto();
			dto.attribute_name = attrib.getName();
			if(attrib.getSubtype().equals("whole number")){
				dto.sql_type = "BIGINT";
			}else if(attrib.getSubtype().equals("Text")){
				dto.sql_type = "VARCHAR";
			}else if(attrib.getSubtype().equals("currency")){
				dto.sql_type = "DECIMAL";
			}else if(attrib.getSubtype().equals("true/false")){
				dto.sql_type = "CHAR";
			}else if(attrib.getSubtype().equals("Date")){
				dto.sql_type = "TIMESTAMP";
			}

			
			dto_list.add(dto);
			
		}
		st.add("the_parms", dto_list);
		
		ArrayList<String> key_holder_list = new ArrayList<String>();
		key_holder_list.add("id");
		st.add("the_keyholder", key_holder_list);
		
		String file_extension =  this.base_configs
				.get("java_extension").getValue();
	
	
		String the_path_string = this.mapper_directory_path.toString() 
		+ this.file_separator + this.get_insert_mapper_type(activity)	+ file_extension +  this.file_separator;
		
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file( test_write_path, st);

	}

	
	private void generate_update_mapper(GpActivity activity) throws Exception{
		ST st = super.read_template_group(this.update_template_group_path, "output");
		
		String copy_right_range =  this.base_configs.get(
				"copy_right_range").getValue();
		
		GpTechProperties tech_prop = super
				.get_tech_property(this.the_project.getServer_dbms());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@ " + tech_prop.getName());

		this.base_mapper_package = this.dao_package_name 
			+"."+"support"	+ "." 
					+ activity.getPrimary_noun().getName();

		
		//this.base_mapper_package = this.dao_package_name + "."+"support" 
		//		+ "." + activity.getPrimary_noun().getName().toLowerCase();
		
		
		
		st.add("package_name", this.base_mapper_package);
		st.add("class_name", this.get_update_mapper_type(activity));
		st.add("insert_or_update", "UPDATE");
		st.add("primary_noun", activity.getPrimary_noun().getName().toUpperCase());
		
		
		ArrayList<GpNounAttribute> attribs = activity.
									getPrimary_noun().getNounattributes();
		ArrayList<GpSqlMapperDto> dto_list = new ArrayList<GpSqlMapperDto>();
		
		for(GpNounAttribute attrib : attribs){
			GpSqlMapperDto dto = new GpSqlMapperDto();
			dto.attribute_name = attrib.getName();
			//System.out.println("################### attrib");
			if(attrib.getSubtype().equals("whole number")){
				dto.sql_type = "BIGINT";
			}else if(attrib.getSubtype().equals("Text")){
				dto.sql_type = "VARCHAR";
			}else if(attrib.getSubtype().equals("currency")){
				dto.sql_type = "DECIMAL";
			}else if(attrib.getSubtype().equals("true/false")){
				dto.sql_type = "CHAR";
			}else if(attrib.getSubtype().equals("Date")){
				dto.sql_type = "TIMESTAMP";
			}

			
			dto_list.add(dto);
		}
		GpSqlMapperDto dto_for_implicit_id = new GpSqlMapperDto();
		dto_for_implicit_id.attribute_name = "Id";
		dto_for_implicit_id.sql_type = "BIGINT";
		dto_list.add(dto_for_implicit_id);
		st.add("the_parms", dto_list);
		
		String file_extension =  this.base_configs
				.get("java_extension").getValue();
	
	
		String the_path_string = this.mapper_directory_path.toString() 
		+ this.file_separator + this.get_update_mapper_type(activity)	+ file_extension +  this.file_separator;
		
	
		Path test_write_path = Paths.get(the_path_string);
		super.write_file( test_write_path, st);


	}
	private void set_up_update_mapper_template_path(){
		
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String update_mapper_template_location = this.base_configs
			.get("server_java_spring_legacy_dao_update_mapper_template_location").getValue();
		
		
		String[] template_tokens = this.tokenize_string(
					update_mapper_template_location, null);
		
		String core_template_location_temp = root_code_template_location;
		for(String tok : template_tokens){
		core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
		}
		
		String template_name =  this.base_configs.get(
		"server_java_spring_legacy_dao_update_mapper_template_name").getValue();
		
		
		Path update_mapper_template_path = 
			Paths.get(core_template_location_temp 
								+ this.file_separator + template_name);

		
		this.update_template_group_path = update_mapper_template_path;
	}
	
	private void set_up_read_mapper_template_path(){
		
		
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();

		String update_mapper_template_location = this.base_configs
			.get("server_java_spring_legacy_dao_read_mapper_template_location").getValue();
		
		
		String[] template_tokens = this.tokenize_string(
					update_mapper_template_location, null);
		
		String core_template_location_temp = root_code_template_location;
		for(String tok : template_tokens){
		core_template_location_temp = core_template_location_temp +  this.file_separator +  tok;
		}
		
		String template_name =  this.base_configs.get(
		"server_java_spring_legacy_dao_read_mapper_template_name").getValue();
		
		
		Path read_mapper_template_path = 
			Paths.get(core_template_location_temp 
								+ this.file_separator + template_name);
		
		this.read_template_group_path = read_mapper_template_path;
	}

}
