package com.billjc.essay.student.entity;

import java.io.Serializable;
import java.util.Date;



public class EssayStudent implements Serializable{
	
	private static final long serialVersionUID = 4351794995925711145L;
	private long stuId;
	private String name;
	private String password;
	private String active;
	private String isAdmin;
	private String role;
	private String realName;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private String qq;
	private String ww;
	private String skype;
	private Date createTime;
	private long amountTask1;
	private long amountTask2;
	private long amountOther;
	private int times;
	private int cycle;
	private long createby;
	public long getStuId() {
		return stuId;
	}
	public void setStuId(long stuId) {
		this.stuId = stuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWw() {
		return ww;
	}
	public void setWw(String ww) {
		this.ww = ww;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getAmountTask1() {
		return amountTask1;
	}
	public void setAmountTask1(int amountTask1) {
		this.amountTask1 = amountTask1;
	}
	public long getAmountTask2() {
		return amountTask2;
	}
	public void setAmountTask2(int amountTask2) {
		this.amountTask2 = amountTask2;
	}
	public long getAmountOther() {
		return amountOther;
	}
	public void setAmountOther(int amountOther) {
		this.amountOther = amountOther;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public long getCreateby() {
		return createby;
	}
	public void setCreateby(long createby) {
		this.createby = createby;
	}
	
}
