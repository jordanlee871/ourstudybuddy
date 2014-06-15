package com.billjc.speak.system.controller;

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
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.system.entity.SystemSettingBean;
import com.billjc.speak.system.service.SystemSettingService;

@Controller
@RequestMapping("/business/users/")
public class SystemSetingController {
	final Logger logger = LoggerFactory.getLogger(SystemSetingController.class);

	@Autowired
	private SystemSettingService systemSetingService;

	// 获取紧急取消时间
	@RequestMapping(value = "/getSeting")
	public ModelAndView getSeting(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		SystemSetting systemSeting = systemSetingService.getCxtSetting();
		ModelAndView mav = new ModelAndView(
				"/business/users/EMG_CXl_TimeSeting");
		mav.addObject("systemSeting", systemSeting);
		return mav;
	}

	// 修改紧急取消时间
	@RequestMapping(value = "/updateSeting", method = RequestMethod.POST)
	public ModelAndView updateUserInfo(
			@RequestParam("stu_emg_cxl") int stu_emg_cxl,
			@RequestParam("tea_emg_cxl") int tea_emg_cxl,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
					"/resources/common/securityError");
		}

		systemSetingService.updateCxtSetting(stu_emg_cxl,tea_emg_cxl);

		return new ModelAndView("redirect:/business/users/getSeting.do");

	}
}
