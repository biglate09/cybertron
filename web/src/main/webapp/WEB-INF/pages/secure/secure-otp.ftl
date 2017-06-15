<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS ={
			signup : '${contextPath}',
			submit: '${contextPath}/secure/member/personal/confirmOtp.html',
			reset : '${contextPath}/secure/member/personal/resetOtp.html',
			back : '${contextPath}/secure/member/personal/info.html'
		};
	</script>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					กรุณากรอกรหัส <br /> OTP ที่ท่านได้รับ
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
				<form action="#" id="confirm-form" method="post">
				<input type="hidden" name="otp.token" id="reset-token" value="${Request.otp_token!""}"/>
					<fieldset class="form-group">
						<label for="userId">หมายเลขโทรศัพท์มือถือ</label>
					    <input type="text" name="otp.tel" class="form-control" id="tel" readonly value="${Request.otp_tel!""}">
					</fieldset>	
					<fieldset class="form-group">
						<label for="reference">หมายเลขอ้างอิง</label>
					    <input type="text" name="otp.ref" class="form-control" id="reference" readonly  value="${Request.otp_ref!""}">
					</fieldset>
					<fieldset class="form-group">
						<label for="otp">รหัสยืนยัน OTP</label>
					    <input type="text" name="otp.otp" class="form-control" id="otp" placeholder="รหัสยืนยัน OTP" data-validate-required="กรอกรหัส OTP">
					</fieldset>
					<fieldset class="form-group" style="text-align:center">
						<a href="${contextPath}/secure/member/personal/edit.html?policyNo=${Request.policyNo}" class="btn btn-default oli-arrow-left">ยกเลิก</a>
						<a href="javascript:void(0)" id="confirmOTP" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</a>
						<a href="javascript:void(0)" id="resetOTP" class="btn btn-default ">ขอรับรหัสใหม่</a>
					</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${contextPath}/scripts/secure-otp.js"></script>
</body>
</html>