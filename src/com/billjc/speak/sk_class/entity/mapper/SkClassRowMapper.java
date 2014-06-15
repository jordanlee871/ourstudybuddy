package com.billjc.speak.sk_class.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.billjc.speak.sk_class.entity.SkClass;

public class SkClassRowMapper implements RowMapper<SkClass> {
	
	 	Boolean flag=false;
   public SkClassRowMapper(boolean flag) {
	// TODO Auto-generated constructor stub
	   this.flag=flag;
   }
   public SkClassRowMapper(){
	   
   }
	
	public SkClass mapRow(ResultSet rs, int row) throws SQLException {
		
		SkClass sc=new SkClass();
		
		sc.setBeginTime(rs.getTimestamp("cls_begin_time"));
		sc.setBookingRemark(rs.getString("booking_remark"));
		sc.setClsType(rs.getInt("cls_type"));
		sc.setClsComment(rs.getString("cls_comment"));
		sc.setClsId(rs.getLong("cls_id"));
		sc.setClsLength(rs.getFloat("cls_length"));
		sc.setStuLate(rs.getInt("stu_late"));
		sc.setTeaLate(rs.getInt("tea_late"));
		sc.setTeaId(rs.getLong("tea_id"));
		sc.setStuId(rs.getLong("stu_id"));
		sc.setClsStatus(rs.getInt("cls_status"));
	
		if(flag){
			
			sc.setName(rs.getString("user_name"));
			sc.setEname(rs.getString("ww_num"));
		}
		
	
		
		
		return sc;
		
	}

}
