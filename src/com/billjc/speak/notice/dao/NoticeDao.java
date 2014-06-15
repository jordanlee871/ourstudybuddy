package com.billjc.speak.notice.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.notice.entity.Notice;
import com.billjc.speak.notice.entity.mapper.NoticeRowMapper;
import com.billjc.speak.push.entity.Push;

@Repository
public class NoticeDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(NoticeDao.class);
	
	/**
	 * 获取发送消息的设置
	 * @return
	 */
	public List<Notice> queryAll() {

		String sql = "SELECT * FROM `sk_notice` WHERE 1=1 ORDER BY `notice_id`";
	
		logger.info(sql);
		
		List<Notice> result = this.getJdbcTemplate().query(sql, new NoticeRowMapper());
		return result;
	}

	/**
	 * 修改发送消息的设置
	 * @return
	 */
	public int updateNotice(Notice notice) {

		String sql = "UPDATE `sk_notice` SET `is_send`=?  WHERE `notice_id`=? ";
		logger.info(sql);
		Object[] param = new Object[] {notice.getIsSend(), notice.getNoticeId() };
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/**
	 * 根据条件查找是否需要发送
	 * @param notice
	 * @return
	 */
	public boolean findIsSend(Integer fromType, Integer noticeType, Integer toUserType, Integer typeId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM `sk_notice` WHERE `is_send`=1 ");
		if(fromType != null){
			sb.append("AND from_type="+ fromType+ " ");
		}
		if(noticeType != null){
			sb.append("AND notice_type="+ noticeType+ " ");
		}
		if(toUserType != null){
			sb.append("AND to_user_type="+ toUserType+ " ");
		}
		if(typeId != null){
			sb.append("AND type_id="+ typeId+ " ");
		}
		String sql = sb.toString();
		logger.info(sql);
		Object[] param = new Object[] {};
		return this.getJdbcTemplate().query(sql, new NoticeRowMapper()).size()>0;
	}
}
