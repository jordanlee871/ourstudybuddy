package com.billjc.framework;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.essay.appointment.dao.EssayAppointmentDao;
import com.billjc.essay.appointment.entity.EssayAppointment;
import com.billjc.essay.balance.dao.EssayBalanceDao;
import com.billjc.essay.balance.entity.EssayBalance;
import com.billjc.essay.student.dao.EssayStudentDao;
import com.billjc.essay.student.entity.EssayStudent;
import com.billjc.framework.util.Constant;
import com.billjc.framework.util.Email;
import com.billjc.framework.util.OtherUtil;
import com.billjc.framework.util.RegexUtil;
import com.billjc.speak.push.dao.PushDao;
import com.billjc.speak.push.entity.Push;
import com.billjc.speak.sk_class.dao.SkClassDao;
import com.billjc.speak.sk_class.entity.SkClass;
import com.billjc.speak.smsLog.entity.SMSLog;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.system.dao.SystemSettingDao;
import com.billjc.speak.system.entity.SystemSetting;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.LogisticsDummySendRequest;
import com.taobao.api.request.TradesSoldIncrementvGetRequest;
import com.taobao.api.response.LogisticsDummySendResponse;
import com.taobao.api.response.TradesSoldIncrementvGetResponse;

@Repository
public class JobAction implements org.quartz.Job { // 继承quartz 的job接口

	private static Logger logger = Logger.getLogger(JobAction.class);

	// 实现job接口的execute函数，在其中简单调用perform()函数就可以了。
	public void execute(org.quartz.JobExecutionContext context) { // 执行报表统计入口函数
		loopServiceExpired(context);
	}

	// 其它的保持不变。

