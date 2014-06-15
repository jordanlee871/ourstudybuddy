package com.billjc.speak.report.entity;

import com.billjc.framework.util.ExcelEntity;

public class FirstClassInfo extends ExcelEntity {
	private Integer stuId;
	private String stuName;
	private String stuLink;
	private Integer teaId;
	private String teaName;
	private String teaLink;
	private String clsBeginTime;
	private Integer clsType;
	private String clsTypeName;
	private Double clsLength;
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
	public String getClsBeginTime() {
		return clsBeginTime;
	}
	public void setClsBeginTime(String clsBeginTime) {
		this.clsBeginTime = clsBeginTime;
	}
	public Integer getClsType() {
		return clsType;
	}
	public void setClsType(Integer clsType) {
		this.clsType = clsType;
	}
	public String getClsTypeName() {
		return clsTypeName;
	}
	public void setClsTypeName(String clsTypeName) {
		this.clsTypeName = clsTypeName;
	}
	public Double getClsLength() {
		return clsLength;
	}
	public void setClsLength(Double clsLength) {
		this.clsLength = clsLength;
	}
}
