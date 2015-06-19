package com.billjc.essay.appointment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.billjc.essay.appointment.entity.EssayAppointment;
import com.billjc.essay.appointment.entity.mapper.EssayAppointmentRowMapper;
import com.billjc.framework.BaseJdbcDaoEssay;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.essay.appointment.dao.EssayAppointmentDao;

@Service
public class EssayAppointmentService {
	@Autowired EssayAppointmentDao essayapptDao;
	
	public List<EssayAppointment> queryApptObject( int startLine ){
		return this.essayapptDao.queryApptObject(startLine);
	}
	
	public List<String> queryTeacherName( ){
		return this.essayapptDao.queryTeacherName();
	}
	
	public List<String> queryTeacherId( ){
		return this.essayapptDao.queryTeacherId();
	}
	
	public List<String> queryTeacherActive( ){
		return this.essayapptDao.queryTeacherActive();
	}
	
	public String queryApptTeacher( long teacherid ){
		return this.essayapptDao.queryApptTeacher( teacherid );
	}

	public String queryStudenteMail( long stuid ){
		return this.essayapptDao.queryStudenteMail( stuid );
	}
	
	public String queryStudentName( long stuid ){
		return this.essayapptDao.queryStudentName( stuid );
	}
	
	public List<Integer> queryTeacherAppt(){
		return this.essayapptDao.queryTeacherAppt();
	}
	
	public List queryTeacherEdit(){
		return this.essayapptDao.queryTeacherEdit();
	}
}
