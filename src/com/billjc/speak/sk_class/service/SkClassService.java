package com.billjc.speak.sk_class.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.framework.SendMsg_webchinese;
import com.billjc.framework.SimpleHtmlMailSender;
import com.billjc.framework.util.Constant;
import com.billjc.framework.util.Email;
import com.billjc.framework.util.OtherUtil;
import com.billjc.framework.util.RegexUtil;
import com.billjc.speak.balance.dao.BalanceDao;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.myClass.entity.MyClass;
import com.billjc.speak.notice.dao.NoticeDao;
import com.billjc.speak.push.dao.PushDao;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.schedule.dao.ScheduleDao;
import com.billjc.speak.schedule.entity.Schedule;
import com.billjc.speak.sk_class.dao.SkClassDao;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.smsLog.entity.SMSLog;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.teacher.service.TeacherService;
import com.billjc.speak.users.entity.User;

@Service
public class SkClassService {
	final Logger logger = LoggerFactory.getLogger(SkClassService.class);
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private TeacherDao teacherDao;
	@Autowired
	private SkClassDao skClassDao;

	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	TeacherService teacherService;
	@Autowired
	private BalanceDao balanceDao;
	
	@Autowired
	private SimpleHtmlMailSender emailSender;
	
	@Autowired
	private PushDao pushDao;
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Autowired
	private SendMsg_webchinese smsSender;

	/**
	 * 新增一个课程预约
	 * 
	 * @param id
	 * @return
	 */

