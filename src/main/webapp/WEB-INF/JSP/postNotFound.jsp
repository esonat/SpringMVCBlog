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
    <section>
		<div class="container">
			<h1> Post Not Found</h1>
		</div>
	</section>
	<section>
		<div class="container">
			<p>
				<a href="<spring:url value="/post" />" class="btn btn-primary">
					<span class="glyphicon-hand-left glyphicon"></span> Posts
				</a>
			</p>
		</div>
	</section>
    </jsp:body>
</t:genericpage>