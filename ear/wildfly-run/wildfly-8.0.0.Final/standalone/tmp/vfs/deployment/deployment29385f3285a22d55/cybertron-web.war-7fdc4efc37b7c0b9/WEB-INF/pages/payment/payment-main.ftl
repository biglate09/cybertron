<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" href="${contextPath}/css/payment-main.css" />
</head>
<body>
	<div class="container">
		<div class="row ">
			<#if Request.policy??>
			<#assign person = Session.sessionUser>
			<#assign policy = Request.policy>
			<div class="col-md-3">
				<div class="section sec-title">
					ช่องทาง</br>การชำระเบี้ย
				</div>
			</div>
			<div class="col-md-9 md-right">
				<div class="card">
				<div class=" pay-option-block" onclick="paymentValidate('${policy.policyNo!''}'); ga('send', 'event', { eventCategory:'paymentmain', eventAction: 'click',eventLabel: 'creditcard'});">
					<div class="icon" align="center">
							<img id="payment-type-1" src="${contextPath}/images/payment/logo-credit-card.png"/>
					</div>
					<div class="header">
						<span style="margin-top: 0px;" class="font-head">Credit Card</span><br/>
						<span style="margin-top: 0px;" class="font-sub-head">ชำระเบี้ยประกันภัยผ่านบัตรเครดิต สะดวก รวดเร็ว ปลอดภัย</span>
					</div>
				</div>
				<div class="pay-option-block" onclick="downloadDirectCredit(); ga('send', 'event', { eventCategory:'paymentmain', eventAction: 'click',eventLabel: 'directdebit'});">
					<div class="icon" align="center">
						<img id="payment-type-2" src="${contextPath}/images/payment/logo-direct-debit.png"/>
					</div>
					<div class="header">
						<span style="margin-top: 0px;" class="font-head">Direct Debit</span><br/>
						<span style="margin-top: 0px;" class="font-sub-head">ชำระเบี้ยประกันภัยผ่านช่องทาง Direct Debit ง่ายๆ ด้วยการดาวน์โหลดแบบฟอร์มสมัครชำระเบี้ย ประกันชีวิตจากการหักบัญชีธนาคาร (Direct Debit) ออนไลน์</span>
					</div>
				</div>
				<#if allowDownload.allowDownloadNoti == "Y" && policy.canDownload?contains("effective")>
				<div class="pay-option-block" onclick="javascript:void(0); ga('send', 'event', { eventCategory:'paymentmain', eventAction: 'click',eventLabel: 'counterservice'});" data-download="notify,${(policy.prdName!"")?html},${(policy.policyNo!"")?html},${(policy.notifyData!"")?html}">
				<#else>
				<div class="pay-option-block disabled" onclick="javascript:void(0)" >
				</#if>
					<div class="icon" align="center">
						<img id="payment-type-3" src="${contextPath}/images/payment/logo-counter-service.png"/>
					</div>
					<div class="header">
						<span style="margin-top: 0px;" class="font-head">Counter Service</span><br/>
						<span style="margin-top: 0px;" class="font-sub-head">พิมพ์ใบแจ้งเตือนเพื่อนำไปชำระเบี้ยผ่านช่องทาง 7-Eleven, Counter Service, Counter Bank</span>
					</div>
				</div>
				<div class="pay-option-block" onclick="authority('${policy.policyNo!''}'); ga('send', 'event', { eventCategory:'paymentmain', eventAction: 'click',eventLabel: 'paymentcard'});">
					<div class="icon" align="center">
						<img id="payment-type-4" src="${contextPath}/images/payment/logo-payment-card.png">
					</div>
					<div class="header">
						<span style="margin-top: 0px;" class="font-head">Payment Card</span><br/>
						<span style="margin-top: 0px;" class="font-sub-head">เมื่อสมัครบัตรชำระเบี้ยประกันภัย Payment Card ท่านจะสามารถนำไปชำระเบี้ยผ่านช่องทางที่หลากหลายเช่น</span>
						<ul>
							<li>Counter Bank 
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_1.png"/>
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_2.png"/>
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_3.png"/> </li>
							<li>Counter Service 
							<img class="img-guild" src="${contextPath}/images/logo_list_payment_card_2_1.png"/>
							<img class="img-guild" src="${contextPath}/images/logo_list_payment_card_2_2.png"/></li>
						</ul>
					</div>
				</div>
				</div>
			</div>
			</#if>
		</div>
	</div>
	<script>
		function authority(policy){
			var data = {policyNo: policy};
			$.ajax({
  method: "POST",
				url: '${contextPath}/secure/payment/paymentCardAuthority.html',
				data: data,
				success: function(res){
					var data = res.data;
					if(data.success){
						window.location= '${contextPath}/'+data.goto+"?policyNo="+data.policyNo;
					}else if(data.message){
					CSSDialog.warn(data.message).on('ok',function(e){});
					}
				},
				fail: function(error){
					CSSDialog.warn(error).on('ok',function(e){});
				}
			});
		
		}
		
		function paymentValidate(policy){
			var data = {policyNo: policy};
			$.ajax({
  method: "POST",
				url: '${contextPath}/secure/payment/paymentValidate.html',
				data: data,
				success: function(res){
					var data = res.data;
					if(data.success){
						window.location= '${contextPath}/'+data.goto+"?policyNo="+data.policyNo;
					}else if(data.message){
					CSSDialog.warn(data.message).on('ok',function(e){});
					}
				},
				fail: function(error){
					CSSDialog.warn(error).on('ok',function(e){});
				}
			});
		
		}
		function downloadDirectCredit(){
			window.location='https://www.ocean.co.th/file/1859/download?token=2NfTGlXA';
		}
	</script>
	<script src="${contextPath}/scripts/download-document.js"></script>
</body>
</html>