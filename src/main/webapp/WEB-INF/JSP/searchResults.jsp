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
	<jsp:attribute name="categories">
    	<div class="col-lg-6">
			<ul class="list-unstyled">
			<c:forEach items="${categories}" var="category">
				<li><a href="/blog/post/category/${category.ID}">${category.name}</a></li>
			</c:forEach>
			</ul>
		</div>
    </jsp:attribute>
	<jsp:body>
    <c:forEach items="${postList}" var="post">
		    <p>${post.text}</p>
	 		<small><span class="glyphicon glyphicon-time"></span>${post.date}</small>
			<p>${post.user.username}</p>
    </c:forEach>
    <c:forEach items="${commentList}" var="comment">
		    <p>${comment.text}</p>
	 		<small><span class="glyphicon glyphicon-time"></span>${comment.datetime}</small>
    </c:forEach>    
        </jsp:body>
</t:genericpage>