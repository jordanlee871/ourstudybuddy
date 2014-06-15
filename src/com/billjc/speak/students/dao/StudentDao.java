package com.billjc.speak.students.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.DateUtil;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.entity.mapper.StudentRowMapper;
import com.billjc.speak.teacher.entity.Teacher;

@Repository
public class StudentDao extends BaseJdbcDao {
	final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	/**
	 * 新增学生
	 * 
	 * @param student
	 * @return
	 */
	public int insertStudent(Student student) {
		String sql = " INSERT INTO sk_student(USER_ID,WW_NUM,ENAME,PHONE,QQ_NUM,SKYPE_NUM,EMAIL,REGISTER_TIME,"
				+ "STATUS,REMARK,TEACHER_REMARK,BALANCE,MODIFY_LOG) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[] { student.getUser_id(),
				student.getWw_num(), student.getEname(), student.getPhone(),
				student.getQq_num(), student.getSkype_num(),
				student.getEmail(), student.getRegister_time(),
				student.getStatus(), student.getRemark(),
				student.getTeacher_remark(), student.getBalance(),
				student.getModify_log() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 查询申请注册的学生数量
	 * 
	 * @param condition
	 * @return
	 */
	public int queryRegStuListCount(String condition) {
		return this.getJdbcTemplate().queryForInt(
				"SELECT COUNT(*) FROM sk_student  WHERE STATUS=0 " + condition);
	}

	/**
	 * 查询旺旺号
	 * 
	 * @param
	 * @return
	 */
	public int checkWw_num(String ww_num) {
		String sql = "SELECT COUNT(*) FROM sk_student WHERE  WW_NUM=?";
		Object[] param = new Object[] { ww_num };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 查询旺旺号
	 * 
	 * @param
	 * @return
	 */
	public int checkWw_num2(String ww_num) {
		String sql = "SELECT COUNT(*) FROM sk_student WHERE  WW_NUM=? AND STATUS=1";
		Object[] param = new Object[] { ww_num };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 通过QQ查询学生数量
	 * 
	 * @param qqNum
	 * @return 返回学生的数量，0表示没有学生
	 */
	public int countStudentByQq(String qqNum) {
		String sql = "SELECT COUNT(*) FROM sk_student WHERE QQ_NUM=?";
		Object[] param = new Object[] { qqNum };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 通过PHONE查询学生数量
	 * 
	 * @param qqNum
	 * @return 返回学生的数量，0表示没有学生
	 */
	public int countStudentByPhone(String phone) {
		String sql = "SELECT COUNT(*) FROM sk_student WHERE PHONE=?";
		Object[] param = new Object[] { phone };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 查询学生名
	 * 
	 * @param
	 * @return
	 */
	public int checkStuName(String ww_num) {
		String sql = "SELECT COUNT(*) FROM sk_student WHERE ENAME=?";
		Object[] param = new Object[] { ww_num };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 查询QQ号是否可用
	 * 
	 * @param
	 * @return
	 */
	/*
	 * public int checkQQ_NUM(String qq_num) { String sql =
	 * "SELECT COUNT(*) FROM sk_student WHERE  QQ_NUM=?"; Object[] param= new
	 * Object[]{qq_num}; logger.info(sql.replaceAll("\\?", "{}"), param); return
	 * this.getJdbcTemplate().queryForInt(sql, param); }
	 *//**
	 * 查询手机号是否可用
	 * 
	 * @param
	 * @return
	 */
	/*
	 * public int checkPhone(String phone) { String sql =
	 * "SELECT COUNT(*) FROM sk_student WHERE  PHONE=?"; Object[] param= new
	 * Object[]{phone}; logger.info(sql.replaceAll("\\?", "{}"), param); return
	 * this.getJdbcTemplate().queryForInt(sql, param); }
	 *//**
	 * 查询邮箱是否可用
	 * 
	 * @param
	 * @return
	 */
	/*
	 * public int checkMail(String email) { String sql =
	 * "SELECT COUNT(*) FROM sk_student WHERE  EMAIL=?"; Object[] param= new
	 * Object[]{email}; logger.info(sql.replaceAll("\\?", "{}"), param); return
	 * this.getJdbcTemplate().queryForInt(sql, param); }
	 *//**
	 * 查询Skype_num是否可用
	 * 
	 * @param
	 * @return
	 */
	/*
	 * public int checkSkype_num(String skype_num) { String sql =
	 * "SELECT COUNT(*) FROM sk_student WHERE  SKYPE_NUM=?"; Object[] param= new
	 * Object[]{skype_num}; logger.info(sql.replaceAll("\\?", "{}"), param);
	 * return this.getJdbcTemplate().queryForInt(sql, param); }
	 */

	public List<Student> queryRegisterStudents(String condition,
			String orderBy, int start, int end) {
		String sql = "SELECT * FROM sk_student  WHERE STATUS=0  " + condition
				+ orderBy + " LIMIT ?,?";
		Object[] param = new Object[] { start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		System.out.println("------------"
				+ this.getJdbcTemplate().query(sql, param,
						new StudentRowMapper(0)));
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public Student queryStudentById(Long id) {
		String sql = "SELECT * FROM sk_student  WHERE STU_ID=?";
		Object[] param = new Object[] { id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public Student queryStudentByUserId(Long id) {
		String sql = "SELECT * FROM sk_student  WHERE USER_ID=?";
		Object[] param = new Object[] { id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public Student queryStudentByStuId(Long id) {
		String sql = "SELECT * FROM sk_student  WHERE STU_ID=?";
		Object[] param = new Object[] { id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public List<Student> queryStudentByQq(String qq) {
		String sql = "SELECT * FROM sk_student  WHERE QQ_Num=?";
		Object[] param = new Object[] { qq };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 根据英文名查询学生
	 * 
	 * @param ename
	 * @return
	 */
	/*
	 * public List<Student> queryStudentByEname(String ename) { String sql =
	 * "SELECT * FROM sk_student  WHERE ENAME like ?";
	 * 
	 * String
	 * sql="select *  from sk_student st ,sk_teacher sc ,sk_schedule sh    " +
	 * "where  st.stu_id=sh.stu_id and sc.tea_id=sh.tea_id " +
	 * "and   st.ename like ?";
	 * 
	 * 
	 * Object[] param = new Object[] { "%"+ename+"%" };
	 * logger.info(sql.replaceAll("\\?", "{}"), param);
	 * System.out.println(sql+param); return this.getJdbcTemplate().query(sql,
	 * param, new StudentRowMapper(1));
	 * 
	 * }
	 */
	/**
	 * 根据英文名查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public List<Student> queryStudentByEname(String ename, int start, int end) {
		/* String sql = "SELECT * FROM sk_student  WHERE ENAME like ?"; */

		String sql = "SELECT *  FROM sk_student ST ,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE  ST.STU_ID=SH.STU_ID AND SC.TEA_ID=SH.TEA_ID "
				+ "AND   ST.ENAME LIKE ? LIMIT ?,?";

		Object[] param = new Object[] { "%" + ename + "%", start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		System.out.println(sql + param);
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(1));

	}

	/**
	 * 根据英文名查询学生数量（分页）
	 * 
	 * @param ename
	 * @return
	 */
	public int queryStudentByEnameCount(String ename) {
		String sql = "SELECT COUNT(*)  FROM sk_student ST ,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE  ST.STU_ID=SH.STU_ID AND SC.TEA_ID=SH.TEA_ID "
				+ "AND ST.ENAME LIKE ? ";
		Object[] param = new Object[] { "%" + ename + "%" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		System.out.println(sql + param);
		return this.getJdbcTemplate().queryForInt(sql, param);

	}

	/**
	 * 查找老师某天的学生，如果已经取消关联，则不显示
	 * 
	 * @param ename
	 * @return
	 */
	public List<Student> queryStudents(Teacher tea, Date date) {
		String sql = "select distinct stu.* from sk_student stu, sk_class cls, sk_private_set pri "
				+ " where cls.cls_begin_time>=?  and cls.cls_begin_time<? "
				+ " and cls.tea_id=? and cls.stu_id=stu.stu_id and stu.stu_id=pri.stu_id and cls.tea_id=pri.tea_id"
				+ " order by stu.ename";
		String dateString = DateUtil.formatYDM(date);
		Object[] param = new Object[] { dateString + " 00:00:00",
				dateString + " 23:59:59", tea.getTea_id() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(0));

	}

	/**
	 * 更新学生
	 * 
	 * @param student
	 * @return
	 */
	@Transactional
	public int updateRegStu(Student stu) {
		String sql = "UPDATE sk_student  SET WW_NUM=?,ENAME=?,SKYPE_NUM=?,PHONE=?,EMAIL=?,QQ_NUM=?,REMARK=?,TEACHER_REMARK=?, BALANCE=?, STATUS=1 WHERE STU_ID=?";
		Object[] param = new Object[] { stu.getWw_num(), stu.getEname(),
				stu.getSkype_num(), stu.getPhone(), stu.getEmail(),
				stu.getQq_num(), stu.getRemark(), stu.getTeacher_remark(),
				stu.getBalance(), stu.getStu_id() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}

	public Student queryStudentBywwNum(String searchName) {
		String sql = "SELECT * FROM sk_student  WHERE WW_NUM=?";
		Object[] param = new Object[] { searchName };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));
	}

	public Student queryStudentBywwNum2(String searchName) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM sk_student  WHERE WW_NUM=?";
		Object[] param = new Object[] { searchName };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));
	}
	
	/**
	 * 模糊查询，会查询旺旺以及以这个旺旺开始的帐号
	 * @param ww
	 * @return
	 */
	public List<Student> queryStudentBywwNum3(String ww) {
		String sql = "SELECT * FROM sk_student  WHERE WW_NUM LIKE ?";
		Object[] param = new Object[] { ww+"%" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}

	public Student queryStudentByename(String searchName) {
		String sql = "SELECT * FROM sk_student  WHERE ename=?";
		Object[] param = new Object[] { searchName };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new StudentRowMapper(0));
	}

	/**
	 * 根据tea_id查找出相关连的学生
	 * 
	 * @param stuId
	 * @return Student List
	 */
	public List<Student> DqueryStuByTeaID(long tea_id) {
		String sql = "SELECT *  FROM sk_student ST ,sk_private_set SE,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE ST.STU_ID = SE.STU_ID AND SC.TEA_ID =SE.TEA_ID  AND  ST.STU_ID=SH.STU_ID "
				+ "AND  SC.TEA_ID = SH.TEA_ID AND SC.TEA_ID=? AND SE.STATUS=0;";
		Object[] param = new Object[] { tea_id };
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(1));
	}

	/**
	 * 根据上课时间，老师姓名查找学生
	 * 
	 * @param stuId
	 * @return Student List
	 */
	public List<Student> queryStudentByTimeandTname(long tea_id,
			Date sch_datetime, int start, int end) {
		String sql = "SELECT *  FROM sk_student ST ,sk_private_set SE,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE ST.STU_ID = SE.STU_ID AND SC.TEA_ID =SE.TEA_ID  AND  ST.STU_ID=SH.STU_ID "
				+ "AND  SC.TEA_ID = SH.TEA_ID AND SC.TEA_ID=? AND SH.SCH_DATETIME=? AND SE.STATUS=0 LIMIT ?,?";
		Object[] param = new Object[] { tea_id, sch_datetime, start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(1));
	}

	/**
	 * 根据上课时间，老师姓名查找学生数量
	 * 
	 * @param stuId
	 * @return Student List
	 */
	public int queryStudentByTimeandTnameCount(long tea_id, Date sch_datetime) {
		String sql = "SELECT COUNT(*)  FROM sk_student ST ,sk_private_set SE,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE ST.STU_ID = SE.STU_ID AND SC.TEA_ID =SE.TEA_ID  AND  ST.STU_ID=SH.STU_ID "
				+ "AND  SC.TEA_ID = SH.TEA_ID AND SC.TEA_ID=? AND SH.SCH_DATETIME=? AND SE.STATUS=0;";
		Object[] param = new Object[] { tea_id, sch_datetime };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 根据上课时间查找学生
	 * 
	 * @param stuId
	 * @return Student List
	 */
	public List<Student> queryStudentByTime(Date sch_datetime, int start,
			int end) {

		java.sql.Date tb = new java.sql.Date(sch_datetime.getTime());
		tb.setDate(tb.getDate() - 1);
		java.sql.Date ta = new java.sql.Date(sch_datetime.getTime());
		ta.setDate(ta.getDate() + 1);

		String sql = "SELECT *  FROM sk_student ST ,sk_private_set SE,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE ST.STU_ID = SE.STU_ID AND SC.TEA_ID =SE.TEA_ID  AND  ST.STU_ID=SH.STU_ID "
				+ "AND  SC.TEA_ID = SH.TEA_ID AND SH.SCH_DATETIME>? AND SH.SCH_DATETIME<?  LIMIT ?,?";
		Object[] param = new Object[] { tb, ta, start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate()
				.query(sql, param, new StudentRowMapper(1));
	}

	/**
	 * 根据上课时间查找学生数量
	 * 
	 * @param stuId
	 * @return Student List
	 */
	public int queryStudentByTimeCount(Date sch_datetime) {

		Date tb = new Date(sch_datetime.getTime());
		tb.setDate(tb.getDate() - 1);
		Date ta = new Date(sch_datetime.getTime());
		ta.setDate(ta.getDate() + 1);

		String sql = "SELECT COUNT(*)   FROM sk_student ST ,sk_private_set SE,sk_teacher SC ,sk_schedule SH    "
				+ "WHERE ST.STU_ID = SE.STU_ID AND SC.TEA_ID =SE.TEA_ID  AND  ST.STU_ID=SH.STU_ID "
				+ "AND  SC.TEA_ID = SH.TEA_ID AND   SH.SCH_DATETIME>? AND SH.SCH_DATETIME<?  ";
		Object[] param = new Object[] { tb, ta };

		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	public List<Student> queryStudent(String condition, int start, int end) {

		String sql = "SELECT * FROM sk_student WHERE 1=1 AND STATUS=1 "
				+ condition + " LIMIT ?,?";
		Object[] param = new Object[] { start, end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper());
	}

	public int queryStudentCount(String condition) {
		return this.getJdbcTemplate().queryForInt(
				"SELECT COUNT(1) FROM sk_student WHERE 1=1 AND STATUS=1 "
						+ condition);
	}

	/*
	 * 更改学生信息
	 * 
	 * @param
	 */
	public int updateStudentInfo(Long stu_id, String ename, String ww_num,
			String qq_num, String skype_num, String phone, String email,
			String notifySMS, String notifyMail) {
		String sql = "UPDATE sk_student SET ENAME=?,WW_NUM=?,QQ_NUM=?,SKYPE_NUM=?,PHONE=?,EMAIL=?,NOTIFY_SMS=?,NOTIFY_MAIL=? WHERE STU_ID=?";
		Object[] param = new Object[] { ename, ww_num, qq_num, skype_num,
				phone, email, notifySMS, notifyMail, stu_id};
		this.getJdbcTemplate().update(sql, param);
		logger.info(sql.replaceAll("\\?", "{}"), param);

		String sql2 = "UPDATE sk_user SET user_name=? WHERE USER_ID IN (SELECT USER_ID FROM sk_student WHERE STU_ID=?)";
		Object[] param2 = new Object[] { ww_num, stu_id };
		this.getJdbcTemplate().update(sql2, param2);

		logger.info(sql2.replaceAll("\\?", "{}"), param2);
		return 1;
	}

	/*
	 * 更改学生备注信息
	 * 
	 * @param stu_id,remark
	 */
	public int updateRemark(Long stu_id, String remark) {
		String sql = "UPDATE sk_student SET REMARK=? WHERE STU_ID=?";
		Object[] param = new Object[] { remark, stu_id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/*
	 * 更改学生备注信息
	 * 
	 * @param stu_id,remark
	 */
	public int addRemark(String ww, String remark) {
		String sql = "UPDATE sk_student SET REMARK=? WHERE WW_NUM=?";
		Object[] param = new Object[] { remark, ww };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}

	/*
	 * 更改学生的老师备注信息
	 * 
	 * @param stu_id,remark
	 */
	public int updateTeacherRemark(Long stu_id, String remark) {
		String sql = "UPDATE sk_student SET TEACHER_REMARK=? WHERE STU_ID=?";
		Object[] param = new Object[] { remark, stu_id };
		return this.getJdbcTemplate().update(sql, param);
	}

	// 搜索和与某个老师有关联的学生
	public List<Student> queryStudentByTeaId(long teaId) {
		String sql = "SELECT SS.* FROM sk_student SS,sk_private_set SPS WHERE SPS.TEA_ID=? AND SS.STU_ID=SPS.STU_ID ORDER BY SS.LAST_CLASS DESC";
		Object[] param = new Object[] { teaId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper());
	}

	public int countStudentByTeaId(long teaId) {
		String sql = "SELECT COUNT(*) FROM sk_student SS,sk_private_set SPS WHERE SPS.TEA_ID=? AND SS.STU_ID=SPS.STU_ID ";
		Object[] param = new Object[] { teaId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}

	/**
	 * 根据skype查找学生
	 * 
	 * @param skype
	 * @return
	 */
	public List<Student> queryStudentBySkype(String skype) {
		String sql = "SELECT * FROM sk_student  WHERE SKYPE_NUM=?";
		Object[] param = new Object[] { skype };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}

	public int updateLastClass(Long stuId, Date beginTime) {
		String sql = "UPDATE sk_student SET LAST_CLASS=? WHERE STU_ID=?";
		Object[] param = new Object[] { beginTime, stuId };
		return this.getJdbcTemplate().update(sql, param);
		
	}

	public List<Student> queryStudentByPhone(String phone) {
		String sql = "SELECT * FROM sk_student  WHERE PHONE=?";
		Object[] param = new Object[] { phone };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}

	public Collection<? extends Student> batchQueryStuById(String[] idList) {
		String sql = "SELECT * FROM sk_student  WHERE STU_ID IN (?";
		for(int i=1;i<idList.length;i++){
			sql+=",?";
		}
		sql+=");";
		Object[] param = idList;
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}
	public Collection<? extends Student> batchQueryStuByWw(String[] wwList) {
		String sql = "SELECT * FROM sk_student  WHERE WW_NUM IN (?";
		for(int i=1;i<wwList.length;i++){
			sql+=",?";
		}
		sql+=");";
		Object[] param = wwList;
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}
	public Collection<? extends Student> batchQueryStuByQq(String[] qqList) {
		String sql = "SELECT * FROM sk_student  WHERE QQ_NUM IN (?";
		for(int i=1;i<qqList.length;i++){
			sql+=",?";
		}
		sql+=");";
		Object[] param = qqList;
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}
	public Collection<? extends Student> batchQueryStuByPhone(String[] phoneList) {
		String sql = "SELECT * FROM sk_student  WHERE PHONE IN (?";
		for(int i=1;i<phoneList.length;i++){
			sql+=",?";
		}
		sql+=");";
		Object[] param = phoneList;
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}
	public Collection<? extends Student> batchQueryStuBySkype(String[] skypeList) {
		String sql = "SELECT * FROM sk_student  WHERE SKYPE_NUM IN (?";
		for(int i=1;i<skypeList.length;i++){
			sql+=",?";
		}
		sql+=");";
		Object[] param = skypeList;
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(0));
	}
}
