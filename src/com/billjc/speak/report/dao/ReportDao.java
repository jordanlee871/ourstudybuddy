package com.billjc.speak.report.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.Constant;
import com.billjc.speak.report.entity.FirstClassInfo;
import com.billjc.speak.report.entity.StuClsInfo;
import com.billjc.speak.report.entity.TeaDailyClsInfo;

@Repository
public class ReportDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(ReportDao.class);
	
	/**
	 * 查看老师当天上课情况（除了空余时间总数）
	 * @param date
	 * @return
	 */
	public List<TeaDailyClsInfo>getTeaDailyList(Date date){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT ");
		sql.append("class_table.`tea_id` AS `tea_id`, ");
		sql.append("class_table.`name` AS `name`, ");
		sql.append("SUM( CASE WHEN class_table.`cls_type` = " + Constant.CLASS_TYPE_TRIAL 
				+ " AND (class_table.`cls_status` = " + Constant.CLASS_STATUS_PENDING + " OR class_table.`cls_status` = " + Constant.CLASS_STATUS_COMPLETED + " )"
				+ " THEN 1 ELSE 0 END ) AS `trail_times`,");
		sql.append("SUM( CASE WHEN class_table.`cls_type` = " + Constant.CLASS_TYPE_TRIAL 
				+ " AND (class_table.`cls_status` = " + Constant.CLASS_STATUS_PENDING + " OR class_table.`cls_status` = " + Constant.CLASS_STATUS_COMPLETED + " )"
				+ " THEN `cls_length` ELSE 0 END ) AS `trail_class`, ");
		sql.append("SUM( CASE WHEN class_table.`cls_type` != " + Constant.CLASS_TYPE_TRIAL 
				+ " AND (class_table.`cls_status` = " + Constant.CLASS_STATUS_PENDING + " OR class_table.`cls_status` = " + Constant.CLASS_STATUS_COMPLETED + " )"
				+ " THEN 1 ELSE 0 END ) AS `no_trail_times`,");
		sql.append("SUM( CASE WHEN class_table.`cls_type` != " + Constant.CLASS_TYPE_TRIAL 
				+ " AND (class_table.`cls_status` = " + Constant.CLASS_STATUS_PENDING + " OR class_table.`cls_status` = " + Constant.CLASS_STATUS_COMPLETED + " )"
				+ " THEN `cls_length` ELSE 0 END ) AS `no_trail_class`,");
		sql.append("SUM( CASE WHEN class_table.`cls_status` = " + Constant.CLASS_STATUS_STU_EMG_CXL + " THEN 1 ELSE 0 END ) AS `stu_em_cxl_times`,");
		sql.append("SUM( CASE WHEN class_table.`cls_status` = " + Constant.CLASS_STATUS_TEA_EMG_CXL + " THEN 1 ELSE 0 END ) AS `tea_em_cxl_times` ");
		sql.append("FROM (SELECT ");
		sql.append("`sk_teacher`.`groupid` AS `groupid`,");
		sql.append("`sk_teacher`.`tea_id` AS `tea_id`,");
		sql.append("`sk_teacher`.`name` AS `name`,");
		sql.append("`sk_class`.`cls_type` AS `cls_type`,");
		sql.append("`sk_class`.`cls_length` AS `cls_length`,");
		sql.append("`sk_class`.`cls_status` AS `cls_status` ");
		sql.append("FROM ");
		sql.append("`sk_teacher` ");
		sql.append("LEFT JOIN `sk_user` ON `sk_teacher`.`user_id` = `sk_user`.`user_id` ");
		sql.append("LEFT JOIN `sk_class` ON `sk_teacher`.`tea_id` = `sk_class`.`tea_id` ");
		
		if(date != null){
			sql.append("AND DATE_FORMAT(`sk_class`.`cls_begin_time`,'%Y-%m-%d' ) = DATE_FORMAT( ?,'%Y-%m-%d' ) ");
		}
		
		sql.append("WHERE `sk_user`.`status` = 1 ");
		sql.append(") class_table ");
		sql.append("GROUP BY `tea_id` ORDER BY `groupid`,`tea_id`;");
		
		List<Object> param = new ArrayList<Object>();
		if(date != null){
			param.add(simple.format(date));
		}
		
		logger.info(sql.toString().replaceAll("\\?", "{}"), param.toArray());
		return this.getJdbcTemplate().query(sql.toString(), param.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(TeaDailyClsInfo.class));
	}
	
	/**
	 * 查看老师当天上课情况（只查空余时间总数）
	 * @param date
	 * @return
	 */
	public List<TeaDailyClsInfo>getTeaDailyListFree(Date date){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT ");
		sql.append("schedule_table.`tea_id` AS `tea_id`, ");
		sql.append("SUM( CASE WHEN schedule_table.`status` = 1 THEN 1 ELSE 0 END ) AS `free_class` ");
		sql.append("FROM (SELECT ");
		sql.append("`sk_teacher`.`groupid` AS `groupid`,");
		sql.append("`sk_teacher`.`tea_id` AS `tea_id`,");
		sql.append("`sk_schedule`.`status` AS `status` ");
		sql.append("FROM ");
		sql.append("`sk_teacher` ");
		sql.append("LEFT JOIN `sk_user` ON `sk_teacher`.`user_id` = `sk_user`.`user_id` ");
		sql.append("LEFT JOIN `sk_schedule` ON `sk_teacher`.`tea_id` = `sk_schedule`.`tea_id` ");
		
		if(date != null){
			sql.append("AND DATE_FORMAT(`sk_schedule`.`sch_datetime`,'%Y-%m-%d' ) = DATE_FORMAT( ?,'%Y-%m-%d' ) ");
		}
		
		sql.append("WHERE `sk_user`.`status` = 1 ");
		sql.append(") schedule_table ");
		sql.append("GROUP BY `tea_id` ORDER BY `groupid`,`tea_id`;");
		
		List<Object> param = new ArrayList<Object>();
		if(date != null){
			param.add(simple.format(date));
		}
		
		logger.info(sql.toString().replaceAll("\\?", "{}"), param.toArray());
		return this.getJdbcTemplate().query(sql.toString(), param.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(TeaDailyClsInfo.class));
	}
	
	/**
	 * 查询首节跟进课程（当天上课的学生中，余额为0的学生,显示该学生的所有课程，只显示Pending跟complete的课程）
	 * @param date
	 * @return
	 */
	public List<FirstClassInfo>getFirstClassList(Date date){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT ");
		sql.append("`sk_student`.`stu_id` AS `stu_id`,");
		sql.append("`sk_student`.`ename` AS `stu_name`,");
		sql.append("`sk_teacher`.`tea_id` AS `tea_id`,");
		sql.append("`sk_teacher`.`name` AS `tea_name`,");
		sql.append("`sk_class`.`cls_begin_time`  AS `cls_begin_time`,");
		sql.append("`sk_class`.`cls_type`  AS `cls_type`,");
		sql.append("`sk_class`.`cls_length`  AS `cls_length` ");
		sql.append("FROM `sk_class` ");
		sql.append("LEFT JOIN `sk_student` ON `sk_class`.`stu_id` = `sk_student`.`stu_id` ");
		sql.append("LEFT JOIN `sk_teacher` ON `sk_class`.`tea_id` = `sk_teacher`.`tea_id` ");
		sql.append("WHERE `sk_student`.`balance` = 0 ");
		sql.append("AND (`sk_class`.`cls_status` = " + Constant.CLASS_STATUS_PENDING 
				+ " OR `sk_class`.`cls_status` = " + Constant.CLASS_STATUS_COMPLETED + ") ");
		
		if(date != null){
			sql.append("AND DATE_FORMAT(`sk_class`.`cls_begin_time`,'%Y-%m-%d' ) = DATE_FORMAT( ?,'%Y-%m-%d' ) ");
		}
			
		sql.append("ORDER BY `sk_student`.`stu_id`,`sk_teacher`.`tea_id`,`sk_class`.`cls_id`;");
		
		List<Object> param = new ArrayList<Object>();
		if(date != null){
			param.add(simple.format(date));
		}
		
		logger.info(sql.toString().replaceAll("\\?", "{}"), param.toArray());
		return this.getJdbcTemplate().query(sql.toString(), param.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(FirstClassInfo.class));
	}
	
	/**
	 * 查询学生上课进度
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param minBalance 最少余额
	 * @return
	 */
	public List<StuClsInfo>getStuClsInfoList(Date startDate, Date endDate, Integer minBalance){
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT ");
		sql.append("`student_table`.`stu_id` AS `stu_id`,");
		sql.append("`student_table`.`stu_name` AS `stu_name`,");
		sql.append("`student_table`.`bl_datetime` AS `bl_datetime`,");
		sql.append("`student_table`.`bl_num` AS `last_buy`,");
		sql.append("`student_table`.`balance` AS `balance`,");
		sql.append("`sk_class`.`cls_begin_time` AS `cls_begin_time`,");
		sql.append("`sk_teacher`.`tea_id` AS `tea_id`,");
		sql.append("`sk_teacher`.`name` AS `tea_name` ");
		sql.append("FROM ");
		sql.append("( ");
		sql.append("SELECT ");
		sql.append("`sk_student`.`stu_id` AS `stu_id`,");
		sql.append("`sk_student`.`ename` AS `stu_name`,");
		sql.append("IFNULL( MAX( `sk_balance_log`.`bl_datetime` ), 'N/A') AS `bl_datetime`,");
		sql.append("CASE WHEN MAX( `sk_balance_log`.`bl_datetime` ) IS NULL THEN 'N/A' ELSE `sk_balance_log`.`bl_num` END  AS `bl_num`,");
		sql.append("`sk_student`.`balance` AS `balance`");
		sql.append("FROM `sk_student` ");
		sql.append("LEFT JOIN `sk_balance_log` ON `sk_student`.`stu_id` = `sk_balance_log`.`stu_id` AND `sk_balance_log`.`is_charge` = 1 ");
		
//		sql.append("FROM `sk_student`, `sk_balance_log` ");
//		sql.append("WHERE `sk_student`.`stu_id` = `sk_balance_log`.`stu_id` AND `sk_balance_log`.`is_charge` = 1 ");
		
		sql.append("GROUP BY `sk_student`.`stu_id` ");
		sql.append(") `student_table` ");
		sql.append(",`sk_class`, `sk_teacher` ");
		sql.append("WHERE `student_table`.`stu_id` = `sk_class`.`stu_id` ");
		sql.append("AND `sk_class`.`tea_id` = `sk_teacher`.`tea_id` ");
		
		if(startDate != null){
			sql.append("AND DATE_FORMAT(?,'%Y-%m-%d') <= DATE_FORMAT(`sk_class`.`cls_begin_time`,'%Y-%m-%d') ");
		}

		if(minBalance > 0){
			sql.append("AND `student_table`.`balance` >= ? ");
		}

		if(endDate != null){
			sql.append("AND DATE_FORMAT(?,'%Y-%m-%d') >= DATE_FORMAT(`sk_class`.`cls_begin_time`,'%Y-%m-%d') ");
		}
			
		sql.append("ORDER BY `stu_id` ,`cls_begin_time` DESC ;");
		
		List<Object> param = new ArrayList<Object>();
		
		if(startDate != null){
			param.add(simple.format(startDate));
		}

		if(minBalance > 0){
			param.add(minBalance);
		}

		if(endDate != null){
			param.add(simple.format(endDate));
		}
		
		logger.info(sql.toString().replaceAll("\\?", "{}"), param.toArray());
		return this.getJdbcTemplate().query(sql.toString(), param.toArray(), ParameterizedBeanPropertyRowMapper.newInstance(StuClsInfo.class));
	}
}
