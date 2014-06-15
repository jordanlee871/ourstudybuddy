package com.billjc.speak.students.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.DateUtil;
import com.billjc.framework.util.MD5Util;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.privateset.service.PrivatesetService;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/students/")
public class StudentController {
	final Logger logger = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private PrivatesetService privatesetService;

	// 新增学生
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	@Transactional
	public ModelAndView addStudent(
			@RequestParam(value = "password") String password,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ww_num = ParamUtils.getParameter(request, "ww_num", "");
		String ename = ParamUtils.getParameter(request, "ename", "");
		String phone = ParamUtils.getParameter(request, "phone", "");
		String qq_num = ParamUtils.getParameter(request, "qq_num", "");
		String skype_num = ParamUtils.getParameter(request, "skype_num", "");
		String email = ParamUtils.getParameter(request, "email", "");
		String remark = ParamUtils.getParameter(request, "remark", "");
		String teacher_remark = ParamUtils.getParameter(request,
				"teacher_remark", "");
		Date register_time = new Date();

		User user = new User();
		user.setUser_name(ww_num);
		user.setUser_pwd(MD5Util.encode32L(password));
		user.setUser_role(Constant.USER_ROLE_STUDENT);
		/*
		 * userService.inserRegisterUser(user);
		 * 
		 * User user2=userService.queryByName(ww_num);
		 */

		Student student = new Student();
		// student.setUser_id(user2.getUser_id());
		student.setEname(ename);
		student.setWw_num(ww_num);
		student.setPhone(phone);
		student.setQq_num(qq_num);
		student.setSkype_num(skype_num);
		student.setEmail(email);
		student.setRegister_time(register_time);

		Long stuId = studentService.insertStudent(student, user, "");
		return new ModelAndView("redirect:/StudentRegistResult.jsp");
	}

	// 助教端操作学生注册
	@RequestMapping(value = "/addStudent2", method = RequestMethod.POST)
	public ModelAndView addStudent2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ww_num = ParamUtils.getParameter(request, "ww_num", "");
		String ename = ParamUtils.getParameter(request, "ename", "");
		String phone = ParamUtils.getParameter(request, "phone", "");
		String qq_num = ParamUtils.getParameter(request, "qq_num", "");
		String skype_num = ParamUtils.getParameter(request, "skype_num", "");
		String autoBalance = ParamUtils.getParameter(request, "auto_balance");
		String email = ParamUtils.getParameter(request, "email", "");
		String remark = ParamUtils.getParameter(request, "remark", "");
		String teacher_remark = ParamUtils.getParameter(request,
				"teacher_remark", "");
		Date register_time = new Date();

		User user = new User();
		user.setUser_name(ww_num);
		user.setUser_pwd(MD5Util.encode32L(phone));
		user.setUser_role(Constant.USER_ROLE_STUDENT);
		/*
		 * userService.inserRegisterUser(user);
		 * 
		 * User user2=userService.queryByName(ww_num);
		 */

		Student student = new Student();
		// student.setUser_id(user2.getUser_id());
		student.setEname(ename);
		student.setWw_num(ww_num);
		student.setPhone(phone);
		student.setQq_num(qq_num);
		student.setSkype_num(skype_num);
		student.setEmail(email);
		student.setRegister_time(register_time);
		student.setStatus(1);
		student.setRemark(remark);
		student.setTeacher_remark(teacher_remark);

