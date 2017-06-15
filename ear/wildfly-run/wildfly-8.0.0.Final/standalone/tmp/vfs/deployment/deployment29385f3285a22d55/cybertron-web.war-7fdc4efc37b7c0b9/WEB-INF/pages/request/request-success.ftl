<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<style>
		.success-text {
			font-size: 24px;
			color: gray;
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
				<#assign reqNo = Request.reqNo>
				<div align="center" class="success-text">
					ได้รับคำร้องขอเปลี่ยนแปลงแก้ไขข้อมูลออนไลน์(iService) เลขที่ ${(reqNo!"")?html} เรียบร้อยแล้ว
				</div>
				<div align="center" class="success-text">
					หากมีข้อสอบถามเพิ่มเติม โทร. 0-2207-8888
				</div>
				<div align="center" class="success-text">
					ขอบคุณที่ใช้บริการ
				</div>
				<br/>
				<br/>
				<div align="center" class="success-text">
					<a href="${contextPath}/secure/request/change/choice.html" class="btn btn-default oli-arrow-left">กลับสู่เมนูหลัก</a>
				</div>
				<br/>
				<br/>
			</div>
		</div>
	</div>
	
</body>
</html>