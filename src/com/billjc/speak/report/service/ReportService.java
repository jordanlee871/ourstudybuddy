package com.billjc.speak.report.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.framework.util.Constant;
import com.billjc.speak.report.dao.ReportDao;
import com.billjc.speak.report.entity.FirstClassInfo;
import com.billjc.speak.report.entity.StuClsInfo;
import com.billjc.speak.report.entity.TeaDailyClsInfo;

@Service
public class ReportService {
	final Logger logger = LoggerFactory.getLogger(ReportService.class);

	@Autowired
	private ReportDao reportDao;
	
	/**
	 * 查看老师当天上课情况（除了空余时间总数）
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public List<TeaDailyClsInfo>getTeaDailyList(String dateStr) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = (dateStr == null || "".equals(dateStr) )? null : simple.parse(dateStr);
		
		List<TeaDailyClsInfo> infoList = reportDao.getTeaDailyList(date);
		List<TeaDailyClsInfo> infoList2 = reportDao.getTeaDailyListFree(date);
		
		int i=0;
		for(TeaDailyClsInfo info : infoList){
			//设置链接
			info.setFreeClass( infoList2.get(i).getFreeClass() );
			info.setLink( info.getName() + "(TID:" + info.getTeaId() + ")" );
			i++;
		}
		return infoList;
	}
	
	/**
	 * 查看老师当天上课情况（除了空余时间总数）
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public List<FirstClassInfo>getFirstClassList(String dateStr) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = (dateStr == null || "".equals(dateStr) )? null : simple.parse(dateStr);
		
		List<FirstClassInfo> infoList = reportDao.getFirstClassList(date);
		
		int i=0;
		for(FirstClassInfo info : infoList){
			//设置链接
			info.setStuLink( info.getStuName() + "(ID:" + info.getStuId() + ")" );
			info.setTeaLink( info.getTeaName() + "(ID:" + info.getTeaId() + ")" );
			info.setClsTypeName( Constant.getClsTypeName(info.getClsType()) );
			i++;
		}
		return infoList;
	}
	
	/**
	 * 查询学生上课进度
	 * @param startDateStr 开始时间
	 * @param endDateStr 结束时间
	 * @param minBalance 最少余额
	 * @param times 课程记录数量
	 * @return
	 */
	public List<StuClsInfo>getStuClsInfoList(String startDateStr, String endDateStr, Integer minBalance, Integer times) throws ParseException{
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDate = (startDateStr == null || "".equals(startDateStr) )? null : simple.parse(startDateStr);
		Date endDate = (endDateStr == null || "".equals(endDateStr) )? null : simple.parse(endDateStr);
		
		List<StuClsInfo> infoList = reportDao.getStuClsInfoList(startDate, endDate, minBalance);
		List<StuClsInfo> resultList = new ArrayList<StuClsInfo>();
		
		int j=1;
		Integer lastStuId = 0; //每次循环的上一个学生id
		for(StuClsInfo info : infoList){
			//设置链接
			info.setStuLink( info.getStuName() + "(ID:" + info.getStuId() + ")" );
			info.setTeaLink( info.getTeaName() + "(ID:" + info.getTeaId() + ")" );
			
			if(lastStuId.equals( info.getStuId() )){
				j++;
			}else{
				j = 1;
			}
			lastStuId = info.getStuId();
			if( j <= times ){
				resultList.add(info);
			}
		}
		return resultList;
	}
}
