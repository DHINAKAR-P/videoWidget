package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.constants.GpDataBindingConstants;
import com.npb.gp.constants.GpVerbBindingConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpEventVerbConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpWidgetConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenManagerBase;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.GpAngularJsHtmlGenSupport;
import com.sun.glass.ui.Screen;
/**
 *
 * @author Dan Castillo
 * Date Created: 05/20/2015</br>
 * @since .2</p>
 *
 * Contains the coordination logic to generate the HTML and AngularJS template</br>
 * for an Ionic enabled mobile page</p>
 *
 *
 */
@Component("GpIonicGenManager")
public class GpIonicGenManager extends GpAngularJsHtmlGenManagerBase {
	String client_device_type_os;
	GpAngularJsHtmlGenSupport html_gen_support;
	String client_device_type;
	private boolean factory_for_primary_noun_generated = false;
	public GpAngularJsHtmlGenSupport getHtml_gen_support() {
		return html_gen_support;
	}
	@Resource(name="GpAngularJsHtmlGenSupport")
	public void setHtml_gen_support(GpAngularJsHtmlGenSupport html_gen_support) {
		this.html_gen_support = html_gen_support;
	}
	@Override
	public HashMap<String,String> generate_html_page(List<GpUiWidgetX> sorted_widgets,
			GpScreenX screen, GpActivity activity) throws Exception{
		client_device_type = screen.getClient_device_type();
		client_device_type_os = screen.getClient_device_type_os_name();
		//this is where you should start the generation of the page
		//OJO - YOU don't need the HEAD becuase these are AngularJS HTML templates - the head is in the Index.html
		//1. after you deal with the head of the html page then you have to start the body
		//2. for the body where do you start the TAB start for the DIVS? AND COMPONENENTS
		HashMap<String, String> map  = new HashMap<String, String>();
		String body_string = "";
		//Is there a verb? is possible to have more than 1 form? working with just one for now
		boolean isThereAVerb = isThereAVerb(sorted_widgets);
		body_string += "<ion-content>\n";
		if(isThereAVerb){
			body_string += "<form class=\"content content-field\" >\n";
		}
		body_string += get_html(sorted_widgets, activity);
		if(isThereAVerb){
			body_string += "</form>";
		}
		body_string += "</ion-content>";
		map.put("body", body_string);
		return map;
	}

	public String get_html(List<GpUiWidgetX> widgets, GpActivity activity){
		String the_html = "";
		for(GpUiWidgetX widget : widgets){
			if(widget.getIs_container().equals("true")){
				the_html += handle_containers(widget, widgets,activity,null);
			}
			if(widget.getParentid() == widget.getScreen_id()){//Elements outside a container
				the_html += handle_elements(widget, activity, null) + "\n";
			}
		}
		return the_html;
	}

	public String handle_containers(GpUiWidgetX widget, List<GpUiWidgetX> widgets, GpActivity activity, GpBaseHtmlGenInfo parent){
		if (widget.getType().equals(GpWidgetConstants.TYPE_LIST)) {
			return handle_list(widget, widgets, activity) + "\n";
		}
		if (widget.getType().equals(GpWidgetConstants.TYPE_CARD)) {
			return handle_card(widget, widgets, activity) + "\n";
		}
		if (widget.getType().equals(GpWidgetConstants.TYPE_TAB)) {
			return handle_tab(widget, widgets, activity) + "\n";
		}
		if (widget.getType().equals(GpWidgetConstants.TYPE_SECTION)) {//for now is the only one existing section
			if(parent != null && parent.generated){
				if(parent.widget.getType().equals(GpWidgetConstants.TYPE_TAB)){
					return handle_tab_section(widget, widgets, activity, parent) + "\n";
				}
			}
		}
		return "";
	}


