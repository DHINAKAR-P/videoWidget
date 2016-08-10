package com.npb.gp.dao.mysql.support.screen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author
 * 
 *         Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 * 
 *         <p>
 *         Modified the setScreen_is_orientation_locked and
 *         setWidget_is_container result set to String
 *         </p>
 *
 */

public class Screen_with_widget_mapper implements
		RowMapper<GpDto_screen_and_widgets> {

	@Override
	public GpDto_screen_and_widgets mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		GpDto_screen_and_widgets dto = new GpDto_screen_and_widgets();

		/* SCREEN ATTRIBUTES START */
		dto.setScreen_id(rs.getLong("SCREEN_ID"));
		dto.setScreen_name(rs.getString("SCREEN_NAME"));
		dto.setScreen_label(rs.getString("SCREEEN_LABEL"));
		dto.setScreen_description(rs.getString("SCREEN_DESCRIPTION"));
		dto.setScreen_projectid(rs.getLong("SCREEN_PROJECTID"));
		dto.setScreen_activity_id(rs.getLong("SCREEN_ACTIVITY_ID"));
		dto.setScreen_type(rs.getString("SCREEN_TYPE"));
		dto.setScreen_client_device_type_id(rs
				.getLong("SCREEN_CLIENT_DEVICE_TYPE_ID"));
		dto.setScreen_client_device_type_label(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_LABEL"));
		dto.setScreen_client_device_type_os_name(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_OS_NAME"));
		dto.setScreen_client_device_type(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE"));
		dto.setScreen_human_language_id(rs.getLong("SCREEN_HUMAN_LANGUAGE_ID"));
		dto.setScreen_current_orientation(rs
				.getString("SCREEN_CURRENT_ORIENTATION"));
		dto.setScreen_is_orientation_locked(rs
				.getString("SCREEN_IS_ORIENTATION_LOCKED"));
		dto.setScreen_primary_noun_id(rs.getLong("SCREEN_PRIMARY_NOUN_ID"));
		dto.setScreen_secondary_noun_ids(rs.getString("SCREEN_SECONDARY_IDS"));
		dto.setScreen_landscape_image_name(rs
				.getString("SCREEN_LANDSCAPE_IMAGE_NAME"));
		dto.setScreen_portrait_image_name(rs
				.getString("SCREEN_PORTRAIT_IMAGE_NAME"));
		dto.setScreen_createdby(rs.getLong("SCREEN_CREATED_BY"));
		dto.setScreen_createdate(rs.getTimestamp("SCREEN_CREATED_DATE"));
		dto.setScreen_lastmodifiedby(rs.getLong("SCREEN_LAST_MODIFIED_BY"));
		dto.setScreen_lastmodifieddate(rs
				.getTimestamp("SCREEN_LAST_MODIFIED_DATE"));

		/* SCREEN ATTRIBUTES END */

		/* WIDGETS ATTRIBUTES START */

		dto.setWidget_id(rs.getLong("WIDGET_ID"));
		dto.setWidget_name(rs.getString("WIDGET_NAME"));
		dto.setWidget_label(rs.getString("WIDGET_LABEL"));
		dto.setWidget_description(rs.getString("WIDGET_DESCRIPTION"));
		dto.setWidget_supports_label(rs.getString("WIDGET_SUPPORTS_LABEL"));
		dto.setWidget_parent_name(rs.getString("WIDGET_PARENT_NAME"));
		dto.setWidget_is_container(rs.getString("WIDGET_IS_CONTAINER"));
		dto.setWidget_parent_id(rs.getLong("WIDGET_PARENT_ID"));
		dto.setWidget_type(rs.getString("WIDGET_TYPE"));
		dto.setWidget_height(rs.getString("WIDGET_HEIGHT"));
		dto.setWidget_width(rs.getString("WIDGET_WIDTH"));
		dto.setWidget_x(rs.getLong("WIDGET_X"));
		dto.setWidget_y(rs.getLong("WIDGET_Y"));
		dto.setWidget_landscape_height(rs.getString("WIDGET_LANDSCAPE_HEIGHT"));
		dto.setWidget_landscape_width(rs.getString("WIDGET_LANDSCAPE_WIDTH"));
		dto.setWidget_landscape_x(rs.getLong("WIDGET_LANDSCAPE_X"));
		dto.setWidget_landscape_y(rs.getLong("WIDGET_LANDSCAPE_Y"));
		dto.setWidget_portrait_height(rs.getString("WIDGET_PORTRAIT_HEIGHT"));
		dto.setWidget_portrait_width(rs.getString("WIDGET_PORTRAIT_WIDTH"));
		dto.setWidget_portrait_x(rs.getLong("WIDGET_PORTRAIT_X"));
		dto.setWidget_portrait_y(rs.getLong("WIDGET_PORTRAIT_Y"));
		dto.setWidget_data_binding_context(rs
				.getString("WIDGET_DATA_BINDING_CONTEXT"));
		dto.setWidget_noun_id(rs.getLong("WIDGET_NOUN_ID"));
		dto.setWidget_noun_attribute_id(rs.getLong("WIDGET_NOUN_ATTRIBUTE_ID"));
		dto.setWidget_verb_binding_context(rs
				.getString("WIDGET_VERB_BINDING_CONTEXT"));
		dto.setWidget_verb_target(rs.getString("WIDGET_VERB_TARGET"));
		dto.setWidget_number_of_children(rs
				.getLong("WIDGET_NUMBER_OF_CHILDREN"));
		dto.setWidget_extended_attributes(rs
				.getString("WIDGET_EXTENDED_ATTRIBUTES"));
		dto.setWidget_event_verb_combo(rs.getString("WIDGET_EVENT_VERB_COMBO"));
		dto.setWidget_notes(rs.getString("WIDGET_NOTES"));
		dto.setWidget_createdby(rs.getLong("SCREEN_CREATED_BY"));
		dto.setWidget_createdate(rs.getTimestamp("SCREEN_CREATED_DATE"));
		dto.setWidget_lastmodifiedby(rs.getLong("SCREEN_LAST_MODIFIED_BY"));
		dto.setWidget_lastmodifieddate(rs
				.getTimestamp("SCREEN_LAST_MODIFIED_DATE"));

		/* WIDGETS ATTRIBUTES END */

		return dto;
	}
}
