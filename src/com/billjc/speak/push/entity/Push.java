package com.billjc.speak.push.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.billjc.speak.notice.entity.Notice;

public class Push implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609962455152890383L;
	
	private Integer pushId;
	private String emailName;//系统邮件发送者
	private String emailPwd;//系统邮件发送者密码
	private String assEmail;//系统邮件发送者
	private String assTel;//助教电话号码
	private String assMsgKey;//发短信密钥
	private String assMsgName;//发短信的用户名
	
	private String noTrailNoticeMsg;//提醒课程-短信
	private String noTrailNoticeEmail;//提醒课程-邮件
	private String noTrailNoticeTitle;//提醒课程-标题
	
	private String trailNoticeMsg;//trail提醒课程-短信
	private String trailNoticeEmail;//trail提醒课程-邮件
	private String trailNoticeTitle;//trail提醒课程-标题
	
	private String bookMsg;//预约课程-短信
	private String bookEmail;//预约课程-邮件
	private String bookTitle;//预约课程-标题
	
	private String teaCxlMsg;//老师课程普通取消-短信
	private String teaCxlEmail;//老师课程普通取消-邮件
	private String teaCxlTitle;//老师课程普通取消-标题
	
	private String stuCxlMsg;//学生课程普通取消-短信
	private String stuCxlEmail;//学生课程普通取消-邮件
	private String stuCxlTitle;//学生课程普通取消-标题
	
	private String teaEmcxlMsg;//老师课程紧急取消-短信
	private String teaEmcxlEmail;//老师课程紧急取消-邮件
	private String teaEmcxlTitle;//老师课程紧急取消-标题
	
	private String stuEmcxlMsg;//学生课程紧急取消-短信
	private String stuEmcxlEmail;//学生课程紧急取消-邮件
	private String stuEmcxlTitle;//学生课程紧急取消-标题
	
	private String stuAbMsg;//学生缺席-短信
	private String stuAbEmail;//学生缺席-邮件
	private String stuAbTitle;//学生缺席-标题
	
	private String teaAbMsg;//老师缺席-短信
	private String teaAbEmail;//老师缺席-邮件
	private String teaAbTitle;//老师缺席-标题
	
	private String compMsg;//课程complete-短信
	private String compEmail;//课程complete-邮件
	private String compTitle;//课程complete-标题
	
	private Integer noTrail;//所有课程提前n个半小时短信邮件提醒学生
	private Integer trail;//Trial课程提前n个半小时短信邮件提醒学生
	
	private Integer updateUser;
	private Date updateTime;
	
	private List<Notice> listNotice;

	public Integer getPushId() {
		return pushId;
	}

	public void setPushId(Integer pushId) {
		this.pushId = pushId;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailPwd() {
		return emailPwd;
	}

	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}

	public String getAssEmail() {
		return assEmail;
	}

	public void setAssEmail(String assEmail) {
		this.assEmail = assEmail;
	}

	public String getAssTel() {
		return assTel;
	}

	public void setAssTel(String assTel) {
		this.assTel = assTel;
	}

	public String getAssMsgKey() {
		return assMsgKey;
	}

	public void setAssMsgKey(String assMsgKey) {
		this.assMsgKey = assMsgKey;
	}

	public String getBookMsg() {
		return bookMsg;
	}

	public void setBookMsg(String bookMsg) {
		this.bookMsg = bookMsg;
	}

	public String getBookEmail() {
		return bookEmail;
	}

	public void setBookEmail(String bookEmail) {
		this.bookEmail = bookEmail;
	}

	public String getTeaCxlMsg() {
		return teaCxlMsg;
	}

	public void setTeaCxlMsg(String teaCxlMsg) {
		this.teaCxlMsg = teaCxlMsg;
	}

	public String getTeaCxlEmail() {
		return teaCxlEmail;
	}

	public void setTeaCxlEmail(String teaCxlEmail) {
		this.teaCxlEmail = teaCxlEmail;
	}

	public String getStuCxlMsg() {
		return stuCxlMsg;
	}

	public void setStuCxlMsg(String stuCxlMsg) {
		this.stuCxlMsg = stuCxlMsg;
	}

	public String getStuCxlEmail() {
		return stuCxlEmail;
	}

	public void setStuCxlEmail(String stuCxlEmail) {
		this.stuCxlEmail = stuCxlEmail;
	}

	public String getTeaEmcxlMsg() {
		return teaEmcxlMsg;
	}

	public void setTeaEmcxlMsg(String teaEmcxlMsg) {
		this.teaEmcxlMsg = teaEmcxlMsg;
	}

	public String getTeaEmcxlEmail() {
		return teaEmcxlEmail;
	}

	public void setTeaEmcxlEmail(String teaEmcxlEmail) {
		this.teaEmcxlEmail = teaEmcxlEmail;
	}

	public String getStuEmcxlMsg() {
		return stuEmcxlMsg;
	}

	public void setStuEmcxlMsg(String stuEmcxlMsg) {
		this.stuEmcxlMsg = stuEmcxlMsg;
	}

	public String getStuEmcxlEmail() {
		return stuEmcxlEmail;
	}

	public void setStuEmcxlEmail(String stuEmcxlEmail) {
		this.stuEmcxlEmail = stuEmcxlEmail;
	}

	public String getStuAbMsg() {
		return stuAbMsg;
	}

	public void setStuAbMsg(String stuAbMsg) {
		this.stuAbMsg = stuAbMsg;
	}

	public String getStuAbEmail() {
		return stuAbEmail;
	}

	public void setStuAbEmail(String stuAbEmail) {
		this.stuAbEmail = stuAbEmail;
	}

	public String getCompMsg() {
		return compMsg;
	}

	public void setCompMsg(String compMsg) {
		this.compMsg = compMsg;
	}

	public String getCompEmail() {
		return compEmail;
	}

	public void setCompEmail(String compEmail) {
		this.compEmail = compEmail;
	}

	public Integer getNoTrail() {
		return noTrail;
	}

	public void setNoTrail(Integer noTrail) {
		this.noTrail = noTrail;
	}

	public Integer getTrail() {
		return trail;
	}

	public void setTrail(Integer trail) {
		this.trail = trail;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Notice> getListNotice() {
		return listNotice;
	}

	public void setListNotice(List<Notice> listNotice) {
		this.listNotice = listNotice;
	}

	public String getNoTrailNoticeMsg() {
		return noTrailNoticeMsg;
	}

	public void setNoTrailNoticeMsg(String noTrailNoticeMsg) {
		this.noTrailNoticeMsg = noTrailNoticeMsg;
	}

	public String getNoTrailNoticeEmail() {
		return noTrailNoticeEmail;
	}

	public void setNoTrailNoticeEmail(String noTrailNoticeEmail) {
		this.noTrailNoticeEmail = noTrailNoticeEmail;
	}

	public String getNoTrailNoticeTitle() {
		return noTrailNoticeTitle;
	}

	public void setNoTrailNoticeTitle(String noTrailNoticeTitle) {
		this.noTrailNoticeTitle = noTrailNoticeTitle;
	}

	public String getTrailNoticeMsg() {
		return trailNoticeMsg;
	}

	public void setTrailNoticeMsg(String trailNoticeMsg) {
		this.trailNoticeMsg = trailNoticeMsg;
	}

	public String getTrailNoticeEmail() {
		return trailNoticeEmail;
	}

	public void setTrailNoticeEmail(String trailNoticeEmail) {
		this.trailNoticeEmail = trailNoticeEmail;
	}

	public String getTrailNoticeTitle() {
		return trailNoticeTitle;
	}

	public void setTrailNoticeTitle(String trailNoticeTitle) {
		this.trailNoticeTitle = trailNoticeTitle;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getTeaCxlTitle() {
		return teaCxlTitle;
	}

	public void setTeaCxlTitle(String teaCxlTitle) {
		this.teaCxlTitle = teaCxlTitle;
	}

	public String getStuCxlTitle() {
		return stuCxlTitle;
	}

	public void setStuCxlTitle(String stuCxlTitle) {
		this.stuCxlTitle = stuCxlTitle;
	}

	public String getTeaEmcxlTitle() {
		return teaEmcxlTitle;
	}

	public void setTeaEmcxlTitle(String teaEmcxlTitle) {
		this.teaEmcxlTitle = teaEmcxlTitle;
	}

	public String getStuEmcxlTitle() {
		return stuEmcxlTitle;
	}

	public void setStuEmcxlTitle(String stuEmcxlTitle) {
		this.stuEmcxlTitle = stuEmcxlTitle;
	}

	public String getStuAbTitle() {
		return stuAbTitle;
	}

	public void setStuAbTitle(String stuAbTitle) {
		this.stuAbTitle = stuAbTitle;
	}

	public String getCompTitle() {
		return compTitle;
	}

	public void setCompTitle(String compTitle) {
		this.compTitle = compTitle;
	}

	public String getTeaAbMsg() {
		return teaAbMsg;
	}

	public void setTeaAbMsg(String teaAbMsg) {
		this.teaAbMsg = teaAbMsg;
	}

	public String getTeaAbEmail() {
		return teaAbEmail;
	}

	public void setTeaAbEmail(String teaAbEmail) {
		this.teaAbEmail = teaAbEmail;
	}

	public String getTeaAbTitle() {
		return teaAbTitle;
	}

	public void setTeaAbTitle(String teaAbTitle) {
		this.teaAbTitle = teaAbTitle;
	}

	public String getAssMsgName() {
		return assMsgName;
	}

	public void setAssMsgName(String assMsgName) {
		this.assMsgName = assMsgName;
	}
	
	
}
