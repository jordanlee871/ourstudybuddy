package com.billjc.essay.balance.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.essay.balance.entity.EssayBalance;
public class EssayBalanceRowMapper implements RowMapper<EssayBalance> {
	public EssayBalance mapRow(ResultSet rs, int row) throws SQLException {
		EssayBalance bl= new EssayBalance();
        
		bl.setBlId(rs.getLong("id"));
		bl.setAmount(rs.getLong("amount"));
		bl.setComment(rs.getString("wangwangno"));
		bl.setStuId(rs.getLong("student_id"));
		bl.setAssId(rs.getLong("ass_id"));
		bl.setCreateTime(rs.getTimestamp("create_time"));
		bl.setEssayType(rs.getString("type"));
		bl.setNum(rs.getLong("num"));
		bl.setTaobaoOrder(rs.getString("order_no"));
		
		return bl;
	}
	
	

}
