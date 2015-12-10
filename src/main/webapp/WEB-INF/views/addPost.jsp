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
       <form:form action="/blog/post/add" modelAttribute="post" method="POST">
       <form:errors path="*" cssClass="alert alert-danger" element="div"/>
       <div class="form-group">
			<form:textarea id="text" path="text" rows="10" cols="100" name="text" class="form-control"/>
		</div>
		<input type="submit" value="Submit" class="btn btn-primary"/>
	 </form:form>	 
    </jsp:body>
</t:genericpage>

<!-- 
<spring:url value="/resources/css/boostrap.css" var="bootstrapCss" />
	<spring:url value="/resources/css/blog-post.css" var="postCss" />
	<spring:url value="/resources/js/jquery.js" var="jqueryJs" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
	 -->