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
<script type="text/javascript" src="<%=basePath%>/static/js/uploadify_init.js" ></script>
<script type="text/javascript" src="<%=basePath%>/static/js/upload/jquery.uploadify.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	new UploadifyInit({
			basePath:"<%=basePath%>",
			uploadifyId:"uploadify",
			submitId:"uploadifySubmit"
		});

	new UploadifyInit({
			basePath:"<%=basePath%>",
			uploadifyId:"uploadify2",
			submitId:"uploadifySubmit2"
		});
});  
</script>  
</head>  
<body>
	<div id="fileQueue"></div>
    <input type="file" name="uploadify" id="uploadify" />
    <p id="uploadifySubmit" >
    </p>
    <input type="file" name="uploadify2" id="uploadify2" />
    <p id="uploadifySubmit2" >
    </p>
</body>
</html>