package com.billjc.speak.smsLog.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.notice.dao.NoticeDao;
import com.billjc.speak.notice.entity.mapper.NoticeRowMapper;
import com.billjc.speak.smsLog.entity.SMSLog;

@Repository
public class SMSLogDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(SMSLogDao.class);
	
	/**
	 * 插入发送短信的记录
	 * @return
	 */
	public int insertLog(SMSLog smsLog) {

		String sql = "INSERT INTO `sk_sms_log`(`to_phone`, `text`, `result`, `status_code`, `ex_msg`, " 
			+ "`create_time`, `create_user`) VALUES(?, ?, ?, ?, ?, ?, ?)";
		logger.info(sql);
		Object[] param = new Object[] {smsLog.getToPhone(), smsLog.getText(), smsLog.getResult(), smsLog.getStatusCode(), 
				smsLog.getExMsg(), smsLog.getCreateTime(), smsLog.getCreateUser() };
		return this.getJdbcTemplate().update(sql, param);
	}
}
