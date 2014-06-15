package com.billjc.speak.schedule.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.framework.util.Constant;
import com.billjc.framework.util.DateUtil;
import com.billjc.speak.schedule.dao.ScheduleDao;
import com.billjc.speak.schedule.entity.Schedule;
import com.billjc.speak.sk_class.dao.SkClassDao;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.users.entity.User;

@Service
public class ScheduleService {
	final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

	@Autowired
	private ScheduleDao scheduleDao;

	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private SkClassDao skClassDao;

	/**
	 * 获取所有老师的空闲时间
	 * 
	 * @param tea_id
	 * 
	 */
	public Map<String, Object> getfreetime(String realPath, String search_name,
			java.sql.Date search_time, int pageNo, long tea_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date day = new Date();
		int hours = day.getHours();
		int min = day.getMinutes();

		int num = 0;
		if (min < 30) {
			num = (hours) * 2 + 1;
		} else {
			num = hours * 2 + 2;
		}
		if (null != search_time
				&& (day.getYear() != search_time.getYear()
						|| day.getMonth() != search_time.getMonth() || day
						.getDate() != search_time.getDate())) {

			if (day.getTime() > search_time.getTime()) {
				num = 48;
			} else {
				num = 0;
			}
		}
		StringBuffer condition = new StringBuffer();
		List<Teacher> teaList_1 = new ArrayList<Teacher>();

		if (!"".equals(search_name)) {
			condition.append(" AND (R.NAME LIKE '%" + search_name + "%'" + ")");
			teaList_1 = teacherDao.queryTeachers(condition.toString(), pageNo);

		} else if (tea_id > 0) {
			condition.append(" AND (R.TEA_ID = " + tea_id + ")");
			teaList_1 = teacherDao.queryTeachers(condition.toString(), pageNo);
			if (teaList_1 != null && teaList_1.size() > 0) {
				search_name = teaList_1.get(0).getName();
			}
		} else {
			teaList_1 = teacherDao.queryTeachersByPage(pageNo);
		}
		int count = teacherDao.queryAllTeachersNum(condition.toString());
		List<Teacher> teaList = new ArrayList<Teacher>();
		List<Teacher> teaList_3 = new ArrayList<Teacher>();

		// 对老师进行排序 有空闲时间者优先 groupid 优先
		if (search_time == null) {

			for (int i = 0; i < teaList_1.size(); i++) {

				if (scheduleDao.haveFreeTimeOrNot(teaList_1.get(i).getTea_id(),
						num) >= 1) {

					teaList.add(teaList_1.get(i));

				} else {

					teaList_3.add(teaList_1.get(i));
				}

			}

		} else {

			for (int i = 0; i < teaList_1.size(); i++) {

				if (scheduleDao.haveFreeTimeOrNotByTime(teaList_1.get(i)
						.getTea_id(), num, search_time) >= 1) {

					teaList.add(teaList_1.get(i));

				} else {

					teaList_3.add(teaList_1.get(i));
				}

			}
		}

		teaList.addAll(teaList_3);

		// 还没有判断老师数量为空时的情况
		int teaNums = teaList.size();

		// 老师id数组
		long[] teacherIds = new long[teaNums];

		for (int i = 0; i < teacherIds.length; i++) {

			teacherIds[i] = teaList.get(i).getTea_id();
		}

		// 老师姓名数组
		String[] teacherNames = new String[teaNums];

		for (int i = 0; i < teacherNames.length; i++) {

			teacherNames[i] = teaList.get(i).getName();
		}

		String data[][] = new String[48][teaNums];

		for (int a = 0; a < teaNums; a++) {
			for (int b = 0; b < 48; b++) {

				if (search_time == null) {
					data[b][a] = "<td><input type='checkbox' class='groupclass' value='0_"
							+ b
							+ "_"
							+ teacherNames[a]
							+ "_0"
							+ "' style='width: 40px;' name='ft_t_"
							+ a
							+ "'/>close</td>";
				} else {
					data[b][a] = "<td><input type='checkbox' class='groupclass' value='0_"
							+ b
							+ "_"
							+ teacherNames[a]
							+ "_"
							+ search_time
							+ "' style='width: 40px;' name='ft_t_"
							+ a
							+ "'/>close</td>";
				}
			}
		}

		List<List<Schedule>> sdList = new ArrayList<List<Schedule>>();
		StringBuffer condtion_schedule = new StringBuffer();

		if (search_time == null) {
			for (int i = 0; i < teaList.size(); i++) {
				sdList.add(scheduleDao.getMySchedule(
						teaList.get(i).getTea_id(), 0));
			}
		} else {

			condtion_schedule.append(" AND R.SCH_DATETIME = '" + search_time
					+ "'");

			for (int i = 0; i < teaList.size(); i++) {
				sdList.add(scheduleDao.getScheduleByTime(teaList.get(i)
						.getTea_id(), condtion_schedule.toString()));
			}
		}
		for (int i = 0; i < teaNums; i++) {

			for (int k = 0; k < num; k++) {

				data[k][i] = "<td style='background-color:gray;'>close</td>";
				for (int m = 0; m < sdList.get(i).size(); m++) {
					if (sdList.get(i).get(m).getSch_length() == k) {

						if (sdList.get(i).get(m).getStatus() == 0
								|| sdList.get(i).get(m).getStatus() == 1) {
							data[k][i] = "<td style='background-color:gray;'>close</td>";

						} else {

							Student stu = studentDao.queryStudentByStuId(sdList
									.get(i).get(m).getStu_id());

							data[k][i] = "<td style='background-color:gray;font-size: medium;'><a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ sdList.get(i).get(m).getStatus()
									+ "'  >"
									+ stu.getEname() + "<a></td>";
						}
					}
				}

			}

			for (int n = num; n < 48; n++) {
				for (int m = 0; m < sdList.get(i).size(); m++) {
					if (sdList.get(i).get(m).getSch_length() == n) {

						if (sdList.get(i).get(m).getStatus() == 0) {
							if (search_time == null) {
								data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
										+ n
										+ "_"
										+ teacherNames[i]
										+ "_0"
										+ "' style='width: 40px;' name='ft_t_"
										+ i + "' />close</td>";
							} else {
								data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
										+ n
										+ "_"
										+ teacherNames[i]
										+ "_"
										+ search_time
										+ "' style='width: 40px;' name='ft_t_"
										+ i + "' />close</td>";
							}
						} else if (sdList.get(i).get(m).getStatus() == 1) {

							if (search_time == null) {
								data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
										+ n
										+ "_"
										+ teacherNames[i]
										+ "_0"
										+ "' style='width: 40px;' name='ft_t_"
										+ i
										+ "' /><a href='"
										+ realPath
										+ "/business/schedule/gotoBookClass.do?station=1&days=4&flag=1&teaId="
										+ teacherIds[i]
										+ "&lengthNum="
										+ n
										+ "' >free<a></td>";
							} else {
								data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
										+ n
										+ "_"
										+ teacherNames[i]
										+ "_"
										+ search_time
										+ "' style='width: 40px;' name='ft_t_"
										+ i
										+ "' /><a href='"
										+ realPath
										+ "/business/schedule/gotoBookClass.do?station=1&days=4&flag=1&teaId="
										+ teacherIds[i]
										+ "&lengthNum="
										+ n
										+ "&time="
										+ search_time
										+ "' >free<a></td>";
							}
						} else {
							Student stu = studentDao.queryStudentByStuId(sdList
									.get(i).get(m).getStu_id());

							data[n][i] = "<td><a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ sdList.get(i).get(m).getStatus() + "'  >"
									+ stu.getEname() + "<a></td>";
						}
					}
				}
			}

		}
		String[][] data2 = new String[teaNums][49];

		for (int n = 0; n < teaNums; n++) {
			data2[n][0] = teacherNames[n];
		}

		for (int i = 1; i < 49; i++) {

			for (int n = 0; n < teaNums; n++) {

				data2[n][i] = data[i - 1][n];
			}

		}

		result.put("data", data);
		result.put("count", count);
		result.put("tName", teacherNames);

		result.put("search_name", search_name);

		return result;

	}
	
