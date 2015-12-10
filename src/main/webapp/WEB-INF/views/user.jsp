<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>User: ${user}</h1>
<c:forEach items="${post}" var="post">
	<h2>${post.text}</h2s>
</c:forEach>

</body>
</html> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

 <t:genericpage>
    <jsp:attribute name="header">
         <li><a href="#">Logged User: ${loggedUser}</a></li>
       	<li><a href="<c:url value="/logout"/>">Logout</a></li>  
	</jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
   <div class="form-group">
    	<p>${post.text}</p>
		<c:forEach items="${post}" var="post">
			<small>${user} - <span class="glyphicon glyphicon-time"></span> ${post.date}</small>
		</c:forEach>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		  	<spring:url value="/post/${post.ID}/delete" var="deleteUrl" />
		  	<form action="${deleteUrl}" method="POST">
				<button class="btn btn-danger">Delete</button>
				<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
			</form>
		</sec:authorize>
   		<hr></hr>
	   	</div>
    </jsp:body>
</t:genericpage>