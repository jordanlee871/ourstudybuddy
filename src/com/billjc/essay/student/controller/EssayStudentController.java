package com.billjc.essay.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import com.billjc.essay.student.service.EssayStudentService;
import com.billjc.essay.student.dao.Student;

@Controller
@RequestMapping(value = "/essaystudent")
public class EssayStudentController {
	@Autowired
	EssayStudentService essaystudentservice;
	
	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView CheckLogin(@RequestParam("loginName")String loginName, 
								   @RequestParam("loginPass")String loginPass, 
								   HttpServletRequest request, 
								   HttpServletResponse response) throws Exception{
		
		List<Student> students = essaystudentservice.Liststudent();
		
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		mav.addObject("studentlist", students);
		return mav;
	}
}
