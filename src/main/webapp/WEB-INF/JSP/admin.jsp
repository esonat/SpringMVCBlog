<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	    <small>${username} - <span class="glyphicon glyphicon-time"></span> ${post.date}</small>
	    <hr></hr>
	</div>
    </jsp:body>
</t:genericpage>