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
    
    <div style="margin-left:${comment.depth*50};">
						<!-- COMMENT TEXT -->
						<p>${comment.text}</p>
						<!-- COMMENT DATETIME -->
						<small><span class="glyphicon glyphicon-time"></span> ${comment.datetime}</small>
						 	<sec:authorize access="hasRole('ROLE_ADMIN')">
									<!-- IF POST COMMENT -->
									<c:if test="${comment.depth==0}">
								  		<spring:url value="/post/${postItem.key.ID}/comment/${comment.ID}/delete?returnURL=${returnURL}" var="deleteCommentUrl" />
									</c:if>				
									<!-- IF CHILD -->		 
									<c:if test="${comment.depth!=0}">
								  		<spring:url value="/post/${postItem.key.ID}/comment/${comment.parent.ID}/comment/${comment.ID}/delete?returnURL=${returnURL}" var="deleteCommentUrl" />
									</c:if>						 
								  	<form action="${deleteCommentUrl}" method="POST">
									<table>
										<tr>
											<td><button style="font-size:10px;" class="btn btn-danger">Delete</button></td>
											<td><input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" /></td>
										</tr>
									</table>
									</form>	
							</sec:authorize>
							
									<c:if test="${comment.depth==0}">
									  	<spring:url value="/post/${postItem.key.ID}/comment/${comment.ID}/add?returnURL=${returnURL}" var="addCommentUrl" />
									</c:if>				
									<!-- IF CHILD -->		 
									<c:if test="${comment.depth!=0}">
								  		<spring:url value="/post/${postItem.key.ID}/comment/${comment.parent.ID }/comment/${comment.ID}/add?returnURL=${returnURL}" var="addCommentUrl" />
									</c:if>
																													
									<form:form action="/blog/post/${postItem.key.ID}/comment/${comment.ID}/comment/add?returnURL=${returnURL}" modelAttribute="comment" method="POST">
									    <form:errors path="text" cssClass="alert alert-danger" element="div"/>
										<table>
										<tr>
											<td><form:input class="comment form-control" type="text" id="text" path="text" size="300" name="text" placeholder="Comment..."/></td>
										</tr>
										</table>
									</form:form>		
							</div>
    </jsp:body>
</t:genericpage>