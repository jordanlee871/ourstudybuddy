package com.billjc.essay.appointment.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Repository;

import com.billjc.essay.appointment.entity.EssayAppointment;
import com.billjc.essay.appointment.entity.mapper.EssayAppointmentRowMapper;
import com.billjc.framework.BaseJdbcDaoEssay;
import com.billjc.speak.students.dao.StudentDao;

@Repository
public class EssayAppointmentDao extends BaseJdbcDaoEssay {
	final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	
	public List<EssayAppointment> queryApptObject( int startLine ){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = new Date();
		String strdate = sdf.format(date); 
		strdate = strdate + "%" ;				
		String sql = "select * from t_appoint where date like ? limit ?, 20" ;
		Object[] param = new Object[] { strdate, startLine } ;
		logger.info(sql.replaceAll("\\?", "{}"), param) ;
		return this.getJdbcTemplate().query(sql, param, new EssayAppointmentRowMapper());
	}
	
	
	public String queryApptTeacher( long teacherid ){
		String sql = "select teacher_name_en from t_teacher where teacher_id = ?" ;
		Object[] param = new Object[] { teacherid } ;
		logger.info(sql.replaceAll("\\?", "{}"), param) ;
		return this.getJdbcTemplate().queryForObject(sql, param, String.class);
	}
	
	public String queryStudenteMail( long stuid ){
		String sql = "select email from com_member where id = ?" ;
		Object[] param = new Object[] { stuid } ;
		logger.info(sql.replaceAll("\\?", "{}"), param) ;
		return this.getJdbcTemplate().queryForObject(sql, param, String.class);
	}
	
	public String queryStudentName( long stuid ){
		String sql = "select memberName from com_member where id = ?" ;
		Object[] param = new Object[] { stuid } ;
		logger.info(sql.replaceAll("\\?", "{}"), param) ;
		return this.getJdbcTemplate().queryForObject(sql, param, String.class);
	}
	
	public List<String> queryTeacherName(){
		String sql = "select teacher_name_en from t_teacher" ;
		logger.info(sql.replaceAll("\\?", "{}")) ;
		return this.getJdbcTemplate().queryForList(sql, String.class);
	}
	
	public List<String> queryTeacherId(){
		String sql = "select teacher_id from t_teacher" ;
		logger.info(sql.replaceAll("\\?", "{}")) ;
		return this.getJdbcTemplate().queryForList(sql, String.class);
	}
	public List<String> queryTeacherActive(){
		String sql = "select active from t_teacher" ;
		logger.info(sql.replaceAll("\\?", "{}")) ;
		return this.getJdbcTemplate().queryForList(sql, String.class);
	}

	public List<Integer> queryTeacherAppt(){
		String sql_teacherid = "select teacher_id from t_teacher" ;
		String sql_teacherday1 = "select Day1num from t_teacher" ;
		String sql_teacherday2 = "select Day2num from t_teacher" ;
		String sql_teacherday3 = "select Day3num from t_teacher" ;
		logger.info(sql_teacherid.replaceAll("\\?", "{}")) ;
		List<String> List_teacherid = this.getJdbcTemplate().queryForList(sql_teacherid, String.class);
		
		logger.info(sql_teacherday1.replaceAll("\\?", "{}")) ;
		logger.info(sql_teacherday2.replaceAll("\\?", "{}")) ;
		logger.info(sql_teacherday3.replaceAll("\\?", "{}")) ;
		List<Integer> List_teacherday1 = this.getJdbcTemplate().queryForList(sql_teacherday1, Integer.class);
		List<Integer> List_teacherday2 = this.getJdbcTemplate().queryForList(sql_teacherday2, Integer.class);
		List<Integer> List_teacherday3 = this.getJdbcTemplate().queryForList(sql_teacherday3, Integer.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Calendar calendar=Calendar.getInstance();
		String strtoday = sdf.format( calendar.getTime() ); 
		strtoday = strtoday + "%" ;
		
		
		List<Integer> teacherapptcount = new ArrayList<Integer>(); 
		
		String sql_appt_coount = "select COUNT(*) from t_appoint where teacher_id=? and date like ?";
		
		for( int i =0 ; i < List_teacherid.size(); i ++){
			Object[] param = new Object[] { List_teacherid.get(i), strtoday } ;
			logger.info(sql_appt_coount.replaceAll("\\?", "{}"), param) ;
			int temp1 = this.getJdbcTemplate().queryForInt(sql_appt_coount, param);
			teacherapptcount.add( List_teacherday1.get(i) - temp1 );
//			System.out.println( List_teacherday1.get(i) - temp1 ) ;
		}
		
		calendar=Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR,1);
		String strtmr = sdf.format( calendar.getTime() ); 
		strtmr = strtmr + "%";
		for( int i =0 ; i < List_teacherid.size(); i ++){
			Object[] param = new Object[] { List_teacherid.get(i), strtmr } ;
			logger.info(sql_appt_coount.replaceAll("\\?", "{}"), param) ;
			int temp1 = this.getJdbcTemplate().queryForInt(sql_appt_coount, param);
			teacherapptcount.add( List_teacherday2.get(i) - temp1 );
//			System.out.println( List_teacherday2.get(i) - temp1 ) ;
		}

		calendar=Calendar.getInstance();
		calendar.roll(Calendar.DAY_OF_YEAR,2);
		String strdayafttmr = sdf.format( calendar.getTime() ); 
		strdayafttmr = strdayafttmr + "%";
		for( int i =0 ; i < List_teacherid.size(); i ++){
			Object[] param = new Object[] { List_teacherid.get(i), strdayafttmr } ;
			logger.info(sql_appt_coount.replaceAll("\\?", "{}"), param) ;
			int temp1 = this.getJdbcTemplate().queryForInt(sql_appt_coount, param);
			teacherapptcount.add( List_teacherday3.get(i) - temp1 );
//			System.out.println( List_teacherday3.get(i) - temp1 ) ;
		}
		teacherapptcount.add( List_teacherid.size() );
		
		return teacherapptcount;
	}
	
