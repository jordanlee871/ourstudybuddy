package com.billjc.speak.smsLog.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.notice.entity.Notice;
import com.billjc.speak.smsLog.entity.SMSLog;

public class SMSLogRowMapper implements RowMapper<SMSLog> {

	public SMSLog mapRow(ResultSet rs, int row) throws SQLException {
		SMSLog smsLog=new SMSLog();
		smsLog.setLogId(rs.getInt("log_id"));
		smsLog.setToPhone(rs.getString("to_phone"));
		smsLog.setText(rs.getString("text"));
		smsLog.setResult(rs.getString("result"));
		smsLog.setStatusCode(rs.getInt("status_code"));
		smsLog.setExMsg(rs.getString("ex_msg"));
		smsLog.setCreateTime(rs.getDate("create_time"));
		smsLog.setCreateUser(rs.getInt("create_user"));
		return smsLog;
	}

}