	private String handle_tab_section(GpUiWidgetX widget,
			List<GpUiWidgetX> widgets, GpActivity activity, GpBaseHtmlGenInfo parent) {
		GpIonicTabSection ionicTabSection = new GpIonicTabSection(parent, client_device_type);
		ionicTabSection.set_base_attributes(widget);
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			ionicTabSection.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		return ionicTabSection.get_html_tag() + get_html_of_children(widgets,ionicTabSection,activity) + ionicTabSection.getEnd_tag();
	}
	private String handle_tab(GpUiWidgetX widget, List<GpUiWidgetX> widgets,
			GpActivity activity) {
		GpIonicTab ionicTab = new GpIonicTab(client_device_type);
		ionicTab.set_base_attributes(widget);
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			ionicTab.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		return ionicTab.get_html_tag() + get_html_of_children(widgets,ionicTab,activity) + ionicTab.getEnd_tag();
	}
	private String handle_card(GpUiWidgetX widget, List<GpUiWidgetX> widgets,
			GpActivity activity) {
		GpIonicCard ionicCard = new GpIonicCard(client_device_type);
		ionicCard.set_base_attributes(widget);
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			ionicCard.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		return ionicCard.get_html_tag() + get_html_of_children(widgets,ionicCard,activity) + ionicCard.getEnd_tag();
	}
	public String handle_list(GpUiWidgetX widget, List<GpUiWidgetX> widgets, GpActivity activity) {
		System.out.println("Generating List");
		Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
		String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
		if(code_to_add == null)
			code_to_add = "";
		code_to_add += "\n";
		code_to_add += "function handle_search_result(search_result){\n";
		code_to_add += "\t$scope.items = search_result;\n";
		code_to_add += "}\n";
		code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
		GpIonicList ionicList = new GpIonicList(client_device_type);
		ionicList.set_base_attributes(widget);
		if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
			ionicList.set_event_and_function(get_event_and_function_bounded(widget, activity));
		}
		if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
			String noun_attribute = get_nounAttribute_name_bounded(widget,activity);
			ionicList.setNoun_attribute(noun_attribute);
		}
		//do we really need the rows?
		//return ionicList.get_html_tag() + get_html_of_children(widgets,ionicList,activity) + ionicList.getEnd_tag();
		return ionicList.get_html_tag() + ionicList.getEnd_tag();
	}

	public String get_html_of_children(List<GpUiWidgetX> widgets, GpBaseHtmlGenInfo parent, GpActivity activity){
		String the_html = "";
		for(GpUiWidgetX widget : widgets){
			if(widget.getParentid() == parent.widget.getId()){
				if(widget.getIs_container().equals("true")){//is a container inside a container. Well i think is not supported on frontend
					the_html += handle_containers(widget, widgets,activity,parent);
				}
				else{
					the_html += handle_elements(widget, activity, parent) + "\n";
				}
			}
		}
		return the_html;
	}

	public String handle_elements(GpUiWidgetX widget, GpActivity activity, GpBaseHtmlGenInfo parent){
		System.err.println("--------------------------------------widegt type---------------------------"+widget.getType());
		if(widget.getType().equals(GpWidgetConstants.TYPE_BUTTON)){
			System.out.println("Generating button");
			GpIonicButton ionicButton = new GpIonicButton(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicButton.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicButton.set_base_attributes(widget);
			return ionicButton.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_INPUT)){
			System.out.println("Generating input");
			GpIonicInput ionicInput = new GpIonicInput(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicInput.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				ionicInput.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			ionicInput.set_base_attributes(widget);
			return ionicInput.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_CHECKBOX)){
			System.out.println("Generating checkbox");
			GpIonicCheckBox ionicCheckBox = new GpIonicCheckBox(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicCheckBox.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				ionicCheckBox.setNoun_attribute(noun_attribute);//TODO: get noun and attribute
			}
			ionicCheckBox.set_base_attributes(widget);
			return ionicCheckBox.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_LABEL)){
			System.out.println("Generating label");
			GpIonicLabel ionicLabel = new GpIonicLabel(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicLabel.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicLabel.set_base_attributes(widget);
			return ionicLabel.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_RADIO)){
			System.out.println("Generating radio");
			GpIonicRadio ionicRadio = new GpIonicRadio(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicRadio.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicRadio.set_base_attributes(widget);
			return ionicRadio.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_DATEPICKER)){
			System.out.println("Generating datepicker");
			Map<String,String> dependencies_to_add =  getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().getAngular_dependencies_map();
			String dependencies = dependencies_to_add.get(client_device_type_os + "-" + client_device_type);
			if(dependencies == null)
				dependencies = ",'ionic-datepicker'";
			else
				dependencies += ",'ionic-datepicker'";
			dependencies_to_add.put(client_device_type_os + "-" + client_device_type, dependencies);
			Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
			String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
			if(code_to_add == null)
				code_to_add = "";
			code_to_add += "\n";
			code_to_add += "$scope.datepickerObject_"+ widget.getName() +" = {\n"
							+ "\t" + "titleLabel: 'Pick date'," + "\n"
							+ "\t" + "inputDate: new Date()," + "\n"
							+ "\t" + "mondayFirst: true," + "\n"
							+ "\t" + "showTodayButton: 'true'," + "\n"
							+ "\t" + "callback: function (val) {" + "\n"
							+ "\t\t" + "datePickerCallback(val);" + "\n"
							+ "\t" + "}" + "\n"
							+ "" + "};" + "\n";
			code_to_add += "var datePickerCallback = function (val) {" + "\n"
							+ "\t" + "if (typeof(val) === 'undefined') {" + "\n"
							+ "\t\t" + "console.log('No date selected');" + "\n"
							+ "\t" + "} else {" + "\n"
							+ "\t\t" + "console.log('Selected date is : ', val)" + "\n"
							+ "\t\t" + "$scope.datepickerObject_"+ widget.getName() +".inputDate = val;" + "\n";
			if(!widget.getData_binding_context().equals(GpDataBindingConstants.GpNotBound)){
				String noun_attribute = get_nounAttribute_bounded(widget,activity);
				code_to_add += "\t\t" + "$scope."+ noun_attribute +" = val;" + "\n";
			}
			code_to_add +=	"\t" + "}" + "\n"
							+ "" + "};" + "\n";
			code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
			GpIonicDatePicker ionicDatePicker = new GpIonicDatePicker(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicDatePicker.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}

			ionicDatePicker.set_base_attributes(widget);
			return ionicDatePicker.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_SELECT)){
			System.out.println("Generating Dropdown List");
			GpIonicDropdownList ionicDropdownList = new GpIonicDropdownList(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicDropdownList.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicDropdownList.set_base_attributes(widget);
			return ionicDropdownList.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_TOGGLE)){
			System.out.println("Generating Dropdown List");
			GpIonicToogle gpIonicToogle = new GpIonicToogle(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				gpIonicToogle.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			gpIonicToogle.set_base_attributes(widget);
			return gpIonicToogle.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_RANGE)){
			System.out.println("Generating Dropdown List");
			GpIonicRange gpIonicRange = new GpIonicRange(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				gpIonicRange.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			gpIonicRange.set_base_attributes(widget);
			return gpIonicRange.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_LIST_ITEM)){
			System.out.println("Generating List Item");
			GpIonicListItem gpIonicListItem = new GpIonicListItem(parent, client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				gpIonicListItem.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			gpIonicListItem.set_base_attributes(widget);
			return gpIonicListItem.get_html_tag() + gpIonicListItem.getEnd_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_IMAGE)){
			System.out.println("Generating Image");
			GpIonicImage ionicImage = new GpIonicImage(parent,client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicImage.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicImage.set_base_attributes(widget);
			return ionicImage.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_HEADER)){
			System.out.println("Generating Image");
			GpIonicHeader ionicHeader = new GpIonicHeader(client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicHeader.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicHeader.set_base_attributes(widget);
			return ionicHeader.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_FOOTER)){
			System.out.println("Generating Image");
			GpIonicFooter ionicFooter = new GpIonicFooter(client_device_type);
			if(widget.getVerb_binding_context().equals(GpVerbBindingConstants.GpBound)){
				ionicFooter.set_event_and_function(get_event_and_function_bounded(widget, activity));
			}
			ionicFooter.set_base_attributes(widget);
			return ionicFooter.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_CAMERA)){
			this.html_gen_support.getThe_worker().get_generation_service().getActivity_service().getPostGenerationService().getThe_build_service().getThe_build_ionic_apps_worker().add_plugin_cordova("cordova-plugin-camera");
			this.html_gen_support.getThe_worker().get_generation_service().getActivity_service().getPostGenerationService().getThe_build_service().getThe_build_ionic_apps_worker().add_plugin_cordova("cordova-plugin-file-transfer");
			Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
			String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
			if(code_to_add == null)
				code_to_add = "";
			code_to_add += "\n";
			code_to_add += "$scope.takePhoto = function () {"
					+ "\n\tdocument.addEventListener('deviceready', function () {"
					+ "\n\t\tvar options = {"
					+ "\n\t\t\tquality: 100,"
					+ "\n\t\t\tdestinationType: Camera.DestinationType.DATA_URL,"
					+ "\n\t\t\tsourceType: Camera.PictureSourceType.CAMERA,"
					+ "\n\t\t\tencodingType: Camera.EncodingType.JPEG,"
					+ "\n\t\t\tsaveToPhotoAlbum: false,"
					+ "\n\t\t\tcorrectOrientation: true"
					+ "\n\t\t};"
					+ "\n\t\t$cordovaCamera.getPicture(options).then(function (res) {"
					+ "\n\t\t\tvar src = 'data:image/jpeg;base64,' + res;"
					+ "\n\t\t\tconsole.log('src_length: ', src.length);"
					+ "\n\t\t}, function (err) {"
					+ "\n\t\t\tconsole.error(err);"
					+ "\n\t\t});"
					+ "\n\t}, false);"
					+ "\n};";
			//code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
			if(client_device_type_os.equals(GpGenConstants.GpClientOS_ANDROID) && client_device_type.equals(GpGenConstants.GpClientScreenType_phone))
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaCamera");
			//getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaSQLite");
			getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaFileTransfer");
			if(client_device_type_os.equals(GpGenConstants.GpClientOS_IOS) && client_device_type.equals(GpGenConstants.GpClientScreenType_phone))
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone(",$cordovaCamera,$cordovaFileTransfer");

			System.out.println("Generating Camera widget");
			GpIonicCamera ionicCamera = new GpIonicCamera(parent,client_device_type);
			ionicCamera.set_base_attributes(widget);
			return ionicCamera.get_html_tag();
		}
		if(widget.getType().equals(GpWidgetConstants.TYPE_RECORDER)){
			this.html_gen_support.getThe_worker().get_generation_service().getActivity_service().getPostGenerationService().getThe_build_service().getThe_build_ionic_apps_worker().add_plugin_cordova("cordova-plugin-camera");
			this.html_gen_support.getThe_worker().get_generation_service().getActivity_service().getPostGenerationService().getThe_build_service().getThe_build_ionic_apps_worker().add_plugin_cordova("cordova-plugin-media-capture");
			this.html_gen_support.getThe_worker().get_generation_service().getActivity_service().getPostGenerationService().getThe_build_service().getThe_build_ionic_apps_worker().add_plugin_cordova("cordova-plugin-file-transfer");
			Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
			String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
			if(code_to_add == null)
				code_to_add = "";
			code_to_add += "\n";
			code_to_add += "if u need code for recorder please add here";
					 
			//code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
			if(client_device_type_os.equals(GpGenConstants.GpClientOS_ANDROID) && client_device_type.equals(GpGenConstants.GpClientScreenType_phone))
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaCamera");
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaCapture");
			//$cordovaCapture
			//getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaSQLite");
			getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$cordovaFileTransfer");
			if(client_device_type_os.equals(GpGenConstants.GpClientOS_IOS) && client_device_type.equals(GpGenConstants.GpClientScreenType_phone))
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone(",$cordovaCamera");
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone(",$cordovaCapture");
				getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone(",$cordovaFileTransfer");
			System.out.println("Generating recorder widget");
			GpIonicRecorder ionicRecorder = new GpIonicRecorder(parent,client_device_type);
			ionicRecorder.set_base_attributes(widget);
			return ionicRecorder.get_html_tag();
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

	public String get_nounAttribute_bounded(GpUiWidgetX widget, GpActivity activity){
		if(widget.getData_binding_context().equals(GpDataBindingConstants.GpPrimaryNoun)){
			ArrayList<GpNounAttribute> attributes = activity.getPrimary_noun().getNounattributes();
			for(GpNounAttribute attr : attributes){
				if(attr.getId() == widget.getNoun_attribute_id()){
					return activity.getPrimary_noun().getName() + "." + attr.getName().toLowerCase();
				}
			}
		}
		//TODO: secondary nouns
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
		//TODO: secondary nouns
		return "not_found";
	}

	public String get_event_and_function_bounded(GpUiWidgetX widget, GpActivity activity){
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
		String callName="";
		switch (split2[1]) {
			case GpEventVerbConstants.CREATE:
				/*function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getCreate_handler().getFunction_name();
				break;*/
				for(GpNounAttribute nounattribute:activity.getPrimary_noun().getNounattributes())
				{
				System.out.println("----nounattriutes.getSubtype()---"+nounattribute.getSubtype());
				if(nounattribute.getSubtype().equals("Picture")){
					callName = "pushToCloud";
					//callName=html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getTakePhoto_handler().getFunction_name();
					System.out.println("------pushToCloud------in Gpcreate"+nounattribute.getSubtype());
					//break;
						}else if (nounattribute.getSubtype().equals("Video")){
							callName = "pushVideoToCloud";
							//callName=html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getVideo_recorder_handler().getFunction_name();
					System.out.println("--------------------------********function********************-----------------------------"+callName+"-----"+nounattribute.getSubtype());
					//break;
						}else{
							callName = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getCreate_handler().getFunction_name();
						}
					}
				function = callName;
				break;
			case GpEventVerbConstants.SEARCH:
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getSearch_handler().getFunction_name();
				break;
			case GpEventVerbConstants.UPDATE:
				set_factory_id_for_primary_noun(activity);
				check_id_from_search_for_detail(activity);
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getUpdate_handler().getFunction_name();
				break;
			case GpEventVerbConstants.DELETE:
				set_factory_id_for_primary_noun(activity);
				check_id_from_search_for_detail(activity);
				function = html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getDelete_handler().getFunction_name();
				break;
			case GpEventVerbConstants.GET_ALL_VALUES:
				function= html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getGet_all_values_handler().getFunction_name();
				break;
		/*	case GpEventVerbConstants.CAMERA:
				function= html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getTakePhoto_handler().getFunction_name();
				break;
			case GpEventVerbConstants.VIDEO_RECORDER:
				function= html_gen_support.getThe_worker().get_generation_service().getController_worker().getGen_support().getVideo_recorder_handler().getFunction_name();
				break;*/
			case GpEventVerbConstants.SEARCH_FOR_DETAIL:
				if(widget.getType().equals(GpWidgetConstants.TYPE_LIST)){
					set_factory_id_for_primary_noun(activity);
					//code to add to controller
					Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
					String code_to_add = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
					if(code_to_add == null)
						code_to_add = "";
					code_to_add += "\n";
					//get search result
					//TODO: search_result should be the id of the list
					code_to_add += "$(\"#" + widget.getName() + "\").on('click li', function(event) {";
					code_to_add += "\n\tvar $target = $(event.target),";
					code_to_add += "\n\titemId = $target.data('id');";
					code_to_add += "\n\t"+ activity.getPrimary_noun().getName() +"Id.setId(itemId);";
					code_to_add += "\n\t$rootScope.$apply(function() {";
					code_to_add += "\n\t\t$location.path('/app/Update-en');";
					code_to_add += "\n\t});";
					code_to_add += "\n\tconsole.log(\"ID is: \", itemId);";
					code_to_add += "\n});";
					code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
				}
				function = "NotImplemented for SearchForDetail";
				break;
			default:
				function = "NotImplemented";
				break;
		}
		return event + ":" + function;
	}

	private void set_factory_id_for_primary_noun(GpActivity activity){
		if(!factory_for_primary_noun_generated){
			factory_for_primary_noun_generated = true;
			//code to add to appjs
			String code_to_add_to_appjs = ".factory('" + activity.getPrimary_noun().getName() + "Id', function () {";
			code_to_add_to_appjs += "\n\tvar id = '';";
			code_to_add_to_appjs += "\n\treturn {";
			code_to_add_to_appjs += "\n\t\tsetId: function (id){";
			code_to_add_to_appjs += "\n\t\t\tthis.id = id;";
			code_to_add_to_appjs += "\n\t\t},";
			code_to_add_to_appjs += "\n\t\tgetId: function (){";
			code_to_add_to_appjs += "\n\t\t\treturn this.id;";
			code_to_add_to_appjs += "\n\t\t}";
			code_to_add_to_appjs += "\n\t}";
			code_to_add_to_appjs += "\n})";
			//add parameter to controller
			if (client_device_type_os.equals(GpGenConstants.GpClientOS_ANDROID)) {
				if (client_device_type.equals(GpGenConstants.GpClientScreenType_phone)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_phone);
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone("," + activity.getPrimary_noun().getName() + "Id");
				} else if (client_device_type.equals(GpGenConstants.GpClientScreenType_tablet)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_ANDROID + "_" + GpGenConstants.GpClientScreenType_tablet);
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_tablet("," + activity.getPrimary_noun().getName() + "Id");
				}
			} else if (client_device_type_os.equals(GpGenConstants.GpClientOS_IOS)) {
				if (client_device_type.equals(GpGenConstants.GpClientScreenType_phone)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_phone);
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone("," + activity.getPrimary_noun().getName() + "Id");
				} else if (client_device_type.equals(GpGenConstants.GpClientScreenType_tablet)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getAppjs_worker().add_code(code_to_add_to_appjs, GpGenConstants.GpClientOS_IOS + "_" + GpGenConstants.GpClientScreenType_tablet);
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_tablet("," + activity.getPrimary_noun().getName() + "Id");
				}
			}
		}
	}

	public void check_id_from_search_for_detail(GpActivity activity){
		Map<String,String> code_to_add_to_controllers =  getHtml_gen_support().getThe_worker().getCode_to_add_to_controllers();
		String code_to_add = code_to_add_to_controllers.get(activity.getPrimary_noun().getName() + "Id" + "-" + client_device_type_os + "-" + client_device_type);
		if(code_to_add == null){
			if (client_device_type_os.equals(GpGenConstants.GpClientOS_ANDROID)) {
				if (client_device_type.equals(GpGenConstants.GpClientScreenType_phone)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_phone(",$ionicPopup");
				} else if (client_device_type.equals(GpGenConstants.GpClientScreenType_tablet)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_android_tablet(",$ionicPopup");
				}
			} else if (client_device_type_os.equals(GpGenConstants.GpClientOS_IOS)) {
				if (client_device_type.equals(GpGenConstants.GpClientScreenType_phone)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_phone(",$ionicPopup");
				} else if (client_device_type.equals(GpGenConstants.GpClientScreenType_tablet)) {
					getHtml_gen_support().getThe_worker().get_generation_service().getController_worker().add_dependent_services_for_ios_tablet(",$ionicPopup");
				}
			}
			code_to_add = "var showPopup = function(verb){\n";
			code_to_add += "\t$ionicPopup.prompt({\n";
			code_to_add += "\t\ttitle: verb,\n";
			code_to_add += "\t\ttemplate: 'Enter the " + activity.getPrimary_noun().getName() + " ID to ' + verb,\n";
			code_to_add += "\t\tinputType: 'text',\n";
			code_to_add += "\t\tinputPlaceholder: 'Your " + activity.getPrimary_noun().getName() + " ID',\n";
			code_to_add += "\t\tokText: verb\n";
			code_to_add += "\t}).then(function(res) {\n";
			code_to_add += "\t\tconsole.log('Your " + activity.getPrimary_noun().getName() + "Id is', res);\n";
			code_to_add += "\t\tif (res) {\n";
			code_to_add += "\t\t\tid = res;\n";
			code_to_add += "\t\t\t$scope.search_for_update(id);\n";
			code_to_add += "\t\t} else {\n";
			code_to_add += "\t\t\talert('You should enter ID');\n\t";
			code_to_add += "\t}\n});\n};";
			code_to_add += "var id = " + activity.getPrimary_noun().getName() + "Id.getId();\n";
			code_to_add += "if (id) {\n";
			code_to_add += "\tconsole.log(id);\n";
			code_to_add += "\t$scope.search_for_update(id);\n";
			code_to_add += "} else {\n";
			//We should change this
			code_to_add += "\tif ($location.path().match('Update-en') || $location.path().match('update-en')) {\n";
			code_to_add += "\tshowPopup('Search');\n";
			code_to_add += "\t};\n";
			code_to_add += "}\n\n";

			code_to_add_to_controllers.put(activity.getPrimary_noun().getName() + "Id" + "-" + client_device_type_os + "-" + client_device_type, code_to_add);
			String code_to_add_to_controller = code_to_add_to_controllers.get(activity.getName() + "-" + client_device_type_os + "-" + client_device_type);
			if(code_to_add_to_controller == null)
				code_to_add_to_controller = "";
			code_to_add_to_controller += "\n";
			code_to_add_to_controller += code_to_add;
			code_to_add_to_controllers.put(activity.getName() + "-" + client_device_type_os + "-" + client_device_type, code_to_add_to_controller);
		}

	}
}
