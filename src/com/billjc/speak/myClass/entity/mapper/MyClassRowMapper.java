package com.billjc.speak.myClass.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.myClass.entity.MyClass;

public class MyClassRowMapper implements RowMapper<MyClass> {
	
	public MyClass mapRow(ResultSet rs, int row) throws SQLException {
		MyClass myClass = new MyClass();
		myClass.setCls_id(rs.getLong("cls_id"));
		myClass.setStu_id(rs.getLong("stu_id"));
		myClass.setTea_id(rs.getLong("tea_id"));
		myClass.setCls_begin_time(rs.getDate("cls_begin_time"));
		myClass.setCls_length(rs.getFloat("cls_length"));
		myClass.setCls_type(rs.getInt("cls_type"));
		myClass.setCls_status(rs.getInt("cls_status"));
		myClass.setStu_late(rs.getInt("stu_late"));
		myClass.setTea_late(rs.getInt("tea_late"));
		myClass.setBooking_remark(rs.getString("booking_remark"));
		myClass.setCls_comment(rs.getString("cls_comment"));

		
		return myClass;
	}


}
