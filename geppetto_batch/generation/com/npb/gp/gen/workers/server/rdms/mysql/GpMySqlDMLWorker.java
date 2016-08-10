package com.npb.gp.gen.workers.server.rdms.mysql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.GpBatchGen;
import com.npb.gp.dao.mysql.GpNounDao;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpArchitypeConfigurations;
import com.npb.gp.domain.core.GpFlowControl;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpProject;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpGenConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.constants.json.nounattr.relationships.NounAttrRelationshipJson;
import com.npb.gp.gen.interfaces.managers.IGpGenManager;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.GpServiceVerbGenInfo;
import com.npb.gp.gen.workers.GpRdbmsBaseDMLWorker;
import com.npb.gp.gen.workers.server.sql.support.dml.GpSqlQueriesGenSupport;
/**
 *
 * @author Dan Castillo
 * Date Created: 01/10/2015</br>
 * @since .2</p>
 *
 * the purpose of tis class is to generate DML statements that are specific</br>
 * to the MySql RDBMS </p>
 */
@Component("GpMySqlDMLWorker")
public class GpMySqlDMLWorker extends GpRdbmsBaseDMLWorker {

	private String mysql_version;
	private GpSqlQueriesGenSupport dml_gen_support;
	private GpNounDao noun_dao;



	public GpSqlQueriesGenSupport getDml_gen_support() {
		return dml_gen_support;
	}
	@Resource(name="GpSqlQueriesGenSupport")
	public void setDml_gen_support(GpSqlQueriesGenSupport dml_gen_support) {
		this.dml_gen_support = dml_gen_support;
	}

	public String getMysql_version() {
		return mysql_version;
	}

	public void setMysql_version(String mysql_version) {
		this.mysql_version = mysql_version;
	}


	@Override
	/**
	 * Generates ALL DML statements for all activities in the project
	 */
	 public void generate_code(GpProject the_project,
			 	HashMap<String,GpArchitypeConfigurations> base_configs,
			 	HashMap<String, GpArchitypeConfigurations> derived_configs,
			 						GpFlowControl the_flow, IGpGenManager gen_manager) throws Exception{

		long project_id = gen_manager.get_project().getId();
		long user_id = gen_manager.get_user().getId();
		String username = gen_manager.get_user().getUsername();

		GpArchitypeConfigurations activities_prop = derived_configs
									.get(GpGenConstants.PROJECT_ACTIVITIES);

		ArrayList<GpActivity> the_activities =
				(ArrayList<GpActivity>) activities_prop.getObject_value();

		//gen_manager.update_job_status(project_id, user_id, username, "gen_dml_by_activity-GpMySqlDMLWorker", "gen_processing");
		for(GpActivity an_activity : the_activities){
			this.do_generation(an_activity);
		}


	}


	@Override
	public void generate_code_by_activity(GpActivity activity, GpProject the_project,
		 							HashMap<String,GpArchitypeConfigurations> base_configs,
		 							HashMap<String, GpArchitypeConfigurations> derived_configs,
			 											GpFlowControl the_flow, IGpGenManager gen_manager)	throws Exception{


		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;


		this.set_up_paths_and_templates();
		this.do_generation(activity);

	}


	@Override
	public void prep_derived_values(GpProject the_project,
		HashMap<String,GpArchitypeConfigurations> base_configs,
		HashMap<String, GpArchitypeConfigurations> derived_configs) throws Exception{

		super.base_configs = base_configs;
		super.derived_configs = derived_configs;
		super.the_project = the_project;

		this.set_up_paths_and_templates();
	}

