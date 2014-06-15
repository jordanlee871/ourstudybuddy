package com.billjc.speak.system.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.speak.system.dao.SystemSettingDao;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.system.entity.SystemSettingBean;

@Service
public class SystemSettingService {
	final Logger logger = LoggerFactory.getLogger(SystemSettingService.class);

	@Autowired
	private SystemSettingDao systemSettingDao;

	/**
	 * 获取紧急取消时间
	 * 
	 * @return
	 */
	public SystemSetting getCxtSetting() {
		return systemSettingDao.queryCxt();
	}

	/**
	 * 获取淘宝设定
	 * 
	 * @return
	 */
	public SystemSetting getTaobaoSetting() {
		return systemSettingDao.queryTaobao();
	}

	/**
	 * 修改緊急取消時間
	 * 
	 * @param systemSeting
	 */
	public int updateCxtSetting(int stuEmgCxlMin, int teaEmgCxlMin) {
		int result = 0;

		SystemSetting ss = getCxtSetting();
		ss.getTeaEmgCxl().setSettingValue(Integer.toString(teaEmgCxlMin));
		ss.getStuEmgCxl().setSettingValue(Integer.toString(stuEmgCxlMin));

		result = systemSettingDao.updateSeting(ss.getTeaEmgCxl());
		if (0 < result) {
			result = systemSettingDao.updateSeting(ss.getStuEmgCxl());
			if(0<result){
				return 1;
			}
		}
		return -1;
	}

	public boolean updateSessionKey(String sessionKey) {
		SystemSetting ss = getTaobaoSetting();
		ss.getSessionKeyTaobao().setSettingValue(sessionKey);
		return systemSettingDao.updateSeting(ss.getSessionKeyTaobao()) > 0;

	}

	/**
	 * 更新淘宝基本信息
	 * 
	 * @param appKey
	 * @param appSecret
	 * @return 1：成功； -1：失败
	 */
	public int updateTaobaoBasicInfo(String appKey, String appSecret) {
		int result = 0;
		SystemSetting ss = getTaobaoSetting();
		ss.getAppKeyTaobao().setSettingValue(appKey);
		ss.getAppSecretTaobao().setSettingValue(appSecret);
		result = systemSettingDao.updateSeting(ss.getAppKeyTaobao());
		if (0 < result) {
			result = systemSettingDao.updateSeting(ss.getAppSecretTaobao());
			if (0 < result) {
				return 1;
			}
		}
		return -1;
	}

	public int updateTaobaoProdInfo(String speakingIdTaobao,
			String essayIdTaobao, String essayPart1SkuTaobao,
			String essayPart2SkuTaobao,String essay7D5ESkuTaobao) {
		int result = 0;
		SystemSetting ss = getTaobaoSetting();
		ss.getSpeakingIdTaobao().setSettingValue(speakingIdTaobao);
		ss.getEssayIdTaobao().setSettingValue(essayIdTaobao);
		ss.getEssayPart1SkuTaobao().setSettingValue(essayPart1SkuTaobao);
		ss.getEssayPart2SkuTaobao().setSettingValue(essayPart2SkuTaobao);
		ss.getEssay7D5ESkuTaobao().setSettingValue(essay7D5ESkuTaobao);
		result = systemSettingDao.updateSeting(ss.getSpeakingIdTaobao());
		if (0 < result) {
			result = systemSettingDao.updateSeting(ss.getEssayIdTaobao());
			if (0 < result) {
				result = systemSettingDao.updateSeting(ss.getEssayPart1SkuTaobao());
				if(0<result){
					result = systemSettingDao.updateSeting(ss.getEssayPart2SkuTaobao());
					if(0<result){
						result = systemSettingDao.updateSeting(ss.getEssay7D5ESkuTaobao());
						if(0<result){
							return 1;
						}
					}
				}
			}
		}
		return -1;
	}
}
