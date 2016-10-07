<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!doctype html public "-//W3C//DTD HTML 4.01//EN' 'http://www.w3.org/TR/html4/strict.dtd">
<html>  
<head>  
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>  
<title>JS模拟上传进度条</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/upload/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>/upload/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>/upload/jquery.uploadify.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function() {
	    $("#uploadify").uploadify({
	    	buttonText     :'点击上传',
	    	removeCompleted:false,
	        height	       : 30,
	        swf		       : '<%=basePath%>/upload/uploadify.swf',
	        uploader	   : '<%=basePath%>/fileupload',
	        width		   : 120,
	        auto		   : false,
	        multi		   : false,
	        queueSizeLimit :1,
	        uploadLimit    :1,
	        fileSizeLimit  :'2MB',
	        fileTypeExts   :'*.gif;*.jpg;*.png;*.bmp;*.jepg', 
	        method         :'post',
	        onSelect       : function(file) {        }, 
	        onSelectError  :function(file, errorCode, errorMsg){
								switch(errorCode) {
									case -100:
										alert("上传的文件数量已经超出系统限制的"+$('#uploadify').uploadify('settings','queueSizeLimit')+"个文件！");
										break;
									case -110:
										alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#uploadify').uploadify('settings','fileSizeLimit')+"大小！");
										break;
									case -120:
										alert("文件 ["+file.name+"] 大小异常！");
										break;
									case -130:
										alert("文件 ["+file.name+"] 类型不正确！");
										break;
								}
							},        //检测FLASH失败调用       
	        onFallback     :function(){
	        					alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
	        				},  
	        
			onUploadComplete: function(file){
				alert("111111111111");
			}

	    });
	});
});  
</script>  
</head>  
<body>
	<div id="fileQueue"></div>
    <input type="file" name="uploadify" id="uploadify" />
    <p>
      <a href="javascript:$('#uploadify').uploadify('upload')">上传</a>| 
    </p>
</body>
</html>