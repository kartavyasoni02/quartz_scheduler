<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="myApp">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
.panel {
	background-color: #369C60
}

#ty {
	color: #0E6F5F
}

.header {
	background-color: #884EA0;
	text-align: center;
	color: white;
}

body {
	min-height: 500px;
}

.content{
	min-height: 500px;
}
 /* Remove the navbar's default margin-bottom and rounded borders */ 
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }
    
    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 500px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      height: 500px;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #333333;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;} 
    }
</style>

<!--   <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular-route.min.js"></script>
     -->
<script type="text/javascript"
	src="<c:url value="/static/js/libary/jquery-1.12.3.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/static/js/libary/angular.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/static/js/libary/angular-route.min.js"/>"></script>




<script type="text/javascript"
	src="<c:url value="/static/js/libary/bootstrap.min.js"/>"></script>

<script src="<c:url value="/static/js/libary/jquery.confirm.min.js"/>"></script>

<script
	src="<c:url value="/static/js/libary/bootstrap-clockpicker.min.js"/>"></script>
<script
	src="<c:url value="/static/js/libary/jquery-clockpicker.min.js"/>"></script>



<!----            CSS library                 -->


<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/bootstrap-clockpicker.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/font-awesome.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/jquery-clockpicker.min.css" />">





<!----     Static User library AngularJs                -->
<script src="<c:url value="/static/js/app.js"/>"></script>
<script src="<c:url value="/static/js/appController.js"/>"></script>
<script src="<c:url value="/static/js/schedularController.js"/>"></script>

<script type="text/javascript">

$(document).ready(function(){
$(".toggle").on('click', function() {
    $(this).toggleClass('glyphicon-chevron-right glyphicon-chevron-down');
	});
});
</script>


</head>

<body ng-controller="appController">
	<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
      </ul>
    </div>
  </div>
</nav>
  
<div class="container-fluid text-center">    
  <div class="row content">
    <div class="col-sm-2 sidenav" ng-controller="schedularController">
      <p><a href="#listschedulejob" ng-click="empty()">List Scheduled Jobs</a></p>
      <p><a href="#configurationschedule" ng-click="empty()">Configure Jobs</a></p>
    </div>

	<div ng-view class="col-sm-10 text-left container-fluid content"></div>
</div>
</div>
</body>
<footer class="container-fluid text-center navbar-inverse">
	<p>
		<b>@FissionSoft TECHNOLOGIES - 2019 </b>
	</p>
</footer>
</html>



