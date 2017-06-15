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
	<link rel="stylesheet" href="<s:url value="/" />css/common.css">

	<script src="<s:url value="/" />scripts/jquery-1.12.4.min.js"></script>
	<script src="<s:url value="/" />bootstrap/js/bootstrap.js"></script>
	<decorator:head />
</head>
<body>
	<nav class="navbar header"> 
  		<div class="container-fluid header-container">
			<div class="navbar-header">
				<a class="navbar-brand navbar-logo" >
					<img class="only hidden-xs" src="<s:url value="/" />images/pub_logo.png">
					<img class="substitute visible-xs" src="<s:url value="/" />images/pub_logo.png" alt="ไทยสมุทร">
				</a>
			</div>  
			<div class="collapse navbar-collapse" id="main-collapse">

			</div>
		</div>
	</nav>
	<div class="under-title-band">
		<h1><decorator:title default="OceanLife iService"/></h1>
	</div>
    <decorator:body />
	<footer class="text-center footer">
		<span class="license" title="<s:property value='remoteActionServiceUrl' />"><s:property value='pomArtifactId'/> <s:property value='pomVersion'/> <s:property value='svnRevision'/><s:property value='jenkinsBuildNumber'/></span>
        <span class="version"><%=thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION%></span>
	</footer>
	<script src="<s:url value="/" />scripts/css-dialog.js"></script>
	<script src="<s:url value="/" />scripts/css-validate.js"></script>
	
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