	/**
	 * 获取所有老师的空闲时间
	 * 
	 * @param tea_id
	 * 
	 */
	public Map<String, Object> getfreetimeForAdmin(String realPath, String search_name,
			java.sql.Date search_time, int pageNo, long tea_id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date day = new Date();
		int hours = day.getHours();
		int min = day.getMinutes();

		int num = 0;
		if (min < 30) {
			num = (hours) * 2 + 1;
		} else {
			num = hours * 2 + 2;
		}
		if (null != search_time
				&& (day.getYear() != search_time.getYear()
						|| day.getMonth() != search_time.getMonth() || day
						.getDate() != search_time.getDate())) {

			if (day.getTime() > search_time.getTime()) {
				num = 48;
			} else {
				num = 0;
			}
		}
		StringBuffer condition = new StringBuffer();
		List<Teacher> teaList_1 = new ArrayList<Teacher>();

		if (!"".equals(search_name)) {
			condition.append(" AND (R.NAME LIKE '%" + search_name + "%'" + ")");
			teaList_1 = teacherDao.queryTeachers(condition.toString(), pageNo);

		} else if (tea_id > 0) {
			condition.append(" AND (R.TEA_ID = " + tea_id + ")");
			teaList_1 = teacherDao.queryTeachers(condition.toString(), pageNo);
			if (teaList_1 != null && teaList_1.size() > 0) {
				search_name = teaList_1.get(0).getName();
			}
		} else {
			teaList_1 = teacherDao.queryTeachersByPage(pageNo);
		}
		int count = teacherDao.queryAllTeachersNum(condition.toString());
		List<Teacher> teaList = new ArrayList<Teacher>();
		List<Teacher> teaList_3 = new ArrayList<Teacher>();

		// 对老师进行排序 有空闲时间者优先 groupid 优先
		if (search_time == null) {

			for (int i = 0; i < teaList_1.size(); i++) {

				if (scheduleDao.haveFreeTimeOrNot(teaList_1.get(i).getTea_id(),
						num) >= 1) {

					teaList.add(teaList_1.get(i));

				} else {

					teaList_3.add(teaList_1.get(i));
				}

			}

		} else {

			for (int i = 0; i < teaList_1.size(); i++) {

				if (scheduleDao.haveFreeTimeOrNotByTime(teaList_1.get(i)
						.getTea_id(), num, search_time) >= 1) {

					teaList.add(teaList_1.get(i));

				} else {

					teaList_3.add(teaList_1.get(i));
				}

			}
		}

		teaList.addAll(teaList_3);

		// 还没有判断老师数量为空时的情况
		int teaNums = teaList.size();

		// 老师id数组
		long[] teacherIds = new long[teaNums];

		for (int i = 0; i < teacherIds.length; i++) {

			teacherIds[i] = teaList.get(i).getTea_id();
		}

		// 老师姓名数组
		String[] teacherNames = new String[teaNums];

		for (int i = 0; i < teacherNames.length; i++) {

			teacherNames[i] = teaList.get(i).getName();
		}

		String data[][] = new String[48][teaNums];

		for (int a = 0; a < teaNums; a++) {
			for (int b = 0; b < 48; b++) {

				if (search_time == null) {
					data[b][a] = "<td><input type='checkbox' class='groupclass' value='0_"
							+ b
							+ "_"
							+ teacherNames[a]
							+ "_0"
							+ "' style='width: 40px;' name='ft_t_"
							+ a
							+ "'/>close</td>";
				} else {
					data[b][a] = "<td><input type='checkbox' class='groupclass' value='0_"
							+ b
							+ "_"
							+ teacherNames[a]
							+ "_"
							+ search_time
							+ "' style='width: 40px;' name='ft_t_"
							+ a
							+ "'/>close</td>";
				}
			}
		}

		List<List<Schedule>> sdList = new ArrayList<List<Schedule>>();
		StringBuffer condtion_schedule = new StringBuffer();

		if (search_time == null) {
			for (int i = 0; i < teaList.size(); i++) {
				sdList.add(scheduleDao.getMySchedule(
						teaList.get(i).getTea_id(), 0));
			}
		} else {

			condtion_schedule.append(" AND R.SCH_DATETIME = '" + search_time
					+ "'");

			for (int i = 0; i < teaList.size(); i++) {
				sdList.add(scheduleDao.getScheduleByTime(teaList.get(i)
						.getTea_id(), condtion_schedule.toString()));
			}
		}
		for (int i = 0; i < teaNums; i++) {
			for (int n = 0; n < 48; n++) {
				for (int m = 0; m < sdList.get(i).size(); m++) {
					if (sdList.get(i).get(m).getSch_length() == n) {

						if (sdList.get(i).get(m).getStatus() == 0) {
							data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
									+ n
									+ "_"
									+ teacherNames[i]
									+ "_"
									+ search_time
									+ "' style='width: 40px;' name='ft_t_"
									+ i
									+ "' />close</td>";
						} else if (sdList.get(i).get(m).getStatus() == 1) {
							data[n][i] = "<td><input type='checkbox' class='groupclass' value='1_"
									+ n
									+ "_"
									+ teacherNames[i]
									+ "_"
									+ search_time
									+ "' style='width: 40px;' name='ft_t_"
									+ i
									+ "' /><a href='"
									+ realPath
									+ "/business/schedule/gotoBookClass.do?station=1&days=4&flag=1&teaId="
									+ teacherIds[i]
									+ "&lengthNum="
									+ n
									+ "&scheduleDate="
									+ search_time
									+ "' >free<a></td>";
						} else {
							Student stu = studentDao.queryStudentByStuId(sdList
									.get(i).get(m).getStu_id());

							data[n][i] = "<td><a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ sdList.get(i).get(m).getStatus() + "'  >"
									+ stu.getEname() + "<a></td>";
						}
					}
				}
			}

		}
		String[][] data2 = new String[teaNums][49];

		for (int n = 0; n < teaNums; n++) {
			data2[n][0] = teacherNames[n];
		}

		for (int i = 1; i < 49; i++) {

			for (int n = 0; n < teaNums; n++) {

				data2[n][i] = data[i - 1][n];
			}

		}

		result.put("data", data);
		result.put("count", count);
		result.put("teachers", teaList);

		result.put("search_name", search_name);

		return result;

	}

	/**
	 * 获取可选的课程长度
	 * 
	 * @param lengthNum
	 *            teaId
	 * 
	 * @string[]
	 */
	public String[] checkEnableBookClassLength(long lengthNum, long teaId) {

		int num = 1;

		double init = 0.5;

		String[] result;

		int eachNum = 0;

		if (lengthNum == 45) {

			eachNum = 2;
		} else if (lengthNum == 46) {
			eachNum = 1;
		} else if (lengthNum == 47) {
			eachNum = 1;
		} else {
			eachNum = 3;
		}

		for (int i = 0; i < eachNum; i++) {

			lengthNum++;

			Schedule sh = scheduleDao
					.querySchByLengthAndTeaId(lengthNum, teaId);
			if (sh != null && sh.getStatus() == 1) {

				num++;
			} else {

				break;
			}

		}

		result = new String[num];
		for (int i = 0; i < num; i++) {
			String init_String = init + "";
			result[i] = init_String;
			init = init + 0.5;
		}

		return result;
	}

	/**
	 * 获取个人时间表
	 * 
	 * @param lengthNum
	 *            teaId
	 * 
	 * @string[]
	 */
	public String[] checkEnableBookClassLengthAndDays(long lengthNum,
			long teaId, int days) {

		int num = 1;
		double init = 0.5;
		String[] result;
		int eachNum = 0;
		if (lengthNum == 45) {
			eachNum = 2;
		} else if (lengthNum == 46) {
			eachNum = 1;
		} else if (lengthNum == 47) {
			eachNum = 1;
		} else {
			eachNum = 3;
		}
		for (int i = 0; i < eachNum; i++) {
			lengthNum++;
			Schedule sh = scheduleDao.querySchByLengthAndTeaIdAndDays(
					lengthNum, teaId, days);
			if (sh != null && sh.getStatus() == 1) {
				num++;
			} else {

				break;
			}
		}
		result = new String[num];
		for (int i = 0; i < num; i++) {
			String init_String = init + "";
			result[i] = init_String;
			init = init + 0.5;
		}
		return result;
	}

