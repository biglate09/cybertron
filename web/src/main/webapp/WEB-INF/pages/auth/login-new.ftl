<!DOCTYPE html PUBLIC 
  "-//W3C//DTD XHTML 1.1 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer Self Service</title>
    <link rel="shortcut icon" href="${contextPath}/images/favicon.png">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	
	<link rel="stylesheet" href="${contextPath}/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${contextPath}/bootstrap/css/bootstrap-theme.css">

    <link rel="stylesheet" href="${contextPath}/css/material.min.css" charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/css/index-common.css" charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/css/index.css" charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/css/login.css" charset="utf-8">
    <link rel="stylesheet" href="${contextPath}/css/slider.css" charset="utf-8">
    <style>
        .demo-layout-waterfall .mdl-layout__header-row .mdl-navigation__link:last-of-type {
            padding-right: 0;
        }
    </style>
	<script src="${contextPath}/scripts/jquery-1.12.4.min.js"></script>
	<script src="${contextPath}/bootstrap/js/bootstrap.js"></script>
</head>
<body>
	
<body>
    <div class="demo-layout-waterfall mdl-layout mdl-js-layout">
        <header class="oli__header mdl-layout__header mdl-layout__header--waterfall">
            <!-- Bottom row, not visible on scroll -->
            <div class="mdl-layout__header-row">
                <span class="mdl-layout-title">
        <a href="#">
            <img class="mdl-title__image" src="${contextPath}/images/logo-64x64.png" alt="ไทยสมุทร" /><span>ไทยสมุทร</span>
        </a>
      </span>
                <div class="mdl-layout-spacer"></div>
                <!-- Navigation -->
                <nav class="mdl-navigation">
                    <button id="btn-login" data-scroll-to="#section--signin" class="oli-signup-button mdl-button mdl-js-button mdl-button--primary">เข้าสู่ระบบ</button>
                    <a class="oli-signup-button mdl-button mdl-js-button mdl-button--raised mdl-button--colored" href="">สมัครสมาชิก</a>
                </nav>
            </div>
        </header>
        
        <main class="main__content mdl-layout__content">
            <section class="hero hero--home">
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
                				<img src="${contextPath}/images/slider/slide-1.jpg">
                				</div>
                				<div class="item">
                				<img src="${contextPath}/images/slider/slide-2.jpg">
                				</div>
                				<div class="item">
                				<img src="${contextPath}/images/slider/slide-1.jpg">
                				</div>
                				<div class="item">
                					<img src="${contextPath}/images/slider/slide-2.jpg">
                				</div>
                			</div>
                		</div>
                	</div>
                <button class="hero__fab mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--fab mdl-shadow--4dp" data-scroll-to="#section--signin" data-g-event="FAB" data-g-action="click" data-g-label="Down to content">
                    <i class="material-icons">&#xE313;</i>
                </button>
            </section>
            <section id ="section--signin" class="section section--rev content__login-form mdl-grid">
                <div id="login-form-container" class="grid mdl-cell mdl-cell--6-col mdl-cell--12-col-phone">
                    <!--form-->
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" name="j_username"  type="text" tabindex="1" id="sample1">
                            <label class="mdl-textfield__label" for="sample1">รหัสผู้ใช้งาน</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <input class="mdl-textfield__input" name="j_password" type="password" tabindex="2" id="sample1">
                            <label class="mdl-textfield__label" for="sample1">รหัสผ่าน</label>
                        </div>
                        <div class="mdl-textfield mdl-js-textfield">
                            <button tabindex="3"  id="btn_submit" class="mdl-button oli-signup-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent color-white">
                                เข้าระบบ
                            </button>
                        </div>
                    <!--form-->
                </div>
            </section
        </main>
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
				<h3>ขอหนังสือรับรองการชำระเบี้ยประกันภัย</h3>
			</div>
			<div class="prev-grid ico noti">
				<h3>ขอหนังสือแจ้งเตือนการชำระเบี้ยประกันภัย</h3>
			</div>
			<div class="prev-grid ico gift">
				<h3>สมัครสมาชิก Ocean Club และ ค้นหาสิทธิพิเศษ</h3>
			</div>
			<div class="prev-grid ico paper">
				<h3>ดาวน์โหลดเอกสารและ<br/>แบบฟอร์มต่างๆ</h3>
			</div>
		</div>
	</div>
	<div class="container contract-content">
			<div class="contract-grid tel">
			</div>
			<div class="contract-grid mail">
			</div>
			<div class="contract-grid fax">
			</div>
			<div class="contract-grid ocean-club">
				<a target="_blank" href="https://www.oceanlifeonline.com/"> </a>
			</div>
			<div class="contract-grid subscript">
			</div>
			<div class="contract-grid facebook">
				<a target="_blank" href="https://www.facebook.com/ThaisamutFanpage"></a>
			</div>
	</div>
	<footer class="footer-darker">
		<span class="license" title="<s:property value='remoteActionServiceUrl' />"><s:property value='pomArtifactId'/> <s:property value='pomVersion'/> <s:property value='svnRevision'/><s:property value='jenkinsBuildNumber'/></span>
         <span class="version"> <%=thaisamut.nbs.core.POM.Name + " " + thaisamut.nbs.core.POM.Version + " #" + thaisamut.nbs.core.SVNInfo.REVISION%></span>
	</footer>
	<div class="footer-content">
		<h2>สงวนลิขสิทธิ์ พ.ศ. 2558 , บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) สำนักงานใหญ่ 170/74-83 อาคารโอเชี่ยนทาวเวอร์1 ถนนรัชดาภิเษก เขตคลองเตย กทม. 10110 โทร 0 2261 2300</h2>
		<h2>Copyright 2015 Ocean Life Insurance Public Company Limited Head office 170/74-83 Ocean Tower1, Ratchadaphisek Road, Khlong Toei, Bangkok, 10110, Thailand, Tel 0 2261 2300 </h2>
	</div>
    </div>
	<script src="${contextPath}/scripts/css-dialog.js"></script>
	<script src="${contextPath}/scripts/css-validate.js"></script>
    <script src="${contextPath}/scripts/material.min.js" charset="utf-8"></script>
    <script src="${contextPath}/scripts/index.js" charset="utf-8"></script>
<script>
	function validate(e) {
	  if($("#userName").val()=='' || $("#password").val()==''){
	  	CSSDialog.warn("กรอกบัญชีผู้ใช้งาน และรหัสผ่าน");
		e.preventDefault();
	  	return false;
	  }
	}
	setTimeout(function(){
		$("#btn-login").triggerHandler('click');
	},2700);
	$(document).ready(function(){
		$('#loginForm').on('submit',validate);
		$('#btn_submit').on('click',validate);
	});
</script>
</body>
</html>