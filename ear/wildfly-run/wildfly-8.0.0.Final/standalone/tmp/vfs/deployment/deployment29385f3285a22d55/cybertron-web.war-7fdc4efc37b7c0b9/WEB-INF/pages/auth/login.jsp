<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>OceanLife iService</title>
<link rel="shortcut icon" href="<s:url value="/" />images/favicon.ico">

<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="<s:url value="/" />bootstrap/css/bootstrap-theme.css">
<link rel="stylesheet" href="<s:url value="/" />css/common.css">
<link rel="stylesheet" href="<s:url value="/" />css/login.css">
<link rel="stylesheet" href="<s:url value="/" />css/lang.css">
<script src="<s:url value="/" />scripts/jquery-1.12.4.min.js"></script>
<script src="<s:url value="/" />bootstrap/js/bootstrap.js"></script>
<script>
<c:if test="${not empty sessionScope.loginErrorDetail}">
	var loginErrorDetail = 'ERROR:${sessionScope.loginErrorDetail}'
</c:if>
</script>
</head>
<body>
	<script src="<s:url value="/" />scripts/lang.min.js"></script>
	<div class="navbar navbar-compact navbar-static-top">
		<div class="container">
			<a class="navbar-brand navbar-logo" href="https://www.ocean.co.th/">
				<img src="<s:url value="/" />images/pub_logo.png">
			</a>
			<!-- <div class="collapse navbar-collapse navbar-right">
				<ul class="nav navbar-nav">
					<li><a lang="th" href="javascript:void(0)" onclick="selectLang('th')">TH</a></li>
					<li><a lang="en" href="javascript:void(0)" onclick="selectLang('en')">EN</a></li>
				</ul>
			</div> -->
		</div>
	</div>
	<div class="main-container">
		<div class="background"></div>
		<div class="col-md-5 float-right">
			<s:form id="loginForm" action="" autocomplete="off">
				<h2>OceanLife iService</h2>
				<div class="form-group">
					<input type="text" class="form-control" name="j_username" id="userName" autocomplete="off" placeholder="บัญชีผู้ใช้งาน" tabindex="1" focused>
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="j_password" id="password" placeholder="รหัสผ่าน" tabindex="2">
				</div>
				<div class="row">
					<div class="col-xs-6">
						<button id="btn_submit" type="submit" class="btn btn-default oli-login-button" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'member', eventAction: 'submit', eventLabel:'login'});">เข้าระบบ</button>
					</div>
					<div class="col-xs-6">
						<a class="btn btn-default oli-register-button" href="<s:url value="/pub/page/signup.html" />" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'signup'});">สมัครสมาชิก</a>
					</div>
				</div>
				<div class="bottom-menu">
					<div class="col-xs-6">
						<div class="form-group">
							<div>
								<a href="<s:url value="/pub/page/forget/index.html" />" onclick="ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'forgot'});">ลืมบัญชีผู้ใช้งานและรหัสผ่าน</a>
							</div>
							<div>
								<a href="<s:url value="/pub/page/changepassword.html" />" onclick="ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel: 'changepass'});">เปลี่ยนรหัสผ่าน</a>
							</div>
						</div>
					</div>
					<div class="col-xs-6 div-link-manual">
						<div>
							<a class="userManual" id="userManual" onclick="ga('send', 'event', { eventCategory:'member', eventAction: 'click', eventLabel:'manual'});"><img src="<s:url value="/" />images/manual.png" id="imgClick"></a>
						</div>
						
					</div>
					
				</div>
			</s:form>
			
		</div>
		
	</div>
	<div class="div-heart-bg"></div>
	<div class="container previllage-content">
		<h1>ประโยชน์ของการเป็นสมาชิก OceanLife iService</h1>
		<div class="previllage-detail">
			<div class="prev-grid ico mag">
				<h3>ตรวจสอบรายละเอียดและ<br/>ความคุ้มครองของกรมธรรม์</h3>
			</div>
			<div class="prev-grid ico person">
				<h3>แก้ไขข้อมูลส่วนตัวและ<br/>ที่อยู่สำหรับติดต่อ</h3>
			</div>
			<div class="prev-grid ico wallet">
				<h3>ชำระเบี้ยประกันภัยและ<br/>ตรวจสอบการชำระเบี้ยประกันภัย</h3>
			</div>
			<div class="prev-grid ico doc">
				<h3>ขอหนังสือรับรองการชำระเบี้ยประกันชีวิต</h3>
			</div>
			<div class="prev-grid ico noti">
				<h3>ขอรับใบแจ้งวันถึงกำหนดชำระเบี้ยประกันภัย</h3>
			</div>
			<div class="prev-grid ico gift">
				<h3>สมัครสมาชิก Ocean Club และ ค้นหาสิทธิพิเศษ</h3>
			</div>
			<div class="prev-grid ico paper">
				<h3>ดาวน์โหลดเอกสารและ<br/>แบบฟอร์มต่างๆ</h3>
			</div>
		</div>
	</div>
	<div class="heart-background">
	<div class="container contract-content">
			<div class="contract-grid tel">
				<a> </a>
			</div>
			<div class="contract-grid mail">
				<a> </a>
			</div>
			<div class="contract-grid fax">
				<a> </a>
			</div>
			<div class="contract-grid ocean-club">
				<a target="_blank" href="https://www.oceanlifeonline.com/"> </a>
			</div>
			<div class="contract-grid subscript">
			</div>
			<div class="contract-grid facebook">
				<a target="_blank" href="https://www.facebook.com/oceanlifepage"></a>
			</div>
	</div>
	<footer class="footer-darker">
		<span class="license" title="<s:property value='remoteActionServiceUrl' />"><s:property value='pomArtifactId'/> <s:property value='pomVersion'/> <s:property value='svnRevision'/><s:property value='jenkinsBuildNumber'/></span>
         <span class="version"> <%=thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION%></span>
	</footer>
	</div>
	<div class="footer-content">
		<h2>สงวนลิขสิทธิ์ พ.ศ. 2558 , บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) สำนักงานใหญ่ 170/74-83 อาคารโอเชี่ยนทาวเวอร์1 ถนนรัชดาภิเษก เขตคลองเตย กทม. 10110 โทร 0 2261 2300</h2>
		<h2>Copyright 2015 Ocean Life Insurance Public Company Limited Head office 170/74-83 Ocean Tower1, Ratchadaphisek Road, Khlong Toei, Bangkok, 10110, Thailand, Tel 0 2261 2300 </h2>
	</div>
	<script src="<s:url value="/" />scripts/css-dialog.js"></script>
	<script src="<s:url value="/" />scripts/css-validate.js"></script>