	/**
	 * 获取个人时间表2
	 * 
	 * @param lengthNum
	 * @param teaId
	 * @param days
	 * @param time
	 * 
	 * @string[]
	 */
	public String[] checkEnableBookClassLengthAndDays2(long lengthNum,
			long teaId, int days, String time) {

		int num = 1;
		double init = 0.5;
		String[] result;
		int eachNum = 0;
		if (lengthNum == 45) {
			eachNum = 2;
		} else if (lengthNum == 46) {
			eachNum = 1;
		} else if (lengthNum == 47) {
			eachNum = 1;
		} else {
			eachNum = 3;
		}
		for (int i = 0; i < eachNum; i++) {
			lengthNum++;
			Schedule sh = scheduleDao.querySchByLengthAndTeaIdAndDays2(
					lengthNum, teaId, time);
			if (sh != null && sh.getStatus() == 1) {
				num++;
			} else {

				break;
			}
		}
		result = new String[num];
		for (int i = 0; i < num; i++) {
			String init_String = init + "";
			result[i] = init_String;
			init = init + 0.5;
		}
		return result;
	}
	
	/**
	 * 获取可以预约的时间
	 * 
	 * @param firstPeriod
	 * @param teacher<Teacher>
	 * @param startDateTime<Date>
	 * 
	 * @string[]
	 */
	public String[] getBookingTime(long firstPeriod,
			Teacher teacher, Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		int num = 1;
		double init = 0.5;
		String[] result;
		int eachNum = 0;
		if (firstPeriod == 45) {
			eachNum = 2;
		} else if (firstPeriod == 46) {
			eachNum = 1;
		} else if (firstPeriod == 47) {
			eachNum = 1;
		} else {
			eachNum = 3;
		}
		for (int i = 0; i < eachNum; i++) {
			firstPeriod++;
			Schedule sh = scheduleDao.querySchByLengthAndTeaIdAndDays2(
					firstPeriod, teacher.getTea_id(), dateFormat.format(date));
			if (sh != null && sh.getStatus() == 1) {
				num++;
			} else {

				break;
			}
		}
		result = new String[num];
		for (int i = 0; i < num; i++) {
			String init_String = init + "";
			result[i] = init_String;
			init = init + 0.5;
		}
		return result;
	}

