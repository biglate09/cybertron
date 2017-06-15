<!doctype html>
<html lang="en">
<head>
<title>ยืนยันรหัส OTP</title>
	<meta charset="UTF-8" />
	<script>
		var ACTIONS ={
			home : '${contextPath}/secure/home.html',
			processConfirmByOtp : '${contextPath}/pub/page/forget/processConfirmByOtp.html',
			processResetOtp : '${contextPath}/pub/page/forget/processResetOtp.html',
			logout:'${contextPath}/secure/logout.html',
		};
		var REQ_PARAM={
			telNo : '${(telNo!"")?html}',
			refNo :'${(refNo!"")?html}',
			tokenId : '${(tokenId!"")?html}',
			backToActivatePage : '${(backToActivatePage!"")?html}'
		}
	</script>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					กรุณากรอกรหัส OTP ที่ท่านได้รับ เพื่อยืนยันการสมัครใช้บริการ OceanLife iService
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
				<form id="form-otp" action="#" method="post">
					<fieldset class="form-group">
						<label for="userId">หมายเลขโทรศัพท์มือถือ</label>
					    <input type="text" class="form-control" id="telNo" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="reference">หมายเลขอ้างอิง</label>
					    <input type="text" class="form-control" id="refNo" name="reference" disabled />
					</fieldset>
					<fieldset class="form-group">
						<label for="otp">รหัสยืนยัน OTP</label>
					    <input type="text" class="form-control" id="otp" name="otp" placeholder="รหัสยืนยัน OTP" data-validate-required="กรอกรหัสยืนยัน OTP" />
					</fieldset>
					<fieldset class="form-group" style="text-align:center">
						<button type="button" id="btn_back" class="btn btn-default oli-arrow-left">ย้อนกลับ</button>
						<button type="submit" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
						<button type="button" id="btn_reset" class="btn btn-default ">ขอรับรหัสใหม่</button>
					</fieldset>
				</form>
				</div>
			</div>
		</div>
	</div>
	<script src="${contextPath}/scripts/confirm-otp.js"></script>
	<#if errorOTP?exists>
	<script>
	    $(document).ready(function(){CSSDialog.warn('${errorOTP?html}')});
	</script>
	</#if>
</body>
</html>