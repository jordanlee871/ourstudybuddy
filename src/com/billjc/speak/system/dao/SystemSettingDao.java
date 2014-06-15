package com.billjc.speak.system.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.system.entity.SystemSettingBean;
import com.billjc.speak.system.entity.mapper.SystemSetingBeanRowMapper;

@Repository
public class SystemSettingDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(SystemSettingDao.class);

	/**
	 * 获取紧急取消时间
	 * @return
	 */
	public SystemSetting queryCxt() {
		String sql = "SELECT * FROM sk_sys where SET_TYPE='CXL'";
		logger.info(sql);
		return new SystemSetting(this.getJdbcTemplate().query(sql, new SystemSetingBeanRowMapper()));
	}

	/**
	 * 修改紧急取消时间
	 * @return
	 */
	public int  updateSeting(SystemSettingBean systemSeting) {
		String sql = "UPDATE  sk_sys SET SET_VALUE=? where SET_NAME=? and SET_TYPE=? ";
		logger.info(sql);
		Object[] param = new Object[] { systemSeting.getSettingValue(),systemSeting.getSettingName(),systemSeting.getSettingType()};
		 return this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 获取淘宝设定
	 * @return
	 */
	public SystemSetting queryTaobao() {
		String sql = "SELECT * FROM sk_sys where SET_TYPE='TAOBAO'";
		logger.info(sql);
		return new SystemSetting(this.getJdbcTemplate().query(sql, new SystemSetingBeanRowMapper()));
	}
}
