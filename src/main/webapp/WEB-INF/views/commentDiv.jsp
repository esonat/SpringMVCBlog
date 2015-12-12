<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 
 	<p>${comment.text}</p>
 	<p>${comment.datetime}</p>
 	<c:forEach items="${comment.children}" var="childComment">
 		<p>${childComment.text}</p>
 		<p>${childComment.datetime}</p>
 		<jsp:include page='<%="commentDiv.jsp"%>'>
    		<jsp:param name="comment" value="${childComment}"/>
		</jsp:include>
 	</c:forEach>
 