	/**
	 * 获取老师个人时间表
	 * 
	 * @param Teacher
	 * @param stu_id 
	 * @param realPath 
	 * @param loginUser
	 * 
	 */
	public Map<String, Object> getMySchedule(Teacher t, String stuIdParm,
			String realPath, User loginUser, Date scheduleDate) {
		Map<String, Object> result = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		int role = loginUser.getUser_role();
		
		Date today = new Date();
		int currentPeriod = 0;
		if (new Date().getMinutes() < 30) {
			currentPeriod = (new Date().getHours()) * 2 + 1;
		} else {
			currentPeriod = new Date().getHours() * 2 + 2;
		}
		if (null == stuIdParm || "null".equals(stuIdParm) || "0".equals(stuIdParm)){
			stuIdParm =null;
		}
		
		Long tea_id = t.getTea_id();
		String tea_name = t.getName();
		List<Schedule> listPreviousDay = new ArrayList<Schedule>();
		List<Schedule> listSearchDay = new ArrayList<Schedule>();
		List<Schedule> list2ndDay = new ArrayList<Schedule>();
		List<Schedule> list3rdDay = new ArrayList<Schedule>();
		List<Schedule> list4thDay = new ArrayList<Schedule>();
		List<Schedule> list5thDay = new ArrayList<Schedule>();
		List<Schedule> list6thDay = new ArrayList<Schedule>();
		
		List<Date> scheduleDates = new ArrayList<Date>();
		scheduleDates.add(new Date(scheduleDate.getTime() - 86400000));
		scheduleDates.add(new Date(scheduleDate.getTime()));
		scheduleDates.add(new Date(scheduleDates.get(1).getTime() + 86400000));
		scheduleDates.add(new Date(scheduleDates.get(2).getTime() + 86400000));
		scheduleDates.add(new Date(scheduleDates.get(3).getTime() + 86400000));
		scheduleDates.add(new Date(scheduleDates.get(4).getTime() + 86400000));
		scheduleDates.add(new Date(scheduleDates.get(5).getTime() + 86400000));
		List<String> scheduleDatesYMD = new ArrayList<String>();
		for(int i=0,size=scheduleDates.size(); i<size; i++){
			scheduleDatesYMD.add(dateFormat.format(scheduleDates.get(i)));
		}
		
		listPreviousDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(0));
		listSearchDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(1));
		list2ndDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(2));
		list3rdDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(3));
		list4thDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(4));
		list5thDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(5));
		list6thDay = scheduleDao.getMySchedule(tea_id, scheduleDates.get(6));

		List<List<Schedule>> schedulelist = new ArrayList<List<Schedule>>();

		schedulelist.add(listPreviousDay);
		schedulelist.add(listSearchDay);
		schedulelist.add(list2ndDay);
		schedulelist.add(list3rdDay);
		schedulelist.add(list4thDay);
		schedulelist.add(list5thDay);
		schedulelist.add(list6thDay);

		// result.put("day1", scheduleDao.getMySchedule(id,day));



		int flag=0;
		String data[][] = new String[48][7];

		for (int i = 0; i < 7; i++) {
			for (int n = 0; n < 48; n++) {
				if(role == Constant.USER_ROLE_ADMIN){
					if (isAfterEqual(scheduleDates.get(i),n,today,currentPeriod)) {
						data[n][i] = "<td style='background-color: white;'>";
					} else {
						data[n][i] = "<td style='background-color: rgb(217,217,217);'>";
					}
					data[n][i] += "<input id='"
							+ n
							+ ""
							+ scheduleDatesYMD.get(i)
							+ "' type='checkbox' class='groupclass' name='ft_t_"
							+ scheduleDatesYMD.get(i) + "' value='0_" + n + "_"
							+ tea_name + "_" + scheduleDatesYMD.get(i)
							+ "' style='width: 40px;' />close</td>";
				}else if (role == Constant.USER_ROLE_TEACHER) {
					if (isAfterEqual(scheduleDates.get(i),n,today,currentPeriod)) {
						data[n][i] = "<td style='background-color: white;'><input id='"
								+ n + "" + scheduleDatesYMD.get(i)
								+ "' type='checkbox' class='groupclass' name='ft_t_"
								+ scheduleDatesYMD.get(i)
								+ "' value='0_"
								+ n
								+ "_"
								+ tea_name
								+ "_"
								+ scheduleDatesYMD.get(i)
								+ "' style='width: 40px;' />close</td>";
					} else {// 这个时间已经过去
						data[n][i] = "<td style='background-color: rgb(217,217,217);'>close</td>";
					}
				}
				else if (role == Constant.USER_ROLE_STUDENT) {
					if (isAfterEqual(scheduleDates.get(i),n,today,currentPeriod)) {
						data[n][i] = "<td style='background-color: white;'>close</td>";
					} else {
						data[n][i] = "<td style='background-color: rgb(217,217,217);'>close</td>";
					}
				}
			}

		}

		for (int i=0;i<schedulelist.size();i++){
			for(int n=0; n<schedulelist.get(i).size();n++){
				Schedule schedule=schedulelist.get(i).get(n);
				int periodTemp = schedule.getSch_length();
				if (schedulelist.get(i).get(n).getStatus() == Constant.SCHEDULE_STATUS_CLOSE) {
					//默认情况已经处理好了
				} else if (schedulelist.get(i).get(n).getStatus() == Constant.SCHEDULE_STATUS_FREE) {
					if (isAfterEqual(scheduleDates.get(i),periodTemp,today,currentPeriod)) {
						data[periodTemp][i] = "<td style='background-color: white;'> <input id='"
								+ periodTemp + "" + scheduleDatesYMD.get(i);
						if (role==Constant.USER_ROLE_TEACHER || role ==Constant.USER_ROLE_ADMIN){
							data[periodTemp][i] += "' type='checkbox'";
						}else{
							data[periodTemp][i] += "' type='hidden'";
						}
						data[periodTemp][i] += " class='groupclass' name='ft_t_"
								+ scheduleDatesYMD.get(i)
								+ "' value='"
								+ i
								+ "_"
								+ periodTemp
								+ "_"
								+ tea_name
								+ "_"
								+ scheduleDatesYMD.get(i)
								+ "' style='width: 40px;' />"
								+ "<a href='"
								+ realPath
								+ "/business/schedule/gotoBookClass.do?station=2&teaId="
								+ tea_id + "&lengthNum=" + periodTemp + "&days=" + i
								+ "&scheduleDate=" + scheduleDatesYMD.get(i)
								+ "&stu_id=" + stuIdParm;// 空闲时间片段时，学生ID不为空。// 增加"stu_id=null"
						data[periodTemp][i] += "' >free</a></td>";
					} else {//已经过去的空闲时间
						if (role == Constant.USER_ROLE_ADMIN) {
							data[periodTemp][i] = "<td style='background-color: rgb(217,217,217);'> <input id='"
									+ periodTemp + "" + scheduleDatesYMD.get(i);
							data[periodTemp][i] += "' type='checkbox'";
							data[periodTemp][i] += " class='groupclass' name='ft_t_"
									+ scheduleDatesYMD.get(i)
									+ "' value='"
									+ i
									+ "_"
									+ periodTemp
									+ "_"
									+ tea_name
									+ "_"
									+ scheduleDatesYMD.get(i)
									+ "' style='width: 40px;' />"
									+ "<a href='"
									+ realPath
									+ "/business/schedule/gotoBookClass.do?station=2&teaId="
									+ tea_id
									+ "&lengthNum="
									+ periodTemp
									+ "&days="
									+ i
									+ "&scheduleDate="
									+ scheduleDatesYMD.get(i)
									+ "&stu_id="
									+ stuIdParm;
							if (null == stuIdParm) {// 空闲时间片段时，学生ID不为空。//
														// 增加"stu_id=null"
								data[periodTemp][i] += "&stu_id=null";
							}
							data[periodTemp][i] += "' >free</a></td>";
						}else{//老师、学生直接显示free ，但不能选择
						data[periodTemp][i] = "<td style='background-color: rgb(217,217,217);'>free</td>";
						}
					}
				} else {//课程状态不是free或者close，表示时间已经被人预约了。
					if (role == Constant.USER_ROLE_TEACHER
							|| role == Constant.USER_ROLE_ADMIN) {
						if (isAfterEqual(scheduleDates.get(i), periodTemp,
								today, currentPeriod)) {
							data[schedule.getSch_length()][i] = "<td style='background-color: white;'>"
									+ "<a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ schedulelist.get(i).get(n).getCls()
											.getClsId()
									+ "'>"
									+ schedulelist.get(i).get(n).getStudent()
											.getEname();
						} else {
							data[schedule.getSch_length()][i] = "<td style='background-color: rgb(217,217,217);'>"
									+ "<a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ schedulelist.get(i).get(n).getCls()
											.getClsId()
									+ "'>"
									+ schedulelist.get(i).get(n).getStudent()
											.getEname();
							if (Constant.CLASS_STATUS_PENDING == schedulelist
									.get(i).get(n).getCls().getClsStatus()) {
								data[schedule.getSch_length()][i] += "<font style='color:red'>&lt;P&gt;</font>";
							}
						}
						data[schedule.getSch_length()][i] += "</a> </td>";
					} else if (role == Constant.USER_ROLE_STUDENT) {
						Student stu = studentDao.queryStudentById(schedule.getStu_id());
						Long stu_user_id = stu.getUser_id();
						String name = stu.getEname();
						if (stu_user_id == loginUser.getUser_id()) {
							if (isAfterEqual(scheduleDates.get(i),periodTemp,today,currentPeriod)) {
								data[schedule.getSch_length()][i] = "<td style='background-color: white;'>";
							} else {
								data[schedule.getSch_length()][i] = "<td style='background-color: rgb(217,217,217);'>";
							}
							data[schedule.getSch_length()][i]  +="<a href='"
									+ realPath
									+ "/business/sk_class/gotoClassInfo.do?clsId="
									+ schedule.getStatus()
									+ "'>" + name + "</a> </td>";
						} else {
							if (isAfterEqual(scheduleDates.get(i),periodTemp,today,currentPeriod)) {
								data[schedule.getSch_length()][i] = "<td style='background-color: white;'>";
							} else {
								data[schedule.getSch_length()][i] = "<td style='background-color: rgb(217,217,217);'>";
							}
							data[schedule.getSch_length()][i] += "close</td>";
						}
					}
				}
			}
		}
		/*for (int i = 0; i < 7; i++) {
			//这个时间是否已经过去
			boolean dateIsBefore = scheduleDates.get(i).before(new Date());
			for (int n = 0; n < 48; n++) {
				for (int m = 0; m < schedulelist.get(i).size(); m++) {
					String idd = n + "" + scheduleDatesYMD.get(i);
					// 这个就是我们要的。
					if (schedulelist.get(i).get(m).getSch_length() == n) {
						if (schedulelist.get(i).get(m).getStatus() == Constant.SCHEDULE_STATUS_CLOSE) {
							if (role == Constant.USER_ROLE_TEACHER
									|| role == Constant.USER_ROLE_ADMIN) {
								//如果是老师，并且时间已经过去，则单纯显示close并标灰
								if(Constant.USER_ROLE_TEACHER == role && (scheduleDates.get(i).after(today) || (DateUtil.isSameDay(scheduleDates.get(i),today)&&n >= schedulePeriod))){
									data[n][i] = "<td style='background-color: rgb(217,217,217);'>close</td>";
								} else {//老师查看将来的时间，或者助教看所有close
									data[n][i] = "<td style='background-color: white;'><input id='"
											+ idd
											+ "' type='checkbox' class='groupclass' name='ft_t_"
											+ scheduleDatesYMD.get(i)
											+ "' value='"
											+ i
											+ "_"
											+ n
											+ "_"
											+ tea_name
											+ "_"
											+ scheduleDatesYMD.get(i)
											+ "' style='width: 40px;' />close</td>";
								}
							} else if (role == Constant.USER_ROLE_STUDENT) {
								if (dateIsBefore) {
									data[n][i] = "<td style='background-color: rgb(217,217,217);'>close</td>";
								} else {
									data[n][i] = "<td style='background-color: white;'>close</td>";
								}
							}
						} else if (schedulelist.get(i).get(m).getStatus() == Constant.SCHEDULE_STATUS_FREE) {
							if (!("0".equals(stuIdParm))) {
								if (dateIsBefore) {
									data[n][i] = "<td style='background-color: rgb(217,217,217);'> <input id='"
											+ idd;
								} else {
									data[n][i] = "<td style='background-color: white;'> <input id='"
											+ idd;
								}
								// 学生
								if (role == Constant.USER_ROLE_STUDENT) {
									data[n][i] += "' type='hidden'";
								} else {// 老师、助教
									data[n][i] += "' type='checkbox'";
								}
								data[n][i] += " class='groupclass' name='ft_t_"
										+ scheduleDatesYMD.get(i)
										+ "' value='"
										+ i
										+ "_"
										+ n
										+ "_"
										+ tea_name
										+ "_"
										+ scheduleDatesYMD.get(i)
										+ "' style='width: 40px;' />"
										+ "<a href='"
										+ realPath
										+ "/business/schedule/gotoBookClass.do?station=2&teaId="
										+ tea_id + "&lengthNum=" + n + "&days="
										+ i + "&scheduleDate="
										+ scheduleDatesYMD.get(i) + "&stu_id="
										+ stuIdParm + "' >free</a></td>";
							} else {//空闲时间片段时，学生ID不为空。 增加"stu_id=null"
								data[n][i] = "<td style='background-color: white;'><input id='"
										+ idd
										+ "' type='checkbox' class='groupclass' name='ft_t_"
										+ scheduleDatesYMD.get(i)
										+ "' value='"
										+ i
										+ "_"
										+ n
										+ "_"
										+ tea_name
										+ "_"
										+ scheduleDatesYMD.get(i)
										+ "' style='width: 40px;' />"
										+ "<a href='"
										+ realPath
										+ "/business/schedule/gotoBookClass.do?station=3&teaId="
										+ tea_id
										+ "&stu_id=null&lengthNum="
										+ n
										+ "&days="
										+ i
										+ "&scheduleDate="
										+ scheduleDatesYMD.get(i)
										+ "' >free</a> </td>";
							}
						} else {//课程状态不为空，表示时间已经被人预约了。
							if (role == Constant.USER_ROLE_TEACHER
									|| role == Constant.USER_ROLE_ADMIN) {
								Long stu_id = schedulelist.get(i).get(m)
										.getStu_id();
								Student stu = studentDao
										.queryStudentById(stu_id);
								String name = stu.getEname();
								data[n][i] = "<td style='background-color: white;'><a href='"
										+ realPath
										+ "/business/sk_class/gotoClassInfo.do?clsId="
										+ schedulelist.get(i).get(m)
												.getStatus()
										+ "'>"
										+ name
										+ "</a> </td>";
							} else if (role == Constant.USER_ROLE_STUDENT) {
								Long stu_id = schedulelist.get(i).get(m)
										.getStu_id();
								Student stu = studentDao
										.queryStudentById(stu_id);
								Long stu_user_id = stu.getUser_id();
								String name = stu.getEname();
								if (stu_user_id == loginUser.getUser_id()){
									data[n][i] = "<td style='background-color: white;'><a href='"
											+ realPath
											+ "/business/sk_class/gotoClassInfo.do?clsId="
											+ schedulelist.get(i).get(m)
													.getStatus()
											+ "'>"
											+ name
											+ "</a> </td>";
								}else{
									data[n][i] = "<td style='background-color: white;'>close</td>";
								}
							}
						}
					}
				}
			}
		}*/
		result.put("data", data);
		return result;
	}
	
