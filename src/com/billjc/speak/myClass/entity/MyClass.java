package com.billjc.speak.myClass.entity;

import java.util.Date;

public class MyClass {
	
	private Long cls_id;
	private Long stu_id;
	private Long tea_id;
	private Date cls_begin_time;
	private float cls_length;
	private int cls_type;
	private int cls_status;
	private int stu_late;
	private int tea_late;
	private String booking_remark;
	private String cls_comment;
	
	
	public Long getCls_id() {
		return cls_id;
	}
	public void setCls_id(Long cls_id) {
		this.cls_id = cls_id;
	}
	public Long getStu_id() {
		return stu_id;
	}
	public void setStu_id(Long stu_id) {
		this.stu_id = stu_id;
	}
	public Date getCls_begin_time() {
		return cls_begin_time;
	}
	public void setCls_begin_time(Date cls_begin_time) {
		this.cls_begin_time = cls_begin_time;
	}
	public float getCls_length() {
		return cls_length;
	}
	public void setCls_length(float cls_length) {
		this.cls_length = cls_length;
	}
	public int getCls_type() {
		return cls_type;
	}
	public void setCls_type(int cls_type) {
		this.cls_type = cls_type;
	}
	public int getCls_status() {
		return cls_status;
	}
	public void setCls_status(int cls_status) {
		this.cls_status = cls_status;
	}
	public int getStu_late() {
		return stu_late;
	}
	public void setStu_late(int stu_late) {
		this.stu_late = stu_late;
	}
	public int getTea_late() {
		return tea_late;
	}
	public void setTea_late(int tea_late) {
		this.tea_late = tea_late;
	}
	public String getBooking_remark() {
		return booking_remark;
	}
	public void setBooking_remark(String booking_remark) {
		this.booking_remark = booking_remark;
	}
	public String getCls_comment() {
		return cls_comment;
	}
	public void setCls_comment(String cls_comment) {
		this.cls_comment = cls_comment;
	}
	public Long getTea_id() {
		return tea_id;
	}
	public void setTea_id(Long tea_id) {
		this.tea_id = tea_id;
	}
	

}
