package com.billjc.speak.system.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.system.entity.SysParam;
import com.billjc.speak.system.entity.mapper.SysParamRowMapper;

@Repository
public class SysParamDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(SysParamDao.class);

	/**
	 * 获取所有系统参数
	 * @return
	 */
	public List<SysParam> queryAllParams() {
		String sql = "SELECT T.* FROM WEBSITE_SYSPARAM@WEBSITEDB T WHERE T.PARAM_STATE=1 ORDER BY T.PARAM_ORDER ASC";
		logger.info(sql);
		return this.getJdbcTemplate().query(sql, new SysParamRowMapper());
	}

}
