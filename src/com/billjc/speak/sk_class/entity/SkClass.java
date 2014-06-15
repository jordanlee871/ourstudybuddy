package com.billjc.speak.sk_class.entity;

import java.io.Serializable;
import java.util.Date;

import com.billjc.framework.util.DateUtil;

public class SkClass implements Serializable {

	private static final long serialVersionUID = -202110113447249284L;

	private Long clsId;
	private Long stuId;
	private Long teaId;
	private Float clsLength;
	private Date beginTime;
	private Integer clsType;
	private Integer clsStatus;
	private Integer stuLate;
	private Integer teaLate;
	private String bookingRemark;
	private String clsComment;
	private String ename;
	private String name;
	
	private String beginTimeEx;

	public String getBeginTimeEx() {
		if(beginTime!=null) {
			beginTimeEx=DateUtil.getDateMinuteFormat(beginTime);
		}
		return beginTimeEx;
	}

	public void setBeginTimeEx(String beginTimeEx) {
		this.beginTimeEx = beginTimeEx;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getClsId() {
		return clsId;
	}

	public void setClsId(Long clsId) {
		this.clsId = clsId;
	}

	public Long getStuId() {
		return stuId;
	}

	public void setStuId(Long stuId) {
		this.stuId = stuId;
	}

	public Long getTeaId() {
		return teaId;
	}

	public void setTeaId(Long teaId) {
		this.teaId = teaId;
	}

	public Float getClsLength() {
		return clsLength;
	}

	public void setClsLength(Float clsLength) {
		this.clsLength = clsLength;
	}

	public Date getBeginTime() {
		
		
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}



	public Integer getClsType() {
		return clsType;
	}

	public void setClsType(Integer clsType) {
		this.clsType = clsType;
	}

	public Integer getClsStatus() {
		return clsStatus;
	}

	public void setClsStatus(Integer clsStatus) {
		this.clsStatus = clsStatus;
	}

	public Integer getStuLate() {
		return stuLate;
	}

	public void setStuLate(Integer stuLate) {
		this.stuLate = stuLate;
	}

	public Integer getTeaLate() {
		return teaLate;
	}

	public void setTeaLate(Integer teaLate) {
		this.teaLate = teaLate;
	}

	public String getBookingRemark() {
		if(bookingRemark!=null)
			bookingRemark=bookingRemark.trim();
		return bookingRemark;
	}

	public void setBookingRemark(String bookingRemark) {

		if(bookingRemark!=null)
			bookingRemark=bookingRemark.trim();
		
		this.bookingRemark = bookingRemark;
	}

	public String getClsComment() {
		if(clsComment!=null)
			clsComment=clsComment.trim();
		return clsComment;
	}

	public void setClsComment(String clsComment) {
		if(clsComment!=null)
			clsComment=clsComment.trim();
		this.clsComment = clsComment;
	}

}
