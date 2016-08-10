package com.npb.gp.gen.domain.java;

import java.util.ArrayList;

import com.npb.gp.gen.domain.GpDataType;
import com.npb.gp.gen.domain.GpMethodDescription;

public class GpJavaMethodDescription extends GpMethodDescription {
	
	private String description;
	private ArrayList<String> exceptions = new ArrayList<String>();
	private ArrayList<GpDataType> input_parms = new ArrayList<GpDataType>();
	private String name;
	private GpDataType return_parm = new GpDataType();
	private String visibility;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getExceptions() {
		return exceptions;
	}
	public void setExceptions(ArrayList<String> exceptions) {
		this.exceptions = exceptions;
	}
	public ArrayList<GpDataType> getInput_parms() {
		return input_parms;
	}
	public void setInput_parms(ArrayList<GpDataType> input_parms) {
		this.input_parms = input_parms;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	public GpDataType getReturn_parm() {
		return return_parm;
	}
	public void setReturn_parm(GpDataType return_parm) {
		this.return_parm = return_parm;
	}
	
	
	
	
	
	
	

}
