package com.npb.gp.gen.workers.server.node.express;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.js.node.express.ExpressRouterDescription;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlMethodDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlModelDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlModelPropertyDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlParametersDto;
import com.npb.gp.gen.util.dto.nodejs.express.GpNodeExpressSwaggerYamlResponseDto;
import com.npb.gp.gen.workers.GpGenNodeServerExpressBaseWorker;
import com.npb.gp.gen.workers.server.node.express.support.routes.GpExpressRoutesGenSupport;

@Component("GpExpressSwaggerGenWorker")
public class GpExpressSwaggerGenWorker extends GpGenNodeServerExpressBaseWorker{
	private Path template_group_path;
	private String file_extension = ".yaml";
	private String file_name = "swagger";
	private List<GpNodeExpressSwaggerYamlDto> paths;
	private List<GpNodeExpressSwaggerYamlModelDto> models;
	private Path swagger_path;
	@Override
	public void prep_derived_values(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception {
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;
		this.set_up_paths_and_templates();
	}
	
	private void set_up_paths_and_templates() {
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String server_nodejs_express_dao_file_template_location = this.base_configs
				.get("server_nodejs_express_swagger_template_location").getValue();
		String[] tokens = this.tokenize_string(
				server_nodejs_express_dao_file_template_location, null);
		String config_name;
		config_name = this.build_name_from_tokens(tokens) + "_swagger_file" + GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;	
		String core_template_location_temp = root_code_template_location;
		for(String tok : tokens){
			core_template_location_temp +=  this.file_separator +  tok;
		}
		String template_name =  this.base_configs.get(
				"server_nodejs_express_swagger_template_name").getValue();
		core_template_location_temp += this.file_separator + template_name;
		GpArchitypeConfigurations package_path_config = new GpArchitypeConfigurations();
		package_path_config.setName(config_name);
		package_path_config.setObject_value(Paths.get(core_template_location_temp));
		this.derived_configs.put(config_name, package_path_config);
		this.template_group_path =   (Path) package_path_config.getObject_value();
		
	}
	
	private void create_swagger_directory() throws IOException{
		swagger_path = Paths.get(getGen_service().getThe_directory_worker().getServer_client_root_path().toString() + this.file_separator + "swagger");
		Files.createDirectory(swagger_path );
	}
	
	private void copy_swagger_ui() throws Exception{
		String root_code_template_location = this.base_configs
				.get("root_code_template_location").getValue();
		String swagger_ui_location = this.base_configs
				.get("server_nodejs_express_swaggerui_folder_location").getValue();
		String[] tokens = this.tokenize_string(
				swagger_ui_location, null);
		String path_swagger_ui_string = root_code_template_location;
		for(String tok : tokens){
			path_swagger_ui_string +=  this.file_separator +  tok;
		}
		getGen_service().getThe_directory_worker().copy_directory(new File(path_swagger_ui_string), new File(getGen_service().getThe_directory_worker().getServer_client_root_path().toString() + this.file_separator + "api"));
	}

	@Override
	public void generate_code(GpProject the_project, HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		this.create_swagger_directory();
		this.copy_swagger_ui();
		GpArchitypeConfigurations activities_prop = super.derived_configs.get(GpGenConstants.PROJECT_ACTIVITIES);
		ArrayList<GpActivity> the_activities = (ArrayList<GpActivity>) activities_prop.getObject_value();
		paths = new ArrayList<>();
		models = new ArrayList<>();
		for(GpActivity activity : the_activities){
			if (activity.getModule_type() != null && activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				//swagger for modules
			}
			else{
				this.generate_code_by_activity(activity, the_project, base_configs, derived_configs, the_flow, gen_manager);
			}
		}
		ST st = super.read_template_group(this.template_group_path, "output");
		st.add("project_name", super.the_project.getName());
		String server_host_name =  this.base_configs.get(
				"server_host_name").getValue();
		server_host_name = server_host_name.replace("http://", "");
		st.add("server_url", server_host_name);
		st.add("port", GpServerNodeExpressGenService.server_port);
		st.add("base_path", super.the_project.getName() + "_" + super.the_project.getCreatedby());
		if(!paths.isEmpty()){
			st.add("paths_key", "paths:");
			st.add("paths", paths);
		}		
		if(!models.isEmpty()){
			st.add("definitions_key", "definitions:");
			st.add("models", models);
		}
		
		String the_path_string = swagger_path + this.file_separator + file_name + file_extension;
		Path the_path = Paths.get(the_path_string);
		super.write_file(the_path, st);
	}
	
	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
			HashMap<String, GpArchitypeConfigurations> base_configs,
			HashMap<String, GpArchitypeConfigurations> derived_configs, GpFlowControl the_flow,
			IGpGenManager gen_manager) throws Exception {
		Map<String,ExpressRouterDescription> the_map_routes = getGen_service().getThe_routes_worker().getThe_gen_support().get_the_routes_map(activity);
		Set<String> set = the_map_routes.keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String verb_action = (String) iterator.next();
			ExpressRouterDescription routerDescription = the_map_routes.get(verb_action);
			GpNodeExpressSwaggerYamlDto the_path = new GpNodeExpressSwaggerYamlDto();
			the_path.string_path = "/" + activity.getName() + "/" + routerDescription.string_route_path;
			GpNodeExpressSwaggerYamlMethodDto the_method = new GpNodeExpressSwaggerYamlMethodDto();
			the_method.http_method = routerDescription.http_method;
			if(verb_action.equals(GpBaseVerbsConstants.GpGetAllValues)){
				the_method.description = "Get all " + activity.getPrimary_noun().getName();
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "200";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpGetNounById)){
				the_method.description = "Get " + activity.getPrimary_noun().getName() + " by id";
				the_path.string_path += "/{" + activity.getPrimary_noun().getName() + "Id}";
				the_method.parameter_key = "parameters:";
				GpNodeExpressSwaggerYamlParametersDto parametersDto = new GpNodeExpressSwaggerYamlParametersDto();
				parametersDto.name = activity.getPrimary_noun().getName() + "Id";
				parametersDto.description = "id";
				parametersDto.in = "path";
				parametersDto.required = "true";
				parametersDto.type = "integer";
				the_method.parameters.add(parametersDto);
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "200";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpSearchForUpdate)){
				the_method.description = "Get " + activity.getPrimary_noun().getName() + " by id";
				the_path.string_path += "/{" + activity.getPrimary_noun().getName() + "Id}";
				the_method.parameter_key = "parameters:";
				GpNodeExpressSwaggerYamlParametersDto parametersDto = new GpNodeExpressSwaggerYamlParametersDto();
				parametersDto.name = activity.getPrimary_noun().getName() + "Id";
				parametersDto.description = "id";
				parametersDto.in = "path";
				parametersDto.required = "true";
				parametersDto.type = "integer";
				the_method.parameters.add(parametersDto);
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "200";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpUpdate)){
				the_method.description = "Update " + activity.getPrimary_noun().getName();
				the_method.parameter_key = "parameters:";
				GpNodeExpressSwaggerYamlParametersDto parametersdto = new GpNodeExpressSwaggerYamlParametersDto();
				parametersdto.name = activity.getPrimary_noun().getName().toLowerCase();
				parametersdto.description = "json object";
				parametersdto.in = "body";
				parametersdto.required = "true";
				parametersdto.type = "string";
				parametersdto.schema_key = "schema:";
				parametersdto.shema_value = "$ref: '#/definitions/"+ activity.getPrimary_noun().getName() +"'";
				the_method.parameters.add(parametersdto);
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "200";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpSearch)){
				the_method.description = "Search " + activity.getPrimary_noun().getName();
				the_method.parameter_key = "parameters:";
				List<GpNounAttribute> noun_attrs = activity.getPrimary_noun().getNounattributes();
				for(GpNounAttribute attr : noun_attrs){
					GpNodeExpressSwaggerYamlParametersDto parametersDto_attr = new GpNodeExpressSwaggerYamlParametersDto();
					parametersDto_attr.name = attr.getName().toLowerCase();
					parametersDto_attr.description = attr.getName();
					parametersDto_attr.in = "query";
					parametersDto_attr.required = "true";
					String sub_type = attr.getSubtype();
					if(sub_type.equals("whole number")){
						parametersDto_attr.type = "integer";
					}
					else if(sub_type.equals("Text")){
						parametersDto_attr.type = "string";
					}
					else if(sub_type.equals("currency")){
						parametersDto_attr.type = "float";
					}
					else if(sub_type.equals("true/false")){
						parametersDto_attr.type = "boolean";
					}
					else if(sub_type.equals("Date")){
						parametersDto_attr.type = "string";
					}
					the_method.parameters.add(parametersDto_attr);
				}
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "200";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpCreate)){
				the_method.description = "Create " + activity.getPrimary_noun().getName();
				the_method.parameter_key = "parameters:";
				GpNodeExpressSwaggerYamlParametersDto parametersdto = new GpNodeExpressSwaggerYamlParametersDto();
				parametersdto.name = activity.getPrimary_noun().getName().toLowerCase();
				parametersdto.description = "json object";
				parametersdto.in = "body";
				parametersdto.required = "true";
				parametersdto.type = "string";
				parametersdto.schema_key = "schema:";
				parametersdto.shema_value = "$ref: '#/definitions/"+ activity.getPrimary_noun().getName() +"'";
				the_method.parameters.add(parametersdto);
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "201";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
			if(verb_action.equals(GpBaseVerbsConstants.GpDelete)){
				the_method.description = "Delete " + activity.getPrimary_noun().getName();
				the_method.parameter_key = "parameters:";
				the_path.string_path += "/{" + activity.getPrimary_noun().getName() + "Id}";
				GpNodeExpressSwaggerYamlParametersDto parametersDto = new GpNodeExpressSwaggerYamlParametersDto();
				parametersDto.name = activity.getPrimary_noun().getName() + "Id";
				parametersDto.description = "id";
				parametersDto.in = "path";
				parametersDto.required = "true";
				parametersDto.type = "integer";
				the_method.parameters.add(parametersDto);
				GpNodeExpressSwaggerYamlResponseDto responseDto = new GpNodeExpressSwaggerYamlResponseDto();
				responseDto.http_code = "204";
				responseDto.description = "Success";
				the_method.responses.add(responseDto);
				the_path.methods.add(the_method);
				this.add_path(the_path);
				continue;
			}
		}
		this.addModel(activity.getPrimary_noun());
	}
	
	private void addModel(GpNoun noun){
		boolean added = false;
		for(GpNodeExpressSwaggerYamlModelDto model : models){
			if(model.name.equals(noun.getName())){
				added = true;
				break;
			}
		}
		if(!added){
			GpNodeExpressSwaggerYamlModelDto new_model = new GpNodeExpressSwaggerYamlModelDto();
			new_model.name = noun.getName();
			GpNodeExpressSwaggerYamlModelPropertyDto implicit_property = new GpNodeExpressSwaggerYamlModelPropertyDto();
			implicit_property.name = "id";
			implicit_property.type = "integer";
			new_model.properties.add(implicit_property);
			for(GpNounAttribute attr : noun.getNounattributes()){
				GpNodeExpressSwaggerYamlModelPropertyDto property = new GpNodeExpressSwaggerYamlModelPropertyDto();
				property.name = attr.getName().toLowerCase();
				String sub_type = attr.getSubtype();
				if(sub_type.equals("whole number")){
					property.type = "integer";
				}				
				if(sub_type.equals("Text")){
					property.type = "string";
				}
				if(sub_type.equals("currency")){
					property.type = "float";
				}
				if(sub_type.equals("true/false")){
					property.type = "boolean";
				}
				if(sub_type.equals("Date")){
					property.type = "string";
				}
				new_model.properties.add(property);
				new_model.required_properties.add(property);
			}
			this.models.add(new_model);
		}
	}
	
	private void add_path(GpNodeExpressSwaggerYamlDto the_new_path){
		boolean added = false;
		for(GpNodeExpressSwaggerYamlDto path : paths){
			for(GpNodeExpressSwaggerYamlMethodDto method : path.methods){
				if(path.string_path.equals(the_new_path.string_path)){//same route
					if(the_new_path.methods.get(0).http_method.equals(method.http_method)){
						//same method? this should not happen
						System.out.println("Same http method in same route");
					}
					else{
						added = true;
						path.methods.add(the_new_path.methods.get(0));
						break;
					}
				}
			}
			
		}
		if(!added){
			paths.add(the_new_path);
		}
	}
	
}
