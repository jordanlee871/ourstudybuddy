package com.billjc.essay.system.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Date;
import java.lang.StringBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.billjc.essay.student.entity.EssayStudent;
import com.billjc.essay.student.service.EssayStudentService;
//import com.billjc.essay.balance.service.*;
import com.billjc.essay.appointment.service.*;
import com.billjc.essay.appointment.entity.EssayAppointment;

/*
 * Production page http://www.ourstudybuddy.com/cms/index.php
 * */
@Controller
@RequestMapping(value = "/system/")
public class EssaySystemController {

	@Autowired
	EssayStudentService essaystudentservice;

	@Autowired
	EssayAppointmentService essayApptservice;

	private int studentindex;
	private String loginName;
	private String loginPass;
	private String UserNameType;
	private EssayStudent com_member;

	@RequestMapping(value = "/checkessaylogin", method = RequestMethod.GET)
	public ModelAndView CheckLogin(@RequestParam("login") String loginName,
			@RequestParam("pwd") String loginPass, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Check admin login account
		studentindex = 0;
		this.loginName = loginName;
		this.loginPass = loginPass;
		Map<String, String> hashmap = new HashMap<String, String>();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		List<EssayStudent> ComMemberList = essaystudentservice
				.ListComMemberObject(loginName, loginPass);

		// Load JSP pages base on user type
		if (ComMemberList.size() == 1) {
			// load admin page
			this.com_member = ComMemberList.get(0);
			if (com_member.getIsAdmin().equals("1")) {
				String role = com_member.getRole();
				String role_cn = (String) hashmap.get(role);
				UserNameType = com_member.getName() + "(" + role_cn + ")";

				ModelAndView mav = new ModelAndView("/JSP/sitemap/student_edit");
				mav.addObject("UserNameType", UserNameType);
				mav.addObject("UserType", "system");
				return mav;
			}

			// load other user page (Assistant, Teacher, Student)
			// logout default first
			else {
				ModelAndView mav = new ModelAndView("/JSP/logout");
				return mav;
			}

		}

		// login fail will redirect to logout page
		if (ComMemberList.size() <= 0) {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

		if (ComMemberList.size() > 1) {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

		// Logout no matter what value.
		ModelAndView mav = new ModelAndView("/JSP/logout");
		return mav;
	}

	@RequestMapping(value = "/AddStudent", method = RequestMethod.POST)
	public void StudentEditCreate(@RequestParam("wanwan") String wanwan,
			@RequestParam("qq") String qq, @RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("real_name") String real_name,
			@RequestParam("active") String active, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		EssayStudent new_stu = new EssayStudent();
		Date create_time = new Date();

		new_stu.setWw(wanwan);
		new_stu.setQq(qq);
		new_stu.setEmail(email);
		new_stu.setPhone(phone);
		new_stu.setName("xxx");
		new_stu.setActive(active);
		new_stu.setPassword("123456");
		new_stu.setActive("1");
		new_stu.setRole("Student");
		new_stu.setCreateTime(create_time);
		new_stu.setIsAdmin("");

		int inertRtnCode = essaystudentservice.insertNewStudent(new_stu);

		// Create json string to return.
		String JsonObj = new String();
		/*
		 * if ( 0 == inertRtnCode ){ JsonObj =
		 * "{success:true,msg:\"恭喜你\",url:\"/ourstudybuddy/system/student_Edit.do\"}"
		 * ; } else{ JsonObj =
		 * "{success:false,msg:\"创建失败\",url:\"/ourstudybuddy/system/student_Edit.do\"}"
		 * ; }
		 */
		JsonObj = "{success:true,msg:\"恭喜你\",href:\"/ourstudybuddy/system/student_Edit.do\"}";
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter ReturnString = response.getWriter();
		ReturnString.write(JsonObj.toString());
		ReturnString.close();
	}

	@RequestMapping(value = "/student_Edit", method = RequestMethod.GET)
	public ModelAndView StudentEditPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/setup", method = RequestMethod.GET)
	public ModelAndView SystemSetupPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> hashmap = new HashMap<String, String>();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/password_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	public void SystemPasswordChange(
			@RequestParam("newpwd") String NewPassword,
			@RequestParam("conpwd") String ConfirmNewPassword,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String JsonObj = new String();

		if (NewPassword.equals(ConfirmNewPassword)) {
			essaystudentservice.updatePassword(ConfirmNewPassword,
					String.valueOf(com_member.getStuId()));
			JsonObj = "成功更改密码!";
		}

		else {
			JsonObj = "密码不一致";
		}

		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter ReturnString = response.getWriter();
		ReturnString.write(JsonObj.toString());
		ReturnString.close();
	}

	@RequestMapping(value = "/student_List", method = RequestMethod.GET)
	public ModelAndView StudentFirstPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			// List first 10 member record
			List<EssayStudent> liststudent = essaystudentservice.Liststudent(0);

			int recordCount = essaystudentservice.queryMemberCount();

			int PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;
			studentindex = 0;

			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", "1");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/appointment_List", method = RequestMethod.GET)
	public ModelAndView AppointmentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			ArrayList<String> apptrecordList = new ArrayList<String>();

			long stuid;
			long teacherid;
			String apptid;
			String apptessaytype;
			String apptcycle;
			String teachername;
			String studname;
			String studmail;
			String apptdate;
			StringBuffer HTMLTDRecord = new StringBuffer(
					"<td style=\"padding-left:5px;\" height=\"25\" align=\"center\">###</td>");
			StringBuffer CheckBoxRecord = new StringBuffer(
					"<input type=\"checkbox\" class=\"checkbox\" name=\"check_single\" id=\"check_single\" value=\"###\"/>");
			StringBuffer CancelApptRecord_1 = new StringBuffer(
					"<a href=\"javascript:do_function(\'delete_appointment\',\'###\',\'/ourstudybuddy/system/student_List.do\',\'即将为您取消$$$的一篇%%%作文，请确认\');\">取消预约</a>");
			StringBuffer CancelApptRecord_2 = new StringBuffer(
					"<td style=\"padding-left:5px;\" height=\"25\" align=\"center\">已取消</td>");
			StringBuffer apptrecord = new StringBuffer();
			StringBuffer temp1 = new StringBuffer(), temp2 = new StringBuffer();
			UserNameType = com_member.getName() + "(" + role_cn + ")";
			List<EssayAppointment> essayAppt = this.essayApptservice
					.queryApptObject(0);
			apptrecordList.add("");

			for (int i = 0; i < essayAppt.size(); i++) {

				apptrecord.delete(0, apptrecord.length());
				essayAppt.get(i).getDatetime();

				stuid = essayAppt.get(i).getStuId();
				teacherid = essayAppt.get(i).getTeaId();

				apptid = String.valueOf(essayAppt.get(i).getAppointId());
				apptdate = (new SimpleDateFormat("yyyy-MM-dd"))
						.format(essayAppt.get(i).getDatetime());
				apptessaytype = essayAppt.get(i).getEssayType();
				apptcycle = String.valueOf(essayAppt.get(i).getCycle());
				studname = this.essayApptservice.queryStudentName(stuid);
				studmail = this.essayApptservice.queryStudenteMail(stuid);
				teachername = this.essayApptservice.queryApptTeacher(teacherid);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp1.append(CheckBoxRecord);
				temp1.replace(temp1.indexOf("###"), temp1.indexOf("###") + 3,
						apptid);
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						temp1.toString());
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						apptdate);
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						studname);
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						apptessaytype);
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						apptcycle);
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						studmail + "-" + apptessaytype + "-cycle" + apptcycle
								+ "-" + teachername);
				apptrecord.append(temp2);

				temp1.delete(0, temp1.length());
				temp2.delete(0, temp2.length());
				temp2.append(HTMLTDRecord);
				temp2.replace(temp2.indexOf("###"), temp2.indexOf("###") + 3,
						teachername);
				apptrecord.append(temp2);

				if (0 == essayAppt.get(i).getActive()) {
					apptrecord.append(CancelApptRecord_2);
				} else {
					temp1.delete(0, temp1.length());
					temp2.delete(0, temp2.length());
					temp1.append(CancelApptRecord_1);
					temp2.append(HTMLTDRecord);
					temp1.replace(temp1.indexOf("###"),
							temp1.indexOf("###") + 3, apptid);
					temp1.replace(temp1.indexOf("$$$"),
							temp1.indexOf("$$$") + 3, apptdate);
					temp1.replace(temp1.indexOf("%%%"),
							temp1.indexOf("%%%") + 3, apptessaytype);
					temp2.replace(temp2.indexOf("###"),
							temp2.indexOf("###") + 3, temp1.toString());
					apptrecord.append(temp2);
				}
				apptrecordList.add(apptrecord.toString());
			}

			ModelAndView mav = new ModelAndView("/JSP/sitemap/appointment_list");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("arraysize", String.valueOf(essayAppt.size()));
			mav.addObject("apptrecordList", apptrecordList);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/appointment_Edit", method = RequestMethod.GET)
	public ModelAndView AppointmentEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();

		StringBuffer HTMLTDRecord = new StringBuffer(
				"<a href=\"http://onlineenglishpartner.taobao.com/p/Essay###.htm\" target=\"_blank\">$$$</a>");
		ArrayList<String> teacherList = new ArrayList<String>();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		teacherList.add("");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			List<String> Teachername = essayApptservice.queryTeacherName();

			StringBuffer apptrecord = new StringBuffer();
			for (int i = 0; i < Teachername.size(); i++) {
				apptrecord.delete(0, apptrecord.length());
				apptrecord.append(HTMLTDRecord);
				apptrecord.replace(apptrecord.indexOf("$$$"),
						apptrecord.indexOf("$$$") + 3, Teachername.get(i));
				apptrecord.replace(apptrecord.indexOf("###"),
						apptrecord.indexOf("###") + 3, Teachername.get(i));

				if (apptrecord.indexOf("(中教).") >= 0) {
					apptrecord.replace(apptrecord.indexOf("(中教)."),
							apptrecord.indexOf("(中教).") + 4, "");
				}

				teacherList.add(apptrecord.toString());
			}

			List<Integer> TeacherApptcount = essayApptservice
					.queryTeacherAppt();
			List<String> TeacherId = essayApptservice.queryTeacherId();
			List<String> Teacherday1appt = new ArrayList<String>();
			List<String> Teacherday2appt = new ArrayList<String>();
			List<String> Teacherday3appt = new ArrayList<String>();

			int teacherCount = Teachername.size();

			StringBuffer HTMLApptrecord = new StringBuffer(
					"<a href=\'/ourstudybuddy/system/make_appointment_Edit.do?teacher_id=##&date=%%\'>@@</a>");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			String strtoday = sdf.format(calendar.getTime());

			calendar = Calendar.getInstance();
			calendar.roll(Calendar.DAY_OF_YEAR, 1);
			String strtmr = sdf.format(calendar.getTime());

			calendar = Calendar.getInstance();
			calendar.roll(Calendar.DAY_OF_YEAR, 2);
			String strdayafttmr = sdf.format(calendar.getTime());

			for (int i = 0; i < teacherCount; i++) {
				if (TeacherApptcount.get(i) < 0) {
					Teacherday1appt.add("休假中");
				}
				if (TeacherApptcount.get(i) == 0) {
					Teacherday1appt.add("约满");
				}
				if (TeacherApptcount.get(i) > 0) {
					apptrecord.delete(0, apptrecord.length());
					apptrecord.append(HTMLApptrecord);
					String tempid = TeacherId.get(i % teacherCount);
					apptrecord.replace(apptrecord.indexOf("##"),
							apptrecord.indexOf("##") + 2, tempid);
					apptrecord.replace(apptrecord.indexOf("%%"),
							apptrecord.indexOf("%%") + 2, strtoday);
					apptrecord.replace(apptrecord.indexOf("@@"),
							apptrecord.indexOf("@@") + 2,
							TeacherApptcount.get(i).toString());
					Teacherday1appt.add(apptrecord.toString());
				}
			}

			for (int i = teacherCount; i < teacherCount * 2; i++) {
				if (TeacherApptcount.get(i) < 0) {
					Teacherday2appt.add("休假中");
				}
				if (TeacherApptcount.get(i) == 0) {
					Teacherday2appt.add("约满");
				}
				if (TeacherApptcount.get(i) > 0) {
					apptrecord.delete(0, apptrecord.length());
					apptrecord.append(HTMLApptrecord);
					String tempid = TeacherId.get(i % teacherCount);
					apptrecord.replace(apptrecord.indexOf("##"),
							apptrecord.indexOf("##") + 2, tempid);
					apptrecord.replace(apptrecord.indexOf("%%"),
							apptrecord.indexOf("%%") + 2, strtmr);
					apptrecord.replace(apptrecord.indexOf("@@"),
							apptrecord.indexOf("@@") + 2,
							TeacherApptcount.get(i).toString());
					Teacherday2appt.add(apptrecord.toString());
				}
			}

			for (int i = teacherCount * 2; i < teacherCount * 3; i++) {
				if (TeacherApptcount.get(i) < 0) {
					Teacherday3appt.add("休假中");
				}
				if (TeacherApptcount.get(i) == 0) {
					Teacherday3appt.add("约满");
				}
				if (TeacherApptcount.get(i) > 0) {
					apptrecord.delete(0, apptrecord.length());
					apptrecord.append(HTMLApptrecord);
					String tempid = TeacherId.get(i % teacherCount);
					apptrecord.replace(apptrecord.indexOf("##"),
							apptrecord.indexOf("##") + 2, tempid);
					apptrecord.replace(apptrecord.indexOf("%%"),
							apptrecord.indexOf("%%") + 2, strdayafttmr);
					apptrecord.replace(apptrecord.indexOf("@@"),
							apptrecord.indexOf("@@") + 2,
							TeacherApptcount.get(i).toString());
					Teacherday3appt.add(apptrecord.toString());
				}
			}

			ModelAndView mav = new ModelAndView("/JSP/sitemap/appointment_edit");
			mav.addObject("TeacherList", teacherList);
			mav.addObject("Teacherday1appt", Teacherday1appt);
			mav.addObject("Teacherday2appt", Teacherday2appt);
			mav.addObject("Teacherday3appt", Teacherday3appt);

			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");

			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/teacher_Edit", method = RequestMethod.GET)
	public ModelAndView TeacherEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			StringBuffer HTMLTeacherRecord = new StringBuffer(
					"##<input name=\"teacher_id[]\" value=\"@@\" type=\"hidden\"/>");
			StringBuffer TeacherRecord = new StringBuffer();
			List Teacheredit = essayApptservice.queryTeacherEdit();
			System.out.println( Teacheredit );
			System.out.println( "Teacheredit.size = " + Teacheredit.get(0).getClass() );
