<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>


<t:genericpage>
	<jsp:attribute name="header">
         <li><a href="#">Logged User: ${loggedUser}</a></li>
       	<li><a href="<c:url value="/logout"/>">Logout</a></li>  
	</jsp:attribute>
	<jsp:attribute name="footer">
    </jsp:attribute>
	<jsp:body>
    	<h1>Access is denied</h1>

		<h2>You do not have permission to access this page!</h2>

    
    </jsp:body>
</t:genericpage>