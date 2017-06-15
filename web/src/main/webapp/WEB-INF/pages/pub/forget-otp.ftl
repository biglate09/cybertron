<!doctype html>
<html lang="en">
<head>
<title>ลืมบัญชีผู้ใช้งาน และรหัสผ่าน</title>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS ={
			signup : '${contextPath}',
			processConfirmByOtp : '${contextPath}/pub/page/forget/processConfirmByOtp.html',
			processResetOtp : '${contextPath}/pub/page/forget/processResetOtp.html',
			confirmByOtpSuccess: '${contextPath}/pub/page/forget/confirmByOtpSuccess.html'
		};
		var REQ_PARAM={
			forgetMethod : '${forgetMethod?html}',
			telNo : '${telNo?html}',
			refNo :'${refNo?html}',
			tokenId : '${tokenId?html}'
		}
	</script>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="forget-method" method="post">
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
				<form id="form-forget-otp" action="#">
					<fieldset class="form-group">
						<label for="telNo">หมายเลขโทรศัพท์มือถือ</label>
					    <input type="text" class="form-control" id="telNo" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="refNo">หมายเลขอ้างอิง</label>
					    <input type="text" class="form-control" id="refNo" name="reference" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="otp" required>รหัสยืนยัน OTP</label>
					    <input type="text" class="form-control" id="otp" name="otp" placeholder="รหัสยืนยัน OTP" data-validate-required="กรอกรหัสยืนยัน OTP" />
					</fieldset>
					<fieldset class="form-group" style="text-align:center">
						<a href="${contextPath}" class="btn btn-default oli-arrow-left">ยกเลิก</a>
						<button type="submit" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
						<button type="button" id="btn_reset" class="btn btn-default ">ขอรับรหัสใหม่</button>
					</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			resetOTP()
			if(REQ_PARAM.forgetMethod == 'user'){
				$('#forget-method')[0].innerHTML = "ลืมบัญชีผู้ใช้งาน";
			}else{
				$('#forget-method')[0].innerHTML = "ขอรหัสผ่านใหม่";
			}
			
			$('#telNo').val(REQ_PARAM.telNo);
			//$('#refNo').val(REQ_PARAM.refNo);
			
			$("#form-forget-otp").on('submit',function(e){
				e.preventDefault();
		
				var verify = CSSValidate(this);
			
				if(!verify)return;
				confirmOTP();
			});
			
			$("#btn_reset").on('click',function(){
				resetOTP();
			});
			
		});
		
		function confirmOTP(){
			var params = {};
			params['params.refNo'] = REQ_PARAM.refNo;
			params['params.otp'] = $('#otp').val();
			
			$.ajax({
  method: "POST",
				data : params,
				url : ACTIONS.processConfirmByOtp,
				success : function(json) {
					if(json.data.success){
						if(json.data.msg != ''){
							CSSDialog.alert(json.data.msg);
						}else{
							//CSSDialog.alert('ยืนยัน otp สำเร็จ'+json.data.username);
							window.location = ACTIONS.confirmByOtpSuccess+'?forgetMethod='+REQ_PARAM.forgetMethod+'&username='+json.data.username+'&telNo='+REQ_PARAM.telNo;
						}
					}else{
						CSSDialog.alert(json.data.msg).on('ok', 
							function(){
								$('#otp').val('');
							}
						);
					}
				},
				error : function(xhr, desc, exceptionobj) {
					if (xhr.responseText != "") {
						alert("ERROR :" + xhr.responseText);
					}
				}
			});
		}
		
		
		function resetOTP(){
		CSSDialog.confirm('ระบบจะทำการส่ง OTP เพื่อยืนยันการทำรายการ<br/>ผ่านทาง SMS หมายเลข '+REQ_PARAM.telNo)
				 .on('confirm',function(){
			var params = {};
			params['params.tokenId'] = REQ_PARAM.tokenId;
			params['params.telNo'] = REQ_PARAM.telNo;
			params['params.forgetMethod'] = REQ_PARAM.forgetMethod;
			
			$.ajax({
  method: "POST",
				data : params,
				url : ACTIONS.processResetOtp,
				success : function(json) {
					if(json.data.success){
						REQ_PARAM.refNo = json.data.refNo;
						$('#refNo').val(json.data.refNo);
					}else if(json.data.warning){
						CSSDialog.warn('<h1>' + json.data.warning + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
					}else{
						CSSDialog.warn('<h1>เกิดข้อผิดพลาด</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
					}
				},
				error : function(xhr, desc, exceptionobj) {
					if (xhr.responseText != "") {
						CSSDialog.warn("ERROR :" + xhr.responseText);
					}
				}
			});
						
		});
		}
		
	</script>
	<#if errorOTP?exists>
	<script>
	    $(document).ready(function(){CSSDialog.warn('${errorOTP?html}')});
	</script>
	</#if>
</body>
</html>