	@Transactional
	@SuppressWarnings({ "unchecked", "finally" })
	public void loopServiceExpired(org.quartz.JobExecutionContext context) {
		// 业务逻辑代码
		try {

			ApplicationContext ctx = getApplicationContext(context);
			String[] beanNameArr = ctx.getBeanDefinitionNames();
			for (int i = 0; i < beanNameArr.length; i++) {
				// System.out.println(beanNameArr[i]);
			}
			this.doNoTrailNotice(ctx);
			this.doTrailNotice(ctx);
			this.chargeEssays(ctx);
			this.checkAndCancelEssays(ctx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * notrail课程提醒操作
	 * 
	 * @param context
	 */
	public void doNoTrailNotice(ApplicationContext ctx) {
		logger.info("-- 做notrail课程提醒通知 " + new Date());
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			Calendar cal = Calendar.getInstance();
			// cal.set(2013, 0, 17, 20, 30, 0); //默认时间：2013-01-18 00:30:00

			SkClassDao skClassDao = ctx.getBean("skClassDao", SkClassDao.class);
			PushDao pushDao = ctx.getBean("pushDao", PushDao.class);
			Push push = pushDao.queryParams();

			if (Integer.valueOf(0).equals(push.getNoTrail())) {
				logger.info("不用提醒");
				return;
			}

			Integer noTrailTime = push.getNoTrail();
			cal.add(Calendar.MINUTE, noTrailTime * 30);
			String now = dateFormat.format(cal.getTime());

			List<SkClass> listClass = skClassDao.queryList(
					"AND DATE_FORMAT(`cls_begin_time`,'%Y-%m-%d %H:%i')=DATE_FORMAT('"
							+ now + "', '%Y-%m-%d %H:%i') ", "", 0, 1000000000);

			logger.info("查询时间：" + now + "\t提醒的课程长度:" + listClass.size());
			for (SkClass skClass : listClass) {
				Long teaId = skClass.getTeaId();
				TeacherDao teacherDao = ctx.getBean("teacherDao",
						TeacherDao.class);
				Teacher teacher = teacherDao.queryById(teaId);
				logger.info("老师ID：" + teaId + "\t老师邮件：" + teacher.getEmail());

				Long stuId = skClass.getStuId();
				StudentDao studentDao = ctx.getBean("studentDao",
						StudentDao.class);
				Student student = studentDao.queryStudentById(stuId);
				logger.info("学生ID：" + stuId + "\t学生邮件：" + student.getEmail());

				Date clsTime = skClass.getBeginTime();

				// 发邮件给学生
				SimpleHtmlMailSender emailSender = ctx.getBean(
						"simpleHtmlMailSender", SimpleHtmlMailSender.class);
				System.out.println("Notrail：计算时间：" + cal.getTime());

				if (student.getEmail() != null
						&& !"".equals(student.getEmail())
						&& RegexUtil.isEmail(student.getEmail())) {
					Email email = new Email();
					String content = pushDao
							.getContent(Constant
									.getStatusInDB(Constant.CLASS_STATUS_NOTRAIL_NOTICE)
									+ "_email");
					content = OtherUtil.replaceContent(content, student
							.getEname(), Long.toString(student.getStu_id()),
							teacher.getName(), dateFormat.format(skClass
									.getBeginTime()), skClass.getClsLength()
									.toString());
					String subject = pushDao
							.getContent(Constant
									.getStatusInDB(Constant.CLASS_STATUS_NOTRAIL_NOTICE)
									+ "_title");
					email.setMailContent(content);
					email.setMail_to(student.getEmail());
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

				// 发短信给学生
				SendMsg_webchinese smsSender = ctx.getBean(
						"sendMsg_webchinese", SendMsg_webchinese.class);

				if (student.getPhone() != null
						&& !"".equals(student.getPhone())
						&& RegexUtil.isMobileNO(student.getPhone())) {
					String content = pushDao
							.getContent(Constant
									.getStatusInDB(Constant.CLASS_STATUS_NOTRAIL_NOTICE)
									+ "_msg");
					content = OtherUtil.replaceContent(content, skClass
							.getEname(), Long.toString(student.getStu_id()),
							teacher.getName(), dateFormat.format(skClass
									.getBeginTime()), skClass.getClsLength()
									.toString());
					SMSLog smsLog = new SMSLog();
					smsLog.setToPhone(student.getPhone());
					smsLog.setText(content);
					smsSender.doSend(push, smsLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * trail课程提醒操作
	 * 
	 * @param context
	 */
	public void doTrailNotice(ApplicationContext ctx) {
		// TrailClass操作
		logger.info("-- 做trail课程提醒通知 " + new Date());
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			Calendar cal = Calendar.getInstance();
			// cal.set(2013, 0, 29, 20, 0, 0); //默认时间：2013-01-29 20:30:00

			SkClassDao skClassDao = ctx.getBean("skClassDao", SkClassDao.class);
			PushDao pushDao = ctx.getBean("pushDao", PushDao.class);
			Push push = pushDao.queryParams();

			if (Integer.valueOf(0).equals(push.getTrail())) {
				logger.info("不用提醒");
				return;
			}

			Integer trailTime = push.getTrail();
			cal.add(Calendar.MINUTE, trailTime * 30);
			String now = dateFormat.format(cal.getTime());

			List<SkClass> listClass = skClassDao.queryList(
					"AND DATE_FORMAT(`cls_begin_time`,'%Y-%m-%d %H:%i')=DATE_FORMAT('"
							+ now + "', '%Y-%m-%d %H:%i') AND `cls_type`="
							+ Constant.CLASS_TYPE_TRIAL, "", 0, 1000000000);

			logger.info("Trail 查询时间：" + now + "\t提醒的课程长度:" + listClass.size());
			for (SkClass skClass : listClass) {
				Long teaId = skClass.getTeaId();
				TeacherDao teacherDao = ctx.getBean("teacherDao",
						TeacherDao.class);
				Teacher teacher = teacherDao.queryById(teaId);
				logger.info("老师ID：" + teaId + "\t老师邮件：" + teacher.getEmail());

				Long stuId = skClass.getStuId();
				StudentDao studentDao = ctx.getBean("studentDao",
						StudentDao.class);
				Student student = studentDao.queryStudentById(stuId);
				logger.info("学生ID：" + stuId + "\t学生邮件：" + student.getEmail());

				Date clsTime = skClass.getBeginTime();

				// 发邮件给助教
				SimpleHtmlMailSender emailSender = ctx.getBean(
						"simpleHtmlMailSender", SimpleHtmlMailSender.class);
				System.out.println("Notrail：计算时间：" + cal.getTime());

				if (push.getAssEmail() != null
						&& !"".equals(push.getAssEmail())
						&& RegexUtil.isEmail(push.getAssEmail())) {
					Email email = new Email();
					String content = pushDao.getContent(Constant
							.getStatusInDB(Constant.CLASS_STATUS_TRAIL_NOTICE)
							+ "_email");
					content = OtherUtil.replaceContent(content, student
							.getEname(), Long.toString(student.getStu_id()),
							teacher.getName(), skClass.getBeginTime()
									.toLocaleString(), skClass.getClsLength()
									.toString());
					String subject = pushDao.getContent(Constant
							.getStatusInDB(Constant.CLASS_STATUS_TRAIL_NOTICE)
							+ "_title");
					email.setMailContent(content);
					email.setMail_to(push.getAssEmail());
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

				// 发短信给学生
				SendMsg_webchinese smsSender = ctx.getBean(
						"sendMsg_webchinese", SendMsg_webchinese.class);

				if (push.getAssTel() != null && !"".equals(push.getAssTel())
						&& RegexUtil.isMobileNO(push.getAssTel())) {
					String content = pushDao.getContent(Constant
							.getStatusInDB(Constant.CLASS_STATUS_TRAIL_NOTICE)
							+ "_msg");
					content = OtherUtil.replaceContent(content, skClass
							.getEname(), Long.toString(student.getStu_id()),
							teacher.getName(), dateFormat.format(skClass
									.getBeginTime()), skClass.getClsLength()
									.toString());
					SMSLog smsLog = new SMSLog();
					smsLog.setToPhone(push.getAssTel());
					smsLog.setText(content);
					smsSender.doSend(push, smsLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	private ApplicationContext getApplicationContext(JobExecutionContext context)
			throws Exception {
		ApplicationContext appCtx = null;
		appCtx = (ApplicationContext) context.getScheduler().getContext()
				.get(APPLICATION_CONTEXT_KEY);
		if (appCtx == null) {
			throw new JobExecutionException(
					"No application context available in scheduler context for key \""
							+ APPLICATION_CONTEXT_KEY + "\"");
		}
		return appCtx;
	}

	public void chargeEssays(ApplicationContext ctx) throws Exception {
		SystemSettingDao ssDao = ctx.getBean("systemSettingDao",
				SystemSettingDao.class);
		SystemSetting ss = ssDao.queryTaobao();
		EssayStudentDao stuDao = ctx.getBean("essayStudentDao",
				EssayStudentDao.class);
		EssayBalanceDao blDao = ctx.getBean("essayBalanceDao",
				EssayBalanceDao.class);

		// 获取淘宝订单
		TaobaoClient client = new DefaultTaobaoClient(ss.getUrlTaobao()
				.getSettingValue(), ss.getAppKeyTaobao().getSettingValue(), ss
				.getAppSecretTaobao().getSettingValue());
		TradesSoldIncrementvGetRequest req = new TradesSoldIncrementvGetRequest();
		req.setFields("tid,buyer_nick,tid,modified,orders");
		Date endTime = new Date();
		req.setEndCreate(endTime);
		Date dateTime = new Date(endTime.getTime() - 1800000);// 30分钟
//		Date dateTime = new Date(endTime.getTime() - 3600000);// 60分钟
//		Date dateTime = new Date(endTime.getTime() - 86400000);// 24小时
		req.setStartCreate(dateTime);
		req.setPageSize(99L);
		req.setStatus(Constant.TAOBAO_WAIT_SELLER_SEND_GOODS);
		TradesSoldIncrementvGetResponse response = client.execute(req, ss
				.getSessionKeyTaobao().getSettingValue());
		if (!response.isSuccess()) {
			throw new Exception("读取淘宝订餐错误, errorCode："
					+ response.getErrorCode());
		}

		// 检查淘宝并在作文系统中充值
		List<Trade> trades = response.getTrades();
		if (trades == null || trades.size() <= 0) {
			logger.info("没有淘宝订单需要处理");
			return;
		}
		List<Long> tradeOrders = new ArrayList<Long>();
		for (Trade trade : trades) {
			boolean chargeOrder = true;
			boolean chargedEssay = blDao.checkCharged(trade.getTid());
			for (Order order : trade.getOrders()) {
				if (ss.getEssayIdTaobao().getSettingValue()
						.equals(order.getNumIid().toString())) {// 作文订单
					chargeOrder = chargeOrder & true;

					if (!order.getRefundStatus().equals(
							Constant.TAOBAO_REFUND_STATUS_NO)) {// 是否在申请退款
						logger.info("[作文系统]退款中:" + trade.getTid());
						chargeOrder = chargeOrder & false;
						continue;
					}

					List<EssayStudent> listStu = stuDao.queryStudent(trade
							.getBuyerNick());
					if (0 == listStu.size()) {
						logger.info("[作文系统]未注册：" + trade.getBuyerNick());
						chargeOrder = chargeOrder & false;
						continue;
					}
					if (!chargedEssay) {// 没充值过
						EssayStudent stu = listStu.get(0);
						EssayBalance bl = new EssayBalance();
						bl.setNum(order.getNum());
						bl.setStuId(stu.getStuId());
						bl.setAssId(19L);
						bl.setCreateTime(new Date());
						bl.setTaobaoOrder(trade.getTid().toString());
						if (ss.getEssayPart1SkuTaobao().getSettingValue()
								.equals(order.getSkuPropertiesName())) {
							logger.info("[作文系统]充值part1：" + trade.getBuyerNick()
									+ ";" + trade.getTid());
							bl.setEssayType(Constant.ESSAY_DB_PART1);
							bl.setAmount(stu.getAmountTask1());
							blDao.insertBalance(bl);
							if (0 >= stuDao.upateBalance(stu,
									Constant.ESSAY_DB_PART1, order.getNum())) {
								chargeOrder = chargeOrder & false;
							}
						} else if (ss.getEssayPart2SkuTaobao()
								.getSettingValue()
								.equals(order.getSkuPropertiesName())) {
							logger.info("[作文系统]充值part2：" + trade.getBuyerNick()
									+ ";" + trade.getTid());
							bl.setEssayType(Constant.ESSAY_DB_PART2);
							bl.setAmount(stu.getAmountTask2());
							blDao.insertBalance(bl);
							if (0 >= stuDao.upateBalance(stu,
									Constant.ESSAY_DB_PART2, order.getNum())) {
								chargeOrder = chargeOrder & false;
							}
						} else if (ss.getEssay7D5ESkuTaobao()
								.getSettingValue()
								.equals(order.getSkuPropertiesName())) {
							logger.info("[作文系统]7天5篇：" + trade.getBuyerNick()
									+ ";" + trade.getTid());
							bl.setEssayType(Constant.ESSAY_DB_PART2);
							bl.setAmount(5L);
							blDao.insertBalance(bl);
							if (0 >= stuDao.upateBalance(stu,
									Constant.ESSAY_DB_PART2, 5L)) {
								chargeOrder = chargeOrder & false;
							}
						}else{
							logger.info("[作文系统]未知订单作文类型：" + trade.getBuyerNick()
									+ ";" + trade.getTid());
							chargeOrder = chargeOrder & false;
						}
					} else {
						logger.info("[作文系统]已经充值：" + trade.getBuyerNick() + ";"
								+ trade.getTid());
						chargeOrder = chargeOrder & false;
						continue;
					}
				} else {
					chargeOrder = chargeOrder & false;
					logger.info("[作文系统]非作文订单：" + trade.getBuyerNick() + ";"
							+ trade.getTid());
				}
			}
			if (chargeOrder) {// 准备发货
				tradeOrders.add(trade.getTid());
			}
		}

		// 发货
		for (Long orderId : tradeOrders) {
			LogisticsDummySendRequest reqForSendTaobaoOrder = new LogisticsDummySendRequest();
			reqForSendTaobaoOrder.setTid(orderId);
			LogisticsDummySendResponse responseForSendTaobaoOrder = client
					.execute(reqForSendTaobaoOrder, ss.getSessionKeyTaobao()
							.getSettingValue());
			if (!responseForSendTaobaoOrder.isSuccess()) {
				throw new Exception("");
			}
			logger.info("[作文系统]发货：" + orderId);
		}
	}

	private void checkAndCancelEssays(ApplicationContext ctx) throws Exception {
		EssayStudentDao stuDao = ctx.getBean("essayStudentDao",
				EssayStudentDao.class);
		EssayAppointmentDao appDao = ctx.getBean("essayAppointmentDao",
				EssayAppointmentDao.class);
		PushDao pushDao = ctx.getBean("pushDao", PushDao.class);
		Push push = pushDao.queryParams();
		SendMsg_webchinese smsSender = ctx.getBean("sendMsg_webchinese",
				SendMsg_webchinese.class);
		@SuppressWarnings("deprecation")
		// Date now = new Date(2013-1900,11,12,12,0);
		Date now = new Date();
		Date remindChkStartTime = new Date(now.getTime() - 2700000);// 45分钟前
		Date remindChkEndTime = new Date(now.getTime() - 2100000);// 35分钟前
		Date cancelStartTime = new Date(now.getTime() - 7200000);// 120分钟前
		Date cancelEndTime = new Date(now.getTime() - 3600000);// 60分钟前

		List<EssayStudent> stusRemind = stuDao.queryStudent(remindChkStartTime,
				remindChkEndTime);
		// 发短信提示学生：你的作文余额尚欠【n小】【n大】，请20分钟内用你的旺旺在淘宝上购买或手动取消作文，否则系统会在20分钟后取消你最近创建预约的一些作文。咨询请电15918718440.
		for (EssayStudent student : stusRemind) {
			String warnMsg = "您好，您的作文批改余额不足，尚欠";
			if (student.getAmountTask1() < 0) {
				warnMsg += (-student.getAmountTask1()) + "篇小作文";
			}
			if (student.getAmountTask1() < 0 & student.getAmountTask2() < 0) {
				warnMsg += "以及";
			}
			if (student.getAmountTask2() < 0) {
				warnMsg += (-student.getAmountTask2()) + "篇大作文";
			}
			warnMsg += ",请尽快充值，不然系统会在15分钟后取消您的作文，有问题请电询:15918718440.";
//			System.out.println(warnMsg);
			SMSLog smsLog = new SMSLog();
			smsLog.setToPhone(student.getPhone());
			smsLog.setText(warnMsg);
			smsSender.doSend(push, smsLog);
		}

		// 对于1个小时之前2个小时内预约的作文，如果余额小于0，则取消最新的作文。
		List<EssayStudent> stusCancel = stuDao.queryStudent(cancelStartTime,
				cancelEndTime);// 查找预约了的学生
		List<Long> appointIdsForCancel = new ArrayList<Long>();
		List<EssayAppointment> apptsForCancel = new ArrayList<EssayAppointment>();
		for (EssayStudent student : stusCancel) {
			if (0 > student.getAmountTask1()) {
				apptsForCancel.addAll(appDao.queryAppt(student.getStuId(),
						-student.getAmountTask1(), Constant.ESSAY_DB_PART1));// 获取要取消的作文
			}
			if (0 > student.getAmountTask2()) {
				apptsForCancel.addAll(appDao.queryAppt(student.getStuId(),
						-student.getAmountTask2(), Constant.ESSAY_DB_PART2));// 获取要取消的作文
			}
		}
		if (0 < apptsForCancel.size()) {// 没结果
			// 获取作文ID，再一次过取消
			for (EssayAppointment appt : apptsForCancel) {
				logger.info("[作文系统]取消作文：" + appt.getAppointId());
				appointIdsForCancel.add(appt.getAppointId());
			}
			appDao.cancelAllAppt(appointIdsForCancel);

			// 修正余额
			for (EssayStudent student : stusCancel) {
				if (0 > student.getAmountTask1()) {
					logger.info("[作文系统]修正小作文余额：studentID:" + student.getStuId());
					stuDao.fixBalance(student.getStuId(),
							Constant.ESSAY_DB_PART1);
				}
				if (0 > student.getAmountTask2()) {
					logger.info("[作文系统]修正大作文余额：studentID:" + student.getStuId());
					stuDao.fixBalance(student.getStuId(),
							Constant.ESSAY_DB_PART2);
				}
			}
		}

	}
}