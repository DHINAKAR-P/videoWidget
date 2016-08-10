package com.npb.gp.dao.mysql.support.screen;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 26/03/2015</br>
 * @since .75</p>
 *
 *        Update class supporting the GpScreenDao class</p>
 *
 */

public class UpdateScreen extends SqlUpdate {

	public static String SQL_UPDATE_SCREEN = "";

	public UpdateScreen(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_SCREEN);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("projectid", Types.BIGINT));
		super.declareParameter(new SqlParameter("activityid", Types.BIGINT));
		super.declareParameter(new SqlParameter("client_device_type_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_type_label",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_type",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("human_language_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("current_orientation",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("landscape_image_name",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("portrait_image_name",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("primary_noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("secondary_noun_ids",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_type_os_name",
				Types.VARCHAR));

		super.declareParameter(new SqlParameter("client_device_type_name",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter(
				"client_device_type_description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_screen_size",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_resolution",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_device_ppcm",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter(
				"client_device_type_os_version", Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_language_type",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("client_library_type",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("is_orientation_locked",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("height", Types.BIGINT));
		super.declareParameter(new SqlParameter("width", Types.BIGINT));

		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date",
				Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by",
				Types.BIGINT));
	}
}
