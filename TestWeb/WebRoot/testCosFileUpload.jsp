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
			String outputStr = (String)request.getSession().getAttribute("outputStr");
			if(outputStr!=null){
				response.getWriter().write(outputStr);	
			}
			request.getSession().removeAttribute("outputStr");
		%>
	</div>
</body>
</html>