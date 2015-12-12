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
   		<c:forEach items="${postsMap}" var="mapItem">
   				<jsp:include page="postDiv.jsp">
	    			<jsp:param name="postText" value="${mapItem.key.text}"/>
	    			<jsp:param name="postDate" value="${mapItem.key.date}"/>
	    			<jsp:param name="username" value="${mapItem.key.user.username}"/>
	    			<jsp:param name="comments" value="${mapItem.value}"/>
				</jsp:include> 			
		 </c:forEach>	
	</div>
    </jsp:body>
</t:genericpage>