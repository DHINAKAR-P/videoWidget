package com.npb.gp.gen.services.server.node;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.npb.gb.utils.GpChildRelationshipInfo;
import com.npb.gb.utils.GpRelationshipInfo;
import com.npb.gp.constants.GpBaseConstants;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.gen.constants.GpBaseFlowConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.dao.mysql.GpGenFlowDao;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.services.GpBaseGenerationService;
import com.npb.gp.gen.workers.GpNotDefaultActivityGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpConfigGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpDataAccessMySQLGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressControllerGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressDaoGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressMainFileWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressRoutesGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressServiceGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpExpressSwaggerGenWorker;
import com.npb.gp.gen.workers.server.node.express.GpNodeExpressDirectoryWorker;
import com.npb.gp.gen.workers.server.node.express.GpPackageGenWorker;
import com.npb.gp.gen.workers.server.sql.GpSqlDDLWorker;

@Service("GpServerNodeExpressGenService")
public class GpServerNodeExpressGenService extends GpBaseGenerationService{
	public static String server_port = "9090";
	
	private GpNodeExpressDirectoryWorker the_directory_worker;
	private GpPackageGenWorker the_package_worker;
	private GpConfigGenWorker the_config_worker;
	private GpDataAccessMySQLGenWorker the_db_access_mysql_worker;
	private GpSqlDDLWorker the_ddl_worker;
	private GpGenFlowDao flow_dao;
	private GpExpressDaoGenWorker the_dao_worker;
	private GpExpressServiceGenWorker the_service_worker;
	private GpExpressControllerGenWorker the_controller_worker;
	private GpExpressRoutesGenWorker the_routes_worker;
	private GpExpressMainFileWorker the_main_worker;
	private GpExpressSwaggerGenWorker the_swagger_worker;
	private GpNotDefaultActivityGenWorker not_default_activity_worker;

	private Map<Long, JSONArray> relationships_map;

	private Map<Long, GpRelationshipInfo> relation_between_activities;
	
	@SuppressWarnings("unchecked")
	@Override
	public void generate(IGpGenManager gen_manager) throws Exception {
		System.out.println("Generating Node Server");
		this.check_project_type(gen_manager);
		// TODO Auto-generated method stub
		GpArchitypeConfigurations activities_prop = gen_manager
				.getDerived_configs().get(GpGenConstants.PROJECT_ACTIVITIES);
		ArrayList<GpActivity> the_activities = 
					(ArrayList<GpActivity>) activities_prop.getObject_value();
		this.prep_workers(gen_manager);
		this.set_up_directories(gen_manager);
		relation_between_activities = this.handle_relations_between_activities(the_activities);
		for(GpActivity an_activity : the_activities){						
			
			if (an_activity.getModule_type() != null && an_activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				Path base_path = Paths.get(gen_manager.getBase_configs().get("base_generation_directory").getValue());
				String project_folder = gen_manager.get_project().getName() + "_" + gen_manager.get_user().getId();											
				this.not_default_activity_worker.setModule_final_directory(base_path.toString(),project_folder,"server");
				this.not_default_activity_worker.import_server_module(an_activity, this);
			} else {
				if(an_activity.getPrimary_noun() != null){
					this.process_flow(gen_manager, an_activity);
				}
			}
		}
		this.process_global_components(gen_manager);
		the_routes_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
		
		the_main_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
		
		//if swagger?
		the_swagger_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
		
	}
	
	private void set_up_directories(IGpGenManager gen_manager) throws Exception{
		the_directory_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
	}
	
	private void prep_workers(IGpGenManager gen_manager) throws Exception{
		relationships_map = this.handle_relationships(gen_manager.get_project().getProject_nouns());
		the_ddl_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		
		the_db_access_mysql_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_db_access_mysql_worker.setGen_service(this);
		
		the_package_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_package_worker.setGen_service(this);
		
		the_config_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_config_worker.setGen_service(this);
		
		the_directory_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_directory_worker.setGen_service(this);
		
		the_dao_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_dao_worker.setGen_service(this);
		
		the_service_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_service_worker.setGen_service(this);
		
		the_controller_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_controller_worker.setGen_service(this);
		
		the_routes_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_routes_worker.setGen_service(this);
		
		the_main_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_main_worker.setGen_service(this);
		
		the_swagger_worker.prep_derived_values(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs());
		the_swagger_worker.setGen_service(this);
		
		
	}
	
	private void process_global_components(IGpGenManager gen_manager) throws Exception{
		the_ddl_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager,getNot_default_activity_worker().getModule_properties_list());
		//TODO If the data access is MySQL
		the_db_access_mysql_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
		
		the_config_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
		
