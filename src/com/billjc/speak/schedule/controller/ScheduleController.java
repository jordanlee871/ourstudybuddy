package com.billjc.speak.schedule.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.util.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.awt.windows.WWindowPeer;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.DateUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.schedule.entity.Schedule;
import com.billjc.speak.schedule.service.ScheduleService;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;

@Controller
@RequestMapping("/business/schedule/")
public class ScheduleController {
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private StudentService studentService;
	
	//显示学生时间表
	@RequestMapping(value="stuSchedule")
	public ModelAndView stuSchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/schedule/stuSchedule");
		
		User loginUser = SessionUtil.getLoginUser(request);
		
		String realPath=request.getContextPath();
		Student s=(Student)studentService.queryStudentBywwNum(loginUser.getUser_name());
		model.addObject("data", scheduleService.getStuSchedule(s,realPath).get("data"));
		return model;
	}

	//显示老师的时间列表
	@RequestMapping(value="mySchedule")
	public ModelAndView mySchedule(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/schedule/teaSchedule");
		String scheduleCheckDateString = ParamUtils.getParameter(request, "scheduleCheckDate",null);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date scheduleCheckDate = null;
		if (null != scheduleCheckDateString) {
			scheduleCheckDate = df.parse(scheduleCheckDateString);
		} else{
			scheduleCheckDate = new Date();
		}
		String realPath=request.getContextPath();
		
		User loginUser = SessionUtil.getLoginUser(request);
		long teaId=ParamUtils.getLongParameter(request, "teaId", 0);
		String teaName=ParamUtils.getParameter(request, "teaName", null);
		Teacher tea = null;
		if(0!=teaId){
			tea=teacherService.queryById(teaId);
		}else if(null != teaName){
			tea=teacherService.queryByName(teaName.trim());
		}
		String stu_id=ParamUtils.getParameter(request, "stu_id", "0");
		
		if(loginUser.getUser_role()==Constant.USER_ROLE_ADMIN){
			model.addObject("role",Constant.USER_ROLE_ADMIN);
			model.addObject("stu_id",stu_id);
			model.addObject("scheduleCheckDate", df.format(scheduleCheckDate));
			model.addObject("viewNameFlag",loginUser.getUser_role());
			if(null == tea){
				model.addObject("data", null);
			}else{
				model.addObject("viewName",tea.getName());
				model.addObject("teaId",tea.getTea_id());
				model.addObject("data", scheduleService.getMySchedule(tea,stu_id,realPath,loginUser,scheduleCheckDate).get("data"));
			}
		}else if(loginUser.getUser_role()==Constant.USER_ROLE_TEACHER){
			List teacher= teacherService.selectByUid(loginUser.getUser_id());
		        Iterator it=teacher.iterator();
		        while(it.hasNext()){
		       	Teacher tempTeacher=(Teacher)it.next();
		       	
		       	model.addObject("stu_id",stu_id);
		       	model.addObject("teaId",tempTeacher.getTea_id());
		       	model.addObject("role",Constant.USER_ROLE_TEACHER);
		       	model.addObject("scheduleCheckDate", df.format(scheduleCheckDate));
		       	model.addObject("viewName",tempTeacher.getName());
		       	model.addObject("viewNameFlag",loginUser.getUser_role());
		    	
		       	model.addObject("data", scheduleService.getMySchedule(tempTeacher,stu_id,realPath,loginUser,scheduleCheckDate).get("data"));
		          }
		}else if(loginUser.getUser_role()==Constant.USER_ROLE_STUDENT){
			
			model.addObject("teaId",tea.getTea_id());
			model.addObject("stu_id",stu_id);
			model.addObject("role",Constant.USER_ROLE_STUDENT);
	       	model.addObject("scheduleCheckDate",scheduleCheckDateString);
			model.addObject("viewName",tea.getName());
			model.addObject("viewNameFlag",loginUser.getUser_role());
			//学生必然是搜索当天
			model.addObject("data", scheduleService.getMySchedule(tea,stu_id,realPath,loginUser,scheduleCheckDate).get("data"));
		}
		return model;
	}
	
	
