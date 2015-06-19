package com.billjc.essay.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.essay.student.service.EssayStudentService;
import com.billjc.essay.student.entity.EssayStudent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/essaystudent")
public class EssayStudentController {
	@Autowired
	EssayStudentService essaystudentservice;
	
	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView CheckLogin(@RequestParam("login")String loginName, 
								   @RequestParam("pwd")String loginPass, 
								   HttpServletRequest request, 
								   HttpServletResponse response) throws Exception{
		
/*		int i = essaystudentservice.queryStudent( loginName, loginPass);
		
		System.out.print(i);

		List<EssayStudent> ComMemberList = essaystudentservice.ListComMemberObject(loginName, loginPass);
		
		System.out.println("Error: user not uniq!");
		if ( ComMemberList.size() == 1 ){
			System.out.println(ComMemberList.get(0).getName());
		}
		else{
			System.out.println("Error: user not uniq!");
		}
*/		
		List<EssayStudent> students = essaystudentservice.ListComMemberObject(loginName, loginPass);;		
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		mav.addObject("studentlist", students);	

		return mav;
		

	}
/*	
	public ModelAndView StudentListPage(){
		ModelAndView mav = new ModelAndView("/JSP/sitemap/admin/student_list");
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		List<EssayStudent> subliststudent = liststudent.subList(0, 10);
		mav.addObject("studentlist", subliststudent);
		return mav;
	}
*/
}
