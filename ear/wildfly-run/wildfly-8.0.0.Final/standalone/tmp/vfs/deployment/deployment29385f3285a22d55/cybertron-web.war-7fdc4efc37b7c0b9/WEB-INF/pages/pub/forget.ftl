<!doctype html>
<html lang="en">
<head>
<title>ลืมบัญชีผู้ใช้งาน และรหัสผ่าน</title>
	<meta charset="UTF-8" />
<script>
	var ACTIONS = {
		confirmByIdCard : "${contextPath}/pub/page/forget/confirmByIdCard.html"
	}
</script>	
<style type="text/css">
body{
	margin-bottom:22px !important;
} 

.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
}
	</style>
<link rel="stylesheet" href="${contextPath}/css/signup.css" />
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					ลืมบัญชีผู้ใช้งาน</br>และรหัสผ่าน
				</div>
			</div>
			<div class="col-md-9">
				<div class="section sec-content">
					<fieldset class="form-group">
						<div class="row ">
							<div class="col-md-4 col-md-offset-4">
								<div class="input-group">
									<input type="radio" class="fancy"  id="forget-user" name="forget" value="user">
									<label for="forget-user">ลืมบัญชีผู้ใช้งาน</label>
								</div>
								<div class="input-group">
									<input type="radio" class="fancy" id="forget-password" name="forget" value="password">
									<label for="forget-password">ลืมรหัสผ่าน
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset class="form-group">
						<div class="row">
							<div class="col-md-3 col-md-offset-3 col-sm-6 col-xs-6 text-right">
								<a href="${contextPath}/" class="btn btn-default oli-arrow-left" >ย้อนกลับ</a>
							</div>
							<div class="col-md-3 col-sm-6 col-xs-6">
								<button id="btn-submit" class="btn btn-default oli-default-button oli-arrow-right">ตกลง</button>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
	<script src="${contextPath}/scripts/forget.js"></script>
</body>
</html>