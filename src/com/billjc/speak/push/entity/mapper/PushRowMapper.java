package com.billjc.speak.push.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.push.entity.Push;

public class PushRowMapper implements RowMapper<Push> {
	
	public Push mapRow(ResultSet rs, int row) throws SQLException {
		Push push=new Push();
		push.setPushId(rs.getInt("push_id"));
		push.setEmailName(rs.getString("email_name"));
		push.setEmailPwd(rs.getString("email_pwd"));
		push.setAssEmail(rs.getString("ass_email"));
		push.setAssTel(rs.getString("ass_tel"));
		push.setAssMsgKey(rs.getString("ass_msg_key"));
		push.setAssMsgName(rs.getString("ass_msg_name"));
		
		push.setNoTrailNoticeMsg(rs.getString("no_trail_notice_msg"));
		push.setNoTrailNoticeEmail(rs.getString("no_trail_notice_email"));
		push.setNoTrailNoticeTitle(rs.getString("no_trail_notice_title"));
		
		push.setTrailNoticeMsg(rs.getString("trail_notice_msg"));
		push.setTrailNoticeEmail(rs.getString("trail_notice_email"));
		push.setTrailNoticeTitle(rs.getString("trail_notice_title"));
		
		push.setBookMsg(rs.getString("book_msg"));
		push.setBookEmail(rs.getString("book_email"));
		push.setBookTitle(rs.getString("book_title"));
		
		push.setTeaCxlMsg(rs.getString("tea_cxl_msg"));
		push.setTeaCxlEmail(rs.getString("tea_cxl_email"));
		push.setTeaCxlTitle(rs.getString("tea_cxl_title"));
		
		push.setStuCxlMsg(rs.getString("stu_cxl_msg"));
		push.setStuCxlEmail(rs.getString("stu_cxl_email"));
		push.setStuCxlTitle(rs.getString("stu_cxl_title"));
		
		push.setTeaEmcxlMsg(rs.getString("tea_emcxl_msg"));
		push.setTeaEmcxlEmail(rs.getString("tea_emcxl_email"));
		push.setTeaEmcxlTitle(rs.getString("tea_emcxl_title"));
		
		push.setStuEmcxlMsg(rs.getString("stu_emcxl_msg"));
		push.setStuEmcxlEmail(rs.getString("stu_emcxl_email"));
		push.setStuEmcxlTitle(rs.getString("stu_emcxl_title"));
		
		push.setStuAbMsg(rs.getString("stu_ab_msg"));
		push.setStuAbEmail(rs.getString("stu_ab_email"));
		push.setStuAbTitle(rs.getString("stu_ab_title"));

		push.setTeaAbMsg(rs.getString("tea_ab_msg"));
		push.setTeaAbEmail(rs.getString("tea_ab_email"));
		push.setTeaAbTitle(rs.getString("tea_ab_title"));
		
		push.setCompMsg(rs.getString("comp_msg"));
		push.setCompEmail(rs.getString("comp_email"));
		push.setCompTitle(rs.getString("comp_title"));
		
		push.setNoTrail(rs.getInt("no_trail"));
		push.setTrail(rs.getInt("trail"));
		return push;
	}

}
