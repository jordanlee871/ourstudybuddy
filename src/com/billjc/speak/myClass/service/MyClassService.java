package com.billjc.speak.myClass.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.speak.myClass.dao.MyClassDao;
import com.billjc.speak.myClass.entity.MyClass;
import com.billjc.speak.teacher.entity.Teacher;

@Service
public class MyClassService {
	
	@Autowired
	private MyClassDao myClassDao;
	
	
	public Map<String, Object> teacherClsList(int start, int end,Map<String, String> searchParam){
        Map<String, Object> result = new HashMap<String, Object>();
		
		
		Long tea_id=Long.parseLong(searchParam.get("tea_id"));
		
		String search_order_by = searchParam.get("order_by").toUpperCase();
		StringBuilder condition = new StringBuilder();
		    condition.append(" AND C.TEA_ID = "+tea_id+" ");
						
		String orderBy = "";
		if("ID_ASC".equals(search_order_by)){
			orderBy = " ";
		}else if("ID_DESC".equals(search_order_by)){
			orderBy = " ";
		}else{
			orderBy = " ";
		}
			
		
		result.put("count", myClassDao.teacherClsListCount(condition.toString()));
		result.put("data", myClassDao.teacherClsList(condition.toString(),tea_id,orderBy,start, end));
		
		return result;
	}

}
