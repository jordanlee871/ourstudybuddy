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

	public List<EssayStudent> ListComMemberObject( String Name, String Password ){
		return essaystudentdao.queryComMemberObject(Name, Password);
	}
	
	public List<EssayStudent> Liststudent( int startLine ){
		return essaystudentdao.queryStudent( startLine );
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
	
	public List<EssayStudent> queryStudentSearch( String wanwan, String qq, String phone, String email, String date, int startLine ){
		return essaystudentdao.queryStudentSearch( wanwan, qq, phone, email, date, startLine);
	}
	
	public int queryStudentSearchCount( String wanwan, String qq, String phone, String email, String date ){
		return essaystudentdao.queryStudentSearchCount( wanwan, qq, phone, email, date );
	}
	
	public int insertNewStudent(EssayStudent student){
		return essaystudentdao.insertNewStudent(student);
	}
	
	public int updatePassword( String password, String id){
		return essaystudentdao.updatePassword(password, id);
	}
	
	public int queryMemberCount(){
		return essaystudentdao.queryMemberCount();
	}
}
