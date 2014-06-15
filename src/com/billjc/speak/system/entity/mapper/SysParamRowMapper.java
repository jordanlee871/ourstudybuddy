package com.billjc.speak.system.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.system.entity.SysParam;

public class SysParamRowMapper implements RowMapper<SysParam> {
	
	public SysParam mapRow(ResultSet rs, int row) throws SQLException {
		SysParam param = new SysParam();
		param.setParamId(rs.getLong("PARAM_ID"));
		param.setParamCode(rs.getString("PARAM_CODE"));
		param.setParamKey(rs.getString("PARAM_KEY"));
		param.setParamValue(rs.getString("PARAM_VALUE"));
		param.setParamState(rs.getInt("PARAM_STATE"));
		param.setParamOrder(rs.getInt("PARAM_ORDER"));
		param.setParamDesc(rs.getString("PARAM_DESC"));
		param.setParamRemark(rs.getString("PARAM_REMARK"));
		return param;
	}

}
