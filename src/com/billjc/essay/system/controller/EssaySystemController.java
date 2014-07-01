package com.billjc.essay.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.essay.student.entity.EssayStudent;
import com.billjc.essay.student.service.EssayStudentService;
import com.billjc.essay.balance.service.*;
import com.billjc.essay.appointment.*;

@Controller
@RequestMapping(value = "/system/")
public class EssaySystemController {

	
	@Autowired
	EssayStudentService essaystudentservice;
	
	private int studentindex;
	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView CheckLogin(@RequestParam("login")String loginName, 
								   @RequestParam("pwd")String loginPass, 
								   HttpServletRequest request, 
								   HttpServletResponse response) throws Exception{
		
		//Check admin login account
		studentindex = 0;
		int rcdcount = essaystudentservice.queryAdmin( loginName, loginPass);
		
		if(rcdcount == 1){
			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			List<EssayStudent> liststudent = essaystudentservice.Liststudent();
			List<EssayStudent> subliststudent = liststudent.subList(0, 10);
			mav.addObject("studentlist", subliststudent);
			return mav;
		}
		
		else{
			ModelAndView mav = new ModelAndView("logout");
			return mav;
		}
	}
	
	@RequestMapping(value = "/studentfirstpage", method = RequestMethod.GET)
	public ModelAndView StudentFirstPage(HttpServletRequest request, 
			   HttpServletResponse response) throws Exception{
		
		studentindex = 0;
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		List<EssayStudent> subliststudent = liststudent.subList(0, 10);
		mav.addObject("studentlist", subliststudent);
		return mav;
		
	}
	
	@RequestMapping(value = "/studentlastpage", method = RequestMethod.GET)
	public ModelAndView StudentLastPage(HttpServletRequest request, 
			   HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		int index;
		
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		index = liststudent.size();
		studentindex = index / 10 - 1;
		List<EssayStudent> subliststudent = liststudent.subList(index-10, index);
		mav.addObject("studentlist", subliststudent);
		return mav;		
	}
	
	@RequestMapping(value = "/studentpreviouspage", method = RequestMethod.GET)
	public ModelAndView StudentPreviousPage(HttpServletRequest request, 
			   HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		List<EssayStudent> subliststudent;
		studentindex = studentindex - 1 ;
		
		//check if it is first page;
		
		if( studentindex >= 0){
			subliststudent = liststudent.subList(studentindex * 10, (studentindex+1) * 10);
		}
		else{
			studentindex = 0;
			subliststudent = liststudent.subList(studentindex * 10, (studentindex+1) * 10);
		}
		mav.addObject("studentlist", subliststudent);
		return mav;		
	}
	
	@RequestMapping(value = "/studentnextpage", method = RequestMethod.GET)
	public ModelAndView StudentNextPage(HttpServletRequest request, 
			   HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		List<EssayStudent> subliststudent ;
		studentindex = studentindex + 1 ;
		
		// check if it is last page
		if ((studentindex+1) * 10 < liststudent.size() ){
			subliststudent = liststudent.subList(studentindex * 10, (studentindex+1) * 10);
		}
		else{
			subliststudent = liststudent.subList(studentindex * 10, liststudent.size());
		}
		mav.addObject("studentlist", subliststudent);
		return mav;		
	}

	@RequestMapping(value = "/studentessayselect", method = RequestMethod.GET)
	public ModelAndView StudentSelectPage(
			@RequestParam("page")String page,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		
		List<EssayStudent> liststudent = essaystudentservice.Liststudent();
		List<EssayStudent> subliststudent ;
		studentindex = Integer.parseInt(page);
		
		// check if it is last page
		if ((studentindex+1) * 10 < liststudent.size() ){
			subliststudent = liststudent.subList(studentindex * 10, (studentindex+1) * 10);
		}
		else{
			subliststudent = liststudent.subList(studentindex * 10, liststudent.size());
		}
		mav.addObject("studentlist", subliststudent);
		return mav;		
	}
}