/*	*//**
	 * 获取老师个人时间表
	 * 
	 * @param Teacher
	 *            t @param stu_id @param realPath @param loginUser
	 * 
	 *//*
	public Map<String, Object> getMyScheduleForAdmin(Teacher t, String getStu_id,
			String realPath, User loginUser) {
		Map<String, Object> result = new HashMap<String, Object>();

		int role = loginUser.getUser_role();
		Date day = new Date();
		int hours = day.getHours();
		int min = day.getMinutes();
		Long tea_id = t.getTea_id();
		String tea_name = t.getName();
		List<Schedule> list = new ArrayList();
		List<Schedule> list1 = new ArrayList();
		List<Schedule> list2 = new ArrayList();
		List<Schedule> list3 = new ArrayList();
		list = scheduleDao.getMySchedule(tea_id, 0);
		list1 = scheduleDao.getMySchedule(tea_id, 1);
		list2 = scheduleDao.getMySchedule(tea_id, 2);
		list3 = scheduleDao.getMySchedule(tea_id, 3);

		List<List<Schedule>> schedulelist = new ArrayList<List<Schedule>>();

		schedulelist.add(list);
		schedulelist.add(list1);
		schedulelist.add(list2);
		schedulelist.add(list3);

		// result.put("day1", scheduleDao.getMySchedule(id,day));

		int num = 0;
		if (min < 30) {
			num = (hours) * 2 + 1;
		} else {
			num = hours * 2 + 2;
		}

		int flag=0;
		String data[][] = new String[48][4];

		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 48; b++) {
				String idd = b + "" + a;
				if (role == Constant.USER_ROLE_TEACHER
						|| role == Constant.USER_ROLE_ADMIN)
					data[b][a] = "<td style='background-color: white;'><input id='"
							+ idd
							+ "' type='checkbox' class='groupclass' name='ft_t_"
							+ a
							+ "' value='0_"
							+ b
							+ "_"
							+ tea_name
							+ "_"
							+ a
							+ "' style='width: 40px;' />close</td>";
				else if (role == Constant.USER_ROLE_STUDENT)
					data[b][a] = "<td style='background-color: white;'>close</td>";
			}

		}

		for (int i = 0; i < 4; i++) {
			// for (int j = 0; j < 48; j++) {
			if (i == 0) {
//				for (int k = 0; k < num; k++) {
//					// data[k][i] ="<td style='background-color:gray;'>close</td>";
//					if(schedulelist.get(i).size()>0) {
//						for (int m = 0; m < schedulelist.get(i).size(); m++) {
//							if (schedulelist.get(i).get(m).getSch_length() == k) {
//								// String idd=k+""+i;
//								if (schedulelist.get(i).get(m).getStatus() == 0) {
//									if (role == Constant.USER_ROLE_TEACHER
//											|| role == Constant.USER_ROLE_ADMIN)
//										data[k][i] = "<td style='background-color:gray;'>close</td>";
//									else if (role == Constant.USER_ROLE_STUDENT)
//										data[k][i] = "<td style='background-color:gray;'>close</td>";
//								} else if (schedulelist.get(i).get(m).getStatus() == 1) {
//
//									if (!("0".equals(getStu_id))) {
//
//										if (role == Constant.USER_ROLE_STUDENT) {
//											data[k][i] = "<td style='background-color:gray;'>close</td>";
//										} else {
//											data[k][i] = "<td style='background-color:gray;'>close</td>";
//										}
//
//									}
//
//									else {
//
//										data[k][i] = "<td style='background-color:gray;'>close</td>";
//
//									}
//								}
//
//								else {
//
//									if (role == Constant.USER_ROLE_TEACHER
//											|| role == Constant.USER_ROLE_ADMIN) {
//										Long stu_id = schedulelist.get(i).get(m)
//												.getStu_id();
//										Student stu = studentDao
//												.queryStudentById(stu_id);
//
//										String name = stu.getEname();
//										flag=1;
//
//										data[k][i] = "<td style='background-color:gray;'><a href='"
//												+ realPath
//												+ "/business/sk_class/gotoClassInfo.do?clsId="
//												+ schedulelist.get(i).get(m)
//														.getStatus()
//												+ "'>"
//												+ name
//												+ "</a> </td>";
//										break;
//
//									} else if (role == Constant.USER_ROLE_STUDENT) {
//										Long stu_id = schedulelist.get(i).get(m)
//												.getStu_id();
//										Student stu = studentDao
//												.queryStudentById(stu_id);
//										Long stu_user_id = stu.getUser_id();
//										String name = stu.getEname();
//										flag=1;
//										if (stu_user_id == loginUser.getUser_id())
//											data[k][i] = "<td style='background-color:gray;'><a href='"
//													+ realPath
//													+ "/business/sk_class/gotoClassInfo.do?clsId="
//													+ schedulelist.get(i).get(m)
//															.getStatus()
//													+ "'>"
//													+ name + "</a> </td>";
//										else
//											data[k][i] = "<td style='background-color:gray;'>close</td>";
//										break;
//
//									}
//
//								}
//							}
//							else {
////								if(flag==0)
//									data[k][i] = "<td style='background-color:gray;'>close</td>";
//								
//							}
//						}// end for i==0 gray part
//					}
//					else {
//						data[k][i] ="<td style='background-color:gray;'>close</td>";
//					}
//					
//				}
				
				
				for (int n = 0; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {
						if (schedulelist.get(i).get(m).getSch_length() == n) {
							String idd = n + "" + i;
							if (schedulelist.get(i).get(m).getStatus() == 0) {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN)
									data[n][i] = "<td style='background-color: white;'><input id='"
											+ idd
											+ "' type='checkbox' class='groupclass' name='ft_t_"
											+ i
											+ "' value='1_"
											+ n
											+ "_"
											+ tea_name
											+ "_"
											+ i
											+ "' style='width: 40px;' />close</td>";
								else if (role == Constant.USER_ROLE_STUDENT)
									data[n][i] = "<td style='background-color: white;'>close</td>";
							} else if (schedulelist.get(i).get(m).getStatus() == 1) {

								if (!("0".equals(getStu_id))) {

									if (role == Constant.USER_ROLE_STUDENT) {
										data[n][i] = "<td style='background-color: white;'> <input id='"
												+ idd
												+ "' type='hidden' class='groupclass' name='ft_t_"
												+ i
												+ "' value='"
												+ i
												+ "_"
												+ n
												+ "_"
												+ tea_name
												+ "_"
												+ i
												+ "' style='width: 40px;' />"
												+ "<a href='"
												+ realPath
												+ "/business/schedule/gotoBookClass.do?station=2&teaId="
												+ tea_id
												+ "&lengthNum="
												+ n
												+ "&days="
												+ i
												+ "&stu_id="
												+ getStu_id
												+ "' >free</a></td>";
									} else {
										data[n][i] = "<td style='background-color: white;'> <input id='"
												+ idd
												+ "' type='checkbox' class='groupclass' name='ft_t_"
												+ i
												+ "' value='"
												+ i
												+ "_"
												+ n
												+ "_"
												+ tea_name
												+ "_"
												+ i
												+ "' style='width: 40px;' />"
												+ "<a href='"
												+ realPath
												+ "/business/schedule/gotoBookClass.do?station=2&teaId="
												+ tea_id
												+ "&lengthNum="
												+ n
												+ "&days="
												+ i
												+ "&stu_id="
												+ getStu_id
												+ "' >free</a></td>";
									}

								}

								else {

									data[n][i] = "<td style='background-color: white;'><input id='"
											+ idd
											+ "' type='checkbox' class='groupclass' name='ft_t_"
											+ i
											+ "' value='"
											+ i
											+ "_"
											+ n
											+ "_"
											+ tea_name
											+ "_"
											+ i
											+ "' style='width: 40px;' />"
											+ "<a href='"
											+ realPath
											+ "/business/schedule/gotoBookClass.do?station=3&teaId="
											+ tea_id
											+ "&stu_id=null&lengthNum="
											+ n
											+ "&days="
											+ i
											+ "' >free</a> </td>";

								}
							}

							else {

								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);

									String name = stu.getEname();

									data[n][i] = "<td style='background-color: white;'><a href='"
											+ realPath
											+ "/business/sk_class/gotoClassInfo.do?clsId="
											+ schedulelist.get(i).get(m)
													.getStatus()
											+ "'>"
											+ name
											+ "</a> </td>";

								} else if (role == Constant.USER_ROLE_STUDENT) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);
									Long stu_user_id = stu.getUser_id();
									String name = stu.getEname();
									if (stu_user_id == loginUser.getUser_id())
										data[n][i] = "<td style='background-color: white;'><a href='"
												+ realPath
												+ "/business/sk_class/gotoClassInfo.do?clsId="
												+ schedulelist.get(i).get(m)
														.getStatus()
												+ "'>"
												+ name + "</a> </td>";
									else
										data[n][i] = "<td style='background-color: white;'>close</td>";

								}

							}
						}
					}
				}

			} else {
				for (int n = 0; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {

						String idd = n + "" + i;
						if (schedulelist.get(i).get(m).getSch_length() == n) {

							if (schedulelist.get(i).get(m).getStatus() == 0) {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN)
									data[n][i] = "<td style='background-color: white;'><input id='"
											+ idd
											+ "' type='checkbox' class='groupclass' name='ft_t_"
											+ i
											+ "' value='"
											+ i
											+ "_"
											+ n
											+ "_"
											+ tea_name
											+ "_"
											+ i
											+ "' style='width: 40px;' />close</td>";

								else if (role == Constant.USER_ROLE_STUDENT)
									data[n][i] = "<td style='background-color: white;'>close</td>";
							} else if (schedulelist.get(i).get(m).getStatus() == 1) {
								if (!("0".equals(getStu_id))) {

									if (role == Constant.USER_ROLE_STUDENT) {
										data[n][i] = "<td style='background-color: white;'> <input id='"
												+ idd
												+ "' type='hidden' class='groupclass' name='ft_t_"
												+ i
												+ "' value='"
												+ i
												+ "_"
												+ n
												+ "_"
												+ tea_name
												+ "_"
												+ i
												+ "' style='width: 40px;' />"
												+ "<a href='"
												+ realPath
												+ "/business/schedule/gotoBookClass.do?station=2&teaId="
												+ tea_id
												+ "&lengthNum="
												+ n
												+ "&days="
												+ i
												+ "&stu_id="
												+ getStu_id
												+ "' >free</a></td>";
									} else {
										data[n][i] = "<td style='background-color: white;'> <input id='"
												+ idd
												+ "' type='checkbox' class='groupclass' name='ft_t_"
												+ i
												+ "' value='"
												+ i
												+ "_"
												+ n
												+ "_"
												+ tea_name
												+ "_"
												+ i
												+ "' style='width: 40px;' />"
												+ "<a href='"
												+ realPath
												+ "/business/schedule/gotoBookClass.do?station=2&teaId="
												+ tea_id
												+ "&lengthNum="
												+ n
												+ "&days="
												+ i
												+ "&stu_id="
												+ getStu_id
												+ "' >free</a></td>";
									}

								} else {

									data[n][i] = "<td style='background-color: white;'><input id='"
											+ idd
											+ "' type='checkbox' class='groupclass' name='ft_t_"
											+ i
											+ "' value='"
											+ i
											+ "_"
											+ n
											+ "_"
											+ tea_name
											+ "_"
											+ i
											+ "' style='width: 40px;' />"
											+ "<a href='"
											+ realPath
											+ "/business/schedule/gotoBookClass.do?station=3&teaId="
											+ tea_id
											+ "&stu_id=null&lengthNum="
											+ n
											+ "&days="
											+ i
											+ "' >free</a> </td>";

								}

							} else {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);

									String name = stu.getEname();

									data[n][i] = "<td style='background-color: white;'><a href='"
											+ realPath
											+ "/business/sk_class/gotoClassInfo.do?clsId="
											+ schedulelist.get(i).get(m)
													.getStatus()
											+ "'>"
											+ name
											+ "</a> </td>";

								} else if (role == Constant.USER_ROLE_STUDENT) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);
									Long stu_user_id = stu.getUser_id();
									String name = stu.getEname();
									if (stu_user_id == loginUser.getUser_id())
										data[n][i] = "<td style='background-color: white;'><a href='"
												+ realPath
												+ "/business/sk_class/gotoClassInfo.do?clsId="
												+ schedulelist.get(i).get(m)
														.getStatus()
												+ "'>"
												+ name + "</a> </td>";
									else
										data[n][i] = "<td style='background-color: white;'>close</td>";

								}
							}
						}
					}
				}
			}
			// }

		}
		result.put("data", data);
		return result;
	}
*/
	
	/**
	 * 获取老师个人时间表
	 * 
	 * @param Teacher
	 *            t @param stu_id @param realPath @param loginUser
	 * 
	 */
