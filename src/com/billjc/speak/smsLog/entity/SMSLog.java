package com.billjc.speak.smsLog.entity;

import java.util.Date;

public class SMSLog {
	public String toPhone;
	public String text;
	public String result;
	public Integer statusCode;
	public Integer logId;
	public String exMsg;
	public Date createTime;
	public Integer createUser;
	public String getToPhone() {
		return toPhone;
	}
	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public String getExMsg() {
		return exMsg;
	}
	public void setExMsg(String exMsg) {
		this.exMsg = exMsg;
	}
	
}