/*	//显示老师列表某个老师的时间列表
		@RequestMapping(value="myScheduleForTeaList")
		public ModelAndView myScheduleForTeaList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView model = new ModelAndView("/business/schedule/teaScheduleEx");
			String realPath=request.getContextPath();
			User loginUser = SessionUtil.getLoginUser(request);
			long teaId=ParamUtils.getLongParameter(request, "tea_id", 0);
			Teacher t=teacherService.queryById(teaId);
			model.addObject("role",1);
			model.addObject("viewName",t.getName());
//			model.addObject("data", scheduleService.getMyScheduleForTeaList(t,realPath,loginUser).get("data"));
			model.addObject("data", scheduleService.getMySchedule(t,null,realPath,loginUser,new Date()).get("data"));
			return model;
		}*/
	
	//显示老师空闲时间
	@RequestMapping(value="freetime")
	public ModelAndView getTeacherFreeTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/schedule/freetime");
		long  tea_id=ParamUtils.getLongParameter(request, "tea_id",0);
		
		String search_name=new String (ParamUtils.getParameter(request, "search_name", "").getBytes("iso8859-1"),"utf-8");

		int pageNo=ParamUtils.getIntParameter(request, "pageNo", 1);
		Date scheduleCheckDateParm=DateUtil.parseDateDayFormat(ParamUtils.getParameter(request, "scheduleCheckDate",null));
		java.sql.Date scheduleCheckDate=null;
		if (null != scheduleCheckDateParm) {
			scheduleCheckDate = new java.sql.Date(scheduleCheckDateParm.getTime());
		} else {
			scheduleCheckDate = new java.sql.Date(new Date().getTime());
		}
		
		User loginUser = SessionUtil.getLoginUser(request);
		Map<String,Object> result;
		if(loginUser.getUser_role()==Constant.USER_ROLE_ADMIN){
			result=scheduleService.getfreetimeForAdmin(request.getContextPath(),search_name,scheduleCheckDate,pageNo,tea_id);
		}else {
			throw new Exception("异常用户访问：freetime.do: userID"+loginUser.getUser_id());
//			result=scheduleService.getfreetime(request.getContextPath(),search_name,search_time,pageNo,tea_id);
		}

		 int  sum= (Integer)result.get("count");
		 int  pagSize=Constant.FREETIME_PAGESIZE;
		 int  pageCount=sum/pagSize;
		 int  flag=sum%pagSize;
		 
		 if(flag>0){
			 pageCount++;
		 }
		model.addObject("data", result.get("data"));
		model.addObject("teachers", result.get("teachers"));
		model.addObject("scheduleCheckDate", scheduleCheckDate);
		model.addObject("search_name",
				search_name == null ? result.get("search_name") : search_name);
		model.addObject("pageNo", pageNo);
		model.addObject("pageCount", pageCount);
		model.addObject("pageSize", pagSize);
		return model;
	}
	

	
	//设置所选的时间表记录为free
	@RequestMapping(value = "/freeIt", method = RequestMethod.POST)
	public  @ResponseBody String freeSchedules(@RequestParam("idsAndNames")String idsAndNames, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(scheduleService.freeSchedules(idsAndNames)>=1){			
			return "true";
		}else{
			return "false";
		}
	}
	
	//设置所选的时间表记录为close
	@RequestMapping(value = "/closeIt", method = RequestMethod.POST)
	public  @ResponseBody String closeSchedules(@RequestParam("idsAndNames")String idsAndNames, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(scheduleService.closeSchedules(idsAndNames)>=1){			
			return "true";
		}else{
			return "false";
		}
	}	
	
	//设置4天时间表所选的记录为free
	@RequestMapping(value = "/freeNextDays", method = RequestMethod.POST)
	public @ResponseBody String freeNextDays(@RequestParam("idsAndNames")String idsAndNames, HttpServletRequest request, HttpServletResponse response) throws Exception{	
		if(scheduleService.freeNextDays(idsAndNames)>=1){			
			return "true";
		}else{
			return "false";
		}
	}
	
	//设置4天时间表所选的记录为close
	@RequestMapping(value = "/closeNextDays", method = RequestMethod.POST)
	public @ResponseBody String closeNextDays(@RequestParam("idsAndNames")String idsAndNames, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(scheduleService.closeNextDays(idsAndNames)>=1){			
			return "true";
		}else{
			return "false";
		}
	}

	
	//跳转到预定课程页面
	@RequestMapping(value="gotoBookClass")
	public ModelAndView gotoBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/sk_class/bookClassInfo");
		SimpleDateFormat dateFromat=new SimpleDateFormat("yyyy-MM-dd");
		User loginUser = SessionUtil.getLoginUser(request);
		String viewEc = ParamUtils.getParameter(request, "viewEC", "");
      
		long  teaId=ParamUtils.getLongParameter(request, "teaId",0);
		String stuId=ParamUtils.getParameter(request, "stu_id","null");
		String mixId = ParamUtils.getParameter(request, "mixId",null);
		long  lengthNum=Long.parseLong(ParamUtils.getParameter(request, "lengthNum",""));
		String scheduleDateString=ParamUtils.getParameter(request, "scheduleDate");
		int  flag=ParamUtils.getIntParameter(request, "flag",0);
		
		
		Date scheduleDate = null;
		if (null == scheduleDateString || scheduleDateString.isEmpty()) {
			throw new Exception("scheduleDate is empty: user:"+loginUser.getUser_id()+";stuID="+stuId+";teaID="+teaId);
		} else {
			scheduleDate = dateFromat.parse(scheduleDateString);
		}
		
		Student stu=null ;
		if(!"null".equals(stuId)){
			stu = studentService.queryStudentByStuId(Long.parseLong(stuId));
			if(null == stu){
				viewEc = Constant.VIEW_EC_STUDENT_NOT_FOUNT;
			}
		}else if(null != mixId){
			Teacher teaTemp = new Teacher();
			teaTemp.setTea_id(teaId);
			List<Student> stuList = studentService.queryStudentByMixId(mixId, Constant.USER_ROLE_ADMIN == loginUser.getUser_role(),teaTemp);
			if(null == stuList || stuList.size()==0){
				viewEc = Constant.VIEW_EC_STUDENT_NOT_FOUNT;
			} else{
				stu = stuList.get(0);
			}
		}
		
		Teacher teacher = teacherService.queryById(teaId);
		
		//设置开始时间
		String beginTime=null;
		
		if(lengthNum%2==0){
			float first_float= lengthNum/2;
			int first_int=  new Float(first_float).intValue();
			if(first_int<10){
				beginTime=scheduleDateString+" 0"+first_int+":00";
			}else{
				beginTime=scheduleDateString+" "+first_int+":00";
			}
		}else{
			float first_float= (lengthNum-1)/2;
			int first_int=  new Float(first_float).intValue();
			if(first_int<10){
				beginTime=scheduleDateString+" 0"+first_int+":30";
			}else{
				beginTime=scheduleDateString+" "+first_int+":30";
			}
		}
		
		//如果缺少学生，则跳转到学生搜索界面
		if (null == stu) {
			model.addObject("scheduleDate", scheduleDateString);
			model.addObject("beginTime", beginTime);
			model.addObject("beginTimeEx", beginTime);
			model.addObject("lengthNum", lengthNum);
			model.addObject("teaName", teacher.getName());
			model.addObject("stu_id", stuId);
			model.addObject("viewEC",viewEc);
			model.addObject("teaId", teaId);
			model.setViewName("redirect:/business/students/queryStudent.do");
			return model;
		}
		
		//设置可选的课程长度
		String[] classLengths = scheduleService.getBookingTime(lengthNum,teacher,scheduleDate);
		if(null!=stu){
			String message="";
			if(stu.getBalance()<=(float)(classLengths.length/2)){
				int num=1;
			if(stu.getBalance()>=(float)2){
				 num=4;
			}else{
				num=(int) (stu.getBalance()*2);
			}
				double init=0.5;
				
				if(lengthNum==45&&stu.getBalance()>=(float)(1.5)){
					num=3;
				}else if(lengthNum==46&&stu.getBalance()>=(float)1){
					num=2;
				}else if(lengthNum==47&&stu.getBalance()>=(float)(0.5)){
					num=1;
				}
				System.out.println(num);
				if(num!=0){
					classLengths= new String [num];
				for(int i=0;i<num;i++){
					String init_String=init+"";
					classLengths[i]=init_String;
					init=init+0.5;	
				}
				}else{
					classLengths=new String[1];
					classLengths[0] ="0.0";
					
				}
			}
			
			if(stu.getBalance()==(float)3){
				message="3.0 class left";
			}
			if(stu.getBalance()==(float)2){
				message="2.0 class left";
			}
			
			if(stu.getBalance()==(float)1.5){
				message="1.5 class left";
			}
			
			if(stu.getBalance()==(float)1){
				message="THE LAST 1.0 class";
			}
			
			if(stu.getBalance()==(float)0.5){
				message="THE LAST 0.5 class";
			}
			
			if(stu.getBalance()==(float)0){
				message="NO BALANCE";
			}
			model.addObject("message",message);
			
		}
		model.addObject("classLengths",classLengths);
		model.addObject("scheduleDate",scheduleDateString);
		model.addObject("beginTime",beginTime);
		model.addObject("beginTimeEx",beginTime);
		model.addObject("lengthNum",lengthNum);
		model.addObject("teaName",teacher.getName());
		model.addObject("stu_id",stu.getStu_id());
		model.addObject("stuName",stu.getEname());
		model.addObject("stuQq",stu.getQq_num());
		model.addObject("stuSkype",stu.getSkype_num());
		model.addObject("teaId",teaId);
		
		
		return model;
	}
	
	
	//跳转到预定课程页面
	@RequestMapping(value="gotoDaysBookClass")
	public ModelAndView gotoDaysBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/sk_class/bookClassInfo");
		
      
		long  teaId=ParamUtils.getLongParameter(request, "teaId",0);
		
		long  lengthNum=Long.parseLong(ParamUtils.getParameter(request, "lengthNum",""));
		
		int days=ParamUtils.getIntParameter(request, "days", 0);

		
        String station=ParamUtils.getParameter(request, "station", "");
		
		if("1".equals(station)){
			model.addObject("station",1);
		}else if("2".equals(station)){
			model.addObject("station",2);
		}else if("3".equals(station)){
			model.addObject("station",3);
		}
       		String  teaName=teacherService.queryById(teaId).getUser_name();
       		
       		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       		
       		
       		
		//设置开始时间
		String first="";
		String am_pm=" AM";
		Date d=new Date();
		String beginTime= sdf.format(new Date(d.getTime() + days * 24 * 60 * 60 * 1000));
		
		if(lengthNum%2==0){
			float first_float= lengthNum/2;
			
			int first_int=  new Float(first_float).intValue();
			
			
			first=(first_int)+"";
			
			if(first_int<10){
				
				beginTime=beginTime+" 0"+first+":00";
				
			}else{
				
				beginTime=beginTime+" "+first+":00";
				if(first_int>=12)
					am_pm=" PM";
			}
			
		}else{
			
			float first_float= (lengthNum-1)/2;
			
			int first_int=  new Float(first_float).intValue();
			
			first=(first_int)+"";
			
			if(first_int<10){
				
				beginTime=beginTime+" 0"+first+":30";
				
			}else{
				
				beginTime=beginTime+" "+first+":30";
				if(first_int>=12)
					am_pm=" PM";
			}
		}
		
		//设置可选的课程长度
		String[] classLengths = scheduleService.checkEnableBookClassLengthAndDays(lengthNum,teaId,days);
		
		
		
		model.addObject("classLengths",classLengths);
		model.addObject("days",days);
		model.addObject("beginTime",beginTime);
		model.addObject("beginTimeEx",beginTime+am_pm);
		model.addObject("lengthNum",lengthNum);
		model.addObject("teaName",teaName);
		model.addObject("teaId",teaId);
		return model;
	}
	
	//跳转到预定课程页面
	@RequestMapping(value="gotoStuBookClass")
	public ModelAndView gotoStuBooking(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/sk_class/bookClassInfo");
		
		long  teaId=ParamUtils.getLongParameter(request, "teaId",0);
		
		long  lengthNum=Long.parseLong(ParamUtils.getParameter(request, "lengthNum",""));
		
		int days=ParamUtils.getIntParameter(request, "days", 0);

        String station=ParamUtils.getParameter(request, "station", "");
		
		if("1".equals(station)){
			model.addObject("station",1);
		}else if("2".equals(station)){
			model.addObject("station",2);
		}else if("3".equals(station)){
			model.addObject("station",3);
		}
       		String  teaName=teacherService.queryById(teaId).getUser_name();
       		
       		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       		
       	User loginUser = SessionUtil.getLoginUser(request);	
       	
       	Student stu_login=studentService.queryStudentByUserId(loginUser.getUser_id());
       	
       	model.addObject("stu_id",stu_login.getStu_id());
       	
       		
		//设置开始时间
		String first="";
		String am_pm=" AM";
		Date d=new Date();
		String beginTime= sdf.format(new Date(d.getTime() + days * 24 * 60 * 60 * 1000));
		
		if(lengthNum%2==0){
			float first_float= lengthNum/2;
			
			int first_int=  new Float(first_float).intValue();
			
			
			first=(first_int)+"";
			
			if(first_int<10){
				
				beginTime=beginTime+" 0"+first+":00";
				
			}else{
				
				beginTime=beginTime+" "+first+":00";
				if(first_int>=12)
					am_pm=" PM";
			}
			
		}else{
			
			float first_float= (lengthNum-1)/2;
			
			int first_int=  new Float(first_float).intValue();
			
			first=(first_int)+"";
			
			if(first_int<10){
				
				beginTime=beginTime+" 0"+first+":30";
				
			}else{
				
				beginTime=beginTime+" "+first+":30";
				if(first_int>=12)
					am_pm=" PM";
			}
		}
		//设置可选的课程长度
		String message="";
		String[] classLengths = scheduleService.checkEnableBookClassLengthAndDays(lengthNum,teaId,days);
		if(stu_login.getBalance()<=(float)(classLengths.length/2)){
			
			int num=1;
		if(stu_login.getBalance()>=(float)2){
			 num=4;
		}else{
			num=(int) (stu_login.getBalance()*2);
			
		}
			double init=0.5;
			
			if(lengthNum==45&&stu_login.getBalance()>=(float)(1.5)){
				num=3;
			}else if(lengthNum==46&&stu_login.getBalance()>=(float)1){
				num=2;
			}else if(lengthNum==47&&stu_login.getBalance()>=(float)(0.5)){
				num=1;
			}
			System.out.println(num);
			if(num!=0){
				classLengths= new String [num];
			for(int i=0;i<num;i++){
				String init_String=init+"";
				classLengths[i]=init_String;
				init=init+0.5;	
			}
			}else{
				classLengths=new String[1];
				classLengths[0] ="0.0";
				
			}
			
				
		}
		
		if(stu_login.getBalance()==(float)3){
			message="本期学习计划就剩下3课时了，加油!";
		}
		if(stu_login.getBalance()==(float)2){
			message="最后2.0课时，一起冲刺吧!";
		}
		
		if(stu_login.getBalance()==(float)1.5){
			message="最后1.5课时，一起冲刺吧!";
		}
		
		if(stu_login.getBalance()==(float)1){
			message="最后1.0课时，一起冲刺吧!";
		}
		
		if(stu_login.getBalance()==(float)0.5){
			message="最后0.5课时，一起冲刺吧!";
		}
		
		if(stu_login.getBalance()==(float)0){
			message="对不起，你的余额不足，不能预订课程 !";
		}
		model.addObject("classLengths",classLengths);
		model.addObject("message",message);
		model.addObject("days",days);
		model.addObject("beginTime",beginTime);
		model.addObject("beginTimeEx",beginTime+am_pm);
		model.addObject("lengthNum",lengthNum);
		model.addObject("teaName",teaName);
		model.addObject("teaId",teaId);
		 
		return model;
	}
	
	
}
