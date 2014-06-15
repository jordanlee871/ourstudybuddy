package com.billjc.speak.system.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.system.entity.SystemSettingBean;

public class SystemSetingBeanRowMapper implements RowMapper<SystemSettingBean> {
	
	public SystemSettingBean mapRow(ResultSet rs, int row) throws SQLException {
		SystemSettingBean setting=new SystemSettingBean();
		setting.setSettingName(rs.getString("set_name"));
		setting.setSettingType(rs.getString("set_type"));
		setting.setSettingValue(rs.getString("set_value"));
		return setting;
	}

}
