<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
			changePaymentModeChecking:'${contextPath}/secure/request/change/changePaymentModeChecking.html',
		}
	</script>
</head>
<body>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/request-change/change-installment.js"></script>

<script src="${contextPath}/scripts/upload/jquery.knob.js"></script>

<script src="${contextPath}/scripts/upload/jquery.ui.widget.js"></script>
<script src="${contextPath}/scripts/upload/jquery.iframe-transport.js"></script>
<script src="${contextPath}/scripts/upload/jquery.fileupload.js"></script>

<form id="form-change-installment" method="post" enctype="multipart/form-data" action="${contextPath}/secure/request/change/saveInstallment.html">
<input type="hidden" id="requestType" value="installment" />
	<div class="container" id="sticky-container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1>ข้อมูลรูปแบบงวด ชำระประกันภัย</h1>
					<h3>สำหรับรูปแบบการเลือกชำระเบี้ย ประกันภัย</h3>
				</div>
			</div>
			<#if Request.lastestPolicy??>
			<#assign lastestPolicy = Request.lastestPolicy>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">แบบฟอร์มขอเปลี่ยนแปลงการแก้ไขงวดการชำระเบี้ยประกันภัย</h4>
					</div>
					<div class="card-block card-body">
						<table class="table" style="margin-bottom: 5px;">
							<tbody>
								<tr>
									<td><input type="radio" name="installmentType" id="type" value="year" ${(lastestPolicy.paymentMode = 12)?string('checked', '')}><span style="font-size: 25px;padding-left: 10px;">รายปี</span></td>
								</tr>
								<tr>
									<td><input type="radio" name="installmentType" id="type" value="sixMonth" ${(lastestPolicy.paymentMode = 6)?string('checked', '')}><span style="font-size: 25px;padding-left: 10px;">รายหกเดือน</span></td>
								</tr>
								<tr>
									<td><input type="radio" name="installmentType" id="type" value="threeMonth" ${(lastestPolicy.paymentMode = 3)?string('checked', '')}><span style="font-size: 25px;padding-left: 10px;">รายสามเดือน</span></td>
								</tr>
								<tr>
									<td><input type="radio" name="installmentType" id="type" value="month" ${(lastestPolicy.paymentMode = 1)?string('checked', '')}><span style="font-size: 25px;padding-left: 10px;">รายเดือน</span></td>
								</tr>
							</tbody>
						</table>
<!-- 						<div id="uploadDiv"> -->
<!-- 							<a id="linkFile1" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><label required></label><span id="file1Name" style="padding-left: 5px;font-size: 20px;"></span> -->
<!-- 							<input type="file" name="file1" id="file1" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;display: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" > -->
<!-- 						</div> -->
						<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;width: 100%" accept=".jpeg,.jpg,.png,.gif,.pdf" >
						<label for="building" class="mandatory" style="padding-right: 5px;"></label><span style="font-size: 17px;">(สำเนาบัตรประชาชน)</span>
						<br/>
						<span style="color:red;font-size: 20px;">รองรับไฟล์ประเภท jpeg,png,gif หรือ pdf เท่านั้น</span>
					</div>
				</div>
				<div class="section sec-title">
					<h2>กรุณาเลือกกรมธรรม์ที่ต้องการเปลี่ยนแปลง</h2>
				</div>
				<br/>
				<#if Session.sessionPolicy??>
				<#assign sortByTime = 'thaisamut.util.CssSortPolicy'?new()>
				<#assign policies = Request.petitionPolicy>
				<#list sortByTime(policies?values) as policy>
				<#if (policy?? && policy.policyType?? && policy.policyType='O' && policy.policyStatus?? && (policy.policyStatus!='O'&&policy.policyStatus!='L'))>
				    
				    <div class="card">
						<div class="card-block card-header">
							<table>
								<tr>
									<td><span style="color: white; font-size: 20px;">เลขที่กรมธรรม์</span></td>
									<td><span style="margin-left: 10px; font-size: 20px;">${policy.policyNo}</span></td>
									<td><span style="margin-left: 10px; color: white; font-size: 20px;">${policy.prdName}</span></td>
								</tr>
							</table>
						</div>
						<div class="card-block card-body">
							<table>
								<tbody>
									<tr>
										<td><input type="checkbox" id="policy" name="policy" value="${policy.policyNo}"><span style="font-size: 20px;padding-left: 10px;">ต้องการเปลี่ยนรูปแบบงวดการชำระเบี้ย </span>
											<#if (policy.paymentMode != 0)>
												<span>จากราย ${policy.paymentMode} เดือน</span>
											<#else>
												<span>-</span>
											</#if>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</#if>
				</#list>
				</#if>
				<fieldset class="form-group">
					<div class="row" align="center">
							<a href="${contextPath}/secure/request/change/choice.html" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>
							<button class="btn oli-notify-button" type="submit" >บันทึก</button>
					</div>
				</fieldset>
			</div>
			</#if>
		</div>
	</div>
</form>
<!-- 	<script src="${contextPath}/scripts/report-request.js"></script> -->
</body>
</html>