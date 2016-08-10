package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicLabel extends GpBaseHtmlGenInfo{
	GpBaseHtmlGenInfo parent;
	String screen_type;
	public GpIonicLabel(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}



	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div class=\"list\"";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "()\""; 
		}
		the_html += ">\n";
		the_html += "<label>\n" + widget.getLabel() + "\n</label>\n</div>\n";
		return the_html;
	}
	
	
	
	@Override
	public void set_attributes() {
		//section: style 
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
		style = "position: absolute;";
		style += "width: " + this.width + "vw;";
		style += "height: " + this.height + "vh;";
		style += "left: " + this.widget.getX() + "vw;";
		style += "top: " + this.widget.getY() + "vh;";
		style += "line-height: " + this.height + "vh;";
		
	}
	
}
