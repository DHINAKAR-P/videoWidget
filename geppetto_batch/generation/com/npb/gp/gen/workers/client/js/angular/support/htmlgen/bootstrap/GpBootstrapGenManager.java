package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.constants.GpDataBindingConstants;
import com.npb.gp.constants.GpVerbBindingConstants;
import com.npb.gp.dao.mysql.GpVerbsDao;
import com.npb.gp.dao.mysql.TemplateDependenciesDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpEventVerbConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpWidgetConstants;
import com.npb.gp.gen.json.mappers.gcd.GCDJsonKeys;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenManagerBase;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenSupport;

/**
 * 
 * @author Dan Castillo
 * Date Created: 05/20/2015</br>
 * @since .2</p> 
 * 
 * Contains the coordination logic to generate the HTML and AngularJS template</br>
 * for a BootStrap enabled page - for this version of Geppetto code Generator</br>
 * BootStrap is synonymous with standard desktop browser based applications</p>
 * 
 */

@Component("GpBootstrapGenManager")
public class GpBootstrapGenManager extends GpAngularJsHtmlGenManagerBase {
	private HashMap<String,GpArchitypeConfigurations> base_configs;
	GpAngularJsHtmlGenSupport html_gen_support;
	int count_sections;
	String modal_for_getNounId;
	private boolean factory_for_primary_noun_generated = false;
	private TemplateDependenciesDao templateDependenciesDao;
	private GpVerbsDao verbs_dao;
	
	public GpVerbsDao getVerbs_dao() {
		return verbs_dao;
	}
	
	@Resource(name="GpVerbsDao")
	public void setVerbs_dao(GpVerbsDao verbs_dao) {
		this.verbs_dao = verbs_dao;
	}
	
	public TemplateDependenciesDao getTemplateDependenciesDao() {
		return templateDependenciesDao;
	}
	
	@Resource(name="TemplateDependenciesDao")
	public void setTemplateDependenciesDao(
			TemplateDependenciesDao templateDependenciesDao) {
		this.templateDependenciesDao = templateDependenciesDao;
	}
	
	public GpAngularJsHtmlGenSupport getHtml_gen_support() {
		return html_gen_support;
	}
	@Resource(name="GpAngularJsHtmlGenSupport")
	public void setHtml_gen_support(GpAngularJsHtmlGenSupport html_gen_support) {
		this.html_gen_support = html_gen_support;
	}
	
	public HashMap<String, GpArchitypeConfigurations> getBase_configs() {
		return base_configs;
	}
	
	public void setBase_configs(HashMap<String, GpArchitypeConfigurations> base_configs) {
		this.base_configs = base_configs;
	}

	@Override
	public HashMap<String,String> generate_html_page(List<GpUiWidgetX> sorted_widgets,
			GpScreenX screen, GpActivity activity,String templateName) throws Exception{
		System.out.println("@@@@@@@@@@@@@@ In GpBootstrapGenManager - generate_html_page @@@@@@@@@@");
		//this is where you should start the generation of the page
		//OJO - YOU don't need the HEAD becuase these are AngularJS HTML templates - the head is in the Index.html
		//1. after you deal with the head of the html page then you have to start the body 
		//2. for the body where do you start the TAB start for the DIVS? AND COMPONENENTS
		HashMap<String, String> map  = new HashMap<String, String>();
		String body_string = "";
		modal_for_getNounId = null;
		//Is there a verb? is possible to have more than 1 form? working with just one for now
		
		String header = 	this.templateDependenciesDao.getDependenciesByComponentType
				("html", templateName,"header");
		String footer = 	this.templateDependenciesDao.getDependenciesByComponentType
				("html", templateName,"footer");
		boolean isThereAVerb = isThereAVerb(sorted_widgets);
		body_string += "<div class=\"screen\">\n";
		if(isThereAVerb){
			body_string += "<form class=\"form-horizontal\" role=\"form\">\n";
		}
		body_string += get_html(sorted_widgets, activity);
		if(modal_for_getNounId != null)
			body_string += modal_for_getNounId;
		if(isThereAVerb){
			body_string += "</form>\n";
		}
		body_string += "</div>\n";
		map.put("header", header);
		map.put("body", body_string);
		map.put("footer", footer);
		return map;
	}
	
