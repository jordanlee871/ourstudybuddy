package com.billjc.essay.student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

		Student student = new Student();

		student.setPassword(rs.getString("password"));
		student.setName(rs.getString("memberName"));
		student.setId(rs.getInt("id"));
		student.setActive(rs.getString("active"));
		student.setIsAdmin(rs.getString("isAdmin"));
		student.setRole(rs.getString("role"));
		student.setReal_name(rs.getString("real_name"));
		student.setGender(rs.getString("gender"));
		student.setPhone(rs.getString("phone"));
		student.setEmail(rs.getString("email"));
		student.setAddress(rs.getString("address"));
		student.setQq(rs.getString("qq"));
		student.setWanwan(rs.getString("wanwan"));
		student.setSkype(rs.getString("skype"));
		student.setCreate_time(rs.getTimestamp("create_time"));
		student.setAmount_task1(rs.getInt("amount_task1"));
		student.setCreate_by(rs.getInt("create_by"));
		student.setAmount_task2(rs.getInt("amount_task2"));
		student.setAmount_other(rs.getInt("amount_other"));
		student.setTimes(rs.getInt("times"));
		student.setCompo_count(rs.getInt("compo_count"));
		return student;
	}
}