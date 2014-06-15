package com.billjc.speak.notice.entity;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609962455152890383L;
	
	private Integer noticeId;
	private Integer noticeType;
	private Integer toUserType;
	private Integer isSend;
	private Integer typeId;
	private Integer fromType;
	private Date updateTime;
	private Integer updateUser;
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public Integer getToUserType() {
		return toUserType;
	}
	public void setToUserType(Integer toUserType) {
		this.toUserType = toUserType;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getFromType() {
		return fromType;
	}
	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}
}
