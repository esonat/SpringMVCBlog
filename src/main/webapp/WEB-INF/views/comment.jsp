<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <t:genericpage>
    <jsp:attribute name="header">
       <li><a href="#">Logged User: ${loggedUser}</a></li>
       <li><a href="<c:url value="/logout"/>">Logout</a></li>       
	</jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
   <div class="form-group">
   		<p>${comment.text}</p>
	    <small>${username} - <span class="glyphicon glyphicon-time"></span> ${comment.datetime}</small>
	    	<sec:authorize access="hasRole('ROLE_ADMIN')">
			  	<spring:url value="/post/${postID}/comment/${comment.ID}/delete" var="deleteUrl" />
			  	<form action="${deleteUrl}" method="POST">
					<button class="btn btn-danger">Delete</button>
					<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
				</form>
			</sec:authorize>
	    <hr></hr>
	</div>
    </jsp:body>
</t:genericpage>