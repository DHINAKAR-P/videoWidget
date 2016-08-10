package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapImage extends GpBaseHtmlGenInfo{
	
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapImage(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<img ";
		if(noun_attribute == null)
			the_html += "src=\""+ this.widget.getImage_src() +"\"";
		else
			the_html += "src=\"{{"+ this.noun_attribute +"}}\"";
		the_html += " style=\"" + style + "\"";
		the_html += " class=\"" + css_class + "\"";
		the_html += " alt=\"" + this.widget.getName() + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += " >";
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
		css_class = "img-thumbnail";
	}
}
