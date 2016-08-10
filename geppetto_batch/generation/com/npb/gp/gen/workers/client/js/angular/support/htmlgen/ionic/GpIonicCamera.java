package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic;

import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpIonicCamera  extends GpBaseHtmlGenInfo{
	GpBaseHtmlGenInfo parent;
	String screen_type;
	public GpIonicCamera(GpBaseHtmlGenInfo parent, String screen_type) {
		this.parent = parent;
		this.screen_type = screen_type;
	}
	
	@Override
	public String get_html_tag() {
		String the_html = "<button class=\"button button-clear icon-left ion-camera button-positive\" data-ng-click=\"takePhoto()\">Upload Photo</button>";
		//the_html +="\n\t\t <button class=\"button button-block button-positive\" style=\"position: absolute;width: 20vw;height: 7vh;left: 75vw;top: 82vh;\" type=\"submit\" data-ng-click=\"pushToCloud()\">Push</button>";
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
}
