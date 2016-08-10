package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicInput extends GpBaseHtmlGenInfo{
	String label_tag_start;
	String label_tag_end;
	String type;
	String placeholder;
	GpBaseHtmlGenInfo parent;
	private String noun_attribute;
	String screen_type;
	public GpIonicInput(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}

	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div class=\"content content-field\"";
		the_html += " style=\"" + style + "\" >\n";
		the_html += label_tag_start + "\n\t<input";
		the_html += " type=\"" + type + "\""; 
		the_html += " placeholder=\"" + placeholder + "\"";
		if(noun_attribute != null){
			the_html += " data-ng-model=\"" + noun_attribute + "\"";
		}
		if(event != null){
			the_html += " " + event + "=\"" + function + "()\""; 
		}
		the_html += ">\n" + label_tag_end + "\n";
		the_html += "</div>\n";
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
			if(parent != null){
				this.widget.x = this.widget.x - parent.widget.x;
				this.widget.y = this.widget.y - parent.widget.y;
			}
		}
		else{
			geppetto_X = 405;
			geppetto_Y = 515;
		}
		//label container
		label_tag_start = "<label class=\"item item-input\">";
		label_tag_end = "</label>";
		//type -> may be others but i don't know
		type = "text";
		//placeholder
		placeholder = widget.getLabel();
		//style 
		style = "position: absolute;";
		style += "width: " + this.width + "vw;";
		style += "height: " + this.height + "vh;";
		style += "left: " + this.widget.getX() + "vw;";
		style += "top: " + this.widget.getY() + "vh;";
	}
	public void setNoun_attribute(String noun_attribute) {
		this.noun_attribute = noun_attribute;
	}
}
