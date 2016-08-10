package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapLink extends GpBaseHtmlGenInfo{
	
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapLink(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<a href=\""+ this.widget.getTarget_url() +"\"";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += ">";
		the_html += this.widget.getLabel();
		the_html += "</a>";
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
	}
}
