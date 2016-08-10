package com.npb.gp.gen.workers.client.js.angular;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.npb.gp.dao.mysql.GpScreenXDao;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpModuleProperties;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpScreenX;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.json.mappers.modules.GpModuleAngularViews;
import com.npb.gp.gen.json.mappers.modules.GpModuleMobileInfo;
import com.npb.gp.gen.workers.GpGenJSClientAngularBaseWorker;
import com.npb.gp.gen.workers.client.js.angular.support.directorygen.GpAndroidDirectoryGenSupport;
import com.npb.gp.gen.workers.client.js.angular.support.directorygen.GpIosDirectoryGenSupport;
import com.npb.gp.gen.workers.client.js.angular.support.directorygen.GpWindowsDirectoryGenSupport;
/**
 * 
 * @author Dan Castillo
 * Date Created: 03/05/2015</br>
 * @since .2</p> 
 * 
 *  worker to handle the generation of required directories when working</br>
 *  AngularJS UI</p>
 *  
 *   
 */
@Component("GpAngularDirectoryGenWorker")
public class GpAngularDirectoryGenWorker extends
		GpGenJSClientAngularBaseWorker {

	private GpAndroidDirectoryGenSupport android_support;
	private GpIosDirectoryGenSupport ios_support;
	private GpWindowsDirectoryGenSupport desktop_support;
	private Path angular_root_directory_path;
	private Path android_root_directory_path;
	private Path ios_root_directory_path;
	/*windows is synonymous with desktop - Dan Castillo 03/08/2015 */
	private Path windows_root_directory_path;


	//Android Phone
	private Path android_phone_root_directory_path;
	private Path android_phone_app_root_directory_path;
	private Path android_phone_app_lib_root_directory_path;
	private Path android_phone_app_css_directory_path;
	private Path android_phone_app_fonts_directory_path;
	private Path android_phone_app_views_directory_path;
	private Path android_phone_app_root_js_directory_path;
	private Path android_phone_app_controller_directory_path;
	private Path android_phone_app_directives_directory_path;
	private Path android_phone_app_services_directory_path;
	
	private Path android_phone_app_lib_css_directory_path;
	private Path android_phone_app_lib_fonts_directory_path;
	private Path android_phone_app_lib_js_directory_path;
	
	//Android Tablet
	private Path android_tablet_root_directory_path;
	private Path android_tablet_app_root_directory_path;
	private Path android_tablet_app_lib_root_directory_path;
	private Path android_tablet_app_css_directory_path;
	private Path android_tablet_app_fonts_directory_path;
	private Path android_tablet_app_views_directory_path;
	private Path android_tablet_app_root_js_directory_path;
	private Path android_tablet_app_controller_directory_path;
	private Path android_tablet_app_directives_directory_path;
	private Path android_tablet_app_services_directory_path;
	
	private Path android_tablet_app_lib_css_directory_path;
	private Path android_tablet_app_lib_fonts_directory_path;
	private Path android_tablet_app_lib_js_directory_path;
	
	
	//IOS Phone
	private Path ios_phone_root_directory_path;
	private Path ios_phone_app_root_directory_path;
	private Path ios_phone_app_lib_root_directory_path;
	private Path ios_phone_app_css_directory_path;
	private Path ios_phone_app_fonts_directory_path;
	private Path ios_phone_app_views_directory_path;
	private Path ios_phone_app_root_js_directory_path;
	private Path ios_phone_app_controller_directory_path;
	private Path ios_phone_app_directives_directory_path;
	private Path ios_phone_app_services_directory_path;
	
	private Path ios_phone_app_lib_css_directory_path;
	private Path ios_phone_app_lib_fonts_directory_path;
	private Path ios_phone_app_lib_js_directory_path;

	//IOS Tablet
	private Path ios_tablet_root_directory_path;
	private Path ios_tablet_app_root_directory_path;
	private Path ios_tablet_app_lib_root_directory_path;
	private Path ios_tablet_app_css_directory_path;
	private Path ios_tablet_app_fonts_directory_path;
	private Path ios_tablet_app_views_directory_path;
	private Path ios_tablet_app_root_js_directory_path;
	private Path ios_tablet_app_controller_directory_path;
	private Path ios_tablet_app_directives_directory_path;
	private Path ios_tablet_app_services_directory_path;
	
	private Path ios_tablet_app_lib_css_directory_path;
	private Path ios_tablet_app_lib_fonts_directory_path;
	private Path ios_tablet_app_lib_js_directory_path;


	//Windows 
	private Path windows_app_root_directory_path;
	private Path windows_app_lib_root_directory_path;
	private Path windows_app_css_directory_path;
	private Path windows_app_fonts_directory_path;
	private Path windows_app_views_directory_path;
	private Path windows_app_root_js_directory_path;
	private Path windows_app_controller_directory_path;
	private Path windows_app_directives_directory_path;
	private Path windows_app_services_directory_path;
	
	private Path windows_app_lib_css_directory_path;
	private Path windows_app_lib_fonts_directory_path;
	private Path windows_app_lib_js_directory_path;

	private GpScreenXDao screen_dao;
	public boolean desktop_created = false;
	public boolean android_created = false;
	public boolean ios_created = false;
	
	public GpScreenXDao getScreen_dao() {
		return screen_dao;
	}

	@Resource(name="GpScreenXDao")
	public void setScreen_dao(GpScreenXDao screen_dao) {
		this.screen_dao = screen_dao;
	}
	
	
	public GpWindowsDirectoryGenSupport getDesktop_support() {
		return desktop_support;
	}
	@Resource(name="GpWindowsDirectoryGenSupport")
	public void setDesktop_support(GpWindowsDirectoryGenSupport desktop_support) {
		this.desktop_support = desktop_support;
	}
	public GpIosDirectoryGenSupport getIos_support() {
		return ios_support;
	}
	@Resource(name="GpIosDirectoryGenSupport")
	public void setIos_support(GpIosDirectoryGenSupport ios_support) {
		this.ios_support = ios_support;
	}
	public GpAndroidDirectoryGenSupport getAndroid_support() {
		return android_support;
	}
	@Resource(name="GpAndroidDirectoryGenSupport")
	public void setAndroid_support(GpAndroidDirectoryGenSupport android_support) {
		this.android_support = android_support;
	}

	
	public Path getWindows_app_root_directory_path() {
		return windows_app_root_directory_path;
	}
	public void setWindows_app_root_directory_path(
			Path windows_app_root_directory_path) {
		this.windows_app_root_directory_path = windows_app_root_directory_path;
	}
	public Path getWindows_app_lib_root_directory_path() {
		return windows_app_lib_root_directory_path;
	}
	public void setWindows_app_lib_root_directory_path(
			Path windows_app_lib_root_directory_path) {
		this.windows_app_lib_root_directory_path = windows_app_lib_root_directory_path;
	}
	public Path getWindows_app_css_directory_path() {
		return windows_app_css_directory_path;
	}
	public void setWindows_app_css_directory_path(
			Path windows_app_css_directory_path) {
		this.windows_app_css_directory_path = windows_app_css_directory_path;
	}
	public Path getWindows_app_fonts_directory_path() {
		return windows_app_fonts_directory_path;
	}
	public void setWindows_app_fonts_directory_path(
			Path windows_app_fonts_directory_path) {
		this.windows_app_fonts_directory_path = windows_app_fonts_directory_path;
	}
	public Path getWindows_app_views_directory_path() {
		return windows_app_views_directory_path;
	}
	public void setWindows_app_views_directory_path(
			Path windows_app_views_directory_path) {
		this.windows_app_views_directory_path = windows_app_views_directory_path;
	}
	public Path getWindows_app_root_js_directory_path() {
		return windows_app_root_js_directory_path;
	}
	public void setWindows_app_root_js_directory_path(
			Path windows_app_root_js_directory_path) {
		this.windows_app_root_js_directory_path = windows_app_root_js_directory_path;
	}
	public Path getWindows_app_controller_directory_path() {
		return windows_app_controller_directory_path;
	}
	public void setWindows_app_controller_directory_path(
			Path windows_app_controller_directory_path) {
		this.windows_app_controller_directory_path = windows_app_controller_directory_path;
	}
	public Path getWindows_app_directives_directory_path() {
		return windows_app_directives_directory_path;
	}
	public void setWindows_app_directives_directory_path(
			Path windows_app_directives_directory_path) {
		this.windows_app_directives_directory_path = windows_app_directives_directory_path;
	}
	public Path getWindows_app_services_directory_path() {
		return windows_app_services_directory_path;
	}
	public void setWindows_app_services_directory_path(
			Path windows_app_services_directory_path) {
		this.windows_app_services_directory_path = windows_app_services_directory_path;
	}
	public Path getWindows_app_lib_css_directory_path() {
		return windows_app_lib_css_directory_path;
	}
	public void setWindows_app_lib_css_directory_path(
			Path windows_app_lib_css_directory_path) {
		this.windows_app_lib_css_directory_path = windows_app_lib_css_directory_path;
	}
	public Path getWindows_app_lib_fonts_directory_path() {
		return windows_app_lib_fonts_directory_path;
	}
	public void setWindows_app_lib_fonts_directory_path(
			Path windows_app_lib_fonts_directory_path) {
		this.windows_app_lib_fonts_directory_path = windows_app_lib_fonts_directory_path;
	}
	public Path getWindows_app_lib_js_directory_path() {
		return windows_app_lib_js_directory_path;
	}
	public void setWindows_app_lib_js_directory_path(
			Path windows_app_lib_js_directory_path) {
		this.windows_app_lib_js_directory_path = windows_app_lib_js_directory_path;
	}
	public Path getIos_phone_app_root_directory_path() {
		return ios_phone_app_root_directory_path;
	}
	public void setIos_phone_app_root_directory_path(
			Path ios_phone_app_root_directory_path) {
		this.ios_phone_app_root_directory_path = ios_phone_app_root_directory_path;
	}
	public Path getIos_phone_app_lib_root_directory_path() {
		return ios_phone_app_lib_root_directory_path;
	}
	public void setIos_phone_app_lib_root_directory_path(
			Path ios_phone_app_lib_root_directory_path) {
		this.ios_phone_app_lib_root_directory_path = ios_phone_app_lib_root_directory_path;
	}
	public Path getIos_phone_app_css_directory_path() {
		return ios_phone_app_css_directory_path;
	}
	public void setIos_phone_app_css_directory_path(
			Path ios_phone_app_css_directory_path) {
		this.ios_phone_app_css_directory_path = ios_phone_app_css_directory_path;
	}
	public Path getIos_phone_app_fonts_directory_path() {
		return ios_phone_app_fonts_directory_path;
	}
	public void setIos_phone_app_fonts_directory_path(
			Path ios_phone_app_fonts_directory_path) {
		this.ios_phone_app_fonts_directory_path = ios_phone_app_fonts_directory_path;
	}
	public Path getIos_phone_app_views_directory_path() {
		return ios_phone_app_views_directory_path;
	}
	public void setIos_phone_app_views_directory_path(
			Path ios_phone_app_views_directory_path) {
		this.ios_phone_app_views_directory_path = ios_phone_app_views_directory_path;
	}
	public Path getIos_phone_app_root_js_directory_path() {
		return ios_phone_app_root_js_directory_path;
	}
	public void setIos_phone_app_root_js_directory_path(
			Path ios_phone_app_root_js_directory_path) {
		this.ios_phone_app_root_js_directory_path = ios_phone_app_root_js_directory_path;
	}
	public Path getIos_phone_app_controller_directory_path() {
		return ios_phone_app_controller_directory_path;
	}
	public void setIos_phone_app_controller_directory_path(
			Path ios_phone_app_controller_directory_path) {
		this.ios_phone_app_controller_directory_path = ios_phone_app_controller_directory_path;
	}
	public Path getIos_phone_app_directives_directory_path() {
		return ios_phone_app_directives_directory_path;
	}
	public void setIos_phone_app_directives_directory_path(
			Path ios_phone_app_directives_directory_path) {
		this.ios_phone_app_directives_directory_path = ios_phone_app_directives_directory_path;
	}
	public Path getIos_phone_app_services_directory_path() {
		return ios_phone_app_services_directory_path;
	}
	public void setIos_phone_app_services_directory_path(
			Path ios_phone_app_services_directory_path) {
		this.ios_phone_app_services_directory_path = ios_phone_app_services_directory_path;
	}
	public Path getIos_phone_app_lib_css_directory_path() {
		return ios_phone_app_lib_css_directory_path;
	}
	public void setIos_phone_app_lib_css_directory_path(
			Path ios_phone_app_lib_css_directory_path) {
		this.ios_phone_app_lib_css_directory_path = ios_phone_app_lib_css_directory_path;
	}
	public Path getIos_phone_app_lib_fonts_directory_path() {
		return ios_phone_app_lib_fonts_directory_path;
	}
	public void setIos_phone_app_lib_fonts_directory_path(
			Path ios_phone_app_lib_fonts_directory_path) {
		this.ios_phone_app_lib_fonts_directory_path = ios_phone_app_lib_fonts_directory_path;
	}
	public Path getIos_phone_app_lib_js_directory_path() {
		return ios_phone_app_lib_js_directory_path;
	}
	public void setIos_phone_app_lib_js_directory_path(
			Path ios_phone_app_lib_js_directory_path) {
		this.ios_phone_app_lib_js_directory_path = ios_phone_app_lib_js_directory_path;
	}
	public Path getIos_tablet_app_root_directory_path() {
		return ios_tablet_app_root_directory_path;
	}
	public void setIos_tablet_app_root_directory_path(
			Path ios_tablet_app_root_directory_path) {
		this.ios_tablet_app_root_directory_path = ios_tablet_app_root_directory_path;
	}
	public Path getIos_tablet_app_lib_root_directory_path() {
		return ios_tablet_app_lib_root_directory_path;
	}
	public void setIos_tablet_app_lib_root_directory_path(
			Path ios_tablet_app_lib_root_directory_path) {
		this.ios_tablet_app_lib_root_directory_path = ios_tablet_app_lib_root_directory_path;
	}
	public Path getIos_tablet_app_css_directory_path() {
		return ios_tablet_app_css_directory_path;
	}
	public void setIos_tablet_app_css_directory_path(
			Path ios_tablet_app_css_directory_path) {
		this.ios_tablet_app_css_directory_path = ios_tablet_app_css_directory_path;
	}
	public Path getIos_tablet_app_fonts_directory_path() {
		return ios_tablet_app_fonts_directory_path;
	}
	public void setIos_tablet_app_fonts_directory_path(
			Path ios_tablet_app_fonts_directory_path) {
		this.ios_tablet_app_fonts_directory_path = ios_tablet_app_fonts_directory_path;
	}
	public Path getIos_tablet_app_views_directory_path() {
		return ios_tablet_app_views_directory_path;
	}
	public void setIos_tablet_app_views_directory_path(
			Path ios_tablet_app_views_directory_path) {
		this.ios_tablet_app_views_directory_path = ios_tablet_app_views_directory_path;
	}
	public Path getIos_tablet_app_root_js_directory_path() {
		return ios_tablet_app_root_js_directory_path;
	}
	public void setIos_tablet_app_root_js_directory_path(
			Path ios_tablet_app_root_js_directory_path) {
		this.ios_tablet_app_root_js_directory_path = ios_tablet_app_root_js_directory_path;
	}
	public Path getIos_tablet_app_controller_directory_path() {
		return ios_tablet_app_controller_directory_path;
	}
	public void setIos_tablet_app_controller_directory_path(
			Path ios_tablet_app_controller_directory_path) {
		this.ios_tablet_app_controller_directory_path = ios_tablet_app_controller_directory_path;
	}
	public Path getIos_tablet_app_directives_directory_path() {
		return ios_tablet_app_directives_directory_path;
	}
	public void setIos_tablet_app_directives_directory_path(
			Path ios_tablet_app_directives_directory_path) {
		this.ios_tablet_app_directives_directory_path = ios_tablet_app_directives_directory_path;
	}
	public Path getIos_tablet_app_services_directory_path() {
		return ios_tablet_app_services_directory_path;
	}
	public void setIos_tablet_app_services_directory_path(
			Path ios_tablet_app_services_directory_path) {
		this.ios_tablet_app_services_directory_path = ios_tablet_app_services_directory_path;
	}
	public Path getIos_tablet_app_lib_css_directory_path() {
		return ios_tablet_app_lib_css_directory_path;
	}
	public void setIos_tablet_app_lib_css_directory_path(
			Path ios_tablet_app_lib_css_directory_path) {
		this.ios_tablet_app_lib_css_directory_path = ios_tablet_app_lib_css_directory_path;
	}
	public Path getIos_tablet_app_lib_fonts_directory_path() {
		return ios_tablet_app_lib_fonts_directory_path;
	}
	public void setIos_tablet_app_lib_fonts_directory_path(
			Path ios_tablet_app_lib_fonts_directory_path) {
		this.ios_tablet_app_lib_fonts_directory_path = ios_tablet_app_lib_fonts_directory_path;
	}
	public Path getIos_tablet_app_lib_js_directory_path() {
		return ios_tablet_app_lib_js_directory_path;
	}
	public void setIos_tablet_app_lib_js_directory_path(
			Path ios_tablet_app_lib_js_directory_path) {
		this.ios_tablet_app_lib_js_directory_path = ios_tablet_app_lib_js_directory_path;
	}
	public Path getAndroid_tablet_app_root_directory_path() {
		return android_tablet_app_root_directory_path;
	}
	public void setAndroid_tablet_app_root_directory_path(
			Path android_tablet_app_root_directory_path) {
		this.android_tablet_app_root_directory_path = android_tablet_app_root_directory_path;
	}
	public Path getAndroid_tablet_app_lib_root_directory_path() {
		return android_tablet_app_lib_root_directory_path;
	}
	public void setAndroid_tablet_app_lib_root_directory_path(
			Path android_tablet_app_lib_root_directory_path) {
		this.android_tablet_app_lib_root_directory_path = android_tablet_app_lib_root_directory_path;
	}
	public Path getAndroid_tablet_app_css_directory_path() {
		return android_tablet_app_css_directory_path;
	}
	public void setAndroid_tablet_app_css_directory_path(
			Path android_tablet_app_css_directory_path) {
		this.android_tablet_app_css_directory_path = android_tablet_app_css_directory_path;
	}
	public Path getAndroid_tablet_app_fonts_directory_path() {
		return android_tablet_app_fonts_directory_path;
	}
	public void setAndroid_tablet_app_fonts_directory_path(
			Path android_tablet_app_fonts_directory_path) {
		this.android_tablet_app_fonts_directory_path = android_tablet_app_fonts_directory_path;
	}
	public Path getAndroid_tablet_app_views_directory_path() {
		return android_tablet_app_views_directory_path;
	}
	public void setAndroid_tablet_app_views_directory_path(
			Path android_tablet_app_views_directory_path) {
		this.android_tablet_app_views_directory_path = android_tablet_app_views_directory_path;
	}
	public Path getAndroid_tablet_app_root_js_directory_path() {
		return android_tablet_app_root_js_directory_path;
	}
	public void setAndroid_tablet_app_root_js_directory_path(
			Path android_tablet_app_root_js_directory_path) {
		this.android_tablet_app_root_js_directory_path = android_tablet_app_root_js_directory_path;
	}
	public Path getAndroid_tablet_app_controller_directory_path() {
		return android_tablet_app_controller_directory_path;
	}
	public void setAndroid_tablet_app_controller_directory_path(
			Path android_tablet_app_controller_directory_path) {
		this.android_tablet_app_controller_directory_path = android_tablet_app_controller_directory_path;
	}
	public Path getAndroid_tablet_app_directives_directory_path() {
		return android_tablet_app_directives_directory_path;
	}
	public void setAndroid_tablet_app_directives_directory_path(
			Path android_tablet_app_directives_directory_path) {
		this.android_tablet_app_directives_directory_path = android_tablet_app_directives_directory_path;
	}
	public Path getAndroid_tablet_app_services_directory_path() {
		return android_tablet_app_services_directory_path;
	}
	public void setAndroid_tablet_app_services_directory_path(
			Path android_tablet_app_services_directory_path) {
		this.android_tablet_app_services_directory_path = android_tablet_app_services_directory_path;
	}
	public Path getAndroid_tablet_app_lib_css_directory_path() {
		return android_tablet_app_lib_css_directory_path;
	}
	public void setAndroid_tablet_app_lib_css_directory_path(
			Path android_tablet_app_lib_css_directory_path) {
		this.android_tablet_app_lib_css_directory_path = android_tablet_app_lib_css_directory_path;
	}
	public Path getAndroid_tablet_app_lib_fonts_directory_path() {
		return android_tablet_app_lib_fonts_directory_path;
	}
	public void setAndroid_tablet_app_lib_fonts_directory_path(
			Path android_tablet_app_lib_fonts_directory_path) {
		this.android_tablet_app_lib_fonts_directory_path = android_tablet_app_lib_fonts_directory_path;
	}
	public Path getAndroid_tablet_app_lib_js_directory_path() {
		return android_tablet_app_lib_js_directory_path;
	}
	public void setAndroid_tablet_app_lib_js_directory_path(
			Path android_tablet_app_lib_js_directory_path) {
		this.android_tablet_app_lib_js_directory_path = android_tablet_app_lib_js_directory_path;
	}
	public Path getAndroid_phone_app_root_directory_path() {
		return android_phone_app_root_directory_path;
	}

	public void setAndroid_phone_app_root_directory_path(
			Path android_phone_app_root_directory_path) {
		this.android_phone_app_root_directory_path = android_phone_app_root_directory_path;
	}

	public Path getAndroid_phone_app_lib_root_directory_path() {
		return android_phone_app_lib_root_directory_path;
	}

	public void setAndroid_phone_app_lib_root_directory_path(
			Path android_phone_app_lib_root_directory_path) {
		this.android_phone_app_lib_root_directory_path = android_phone_app_lib_root_directory_path;
	}

	public Path getAndroid_phone_app_css_directory_path() {
		return android_phone_app_css_directory_path;
	}

	public void setAndroid_phone_app_css_directory_path(
			Path android_phone_app_css_directory_path) {
		this.android_phone_app_css_directory_path = android_phone_app_css_directory_path;
	}

	public Path getAndroid_phone_app_fonts_directory_path() {
		return android_phone_app_fonts_directory_path;
	}

	public void setAndroid_phone_app_fonts_directory_path(
			Path android_phone_app_fonts_directory_path) {
		this.android_phone_app_fonts_directory_path = android_phone_app_fonts_directory_path;
	}

	public Path getAndroid_phone_app_views_directory_path() {
		return android_phone_app_views_directory_path;
	}

	public void setAndroid_phone_app_views_directory_path(
			Path android_phone_app_views_directory_path) {
		this.android_phone_app_views_directory_path = android_phone_app_views_directory_path;
	}

	public Path getAndroid_phone_app_root_js_directory_path() {
		return android_phone_app_root_js_directory_path;
	}

	public void setAndroid_phone_app_root_js_directory_path(
			Path android_phone_app_root_js_directory_path) {
		this.android_phone_app_root_js_directory_path = android_phone_app_root_js_directory_path;
	}

	public Path getAndroid_phone_app_controller_directory_path() {
		return android_phone_app_controller_directory_path;
	}

	public void setAndroid_phone_app_controller_directory_path(
			Path android_phone_app_controller_directory_path) {
		this.android_phone_app_controller_directory_path = android_phone_app_controller_directory_path;
	}

	public Path getAndroid_phone_app_directives_directory_path() {
		return android_phone_app_directives_directory_path;
	}

	public void setAndroid_phone_app_directives_directory_path(
			Path android_phone_app_directives_directory_path) {
		this.android_phone_app_directives_directory_path = android_phone_app_directives_directory_path;
	}

	public Path getAndroid_phone_app_services_directory_path() {
		return android_phone_app_services_directory_path;
	}

	public void setAndroid_phone_app_services_directory_path(
			Path android_phone_app_services_directory_path) {
		this.android_phone_app_services_directory_path = android_phone_app_services_directory_path;
	}

	public Path getAndroid_phone_app_lib_css_directory_path() {
		return android_phone_app_lib_css_directory_path;
	}

	public void setAndroid_phone_app_lib_css_directory_path(
			Path android_phone_app_lib_css_directory_path) {
		this.android_phone_app_lib_css_directory_path = android_phone_app_lib_css_directory_path;
	}

	public Path getAndroid_phone_app_lib_fonts_directory_path() {
		return android_phone_app_lib_fonts_directory_path;
	}

	public void setAndroid_phone_app_lib_fonts_directory_path(
			Path android_phone_app_lib_fonts_directory_path) {
		this.android_phone_app_lib_fonts_directory_path = android_phone_app_lib_fonts_directory_path;
	}

	public Path getAndroid_phone_app_lib_js_directory_path() {
		return android_phone_app_lib_js_directory_path;
	}

	public void setAndroid_phone_app_lib_js_directory_path(
			Path android_phone_app_lib_js_directory_path) {
		this.android_phone_app_lib_js_directory_path = android_phone_app_lib_js_directory_path;
	}

	
	public Path getAngular_root_directory_path() {
		return angular_root_directory_path;
	}

	public void setAngular_root_directory_path(Path angular_root_directory_path) {
		this.angular_root_directory_path = angular_root_directory_path;
	}

	public Path getAndroid_root_directory_path() {
		return android_root_directory_path;
	}

	public void setAndroid_root_directory_path(Path android_root_directory_path) {
		this.android_root_directory_path = android_root_directory_path;
	}

	public Path getAndroid_tablet_root_directory_path() {
		return android_tablet_root_directory_path;
	}

	public void setAndroid_tablet_root_directory_path(
			Path android_tablet_root_directory_path) {
		this.android_tablet_root_directory_path = android_tablet_root_directory_path;
	}

	public Path getAndroid_phone_root_directory_path() {
		return android_phone_root_directory_path;
	}

	public void setAndroid_phone_root_directory_path(
			Path android_phone_root_directory_path) {
		this.android_phone_root_directory_path = android_phone_root_directory_path;
	}

	public Path getIos_root_directory_path() {
		return ios_root_directory_path;
	}

	public void setIos_root_directory_path(Path ios_root_directory_path) {
		this.ios_root_directory_path = ios_root_directory_path;
	}

	public Path getIos_tablet_root_directory_path() {
		return ios_tablet_root_directory_path;
	}

	public void setIos_tablet_root_directory_path(
			Path ios_tablet_root_directory_path) {
		this.ios_tablet_root_directory_path = ios_tablet_root_directory_path;
	}

	public Path getIos_phone_root_directory_path() {
		return ios_phone_root_directory_path;
	}

	public void setIos_phone_root_directory_path(Path ios_phone_root_directory_path) {
		this.ios_phone_root_directory_path = ios_phone_root_directory_path;
	}

	public Path getWindows_root_directory_path() {
		return windows_root_directory_path;
	}

	public void setWindows_root_directory_path(Path windows_root_directory_path) {
		this.windows_root_directory_path = windows_root_directory_path;
	}

	@Override
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{
		
		//System.out.println("In GpAngularDirectoryGenWorker - generate_code");
		
		
		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths();
		
	}
	
	public void set_up_paths_for_landing_page() throws Exception{
		/*
		if(!android_created){
			android_created = true;
			this.create_base_android_directories();
		}
		if(!ios_created){
			ios_created = true;
			this.create_base_ios_directories();
		}
		
		if(!desktop_created){
			desktop_created = true;
			this.create_base_windows_directories();
		}
		*/
	}
	
	private void set_up_paths() throws Exception {
		this.set_up_angular_base();
		
		ArrayList<GpScreenX> screen_list = this.screen_dao.find_by_project_id(this.the_project.getId());
		for(GpScreenX screenX : screen_list) {
			if(screenX.getClient_device_type().equals(GpGenConstants.GpClientScreenType_desktop) && !desktop_created){
				desktop_created = true;
				this.create_base_windows_directories();
			}
			if(screenX.getClient_device_type_os_name().equals(GpGenConstants.GpClientOS_ANDROID) && !android_created){
				android_created = true;
				this.create_base_android_directories();
			}
			if(screenX.getClient_device_type_os_name().equals(GpGenConstants.GpClientOS_IOS) && !ios_created){
				ios_created = true;
				this.create_base_ios_directories();
			}
		}
		if(!desktop_created || !android_created || !ios_created){
			//there are no screens in the project, but are there any modules?
			List<GpModuleProperties> modules = get_generation_service().getNot_default_activity_worker().getModule_properties_list();
			for(GpModuleProperties module : modules){
				GpModuleAngularViews[] views = module.getClient_meta_data().getJava_script().getAngular_js().getResources();
				if((views != null || views.length!=0) && !desktop_created){
					this.create_base_windows_directories();
					desktop_created = true;
				}
				GpModuleMobileInfo mobile_info = module.getClient_meta_data().getJava_script().getAngular_js().getMobile_info();
				if(mobile_info != null){
					if(!android_created){
						this.create_base_android_directories();
						android_created = true;
					}
					if(!ios_created){
						this.create_base_ios_directories();
						ios_created = true;
					}
				}
			}
		}
		
	}
	
	/*please note windows is synonymous with desktop - Dan Castillo 03/09/2015  */
	private void create_base_windows_directories() throws Exception{
		this.desktop_support.set_gen_worker(this);
		this.desktop_support.build_directories(this.file_separator,  this.base_configs);
	}
	
	private void create_base_ios_directories() throws Exception{
		this.ios_support.set_gen_worker(this);
		this.ios_support.build_directories(this.file_separator,  this.base_configs);
		
	}
	
	private void create_base_android_directories() throws Exception{
		this.android_support.set_gen_worker(this);
		this.android_support.build_directories(this.file_separator,  this.base_configs);
	}
	
	private void set_up_angular_base() throws Exception{
		
		String config_name = "";
		String[] tokens;
		
		Path client_root_path = (Path)this.derived_configs.get(
					GpGenConstants.GEN_CLIENT_DIRECTORY_NAME_PATH).getObject_value();
		
		
		
		String gen_client_angular_root_directory_name =  this.base_configs.get(
				GpGenConstants.GEN_CLIENT_ANGULAR_ROOT_DIRECTORY_NAME).getValue();
		
		tokens = this.tokenize_string(
				gen_client_angular_root_directory_name, null);

		config_name = this.build_name_from_tokens(tokens);

		config_name = GpGenConstants.GEN_CLIENT_ANGULAR_ROOT_DIRECTORY_NAME
								+ GpGenConstants.DEFAULT_CONFIG_PATH_EXTENSION;

		Path angular_root_directory_path = Paths.get(client_root_path.toString() + this.file_separator
				+ tokens[0] );
		Files.createDirectory(angular_root_directory_path);
		this.angular_root_directory_path = angular_root_directory_path;

		GpArchitypeConfigurations angular_root_directory_path_config;
		angular_root_directory_path_config = new GpArchitypeConfigurations();
		
		angular_root_directory_path_config.setName(config_name);
		angular_root_directory_path_config.setObject_value(angular_root_directory_path);
		this.derived_configs.put(config_name, angular_root_directory_path_config);
		
	}

}
