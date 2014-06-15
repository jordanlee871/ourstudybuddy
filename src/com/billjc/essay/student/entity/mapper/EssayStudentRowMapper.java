package com.billjc.essay.student.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.essay.student.entity.EssayStudent;
public class EssayStudentRowMapper implements RowMapper<EssayStudent> {
	public EssayStudent mapRow(ResultSet rs, int row) throws SQLException {
		EssayStudent stu= new EssayStudent();
        
		stu.setStuId(rs.getLong("id"));
		stu.setName(rs.getString("memberName"));
		stu.setPassword(rs.getString("password"));
		stu.setActive(rs.getString("active"));
		stu.setIsAdmin(rs.getString("isAdmin"));
		stu.setRole(rs.getString("role"));
		stu.setRealName(rs.getString("real_name"));
		stu.setGender(rs.getString("gender"));
		stu.setPhone(rs.getString("phone"));
		stu.setEmail(rs.getString("email"));
		stu.setAddress(rs.getString("address"));
		stu.setQq(rs.getString("qq"));
		stu.setWw(rs.getString("wanwan"));
		stu.setSkype(rs.getString("skype"));
		stu.setCreateTime(rs.getTimestamp("create_time"));
		stu.setAmountTask1(rs.getInt("amount_task1"));
		stu.setAmountTask2(rs.getInt("amount_task2"));
		stu.setAmountOther(rs.getInt("amount_other"));
		stu.setTimes(rs.getInt("times"));
		stu.setCycle(rs.getInt("compo_count"));
		stu.setCreateby(rs.getLong("create_by"));
		return stu;
	}
}
