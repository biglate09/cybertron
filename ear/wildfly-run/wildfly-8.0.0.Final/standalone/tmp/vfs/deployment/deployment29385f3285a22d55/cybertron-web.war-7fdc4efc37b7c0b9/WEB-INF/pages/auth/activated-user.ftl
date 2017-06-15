<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
<script>
	var ACTIONS = {
		forget : "${contextPath}/pub/page/checkforget.html",
		otp:'${contextPath}/pub/page/confirmOtp.html'
	}
</script>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					บัญชีผู้ใช้ยังไม่ผ่านการยืนยัน
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
					<div class="row ">
						<fieldset class="form-group">
							<div class="col-md-4"><label required>ช่องทางในการยืนยันการสมัคร</label></div>
							<div class="col-md-8">
							<div class="btn-group" role="group">
								<input class="custom-radio" id="confirm-tel" type="radio" name="signup.confirmMode" value="tel" checked/>
							   	<label for="confirm-tel" class="custom-radio glyphicon glyphicon-phone">โทรศัพท์มือถือ</label>
							   	<input class="custom-radio" id="confirm-mail" type="radio" name="signup.confirmMode" value="email"/>
								<label for="confirm-mail" class="custom-radio glyphicon glyphicon-envelope">อีเมล</label>
							</div>
							</div>
						</fieldset>
					</div>
	<br/>
							<div class="row">
								<div class="col-md-3 col-md-offset-3 col-sm-6 col-xs-6 text-right">
									<a href="${contextPath}/secure/logout.html" class="btn btn-default oli-arrow-left" >ออกจากระบบ</a>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-6">
									<a href="javascript:void(0)" id="btn-submit" class="btn btn-default oli-default-button oli-arrow-right">ดำเนินการ</a>
								</div>
							</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			$("#btn-submit").on('click',function(){
				
				var telChecked = $("input[name='signup.confirmMode']")[0].checked;
				var emailChecked = $("input[name='signup.confirmMode']")[1].checked;
				
				if(!telChecked && !emailChecked){
						CSSDialog.alert('กรุณาเลือกช่องทางในการยืนยันการสมัคร');
				}else{
					var confirmMode = telChecked ? 'tel' : 'email';
					var email = '';
					var flagSaveEmail = false;
					
					if(confirmMode == 'email'){
						email = getEmail();
						if(email == ''){
							return;
						}
					}else{
						// Confirm by OTP
						performActive(confirmMode, email, flagSaveEmail);
					}
				}
			});
		});
		
		function getEmail(){
			$.ajax({
  method: "POST",
				url : '${contextPath}/secure/getEmailExist.html',
				data: {},
				success:function(json){
					if(json.data.email == ''){
						CSSDialog.warn('<h1>ขออภัย</h1><p>ไม่พบข้อมูลอีเมลของท่านในระบบ</p><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
					}else{
						performActive('email', json.data.email, false);
					}
				},
				error: function(){
				
				},
				complete: function(){
				
				}
			});
		}
		
		function performActive(confirmMode, email, flagSaveEmail){
			$.ajax({
  method: "POST",
				url : '${contextPath}/secure/performActive.html',
				data: {'confirmMode':confirmMode, 'email' : email, 'flagSaveEmail' : flagSaveEmail},
				success:function(json){
					if(confirmMode == 'email'){
						if(!json.data.success ){
							if(json.data.WARNING){
								CSSDialog.warn('<h1>' + json.data.WARNING + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>')
							}else{
								CSSDialog.warn('<h1>ขออภัย</h1><p>เกิดข้อผิดพลาด</p><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
							}
						}else if(json.data.isDuplicateEmail){
							CSSDialog.warn('<h1>ขออภัย</h1><p>อีเมลของท่านเคยลงทะเบียนแล้ว</p><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
						}else{
							CSSDialog.alert('บริษัทฯ ได้จัดส่งลิงค์สำหรับยืนยันการสมัครบริการ<br/>OceanLife iService ไปยังอีเมล '+json.data.email+'<br/>กรุณากดลิงค์ในอีเมลเพื่อยืนยันการสมัคร').on('ok', 
								function(){
									window.location = '${contextPath}/secure/logout.html';
								}
							);
						}
					}else if(json.data&&json.data.success){
						CSSDialog.alert('กรุณารอรับ OTP ทาง SMS สำหรับยืนยันการสมัครบริการ OceanLife iService').on('ok', 
							function(){
								window.location = ACTIONS.otp+('?&backToActivatePage=true');
							}
						);
					}else if (json.data&&json.data.WARNING) {
						CSSDialog.warn('<h1>' + json.data.WARNING + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>')
					} else{
						CSSDialog.warn('<h1>' + "เกิดข้อผิดพลาด" + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
					}
				},
				error: function(){
				
				},
				complete: function(){
				
				}
			});
		}
	</script>
</body>
</html>