package com.npb.gp.gen.constants;
/**
 * 
 *  Modified by : KUMARESAN PERUMAL
 *  Modified Date : 03/09/2016
 *  <p> here i added server language and server framework for spring boot technology</p>
 *
 */



public class GpGenConstants {
	



	public static String TECH_PROPERTY_LIST = "tech_property_list";  //returns ArrayList<GpTechProperties>
	
	
	
	/*
	 * 
	 * The section handles the keys for dealing with the names
	 * of directories used in the code generation process
	 * 
	 */
	/* ********************  section start ****************************   */
	public static String SERVER_SOURCE_ROOT_PATH = "server_src_root_path";
	public static String GEN_SERVER_DIRECTORY_NAME_PATH = "gen_server_directory_name";
	public static String GEN_CLIENT_DIRECTORY_NAME_PATH = "gen_client_directory_name";
	public static String DEFAULT_CONFIG_PATH_EXTENSION = "_config_path";
	public static String APP_BASE_PACKAGE = "app_base_package";
	public static String GEN_CLIENT_ANGULAR_ROOT_DIRECTORY_NAME = "gen_client_angular_directory_name";
	public static String GEN_CLIENT_ANDROID_DIRECTORY_NAME = "gen_client_android_directory_name";
	public static String GEN_CLIENT_IOS_DIRECTORY_NAME = "gen_client_ios_directory_name";
	public static String GEN_CLIENT_DESKTOP_DIRECTORY_NAME = "gen_client_desktop_directory_name";
	public static String GEN_CLIENT_PHONE_DIRECTORY_NAME = "gen_client_phone_directory_name";
	public static String GEN_CLIENT_TABLET_DIRECTORY_NAME = "gen_client_tablet_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_ROOT_DIRECTORY_NAME = "gen_client_angular_app_root_directory_name";
	public static String GEN_CLIENT_APP_CSS_DIRECTORY_NAME = "gen_client_app_css_directory_name";
	public static String GEN_CLIENT_APP_FONTS_DIRECTORY_NAME = "gen_client_app_fonts_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_ROOT_JS_DIRECTORY_NAME = "gen_client_angular_app_root_js_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_ROOT_VIEWS_DIRECTORY_NAME = "gen_client_angular_app_root_views_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_CONTROLLER_DIRECTORY_NAME = "gen_client_angular_app_controller_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_DIRECTIVES_DIRECTORY_NAME = "gen_client_angular_app_directives_directory_name";
	public static String GEN_CLIENT_ANGULAR_APP_SERVICES_DIRECTORY_NAME = "gen_client_angular_app_services_directory_name";
	public static String GEN_CLIENT_ANGULAR_LIB_ROOT_DIRECTORY_NAME = "gen_client_angular_lib_root_directory_name";
	public static String GEN_CLIENT_APP_LIB_CSS_DIRECTORY_NAME = "gen_client_app_lib_css_directory_name";
	public static String GEN_CLIENT_APP_LIB_FONTS_DIRECTORY_NAME = "gen_client_app_lib_fonts_directory_name";
	public static String GEN_CLIENT_APP_LIB_JS_DIRECTORY_NAME = "gen_client_app_lib_js_directory_name";
	
	public static String GEN_SERVER_NODE_EXPRESS_CONFIG_DIRECTORY_NAME = "gen_server_node_express_config_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_CONTROLLERS_DIRECTORY_NAME = "gen_server_node_express_controllers_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_DAOS_DIRECTORY_NAME = "gen_server_node_express_daos_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_MODELS_DIRECTORY_NAME = "gen_server_node_express_models_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_ROUTES_DIRECTORY_NAME = "gen_server_node_express_routes_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_SERVICES_DIRECTORY_NAME = "gen_server_node_express_services_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_SQL_QUERIES_DIRECTORY_NAME = "gen_server_node_express_sql_queries_directory_name";
	public static String GEN_SERVER_NODE_EXPRESS_ROUTERS_DIRECTORY_NAME = "gen_server_node_express_routers_directory_name";
	
	
	/* ********************  section end ****************************   */
	
	/*
	 * 
	 * The section handles all tech property types
	 * 
	 * 
	 */
	/* ********************  section start ****************************   */
	public static String GpServerLanguage = "GpServerLanguage";
	public static String GpServerDevFramework = "GpServerDevFramework";
	public static String GpServerRunTime = "GpServerRunTime";
	public static String GpServerOS = "GpServerOS";
	public static String GpServerDBMS = "GpServerDBMS";
	public static String GpServerCodePattern = "GpServerCodePattern";
	public static String GpServerDeploymentEnvironment = "GpServerDeploymentEnvironment";
	public static String GpClientCodePattern = "GpClientCodePattern";
	public static String GpClientDevFramework = "GpClientDevFramework";
	public static String GpClientLanguage = "GpClientLanguage";
	public static String GpBrowser = "GpBrowser";
	public static String GpClientOS = "GpClientOS"; 
	public static String GpCssFramework = "GpCssFramework";
	
	/* ********************  section end ****************************   */
	
	/*
	 * 
	 * The section handles the keys for dealing with the names
	 * of screen types
	 * 
	 */
	/* ********************  section start ****************************   */
	public static String GpClientScreenType_phone = "phone"; 
	public static String GpClientScreenType_tablet = "tablet"; 	
	public static String GpClientScreenType_desktop = "pc"; 	
	
	/* ********************  section end ****************************   */
	
	/*
	 * 
	 * The section handles the keys for dealing with the names
	 * of client OS types
	 * 
	 */
	/* ********************  section start ****************************   */
	public static String GpClientOS_ANDROID = "android"; 
	public static String GpClientOS_IOS = "iOS";
	public static String GpClientOS_WINDOWS = "windows"; 	
	
	
	/* ********************  section end ****************************   */
	
	/* dealing with verbs    */
	public static String VERB_DEFINITIONS = "verb_definitions";
	
	/* dealing with authoriations    */
	public static String AUTHORIZATION_DEFINITIONS = "authorization_definitions";

	
	
	/* these are per project */
	
	public static String WEB_INF_PATH = "web_inf_path";
	public static String RESOURCES_PATH = "resources_path";
	public static String LIB_PATH = "lib_path";
	public static String SQL_QUERIES_PATH = "sql_queries_path";
	public static String SPRING_BOOT_RESOURCE_PATH = "spring_boot_resources_path";
	
	
	public static String PROJECT_ACTIVITIES = "activities";
	
	public static String GpGenerationType_Mixed =  "mixed";
	public static String GpGenerationType_Server =  "server";
	public static String GpGenerationType_Client =  "client";

	
	public static String GpServerDevLanguage_JAVA_7= "java 7";
	public static String GpServerDevLanguage_JAVA_8= "java 8";
	public static String GpServerDevLanguage_JS= "java_script";
	public static String GpServerDevLanguage_Node= "Node.js";
	
	
	public static String GpSeverDevFramework_Spring = "spring";
	public static String GpServerDevFramework_Express= "Express";
	public static String GpServerDevFramework_Spring_Boot= "Spring Boot";
	
	public static String GpSeverClientDevFramework_AngularJS = "angularJS";
	
	public static final Object GpDB_MYSQL = "MySql";



	public static final String APP_SERVICES_PACKAGE = "services_package";

}
