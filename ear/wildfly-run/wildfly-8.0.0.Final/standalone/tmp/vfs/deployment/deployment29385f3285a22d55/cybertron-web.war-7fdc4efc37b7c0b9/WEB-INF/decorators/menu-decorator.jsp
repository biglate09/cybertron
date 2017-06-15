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
    <link rel="shortcut icon" href="${contextPath}/images/favicon.ico">
	
	<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap-theme.css">
	<link rel="stylesheet" href="<s:url value="/" />css/tooltip-theme-arrows.css">
	<link rel="stylesheet" href="<s:url value="/" />css/common.css">
	<link rel="stylesheet" href="<s:url value="/" />css/lang.css">

	<script src="<s:url value="/" />scripts/jquery-1.12.4.min.js"></script>
	<script src="<s:url value="/" />bootstrap/js/bootstrap.js"></script>
	<decorator:head />
	<script>
		var REPORT_URLS={
			download:{
				cert: '${contextPath}/secure/member/document/downloadcertify.html',
				notify: '${contextPath}/secure/member/document/downloadnotify.html'
					},
			email:{
				cert: '${contextPath}/secure/member/document/emailcertify.html',
				notify: '${contextPath}/secure/member/document/emailnotify.html'
				},
			available: '${contextPath}/secure/member/document/available.html'
		},
		MENU_LINK = {
				editAddress : "${contextPath}/secure/member/personal/edit.html"
		},
		ACTIONS_MENU = {
				checkOceanClub : "${contextPath}/secure/ocean/club/checkOceanClub.html",
				checkOceanCareCard : "${contextPath}/secure/ocean/care/card/checkOceanCareCard.html"
		}
	</script>
</head>
<body>
	<input type="hidden" id="contextPath" value="${contextPath}" />
	<script src="<s:url value="/" />scripts/lang.min.js"></script>
	<nav class="navbar header"> 
  		<div class="container-fluid header-container">
			<div class="navbar-header">
				<a class="navbar-brand navbar-logo" href="javascript:void(0)" onclick="gotoOcean(); ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'logolink', eventAction: 'click', eventLabel:'logout'});">
					<img class="main" src="<s:url value="/" />images/pub_logo.png">
					<img class="substitute hidden-xs" src="<s:url value="/" />images/pub_logo.png" alt="ไทยสมุทร">
					<img class="substitute visible-xs" src="<s:url value="/" />images/pub_logo.png" alt="ไทยสมุทร">
				</a>
				<button type="button" class="navbar-toggle collapsed glyphicon glyphicon-menu-hamburger" data-toggle="collapse" data-target="#main-collapse" aria-expanded="false">
				</button>
			</div>  
			<div class="collapse navbar-collapse" id="main-collapse">
				<ul class="nav navbar-nav  navbar-right">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown"  href="javascript:void(0)">ข้อมูลผู้เอาประกัน</a>
						<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}/secure/member/personal/info.html">ข้อมูลผู้เอาประกัน</a></li>
							<li><a href="${contextPath}/secure/changepassword.html">เปลี่ยนรหัสผ่าน</a></li>
						</ul>
					</li>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">บริการกรมธรรม์</a>
						<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${contextPath}/secure/member/policy/policyinfo.html">ข้อมูลกรมธรรม์</a></li>
<!-- 							<li><a href="#">ติดตามเรียกร้องสินไหม</a></li> -->
							<li><a href="${contextPath}/secure/claim/history.html">ติดตามและดูประวัติการเรียกร้องสินไหม</a></li>
							<li><a id="ocean-care-card">Ocean Care Card</a></li>
						</ul>
					</li>
<%-- 					<li><a href="${contextPath}/secure/member/policy/policyinfo.html">บริการกรมธรรม์</a></li> --%>
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">เอกสารและแบบฟอร์ม</a>
						<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a target="_blank" href="https://www.ocean.co.th/%E0%B9%80%E0%B8%AD%E0%B8%81%E0%B8%AA%E0%B8%B2%E0%B8%A3%E0%B9%80%E0%B8%9A%E0%B8%B5%E0%B9%89%E0%B8%A2%E0%B8%9B%E0%B8%A3%E0%B8%B0%E0%B8%81%E0%B8%B1%E0%B8%99%E0%B8%A0%E0%B8%B1%E0%B8%A2" onclick="ga('send', 'event', {eventCategory: 'outboundlink',eventAction: 'click', eventLabel:'download'});">ดาวน์โหลดเอกสาร</a></li>
							<li><a href="${contextPath}/secure/request/change/choice.html">ยื่นคำขอเปลี่ยนแปลงข้อมูลออนไลน์</a></li>
						</ul>
					</li>
