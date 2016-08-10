package com.npb.gp.domain.core;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.npb.gp.gen.json.mappers.modules.GpModuleClientMetaData;
import com.npb.gp.gen.json.mappers.modules.GpModuleServerMetaData;

/**
 * @author Reinaldo</br>
 * Creation Date: 18/09/2015</br>
 * 
 * The purpose of this class is to encapsulate module properties definition</br>
 *
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpModuleProperties {

	private String name;
	private String description;
	private String version;
	private GpModuleClientMetaData client_meta_data;
	private GpModuleServerMetaData server_meta_data;
	private String[] table_statements;
	private String Activity_name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public GpModuleClientMetaData getClient_meta_data() {
		return client_meta_data;
	}
	public void setClient_meta_data(GpModuleClientMetaData client_meta_data) {
		this.client_meta_data = client_meta_data;
	}
	public GpModuleServerMetaData getServer_meta_data() {
		return server_meta_data;
	}
	public void setServer_meta_data(GpModuleServerMetaData server_meta_data) {
		this.server_meta_data = server_meta_data;
	}
	public String[] getTable_statements() {
		return table_statements;
	}
	public void setTable_statements(String[] table_statements) {
		this.table_statements = table_statements;
	}
	public String getActivity_name() {
		return Activity_name;
	}
	public void setActivity_name(String activity_name) {
		Activity_name = activity_name;
	}
}