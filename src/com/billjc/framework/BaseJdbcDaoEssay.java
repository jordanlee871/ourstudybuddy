package com.billjc.framework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BaseJdbcDaoEssay {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplateEssay) {
		this.jdbcTemplate = jdbcTemplateEssay;
	}
	
	/**
	 * 查询单个对象,如果结果为0时返回NULL,解决JdbcTemplate此方法不足的问题(结果为0时产生异常)
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = this.jdbcTemplate.query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));
		if(results.size() > 1){
			List<T> newResults = new ArrayList<T>();
			newResults.add(results.get(0));
			results = newResults;
		}
		return DataAccessUtils.singleResult(results);
	}
	
	/**
	 * 查询单个对象,如果结果为0时返回NULL,解决JdbcTemplate此方法不足的问题(结果为0时产生异常)
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = this.jdbcTemplate.query(sql, rowMapper);
	
		return DataAccessUtils.singleResult(results);
	}
}