		the_package_worker.generate_code(gen_manager.get_project(), 
				gen_manager.getBase_configs(), gen_manager.getDerived_configs(), null, gen_manager);
	}

	private void process_flow(IGpGenManager gen_manager, GpActivity an_activity) throws Exception{
		long master_flow_id = an_activity.getMaster_flow_id();
		ArrayList<GpFlowControl> a_flow = this.flow_dao.find_flow_by_id(master_flow_id);
		int i = 1;
		for(GpFlowControl a_flow_comp : a_flow){
			if(a_flow_comp.getType().equals(GpGenConstants.GpGenerationType_Server)){
				Long the_count_for_seq = this
						.flow_dao.get_count_for_one_seq(master_flow_id,
								GpGenConstants.GpGenerationType_Server, i);
				
				if(the_count_for_seq == 0){
					/*this means that it is a child of a previous sequence and was handled in the 
					 * the while loop below and does not need to be handled again it would 
					 * be more elegant if it was handled in the loop above that  creates
					 * the server flows that this FOR loop iterates through _ Dan Castillo 11/26/2014
					 *   
					 * 
					 */
					continue;
				}
				//System.out.println("The NUMBER OF SUB_STEPS for FOR STEP " + i + " is: " + the_count_for_seq
				//		+ " the flow_comp is: " + flow_comp.getComponent_type());
				int x = 1;
				while(x <= the_count_for_seq){
					GpFlowControl the_flow =  this.flow_dao.find_a_server_flow(master_flow_id, i, x);
					//System.out.println("the_flow.getComponent_type() is: " + the_flow.getComponent_type());
					if(the_flow.getComponent_type().equals(GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpController)){
						//System.out.println("GOT ME A CONTROLLER");
						// NOW SEND IT TO THE CONTROLLER WORKER!
						
						the_controller_worker.generate_code_by_activity(an_activity, gen_manager.get_project(),gen_manager.getBase_configs(),
								gen_manager.getDerived_configs(), the_flow, gen_manager);

						x++;
						continue;
					}

					if(the_flow.getComponent_type().equals(GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpService)){
						// NOW SEND IT TO THE SERVICE WORKER!
						
						the_service_worker.generate_code_by_activity(an_activity, gen_manager.get_project(),gen_manager.getBase_configs(),
								gen_manager.getDerived_configs(), the_flow, gen_manager);
						x++;
						continue;
					}

					if(the_flow.getComponent_type().equals(GpBaseFlowConstants.NodeJs_Express_Flow_Component_type_GpDao)){
						the_dao_worker.generate_code_by_activity(an_activity, gen_manager.get_project(),gen_manager.getBase_configs(),
									gen_manager.getDerived_configs(), the_flow, gen_manager);

						x++;
						continue;
					}

					x++;
				}
				
				i++;
			}
		}
	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					//System.out.println("handle_relationships");
					//System.out.println(noun.getName());
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						JSONArray parent_nouns = relationships_map.get(noun_id);
						if(parent_nouns == null){
							//System.out.println("null json array parents");
							parent_nouns = new JSONArray();
						}
						JSONObject json_parent = new JSONObject();
						json_parent.put("type", "ONE_TO_ONE");
						json_parent.put("Noun_name", noun.getName());
						json_parent.put("Noun_id", noun.getId());
						parent_nouns.put(json_parent);
						//System.out.println(parent_nouns.length());
						relationships_map.put(noun_id, parent_nouns);
					}
					continue;
				}
				if(sub_type.equals(GpNounConstants.SUB_TYPE_LIST)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						String type = relationship_json.getString(NounAttrRelationshipJson.KEY_TYPE);
						if(type.equals("Noun")){
							long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
							JSONArray parent_nouns = relationships_map.get(noun_id);
							if(parent_nouns == null){
								//System.out.println("null json array parents");
								parent_nouns = new JSONArray();
							}
							JSONObject json_parent = new JSONObject();
							json_parent.put("type", "ONE_TO_MANY");
							json_parent.put("Noun_name", noun.getName());
							json_parent.put("Noun_id", noun.getId());
							parent_nouns.put(json_parent);
							//System.out.println(parent_nouns.length());
							relationships_map.put(noun_id, parent_nouns);
						}
					}
					continue;
				}
			}
		}
		return relationships_map;
	}
	
	private Map<Long, GpRelationshipInfo> handle_relations_between_activities(ArrayList<GpActivity> the_activities) throws Exception {
		Map<Long, GpRelationshipInfo> map = new HashMap<>();
		List<GpActivity> the_activities2 = new ArrayList<>();
		the_activities2.addAll(the_activities);
		for(GpActivity activity : the_activities){
			if (activity.getModule_type() != null && activity.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
				continue;
			}
			if(this.getRelationships_map() != null && activity.getPrimary_noun() != null){
				JSONArray json_array_parent = this.getRelationships_map().get(activity.getPrimary_noun().getId());
				GpNoun noun = activity.getPrimary_noun();
				if(json_array_parent != null){
					for(int i =0; i<json_array_parent.length();i++){
						JSONObject json_parent = json_array_parent.getJSONObject(i);
						long noun_id = json_parent.getLong("Noun_id");
						for(GpActivity activity2 : the_activities2){
							if (activity2.getModule_type() != null && activity2.getModule_type().equals(GpBaseConstants.GpActivity_Mode_Not_Default)) {
								continue;
							}
							if(activity2.getPrimary_noun().getId() == noun_id){
								GpRelationshipInfo rel_info = map.get(activity2.getId());
								if(rel_info == null)
									rel_info = new GpRelationshipInfo();
								GpChildRelationshipInfo child = new GpChildRelationshipInfo(); 
								child.setNoun(noun);
								child.setActivity(activity);
								rel_info.add_child(child);
								map.put(activity2.getId(), rel_info);
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	//Getters and Setters
	
	public GpNodeExpressDirectoryWorker getThe_directory_worker() {
		return the_directory_worker;
	}
	@Resource(name="GpNodeExpressDirectoryWorker")
	public void setThe_directory_worker(GpNodeExpressDirectoryWorker the_directory_worker) {
		this.the_directory_worker = the_directory_worker;
	}

	public GpPackageGenWorker getThe_package_worker() {
		return the_package_worker;
	}
	
	@Resource(name="GpPackageGenWorker")
	public void setThe_package_worker(GpPackageGenWorker the_package_worker) {
		this.the_package_worker = the_package_worker;
	}

	public GpConfigGenWorker getThe_config_worker() {
		return the_config_worker;
	}
	@Resource(name="GpConfigGenWorker")
	public void setThe_config_worker(GpConfigGenWorker the_config_worker) {
		this.the_config_worker = the_config_worker;
	}

	public GpDataAccessMySQLGenWorker getThe_db_access_mysql_worker() {
		return the_db_access_mysql_worker;
	}
	@Resource(name="GpDataAccessMySQLGenWorker")
	public void setThe_db_access_mysql_worker(GpDataAccessMySQLGenWorker the_db_access_mysql_worker) {
		this.the_db_access_mysql_worker = the_db_access_mysql_worker;
	}
	@Resource(name="GpSqlDDLWorker")
	public void setThe_ddl_worker(GpSqlDDLWorker the_ddl_worker) {
		this.the_ddl_worker = the_ddl_worker;
	}
	
	public GpSqlDDLWorker getThe_ddl_worker() {
		return the_ddl_worker;
	}
	
	public GpGenFlowDao getFlow_dao() {
		return flow_dao;
	}
	
	@Resource(name="GpGenFlowDao")
	public void setFlow_dao(GpGenFlowDao flow_dao) {
		this.flow_dao = flow_dao;
	}
	
	public GpExpressDaoGenWorker getThe_dao_worker() {
		return the_dao_worker;
	}
	
	@Resource(name="GpExpressDaoGenWorker")
	public void setThe_dao_worker(GpExpressDaoGenWorker the_dao_worker) {
		this.the_dao_worker = the_dao_worker;
	}
	
	public GpExpressServiceGenWorker getThe_service_worker() {
		return the_service_worker;
	}
	
	@Resource(name="GpExpressServiceGenWorker")
	public void setThe_service_worker(GpExpressServiceGenWorker the_service_worker) {
		this.the_service_worker = the_service_worker;
	}
	
	public GpExpressControllerGenWorker getThe_controller_worker() {
		return the_controller_worker;
	}
	
	@Resource(name="GpExpressControllerGenWorker")
	public void setThe_controller_worker(GpExpressControllerGenWorker the_controller_worker) {
		this.the_controller_worker = the_controller_worker;
	}
	
	public GpExpressRoutesGenWorker getThe_routes_worker() {
		return the_routes_worker;
	}
	
	@Resource(name="GpExpressRoutesGenWorker")
	public void setThe_routes_worker(GpExpressRoutesGenWorker the_routes_worker) {
		this.the_routes_worker = the_routes_worker;
	}
	
	public GpExpressMainFileWorker getThe_main_worker() {
		return the_main_worker;
	}
	
	@Resource(name="GpExpressMainFileWorker")
	public void setThe_main_worker(GpExpressMainFileWorker the_main_worker) {
		this.the_main_worker = the_main_worker;
	}
	
	public GpExpressSwaggerGenWorker getThe_swagger_worker() {
		return the_swagger_worker;
	}
	
	@Resource(name="GpExpressSwaggerGenWorker")
	public void setThe_swagger_worker(GpExpressSwaggerGenWorker the_swagger_worker) {
		this.the_swagger_worker = the_swagger_worker;
	}
	
	public GpNotDefaultActivityGenWorker getNot_default_activity_worker() {
		return not_default_activity_worker;
	}
	
	@Resource(name="GpNotDefaultActivityGenWorker")
	public void setNot_default_activity_worker(GpNotDefaultActivityGenWorker not_default_activity_worker) {
		this.not_default_activity_worker = not_default_activity_worker;
	}
	
	public Map<Long, JSONArray> getRelationships_map() {
		return relationships_map;
	}
	
	public Map<Long, GpRelationshipInfo> getRelation_between_activities() {
		return relation_between_activities;
	}
}
