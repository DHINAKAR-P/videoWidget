package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
/**
 * 
 * @author Dan Castillo
 * Date Created: 05/25/2015</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate a button that uses Ionic as its css</br> 
 * framework</p>
 *  
 */
public class GpIonicButton extends GpBaseHtmlGenInfo {
	
	GpBaseHtmlGenInfo parent;
	String screen_type;
	public GpIonicButton(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}

	@Override
	public String get_html_tag(){
		set_attributes();
		String the_html = "<button";
		the_html += " class=\"" + css_class + "\"";
		the_html += " style=\"" + style + "\"";
		if(this.widget.getVerb_binding_context() != null){
			the_html += " type=\"submit\" ";
		}else{
			the_html += " type=\"button\" ";
		}
		if(event != null){
			the_html += event + "=\"" + function + "()\""; 
		}
		the_html += ">" + widget.getLabel() + "</button>";  
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
		//class
		css_class = "button button-block button-positive";
		//style
		style = "position: absolute;";
		style += "width: " + this.width + "vw;";
		style += "height: " + this.height + "vh;";
		style += "left: " + this.widget.getX() + "vw;";
		style += "top: " + this.widget.getY() + "vh;";
	}
	public GpBaseHtmlGenInfo getParent() {
		return parent;
	}

	public void setParent(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}

	public String getScreen_type() {
		return screen_type;
	}

	public void setScreen_type(String screen_type) {
		this.screen_type = screen_type;
	}


}
