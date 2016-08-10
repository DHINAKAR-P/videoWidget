package com.npb.gp.gen.workers.client.js.angular.support.htmlgen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.domain.core.GpUiWidgetX;
import com.npb.gp.gen.util.dto.GpBaseHtmlGenInfo;
import com.npb.gp.gen.workers.client.js.angular.GpAngularHtmlGenWorker;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.bootstrap.GpBootstrapGenManager;
import com.npb.gp.gen.workers.client.js.angular.support.htmlgen.ionic.GpIonicGenManager;
/**
 * 
 * @author Dan Castillo
 * Date Created: 05/04/2015</br>
 * @since .2</p> 
 * 
 * Contains the coordination logic to generate the HTML screens for an</br>
 * AngularJs application it is designed to be called by the </br>
 * worker that handles the AngularJs HTML generation</p> 
 */
@Component("GpAngularJsHtmlGenSupport")
public class GpAngularJsHtmlGenSupport {
	
	GpAngularHtmlGenWorker the_worker;
	
	GpBootstrapGenManager bootstrap_manager;
	GpIonicGenManager ionic_manager;
	
	
	public GpBootstrapGenManager getBootstrap_manager() {
		return bootstrap_manager;
	}
	@Resource(name="GpBootstrapGenManager")
	public void setBootstrap_manager(GpBootstrapGenManager bootstrap_manager) {
		this.bootstrap_manager = bootstrap_manager;
	}
	public GpIonicGenManager getIonic_manager() {
		return ionic_manager;
	}
	@Resource(name="GpIonicGenManager")
	public void setIonic_manager(GpIonicGenManager ionic_manager) {
		this.ionic_manager = ionic_manager;
	}
	public GpAngularHtmlGenWorker getThe_worker() {
		return the_worker;
	}
	@Resource(name="GpAngularHtmlGenWorker")
	public void setThe_worker(GpAngularHtmlGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	
	public HashMap<String,GpBaseHtmlGenInfo> 
	get_html_for_screen(GpScreenX screen, GpActivity activity) throws Exception{
		
		List<GpUiWidgetX> sorted_widgets = this.get_widget_list(screen);
		
		
		System.out.println("In GpAngularJsHtmlGenSupport -  get_html_for_screen @@@@@@@@@@");
		for(GpUiWidgetX wid : sorted_widgets){
			 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX()); 
		 }

		
		return null;
	}
	
	public HashMap<String,String> handle_html_gen_android(
						GpScreenX screen, GpActivity activity) throws Exception{
		
		System.out.println("@@@@@@@@@@@@@@ In handle_html_gen_android @@@@@@@@@@");
		//Tablet == phone?
		
		Map<String, GpUiWidgetX>  widget_map = this.the_worker
				.getScreen_dao().find_all_widgets_by_screen(screen.getId());

		List<GpUiWidgetX> sorted_widgets = this.ionic_manager.get_widget_list(widget_map);
		HashMap<String, String> map = this.ionic_manager.generate_html_page(sorted_widgets, screen, activity);
		//List<GpUiWidgetX> sorted_widgets = this.get_widget_list(screen);
		
		//for(GpUiWidgetX wid : sorted_widgets){
		//	 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX()); 
		 //}

		
		return map;
	}

	
	public HashMap<String,String> handle_html_gen_ios(
						GpScreenX screen, GpActivity activity) throws Exception{
		//Tablet == phone?
		System.out.println("In AngularJsHtmlGenSupport In handle_html_gen_ios @@@@@@@@@@");
		Map<String, GpUiWidgetX>  widget_map = this.the_worker
				.getScreen_dao().find_all_widgets_by_screen(screen.getId());

		List<GpUiWidgetX> sorted_widgets = this.ionic_manager.get_widget_list(widget_map);
		HashMap<String, String> map = this.ionic_manager.generate_html_page(sorted_widgets, screen, activity);
		
		//for(GpUiWidgetX wid : sorted_widgets){
		//	 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX()); 
		 //}

		
		return map;
	}
	
	public void generate_menus(){
		
	}

	
	public HashMap<String,String> handle_html_gen_windows(
						GpScreenX screen, GpActivity activity,String templateName) throws Exception{
		
		System.out.println("@@@@@@@@@@@@@@ In handle_html_gen_windows @@@@@@@@@@");
		System.out.println("Activity: " + activity.getId());
		System.out.println("Screen: " + screen.getId());
		
		Map<String, GpUiWidgetX>  widget_map = this.the_worker
				.getScreen_dao().find_all_widgets_by_screen(screen.getId());

		List<GpUiWidgetX> sorted_widgets = this.bootstrap_manager.get_widget_list(widget_map);
		HashMap<String, String> map = this.bootstrap_manager.generate_html_page(sorted_widgets, screen, activity,templateName);
		
		//List<GpUiWidgetX> sorted_widgets = this.get_widget_list(screen);
		/*
		for(GpUiWidgetX wid : sorted_widgets){
			 System.out.println("Type: "+ wid.getType() + " Portrait Y: " + wid.getPortraitY() + " Portrait X: " + wid.getPortraitX()); 
		 }
		 */

		
		return map;
	}

	
	private List<GpUiWidgetX> get_widget_list(GpScreenX screen) throws Exception{
		
			
		Map<String, GpUiWidgetX>  widget_map = this.the_worker
				.getScreen_dao().find_all_widgets_by_screen(screen.getId());
		
		ArrayList<GpUiWidgetX> widget_list  = new ArrayList<GpUiWidgetX>();
		for (Map.Entry<String, GpUiWidgetX> entry : widget_map.entrySet()) {
			System.out.println("$$$$$ the entry is: " + entry.getKey());
			GpUiWidgetX widget = (GpUiWidgetX)entry.getValue();
			
			System.out.println("the type is: " + widget.getType() + " the name is: " + widget.getName()
					+ " the portrait X is: " + widget.getPortraitX()
					+ " the portrait Y is: " + widget.getPortraitY()
					+ " the widget id is: " + widget.getId());
			widget_list.add(widget);
		}
		
		
		return this.sort_screen_widgets(widget_list);
	}
	
	private List<GpUiWidgetX> sort_screen_widgets(ArrayList<GpUiWidgetX> widget_list){
		System.out.println("@@@@@@@@@@@@@@ IN sort_screen_widgets @@@@@@@@@@");
		
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
	

}
