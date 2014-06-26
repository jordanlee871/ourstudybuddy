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
@RequestMapping(value = "/system")
public class EssaySystemController {

	
	@Autowired
	EssayStudentService essaystudentservice;
	
	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView CheckLogin(@RequestParam("login")String loginName, 
								   @RequestParam("pwd")String loginPass, 
								   HttpServletRequest request, 
								   HttpServletResponse response) throws Exception{
		
		int rcdcount = essaystudentservice.queryStudent( loginName, loginPass);
		
		if(rcdcount > 0){
			ModelAndView mav = new ModelAndView("/JSP/sitemap/appointment_edit");
			return mav;
		}
		
		else{
			ModelAndView mav = new ModelAndView("logout");
			return mav;
		}
	}
}
