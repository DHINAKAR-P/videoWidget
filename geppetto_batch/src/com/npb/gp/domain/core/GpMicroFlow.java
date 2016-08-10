package com.npb.gp.domain.core;
/**
 * 
 * @author Dan Castillo
 * Date Created: 01/03/2015</br>
 * @since .75</p> 
 *
 * 
 * This class encapsulates the base data for a MicroFlow. The concept of a</br>
 * MicroFlow attempts to capture the dependencies as well as the FLOW of a </br>
 * Software component. For example, if the system is using Java and a standard</br>
 * Controller ---> Service ---->Business Logic -----> DAO architecture a MicroFlow</br>
 * would contain the META DATA and Flow for each of the components in the</br>
 * architecture. The same can apply to client side components.</p>
 *
 */
public class GpMicroFlow {
	
	private long id;
	private long flow_control_id; 
	private String component_type;
	private String description;
	private long 	master_flow_id;
	private long code_id;
	private long sequence_id;
	private long verb_id;
	private String action;
	
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
	public long getFlow_control_id() {
		return flow_control_id;
	}
	public void setFlow_control_id(long flow_control_id) {
		this.flow_control_id = flow_control_id;
	}
	public String getComponent_type() {
		return component_type;
	}
	public void setComponent_type(String component_type) {
		this.component_type = component_type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCode_id() {
		return code_id;
	}
	public void setCode_id(long code_id) {
		this.code_id = code_id;
	}
	public long getSequence_id() {
		return sequence_id;
	}
	public void setSequence_id(long sequence_id) {
		this.sequence_id = sequence_id;
	}
	public long getVerb_id() {
		return verb_id;
	}
	public void setVerb_id(long verb_id) {
		this.verb_id = verb_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
	
	
	

}