	public List<String> queryTeacherEdit(){
/*		
		String sql_teacherid = "select teacher_id from t_teacher" ;
		String sql_teachername = "select teacher_name_en from t_teacher" ;
		String sql_teacherday1 = "select Day1num from t_teacher" ;
		String sql_teacherday2 = "select Day2num from t_teacher" ;
		String sql_teacherday3 = "select Day3num from t_teacher" ;
		
		logger.info(sql_teacherid.replaceAll("\\?", "{}")) ;
		logger.info(sql_teachername.replaceAll("\\?", "{}")) ;
		logger.info(sql_teacherday1.replaceAll("\\?", "{}")) ;
		logger.info(sql_teacherday2.replaceAll("\\?", "{}")) ;
		logger.info(sql_teacherday3.replaceAll("\\?", "{}")) ;
		
		List<String> List_teacherid = this.getJdbcTemplate().queryForList(sql_teacherid, String.class);
		List<String> List_teachername = this.getJdbcTemplate().queryForList(sql_teachername, String.class);
		List<String> List_teacherday1 = this.getJdbcTemplate().queryForList(sql_teacherday1, String.class);
		List<String> List_teacherday2 = this.getJdbcTemplate().queryForList(sql_teacherday2, String.class);
		List<String> List_teacherday3 = this.getJdbcTemplate().queryForList(sql_teacherday3, String.class);
		
		List<String> List_teacher = new ArrayList<String> ();
		
		List_teacher.addAll(List_teacherid);
		List_teacher.addAll(List_teachername);
		List_teacher.addAll(List_teacherday1);
		List_teacher.addAll(List_teacherday2);
		List_teacher.addAll(List_teacherday3);
		List_teacher.add( String.valueOf( List_teacherid.size() ) );
*/		
		List List_teacher = this.getJdbcTemplate().query( "select * from t_teacher", new ColumnMapRowMapper() );
		return List_teacher;
	}
	
	/**
	 * 查找某学生最近预约的作文
	 * @param stuId 
	 * @param limit 返回结果数量
	 * @param essayType 作文类型
	 * @return
	 */
	public List<EssayAppointment> queryAppt(long stuId, long limit, String essayType) {
		String sql = "SELECT * FROM t_appoint where student_id=? AND type=? AND active=1 order by create_time desc limit ?";
		Object[] param = new Object[] { stuId, essayType, limit };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param,
				new EssayAppointmentRowMapper());
	}

	public int cancelAllAppt(List<Long> appointIdsForCancel) {
		String sql = "UPDATE t_appoint SET active = '0' WHERE appointment_id IN (?";
		for(int i=1;i<appointIdsForCancel.size();i++){
			sql+=",?";
		}
		sql+=");";
//		String ids = appointIdsForCancel.toString().replace("[", "(").replace("]", ")");
		Object[] param = appointIdsForCancel.toArray();
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("list", appointIdsForCancel);
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
}
