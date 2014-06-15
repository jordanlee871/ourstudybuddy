package com.billjc.speak.privateset.controller;

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
import com.billjc.speak.privateset.service.PrivatesetService;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/privateset/")
public class PrivatesetController {
	final Logger logger = LoggerFactory.getLogger(PrivatesetController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PrivatesetService privatesetService;
	
	
	
	 //跳转到学生的专属老师列表界面	
	@RequestMapping(value = "/ps_tea_list", method = RequestMethod.GET)
	public ModelAndView queryRegStuList(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/privateset/p_teacherList");
		
	
		long stuId=Long.parseLong(ParamUtils.getParameter(request, "stuId",""));
				
		
		Map<String, Object> result = privatesetService.queryAllTeachers(stuId);
		
		

		model.addObject("stuId",stuId);
		model.addObject("c_p_teacherList", result.get("c_teachetData"));
		model.addObject("dc_p_teacherList", result.get("dc_teachetData"));
		
	
 
	   return model;
	}
	
	
	
	
	
	
	//添加专属老师关联	
	@RequestMapping(value = "/connect", method = RequestMethod.GET)
	public ModelAndView InsertPrivateset(  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long stuId=Long.parseLong(ParamUtils.getParameter(request, "stuId",""));
		long teaId=Long.parseLong(ParamUtils.getParameter(request, "teaId",""));
		
		privatesetService.insertPrivateset(stuId, teaId);
		ModelAndView model = new ModelAndView("redirect:/business/privateset/ps_tea_list.do?stuId="+stuId);
		return model;
	
	
	}
	
	//中断专属老师关联	
	@RequestMapping(value = "/disconnect", method = RequestMethod.GET)
	public ModelAndView delPrivateset(  HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
		long stuId=Long.parseLong(ParamUtils.getParameter(request, "stuId",""));
		long teaId=Long.parseLong(ParamUtils.getParameter(request, "teaId",""));
		
		privatesetService.delPrivateset(stuId, teaId);
		ModelAndView model = new ModelAndView("redirect:/business/privateset/ps_tea_list.do?stuId="+stuId);
		return model;
	
	
	}
	
	
}
