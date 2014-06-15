package com.billjc.essay.balance.entity;

import java.io.Serializable;
import java.util.Date;



public class EssayBalance implements Serializable{
	private static final long serialVersionUID = 6857713303201257752L;
	private long blId;
	private long amount;
	private String comment;
	private long stuId;
	private long assId;
	private Date createTime;
	private String essayType;
	private long num;
	private String taobaoOrder;
	public long getBlId() {
		return blId;
	}
	public void setBlId(long blId) {
		this.blId = blId;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getStuId() {
		return stuId;
	}
	public void setStuId(long stuId) {
		this.stuId = stuId;
	}
	public long getAssId() {
		return assId;
	}
	public void setAssId(long assId) {
		this.assId = assId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEssayType() {
		return essayType;
	}
	public void setEssayType(String essayType) {
		this.essayType = essayType;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public String getTaobaoOrder() {
		return taobaoOrder;
	}
	public void setTaobaoOrder(String taobaoOrder) {
		this.taobaoOrder = taobaoOrder;
	}
	
}
