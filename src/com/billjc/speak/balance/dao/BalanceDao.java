package com.billjc.speak.balance.dao;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.speak.balance.entity.Balance;
import com.billjc.speak.balance.entity.mapper.BalanceRowMapper;
import com.billjc.speak.privateset.entity.Privateset;
import com.billjc.speak.privateset.entity.mapper.PrivatesetRowMapper;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.entity.mapper.UserRowMapper;

@Repository
public class BalanceDao extends  BaseJdbcDao{
	final Logger logger = LoggerFactory.getLogger(BalanceDao.class);

	
	
	
	
	/**
	 * 新增充值记录
	 * 
	 * @param bl
	 * @return 
	 */
	
	public int insertBlance(Balance bl){
		
		String sql = " INSERT INTO sk_balance_log(BL_NUM,BL_COMMENT,STU_ID,BL_BALANCE_BEFORE,BL_DATETIME,IS_CHARGE) VALUES(?,?,?,?,NOW(),1)";
		Object[] param = new Object[] {bl.getBlNum(), bl.getComment(),bl.getStuId(),bl.getBalance_before()};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
		
	}
	
	/**
	 * 更新充值记录
	 * 
	 * @param bl
	 * @return 
	 */
	
	public int updateBlance(Balance bl, Long stuId){
		
		String sql = "UPDATE sk_balance_log SET BL_NUM=?, BL_COMMENT=?, BL_BALANCE_BEFORE =? ,BL_DATETIME=CURDATE()  WHERE STU_ID=?";
		Object[] param = new Object[] {bl.getBlNum(), bl.getComment(),bl.getBalance_before(),stuId};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
		
		
		
	}
	
	
	/**
	 * 查询充值记录列表
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Balance> queryList(String condition, String orderBy,int start, int end) {
		String sql = "SELECT * FROM sk_balance_log "+ condition + orderBy +" LIMIT ?,?";
		Object[] param = new Object[] { start,end  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new BalanceRowMapper());
	}
	
	
	/**
	 * 查询充值记录数量
	 * 
	 * @param condition
	 * @return
	 */
	
	public int queryListCount(String condition) {
		
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM sk_balance_log  " +condition);
	}

}
