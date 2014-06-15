package com.billjc.speak.teacher.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.framework.util.SessionUtil;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;
import com.billjc.speak.users.entity.User;

@Service
public class TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	/**
	 * 增加老师
	 * @param teacher
	 * @return
	 */
	public int innerTeacher(Teacher teacher) {
		return teacherDao.innerTeacher(teacher);
	}

	public List selectByUid(Long uid) {
		return teacherDao.selectByUid(uid);
	}

	/**
	 * 获取老师列表
	 * @param searchParam
	 * 
	 **/
	public Map<String, Object> teacherList(int start, int end,
			Map<String, String> searchParam) {
		Map<String, Object> result = new HashMap<String, Object>();

		String search_name = searchParam.get("search_name");
		String search_order_by = searchParam.get("order_by").toUpperCase();
		StringBuilder condition = new StringBuilder();
		if (!"".equals(search_name)) {
			condition.append(" AND (T.NAME LIKE '%" + search_name + "%'" + ")");
		}

		String orderBy = "";
		if ("GROUPID_ASC".equals(search_order_by)) {
			orderBy = " ORDER BY T.NAME ASC";
		} else if ("GROUPID_DESC".equals(search_order_by)) {
			orderBy = " ORDER BY T.NAME DESC";
		} else {
			orderBy = " ORDER BY T.NAME ASC";
		}

		result.put("count", teacherDao.queryListCount(condition.toString()));
		result.put("data", teacherDao.queryList(condition.toString(), orderBy,
				start, end));

		return result;
	}

	/**
	 * 根据老师名称查找老师
	 * 
	 * @param name
	 * @return
	 */
	public Teacher queryByName(String name) {
		return teacherDao.queryByName(name);
	}

	/**
	 * 查找所有老师
	 * 
	 */

	public List<Teacher> queryAllTeachers() {
		return teacherDao.queryAllTeachers();
	}

	/**
	 * 根据Id查找老师
	 * 
	 */
	public Teacher queryById(long teaId) {
		// TODO Auto-generated method stub
		return teacherDao.queryById(teaId);
	}

	/**
	 * 根据userId查找老师
	 * 
	 */
	public Teacher queryByUserId(long userId) {
		// TODO Auto-generated method stub
		return teacherDao.queryByUserId(userId);
	}

	/**
	 * 根据User_id删除老师
	 * @param id
	 */
	public void deleteTeacherByUserId(Long id) {
		teacherDao.deleteTeacherByUserId(id);
	}

	/**
	 * 老师端操作,根据英文名查找学生(有分页)
	 */

	public Map<String, Object> queryStudentFromTeacherByEname(String ename,
			int start, int end) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", teacherDao.queryStudentFromTeacherCount(ename,
				start, end));
		result.put("data", teacherDao.queryStudentFromTeacherByEname(ename));
		return result;

	}

	/**
	 * 老师端操作, 根据课程类型和时间查找学生(有分页)
	 */
	public Map<String, Object> queryStudentByCLSType(Date cls_begin_time,
			int cls_type, int start, int end) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("count", teacherDao.queryStudentCount(cls_begin_time,
				cls_type, start, end));
		result.put("data", teacherDao.queryStudentByCLSType(cls_begin_time,
				cls_type));
		return result;

	}

	/**
	 * 更新老师信息
	 * @param t
	 * @return
	 */
	public int updateTeacher(Teacher t) {
		return teacherDao.updateTeacher(t);
	}

	// 查找学生---sky
	public Map<String, Object> queryStudent(int start, int end, Map<String, String> searchParam,long teacherID ) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
	
		
		String stu_id = searchParam.get("stu_id");
		String qq_num = searchParam.get("qq_num");
		String skype_num = searchParam.get("skype_num");

		String ename = searchParam.get("ename");
		
		String tea_name1=searchParam.get("tea_name");
		
	   
		 int classType=Integer.parseInt(searchParam.get("cls_type"));
		
		String cls_begin_time = searchParam.get("cls_begin_time");

		StringBuilder condition = new StringBuilder();

		if (!("".equals(stu_id))) {
			
		    condition.append(" AND ST.STU_ID ='" + stu_id + "' ");
	    }
		if(!("".equals(qq_num))){
			 condition.append(" AND ST.QQ_NUM = '"+qq_num+"' ");
		 } 
	    if(!("".equals(skype_num))){
			 condition.append(" AND ST.SKYPE_NUM = '"+skype_num+"' ");
		 }
		 if(!("".equals(ename))){
			 condition.append(" AND ENAME LIKE '%"+ename+"%' ");
		 }
		 
		 if(!(0==classType)){
			 condition.append(" AND SP.TEA_ID in (SELECT  DISTINCT TEA_ID FROM sk_class where CLS_TYPE = '"+classType+"' ");
			 condition.append(" AND TEA_ID='"+teacherID+"') ");
		 }
		 if(!("".equals(cls_begin_time))){
			 	cls_begin_time=cls_begin_time+"00:00:00";
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date time=sdf.parse(cls_begin_time);
			 
			 java.sql.Date init_time=new java.sql.Date (time.getTime());
			 java.sql.Date last_time=new java.sql.Date (time.getTime());
			 init_time.setDate(init_time.getDate());
			 last_time.setDate(last_time.getDate()+1);
			 
			 condition.append(" AND SP.TEA_ID in (SELECT  DISTINCT TEA_ID FROM sk_class where CLS_BEGIN_TIME >'"+init_time+"'  AND  CLS_BEGIN_TIME <'"+last_time+"' ");
			 condition.append(" AND TEA_ID='"+teacherID+"') ");
			 
			 
//		 condition.append(" AND  CLS_BEGIN_TIME >'"+init_time+"'  AND  CLS_BEGIN_TIME <'"+last_time+"' AND SC.TEA_ID='"+teacherID+"'");
			 }
		
		 result.put("data", teacherDao.queryStudentByTeacher(condition.toString(), start, end , teacherID));
		 
		 result.put("count", teacherDao.queryStudentByTeacherCount(condition.toString(),teacherID));
		return result;
		
		
   }
	
	
/**
 * 获取注销老师列表
 * @return List
 **/
	public Map<String, Object> cancelTeacherList(int start, int end,
			Map<String, String> searchParam) {

		Map<String, Object> result = new HashMap<String, Object>();

		String search_name = searchParam.get("search_name");
		String search_order_by = searchParam.get("order_by").toUpperCase();
		StringBuilder condition = new StringBuilder();
		if (!"".equals(search_name)) {
			condition.append(" AND (T.NAME LIKE '%" + search_name + "%'" + ")");
		}

		String orderBy = "";
		if ("GROUPID_ASC".equals(search_order_by)) {
			orderBy = " ORDER BY T.GROUPID ASC";
		} else if ("GROUPID_DESC".equals(search_order_by)) {
			orderBy = " ORDER BY T.GROUPID DESC";
		} else {
			orderBy = " ORDER BY T.GROUPID DESC";
		}

		result.put("count", teacherDao.querycancelTeacherListCount(condition.toString()));
		result.put("data", teacherDao.querycancelTeacherList(condition.toString(), orderBy,
				start, end));
		
		return result;
	}
}