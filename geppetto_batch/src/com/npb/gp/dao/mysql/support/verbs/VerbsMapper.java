package com.npb.gp.dao.mysql.support.verbs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;

public class VerbsMapper implements RowMapper<GpVerb> {
	@Override
	public GpVerb mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		GpVerb the_verb = new GpVerb();
		the_verb.setId(rs.getLong("VERB_ID"));
		the_verb.setName(rs.getString("VERB_NAME"));
		the_verb.setLabel(rs.getString("VERB_LABEL"));
		the_verb.setDescription(rs.getString("VERB_DESCRIPTION"));
		the_verb.setNotes(rs.getString("VERB_NOTES"));
		the_verb.setAction_on_data(rs.getString("VERB_ACTION_ON_DATA"));
		the_verb.setScreen_id(rs.getString("VERB_SCREEN_ID"));
		the_verb.setActual_information(rs.getString("VERB_INFO"));
		ArrayList<GpAuthorization> auth_values = new ArrayList<GpAuthorization>();
		the_verb.setAuthorizations(auth_values);
		if(rs.getString("VERB_AUTHORIZATIONS") != null){
			if(rs.getString("VERB_AUTHORIZATIONS").trim().length() > 0){
				String[] temp = GpGenericRecordParserBuilder
						.parseDelimitedString(rs.getString("VERB_AUTHORIZATIONS"));
				for(String str  : temp){
					GpAuthorization auth = new GpAuthorization();
					auth.setId(new Long(str).longValue());
					auth_values.add(auth);
				}
			}
		}
		
		return the_verb;
		
	}


}