	@Transactional
	public int insertSkClass(SkClass cls, long timeFlag, int days) {

		// 防止重复预订课程
		long clsId = 0;
		if (0 != skClassDao.queryClsIdByCls(cls).size()) {
			clsId = skClassDao.queryClsIdByCls(cls).get(0).getClsId();
		}

		int a = 0;
		if (0 == clsId) {
			a = skClassDao.insertClass(cls);
			// 取得刚预订的课程Id
			clsId = skClassDao.queryClsIdByCls(cls).get(0).getClsId();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(cls.getBeginTime());
		float b = cls.getClsLength() * 2;
		int c = new Float(b).intValue();
		for (int i = 0; i < c; i++) {
			Schedule s = scheduleDao.querySchByLengthAndTeaIdAndTime(timeFlag,
					cls.getTeaId(), time);
			if (s != null) {
				s.setStatus(clsId);
				s.setStu_id(cls.getStuId());
				// k+=scheduleDao.updateSchedule(s);
				scheduleDao.updateSchedule(s);
			}
			timeFlag++;
		}
		
		//更新学生的最后上课时间
		studentDao.updateLastClass(cls.getStuId(),cls.getBeginTime());
		return a;
	}
	
	/**
	 * 如果课程被预约返回true
	 * @param teaId
	 * @param beginTime
	 * @return
	 */
	public boolean queryClassByTime(Long teaId, Date beginTime){
		boolean flag = false;
		
		List<SkClass> clsList = skClassDao.queryNormalClass(teaId, beginTime);
		if(clsList.size() >0 ){
			return true;
		}
		
		return flag;
	}

	/**
	 * 如果课程被预约返回true
	 * @param teaId
	 * @param beginTime
	 * @return
	 */
	public List<SkClass> queryNormalClass(Long teaId, Date beginTime){
		
		List<SkClass> clsList = skClassDao.queryNormalClass(teaId, beginTime);
		
		return clsList;
	}
	
	/**
	 * 查看课程预约信息
	 * 
	 * @param ClsId
	 * @return SkClass
	 */

	public SkClass queryClassByClsId(Long clsId) {

		return skClassDao.queryByClsId(clsId);
	}

	/**
	 * 更新预约课程信息
	 * 
	 * @param clsId
	 *            ,stuLate,teaLate,clsStatus
	 * @return int
	 * @throws Exception 
	 * 
	 */

	public int updateClass(long clsId, int stuLate, int teaLate, int clsStatus,
			String stuName, int clsType, String clsBookingRemark,
			String clsComment,User loginUser) throws Exception {

		SkClass cls = queryClassByClsId(clsId);
		cls.setTeaLate(teaLate);
		cls.setClsType(clsType);
		cls.setStuLate(stuLate);
		cls.setClsComment(clsComment);
		cls.setBookingRemark(clsBookingRemark);
		Student stu = studentDao.queryStudentBywwNum2(cls.getEname());
		if (clsStatus != 1 && cls.getClsLength() > 1) {
//			stu = studentDao.queryStudentBywwNum2(stuName);
			// 获取学生ID
			long stuId = stu.getStu_id();
			Balance bl = new Balance();
			// 使用金额
			float blNum = 0;
			// 老师名称
			String TeaName = teacherDao.queryById(cls.getTeaId()).getName();
			String comment = "";
			if (loginUser.getUser_role() == Constant.USER_ROLE_STUDENT
					.intValue()) {
				Student tempStu = studentDao.queryStudentByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(SID:"
						+ tempStu.getStu_id() + ")]";
			} else if (loginUser.getUser_role() == Constant.USER_ROLE_TEACHER
					.intValue()) {
				Teacher tempTea = teacherDao.queryByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(TID:"
						+ tempTea.getTea_id() + ")]";
			} else {
				comment += "[" + loginUser.getUser_name() + "(ID:"
						+ loginUser.getUser_id() + ")]";
			}
			if (clsStatus == 6) {
				if ((float) 1.5 == cls.getClsLength()) {
					blNum = ((float) 0.5);
				}
				if ((float) 2 == cls.getClsLength()) {
					blNum = ((float) 1);
				}
				
				comment = " Stu absent:" + stuName + " " + cls.getClsLength()
						+ " at " + cls.getBeginTimeEx() + " 学生缺席";
			} else {
				blNum = ((float) 1);
				comment = " Tea absent:" + TeaName + " " + cls.getClsLength()
						+ " at " + cls.getBeginTimeEx() + " 老师缺席";
			}
			bl.setBlNum(blNum);
			bl.setComment(comment);
			bl.setBalance_before(stu.getBalance());
			bl.setDateTime(new Date());
			bl.setStuId(stuId);
			balanceDao.insertBlance(bl);
			stu.setBalance(stu.getBalance() + blNum);
			studentDao.updateRegStu(stu);
			
			
		}
		if (0 != clsStatus) {
			cls.setClsStatus(clsStatus);
		}
		
		int result = skClassDao.updateClass(cls);
		
		// 循环次数
		float b = cls.getClsLength() * 2;
		int c = new Float(b).intValue();
		Date dt = cls.getBeginTime();
		Date ymd = new Date();
		ymd.setYear(dt.getYear());
		ymd.setMonth(dt.getMonth());
		ymd.setDate(dt.getDate());
		int lengthNum = 0;
		if (30 == dt.getMinutes()) {
			lengthNum = dt.getHours() * 2 + 1;
		} else {
			lengthNum = dt.getHours() * 2;
		}

		String teaIds = "";
		
		for (int i = 0; i < c; i++) {
			Schedule s = scheduleDao.querySchByLengthAndTeaIdAndDate(lengthNum,
					cls.getTeaId(), cls.getStuId(), ymd);
			if (clsStatus == Constant.CLASS_STATUS_STU_CXL
					|| clsStatus == Constant.CLASS_STATUS_TEA_CXL
					|| clsStatus == Constant.CLASS_STATUS_STU_EMG_CXL
					|| clsStatus == Constant.CLASS_STATUS_TEA_EMG_CXL) {
				s.setStatus(Constant.SCHEDULE_STATUS_FREE);
				scheduleDao.updateSchedule(s);
				
				//记录发邮件的老师的id
			} else {
				s.setStatus(cls.getClsId());
				scheduleDao.updateSchedule(s);
			}
			teaIds += cls.getTeaId() + ",";
			lengthNum++;
		}
		
		this.sendCxlEmail(loginUser.getUser_role(),teaIds,stu,clsStatus,cls);
		this.sendCxlSMS(loginUser.getUser_role(),teaIds,stu,clsStatus,cls);
		
		return result;
	}

	/**
	 * admin change 课程信息
	 * 
	 * @param clsId
	 *            ,stuLate,teaLate,clsStatus
	 * @return int
	 * @throws Exception 
	 */
	@Transactional
	public int adminCancelClass(long clsId, int stuLateNew, int teaLateNew,
			int clsStatusNew, int clsTypeNew, String clsBookingRemarkNew,
			String clsCommentNew, User loginUser) throws Exception {

		float updateBl = 0;
		float classBl = 0;
		int clsStatusOld;
		int result = 0;

		SkClass cls = queryClassByClsId(clsId);
		Teacher tea = teacherDao.queryById(cls.getTeaId());
		String teaIds = "";
		classBl = cls.getClsLength();
		clsStatusOld = cls.getClsStatus();
		Balance bl = null;
		Student stu = studentDao.queryStudentBywwNum2(cls.getEname());

		if (clsStatusNew != cls.getClsStatus()) {
			switch (clsStatusOld) {// 恢复到Pending状态
			case Constant.CLASS_STATUS_STU_CXL:
				updateBl = 0 - classBl;
				break;
			case Constant.CLASS_STATUS_TEA_CXL:
				updateBl = 0 - classBl;
				break;
			case Constant.CLASS_STATUS_STU_EMG_CXL:
				updateBl = 0 + Constant.CLASS_SPLIT_CANCELLED - classBl;
				break;
			case Constant.CLASS_STATUS_TEA_EMG_CXL:
				updateBl = 0 - Constant.CLASS_SPLIT_CANCELLED - classBl;
				break;
			case Constant.CLASS_STATUS_STU_ABSENT:
				updateBl = 0 + Constant.CLASS_SPLIT_ABSENT - classBl;
				break;
			case Constant.CLASS_STATUS_TEA_ABSENT:
				updateBl = 0 - Constant.CLASS_SPLIT_ABSENT - classBl;
				break;
			case Constant.CLASS_STATUS_PENDING:
				updateBl = 0;
				break;
			case Constant.CLASS_STATUS_COMPLETED:
				updateBl = 0;
				break;
			default:
				throw new Exception("invalid class type");
			}

			switch (clsStatusNew) {// 从Pending更新到新状态余额
			case Constant.CLASS_STATUS_STU_CXL:
				updateBl += classBl;
				break;
			case Constant.CLASS_STATUS_TEA_CXL:
				updateBl += classBl;
				break;
			case Constant.CLASS_STATUS_STU_EMG_CXL:
				updateBl += -Constant.CLASS_SPLIT_CANCELLED + classBl;
				break;
			case Constant.CLASS_STATUS_TEA_EMG_CXL:
				updateBl += Constant.CLASS_SPLIT_CANCELLED + classBl;
				break;
			case Constant.CLASS_STATUS_STU_ABSENT:
				updateBl += -Constant.CLASS_SPLIT_ABSENT + classBl;
				break;
			case Constant.CLASS_STATUS_TEA_ABSENT:
				updateBl += +Constant.CLASS_SPLIT_ABSENT + classBl;
				break;
			case Constant.CLASS_STATUS_PENDING:
				throw new Exception("invalid class type");
			case Constant.CLASS_STATUS_COMPLETED:
				updateBl += 0;
				if(Constant.CLASS_STATUS_PENDING != clsStatusOld){
					throw new Exception(
							"The old class status is not Pending when it taged to Complete");
				}
				break;
			default:
				throw new Exception("invalid class type");
			}

			cls.setClsStatus(clsStatusNew);

			// 循环次数
			float b = cls.getClsLength() * 2;
			int c = new Float(b).intValue();
			Date dt = cls.getBeginTime();
			Date ymd = new Date();
			ymd.setYear(dt.getYear());
			ymd.setMonth(dt.getMonth());
			ymd.setDate(dt.getDate());
			int lengthNum = 0;
			if (30 == dt.getMinutes()) {
				lengthNum = dt.getHours() * 2 + 1;
			} else {
				lengthNum = dt.getHours() * 2;
			}

			for (int i = 0; i < c; i++) {// 更改Schedule状态
				Schedule s = scheduleDao.querySchByLengthAndTeaIdAndDate(
						lengthNum, cls.getTeaId(), cls.getStuId(), ymd);
				if ((Constant.CLASS_STATUS_STU_CXL == clsStatusNew
						|| Constant.CLASS_STATUS_TEA_CXL == clsStatusNew
						|| Constant.CLASS_STATUS_STU_EMG_CXL == clsStatusNew
						|| Constant.CLASS_STATUS_TEA_EMG_CXL == clsStatusNew
						|| Constant.CLASS_STATUS_STU_ABSENT == clsStatusNew || Constant.CLASS_STATUS_TEA_ABSENT == clsStatusNew)
						&& (Constant.CLASS_STATUS_PENDING == clsStatusOld || Constant.CLASS_STATUS_COMPLETED == clsStatusOld)) {
					s.setStatus(Constant.SCHEDULE_STATUS_FREE);
					scheduleDao.updateSchedule(s);
				}
				teaIds += cls.getTeaId() + ",";
				lengthNum++;
			}

			// 获取学生ID
			bl = new Balance();
			bl.setBlNum(updateBl);

			String comment = "";
			if (loginUser.getUser_role() == Constant.USER_ROLE_STUDENT
					.intValue()) {
				Student tempStu = studentDao.queryStudentByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(SID:"
						+ tempStu.getStu_id() + ")]";
			} else if (loginUser.getUser_role() == Constant.USER_ROLE_TEACHER
					.intValue()) {
				Teacher tempTea = teacherDao.queryByUserId(loginUser
						.getUser_id());
				comment += "[" + loginUser.getUser_name() + "(TID:"
						+ tempTea.getTea_id() + ")]";
			} else {
				comment += "[" + loginUser.getUser_name() + "(ID:"
						+ loginUser.getUser_id() + ")]";
			}
			bl.setComment(comment + " [" + Constant.getStatusInfo(clsStatusOld)
					+ "->" + Constant.getStatusInfo(clsStatusNew) + "]" + " ["
					+ tea.getName() + "(TID:" + tea.getTea_id() + ") "
					+ cls.getClsLength() + " " + cls.getBeginTimeEx() + "]");
			bl.setBalance_before(stu.getBalance());
			bl.setDateTime(new Date());
			bl.setStuId(cls.getStuId());
			balanceDao.insertBlance(bl);
			stu.setBalance(stu.getBalance() + updateBl);
			studentDao.updateRegStu(stu);

			cls.setTeaLate(teaLateNew);
			cls.setClsType(clsTypeNew);
			cls.setStuLate(stuLateNew);
			cls.setClsComment(clsCommentNew);
			cls.setBookingRemark(clsBookingRemarkNew);
			result = skClassDao.updateClass(cls);
			this.sendCxlEmail(loginUser.getUser_role(), teaIds, stu,
					clsStatusNew, cls);
			this.sendCxlSMS(loginUser.getUser_role(), teaIds, stu,
					clsStatusNew, cls);

		} else {// 课程状态一致，只修改课程信息
			cls.setTeaLate(teaLateNew);
			cls.setClsType(clsTypeNew);
			cls.setStuLate(stuLateNew);
			cls.setClsComment(clsCommentNew);
			cls.setBookingRemark(clsBookingRemarkNew);
			result = skClassDao.updateClass(cls);
		}
		return result;
	}
	
	/**
	 * 预约后邮件提醒
	 * @param user
	 * @param teaId
	 * @param stu
	 * @param clsStatus
	 * @param cls
	 * @throws Exception 
	 */
	public void doBookNotice(User user,String teaId,Student stu,int clsStatus,SkClass cls) throws Exception{
		sendCxlEmail(user.getUser_role(),teaId,stu,clsStatus,cls);
		sendCxlSMS(user.getUser_role(), teaId, stu, clsStatus, cls);
	}
	
	/**
	 * 取消课程时，发送邮件
	 * @param userId 登陆用户Id
	 * @param teaIds 需要发送的老师们
	 * @param stu 学生
	 * @param clsStatus 课程状态
	 * @param cls 取消的课程
	 */
	private void sendCxlEmail(Integer userRole,String teaIds, Student stu, int clsStatus, SkClass cls){

		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		teaIds = teaIds.substring(0, teaIds.length()-1);
		List<Teacher> listTeacher = teacherDao.queryTeacherByIDs(teaIds);
		
		Push push = pushDao.queryParams();
		
		//是否发送给助教
		boolean sendFlag = noticeDao.findIsSend(userRole, 
				Constant.NOTICE_TYPE_EMAIL, Constant.USER_ROLE_ADMIN, clsStatus);
		if(sendFlag){
			if( push.getAssEmail() != null && !"".equals(push.getAssEmail()) && RegexUtil.isEmail(push.getAssEmail()) ){
				Email email = new Email();
				String content = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_email");
				content = OtherUtil.replaceContent(content, stu.getEname(), Long.toString(stu.getStu_id()), listTeacher.get(0).getName()
						, dateFormat.format(cls.getBeginTime()), cls.getClsLength().toString());
				String subject = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_title");
				email.setMailContent(content);
				email.setMail_to(push.getAssEmail());
				email.setMail_subject(subject);
				try {
					emailSender.sendMessage(push,email);
				} catch (AuthenticationFailedException e) {
					e.printStackTrace();
					logger.info(e.getMessage());
				} catch (MessagingException e) {
					e.printStackTrace();
					logger.info(e.getMessage());
				}
			}
		}
			
		//是否发邮件给老师
		sendFlag = noticeDao.findIsSend(userRole, 
				Constant.NOTICE_TYPE_EMAIL, Constant.USER_ROLE_TEACHER, clsStatus);
		if(sendFlag){
			for( int i = 0; i < listTeacher.size(); i++){
				if( listTeacher.get(i).getEmail() != null && !"".equals(listTeacher.get(i).getEmail()) && RegexUtil.isEmail(listTeacher.get(i).getEmail()) ){
					Email email = new Email();
					String content = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_email");
					content = OtherUtil.replaceContent(content, stu.getEname(), Long.toString(stu.getStu_id()), listTeacher.get(0).getName()
							, dateFormat.format(cls.getBeginTime()), cls.getClsLength().toString());
					String subject = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_title");
					email.setMailContent(content);
					email.setMail_to(listTeacher.get(i).getEmail());
					email.setMail_subject(subject);
					try {
						emailSender.sendMessage(push,email);
					} catch (AuthenticationFailedException e) {
						e.printStackTrace();
						logger.info(e.getMessage());
					} catch (MessagingException e) {
						e.printStackTrace();
						logger.info(e.getMessage());
					}
				}
			}
		}
		
		//是否发邮件给学生
		if (!Constant.NOTIFY_MAIL_NO.equals(stu.getNotifyMail())) {
			sendFlag = false;
			if (Constant.NOTIFY_MAIL_DEFAULT.equals(stu.getNotifyMail())) {
				sendFlag = noticeDao.findIsSend(userRole, Constant.NOTICE_TYPE_EMAIL,
						Constant.USER_ROLE_STUDENT, clsStatus);
			}
			if (Constant.NOTIFY_MAIL_YES.equals(stu.getNotifyMail()) || sendFlag) {
				if (stu.getEmail() != null && !"".equals(stu.getEmail())
						&& RegexUtil.isEmail(stu.getEmail())) {
					Email email = new Email();
					String content = pushDao.getContent(Constant
							.getStatusInDB(clsStatus) + "_email");
					content = OtherUtil.replaceContent(content, stu.getEname(),
							Long.toString(stu.getStu_id()), listTeacher.get(0)
									.getName(), dateFormat.format(cls
									.getBeginTime()), cls.getClsLength()
									.toString());
					String subject = pushDao.getContent(Constant
							.getStatusInDB(clsStatus) + "_title");
					subject = OtherUtil.replaceContent(subject, stu.getEname(),
							Long.toString(stu.getStu_id()), listTeacher.get(0)
							.getName(), dateFormat.format(cls
							.getBeginTime()), cls.getClsLength()
							.toString());
					email.setMailContent(content);
					email.setMail_to(stu.getEmail());
					email.setMail_subject(subject);
					try {
						emailSender.sendMessage(push, email);
					} catch (AuthenticationFailedException e) {
						e.printStackTrace();
						logger.info(e.getMessage());
					} catch (MessagingException e) {
						e.printStackTrace();
						logger.info(e.getMessage());
					}
				}
			}
		}
		
	}
	
	/**
	 * 取消课程时，发送短信
	 * @param userId 登陆用户Id
	 * @param teaIds 需要发送的老师们
	 * @param stu 学生
	 * @param clsStatus 课程状态
	 * @param cls 取消的课程
	 * @throws Exception 
	 */
	private void sendCxlSMS(Integer userRole,String teaIds, Student stu, int clsStatus, SkClass cls) throws Exception{

		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		
		teaIds = teaIds.substring(0, teaIds.length()-1);
		List<Teacher> listTeacher = teacherDao.queryTeacherByIDs(teaIds);
		
		Push push = pushDao.queryParams();
		
		//是否发送短信给助教
		boolean sendFlag = noticeDao.findIsSend(userRole, 
				Constant.NOTICE_TYPE_MSG, Constant.USER_ROLE_ADMIN, clsStatus);
		if(sendFlag){
			if( push.getAssTel() != null && !"".equals(push.getAssTel()) && RegexUtil.isMobileNO(push.getAssTel()) ){
				String content = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_msg");
				content = OtherUtil.replaceContent(content, stu.getEname(), Long.toString(stu.getStu_id()), listTeacher.get(0).getName()
						, dateFormat.format(cls.getBeginTime()), cls.getClsLength().toString());
				SMSLog smsLog = new SMSLog();
				smsLog.setToPhone(push.getAssTel() );
				smsLog.setText(content);
				smsSender.doSend(push, smsLog);
			}
		}
			
		//是否发短信给老师
		sendFlag = noticeDao.findIsSend(userRole, 
				Constant.NOTICE_TYPE_MSG, Constant.USER_ROLE_TEACHER, clsStatus);
		if(sendFlag){
			for( int i = 0; i < listTeacher.size(); i++){
				if( listTeacher.get(i).getPhone() != null && !"".equals(listTeacher.get(i).getPhone()) && RegexUtil.isMobileNO(push.getAssTel()) ){
					String content = pushDao.getContent(Constant.getStatusInDB(clsStatus)+"_msg");
					content = OtherUtil.replaceContent(content, stu.getEname(), Long.toString(stu.getStu_id()), listTeacher.get(0).getName()
							, dateFormat.format(cls.getBeginTime()), cls.getClsLength().toString());
					SMSLog smsLog = new SMSLog();
					smsLog.setToPhone(listTeacher.get(i).getPhone() );
					smsLog.setText(content);
					smsSender.doSend(push, smsLog);
				}
			}
		}
		
		//是否发短信给学生
		if (!Constant.NOTIFY_SMS_NO.equals(stu.getNotifySMS())) {
			sendFlag = false;
			if (Constant.NOTIFY_SMS_DEFAULT.equals(stu.getNotifySMS())) {
				sendFlag = noticeDao.findIsSend(userRole,
						Constant.NOTICE_TYPE_MSG, Constant.USER_ROLE_STUDENT,
						clsStatus);
			}
			if (Constant.NOTIFY_SMS_YES.equals(stu.getNotifySMS()) || sendFlag) {
				if (stu.getPhone() != null && !"".equals(stu.getPhone())
						&& RegexUtil.isMobileNO(stu.getPhone())) {
					String content = pushDao.getContent(Constant
							.getStatusInDB(clsStatus) + "_msg");
					content = OtherUtil.replaceContent(content, stu.getEname(),
							Long.toString(stu.getStu_id()), listTeacher.get(0)
									.getName(), dateFormat.format(cls
									.getBeginTime()), cls.getClsLength()
									.toString());
					SMSLog smsLog = new SMSLog();
					smsLog.setToPhone(stu.getPhone());
					smsLog.setText(content);
					smsSender.doSend(push, smsLog);
				}
			}
		}
	}
	
	/**
	 * 取消预约课程信息
	 * 
	 * @param clsId
	 *            ,stuLate,teaLate,clsStatus
	 * @return int
	 * @throws Exception 
	 */
	@Transactional
	public int cancleClass(long clsId, int stuLate, int teaLate, int clsStatus,
			int clsType, String clsBookingRemark, String clsComment, User loginUser) throws Exception {

		SkClass cls = queryClassByClsId(clsId);
		cls.setTeaLate(teaLate);
		cls.setClsType(clsType);
		cls.setStuLate(stuLate);
		cls.setClsComment(clsComment);
		cls.setBookingRemark(clsBookingRemark);
		if (0 != clsStatus) {
			cls.setClsStatus(clsStatus);
		}
		Teacher tea = teacherDao.queryById(cls.getTeaId());
		
//		if(cls.getClsStatus() != null){
//			if(Integer.valueOf(Constant.CLASS_STATUS_STU_CXL).equals(cls.getClsStatus())){
//				return 0;
//			}
//			if(Integer.valueOf(Constant.CLASS_STATUS_TEA_CXL).equals(cls.getClsStatus())){
//				return 0;
//			}
//			if(Integer.valueOf(Constant.CLASS_STATUS_STU_EMG_CXL).equals(cls.getClsStatus())){
//				return 0;
//			}
//			if(Integer.valueOf(Constant.CLASS_STATUS_TEA_EMG_CXL).equals(cls.getClsStatus())){
//				return 0;
//			}
//		}

		// 循环次数
		float b = cls.getClsLength() * 2;
		int c = new Float(b).intValue();
		Date dt = cls.getBeginTime();
		Date ymd = new Date();
		ymd.setYear(dt.getYear());
		ymd.setMonth(dt.getMonth());
		ymd.setDate(dt.getDate());
		int lengthNum = 0;
		if (30 == dt.getMinutes()) {
			lengthNum = dt.getHours() * 2 + 1;
		} else {
			lengthNum = dt.getHours() * 2;
		}

		String teaIds = "";
		
		for (int i = 0; i < c; i++) {
			Schedule s = scheduleDao.querySchByLengthAndTeaIdAndDate(lengthNum,
					cls.getTeaId(), cls.getStuId(), ymd);
			s.setStatus(Constant.SCHEDULE_STATUS_FREE);
			scheduleDao.updateSchedule(s);
			teaIds += cls.getTeaId() + ",";
			lengthNum++;
		}

		float blNum = 0;
		String comment = "";
		
		//添加操作者名字以及ID
		if (loginUser.getUser_role() == Constant.USER_ROLE_STUDENT.intValue()) {
			Student tempStu = studentDao.queryStudentByUserId(loginUser
					.getUser_id());
			comment += "[" + loginUser.getUser_name() + "(SID:"
					+ tempStu.getStu_id() + ")]";
		} else if (loginUser.getUser_role() == Constant.USER_ROLE_TEACHER
				.intValue()) {
			Teacher tempTea = teacherDao.queryByUserId(loginUser.getUser_id());
			comment += "[" + loginUser.getUser_name() + "(TID:"
					+ tempTea.getTea_id() + ")]";
		} else {
			comment += "[" + loginUser.getUser_name() + "(ID:"
					+ loginUser.getUser_id() + ")]";
		}
		
		if (clsStatus == 2 || clsStatus == 3) {
			blNum = cls.getClsLength();
			if (clsStatus == 2) {
				comment += " [Stu CXL]";
			}
			if (clsStatus == 3) {
				comment += " [Tea CXL]";
			}
		} else {
			if (clsStatus == 4) {
				blNum = cls.getClsLength() - (float) 0.5;
				comment += " [Stu EMG CXL]";
			}
			if (clsStatus == 5) {
				blNum = cls.getClsLength() + (float) 0.5;
				comment += " [Tea EMG CXL]";
			}
		}
		comment +=  " [" + tea.getName() + "(TID:"
				+ tea.getTea_id() + ") " + cls.getClsLength() + " "
				+ cls.getBeginTimeEx() + "]";
		
		Student stu = studentDao.queryStudentBywwNum2(cls.getEname());
		// 获取学生ID
		Balance bl = new Balance();
		bl.setBlNum(blNum);
		bl.setComment(comment);
		bl.setBalance_before(stu.getBalance());
		bl.setDateTime(new Date());
		bl.setStuId(cls.getStuId());
		balanceDao.insertBlance(bl);
		stu.setBalance(stu.getBalance() + blNum);
		studentDao.updateRegStu(stu);
		
		int result = skClassDao.updateClass(cls);
		
		this.sendCxlEmail(loginUser.getUser_role(),teaIds,stu,clsStatus,cls);
		this.sendCxlSMS(loginUser.getUser_role(),teaIds,stu,clsStatus,cls);
		
		return result;
	}

	/**
	 * 统计老师课程信息
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, String> count(long teaId, Date begin, Date end) {

		StringBuffer conditon = new StringBuffer();

		if (begin != null) {

			conditon.append("  AND CLS_BEGIN_TIME>='" + (begin+" 00:00:00") + "'");

		}
		if (end != null) {

			conditon.append("  AND CLS_BEGIN_TIME<='" + (end+" 23:59:59") + "'");

		}

		return this.skClassDao.count(teaId, conditon.toString());

	}

	/**
	 * 查询课程信息
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Object> queryList(int start, int end,
			Map<String, Object> searchParam) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = (User) searchParam.get("user");
		String sName = (String) searchParam.get("sName");
		String tName = (String) searchParam.get("tName");
		String search_time = (String) searchParam.get("search_time");
		int clsType = Integer.parseInt((searchParam.get("clsType")).toString());
		float clsLength = Float.parseFloat((searchParam.get("clsLength"))
				.toString());
		int clsStatus = Integer.parseInt((searchParam.get("clsStatus"))
				.toString());
		String search_order_by = searchParam.get("order_by").toString()
				.toUpperCase();
		StringBuilder condition = new StringBuilder();
		long teaId = Long.parseLong(searchParam.get("teaId").toString());
		long stuId = Long.parseLong(searchParam.get("stuId").toString());

		if (!("".equals(search_time))) {

			condition.append("AND (SC.CLS_BEGIN_TIME LIKE '%" + search_time
					+ "%')");
		}
		if (teaId != 0) {

			condition.append("AND (SC.TEA_ID=" + teaId + ")");
		}
		if (stuId != 0) {

			condition.append("AND ( SS.STU_ID=" + stuId + ")");
		}

		if (user.getUser_role() == 2) {

//			Teacher t = teacherService.queryByUserId((long) user.getUser_id());
//			condition.append(" AND ( SC.TEA_ID=" + t.getTea_id() + ")");
			condition.append(" AND ( ST.USER_ID=" + user.getUser_id()+ ")");
		}
		if (user.getUser_role() == 3) {

			Student studnet = studentDao.queryStudentBywwNum(user
					.getUser_name());
			condition.append(" AND ( SS.STU_ID=" + studnet.getStu_id() + ")");
		}

		if (!"".equals(sName)) {
			condition.append(" AND (SS.ENAME LIKE '%" + sName + "%'" + ")");
		}
		if (!"".equals(tName)) {
			condition.append(" AND (ST.NAME LIKE '%" + tName + "%'" + ")");
		}
		if (8 != clsStatus) {
			condition.append(" AND (SC.CLS_STATUS = " + clsStatus + ")");
		}
		if (0 != clsLength) {
			condition.append(" AND (SC.CLS_LENGTH =" + clsLength + ")");
		}
		if (0 != clsType) {
			condition.append(" AND (SC.CLS_TYPE =" + clsType + ")");
		}

		String orderBy = "";
		if ("ID_ASC".equals(search_order_by)) {
			orderBy = " ORDER BY SC.CLS_BEGIN_TIME ASC";
		} else if ("ID_DESC".equals(search_order_by)) {
			orderBy = " ORDER BY SC.CLS_BEGIN_TIME DESC";
		} else {
			orderBy = "ORDER BY SC.CLS_BEGIN_TIME DESC";
		}

		result.put("count", skClassDao.queryListCount(condition.toString()));
		List<SkClass> listClass = skClassDao.queryList(condition.toString(), orderBy, start, end);
		result.put("data",listClass);
		

		return result;
	}

	public Map<String, Object> queryList(long teaId, int start, int count) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("count", skClassDao.queryListCount(""));
		List<SkClass> classes = null;
		if (0 == start) {
			classes = skClassDao.queryPastPendingClasses(teaId);
			classes.addAll(skClassDao.queryFucturePendingClasses(teaId));
			classes.addAll(skClassDao.queryAllNonPendingClasses(teaId, start, count));
		} else{
			classes = skClassDao.queryAllNonPendingClasses(teaId, start, count);
		}

		result.put("data", classes);

		return result;
	}


}
