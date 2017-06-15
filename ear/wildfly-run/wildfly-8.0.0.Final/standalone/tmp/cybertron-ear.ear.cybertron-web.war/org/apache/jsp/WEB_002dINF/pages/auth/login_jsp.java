package org.apache.jsp.WEB_002dINF.pages.auth;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
    _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction.release();
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      response.addHeader("X-Powered-By", "JSP/2.2");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("<title>OceanLife iService</title>\n");
      out.write("<link rel=\"shortcut icon\" href=\"");
      if (_jspx_meth_s_005furl_005f0(_jspx_page_context))
        return;
      out.write("images/favicon.ico\">\n");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_s_005furl_005f1(_jspx_page_context))
        return;
      out.write("bootstrap/css/bootstrap.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_s_005furl_005f2(_jspx_page_context))
        return;
      out.write("bootstrap/css/bootstrap-theme.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_s_005furl_005f3(_jspx_page_context))
        return;
      out.write("css/common.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_s_005furl_005f4(_jspx_page_context))
        return;
      out.write("css/login.css\">\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      if (_jspx_meth_s_005furl_005f5(_jspx_page_context))
        return;
      out.write("css/lang.css\">\n");
      out.write("<script src=\"");
      if (_jspx_meth_s_005furl_005f6(_jspx_page_context))
        return;
      out.write("scripts/jquery-1.12.4.min.js\"></script>\n");
      out.write("<script src=\"");
      if (_jspx_meth_s_005furl_005f7(_jspx_page_context))
        return;
      out.write("bootstrap/js/bootstrap.js\"></script>\n");
      out.write("<script>\n");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("</script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<script src=\"");
      if (_jspx_meth_s_005furl_005f8(_jspx_page_context))
        return;
      out.write("scripts/lang.min.js\"></script>\n");
      out.write("\t<div class=\"navbar navbar-compact navbar-static-top\">\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<a class=\"navbar-brand navbar-logo\" href=\"https://www.ocean.co.th/\">\n");
      out.write("\t\t\t\t<img src=\"");
      if (_jspx_meth_s_005furl_005f9(_jspx_page_context))
        return;
      out.write("images/pub_logo.png\">\n");
      out.write("\t\t\t</a>\n");
      out.write("\t\t\t<!-- <div class=\"collapse navbar-collapse navbar-right\">\n");
      out.write("\t\t\t\t<ul class=\"nav navbar-nav\">\n");
      out.write("\t\t\t\t\t<li><a lang=\"th\" href=\"javascript:void(0)\" onclick=\"selectLang('th')\">TH</a></li>\n");
      out.write("\t\t\t\t\t<li><a lang=\"en\" href=\"javascript:void(0)\" onclick=\"selectLang('en')\">EN</a></li>\n");
      out.write("\t\t\t\t</ul>\n");
      out.write("\t\t\t</div> -->\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"main-container\">\n");
      out.write("\t\t<div class=\"background\"></div>\n");
      out.write("\t\t<div class=\"col-md-5 float-right\">\n");
      out.write("\t\t\t");
      if (_jspx_meth_s_005fform_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\n");
      out.write("\t\t</div>\n");
      out.write("\t\t\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"div-heart-bg\"></div>\n");
      out.write("\t<div class=\"container previllage-content\">\n");
      out.write("\t\t<h1>ประโยชน์ของการเป็นสมาชิก OceanLife iService</h1>\n");
      out.write("\t\t<div class=\"previllage-detail\">\n");
      out.write("\t\t\t<div class=\"prev-grid ico mag\">\n");
      out.write("\t\t\t\t<h3>ตรวจสอบรายละเอียดและ<br/>ความคุ้มครองของกรมธรรม์</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico person\">\n");
      out.write("\t\t\t\t<h3>แก้ไขข้อมูลส่วนตัวและ<br/>ที่อยู่สำหรับติดต่อ</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico wallet\">\n");
      out.write("\t\t\t\t<h3>ชำระเบี้ยประกันภัยและ<br/>ตรวจสอบการชำระเบี้ยประกันภัย</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico doc\">\n");
      out.write("\t\t\t\t<h3>ขอหนังสือรับรองการชำระเบี้ยประกันชีวิต</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico noti\">\n");
      out.write("\t\t\t\t<h3>ขอรับใบแจ้งวันถึงกำหนดชำระเบี้ยประกันภัย</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico gift\">\n");
      out.write("\t\t\t\t<h3>สมัครสมาชิก Ocean Club และ ค้นหาสิทธิพิเศษ</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"prev-grid ico paper\">\n");
      out.write("\t\t\t\t<h3>ดาวน์โหลดเอกสารและ<br/>แบบฟอร์มต่างๆ</h3>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"heart-background\">\n");
      out.write("\t<div class=\"container contract-content\">\n");
      out.write("\t\t\t<div class=\"contract-grid tel\">\n");
      out.write("\t\t\t\t<a> </a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"contract-grid mail\">\n");
      out.write("\t\t\t\t<a> </a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"contract-grid fax\">\n");
      out.write("\t\t\t\t<a> </a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"contract-grid ocean-club\">\n");
      out.write("\t\t\t\t<a target=\"_blank\" href=\"https://www.oceanlifeonline.com/\"> </a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"contract-grid subscript\">\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"contract-grid facebook\">\n");
      out.write("\t\t\t\t<a target=\"_blank\" href=\"https://www.facebook.com/oceanlifepage\"></a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t<footer class=\"footer-darker\">\n");
      out.write("\t\t<span class=\"license\" title=\"");
      if (_jspx_meth_s_005fproperty_005f0(_jspx_page_context))
        return;
      out.write('"');
      out.write('>');
      if (_jspx_meth_s_005fproperty_005f1(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_s_005fproperty_005f2(_jspx_page_context))
        return;
      out.write(' ');
      if (_jspx_meth_s_005fproperty_005f3(_jspx_page_context))
        return;
      if (_jspx_meth_s_005fproperty_005f4(_jspx_page_context))
        return;
      out.write("</span>\n");
      out.write("         <span class=\"version\"> ");
      out.print(thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION);
      out.write("</span>\n");
      out.write("\t</footer>\n");
      out.write("\t</div>\n");
      out.write("\t<div class=\"footer-content\">\n");
      out.write("\t\t<h2>สงวนลิขสิทธิ์ พ.ศ. 2558 , บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) สำนักงานใหญ่ 170/74-83 อาคารโอเชี่ยนทาวเวอร์1 ถนนรัชดาภิเษก เขตคลองเตย กทม. 10110 โทร 0 2261 2300</h2>\n");
      out.write("\t\t<h2>Copyright 2015 Ocean Life Insurance Public Company Limited Head office 170/74-83 Ocean Tower1, Ratchadaphisek Road, Khlong Toei, Bangkok, 10110, Thailand, Tel 0 2261 2300 </h2>\n");
      out.write("\t</div>\n");
      out.write("\t<script src=\"");
      if (_jspx_meth_s_005furl_005f14(_jspx_page_context))
        return;
      out.write("scripts/css-dialog.js\"></script>\n");
      out.write("\t<script src=\"");
      if (_jspx_meth_s_005furl_005f15(_jspx_page_context))
        return;
      out.write("scripts/css-validate.js\"></script>\n");
      out.write("<script>\n");
      out.write("\tvar homeUrl = \"/cybertron/secure/home.html\";\n");
      out.write("\tvar memberInfoUrl = \"/cybertron/secure/member/personal/info.html\";\n");
      out.write("\t\n");
      out.write("\tfunction validate(e) {\n");
      out.write("\t\tvar username = $(\"#userName\").val().trim();\n");
      out.write("\t\tvar password = $(\"#password\").val().trim();\n");
      out.write("\t\t\n");
      out.write("\t\tif (username == '' && password == '') {\n");
      out.write("\t\t\tCSSDialog.warn(\"กรุณากรอกบัญชีผู้ใช้งานและรหัสผ่าน\");\n");
      out.write("\t\t\te.preventDefault();\n");
      out.write("\t\t\treturn false;\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\tif (username == '') {\n");
      out.write("\t\t\t\tCSSDialog.warn(\"กรุณากรอกบัญชีผู้ใช้งาน\");\n");
      out.write("\t\t\t\te.preventDefault();\n");
      out.write("\t\t\t\treturn false;\n");
      out.write("\t\t\t} else if (password == '') {\n");
      out.write("\t\t\t\tCSSDialog.warn(\"กรุณากรอกรหัสผ่าน\");\n");
      out.write("\t\t\t\te.preventDefault();\n");
      out.write("\t\t\t\treturn false;\n");
      out.write("\t\t\t} else {\n");
      out.write("\t\t\t\tif (username.length < 6) {\n");
      out.write("\t\t\t\t\tCSSDialog.warn(\"ท่านใส่บัญชีผู้ใช้งาน หรือ รหัสผ่าน ไม่ถูกต้อง\");\n");
      out.write("\t\t\t\t\te.preventDefault();\n");
      out.write("\t\t\t\t\treturn false;\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\tlogin();\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}\n");
      out.write("\n");
      out.write("\t\te.preventDefault();\n");
      out.write("\t}\n");
      out.write("\n");
      out.write("\t$(document).ready(function() {\n");
      out.write("\t\t$('#loginForm').on('submit',validate);\n");
      out.write("\t\t/*$('#btn_submit').on('click', validate);*/\n");
      out.write("\t\t$(\"input\").bind(\"keyup\", function(e) {\n");
      out.write("\t\t\tif (e.keyCode === 13) {\n");
      out.write("\t\t\t\tvar username = $(\"#userName\");\n");
      out.write("\t\t\t\tvar password = $(\"#password\");\n");
      out.write("\t\t\t\tif (username.val().trim() == \"\") {\n");
      out.write("\t\t\t\t\tusername.focus();\n");
      out.write("\t\t\t\t} else if (password.val().trim() == \"\") {\n");
      out.write("\t\t\t\t\tpassword.focus();\n");
      out.write("\t\t\t\t} else {\n");
      out.write("\t\t\t\t\tvalidate(e);\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t}\n");
      out.write("\t\t});\n");
      out.write("\t\t\n");
      out.write("\t\t$(document).keyup(function(ev){\n");
      out.write("            if(ev.keyCode == 27)\n");
      out.write("                CSSDialog.dismiss();\n");
      out.write("        });\n");
      out.write("\t\t\n");
      out.write("\t\t\n");
      out.write("\t\tfunction addContent(){\n");
      out.write("           \n");
      out.write("           var iframe= '<img src=\"");
      if (_jspx_meth_s_005furl_005f16(_jspx_page_context))
        return;
      out.write("images/usermanual.jpg\" class=\"img-responsive\">';\n");
      out.write("           \n");
      out.write("           return iframe;\n");
      out.write("        }\n");
      out.write("\t\t\n");
      out.write("\t\t$(\"#userManual\").on('click',function() {\n");
      out.write("            CSSDialog.custom(addContent(),\n");
      out.write("              [{label:'ปิดหน้าจอ',event:'cancel',className:'btn btn-default oli-default-button'}],'dialog-box-white')//,'dialog-box-white'\n");
      out.write("            .on('cancel',function(){});\n");
      out.write("       });\n");
      out.write("\t\t\n");
      out.write("\t});\n");
      out.write("\n");
      out.write("\tfunction login() {\n");
      out.write("\t\tvar username = $(\"#userName\").val().trim();\n");
      out.write("\t\tvar password = $(\"#password\").val().trim();\n");
      out.write("\t\t$.ajax({\n");
      out.write("\t\t\ttype : \"POST\",\n");
      out.write("\t\t\turl : \"../j_security_check\",\n");
      out.write("\t\t\tdata: { j_username: username, j_password: password },\n");
      out.write("\t\t\tsuccess : function(json) {\n");
      out.write("\t\t\t\tvar html = !json.data ? '' : json.data;\n");
      out.write("\t\t\t\tif (html.indexOf(errorAccountLocked) >= 0) {\n");
      out.write("\t\t\t\t\tCSSDialog.error(errorAccountLocked).on('ok', function() { $(\"#userName,#password\").val('');});\n");
      out.write("\t\t\t\t} else if (html.indexOf(errorInvalidCredentials) >= 0) {\n");
      out.write("\t\t\t\t\tCSSDialog.error(errorInvalidCredentials).on('ok',function() { $(\"#userName,#password\").val('');});\n");
      out.write("\t\t\t\t} else if(html ===\"Unknown\"){\n");
      out.write("\t\t\t\t\tCSSDialog.error(\"ท่านใส่รหัสผ่านไม่ถูกต้องเกินกำหนด IP Address ของท่านถูกระงับการใช้งานบริการ OceanLife iService ชั่วคราว<BR/>กรุณาทำการเข้าระบบใหม่อีกครั้งอีก 24 ชั่วโมง\").on('ok',function() {$(\"#userName,#password\").val('');});\n");
      out.write("\t\t\t\t}else {\n");
      out.write("\t\t\t\t\twindow.location = memberInfoUrl;\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t},\n");
      out.write("\t\t\terror : function(xhr, desc, exceptionobj) {\n");
      out.write("\t\t\t\t/*$( \"#ajax-loading\" ).hide();\n");
      out.write("\t\t\t\tCSSDialog.warn('The server is currently busy. Please wait ...').on('ok', function(){\n");
      out.write("\t\t\t\t\t$( \"#ajax-loading\" ).show();\n");
      out.write("\t\t\t\t\tsetTimeout(function(){\n");
      out.write("\t\t\t\t\t\twindow.location = \"/cybertron/secure/member/personal/info.html\";\n");
      out.write("\t\t\t\t\t},3000);\n");
      out.write("\t\t\t\t});*/\n");
      out.write("\t\t\t\twindow.location = memberInfoUrl;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t});\n");
      out.write("\t}\n");
      out.write("\t// \tscrollFx = function(e){\n");
      out.write("\t// \t\ttry{\n");
      out.write("\t// \t\tvar scroll = $(document).scrollTop()*0.25;\n");
      out.write("\t// \t\tvar bgPosX = $(\".main-container .background\").css(\"background-position\",\"\").css(\"background-position\").split(\" \")[0];\n");
      out.write("\t// \t\t$(\".main-container .background\").css(\"background-position\",bgPosX +\" \"+scroll+\"px\");\n");
      out.write("\t// \t\tvar under = ($(window).height()+$(document).scrollTop()-$(\"body\").height())*0.25;\n");
      out.write("\t// \t\tunder = under>0?0:under;\n");
      out.write("\t// \t\t$(\".contract-content\").css(\"top\",under+\"px\");\n");
      out.write("\t// \t\t}catch(ignored){}\n");
      out.write("\t// \t}\n");
      out.write("\t// \t$(document).off('scroll').off('resize').on( \"scroll\",scrollFx).on(\"resize\",scrollFx);\n");
      out.write("</script>\n");
      out.write("\t\n");
      out.write("<script>\n");
      out.write("  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n");
      out.write("  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n");
      out.write("  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n");
      out.write("  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');\n");
      out.write("\n");
      out.write("  ga('create', 'UA-9956859-3', 'auto');\n");
      out.write("  ga('send', 'pageview');\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");

	session.removeAttribute(thaisamut.nbs.struts.action.AuthAction.SES_LOGIN_ERROR_DETAIL);

      out.write('\n');
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write('\n');

	session.removeAttribute("error_message");

      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_s_005furl_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f0 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f0.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(10,32) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f0.setValue("/");
    int _jspx_eval_s_005furl_005f0 = _jspx_th_s_005furl_005f0.doStartTag();
    if (_jspx_th_s_005furl_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f1 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f1.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f1.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(12,29) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f1.setValue("/");
    int _jspx_eval_s_005furl_005f1 = _jspx_th_s_005furl_005f1.doStartTag();
    if (_jspx_th_s_005furl_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f1);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f2 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f2.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f2.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(13,29) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f2.setValue("/");
    int _jspx_eval_s_005furl_005f2 = _jspx_th_s_005furl_005f2.doStartTag();
    if (_jspx_th_s_005furl_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f2);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f3 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f3.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f3.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(14,29) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f3.setValue("/");
    int _jspx_eval_s_005furl_005f3 = _jspx_th_s_005furl_005f3.doStartTag();
    if (_jspx_th_s_005furl_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f3);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f4 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f4.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f4.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(15,29) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f4.setValue("/");
    int _jspx_eval_s_005furl_005f4 = _jspx_th_s_005furl_005f4.doStartTag();
    if (_jspx_th_s_005furl_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f4);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f5 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f5.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f5.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(16,29) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f5.setValue("/");
    int _jspx_eval_s_005furl_005f5 = _jspx_th_s_005furl_005f5.doStartTag();
    if (_jspx_th_s_005furl_005f5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f5);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f5);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f6 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f6.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f6.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(17,13) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f6.setValue("/");
    int _jspx_eval_s_005furl_005f6 = _jspx_th_s_005furl_005f6.doStartTag();
    if (_jspx_th_s_005furl_005f6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f6);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f6);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f7 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f7.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f7.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(18,13) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f7.setValue("/");
    int _jspx_eval_s_005furl_005f7 = _jspx_th_s_005furl_005f7.doStartTag();
    if (_jspx_th_s_005furl_005f7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f7);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f7);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(20,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not empty sessionScope.loginErrorDetail}", boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\tvar loginErrorDetail = 'ERROR:");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.loginErrorDetail}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write('\'');
        out.write('\n');
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f8 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f8.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f8.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(26,14) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f8.setValue("/");
    int _jspx_eval_s_005furl_005f8 = _jspx_th_s_005furl_005f8.doStartTag();
    if (_jspx_th_s_005furl_005f8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f8);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f8);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f9(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f9 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f9.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f9.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(30,14) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f9.setValue("/");
    int _jspx_eval_s_005furl_005f9 = _jspx_th_s_005furl_005f9.doStartTag();
    if (_jspx_th_s_005furl_005f9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f9);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f9);
    return false;
  }

  private boolean _jspx_meth_s_005fform_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_005fform_005f0 = (org.apache.struts2.views.jsp.ui.FormTag) _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_005fform_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005fform_005f0.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(43,3) name = id type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fform_005f0.setId("loginForm");
    // /WEB-INF/pages/auth/login.jsp(43,3) name = action type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fform_005f0.setAction("");
    // /WEB-INF/pages/auth/login.jsp(43,3) null
    _jspx_th_s_005fform_005f0.setDynamicAttribute(null, "autocomplete", "off");
    int _jspx_eval_s_005fform_005f0 = _jspx_th_s_005fform_005f0.doStartTag();
    if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fform_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fform_005f0.doInitBody();
      }
      do {
        out.write("\n");
        out.write("\t\t\t\t<h2>OceanLife iService</h2>\n");
        out.write("\t\t\t\t<div class=\"form-group\">\n");
        out.write("\t\t\t\t\t<input type=\"text\" class=\"form-control\" name=\"j_username\" id=\"userName\" autocomplete=\"off\" placeholder=\"บัญชีผู้ใช้งาน\" tabindex=\"1\" focused>\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t\t<div class=\"form-group\">\n");
        out.write("\t\t\t\t\t<input type=\"password\" class=\"form-control\" name=\"j_password\" id=\"password\" placeholder=\"รหัสผ่าน\" tabindex=\"2\">\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t\t<div class=\"row\">\n");
        out.write("\t\t\t\t\t<div class=\"col-xs-6\">\n");
        out.write("\t\t\t\t\t\t<button id=\"btn_submit\" type=\"submit\" class=\"btn btn-default oli-login-button\" onclick=\"ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'member', eventAction: 'submit', eventLabel:'login'});\">เข้าระบบ</button>\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t<div class=\"col-xs-6\">\n");
        out.write("\t\t\t\t\t\t<a class=\"btn btn-default oli-register-button\" href=\"");
        if (_jspx_meth_s_005furl_005f10(_jspx_th_s_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\" onclick=\"ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'signup'});\">สมัครสมาชิก</a>\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t\t<div class=\"bottom-menu\">\n");
        out.write("\t\t\t\t\t<div class=\"col-xs-6\">\n");
        out.write("\t\t\t\t\t\t<div class=\"form-group\">\n");
        out.write("\t\t\t\t\t\t\t<div>\n");
        out.write("\t\t\t\t\t\t\t\t<a href=\"");
        if (_jspx_meth_s_005furl_005f11(_jspx_th_s_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\" onclick=\"ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'forgot'});\">ลืมบัญชีผู้ใช้งานและรหัสผ่าน</a>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t<div>\n");
        out.write("\t\t\t\t\t\t\t\t<a href=\"");
        if (_jspx_meth_s_005furl_005f12(_jspx_th_s_005fform_005f0, _jspx_page_context))
          return true;
        out.write("\" onclick=\"ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel: 'changepass'});\">เปลี่ยนรหัสผ่าน</a>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t<div class=\"col-xs-6 div-link-manual\">\n");
        out.write("\t\t\t\t\t\t<div>\n");
        out.write("\t\t\t\t\t\t\t<a class=\"userManual\" id=\"userManual\" onclick=\"ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'manual'});\"><img src=\"");
        if (_jspx_meth_s_005furl_005f13(_jspx_th_s_005fform_005f0, _jspx_page_context))
          return true;
        out.write("images/manual.png\" id=\"imgClick\"></a>\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_s_005fform_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction.reuse(_jspx_th_s_005fform_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fform_0026_005fid_005fautocomplete_005faction.reuse(_jspx_th_s_005fform_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f10(javax.servlet.jsp.tagext.JspTag _jspx_th_s_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f10 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f10.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_005fform_005f0);
    // /WEB-INF/pages/auth/login.jsp(56,59) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f10.setValue("/pub/page/signup.html");
    int _jspx_eval_s_005furl_005f10 = _jspx_th_s_005furl_005f10.doStartTag();
    if (_jspx_th_s_005furl_005f10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f10);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f10);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f11(javax.servlet.jsp.tagext.JspTag _jspx_th_s_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f11 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f11.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_005fform_005f0);
    // /WEB-INF/pages/auth/login.jsp(63,17) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f11.setValue("/pub/page/forget/index.html");
    int _jspx_eval_s_005furl_005f11 = _jspx_th_s_005furl_005f11.doStartTag();
    if (_jspx_th_s_005furl_005f11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f11);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f11);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f12(javax.servlet.jsp.tagext.JspTag _jspx_th_s_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f12 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f12.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_005fform_005f0);
    // /WEB-INF/pages/auth/login.jsp(66,17) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f12.setValue("/pub/page/changepassword.html");
    int _jspx_eval_s_005furl_005f12 = _jspx_th_s_005furl_005f12.doStartTag();
    if (_jspx_th_s_005furl_005f12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f12);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f12);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f13(javax.servlet.jsp.tagext.JspTag _jspx_th_s_005fform_005f0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f13 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f13.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_s_005fform_005f0);
    // /WEB-INF/pages/auth/login.jsp(72,156) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f13.setValue("/");
    int _jspx_eval_s_005furl_005f13 = _jspx_th_s_005furl_005f13.doStartTag();
    if (_jspx_th_s_005furl_005f13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f13);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f13);
    return false;
  }

  private boolean _jspx_meth_s_005fproperty_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f0 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f0.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(131,31) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fproperty_005f0.setValue("remoteActionServiceUrl");
    int _jspx_eval_s_005fproperty_005f0 = _jspx_th_s_005fproperty_005f0.doStartTag();
    if (_jspx_th_s_005fproperty_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005fproperty_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f1 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f1.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f1.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(131,78) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fproperty_005f1.setValue("pomArtifactId");
    int _jspx_eval_s_005fproperty_005f1 = _jspx_th_s_005fproperty_005f1.doStartTag();
    if (_jspx_th_s_005fproperty_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f1);
    return false;
  }

  private boolean _jspx_meth_s_005fproperty_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f2 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f2.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f2.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(131,114) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fproperty_005f2.setValue("pomVersion");
    int _jspx_eval_s_005fproperty_005f2 = _jspx_th_s_005fproperty_005f2.doStartTag();
    if (_jspx_th_s_005fproperty_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f2);
    return false;
  }

  private boolean _jspx_meth_s_005fproperty_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f3 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f3.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f3.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(131,147) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fproperty_005f3.setValue("svnRevision");
    int _jspx_eval_s_005fproperty_005f3 = _jspx_th_s_005fproperty_005f3.doStartTag();
    if (_jspx_th_s_005fproperty_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f3);
    return false;
  }

  private boolean _jspx_meth_s_005fproperty_005f4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f4 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f4.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f4.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(131,180) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005fproperty_005f4.setValue("jenkinsBuildNumber");
    int _jspx_eval_s_005fproperty_005f4 = _jspx_th_s_005fproperty_005f4.doStartTag();
    if (_jspx_th_s_005fproperty_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f4);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f14 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f14.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f14.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(139,14) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f14.setValue("/");
    int _jspx_eval_s_005furl_005f14 = _jspx_th_s_005furl_005f14.doStartTag();
    if (_jspx_th_s_005furl_005f14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f14);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f14);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f15(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f15 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f15.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f15.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(140,14) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f15.setValue("/");
    int _jspx_eval_s_005furl_005f15 = _jspx_th_s_005furl_005f15.doStartTag();
    if (_jspx_th_s_005furl_005f15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f15);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f15);
    return false;
  }

  private boolean _jspx_meth_s_005furl_005f16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:url
    org.apache.struts2.views.jsp.URLTag _jspx_th_s_005furl_005f16 = (org.apache.struts2.views.jsp.URLTag) _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.URLTag.class);
    _jspx_th_s_005furl_005f16.setPageContext(_jspx_page_context);
    _jspx_th_s_005furl_005f16.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(200,34) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_s_005furl_005f16.setValue("/");
    int _jspx_eval_s_005furl_005f16 = _jspx_th_s_005furl_005f16.doStartTag();
    if (_jspx_th_s_005furl_005f16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f16);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005furl_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005furl_005f16);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /WEB-INF/pages/auth/login.jsp(273,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false deferredMethod = false expectedTypeName = null methodSignature = null 
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${not empty sessionScope.error_message}", boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t<script>\n");
        out.write("\t\tCSSDialog.warn('<h1>");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${sessionScope.error_message}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');\n");
        out.write("\t</script>\n");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }
}
