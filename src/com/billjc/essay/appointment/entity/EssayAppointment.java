package com.billjc.essay.appointment.entity;

import java.io.Serializable;
import java.util.Date;

import com.billjc.essay.student.entity.EssayStudent;



public class EssayAppointment implements Serializable{

	private static final long serialVersionUID = 3194580462232044813L;
	
	private long appointId;
	private long teaId;
	private long stuId;
	private Date datetime;
	private Date createTime;
	private long createBy;
	private String essayType;
	private int active;
	private long cycle;
	private EssayStudent stu;
	public long getAppointId() {
		return appointId;
	}
	public void setAppointId(long appointId) {
		this.appointId = appointId;
	}
	public long getTeaId() {
		return teaId;
	}
	public void setTeaId(long teaId) {
		this.teaId = teaId;
	}
	public long getStuId() {
		return stuId;
	}
	public void setStuId(long stuId) {
		this.stuId = stuId;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}
	public String getEssayType() {
		return essayType;
	}
	public void setEssayType(String essayType) {
		this.essayType = essayType;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public long getCycle() {
		return cycle;
	}
	public void setCycle(long cycle) {
		this.cycle = cycle;
	}
	public EssayStudent getStu() {
		return stu;
	}
	public void setStu(EssayStudent stu) {
		this.stu = stu;
	}
	
}
