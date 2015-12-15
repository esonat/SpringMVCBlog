<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


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
    <div class="form-group">
    	<c:forEach items="${postList}" var="post">
    		<p>${post.text}</p>
    		
	 		<small><span class="glyphicon glyphicon-time"></span>${post.date}</small>
	 		
	 		<!-- ADMIN DELETE BUTTON -->
	 		<sec:authorize access="hasRole('ROLE_ADMIN')">
			  	<spring:url value="/post/${post.ID}/delete" var="deletePostUrl" />
			  	<form action="${deletePostUrl}" method="POST">
					<button style="font-size:10px;" class="btn btn-danger">Delete</button>
					<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
				</form>
			</sec:authorize>
			 			    		
			<!-- ADD COMMENT TO POST -->	
			<div class="well">
			<form:form action="/blog/post/${post.ID}/comment/add" modelAttribute="comment" method="POST">
				  <h4>Leave a Comment:</h4>
                   <form:errors path="*" cssClass="alert alert-danger" element="div"/>
						<table>
						<tr>
							<td><form:input class="form-control comment" type="text" id="text" path="text" size="300" name="text" placeholder="Comment..."/></td>
						</tr>
						</table>
					</form:form>
              </div>
              <!-- COMMENTS -->
              	<c:forEach items="${commentList}" var="comment">
				<!--<c:if test="${comment.post.ID == post.ID}">-->
					<div style="margin-left:${comment.depth*50};">
						<!-- COMMENT TEXT -->
						<p>${comment.text}</p>
						<!-- COMMENT DATETIME -->
						<small><span class="glyphicon glyphicon-time"></span> ${comment.datetime}</small>
						 	<sec:authorize access="hasRole('ROLE_ADMIN')">
									<!-- IF POST COMMENT -->
									<c:if test="${commentStruct.comment.depth==0}">
								  		<spring:url value="/post/${post.ID}/comment/${commentStruct.comment.ID}/delete" var="deleteCommentUrl" />
									</c:if>				
									<!-- IF CHILD -->		 
									<c:if test="${commentStruct.comment.depth!=0}">
								  		<spring:url value="/post/${post.ID}/comment/${commentStruct.parentID}/comment/${commentStruct.comment.ID}/delete" var="deleteCommentUrl" />
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
									  	<spring:url value="/post/${post.ID}/comment/${comment.ID}/add" var="addCommentUrl" />
									</c:if>				
									<!-- IF CHILD -->		 
									<c:if test="${comment.depth!=0}">
								  		<spring:url value="/post/${post.ID}/comment/${comment.parent.ID }/comment/${comment.ID}/add" var="addCommentUrl" />
									</c:if>
																													
									<form:form action="/blog/post/${post.ID}/comment/${comment.ID}/comment/add" modelAttribute="comment" method="POST">
									    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
										<table>
										<tr>
											<td><form:input class="comment form-control" type="text" id="text" path="text" size="300" name="text" placeholder="Comment..."/></td>
											<td><input type="submit" style="font-size:10px;" value="Comment" class="btn btn-primary"/></td>
										</tr>
										</table>
									</form:form>		
							</div>
				<!--  </c:if>-->	
					</c:forEach>
					</c:forEach>
					</div>
		</jsp:body>
</t:genericpage>