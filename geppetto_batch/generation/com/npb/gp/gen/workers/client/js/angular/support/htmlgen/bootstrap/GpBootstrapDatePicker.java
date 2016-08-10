package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapDatePicker extends GpBaseHtmlGenInfo{
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapDatePicker(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div type=\"date\"";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += " >\n\t";
		the_html += "<div data-role=\"date\" class=\"date input-group\" id=\""+ this.widget.getName() +"\">\n\t";
		the_html += "<input type=\"text\" placeholder=\"Pick a Date\"";
		if(noun_attribute != null){
			the_html += " data-ng-model=\"" + noun_attribute + "\"";
		}
		the_html += " class=\"" + css_class + "\">\n\t";
		the_html += "<span class=\"input-group-addon\"><i class=\"fa fa-calendar\"></i></span>\n";
		the_html += "</div>\n</div>";
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
		//section: class
		css_class = "form-control";
	}

}
