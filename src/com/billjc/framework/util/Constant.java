package com.billjc.framework.util;

public class Constant {

	/**
	 * 登录用户SESSION
	 */
	public static final String LOGIN_USER = "LOGIN_USER";

	/**
	 * 列表每页显示的数量
	 */
	public static final int LIST_PAGE_SIZE = 50;

	/**
	 * 邮件和短信配置复选框个数
	 */
	public static final int NOTICE_CHECK_COUNT = 90;

	/**
	 * 提醒类型-邮件
	 */
	public static final Integer NOTICE_TYPE_EMAIL = 1;
	/**
	 * 提醒类型-短信
	 */
	public static final Integer NOTICE_TYPE_MSG = 2;

	/**
	 * 学生个别SMS提醒 - 发送
	 */
	public static final String NOTIFY_SMS_YES = "Y";
	/**
	 * 学生个别SMS提醒 - 不发送
	 */
	public static final String NOTIFY_SMS_NO = "N";
	/**
	 * 学生个别SMS提醒 - 依据默认设置
	 */
	public static final String NOTIFY_SMS_DEFAULT = "D";

	/**
	 * 学生个别邮件提醒 - 发送
	 */
	public static final String NOTIFY_MAIL_YES = "Y";
	/**
	 * 学生个别邮件提醒 - 不发送
	 */
	public static final String NOTIFY_MAIL_NO = "N";
	/**
	 * 学生个别邮件提醒 - 依据默认设置
	 */
	public static final String NOTIFY_MAIL_DEFAULT = "D";

	/**
	 * 角色类型-学生
	 */
	public static final Integer USER_ROLE_STUDENT = 3;
	/**
	 * 角色类型-老师
	 */
	public static final Integer USER_ROLE_TEACHER = 2;
	/**
	 * 角色类型-助教
	 */
	public static final Integer USER_ROLE_ADMIN = 1;

	/**
	 * 时间表记录状态-空闲
	 */
	public static final long SCHEDULE_STATUS_FREE = 1;
	/**
	 * 时间表记录状态-关闭
	 */
	public static final long SCHEDULE_STATUS_CLOSE = 0;

	/**
	 * 课程取消退回
	 */
	public static final Float CLASS_SPLIT_CANCELLED = (float) 0.5;

	/**
	 * 课程缺席退回
	 */
	public static final Float CLASS_SPLIT_ABSENT = (float) 0.5;

	/**
	 * 时间表记录状态-未上
	 */
	public static final int CLASS_STATUS_PENDING = 0;
	/**
	 * 时间表记录状态-已上完
	 */
	public static final int CLASS_STATUS_COMPLETED = 1;
	/**
	 * 时间表记录状态-学生正常取消
	 */
	public static final int CLASS_STATUS_STU_CXL = 2;
	/**
	 * 时间表记录状态-老师正常取消
	 */
	public static final int CLASS_STATUS_TEA_CXL = 3;
	/**
	 * 时间表记录状态-学生紧急取消
	 */
	public static final int CLASS_STATUS_STU_EMG_CXL = 4;
	/**
	 * 时间表记录状态-老师紧急取消
	 */
	public static final int CLASS_STATUS_TEA_EMG_CXL = 5;
	/**
	 * 时间表记录状态-学生缺席
	 */
	public static final int CLASS_STATUS_STU_ABSENT = 6;
	/**
	 * 时间表记录状态-老师缺席
	 */
	public static final int CLASS_STATUS_TEA_ABSENT = 7;
	/**
	 * 预约课程
	 */
	public static final int CLASS_STATUS_BOOK = 8;
	/**
	 * notrail课程提醒
	 */
	public static final int CLASS_STATUS_NOTRAIL_NOTICE = 9;
	/**
	 * trail课程提醒
	 */
	public static final int CLASS_STATUS_TRAIL_NOTICE = 10;

	/**
	 * 空闲时间显示的数据条数
	 */
	public static final int FREETIME_PAGESIZE = 1000;

	/**
	 * 所有课程
	 */
	public static final int CLASS_TYPE_ALL = 0;
	/**
	 * IELTS课程
	 */
	public static final int CLASS_TYPE_IELTS = 1;
	/**
	 * TOEFL课程
	 */
	public static final int CLASS_TYPE_TOEFL = 2;
	/**
	 * OTHER课程
	 */
	public static final int CLASS_TYPE_OTHER = 3;
	/**
	 * TRIAL课程
	 */
	public static final int CLASS_TYPE_TRIAL = 4;

	/**
	 * 页面错误信息：无法查找到学生
	 */
	public static final String VIEW_EC_STUDENT_NOT_FOUNT = "1";

