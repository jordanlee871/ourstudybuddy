package com.billjc.essay.balance.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.essay.balance.entity.EssayBalance;
import com.billjc.framework.BaseJdbcDaoEssay;
import com.billjc.speak.students.dao.StudentDao;

@Repository
public class EssayBalanceDao extends BaseJdbcDaoEssay {
	final Logger logger = LoggerFactory.getLogger(StudentDao.class);

	/**
	 * 新增充值记录
	 * 
	 * @param student
	 * @return
	 */
	public int insertBalance(EssayBalance bl) {
		String sql = "INSERT INTO charge_record(AMOUNT,WANGWANGNO,STUDENT_ID,ASS_ID,CREATE_TIME,TYPE,NUM,ORDER_NO) VALUES(?,?,?,?,?,?,?,?)";
		Object[] param = new Object[] {bl.getAmount(),bl.getComment(),bl.getStuId(),bl.getAssId(),bl.getCreateTime(),bl.getEssayType(),bl.getNum(),bl.getTaobaoOrder()};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}

	/**
	 * 检查淘宝订单是否已经充值
	 * 
	 * @param oid
	 *            淘宝订单号
	 * @return true： 已经充值。 false：没充值过
	 */
	public boolean checkCharged(Long oid) {
		String sql = "SELECT COUNT(*) FROM charge_record WHERE order_no = ?";
		Object[] param = new Object[] { oid };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().queryForInt(sql, param) > 0;
	}
}
