package com.billjc.speak.students.entity;

import java.io.Serializable;
import java.util.Date;

import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.teacher.entity.Teacher;

public class Student implements Serializable {
	private static final long serialVersionUID = -291110113447249284L;

	private long stu_id;
	private long user_id; // 外键 User表的ID；
	private String ww_num;
	private String email;
	private String ename; // 英文名
	private String phone;
	private String qq_num;
	private String skype_num; // skype号码
	private Date register_time; // 注册时间
	private int status; // 是否已确认
	private String remark;
	private String teacher_remark;
	private float balance; // 余额
	private String modify_log;
	private String teaName; // 上课老师的姓名
	private Date sch_datetime;
	private Date cls_begin_time;
	/**
	 * 短信通知. Y:短信通知 N:不发短信通知 D:默认处理
	 */
	private String notifySMS;
	/**
	 * 邮件通知. Y:邮件通知 N:不发邮件通知 D:默认处理
	 */
	private String notifyMail;
	/**
	 * 最后一节课的上课时间
	 */
	private Date lastClass;

	public Date getCls_begin_time() {
		return cls_begin_time;
	}

	public void setCls_begin_time(Date cls_begin_time) {
		this.cls_begin_time = cls_begin_time;
	}

	// 记录修改过的字段名称
	public long getStu_id() {
		return stu_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public String getWw_num() {
		return ww_num;
	}

	public String getEmail() {
		return email;
	}

	public String getEname() {
		return ename;
	}

	public String getPhone() {
		return phone;
	}

	public String getQq_num() {
		return qq_num;
	}

	public String getSkype_num() {
		return skype_num;
	}

	public Date getRegister_time() {
		return register_time;
	}

	public int getStatus() {
		return status;
	}

	public String getRemark() {
		return remark;
	}

	public String getTeacher_remark() {
		return teacher_remark;
	}

	public float getBalance() {
		return balance;
	}

	public String getModify_log() {
		return modify_log;
	}

	public void setStu_id(long stu_id) {
		this.stu_id = stu_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public void setWw_num(String ww_num) {
		this.ww_num = ww_num;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setQq_num(String qq_num) {
		this.qq_num = qq_num;
	}

	public void setSkype_num(String skype_num) {
		this.skype_num = skype_num;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTeacher_remark(String teacher_remark) {
		this.teacher_remark = teacher_remark;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public void setModify_log(String modify_log) {
		this.modify_log = modify_log;
	}

	public String getTeaName() {
		return teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public Date getSch_datetime() {
		return sch_datetime;
	}

	public void setSch_datetime(Date sch_datetime) {
		this.sch_datetime = sch_datetime;
	}

	public String getNotifySMS() {
		return notifySMS;
	}

	public void setNotifySMS(String notifySMS) {
		this.notifySMS = notifySMS;
	}

	public String getNotifyMail() {
		return notifyMail;
	}

	public void setNotifyMail(String notifyMail) {
		this.notifyMail = notifyMail;
	}

	public Date getLastClass() {
		return lastClass;
	}

	public void setLastClass(Date lastClass) {
		this.lastClass = lastClass;
	}
	

}
