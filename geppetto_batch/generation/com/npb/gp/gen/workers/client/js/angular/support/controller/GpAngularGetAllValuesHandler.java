package com.npb.gp.gen.workers.client.js.angular.support.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.interfaces.angularjs.IGpAngularControllerVerbGenSupport;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;

/**
 * 
 * @author Dan Castillo Date Created: 03/14/2014</br>
 * @since .2
 *        </p>
 * 
 *        Contains the logic to generates the function that handles the
 *        verb</br>
 *        action on data: GpGetAllValues
 *        </p>
 */
@Component("GpAngularGetAllValuesHandler")
public class GpAngularGetAllValuesHandler extends GpBaseAngularControllerVerbFunctionHandler
		implements IGpAngularControllerVerbGenSupport {
	String function_name = "get_all_values";

	public String getFunction_name() {
		return function_name;
	}

	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	@Override
	public GpAngularControllerGenDto handle_verb(GpVerb verb, GpActivity activity) throws Exception {

		try {
			// System.out.println("$$$$$$$$ In GpAngularGetAllValuesHandler -
			// handle_verb - verb id is: " + verb.getId());
			ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao().find_by_verb_id(verb.getId());
			if (the_micro_flow.size() > 0) {
				for (GpMicroFlow mcr_flow : the_micro_flow) {

					if (mcr_flow.getAction().equals("GpStart")) {
						this.gp_start(verb, activity);
					} else if (mcr_flow.getAction().equals("GpValidate")) {
						this.gp_validate(verb);

					} else if (mcr_flow.getAction().equals("GpConfirm")) {
						this.gp_confirm(verb);

					} else if (mcr_flow.getAction().equals("GpServerPost")) {
						this.gp_server_post(verb, activity);

					} else if (mcr_flow.getAction().equals("GpServerResponse")) {
						this.gp_server_response(verb);

					} else if (mcr_flow.getAction().equals("GpDisplayServerReponse")) {
						this.gp_display_server_response(verb);

					} else if (mcr_flow.getAction().equals("GpTransition")) {
						this.gp_transition(verb);

					} else if (mcr_flow.getAction().equals("GpEnd")) {
						this.gp_end(verb);
					}
					// System.out.println("########### " +
					// mcr_flow.getSequence_id()+ " " + mcr_flow.getVerb_id()+ "
					// " + mcr_flow.getAction() + "\n");
				}
				return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			System.out.println("########## verb name is: " + verb.getName());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void get_function_signiture(GpVerb verb) throws Exception {

		this.the_dto.scope_service = "$scope";
		this.the_dto.function_name = "get_all_values";
		this.the_dto.function_parms = "";

	}

	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		this.get_function_signiture(verb);
		this.the_dto.gp_start_code = "//this is where the start code goes";

	}

	@Override
	public void gp_end(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_end_code = "//this is where the end code goes";

	}

	@Override
	public void gp_confirm(GpVerb verb) throws Exception {
		this.the_dto.gp_confirm_code = "//this is where the confirm code goes";
	}

	@Override
	public void gp_validate(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_validate_code = "//this is where the validate code goes";

	}

	@Override
	public void gp_server_post(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_server_post_code = "//this is where the post code goes \n";

		String nounstr = "";
		String executeString = "";
		String questionMark = "";
		int x = 0;
		for (GpNounAttribute noun : activity.getPrimary_noun().getNounattributes()) {
			System.out.println("noun---list- get all valuse-" + noun.getName());

			if (x == activity.getPrimary_noun().getNounattributes().size() - 1) {
				nounstr += noun.getName().toLowerCase();
				System.out.println("true--partn get all valuse " + x);
				executeString += "$scope." + activity.getPrimary_noun().getName() + "." + noun.getName().toLowerCase();
				
				questionMark += "?";
				x++;
			} else {
				System.out.println("in else part get all valuse" + x);
				nounstr += noun.getName().toLowerCase() + ",";
				executeString += "$scope." + activity.getPrimary_noun().getName() + "." + noun.getName().toLowerCase() + ",";
				questionMark += "?" + ",";
				x++;
			}
		}

		String InsertQuesry = "var query = \"SELECT * FROM " + activity.getPrimary_noun().getName() + ";\";\n";
		
		
		String globalPictureNounAtrr="";
		for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
			System.out.println("noun---list--"+noun.getName()+"--"+noun.getSubtype());
			if(noun.getSubtype().equals("Picture")){
				globalPictureNounAtrr=noun.getName();
			}else{
				globalPictureNounAtrr="id";
			}
		} 

		String executeQuery = "$cordovaSQLite.execute($rootScope.db, query,"
				+ "[]).then(function(res) {\n" +
				" if(res.rows.length > 0) {\n"+
               "for (var i = 0; i < res.rows.length; i++) {\n"+
						"var message = res.rows.item(i)."+globalPictureNounAtrr.toLowerCase()+";\n"+
						"console.log(res.rows.item(i));\n"+
						"alert(message);\n"+
					"};\n"+
            "} else {\n"+
            " alert(\"No results found\");\n"+
                "console.log(\"No results found\");\n"+
            "}\n"+
		  "}, function (err) {\n"+
		  "console.error(err);\n"+
		  "alert(err);\n"+
		  "});\n";
		/* "}"; */

		//this.the_dto.gp_server_post_code += InsertQuesry + executeQuery;
	}

	@Override
	public void gp_server_response(GpVerb verb) throws Exception {
		this.the_dto.gp_server_response_code = "//this is where the server response code goes";
	}

	@Override
	public void gp_display_server_response(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_display_server_response_code = "//this is where the display server response code goes";
	}

	@Override
	public void gp_transition(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_transition_code = "//this is where the transition code goes";
	}

}
