package com.billjc.speak.teacher.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.DateUtil;
import com.billjc.framework.util.MD5Util;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/teacher/")
public class TeacherController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	
	 
						
	//获取注销老师列表
	@RequestMapping(value="cancelTeacherList")
	public ModelAndView cancelTeacherList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/teacher/cancelTeacherList");
		
		String search_name = ParamUtils.getParameterOfDecoder(request, "search_name", "UTF-8", "");
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;		
		Map<String, Object> result = teacherService.cancelTeacherList(start, end,searchParam);
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
		model.addObject("users", result.get("data"));

		return model;
	}
	
	//获取老师列表
	@RequestMapping(value="teacherList")
	public ModelAndView teacherList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/teacher/teacherList");
		
		String search_name = ParamUtils.getParameterOfDecoder(request, "search_name", "UTF-8", "");
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
		
		Map<String, Object> result = teacherService.teacherList(start, end,searchParam);
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
		model.addObject("users", result.get("data"));

		return model;
	}
	
	
	//添加老师
	@RequestMapping(value="addTeacher")
	public ModelAndView addTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/teacher/registTeacher");
 
		
//		User loginUser = SessionUtil.getLoginUser(request);
		
		return model;
	}
	
	//注册老师
	@RequestMapping(value="registTeacher")
	public ModelAndView registTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("/business/teacher/registResult");
		Teacher teacher=new Teacher();
		User user =new User();
		
		
        String account=ParamUtils.getParameter(request,"name","0");
		String name=ParamUtils.getParameterOfDecoder(request, "name2", "utf-8", "");
		String QQ = ParamUtils.getParameter(request, "QQ","");
		String skype=ParamUtils.getParameter(request, "skype","");
		String email=ParamUtils.getParameter(request, "email","");
		String tel=ParamUtils.getParameter(request, "tel","");
		int groupId=Integer.parseInt(ParamUtils.getParameter(request, "groupId","0"));
		String password=ParamUtils.getParameter(request, "password","");
		String comment=ParamUtils.getParameter(request, "comment","");
		teacher.setName(name);
		teacher.setSkype_num(skype);
		teacher.setEmail(email);
		teacher.setPhone(tel);
		teacher.setQQ(QQ);
		teacher.setGroupid(groupId);
		teacher.setComment(comment);
		
		user.setUser_name(account);
		user.setUser_pwd(MD5Util.encode32L(password));
		user.setUser_role(Constant.USER_ROLE_TEACHER);
		
		userService.inserRegisterUser(user);
		User user2=userService.queryByName(account);
		teacher.setUser_id(user2.getUser_id());
		teacherService.innerTeacher(teacher);
		
		return model;
		//return new ModelAndView("redirect:/business/teacher/teacherList.do");
	}
	
	
	
	
	
	
	//查到studentList后，点击ID查询该学生主要信息
	@RequestMapping(value="/searchStudentById",method=RequestMethod.GET )
	public ModelAndView seacrchStudent(@RequestParam(value="stu_id")long stu_id,   HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		 Student studentInfo= studentService.queryStudentById(stu_id);
		 mav.addObject("studentInfo", studentInfo);
		 mav.setViewName("/business/teacher/studentInfo"); 
	    return mav;
	}
	
	

	
	//根据不同条件查询，得到学生信息
	@RequestMapping(value="/searchFromTeacher" ,method = RequestMethod.POST)
	public ModelAndView seacrchFromteacher(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		//获得前台的参数
		long stu_id = Integer.parseInt(ParamUtils.getParameter(request,"stu_id","0"));
		int cls_type=Integer.parseInt(ParamUtils.getParameter(request, "cls_type" ,"0"));
		Date cls_begin_time=DateUtil.parseDateDayFormat(ParamUtils.getParameter(request, "cls_begin_time",""));
		String ename= ParamUtils.getParameter(request, "ename","");
	
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
	
		//1、根据ID查询学生信息
		 if(stu_id!=0){
			 Student studentInfo= studentService.queryStudentById(stu_id);
			 
			 mav.addObject("studentInfo", studentInfo);
			 mav.setViewName("/business/teacher/studentInfo"); 
			
		 }
		 
		 
		 //根据英文名查询学生信息，该查询为模糊查询
		 if(stu_id==0&&cls_type==0&&cls_begin_time==null){

				
			 Map<String, Object>  studentInfo=teacherService.queryStudentFromTeacherByEname(ename,start,end);
			 total = (Integer)studentInfo.get("count");
			// 生成分页工具栏
				PageUtil pageUtil = new PageUtil();
				pageUtil.setPageSize(size);
				pageUtil.setCurPage(page);
				pageUtil.setTotalRow(total);
				page = page > pageUtil.getTotalPage() ? 1 : page;
			
			    mav.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
				mav.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
				mav.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
			    mav.addObject("studentInfo", studentInfo.get("data"));
			    mav.setViewName("/business/teacher/studentList"); 
			 
		 }
		 //根据课程类型和时间查询
		 
		 
		 if(stu_id==0&&ename==""&&cls_type!=0&&cls_begin_time!=null){
			 
			 Map<String, Object> studentInfo=teacherService.queryStudentByCLSType(cls_begin_time, cls_type,start,end);
			 total = (Integer)studentInfo.get("count");
				// 生成分页工具栏
					PageUtil pageUtil = new PageUtil();
					pageUtil.setPageSize(size);
					pageUtil.setCurPage(page);
					pageUtil.setTotalRow(total);
					page = page > pageUtil.getTotalPage() ? 1 : page;
					
				    mav.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
					mav.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
					mav.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
				    mav.addObject("studentInfo", studentInfo.get("data"));
				    mav.setViewName("/business/teacher/studentList");  
		 
		 }

		return mav;
	}
	
	
	//根据id 查询老师
	@RequestMapping(value="/selTeacher")
	public ModelAndView queryTeacherById(@RequestParam("id")int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Teacher teacher =teacherService.queryByUserId(id);
		ModelAndView mav = new ModelAndView("/business/teacher/teacherInfo");
		mav.addObject("teacher", teacher); 	
	    return mav;
	}
	
	//根据id 删除老师
	@RequestMapping(value="/deleteTeacherByUserId")
	public ModelAndView deleteTeacherByUserId(@RequestParam("id")Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.deleteTeacherByUserId(id);
		return new ModelAndView("redirect:/business/teacher/teacherList.do");
	}
	
	//更新老师前获取老师信息
	@RequestMapping(value="/updateTeacher")
	public ModelAndView updateTeacher(@RequestParam("id")int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Teacher teacher =teacherService.queryByUserId(id);
		String [] modify_log=new String []{};
		if(null!=teacher.getModify_log()&&!"".equals(teacher.getModify_log())){
		modify_log= teacher.getModify_log().split(",");
		}
		ModelAndView mav = new ModelAndView("/business/teacher/updateTeacher");
		mav.addObject("teacher", teacher); 	
		mav.addObject("modify_log", modify_log); 	
	    return mav;
	}
	
	//更新老师信息
	@RequestMapping(value="/upTeacher")
	public ModelAndView upTeacher( HttpServletRequest request, HttpServletResponse response) throws Exception {

        Long tea_id=ParamUtils.getLongParameter(request, "tea_id", 0);
        Teacher teacher =teacherService.queryById(tea_id);
        
		String name=ParamUtils.getParameter(request, "name","");
		String QQ =ParamUtils.getParameter(request, "QQ","");
		String skype=ParamUtils.getParameter(request, "skype","");
		String email=ParamUtils.getParameter(request, "email","");
		String tel=ParamUtils.getParameter(request, "tel","");
		int groupId=ParamUtils.getIntParameter(request, "groupId",0);
		String password=ParamUtils.getParameter(request, "password","");
		String comment=ParamUtils.getParameter(request, "comment","");
		
		StringBuffer modify_log=new StringBuffer();
		if(!(teacher.getQQ().equals(QQ))){
			modify_log.append("qq");
		}
        if(!teacher.getName().equals(name)){
			modify_log.append(",name");
		}
		if(!teacher.getSkype_num().equals(skype)){
			
			modify_log.append(",skype");
		}
		if(!teacher.getEmail().equals(email)){
			
			modify_log.append(",email");
		}
		if(!teacher.getPhone().equals(tel)){
			
			modify_log.append(",tel");
		}
		if(!(teacher.getGroupid()==groupId)){
			
			modify_log.append(",groupId");
		}
		if(!(teacher.getComment().equals(comment))){
			
			modify_log.append(",comment");
		}
		teacher.setName(name);
		teacher.setSkype_num(skype);
		teacher.setEmail(email);
		teacher.setPhone(tel);
		teacher.setGroupid(groupId);
		teacher.setComment(comment);
		teacher.setQQ(QQ);
		teacher.setModify_log(modify_log.toString());
		teacherService.updateTeacher(teacher);
		return new ModelAndView("redirect:/business/teacher/teacherList.do");
	}
	
	//老师端的学生管理
	@RequestMapping(value = "/queryStudentByTeacher")
	public ModelAndView queryStudent ( HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav= new ModelAndView();
		User loginUser = SessionUtil.getLoginUser(request);
		long userid2=loginUser.getUser_id();
		Teacher te=teacherService.queryByUserId(userid2);
		long teacherID=te.getTea_id();
		System.out.println(te.getTea_id()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~对应的老师名字的id");
	String	tea_name=loginUser.getUser_name();
		System.out.println(tea_name+"~!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~对应的老师名字");
	
		String stu_id=ParamUtils.getParameter(request, "stu_id", "");
		
		String  qq_num=ParamUtils.getParameter(request, "qq_num", "");
		String  skype_num=ParamUtils.getParameter(request, "skype_num", "");
		
		String  ename=ParamUtils.getParameter(request, "ename", "");
		String cls_type=ParamUtils.getParameter(request, "cls_type","0");
		String cls_begin_time=ParamUtils.getParameter(request, "cls_begin_time", "");
		
		
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("stu_id", stu_id);
		searchParam.put("qq_num", qq_num);
		searchParam.put("skype_num",skype_num);
		
		
		searchParam.put("ename",ename);
		searchParam.put("tea_name", tea_name);
		searchParam.put("cls_type", cls_type);
		searchParam.put("cls_begin_time", cls_begin_time);
		System.out.println(cls_begin_time+"传过来的时间");
		
//		if("".equals(stu_id) & "".equals(qq_num) & "".equals(skype_num)){
System.out.println("----------------------queryStu fen ye------------------------");
			
			int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
			int size = Constant.LIST_PAGE_SIZE;//每页多少条
			int total = 0;//总条数
			// 使用SQL语句直接分页查询时使用start和end
			int start = (page - 1) * size;
			int end = start + size;
			Map<String, Object> result=teacherService.queryStudent(start, end, searchParam,teacherID);
			total=(Integer)result.get("count");// 查询得出数据记录数;
			
			if(total<2) {
				if(total==0){
					mav.addObject("studentInfo",null);
					mav.setViewName("/business/teacher/studentInfo");
				}else{
					Iterator it =((List) result.get("data")).iterator();
				while(it.hasNext()){
					Student stu=(Student)it.next();
					mav.addObject("studentInfo",stu);
				}
			     mav.setViewName("/business/teacher/studentInfo");
				}
				return mav;
			}
			
			PageUtil pageUtil = new PageUtil();
			pageUtil.setPageSize(size);
			pageUtil.setCurPage(page);
			pageUtil.setTotalRow(total);
			page = page > pageUtil.getTotalPage() ? 1 : page;
			
			mav.addObject("ename",ename);
			mav.addObject("cls_type",cls_type);
			mav.addObject("cls_begin_time", cls_begin_time);
			mav.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
			mav.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
			mav.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
            mav.addObject("studentInfo",result.get("data"));
			mav.setViewName("/business/teacher/studentList");
//		}else{							
//			List s=(List)teacherService.queryStudent(0, 999999999, searchParam,teacherID).get("data");		
//			if(s.size()==0){
//				mav.addObject("studentInfo",null);
//				mav.setViewName("/business/teacher/studentInfo");
//			}else{
//			Iterator it =s.iterator();
//			while(it.hasNext()){
//				Student stu=(Student)it.next();
//				mav.addObject("studentInfo",stu);
//			}
//		mav.setViewName("/business/teacher/studentInfo");
//		}
//		
//		}	
		return mav;
	}
	
	// 老师端的学生管理
	@RequestMapping(value = "/queryStudentForTeacher")
	public ModelAndView queryStudentForTeacher(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		User loginUser = SessionUtil.getLoginUser(request);
		Teacher teacher = null;
		if (Constant.USER_ROLE_TEACHER == loginUser.getUser_role()) {
			teacher = teacherService.queryByUserId(loginUser.getUser_id());
		} else if (Constant.USER_ROLE_ADMIN == loginUser.getUser_role()) {
			Long teaId = ParamUtils.getLongParameter(request, "teaId", 0);
			if (0 == teaId) {
				throw new Exception("queryStudentForTeacher.do; no teaId");
			}
			teacher = teacherService.queryById(teaId);
		} else {
			throw new Exception("queryStudentForTeacher.do; invalid user:"
					+ loginUser.getUser_id());
		}

		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("teaId", teacher.getTea_id().toString());

		Map<String, Object> result = studentService.queryStudent(0, 10000,
				searchParam);

		mav.addObject("studentInfo", result.get("data"));
		if (teacher.getTea_id() == 24 || teacher.getTea_id() == 24
				|| teacher.getTea_id() == 39 || teacher.getTea_id() == 21
				|| teacher.getTea_id() == 43) {
			mav.setViewName("/business/teacher/myStudentsTest");
		}else{
			mav.setViewName("/business/teacher/myStudents");
		}
		return mav;
	}
	
	//根据学生ID获取学校信息
	@RequestMapping(value = "/queryStuByStuid")
	public ModelAndView queryStuByStuid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav= new ModelAndView();
		String stu_id=ParamUtils.getParameter(request, "stu_id", "");
		 mav.addObject("studentInfo",studentService.queryStudentByStuId(Long.parseLong(stu_id)));
		 mav.setViewName("/business/teacher/studentInfo");
		 return mav;
	}
	
	
}