	/**
	 * 淘宝交易状态。 可选值 TRADE_NO_CREATE_PAY(没有创建支付宝交易)
	 * WAIT_BUYER_PAY(等待买家付款) WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * SELLER_CONSIGNED_PART（卖家部分发货） WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功) TRADE_CLOSED(交易关闭)
	 * TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭)
	 */
	public static final String TAOBAO_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
	public static final String TAOBAO_WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";
	public static final String TAOBAO_SELLER_CONSIGNED_PART = "SELLER_CONSIGNED_PART";
	public static final String TAOBAO_WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";
	public static final String TAOBAO_TRADE_FINISHED = "TRADE_FINISHED";
	public static final String TAOBAO_TRADE_CLOSED = "TRADE_CLOSED";
	public static final String TAOBAO_TRADE_CLOSED_BY_TAOBAO = "TRADE_CLOSED_BY_TAOBAO";
	public static final String TAOBAO_REFUND_STATUS_NO = "NO_REFUND";
	
	/**
	 * 作文系统数据库中小作文类型
	 */
	public static final String ESSAY_DB_PART1="task1";
	/**
	 * 作文系统数据库中大作文类型
	 */
	public static final String ESSAY_DB_PART2="task2";
	/**
	 * 作文系统数据库中“其他”作文类型
	 */
	public static final String ESSAY_DB_OTHER="other";

	public static String getClsTypeName(int status) {
		if (status == CLASS_TYPE_IELTS) {
			return "IELTS";
		} else if (status == CLASS_TYPE_TOEFL) {
			return "TOEFL";
		} else if (status == CLASS_TYPE_OTHER) {
			return "OTHER";
		} else if (status == CLASS_TYPE_TRIAL) {
			return "TRIAL";
		}
		return "";
	}

	public static String getStatusInfo(int status) {
		if (status == CLASS_STATUS_PENDING) {
			return "Pending";
		} else if (status == CLASS_STATUS_COMPLETED) {
			return "Completed";
		} else if (status == CLASS_STATUS_STU_CXL) {
			return "Stu Cancelled";
		} else if (status == CLASS_STATUS_TEA_CXL) {
			return "Tea Cancelled";
		} else if (status == CLASS_STATUS_STU_EMG_CXL) {
			return "Stu EMG Cancelled";
		} else if (status == CLASS_STATUS_TEA_EMG_CXL) {
			return "Tea EMG Cancelled";
		} else if (status == CLASS_STATUS_STU_ABSENT) {
			return "Stu Absent";
		} else {
			return "Tea Absent";
		}
	}

	public static String getStatusInDB(int status) {
		if (status == CLASS_STATUS_BOOK) {
			return "book";
		} else if (status == CLASS_STATUS_COMPLETED) {
			return "comp";
		} else if (status == CLASS_STATUS_STU_CXL) {
			return "stu_cxl";
		} else if (status == CLASS_STATUS_TEA_CXL) {
			return "tea_cxl";
		} else if (status == CLASS_STATUS_STU_EMG_CXL) {
			return "stu_emcxl";
		} else if (status == CLASS_STATUS_TEA_EMG_CXL) {
			return "tea_emcxl";
		} else if (status == CLASS_STATUS_STU_ABSENT) {
			return "stu_ab";
		} else if (status == CLASS_STATUS_TEA_ABSENT) {
			return "tea_ab";
		} else if (status == CLASS_STATUS_NOTRAIL_NOTICE) {
			return "no_trail_notice";
		} else if (status == CLASS_STATUS_TRAIL_NOTICE) {
			return "trail_notice";
		} else {
			return "";
		}
	}

	/**
	 * 将短信的结果转换为说明
	 * 
	 * @param result
	 *            短信的结果
	 * @return
	 */
	public static String getSMSCaption(String result) {
		if ("-1".equals(result)) {
			return "没有该用户账户";
		} else if ("-2".equals(result)) {
			return "密钥不正确";
		} else if ("-3".equals(result)) {
			return "短信数量不足";
		} else if ("-11".equals(result)) {
			return "该用户被禁用";
		} else if ("-14".equals(result)) {
			return "短信内容出现非法字符";
		} else if ("-4".equals(result)) {
			return "手机号格式不正确";
		} else if ("-41".equals(result)) {
			return "手机号码为空";
		} else if ("-42".equals(result)) {
			return "手机号码为空";
		} else if (Integer.parseInt(result) > 0) {
			return "短信发送数量：" + result;
		} else {
			return "未知错误：" + result;
		}
	}
}
