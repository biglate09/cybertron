<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
			checkPolicyTypeOrd:'${contextPath}/secure/request/change/checkPolicyTypeOrd.html',
			checkPolicyTypeAll:'${contextPath}/secure/request/change/checkPolicyTypeAll.html',
		}
	</script>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/request-change/change-main.js"></script>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title followMeBar " id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">บันทึกข้อมูล</h1><h1 style="margin-top: 0px;">ผ่านระบบ<span style="white-space:nowrap"> ออนไลน์</span></h1><h1 style="margin-top: 0px;">E-Form</h1>
				</div>
			</div>
			<div class="col-md-9">
				<div class="section"  style="padding:0px;">
					
					<table class="table">
						<tbody>
							<tr>
								<td>เปลี่ยนงวดการชำระเบี้ยประกันภัย</td>
								<td class="center"><a href="#" id="btnChangePaymentPeriod" class="mini-btn glyphicon glyphicon-list-alt" title="เปลี่ยนงวดการชำระ" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'changerequest', eventAction: 'click',eventLabel: 'premium'});"></a></td>
							</tr>
							<tr>
								<td>เปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกัน</td>
								<td class="center"><a href="#" id="btnChangeAssured" class="mini-btn glyphicon glyphicon-list-alt" title="เปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกัน" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'changerequest', eventAction: 'click',eventLabel: 'name'});"></a></td>
							</tr>
							<tr>
								<td>เปลี่ยนแปลงที่อยู่ตามทะเบียนบ้าน</td>
								<td class="center"><a href="#" id="btnChangeAddress" class="mini-btn glyphicon glyphicon-list-alt" title="เปลี่ยนแปลงสถานที่ติดต่อและที่อยู่ตามทะเบียนบ้าน" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'changerequest', eventAction: 'click',eventLabel: 'address'});"></a></td>
							</tr>
							<tr>
								<td>เปลี่ยนแปลงวิธีรับเงินผลประโยชน์</td>
								<td class="center"><a href="#" id="btnChangeReceive" class="mini-btn glyphicon glyphicon-list-alt" title="เปลี่ยนแปลงวิธีรับเงินผลประโยชน์" onclick="ga('set', 'nonInteraction',true);ga('send', 'event', { eventCategory:'changerequest', eventAction: 'click',eventLabel: 'benefit'});"></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>