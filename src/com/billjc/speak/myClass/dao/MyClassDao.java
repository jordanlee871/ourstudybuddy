package com.billjc.speak.myClass.dao;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.myClass.entity.MyClass;
import com.billjc.speak.myClass.entity.mapper.MyClassRowMapper;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.entity.mapper.UserRowMapper;


@Repository
public class MyClassDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(MyClass.class);
	
	
	public List teacherClsList(String condition,Long tea_id,int star,int end){
		String sql = " SELECT * FROM sk_class C WHERE  C.TEA_ID = "+tea_id+"  ";
		Object[] param = new Object[] { tea_id,star,end };
		return this.getJdbcTemplate().query(sql, param, new MyClassRowMapper());
		
	}

	public int teacherClsListCount(String condition) {
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(1) FROM sk_class C WHERE 1=1 " +condition);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<MyClass> teacherClsList(String condition, Long tea_id,String orderBy,int start, int end) {
		String sql = "SELECT * FROM sk_class C  WHERE TEA_ID = ? ";
		Object[] param = new Object[] { tea_id  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new MyClassRowMapper());
		
	}
}
