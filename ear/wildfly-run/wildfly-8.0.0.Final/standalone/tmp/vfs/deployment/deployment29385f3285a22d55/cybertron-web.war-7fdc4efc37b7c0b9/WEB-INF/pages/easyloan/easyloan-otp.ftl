<!doctype html>
<html lang="en">
<head>
<title>Easy Loan ยืนยันรหัส OTP</title>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS ={
			validateOtp : '${contextPath}/secure/easyloan/validateOtp.html',
			successOtp : '${contextPath}/secure/easyloan/successOtp.html',
			back : '${contextPath}/secure/member/policy/policyinfo.html',
			resetOtp : '${contextPath}/secure/easyloan/resetOtp.html',
			confOtp:'${contextPath}/secure/easyloan/confOtp.html',
		};
		var policy = ${Session.policyStore!''};
		var userStore = ${Session.userStore!''};
		var policyNoStore = '${Session.policyNoStore!''}';
	</script>
</head>
<script src="${contextPath}/scripts/easyloan/easyloan-otp.js"></script>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					กรุณากรอกรหัส<br/>OTP ที่ท่านได้รับ<br/>เพื่อยืนยันการกู้เงิน<br/>Easy Loan
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
				<form id="form-easy-loan-otp" action="#" method="post">
					<input type="hidden" name="params.policyNo" id="params.policyNo" value="${Session.policyNoStore!''}"/>
					<fieldset class="form-group">
						<label for="userId">หมายเลขโทรศัพท์มือถือ</label>
					    <input type="text" class="form-control" id="params.telNo" name="params.telNo" value="${Session.telNoStore!''}" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="reference">หมายเลขอ้างอิง</label>
					    <input type="text" class="form-control" id="params.refNo" name="params.refNo" value="${Session.refNoStore!''}" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="otp">รหัสยืนยัน OTP</label>
					    <input type="text" class="form-control" id="params.otp" name="params.otp" placeholder="รหัสยืนยัน OTP" data-validate-required="กรอกรหัสยืนยัน OTP" />
					</fieldset>
					<fieldset class="form-group" style="text-align:center">
						<button type="button" id="btn_back" class="btn btn-default oli-arrow-left">ย้อนกลับ</button>
						<button type="button" id="btn_confirm" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
						<button type="button" id="btn_reset" class="btn btn-default ">ขอรับรหัสใหม่</button>
					</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>