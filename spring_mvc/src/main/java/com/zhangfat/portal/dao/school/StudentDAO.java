package com.zhangfat.portal.dao.school;

import java.util.Map;

import com.zhangfat.common.orm.pojo.PageResultPojo;
import com.zhangfat.portal.pojo.school.Student;

public interface StudentDAO {
	
	/**
	 * get student info by id
	 * @param id
	 * @return
	 */
	public Student getStudentById(Long id);
	
	/**
	 * get student page list info by parameters and page info
	 * @param filterMap		parameter map
	 * @param page			page number
	 * @param pageNum		record number per page
	 * @return
	 */
	public PageResultPojo<Student> getStudentPageList( Map<String,Object> filterMap, Integer page, Integer pageNum );
}
