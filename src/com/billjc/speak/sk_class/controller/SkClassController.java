package com.billjc.speak.sk_class.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.DateUtil;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.balance.service.BalanceService;
import com.billjc.speak.privateset.service.PrivatesetService;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.sk_class.service.SkClassService;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.system.service.SystemSettingService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/sk_class/")
public class SkClassController {
	final Logger logger = LoggerFactory.getLogger(SkClassController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PrivatesetService privatesetService;
	
	@Autowired
	private SkClassService skClassService;
	@Autowired
	private  BalanceService balanceService;
	
	@Autowired
	private SystemSettingService systemSetingService;
	
	
  //新增一个预约课程
	@RequestMapping(value = "/bookClass", method = RequestMethod.POST)
	public ModelAndView addStudent(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("redirect:/business/sk_class/gotoClassInfo.do");
		String getStu_id=ParamUtils.getParameter(request, "stu_id", "");
		Long teaId=ParamUtils.getLongParameter(request, "teaId", 0);
		int days=ParamUtils.getIntParameter(request, "days", 9);
		Long lengthNum=ParamUtils.getLongParameter(request, "lengthNum", 0);
		String  beginTime=ParamUtils.getParameter(request, "beginTime", "");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date beginTime_util=sdf.parse(beginTime);
		String clsLength= ParamUtils.getParameter(request, "clsLength", "");
		String bookingRemark=ParamUtils.getParameter(request, "bookingRemark","");
		int  clsType =ParamUtils.getIntParameter(request, "clsType", 0);
		User loginUser = SessionUtil.getLoginUser(request);
		int role=loginUser.getUser_role();
		if(role==Constant.USER_ROLE_ADMIN){
			model.addObject("role",1);
		}else if(role==Constant.USER_ROLE_TEACHER){
			model.addObject("role",2);
		}else if(role==Constant.USER_ROLE_STUDENT){
			model.addObject("role",3);
		}
		Student s= studentService.queryStudentByStuId(Long.parseLong(getStu_id));
		int flag =new Integer(days);
		model.addObject("flag",flag);
		if(days==4){
			
			days=0;
		}
		SkClass cls=new SkClass();
		cls.setBeginTime(beginTime_util);
		cls.setBookingRemark(bookingRemark);
		cls.setClsComment("");
		cls.setClsLength(Float.parseFloat(clsLength));
		cls.setClsStatus(Constant.CLASS_STATUS_PENDING);
		cls.setClsType(clsType);
		cls.setStuId(s.getStu_id());
		cls.setTeaId(teaId);
		cls.setStuLate(0);
		cls.setTeaLate(0);
		
		List<SkClass> classes = skClassService.queryNormalClass(teaId, beginTime_util);
		
		if(0 < classes.size()){
			model.addObject("clsId",classes.get(0).getClsId());
			model.addObject("returnFlag","2");
			return model;
		}
		
		int returnFlag = skClassService.insertSkClass(cls, lengthNum,days);
		if(returnFlag>0) {
			Balance bl=new Balance();
			bl.setBalance_before(s.getBalance());
			bl.setBlNum(-Float.parseFloat(clsLength));
			bl.setStuId(s.getStu_id());
			String teaName=teacherService.queryById(teaId).getName();
			
			String comment = "";

			// 添加操作者名字以及ID
			if (loginUser.getUser_role() == Constant.USER_ROLE_STUDENT
					.intValue()) {
				Student tempStu = studentService.queryStudentByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(SID:"
						+ tempStu.getStu_id() + ")]";
			} else if (loginUser.getUser_role() == Constant.USER_ROLE_TEACHER
					.intValue()) {
				Teacher tempTea = teacherService.queryByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(TID:"
						+ tempTea.getTea_id() + ")]";
			} else {
				comment += "[" + loginUser.getUser_name() + "(ID:"
						+ loginUser.getUser_id() + ")]";
			}

			comment += " [Book] " + " [" + teaName + "(TID:" + teaId + ") "
					+ cls.getClsLength() + " " + cls.getBeginTimeEx() + "]";
			bl.setComment(comment);

			balanceService.charge(s.getStu_id(), bl);
			s.setBalance(s.getBalance() - Float.parseFloat(clsLength));
			studentService.UpdateRegStudent(s);
		}
		
		skClassService.doBookNotice(loginUser, teaId.toString()+",", s, Constant.CLASS_STATUS_BOOK, cls);
		
		
		model.addObject("clsId",skClassService.queryNormalClass(teaId, beginTime_util).get(0).getClsId());
		model.addObject("returnFlag","1");
		
		return model;
	}
	
	
	
	
	
	//查询预约课程信息
	@RequestMapping(value = "/gotoClassInfo", method = RequestMethod.GET)
	public ModelAndView getClassInfo(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView model = new ModelAndView("/business/sk_class/ClassInfo");
		
		Long clsId=ParamUtils.getLongParameter(request, "clsId", 0);
		String clsList=ParamUtils.getParameter(request, "clsList","");
		String returnFlag=ParamUtils.getParameter(request, "returnFlag","");
		
	    SkClass cls=skClassService.queryClassByClsId(clsId);
	    
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
	    String  am_pm =cls.getBeginTime().getHours()<12?" AM":" PM";
	    String beginTime =  sdf.format(cls.getBeginTime())+am_pm;
	    
	    Date d=new Date();
	    SystemSetting seting=systemSetingService.getCxtSetting();
	    
	    Student student = studentService.queryStudentByStuId(cls.getStuId());
	    
	    Teacher teacher = teacherService.queryById(cls.getTeaId());
	    
	    //设置可选是否可以取消预约课程
	    int stuClx=2;
	    int teaClx=2;
	    if(d.getTime()<=cls.getBeginTime().getTime()){
	    Date SresultTime= new Date(cls.getBeginTime().getTime() - Integer.parseInt(seting.getStuEmgCxl().getSettingValue())*60000);
	    Date TresultTime= new Date(cls.getBeginTime().getTime() - Integer.parseInt(seting.getTeaEmgCxl().getSettingValue())*60000);
	    
	    if(d.getTime()>SresultTime.getTime()){
	    	stuClx=1;
	    }else{
	    	stuClx=0;
	    }
	    if(d.getTime()>TresultTime.getTime()){
	    	teaClx=1;
	    }else{
	    	teaClx=0;
	    }
	    
	    }
		model.addObject("cls",cls);
		model.addObject("student",student);
		model.addObject("teacher",teacher);
		model.addObject("clsList",clsList);
		model.addObject("stuClx",stuClx);
		model.addObject("teaClx",teaClx);
		model.addObject("beginTime",beginTime);
		model.addObject("returnFlag",returnFlag);
		return model;
	}


	//检查账户余额
	@RequestMapping(value = "/checkBalance", method = RequestMethod.POST)
	public @ResponseBody String checkBalance( HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";
		String stu_id=ParamUtils.getParameter(request, "stu_id","");
		long teaId=ParamUtils.getLongParameter(request, "teaId",0);
		Student s=studentService.queryStudentByStuId(Long.parseLong(stu_id));
		String stuName=s.getWw_num();
		 result= studentService.checkBalance(stuName,teaId);
		return result;
	}
	
	
	
	//更新预约课程信息
	@RequestMapping(value = "/updateClass", method = RequestMethod.POST)
	public ModelAndView UpdateClassInfo(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView model = new ModelAndView("/business/sk_class/result");
		Long clsId=ParamUtils.getLongParameter(request, "clsId", 0);
		Integer stuLate=ParamUtils.getIntParameter(request, "stuLate", 0);
		Integer teaLate=ParamUtils.getIntParameter(request, "teaLate", 0);
		Integer clsType=ParamUtils.getIntParameter(request, "clsType", 0);
		String clsBookingRemark=ParamUtils.getParameter(request, "clsBookingRemark", "");
		String clsComment=ParamUtils.getParameter(request, "clsComment", "");
		Integer	clsStatus=ParamUtils.getIntParameter(request, "clsStatus",-1);
		String stuName=ParamUtils.getParameter(request, "stuName", "");
		
		if(clsBookingRemark!=null)
			clsBookingRemark=clsBookingRemark.trim();
		if(clsComment!=null)
			clsComment=clsComment.trim();
		
		User loginUser = SessionUtil.getLoginUser(request);
		if(loginUser.getUser_role()==Constant.USER_ROLE_ADMIN){
			skClassService.adminCancelClass(clsId,stuLate,teaLate,clsStatus,clsType,clsBookingRemark,clsComment,loginUser);
		}else {
//			if(clsStatus==2||clsStatus==3||clsStatus==4||clsStatus==5){
//				skClassService.cancleClass(clsId,stuLate,teaLate,clsStatus,clsType,clsBookingRemark,clsComment,loginUser);
//				model.addObject("returnTo","cancle");
//			}else{
//				skClassService.updateClass(clsId,stuLate,teaLate,clsStatus,stuName,clsType,clsBookingRemark,clsComment,loginUser);
//			}
			skClassService.adminCancelClass(clsId,stuLate,teaLate,clsStatus,clsType,clsBookingRemark,clsComment,loginUser);
		}
		
		SkClass cls= skClassService.queryClassByClsId(clsId);
		model.addObject("stu_ww",stuName);
		model.addObject("stu_id",cls.getStuId());
		model.addObject("teaId",cls.getTeaId());
	   	return model;
	}
	
	//查看老师课程统计信息
	@RequestMapping(value = "/count")
	public ModelAndView countCls(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/sk_class/statisticsCls");
		User user = SessionUtil.getLoginUser(request);
		
		long teaId= Long.parseLong(ParamUtils.getParameter(request, "teaId", "0"));
		if(Constant.USER_ROLE_TEACHER == user.getUser_role()){
			teaId = teacherService.queryByUserId(user.getUser_id()).getTea_id();
		}
		Date begin=DateUtil.parseDateDayFormat(ParamUtils.getParameter(request, "begin",""));
		
	
		Date end=DateUtil.parseDateDayFormat(ParamUtils.getParameter(request, "end",""));
		
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 String begin_String=null;
		 String end_String=null;
		 
		java.sql.Date begin_sql=null;
		if(begin!=null){
			begin_String=sdf.format(begin);
//			begin.setDate(begin.getDate()-1);
		 begin_sql=new java.sql.Date(begin.getTime());
		}
		java.sql.Date end_sql=null;
		if(end!=null){
			 end_String=sdf.format(end);
//			end.setDate(end.getDate()+1);
		 end_sql=new java.sql.Date(end.getTime());
		}
		Map<String,String> map=this.skClassService.count(teaId,begin_sql,end_sql);
		
		model.addObject("ieCount",map.get("ieCount"));
		model.addObject("toCount",map.get("toCount"));
		model.addObject("trCount",map.get("trCount"));
		model.addObject("otCount",map.get("otCount"));
		
		model.addObject("ieCount1",map.get("ieCount1"));
		model.addObject("toCount1",map.get("toCount1"));
		model.addObject("trCount1",map.get("trCount1"));
		model.addObject("otCount1",map.get("otCount1"));
		
		model.addObject("scCount",map.get("scCount"));
		model.addObject("tcCount",map.get("tcCount"));
		model.addObject("secCount",map.get("secCount"));
		model.addObject("tecCount",map.get("tecCount"));
		model.addObject("saCount",map.get("saCount"));
		model.addObject("taCount",map.get("taCount"));
		model.addObject("PeCount",map.get("PeCount"));
		
		model.addObject("tName", teacherService.queryById(teaId).getName());
		model.addObject("teaId",teaId);
		model.addObject("begin_jsp",begin_String);
		model.addObject("end_jsp",end_String);
		
		return model;
	}
	
	
	//查看课程历史
	@RequestMapping(value = "/clsList")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/sk_class/ClsList");
		
		User user = SessionUtil.getLoginUser(request);
		
		String sName = ParamUtils.getParameterOfDecoder(request, "sName", "UTF-8", "");
		String tName = ParamUtils.getParameterOfDecoder(request, "tName", "UTF-8", "");
		float clsLength = Float.parseFloat(ParamUtils.getParameter(request, "clsLength", "0"));
		int clsStatus = ParamUtils.getIntParameter(request, "clsStatus", 8);
		int clsType = ParamUtils.getIntParameter(request, "clsType",0);
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		String search_time = ParamUtils.getParameter(request, "search_time", "");
		
		long teaId=ParamUtils.getLongParameter(request, "teaId", 0);
		long stuId=ParamUtils.getLongParameter(request, "stuId", 0);
		
		
		Map<String, Object> searchParam = new HashMap<String, Object>();
		
		searchParam.put("sName", sName);
		searchParam.put("tName", tName);
		searchParam.put("clsLength", clsLength);
		searchParam.put("clsStatus", clsStatus);
		searchParam.put("clsType", clsType);
		searchParam.put("order_by", order_by);
		searchParam.put("user", user);
		searchParam.put("teaId", teaId);
		searchParam.put("stuId", stuId);
		searchParam.put("search_time", search_time);
		
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
		
		Map<String, Object> result = skClassService.queryList(start, end,searchParam);
		total = (Integer)result.get("count");// 查询得出数据记录数;
		
		// 生成分页工具栏
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPageSize(size);
		pageUtil.setCurPage(page);
		pageUtil.setTotalRow(total);
		page = page > pageUtil.getTotalPage() ? 1 : page;

		model.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
		model.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
		model.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
		model.addObject("clsList", result.get("data"));
		
		//查询的参数
		model.addObject("order_by", order_by);
		model.addObject("sName", sName);
		model.addObject("tName", tName);
		model.addObject("clsType", clsType);
		model.addObject("clsLength", clsLength);
		model.addObject("clsStatus", clsStatus);
		model.addObject("stuId", stuId);
		model.addObject("teaId", teaId);
		model.addObject("search_time", search_time);
		
		return model;
	}
	
	//查看课程历史
		@RequestMapping(value = "/MyClasses")
		public ModelAndView listMyClasses(HttpServletRequest request, HttpServletResponse response) throws Exception {
			ModelAndView model = new ModelAndView("/business/sk_class/ClsList");
			
			User user = SessionUtil.getLoginUser(request);
			long teaId = -1;;
			if(Constant.USER_ROLE_TEACHER == user.getUser_role()){
				teaId = teacherService.queryByUserId(user.getUser_id()).getTea_id();
			} else {
				teaId=ParamUtils.getLongParameter(request, "teaId", -1);
			}			
			
			int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
			int size = Constant.LIST_PAGE_SIZE;//每页多少条
			int total = 0;//总条数
			// 使用SQL语句直接分页查询时使用start和end
			int start = (page - 1) * size;
			
			Map<String, Object> result = skClassService.queryList(teaId,start, size);
			total = (Integer)result.get("count");// 查询得出数据记录数;
			
			// 生成分页工具栏
			PageUtil pageUtil = new PageUtil();
			pageUtil.setPageSize(size);
			pageUtil.setCurPage(page);
			pageUtil.setTotalRow(total);
			page = page > pageUtil.getTotalPage() ? 1 : page;

			model.addObject("teaId",teaId);//为后续翻页、搜索用。
			model.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
			model.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
			model.addObject("startSerial", 0);//起始序号,用于列表序号计算。 由于加了Pending课程进去，这个排序不精确，故删除之
//			model.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
			model.addObject("clsList", result.get("data"));
			
			return model;
		}

	 //新增一个预约课程
	@RequestMapping(value = "/stuBookClass", method = RequestMethod.POST)
	public ModelAndView addStuBookCls(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User loginUser = SessionUtil.getLoginUser(request);
		Long user_id=loginUser.getUser_id();
		Student s=studentService.queryStudentByUserId(user_id);
		Long stu_id=s.getStu_id();
		String teaName=ParamUtils.getParameter(request, "teaName", "");
        //	跳到学生刚刚登陆页面	
        //  ModelAndView model = new ModelAndView("redirect:/business/students/detail.do?stu_id="+stu_id+"&flag=index");
		//  跳到老师时间表
		ModelAndView model = new ModelAndView("redirect:/business/schedule/mySchedule.do?teaName="+teaName+"");
		
		
	
		Long stuId=ParamUtils.getLongParameter(request, "stuId", 0);
		Long teaId=ParamUtils.getLongParameter(request, "teaId", 0);
		int days=ParamUtils.getIntParameter(request, "days", 0);
		Long lengthNum=ParamUtils.getLongParameter(request, "lengthNum", 0);
		String  beginTime=ParamUtils.getParameter(request, "beginTime", "");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date beginTime_util=sdf.parse(beginTime);
		String clsLength= ParamUtils.getParameter(request, "clsLength", "");
		String bookingRemark=ParamUtils.getParameter(request, "bookingRemark","");
		int  clsType =ParamUtils.getIntParameter(request, "clsType", 0);
		
		
		SkClass cls=new SkClass();
		cls.setBeginTime(beginTime_util);
		cls.setBookingRemark(bookingRemark);
		cls.setClsComment("");
		cls.setClsLength(Float.parseFloat(clsLength));
		cls.setClsStatus(Constant.CLASS_STATUS_PENDING);
		cls.setClsType(clsType);
		cls.setStuId(stuId);
		cls.setTeaId(teaId);
		cls.setStuLate(0);
		cls.setTeaLate(0);
		
		if(0 < skClassService.queryNormalClass(teaId, beginTime_util).size()){
			model.addObject("stu_id",s.getStu_id());
			model.addObject("teaId",teaId);
			return model;
		}
		
		skClassService.insertSkClass(cls, lengthNum,days);
		
		
		return model;
	}

	
	
	
}
