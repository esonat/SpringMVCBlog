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
						<form:input type="text" id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
						</br>
						<input type="submit" style="font-size:10px;" value="Comment" class="btn btn-primary"/>
					</form:form>
                  </div>			
			<div>
			<c:forEach items="${commentList}" var="commentStruct">
				<c:if test="${commentStruct.postID == post.ID}">
					
					<div style="margin-left:${commentStruct.comment.depth*50};">
					<c:if test="${commentStruct.parentID==0}">	
						<!-- COMMENT TEXT -->
						<p>${commentStruct.comment.text}</p>
						<!-- COMMENT DATETIME -->
						<small><span class="glyphicon glyphicon-time"></span> ${commentStruct.comment.datetime}</small>
						 	<sec:authorize access="hasRole('ROLE_ADMIN')">
								<!-- IF POST COMMENT -->
								<c:if test="${commentStruct.comment.depth==0}">
							  		<spring:url value="/post/${post.ID}/comment/${commentStruct.comment.ID}/delete" var="deleteCommentUrl" />
								</c:if>				
								<!-- IF  -->		 
								<c:if test="${commentStruct.comment.depth!=0}">
							  		<spring:url value="/post/${post.ID}/comment/${commentStruct.parentID}/comment/${commentStruct.comment.ID}/delete" var="deleteCommentUrl" />
								</c:if>						 
								 
							  	<form action="${deleteCommentUrl}" method="POST">
									<button style="font-size:10px;" class="btn btn-danger">Delete</button>
									<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
								</form>	
							</sec:authorize>
																				
							<form:form action="/blog/post/${post.ID}/comment/${commentStruct.comment.ID}/comment/add" modelAttribute="comment" method="POST">
							    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
									<form:input type="text" id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
									</br>
									<input type="submit" style="font-size:10px;" value="Comment" class="btn btn-primary"/>
							</form:form>		
						<hr></hr>
					</c:if>
					<c:forEach items="${commentStruct.children}" var="childComment">
						
						<div style="margin-left:${childComment.depth*50};">
							<p>${childComment.text}</p>
							<small><span class="glyphicon glyphicon-time"></span> ${childComment.datetime}</small>
								
							<sec:authorize access="hasRole('ROLE_ADMIN')">
						  		<spring:url value="/post/${post.ID}/comment/${commentStruct.comment.ID}/comment/${childComment.ID}/delete" var="deleteCommentUrl" />
							  	<form action="${deleteCommentUrl}" method="POST">
									<button style="font-size:10px;" class="btn btn-danger">Delete</button>
									<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
								</form>
							</sec:authorize>
							
								<form:form action="/blog/post/${post.ID}/comment/${childComment.ID}/comment/add" modelAttribute="comment" method="POST">
								    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
										<form:input type="text" id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
										</br>
										<input type="submit" style="font-size:10px;" value="Comment" class="btn btn-primary"/>
								</form:form>					
							<hr></hr>
						</div>
					</c:forEach>
				</div>
				</c:if>
			</c:forEach>			
			</div>
		</c:forEach>
    </div>
    
    
  <%--  <div class="form-group">
   		<c:forEach items="${postMap}" var="postItem">
	 		<p>${postItem.key.text}</p>
	 		<p>${postItem.key.date}</p>
	 			<!-- DELETE POST BUTTON -->
	 			<sec:authorize access="hasRole('ROLE_ADMIN')">
				  	<spring:url value="/post/${postItem.key.ID}/delete" var="deletePostUrl" />
				  	<form action="${deletePostUrl}" method="POST">
						<button class="btn btn-danger">Delete</button>
						<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
					</form>
				</sec:authorize>
			 			    			
				<form:form action="/blog/post/${postItem.key.ID}/comment/add" modelAttribute="comment" method="POST">
				    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
				    <div class="form-group">
						<form:textarea id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
					</div>
					<input type="submit" value="Comment" class="btn btn-primary"/>
				</form:form>	
				
				<!-- POST COMMENTS -->
	 			<c:forEach items="${postItem.value}" var="commentItem">
	 			<div>
					<p>${commentItem.key.text}</p>
	 				<p>${commentItem.key.datetime}</p>
	 				<hr></hr>
		 				<c:forEach items="${commentItem.value}" var="childComment">
		 					<div style="margin-left:${childComment.depth*50};background-color:black;">
			 					<p>${childComment.text}</p>
			 					<p>${childComment.datetime}</p>
		 					</div>
		 					<hr></hr>
		 				</c:forEach>
	 			</div>
	 			</c:forEach>
	 	</c:forEach>
	 </div> --%>   
    </jsp:body>
</t:genericpage>
<!-- 
   <div class="form-group">
   		<c:forEach items="${postsMap}" var="mapItem">
   				<jsp:include page="postDiv.jsp">
	    			<jsp:param name="postText" value="${mapItem.key.text}"/>
	    			<jsp:param name="postDate" value="${mapItem.key.date}"/>
	    			<jsp:param name="username" value="${mapItem.key.user.username}"/>
	    			<jsp:param name="comments" value="${mapItem.value}"/>
				</jsp:include> 			
		 </c:forEach>	
	</div>
-->