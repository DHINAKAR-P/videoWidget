package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicRange extends GpBaseHtmlGenInfo{
	GpBaseHtmlGenInfo parent;
	String screen_type;
	public GpIonicRange(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "()\""; 
		}
		the_html += ">\n";
		the_html += "<div class=\"item range range-positive\">\n";
		the_html += "<i class=\"icon ion-ios-sunny-outline\"></i>\n";
		the_html += "<input type=\"range\" name=\"volume\" min=\"0\" max=\"100\" value=\"33\">\n";
		the_html += "<i class=\"icon ion-ios-sunny\"></i>";
		the_html += "\n</div>\n</div>";
		return the_html;
	}
	
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
		//section: style 
		style = "position: absolute; ";
		style += "width: " + this.width + "vw; ";
		style += "height: " + this.height + "vh; ";
		style += "left: " + this.widget.getX() + "vw; ";
		style += "top: " + this.widget.getY() + "vh;";		
	};
}
