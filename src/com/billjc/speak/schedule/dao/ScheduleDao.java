package com.billjc.speak.schedule.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.Constant;
import com.billjc.speak.schedule.entity.Schedule;
import com.billjc.speak.schedule.entity.mapper.ScheduleRowMapper;
import com.billjc.speak.teacher.entity.Teacher;

@Repository
public class ScheduleDao extends BaseJdbcDao  {
	final Logger logger = LoggerFactory.getLogger(ScheduleDao.class);
	
	
	/**
	 *根据传入参数获取今天，明天，后天，大后天，的时间表数据
	 * 
	 * @param tea_id dayFlag
	 * @return  List<Schedule> 
	 */
	public List<Schedule> getMySchedule(Long tea_id,int dayFlag){
	    
		
		String sql="";
		Object[] param =new Object[]{};
		if(0==dayFlag){
			
			sql="SELECT * FROM sk_schedule R WHERE R.TEA_ID =? AND  TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW())<=0 AND TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW()) >=0 ";
		
			 param = new Object[] { tea_id};
			
		}else{
			
			sql="SELECT * FROM sk_schedule R WHERE R.TEA_ID =? AND  TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW())<=? AND TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW()) >?";
			
			int dayFlag_temp=dayFlag-1;
			
			 param = new Object[] { tea_id,dayFlag,dayFlag_temp};
		}
		logger.info(sql.replaceAll("\\?", "{}"), param);	
		return this.getJdbcTemplate().query(sql, param, new ScheduleRowMapper());
	}
	
