<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>Insert title here2</title>
</head>
<body>
	<form name="form1" action="${pageContext.request.contextPath}/upload.action" method="post" enctype="multipart/form-data">
		文件类型<input id="file_type" type="text" name="file_type">
		<br />
		<input id="file1" name="file1" type="file" value="请上传文件">
		<input type="submit">
	</form>
	
	<div>
			<%
			String outputStr = (String)request.getSession().getAttribute("outputStr");
			if(outputStr!=null){
				response.getWriter().write(outputStr);	
			}
			request.getSession().removeAttribute("outputStr");
		%>
	</div>
</body>
</html>