		Long stuId = studentService.insertStudent(student, user, autoBalance);
		return new ModelAndView(
				"redirect:/business/privateset/ps_tea_list.do?stuId=" + stuId);
	}

	// 验证旺旺号是否可用

	@RequestMapping(value = "/checkWw_num", method = RequestMethod.POST)
	public @ResponseBody
	String checkWw_num(@RequestParam(value = "ww_num") String ww_num,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "true";
		int count = studentService.checkWw_num(ww_num);
		if (count >= 1) {
			result = "false";// 旺旺号已存在
		}
		return result;
	}

	// 验证旺旺号是否可用

	@RequestMapping(value = "/checkWw_num2", method = RequestMethod.POST)
	public @ResponseBody
	String checkWw_num2(@RequestParam("ww_num") String ww_num,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String result = "true";
		int count = studentService.checkWw_num2(ww_num);
		if (count >= 1) {
			result = "false";// 旺旺号已存在
		}
		return result;
	}

	/**
	 * 验证QQ是否已经注册
	 * 
	 * @return "true" 表示可以用 "false" 表示不可以用 "warn" 表示需要注意
	 */
	@RequestMapping(value = "/checkQqNum", method = RequestMethod.POST)
	public @ResponseBody
	String checkQqNum(@RequestParam(value = "qqNum") String qqNumParm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "false";
		int count = studentService.countStudentByQq(qqNumParm);
		if (count >= 1) {
			result = "warn";// 旺旺号已存在
		} else {
			result = "true";
		}
		return result;
	}

	/**
	 * 验证PHONE是否已经注册
	 * 
	 * @return "true" 表示可以用 "false" 表示不可以用 "warn" 表示需要注意
	 */
	@RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	public @ResponseBody
	String checkPhone(@RequestParam(value = "phone") String phoneParm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "false";
		int count = studentService.countStudentByPhone(phoneParm);
		if (count >= 1) {
			result = "warn";// 旺旺号已存在
		} else {
			result = "true";
		}
		return result;
	}

	/*
	 * //验证手机号码是否可用
	 * 
	 * @RequestMapping(value = "/checkPhone", method = RequestMethod.POST)
	 * public @ResponseBody String checkPhone( @RequestParam("phone")String
	 * phone, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { String result = "true"; int
	 * count=studentService.checkPhone(phone); if(count>=1){ result =
	 * "false";//手机号码已存在 } return result; }
	 */
	/*
	 * //验证邮箱是否可用
	 * 
	 * @RequestMapping(value = "/checkmail", method = RequestMethod.POST) public
	 * 
	 * @ResponseBody String checkMail( @RequestParam("email")String email,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { String result = "true"; int
	 * count=studentService.checkMail(email); if(count>=1){ result =
	 * "false";//该邮箱已存在 } return result; } //验证QQ号是否可用
	 * 
	 * @RequestMapping(value = "/checkqq_num", method = RequestMethod.POST)
	 * public @ResponseBody String checkQq_num( @RequestParam("qq_num")String
	 * qq_num, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { String result = "true"; int
	 * count=studentService.checkQQ_NUM(qq_num); if(count>=1){ result =
	 * "false";//QQ号已存在 } return result; }
	 */
	/*
	 * //验证Skype是否可用
	 * 
	 * @RequestMapping(value = "/checkSkype_num", method = RequestMethod.POST)
	 * public @ResponseBody String checkSkype_num(
	 * 
	 * @RequestParam("skype_num")String skype_num, HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { String result = "true";
	 * int count=studentService.checkSkype_num(skype_num); if(count>=1){ result
	 * = "false";//Skype_num已存在 } return result; }
	 */

	// 验证学生ID是否存在

	@RequestMapping(value = "/check_exist_ww_num", method = RequestMethod.POST)
	public @ResponseBody
	String check_exist_ww_num(@RequestParam("ww_num") String ww_num,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String result = "false";

		Student s = studentService.queryStudentBywwNum(ww_num);
		if (null != s) {
			result = "true";// 旺旺号已存在
		}
		return result;
	}

	@RequestMapping(value = "/check_exist_stu_id", method = RequestMethod.POST)
	public @ResponseBody
	String check_exist_id(@RequestParam("stu_id") String stu_id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String result = "false";

		Student s = studentService.queryStudentByStuId(Long.parseLong(stu_id));
		if (null != s) {
			result = "true";// 旺旺号已存在
		}
		return result;
	}

	// 申请注册学生列表
	@RequestMapping(value = "/RegisterStudentList")
	public ModelAndView queryRegStuList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView(
				"/business/users/StudentRegisterList");
		String search_name = ParamUtils.getParameterOfDecoder(request,
				"search_name", "UTF-8", "");
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);

		int page = Integer.valueOf(ParamUtils
				.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;// 每页多少条
		int total = 0;// 总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;

		Map<String, Object> result = studentService.queryRegisterStudents(
				start, end, searchParam);
		total = (Integer) result.get("count");// 查询得出数据记录数;

		// 生成分页工具栏
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPageSize(size);
		pageUtil.setCurPage(page);
		pageUtil.setTotalRow(total);
		page = page > pageUtil.getTotalPage() ? 1 : page;

		model.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
		model.addObject("toolNav2",
				pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
		model.addObject("startSerial",
				(pageUtil.getCurPage() - 1) * pageUtil.getPageSize());// 起始序号,用于列表序号计算
		model.addObject("regStuList", result.get("data"));

		// 查询的参数
		model.addObject("order_by", order_by);
		model.addObject("search_name", search_name);

		return model;
	}

	// 跳转到激活注册学生账户页面
	@RequestMapping(value = "/eable", method = RequestMethod.GET)
	public ModelAndView eableRegister(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/users/enableStudent");
		long StudentId = Integer.parseInt(ParamUtils.getParameter(request,
				"studentId", ""));
		Student student = studentService.queryStudentById(StudentId);
		model.addObject("student", student);
		return model;
	}

	// 激活注册学生账户
	@RequestMapping(value = "/enableStudent", method = RequestMethod.POST)
	public ModelAndView eableRegisterStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView(
				"redirect:/business/students/RegisterStudentList.do");

		String ename = ParamUtils.getParameter(request, "ename", "");
		String ww_num = ParamUtils.getParameter(request, "ww_num", "");
		String qq_num = ParamUtils.getParameter(request, "qq_num", "");
		String phone = ParamUtils.getParameter(request, "phone", "");
		String skype_num = ParamUtils.getParameter(request, "skype_num", "");
		String remark = ParamUtils.getParameter(request, "remark", "");
		String teacher_remark = ParamUtils.getParameter(request,
				"teacher_remark", "");
		String email = ParamUtils.getParameter(request, "email", "");
		long stuId = ParamUtils.getLongParameter(request, "studentId", 0);

		Student student = new Student();

		student.setStu_id(stuId);
		student.setEmail(email);
		student.setEname(ename);
		student.setQq_num(qq_num);
		student.setWw_num(ww_num);
		student.setRemark(remark);
		student.setTeacher_remark(teacher_remark);
		student.setPhone(phone);
		student.setSkype_num(skype_num);

		// 调用Service方法传入Student;

		studentService.UpdateRegStudent(student);

		return model;
	}

	/*
	 * //搜索学生信息，如果传过来的老师，上课时间为空时，就根据学生ID查找；如果只输入英文名，则按英文名进行查询
	 * 
	 * @RequestMapping(value = "/queryStudentInfo", method = RequestMethod.POST)
	 * public ModelAndView queryStudentInfo (HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { ModelAndView mav= new
	 * ModelAndView(); long stu_id =
	 * Integer.parseInt(ParamUtils.getParameter(request,"stu_id","0"));
	 * 
	 * String ename=ParamUtils.getParameter(request, "ename", ""); String
	 * teacher_name= ParamUtils.getParameter(request, "teacher_name", "");
	 * 
	 * Date
	 * sch_datetime=DateUtil.parseDateDayFormat(ParamUtils.getParameter(request,
	 * "sch_datetime",""));
	 * 
	 * List<Teacher> teacherlist=new ArrayList<Teacher>();;
	 * 
	 * int page = Integer.valueOf(ParamUtils.getParameter(request, "page",
	 * "1")); int size = Constant.LIST_PAGE_SIZE;//每页多少条 int total = 0;//总条数 //
	 * 使用SQL语句直接分页查询时使用start和end int start = (page - 1) * size; int end = start
	 * + size;
	 * 
	 * 
	 * //输入有ID号的，按ID查询 if(stu_id!=0){ Student studentInfo=
	 * studentService.queryStudentById(stu_id); teacherlist =
	 * (List<Teacher>)privatesetService
	 * .queryAllTeachers(studentInfo.getStu_id()).get("c_teachetData");
	 * mav.addObject("studentInfo", studentInfo); mav.addObject("teacherlist",
	 * teacherlist); mav.setViewName("/business/students/studentInfo"); }
	 * 
	 * //只输入老师姓名查到的学生 if(stu_id ==0&&sch_datetime==null&&ename==""){ Teacher
	 * teacher= new Teacher(); teacher=teacherService.queryByName(teacher_name);
	 * long tea_id=teacher.getTea_id(); //根据老师的姓名获得相应的老师ID
	 * 
	 * List<Student> list = studentService.queryStudentListByTeaID(tea_id); int
	 * count= list.size(); mav.addObject("count", count);
	 * mav.addObject("studentInfo", list);
	 * mav.setViewName("/business/students/studentList"); }
	 * //有输入英文名则按英文名查询，没有英文名的按其他输入条件查询（）
	 * 
	 * if(stu_id==0&&ename!=""&&teacher_name==""&&sch_datetime==null||stu_id==0&&
	 * ename!=""&&teacher_name!=""&&sch_datetime!=null){
	 * 
	 * 
	 * Map<String, Object>
	 * studentlistByEname=studentService.queryStudentByEname(ename,start,end);
	 * total = (Integer)studentlistByEname.get("count");
	 * 
	 * PageUtil pageUtil = new PageUtil(); pageUtil.setPageSize(size);
	 * pageUtil.setCurPage(page); pageUtil.setTotalRow(total); page = page >
	 * pageUtil.getTotalPage() ? 1 : page; mav.addObject("toolNav1",
	 * pageUtil.getToolsMenuLotteryStatistics()); mav.addObject("toolNav2",
	 * pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
	 * mav.addObject("startSerial",
	 * (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
	 * mav.addObject("studentInfo", studentlistByEname.get("data"));
	 * mav.setViewName("/business/students/studentList"); }
	 * 
	 * 
	 * //输入老师姓名，课程时间
	 * 
	 * if(stu_id==0&&ename!=""&&teacher_name!=""&&sch_datetime!=null||stu_id==0&&
	 * ename==""&&teacher_name!=""&&sch_datetime!=null){
	 * 
	 * Teacher teacher= new Teacher();
	 * teacher=teacherService.queryByName(teacher_name); long
	 * tea_id=teacher.getTea_id();
	 * 
	 * Map<String, Object> studentListByTNameandTime
	 * =studentService.queryStudentByTimeandTname
	 * (tea_id,sch_datetime,start,end); total =
	 * (Integer)studentListByTNameandTime.get("count"); PageUtil pageUtil = new
	 * PageUtil(); pageUtil.setPageSize(size); pageUtil.setCurPage(page);
	 * pageUtil.setTotalRow(total); page = page > pageUtil.getTotalPage() ? 1 :
	 * page; mav.addObject("toolNav1",
	 * pageUtil.getToolsMenuLotteryStatistics()); mav.addObject("toolNav2",
	 * pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
	 * mav.addObject("startSerial",
	 * (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
	 * mav.addObject("studentInfo", studentListByTNameandTime.get("data"));
	 * mav.setViewName("/business/students/studentList");
	 * 
	 * }
	 * 
	 * if(stu_id==0&&ename==""&&teacher_name==""&&sch_datetime!=null){
	 * Map<String, Object> studentListByTime
	 * =studentService.queryStudentByTime(sch_datetime, start, end); total =
	 * (Integer)studentListByTime.get("count");
	 * 
	 * 
	 * PageUtil pageUtil = new PageUtil(); pageUtil.setPageSize(size);
	 * pageUtil.setCurPage(page); pageUtil.setTotalRow(total); page = page >
	 * pageUtil.getTotalPage() ? 1 : page; mav.addObject("toolNav1",
	 * pageUtil.getToolsMenuLotteryStatistics()); mav.addObject("toolNav2",
	 * pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
	 * mav.addObject("startSerial",
	 * (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
	 * mav.addObject("studentInfo", studentListByTime.get("data"));
	 * mav.setViewName("/business/students/studentList");
	 * 
	 * }
	 * 
	 * 
	 * return mav;
	 * 
	 * 
	 * 
	 * }
	 */

	/**
	 * 助教角色搜索学生
	 */
	@RequestMapping(value = "/queryStudent")
	public ModelAndView queryStudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modeAndView = new ModelAndView();
		String viewEc = ParamUtils.getParameter(request, "viewEC", "");
		String stu_id = ParamUtils.getParameter(request, "stu_id", "");
		String teaId = ParamUtils.getParameter(request, "teaId", "0");
		String ww_num = ParamUtils.getParameter(request, "ww_num", "");
		String qq_num = ParamUtils.getParameter(request, "qq_num", "");
		String skype_num = ParamUtils.getParameter(request, "skype_num", "");
		String phone = ParamUtils.getParameter(request, "phone", "");
		String email = ParamUtils.getParameter(request, "email", "");
		String ename = ParamUtils.getParameter(request, "ename", "");
		String scheduleDate = ParamUtils.getParameter(request, "scheduleDate",
				"");
		String teacher_name = ParamUtils.getParameter(request, "teacher_name",
				"");
		String sch_datetime = ParamUtils.getParameter(request, "sch_datetime",
				"");

		if (!("0".equals(teaId))) {
			String days = ParamUtils.getParameter(request, "days", "");
			if ("4".equals(days)) {
				days = "0";
			}

			String lengthNum = ParamUtils
					.getParameter(request, "lengthNum", "");
			String time = ParamUtils.getParameter(request, "time", "");
			String station = ParamUtils.getParameter(request, "station", "");
			modeAndView.addObject("days", days);
			modeAndView.addObject("time", time);
			modeAndView.addObject("station", station);
			modeAndView.addObject("lengthNum", lengthNum);
			modeAndView.addObject("teaId", teaId);
		}
		// 如果只有老师、时间，其他信息都没有，则跳转去显示这个老师最近的学生记录，并提供搜索。
		if ((null != teaId && !teaId.equals("0"))
				&& (null == stu_id || stu_id.equals("null") || stu_id
						.equals("")) && ww_num.equals("") && qq_num.equals("")
				&& skype_num.equals("") & phone.equals("") && email.equals("")
				&& ename.equals("") && teacher_name.equals("")) {
			List<Student> studentsToday = studentService.queryStudentList(
					new Teacher(teaId), new Date());
			List<Student> studentsYesterday = studentService.queryStudentList(
					new Teacher(teaId), DateUtil.getPlusDays(new Date(), -1));
			List<Student> students2DaysBefore = studentService
					.queryStudentList(new Teacher(teaId),
							DateUtil.getPlusDays(new Date(), -2));
			List<Student> students3DaysBefore = studentService
					.queryStudentList(new Teacher(teaId),
							DateUtil.getPlusDays(new Date(), -3));
			modeAndView.addObject("studentsToday", studentsToday);
			modeAndView.addObject("studentsYesterday", studentsYesterday);
			modeAndView.addObject("students2DaysBefore", students2DaysBefore);
			modeAndView.addObject("students3DaysBefore", students3DaysBefore);
			modeAndView.addObject("sch_datetime", sch_datetime);
			modeAndView.addObject("scheduleDate", scheduleDate);
			modeAndView.addObject("viewEc", viewEc);
			modeAndView.setViewName("/business/students/studentListRecently");
			return modeAndView;
		}

		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("stu_id", stu_id);
		searchParam.put("ww_num", ww_num);
		searchParam.put("qq_num", qq_num);
		searchParam.put("skype_num", skype_num);
		searchParam.put("phone", phone);
		searchParam.put("email", email);

		searchParam.put("ename", ename);
		searchParam.put("teaId", teaId);
		searchParam.put("teacher_name", teacher_name);
		searchParam.put("sch_datetime", sch_datetime);

		int page = Integer.valueOf(ParamUtils
				.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;// 每页多少条
		int total = 0;// 总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;

		Map<String, Object> result = studentService.queryStudent(start, end,
				searchParam);
		total = (Integer) result.get("count");// 查询得出数据记录数;
		if (total < 2) {// 只有0/1条记录的时候
			if (total == 0) {
				modeAndView.addObject("studentInfo", null);
				modeAndView.setViewName("/business/students/studentInfo");
			} else {

				Iterator it = ((List) result.get("data")).iterator();
				while (it.hasNext()) {
					Student stu = (Student) it.next();
					modeAndView.addObject("studentInfo", stu);
					String stu_id22 = stu.getStu_id() + "";
					modeAndView.setViewName("redirect:detail.do?stu_id="
							+ stu_id22 + "");
				}
			}
			return modeAndView;
		}

		// 生成分页工具栏
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPageSize(size);
		pageUtil.setCurPage(page);
		pageUtil.setTotalRow(total);
		page = page > pageUtil.getTotalPage() ? 1 : page;

		modeAndView.addObject("ename", ename);
		modeAndView.addObject("teacher_name", teacher_name);
		modeAndView.addObject("sch_datetime", sch_datetime);
		modeAndView.addObject("scheduleDate", scheduleDate);
		modeAndView.addObject("toolNav1",
				pageUtil.getToolsMenuLotteryStatistics());
		modeAndView.addObject("toolNav2",
				pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
		modeAndView.addObject("startSerial", (pageUtil.getCurPage() - 1)
				* pageUtil.getPageSize());// 起始序号,用于列表序号计算
		modeAndView.addObject("studentInfo", result.get("data"));
		modeAndView.addObject("viewEc", viewEc);
		modeAndView.setViewName("/business/students/studentList");
		return modeAndView;
	}

	/**
	 * 助教、老师统一搜索学生，先匹配stuId、QQ、WW、Skype
	 */
	@RequestMapping(value = "/queryStudentMix", method = RequestMethod.GET)
	public ModelAndView queryStudentMix(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = SessionUtil.getLoginUser(request);
		ModelAndView modeAndView = new ModelAndView();
		String mixId = ParamUtils.getParameter(request, "mixId", null);
		if (null != mixId && 1 < mixId.getBytes("ISO8859-1").length) {
			mixId = new String(mixId.getBytes("ISO8859-1"), "UTF8");
		}
		String teaId = ParamUtils.getParameter(request, "teaId", null);
		List<Student> students = null;
		List<Teacher> teacherlist = null;
		Teacher teacher = null;
		if (Constant.USER_ROLE_TEACHER == user.getUser_role()) {
			teacher = teacherService.queryByUserId(user.getUser_id());
		}

		students = studentService.queryStudentByMixId(mixId,
				Constant.USER_ROLE_ADMIN == user.getUser_role(), teacher);
		if (null != students && 0 < students.size()) {
			teacherlist = (List<Teacher>) privatesetService.queryAllTeachers(
					students.get(0).getStu_id()).get("c_teachetData");
		}

		if (Constant.USER_ROLE_TEACHER == user.getUser_role()) {
			if (null != students && 0 > students.size()) {
				modeAndView.addObject("studentInfo", students.get(0));
				modeAndView.addObject("teacherlist", teacherlist.get(0));
			}
			modeAndView.setViewName("/business/teacher/studentInfo");
		} else if (Constant.USER_ROLE_ADMIN == user.getUser_role()) {
			if (1 == students.size()) {
				modeAndView.addObject("studentInfo", students.get(0));
				modeAndView.addObject("teacherlist", teacherlist);
				modeAndView.setViewName("/business/students/studentInfo");
			} else if(0== students.size()){
				modeAndView.addObject("studentInfo", null);
				modeAndView.addObject("teacherlist", teacherlist);
				modeAndView.setViewName("/business/students/studentInfo");
			}else{
				// 生成分页工具栏
				PageUtil pageUtil = new PageUtil();
				pageUtil.setPageSize(30);
				pageUtil.setCurPage(1);
				pageUtil.setTotalRow(1);

				modeAndView.addObject("toolNav1",
						pageUtil.getToolsMenuLotteryStatistics());
				modeAndView.addObject("toolNav2",
						pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
				modeAndView.addObject("startSerial", (pageUtil.getCurPage() - 1)
						* pageUtil.getPageSize());// 起始序号,用于列表序号计算
				modeAndView.addObject("studentInfo", students);
				modeAndView.setViewName("/business/students/studentList");
			}
		}
		return modeAndView;
	}
	
	/**
	 * 助教用批量搜索学生列表
	 */
	@RequestMapping(value = "/batchQueryStu", method = RequestMethod.POST)
	public ModelAndView batchQueryStu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modeAndView = new ModelAndView();
		String idListString = ParamUtils.getParameter(request, "idList", null);
		String wwListString = ParamUtils.getParameter(request, "wwList", null);
		String qqListString = ParamUtils.getParameter(request, "qqList", null);
		String phoneListString = ParamUtils.getParameter(request, "phoneList",
				null);
		String skypeListString = ParamUtils.getParameter(request, "skypeList",
				null);

		List<Student> students = null;
		List<Teacher> teacherlist = null;
		Teacher teacher = null;

		students = studentService.batchQueryStu(idListString, wwListString,
				qqListString, phoneListString, skypeListString);

		modeAndView.addObject("studentInfo", students);
		modeAndView.setViewName("/business/teacher/myStudentsTest");

		return modeAndView;
	}

	// 在学生列表那里点击datail,查看某个学生的详情
	@RequestMapping(value = "/detail")
	public ModelAndView studentInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long stu_id = Long.parseLong(ParamUtils.getParameter(request, "stu_id",
				"0"));
		int returnFlag = ParamUtils.getIntParameter(request, "returnFlag", 100);
		User user = SessionUtil.getLoginUser(request);
		String flag = ParamUtils.getParameter(request, "flag", "");
		if ("index".equals(flag)) {

			Student stu = studentService.queryStudentBywwNum(user
					.getUser_name());

			stu_id = stu.getStu_id();
		}
		int role = user.getUser_role();

		ModelAndView mav1 = new ModelAndView();
		if (role == Constant.USER_ROLE_ADMIN)
			mav1.addObject("role", 1);
		else if (role == Constant.USER_ROLE_TEACHER)
			mav1.addObject("role", 2);
		else if (role == Constant.USER_ROLE_STUDENT)
			mav1.addObject("role", 3);
		List<Teacher> teacherlist = (List<Teacher>) privatesetService
				.queryAllTeachers(stu_id).get("c_teachetData");
		Student studentInfo = studentService.queryStudentById(stu_id);
		mav1.addObject("studentInfo", studentInfo);
		mav1.addObject("teacherlist", teacherlist);
		mav1.addObject("returnFlag", returnFlag);
		mav1.setViewName("/business/students/studentInfo");

		return mav1;
	}

	// 修改密码前获得账号名称
	@RequestMapping(value = "/getusername")
	public ModelAndView getusername(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		User loginUser = SessionUtil.getLoginUser(request);
		mav.addObject("user", loginUser);
		mav.setViewName("/business/students/ChangePassword");
		return mav;
	}

	// 修改Student info
	@RequestMapping(value = "/updateStudentInfo")
	public ModelAndView updateStudentInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long stu_id = ParamUtils.getLongParameter(request, "stu_id", 0);
		String ename = ParamUtils.getParameter(request, "ename");
		String ww_num = ParamUtils.getParameter(request, "ww_num");
		String qq_num = ParamUtils.getParameter(request, "qq_num");
		String skype_num = ParamUtils.getParameter(request, "skype_num");
		String phone = ParamUtils.getParameter(request, "phone");
		String email = ParamUtils.getParameter(request, "email");
		String notifySMS = ParamUtils.getParameter(request, "notifySMS");
		String notifyMail = ParamUtils.getParameter(request, "notifyMail");

		int returnFlag = 1;// Save Student Info Success!
		Student st = studentService.queryStudentBywwNum(ww_num);
		if (st != null && st.getStu_id() != stu_id) {
			returnFlag = 0;// WW Number exist,pls try other!
		} else {
			studentService.updateStudentInfo(stu_id, ename, ww_num, qq_num,
					skype_num, phone, email, notifySMS, notifyMail);
		}

		ModelAndView mav = new ModelAndView();
		User loginUser = SessionUtil.getLoginUser(request);

		mav.addObject("user", loginUser);
		mav.setViewName("redirect:/business/students/detail.do?stu_id="
				+ stu_id + "&returnFlag=" + returnFlag);
		return mav;
	}

	@RequestMapping(value = "/updateRemark")
	public String updateReamrk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String remark = ParamUtils.getParameter(request, "remark");
		Long stu_id = ParamUtils.getLongParameter(request, "stu_id", 0);

		if (studentService.updateRemark(stu_id, remark) >= 1) {
			return "true";
		} else {
			return "false";
		}

		/*
		 * 
		 * User user=SessionUtil.getLoginUser(request); int role
		 * =user.getUser_role(); ModelAndView mav1= new ModelAndView();
		 * if(role==Constant.USER_ROLE_ADMIN) mav1.addObject("role",1); else
		 * if(role==Constant.USER_ROLE_TEACHER) mav1.addObject("role",2); else
		 * if(role==Constant.USER_ROLE_STUDENT) mav1.addObject("role",3);
		 * Student studentInfo=studentService.queryStudentById(stu_id);
		 * mav1.addObject("studentInfo", studentInfo);
		 * mav1.setViewName("redirect:/business/students/detail.do?stu_id="
		 * +stu_id); return mav1;
		 */

	}

	@RequestMapping(value = "/updateTeacherRemark")
	public String updateTeacherReamrk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String teacherRemark = ParamUtils
				.getParameter(request, "teacherRemark");
		Long stu_id = ParamUtils.getLongParameter(request, "stu_id", 0);

		if (studentService.updateTeacherRemark(stu_id, teacherRemark) >= 1) {
			return "true";
		} else {
			return "false";
		}

		/*
		 * User user=SessionUtil.getLoginUser(request); int role
		 * =user.getUser_role(); ModelAndView mav1= new ModelAndView();
		 * if(role==Constant.USER_ROLE_ADMIN) mav1.addObject("role",1); else
		 * if(role==Constant.USER_ROLE_TEACHER) mav1.addObject("role",2); else
		 * if(role==Constant.USER_ROLE_STUDENT) mav1.addObject("role",3);
		 * Student studentInfo=studentService.queryStudentById(stu_id);
		 * mav1.addObject("studentInfo", studentInfo);
		 * mav1.setViewName("redirect:/business/students/detail.do?stu_id="
		 * +stu_id);
		 * 
		 * return mav1;
		 */
	}

	@RequestMapping(value = "/batUdStuRemark", method = RequestMethod.POST)
	public String batchUpdateStudentRemark(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
		HSSFWorkbook workbook = new HSSFWorkbook(multipartFile.getInputStream());
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rowIte = sheet.iterator();
		while(rowIte.hasNext()){
			HSSFRow row = (HSSFRow)rowIte.next();
			studentService.addRemark(row.getCell(0).toString(),row.getCell(1).toString());
		}
		return null;
	}

}
