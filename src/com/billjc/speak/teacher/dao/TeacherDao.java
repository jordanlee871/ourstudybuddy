package com.billjc.speak.teacher.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.Constant;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.entity.mapper.StudentRowMapper;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.entity.mapper.TeacherRowMapper;


@Repository
public class TeacherDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(TeacherDao.class);
	
	/**
	 * 插入老师
	 * @param Teacher
	 * 
	 **/
	public int innerTeacher(Teacher teacher){
		String sql = " INSERT INTO sk_teacher(QQ,USER_ID,NAME,SKYPE_NUM,EMAIL,PHONE,GROUPID,COMMENT,REGISTER_TIME) VALUES(?,?,?,?,?,?,?,?,CURDATE())";
		Object[] param = new Object[] {
				teacher.getQQ(),
				teacher.getUser_id(),
				teacher.getName(),
				teacher.getSkype_num(),
				teacher.getEmail(),
				teacher.getPhone(),
				teacher.getGroupid(),
				teacher.getComment(),
		};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	public List selectByUid(Long uid){
		String sql = "SELECT * FROM sk_teacher WHERE USER_ID="+uid+"";
		return this.getJdbcTemplate().query(sql,  new TeacherRowMapper());	
	}

	/**
	 * 获取老师列表数量
	 * @param concition
	 * 
	 **/
	public int queryListCount(String condition) {
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(1) FROM sk_teacher T,sk_user SU  WHERE SU.USER_ID=T.USER_ID AND SU.STATUS=1 " +condition);
	}
	
	/**
	 * 获取老师列表
	 * @param condition
	 * 
	 **/
	public List<Teacher> queryList(String condition, String orderBy,int start, int end) {
		String sql = "SELECT T.*,SU.USER_NAME AS USER_NAME FROM sk_teacher T,sk_user SU  WHERE SU.USER_ID=T.USER_ID AND SU.STATUS=1  "+ condition + orderBy +" LIMIT ?,?";
		Object[] param = new Object[] { start,end  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new TeacherRowMapper(true));
	}
	
	/**
	 * 根据老师名字查询
	 * @param nam
	 * @return teacher
	 **/
	public Teacher queryByName(String name) {		
		String sql = "SELECT * FROM sk_teacher  WHERE NAME=?";
		System.out.println("--"+name);
		Object[] param = new Object[]{ name };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		List<Teacher> result = this.getJdbcTemplate().query(sql, param, new TeacherRowMapper());
		if (result.size()>0){
			return result.get(0);
		} else{
			return null;
		}
	}
	/**
	 * 查询老师列表
	 * 
	 * @return　
	 */
	public List<Teacher> queryAllTeachers(){
		
		
		String sql = " SELECT ST.*,SU.USER_NAME FROM sk_teacher ST,sk_user SU WHERE SU.USER_ID=ST.USER_ID AND SU.STATUS=1 ORDER BY NAME ";
		
		logger.info(sql.replaceAll("\\?", "{}"));
		
		return this.getJdbcTemplate().query(sql,new TeacherRowMapper(true));
		
	}
	
	/**
	 * 分页查询老师
	 * 
	 * @return　
	 */
	public List<Teacher> queryTeachersByPage(int pageNo){
		
		int	start=(pageNo-1)*Constant.FREETIME_PAGESIZE;
//		int end=(pageNo)*Constant.FREETIME_PAGESIZE;
		int end=Constant.FREETIME_PAGESIZE;
		String sql = " SELECT ST.*,SU.USER_NAME FROM sk_teacher ST,sk_user SU WHERE SU.USER_ID=ST.USER_ID AND SU.STATUS=1 ORDER BY GROUPID LIMIT ?,?";
		Object[] param = new Object[]{ start,end };
		logger.info(sql.replaceAll("\\?", "{}"));
		return this.getJdbcTemplate().query(sql,param,new TeacherRowMapper(true));
		
	}
	
	
	
	/**
	 * 根据条件查询老师
	 * 
	 * @return　
	 */
	public List<Teacher> queryTeachers(String conditon ,int pageNo){
		int	start=(pageNo-1)*Constant.FREETIME_PAGESIZE;
//		int end=(pageNo)*Constant.FREETIME_PAGESIZE-1;
		int end=Constant.FREETIME_PAGESIZE;
		String sql = " SELECT * FROM sk_teacher R,sk_user SU WHERE 1=1 AND SU.USER_ID=R.USER_ID AND SU.STATUS=1 "+conditon+" ORDER BY  R.GROUPID LIMIT ?,? ";
		Object[] param = new Object[]{ start,end };
		logger.info(sql.replaceAll("\\?", "{}"));
		return this.getJdbcTemplate().query(sql,param,new TeacherRowMapper());
	}
	
	/**
	 * 根据ID数组查询老师
	 * 
	 * @return　
	 */
	public List<Teacher> queryTeacherByIDs(String teaIds){
		if(teaIds.length()>0){
			String sql = " SELECT * FROM `sk_teacher` `r` LEFT JOIN `sk_user` `su` ON `r`.`user_id`=`su`.`user_id` "
				+ "WHERE 1=1 AND `su`.`status`=1 AND `r`.`tea_id` IN (" + teaIds + ")";
			Object[] param = new Object[]{ };
			logger.info(sql.replaceAll("\\?", "{}"));
			return this.getJdbcTemplate().query(sql,param,new TeacherRowMapper());
		}else{
			return new ArrayList<Teacher>();
		}
	}
	
	
	/**
	 * 更新老师信息
	 * @param teacher
	 * 
	 **/
	public int updateTeacher(Teacher t){
		long id = t.getUser_id();
		String sql = " UPDATE sk_teacher  SET COMMENT=?,NAME=?, SKYPE_NUM=?,EMAIL=?,PHONE=?,GROUPID=?,QQ=?,MODIFY_LOG=? WHERE USER_ID=" + id + " ";
		Object[] param = new Object[] { t.getComment(),t.getName(),t.getSkype_num(),t.getEmail(),t.getPhone(),t.getGroupid(),t.getQQ() ,t.getModify_log()};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}


	/**
	 * 查询老师数量
	 * 
	 * @return 
	 **/
	public int queryAllTeachersNum(String condition ){				
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM sk_teacher  R where 1=1  " +condition);				
	}
	/**
	 * 根据teaID查询老师
	 * 
	 * @return 
	 **/
	public Teacher queryById(long teaId) {
		// TODO Auto-generated method stub
		String sql = "SELECT ST.*,SU.USER_NAME FROM sk_teacher ST,sk_user SU  WHERE ST.TEA_ID=? AND SU.USER_ID=ST.USER_ID";
		Object[] param = new Object[]{ teaId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		List<Teacher> result = this.getJdbcTemplate().query(sql, param, new TeacherRowMapper(true));
		if (result.size()>0){
			return result.get(0);
		} else{
			return null;
		}

	}
	/**
	 * 根据userID 查询老师
	 * 
	 * @return 
	 **/
	public Teacher queryByUserId(long userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM sk_teacher  WHERE USER_ID=?";
		
		Object[] param = new Object[]{ userId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForObject(sql, param, new TeacherRowMapper());
	}

	/**
	 * 在老师端操作，根据学生英文名查询
	 * 
	 * 
	 * 
	 *
	 * 根据英文名查询学生
	 * @param ename
	 * @return
	 **/
	public  List<Student> queryStudentFromTeacherByEname(String ename) {
		
		 String sql="SELECT *  FROM sk_student ST ,sk_teacher SC ,sk_class SKL  " +
	 		"WHERE  ST.STU_ID=SKL.STU_ID AND SC.TEA_ID=SKL.TEA_ID "  +
	 		"AND   ST.ENAME LIKE ?";
		
		
		Object[] param = new Object[] { "%"+ename+"%" };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		System.out.println(sql+param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(2));
		
	}
	

	/**
	 * 根据英文名查询学生数量
	 * @param ename
	 * @return
	**/

	public  int queryStudentFromTeacherCount(String ename ,int start, int end) {
		/*String sql = "SELECT * FROM sk_student  WHERE ENAME like ?";*/
		
		 String sql="SELECT COUNT(*)  FROM sk_student ST ,sk_teacher SC ,sk_class SKL  " +
	 		"WHERE  ST.STU_ID=SKL.STU_ID AND SC.TEA_ID=SKL.TEA_ID "  +
	 		"AND   ST.ENAME LIKE ?  LIMIT ?,?";
		Object[] param = new Object[] {"%"+ename+"%", start,end };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this. getJdbcTemplate().queryForInt(sql, param);
		
	}



	
	
	/**
	 * 根据课程和时间查询学生
	 * @param cls_begin_time
	 * @param cls_type
	 * @return 
	 **/
	public List<Student>queryStudentByCLSType(Date cls_begin_time,int cls_type){
		
		String sql="SELECT *  FROM sk_student ST ,sk_teacher SC ,sk_class SKL  " +
 		"WHERE  ST.STU_ID=SKL.STU_ID AND SC.TEA_ID=SKL.TEA_ID "  +
 		"AND CLS_BEGIN_TIME=?&&CLS_TYPE=? ";
		Object[] param = new Object[] {cls_begin_time,cls_type};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(2));
	}
	
	
	
	/**
	 * @param 根据课程和时间查询学生的数量
	 * 
	 * 
	 * */
	
   public int  queryStudentCount(Date cls_begin_time,int cls_type,int start, int end){
		
		String sql="SELECT COUNT(*) FROM sk_student ST ,sk_teacher SC ,sk_class SKL  " +
 		"WHERE  ST.STU_ID=SKL.STU_ID AND SC.TEA_ID=SKL.TEA_ID "  +
 		"AND CLS_BEGIN_TIME=?&&CLS_TYPE=? LIMIT ?,?";
		Object[] param = new Object[] {cls_begin_time,cls_type,start,end};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this. getJdbcTemplate().queryForInt(sql, param);
	}
	public void deleteTeacherByUserId(Long id) {
		String sql = "DELETE FROM sk_teacher WHERE USER_ID=" + id;
		this.getJdbcTemplate().update(sql);

	}

	
	/**
	 * @param 根据相应的条件查找学生
	 * 
	 * 
	 * */
	//querystudent----sky
	public List<Student>queryStudentByTeacher(String condition, int start, int end,long teacherID) {		
		String sql ="SELECT ST.*,ST.register_time as CLS_BEGIN_TIME  FROM sk_student ST ,sk_teacher SC ,sk_private_set SP " +
		 		"WHERE   ST.STU_ID=SP.STU_ID  AND SC.TEA_ID=SP.TEA_ID    AND SP.STATUS=1 AND 1=1 AND SP.TEA_ID="+teacherID+" "+ condition +" GROUP BY ST.STU_ID   LIMIT ?,?";

		Object[] param = new Object[] { start,end  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new StudentRowMapper(2));
	}
	
	/**
	 * @param 根据统计由相应的条件查找学生的数量
	 * 
	 * 
	 * */
	public int queryStudentByTeacherCount(String condition ,long  teacherID) {
		
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(DISTINCT ST.WW_NUM)   FROM sk_student ST ,sk_teacher SC ,sk_private_set SP " +
		 		"WHERE   ST.STU_ID=SP.STU_ID  AND SC.TEA_ID=SP.TEA_ID    AND SP.STATUS=1 AND 1=1 AND SP.TEA_ID="+teacherID+" "+ condition);
//		return this.getJdbcTemplate().queryForInt("SELECT COUNT(DISTINCT ST.WW_NUM)  FROM sk_student ST ,sk_teacher SC ,sk_class SKL ,sk_private_set SP " +
// 		"WHERE  ST.STU_ID=SKL.STU_ID AND ST.STU_ID=SP.STU_ID AND SC.TEA_ID=SKL.TEA_ID   AND SC.TEA_ID=SP.TEA_ID   AND SP.STATUS=1 AND   1=1 "+condition);
	}


/**
 * 获取注销老师数量
 * @param condition
 **/
	public int querycancelTeacherListCount(String condition ) {
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(1) FROM sk_teacher T,sk_user SU  WHERE SU.USER_ID=T.USER_ID AND SU.STATUS=0 " +condition);
	}
	
	/**
	 * 获取注销老师列表
	 *  @param condition
	 **/
	public List<Teacher> querycancelTeacherList(String condition, String orderBy,int start, int end) {
		String sql = "SELECT T.*,SU.USER_NAME AS USER_NAME FROM sk_teacher T,sk_user SU  WHERE SU.USER_ID=T.USER_ID AND SU.STATUS=0  "+ condition + orderBy +" LIMIT ?,?";
		Object[] param = new Object[] { start,end  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new TeacherRowMapper(true));
	}

	/**
	 * 根据老师的名字获取老师的数量
	 * @param name
	 * @return
	 */
	public int queryTeacherCountByName(String name){
		String sql="SELECT COUNT(1) FROM sk_teacher WHERE NAME LIKE '%"+name+"%'";
		logger.info(sql.replaceAll("\\?", "{}"));
		int a= this.getJdbcTemplate().queryForInt(sql);
		return a;
	}
	

}
