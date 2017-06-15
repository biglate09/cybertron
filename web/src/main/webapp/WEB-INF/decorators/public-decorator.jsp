 <%@ page contentType="text/html; charset=UTF-8"%>
<% response.setHeader("Cache-Control","no-store"); %>

<!DOCTYPE html PUBLIC 
  "-//W3C//DTD XHTML 1.1 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Locale" %>
<%@ page import="thaisamut.util.DevelopmentTextUtil" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><decorator:title default="OceanLife iService"/></title>
    <link rel="shortcut icon" href="images/favicon.png">
	
	<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap-theme.css">
	<link rel="stylesheet" href="<s:url value="/" />css/common.css">

	<script src="<s:url value="/" />scripts/jquery-1.12.4.min.js"></script>
	<script src="<s:url value="/" />bootstrap/js/bootstrap.js"></script>
	<decorator:head />
</head>
<body>
<div class="navbar navbar-compact navbar-static-top hidden-xs"> 
		<div class="container">
			<div class="navbar-logo">
				<img src="<s:url value="/" />images/pub_logo.png">
			</div>
			<div class="collapse navbar-collapse navbar-right">
				<ul class="nav navbar-nav">
					<li><a href="#">TH</a></li>
					<li><a href="#">EN</a></li>
				</ul>
			</div>
		</div>
	</div>	
	<div id="myCarousel" class="carousel slide carousel-header" data-ride="carousel">
		<!-- Indicators -->

		<div class="container">
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner carousel-header" role="listbox">
				<div class="item active">
					<img src="<s:url value="/" />images/ads/ads_1.png">
				</div>
				<div class="item">
					<img src="<s:url value="/" />images/ads/ads_1.png">
				</div>
				<div class="item">
					<img src="<s:url value="/" />images/ads/ads_1.png">
				</div>
				<div class="item">
					<img src="<s:url value="/" />images/ads/ads_1.png">
				</div>
			</div>
		</div>
	</div>
	<nav class="navbar"> 
  		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand small-logo mobile-view" href="#"></a>
				<button type="button" class="navbar-toggle collapsed glyphicon glyphicon-menu-hamburger" data-toggle="collapse" data-target="#main-collapse" aria-expanded="false">
				</button>
			</div>  
			<div class="collapse navbar-collapse" id="main-collapse">
				<ul class="nav navbar-nav">
					<li><a href="${contextPath}/pub/policydetail.html">ข้อมูลผู้เอาประกัน</a></li>
					<li><a href="#">บริการกรมธรรม์</a></li>
					<li><a href="#">ชำระเบี้ยประกัน</a></li>
					<li><a href="#">เอกสารและแบบฟอร์ม</a></li>
					<li><a href="#">บริการสินไหม/ ร.พ. ในเครือ</a></li>
					<li><a href="#">OCLEAN CLUB</a></li>
					<li><a href="#">ติดต่อเรา</a></li>
					<li role="separator" class="divider visible-xs"></li>
		            <li class="visible-xs"><a href="#">TH</a>|<a href="#">EN</a></li>
			        
				</ul>
			</div>
		</div>
	</nav>
      <decorator:body />
	<footer class="container text-center footer">
		&nbsp;
	</footer>
	<script src="<s:url value="/" />scripts/css-dialog.js"></script>
	
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-9956859-3', 'auto');
  ga('send', 'pageview');

</script>
<c:if test="${not empty sessionScope.error_message}">
	<script>
	CSSDialog.warn('<h1>${sessionScope.error_message}</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
	</script>
</c:if>
<%
	session.removeAttribute("error_message");
%>
</body>
</html>