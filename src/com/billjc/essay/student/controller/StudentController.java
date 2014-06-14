package com.billjc.essay.student.controller;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.billjc.essay.student.dao.*;
import com.billjc.essay.student.service.*;
import java.util.List;


@Controller
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private StudentService studentservice;
/*	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView demologin() 
	{
		System.out.print("Check login");
		String message = "<br><div align='center'>" + "<h1>Hello World, Spring 3.2.1 Example by Crunchify.com<h1> <br>";
		message += "<a href='http://crunchify.com/category/java-web-development-tutorial/'>More Examples</a>";
		
		List<Student> students = studentservice.listStudents();
		for (Student record : students) {
			System.out.print("ID : " + record.getName() );
			System.out.print(", Name : " + record.getId() );
			System.out.println(", Age : " + record.getPassword());
		}
		
		
		return new ModelAndView("welcome", "message", message);
    }
*/  
	/* demo display database in jsp*/
	
	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView demologin(){		
		ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
		mav.addObject("message","hello JSPEL");
		
		List<Student> students = studentservice.listStudents();
		
		mav.addObject("studentlist", students);
		return mav;
	}
}
