package com.npb.gp.dao.mysql.support.base_verbs;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpVerb;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/15/2015</br>
 * @since .75</p>  
 *
 *standard mapper used in get data from the base_verbs table</p>
 *
 *
 */

public class GpBaseVerbMapper implements RowMapper<GpVerb> {
	@Override
	public GpVerb mapRow(ResultSet rs, int rowNum) throws SQLException {
		GpVerb the_verb = new GpVerb();
		the_verb.setId(rs.getLong("VERB_ID"));
		the_verb.setName(rs.getString("VERB_NAME"));
		the_verb.setLabel(rs.getString("VERB_LABEL"));
		the_verb.setDescription(rs.getString("VERB_DESCRIPTION"));
		the_verb.setNotes(rs.getString("VERB_NOTES"));
		the_verb.setAction_on_data(rs.getString("VERB_ACTION_ON_DATA"));

		return the_verb;
	}
	
	
}
