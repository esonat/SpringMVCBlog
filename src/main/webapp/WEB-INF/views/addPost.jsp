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
       <form:form action="/blog/post/add" modelAttribute="post" method="POST">
       <form:errors path="*" cssClass="alert alert-danger" element="div"/>
       <div class="form-group">
			<form:textarea id="text" path="text" rows="10" cols="100" name="text" class="form-control"/>
			<select name="categoryName" id="categoryName">
				<c:forEach items="${categories}" var="category">
					<option value="${category.name}">${category.name}</option>
				</c:forEach>
			</select>
		</div>
		<input type="submit" value="Submit" class="btn btn-primary"/>
	 </form:form>	 
    </jsp:body>
</t:genericpage>