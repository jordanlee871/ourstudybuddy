package com.billjc.speak.privateset.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.privateset.entity.Privateset;
public class PrivatesetRowMapper implements RowMapper<Privateset> {
	public Privateset mapRow(ResultSet rs, int row) throws SQLException {
		Privateset ps= new Privateset();
		ps.setPsId(rs.getLong("ps_id"));
		ps.setStuId(rs.getLong("stu_id"));
		ps.setTeaId(rs.getLong("tea_id"));
		ps.setStatus(rs.getString("status"));
         
		return ps;
	}
	
	

}
