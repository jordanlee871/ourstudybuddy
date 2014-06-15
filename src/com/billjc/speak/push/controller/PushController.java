package com.billjc.speak.push.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.billjc.framework.util.ParamUtils;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.notice.entity.Notice;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.push.service.PushService;
import com.billjc.speak.users.entity.User;

@Controller
@RequestMapping("/business/users/")
public class PushController {
	final Logger logger = LoggerFactory.getLogger(PushController.class);

	@Autowired
	private PushService pushService;

	private Push push;

	// 获取发送消息的设置
	@RequestMapping(value = "/getPush")
	public ModelAndView getPush(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		Push push = pushService.getPush();
		ModelAndView mav = new ModelAndView("/business/users/push_setting");
		mav.addObject("push", push);
		List<Notice> listNotice = new ArrayList<Notice>();
		for (int i = 1; i <= push.getListNotice().size(); i++) {
			listNotice.add(push.getListNotice().get(i - 1));
			if (i % 6 == 0) {
				mav.addObject("listNotice" + (i / 6), listNotice);
				listNotice = new ArrayList<Notice>();
			}
		}
		return mav;
	}

	// 修改发送消息的设置
	@RequestMapping(value = "/updatePush", method = RequestMethod.POST)
	public ModelAndView updatePush(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		List<Notice> listNotice = new ArrayList<Notice>();
		for (int i = 1; i <= Constant.NOTICE_CHECK_COUNT; i++) { // 循环所有checkbox
			Notice notice = new Notice();
			notice.setNoticeId(i);
			String noticeOn = request.getParameter("noticeCheck" + i);
			String noticeReOn = request.getParameter("noticeCheckRe" + i);
			notice.setIsSend(noticeOn == null ? 0 : 1);
			if (Integer.valueOf(notice.getIsSend()) == Integer
					.parseInt(noticeReOn)) { // 一样不用update处理
				continue;
			} else {
				listNotice.add(notice);
			}
		}
		Push push = new Push();
		push.setListNotice(listNotice);

		push = (Push) ParamUtils.getClassFromRequest(request, Push.class, push);
		pushService.updatePush(push);

		return new ModelAndView("redirect:/business/users/getPush.do");

	}

	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}

}
