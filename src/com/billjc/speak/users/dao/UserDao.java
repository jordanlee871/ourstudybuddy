package com.billjc.speak.users.dao;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.framework.BaseJdbcDao;
import com.billjc.framework.util.MD5Util;
import com.billjc.speak.users.entity.User;
import com.billjc.speak.users.entity.mapper.UserRowMapper;

@Repository
public class UserDao extends BaseJdbcDao {

	final Logger logger = LoggerFactory.getLogger(UserDao.class);

	/**
	 * 验证登录，返回User对象
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User checkLogin(String loginName, String password) {
		String sql = " SELECT * FROM sk_user R WHERE R.USER_NAME =? AND R.USER_PWD=? ";
		Object[] param = new Object[] { loginName, password };
		return this.queryForObject(sql, param, new UserRowMapper());
	}
	
	/**
	 * 根据用户名查找用户
	 * @param name
	 * @return
	 */
	public User queryByName(String name) {
		String sql = "SELECT * FROM sk_user T WHERE T.USER_NAME=?";
		Object[] param = new Object[] { name };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new UserRowMapper());
	}

	
	
	
	public int queryListCount(String condition) {
		return this.getJdbcTemplate().queryForInt("SELECT COUNT(1) FROM sk_user T WHERE 1=1 " +condition);
	}

	/**
	 * 查询用户列表
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<User> queryList(String condition, String orderBy,int start, int end) {
		String sql = "SELECT * FROM sk_user T  WHERE 1=1  "+ condition + orderBy +" LIMIT ?,?";
		Object[] param = new Object[] { start,end  };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().query(sql, param, new UserRowMapper());
	}

	
	
	
	
	
	/**
	 * 修改管理员登录密码
	 * 
	 * @param userId
	 * @param newPass
	 * @return
	 */
	@Transactional
	public int updatePwd(long userId, String newPass) {
		String sql = "UPDATE sk_user T SET T.USER_PWD=? WHERE T.USER_ID=?";
		Object[] param = new Object[] { newPass, userId };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	public int insertUser(User user) {
		String sql = " INSERT INTO sk_user(USER_NAME,USER_PWD,CREATE_TIME) VALUES(?,?,CURDATE())";
		Object[] param = new Object[] {  user.getUser_name(), user.getUser_pwd()};
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	
	public int inserRegisterUser(User user){
		String sql=" INSERT INTO sk_user(USER_NAME,USER_PWD,CREATE_TIME,USER_ROLE)VALUES(?,?,CURDATE(),?)";
	    Object[] param= new Object[]{user.getUser_name(),user.getUser_pwd(),user.getUser_role()};
	    logger.info(sql.replaceAll("\\?", "{}"), param);
	    return this.getJdbcTemplate().update(sql, param);
	
	}
	
	//删除用户
	@Transactional
	public int deleteUsers(String boxListValue){
		
		//先删除子表再删除主表
		String sql1 = " DELETE FROM sk_user WHERE USER_ID IN("+boxListValue+") ";
		return this.getJdbcTemplate().update(sql1);

	}
	
	// 查询管理员
	public User queryUserById(int id) {
		String sql = "SELECT * FROM sk_user T WHERE T.USER_ID=?";
		Object[] param = new Object[] { id };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new UserRowMapper());
	}
	
	// 修改管理员信息
	public int updateUserInfo(User user) {
		long id = user.getUser_id();
		String sql = " UPDATE sk_user  SET USER_NAME=?, USER_PWD=? WHERE USER_ID=" + id + " ";
		Object[] param = new Object[] { user.getUser_name(), user.getUser_pwd() };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.getJdbcTemplate().update(sql, param);
	}
	
	// 删除单个管理员

	public void deleteUserById(int id) {
		String sql = "DELETE FROM sk_user WHERE USER_ID=" + id;
		this.getJdbcTemplate().update(sql);

	}
	
	// 修改前匹配原始密码
	public User queryPassWordById(int id, String pwd) {
		String password = MD5Util.encode32L(pwd);
		String sql = "SELECT * FROM sk_user T WHERE T.USER_ID=? AND T.USER_PWD=?";
		Object[] param = new Object[] { id, password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new UserRowMapper());
	}
	
	// 修改前匹配原始密码
	public User queryStudentOldPassWordById(String user_name, String pwd) {
		String password = MD5Util.encode32L(pwd);
		String sql = "SELECT * FROM sk_user T WHERE T.USER_NAME=? AND T.USER_PWD=?";
		Object[] param = new Object[] { user_name, password };
		logger.info(sql.replaceAll("\\?", "{}"), param);
		return this.queryForObject(sql, param, new UserRowMapper());
	}

	public void changeStatus(User user) {
		int status=user.getStatus();
		if(status==1){
			status=0;
		}else if(status==0){
			status=1;
		}
		String sql="UPDATE sk_user SET STATUS = ? WHERE USER_ID= ?";
		Object[] param = new Object[] { status, user.getUser_id() };
		this.getJdbcTemplate().update(sql,param);
	}
	
}