<script>
	var homeUrl = "/cybertron/secure/home.html";
	var memberInfoUrl = "/cybertron/secure/member/personal/info.html";
	
	function validate(e) {
		var username = $("#userName").val().trim();
		var password = $("#password").val().trim();
		
		if (username == '' && password == '') {
			CSSDialog.warn("กรุณากรอกบัญชีผู้ใช้งานและรหัสผ่าน");
			e.preventDefault();
			return false;
		}else{
			if (username == '') {
				CSSDialog.warn("กรุณากรอกบัญชีผู้ใช้งาน");
				e.preventDefault();
				return false;
			} else if (password == '') {
				CSSDialog.warn("กรุณากรอกรหัสผ่าน");
				e.preventDefault();
				return false;
			} else {
				if (username.length < 6) {
					CSSDialog.warn("ท่านใส่บัญชีผู้ใช้งาน หรือ รหัสผ่าน ไม่ถูกต้อง");
					e.preventDefault();
					return false;
				}
				login();
			}
		}

		e.preventDefault();
	}

	$(document).ready(function() {
		$('#loginForm').on('submit',validate);
		/*$('#btn_submit').on('click', validate);*/
		$("input").bind("keyup", function(e) {
			if (e.keyCode === 13) {
				var username = $("#userName");
				var password = $("#password");
				if (username.val().trim() == "") {
					username.focus();
				} else if (password.val().trim() == "") {
					password.focus();
				} else {
					validate(e);
				}
			}
		});
		
		$(document).keyup(function(ev){
            if(ev.keyCode == 27)
                CSSDialog.dismiss();
        });
		
		
		function addContent(){
           
           var iframe= '<img src="<s:url value="/" />images/usermanual.jpg" class="img-responsive">';
           
           return iframe;
        }
		
		$("#userManual").on('click',function() {
            CSSDialog.custom(addContent(),
              [{label:'ปิดหน้าจอ',event:'cancel',className:'btn btn-default oli-default-button'}],'dialog-box-white')//,'dialog-box-white'
            .on('cancel',function(){});
       });
		
	});

	function login() {
		var username = $("#userName").val().trim();
		var password = $("#password").val().trim();
		$.ajax({
			type : "POST",
			url : "../j_security_check",
			data: { j_username: username, j_password: password },
			success : function(json) {
				var html = !json.data ? '' : json.data;
				if (html.indexOf(errorAccountLocked) >= 0) {
					CSSDialog.error(errorAccountLocked).on('ok', function() { $("#userName,#password").val('');});
				} else if (html.indexOf(errorInvalidCredentials) >= 0) {
					CSSDialog.error(errorInvalidCredentials).on('ok',function() { $("#userName,#password").val('');});
				} else if(html ==="Unknown"){
					CSSDialog.error("ท่านใส่รหัสผ่านไม่ถูกต้องเกินกำหนด IP Address ของท่านถูกระงับการใช้งานบริการ OceanLife iService ชั่วคราว<BR/>กรุณาทำการเข้าระบบใหม่อีกครั้งอีก 24 ชั่วโมง").on('ok',function() {$("#userName,#password").val('');});
				}else {
					window.location = memberInfoUrl;
				}
			},
			error : function(xhr, desc, exceptionobj) {
				/*$( "#ajax-loading" ).hide();
				CSSDialog.warn('The server is currently busy. Please wait ...').on('ok', function(){
					$( "#ajax-loading" ).show();
					setTimeout(function(){
						window.location = "/cybertron/secure/member/personal/info.html";
					},3000);
				});*/
				window.location = memberInfoUrl;
			}
		});
	}
	// 	scrollFx = function(e){
	// 		try{
	// 		var scroll = $(document).scrollTop()*0.25;
	// 		var bgPosX = $(".main-container .background").css("background-position","").css("background-position").split(" ")[0];
	// 		$(".main-container .background").css("background-position",bgPosX +" "+scroll+"px");
	// 		var under = ($(window).height()+$(document).scrollTop()-$("body").height())*0.25;
	// 		under = under>0?0:under;
	// 		$(".contract-content").css("top",under+"px");
	// 		}catch(ignored){}
	// 	}
	// 	$(document).off('scroll').off('resize').on( "scroll",scrollFx).on("resize",scrollFx);
</script>
	
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-9956859-3', 'auto');
  ga('send', 'pageview');

</script>
</body>

</html>
<%
	session.removeAttribute(thaisamut.nbs.struts.action.AuthAction.SES_LOGIN_ERROR_DETAIL);
%>
<c:if test="${not empty sessionScope.error_message}">
	<script>
		CSSDialog.warn('<h1>${sessionScope.error_message}</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
	</script>
</c:if>
<%
	session.removeAttribute("error_message");
%>
