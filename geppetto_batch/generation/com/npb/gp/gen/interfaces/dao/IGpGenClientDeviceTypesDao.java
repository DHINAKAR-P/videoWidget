package com.npb.gp.gen.interfaces.dao;

import java.util.Map;

import com.npb.gp.domain.core.GpClientDeviceTypes;

public interface IGpGenClientDeviceTypesDao {
	public Map<Long, GpClientDeviceTypes> load_client_devices_types();
}
