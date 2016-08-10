package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicListItem extends GpBaseHtmlGenInfo {
	
	GpBaseHtmlGenInfo parent;
	String end_tag;
	String screen_type;
	
	public GpIonicListItem(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}

	public String getEnd_tag() {
		return end_tag;
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "\n\t<li";
		the_html += " class=\"" + css_class + "\"";
		the_html += " style=\"" + style + "\">" + widget.getLabel();
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		style = "";
		css_class = "item";
		end_tag = "</li>";
	}
}