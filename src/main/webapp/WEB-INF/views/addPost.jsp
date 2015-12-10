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
       <form:form action="/blog/post/add" modelAttribute="Post">
			<textarea rows="10" cols="100" name="text">
			</textarea>
		<input type="submit" value="Submit"/>
	 </form:form>	 
    </jsp:body>
</t:genericpage>

<!-- 
<spring:url value="/resources/css/boostrap.css" var="bootstrapCss" />
	<spring:url value="/resources/css/blog-post.css" var="postCss" />
	<spring:url value="/resources/js/jquery.js" var="jqueryJs" />
	<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJs" />
	 -->