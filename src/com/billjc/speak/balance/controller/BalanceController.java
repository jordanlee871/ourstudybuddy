package com.billjc.speak.balance.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.balance.service.BalanceService;
import com.billjc.speak.privateset.service.PrivatesetService;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/balance/")
public class BalanceController {
	final Logger logger = LoggerFactory.getLogger(BalanceController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PrivatesetService privatesetService;
	
	@Autowired
	private BalanceService balanceService;
	
	 //跳转到学生的专属老师列表界面	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView queryRegStuList(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/balance/balanceList");
		String search_name = ParamUtils.getParameterOfDecoder(request, "search_name", "UTF-8", "");
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		String stu_id = ParamUtils.getParameter(request, "stu_id", "");
		
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);
		searchParam.put("stu_id", stu_id);
		
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
		
		Map<String, Object> result = balanceService.queryList(start, end,searchParam);
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
		model.addObject("balances", result.get("data"));
		
		//查询的参数
		model.addObject("order_by", order_by);
		model.addObject("search_name", search_name);
		model.addObject("stu_id", stu_id);
		
		return model;
	}
	
	 //跳转到充值界面
	@RequestMapping(value = "/gotoCharger", method = RequestMethod.GET)
	public ModelAndView chargerForStudent(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/balance/balanceCharger");
		
		Long stuId=Long.parseLong(ParamUtils.getParameter(request, "stuId",""));
		
		Student stu= studentService.queryStudentById(stuId);
		
		model.addObject("student",stu);
		
		
		return model;
	}
	
	
	//充值
	@RequestMapping(value = "/charge", method = RequestMethod.POST)
	public ModelAndView charger(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/balance/result");
		User loginUser = SessionUtil.getLoginUser(request);
		
		Long stuId=Long.parseLong(ParamUtils.getParameter(request, "stuId",""));
		Float blNum=Float.parseFloat(ParamUtils.getParameter(request, "blNum",""));
		String  comment=ParamUtils.getParameter(request, "comment","")	;	
		Student stu= studentService.queryStudentById(stuId);
		
		Balance bl=new Balance();
		
		bl.setBalance_before(stu.getBalance());
		bl.setBlNum(blNum);
		bl.setComment("[" + loginUser.getUser_name() + "(ID:"
				+ loginUser.getUser_id() + ")] [Charge] ["+comment+"]");
		bl.setStuId(stuId);
		
		boolean flag=balanceService.charge(stuId, bl);
		String result ="";
		if(flag){
			
			result="成功";
		}else{
			
			result="失败，余额不足";
		}
		
		model.addObject("student",stu);
		model.addObject("flag",flag);
		model.addObject("result",result);
		model.addObject("stuId",stuId);
		
		
		
		return model;
	}
	
	
	
	
}
