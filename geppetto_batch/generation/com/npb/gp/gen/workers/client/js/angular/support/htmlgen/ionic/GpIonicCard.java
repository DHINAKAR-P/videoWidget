package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicCard extends GpBaseHtmlGenInfo{
	String screen_type;
	private String end_tag;
	private int height_content;
	public GpIonicCard(String screen_type) {
		this.screen_type = screen_type;
	}
	public String getEnd_tag() {
		return end_tag;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div";
		the_html += " style=\"" + style + "\"";
		if(event != null){
			the_html += " " + event + "=\"" + function + "()\""; 
		}
		the_html += " class=\"" + css_class + "\"";
		the_html += ">\n";
		//header
		the_html += "\t" + "<div class=\"item item-divider\">\n";
		the_html += "\t\t" + this.widget.getCard_header() + "\n";
		the_html += "\t" + "</div>" + "\n";
		//content
		
		the_html += "<div class=\"item item-text-wrap\" style=\"height:"+ this.height_content +"vh\">\n";
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
			double height_content = ((double) (Long.valueOf(this.height)-28-28) / geppetto_Y) * 100;
			int int_height = (int) height;
			this.height = int_height + "";
			this.height_content = (int) height_content;
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
		
		css_class = "card";
		
		end_tag = "</div>\n";
		//footer
		end_tag += "<div class=\"item item-divider\">\n";
		end_tag += "\t" + this.widget.getCard_footer() + "\n";
		end_tag += "</div>\n</div>";
	}
}
