package com.billjc.speak.push.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.speak.notice.dao.NoticeDao;
import com.billjc.speak.notice.entity.Notice;
import com.billjc.speak.push.dao.PushDao;
import com.billjc.speak.push.entity.Push;

@Service
public class PushService {
	final Logger logger = LoggerFactory.getLogger(PushService.class);

	@Autowired
	private PushDao pushDao;
	
	@Autowired
	private NoticeDao noticeDao;

	/**
	 * 获取发送消息的设置
	 * 
	 * @return
	 */

	public Push getPush() {
		List<Notice> listNotice = noticeDao.queryAll();
		Push result = pushDao.queryParams();
		result.setListNotice(listNotice);
		return result;
	}

	/**
	 * 修改发送消息的设置
	 * 
	 * @param systemSeting
	 * @return
	 */
	public int updatePush(Push push ) {
		for(Notice notice : push.getListNotice()){
			noticeDao.updateNotice(notice);
		}
		return pushDao.updatePush(push);
	}
}
