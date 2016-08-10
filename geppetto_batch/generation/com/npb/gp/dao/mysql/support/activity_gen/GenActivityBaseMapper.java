package com.npb.gp.dao.mysql.support.activity_gen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gb.utils.GpGenericRecordParserBuilder;
import com.npb.gp.domain.core.GpActivity;
import com.npb.gp.domain.core.GpAuthorization;
import com.npb.gp.domain.core.GpNoun;
import com.npb.gp.domain.core.GpVerb;

public class GenActivityBaseMapper implements RowMapper<GpActivity> {

	
	@Override
	public GpActivity mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		GpActivity dto = new GpActivity();

		dto.setId(rs.getLong("ACTIVITY_ID"));
		dto.setName(rs.getString("ACTIVITY_NAME"));
		dto.setLabel(rs.getString("ACTIVITY_LABEL"));
		dto.setDescription(rs.getString("ACTIVITY_DESCRIPTION"));
		dto.setProjectid(rs.getLong("ACTIVITY_PROJECT_ID"));
		long temp_noun_id = rs.getLong("ACTIVITY_PRIMARY_NOUN_ID");
		if(temp_noun_id > 0){
			GpNoun temp_noun = new GpNoun();
			temp_noun.setId(temp_noun_id);
			dto.setPrimary_noun(temp_noun);
		}
		
		dto.setModuleid(rs.getLong("ACTIVITY_MODULE_ID"));
		dto.setNotes(rs.getString("ACTIVITY_NOTES"));

		dto.setLocation_logic(rs.getString("ACTIVITY_LOCATION_LOGIC"));
		dto.setProcessing_context(rs.getString("ACTIVITY_PROCESSING_CONTEXT"));
		dto.setMaster_flow_id(rs.getLong("ACTIVITY_MASTER_FLOW_ID"));
		

		ArrayList<GpNoun> values = new ArrayList<GpNoun>();
		dto.setSecondary_nouns(values);
		if(rs.getString("ACTIVITY_SECONDARY_NOUNS") != null){
			if(rs.getString("ACTIVITY_SECONDARY_NOUNS").trim().length() > 0){
					String[] temp = GpGenericRecordParserBuilder
							.parseDelimitedString(rs.getString("ACTIVITY_SECONDARY_NOUNS"));
					for(String str  : temp){
						GpNoun a_noun = new GpNoun(); 
						a_noun.setId(new Long(str).longValue());
						values.add(a_noun);
					}
			}
		
			
		}
		
		ArrayList<GpVerb> the_verbs = new ArrayList<GpVerb>();
		if(rs.getString("ACTIVITY_SUPPORTED_VERBS") != null){
			
			HashMap<String, String> verb_auth_map = GpGenericRecordParserBuilder
					.parseNameValuePairsString(rs.getString("ACTIVITY_SUPPORTED_VERBS"));

			for(Entry<String, String> entry : verb_auth_map.entrySet()){
				GpVerb verb = new GpVerb();
				verb.setId(new Long(entry.getKey()));
				GpAuthorization an_auth = new GpAuthorization();
				an_auth.setId(new Long(entry.getValue()));
				verb.set_an_authorization(an_auth);
				
				the_verbs.add(verb);
				
			}
				
		}
		dto.setTheverbs(the_verbs);
		
		
		dto.setCreatedate(rs.getTimestamp("CREATE_DATE"));
		dto.setCreatedby(rs.getLong("CREATED_BY"));
		dto.setLastmodifieddate(rs.getTimestamp("LAST_MODIFIED_DATE"));

		dto.setLastmodifiedby(rs.getLong("LAST_MODIFIED_BY"));
		
		dto.setModule_type(rs.getString("MODULE_TYPE"));
		
        return dto;
        

	}
	

}
