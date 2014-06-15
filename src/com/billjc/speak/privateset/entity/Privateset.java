package com.billjc.speak.privateset.entity;

import java.io.Serializable;
import java.util.Date;

public class Privateset implements Serializable{
	private static final long serialVersionUID = -2912110113447249289L;

	private Long psId;
	private Long stuId;
	private Long teaId;
	private String status;
	public Long getPsId() {
		return psId;
	}
	public void setPsId(Long psId) {
		this.psId = psId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
