<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" href="${contextPath}/chartist/chartist.css" />
	<link rel="stylesheet" href="${contextPath}/css/common-chart.css" />
	<link rel="stylesheet" href="${contextPath}/css/tooltip-theme-twipsy.css" />
	
	<script>
		var URL_EDIT_POL = "${contextPath}/secure/member/personal/edit.html";
		var ACTIONS={
				easyloan:'${contextPath}/secure/easyloan/easyloanindex.html',
				home:'${contextPath}/',
			}
	</script>
</head>
<body>
	<!-- Start Container -->
	<div class="container">
		<!-- Start Row 1 -->
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					รายละเอียด</br>ความคุ้มครอง
				</div>
			</div>
			<div class="col-md-9">
				<div id="chart1" class="section sec-content-border sec-content-graph sec-w-content">
					
				</div>
			</div>
		</div>
		<!-- Start Row 2 -->
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					รายการ</br>กรมธรรม์
				</div>
			</div>
			<div class="col-md-9">
				<!-- card -->
				
				<#if Session.sessionPolicy??>
				<#assign policies = Session.sessionPolicy>
				<#assign person = Session.sessionUser>
				<#assign user = Session['thaisamut.css.model.CssUser']>
				<#assign sortByTime = 'thaisamut.util.CssSortPolicy'?new()>
				<#list sortByTime(policies?values) as policy>
				<#if policy??>
				<div class="card">
				   	<div class="card-block card-header ${policy.group?html}">
				    <h4 class="card-title">${policy.prdName?html}</h4>
				    <h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${policy.policyNo?html}</span></h6>
				  </div>
				 <div class="card-block card-body row">
  					<div class="col-sm-4">
  						<div class="input-group input-group-margin">
						  <span class="input-group-addon">เงินเอาประกันภัย</span>
						  <span class="form-control  replace-currency">${policy.sumAssured?string["#,##0.00"]}</span>
						</div>
  					</div>
  					<div class="col-sm-4">
  						<div class="input-group input-group-margin">
						  <span class="input-group-addon">เบี้ยประกัน</span>
						  <span class="form-control  replace-currency">${policy.allPermium?string["#,##0.00"]}</span>
						</div>
  					</div>
  					<div class="col-sm-4">
  						<div class="input-group input-group-margin">
						  <span class="input-group-addon">ชำระครั้งต่อไป</span>
						  <span class="form-control">${policy.nextPaidDate!"-"?html}</span>
						</div>
  					</div>
  				</div>
				 <div class="card-block card-footer row">
  					<div class="col-sm-2 block col-xs-6">
  						<div>หนังสือแจ้งเตือน</div>
  						<#if allowDownload.allowDownloadNoti == "Y" && policy.canDownload?contains("effective") >
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-download="notify,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(policy.notifyData!"")?html}" class=" mini-btn glyphicon glyphicon-cloud-download" title="ดาวน์โหลด" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'srnotificationletter'});"></a></div>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-email="notify,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(user.email!"")?html},${(policy.notifyData!"")?html}" class="mini-btn glyphicon glyphicon-envelope"  title="ส่งอีเมล" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'sremailnoti'});"></a></div>
  						<#else>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class=" mini-btn glyphicon glyphicon-cloud-download disabled" title="ดาวน์โหลด"></a></div>
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-envelope disabled"  title="ส่งอีเมล"></a></div>
	  					</#if>
	  				</div>
  					<div class="col-sm-2 block  col-xs-6">
  						<div>หนังสือรับรอง</div>
  						<#if allowDownload.allowDownloadCert == "Y" >
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-download="cert,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(policy.certData!"")?html}" class=" mini-btn glyphicon glyphicon-cloud-download"  title="ดาวน์โหลด" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'srcertificateletter'});"></a></div>
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-email="cert,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(user.email!"")?html},${(policy.certData!"")?html}" class="mini-btn	glyphicon glyphicon-envelope"  title="ส่งอีเมล" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'sremailcert'});"></a></div>
			        	<#else>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class=" mini-btn glyphicon glyphicon-cloud-download disabled" title="ดาวน์โหลด"></a></div>
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-envelope disabled"  title="ส่งอีเมล"></a></div>
			        	</#if>
					</div>
  					<div class="col-sm-3 block col-xs-6 md-left-border ">
  						<div>ประวัติการชำระ/ชำระเบี้ย</div>
			        	<div class="col-xs-6 no-padding"><a href="${contextPath}/secure/payment/paymentinfo.html?policyNo=${policy.policyNo?html}&ref=2" class="mini-btn	glyphicon glyphicon-list-alt" title="ประวัติการชำระเบี้ย" onclick="ga('send', 'event', { eventCategory: 'viewhistory', eventAction: 'click', eventLabel: 'srhistory'});"></a></div>
			        	<#if policy.canPayment?contains("effective") >
			        		<div class="col-xs-6 no-padding"><a href="${contextPath}/secure/payment/paymentmain.html?policyNo=${policy.policyNo?html}&ref=2" class="mini-btn	glyphicon glyphicon-credit-card" title="ชำระเบี้ย" onclick="ga('send', 'event', { eventCategory:'payment', eventAction: 'click', eventLabel: 'srchangepayment'});"></a></div>
			        	<#else>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-credit-card disabled" title="ไม่สามารถชำระเบี้ยประกันภัยขายผลบังคับ"></a></div>
			        	</#if>
			        	
					</div>
					<div class="col-sm-2 block col-xs-6 md-left-border ">
                        <div>Easy Loan</div>
                        <#assign policyType = policy.policyType>
                        <#assign policyStatus = policy.policyStatus>
                        <#assign cssLoanStatus = policy.cssLoanStatus>
                        <#assign dwnLoanStatus = policy.dwnLoanStatus>
                        <#assign personAge = person.age>
                        <#if personAge lt 25 || dwnLoanStatus == "N" || policyType == "P" || ((policyType == "O" || policyType == "I" || policyType == "G") && policyStatus == "M") >
                                <a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-usd disabled" title="ไม่สามารถสมัคร Easy Loan"></a>
                        <#elseif cssLoanStatus == "N">
                                <a href="${contextPath}/secure/easyloan/validateOtpStatusN.html?params.policyNo=${policy.policyNo?html}&params.policyType=${policyType?html}&params.refPage=Info" class="mini-btn glyphicon glyphicon-phone" title="ยืนยัน OTP สมัคร Easy Loan"></a>
                        <#elseif cssLoanStatus == "C">
                                <a href="javascript:void(0)" onclick="regisEasyLoanAgain('${policy.policyNo?html}','${policyType?html}');" class="mini-btn glyphicon glyphicon-usd" title="สมัคร Easy Loan อีกครั้ง"></a>
                        <#else>
                                <a href="${contextPath}/secure/easyloan/easyloanindex.html?params.policyNo=${policy.policyNo?html}&params.policyType=${policyType?html}&params.refPage=Info" class="mini-btn glyphicon glyphicon-usd" title="สมัคร Easy Loan"></a>
                        </#if>
                    </div>
                   <div class="col-sm-3 block col-xs-12">
  						<div>รายละเอียดกรมธรรม์</div>
  						<a href="${contextPath}/secure/member/policy/policydetail.html?policyNo=${policy.policyNo?html}&ref=2" class="mini-btn	glyphicon glyphicon-search" title="รายละเอียดกรมธรรม์" onclick="ga('send', 'event', { eventCategory:'personalinfo', eventAction: 'click', eventLabel:'srviewdetail'});"></a>
  					</div>
  				</div>
				</div>
				</#if>
				</#list>
				</#if>
				<div class="col-sm-6 col-sm-offset-3 addon-section sec-content">
					สนใจทำประกันชีวิตเพิ่มเติม</br><a  target="_blank" href="https://www.ocean.co.th/%E0%B8%84%E0%B9%89%E0%B8%99%E0%B8%AB%E0%B8%B2%E0%B8%82%E0%B9%89%E0%B8%AD%E0%B8%A1%E0%B8%B9%E0%B8%A5%E0%B8%9B%E0%B8%A3%E0%B8%B0%E0%B8%81%E0%B8%B1%E0%B8%99%E0%B8%97%E0%B8%B5%E0%B9%88%E0%B8%95%E0%B8%A3%E0%B8%87%E0%B8%81%E0%B8%B1%E0%B8%9A%E0%B8%84%E0%B8%A7%E0%B8%B2%E0%B8%A1%E0%B8%95%E0%B9%89%E0%B8%AD%E0%B8%87%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B8%82%E0%B8%AD%E0%B8%87%E0%B8%84%E0%B8%B8%E0%B8%93" class="btn default-button oli-default-button text-white">ดูรายละเอียด</a>
				</div>
				
			</div>
		</div>
		<!-- End Row 2 -->
	</div>
	<!-- End Container -->
	
	<#if Session.sessionGraph??>
	<#assign g = Session.sessionGraph>
	<script>RIDER_GRAPH = [	${g.all?string["0"]!"0"?html},
						   	${g.medAll?string["0"]!"0"?html},
							${g.medSep?string["0"]!"0"?html},
							${g.disease?string["0"]!"0"?html},
							${g.epa?string["0"]!"0"?html},
							${g.pa?string["0"]!"0"?html}
							],
							
			 OUTHER = 	[${g.other?string["0"]!"0"?html}
			 			]; </script>
	</#if>
	<script src="${contextPath}/chartist/chartist.js"></script>
	<script src="${contextPath}/scripts/Chart.bundle.min.js"></script>
	<script src="${contextPath}/scripts/policyinfo.js"></script>
	<script src="${contextPath}/scripts/download-document.js"></script>
</body>
</html>