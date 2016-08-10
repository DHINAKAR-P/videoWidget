package com.npb.gp.gen.workers.client.js.angular.support.htmlgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;

/**
 * 
 * @author Dan Castillo
 * Date Created: 06/05/2015</br>
 * @since .2</p> 
 *
 * used as a base class for HTML managers - could be an intefrace as well</br>
 * but I expect that I may share instance variables - as of now</br>
 * this is a work in progress - Dan Castillo - 06/5/15
 * 
 */
public class GpAngularJsHtmlGenManagerBase {
	
	
	public HashMap<String,String> generate_html_page(List<GpUiWidgetX> sorted_widgets,
													GpScreenX screen, GpActivity activity) throws Exception{
		
		return null;
	}
	
	public List<GpUiWidgetX> get_widget_list(Map<String, 
									GpUiWidgetX>  widget_map) throws Exception{
		
		//System.out.println("In GpAngularJsHtmlGenManagerBase - get_widget_list");

		ArrayList<GpUiWidgetX> widget_list  = new ArrayList<GpUiWidgetX>();
		for (Map.Entry<String, GpUiWidgetX> entry : widget_map.entrySet()) {
			//System.out.println("$$$$$ the entry is: " + entry.getKey());
			GpUiWidgetX widget = (GpUiWidgetX)entry.getValue();
			/*
			System.out.println("the type is: " + widget.getType() + " the name is: " + widget.getName()
					+ " the portrait X is: " + widget.getPortraitX()
					+ " the portrait Y is: " + widget.getPortraitY()
					+ " the widget id is: " + widget.getId());
					*/
			widget_list.add(widget);
		}
		//return this.sort_screen_widgets(widget_list);
		Collections.sort(widget_list, new MyComparator());
		return widget_list;
	}
	
	private List<GpUiWidgetX> sort_screen_widgets(ArrayList<GpUiWidgetX> widget_list){
		System.out.println("In GpAngularJsHtmlGenManagerBase - sort_screen_widgets");
		
		 Comparator<GpUiWidgetX> byPortraitY = (e1, e2) -> Long.compare(
		            e1.getPortraitY(), e2.getPortraitY());
		
		 Comparator<GpUiWidgetX> byPortraitX = (e1, e2) -> Long.compare(
		            e1.getPortraitX(), e2.getPortraitX());
		
		 List<GpUiWidgetX> hold =  widget_list.stream()
				 .sorted(byPortraitY)
				 .sorted(byPortraitX)
				 .collect(Collectors.toList());
				 
		 //for(GpUiWidgetX wid : hold){
		//	 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX()); 
		// }

		return hold;
	}
	
	private class MyComparator implements Comparator<GpUiWidgetX> {

		@Override
		public int compare(GpUiWidgetX o1, GpUiWidgetX o2) {
			if(o1.getPortraitY()>o2.getPortraitY())
				return 1;
			if(o1.getPortraitY()<o2.getPortraitY())
				return -1;
			return 0;
		}
		
	}

	public HashMap<String, String> generate_html_page(
			List<GpUiWidgetX> sorted_widgets, GpScreenX screen,
			GpActivity activity, String templateName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
