package com.npb.gp.dao.mysql.support.isolanguages;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.npb.gp.domain.core.GpIsoLanguage;

public class IsoLanguagesMapper implements RowMapper<GpIsoLanguage>{

	@Override
	public GpIsoLanguage mapRow(ResultSet rs, int rownum) throws SQLException {
		GpIsoLanguage gpIsoLanguage = new GpIsoLanguage();
		gpIsoLanguage.setId(rs.getLong("id"));
		gpIsoLanguage.setIso_id(rs.getString("iso_id"));
		gpIsoLanguage.setLanguage_type(rs.getString("language_type"));
		gpIsoLanguage.setPart_1(rs.getString("part_1"));
		gpIsoLanguage.setPart_2b(rs.getString("part_2b"));
		gpIsoLanguage.setPart_2t(rs.getString("part_2t"));
		gpIsoLanguage.setRef_name(rs.getString("ref_name"));
		gpIsoLanguage.setScope(rs.getString("scope"));
		gpIsoLanguage.setComment(rs.getString("comment"));
		return gpIsoLanguage;
	}

}
