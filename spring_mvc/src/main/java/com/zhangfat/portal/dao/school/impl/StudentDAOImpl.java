package com.zhangfat.portal.dao.school.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zhangfat.common.orm.mybatis.BaseDAO;
import com.zhangfat.common.orm.mybatis.PageResult;
import com.zhangfat.common.orm.mybatis.Query;
import com.zhangfat.common.orm.pojo.PageResultPojo;
import com.zhangfat.portal.dao.school.StudentDAO;
import com.zhangfat.portal.pojo.school.Student;

@Transactional
@Repository("studentDAO")
public class StudentDAOImpl extends BaseDAO<Student, Integer> implements StudentDAO {
	
	public StudentDAOImpl() {
		super(StudentDAOImpl.class);
	}

	public Student getStudentById(Long id) {
		return getById(id);
	}
	
	public PageResultPojo<Student> getStudentPageList( Map<String,Object> filterMap, Integer page, Integer pageNum ){
		Query<Student> query = (Query<Student>) this.createQuery("com.zhangfat.portal.dao.school.impl.StudentDAOImpl.list");
		query.filterMap(filterMap);
		PageResult<Student> result = query.listPage(page, pageNum);
		return new PageResultPojo<Student>(result.getResult(), result.getTotalNumber(),page+1,pageNum);
	}
	
}
