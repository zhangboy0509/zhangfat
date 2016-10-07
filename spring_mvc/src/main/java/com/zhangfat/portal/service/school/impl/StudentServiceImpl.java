package com.zhangfat.portal.service.school.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhangfat.common.orm.pojo.PageResultPojo;
import com.zhangfat.portal.dao.school.StudentDAO;
import com.zhangfat.portal.pojo.school.Student;
import com.zhangfat.portal.service.school.StudentService;

@Component("studentService")
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentDAO studentDAO;
	
	public Student getStudentById(){
		Student stu = studentDAO.getStudentById(1L);
		System.out.println(stu.getName());
		System.out.println(stu.getBirthday());
		System.out.println(stu.getId());
		
		return stu;
	}
	
	public void getStudentPageList(){
		Map<String,Object> filterMap = new HashMap<String, Object>();
		PageResultPojo<Student> result = studentDAO.getStudentPageList(filterMap, 2, 5);
		System.out.println(result.getResult().size());
		for( int i=0;i<result.getResult().size();i++ ){
			System.out.println(result.getResult().get(i).getName());
		}
//		result.getResult().stream().forEach(stu -> System.out.println(stu.getName()));
	}
}
