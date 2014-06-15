package com.billjc.speak.report.entity;

import com.billjc.framework.util.ExcelEntity;

/**
 * 学生课程进度跟进
 * @author Administrator
 *
 */
public class StuClsInfo extends ExcelEntity {
	
	private Integer stuId;
	private String stuName;
	private String stuLink;
	private Integer teaId;
	private String teaName;
	private String teaLink;
	private String lastBuy;
	private Double balance;
	private String clsBeginTime;
	private String blDatetime;
	public Integer getStuId() {
		return stuId;
	}
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuLink() {
		return stuLink;
	}
	public void setStuLink(String stuLink) {
		this.stuLink = stuLink;
	}
	public Integer getTeaId() {
		return teaId;
	}
	public void setTeaId(Integer teaId) {
		this.teaId = teaId;
	}
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getTeaLink() {
		return teaLink;
	}
	public void setTeaLink(String teaLink) {
		this.teaLink = teaLink;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getClsBeginTime() {
		return clsBeginTime;
	}
	public void setClsBeginTime(String clsBeginTime) {
		this.clsBeginTime = clsBeginTime;
	}
	public String getLastBuy() {
		return lastBuy;
	}
	public void setLastBuy(String lastBuy) {
		this.lastBuy = lastBuy;
	}
	public String getBlDatetime() {
		return blDatetime;
	}
	public void setBlDatetime(String blDatetime) {
		this.blDatetime = blDatetime;
	}
	
}
