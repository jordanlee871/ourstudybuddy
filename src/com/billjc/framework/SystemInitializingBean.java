package com.billjc.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.billjc.speak.system.dao.SystemSettingDao;

public class SystemInitializingBean implements InitializingBean {
	final Logger logger = LoggerFactory.getLogger(SystemInitializingBean.class);
	
	
	
	
	public void afterPropertiesSet() throws Exception {
		
		logger.info("系统启动完成,开始初始化系统参数......");
		
	}

}
