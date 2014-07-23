package com.billjc.essay.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.essay.student.dao.EssayStudentDao;
import com.billjc.essay.student.entity.EssayStudent;

@Service
public class EssayStudentService {
	
	@Autowired
	private EssayStudentDao essaystudentdao;
	
	public List<EssayStudent> Liststudent(){
		return essaystudentdao.queryStudent();
	}
	
	public int queryStudent( String Name, String Password ){
		return essaystudentdao.queryStudent(Name, Password);
	}
	
	public int queryAdmin( String Name, String Password ){
		return essaystudentdao.queryAdmin(Name, Password);
	}
	
	public List<EssayStudent> ListStudentId( String Id ){
		return essaystudentdao.queryStudentIdObject(Id);
	}
	
}
