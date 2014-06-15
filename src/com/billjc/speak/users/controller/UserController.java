package com.billjc.speak.users.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.billjc.framework.util.Constant;
import com.billjc.framework.util.MD5Util;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.service.UserService;

@Controller
@RequestMapping("/business/users/")
public class UserController {
	final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/users/userList");
		
		
		String search_role = ParamUtils.getParameter(request, "search_role", "");
		String search_name = ParamUtils.getParameterOfDecoder(request, "search_name", "UTF-8", "");
		System.out.println("--"+search_name);
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("search_role", search_role);
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);
		
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
		
		Map<String, Object> result = userService.queryList(start, end,searchParam);
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
		model.addObject("users", result.get("data"));
		
		//查询的参数
		model.addObject("search_role", search_role);
		model.addObject("order_by", order_by);
		model.addObject("search_name", search_name);
		
		return model;
	}
	
	
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = new User();
		String pwd =  ParamUtils.getParameter(request, "pwd", "");
		String name =  ParamUtils.getParameter(request, "name", "");
		user.setUser_name(name);
		user.setUser_pwd(MD5Util.encode32L(pwd));
		
		userService.insertUser(user);
		
		return new ModelAndView("redirect:/business/users/list.do");
	}
	
	
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)
	public @ResponseBody String checkUser(@RequestParam("name")String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "true";
		
		User user = userService.queryByName(name);
		if(null != user){
			result = "false";
		}
		
		return result;
	}
	
	
	/**
	 * 修改管理员密码
	 * @param oldPass
	 * @param newPass
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@RequestParam("oldPass")String oldPass, @RequestParam("newPass")String newPass, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		if(MD5Util.encode32L(oldPass).equals(user.getUser_pwd())){
			if(!oldPass.equals(newPass)){
				int res = userService.updatePwd(user.getUser_id(), MD5Util.encode32L(newPass));
				if(1 == res){
					return "true";
				}else{
					return "操作失败!";
				}
			}else{
				return "新的密码不能与原始密码一致!";
			}
		}else{
			return "原始密码错误!";
		}
	}
	
	@RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
	public  @ResponseBody String deleteUsers(@RequestParam("ids")String ids, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if(userService.deleteUsers(ids)>=1){
			return "true";
		}else{
			return "false";
		}
	}
	
	//根据ID查询管理员个人信息
	@RequestMapping(value="/selUser")
	public ModelAndView selUser(@RequestParam("id")int id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user= userService.queryById(id);
		 ModelAndView mav = new ModelAndView("/business/users/userInfo");
			mav.addObject("user", user); 	
		return mav;
	}
	
	//修改管理员信息
	 @RequestMapping(value="/updateUserInfo" ,method = RequestMethod.POST)
	 public ModelAndView updateUserInfo(User user, @RequestParam("id")String id,@RequestParam("name")String name,@RequestParam("password2")String password2,HttpServletRequest request,HttpServletResponse response)throws Exception{
		 int id2=Integer.parseInt(id);
		 User user2=userService.queryById(id2);
		
		User loginUser = SessionUtil.getLoginUser(request);
		BeanUtils.populate(user, request.getParameterMap());
		 
	      user2.setUser_id(id2);
	      user2.setUser_name(name);
	      user2.setUser_pwd(MD5Util.encode32L(password2));
		  userService.updateUserInfo(user2);

			return new ModelAndView("redirect:/business/users/list.do");

	 }
	 
	//删除单个管理员
	 @RequestMapping(value="/deleteUserById")

	 public  ModelAndView deleteUserById(@RequestParam("id") int id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		 
		
      userService.deleteUserById(id);
      return new ModelAndView("redirect:/business/users/list.do");
	 }
	
	 
	 //判断原始密码是否正确
	 @RequestMapping(value="/checkPassWord" ,method = RequestMethod.POST)	 
	 public @ResponseBody String checkPassWord(@RequestParam("id")String id,@RequestParam("pwd")String pwd,HttpServletRequest request,HttpServletResponse response)throws Exception{
		 String result="false";
		 int id2=Integer.parseInt(id);
		 User user= userService.queryPassWord(id2,pwd);
		
		 if(null!=user){
			 result = "true";
		 }  
		 return result;
	 }
	 
	 
	 //判断学生的原始密码是否正确
	 @RequestMapping(value="/checkStudentPassWord" ,method = RequestMethod.POST)	 
	 public @ResponseBody String checkStudentPassWord(@RequestParam("user_name")String user_name,@RequestParam("pwd")String pwd,HttpServletRequest request,HttpServletResponse response)throws Exception{
		 String result="false";
		 User user= userService.queryStudentOldPassWord(user_name, pwd);
		 
		 if(null!=user){
			 result = "true";
		 }  
		 return result;
	 }
	 
	 
	//修改学生密码
	 @RequestMapping(value="/updateStudentPassword" ,method = RequestMethod.POST)
	 public ModelAndView updateStudentPassword(User user ,@RequestParam(value="user_name")String user_name,@RequestParam(value="confirmpassword") String confirmpassword, HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		
		 User user2=userService.queryByName(user_name);
		
		User loginUser = SessionUtil.getLoginUser(request);
		BeanUtils.populate(user, request.getParameterMap());
		 
	      user2.setUser_id(user2.getUser_id());
	      user2.setUser_name(user_name);
	      user2.setUser_pwd(MD5Util.encode32L(confirmpassword));
		  userService.updateUserInfo(user2);
 
			return new ModelAndView("redirect:/business/students/detail.do?flag=index");
	 }
	 
	 //修改状态
	 @RequestMapping(value="/changeStatus")
	 public ModelAndView changeStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 String id =  ParamUtils.getParameter(request, "id", "0");
		 User user=userService.queryById(Integer.parseInt(id));
		 System.out.println(user.getUser_id());
		 userService.changeStatus(user);

		 return new ModelAndView("redirect:/business/teacher/teacherList.do");
	 }
	 
	 //修改状态
	 @RequestMapping(value="/changeStatus2")
	 public ModelAndView changeStatus2(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 String id =  ParamUtils.getParameter(request, "id", "0");
		 User user=userService.queryById(Integer.parseInt(id));
		 System.out.println(user.getUser_id()+"*******************+++++++++++++++++++++++++++++++++++");
		 userService.changeStatus(user);

		 return new ModelAndView("redirect:/business/users/list.do");
	 }
	 
}
