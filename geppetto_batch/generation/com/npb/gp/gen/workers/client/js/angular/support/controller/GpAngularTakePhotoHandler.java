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

@Component("GpAngularTakePhotoHandler")
public class GpAngularTakePhotoHandler extends
GpBaseAngularControllerVerbFunctionHandler implements
IGpAngularControllerVerbGenSupport {
	
	GpAngularHtmlGenWorker the_worker;
	
	String function_name = "takePhoto";
	
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}

	@Override
	public GpAngularControllerGenDto handle_verb(GpVerb verb, GpActivity activity) throws Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("*************** In GpAngularTakePhotoHandler - handle_verb - verb id is: " + verb.getId());
		/*ArrayList<GpMicroFlow> the_micro_flow = super.getMicro_flow_dao()
											.find_by_verb_id(verb.getId());
			for (GpMicroFlow gpMicroFlow : the_micro_flow) {
			System.out.println("-----------------in angualr controller---------"+gpMicroFlow.getAction());	
			}*/
			
		GpMicroFlow microFlow= new GpMicroFlow();
		GpMicroFlow microFlow2= new GpMicroFlow();
		
			ArrayList<GpMicroFlow>  the_micro_flow = new ArrayList<GpMicroFlow>();
			microFlow.setAction("GpTakePhoto");
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
					
					}else if(mcr_flow.getAction().equals("GpTakePhoto")){
						this.gp_takePhoto(verb, activity);
						
						
					}else if(mcr_flow.getAction().equals("GpServerResponse")){
						this.gp_server_response(verb);
						
					}else if(mcr_flow.getAction().equals("GpDisplayServerReponse")){
						this.gp_display_server_response(verb);
						
					}else if(mcr_flow.getAction().equals("GpTransition")){
						this.gp_transition(verb);
						
					}else if(mcr_flow.getAction().equals("GpEnd")){
						this.gp_end(verb);
					}
					System.out.println("#####---getSequence--###### " + mcr_flow.getSequence_id()+ " " + mcr_flow.getVerb_id()+ " " + mcr_flow.getAction() + "\n");		
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
	public void gp_takePhoto(GpVerb verb, GpActivity activity) throws Exception {

		String project_name = getThe_worker().project_name;
		String server_url = getThe_worker().server_url;
		String server_port = getThe_worker().server_port;
		String created_by = getThe_worker().created_by;
		//String ws_route = getThe_worker().map_url_services.get(verb.getAction_on_data());
		this.the_dto.gp_take_photo = "//this is where the post code goes" + "\n";
		// TODO Auto-generated method stub
		//this.the_dto.gp_take_photo = "//this is where the validate code goes";
		/*this.the_dto.gp_take_photo = "\n\tdocument.addEventListener('deviceready', function () {"
				+ "\n\t\tvar options = {"
				+ "\n\t\t\tquality: 100,"
				+ "\n\t\t\tdestinationType: Camera.DestinationType.DATA_URL,"
				+ "\n\t\t\tsourceType: Camera.PictureSourceType.CAMERA,"
				+ "\n\t\t\tencodingType: Camera.EncodingType.JPEG,"
				+ "\n\t\t\tsaveToPhotoAlbum: false,"
				+ "\n\t\t\tcorrectOrientation: true"
				+ "\n\t\t};"
				+ "\n\t\t$cordovaCamera.getPicture(options).then(function (res) {"
				+ "\n\t\t\tvar src = 'data:image/jpeg;base64,' + res;"
				+ "\n\t\t\tconsole.log('src_length: ', src.length);"
				+ "\n\t\t}, function (err) {"
				+ "\n\t\t\tconsole.error(err);"
				+ "\n\t\t});"
				+ "\n\t}, false);";		*/
		
		String globalPictureNounAtrr="";
		String fileURl ="";
		for(GpNounAttribute noun:activity.getPrimary_noun().getNounattributes()){
			System.out.println("noun---list--"+noun.getName()+"--"+noun.getSubtype());
			if(noun.getSubtype().equals("Picture")){
				globalPictureNounAtrr=activity.getPrimary_noun().getName()+"."+noun.getName().toLowerCase();
				fileURl =noun.getName().toLowerCase();
			}
		} 
		
		String ws_route_upload_file = getThe_worker().map_url_services.get(GpBaseVerbsConstants.GpTakePhoto);
		String ws_route_update = getThe_worker().map_url_services.get(GpBaseVerbsConstants.GpUpdate);
		this.the_dto.gp_take_photo = ""+
	    "\n\t\tnavigator.camera.getPicture(cameraSuccess, cameraError, {"+ 
	    "\n\t\tquality: 50,"+
	    "\n\t\tdestinationType: Camera.DestinationType.FILE_URL"+
	    "\n\t\t});"+
	    "\n\t\t}"+
		"\n\t\tfunction cameraSuccess(fileURI){"+
   		"\n\t\t console.log(\"----fileURI--\",fileURI);"+
       	"\n\t\t $scope."+
       	globalPictureNounAtrr+
        "= fileURI;"+
        "\n\t\t }"+
        "\n\t\t function cameraError(message){"+
        "\n\t\t // load some default image here"+
        "\n\t\t alert('Failed because: ' + message);"+
        "\n\t\t };"+
        "\n\t\t function clearCache() {"+
        "\n\t\t navigator.camera.cleanup();"+
        "\n\t\t }"+
        "\n\t\t function onFail(message) {"+
        "\n\t\t alert('Failed because: ' + message);\n\t"+
         "};\n\t\t"+ 
		  " \n\t\t$scope.pushToCloud = function(){"+
				"\n\t\tvar fileURL = '';"+
				 "\n\t\tvar syncURL =\""+server_url+":"+server_port+"/" + project_name + "_" + created_by +"/"+activity.getName()+"/"+ ws_route_upload_file +"\"; "+
					"\n\t\tfileURL=$scope."
					+globalPictureNounAtrr+";"+  
		  		   			"\n\t\tvar imageURL = fileURL;"+
 		 			 		"\n\t\tvar uri = syncURL; \n\t\t"+
				   "\n\t\tvar options = new FileUploadOptions();"+
				   "\n\t\toptions.fileKey = \"uploadfile\";"+
				   "\n\t\toptions.fileName = fileURL.substr(fileURL.lastIndexOf('/')+1);"+
				   "\n\t\toptions.mimeType = \"image/jpeg\";"+
				   "\n\t\toptions.chunkedMode = false;"+  
				   
		  		   "\n\t\tvar ft = new FileTransfer();\n\t\t"+ 
				   "\n\t\tvar test = ft.upload(imageURL, encodeURI(uri), onSuccess, onError, options); \n\t\t"+  

				    "\n\t\tfunction onSuccess(r) {"+ 
				      "\n\t\tconsole.log(\"code===\"+ angular.toJson(r))"+
				      "\n\t\tconsole.log(\"Response = \" + r.response);"+
					   
					  "\n\t\tvar parsed_json = JSON.parse(r.response);"+
					  "\n\t\t$scope."+ activity.getPrimary_noun().getName() +".id = parsed_json.id;"+
				  		//"\n\t\talert(res[0]);" +
				  		 "\n\t\tvar syncURL=\""+server_url+":"+server_port+"/" + project_name + "_" + created_by + "/"+activity.getName()+"/"+ ws_route_update +"\";"+
				  		 "\n\t\tconsole.log(\"$scope."+activity.getPrimary_noun().getName()+"-----\",$scope."+activity.getPrimary_noun().getName()+");"+
				  		 "\n\t\tvar finalData = $scope."+activity.getPrimary_noun().getName()+";"+ 
		    			"\n\t\tfinalData."+fileURl+" = \"\";"+
				  	
				  	"\n\t\t$http({"+
				  		"\n\t\tmethod: 'PUT',"+
				  		"\n\t\turl: syncURL,"+
				  		"\n\t\tdata: angular.toJson(finalData),"+
				  	"\n\t\t}).then(function(result){ console.log( \"Success data---\" +JSON.stringify(result.data)); "
				  	+ "\n\t\treturn \"data Updated\";})"
				  	+ "\n\t\t.then(function(res){ console.log(\"PUSH completed \"); "
				  	+ "\n\t\talert(\"push completed\"); });"+
				   "\n\t\t}; \n\t\t"+
				   "\n\t\tfunction onError(error) {"+
				      "\n\t\talert(\"An error has occurred: Code = \" + error.code);"+
				      "\n\t\talert(\"An error has occurred: Code = \" + angular.toJson(error));"+
				      "\n\t\tconsole.log(\"upload error source \" + error.source);"+
				      "\n\t\tconsole.log(\"upload error target \" + error.target);"+
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
