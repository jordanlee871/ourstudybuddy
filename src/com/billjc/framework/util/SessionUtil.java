package com.billjc.framework.util;

import javax.servlet.http.HttpServletRequest;

import com.billjc.speak.users.entity.User;

public class SessionUtil {
	
	/**
	 * 获取SessionId
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request){
		return request.getSession().getId();
	}
	
	
	/**
	 * 设置登录的用户对象
	 * @param request
	 * @param user
	 */
	public static void setLoginUser(HttpServletRequest request, User user){
		request.getSession().setAttribute(Constant.LOGIN_USER, user);
	}
	
	
	/**
	 * 获取登录的用户对象
	 * @param request
	 * @return
	 */
	public static User getLoginUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(Constant.LOGIN_USER);
	}
	
	/**
	 * 获取登录的用户对象
	 * @param request
	 * @return
	 */
	public static boolean hasPurview(HttpServletRequest request, Integer userRole){
		boolean flag = true;
		User user = SessionUtil.getLoginUser(request);
		if(!userRole.equals(user.getUser_role())){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 设置登录用户的组织
	 * @param request
	 *//*
	public static void setLoginUserCompany(HttpServletRequest request){
		request.getSession().setAttribute(Constant.LOGIN_USER_COMPANY, "440100001");
	}
	
	*//**
	 * 获取登录用户的组织
	 * @param request
	 *//*
	public static String getLoginUserCompany(HttpServletRequest request){
		return (String)request.getSession().getAttribute(Constant.LOGIN_USER_COMPANY);
	}*/
}
