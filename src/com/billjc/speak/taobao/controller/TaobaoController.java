package com.billjc.speak.taobao.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.util.ParamUtils;
import com.billjc.speak.students.service.StudentService;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.system.service.SystemSettingService;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.service.UserService;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;

@Controller
@RequestMapping("/business/taobao/")
public class TaobaoController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private SystemSettingService systemSettingService;

	// 获取淘宝设定
	@RequestMapping(value = "getSetting")
	public ModelAndView getSetting(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("/business/system/taobaoSetting");
		SystemSetting ss = systemSettingService.getTaobaoSetting();

		model.addObject(ss);

		return model;
	}

	// 修改淘宝基本信息
	@RequestMapping(value = "updateAppInfo")
	public ModelAndView updateAppInfo(@RequestParam("appKey") String appKey,
			@RequestParam("appSecret") String appSecret,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView model = new ModelAndView("/business/system/taobaoSetting");
		int returnFlag = systemSettingService.updateTaobaoBasicInfo(appKey,
				appSecret);
		SystemSetting ss = systemSettingService.getTaobaoSetting();

		model.addObject(ss);
		model.addObject("returnFlag", returnFlag);

		return model;
	}

	// 修改产品信息
	@RequestMapping(value = "updateProdInfo")
	public ModelAndView updateProdInfo(
			@RequestParam("speakingIdTaobao") String speakingIdTaobao,
			@RequestParam("essayIdTaobao") String essayIdTaobao,
			@RequestParam("essayPart1SkuTaobao") String essayPart1SkuTaobao,
			@RequestParam("essayPart2SkuTaobao") String essayPart2SkuTaobao,
			@RequestParam("essay7D5ESkuTaobao") String essay7D5ESkuTaobao,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView model = new ModelAndView("/business/system/taobaoSetting");
		int returnFlag = systemSettingService.updateTaobaoProdInfo(
				speakingIdTaobao, essayIdTaobao, essayPart1SkuTaobao,
				essayPart2SkuTaobao,essay7D5ESkuTaobao);
		SystemSetting ss = systemSettingService.getTaobaoSetting();

		model.addObject(ss);
		model.addObject("returnFlag", returnFlag);

		return model;
	}

	// 刷新淘宝授权
	@RequestMapping(value = "refresh")
	public ModelAndView refresh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView(
				"/business/teacher/cancelTeacherList");

		String code = ParamUtils.getParameter(request, "code", "");
		String error = ParamUtils.getParameter(request, "error", "");
		String error_description = ParamUtils.getParameter(request,
				"error_description", "");
		String redirect_uri = "http://"+request.getServerName()+"/business/taobao";

		if ("" == code) {
			throw new Exception("taobao/refresh.do : 刷新淘宝出错. error=" + error
					+ "; error_description:" + error_description);
		}

		try {
			SystemSetting ss = systemSettingService.getTaobaoSetting();
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(" https://oauth.taobao.com/token");
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf8");// 在头文件中设置转码
			NameValuePair[] data = {
					new NameValuePair("client_id", ss.getAppKeyTaobao().getSettingValue()),
					new NameValuePair("client_secret",ss.getAppSecretTaobao().getSettingValue()),
					new NameValuePair("grant_type", "authorization_code"),
					new NameValuePair("code", code),
					new NameValuePair("redirect_uri", redirect_uri) };
			post.setRequestBody(data);
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			String result = new String(post.getResponseBodyAsString().getBytes(
					"utf8"));
			post.releaseConnection();
			int indexTemp = result.indexOf("access_token") + 16;
			String sessionKey = result.substring(indexTemp, indexTemp + 56);
			systemSettingService.updateSessionKey(sessionKey);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	// 获取注销老师列表
	@RequestMapping(value = "testApi")
	public ModelAndView testApi(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView(
				"/business/teacher/cancelTeacherList");

		TradeFullinfoGetResponse tradeRespone = new TradeFullinfoGetResponse();
		return model;
	}

	// 获取注销老师列表
	@RequestMapping(value = "testApiTwo")
	public ModelAndView testApi2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView(
				"/business/teacher/cancelTeacherList");
		SystemSetting ss = systemSettingService.getTaobaoSetting();
		TaobaoClient client = new DefaultTaobaoClient(
				"http://gw.api.taobao.com/router/rest", ss.getAppKeyTaobao().getSettingValue(), ss.getAppSecretTaobao().getSettingValue());
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		req.setFields("buyer_nick");
		req.setTid(469790325019227L);
		TradeFullinfoGetResponse taobaoResponse = client.execute(req,
				ss.getSessionKeyTaobao().getSettingValue());
		System.out.println(taobaoResponse.getTrade().getBuyerNick());
		return model;
	}
}