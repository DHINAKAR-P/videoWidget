package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.domain.js.node.express.ExpressRouterDescription;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.services.server.java.GpServerJavaSpringGenService;
import com.npb.gp.gen.services.server.java.springboot.GpServerJavaSpringBootGenService;
import com.npb.gp.gen.services.server.node.GpServerNodeExpressGenService;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
import com.npb.gp.gen.util.dto.GpControllerVerbGenInfo;
import com.npb.gp.gen.util.dto.springboot.GpControllerSpringBootVerbGenInfo;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenSupport;

public class GpBootstrapDropdownList extends GpBaseHtmlGenInfo {
	GpBaseHtmlGenInfo parent;
	private boolean isSecondaryNoun;
	
	public GpBootstrapDropdownList(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += ">\n";
		//I don't know how to work from DB so... We should change this
		if(isSecondaryNoun && !noun_attribute.equals("not_found")){
			//System.out.println(noun_attribute);
			String noun_name = noun_attribute.split("\\.")[0];
			the_html += "<select>\n"
					+ "<option ng-repeat=\""+ noun_name + " in array_" + noun_name.toLowerCase() +"\" >{{"+noun_attribute+"}}</option>\n"//value? not ready for now
					+ "</select>";
		}
		else
		the_html += "<select>\n"
				+ "<option value=\"1\">Value 1</option>\n"
				+ "<option value=\"2\">Value 2</option>\n"
				+ "<option value=\"3\">Value 3</option>\n"
				+ "<option value=\"4\">Value 4</option>\n"
				+ "</select>";
		the_html += "\n</div>";
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		if(parent != null){
			this.widget.x = this.widget.x - parent.widget.x;
			this.widget.y = this.widget.y - parent.widget.y;
		}
		//section: style 
		style = "position: absolute;";
		style += "width: " + this.width + "px;";
		style += "height: " + this.height + "px;";
		style += "left: " + this.widget.getX() + "px;";
		style += "top: " + this.widget.getY() + "px;";
		style += "padding: 5px;";
		
	}
	
	public void handle_secondary_noun_binding(GpProject the_project, HashMap<String,GpArchitypeConfigurations> base_configs, GpAngularJsHtmlGenSupport the_gen_support, GpActivity sec_activity, GpActivity main_activity) throws Exception{
		isSecondaryNoun = true;
		String project_name = the_project.getName();
		String created_by = the_project.getCreatedby() + "";
		String server_port = "";
		String server_url = base_configs.get("server_host_name").getValue();
		Map<String, String> map_url_services = new HashMap<>();
		if(the_gen_support.getThe_worker().get_generation_service().back_isJavaSpring){
			server_port = GpServerJavaSpringGenService.server_port;
			GpServerJavaSpringGenService java_spring_service = the_gen_support.getThe_worker().get_generation_service().getActivity_service().getServer_java_spring_gen_service();
			Map<String,GpControllerVerbGenInfo> map_routes_java_spring = java_spring_service.getController_gen_worker().getController_support().get_map_controller(sec_activity, java_spring_service);
			Set<String> set = map_routes_java_spring.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				map_url_services.put(verb_action, map_routes_java_spring.get(verb_action).method_name);
			}
		}
		if(the_gen_support.getThe_worker().get_generation_service().getActivity_service().back_isJavaScriptNodeJSExpress){
			server_port = GpServerNodeExpressGenService.server_port;
			Map<String,ExpressRouterDescription> map_routes_node = the_gen_support.getThe_worker().get_generation_service().getActivity_service().getServer_node_express_gen_service().getThe_routes_worker().getThe_gen_support().get_the_routes_map(sec_activity);
			Set<String> set = map_routes_node.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				map_url_services.put(verb_action, map_routes_node.get(verb_action).string_route_path);
			}
		}
		if(the_gen_support.getThe_worker().get_generation_service().getActivity_service().back_isJavaSpringBootJpa){
			server_port = GpServerJavaSpringGenService.server_port;
			GpServerJavaSpringBootGenService java_spring_boot_service = the_gen_support.getThe_worker().get_generation_service().getActivity_service().getServer_java_spring_boot_gen_service();
			Map<String,GpControllerSpringBootVerbGenInfo> map_routes_java_spring = java_spring_boot_service.getSpring_boot_controller().getController_spring_boot_support().get_the_map_methods(sec_activity, java_spring_boot_service, null);
			Set<String> set = map_routes_java_spring.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String verb_action = (String) iterator.next();
				map_url_services.put(verb_action, map_routes_java_spring.get(verb_action).request_map_value);
			}
		}
		String ws_route = map_url_services.get(GpBaseVerbsConstants.GpGetAllValues);
		Map<String,String> code_to_add_to_controllers =  the_gen_support.getThe_worker().getCode_to_add_to_controllers();
		String code_to_add = code_to_add_to_controllers.get(main_activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
		if(code_to_add == null)
			code_to_add = "";
		code_to_add += "\n";
		code_to_add += "$http.get('"+ server_url + ":" + server_port + "/" + project_name + "_" + created_by + "/" + sec_activity.getName() + "/" + ws_route +"').success(function(response) {"
						+ "\n\t$scope.array_"+ sec_activity.getPrimary_noun().getName().toLowerCase() +" = response;"
						+ "\n\tconsole.log('Operation get all successful');"
		  				+ "}).error(function(err) {"
		  				+ "\n\talert('You got' + err + 'error');"
		  				+ "});";
		code_to_add_to_controllers.put(main_activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add);
	}

}
