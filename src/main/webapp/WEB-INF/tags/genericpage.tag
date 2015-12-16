<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="categories" fragment="true"%>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Blog Application</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <style type="text/css">	
		body {
		    padding-top: 70px; /* Required padding for .navbar-fixed-top. Remove if using .navbar-static-top. Change if height of navigation changes. */
		}	
		footer {
		    margin: 50px 0;
		}
	</style>
	<script type="text/javascript">
  $(document).ready(function() {
    $('.comment').keydown(function(event) {
        if (event.keyCode == 13) {
            this.form.submit();
            return false;
         }
    });
  });
</script>
    
</head>
  <body>
    <div id="pageheader">
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/blog/post">Home</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" style="font-weight:10px;">
                    <li>
                        <a href="/blog/login">Login</a>
                    </li>
                    <li>
                        <a href="/blog/post">Posts</a>
                    </li>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                    <li>
                        <a href="/blog/post/add">Add Post</a>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                     <li>
                        <a href="/blog/category/add">Add Category</a>
                    </li>
                    </sec:authorize>
                    <li>
                        <a href="/blog/user/add">Register</a>
                    </li>
                    <jsp:invoke fragment="header"/>                    
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    </div>
    <div class="container">
        <div class="row">
          <div class="col-lg-8">
		    <div id="body">
		     <jsp:doBody/>   
			 </div>
		 </div>
		 <div class="col-md-4">
		      <!-- Blog Search Well -->
                <div class="well">
                    <h4>Blog Search</h4>
                    <div class="input-group">
                        <input type="text" class="form-control">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">
                                <span class="glyphicon glyphicon-search"></span>
                        </button>
                        </span>
                    </div>
                    <!-- /.input-group -->
                </div>

                <!-- Blog Categories Well -->
                <div class="well">
                    <h4>Blog Categories</h4>
                    <div class="row">
 	                   <jsp:invoke fragment="categories"/>
                    </div>
                    <!-- /.row -->
                </div>
		 </div>	 			
    	<div id="pagefooter">
      		<jsp:invoke fragment="footer"/>
    	</div>
	    </div>

   </div>
     <script src="https://code.jquery.com/jquery-2.1.4.js"></script>
	   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  </body>
</html>