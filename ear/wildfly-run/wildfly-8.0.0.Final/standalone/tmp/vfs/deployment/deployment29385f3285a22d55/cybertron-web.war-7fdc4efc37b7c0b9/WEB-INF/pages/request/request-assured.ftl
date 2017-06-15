<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
</head>
<body>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/request-change/change-assured.js"></script>

<script src="${contextPath}/scripts/upload/jquery.knob.js"></script>

<script src="${contextPath}/scripts/upload/jquery.ui.widget.js"></script>
<script src="${contextPath}/scripts/upload/jquery.iframe-transport.js"></script>
<script src="${contextPath}/scripts/upload/jquery.fileupload.js"></script>

<form id="form-change-assured" method="post" enctype="multipart/form-data" action="${contextPath}/secure/request/change/saveAssured.html">
<input type="hidden" id="requestType" value="assured" />
	<div class="container" id="sticky-container">
		<#assign person = Session.sessionUser>
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1 style="margin-bottom: 0px;">ข้อมูลชื่อ-สกุล</h1><h1 style="margin-top: 0px;">ผู้เอาประกัน</h1>
					<h3 style="margin-bottom: 0px;">สำหรับข้อมูลชื่อ-สกุล</h3><h3 style="margin-top: 0px;">ของผู้เอาประกัน</h3>
				</div>
			</div>
			<#if Request.lastestPolicy??>
			<#assign lastestPolicy = Request.lastestPolicy>
			<input type="hidden" name="param.titleDesc" value="${(lastestPolicy.titleDesc!"")?html}"/>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">แบบฟอร์มขอเปลี่ยนแปลงแก้ไขชื่อ-สกุล ผู้เอาประกัน</h4>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">คำนำหน้า</label></div>
						    <div class="col-sm-3 no-padding">
						    	<select id="titleName" name="param.titleName" class="form-control" style="width:100%" data-validate-required="เลือกคำนำหน้า">
									<option selected value="">เลือกคำนำหน้า</option>
								</select>
						    </div>
						    <div class="col-sm-1 no-padding" style="width: 10px;">
						    
						    </div>
							<div class="col-sm-5 no-padding">
								<input type="text" id="titleNameOther" name="param.titleNameOther" class="form-control" style="width: 198px;" readonly="readonly" maxlength="30">
							</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">ชื่อ</label></div>
						    <div class="col-sm-6 no-padding">
						    	<input type="text" id="firstname" name="param.firstname" class="form-control" placeholder="ชื่อ" data-validate-required="กรอกชื่อ" data-value="${(lastestPolicy.firstName!"")?html}" value="${(lastestPolicy.firstName!"")?html}" maxlength="100">
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">นามสกุล</label></div>
						    <div class="col-sm-6 no-padding">
						    	<input type="text" id="lastname" name="param.lastname"  class="form-control"  placeholder="นามสกุล" data-validate-required="กรอกนามสกุล" data-value="${(lastestPolicy.lastName!"")?html}" value="${(lastestPolicy.lastName!"")?html}" maxlength="100">
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building" class="mandatory">เอกสารแนบ 1</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile1" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file1Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;width: 100%" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
						    	<span style="font-size: 17px;">(สำเนาบัตรประชาชน)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building">เอกสารแนบ 2</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile2" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file2Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" class="form-control" id="file2" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" data-validate-required="เลือกสำเนาใบเปลี่ยนชื่อ" accept="application/pdf,image/*"> -->
						    	<span style="font-size: 17px;">(สำเนาใบเปลี่ยนชื่อ)</span>
						    </div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-2"><label for="building">เอกสารแนบ 3</label></div>
						    <div class="col-sm-6 no-padding">
						    	<div id="uploadDiv">
<!-- 									<a id="linkFile3" class="btn oli-notify-button" style="font-size: 20px;">อัพโหลดไฟล์</a><span id="file3Name" style="padding-left: 5px;font-size: 20px;"></span> -->
									<input type="file" name="file" id="file" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
								</div>
<!-- 						    	<input type="file" class="form-control" id="file3" style="font-size: initial;padding: 6px 0px;border: 0px;box-shadow: none;" accept="application/pdf,image/*"> -->
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
								<td><span style="margin-left: 10px; font-size: 20px;">${policy.policyNo}</span></td>
								<td><span style="margin-left: 10px; color: white; font-size: 20px;">${policy.prdName}</span></td>
							</tr>
						</table>
					</div>
					<div class="card-block card-body">
						<table>
							<tbody>
								<tr>
									<td><span style="font-size: 20px;padding-left: 10px;">ชื่อ-สกุลผู้เอาประกัน ปัจจุบัน</span><span style="padding-left: 20px;">${(policy.titleDesc!"")?html}${(policy.firstName!"")?html} ${(policy.lastName!"")?html}</span></td>
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
<script>
	var TITLE = ${Request.titleList!'undefined'};
</script>
</body>
</html>