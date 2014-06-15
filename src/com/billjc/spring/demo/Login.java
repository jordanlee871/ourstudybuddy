package com.billjc.spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class Login {
	@RequestMapping("/business/demo/login")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = "login successfully";
		return new ModelAndView("demo", "message", message);
	}
}
