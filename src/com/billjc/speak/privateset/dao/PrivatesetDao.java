package com.billjc.speak.privateset.dao;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.privateset.entity.Privateset;
import com.billjc.speak.privateset.entity.mapper.PrivatesetRowMapper;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.entity.mapper.StudentRowMapper;

@Repository
public class PrivatesetDao extends  BaseJdbcDao{
	final Logger logger = LoggerFactory.getLogger(PrivatesetDao.class);

	
	/**
	 * 查询学生专属老师
	 * 
	 * @param stuId
	 * @return psList 
	 */
	public List<Privateset> queryPsList(Long stuId){
		
		String sql="SELECT * FROM sk_private_set WHERE STU_ID=?";
		
		Object[] param=new Object[]{ stuId};
		
		return this.getJdbcTemplate().query(sql, param, new PrivatesetRowMapper());
		
		
		
	}
	/**
	 * 查询学生是否有专属老师
	 * 
	 * @param stuId
	 * @return psList 
	 */
	public Privateset queryPsByWWAndteaId(Long stuId,Long teaId){
		
		String sql="SELECT * FROM sk_private_set WHERE STU_ID=? AND TEA_ID=?";
		
		Object[] param=new Object[]{ stuId,teaId};
		
		return this.queryForObject(sql, param, new PrivatesetRowMapper());
		
	}
	
	/**
	 * 新增学生专属老师
	 * 
	 * @param stuId teaId
	 * @return 
	 */
	
	public int insertPrivateset(Long stuId,Long teaId){
		
		String sql = " INSERT INTO sk_private_set(STU_ID,TEA_ID) VALUES(?,?)";
		Object[] param = new Object[] {stuId, teaId};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
		
	}
	
	/**
	 * 解除学生专属老师
	 * 
	 * @param stuId teaId
	 * @return 
	 */
	
	public int delPrivateset(Long stuId,Long teaId){
		
		String sql = "DELETE FROM sk_private_set WHERE STU_ID=?  AND TEA_ID=?";
		System.out.println(stuId+"---"+teaId);
		Object[] param = new Object[] {stuId, teaId};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
		
	}
	
	
	

	
}
