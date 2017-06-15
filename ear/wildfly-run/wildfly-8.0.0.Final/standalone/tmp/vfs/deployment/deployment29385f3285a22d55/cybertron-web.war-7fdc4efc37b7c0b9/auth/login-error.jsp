<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>

<% if (null == request.getUserPrincipal()) { %>
<%
  WebApplicationContext spring = (WebApplicationContext) application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
  String jsYuiHome = (String) spring.getBean("jsYuiHome");
  String jsYuiGallery = (String) spring.getBean("jsYuiGallery");
  String jsPrototypeHome = (String) spring.getBean("jsPrototypeHome");
  String contextPath = request.getContextPath();

    Exception foundLoginException = (Exception)
        org.jboss.security.SecurityContextAssociation.getContextInfo("org.jboss.security.exception");

    thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil.LDAP_CAUSE_CODE cause =
            thaisamut.pantheon.servlet.ad.LDAPLoginErrorUtil.parseLoginError(foundLoginException);

%>
<!DOCTYPE html PUBLIC 
  "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title>NBS Data File Submission ~ Login Error</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta name="keywords" content="LOGIN_ERROR_PAGE, <%=cause.getMsg()%>">
    <link rel="shortcut icon" type="image/png" href="<%=contextPath%>/images/oli-icon.png">
    <link href="<%=jsYuiHome%>/3.9.0/build/cssreset/reset-min.css" rel="stylesheet" type="text/css">
    <link href="<%=jsYuiHome%>/3.9.0/build/cssfonts/fonts-min.css" rel="stylesheet" type="text/css">
    <link href="<%=jsYuiHome%>/3.9.0/build/cssbase/base-min.css" rel="stylesheet" type="text/css">
    <link href="<%=jsYuiGallery%>/2013-03-13/gallery-layout/assets/gallery-layout-core.css" rel="stylesheet" type="text/css">
    <link href="<%=jsYuiGallery%>/2013-03-13/gallery-layout/assets/skins/sam/gallery-layout.css" rel="stylesheet" type="text/css">
    <link href="<%=contextPath%>/css/auth.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=jsPrototypeHome%>/prototype-1.7.1.js"></script>
    <script type="text/javascript" src="<%=jsYuiHome%>/3.9.0/build/yui/yui-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-layout/gallery-layout-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-layout-cols/gallery-layout-cols-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-dimensions/gallery-dimensions-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-node-optimizations/gallery-node-optimizations-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-funcprog/gallery-funcprog-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-object-extras/gallery-object-extras-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-nodelist-extras/gallery-nodelist-extras-min.js"></script>
    <script type="text/javascript" src="<%=jsYuiGallery%>/2013-03-13/gallery-nodelist-extras2/gallery-nodelist-extras2-min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/scripts/common.js"></script>
  </head>
  <body>
    <div class="layout-hd">
      <div id="topbar"></div>
    </div>
    <div class="layout-bd" style="visibility:hidden;">
      <div class="layout-module-col">
        <div class="layout-module"></div>
      </div>
      <div class="layout-module-col width:25%">
        <div class="layout-module height:25%">
          <div class="layout-m-hd">
            <div class="title error">Invalid Login</div>
          </div>
          <div class="layout-m-bd error">
            Invalid username and/or password
          </div>
          <div class="layout-m-ft">Retry</div>
        </div>
      </div>
      <div class="layout-module-col">
        <div class="layout-module"></div>
      </div>
    </div>
    <div class="layout-ft" style="visibility:hidden;">
      <div class="page-footer">Copyright &copy; <%=Calendar.getInstance(Locale.US).get(Calendar.YEAR)%> Ocean Life Insurance Public Company Limited.&nbsp;&nbsp;All rights reserved.</div>
    </div>
  </body>
</html>
<% } else { response.sendRedirect("../index.jsp"); } %>
