package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapCheckBox extends GpBaseHtmlGenInfo {
	private String value;
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapCheckBox(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	@Override
	public String get_html_tag(){
		set_attributes();
		String the_html = "<label";
		the_html += " style=\"" + style + "\">\n";
		// style="position: absolute; top: 150px; left: 30px; width: 200px; border: 1px solid gray; padding: 5px;">
		the_html += "<input type=\"checkbox\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		if(noun_attribute != null){
			the_html += " data-ng-model=\"" + noun_attribute + "\"";
		}
		the_html += " value=\"" + value + "\"";
		the_html += ">" + widget.getLabel() + "\n</label>";
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
		//section: value
		value = "Y";
	}

}

