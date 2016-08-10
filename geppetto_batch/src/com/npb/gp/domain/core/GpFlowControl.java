package com.npb.gp.domain.core;

import java.util.ArrayList;


/**
 * 
 * @author Dan Castillo
 * Date Created: 11/19/2014</br>
 * @since .75</p> 
 *
 * 
 * This class encapsulates the base data for a FLOW. The concept of a FLOW</br>
 * is loosely  based on merging the concepts of BPM and Flow Based programming</br>
 * the problem that this is attempting to solve is how do you know what is the <br>
 * the sequence of components to generate for a particular activity?(see GpActivity</br>
 * for a description of what an activity represents in Geppetto.
 *
 */
public class GpFlowControl {
	 
	private long id;
	private long master_flow_id;
	private String component_type;
	private String label;
	private String description;
	private String type;
	private long sequence_id;
	private long sub_sequence_id;
	private long activity_id;
	private ArrayList<GpMicroFlow> micro_flows;
    
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMaster_flow_id() {
		return master_flow_id;
	}
	public void setMaster_flow_id(long master_flow_id) {
		this.master_flow_id = master_flow_id;
	}
	public String getComponent_type() {
		return component_type;
	}
	public void setComponent_type(String component_type) {
		this.component_type = component_type;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSequence_id() {
		return sequence_id;
	}
	public void setSequence_id(long sequence_id) {
		this.sequence_id = sequence_id;
	}
	public long getSub_sequence_id() {
		return sub_sequence_id;
	}
	public void setSub_sequence_id(long sub_sequence_id) {
		this.sub_sequence_id = sub_sequence_id;
	}
	public long getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}

	
	public ArrayList<GpMicroFlow> getMicro_flows() {
		return micro_flows;
	}
	public void setMicro_flows(ArrayList<GpMicroFlow> micro_flows) {
		this.micro_flows = micro_flows;
	}
	

}
