package com.billjc.speak.schedule.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.schedule.entity.Schedule;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.students.entity.Student;

public class ScheduleRowMapper implements RowMapper<Schedule>{

	public Schedule mapRow(ResultSet rs, int row) throws SQLException{
		Schedule schedule=new Schedule();
		schedule.setSch_id(rs.getLong("sch_id"));
		schedule.setStu_id(rs.getLong("stu_id"));
		schedule.setTea_id(rs.getLong("tea_id"));
		schedule.setSch_length(rs.getInt("sch_length"));
		schedule.setStatus(rs.getLong("status"));
		schedule.setSch_datetime(rs.getDate("sch_datetime"));
		if(null != schedule.getStu_id()){
			try {
				rs.findColumn("ww_num");//如果找不到，就会catch 到SQLException
				Student studentTemp = new Student();
				studentTemp.setWw_num(rs.getString("ww_num"));
				studentTemp.setEname(rs.getString("ename"));
				schedule.setStudent(studentTemp);
			} catch (SQLException e) {				
			}
			
		} if( null != schedule.getStatus() && 0!= schedule.getStatus() && 1!= schedule.getStatus()){
			try {
				rs.findColumn("cls_id");
				SkClass clsTemp = new SkClass();
				clsTemp.setClsId(rs.getLong("cls_id"));
				clsTemp.setTeaId(rs.getLong("tea_id"));
				clsTemp.setBeginTime(rs.getDate("cls_begin_time"));
				clsTemp.setClsLength(rs.getFloat("cls_length"));
				clsTemp.setClsType(rs.getInt("cls_type"));
				clsTemp.setClsStatus(rs.getInt("cls_status"));
				schedule.setCls(clsTemp);
			} catch (SQLException e) {
			}			
		}
		
		return schedule;
	}
}
