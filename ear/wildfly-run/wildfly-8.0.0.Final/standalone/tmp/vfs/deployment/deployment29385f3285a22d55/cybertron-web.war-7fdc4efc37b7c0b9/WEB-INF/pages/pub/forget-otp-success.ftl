<!doctype html>
<html lang="en">
<head>
<title>ลืมบัญชีผู้ใช้งาน และรหัสผ่าน</title>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS={
			confirmByOtp: '${contextPath}/pub/page/forget/confirmByOtp.html',
			resetPasswordByOtp : '${contextPath}/pub/page/forget/resetPasswordByOtp.html',
			home: '${contextPath}/'
		}
		var REQ_PARAM={
			forgetMethod : '${forgetMethod?html}',
			username : '${username?html}',
			telNo : '${telNo?html}',
			captchaTimeStamp : '${captchaTimeStamp?html}'
		}
	</script>
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
					<form id="form-otp-success" action="#" method="post">
						<fieldset class="form-group">
							<div class="col-md-12"><label for="username">บัญชีผุ้ใช้งานของท่านคือ</label></div>
						    <div class="col-md-12">
						    <input type="text" class="form-control" name="username" id="username" value="${username?html}" readonly  >
						    </div>
						</fieldset>
						<fieldset class="form-group" id="group-new-password">
							<div class="col-md-12"><label for="password" required>รหัสผ่าน</label></div>
						    <div class="col-md-12">
						    <input type="password" class="form-control diff-password password-hint" id="new-password" name="new-password" placeholder="รหัสผ่านใหม่" data-validate-required="กรอกรหัสผ่านใหม่"  data-validate-diff-password="รหัสผ่านคาดเดาง่ายเกินไป" data-validate-min-length-password="อย่างน้อย 8 หลัก"/>
						    </div>
						</fieldset>
						<fieldset class="form-group" id="group-confirm-new-password">
							<div class="col-md-12"><label for="confirm-new-passwod" required>ยืนยันรหัสผ่านใหม่</label></div>
						    <div class="col-md-12">
						    <input type="password" class="form-control" id="confirm-new-passwod" name="confirm-new-passwod" placeholder="ยืนยันรหัสผ่านใหม่" data-validate-required="กรอกยืนยันรหัสผ่านใหม่" data-validate-match="รหัสผ่านไม่ตรงกัน"/>
						    </div>
						</fieldset>
						<fieldset class="form-group" id="group-captcha">
							<div class="col-md-12"><label for="capcha" required>Security Code</label></div>
						    <div class="col-md-12">
							<img id="img-captcha" src="${contextPath}/pub/page/generatecaptcha.html?captchaTimeStamp=${captchaTimeStamp?html}" >
							<img id="reloadcaptcha" src="${contextPath}/images/reload-captcha.gif" height="20" width="20" style="vertical-align:top" alt="captcha"/>
					    	<input type="text" class="form-control" id="captchaCode" name="captchaCode" placeholder="Security Code" data-validate-required="กรอก Security Code ตามภาพ" data-validate-captcha="กรอก Security Code ไม่ถูกต้อง">
							<button id="testcaptcha" type="button" class="btn btn-default" style="display:none">ทดสอบ captcha</button>
							</div>
						</fieldset>
						<br/>
						<fieldset class="form-group">
							<div class="row" id="row_btn_1">
								<div class="col-md-3 col-md-offset-3 col-sm-6 col-xs-6 text-right">
									<button type="button" id="btn_back" class="btn btn-default oli-arrow-left" >ย้อนกลับ</button>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-6">
									<button type="submit" id="btn_submit" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
								</div>
							</div>
							
							<div class="row" id="row_btn_2">
								<div class="col-md-12 col-sm-12 col-xs-12 center">
									<button type="button" id="process_submit" class="btn btn-default oli-default-button">ดำเนินการ</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			if(REQ_PARAM.forgetMethod == 'user'){
				$('#forget-method')[0].innerHTML = "ลืมบัญชีผู้ใช้งาน";
				
				$('#group-new-password').remove();
				$('#group-confirm-new-password').remove();
				$('#group-captcha').remove();
				$('#row_btn_1').remove();
				
				$("#process_submit").on('click',function(){
					window.location = ACTIONS.home;
				});
			}else{
				$('#forget-method')[0].innerHTML = "ขอรหัสผ่านใหม่";
				
				$('#row_btn_2').remove();
				
				$("#form-otp-success").on('submit',function(e){
					e.preventDefault();
			
					var verify = CSSValidate(this);
					
					if(!verify)return;
					
					resetPasswordByOtp();
				});
			}
			
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
			
			$("#btn_back").on('click',function(){
				if(REQ_PARAM.forgetMethod == 'user'){
					window.location = ACTIONS.home;
				}else{
					backToConfirmOtp();
				}
			});
		});
		
		function validate(e) {
		  if( $("#password").val()==''){
			  	CSSDialog.warn("กรุณากรอกรหัสผ่าน");
				e.preventDefault();
			  	return false;
		  }
		}
		
		function backToConfirmOtp(){
			window.location = ACTIONS.confirmByOtp+'?forgetMethod='+REQ_PARAM.forgetMethod+'&telNo='+REQ_PARAM.telNo;
		}
		
		function resetPasswordByOtp(){
			var params = {};
			params['params.username'] = REQ_PARAM.username;
			params['params.newpassword'] = $('#new-password').val();
			params['params.confirmnewpassword'] = $('#confirm-new-passwod').val();
			params['params.captchaTimeStamp'] = REQ_PARAM.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
		
			$.ajax({
  method: "POST",
				data : params,
				url : ACTIONS.resetPasswordByOtp,
				success : function(json) {
					CSSDialog.alert(json.data.resultsMsg).on('ok', 
						function(){
							if(json.data.success){
								window.location="${contextPath}/";
							}else{
								$('#captchaCode').val('');
								CSSDialog.alert((json.data.resultsMsg)||'เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ');
							}
						}
					);
				},
				error : function(xhr, desc, exceptionobj) {
					if (xhr.responseText != "") {
						alert("ERROR :" + xhr.responseText);
					}
				}
			});
		}
		
		validateMatch=function(){
			return $("#new-password").val() == $("#confirm-new-passwod").val();
		}
		
		validateMatchOldPassword=function(el){
			var params = {};
			params['params.username'] = REQ_PARAM.username;
			params['params.newpassword'] = $('#new-password').val();
			var res = $.ajax({	
				type : 'get',
				data : params,
				async : false,
				url :  "/cybertron/pub/page/isduplicateoldpassword.html"
			}).responseText;
			return !JSON.parse(res).data;
		}
		
		validateCaptcha = function(el){
			var params = {};
			params['params.captchaTimeStamp'] = REQ_PARAM.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
			var res = $.ajax({	
				type : 'POST',
				method: 'POST',
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