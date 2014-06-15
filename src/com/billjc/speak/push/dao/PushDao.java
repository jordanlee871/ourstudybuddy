package com.billjc.speak.push.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.push.entity.mapper.PushRowMapper;

@Repository
public class PushDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(PushDao.class);
	
	/**
	 * 获取发送消息的设置
	 * @return
	 */
	public Push queryParams() {

		String sql = "SELECT * FROM `sk_push` WHERE `push_id`=1";
	
		logger.info(sql);
		return this.getJdbcTemplate().query(sql, new PushRowMapper()).get(0);
	}
	
	/**
	 * 获取发送消息
	 * @param column
	 * @return
	 */
	public String getContent(String column){
		String sql = "SELECT `" + column + "` FROM `sk_push` WHERE `push_id`=1";
		
		logger.info(sql);
		return this.getJdbcTemplate().queryForList(sql, String.class).get(0);
	}

	/**
	 * 修改发送消息的设置
	 * @return
	 */
	public int updatePush(Push push) {

		String sql = "UPDATE `sk_push` SET `email_name`=?, `email_pwd`=?, `ass_email`=?, `ass_tel`=?, `ass_msg_key`=?, `ass_msg_name`=?, " +
				"`no_trail_notice_msg`=?, `no_trail_notice_email`=?, `no_trail_notice_title`=?, " +
				"`trail_notice_msg`=?, `trail_notice_email`=?, `trail_notice_title`=?, " + 
				"`book_msg`=?, `book_email`=?, `book_title`=?, " + 
				"`tea_cxl_msg`=?, `tea_cxl_email`=?, `tea_cxl_title`=?, " +
				"`stu_cxl_msg`=?, `stu_cxl_email`=?, `stu_cxl_title`=?, " + 
				"`tea_emcxl_msg`=?, `tea_emcxl_email`=?, `tea_emcxl_title`=?, " + 
				"`stu_emcxl_msg`=?, `stu_emcxl_email`=?, `stu_emcxl_title`=?, " + 
				"`stu_ab_msg`=?, `stu_ab_email`=?, `stu_ab_title`=?, " +
				"`tea_ab_msg`=?, `tea_ab_email`=?, `tea_ab_title`=?, " +
				"`comp_msg`=?, `comp_email`=?, `comp_title`=?, " +
				"`no_trail`=?, `trail`=?  WHERE `push_id`=? ";
		logger.info(sql);
		Object[] param = new Object[] {
				push.getEmailName(), push.getEmailPwd(), push.getAssEmail(), push.getAssTel(), push.getAssMsgKey(), push.getAssMsgName(),  
				push.getNoTrailNoticeMsg(), push.getNoTrailNoticeEmail(), push.getNoTrailNoticeTitle(),
				push.getTrailNoticeMsg(), push.getTrailNoticeEmail(), push.getTrailNoticeTitle(),
				push.getBookMsg(), push.getBookEmail(), push.getBookTitle(),
				push.getTeaCxlMsg(), push.getTeaCxlEmail(), push.getTeaCxlTitle(), 
				push.getStuCxlMsg(), push.getStuCxlEmail(), push.getStuCxlTitle(), 
				push.getTeaEmcxlMsg(), push.getTeaEmcxlEmail(), push.getTeaEmcxlTitle(), 
				push.getStuEmcxlMsg(), push.getStuEmcxlEmail(), push.getStuEmcxlTitle(), 
				push.getStuAbMsg(), push.getStuAbEmail(), push.getStuAbTitle(), 
				push.getTeaAbMsg(), push.getTeaAbEmail(), push.getTeaAbTitle(), 
				push.getCompMsg(), push.getCompEmail(), push.getCompTitle(), 
				push.getNoTrail(), push.getTrail(), push.getPushId() };
		return this.getJdbcTemplate().update(sql, param);
	}
}
