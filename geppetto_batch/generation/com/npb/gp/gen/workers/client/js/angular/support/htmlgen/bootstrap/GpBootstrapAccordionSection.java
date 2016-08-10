package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapAccordionSection extends GpBaseHtmlGenInfo {

	String end_tag;
	int position;
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapAccordionSection(GpBaseHtmlGenInfo parent) {
		this.parent = parent;
	}
	public String getEnd_tag() {
		return end_tag;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	@Override
	public String get_html_tag() {
		set_attributes();
		String the_html = "<div class=\"panel panel-default\">";
		//heading of section
		the_html += "<div class=\"panel-heading\">\n"
			+ "<a class=\"accordion-toggle\" href=\"\" style=\"display:block\" data-toggle=\"collapse\" data-parent=\"#"+parent.widget.getName()+"\" data-target=\"#"+ this.widget.getName() +"\">\n"
            + this.widget.getLabel()
            + "<i class=\"indicator glyphicon glyphicon-chevron-down  pull-right\"></i></a>\n"
            + "</div>\n";
		//body
		if(position == 1)
			the_html += "<div id=\""+ this.widget.getName() +"\" class=\"panel-collapse collapse in\">\n";
		else
			the_html += "<div id=\""+ this.widget.getName() +"\" class=\"panel-collapse collapse\">\n";
		int height = Integer.valueOf(parent.height)-39-44-44;
		the_html += "<div class=\"panel-body\" style=\"height: "+ height +"px;\">\n";
		return the_html;
	}
	
	@Override
	public void set_attributes() {
		//section end_tag
		end_tag = "\n</div>\n</div>\n</div>";
		this.widget.x = parent.widget.x;
		this.widget.y = parent.widget.y;
	}
}
