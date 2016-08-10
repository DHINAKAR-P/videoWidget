package com.npb.gp.interfaces.dao;

import java.util.HashMap;

public interface IGpProjectTemplateDao {

	public HashMap<String, String> find_by_project_id(long project_id)
			throws Exception;

}