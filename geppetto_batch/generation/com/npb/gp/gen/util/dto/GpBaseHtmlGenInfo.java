package com.npb.gp.gen.util.dto;

import com.npb.gp.domain.core.GpClientDeviceTypes;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpGenConstants;

/**
 * 
 * @author Dan Castillo
 * Date Created: 05/04/2015</br>
 * @since .2</p>
 * 
 * The purpose of this class is to encapsulate the bare minimum of information</br>
 * needed to generate the HTML for an AngularJS screen</p>
 * 
 */
public class GpBaseHtmlGenInfo {
	
	public long tab_start_position;
	public  String tag_type;
	public  String category;    //this is based of the w3c categories http://www.w3schools.com/tags/ref_byfunc.asp
	public  String css_class;
	public  String width;
	public  String height;
	public  String landscape_x;
	public  String landscape_y;
	public  String portrait_x;
	public  String portrait_y;
	public  String style;
	public  String event_type;
	public  String event_target;
	public String event;
	public String function;
	public boolean generated;
	public  String complete_start_tag;
	public  String end_tag;
	public String noun_attribute;
	
	public GpUiWidgetX widget;
	
	public void set_base_attributes(GpUiWidgetX wid){
		this.widget = wid;
		
		this.width = new Long(this.widget.getWidth()).toString();
		this.height = new Long(this.widget.getHeight()).toString();
		this.landscape_x = new Long(this.widget.getLandscapeX()).toString();
		this.landscape_y = new Long(this.widget.getLandscapeY()).toString();
		this.portrait_x = new Long(this.widget.getPortraitX()).toString();
		this.portrait_y = new Long(this.widget.getPortraitY()).toString();
		
		
		//Should comment this
		this.widget.x = new Long(this.portrait_x);
		this.widget.y = new Long(this.portrait_y);
		this.width = new Long(this.widget.getPortrait_width()).toString();
		this.height = new Long(this.widget.getPortrait_height()).toString();
		
		
	}
	
	public void set_attributes(){
		
	}
	
	public String get_html_tag(){
		
		return null;
		
	}
	
	public void set_event_and_function(String event_and_function){
		String[] split = event_and_function.split(":");
		event = split[0];
		function = split[1];
	}
	
	public void setNoun_attribute(String noun_attribute) {
		this.noun_attribute = noun_attribute;
	}
	
}
