<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
				changeReceiveChecking:'${contextPath}/secure/request/change/changeReceiveChecking.html',
		}
	</script>
</head>
<body>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/request-change/change-receive.js"></script>

<script src="${contextPath}/scripts/upload/jquery.knob.js"></script>

<script src="${contextPath}/scripts/upload/jquery.ui.widget.js"></script>
<script src="${contextPath}/scripts/upload/jquery.iframe-transport.js"></script>
<script src="${contextPath}/scripts/upload/jquery.fileupload.js"></script>

<form id="form-change-receive" method="post" method="post" enctype="multipart/form-data" action="${contextPath}/secure/request/change/saveReceive.html">
<input type="hidden" id="requestType" value="receive" />
	<div class="container" id="sticky-container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1 style="margin-bottom: 0px;">ข้อมูลช่องทางรับเงิน</h1><h1 style="margin-top: 0px;">ผลประโยชน์</h1>
					<h3 style="margin-bottom: 0px;">สำหรับช่องทางการรับเงิน</h3><h3 style="margin-top: 0px;">ผลประโยชน์</h3>
				</div>
			</div>
			<input type="hidden" name="param.bankDesc"/>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">แบบฟอร์มขอเปลี่ยนแปลงแก้ไขรายละเอียดวิธีรับเงินผลประโยชน์</h4>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">ธนาคาร</label></div>
						    <div class="col-sm-6 no-padding">
						    	<select id="bank" name="param.bankName" style="width:100%" class="form-control" data-validate-required="เลือกธนาคาร">
									<option selected value="">เลือกธนาคาร</option>
								</select>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">ชื่อบัญชี</label></div>
						    <div class="col-sm-6 no-padding">
						    	<input id="bankName" name="param.account" type="text" class="form-control" placeholder="กรอกชื่อบัญชีที่ตรงกับชื่อกรมธรรม์" value="${(lastestPolicy.titleDesc!"")?html}${lastestPolicy.firstName+' '?html!""}${(lastestPolicy.lastName!"")?html}" data-validate-required="กรอกชื่อบัญชีที่ตรงกับชื่อกรมธรรม์" maxlength="200">
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">เลขที่บัญชี</label></div>
						    <div class="col-sm-6 no-padding">
						    	<input id="bankNo" name="param.accountNo" type="text" class="form-control" placeholder="เลขที่บัญชี"  data-validate-required="กรอกเลขที่บัญชี">
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building">สาขา</label></div>
						    <div class="col-sm-6 no-padding">
						    	<input id="bankBarnch" name="param.branch" type="text" class="form-control" placeholder="สาขา">
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building"  class="mandatory">เอกสารแนบ 1</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile1" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file1Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;width: 100%" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" style="font-size: initial;padding-top: 2%"> -->
						    	<span style="font-size: 17px;">(สำเนาบัตรประชาชน)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building"  class="mandatory">เอกสารแนบ 2</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile1" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file1Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;width: 100%" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" style="font-size: initial;padding-top: 2%"> -->
						    	<span style="font-size: 17px;">(สำเนาสมุดบัญชีเงินฝากธนาคาร)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"></div>
							<div class="col-sm-10 no-padding">
								<span style="color:red;font-size: 20px;">หมายเหตุ: ต้องเป็นบัญชีเงินฝากประเภทออมทรัพย์/สะสมทรัพย์ของผู้เอาประกันเท่านั้น</span>
								<br/><span style="color:red;font-size: 20px;">รองรับไฟล์ประเภท jpeg,png,gif หรือ pdf เท่านั้น</span>
							</div>
						</fieldset>
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
				<#if policy??>
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
									<td><input type="checkbox" name="policy" value="${policy.policyNo}"><span style="font-size: 20px;padding-left: 10px;">ต้องการเปลี่ยนวิธีการรับเงินผลประโยชน์</span><span style="padding-left: 40px;">${policy.benefitChannel!"-"}</span></td>
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
							<button class="btn oli-notify-button" type="submit">บันทึก</button>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
	</form>
<script>
	var BANK = ${Request.bankList!'undefined'};
</script>
</body>
</html>