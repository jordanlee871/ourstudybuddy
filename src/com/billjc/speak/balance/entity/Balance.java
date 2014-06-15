package com.billjc.speak.balance.entity;

import java.io.Serializable;
import java.util.Date;

import com.billjc.speak.students.entity.Student;


public class Balance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7257405025667997392L;

	private Long blId;
	private Long stuId;
	private Date dateTime;
	private String comment;
	private Float blNum;
	private Float balance_before;
	private Integer isCharge;
	public Float getBalance_before() {
		return balance_before;
	}
	public void setBalance_before(Float balanceBefore) {
		balance_before = balanceBefore;
	}
	public Long getBlId() {
		return blId;
	}
	public void setBlId(Long blId) {
		this.blId = blId;
	}
	public Long getStuId() {
		return stuId;
	}
	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Float getBlNum() {
		return blNum;
	}
	public void setBlNum(Float blNum) {
		this.blNum = blNum;
	}
	public Integer getIsCharge() {
		return isCharge;
	}
	public void setIsCharge(Integer isCharge) {
		this.isCharge = isCharge;
	}
	
	
	
}
