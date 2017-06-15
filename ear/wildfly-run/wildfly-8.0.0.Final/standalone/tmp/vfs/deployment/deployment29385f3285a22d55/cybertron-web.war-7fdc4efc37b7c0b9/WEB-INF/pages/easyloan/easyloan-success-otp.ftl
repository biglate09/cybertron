<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<style>
		.success-text {
			font-size: 28px;
			color: #00B5CC;
    		font-weight: bold;
		}
	</style>
</head>
<body>
	<input type="hidden" id="contextPath" value="${contextPath}" />
	<div class="container">
		<div class="row ">
			<div class="col-md-12 md-right" style="padding-bottom: 30px;">
				<br/>
				<br/>
				<div align="center" class="success-text">
					<#assign REQ_NO = Session.REQ_NO>
					ได้รับคำร้องขอสมัคร Easy Loan (OceanLife iService) เลขที่ ${REQ_NO!"-"?html}
					เรียบร้อยแล้ว
				</div>
				<div align="center" class="success-text">
					เจ้าหน้าที่จะดำเนินการและแจ้งผลการดำเนินการภายใน 7 วันทำการหรือสอบถามเพิ่มเติม โทร. 0-2207-8888
				</div>
				<div align="center" class="success-text">
					ขอบคุณที่ใช้บริการ
				</div>
				<br/>
				<br/>
				<div align="center" class="success-text">
					<a href="${contextPath}/secure/member/personal/info.html" class="btn btn-default oli-arrow-left">กลับสู่เมนูหลัก</a>
				</div>
				<br/>
				<br/>
			</div>
		</div>
	</div>
	
</body>
</html>