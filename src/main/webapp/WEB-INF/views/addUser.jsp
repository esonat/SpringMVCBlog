<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	 <form:form action="/blog/user/add" modelAttribute="User">
		<table>
			<tr>
				<td>
					<label>Name:</label>
					<input type="text" name="Name"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Username:</label>
					<input type="text" name="username"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Password:</label>
					<input type="password" name="password"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Enabled:</label>
					<input type="checkbox" name="enabled"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Submit"/>
				</td>
			</tr>
		</table>
</form:form>
</body>
</html>