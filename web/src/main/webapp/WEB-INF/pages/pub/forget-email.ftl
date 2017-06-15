<!doctype html>
<html lang="en">
<head>
<title>ลืมบัญชีผู้ใช้งาน และรหัสผ่าน</title>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS={
			validateByEmail: '${contextPath}/pub/page/forget/validateByEmail.html',
			confirmByOtp: '${contextPath}/pub/page/forget/confirmByOtp.html',
			home:'${contextPath}/'
		}
		var REQ_PARAM={
			forgetMethod : '${forgetMethod?html}',
			cardId : '${cardId?html}',
			telNo : '${telNo?html}',
			email : '${(email!'')?html}',
			captchaTimeStamp : '${captchaTimeStamp?html}'
		}
	</script>	<style type="text/css">
body{
	margin-bottom:22px !important;
} 

.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
}
	</style>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-4">
				<div class="section sec-title" id="forget-method">
				</div>
			</div>
			<div class="col-md-8">
				<div class="section sec-content">
				<form id="form-forget-email" action="#" method="post">
					<fieldset class="form-group">
						<label>กรุณาระบุอีเมลของท่านในช่องด้านล่าง<br/>เราจะส่งอีเมลเกี่ยวกับข้อมูลบัญชีของท่านในทันที</label>
					</fieldset>
					<fieldset class="form-group">
						<label for="email" required>อีเมล</label>
						<#if email?has_content>
					    <span class="form-control" id="email">${(email!'')?html}</span>
					    <#else>
					    <span class="form-control" id="email">ไม่พบอีเมลของท่านในระบบ</span>
						</#if>
					</fieldset>
					<fieldset class="form-group">
						<label for="secureCode" required>Security Code</label>
						<img id="img-captcha" src="${contextPath}/pub/page/generatecaptcha.html?captchaTimeStamp=${captchaTimeStamp?html}" >
						<img id="reloadcaptcha" src="${contextPath}/images/reload-captcha.gif" height="20" width="20" style="vertical-align:top" alt="captcha"/>
					    <input type="text" class="form-control" id="captchaCode" name="captchaCode" placeholder="Security Code" data-validate-required="กรอก Security Code ตามภาพ" data-validate-captcha="กรอก Security Code ไม่ถูกต้อง">
						<button id="testcaptcha" type="button" class="btn btn-default" style="display:none">ทดสอบ captcha</button>
					</fieldset>
					<fieldset class="form-group">
						<div class="col-md-6 col-sm-4">
						</div>
						<div class="col-md-6 col-sm-8">
							 <label>หากท่านไม่มีอีเมล กรุณาเลือก</label>
							 <button type="button" id="btn-otp-submit" class="btn btn-default oli-default-button">ที่นี่</button>
						</div>
					</fieldset>
					<br/>
					<fieldset class="form-group">
						<div class="row">
							<div class="col-md-3 col-md-offset-3 col-sm-6 col-xs-6 text-right">
								<a href="${contextPath}/pub/page/forget/index.html" class="btn btn-default oli-arrow-left" >ย้อนกลับ</a>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-6">
								<button type="submit" id="btn-email-submit" disabled class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
							</div>
						</div>
					</fieldset>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			if(REQ_PARAM.forgetMethod == 'user'){
				$('#forget-method')[0].innerHTML = "ลืมบัญชีผู้ใช้งาน";
			}else{
				$('#forget-method')[0].innerHTML = "ขอรหัสผ่านใหม่";
			}
			
			$("#form-forget-email").on('submit',function(e){
				e.preventDefault();
		
				var verify = CSSValidate(this);
			
				if(!verify)return;
				validateByEmail();
			});
			
			$("#reloadcaptcha").on('click',function(){
				$("#img-captcha").attr("src",$("#img-captcha").attr("src")+'?'+new Date().getTime());
			});
			
			$("#reloadcaptcha").hover(
				function(){
					$( this )[0].style.border = "1px solid #FFFFFF";
				}, function() {
   					$( this )[0].style.border = "";
  				}
			);
			
			$('#btn-otp-submit').on('click', function(){
				redirectToOtp();
			});
		});
		
		function validateByEmail(){
			var params = {};
			params['params.email'] = REQ_PARAM.email;
			params['params.cardId'] = REQ_PARAM.cardId;
			params['params.forgetMethod'] = REQ_PARAM.forgetMethod;
			params['params.captchaTimeStamp'] = REQ_PARAM.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
			
			$.ajax({
  method: "POST",
				data : params,
				url : ACTIONS.validateByEmail,
				success : function(json) {
					if(json.data.sendEmailSuccess){
						var msg = '';
						if(REQ_PARAM.forgetMethod == 'user'){
							msg = 'ระบบได้จัดส่งบัญชีผู้ใช้งาน ให้ท่านทางอีเมล<br/>'+json.data.sendEmail+'<br/>เรียบร้อยแล้ว';
						}else{
							msg = 'ระบบได้จัดส่ง ลิงค์สำหรับตั้ง<br/>รหัสผ่านใหม่ ให้ท่านทางอีเมล<br/>'+json.data.sendEmail+'<br/>เรียบร้อยแล้ว';
						}
						CSSDialog.alert(msg).on('ok', 
							function(){
								window.location = ACTIONS.home;
							}
						);
					}else{
						CSSDialog.alert((json.data.errorMessage)||'เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ');
					}
				},
				error : function(xhr, desc, exceptionobj) {
					if (xhr.responseText != "") {
						alert("ERROR :" + xhr.responseText);
					}
				}
			});
		}
		
		function redirectToOtp(){
			window.location = ACTIONS.confirmByOtp+"?forgetMethod="+REQ_PARAM.forgetMethod+"&cardId="+REQ_PARAM.cardId;
		}
		
		validateCaptcha = function(el){
			var params = {};
			params['params.captchaTimeStamp'] = REQ_PARAM.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
			var res = $.ajax({	
  method: "POST",
				data : params,
				async : false,
				url : "/cybertron/pub/page/isvalidcaptcha.html"
			}).responseText;
			return JSON.parse(res).data;
		}
		
		$("#testcaptcha").on('click',function(){
				var params = {};
				params['params.captchaTimeStamp'] = REQ_PARAM.captchaTimeStamp;
				params['params.captchaCode'] = $('#captchaCode').val();
				$.ajax({
  method: "POST",
					data : params,
					url : "/cybertron/pub/page/isvalidcaptcha.html",
					success : function(json) {
						alert(json.data);
					},
					error : function(xhr, desc, exceptionobj) {
						if (xhr.responseText != "") {
							alert("ERROR :" + xhr.responseText);
						}
					}
				});
			});
	</script>
</body>
</html>