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
    <jsp:body>
    <div class="form-group">
    	<c:forEach items="${postList}" var="post">
    		<p>${post.text}</p>
	 		<p>${post.date}</p>
	 		<sec:authorize access="hasRole('ROLE_ADMIN')">
			  	<spring:url value="/post/${post.ID}/delete" var="deletePostUrl" />
			  	<form action="${deletePostUrl}" method="POST">
					<button class="btn btn-danger">Delete</button>
					<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />
				</form>
			</sec:authorize>
			 			    			
			<form:form action="/blog/post/${post.ID}/comment/add" modelAttribute="comment" method="POST">
			    <form:errors path="*" cssClass="alert alert-danger" element="div"/>
			    <div class="form-group">
					<form:textarea id="text" path="text" rows="2" cols="50" name="text" class="form-control"/>
				</div>
				<input type="submit" value="Comment" class="btn btn-primary"/>
			</form:form>
			
			<c:forEach items="${commentList}" var="commentStruct">
				<c:if test="${commentStruct.postID==1}">
				<div style="margin-left:${commentStruct.parent.depth*50};background-color:black;">
					
					<p>${commentStruct.parent.text}</p>
					<p>${commentStruct.parent.datetime}</p>
					<hr></hr>
					
					<c:forEach items="${commentStruct.children}" var="comment">
						<div style="margin-left:${comment.depth*50};background-color:black;">
							<p>${comment.text}</p>
							<p>${comment.datetime}</p>
							<hr></hr>
						</div>
					</c:forEach>
				</div>
				</c:if>
			</c:forEach>			
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