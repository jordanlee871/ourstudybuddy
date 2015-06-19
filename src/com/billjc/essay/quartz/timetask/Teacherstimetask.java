package com.billjc.essay.quartz.timetask;

import org.springframework.scheduling.annotation.Scheduled;  
import org.springframework.stereotype.*; 

/*
@Service
public class Teacherstimetask  {
	 public void myTest(){  
        System.out.println("进入测试");  
 }  
}
*/

/*
 * 
 * SQL statement need to execute in the service bean:
 * UPDATE t_teacher AS dest INNER JOIN (SELECT teacher_id,Day2num FROM (SELECT teacher_id,Day2num from t_teacher) AS a) AS src ON dest.teacher_id=src.teacher_id SET dest.Day1num=src.Day2num;
 * 
 */

@Service
public class Teacherstimetask  {
//	@Scheduled(cron="0/5 * *  * * ? ")
	 public void myTest(){  
       System.out.println("进入测试");  
}  
}
