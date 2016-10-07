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
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/js/upload/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>/static/js/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>/static/js/upload/jquery.uploadify.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function() {
	    $("#uploadify").uploadify({
	        'height'	: 30,
	        'swf'		: '<%=basePath%>/static/js/upload/uploadify.swf',
	        'uploader'	: '<%=basePath%>/fileupload',
	        'width'		: 120,
	        'auto'		: false,
	        'multi'		: false
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