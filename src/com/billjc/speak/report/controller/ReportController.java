package com.billjc.speak.report.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.framework.ExcelUtil;
import com.billjc.framework.util.Constant;
import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.push.controller.PushController;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.report.entity.FirstClassInfo;
import com.billjc.speak.report.entity.StuClsInfo;
import com.billjc.speak.report.entity.TeaDailyClsInfo;
import com.billjc.speak.report.service.ReportService;

@Controller
@RequestMapping("/business/report/")
public class ReportController {
	final Logger logger = LoggerFactory.getLogger(PushController.class);
	
	private final String mappingURL = "/business/report/";

	@Autowired
	private ReportService reportService;

	private Push push;

	// 获取发送消息的设置
	@RequestMapping(value = "/tea_daily_cls_info")
	public ModelAndView getTeaDailyClsInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		String dateStr = request.getParameter("date");

		List<TeaDailyClsInfo> listDailyInfo = reportService
				.getTeaDailyList(dateStr);

		OutputStream os = null;

		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();

		try {
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
			response.setHeader("Content-disposition",
					"attachment;   filename=\"" + simple.format(new Date())
							+ ".xls\"");
			os = response.getOutputStream();

			// create sheet
			Sheet sheet = wb.createSheet("老师每日上课情况");

			sheet = ExcelUtil.setHeader(wb, sheet, new String[] { "老师名字",
					"试听次数", "试听课时数", "正常课程次数", "正常课程课时数", "空余时间总数(半小时)", "学生紧急取消次数",
					"老师紧急取消次数", });

			// 开始写
			String[] contentSort = new String[] { "getLink", "getTrailTimes",
					"getTrailClass", "getNoTrailTimes", "getNoTrailClass",
					"getFreeClass", "getStuEmCxlTimes", "getTeaEmCxlTimes", };
			for (TeaDailyClsInfo info : listDailyInfo) {
				info.setContentSort(contentSort);
			}
			sheet = ExcelUtil.setContent(wb, sheet, listDailyInfo,
					TeaDailyClsInfo.class);
			wb.write(os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {

			}
		}
		return null;
	}
	
	@RequestMapping(value = "/index")
	public ModelAndView gotoIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		return new ModelAndView(this.mappingURL + "index");
	}
	
	// 获取发送消息的设置
	@RequestMapping(value = "/first_cls_info")
	public ModelAndView getFirstClsInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		String dateStr = request.getParameter("date");

		List<FirstClassInfo> listFirstClassInfo = reportService
				.getFirstClassList(dateStr);

		OutputStream os = null;

		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();

		try {
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
			response.setHeader("Content-disposition",
					"attachment;   filename=\"" + simple.format(new Date())
							+ ".xls\"");
			os = response.getOutputStream();

			// create sheet
			Sheet sheet = wb.createSheet("首节跟进");

			sheet = ExcelUtil.setHeader(wb, sheet, new String[] { "学生",
					"老师", "上课时间", "课程类型",  "上课时长" });

			// 开始写
			String[] contentSort = new String[] { "getStuLink", "getTeaLink",
					"getClsBeginTime", "getClsTypeName", "getClsLength"
			};
			for (FirstClassInfo info : listFirstClassInfo) {
				info.setContentSort(contentSort);
			}
			sheet = ExcelUtil.setContent(wb, sheet, listFirstClassInfo,
					FirstClassInfo.class);
			wb.write(os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {

			}
		}
		return null;
	}
	
	@RequestMapping(value = "/stu_cls_info")
	public ModelAndView getStuClsInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!SessionUtil.hasPurview(request, Constant.USER_ROLE_ADMIN)) {
			return new ModelAndView(
				"/resources/common/securityError");
		}

		String startDate = request.getParameter("start_date");
		String endDate = request.getParameter("end_date");
		String minBalance = request.getParameter("min_balance");
		String times = request.getParameter("times");

		List<StuClsInfo> listStuClsInfo = reportService.getStuClsInfoList(
				startDate, endDate, Integer.parseInt(minBalance), 
				Integer.parseInt(times) );
		OutputStream os = null;

		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();

		try {
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
			response.setHeader("Content-disposition",
					"attachment;   filename=\"" + simple.format(new Date())
							+ ".xls\"");
			os = response.getOutputStream();

			// create sheet
			Sheet sheet = wb.createSheet("学生上课进度跟进");

			sheet = ExcelUtil.setHeader(wb, sheet, new String[] { "学生",
					"上次购买时间", "上次购买课时", "剩余课时",  "上课老师"
					,  "上课时间"});

			// 开始写
			String[] contentSort = new String[] { "getStuLink", "getBlDatetime",
					"getLastBuy", "getBalance", "getTeaLink", "getClsBeginTime"
			};
			for (StuClsInfo info : listStuClsInfo) {
				info.setContentSort(contentSort);
			}
			sheet = ExcelUtil.setContent(wb, sheet, listStuClsInfo,
					StuClsInfo.class);
			wb.write(os);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {

			}
		}
		return null;
	}

}