	public ArrayList<GpBaseSqlDto> get_dml_statements_for_activity(GpActivity activity) throws Exception{
		List<GpNoun> nouns = noun_dao.find_by_project_id(the_project.getId());
		Map<Long, JSONArray> relationships_map = this.handle_relationships(nouns);
		ArrayList<GpVerb> the_verbs = activity.getTheverbs();
		ArrayList<GpBaseSqlDto> dto_list = new ArrayList<GpBaseSqlDto>();
		List<String> generated_verbs = new ArrayList<String>();
		for(GpVerb verb : the_verbs){
			if(verb.getAction_on_data().equals("GpGetAllValues") && !generated_verbs.contains("GpGetAllValues")){
				GpBaseSqlDto dto = this.dml_gen_support.get_all_values_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpGetAllValues");
				continue;
			}
			if(verb.getAction_on_data().equals("GpGetNounById") && !generated_verbs.contains("GpGetNounById")){
				GpBaseSqlDto dto = this.dml_gen_support.get_nound_by_id_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpGetNounById");
				continue;
			}
			if(verb.getAction_on_data().equals("GpSearchForUpdate") && !generated_verbs.contains("GpSearchForUpdate")){
				GpBaseSqlDto dto = this.dml_gen_support.search_for_update_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpSearchForUpdate");
				continue;
			}
			if(verb.getAction_on_data().equals("GpCreate") && !generated_verbs.contains("GpCreate")){
				GpBaseSqlDto dto = this.dml_gen_support.create_dml_stmt(activity, relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpCreate");
				continue;
			}

			if(verb.getAction_on_data().equals("GpUpdate") && !generated_verbs.contains("GpUpdate")
					||verb.getAction_on_data().equals(GpBaseVerbsConstants.GpTakePhoto)
					||verb.getAction_on_data().equals(GpBaseVerbsConstants.GpRecordVideo)){
				if(!generated_verbs.contains("GpUpdate")){
				GpBaseSqlDto implicit_dto = this.dml_gen_support.search_for_update_dml_stmt(activity);
				dto_list.add(implicit_dto);
				GpBaseSqlDto dto = this.dml_gen_support.update_dml_stmt(activity, relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpSearchForUpdate");
				generated_verbs.add("GpUpdate");
				continue;
			}
		}
			if(verb.getAction_on_data().equals("GpDelete") && !generated_verbs.contains("GpDelete")){
				GpBaseSqlDto dto = this.dml_gen_support.delete_dml_stmt(activity);
				dto_list.add(dto);
				generated_verbs.add("GpDelete");
				continue;
			}
			if(verb.getAction_on_data().equals("GpSearch") && !generated_verbs.contains("GpSearch")){
				GpBaseSqlDto dto = this.dml_gen_support.search_dml_stmt(activity, relationships_map);
				dto_list.add(dto);
				generated_verbs.add("GpSearch");
				continue;
			}

		}
		
		JSONArray json_parents = relationships_map.get(activity.getPrimary_noun().getId());
		if(json_parents != null){
			//get by parent id
			GpBaseSqlDto dto_get_byparent_id = this.dml_gen_support.get_nound_by_parent_id_dml_stmt(activity, relationships_map);
			dto_list.add(dto_get_byparent_id);
			//delete by parent id
			GpBaseSqlDto dto_delete_by_parent_id = this.dml_gen_support.delete_nound_by_parent_id_dml_stmt(activity, relationships_map);
			dto_list.add(dto_delete_by_parent_id);
		}
		return dto_list;

	}
	
	private Map<Long, JSONArray> handle_relationships(List<GpNoun> nouns) throws JSONException {
		Map<Long, JSONArray> relationships_map = new HashMap<>();
		for(GpNoun noun : nouns){
			List<GpNounAttribute> attribs = noun.getNounattributes();
			for(GpNounAttribute attr : attribs){
				String sub_type = attr.getSubtype();
				if(sub_type.equals(GpNounConstants.SUB_TYPE_NOUN)){
					if(attr.getRelationships() != null && !attr.getRelationships().isEmpty()){
						JSONObject relationship_json = new JSONObject(attr.getRelationships());
						long noun_id = relationship_json.getLong(NounAttrRelationshipJson.KEY_NOUN_ID);
						JSONArray parent_nouns = relationships_map.get(noun_id);
						if(parent_nouns == null)
							parent_nouns = new JSONArray();
						JSONObject json_parent = new JSONObject();
						json_parent.put("type", "ONE_TO_ONE");
						json_parent.put("Noun_name", noun.getName());
						parent_nouns.put(json_parent);
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
							if(parent_nouns == null)
								parent_nouns = new JSONArray();
							JSONObject json_parent = new JSONObject();
							json_parent.put("type", "ONE_TO_MANY");
							json_parent.put("Noun_name", noun.getName());
							parent_nouns.put(json_parent);
							relationships_map.put(noun_id, parent_nouns);
						}
					}
					continue;
				}
			}
		}
		return relationships_map;
	}
	
	private void do_generation(GpActivity activity) throws Exception{
		/*
		 * this should never happen as workers should not get an activity
		 * with out a primary noun since you can't generate anything without
		 * a primary noun - this check was put in here before the check at the
		 * service level was put in - Dan Castillo 04/10/2015
		 *
		 */
		if(activity.getPrimary_noun() == null){  //it should never get here

			return;
		}
		//this.dml_gen_support.get_all_values_dml_stmt(activity);
		//this.dml_gen_support.get_nound_by_id_dml_stmt(activity);
		//this.dml_gen_support.search_for_update_dml_stmt(activity);
		//this.dml_gen_support.create_dml_stmt(activity);
		//this.dml_gen_support.update_dml_stmt(activity);
		//this.dml_gen_support.delete_dml_stmt(activity);


	}


	private void set_up_paths_and_templates() throws Exception {

		/*
		 *
		 * this does not apply at this time - since all DML
		 * statements are held in resoruce bundles
		 *
		 */
	}

	public GpNounDao getNoun_dao() {
		return noun_dao;
	}
	
	@Resource(name="GpNounDao")
	public void setNoun_dao(GpNounDao noun_dao) {
		this.noun_dao = noun_dao;
	}


}
