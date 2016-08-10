package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapPanel extends GpBaseHtmlGenInfo {
	String end_tag;
	public String getEnd_tag() {
		return end_tag;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div";
		the_html += " class=\"" + css_class + "\"";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "\""; 
		}
		the_html += ">\n";
		//heading
		the_html += "<div class=\"panel-heading\">" + widget.getLabel() + "</div>\n";
		the_html += "<div class=\"panel-body\">" + "" + "";
		//the_html += "<div class=\"panel-footer>" + widget.getLabel() + "</div>\n</div>";
		
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
		//section: class
		css_class = "panel panel-default";
		//section: endTag
		end_tag = "\n</div>\n</div>";
	}

}
