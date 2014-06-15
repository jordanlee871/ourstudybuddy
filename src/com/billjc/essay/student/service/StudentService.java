package com.billjc.essay.student.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billjc.essay.student.dao.Student;
import com.billjc.essay.student.dao.StudentDao;

@Service
public class StudentService {
	
	@Autowired
	private StudentDao studentdao;
	
	public List<Student> listStudents(){
		return studentdao.listStudents();
	}
}
