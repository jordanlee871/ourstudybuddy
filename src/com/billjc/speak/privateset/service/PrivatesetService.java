package com.billjc.speak.privateset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.speak.privateset.dao.PrivatesetDao;
import com.billjc.speak.privateset.entity.Privateset;
import com.billjc.speak.students.entity.Student;
import com.billjc.speak.teacher.dao.TeacherDao;
import com.billjc.speak.teacher.entity.Teacher;

@Service
public class PrivatesetService {
	final Logger logger = LoggerFactory.getLogger(PrivatesetService.class);
	@Autowired
	private PrivatesetDao privatesetDao;
	@Autowired
	private TeacherDao teacherDao;

	/**
	 * 查询学生的专属老师列表
	 * 
	 * @param stuId
	 * @return p_teacherList
	 */

	public Map<String, Object> queryAllTeachers(long stuId) {

		Map<String, Object> result = new HashMap<String, Object>();


		


		List<Privateset> ps_list= privatesetDao.queryPsList(stuId);
		List<Teacher> tea_list=teacherDao.queryAllTeachers();
		
		
		
		List <Teacher> c_teaList=new ArrayList<Teacher>();
		
		
		for(int k=0;k<tea_list.size();k++){
			
			for(int i=0;i<ps_list.size();i++){
				
				if(ps_list.get(i).getTeaId()==tea_list.get(k).getTea_id()){
					
					c_teaList.add(tea_list.get(k));
					
					break;
					
				}
			}
			
		}
		
		tea_list.removeAll(c_teaList);
		
		
		result.put("c_teachetData", c_teaList);
		result.put("dc_teachetData",tea_list);
		
		
		return result;

	}

	/**
	 * 中断学生专属老师
	 * 
	 * @param stuId teaId
	 * @return 
	 */	
	
	public int insertPrivateset(Long stuId,Long teaId){
		
		return privatesetDao.insertPrivateset(stuId, teaId);
		
	}
	
	
	/**
	 * 新增学生专属老师
	 * 
	 * @param stuId teaId
	 * @return 
	 */
public int delPrivateset(Long stuId,Long teaId){
		
		return privatesetDao.delPrivateset(stuId, teaId);
		
	}


}
