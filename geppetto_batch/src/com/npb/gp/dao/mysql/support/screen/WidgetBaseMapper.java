package com.npb.gp.dao.mysql.support.screen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpUiWidgetX;

/**
 * 
 * @author Suresh Palanisamy</br> Date Created: 26/03/2015</br>
 * @since .75</p>
 *
 *        The purpose of this class is to declare the values for the widgets
 *        from the result set</p>
 *
 */

public class WidgetBaseMapper implements RowMapper<GpUiWidgetX> {

	@Override
	public GpUiWidgetX mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpUiWidgetX dto = new GpUiWidgetX();

		dto.setId(rs.getLong("id"));
		dto.setName(rs.getString("name"));
		dto.setLabel(rs.getString("label"));
		dto.setDescription(rs.getString("description"));
		dto.setSupports_label(rs.getString("supports_label"));
		dto.setParent_name(rs.getString("parent_name"));
		dto.setIs_container(rs.getString("is_container"));
		dto.setParentid(rs.getLong("parent_id"));
		dto.setType(rs.getString("type"));
		dto.setHeight(rs.getLong("height"));
		dto.setWidth(rs.getLong("width"));
		dto.setScreen_id(rs.getLong("screen_id"));
		dto.setX(rs.getLong("x"));
		dto.setY(rs.getLong("y"));
		//Landscape width and height
		dto.setLandscape_height(rs.getLong("landscape_height"));
		dto.setLandscape_width(rs.getLong("landscape_width"));
		dto.setLandscapeX(rs.getLong("landscape_x"));
		dto.setLandscapeY(rs.getLong("landscape_y"));
		//Portrait width and height
		dto.setPortrait_height(rs.getLong("portrait_height"));
		dto.setPortrait_width(rs.getLong("portrait_width"));
		dto.setPortraitX(rs.getLong("portrait_x"));
		dto.setPortraitY(rs.getLong("portrait_y"));
		dto.setData_binding_context(rs.getString("data_binding_context"));
		dto.setNoun_id(rs.getLong("noun_id"));
		dto.setNoun_attribute_id(rs.getLong("noun_attribute_id"));
		dto.setVerb_binding_context(rs.getString("verb_binding_context"));
		dto.setVerb_target(rs.getString("verb_target"));
		dto.setNumber_of_children(rs.getLong("number_of_children"));
		dto.setExtended_attributes(rs.getString("extended_attributes"));
		dto.setEvent_verb_combo(rs.getString("event_verb_combo"));
		dto.setNotes(rs.getString("notes"));
		dto.setCreatedby(rs.getLong("created_by"));
		dto.setCreatedate(rs.getTimestamp("created_date"));
		dto.setLastmodifiedby(rs.getLong("last_modified_by"));
		dto.setLastmodifieddate(rs.getTimestamp("last_modified_date"));
		dto.setImage_src(rs.getString("image_src"));
		dto.setTarget_url(rs.getString("target_url"));
		dto.setCard_header(rs.getString("header"));
		dto.setCard_footer(rs.getString("footer"));
		dto.setExtra_verb_info(rs.getString("extra_verb_info"));
		return dto;
	}

}
