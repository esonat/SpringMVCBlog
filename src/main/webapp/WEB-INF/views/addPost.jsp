<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add Post</title>
</head>
<body>
	 <form:form action="/blog/post/add" modelAttribute="Post">
		<textarea rows="10" cols="10" name="text" value="text">
		</textarea>
		<input type="submit" value="Submit"/>
	 </form:form>	 
</body>
 </html>