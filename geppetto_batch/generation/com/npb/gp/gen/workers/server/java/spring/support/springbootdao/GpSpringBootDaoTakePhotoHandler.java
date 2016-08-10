package com.npb.gp.gen.workers.server.java.spring.support.springbootdao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.constants.GpNounConstants;
import com.npb.gp.gen.domain.java.GpJavaMethodDescription;
import com.npb.gp.gen.interfaces.springboot.dao.IGpSpringBootDaoVerbGenSupport;
import com.npb.gp.gen.util.dto.GpBaseSqlDto;
import com.npb.gp.gen.util.dto.springboot.GpSpringBootDaoVerbGenInfo;
import com.npb.gp.gen.workers.server.java.spring.springboot.GpSpringBootJpaDaoGenWorker;

/**
 * 
 * @author Dhinakar P
 * Date Created: 02/29/2016</br>
 * @since 1.0</p> 
 * 
 * Contains the logic to generates the function that handles the verb</br>
 * action on data: GpCreate</p>
 */

@Component("GpSpringBootDaoTakePhotoHandler")
public class GpSpringBootDaoTakePhotoHandler extends GpBaseSpringBootDaoVerbFunctionHandler
implements IGpSpringBootDaoVerbGenSupport {

	private GpSpringBootJpaDaoGenWorker the_worker;
	private List<GpSpringBootDaoVerbGenInfo> the_implicit_verbs;
	private GpSpringBootDaoUpdateHandler the_update_handler;
	private GpSpringBootDaoCreateHandler the_create_handler;
	
	public GpSpringBootDaoCreateHandler getThe_create_handler() {
		return the_create_handler;
	}
	
	@Resource(name = "GpSpringBootDaoCreateHandler")
	public void setThe_create_handler(GpSpringBootDaoCreateHandler the_create_handler) {
		this.the_create_handler = the_create_handler;
	}
	
	public GpSpringBootDaoUpdateHandler getThe_update_handler() {
		return the_update_handler;
	}
	
	@Resource(name = "GpSpringBootDaoUpdateHandler")
	public void setThe_update_handler(GpSpringBootDaoUpdateHandler the_update_handler) {
		this.the_update_handler = the_update_handler;
	}

	public void setThe_implicit_verbs(List<GpSpringBootDaoVerbGenInfo> the_implicit_verbs) {
		this.the_implicit_verbs = the_implicit_verbs;
	}
	public GpSpringBootJpaDaoGenWorker getThe_worker() {
		return the_worker;
	}
	
	@Resource(name = "GpSpringBootJpaDaoGenWorker")
	public void setThe_worker(GpSpringBootJpaDaoGenWorker the_worker) {
		this.the_worker = the_worker;
	}
	@Override
	public GpSpringBootDaoVerbGenInfo handle_verb(GpVerb verb,
			GpActivity activity) throws Exception {
		return null;
	}
	
	public List<GpSpringBootDaoVerbGenInfo> getThe_implicit_verbs() {
		return the_implicit_verbs;
	}
	
	public void handle_implicit_verbs(GpVerb verb, GpActivity activity) throws Exception {
		the_implicit_verbs = new ArrayList<>();
		//search for update implicit
		the_implicit_verbs.add(this.gp_update(verb, activity));
		the_implicit_verbs.add(this.gp_create(verb, activity));
		//
	}
	
	private GpSpringBootDaoVerbGenInfo gp_create(GpVerb verb, GpActivity activity) {
		GpSpringBootDaoVerbGenInfo create = the_create_handler.handle_implicit_verb(verb, activity);
		create.verb_action_on_data = GpBaseVerbsConstants.GpCreate;
		return create;
	}

	private GpSpringBootDaoVerbGenInfo gp_update(GpVerb verb, GpActivity activity) throws Exception {
		GpSpringBootDaoVerbGenInfo update = the_update_handler.handle_implicit_verb(verb, activity);
		update.verb_action_on_data = GpBaseVerbsConstants.GpUpdate;
		return update;
	}
	
}
