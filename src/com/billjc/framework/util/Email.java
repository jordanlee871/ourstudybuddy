package com.billjc.framework.util;

import java.util.Date;

public class Email {
	private String status;                            //发送状态
    
	private String mail_from;                         //邮件发送者地址
	private String mail_to;                          //邮件接收者地址列表
	private String mail_cc="";                      //邮件抄送地址列表
	private String mail_bcc="";                     //邮件广播地址列表
	
	
	private String mail_subject;                   //邮件主题
	
	private Date insert_date;                     //邮件插入日期
	private Date start_time;                     //邮件开始日期 
	private Date end_time;                      //截止日期
	private Date send_date;                    //邮件发送日期
	
	private String smtp;
	private String parent;
	private Integer mass_id;
	private Integer open_count;
	private String attachment_name;
	
    private String password;
    private String user_name;
    private String attachment_file;
    
    private String logo1;
    private String logo2;
    
    private String return_time;
    private String error_code;
    
	private String reply_email;
	

	private String date;                        //邮件发送或接收日期
	private int size;                          //邮件大小
	private String mailContent="";                   //邮件正文
	private boolean readFlag;                //邮件已读标记
	private boolean hasAdjunct = false;      //是否包含附件
	private String adjunct="";
	
	private Integer mail_id;	
	private Integer case_id;
	private String system_remark;
	private Integer session_id;
	private String  mail_code;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMail_from() {
		return mail_from;
	}
	public void setMail_from(String mailFrom) {
		mail_from = mailFrom;
	}
	public String getMail_to() {
		return mail_to;
	}
	public void setMail_to(String mailTo) {
		mail_to = mailTo;
	}
	public String getMail_cc() {
		return mail_cc;
	}
	public void setMail_cc(String mailCc) {
		mail_cc = mailCc;
	}
	public String getMail_bcc() {
		return mail_bcc;
	}
	public void setMail_bcc(String mailBcc) {
		mail_bcc = mailBcc;
	}
	public String getMail_subject() {
		return mail_subject;
	}
	public void setMail_subject(String mailSubject) {
		mail_subject = mailSubject;
	}
	public Date getInsert_date() {
		return insert_date;
	}
	public void setInsert_date(Date insertDate) {
		insert_date = insertDate;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date startTime) {
		start_time = startTime;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date endTime) {
		end_time = endTime;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date sendDate) {
		send_date = sendDate;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public Integer getMass_id() {
		return mass_id;
	}
	public void setMass_id(Integer massId) {
		mass_id = massId;
	}
	public Integer getOpen_count() {
		return open_count;
	}
	public void setOpen_count(Integer openCount) {
		open_count = openCount;
	}
	public String getAttachment_name() {
		return attachment_name;
	}
	public void setAttachment_name(String attachmentName) {
		attachment_name = attachmentName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getAttachment_file() {
		return attachment_file;
	}
	public void setAttachment_file(String attachmentFile) {
		attachment_file = attachmentFile;
	}
	public String getLogo1() {
		return logo1;
	}
	public void setLogo1(String logo1) {
		this.logo1 = logo1;
	}
	public String getLogo2() {
		return logo2;
	}
	public void setLogo2(String logo2) {
		this.logo2 = logo2;
	}
	public String getReturn_time() {
		return return_time;
	}
	public void setReturn_time(String returnTime) {
		return_time = returnTime;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String errorCode) {
		error_code = errorCode;
	}
	public String getReply_email() {
		return reply_email;
	}
	public void setReply_email(String replyEmail) {
		reply_email = replyEmail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getMailContent() {
		return mailContent;
	}
	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	public boolean isReadFlag() {
		return readFlag;
	}
	public void setReadFlag(boolean readFlag) {
		this.readFlag = readFlag;
	}
	public boolean isHasAdjunct() {
		return hasAdjunct;
	}
	public void setHasAdjunct(boolean hasAdjunct) {
		this.hasAdjunct = hasAdjunct;
	}
	public String getAdjunct() {
		return adjunct;
	}
	public void setAdjunct(String adjunct) {
		this.adjunct = adjunct;
	}
	public Integer getMail_id() {
		return mail_id;
	}
	public void setMail_id(Integer mailId) {
		mail_id = mailId;
	}
	public Integer getCase_id() {
		return case_id;
	}
	public void setCase_id(Integer caseId) {
		case_id = caseId;
	}
	public String getSystem_remark() {
		return system_remark;
	}
	public void setSystem_remark(String systemRemark) {
		system_remark = systemRemark;
	}
	public Integer getSession_id() {
		return session_id;
	}
	public void setSession_id(Integer sessionId) {
		session_id = sessionId;
	}
	public String getMail_code() {
		return mail_code;
	}
	public void setMail_code(String mailCode) {
		mail_code = mailCode;
	}
    
	
   

}
