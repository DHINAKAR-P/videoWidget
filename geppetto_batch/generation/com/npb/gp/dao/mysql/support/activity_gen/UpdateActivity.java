package com.npb.gp.dao.mysql.support.activity_gen;

import java.sql.Types;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 03/10/2014</br>
 * @since .35</p>  
 *
 * Update class supporting the GpActivityDao class</p>
 *
 *
 */


public class UpdateActivity extends SqlUpdate {
	public static  String SQL_UPDATE_ACTIVITY = "";
	
	public UpdateActivity(DataSource dataSource) {
        super(dataSource, SQL_UPDATE_ACTIVITY);
        super.declareParameter(new SqlParameter("id", Types.BIGINT));
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("label", Types.VARCHAR));
        super.declareParameter(new SqlParameter("description", Types.VARCHAR));
        super.declareParameter(new SqlParameter("projectid", Types.BIGINT));
        super.declareParameter(new SqlParameter("moduleid", Types.BIGINT));
        super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
        super.declareParameter(new SqlParameter("primary_noun_id", Types.BIGINT));
        super.declareParameter(new SqlParameter("activity_types", Types.VARCHAR));
        super.declareParameter(new SqlParameter("secondary_nouns", Types.VARCHAR));
        super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));
		
	}
	
}
