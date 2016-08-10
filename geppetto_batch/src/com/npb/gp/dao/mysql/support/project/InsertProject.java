package com.npb.gp.dao.mysql.support.project;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
/**
 * 
 * @author Dan Castillo</br>
 * Date Created: 04/15/2014</br>
 * @since .35</p>  
 *
 * Insert class supporting the GpProjectDao class</p>
 *
 *
 */

public class InsertProject extends SqlUpdate {

	public static  String SQL_INSERT_PROJECT = "";
	
	public InsertProject(DataSource dataSource) {
        super(dataSource, SQL_INSERT_PROJECT);
        
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("label", Types.VARCHAR));
        super.declareParameter(new SqlParameter("description", Types.VARCHAR));
        super.declareParameter(new SqlParameter("default_module_id", Types.BIGINT));
        super.declareParameter(new SqlParameter("default_module_label", Types.VARCHAR));

        super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
        super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
        super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));

        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
        
	}

}
