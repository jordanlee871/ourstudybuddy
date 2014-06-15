package com.billjc.framework;

import java.util.Date;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import com.billjc.framework.util.Email;
import com.billjc.speak.push.entity.Push;

@Repository
public class SimpleHtmlMailSender {
	private String filePath;
	private String host;

	private String username;

	private String password;

	private String poolName;

	private String returnUrl;
	
	final Logger logger = LoggerFactory.getLogger(SimpleHtmlMailSender.class);

	@SuppressWarnings("finally")
	public String sendMessage(Push push,Email email) throws AuthenticationFailedException, MessagingException {
		String result = "";
		try {
			this.host = "smtp.163.com";
			this.username = push.getEmailName() + "@163.com";
			this.password = push.getEmailPwd();
			this.doSend(email);
			result = "发送成功";
		} 
		catch (MailAuthenticationException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			result = "邮箱密码错误";
		}catch (AuthenticationFailedException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			result = "登陆邮箱验证失败";
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			result = e.getMessage();
		}finally{
			return result;
		}
	}

	private void doSend(Email email) throws AuthenticationFailedException,
			MessagingException,MailAuthenticationException {
		JavaMailSenderImpl javaMail = new JavaMailSenderImpl();
		
		javaMail.setHost(this.host);
		javaMail.setPassword(this.password);
		javaMail.setUsername(this.username);
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		javaMail.setJavaMailProperties(prop);
		MimeMessage mailMessage = javaMail.createMimeMessage();

		MimeMessageHelper msg = new MimeMessageHelper(mailMessage, true,
				"utf-8");
		msg.setFrom(this.username);
		msg.setReplyTo(this.username);
		msg.setTo(email.getMail_to());
		msg.setSubject(email.getMail_subject());
		msg.setText(email.getMailContent(), true);
		msg.setSentDate(new Date());
		javaMail.send(mailMessage);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getPoolName() {
		return poolName;
	}

	/**
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return returnUrl;
	}

	/**
	 * @param returnUrl
	 *            the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}
