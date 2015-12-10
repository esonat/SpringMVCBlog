<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 <t:genericpage>
    <jsp:attribute name="header">
	</jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    <h2>Add User</h2>
	 <form:form action="/blog/user/add" modelAttribute="user" role="form">
		<form:errors path="*" cssClass="alert alert-danger" element="div"/>
		<div class="form-group">
		<table>
			<tr>
				<td>
					<label>Name:</label>
				</td>
				<td>
					<form:input id="Name" path="Name" type="text" name="Name" class="form-control"/>
					<form:errors path="Name" cssClass="text-danger"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Username:</label>
				</td>
				<td>
					<form:input id="username" path="username" type="text" name="username" class="form-control"/>
					<form:errors path="username" cssClass="text-danger"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Password:</label>
				</td>
				<td>
					<form:input id="password" path="password" type="password" name="password" class="form-control"/>
					<form:errors path="password" cssClass="text-danger"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Enabled:</label>
				</td>
				<td>
					<input type="checkbox" name="enabled" class="form-control"/>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Submit" class="btn btn-primary"/>
				</td>
			</tr>
		</table>
		</div>
</form:form>
    </jsp:body>
</t:genericpage>


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html> --%>