<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
 <t:genericpage>
    <jsp:attribute name="header">
       Logged User: ${loggedUser}
	</jsp:attribute>
    <jsp:attribute name="footer">
    </jsp:attribute>
    <jsp:body>
   <div class="form-group">
    	<c:forEach items="${map}" var="entry">
	    	<c:forEach items="${entry.value}" var="post">
	    		<textarea class="form-control">${post.text}</textarea>
	    		<small>${entry.key} - ${post.date}</small>
	   	 	</c:forEach>	   	 	
		</c:forEach>
	</div>
    </jsp:body>
</t:genericpage>


<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<%--  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
    
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<%-- <title>Insert title here</title> --%>
<!-- </head> -->
<!-- <body> -->

<!-- </body> -->
<!-- </html> -->