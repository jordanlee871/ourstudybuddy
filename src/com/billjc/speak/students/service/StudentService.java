package com.billjc.speak.students.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.framework.util.Constant;
import com.billjc.speak.balance.dao.BalanceDao;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.privateset.dao.PrivatesetDao;
import com.billjc.speak.privateset.entity.Privateset;
import com.billjc.speak.sk_class.dao.SkClassDao;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.users.dao.UserDao;
import com.billjc.speak.users.entity.User;

@Service
public class StudentService {
	final Logger logger = LoggerFactory.getLogger(StudentService.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private PrivatesetDao privatesetDao;
	@Autowired
	private SkClassDao skClassDao;
	@Autowired
	private BalanceDao balanceDao;

	/**
	 * 新注册学生
	 * 
	 * @return 学生id
	 */
	@Transactional
	public Long insertStudent(Student student, User user, String autoBalance) {
		int userId = userDao.inserRegisterUser(user);
		user = userDao.queryByName(user.getUser_name());
		student.setUser_id(user.getUser_id());
		studentDao.insertStudent(student);
		student = studentDao.queryStudentBywwNum(user.getUser_name());

		if ("on".equals(autoBalance)) {
			// 获取学生ID
			long stuId = student.getStu_id();
			Balance bl = new Balance();
			// 使用金额
			float blNum = 0.5f;
			// 老师名称
			String comment = "注册自动充值0.5节";
			bl.setBlNum(blNum);
			bl.setComment(comment);
			bl.setBalance_before(student.getBalance());
			bl.setDateTime(new Date());
			bl.setStuId(stuId);
			balanceDao.insertBlance(bl);
			student.setBalance(student.getBalance() + blNum);
			studentDao.updateRegStu(student);
		}

		return student.getStu_id();
	}

	public List<Student> DqueryStudentListByTeaID(long tea_id) {
		return studentDao.DqueryStuByTeaID(tea_id);

	}

	/**
	 * 搜索老师最近4天的学生列表，包括所有课程类型以及课程状态，不包括取消了关联的老师
	 * 
	 * @param tea_id
	 * @return
	 */
	public List<Student> queryStudentList(Teacher tea, Date date) {
		return studentDao.queryStudents(tea, date);

	}

	/**
	 * 查询申请注册的学生列表
	 * 
	 * @return
	 */
	public Map<String, Object> queryRegisterStudents(int start, int end,
			Map<String, String> searchParam) {

		Map<String, Object> result = new HashMap<String, Object>();

		String search_name = searchParam.get("search_name");
		String search_order_by = searchParam.get("order_by").toUpperCase();
		StringBuilder condition = new StringBuilder();

		if (!"".equals(search_name)) {
			condition.append(" AND (ENAME LIKE '%" + search_name + "%'" + ")");
		}

		String orderBy = "";
		if ("ID_ASC".equals(search_order_by)) {
			orderBy = " ORDER BY REGISTER_TIME ASC";
		} else if ("ID_DESC".equals(search_order_by)) {
			orderBy = " ORDER BY REGISTER_TIME DESC";
		} else {
			orderBy = " ORDER BY  REGISTER_TIME  DESC";
		}

		result.put("count",
				studentDao.queryRegStuListCount(condition.toString()));
		result.put("data", studentDao.queryRegisterStudents(
				condition.toString(), orderBy, start, end));

		return result;
	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public Student queryStudentById(Long id) {

		return studentDao.queryStudentById(id);
	}

	/**
	 * 查询学生
	 * 
	 * @param id
	 * @return
	 */
	public Student queryStudentByUserId(Long id) {

		return studentDao.queryStudentByUserId(id);
	}

	/**
	 * 根据英文名查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public Map<String, Object> queryStudentByEname(String ename, int start,
			int end) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", studentDao.queryStudentByEnameCount(ename));
		result.put("data", studentDao.queryStudentByEname(ename, start, end));
		return result;
	}

	/**
	 * 根据ww名查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public Student queryStudentBywwNum(String ww) {
		Student s = studentDao.queryStudentBywwNum2(ww);
		return s;
	}

	/**
	 * 根据学生名名查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public Student queryStudentByename(String ww) {
		Student s = studentDao.queryStudentByename(ww);
		return s;
	}

	/**
	 * 根据学生QQ查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public List<Student> queryStudentByQq(String qq) {
		return studentDao.queryStudentByQq(qq);
	}

	/**
	 * 根据学生Skype查询学生
	 * 
	 * @param ename
	 * @return
	 */
	public List<Student> queryStudentBySkype(String skype) {
		return studentDao.queryStudentBySkype(skype);
	}

	/**
	 * 查询WW_NUM是否已存在
	 * 
	 * @return
	 */

	public int checkWw_num(String ww_num) {
		return studentDao.checkWw_num(ww_num);
	}

	/**
	 * 查询WW_NUM是否已存在
	 * 
	 * @return
	 */

	public int checkWw_num2(String ww_num) {
		return studentDao.checkWw_num2(ww_num);
	}

	/**
	 * 
	 * @param qqNumParm
	 * @return 查找到的QQ记录数量
	 */
	public int countStudentByQq(String qqNum) {
		return studentDao.countStudentByQq(qqNum);
	}

	/**
	 * 
	 * @param 电话号码
	 * @return 查找到的QQ记录数量
	 */
	public int countStudentByPhone(String phone) {
		return studentDao.countStudentByPhone(phone);
	}

	/**
	 * 查询WW_NUM是否已存在
	 * 
	 * @return
	 */

	public String checkBalance(String stuName, long teaId) {

		Student stu = studentDao.queryStudentBywwNum2(stuName);
		String result = "8";

		if (null != stu) {
			result = "7";
			Privateset privateset = privatesetDao.queryPsByWWAndteaId(
					stu.getStu_id(), teaId);
			if (null != privateset) {
				result = "6";

				float balance = stu.getBalance();
				if (balance == (float) 3) {
					result = "5";
				}
				if (balance == (float) 2) {
					result = "4";
				}
				if (balance == (float) 1.5) {
					result = "3";
				}
				if (balance == (float) 1) {
					result = "2";
				}
				if (balance == (float) 0.5) {
					result = "1";
				}
				if (balance == 0) {
					result = "0";
				}
			}
		}
		return result;
	}

	/**
	 * 查询学生名是否已存在
	 * 
	 * @return
	 */

	public int checkStuName(String ww_num) {
		return studentDao.checkStuName(ww_num);
	}

	/*   *//**
	 * 查询学生QQ号是否已存在
	 * 
	 * @return
	 */
	/*
	 * 
	 * public int checkQQ_NUM(String qq_num){ return
	 * studentDao.checkQQ_NUM(qq_num); }
	 */

	/**
	 * 更新学生
	 * 
	 * @param student
	 * @return
	 */
	public int UpdateRegStudent(Student student) {
		return studentDao.updateRegStu(student);
	}

	public Student queryStudentByStuId(long stu_id) {
		return studentDao.queryStudentByStuId(stu_id);
	}

	/**
	 * 根据时间查，老师姓名找学生
	 * 
	 * @param student
	 * @return
	 */
	public Map<String, Object> queryStudentByTimeandTname(long tea_id,
			Date sch_datetime, int start, int end) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", studentDao.queryStudentByTimeandTnameCount(tea_id,
				sch_datetime));
		result.put("data", studentDao.queryStudentByTimeandTname(tea_id,
				sch_datetime, start, end));
		return result;
	}

	/**
	 * 根据时间查找学生
	 * 
	 * @param student
	 * @return
	 */
	public Map<String, Object> queryStudentByTime(Date sch_datetime, int start,
			int end) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("count", studentDao.queryStudentByTimeCount(sch_datetime));
		result.put("data",
				studentDao.queryStudentByTime(sch_datetime, start, end));
		return result;
	}

	/*
	 * 助教角色搜索学生 return List<Student> or Student
	 */
	public Map<String, Object> queryStudent(int start, int end,
			Map<String, String> searchParam) {
		Map<String, Object> result = new HashMap<String, Object>();

		String stu_id = searchParam.get("stu_id");
		// 老师空闲页面传的老师Id
		long teaId = Long.parseLong(searchParam.get("teaId"));
		String ww_num = searchParam.get("ww_num");
		String qq_num = searchParam.get("qq_num");
		String skype_num = searchParam.get("skype_num");
		String phone = searchParam.get("phone");
		String email = searchParam.get("email");
		String ename = searchParam.get("ename");
		String teacher_name = searchParam.get("teacher_name");
		String sch_datetime = searchParam.get("sch_datetime");

		StringBuilder condition = new StringBuilder();

		if (!("".equals(stu_id))) {
			condition.append(" AND STU_ID ='" + stu_id + "' ");
		}
		if (!("".equals(ww_num))) {
			condition.append(" AND WW_NUM ='" + ww_num + "' ");
		}
		if (!("".equals(qq_num))) {
			condition.append(" AND QQ_NUM = '" + qq_num + "' ");
		}
		if (!("".equals(skype_num))) {
			condition.append(" AND SKYPE_NUM = '" + skype_num + "' ");
		}
		if (!("".equals(phone))) {
			condition.append(" AND PHONE = '" + phone + "' ");
		}
		if (!("".equals(email))) {
			condition.append(" AND EMAIL = '" + email + "' ");
		}
		if (!("".equals(ename))) {
			condition.append(" AND ENAME LIKE '%" + ename + "%' ");
		}
		if (!("".equals(teacher_name))) {
			// condition.append(" AND TEACHER_NAME LIKE '%"+teacher_name+"%' ");
			condition
					.append(" AND STU_ID IN ( SELECT DISTINCT SP.STU_ID FROM sk_private_set SP ,sk_teacher ST "
							+ "WHERE SP.TEA_ID = ST.TEA_ID  AND ST.NAME LIKE '%"
							+ teacher_name + "%' )");
		}
		if (!("".equals(sch_datetime))) {

			condition
					.append(" AND  STU_ID IN ( SELECT DISTINCT STU_ID FROM sk_schedule WHERE SCH_DATETIME = '"
							+ sch_datetime + "' )");
		}

		List<Student> stuList = null;
		System.out.println(condition.toString());
		if (0 != teaId) {
			stuList = studentDao.queryStudentByTeaId(teaId);
		} else {
			stuList = studentDao.queryStudent(condition.toString(), start, end);
		}

//		for (int i = 0; i < stuList.size(); i++) {
//
//			List<Privateset> pts = privatesetDao.queryPsList(stuList.get(i)
//					.getStu_id());
//			if (null != pts && pts.size() > 0) {
//				SkClass cls = skClassDao.queryNearlyTimeClassByStudent(stuList
//						.get(i).getStu_id());
//				if (cls.getClsId() != 0) {
//					stuList.get(i).setSch_datetime(cls.getBeginTime());
//					Teacher teacher = teacherDao.queryById(cls.getTeaId());
//					stuList.get(i).setTeaName(teacher.getName());
//				}
//			}
//		}
		result.put("data", stuList);

		if (0 != teaId) {
			result.put("count", studentDao.countStudentByTeaId(teaId));
		} else {
			result.put("count",
					studentDao.queryStudentCount(condition.toString()));
		}
		return result;
	}

	/*
	 * 更改学生信息
	 * 
	 * @param
	 */
	public int updateStudentInfo(Long stu_id, String ename, String ww_num,
			String qq_num, String skype_num, String phone, String email,
			String notifySMS, String notifyMail) {
		return studentDao.updateStudentInfo(stu_id, ename, ww_num, qq_num,
				skype_num, phone, email, notifySMS, notifyMail);
	}


	/*
	 * 更改学生备注信息
	 * 
	 * @param stu_id,remark
	 */
	public int updateRemark(Long stu_id, String remark) {
		return studentDao.updateRemark(stu_id, remark);
	}
	
	/*
	 * 更改学生备注信息. 
	 * 返回-1 表示旺旺不存在
	 * 
	 * @param WW,remark
	 */
	public int addRemark(String ww, String remark) {
		Student stu = studentDao.queryStudentBywwNum(ww);
		if(null != stu){
			return studentDao.addRemark(ww, remark.trim()+"\n"+stu.getRemark());
		}
		return -1;
	}

	/*
	 * 更改学生的老师备注信息
	 * 
	 * @param stu_id,remark
	 */
	public int updateTeacherRemark(Long stu_id, String teacherRemark) {
		return studentDao.updateTeacherRemark(stu_id, teacherRemark);
	}

	/**
	 * 根据学生QQ查询学生
	 * 
	 * @param teacher
	 *            如果为null，则不考虑这个条件
	 * @param ename
	 * @return
	 * @throws Exception
	 */
	public List<Student> queryStudentByMixId(String mixId, boolean isTA,
			Teacher teacher) {
		Student student = null;
		List<Student> students = null;
		try {
			long mixIdLong = Long.parseLong(mixId);
			if (null == teacher) {				
				student = this.queryStudentById(mixIdLong);
				if (null != student){
					students = new ArrayList<Student>();
					students.add(student);
				}
			} else {
				Map<String, String> searchParam = new HashMap<String, String>();
				searchParam.put("stuId", mixId);
				List<Student> temp = this.queryStudent(0, 1, searchParam,
						teacher.getTea_id());
				if (temp.size() > 0) {
					students = temp;
				}
			}
		} catch (NumberFormatException e) {
			student = null;
		}

		if (null == students || 0 == students.size()) {
			if (null == teacher) {
				students = this.queryStudentByQq(mixId);
			} else {
				Map<String, String> searchParam = new HashMap<String, String>();
				searchParam.put("qq", mixId);
				List<Student> temp = this.queryStudent(0, 1, searchParam,
						teacher.getTea_id());
				if (temp.size() > 0) {
					students = temp;
				}
			}
		}
		if ((null == students  || 0 == students.size())&& isTA) {// 只有助教才搜索旺旺
			students = studentDao.queryStudentBywwNum3(mixId);
		}
		if ((null == students  || 0 == students.size())&& isTA) {// 只有助教才搜索电话号码
			students = this.queryStudentByPhone(mixId);
		}
		if (null == students  || 0 == students.size()) {
			if (null == teacher) {
				students = this.queryStudentBySkype(mixId);
			} else {
				Map<String, String> searchParam = new HashMap<String, String>();
				searchParam.put("skype", mixId);
				List<Student> temp = this.queryStudent(0, 1, searchParam,
						teacher.getTea_id());
				if (temp.size() > 0) {
					students = temp;
				}
			}
		}
		return students;
	}

	public List<Student> queryStudent(int start, int end,
			Map<String, String> searchParam, long teacherID) {
		Map<String, Object> result = new HashMap<String, Object>();

		String stuId = searchParam.get("stuId");
		String qq = searchParam.get("qq");
		String skype = searchParam.get("skype");
		String ename = searchParam.get("ename");

		StringBuilder condition = new StringBuilder();

		if (null != stuId && !("".equals(stuId))) {
			condition.append(" AND ST.STU_ID ='" + stuId + "' ");
		}
		if (null != qq && !("".equals(qq))) {
			condition.append(" AND ST.QQ_NUM = '" + qq + "' ");
		}
		if (null != skype && !("".equals(skype))) {
			condition.append(" AND ST.SKYPE_NUM = '" + skype + "' ");
		}

		return teacherDao.queryStudentByTeacher(condition.toString(), start,
				end, teacherID);
	}
	
	private List<Student> queryStudentByPhone(String mixId) {
		return studentDao.queryStudentByPhone(mixId);
	}

	/**
	 * 批量检索学生
	 * @param idListString
	 * @param wwListString
	 * @param qqListString
	 * @param phoneListString
	 * @param skypeListString
	 * @return
	 */
	public List<Student> batchQueryStu(String idListString,
			String wwListString, String qqListString, String phoneListString,
			String skypeListString) {
		ArrayList<Student> students = new ArrayList<Student>();
		
		if(idListString!=null && !idListString.equals("")){
			String[] idList = idListString.trim().split("\\r\\n");
			students.addAll(studentDao.batchQueryStuById(idList));
		}
		if(wwListString!=null && !wwListString.equals("")){
			String[] wwList = wwListString.trim().split("\\r\\n");
			students.addAll(studentDao.batchQueryStuByWw(wwList));
		}
		if(qqListString!=null && !qqListString.equals("")){
			String[] qqList = qqListString.trim().split("\\r\\n");
			students.addAll(studentDao.batchQueryStuByQq(qqList));
		}
		if(phoneListString!=null && !phoneListString.equals("")){
			String[] phoneList = phoneListString.trim().split("\\r\\n");
			students.addAll(studentDao.batchQueryStuByPhone(phoneList));
		}
		if(skypeListString!=null && !skypeListString.equals("")){
			String[] skypeList = skypeListString.trim().split("\\r\\n");
			students.addAll(studentDao.batchQueryStuBySkype(skypeList));
		}
		return students;
	}
}
