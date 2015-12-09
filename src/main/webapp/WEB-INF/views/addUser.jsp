<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/blog/user/add" method="POST">
		<table>
			<tr>
				<td>
					<label>Name:</label>
					<input type="name" name="name"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Username:</label>
					<input type="username" name="username"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Username:</label>
					<input type="password" name="password"/>
				</td>
			</tr>
			<tr><input type="submit" value="Submit"/></tr>
		</table>
	</form>

</body>
</html>