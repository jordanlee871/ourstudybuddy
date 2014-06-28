package com.billjc.essay.student.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.essay.student.entity.EssayStudent;
import com.billjc.essay.student.entity.mapper.EssayStudentRowMapper;
import com.billjc.framework.BaseJdbcDaoEssay;
import com.billjc.framework.util.Constant;
import com.billjc.speak.students.dao.StudentDao;

@Repository
public class EssayStudentDao extends BaseJdbcDaoEssay {
	final Logger logger = LoggerFactory.getLogger(StudentDao.class);
	
	/**
	 * 查找学生
	 * @param 学生用户名，密码
	 * @return 记录数
	 * */
	public int queryStudent(String Name, String Password){
		String sql = "SELECT COUNT(*) FROM COM_MEMBER WHERE  memberName=? and password=?";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}
	
	public int queryAdmin(String Name, String Password){
		String sql = "SELECT COUNT(*) FROM COM_MEMBER WHERE  memberName=? and password=? and isAdmin=1";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}
	
	/*
	 * 无条件查找学生
	 * 
	 * */
	public List<EssayStudent> queryStudent(){
		String sql = "SELECT * FROM COM_MEMBER ";
		return this.getJdbcTemplate().query(sql,new EssayStudentRowMapper());
	}
	/**
	 * 查找学生
	 * 
	 * @param 学生旺旺
	 * @return 学生列表
	 */
	public List<EssayStudent> queryStudent(String ww) {
		String sql = "SELECT * FROM COM_MEMBER WHERE WANWAN=?";
		Object[] param = new Object[] { ww };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param,
				new EssayStudentRowMapper());
	}
	/**
	 * 查找某时间段内预约作文并且余额低于0的学生
	 * 
	 * @param 学生旺旺
	 * @return 学生列表
	 */
	public List<EssayStudent> queryStudent(Date start, Date end) {
		String sql = "SELECT stu.* FROM t_appoint app left join com_member stu ON app.student_id = stu.id where app.create_time >= ? and app.create_time < ? and (stu.amount_task1 < 0  or stu.amount_task2 < 0) group by app.student_id";
		Object[] param = new Object[] { start, end};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param,
				new EssayStudentRowMapper());
	}

	/**
	 * 充值
	 * 
	 * @param 学生旺旺
	 * @return 学生列表
	 */
	public int upateBalance(EssayStudent stu, String type,
			long amount) {
		String sql;
		Object[] param;
		if (Constant.ESSAY_DB_PART1 == type) {
			sql = "update com_member set amount_task1=? where wanwan=?;";
			param = new Object[] { stu.getAmountTask1() + amount, stu.getWw() };
		} else if (Constant.ESSAY_DB_PART2 == type) {
			sql = "update com_member set amount_task2=? where wanwan=?;";
			param = new Object[] { stu.getAmountTask2() + amount, stu.getWw() };
		} else if (Constant.ESSAY_DB_OTHER == type) {
			sql = "update com_member set amount_other=? where wanwan=?;";
			param = new Object[] { stu.getAmountOther() + amount, stu.getWw() };
		} else {
			return -1;
		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	/**
	 * 将学生的余额归0
	 * @param stuId
	 * @param essayType
	 */
	public int fixBalance(long stuId, String essayType) {
		String sql;
		Object[] param;
		if (Constant.ESSAY_DB_PART1 == essayType) {
			sql = "update com_member set amount_task1=0 where id=?;";
		} else if (Constant.ESSAY_DB_PART2 == essayType) {
			sql = "update com_member set amount_task2=0 where id=?;";
		} else if (Constant.ESSAY_DB_OTHER == essayType) {
			sql = "update com_member set amount_other=0 where id=?;";
		} else {
			return -1;
		}
		param = new Object[] { stuId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
}
