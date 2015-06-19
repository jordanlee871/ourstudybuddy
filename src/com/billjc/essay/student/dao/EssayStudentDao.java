package com.billjc.essay.student.dao;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
	final Logger logger = LoggerFactory.getLogger(EssayStudentDao.class);
	
	/*
	 * 查找com_member 所有记录
	 * @parm 用户名，密码
	 * @return com_member 对象（既是学生对象）
	 * */
	
	public List<EssayStudent> queryComMemberObject(String Name, String Password){
		String sql = "SELECT * FROM COM_MEMBER WHERE  memberName=? and password=?";
		Object[] param = new Object[] { Name, Password };
		logger.info("queryComMemberObject");
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query( sql, param, new EssayStudentRowMapper() );
	}
	
	/**
	 * 查找学生
	 * @param 学生用户名，密码
	 * @return 记录数
	 * */
	public int queryStudent(String Name, String Password){
		String sql = "SELECT COUNT(*) FROM COM_MEMBER WHERE  memberName=? and password=? and role=student";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}
	
	/**
	 * 查找学生
	 * @param 学生用户名，密码
	 * @return 学生对象
	 * */
	public List<EssayStudent> queryStudentObject(String Name, String Password){
		String sql = "SELECT * FROM COM_MEMBER WHERE  memberName=? and password=? and role=student";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());
	}
	/*
	 * 查找管理员
	 * @return 记录数
	 * */
	public int queryAdmin(String Name, String Password){
		String sql = "SELECT COUNT(*) FROM COM_MEMBER WHERE  memberName=? and password=? and isAdmin=1";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/*
	 * 查找管理员
	 * @return 管理员对象
	 * */
	public List<EssayStudent> queryAdminObject(String Name, String Password){
		String sql = "SELECT * FROM COM_MEMBER WHERE  memberName=? and password=? and isAdmin=1";
		Object[] param = new Object[] { Name, Password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());
	}
	
	public List<EssayStudent> queryStudentNameObject(String Name){
		String sql = "SELECT * FROM COM_MEMBER WHERE  memberName=? ";
		Object[] param = new Object[] { Name };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());
	}
	
	public List<EssayStudent> queryStudentIdObject( String Id ){
		String sql = "SELECT * FROM COM_MEMBER WHERE  id=? ";
		Object[] param = new Object[] { Id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());		
	}
	
	/*
	 * 无条件查找学生,只返来十条记录。
	 * 
	 * */
	public List<EssayStudent> queryStudent( int startLine ){
		String sql = "SELECT * FROM COM_MEMBER limit ?,10";
		Object[] param = new Object[] { startLine };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());
	}
	
	public int queryMemberCount(){
		
		String sql = "SELECT COUNT(*) FROM COM_MEMBER";
		logger.info( sql );
		return this.getJdbcTemplate().queryForInt(sql);
	
	}
	
	public List<EssayStudent> queryStudentSearch( String wanwan, String qq, String phone, String email, String date, int startLine){
		String sql = "SELECT * FROM COM_MEMBER where wanwan like ? AND qq like ? AND phone like ? AND email like ? AND create_time like ? limit ?,10";
		Object[] param = new Object[] { wanwan, qq, phone, email, date, startLine};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new EssayStudentRowMapper());
	}
	
	public int queryStudentSearchCount( String wanwan, String qq, String phone, String email, String date){
		String sql = "SELECT COUNT(*) FROM COM_MEMBER where wanwan like ? AND qq like ? AND phone like ? AND email like ? AND create_time like ?";
		Object[] param = new Object[] { wanwan, qq, phone, email, date };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param );
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
	
	/*
	 * 添加学生。
	 * @parm 旺旺，qq，电话，邮箱，名字。
	 * */
	public int insertNewStudent(EssayStudent student){
		
		//Generate a uniq id for new student
		String id_sql = "SELECT MAX(id) FROM COM_MEMBER";
		int new_id = this.getJdbcTemplate().queryForInt(id_sql) + 1;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//Insert a new student
		Object[] param = new Object[] {
				new_id,
				student.getWw(),
				student.getQq(),
				student.getPhone(),
				student.getEmail(),
				student.getName(),
				student.getActive(),
				student.getRole(),
				student.getPassword(),
				student.getIsAdmin(),
				df.format( student.getCreateTime() )
		};
		String sql = "insert into COM_MEMBER (id, wanwan, qq, phone, email, memberName, active, role, password, isAdmin, create_time) values(?,?,?,?,?,?,?,?,?,?,?)" ;
		
		logger.info(sql.replaceAll("\\?", "{}"), param);
		int rtncode = this.getJdbcTemplate().update(sql, param);
				
		return rtncode ;
	}
	/*
	 *  更改用户密码
	 *  @parm 密码， id
	 * */
	
	public int updatePassword( String password, String id){
		String sql = "update COM_MEMBER set password = ? where id = ?";
		Object[] param = new Object[] { password, id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		int rtncode = this.getJdbcTemplate().update(sql, param);
		return rtncode ;
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
