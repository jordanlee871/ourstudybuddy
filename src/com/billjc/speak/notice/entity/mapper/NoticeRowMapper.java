package com.billjc.speak.notice.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.notice.entity.Notice;

public class NoticeRowMapper implements RowMapper<Notice> {
	
	public Notice mapRow(ResultSet rs, int row) throws SQLException {
		Notice notice=new Notice();
		notice.setNoticeId(rs.getInt("notice_id"));
		notice.setNoticeType(rs.getInt("notice_type"));
		notice.setToUserType(rs.getInt("to_user_type"));
		notice.setIsSend(rs.getInt("is_send"));
		notice.setTypeId(rs.getInt("type_id"));
		notice.setFromType(rs.getInt("from_type"));
		notice.setUpdateUser(rs.getInt("update_user"));
		notice.setUpdateTime(rs.getDate("update_time"));
		return notice;
	}

}
