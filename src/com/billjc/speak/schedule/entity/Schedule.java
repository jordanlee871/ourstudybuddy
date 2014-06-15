package com.billjc.speak.schedule.entity;

import java.io.Serializable;
import java.util.Date;

import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.students.entity.Student;

public class Schedule implements Serializable{

	private Long sch_id;
	private Long stu_id;
	private Long tea_id;
	private int sch_length;
	private Long status;
	private Date sch_datetime;
	private Student student = null;
	private SkClass cls = null;
	
	
	public Long getSch_id() {
		return sch_id;
	}
	public void setSch_id(Long schId) {
		sch_id = schId;
	}
	public Long getStu_id() {
		return stu_id;
	}
	public void setStu_id(Long stuId) {
		stu_id = stuId;
	}
	public Long getTea_id() {
		return tea_id;
	}
	public void setTea_id(Long teaId) {
		tea_id = teaId;
	}
	public int getSch_length() {
		return sch_length;
	}
	public void setSch_length(int schLength) {
		sch_length = schLength;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getSch_datetime() {
		return sch_datetime;
	}
	public void setSch_datetime(Date schDatetime) {
		sch_datetime = schDatetime;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public SkClass getCls() {
		return cls;
	}
	public void setCls(SkClass cls) {
		this.cls = cls;
	}
	
}
