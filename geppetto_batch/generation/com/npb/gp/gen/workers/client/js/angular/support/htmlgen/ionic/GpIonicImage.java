package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicImage extends GpBaseHtmlGenInfo{
	GpBaseHtmlGenInfo parent;
	String screen_type;
	public GpIonicImage(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<img src=\""+ this.widget.getImage_src() +"\"";
		the_html += " style=\"" + style + "\"";
		the_html += " alt=\"" + this.widget.getName() + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "()\""; 
		}
		the_html += " >";
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
		//section: style 
		style = "position: absolute;";
		style += "width: " + this.width + "vw;";
		style += "height: " + this.height + "vh;";
		style += "left: " + this.widget.getX() + "vw;";
		style += "top: " + this.widget.getY() + "vh;";
		//section: class
	}
	
}
