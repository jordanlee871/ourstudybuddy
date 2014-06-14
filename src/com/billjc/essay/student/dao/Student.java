package com.billjc.essay.student.dao;

import java.util.Date;
import java.sql.Timestamp;

public class Student {
	private String password;
	private String memberName;
	private Integer id;
	private String active;
	private String isAdmin;
	private String role;
	private String real_name;
	private String gender;
	private String phone;
	private String email;
	private String address;
	private String qq;
	private String wanwan;
	private String skype;
	private Timestamp create_time;
	private Integer amount_task1;
	private Integer create_by;
	private Integer amount_task2;
	private Integer amount_other;
	private Integer times;
	private Integer compo_count;

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String memberName) {
		this.memberName = memberName;
	}

	public String getName() {
		return memberName;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setWanwan(String wanwan) {
		this.wanwan = wanwan;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public void setAmount_task1(Integer amount_task1) {
		this.amount_task1 = amount_task1;
	}

	public void setCreate_by(Integer create_by) {
		this.create_by = create_by;
	}

	public void setAmount_task2(Integer amount_task2) {
		this.amount_task2 = amount_task2;
	}

	public void setAmount_other(Integer amount_other) {
		this.amount_other = amount_other;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public void setCompo_count(Integer compo_count) {
		this.compo_count = compo_count;
	}

	public String getActive() {
		return this.active;
	}

	public String getIsAdmin() {
		return this.isAdmin;
	}

	public String getRole() {
		return this.role;
	}

	public String getReal_name() {
		return this.real_name;
	}

	public String getGender() {
		return this.gender;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getEmail() {
		return this.email;
	}

	public String getAddress() {
		return this.address;
	}

	public String getQq() {
		return this.qq;
	}

	public String getWanwan() {
		return this.wanwan;
	}

	public String getSkype() {
		return this.skype;
	}

	public Timestamp getCreate_time() {
		return this.create_time;
	}

	public Integer getAmount_task1() {
		return this.amount_task1;
	}

	public Integer getCreate_by() {
		return this.create_by;
	}

	public Integer getAmount_task2() {
		return this.amount_task2;
	}

	public Integer getAmount_other() {
		return this.amount_other;
	}

	public Integer getTimes() {
		return this.times;
	}

	public Integer getCompo_count() {
		return this.compo_count;
	}
}
