<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS={
			checkuser: '${contextPath}/secure/checkuser.html',
			resetpassword: '${contextPath}/secure/resetpassword.html'
		}
		var SESSION={
			isTokenExpire: '${isTokenExpire?html}',
			captchaTimeStamp : '${captchaTimeStamp?html}'
		}
	</script>
</head>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					เปลี่ยนรหัสผ่าน
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
				<form id="form-change-password" action="#">
					<fieldset class="form-group">
							<div class="col-md-12">
						<label for="userId" required>บัญชีผู้ใช้งาน</label>
							</div>
							<div class="col-md-12">
					    <input type="text" class="form-control" id="userId" placeholder="บัญชีผู้ใช้งาน" readonly value="${(userName!"")?html}" />
						</div>
					</fieldset>
					<fieldset class="form-group">
							<div class="col-md-12">
						<label for="old-password" required>รหัสผ่านเดิม</label>
							</div>
							<div class="col-md-12">
					    <input type="password" class="form-control" id="old-password" name="old-password" placeholder="รหัสผ่านเดิม" data-validate-required="กรอกรหัสผ่านเดิม" />
						</div>
					</fieldset>
					<fieldset class="form-group">
							<div class="col-md-12">
						<label for="new-password" required>รหัสผ่านใหม่</label>
							</div>
							<div class="col-md-12">
					    <input type="password" class="form-control diff-password password-hint" id="new-password" name="new-password" placeholder="รหัสผ่านใหม่" data-validate-required="กรอกรหัสผ่านใหม่" data-validate-min-length-password="อย่างน้อย 8 หลัก" data-validate-diff-password="รหัสผ่านคาดเดาง่ายเกินไป" data-validate-match-old-password="รหัสผ่านเดิมและรหัสผ่านใหม่ต้องไม่ตรงกัน"/>

						</div>
					</fieldset>
					<fieldset class="form-group">
							<div class="col-md-12">
						<label for="confirm-new-password" required>ยืนยันรหัสผ่านใหม่</label>
							</div>
							<div class="col-md-12">
					    <input type="password" class="form-control" id="confirm-new-password" name="confirm-new-password" placeholder="ยืนยันรหัสผ่านใหม่" data-validate-required="กรอกยืนยันรหัสผ่านใหม่" data-validate-match="รหัสผ่านไม่ตรงกัน"/>
						</div>
					</fieldset>
					<fieldset class="form-group">
							<div class="col-md-12">
						<label for="capcha" required>Security Code</label>
							</div>
							<div class="col-md-12">
						<img id="img-captcha" src="${contextPath}/pub/page/generatecaptcha.html?captchaTimeStamp=${captchaTimeStamp?html}" >
						<img id="reloadcaptcha" src="${contextPath}/images/reload-captcha.gif" height="20" width="20" style="vertical-align:top" alt="captcha"/>
					    <input type="text" class="form-control" id="captchaCode" name="captchaCode" placeholder="Security Code" data-validate-required="กรอก Security Code ตามภาพ" data-validate-captcha="กรอก Security Code ไม่ถูกต้อง">
						</div>
					</fieldset>
					<fieldset class="form-group">
						<div class="col-sm-6 xs-bottom-gap sm-right">
							<button type="submit" class="btn btn-default oli-default-button oli-arrow-right xs-full">เปลี่ยนรหัสผ่าน</button>
						</div>
						<div class="col-md-3 col-md-offset-3 col-sm-6 text-right xs-bottom-gap">
							<a href="${contextPath}/" class="btn btn-default oli-arrow-left xs-full">ย้อนกลับ</a>
						</div>
					</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		var action = ACTIONS.resetpassword;
		var _self = window;
			
		$(document).ready(function(){
			
			$("#form-change-password").on('submit',function(e){
				e.preventDefault();
			
				var verify = CSSValidate(this);
			
				if(!verify)return;
				changePassword();
				
			});
			
			$("#reloadcaptcha").on('click',function(){
				$("#img-captcha").attr("src",$("#img-captcha").attr("src")+'&'+new Date().getTime());
			});
			
			$("#reloadcaptcha").hover(
				function(){
					$( this )[0].style.border = "1px solid #FFFFFF";
				}, function() {
   					$( this )[0].style.border = "";
  				}
			);
			
			if(SESSION.isTokenExpire == 'true'){
				CSSDialog.warn('Session ของท่านหมดอายุ กรุณาทำรายการใหม่').on('ok', 
					function(){
						_self.location='${contextPath}/secure/home.html';
					}
				);
			}
			
		});
		
		function changePassword(){
			var params = {};
			params['params.username'] = $('#userId').val();
			params['params.oldpassword'] = $('#old-password').val();
			params['params.newpassword'] = $('#new-password').val();
			params['params.confirmnewpassword'] = $('#confirm-new-password').val();
			params['params.captchaTimeStamp'] = SESSION.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
		
			$.ajax({
  method: "POST",
				data : params,
				url : action,
				success : function(json) {
					CSSDialog.alert(json.data.resultsMsg).on('ok', 
						function(){
							if(json.data.success){
									window.location="${contextPath}/secure/home.html";
							}else{
								$("#img-captcha").attr("src",$("#img-captcha").attr("src")+'&'+new Date().getTime());
								$('#captchaCode').val('');
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
		
		validateMinLengthPassword=function(el){
			el = $(el);
			if(el.val().length>=8){
				return true;
			}
			return false;
		}
		
		validateMatch=function(){
			if($("#new-password").val() == ''){
				$("#confirm-new-password").val('');
			}
			return $("#new-password").val() == $("#confirm-new-password").val();
		}
		
		validateMatchOldPassword=function(){
			return $("#new-password").val() != $("#old-password").val();
		}
		
		validateCaptcha = function(el){
			var params = {};
			params['params.captchaTimeStamp'] = SESSION.captchaTimeStamp;
			params['params.captchaCode'] = $('#captchaCode').val();
			var res = $.ajax({	
				type : 'POST',
  				method: "POST",
				data : params,
				async : false,
				url : "/cybertron/pub/page/isvalidcaptcha.html"
			}).responseText;
			return JSON.parse(res).data;
		}
		
		
		$("#testcaptcha").on('click',function(){
				var params = {};
				params['params.captchaTimeStamp'] = SESSION.captchaTimeStamp;
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