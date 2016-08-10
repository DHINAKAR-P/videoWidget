package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import java.util.List;

import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpWidgetConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapTab extends GpBaseHtmlGenInfo {
	List<GpUiWidgetX> children;
	String end_tag;
	public String getEnd_tag() {
		return end_tag;
	}
	public void setChildren(List<GpUiWidgetX> children) {
		this.children = children;
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
		//nav Tabs
		the_html += "<ul class=\"nav nav-tabs\" role=\"tablist\">\n";
		int pos = 1;
		for(GpUiWidgetX widget_child : children){
			if(pos == 1){
				the_html += "<li role=\"presentation\" class=\"active\"><a data-target=\"#" + GpWidgetConstants.TYPE_TAB + pos + "\""
						+ " aria-controls=\""+ GpWidgetConstants.TYPE_TAB + pos +"\" role=\"tab\" data-toggle=\"tab\">"
						+ "Tab " + pos + "</a></li>\n";
			}
			else{
				the_html += "<li role=\"presentation\"><a data-target=\"#" + GpWidgetConstants.TYPE_TAB + pos + "\""
						+ " aria-controls=\""+ GpWidgetConstants.TYPE_TAB + pos +"\" role=\"tab\" data-toggle=\"tab\">"
						+ "Tab " + pos + "</a></li>\n";
			}
			pos++;
		}
		the_html += "</ul>\n";
		//TabsContent
		the_html += "<div class=\"tab-content\">\n";
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
		style += "border: 1px solid #efefef; border-radius: 5px; padding: 20px;";
		//Section: endtag
		end_tag = "\n</div>\n</div>";
		
	}

}
