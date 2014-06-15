package com.billjc.speak.students.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.students.entity.Student;

public class StudentRowMapper implements RowMapper<Student> {
	private int flag = 0;

	public StudentRowMapper(int flag) {
		this.flag = flag;
	}

	public StudentRowMapper() {
	}

	public Student mapRow(ResultSet rs, int row) throws SQLException {
		Student student = new Student();
		student.setStu_id(rs.getLong("stu_id"));
		student.setUser_id(rs.getLong("user_id"));
		student.setWw_num(rs.getString("ww_num"));
		student.setEname(rs.getString("ename"));
		student.setPhone(rs.getString("phone"));
		student.setQq_num(rs.getString("qq_num"));
		student.setSkype_num(rs.getString("skype_num"));
		student.setEmail(rs.getString("email"));
		student.setRegister_time(rs.getDate("register_time"));
		student.setBalance(rs.getFloat("balance"));
		student.setStatus(rs.getInt("status"));
		student.setRemark(rs.getString("remark"));
		student.setTeacher_remark(rs.getString("teacher_remark"));
		student.setModify_log(rs.getString("modify_log"));
		student.setNotifySMS(rs.getString("notify_SMS"));
		student.setNotifyMail(rs.getString("notify_mail"));
		student.setLastClass(rs.getTimestamp("last_class"));
		if (flag == 2) {
			student.setCls_begin_time(rs.getDate("cls_begin_time"));
		}
		if (flag == 1) {
			student.setTeaName(rs.getString("name"));
			student.setSch_datetime(rs.getDate("sch_datetime"));
		}

		return student;
	}

}
