<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC 
  "-//W3C//DTD XHTML 1.1 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <title><s:text name="global.title" /></title>
    <meta charset="UTF-8">
    <meta name="keywords" content="ACCESS_FORBIDDEN, Access Forbidden">
    <link href="<s:url value="/css/home.css" />" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<s:url value='/scripts/home.js' />"></script>
    <script type="text/javascript" src="<s:url value='/scripts/forbidden.js' />"></script>
  </head>
  <body>
    <div class="layout-module-col">
      <div class="layout-module">
        <div class="layout-m-bd error">
          Error: Access Forbidden
        </div>
      </div>
    </div>
  </body>
</html>
