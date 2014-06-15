package com.billjc.speak.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.billjc.speak.system.dao.SysParamDao;

@Service
public class SysParamService {
	final Logger logger = LoggerFactory.getLogger(SysParamService.class);
	
	@Autowired
	private SysParamDao sysParamDao;
	
}
