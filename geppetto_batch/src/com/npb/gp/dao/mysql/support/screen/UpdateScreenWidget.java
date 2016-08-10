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

public class UpdateScreenWidget extends SqlUpdate {
	public static String SQL_UPDATE_WIDGET = "";

	public UpdateScreenWidget(DataSource dataSource) {
		super(dataSource, SQL_UPDATE_WIDGET);
		super.declareParameter(new SqlParameter("id", Types.BIGINT));
		super.declareParameter(new SqlParameter("name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("description", Types.VARCHAR));
		super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
		super.declareParameter(new SqlParameter("type", Types.VARCHAR));
		super.declareParameter(new SqlParameter("parentid", Types.BIGINT));
		super.declareParameter(new SqlParameter("screen_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("is_container", Types.VARCHAR));
		super.declareParameter(new SqlParameter("supports_label", Types.VARCHAR));
		super.declareParameter(new SqlParameter("ui_technology", Types.VARCHAR));
		super.declareParameter(new SqlParameter("width", Types.VARCHAR));
		super.declareParameter(new SqlParameter("height", Types.VARCHAR));
		super.declareParameter(new SqlParameter("x", Types.BIGINT));
		super.declareParameter(new SqlParameter("y", Types.BIGINT));
		super.declareParameter(new SqlParameter("data_binding_context",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("verb_binding_context",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("noun_id", Types.BIGINT));
		super.declareParameter(new SqlParameter("noun_attribute_id",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("parent_name", Types.VARCHAR));
		super.declareParameter(new SqlParameter("number_of_children",
				Types.BIGINT));
		super.declareParameter(new SqlParameter("extended_attributes",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("event_verb_combo",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("verb_target", Types.VARCHAR));
		super.declareParameter(new SqlParameter("portrait_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("portrait_width", Types.VARCHAR));
		super.declareParameter(new SqlParameter("portrait_height",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("landscape_x", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_y", Types.BIGINT));
		super.declareParameter(new SqlParameter("landscape_width",
				Types.VARCHAR));
		super.declareParameter(new SqlParameter("landscape_height",
				Types.VARCHAR));

		super.declareParameter(new SqlParameter("events", Types.VARCHAR));
		// super.declareParameter(new SqlParameter("font_size", Types.VARCHAR));
		super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
		super.declareParameter(new SqlParameter("last_modified_date",
				Types.TIMESTAMP));
		super.declareParameter(new SqlParameter("last_modified_by",
				Types.BIGINT));
	}
}
