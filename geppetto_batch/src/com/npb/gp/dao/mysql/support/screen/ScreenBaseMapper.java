package com.npb.gp.dao.mysql.support.screen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpScreenX;

/**
 *
 * @author
 *
 *         Modified Date: 07/04/2015</br> Modified By: Suresh Palanisamy</p>
 *
 *         <p>
 *         Modified the retrun type to GpScreenX
 *         </p>
 *
 */

public class ScreenBaseMapper implements RowMapper<GpScreenX> {

	public GpScreenX mapRow(ResultSet rs, int rowNum) throws SQLException {

		GpScreenX screen = new GpScreenX();
		Integer key = 0;

		screen.setId(rs.getLong("SCREEN_ID"));
		screen.setName(rs.getString("SCREEN_NAME"));
		screen.setLabel(rs.getString("SCREEEN_LABEL"));
		screen.setDescription(rs.getString("SCREEN_DESCRIPTION"));
		screen.setActivity_id(rs.getLong("SCREEN_ACTIVITY_ID"));
		screen.setPrimary_noun_id(rs.getLong("SCREEN_PRIMARY_NOUN_ID"));
		screen.setProjectid(rs.getLong("SCREEN_PROJECTID"));

		Map<String, Long> sec_noun_ids = new HashMap<String, Long>();

		if (rs.getString("SCREEN_SECONDARY_IDS") != null) {
			if (rs.getString("SCREEN_SECONDARY_IDS").trim().length() > 0) {
				String[] temp = GpGenericRecordParserBuilder
						.parseDelimitedString(rs
								.getString("SCREEN_SECONDARY_IDS"));

				for (String str : temp) {

					//System.out.println(str);
					if (!str.equals("") && str != null && str.equals(";")) {
						sec_noun_ids.put((++key).toString(),
								new Long(str).longValue());
					}
				}
			}
		}
		screen.setSecondary_noun_ids(sec_noun_ids);

		screen.setType(rs.getString("SCREEN_TYPE"));
		screen.setClient_device_type_id(rs
				.getLong("SCREEN_CLIENT_DEVICE_TYPE_ID"));
		screen.setClient_device_type_label(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_LABEL"));
		screen.setClient_device_type(rs.getString("SCREEN_CLIENT_DEVICE_TYPE"));
		screen.setClient_device_type_os_name(rs
				.getString("SCREEN_CLIENT_DEVICE_TYPE_OS_NAME"));
		screen.setHuman_language_id(rs.getLong("SCREEN_HUMAN_LANGUAGE_ID"));
		screen.setOrientation(rs.getString("SCREEN_CURRENT_ORIENTATION"));
		screen.setOrientation_locked(rs
				.getString("SCREEN_IS_ORIENTATION_LOCKED"));
		screen.setLandscape_image_name(rs
				.getString("SCREEN_LANDSCAPE_IMAGE_NAME"));
		screen.setPortrait_image_name(rs
				.getString("SCREEN_PORTRAIT_IMAGE_NAME"));
		screen.setCreatedby(rs.getLong("SCREEN_CREATED_BY"));
		screen.setCreatedate(rs.getTimestamp("SCREEN_CREATED_DATE"));
		screen.setLastmodifiedby(rs.getLong("SCREEN_LAST_MODIFIED_BY"));
		screen.setLastmodifieddate(rs.getTimestamp("SCREEN_LAST_MODIFIED_DATE"));

		return screen;
	}
}
