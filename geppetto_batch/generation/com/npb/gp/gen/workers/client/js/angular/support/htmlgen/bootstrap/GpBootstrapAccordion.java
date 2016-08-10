package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapAccordion extends GpBaseHtmlGenInfo {
	String end_tag;
	
	public String getEnd_tag() {
		return end_tag;
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
		the_html += "<div class=\"panel-group\" id=\""+ this.widget.getName() +"\">\n";//should change this
		this.generated = true;
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		//section: style 
		style = "position: absolute;";
		style += "width: " + this.width + "px;";
		style += "height: " + this.height + "px;";
		style += "left: " + this.widget.getX() + "px;";
		style += "top: " + this.widget.getY() + "px;";
		style += "padding: 5px;";
		//section end_tag
		end_tag = "\n</div>\n</div>";
	}

}
