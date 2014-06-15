package com.billjc.speak.users.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.billjc.speak.users.dao.UserDao;
import com.billjc.speak.users.entity.User;


@Service
public class UserService {
	final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 验证登录，返回User对象
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User checkLogin(String loginName, String password){
		return userDao.checkLogin(loginName, password);
	}
	
	/**
	 * 根据用户名查找用户
	 * @param name
	 * @return
	 */
	public User queryByName(String name) {
		return userDao.queryByName(name);
	}
	
	// 单个和批量删除活动
	public int deleteUsers(String boxListValue){
		 return this.userDao.deleteUsers(boxListValue);
	}
	
	/**
	 * 查询用户列表
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Object> queryList(int start, int end,Map<String, String> searchParam){
		Map<String, Object> result = new HashMap<String, Object>();
		
		int search_role = Integer.parseInt(!searchParam.get("search_role").equals("")? searchParam.get("search_role"):"0");
		String search_name = searchParam.get("search_name");
		String search_order_by = searchParam.get("order_by").toUpperCase();
		StringBuilder condition = new StringBuilder();
		if(search_role != 0){
			condition.append(" AND T.USER_ROLE = "+search_role+"");
		}
		if(!"".equals(search_name)){
			condition.append(" AND (T.USER_NAME LIKE '%"+search_name+"%'"+")");
		}
		
		String orderBy = "";
		if("ID_ASC".equals(search_order_by)){
			orderBy = " ORDER BY T.USER_ID ASC";
		}else if("ID_DESC".equals(search_order_by)){
			orderBy = " ORDER BY T.USER_ID DESC";
		}else{
			orderBy = " ORDER BY T.USER_ID DESC";
		}
		
		result.put("count", userDao.queryListCount(condition.toString()));
		result.put("data", userDao.queryList(condition.toString(),orderBy,start, end));
		
		return result;
	}
	
	
	
	
	
	
	/**
	 * 修改管理员登录密码
	 * @param userId
	 * @param newPass
	 * @return
	 */
	public int updatePwd(long userId, String newPass){
		return userDao.updatePwd(userId, newPass);
	}
	
	
	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}
	
	//注册时新增用户
	 public int inserRegisterUser(User user){
		 return userDao.inserRegisterUser(user);
	 }
	
	/**
	 * 根据ID查询管理员个人信息
	 * 
	 * @param id
	 * @return
	 */
	
	public User queryById(int id ) {
		return userDao.queryUserById(id);
	}
	
	public int updateUserInfo(User user)
	{
		return  this.userDao.updateUserInfo(user);
	}
	
	
	public void deleteUserById(int id){
		userDao.deleteUserById(id);
	}
	
	public User queryPassWord(int id,String pwd){
		return	userDao.queryPassWordById(id,pwd);
		}
	
	//判断学生修改密码时原始密码是否正确
	public User queryStudentOldPassWord(String user_name,String pwd){
		return	userDao.queryStudentOldPassWordById(user_name, pwd);
		}

	public void changeStatus(User user) {
		userDao.changeStatus(user);
	}
	
}
