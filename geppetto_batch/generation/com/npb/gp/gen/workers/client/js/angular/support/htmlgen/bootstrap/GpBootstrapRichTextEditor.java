package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapRichTextEditor extends GpBaseHtmlGenInfo {
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapRichTextEditor(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div";
		the_html += " style=\"" + style + "\"";
		the_html += ">\n";
		the_html += "\t" + "<textarea data-ck-editor data-ng-model=\"screen_notes\" id=\"screen_notes\"></textarea>" + "\n";
		the_html += "</div>";
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
		style += "overflow: hidden;";
	}
}
