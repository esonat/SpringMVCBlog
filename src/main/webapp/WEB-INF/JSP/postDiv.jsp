<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<c:out value="${param.postText}"/>
<c:out value="${param.comments}"/>
<c:out value="${param.post}"/>


 	<p><c:out value="${param.postText}"/></p>
 	<small>
 	<c:out value="${param.username}"/>- <span class="glyphicon glyphicon-time"></span> <c:out value="${param.postDate}"/>
 	</small>
 	
 	<c:forEach items="${param.comments}" var="comment">
 		<p>${comment.text}</p>
 		<p>${comment.datetime}</p>
 		<jsp:include page="../views/commentDiv.jsp">
    		<jsp:param name="comment" value="${comment}"/>
		</jsp:include>
 	</c:forEach>
 	
 	<sec:authorize access="hasRole('ROLE_ADMIN')">
	  	<spring:url value="/post/${param.post.ID}/delete" var="deleteUrl" />
	  	<form action="${deleteUrl}" method="POST">
			<button class="btn btn-danger">Delete</button>
			<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
		</form>
	</sec:authorize>
 			    			
	<form:form action="/blog/post/${param.post.ID}/comment/add" modelAttribute="comment" method="POST">
	    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
	    <div class="form-group">
			<form:textarea id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
		</div>
		<input type="submit" value="Comment" class="btn btn-primary"/>
	</form:form>	

  --%>