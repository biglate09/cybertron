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

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" class="yui3-loading">
  <head>
    <title><decorator:title default="Missing Page Title!!"/></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <meta name="URL" content="<s:url forceAddSchemeHostAndPort='true' includeParams='all' />">
    <link rel="shortcut icon" type="image/png" href="<s:url value="/" />images/oli-icon.png">
    <link href="<s:property value='jsYuiHome' />/3.9.0/build/cssreset/reset-min.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiHome' />/3.9.0/build/cssfonts/fonts-min.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiHome' />/3.9.0/build/cssbase/base-min.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiHome' />/3.9.0/build/cssbutton/cssbutton-min.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiGallery' />/2013-03-13/gallery-layout/assets/gallery-layout-core.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiGallery' />/2013-03-13/gallery-layout/assets/skins/sam/gallery-layout.css" rel="stylesheet" type="text/css">
    <link href="<s:property value='jsYuiGallery' />/2013-03-13/gallery-busyoverlay/assets/skins/sam/gallery-busyoverlay.css" rel="stylesheet" type="text/css">
    <link href="<s:url value="/" />css/default.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<s:property value='jsPrototypeHome' />/prototype-1.7.1.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiHome' />/3.9.0/build/yui/yui-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-layout/gallery-layout-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-layout-cols/gallery-layout-cols-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-dimensions/gallery-dimensions-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-node-optimizations/gallery-node-optimizations-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-funcprog/gallery-funcprog-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-object-extras/gallery-object-extras-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-nodelist-extras/gallery-nodelist-extras-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-nodelist-extras2/gallery-nodelist-extras2-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-busyoverlay/gallery-busyoverlay-min.js"></script>
    <script type="text/javascript" src="<s:property value='jsYuiGallery' />/2013-03-13/gallery-uuid/gallery-uuid-min.js"></script>
    <script type="text/javascript" src="<s:url value="/" />scripts/common.js"></script>
    <s:set name="credentials" value="userCredentials" />
    <script type="text/javascript">
      APP.namespace('config', { contextPath: "<s:url value="/" />".sub(/\/$/, '') });
      APP.namespace('config.menu', <s:property value="#credentials['Menu Json']" escape="false" escapeJavaScript="false" default="{}" />);
      APP.namespace('actions', {
          home: '<s:url action="home" namespace="/secure" />',
          logout: '<s:url action="logout" namespace="/secure" />'
      });
      APP.namespace('global',{
    	  receipt:{
    		  alert:'<s:text name="global.receipt.alert"/>'
    	  }
      });
    </script>
    <script type="text/javascript" src="<s:url value="/" />scripts/layout-manager.js"></script>
    <decorator:head />
  </head>
  <body class="yui3-skin-sam">
    <div class="layout-hd">
      <div id="topbar" style="<%= DevelopmentTextUtil.TOPBAR_DEVELOPMENT_STYLE %>">
          <span class="topbar-notification">
              <%= DevelopmentTextUtil.DEVELOPMENT_TEXT %>
          </span>
        <table>
          <tr>
          	<td id="user-fullName">
          		<a class="user-action" href="mailto:<s:property value="#credentials['Email']" />"><s:property value="#credentials['Full Name']" /></a>
          		<s:property value="#credentials['Branch Code']" /> <s:property value="#credentials['Branch Name']" />
          	</td>
          </tr>
          <tr><td id="user-loginSince"><s:text name="global.login-since" /> <s:property value="@java.lang.String@format(new java.util.Locale('th', 'TH'), '%1$tA %1$te %1$tB %1$tY %1$tT', #credentials['Login Since'])" /></td></tr>
          <tr><td id="user-actions"><br><a class="user-action" href="javascript:APP.commands.logout('<s:url action="logout" namespace="/secure" />')"><s:text name="global.logout" /></a></td></tr>
          <tr><td id="mainmenu"></td></tr>
        </table>
      </div>
    </div>
    <div class="layout-bd" style="visibility:hidden">
      <decorator:body />
    </div>
    <div class="layout-ft" style="visibility:hidden;">
      <div class="page-footer">
        <table>
          <tr>
            <td class="copyright">Copyright &copy; <%=Calendar.getInstance(Locale.US).get(Calendar.YEAR)%> Ocean Life Insurance Public Company Limited.&nbsp;&nbsp;All rights reserved.</td>
            <td class="appinfo"><%=thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION%></td>
          </tr>
      </div>
    </div>
  </body>
</html>
