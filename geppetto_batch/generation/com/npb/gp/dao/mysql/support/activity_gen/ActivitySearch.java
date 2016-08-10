package com.npb.gp.dao.mysql.support.activity_gen;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import java.sql.Types;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.npb.gp.domain.core.GpActivity;

public class ActivitySearch extends MappingSqlQuery {

	private static final String SQL_SELECT_WITH_SECONDARY_NOUN_SCREEN = "select activities.id as ACTIVITY_ID, activities.name ACTIVITY_NAME, activities.label ACTIVITY_LABEL,"
			+ " geppetto.screens.id as SCREEN_ID, geppetto.screens.label as SCREEN_LABEL from geppetto.activities"
			+ " left join geppetto.screens on geppetto.activities.id = geppetto.screens.activityid WHERE activities.id IN "
			+ " (SELECT id FROM geppetto.activities WHERE id = :id)"; 
	
	private static final String SQL_SELECT_WITH_SECONDARY_NOUN_NOUNS = "";
 
	
	/*
	select activities.id as ACTIVITY_ID, activities.name ACTIVITY_NAME, activities.label ACTIVITY_LABEL, geppetto.screens.id as SCREEN_ID, geppetto.screens.label from geppetto.activities
	left join geppetto.screens on geppetto.activities.id = geppetto.screens.activityid WHERE activities.id IN
	(SELECT id FROM geppetto.activities WHERE id = 1)
	*/
	
	
	protected final Object mapRow(final ResultSet rs, final int rowNum) throws SQLException{
		
		GpDtoActivity_and_Screens dto = new GpDtoActivity_and_Screens();

		dto.setId(rs.getLong("ACTIVITY_ID"));
		dto.setName(rs.getString("ACTIVITY_NAME"));
		dto.setLabel(rs.getString("ACTIVITY_LABEL"));
		
		dto.setScreen_id(rs.getLong("SCREEN_ID"));
		dto.setScreen_label(rs.getString("SCREEN_LABEL"));

        return dto;
		
	}

	
	public ActivitySearch(DataSource dataSource) {
        super(dataSource, SQL_SELECT_WITH_SECONDARY_NOUN_SCREEN);
        super.declareParameter(new SqlParameter("id", Types.BIGINT));
        System.out.println("In ActivitySearch");

    }

}
