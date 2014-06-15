package com.billjc.speak.balance.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.balance.entity.Balance;
public class BalanceRowMapper implements RowMapper<Balance> {
	public Balance mapRow(ResultSet rs, int row) throws SQLException {
		Balance bl= new Balance();
        
		bl.setBalance_before(rs.getFloat("bl_balance_before"));
		bl.setBlNum(rs.getFloat("bl_num"));
		bl.setComment(rs.getString("bl_comment"));
		bl.setStuId(rs.getLong("stu_id"));
		bl.setBlId(rs.getLong("bl_id"));
		bl.setDateTime(rs.getTimestamp("bl_datetime"));
		
		return bl;
	}
	
	

}
