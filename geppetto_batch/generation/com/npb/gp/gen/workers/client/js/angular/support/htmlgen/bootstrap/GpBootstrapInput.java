package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.constants.GpVerbBindingConstants;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapInput extends GpBaseHtmlGenInfo{
	private String type;
	private String attr_class;
	private String style;
	
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapInput(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	
	public void set_attributes(){
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
		//section: class
		attr_class = "form-control";
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<input";
		the_html += " type=\"" + type + "\"";
		if(noun_attribute != null){
			if(type.equals("file"))
				the_html += " fileread=\"" + noun_attribute + "\"";
			else
				the_html += " data-ng-model=\"" + noun_attribute + "\"";
		}
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += " class=\"" + attr_class + "\"";
		the_html += " />";
		return the_html;
	}
	
	public void setType(GpNounAttribute noun_attr, GpBootstrapGenManager gpBootstrapGenManager) {
		if(noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_PICTURE) || noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_SOUND)){
			this.type = "file";
			gpBootstrapGenManager.add_file_read_directive();
			return;
		}
		if(noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_BOOL)){
			this.type = "checkbox";
			return;
		}
		if(noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_DATE)){
			this.type = "date";
			return;
		}
		if(noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_NUMBER)){
			this.type = "number";
			return;
		}
		if(noun_attr.getSubtype().equals(GpNounConstants.SUB_TYPE_TEXT)){
			this.type = "text";
			return;
		}
		this.type = "text";
	}
	
}
