package com.zhangfat.portal.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.zhangfat.portal.service.school.StudentService;

@Controller
public class IndexController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) {
		String username = request.getParameter("username");
		ModelAndView mv = new ModelAndView("/index", "command","LOGIN SUCCESS, " + username);
		return mv;
	}
	
	/**
	 * file upload controller
	 * @author Boyang Zhang
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "fileupload")
	public Object fileBatchUpload(MultipartHttpServletRequest multipart,HttpServletResponse response) {
		Iterator<String> it = multipart.getFileNames();
		
		while(it.hasNext()){
			String fileName = it.next();
			CommonsMultipartFile file=(CommonsMultipartFile)multipart.getFile(fileName);
			
			File nFile = new File("D:\\"+file.getOriginalFilename());
			OutputStream os = null;
			try {
				os = new FileOutputStream(nFile);
				os.write(file.getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if( os!=null ){
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return "success";
	}
	
	@RequestMapping(value = "getstudents")
	public ModelAndView getStudents(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/index", "command","LOGIN SUCCESS");
		studentService.getStudentPageList();
		mv.addObject("student", studentService.getStudentById());
		return mv;
	}
	
}
