package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import org.springframework.stereotype.Component;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
import com.npb.gp.constants.GpDataBindingConstants;
import com.npb.gp.constants.GpVerbBindingConstants;

/**
 * 
 * @author Dan Castillo
 * Date Created: 05/25/2015</br>
 * @since .2</p> 
 * 
 * Contains the logic to generate a button that uses BootStrap as its css</br> 
 * framework</p>
 *  
 */
public class GpBootstrapButton extends GpBaseHtmlGenInfo {
	
	public static String DEFAULT_CSS_CLASS = "btn btn-primary";
	GpBaseHtmlGenInfo parent;
	public GpBootstrapButton(GpBaseHtmlGenInfo parent){
		this.css_class = GpBootstrapButton.DEFAULT_CSS_CLASS; //default css_class for this html type
		this.parent = parent;
	}
	
	@Override
	public String get_html_tag(){
		set_attributes();
		String the_tag; 
		
		the_tag = "<button ";
		
		if(this.widget.getVerb_target() != null){
			the_tag = the_tag + "type=\"submit\" ";
		}else{
			the_tag = the_tag + "type=\"button\" ";
		}
		
		the_tag = the_tag + "class=\"" + this.css_class + "\" ";
		if(event != null){
			the_tag = the_tag + event + "=\"" + function + "\" "; 
		}
		
		the_tag += "style=\"" + style + "\"";
		
		the_tag = the_tag + ">" + this.widget.getLabel() + "</button>";

		return the_tag;
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
	}

}
