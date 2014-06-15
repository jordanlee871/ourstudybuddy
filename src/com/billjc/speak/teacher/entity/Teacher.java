package com.billjc.speak.teacher.entity;

import java.io.Serializable;
import java.util.Date;

public class Teacher implements Serializable{

	private Long tea_id;
	private Long user_id;
	private String QQ;
	private String name;
	private String skype_num;
	private String email;
	private String phone;
	private int groupid;
	private String comment;
	private Date register_time;
	private String modify_log;
	private int private_set;
	private String user_name;

	
	public Teacher(){
		super();
	}
	
	public Teacher(String teaId) {
		super();
		this.tea_id=Long.parseLong(teaId);
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qq) {
		QQ = qq;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Long getTea_id() {
		return tea_id;
	}
	public void setTea_id(Long tea_id) {
		this.tea_id = tea_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSkype_num() {
		return skype_num;
	}
	public void setSkype_num(String skype_num) {
		this.skype_num = skype_num;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}
	public String getModify_log() {
		return modify_log;
	}
	public void setModify_log(String modify_log) {
		this.modify_log = modify_log;
	}
	public int getPrivate_set() {
		return private_set;
	}
	public void setPrivate_set(int private_set) {
		this.private_set = private_set;
	}
	

}
