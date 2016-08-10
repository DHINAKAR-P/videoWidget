package com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap;

import com.npb.gp.gen.constants.GpWidgetConstants;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;

public class GpBootstrapTabSection extends GpBaseHtmlGenInfo{
	int position;
	String end_tag;
	GpBaseHtmlGenInfo parent;
	
	public GpBootstrapTabSection(GpBaseHtmlGenInfo parent) {
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
		String the_html = "<div role=\"tabpanel\" class=\"tab-pane fade";
		if(position == 1)
			the_html += " in active";
		the_html +=	"\" id=\""+ GpWidgetConstants.TYPE_TAB + position + "\">\n";
		return the_html;
	}
	@Override
	public void set_attributes() {
		end_tag = "\n</div>";
		this.widget.x = parent.widget.x;
		this.widget.y = parent.widget.y;
	}
}