	public String get_html(List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		String the_html = "";
		for(GpUiWidgetX widget : widgets){
			if(widget.getIs_container().equals("true")){
				the_html += handle_containers(widget, widgets,activity, null);
			}
			else if(widget.getParentid() == widget.getScreen_id()){//Elements outside a container
				the_html += handle_elements(widget, activity,null) + "\n";
			}
		}
		return the_html;
	}
	
	public String handle_border_container(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		System.out.println("Generating Border Container");
		GpBootstrapBorderContainer bootstrapBorderContainer = new GpBootstrapBorderContainer();
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			bootstrapBorderContainer.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		bootstrapBorderContainer.set_base_attributes(widget);
		return bootstrapBorderContainer.get_html_tag() + get_html_of_children(widgets,bootstrapBorderContainer,activity) + bootstrapBorderContainer.getEnd_tag(); 
	}
	
	public String handle_containers(GpUiWidgetX widget, List<GpUiWidgetX> widgets, GpActivity activity, GpBaseHtmlGenInfo parent) throws Exception{
		if(widget.getType().equals(GpWidgetConstants.TYPE_BORDERCONTAINER)){
			return handle_border_container(widget,widgets,activity) + "\n";
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_PANEL)){
			return handle_panel(widget,widgets,activity) + "\n";
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_TAB)){
			return handle_tab(widget, widgets, activity) + "\n";
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_SECTION)){
			
			if(parent != null && parent.generated){
				if(parent.widget.getType().equals(GpWidgetConstants.TYPE_TAB)){
					return handle_tab_section(widget, widgets, activity, parent) + "\n";
				}
				if(parent.widget.getType().equals(GpWidgetConstants.TYPE_ACCORDION)){
					return handle_accordion_section(widget, widgets, activity, parent);
				}
			}
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_ACCORDION)){
			return handle_accordion(widget, widgets, activity) + "\n";
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_GRID)){
			return handle_grid(widget, widgets, activity) + "\n";
		}
		return "";
	}
	
	public String handle_grid(GpUiWidgetX widget, List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		System.out.println("Generating Grid/table");
		GpBootstrapGrid bootstrapGrid = new GpBootstrapGrid();
		bootstrapGrid.set_base_attributes(widget);
		Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
		String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
		if(code_to_add == null)
			code_to_add = "";
		code_to_add += "\n";
		code_to_add += "$scope.gridOptions = {\nenableRowSelection: true,\nenableSelectAll: false\n};\n";
		//handle result: TODO: this should be done in another way
		code_to_add += "function handle_search_result(search_result){\n";
		code_to_add += "$scope.gridOptions.data = search_result;\n";
		code_to_add += "}\n";
		//lets search the children
		code_to_add += "\n$scope.gridOptions.columnDefs = [\n";
		for(GpUiWidgetX widget_children : widgets){
			if(widget_children.getParentid() == widget.getId()){
				//grid columns
				code_to_add += "{ displayName: '"+ widget_children.getLabel() +"'";
				if(widget.getData_binding_context().equals(GpDataBindingConstants.GpPrimaryNoun)){
					code_to_add += ", name: '" + get_nounAttribute_name_bounded(widget_children, activity) + "'";
				}
				code_to_add += "},\n";
			}
		}
		code_to_add = code_to_add.substring(0,code_to_add.length()-2);
		code_to_add += "\n];";
		code_to_add_to_controllers.put(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add);
		Map<String,String> dependencies_to_add =  getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().getAngular_dependencies_map();
		String dependencies = dependencies_to_add.get(GpGenConstants.GpClientOS_WINDOWS + "-" + GpGenConstants.GpClientScreenType_desktop);
		if(dependencies == null)
			dependencies = ",'ui.grid','ui.grid.selection'";
		else
			dependencies += ",'ui.grid','ui.grid.selection'";
		dependencies_to_add.put(GpGenConstants.GpClientOS_WINDOWS + "-" + GpGenConstants.GpClientScreenType_desktop, dependencies);
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			bootstrapGrid.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		return bootstrapGrid.get_html_tag();
	}
	
	public String handle_accordion(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		System.out.println("Generating Accordion");
		count_sections = 0;
		GpBootstrapAccordion bootstrapAccordion = new GpBootstrapAccordion();
		bootstrapAccordion.set_base_attributes(widget);
		return bootstrapAccordion.get_html_tag() + get_html_of_children(widgets, bootstrapAccordion, activity) + bootstrapAccordion	.getEnd_tag() ;
	}
	public String handle_accordion_section(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity, GpBaseHtmlGenInfo parent) throws Exception{
		System.out.println("Generating Accordion Section");
		count_sections++;
		GpBootstrapAccordionSection bootstrapAccordionSection = new GpBootstrapAccordionSection(parent);
		bootstrapAccordionSection.set_base_attributes(widget);
		bootstrapAccordionSection.setPosition(count_sections);
		return bootstrapAccordionSection.get_html_tag() + get_html_of_children(widgets, bootstrapAccordionSection, activity) + bootstrapAccordionSection.getEnd_tag();
	}
	public String handle_tab_section(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity, GpBaseHtmlGenInfo parent) throws Exception{
		System.out.println("Generating Tab Section");
		count_sections++;
		GpBootstrapTabSection bootstrapTabSection = new GpBootstrapTabSection(parent);
		bootstrapTabSection.set_base_attributes(widget);
		bootstrapTabSection.setPosition(count_sections);
		return bootstrapTabSection.get_html_tag() + get_html_of_children(widgets, bootstrapTabSection, activity) + bootstrapTabSection.getEnd_tag();
	}
	
	public String handle_tab(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		System.out.println("Generating Tab");
		count_sections = 0;
		GpBootstrapTab bootstrapTab = new GpBootstrapTab();
		bootstrapTab.set_base_attributes(widget);
		List<GpUiWidgetX> children = new ArrayList<GpUiWidgetX>();
		for(GpUiWidgetX widget_child : widgets){
			if(widget.getId() == widget_child.getParentid()){
				children.add(widget_child);
			}
		}
		bootstrapTab.setChildren(children);
		return bootstrapTab.get_html_tag() + get_html_of_children(widgets, bootstrapTab, activity) + bootstrapTab.getEnd_tag();
	}
	
	public String handle_panel(GpUiWidgetX widget,List<GpUiWidgetX> widgets, GpActivity activity) throws Exception{
		System.out.println("Generating Panel");
		GpBootstrapPanel bootstrapPanel = new GpBootstrapPanel();
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			bootstrapPanel.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		bootstrapPanel.set_base_attributes(widget);
		return bootstrapPanel.get_html_tag() + get_html_of_children(widgets,bootstrapPanel,activity) + bootstrapPanel.getEnd_tag();
	}
	
	public String get_html_of_children(List<GpUiWidgetX> widgets, GpBaseHtmlGenInfo parent, GpActivity activity) throws Exception{
		String the_html = "";
		for(GpUiWidgetX widget : widgets){
			if(widget.getParentid() == parent.widget.getId()){
				if(widget.getIs_container().equals("true")){//is a container inside a container. Well i think is not supported on frontend
					the_html += handle_containers(widget, widgets,activity,parent);
				}
				else{
					the_html += handle_elements(widget, activity,parent) + "\n";
				}
			}
		}
		return the_html;
	}
	
	public String handle_elements(GpUiWidgetX widget, GpActivity activity, GpBaseHtmlGenInfo parent) throws Exception{
		if(widget.getType().equals(GpWidgetConstants.TYPE_BUTTON)){
			System.out.println("Generating button");
			GpBootstrapButton bootstrapButton = new GpBootstrapButton(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapButton.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapButton.set_base_attributes(widget);
			return bootstrapButton.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_INPUT)){
			System.out.println("Generating input");
			GpBootstrapInput bootstrapInput = new GpBootstrapInput(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapInput.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				bootstrapInput.setType(get_noun_attribute_binded(widget, activity),this);;
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				bootstrapInput.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			bootstrapInput.set_base_attributes(widget);
			return bootstrapInput.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_CHECKBOX)){
			System.out.println("Generating checkbox");
			GpBootstrapCheckBox bootstrapCheckBox = new GpBootstrapCheckBox(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapCheckBox.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				bootstrapCheckBox.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			bootstrapCheckBox.set_base_attributes(widget);
			return bootstrapCheckBox.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_LABEL)){
			System.out.println("Generating label");
			GpBootstrapLabel bootstrapLabel = new GpBootstrapLabel(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapLabel.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				bootstrapLabel.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			bootstrapLabel.set_base_attributes(widget);
			return bootstrapLabel.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_RADIO)){	
			System.out.println("Generating radio");
			GpBootstrapRadio bootstrapRadio = new GpBootstrapRadio(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapRadio.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapRadio.set_base_attributes(widget);
			return bootstrapRadio.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_DATEPICKER)){
			System.out.println("Generating datepicker");
			GpBootstrapDatePicker bootstrapDatePicker = new GpBootstrapDatePicker(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapDatePicker.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				bootstrapDatePicker.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
			String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
			if(code_to_add == null)
				code_to_add = "";
			code_to_add += "\n";
			code_to_add += "$('#"+ widget.getName() +"').datepicker({format: 'yyyy-mm-dd'});";
			code_to_add_to_controllers.put(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add);
			bootstrapDatePicker.set_base_attributes(widget);
			return bootstrapDatePicker.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_VerticalRule)){
			System.out.println("Generating Vertical Rule");
			GpBootstrapVRule bootstrapVRule = new GpBootstrapVRule(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapVRule.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapVRule.set_base_attributes(widget);
			return bootstrapVRule.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_HorizontalRule)){
			System.out.println("Generating Horizontal Rule");
			GpBootstrapHRule bootstrapHRule = new GpBootstrapHRule(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapHRule.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapHRule.set_base_attributes(widget);
			return bootstrapHRule.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_SELECT)){
			System.out.println("Generating Dropdown List");
			GpBootstrapDropdownList bootstrapDropdownList = new GpBootstrapDropdownList(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapDropdownList.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				if(widget.getData_binding_context().equals(GpDataBindingConstants.GpSecondaryNoun)){
					long noun_id = widget.getNoun_id();
					List<GpNoun> nouns = getHtml_gen_support().getThe_worker().the_project.getProject_nouns();
					long activity_id = -1;
					//System.out.println("Nouns size " + nouns.size());
					for(GpNoun noun: nouns){
						if(noun.getId() == noun_id){
							activity_id = noun.getDefault_activity_id();
							//System.out.println("** " + noun_id);
							//System.out.println("** " + activity_id);
							break;
						}
					}
					if(activity_id != -1){
						ArrayList<GpActivity> the_activities = getHtml_gen_support().getThe_worker().getThe_activities();
						for(GpActivity sec_activity : the_activities){
							if(sec_activity.getId() == activity_id){
								String noun_attribute = get_nounAttribute_bounded(widget,sec_activity);
								bootstrapDropdownList.setNoun_attribute(noun_attribute);
								bootstrapDropdownList.handle_secondary_noun_binding(getHtml_gen_support().getThe_worker().the_project, this.base_configs, getHtml_gen_support(), sec_activity,activity);
								break;
							}
						}
					}
				}
			}
			bootstrapDropdownList.set_base_attributes(widget);
			return bootstrapDropdownList.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_IMAGE)){
			System.out.println("Generating Image");
			GpBootstrapImage bootstrapImage = new GpBootstrapImage(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapImage.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				bootstrapImage.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			bootstrapImage.set_base_attributes(widget);
			return bootstrapImage.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_LINK)){
			System.out.println("Generating Link");
			GpBootstrapLink bootstrapLink = new GpBootstrapLink(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapLink.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapLink.set_base_attributes(widget);
			return bootstrapLink.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_EDITOR)){
			System.out.println("Generating Rich Text Editor");
			String code_to_add = "app.directive('ckEditor', function () {"
					+ "\t" + "return {" + "\n"
					+ "\t\t" + "require: '?ngModel'," + "\n"
					+ "\t\t" + "compile: function () {" + "\n"
					+ "\t\t\t" + "return function ($scope, elem, attr, ngModel) {" + "\n"
					+ "\t\t\t\t" + "var ck = CKEDITOR.replace(elem[0], {" + "\n"
					+ "\t\t\t\t\t" + "toolbar: 'Basic'," + "\n"
					+ "\t\t\t\t\t" + "uiColor: '#fafafa'" + "\n"
					+ "\t\t\t\t" + "});" + "\n"
					+ "\t\t\t\t" + "if (!ngModel) return;" + "\n"
					+ "\t\t\t\t" + "ck.on('instanceReady', function () {" + "\n"
					+ "\t\t\t\t\t" + "ck.setData(ngModel.$viewValue);" + "\n"
					+ "\t\t\t\t" + "});" + "\n"
					+ "\t\t\t\t" + "function updateModel() {" + "\n"
					+ "\t\t\t\t\t" + "$scope.$apply(function () {" + "\n"
					+ "\t\t\t\t\t\t" + "ngModel.$setViewValue(ck.getData());" + "\n"
					+ "\t\t\t\t\t" + "});" + "\n"
					+ "\t\t\t\t" + "}" + "\n"
					+ "\t\t\t\t" + "ck.on('change', updateModel);" + "\n"
					+ "\t\t\t\t" + "ck.on('key', updateModel);" + "\n"
					+ "\t\t\t\t" + "ck.on('dataReady', updateModel);" + "\n"
					+ "\t\t\t\t" + "ngModel.$render = function (value) {" + "\n"
					+ "\t\t\t\t\t" + "ck.setData(ngModel.$viewValue);" + "\n"
					+ "\t\t\t\t" + "};" + "\n"
					+ "\t\t\t" + "};" + "\n"
					+ "\t\t" + "}" + "\n"
					+ "\t" + "};" + "\n"
					+ "" + "});" + "\n";
			getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add, GpGenConstants.GpClientOS_WINDOWS + "_" + GpGenConstants.GpClientScreenType_desktop);
			GpBootstrapRichTextEditor bootstrapRichTextEditor = new GpBootstrapRichTextEditor(parent);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				bootstrapRichTextEditor.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			bootstrapRichTextEditor.set_base_attributes(widget);
			return bootstrapRichTextEditor.get_html_tag();
		}
		return "";
	}
	
	public boolean isThereAVerb(List<GpUiWidgetX> widgets){
		for (GpUiWidgetX widget : widgets){
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				return true;
			}
		}
		return false;
	}
	
	public GpNounAttribute get_noun_attribute_binded(GpUiWidgetX widget, GpActivity activity){
		if(widget.getData_binding_context().equals(GpDataBindingConstants.GpPrimaryNoun)){
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun().getNounattributes();
			for(GpNounAttribute attr : attributes){
				if(attr.getId() == widget.getNoun_attribute_id()){
					return attr;
				}
			}
		}
		//search for secondary noun??
		return null;
	}
	
	public String get_nounAttribute_bounded(GpUiWidgetX widget, GpActivity activity){
		if(widget.getData_binding_context().equals(GpDataBindingConstants.GpPrimaryNoun)){
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun().getNounattributes();
			for(GpNounAttribute attr : attributes){
				if(attr.getId() == widget.getNoun_attribute_id()){
					return activity.getPrimary_noun().getName() + "." + attr.getName().toLowerCase();
				}
			}
		}
		if(widget.getData_binding_context().equals(GpDataBindingConstants.GpSecondaryNoun)){
			//System.out.println("secondary noun " + activity.getPrimary_noun().getName());
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun().getNounattributes();
			for(GpNounAttribute attr : attributes){
				if(attr.getId() == widget.getNoun_attribute_id()){
					return activity.getPrimary_noun().getName() + "." + attr.getName().toLowerCase();
				}
			}
		}
		return "not_found";
	}
	
	public String get_nounAttribute_name_bounded(GpUiWidgetX widget, GpActivity activity){
		if(widget.getData_binding_context().equals(GpDataBindingConstants.GpPrimaryNoun)){
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun().getNounattributes();
			for(GpNounAttribute attr : attributes){
				if(attr.getId() == widget.getNoun_attribute_id()){
					return attr.getName().toLowerCase();
				}
			}
		}
		return "not_found";
	}
	
	public String get_event_and_function_bounded(GpUiWidgetX widget, GpActivity activity) throws Exception{
		String function, event;
		String[] split = widget.getEvent_verb_combo().split(";");
		String[] split2 = split[0].split(",");
		//event
		switch (split2[0]) {
			case GpEventVerbConstants.CLICK:
				event = "data-ng-click";
				break;
			default:
				event = "NotImplemented";
				break;
		}
		//function or verb
		switch (split2[1]) {
			case GpEventVerbConstants.CREATE:
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getCreate_handler().getFunction_name() + "()";
				break;
			case GpEventVerbConstants.SEARCH:
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getSearch_handler().getFunction_name() + "()";
				//we should say �Where to put the result?
				break;
			case GpEventVerbConstants.SEARCH_FOR_DETAIL:
				if(widget.getType().equals(GpWidgetConstants.TYPE_GRID)){
					set_factory_id_for_primary_noun(activity);
					//code to add to controller
					Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
					String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
					if(code_to_add == null)
						code_to_add = "";
					code_to_add += "\n";
					//get search result
					//TODO: gridOptions should be the name of the grid -> verb target?
					code_to_add += "$scope.gridOptions.onRegisterApi = function(gridApi){\n";
					code_to_add += "gridApi.selection.on.rowSelectionChanged($scope, function (row) {\n";
					code_to_add += "\n" + activity.getPrimary_noun().getName() + "Id.setId(row.entity['Id']);"; 
					//This is where i should get the target screen
					code_to_add += "\n$location.path('/Update-en');";
					code_to_add += "\n});";
					code_to_add += "\n};";
					code_to_add_to_controllers.put(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add);
				}
				function = "NotImplemented for SearchForDetail";
				break;
			case GpEventVerbConstants.DELETE:
				set_factory_id_for_primary_noun(activity);
				check_id_from_search_for_detail(activity);
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getDelete_handler().getFunction_name() + "()";
				break;
			case GpEventVerbConstants.UPDATE:
				set_factory_id_for_primary_noun(activity);
				check_id_from_search_for_detail(activity);
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getUpdate_handler().getFunction_name() + "()";
				break;
			case GpEventVerbConstants.GpComponentVerb:
				function = this.handle_function_name_for_component_verb(activity,widget);
				break;
			default:
				function = "NotImplemented";
				break;
		}
		return event + ":" + function;
	}
	
	private String handle_function_name_for_component_verb(GpActivity activity, GpUiWidgetX widget) throws Exception {
		String extra_verb_info = widget.getExtra_verb_info();
		if(extra_verb_info != null && !extra_verb_info.isEmpty()){
			JSONObject json_widget_extra_verb_info = new JSONObject(extra_verb_info);
			if(!json_widget_extra_verb_info.isNull("verb_id")){
				long verb_id = json_widget_extra_verb_info.getLong("verb_id");
				GpVerb verb = this.verbs_dao.find_by_id(verb_id);
				String verb_actual_info = verb.getActual_information();
				JSONObject json_verb_actual_info = new JSONObject(verb_actual_info);
				String verb_name = json_verb_actual_info.getString(GCDJsonKeys.VERB_NAME);
				String function_call = verb_name + "(";
				//get the map for parameters?
				JSONArray verb_map_parameters = json_widget_extra_verb_info.getJSONArray("map_parameters");
				Map<Integer, String> map_parameters_with_order = new HashMap<>();
				for(int i=0;i<verb_map_parameters.length();i++){
					JSONObject verb_map_parameter = verb_map_parameters.getJSONObject(i);
					long noun_attr_id = verb_map_parameter.getLong("noun_attr_id");
					String verb_parameter_name = verb_map_parameter.getString("verb_parameter_name");
					int parameter_order = verb_map_parameter.getInt("parameter_order");
					if(activity.getPrimary_noun() != null){
						List<GpNounAttribute> noun_attrs = activity.getPrimary_noun().getNounattributes();
						for(GpNounAttribute noun_attr : noun_attrs){
							if(noun_attr.getId() == noun_attr_id){
								//System.out.println("function_call " + function_call);
								map_parameters_with_order.put(parameter_order, noun_attr.getName().toLowerCase());
								break;
							}
						}
						
					}
				}
				for(int i=0;i<verb_map_parameters.length();i++){
					String noun_attr_parameter_name = map_parameters_with_order.get(i+1);
					function_call += activity.getPrimary_noun().getName() + "." + noun_attr_parameter_name + ",";
				}
				if(function_call.endsWith(","))
					function_call = function_call.substring(0, function_call.length()-1);
				function_call += ")";
				System.out.println("function_call " + function_call);
				return function_call;
			}
			
		}
		return "not_found";
	}

	public void add_file_read_directive(){
		String code_to_add_to_appjs = "";
		code_to_add_to_appjs += "app.directive(\"fileread\", [function () {"
				+ "\n\t" + "return {"
				+ "\n\t\t" + "scope: {"
				+ "\n\t\t\t" + "fileread: \"=\""
				+ "\n\t\t" + "},"
				+ "\n\t\t" + "link: function (scope, element, attributes) {"
				+ "\n\t\t\t" + "element.bind(\"change\", function (changeEvent) {"
				+ "\n\t\t\t\t" + "var reader = new FileReader();"
				+ "\n\t\t\t\t" + "reader.onload = function (loadEvent) {"
				+ "\n\t\t\t\t\t" + "scope.$apply(function () {"
				+ "\n\t\t\t\t\t\t" + "scope.fileread = loadEvent.target.result;"
				+ "\n\t\t\t\t\t" + "});"
				+ "\n\t\t\t\t" + "}"
				+ "\n\t\t\t\t" + "reader.readAsDataURL(changeEvent.target.files[0]);"
				+ "\n\t\t\t" + "});"
				+ "\n\t\t" + "}"
				+ "\n\t" + "}"
				+ "\n" + "}]);";
		getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_WINDOWS + "_" + GpGenConstants.GpClientScreenType_desktop);
	}
	
	private void set_factory_id_for_primary_noun(GpActivity activity){
		if(!factory_for_primary_noun_generated){
			factory_for_primary_noun_generated = true;
			//code to add to appjs
			String code_to_add_to_appjs = "app.factory('" + activity.getPrimary_noun().getName() + "Id', function () {";
			code_to_add_to_appjs += "\n\tvar id = '';";
			code_to_add_to_appjs += "\n\treturn {";
			code_to_add_to_appjs += "\n\t\tsetId: function (id){";
			code_to_add_to_appjs += "\n\t\t\tthis.id = id;";
			code_to_add_to_appjs += "\n\t\t},";
			code_to_add_to_appjs += "\n\t\tgetId: function (){";
			code_to_add_to_appjs += "\n\t\t\treturn this.id;";
			code_to_add_to_appjs += "\n\t\t}";
			code_to_add_to_appjs += "\n\t}";
			code_to_add_to_appjs += "\n});";
			getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_WINDOWS + "_" + GpGenConstants.GpClientScreenType_desktop);
			//add parameter to controller
			getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_desktop("," + activity.getPrimary_noun().getName() + "Id");
		}
	}
	public void check_id_from_search_for_detail(GpActivity activity){
		Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
		String code_to_add = code_to_add_to_controllers.get(activity.getPrimary_noun().getName() + "Id" + "-" + GpGenConstants.GpClientOS_WINDOWS);
		if(code_to_add == null){
			code_to_add = "var id = " + activity.getPrimary_noun().getName() + "Id.getId();\n";
			code_to_add += "if (id) {\n";
			code_to_add += "console.log(id);\n";
			code_to_add += "$scope.search_for_update(id);\n";
			code_to_add += "} else {\n";
			code_to_add += "$('#myModal').modal('toggle');\n";
			code_to_add += "$('#myModal').modal('show');\n";
			code_to_add += "$scope.searchFor"+ activity.getPrimary_noun().getName() +" = function () {\n";
			code_to_add += "if ($scope.idFor"+ activity.getPrimary_noun().getName() +") {\n";
			code_to_add += "$('#myModal').modal('hide');\n";
			code_to_add += "id = $scope.idFor"+ activity.getPrimary_noun().getName() +";\n";
			code_to_add += "console.log(id);\n";
			code_to_add += "$scope.search_for_update(id);\n";
			code_to_add += "}	else {\n";
			code_to_add += "alert('You should enter ID');\n";
			code_to_add += "}\n";
			code_to_add += "};\n";
			code_to_add += "}\n";
			code_to_add_to_controllers.put(activity.getPrimary_noun().getName() + "Id" + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add);
			String code_to_add_to_controller = code_to_add_to_controllers.get(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS);
			if(code_to_add_to_controller == null)
				code_to_add_to_controller = "";
			code_to_add_to_controller += "\n";
			code_to_add_to_controller += code_to_add;
			code_to_add_to_controllers.put(activity.getName() + "-" + GpGenConstants.GpClientOS_WINDOWS, code_to_add_to_controller);
		}
		if(modal_for_getNounId == null){
			modal_for_getNounId = "\n<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" data-backdrop=\"static\" data-keyboard=\"false\">\n";
			modal_for_getNounId += "\n<div class=\"modal-dialog\" role=\"document\">";
			modal_for_getNounId += "\n<div class=\"modal-content\">";
			modal_for_getNounId += "\n<div class=\"modal-header\">";
			modal_for_getNounId += "\n<h4 class=\"modal-title\" id=\"myModalLabel\">You should enter "+ activity.getPrimary_noun().getName() +" Id</h4>";
			modal_for_getNounId += "\n</div>";
			modal_for_getNounId += "\n<div class=\"modal-body\">";
			modal_for_getNounId += "\n<label> ID";
			modal_for_getNounId += "\n<input type=\"text\" placeholder=\""+ activity.getPrimary_noun().getName() +" ID\" data-ng-model=\"idFor"+ activity.getPrimary_noun().getName() +"\"></input>";
			modal_for_getNounId += "\n</label>";
			modal_for_getNounId += "\n</div>";
			modal_for_getNounId += "\n<div class=\"modal-footer\">";
			modal_for_getNounId += "\n<button type=\"button\" data-ng-click=\"searchFor"+ activity.getPrimary_noun().getName() +"()\" class=\"btn btn-primary\">Search "+ activity.getPrimary_noun().getName() +"</button>";
			modal_for_getNounId += "\n</div>";
			modal_for_getNounId += "\n</div>";
			modal_for_getNounId += "\n</div>";
			modal_for_getNounId += "\n</div>";
		}
	}
	
	
}