/*			List<String> Teacheredit = essayApptservice.queryTeacherEdit();
			int size = Integer.valueOf(Teacheredit.get(Teacheredit.size() - 1));
			List<List<String>> TeachereditARRAY = new ArrayList<List<String>>();

			for (int i = 0; i < size; i++) {
				List<String> List_temp = new ArrayList<String>();
				TeacherRecord.delete(0, TeacherRecord.length());
				TeacherRecord.append(HTMLTeacherRecord);
				String tempid = Teacheredit.get(i);
				String tempname = Teacheredit.get(i + size);
				TeacherRecord.replace(TeacherRecord.indexOf("##"),
						TeacherRecord.indexOf("##") + 2, tempname);
				TeacherRecord.replace(TeacherRecord.indexOf("@@"),
						TeacherRecord.indexOf("@@") + 2, tempid);
				List_temp.add(TeacherRecord.toString());
				List_temp.add(Teacheredit.get(i + size * 2));
				List_temp.add(Teacheredit.get(i + size * 3));
				List_temp.add(Teacheredit.get(i + size * 4));
				TeachereditARRAY.add(List_temp);
			}
*/
			ModelAndView mav = new ModelAndView("/JSP/sitemap/teacher_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
//			mav.addObject("TeachereditARRAY", TeachereditARRAY);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/cancelappointment_List", method = RequestMethod.GET)
	public ModelAndView CancelAppointmentList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView(
					"/JSP/sitemap/cancel_appointment_list");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/charge_edit", method = RequestMethod.GET)
	public ModelAndView ChargeEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/charge_s_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/export_Edit", method = RequestMethod.GET)
	public ModelAndView ExportEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/export_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/assistant_Edit", method = RequestMethod.GET)
	public ModelAndView AssistantEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/assistant_edit");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/studentlastpage", method = RequestMethod.GET)
	public ModelAndView StudentLastPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {

			String role = com_member.getRole();
			String role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			int recordCount = essaystudentservice.queryMemberCount();
			int PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;
			studentindex = (PageCount - 1) * 10;
			List<EssayStudent> liststudent = essaystudentservice
					.Liststudent(studentindex);
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", String.valueOf(PageCount));
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/studentpreviouspage", method = RequestMethod.GET)
	public ModelAndView StudentPreviousPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();
		String checkNo = new String();
		int recordCount;
		int PageCount;

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");
		if (com_member.getIsAdmin().equals("1")) {
			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";
			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			recordCount = essaystudentservice.queryMemberCount();
			PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;

			if (0 < studentindex) {
				checkNo = String.valueOf(studentindex / 10);
				studentindex = studentindex - 10;
			} else {
				studentindex = 0;
				checkNo = String.valueOf(1);
			}

			List<EssayStudent> liststudent = essaystudentservice
					.Liststudent(studentindex);

			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", checkNo);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/studentnextpage", method = RequestMethod.GET)
	public ModelAndView StudentNextPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();
		String checkNo = new String();
		int recordCount;
		int PageCount;

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");
		if (com_member.getIsAdmin().equals("1")) {
			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";
			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			recordCount = essaystudentservice.queryMemberCount();
			PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;

			if ((PageCount - 1) > studentindex / 10) {
				checkNo = String.valueOf(studentindex);
				studentindex = studentindex + 10;
			} else {
				studentindex = (PageCount - 1) * 10;
				checkNo = String.valueOf(PageCount / 10);
				;
			}

			List<EssayStudent> liststudent = essaystudentservice
					.Liststudent(studentindex);

			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", checkNo);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/studentessayselect", method = RequestMethod.GET)
	public ModelAndView StudentSelectPage(@RequestParam("page") String page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();
		String checkNo = new String();
		int recordCount;
		int PageCount;

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");
		if (com_member.getIsAdmin().equals("1")) {
			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";
			ModelAndView mav = new ModelAndView("/JSP/sitemap/student_list");
			recordCount = essaystudentservice.queryMemberCount();
			PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;

			studentindex = (Integer.parseInt(page) - 1) * 10;
			checkNo = page;

			List<EssayStudent> liststudent = essaystudentservice
					.Liststudent(studentindex);

			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", checkNo);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/studentessaydetail", method = RequestMethod.GET)
	public ModelAndView StudentDetailPage(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			ModelAndView mav = new ModelAndView(
					"/JSP/sitemap/student_list_editid");
			List<EssayStudent> liststudent = essaystudentservice
					.ListStudentId(id);
			EssayStudent StudentInfor = liststudent.get(0);
			mav.addObject("StudentInfor", StudentInfor);
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			return mav;
		}

		else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}

	}

	@RequestMapping(value = "/studentessaysearch", method = RequestMethod.POST)
	public ModelAndView StudentSearch(@RequestParam("wanwan") String wanwan,
			@RequestParam("qq") String qq, @RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("date") String date, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();
		int recordCount;
		int PageCount;
		String checkNo = new String();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String Wanwan = new String();
			String Qq = new String();
			String Phone = new String();
			String Email = new String();
			String Date = new String();

			Wanwan = "%" + wanwan + "%";
			Qq = "%" + qq + "%";
			Phone = "%" + phone + "%";
			Email = "%" + email + "%";
			Date = "%" + date + "%";

			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			recordCount = essaystudentservice.queryStudentSearchCount(Wanwan,
					Qq, Phone, Email, Date);
			PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;

			List<EssayStudent> liststudent = essaystudentservice
					.queryStudentSearch(Wanwan, Qq, Phone, Email, Date, 0);
			checkNo = "1";

			ModelAndView mav = new ModelAndView(
					"/JSP/sitemap/student_list_search");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", checkNo);
			mav.addObject("wanwan", Wanwan.replace("%", ""));
			mav.addObject("qq", Qq.replace("%", ""));
			mav.addObject("phone", Phone.replace("%", ""));
			mav.addObject("email", Email.replace("%", ""));
			mav.addObject("date", Date.replace("%", ""));

			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	@RequestMapping(value = "/studentessaysearch2", method = RequestMethod.GET)
	public ModelAndView StudentSearch2(@RequestParam("wanwan") String wanwan,
			@RequestParam("qq") String qq, @RequestParam("phone") String phone,
			@RequestParam("email") String email,
			@RequestParam("date") String date,
			@RequestParam("page") String page, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, String> hashmap = new HashMap<String, String>();
		String role = new String();
		String role_cn = new String();
		int recordCount;
		int PageCount;
		String checkNo = new String();

		hashmap.put("assistant", "助教");
		hashmap.put("teacher", "老师");
		hashmap.put("student", "学生");

		if (com_member.getIsAdmin().equals("1")) {
			String Wanwan = new String();
			String Qq = new String();
			String Phone = new String();
			String Email = new String();
			String Date = new String();
			int indexSQL;

			Wanwan = "%" + wanwan + "%";
			Qq = "%" + qq + "%";
			Phone = "%" + phone + "%";
			Email = "%" + email + "%";
			Date = "%" + date + "%";

			role = com_member.getRole();
			role_cn = (String) hashmap.get(role);
			UserNameType = com_member.getName() + "(" + role_cn + ")";

			recordCount = essaystudentservice.queryStudentSearchCount(Wanwan,
					Qq, Phone, Email, Date);
			PageCount = recordCount % 10 == 0 ? 0 : 1;
			PageCount = recordCount / 10 + PageCount;

			indexSQL = Integer.parseInt(page);
			indexSQL = (indexSQL - 1) * 10;
			checkNo = page;

			if (Integer.parseInt(page) <= 0) {
				System.out.println("2 page = " + page);
				indexSQL = 0;
				checkNo = "1";
			}

			if (Integer.parseInt(page) >= PageCount) {
				indexSQL = (PageCount - 1) * 10;
				checkNo = String.valueOf(PageCount);
			}

			List<EssayStudent> liststudent = essaystudentservice
					.queryStudentSearch(Wanwan, Qq, Phone, Email, Date,
							indexSQL);

			ModelAndView mav = new ModelAndView(
					"/JSP/sitemap/student_list_search");
			mav.addObject("UserNameType", UserNameType);
			mav.addObject("UserNmae", com_member.getName());
			mav.addObject("UserType", "system");
			mav.addObject("studentlist", liststudent);
			mav.addObject("pagecount", String.valueOf(PageCount));
			mav.addObject("checkNo", checkNo);
			mav.addObject("wanwan", Wanwan.replace("%", ""));
			mav.addObject("qq", Qq.replace("%", ""));
			mav.addObject("phone", Phone.replace("%", ""));
			mav.addObject("email", Email.replace("%", ""));
			mav.addObject("date", Date.replace("%", ""));

			return mav;
		} else {
			ModelAndView mav = new ModelAndView("/JSP/logout");
			return mav;
		}
	}

	/*
	 * @RequestMapping(value = "/studentessayback", method = RequestMethod.GET)
	 * public ModelAndView StudentDetailBack( HttpServletRequest request,
	 * HttpServletResponse response ) throws Exception {
	 * 
	 * int rcdcount = essaystudentservice.queryAdmin( loginName, loginPass);
	 * 
	 * if(rcdcount == 1){ ModelAndView mav = new
	 * ModelAndView("/JSP/sitemap/student_list"); List<EssayStudent> liststudent
	 * = essaystudentservice.Liststudent(); List<EssayStudent> subliststudent =
	 * liststudent.subList(0, 10); mav.addObject("studentlist", subliststudent);
	 * return mav; }
	 * 
	 * else{ ModelAndView mav = new ModelAndView("logout"); return mav; } }
	 */
}
