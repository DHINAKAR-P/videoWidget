package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapGrid extends GpBaseHtmlGenInfo{
	
	public GpBootstrapGrid() {
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div>\n";
		the_html += "<div ui-grid=\"gridOptions\" ui-grid-selection class=\"myGrid\"";
		the_html += " style=\"" + style + "\"";
		the_html += "></div>\n</div>";
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
	}
}
