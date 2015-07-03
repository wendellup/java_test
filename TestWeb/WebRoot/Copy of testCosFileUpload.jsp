<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Insert title here2</title>
</head>
<body>
	<form method="POST" action="${pageContext.request.contextPath}/CosUploadServlet"
		ENCTYPE="multipart/form-data">
		<table>
			<tr>
				<td><input type="text" name="subject" /></td>
			</tr>
			<tr>
				<td><input type="file" name="file1" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="上传" /></td>
			</tr>
		</table>
	</form>
	<br />
	<div>
		<%
			if(request.getAttribute("cost")!=null){
		%>
			文件名称:${fileName}
			文件大小:${fileSize}
			花费时间:${cost}
		<%
			}
		 %>
	</div>
</body>
</html>