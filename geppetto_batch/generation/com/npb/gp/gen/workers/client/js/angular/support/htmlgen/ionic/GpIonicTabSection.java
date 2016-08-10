package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicTabSection extends GpBaseHtmlGenInfo{
	private String screen_type;
	private String end_tag;
	private GpBaseHtmlGenInfo parent;
	
	public GpIonicTabSection(GpBaseHtmlGenInfo parent, String screen_type) {
		this.screen_type = screen_type;
		this.parent = parent;
	}
	public String getEnd_tag() {
		return end_tag;
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<ion-tab";
		the_html += " style=\"" + style + "\"";
		//title
		the_html += " title=\"" + this.widget.getLabel() + "\"";
		the_html += " data-target=\"#/tab/" + this.widget.getName() + "\"";
		the_html += ">\n";
		the_html += "\t" + "<ion-nav-view name=\"tab-"+ this.widget.getName() +"\">\n";
		the_html += "\t\t" + "<ion-content>" + "\n";
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		if(parent != null){
			this.widget.x = parent.widget.x;
			this.widget.y = parent.widget.y;
		}
		end_tag = "</ion-content>\n</ion-nav-view>\n</ion-tab>\n";
	}
}
