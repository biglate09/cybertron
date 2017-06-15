<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var URL_EDIT_POL = "${contextPath}/secure/member/personal/edit.html";
		var ACTIONS={
				easyloan:'${contextPath}/secure/easyloan/easyloanindex.html',
				home:'${contextPath}/',
			}
	</script>
</head>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					ข้อมูล</br>กรมธรรม์
				</div>
			</div>
			<div class="col-md-9 md-right">
				<#if Request.policy??>
				<#assign user = Session['thaisamut.css.model.CssUser']>
				<#assign person = Session.sessionUser>
				<#assign policy = Request.policy>
				<div class="card"> 
					<div class="card-block card-header"> 
						<h3 class="card-title">${person.fullName!"-"?html}</h3>
						<h5 class="card-subtitle">เลขประจำตัวประชาชน : <span class="form-value">${person.idNo!"-"?html}<span></h5>
						<h5 class="card-subtitle">วันเดือนปีเกิด : <span class="form-value">${person.birthDate!"-"?html}<span></h5>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">กรมธรรม์</label></div>
							<div class="col-sm-3 no-padding">${policy.policyNo!"-"?html}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">แบบประกัน</label></div>
							<div class="col-sm-3 no-padding">${policy.prdName!"-"?html}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">สถานะ</label></div>
							<div class="col-sm-9 no-padding">${policy.groupDescription!"-"?html}</div>		
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">ช่องทางการชำระ</label></div>
							<div class="col-sm-3 no-padding">${policy.paymentChannelDesc!"-"?html}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">งวดชำระ</label></div>
							<div class="col-sm-3 no-padding">${policy.paymentModeDesc!"-"?html}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">เงินเอาประกันภัย</label></div>
							<div class="col-sm-3 no-padding replace-currency">${policy.sumAssured?string["#,##0.00"]} บาท</div>
							<div class="col-sm-3 no-padding"><label class="form-label">เบี้ยประกันต่องวด</label></div>
							<div class="col-sm-3 no-padding replace-currency">${policy.allPermium?string["#,##0.00"]} บาท</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">วันเริ่มสัญญา</label></div>
							<div class="col-sm-3 no-padding">${policy.commencementDate!"-"?html}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">วันครบสัญญา</label></div>
							<div class="col-sm-3 no-padding">${policy.maturityDate!"-"?html}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">ครบกำหนดชำระเบี้ยวันที่</label></div>
							<div class="col-sm-3 no-padding">${policy.nextPaidDate!"-"?html}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">สิ้นสุดการชำระเบี้ยวันที่</label></div>
							<div class="col-sm-3 no-padding">${policy.fullyPaidDate!"-"?html}</div>
						</fieldset>
					</div>
					<div class="card-block card-footer row">
  					<div class="col-sm-2 block col-xs-6">
							<div>หนังสือแจ้งเตือน</div>
  						<#if allowDownload.allowDownloadNoti == "Y" && policy.canDownload?contains("effective") >
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-download="notify,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(policy.notifyData!"")?html}" class=" mini-btn glyphicon glyphicon-cloud-download" title="ดาวน์โหลด" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'notificationletter'});"></a></div>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-email="notify,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(user.email!"")?html},${(policy.notifyData!"")?html}" class="mini-btn glyphicon glyphicon-envelope"  title="ส่งอีเมล" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'emailnoti'});"></a></div>
  						<#else>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class=" mini-btn glyphicon glyphicon-cloud-download disabled" title="ดาวน์โหลด"></a></div>
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-envelope disabled"  title="ส่งอีเมล"></a></div>
	  					</#if>
					</div>
  					<div class="col-sm-2 block col-xs-6">
						<div>หนังสือรับรอง</div>
						<#if allowDownload.allowDownloadCert == "Y" >
							<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-download="cert,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(policy.certData!"")?html}" class="mini-btn glyphicon glyphicon-cloud-download" title="ดาวน์โหลด" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'certificateletter'});"></a></div>
							<div class="col-xs-6 no-padding"><a href="javascript:void(0)" data-email="cert,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(user.email!"")?html},${(policy.certData!"")?html}" class="mini-btn glyphicon glyphicon-envelope" title="ส่งอีเมล" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel: 'emailcert'});"></a></div>
						<#else>
			        		<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class=" mini-btn glyphicon glyphicon-cloud-download disabled" title="ดาวน์โหลด"></a></div>
				        	<div class="col-xs-6 no-padding"><a href="javascript:void(0)" class="mini-btn glyphicon glyphicon-envelope disabled"  title="ส่งอีเมล"></a></div>
			        	</#if>						
					</div>
  					<div class="col-sm-3 block col-xs-6 md-left-border ">
	  						<div>ประวัติการชำระ/ชำระเบี้ย</div>
				        	<div class="col-xs-6 no-padding"><a href="${contextPath}/secure/payment/paymentinfo.html?policyNo=${(policy.policyNo!"")?html}&ref=1&rel=${(ref!"")?html}" class="mini-btn glyphicon glyphicon-list-alt" title="ประวัติการชำระเบี้ย" onclick="ga('send', 'event', { eventCategory: 'viewhistory', eventAction: 'click', eventLabel:'history'});"></a></div>
				        <#if policy.canPayment?contains("effective") >
			        		<div class="col-xs-6 no-padding"><a href="${contextPath}/secure/payment/paymentmain.html?policyNo=${policy.policyNo?html}&ref=2" class="mini-btn glyphicon glyphicon-credit-card" title="ชำระเบี้ย" onclick="ga('send', 'event', { eventCategory:'payment', eventAction: 'click', eventLabel: 'changepayment'});"></a></div>
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
                                <a href="${contextPath}/secure/easyloan/validateOtpStatusN.html?params.policyNo=${policy.policyNo?html}&params.policyType=${policyType?html}&params.refPage=Detail" class="mini-btn glyphicon glyphicon-phone" title="ยืนยัน OTP สมัคร Easy Loan"></a>
                        <#elseif cssLoanStatus == "C">
                                <a href="javascript:void(0)" onclick="regisEasyLoanAgain('${policy.policyNo?html}','${policyType?html}');" class="mini-btn glyphicon glyphicon-usd" title="สมัคร Easy Loan อีกครั้ง"></a>
                        <#else>
                                <a href="${contextPath}/secure/easyloan/easyloanindex.html?params.policyNo=${policy.policyNo?html}&params.policyType=${policyType?html}&params.refPage=Detail" class="mini-btn glyphicon glyphicon-usd" title="สมัคร Easy Loan"></a>
                        </#if>
                    </div>
  					<div class="col-sm-3 block col-xs-12">
							<div>กลับ</div>
							<a href="${contextPath}/${back?html}" class=" mini-btn glyphicon glyphicon-arrow-left" "title="กลับ"></a>
						</div>

					</div>
				</div>
				<#else>
				<script>$(document).ready(function(){CSSDialog.warn('ไม่พบข้อมูลกรมธรรม์').on('ok',function(){window.location="${contextPath}/secure/member/policy/policyinfo.html"})});</script>
				</#if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-9 col-md-offset-3">
				<h1>กรณีรายละเอียดข้อมูลกรมธรรม์ไม่ถูกต้อง<br>กรุณาติดต่อศูนย์บริการลูกค้าสัมพันธ์ 0 2207 8888</h1>
			</div>
		</div>
	</div>
	<script src="${contextPath}/scripts/policydetail.js"></script>
	<script src="${contextPath}/scripts/download-document.js"></script>
</body>
</html>