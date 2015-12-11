<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <t:genericpage>
    <jsp:attribute name="header">
    	 <li><a href="#">Logged User: ${loggedUser}</a></li>
       	<li><a href="<c:url value="/logout"/>">Logout</a></li>
	</jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
    <h3>Register</h3>
    
     <div id="mainWrapper">
            <div class="login-container">
               <div class="login-card">
                   <div class="login-form">
	 <form:form action="/blog/user/add" modelAttribute="user" role="form">
		<form:errors path="*" cssClass="alert alert-danger" element="div"/>
			<div class="input-group input-sm">
					<label class="input-group-addon" for="name"><i class="fa fa-user">Name</i></label>
					<form:input id="name" path="name" type="text" name="name" class="form-control"/>
					<form:errors path="name" cssClass="text-danger"/>
			</div>
			<div class="input-group input-sm">
					<label class="input-group-addon" for="username"><i class="fa fa-user">Username</i></label>
					<form:input id="username" path="username" type="text" name="username" class="form-control"/>
					<form:errors path="username" cssClass="text-danger"/>
			</div>
			<div class="input-group input-sm">
					<label class="input-group-addon" for="password"><i class="fa fa-user">Password</i></label>
					<form:input id="password" path="password" type="password" name="password" class="form-control"/>
					<form:errors path="password" cssClass="text-danger"/>
			</div>
			<div class="input-group input-sm">
					<label class="input-group-addon" for="enabled"><i class="fa fa-user">Enabled</i></label>
					<input type="checkbox" name="enabled" class="form-control"/>
			</div>
			<div class="input-group input-sm">
				<input type="submit" value="Submit" class="btn btn-primary"/>
			</div>
</form:form>
</div>
</div>
</div>
</div>

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