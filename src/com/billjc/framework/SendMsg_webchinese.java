package com.billjc.framework;
import java.io.IOException;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.util.Constant;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.sk_class.service.SkClassService;
import com.billjc.speak.smsLog.dao.SMSLogDao;
import com.billjc.speak.smsLog.entity.SMSLog;

@Repository
public class SendMsg_webchinese {
	
	final Logger logger = LoggerFactory.getLogger(SendMsg_webchinese.class);
	
	@Resource
	protected SMSLogDao smsLogDao;
	
	/**
	 * 发送短信的处理
	 * @param userName 发短信的用户名
	 * @param key	发短信的密钥
	 * @param phone	发送到的电话号码
	 * @param text	发送的内容
	 * @return 0：statusCode；1：发送结果；2：报错信息。
	 * @throws Exception 
	 * @throws HttpException
	 * @throws IOException
	 */
	public SMSLog doSend(Push push,SMSLog smsLog) throws Exception {
		String result = "-1";
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn");
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
			NameValuePair[] data = { new NameValuePair("Uid", push.getAssMsgName()),
					new NameValuePair("Key", push.getAssMsgKey()),
					new NameValuePair("smsMob", smsLog.getToPhone()),
					new NameValuePair("smsText", smsLog.getText()) };
			post.setRequestBody(data);
			smsLog.setCreateTime(new Date());	//设置发送时间
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			smsLog.setStatusCode(statusCode);
			result = new String(post.getResponseBodyAsString().getBytes(
					"utf8"));
			smsLog.setResult(Constant.getSMSCaption(result));
			post.releaseConnection();			
		} catch (HttpException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			smsLog.setExMsg(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			smsLog.setExMsg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			smsLog.setExMsg(e.getMessage());
		}
		smsLogDao.insertLog(smsLog);
		if (0 > Integer.parseInt(result)){
			throw new Exception("短信发送失败：phone:"+smsLog.getToPhone()+" |MSG:"+ smsLog.getText());
		}
		return smsLog;
	}
	
}
