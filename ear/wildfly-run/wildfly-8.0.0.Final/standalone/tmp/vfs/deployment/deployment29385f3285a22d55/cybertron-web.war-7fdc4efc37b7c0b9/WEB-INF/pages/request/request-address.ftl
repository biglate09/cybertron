<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
				changeProvince:'${contextPath}/secure/request/change/changeProvince.html',
		}
	</script>
</head>
<body>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/request-change/change-address.js"></script>

<script src="${contextPath}/scripts/upload/jquery.knob.js"></script>

<script src="${contextPath}/scripts/upload/jquery.ui.widget.js"></script>
<script src="${contextPath}/scripts/upload/jquery.iframe-transport.js"></script>
<script src="${contextPath}/scripts/upload/jquery.fileupload.js"></script>

<form id="form-change-address" method="post" enctype="multipart/form-data" action="${contextPath}/secure/request/change/saveAddress.html">
<input type="hidden" id="requestType" value="address" />
	<div class="container" id="sticky-container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1 style="margin-bottom: 0px;">ข้อมูลที่อยู่ตาม</h1><h1 style="margin-top: 0px;">ทะเบียนบ้าน</h1>
					<h3 style="margin-bottom: 0px;">สำหรับในการติดต่อแจ้งข่าวสาร</h3><h3 style="margin-top: 0px;">ของบริษัท</h3>
				</div>
			</div>
			<#if Request.lastestPolicy??>
			<#assign lastestPolicy = Request.lastestPolicy>
			<input type="hidden" name="param.policyNo" value="${(lastestPolicy.policyNo!"")?html}"/>
			<input type="hidden" name="param.policyType" value="${(lastestPolicy.policyType!"")?html}"/>
			<input type="hidden" name="param.povDesc" value="${(lastestPolicy.provinceName!"")?html}"/>
			<input type="hidden" name="param.disDesc" value="${(lastestPolicy.districtName!"")?html}"/>
			<input type="hidden" name="param.subdistrictName" value="${(lastestPolicy.subdistrictName!"")?html}"/>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">แบบฟอร์มแก้ไขที่อยู่ตามทะเบียนบ้าน</h4>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="building" class="mandatory" >ที่อยู่</label></div>
						    <div class="col-sm-10 no-padding"><textarea id="address" name="param.address" class="form-control" data-value="${(lastestPolicy.addressLine1!"")?html}" data-default="${(lastestPolicy.addressLine1!"")?html}" data-validate-required="กรอกที่อยู่">${(lastestPolicy.addressLine1!"")?html}</textarea></div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-sm-2 no-padding">
									<label for="select-province" class="mandatory">จังหวัด</label>
								</div>
								<div class="col-sm-3 no-padding">
									<select id="select-province" name="param.province" style="width:100%" data-value="${(lastestPolicy.pvCode!"")?html}" data-default="${(lastestPolicy.provinceName!"")?html}"  class="form-control"  data-validate-required="เลือกจังหวัด">
										<option value="">เลือกจังหวัด</option>
									</select>
								</div>
								<div class="col-sm-2 no-padding" class="mandatory">
									<label for="select-district" class="mandatory sm-left-gap">อำเภอ/เขต</label>
								</div>
								<div class="col-sm-3 no-padding">
									<select id="select-district" name="param.district" style="width:100%" data-value="${(lastestPolicy.dtCode!"")?html}" data-default="${(lastestPolicy.districtName!"")?html}" class="form-control" data-validate-required="เลือกอำเภอ">
										<option selected value="">เลือกอำเภอ</option>
									</select>
								</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="select-sub-district" class="mandatory">ตำบล/แขวง</label></div>
						    <div class="col-sm-4 no-padding">
						    	<select id="select-sub-district" name="param.subdistrict" style="width:100%" class="form-control" data-default="${(lastestPolicy.subdistrictName!"")?html}" data-validate-required="กรอกตำบล">
										<option selected value="">เลือกตำบล</option>
								</select>
							</div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-sm-2 no-padding">
									<label for="zipcode" class="mandatory">รหัสไปรษณีย์</label>
								</div>
								<div class="col-sm-4 no-padding"><input type="text" class="form-control" id="zipcode" name="param.zipcode" placeholder="รหัสไปรษณีย์" data-validate-required="กรอกรหัสไปรษณีย์" data-default="${(lastestPolicy.postcode!"")?html}" data-only-rule="number" maxlength="5" data-value="${(lastestPolicy.postcode!"")?html}"  value="${(lastestPolicy.postcode!"")?html}" readonly="readonly"></div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="building" class="mandatory">เอกสารแนบ 1</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile1" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file1Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" style="font-size: initial;padding-top: 2%"> -->
						    	<span style="font-size: 17px;">(สำเนาบัตรประชาชน)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="building" class="mandatory">เอกสารแนบ 2</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile2" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file2Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" style="font-size: initial;padding-top: 2%"> -->
						    	<span style="font-size: 17px;">(สำเนาทะเบียนบ้าน)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"></div>
							<div class="col-sm-6 no-padding">
								<span style="color:red;font-size: 20px;">รองรับไฟล์ประเภท jpeg,png,gif หรือ pdf เท่านั้น</span>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="section sec-title">
					<h2>ระบบจะเปลี่ยนแปลงทุกๆกรมธรรม์ตามรายการด้านล่าง</h2>
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
								<td><span style="margin-left: 10px; font-size: 20px;">${policy.policyNo?html}</span></td>
								<td><span style="margin-left: 10px; color: white; font-size: 20px;">${policy.prdName?html}</span></td>
							</tr>
						</table>
					</div>
					<div class="card-block card-body">
						<table>
							<tbody>
								<tr>
									<td><span style="font-size: 20px;padding-left: 10px;">ต้องการเปลี่ยนที่อยู่</span><span style="padding-left: 40px;">${policy.fullAddress?html}</span></td>
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
			</#if>
		</div>
	</div>
	</form>
<script>
	var PROVINCE = ${Request.province!'undefined'};
	var DISTRICT = ${Request.district!'undefined'};
	var SUBDISTRICT = ${Request.subdistrict!'undefined'};
</script>
	<script src="${contextPath}/scripts/report-request.js"></script>
</body>
</html>