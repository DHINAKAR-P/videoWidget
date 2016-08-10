package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicList extends GpBaseHtmlGenInfo {
	
	String end_tag;
	String screen_type;
	private String noun_attribute;
	public GpIonicList(String screen_type) {
		this.screen_type = screen_type;
	}

	public String getEnd_tag() {
		return end_tag;
	}
	
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "\n<ul";
		the_html += " class=\"" + css_class + "\"";
		the_html += " style=\"" + style + "\"";
		the_html += " id=\"" + widget.getName() + "\">";
		the_html += "<li class=\"item\" ng-repeat=\"item in items\" data-id=\"{{item.Id}}\">";
		if(noun_attribute == null)
			the_html += "{{item.Email}}";
		else
			the_html += "{{item." + noun_attribute + "}}";
		the_html += "</li>";
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		int geppetto_X;
		int geppetto_Y;	
		if(this.screen_type.equals(GpGenConstants.GpClientScreenType_phone)){
			geppetto_X = 320;
			geppetto_Y = 495;
			double width = ((double) Long.valueOf(this.width) / geppetto_X) * 100;
			int int_width = (int) width;
			this.width = int_width + "";
			double height = ((double) Long.valueOf(this.height) / geppetto_Y) * 100;
			int int_height = (int) height;
			this.height = int_height + "";
			double top = ((double) Long.valueOf(this.widget.y) / geppetto_Y) * 100;
			int int_top = (int) top;
			this.widget.y = int_top;
			double left = ((double) Long.valueOf(this.widget.x) / geppetto_X) * 100;
			int int_left = (int) left;
			this.widget.x = int_left;
		}
		else{
			geppetto_X = 405;
			geppetto_Y = 515;
		}
		style = "position: absolute;";
		style += "width: " + this.width + "vw;";
		style += "height: " + this.height + "vh;";
		style += "left: " + this.widget.getX() + "vw;";
		style += "top: " + this.widget.getY() + "vh;";
		css_class = "list";
		end_tag = "</ul>";
	}
	
	public void setNoun_attribute(String noun_attribute) {
		this.noun_attribute = noun_attribute;
	}
}