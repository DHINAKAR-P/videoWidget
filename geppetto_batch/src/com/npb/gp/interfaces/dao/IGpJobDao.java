package com.npb.gp.interfaces.dao;

import com.npb.gp.domain.core.GpJob;

/**
 * 
 * @author Dan Castillo</br>
 *         Date Created: 06/13/2014</br>
 * @since .35
 *        </p>
 *
 *        The purpose of this interface is to declare the standard db operations
 *        required</br>
 *        for the functions that handle the start of code generation
 *        </p>
 *
 *
 */

public interface IGpJobDao {

	public void insert(long project_id, long user_id, String username);

	public void update(long project_id, long user_id);

	public void update_status(long project_id, long user_id, String username, String status_info);

	public void update_message(long project_id, long user_id, String username, String status_message);

	public void update_stacktrace(long project_id, long user_id, String username, String stacktrace);

	public void delete(long project_id, long user_id);

	public GpJob find_by_id(long noun_id) throws Exception;

	public long find_and_lock() throws Exception;

	void insert(long project_id, long user_id, String username,
			String status_info, String stacktrace, String statusMessage);

	void update_claimed(long id, String claimed);
}