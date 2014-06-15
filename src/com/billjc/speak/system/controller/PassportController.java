package com.billjc.speak.system.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.MD5Util;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/passport/")
public class PassportController {
	final Logger logger = LoggerFactory.getLogger(PassportController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private StudentService studentService;
	
	/**
	 * 验证登陆
	 * @param loginName
	 * @param loginPass
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
	public ModelAndView checkLogin(@RequestParam("loginName")String loginName, @RequestParam("loginPass")String loginPass, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = userService.checkLogin(loginName, MD5Util.encode32L(loginPass));
		if (null != user ) {
			//user = null;
			SessionUtil.setLoginUser(request, user);
			//SessionUtil.setLoginUserCompany(request);
			
			
			ModelAndView model=new ModelAndView();
			
			if(user.getUser_role()==1){
				
                if(user.getStatus()==0) {
                	PrintWriter out = response.getWriter();
					out.println("<script language=\"JavaScript\" type=\"text/javascript\">alert('你的账号已被冻结，请在管理员激活后再登录！');window.top.location.href = '"+request.getContextPath()+"/login.jsp'</script>");
					out.close();
					
					 
					 model.setViewName("/login");
                }
                	
                else
				    model=new ModelAndView("redirect:/business/index/adminIndex.jsp");
				
			}
			
			if(user.getUser_role()==2){
				
				if(user.getStatus()==0) {
					
					PrintWriter out = response.getWriter();
					out.println("<script language=\"JavaScript\" type=\"text/javascript\">alert('你的账号已被冻结，请在管理员激活后再登录！');window.top.location.href = '"+request.getContextPath()+"/login.jsp'</script>");
					out.close();
					 model.setViewName("/login");
				} else
				model=new ModelAndView("redirect:/business/index/teacherIndex.jsp");
				
			}
			
			if(user.getUser_role()==3){
				PrintWriter out = response.getWriter();
				out.println("<script language=\"JavaScript\" type=\"text/javascript\">alert('你的账号已被冻结，请找管理员激活后再登录！');window.top.location.href = '"+request.getContextPath()+"/login.jsp'</script>");
				out.close();
				model.setViewName("/login");
			}
			
			return  model; 
		} else {
			return new ModelAndView("redirect:/login.jsp?error=1");
		}
	}
	
	
	/**
	 * 注销登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//注销管理员登录SESSION
		request.getSession().removeAttribute(Constant.LOGIN_USER);
		
		return new ModelAndView("redirect:/business/index/logout.jsp");
	}
	
}