/*	public Map<String, Object> getMyScheduleForTeaList(Teacher t,
			String realPath, User loginUser) {
		Map<String, Object> result = new HashMap<String, Object>();

		int role = loginUser.getUser_role();
		Date day = new Date();
		int hours = day.getHours();
		int min = day.getMinutes();
		Long tea_id = t.getTea_id();
		String tea_name = t.getName();
		List<Schedule> list = new ArrayList();
		List<Schedule> list1 = new ArrayList();
		List<Schedule> list2 = new ArrayList();
		List<Schedule> list3 = new ArrayList();
		list = scheduleDao.getMySchedule(tea_id, 0);
		list1 = scheduleDao.getMySchedule(tea_id, 1);
		list2 = scheduleDao.getMySchedule(tea_id, 2);
		list3 = scheduleDao.getMySchedule(tea_id, 3);

		List<List<Schedule>> schedulelist = new ArrayList<List<Schedule>>();

		schedulelist.add(list);
		schedulelist.add(list1);
		schedulelist.add(list2);
		schedulelist.add(list3);

		// result.put("day1", scheduleDao.getMySchedule(id,day));

		int num = 0;
		if (min < 30) {
			num = (hours) * 2 + 1;
		} else {
			num = hours * 2 + 2;
		}

		String data[][] = new String[48][4];

		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 48; b++) {
				String idd = b + "" + a;
				if (role == Constant.USER_ROLE_TEACHER
						|| role == Constant.USER_ROLE_ADMIN)
					data[b][a] = "<td style='background-color: white;'>close</td>";
			}

		}

		for (int i = 0; i < 4; i++) {
			// for (int j = 0; j < 48; j++) {
			if (i == 0) {
				for (int k = 0; k < num; k++) {
					// data[k][i] =
					// "<td style='background-color:gray;'>close</td>";
					for (int m = 0; m < schedulelist.get(i).size(); m++) {
						if (schedulelist.get(i).get(m).getSch_length() == k) {

							if (schedulelist.get(i).get(m).getStatus() == 0) {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN)
									data[k][i] = "<td style='background-color:gray;'>close</td>";

							} else if (schedulelist.get(i).get(m).getStatus() == 1) {
								data[k][i] = "<td style='background-color:gray;'><strong>free</strong></td>";
							} else {
								Long stu_id = schedulelist.get(i).get(m)
										.getStu_id();
								Student stu = studentDao
										.queryStudentById(stu_id);

								String name = stu.getEname();

								data[k][i] = "<td style='background-color:gray;'><strong>"
										+ name + "</strong></td>";
								break;
							}
						}
						else {
							data[k][i] = "<td style='background-color:gray;'>close</td>";
						}
					}
				}
				for (int n = num; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {
						if (schedulelist.get(i).get(m).getSch_length() == n) {
							String idd = n + "" + i;
							if (schedulelist.get(i).get(m).getStatus() == 0) {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN)
									data[n][i] = "<td style='background-color: white;'>close</td>";

							} else if (schedulelist.get(i).get(m).getStatus() == 1) {

								data[n][i] = "<td style='background-color: white;'><strong>free</strong></td>";

							}

							else {

								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);

									String name = stu.getEname();

									data[n][i] = "<td style='background-color: white;'><strong>"
											+ name + "</strong></td>";

								}

							}
						}
					}
				}

			} else {
				for (int n = 0; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {

						String idd = n + "" + i;
						if (schedulelist.get(i).get(m).getSch_length() == n) {

							if (schedulelist.get(i).get(m).getStatus() == 0) {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN)
									data[n][i] = "<td style='background-color: white;'>close</td>";

							} else if (schedulelist.get(i).get(m).getStatus() == 1) {

								data[n][i] = "<td style='background-color: white;'><strong>free</strong></td>";

							} else {
								if (role == Constant.USER_ROLE_TEACHER
										|| role == Constant.USER_ROLE_ADMIN) {
									Long stu_id = schedulelist.get(i).get(m)
											.getStu_id();
									Student stu = studentDao
											.queryStudentById(stu_id);

									String name = stu.getEname();

									data[n][i] = "<td style='background-color: white;'><strong>"
											+ name + "</strong></td>";

								}
							}
						}
					}
				}
			}
			// }

		}
		result.put("data", data);
		return result;
	}
*/
	public int freeNextDays(String idsAndNames) throws ParseException {
		int flag = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] ids_Names = idsAndNames.split(",");

		for (int i = 0; i < ids_Names.length; i++) {
			String[] id_name = ids_Names[i].split("_");
			Teacher tea = teacherDao.queryByName(id_name[2]);
			Date scheduleDate = dateFormat.parse(id_name[3]);

			Schedule s = scheduleDao.querySchedule(
					Integer.parseInt(id_name[1]), tea, scheduleDate);

			// 假如表中没有数据记录，插入数据表记录
			if (s!=null && s.getSch_id()>0) {
				if(Constant.SCHEDULE_STATUS_CLOSE == s.getStatus()){
					s.setStatus(Constant.SCHEDULE_STATUS_FREE);
					flag = scheduleDao.updateSchedule(s);
				}
			} else {
				flag = scheduleDao.insertSchedule(Integer.parseInt(id_name[1]),
						tea, Constant.SCHEDULE_STATUS_FREE, scheduleDate);
			}
		}

		return flag;
	}

	public int closeNextDays(String idsAndNames) throws ParseException {
		int flag = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] ids_Names = idsAndNames.split(",");

		for (int i = 0; i < ids_Names.length; i++) {
			String[] id_name = ids_Names[i].split("_");
			Teacher tea = teacherDao.queryByName(id_name[2]);
			Date scheduleDate = dateFormat.parse(id_name[3]);
			
			Schedule s = scheduleDao.querySchedule(
					Integer.parseInt(id_name[1]), tea, scheduleDate);
			
			// 假如表中没有数据记录，插入数据表记录
			if (s!=null && s.getSch_id()>0) {	
				if(Constant.SCHEDULE_STATUS_FREE == s.getStatus()){
					s.setStatus(Constant.SCHEDULE_STATUS_CLOSE);
					flag = scheduleDao.updateSchedule(s);
				}
			} else {
				flag = scheduleDao.insertSchedule(Integer.parseInt(id_name[1]),
						tea, Constant.SCHEDULE_STATUS_CLOSE, scheduleDate);
			}
		}

		return flag;
	}

	/**
	 * 设置空闲的时间表
	 * 
	 * @param idsAndNames
	 * @return int
	 */

	public int freeSchedules(String idsAndNames) {

		int flag = 0;

		String[] ids_Names = idsAndNames.split(",");
		;

		for (int i = 0; i < ids_Names.length; i++) {

			String[] id_name = ids_Names[i].split("_");
			
			long teaId = teacherDao.queryByName(id_name[2]).getTea_id();
			Schedule s = null;
			if ("0".equals(id_name[3])) {
				s = scheduleDao.querySchByLengthAndTeaId(
						Integer.parseInt(id_name[1]), teaId);
			} else {
				s = scheduleDao.querSchByTimeAndLengthAndTeaid(
						Integer.parseInt(id_name[1]), teaId, id_name[3]);
			}

			if (s!=null&&s.getSch_id()>0) {		
				if(Constant.SCHEDULE_STATUS_CLOSE == s.getStatus()){
					s.setStatus(Constant.SCHEDULE_STATUS_FREE);
					flag = scheduleDao.updateSchedule(s);
				}
			} else {
				if ("0".equals(id_name[3])) {
					flag = scheduleDao.insertSchedule(
							Integer.parseInt(id_name[1]), teaId, 1);
				} else {
					flag = scheduleDao.insertScheduleWithTime(
							Integer.parseInt(id_name[1]), teaId, 1, id_name[3]);
				}

			}

		}

		return flag;
	}

	/**
	 * 设置关闭的时间表
	 * 
	 * @param idsAndNames
	 * @return int
	 */
	public int closeSchedules(String idsAndNames) {

		int flag = 0;

		String[] ids_Names = idsAndNames.split(",");
		;

		for (int i = 0; i < ids_Names.length; i++) {

			String[] id_name = ids_Names[i].split("_");
			
			long teaId = teacherDao.queryByName(id_name[2]).getTea_id();

			Schedule s = null;
			if ("0".equals(id_name[3])) {
				s = scheduleDao.querySchByLengthAndTeaId(
						Integer.parseInt(id_name[1]), teaId);
			} else {
				s = scheduleDao.querSchByTimeAndLengthAndTeaid(
						Integer.parseInt(id_name[1]), teaId, id_name[3]);
			}

			if (s!=null && s.getSch_id()>0) {
				if(Constant.SCHEDULE_STATUS_FREE == s.getStatus()){
					s.setStatus(Constant.SCHEDULE_STATUS_CLOSE);
					flag = scheduleDao.updateSchedule(s);
				}
			} else {	
//				long teaId = teacherDao.queryByName(id_name[2]).getTea_id();
				if ("0".equals(id_name[3])) {
					flag = scheduleDao.insertSchedule(
							Integer.parseInt(id_name[1]), teaId, 0);
				} else {
					flag = scheduleDao.insertScheduleWithTime(
							Integer.parseInt(id_name[1]), teaId, 0, id_name[3]);
				}

			}

		}

		return flag;
	}

	/**
	 * 获取学生个人时间表
	 * 
	 * @param Student
	 *            @param realPath
	 * 
	 */
	public Map<String, Object> getStuSchedule(Student s, String realPath) {
		Map<String, Object> result = new HashMap<String, Object>();

		Date day = new Date();
		int hours = day.getHours();
		int min = day.getMinutes();
		Long stu_id = s.getStu_id();
		String stu_name = s.getWw_num();
		List<Schedule> list = new ArrayList();
		List<Schedule> list1 = new ArrayList();
		List<Schedule> list2 = new ArrayList();
		List<Schedule> list3 = new ArrayList();
		list = scheduleDao.getStuSchedule(stu_id, 0);
		list1 = scheduleDao.getStuSchedule(stu_id, 1);
		list2 = scheduleDao.getStuSchedule(stu_id, 2);
		list3 = scheduleDao.getStuSchedule(stu_id, 3);

		List<List<Schedule>> schedulelist = new ArrayList<List<Schedule>>();

		schedulelist.add(list);
		schedulelist.add(list1);
		schedulelist.add(list2);
		schedulelist.add(list3);

		int num = 0;
		if (min < 30) {
			num = (hours) * 2 + 1;
		} else {
			num = hours * 2 + 2;
		}

		String data[][] = new String[48][4];

		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 48; b++) {
				String idd = b + "" + a;
				data[b][a] = "<td style='background-color: white;'><input id='"
						+ idd
						+ "' type='checkbox' class='groupclass' name='ft_t_"
						+ a + "' value='0_" + b + "_" + stu_name + "_" + a
						+ "' style='width: 40px;' />close</td>";
			}

		}

		for (int i = 0; i < 4; i++) {
			// for (int j = 0; j < 48; j++) {
			if (i == 0) {
				for (int k = 0; k < num; k++) {
					data[k][i] = "<td style='background-color:gray;'>close</td>";
				}
				for (int n = num; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {
						if (schedulelist.get(i).get(m).getSch_length() == n) {
							String idd = n + "" + i;
							if (schedulelist.get(i).get(m).getStatus() == 0) {
								data[n][i] = "<td style='background-color: green;'><input id='"
										+ idd
										+ "' type='checkbox' class='groupclass' name='ft_t_"
										+ i
										+ "' value='1_"
										+ n
										+ "_"
										+ stu_name
										+ "_"
										+ i
										+ "' style='width: 40px;' />close</td>";
							} else if (list.get(m).getStatus() == 1) {

								data[n][i] = "<td style='background-color: white;'><input id='"
										+ idd
										+ "' type='checkbox' class='groupclass' name='ft_t_"
										+ i
										+ "' value='1_"
										+ n
										+ "_"
										+ stu_name
										+ "_"
										+ i
										+ "' style='width: 40px;' />free </td>";
							}

							else {

								Schedule schedule = scheduleDao
										.querySchByLengthAndStuId(n, stu_id);
								Long tea_id = schedule.getTea_id();

								Teacher teacher = teacherDao.queryById(tea_id);
								String name = teacher.getName();
								data[n][i] = "<td style='background-color: white'><a href='#'>"
										+ name + "</a></td>";
							}
						}
					}
				}

			} else {
				for (int n = 0; n < 48; n++) {
					for (int m = 0; m < schedulelist.get(i).size(); m++) {

						String idd = n + "" + i;
						if (schedulelist.get(i).get(m).getSch_length() == n) {

							if (schedulelist.get(i).get(m).getStatus() == 0) {

								data[n][i] = "<td style='background-color: white;'><input id='"
										+ idd
										+ "' type='checkbox' class='groupclass' name='ft_t_"
										+ i
										+ "' value='"
										+ i
										+ "_"
										+ n
										+ "_"
										+ stu_name
										+ "_"
										+ i
										+ "' style='width: 40px;' />close</td>";

							} else if (schedulelist.get(i).get(m).getStatus() == 1) {

								data[n][i] = "<td style='background-color: white;'> <input id='"
										+ idd
										+ "' type='checkbox' class='groupclass' name='ft_t_"
										+ i
										+ "' value='"
										+ i
										+ "_"
										+ n
										+ "_"
										+ stu_name
										+ "_"
										+ i
										+ "' style='width: 40px;' />free </td>";

							} else {
								Schedule schedule = scheduleDao
										.querySchByLengthAndStuIdAndDays(m,
												stu_id, i);
								Long tea_id = schedule.getTea_id();
								Teacher teacher = teacherDao.queryById(tea_id);
								String name = teacher.getName();
								data[n][i] = "<td style='background-color: white'><a href='#'>"
										+ name + "</a></td>";

							}
						}
					}
				}
			}
			// }

		}
		result.put("data", data);
		return result;
	}

	public int DfreeStuNextDays(String idsAndNames) {
		int flag = 0;
		String[] ids_Names = idsAndNames.split(",");
		;

		for (int i = 0; i < ids_Names.length; i++) {

			String[] id_name = ids_Names[i].split("_");

//			System.out.println(ids_Names[i] + "-----------");

			// 假如表中没有数据记录，插入数据表记录
			long stuId = studentDao.queryStudentBywwNum(id_name[2])
					.getStu_id();

			int numDay = Integer.parseInt(id_name[3]);

			Schedule s = scheduleDao.querySchByLengthAndStuIdAndDays(
					Integer.parseInt(id_name[1]), stuId, numDay);
			if(s!=null && s.getSch_id()>0) {
				s.setStatus(Constant.SCHEDULE_STATUS_FREE);
				flag = scheduleDao.updateStuSchedule(s);

			}else {
				// 插入当天
				if ("0".equals(id_name[3])) {

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 1, 0);
				} else if ("1".equals(id_name[3])) {
					// 插入第二天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 1, 1);
				} else if ("2".equals(id_name[3])) {
					// 插入第三天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 1, 2);
				} else if ("3".equals(id_name[3])) {
					// 插入第四天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 1, 3);
				}
			}
//			if ("0".equals(id_name[0])) {
				
		}

		return flag;
	}

	public int DcloseStuNextDays(String idsAndNames) {
		int flag = 0;
		String[] ids_Names = idsAndNames.split(",");
		;

		for (int i = 0; i < ids_Names.length; i++) {

			String[] id_name = ids_Names[i].split("_");

			System.out.println(ids_Names[i] + "-----------");

			// 假如表中没有数据记录，插入数据表记录
			long stuId = studentDao.queryStudentBywwNum(id_name[2])
					.getStu_id();

			int numDay = Integer.parseInt(id_name[3]);

			Schedule s = scheduleDao.querySchByLengthAndStuIdAndDays(
					Integer.parseInt(id_name[1]), stuId, numDay);
			
			//if ("0".equals(id_name[0])) {
			if(s!=null && s.getSch_id()>0) {
				s.setStatus(Constant.SCHEDULE_STATUS_CLOSE);
				flag = scheduleDao.updateStuSchedule(s);
			}else {
				// 插入当天
				if ("0".equals(id_name[3])) {

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 0, 0);
				} else if ("1".equals(id_name[3])) {
					// 插入第二天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 0, 1);
				} else if ("2".equals(id_name[3])) {
					// 插入第三天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 0, 2);
				} else if ("3".equals(id_name[3])) {
					// 插入第四天的

//					long stuId = studentDao.queryStudentBywwNum(id_name[2])
//							.getStu_id();

					flag = scheduleDao.insertStuScheduleDays(
							Integer.parseInt(id_name[1]), stuId, 0, 3);
				}

			} 
		}

		return flag;
	}

	private boolean isAfterEqual(Date checkDate, int checkPeriod, Date referenceDate, int referencePeriod){
		if (checkDate.after(referenceDate)||(DateUtil.isSameDay(checkDate, referenceDate)
				&& checkPeriod >= referencePeriod)) {
			return true;
		} else{
			return false;
		}
	}
}
