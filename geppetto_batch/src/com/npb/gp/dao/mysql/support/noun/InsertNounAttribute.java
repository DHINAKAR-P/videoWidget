package com.npb.gp.dao.mysql.support.noun;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class InsertNounAttribute extends SqlUpdate {
	
	public static  String SQL_INSERT_ATTRIBUTE = "";
	
	public InsertNounAttribute(DataSource dataSource){
		super(dataSource, SQL_INSERT_ATTRIBUTE);
		
        super.declareParameter(new SqlParameter("name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("label", Types.VARCHAR));
        super.declareParameter(new SqlParameter("description", Types.VARCHAR));
        super.declareParameter(new SqlParameter("nounid", Types.BIGINT));
        super.declareParameter(new SqlParameter("type", Types.VARCHAR));
        super.declareParameter(new SqlParameter("sub_type", Types.VARCHAR));
        super.declareParameter(new SqlParameter("sub_type_modifier", Types.VARCHAR));
        super.declareParameter(new SqlParameter("part_of_unique_key", Types.VARCHAR));
        super.declareParameter(new SqlParameter("data_length", Types.VARCHAR));
        super.declareParameter(new SqlParameter("relationships", Types.VARCHAR));
        
        
        super.declareParameter(new SqlParameter("notes", Types.VARCHAR));
        super.declareParameter(new SqlParameter("created_date", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("created_by", Types.BIGINT));
        super.declareParameter(new SqlParameter("last_modified_date", Types.TIMESTAMP));
        super.declareParameter(new SqlParameter("last_modified_by", Types.BIGINT));

        super.setGeneratedKeysColumnNames(new String[] {"id"});
        super.setReturnGeneratedKeys(true);
		
		
	}

}
