<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
</head>
<body>
<form action="${contextPath}/secure/member/personal/save.html" id="form-change-address" method="post">
	<div class="container" id="sticky-container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1>ข้อมูลที่อยู่ในการติดต่อ <br/>สำหรับติดต่อแจ้งข่าวสาร และจัดส่งเอกสารของบริษัทฯ</h1>
				</div>
			</div>
			<div class="col-md-9">
				<#if Request.main??>
				<#assign policy = Request.main>
				<input type="hidden" name="param.policyNo" value="${(policy.policyNo!"")?html}"/>
				<input type="hidden" name="param.policyType" value="${(policy.policyType!"")?html}"/>
				<input type="hidden" name="param.povDesc" value="${(policy.provinceName!"")?html}"/>
				<input type="hidden" name="param.disDesc" value="${(policy.districtName!"")?html}"/>
				<input type="hidden" name="param.subdistrictName" value="${(policy.subdistrictName!"")?html}"/>
					<div class="card"> 
						
						<#if policy.temp >
						<div class="card-block card-header ${(policy.group!"")?html} edit-address">
						<#else>
						<div class="card-block card-header ${(policy.group!"")?html}">
						</#if>
						    <h4 class="card-title">${(policy.prdName!"")?html}</h4>
							<h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${(policy.policyNo!"")?html}<span></h6>
						</div>
						<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding "><label for="building" class="mandatory"  >ที่อยู่</label></div>
						    <div class="col-sm-10 no-padding"><input type="text" autocomplete="off" class="form-control" id="building" name="param.addressLine1"  placeholder="ที่อยู่" data-validate-required="กรอกที่อยู่" data-value="${(policy.addressLine1!"")?html}" value="${(policy.addressLine1!"")?html}"/></div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-sm-2 no-padding">
									<label for="select-province" class="mandatory">จังหวัด</label>
								</div>
								<div class="col-sm-4 no-padding">
								<input type="hidden" name="param.pvDesc"/>
									<select name="param.province" id="select-province" style="width:100%" data-value="${(policy.pvCode!"")?html}" data-default="${(policy.provinceName!"")?html}" data-validate-required="กรอกจังหวัด">
										<option selected value="">เลือกจังหวัด</option>
									</select>
								</div>
								<div class="col-sm-2 no-padding" class="mandatory">
									<label for="select-district" class="mandatory sm-left-gap">อำเภอ/เขต</label>
								</div>
								<div class="col-sm-4 no-padding">
									<select name="param.district" id="select-district" style="width:100%" data-value="${(policy.dtCode!"")?html}" data-default="${(policy.districtName!"")?html}" data-validate-required="กรอกอำเภอ">
										<option selected value="">เลือกอำเภอ</option>
									</select>
								</div>
						
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="select-sub-district" class="mandatory">ตำบล/แขวง</label></div>
						    <div class="col-sm-4 no-padding">
						    	<select name="select-sub-district" id="select-sub-district" style="width:100%" data-default="${(policy.subdistrictName!"")?html}" data-validate-required="กรอกตำบล">
										<option selected value="">เลือกตำบล</option>
								</select>
							</div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-sm-2 no-padding">
									<label for="zipcode" class="mandatory">รหัสไปรษณีย์</label>
								</div>
								<div class="col-sm-4 no-padding"><input type="text" class="form-control" autocomplete="off" id="zipcode" name="param.postcode" value="${(policy.postcode!"")?html}" data-value="${(policy.postcode!"")?html}" placeholder="รหัสไปรษณีย์" data-validate-required="กรอกรหัสไปรษณี" maxlength="5"  data-only-rule="number" ></div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="phone1" class="mandatory">โทรศัพท์มือถือ1</label></div>
						    <div class="col-sm-10 no-padding"><input type="text" class="form-control" id="phone1" autocomplete="off" name="param.mobile1" value="${(policy.mobile1!"")?html}" data-value="${(policy.mobile1!"")?html}" placeholder="โทรศัพท์มือถือ1" maxlength="10" data-validate-mobile="ข้อมูลไม่ถูกต้อง" data-validate-required="กรอกโทรศัพท์มือถือ" data-only-rule="number"></div>
						    <div class="notic">หมายเลขนี้เป็นหมายเลขหลักสำหรับติดต่อและแจ้งข่าวสารของบริษัทฯ</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="phone2">โทรศัพท์มือถือ2</label></div>
						    <div class="col-sm-10 no-padding"><input type="text" class="form-control" id="phone2" autocomplete="off" name="param.mobile2" value="${(policy.mobile2!"")?html}" data-value="${(policy.mobile2!"")?html}" placeholder="โทรศัพท์มือถือ2" data-validate-mobile="ข้อมูลไม่ถูกต้อง" maxlength="10" data-only-rule="number"></div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-md-2 no-padding">
									<label for="phoneOffice">โทรศัพท์บ้าน<spanl>
								</div>
								<div class="col-md-4 no-padding">
									<input type="text" class="form-control" autocomplete="off" id="phoneHome" name="param.phone1" value="${(policy.phone1!"")?html}" data-value="${(policy.phone1!"")?html}" placeholder="โทรศัพท์บ้าน" data-validate-phone="ข้อมูลไม่ถูกต้อง" maxlength="9"  data-only-rule="number">
								</div>
								<div class="col-md-2 no-padding">
									<label class="sm-left-gap" for="officeExt">ต่อ</label>
								</div>
								<div class="col-md-4 no-padding">
									<input type="text" class="form-control" id="homeExt" autocomplete="off" name="param.ext1" value="${(policy.ext1!"")?html}" data-value="${(policy.ext1!"")?html}" placeholder="ต่อ" data-only-rule="numcomma" maxlength="6">
								</div>
						</fieldset>
						<fieldset class="form-group">
								<div class="col-md-2 no-padding">
									<label for="phoneOffice">โทรศัพท์ที่ทำงาน<label>
								</div>
								<div class="col-md-4 no-padding">
									<input type="text" class="form-control" id="phoneOffice" autocomplete="off" name="param.phone2" value="${(policy.phone2!"")?html}" maxlength="9" data-value="${(policy.phone2!"")?html}" placeholder="โทรศัพท์ที่ทำงาน" data-validate-phone="รูปแบบเบอร์โทรศัพท์ไม่ถูกต้อง" data-only-rule="number">
								</div>
								<div class="col-md-2 no-padding">
									<label class="sm-left-gap" for="officeExt">ต่อ</label>
								</div>
								<div class="col-md-4 no-padding">
									<input type="text" class="form-control" id="officeExt" autocomplete="off" name="param.ext2" value="${(policy.ext2!"")?html}" data-value="${(policy.ext2!"")?html}" placeholder="ต่อ" data-only-rule="numcomma" maxlength="6">
					  			</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2 no-padding"><label for="email">อีเมล</label></div>
						    <div class="col-sm-10 no-padding"><input type="text" class="form-control" autocomplete="off" id="email" name="param.email" maxlength="100" value="${(policy.email!"")?html}" data-value="${(policy.email!"")?html}" placeholder="อีเมล" data-validate-email="รูปแบบอีเมลไม่ถูกต้อง">
								<div class="notic">อีเมลสำหรับติดต่อ และแจ้งข่าวสารของบริษัทฯ</div></div>
						</fieldset>
			
						</div>
					</div>
				</#if>
				<#if Request.sub??>
				<#assign policies = Request.sub>
				<div class="section sec-title" style="margin-bottom:20px">
					<h2>หากท่านต้องการเปลี่ยนที่อยู่ที่ติดต่อของกรมธรรม์อื่นให้เป็นที่เดียวกัน</h2>
					<h3>กรุณาเลือกกรมธรรม์ที่ต้องการเปลี่ยนแปลง</h3>
				</div>
				<#list policies?keys as key>
				<#if policies[key]??>
			<div class="card">
						<#if policies[key].temp >
						<div class="card-block card-header ${policies[key].group?html} edit-address">
						<#else>
						<div class="card-block card-header ${policies[key].group?html}">
						</#if>
						<label for="policy_${key?html}" >
						<h4 class="card-title">${policies[key].prdName?html}</h4>
						<h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${policies[key].policyNo?html}<span></h6>
						</label>
					</div>
					<div class="card-block card-body row">
						<div class="block col-xs-2 col-sm-1 center">
							<div class="input-group input-group-margin">
							<input id="policy_${key?html}" class="fancy" name="param.followPolicy" type="checkbox" value="${key?html}:${policies[key].policyType?html}">
							<label for="policy_${key?html}"></label>
							</div>
						</div>
						<div class="col-xs-10 col-sm-11 card-body ">
						<label for="policy_${key?html}" >
							<div class="input-group input-group-margin">
								<span class="address-label">ต้องการเปลี่ยนที่อยู่ </span>
								<span>${policies[key].fullAddress?html}</span>
							</div>
							<div class="input-group input-group-margin">
							<span class="address-label">หมายเลขโทรศัพท์มือถือ </span>
								<span>${policies[key].mobile1?html}</span>
							</div></label>
						</div>
					</div>
				</div>
				
				</#if>
				</#list>
				</#if>
				<fieldset class="form-group">
					<div class="col-sm-6 xs-bottom-gap sm-right">
						<button  class="btn oli-notify-button xs-full" onclick="ga('send', 'event', { eventCategory:'personalinfo', eventAction: 'click', eventLabel:'editaddress'})">บันทึก</button>
					</div>
					<div class="col-md-3 col-md-offset-3 col-sm-6 text-right xs-bottom-gap">
						<a class="btn btn-default oli-arrow-left xs-full" href="${contextPath}/secure/member/personal/info.html">ย้อนกลับ</a>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
	</form>
<#if INTRIGITY_ERROR??>
	<script>
		$(document).ready(function(){CSSDialog.warn('${INTRIGITY_ERROR}');});
	</script>
</#if>
<script>
	var PROVINCE = ${Request.province!'undefined'},DISTRICT = ${Request.district!'undefined'},SUBDISTRICT = ${Request.subdistrict!'undefined'},mainTelReadOnly = '${(Request.displayT!"")?html}';
</script>
	<script src="${contextPath}/scripts/personal-edit.js"></script>
</body>
</html>