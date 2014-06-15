package com.billjc.speak.teacher.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.teacher.entity.Teacher;

public class TeacherRowMapper implements RowMapper<Teacher>{

	boolean flag=false;
	
	public TeacherRowMapper() {
		// TODO Auto-generated constructor stub
	}
	
	public TeacherRowMapper(boolean flag) {
		// TODO Auto-generated constructor stub
		this.flag=flag;
	}
	
	public Teacher mapRow(ResultSet rs, int count) throws SQLException {
		Teacher t=new Teacher();
		t.setTea_id(rs.getLong("tea_id"));
		t.setUser_id(rs.getLong("user_id"));
		t.setName(rs.getString("name"));
		t.setSkype_num(rs.getString("skype_num"));
		t.setEmail(rs.getString("email"));
		t.setPhone(rs.getString("phone"));
		t.setGroupid(rs.getInt("groupid"));
		t.setComment(rs.getString("comment"));
		t.setRegister_time(rs.getDate("register_time"));
		t.setModify_log(rs.getString("modify_log"));
		t.setPrivate_set(rs.getInt("private_set"));
		t.setComment(rs.getString("comment"));
		t.setQQ(rs.getString("QQ"));
		if(flag){
			
			t.setUser_name(rs.getString("user_name"));
		}
		return t;
	}
	
	
}
