package com.billjc.speak.balance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.speak.balance.dao.BalanceDao;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.privateset.dao.PrivatesetDao;
import com.billjc.speak.privateset.entity.Privateset;
import com.billjc.speak.students.dao.StudentDao;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;

@Service
public class BalanceService {
	final Logger logger = LoggerFactory.getLogger(BalanceService.class);
	@Autowired
	private PrivatesetDao privatesetDao;
	@Autowired
	private TeacherDao teacherDao;

	@Autowired
	private BalanceDao balanceDao;

	@Autowired
	private StudentDao studentDao;

	/**
	 * 查询充值记录列表
	 * 
	 * @param
	 * @return
	 */

	public Map<String, Object> queryList(int start, int end,
			Map<String, String> searchParam) {

		Map<String, Object> result = new HashMap<String, Object>();

		String search_name = searchParam.get("search_name");
		String search_order_by = searchParam.get("order_by").toUpperCase();
		String stu_id = searchParam.get("stu_id");
		
		
		StringBuilder condition = new StringBuilder(" WHERE 1>0 ");
		Student student_temp=null;
		if(!"".equals(search_name)){
			
		 student_temp =studentDao.queryStudentBywwNum(search_name);
		 
		 if (null!=student_temp) {
			 
		 
		 condition.append(" AND STU_ID = " + student_temp.getStu_id() );
		 
		 }else {
			 condition.append(" AND STU_ID = 0" );
			 
		 }
		 
		}
		
		if(stu_id!=null && stu_id.length()>0) {
			condition.append(" AND STU_ID = " + stu_id );
		}
		

		String orderBy = "";
		if ("ID_ASC".equals(search_order_by)) {
			orderBy = " ORDER BY BL_DATETIME ASC";
		} else if ("ID_DESC".equals(search_order_by)) {
			orderBy = " ORDER BY BL_DATETIME DESC";
		} else {
			orderBy = " ORDER BY BL_DATETIME DESC";
		}

		result.put("count",balanceDao.queryListCount(condition.toString()));
		
		
		List<Balance> blList= balanceDao.queryList(condition
				.toString(), orderBy, start, end);
		
		
		result.put("data", blList);

		return result;
	}

	/**
	 * 给某个学生充值
	 * 
	 * @param stuId
	 *            bl
	 * 
	 * @return
	 */
	@Transactional
	public boolean charge(Long stuId, Balance bl) {

		Student stu = studentDao.queryStudentById(stuId);

		Float balance = stu.getBalance() + bl.getBlNum();

		if (balance >= 0) {

			bl.setBalance_before(stu.getBalance());

			stu.setBalance(balance);

			balanceDao.insertBlance(bl);

			studentDao.updateRegStu(stu);

			return true;

		} else {

			return false;
		}

	}
}