	/**
	 *根据传入参数获取今天，明天，后天，大后天，的时间表数据
	 * 
	 * @param tea_id 
	 * @param scheduleDate
	 * @return  List<Schedule> 
	 */
	public List<Schedule> getMySchedule(Long tea_id,Date scheduleDate){
	    
		
		String sql = "SELECT * FROM sk_schedule R LEFT JOIN sk_student S ON R.stu_id = S.stu_id left join sk_class C ON R.status = C.cls_id WHERE R.TEA_ID = ? AND R.SCH_DATETIME=?";
		Object[] param = new Object[] {};
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		param = new Object[] { tea_id, dateFormat.format(scheduleDate)};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new ScheduleRowMapper());
	}
	
	/**
	 *根据传入参数获取相应条件的的时间表数据
	 * 
	 * @param tea_id  condition
	 * @return  List<Schedule> 
	 */
	
	public List<Schedule> getScheduleByTime(Long tea_id,String condition ){
		String sql="SELECT * FROM sk_schedule R WHERE R.TEA_ID =?  "+condition;
		Object[] param = new Object[] { tea_id};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new ScheduleRowMapper());
	}
	
	
	
	/**
	 *根据传入参数获取今天，明天，后天，大后天，的时间表数据
	 * 
	 * @param stu_id dayFlag
	 * @return  List<Schedule> 
	 */
	public List<Schedule> getStuSchedule(Long stu_id,int dayFlag){
		String sql="";
		Object[] param =new Object[]{};
			sql="SELECT * FROM sk_schedule R WHERE R.STU_ID =? AND  TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW())=?  ";	
			 param = new Object[] { stu_id,dayFlag};
				logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new ScheduleRowMapper());
	}
	
	
	
	/**
	 *判断老师当天是否有空闲时间
	 * 
	 * @param tea_id 
	 * @return  int 
	 */
	public int  haveFreeTimeOrNot(Long tea_id,int num){
		String  sql="SELECT COUNT(*) FROM sk_schedule R WHERE R.TEA_ID =? AND R.STATUS=1 AND R.SCH_LENGTH>? AND   TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW())<=0 AND TO_DAYS(R.SCH_DATETIME)-TO_DAYS(NOW()) >=0 ";
		Object[] param =new Object[] { tea_id,num};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}
	/**
	 *根据时间判断老师是否有空闲时间
	 * 
	 * @param tea_id 
	 * @return  int 
	 */
	public int  haveFreeTimeOrNotByTime(Long tea_id,int num,java.sql.Date time){
		String  sql="SELECT COUNT(*) FROM sk_schedule R WHERE R.TEA_ID =? AND R.STATUS=1 AND R.SCH_LENGTH>?  AND   R.SCH_DATETIME=?  ";
		Object[] param =new Object[] { tea_id,num,time};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param);
	}
	
	/**
	 *新增一个时间表记录 
	 * 
	 * @param timeFlag teaId 
	 * @return  int 
	 */
	public int insertSchedule(int timeFlag,Long teaId,int status){
		String  sql="INSERT INTO sk_schedule(TEA_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,CURDATE())";
//		String  sql="INSERT INTO sk_schedule(TEA_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,?)";
		Object[] param=new Object[]{};
		if(status==1){
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_FREE,timeFlag};
//			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_FREE,timeFlag,DateUtil.getDateDayFormat(new Date())+getFreeTimeSplit(timeFlag)};
		}else {
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_CLOSE};
		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/**
	 *新增一个时间表记录 
	 * 
	 * @param timeFlag teaId time
	 * @return  int 
	 */
	public int insertScheduleWithTime(int timeFlag,Long teaId,int status,String  time){
		String  sql="INSERT INTO sk_schedule(TEA_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,?)";
		Object[] param=new Object[]{};
		if(status==1){
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_FREE,timeFlag,time};
		}else {
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_CLOSE,timeFlag,time};
		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/**
	 *新增一个时间表记录 
	 * 
	 * @param timeFlag
	 * @param teacher<Teacher>
	 * @param status
	 * @param date
	 * @return  int 
	 */
	public int insertSchedule(int timeFlag,Teacher teacher,long status,Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "INSERT INTO sk_schedule(TEA_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,?)";
		Object[] param=new Object[]{};
		
		if(status==Constant.SCHEDULE_STATUS_FREE){
			 param =new Object[] { teacher.getTea_id(),Constant.SCHEDULE_STATUS_FREE,timeFlag,dateformat.format(date)};
		}else {
			 param =new Object[] { teacher.getTea_id(),Constant.SCHEDULE_STATUS_CLOSE,timeFlag,dateformat.format(date)};
		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * */
	public int insertScheduleDays(int timeFlag,Long teaId,int status,int days){
		
		String sql = "INSERT INTO sk_schedule(TEA_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,DATE_ADD(CURDATE(),INTERVAL ? DAY))";
		
		Object[] param=new Object[]{};
		
		if(status==1){
			
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_FREE,timeFlag,days};
			 
		}else {
			
			 param =new Object[] { teaId,Constant.SCHEDULE_STATUS_CLOSE,timeFlag,days};

		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * */
	public int insertStuScheduleDays(int timeFlag,Long stuId,int status,int days){
		
		String sql = "INSERT INTO sk_schedule(STU_ID,STATUS,SCH_LENGTH,SCH_DATETIME ) VALUES(?,?,?,DATE_ADD(CURDATE(),INTERVAL ? DAY))";
		
		Object[] param=new Object[]{};
		
		if(status==1){
			
			 param =new Object[] { stuId,Constant.SCHEDULE_STATUS_FREE,timeFlag,days};
			 
		}else {
			
			 param =new Object[] { stuId,Constant.SCHEDULE_STATUS_CLOSE,timeFlag,days};

		}
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
	}
	
	
	/**
	 *更新一个时间表记录
	 * 
	 * @param  s
	 * 
	 * @return  int 
	 */
	public int updateSchedule(Schedule s){
		
		String  sql="UPDATE sk_schedule SET STATUS=?,TEA_ID=?,SCH_LENGTH=?,STU_ID=?  WHERE SCH_ID=? ";
		
		Object[] param =new Object[] {s.getStatus(),s.getTea_id(),s.getSch_length(),s.getStu_id(),s.getSch_id() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
	}
public int updateStuSchedule(Schedule s){
		
		String  sql="UPDATE sk_schedule SET STATUS=?,STU_ID=?,SCH_LENGTH=?,STU_ID=?  WHERE SCH_ID=? ";
		
		Object[] param =new Object[] {s.getStatus(),s.getStu_id(),s.getSch_length(),s.getStu_id(),s.getSch_id() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
	}

public int updateScheduleDays(int timeFlag,Long teaId,int status,int days){
	
	String sql = "UPDATE sk_schedule SET STATUS =? WHERE TEA_ID = ? AND SCH_LENGTH = ? AND SCH_DATETIME =DATE_ADD(CURDATE(),INTERVAL ? DAY)";
	
	Object[] param=new Object[]{};
	
	if(status==1){
		
		 param =new Object[] { Constant.SCHEDULE_STATUS_FREE,teaId,timeFlag,days};
		 
	}else {
		
		 param =new Object[] { Constant.SCHEDULE_STATUS_CLOSE,teaId,timeFlag,days};

	}
	logger.info(sql.replaceAll("\\?", "{}"), param);
	return this.getJdbcTemplate().update(sql, param);
	
}
	
	
	/**
	 *根据老师id和时间段标记查询当天的时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querySchByLengthAndTeaId(long timeFlag,Long teaId ){
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND TO_DAYS(SCH_DATETIME)=TO_DAYS(NOW()) ";
		Object[] param =new Object[] { teaId,timeFlag};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
	}
	/**
	 *根据老师id和时间段标记和具体时间查询时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querSchByTimeAndLengthAndTeaid(long timeFlag,Long teaId,String time ){
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND SCH_DATETIME=? ";
		Object[] param =new Object[] { teaId,timeFlag,time};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
	}
	/**
	 *根据老师id和时间段标记查询当天的时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querySchByLengthAndTeaIdAndDays(long timeFlag,Long teaId,int days ){
		
		
		
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND TO_DAYS(SCH_DATETIME)-TO_DAYS(NOW())=? ";
		
		Object[] param =new Object[] { teaId,timeFlag,days};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}
	/**
	 *根据老师id和时间段标记查询当天的时间表记录2
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querySchByLengthAndTeaIdAndDays2(long timeFlag,Long teaId,String time ){
		
		
		if(time!=null&&time.length()>10)
			time =time.trim().substring(0, 11);
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND SCH_DATETIME=? ";
		
		Object[] param =new Object[] { teaId,timeFlag,time};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}
	/**
	 *根据老师id和时间段标记和具体时间查询时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querySchByLengthAndTeaIdAndTime(long timeFlag,Long teaId,String time ){
		
		
		
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND SCH_DATETIME=? ";
		
		Object[] param =new Object[] { teaId,timeFlag,time};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}
	/**
	 *根据学生id和时间段标记查询当天的时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	
	public Schedule  querySchByLengthAndStuId(long timeFlag,Long stuId ){
		
		String  sql="SELECT * FROM sk_schedule WHERE STU_ID=? AND SCH_LENGTH=? AND TO_DAYS(SCH_DATETIME)=TO_DAYS(NOW()) ";
		
		Object[] param =new Object[] { stuId,timeFlag};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}
	
	
	
public Schedule  querySchByLengthAndTeaIdAndDays(int days,long timeFlag,Long teaId ){
		
		String  sql="SELECT * FROM sk_schedule WHERE TO_DAYS(SCH_DATETIME)-TO_DAYS(NOW())= ? AND TEA_ID=? AND SCH_LENGTH=?  ";
		
		Object[] param =new Object[] {days,teaId,timeFlag};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}
	
	/**
	 *根据老师id和时间段标记查询当天的时间表记录
	 * 
	 * @param @param timeFlag teaId 
	 * @return  int 
	 */
	

	public Schedule  querySchByLengthAndTeaIdAndDate(long timeFlag,Long teaId ,Long stuId,Date date){
		
		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND STU_ID=? AND TO_DAYS(SCH_DATETIME)=TO_DAYS(?) ";
		
		Object[] param =new Object[] { teaId,timeFlag,stuId,date};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}

	/**
	 *根据老师id和时间段标记查询第几天天的时间表记录
	 * 
	 * @param @param @param timeFlag teaId numDay 
	 * @return  int 
	 */



	public Schedule  querySchByLengthAndTeaIdAndDays(int timeFlag,Long teaId,int numDay ){

		

		String  sql="SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND TO_DAYS(SCH_DATETIME)-TO_DAYS(NOW())= ?";

		Object[] param =new Object[] { teaId,timeFlag,numDay};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
		
	}

public Schedule  querySchByLengthAndStuIdAndDays(int timeFlag,Long stuId,int numDay ){
		
		String  sql="SELECT * FROM sk_schedule WHERE SCH_LENGTH=? AND STU_ID=? AND TO_DAYS(SCH_DATETIME)-TO_DAYS(NOW())= ?";
		

		Object[] param =new Object[] { timeFlag,stuId,numDay};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param,new ScheduleRowMapper());
		
	}

	/**
	 * 查询老师的时间记录
	 * 
	 * @param timeFlag
	 * @param Teacher
	 * @param date
	 * @return Schedule, 如果不存在则返回null
	 */
	public Schedule querySchedule(int timeFlag, Teacher teacher, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "SELECT * FROM sk_schedule WHERE TEA_ID=? AND SCH_LENGTH=? AND SCH_DATETIME=?";

		Object[] param = new Object[] { teacher.getTea_id(), timeFlag,
				dateFormat.format(date) };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new ScheduleRowMapper());
	}

public static String getFreeTimeSplit(int days) {
	if(0==days)
		return " 00:00";
	else if (1==days)
		return " 00:30";
	else if (2==days)
		return " 01:00";
	else if (3==days)
		return " 01:30";
	else if (4==days)
		return " 02:00";
	else if (5==days)
		return " 02:30";
	else if (6==days)
		return " 03:00";
	else if (7==days)
		return " 03:30";
	else if (8==days)
		return " 04:00";
	else if (9==days)
		return " 04:30";
	else if (10==days)
		return " 05:00";
	else if (11==days)
		return " 05:30";
	else if (12==days)
		return " 06:00";
	else if (13==days)
		return " 06:30";
	else if (14==days)
		return " 07:00";
	else if (15==days)
		return " 07:30";
	
	else if (16==days)
		return " 08:00";
	else if (17==days)
		return " 08:30";
	else if (18==days)
		return " 09:00";
	else if (19==days)
		return " 09:30";
	else if (20==days)
		return " 10:00";
	else if (21==days)
		return " 10:30";
	else if (22==days)
		return " 11:00";
	else if (23==days)
		return " 11:30";
	else if (24==days)
		return " 12:00";
	else if (25==days)
		return " 12:30";
	else if (26==days)
		return " 13:00";
	else if (27==days)
		return " 13:30";
	else if (28==days)
		return " 14:00";
	else if (29==days)
		return " 14:30";
	else if (30==days)
		return " 15:00";
	else if (31==days)
		return " 15:30";
	
	else if (32==days)
		return " 16:00";
	else if (33==days)
		return " 16:30";
	else if (34==days)
		return " 17:00";
	else if (35==days)
		return " 17:30";
	else if (36==days)
		return " 18:00";
	else if (37==days)
		return " 18:30";
	else if (38==days)
		return " 19:00";
	else if (39==days)
		return " 19:30";	
	else if (40==days)
		return " 20:00";
	else if (41==days)
		return " 20:30";
	else if (42==days)
		return " 21:00";
	else if (43==days)
		return " 21:30";
	else if (44==days)
		return " 22:00";
	else if (45==days)
		return " 22:30";
	else if (46==days)
		return " 23:00";
	else
		return " 23:30";
}
	
		

}
