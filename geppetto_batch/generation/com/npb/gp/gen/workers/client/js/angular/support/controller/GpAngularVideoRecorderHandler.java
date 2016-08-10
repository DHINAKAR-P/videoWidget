package com.npb.gp.gen.workers.client.js.angular.support.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpMicroFlow;
import com.npb.gp.domain.core.GpNounAttribute;
import com.npb.gp.domain.core.GpVerb;
import com.npb.gp.gen.constants.GpBaseVerbsConstants;
import com.npb.gp.gen.interfaces.angularjs.IGpAngularControllerVerbGenSupport;
import com.npb.gp.gen.util.dto.angular.GpAngularControllerGenDto;
import com.npb.gp.gen.workers.client.js.angular.GpAngularHtmlGenWorker;

@Component("GpAngularVideoRecorderHandler")
public class GpAngularVideoRecorderHandler extends
GpBaseAngularControllerVerbFunctionHandler implements
IGpAngularControllerVerbGenSupport {
	
	GpAngularHtmlGenWorker the_worker;
	
	String function_name = "video_recorder";
	
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	@Override
	public GpAngularControllerGenDto handle_verb(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub record_video
		try {
			System.out.println("*************** In GpAngularVideoRecorderHandler - handle_verb - verb id is: " + verb.getId());
		/*ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
											.find_by_verb_id(verb.getId());
			for (GpMicroFlow gpMicroFlow : the_micro_flow) {
			System.out.println("-----------------in angular controller---------"+gpMicroFlow.getAction());	
			}*/
			
		GpMicroFlow microFlow= new GpMicroFlow();
		GpMicroFlow microFlow2= new GpMicroFlow();
		
			ArrayList<GpMicroFlow>  the_micro_flow = new ArrayList<GpMicroFlow>();
			microFlow.setAction("GpRecordVideo");
			microFlow2.setAction("GpStart");
			the_micro_flow.add(microFlow);
			the_micro_flow.add(microFlow2);
			
			if(the_micro_flow.size() >  0){
				for(GpMicroFlow mcr_flow : the_micro_flow){
					
					if(mcr_flow.getAction().equals("GpStart")){
						this.gp_start(verb, activity);
					}else if(mcr_flow.getAction().equals("GpValidate")){
						this.gp_validate(verb);
						
					}else if(mcr_flow.getAction().equals("GpConfirm")){
						this.gp_confirm(verb);
						
					}else if(mcr_flow.getAction().equals("GpServerPost")){
						this.gp_server_post(verb, activity);
					
					}else if(mcr_flow.getAction().equals("GpRecordVideo")){
						this.gp_video_recorder(verb, activity);
						
						
					}else if(mcr_flow.getAction().equals("GpServerResponse")){
						this.gp_server_response(verb);
						
					}else if(mcr_flow.getAction().equals("GpDisplayServerReponse")){
						this.gp_display_server_response(verb);
						
					}else if(mcr_flow.getAction().equals("GpTransition")){
						this.gp_transition(verb);
						
					}else if(mcr_flow.getAction().equals("GpEnd")){
						this.gp_end(verb);
					}
					System.out.println("#####---in video recorder--###### " + mcr_flow.getSequence_id()+ " " + mcr_flow.getVerb_id()+ " " + mcr_flow.getAction() + "\n");		
				}
			return this.the_dto;
			}
			return null;
		} catch (Exception e) {
			System.out.println("########## verb name   video recorder is: " + verb.getName());
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public void get_function_signiture(GpVerb verb) throws Exception {

		this.the_dto.scope_service = "$scope";
		this.the_dto.function_name = function_name;
		this.the_dto.function_parms = "";
	
	}
	@Override
	public void gp_start(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		this.get_function_signiture(verb);
		this.the_dto.gp_start_code = "//this is where the start code goes";

	}

	@Override
	public void gp_confirm(GpVerb verb) throws Exception{
		this.the_dto.gp_confirm_code = "//this is where the confirm code goes";
	}

	@Override
	public void gp_validate(GpVerb verb) throws Exception {
		// TODO Auto-generated method stub
		this.the_dto.gp_validate_code = "//this is where the validate code goes";

	}
	
	@Override
	public void gp_video_recorder(GpVerb verb, GpActivity activity) throws Exception {

		String project_name = getThe_worker().project_name;
		String server_url = getThe_worker().server_url;
		String server_port = getThe_worker().server_port;
		String created_by = getThe_worker().created_by;
		//String ws_route = getThe_worker().map_url_services.get(verb.getAction_on_data());
		this.the_dto.gp_video_recorder = "//this is where the post code goes" + "\n";
		String globalPictureNounAtrr="";
		String fileURl ="";
		for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
			System.out.println("noun--Video-list--"+noun.getName()+"--"+noun.getSubtype());
			if(noun.getSubtype().equals("Video")){
				globalPictureNounAtrr=activity.getPrimary_noun().getName()+"."+noun.getName().toLowerCase();
				fileURl =noun.getName().toLowerCase();
			}
		} 
		
		String ws_route_upload_file = getThe_worker().map_url_services.get(GpBaseVerbsConstants.GpRecordVideo);
		//String ws_route_update = getThe_worker().map_url_services.get(GpBaseVerbsConstants.GpUpdate);
		this.the_dto.gp_video_recorder = "\n\t\tnavigator.device.capture.captureVideo(captureSuccess, captureError, {limit:1});"+	
				"\n\t\tconsole.log(\"captured---- record_sound--\");\n"+
			// capture callback
			 "\n\t\tfunction captureSuccess(mediaFiles) {"+
				"\n\t\tconsole.log(\"in success part of  record_sound--\");"+
			    "\n\t\tvar i, path, len;"+
			    "\n\t\tfor (i = 0, len = mediaFiles.length; i < len; i += 1) {"+
			    	
			        "\n\t\tpath = mediaFiles[i].fullPath;"+
			        "\n\t\tconsole.log(\"inside for loop--\",path);"+
			        "\n\t\t$scope."+ activity.getPrimary_noun().getName() +"."+fileURl+" =path;"+
			        // do something interesting with the file
			    	"\n\t\t}"+

				"\n\t};"+

			// capture error callback
			"\n\t\tfunction captureError (error) {"+
				"\n\t\tconsole.log(\"Error occcured record_sound--\");"+
			    "\n\t\tnavigator.notification.alert('Error code: ' + error.code, null, 'Capture Error');"+
			    "\t};\n\t\t"+		
         "\n\t};\n\t\t"+ 
			    
		  " \n\t\t$scope.pushVideoToCloud = function(){"+
				"\n\t\tvar fileURL = '';"+
				 "\n\t\tvar syncURL =\""+server_url+":"+server_port+"/" + project_name + "_" + created_by +"/"+activity.getName()+"/uploadVideo\";"+//+ ws_route_upload_file +"\"; "+
					"\n\t\tfileURL=$scope."
					+globalPictureNounAtrr+";"+  
		  		   			"\n\t\tvar imageURL = fileURL;"+
 		 			 		"\n\t\tvar uri = syncURL; \n\t\t"+
		  		   			"var filekeyvalue = \"uploadfile\";"+
				   "\n\t\tvar options = new FileUploadOptions();"+
				   "\n\t\toptions.fileKey = filekeyvalue;"+
				   "\n\t\toptions.fileName = fileURL.substr(fileURL.lastIndexOf('/')+1);"+
				   "\n\t\toptions.mimeType = \"video/mpeg\";"+
				   "\n\t\toptions.chunkedMode = false;"+  
				   
		  		   "\n\t\tvar ft = new FileTransfer();\n\t\t"+ 
				   "\n\t\tvar test = ft.upload(imageURL, encodeURI(uri), onSuccess, onError, options); \n\t\t"+  

				    "\n\t\tfunction onSuccess(r) {"+ 
				      "\n\t\tconsole.log(\"code===\"+ angular.toJson(r))"+
				      "\n\t\tconsole.log(\"Response = \" + r.response);"+
					   
					  "\n\t\tvar parsed_json = JSON.parse(r.response);"+
					  "\n\t\t$scope."+ activity.getPrimary_noun().getName() +".id = parsed_json.id;"+
				  		 "\n\t\tvar syncURL=\""+server_url+":"+server_port+"/" + project_name + "_" + created_by + "/"+activity.getName()+"/update_"+ project_name +"\";"+
				  		 "\n\t\tconsole.log(\"$scope."+activity.getPrimary_noun().getName()+"-----\",$scope."+activity.getPrimary_noun().getName()+");"+
				  		 "\n\t\tvar finalData = $scope."+activity.getPrimary_noun().getName()+";"+ 
		    			"\n\t\tfinalData."+fileURl+" = \"\";\n\t\t"+
				  	
				  	"\n\t\t$http({"+
				  		"\n\t\tmethod: 'PUT',"+
				  		"\n\t\turl: syncURL,"+
				  		"\n\t\tdata: angular.toJson(finalData),"+
				  	"\n\t\t}).then(function(result){ console.log( \"Success data---\" +JSON.stringify(result.data)); "
				  	+ "\n\t\treturn \"data Updated\";})"
				  	+ "\n\t\t.then(function(res){ console.log(\"video PUSH completed \"); "
				  	+ "\n\t\talert(\"push video completed\"); });"+
				   "\n\t\t}; \n\t\t"+
				   "\n\t\tfunction onError(error) {"+
				      "\n\t\talert(\"An error has occurred: Code = \" + error.code);"+
				      "\n\t\talert(\"An error has occurred: Code = \" + angular.toJson(error));"+
				      "\n\t\tconsole.log(\"upload error source \" + error.source);"+
				      "\n\t\tconsole.log(\"upload error target \" + error.target);"+
				      //"\n\t\t}"+
				   "\n\t\t}";
	}
	

	@Override
	public void gp_server_post(GpVerb verb, GpActivity activity) throws Exception {
		String project_name = getThe_worker().project_name;
		String server_url = getThe_worker().server_url;
		String server_port = getThe_worker().server_port;
		String created_by = getThe_worker().created_by;
		String ws_route = getThe_worker().map_url_services.get(verb.getAction_on_data());
		this.the_dto.gp_server_post_code = "//this is where the post code goes" + "\n";
		
		this.the_dto.gp_server_post_code += "var deferred = $q.defer();"+ "\n" +

		 "$http.delete('" + server_url + ":" + server_port + "/" + project_name + "_" + created_by + "/" + activity.getName() + "/" + ws_route +
		 
		 "/'+$scope."+ activity.getPrimary_noun().getName() +".id).success(function(response) {"+ "\n" +		 
		 		
		 		"\t alert('Operation "+ verb.getLabel() +activity.getPrimary_noun().getName()+" successful');"+ "\n" +
				"\t deferred.resolve(response);"+ "\n" +
		 "}).error(function(err) {"+ "\n" +
			 	"\t alert('You got' + err + 'error');"+ "\n" +
				"\t deferred.reject(err);"+ "\n" +
		 "});";

	}

//	@Override
//	public void gp_server_response(GpVerb verb) throws Exception {
//		this.the_dto.gp_server_response_code = "//this is where the server response code goes";
//	}
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
