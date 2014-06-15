package com.billjc.essay.appointment.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.essay.appointment.entity.EssayAppointment;
import com.billjc.essay.student.entity.EssayStudent;
public class EssayAppointmentRowMapper implements RowMapper<EssayAppointment> {
	/**
	 * 是否匹配学生。
	 * 0：不匹配（默认）
	 * 1；匹配
	 */
	private int studentFlat=0;
	public EssayAppointmentRowMapper(){
		super();
	}
	public EssayAppointmentRowMapper(int studentFlat){
		super();
		this.studentFlat=studentFlat;
	}
	public EssayAppointment mapRow(ResultSet rs, int row) throws SQLException {
		EssayAppointment ea= new EssayAppointment();
        
		ea.setAppointId(rs.getLong("appointment_id"));
		ea.setTeaId(rs.getLong("teacher_id"));
		ea.setStuId(rs.getLong("student_id"));
		ea.setDatetime(rs.getTimestamp("date"));
		ea.setCreateTime(rs.getTimestamp("create_time"));
		ea.setEssayType(rs.getString("type"));
		ea.setActive(rs.getInt("active"));
		ea.setCycle(rs.getLong("cycle"));
		if(1==studentFlat){
			EssayStudent stu= new EssayStudent();
	        
			stu.setStuId(rs.getLong("stu_id"));
			stu.setName(rs.getString("memberName"));
			stu.setPassword(rs.getString("password"));
			stu.setActive(rs.getString("stu_active"));
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
			stu.setCreateTime(rs.getTimestamp("stu_create_time"));
			stu.setAmountTask1(rs.getInt("amount_task1"));
			stu.setAmountTask2(rs.getInt("amount_task2"));
			stu.setAmountOther(rs.getInt("amount_other"));
			stu.setTimes(rs.getInt("times"));
			stu.setCycle(rs.getInt("compo_count"));
			stu.setCreateby(rs.getLong("stu_create_by"));
			
			ea.setStu(stu);
		}
		return ea;
	}
}
