package com.billjc.speak.myClass.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.PageUtil;
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.myClass.entity.MyClass;
import com.billjc.speak.myClass.service.MyClassService;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;

@Controller
@RequestMapping("/business/myClass/")
public class MyClassController {
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private MyClassService myClassService;

	
	
	/*
	 * 
	 * 
	 * 
	 */
	@RequestMapping(value="teacherClsList")
	public ModelAndView teacherClsList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView model = new ModelAndView("/business/myClass/teacherClsList");
		String search_role = ParamUtils.getParameter(request, "search_role", "");
		String search_name = ParamUtils.getParameterOfDecoder(request, "search_name", "UTF-8", "");
		String order_by = ParamUtils.getParameter(request, "order_by", "");
		
        User loginUser = SessionUtil.getLoginUser(request);
		
	    Teacher teacher= (Teacher)teacherService.selectByUid(loginUser.getUser_id()).get(0);
        
		Long tea_id=teacher.getTea_id();
		Map<String, String> searchParam = new HashMap<String, String>();
		searchParam.put("tea_id", tea_id+"");
		searchParam.put("search_role", search_role);
		searchParam.put("search_name", search_name);
		searchParam.put("order_by", order_by);
		
		
		int page = Integer.valueOf(ParamUtils.getParameter(request, "page", "1"));
		int size = Constant.LIST_PAGE_SIZE;//每页多少条
		int total = 0;//总条数
		// 使用SQL语句直接分页查询时使用start和end
		int start = (page - 1) * size;
		int end = start + size;
		
		Map<String, Object> result = myClassService.teacherClsList(start, end,searchParam);

		
		
//		total = (Integer)result.get("count");// 查询得出数据记录数;
		
		// 生成分页工具栏
		PageUtil pageUtil = new PageUtil();
		pageUtil.setPageSize(size);
		pageUtil.setCurPage(page);
		pageUtil.setTotalRow(total);
		page = page > pageUtil.getTotalPage() ? 1 : page;

		model.addObject("toolNav1", pageUtil.getToolsMenuLotteryStatistics());
		model.addObject("toolNav2", pageUtil.getToolsMenuLotteryOperat(request.getContextPath()));
		model.addObject("startSerial", (pageUtil.getCurPage()-1)*pageUtil.getPageSize());//起始序号,用于列表序号计算
		model.addObject("result", result.get("data"));
		
		//查询的参数
		model.addObject("search_role", search_role);
		model.addObject("order_by", order_by);
		model.addObject("search_name", search_name);
		
		return model;
	}
	

}
