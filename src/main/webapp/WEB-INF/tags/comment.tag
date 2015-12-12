<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="commentDiv" fragment="true" %>
<%@attribute name="mainDiv" fragment="true" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 
 	<p>${comment.text}</p>
 	<c:forEach items="${childComments}" var="childComment">
 		<p>${childComment.text}</p>
 		<p>${childComment.datetime}</p>
 	</c:forEach>
      <jsp:invoke fragment="commentDiv"/>
 