<!-- 					<li><a target="_blank" href="https://www.ocean.co.th/%E0%B8%AA%E0%B8%B4%E0%B8%97%E0%B8%98%E0%B8%B4%E0%B8%9E%E0%B8%B4%E0%B9%80%E0%B8%A8%E0%B8%A9%E0%B8%AA%E0%B8%B3%E0%B8%AB%E0%B8%A3%E0%B8%B1%E0%B8%9A%E0%B8%A5%E0%B8%B9%E0%B8%81%E0%B8%84%E0%B9%89%E0%B8%B2/%E0%B9%82%E0%B8%AD%E0%B9%80%E0%B8%8A%E0%B8%B5%E0%B9%88%E0%B8%A2%E0%B8%99%E0%B8%84%E0%B8%A5%E0%B8%B1%E0%B8%9A%E0%B9%81%E0%B8%A5%E0%B8%B0%E0%B8%AA%E0%B8%B4%E0%B8%97%E0%B8%98%E0%B8%B4%E0%B8%9B%E0%B8%A3%E0%B8%B0%E0%B9%82%E0%B8%A2%E0%B8%8A%E0%B8%99%E0%B9%8C">OCEAN CLUB</a></li> -->
					<li><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0); ga('send', 'event', { eventCategory:'outboundlink', eventAction: 'click',eventLabel: 'oceanclub'});">OCEAN CLUB</a>
						<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a id="ocean-club">เกี่ยวกับโครงการ / สมัครสมาชิก</a></li>
							<li><a id="ocean-club-member">ข้อมูลสมาชิก</a></li>
							<%--<li><a id="ocean-club-campaign">ทดสอบ</a></li>--%>
							<li><a href="${contextPath}/secure/ocean/club/listplace.html">ดูสิทธิพิเศษส่วนลดทั้งหมด</a></li>
							<li><a href="${contextPath}/secure/ocean/club/listdiscount.html">ดูกิจกรรมทั้งหมด</a></li>
							<li><a href="${contextPath}/secure/ocean/club/editprofile.html">แก้ไขประวัติส่วนตัว</a></li>
							<li><a href="${contextPath}/secure/ocean/club/viewhistory.html">ดูประวัติการใช้สิทธิ</a></li>
							<li><a href="${contextPath}/secure/ocean/club/oceanBranch.html">สาขาไทยสมุทร</a></li>
						</ul>
					</li>
					<li><a target="_blank" href="https://www.ocean.co.th/%E0%B8%95%E0%B8%B4%E0%B8%94%E0%B8%95%E0%B9%88%E0%B8%AD%E0%B9%80%E0%B8%A3%E0%B8%B2/%E0%B8%95%E0%B8%B4%E0%B8%94%E0%B8%95%E0%B9%88%E0%B8%AD%E0%B9%80%E0%B8%A3%E0%B8%B2" onclick="ga('send', 'event', { eventCategory:'outboundlink', eventAction: 'click',eventLabel: 'contact'});">ติดต่อเรา</a></li>
					<li class="nav-divider"></li>
					<li><a class="logout-button" href="${contextPath}/secure/logout.html">ออกจากระบบ</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="under-title-band">
		<h1><decorator:title default="OceanLife iService"/></h1>
	</div>
	<div class="container notify-data-date">ข้อมูล ณ วันที่ ${yesterday}</div>
      <decorator:body />
	<footer class="text-center footer">
		<span class="license" title="<s:property value='remoteActionServiceUrl' />"><s:property value='pomArtifactId'/> <s:property value='pomVersion'/> <s:property value='svnRevision'/><s:property value='jenkinsBuildNumber'/></span>
         <span class="version"> <%=thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION%></span>
	</footer>
	<script src="<s:url value="/" />scripts/tether.min.js"></script>
	<script src="<s:url value="/" />scripts/drop.min.js"></script>
	<script src="<s:url value="/" />scripts/tooltip.min.js"></script>
	<script src="<s:url value="/" />scripts/css-dialog.js"></script>
	<script src="<s:url value="/" />scripts/css-validate.js"></script>
	<script src="<s:url value="/" />scripts/sticky-header.js"></script>
	<script src="<s:url value="/" />scripts/css-common.js"></script>
	
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