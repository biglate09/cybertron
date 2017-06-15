<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<style>
		.red-star{
			color:red;
			font-size: 22px;
		}
	</style>
	<script>
		var ACTIONS={
			getClaimChannel:'${contextPath}/secure/easyloan/getClaimChannel.html',
			save:'${contextPath}/secure/easyloan/save.html',
			genOtp:'${contextPath}/secure/easyloan/genOtp.html',
			confOtp:'${contextPath}/secure/easyloan/confOtp.html',
			home:'${contextPath}/',
		}
	</script>
</head>
<script src="${contextPath}/scripts/easyloan/easyloan-info.js"></script>
<script src="${contextPath}/scripts/upload/jquery.knob.js"></script>
<script src="${contextPath}/scripts/upload/jquery.ui.widget.js"></script>
<script src="${contextPath}/scripts/upload/jquery.iframe-transport.js"></script>
<script src="${contextPath}/scripts/upload/jquery.fileupload.js"></script>
<script type="text/javascript"></script>
<body>
<!-- <form id="form-easy-loan" method="post" method="post" enctype="multipart/form-data" action="${contextPath}/secure/easyloan/easyloansave.html"> -->
<form id="form-easy-loan" method="post" enctype="multipart/form-data">
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					Easy Loan
				</div>
			</div>
			<div class="col-md-9 md-right">
				<div class="card"> 
					<div class="card-block card-header"> 
						<h3 class="card-title">Easy Loan</h3>
					</div>
					<div class="card-block card-body">
						<#if Session.sessionPolicy??>
							<#assign policies = Session.sessionPolicy>
							<#assign policyNo = Request.params.policyNo>
							<#assign info = policies[policyNo]>
							<#assign person = Session.sessionUser>
							<#assign loanBean = info.loanBean>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">วันที่ทำรายการ</label></div>
								<div class="col-sm-5 no-padding">${currentDate!"-"?html}</div>
								<div class="col-sm-2 no-padding"><label class="form-label">เลขที่กรมธรรม์</label></div>
								<div class="col-sm-2 no-padding">
								<input type="text" style="display: none;" value="${info.policyNo!'-'?html}" id="params.policyNo" name="params.policyNo"/>
								${info.policyNo!"-"?html}
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">แบบประกัน</label></div>
								<div class="col-sm-9 no-padding">${info.prdName!"-"?html}</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">ชื่อ - สกุล</label></div>
								<div class="col-sm-5 no-padding">${person.fullName!"-"?html}</div>
								<div class="col-sm-2 no-padding"><label class="form-label">อายุ(ปี)</label></div>
								<div class="col-sm-2 no-padding">${person.age!"-"?html}</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">มูลค่าเงินสดที่สามารถกู้ได้</label></div>
								<div class="col-sm-9 no-padding"><span id="loanAmountMax">${loanBean.loanAmountTxt!"-"?html}</span></div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">ระบุจำนวนเงินที่ต้องการกู้</label></div>
								<div class="col-sm-9 no-padding">
								<input type="text" id="params.loanAmount" class="digitFilter" name="params.loanAmount" value="${loanBean.loanAmountTxt}"/>
								<span class="red-star">*</span>
								<span style="color: orange;font-size: 20px;">(สามารถแก้ไขจำนวนเงินกู้ได้)</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">อัตราดอกเบี้ยต่อปี</label></div>
								<div class="col-sm-5 no-padding">${loanBean.loanRateTxt!"-"?html} %</div>
								<div class="col-sm-2 no-padding"><label class="form-label">อากรเงินกู้(บาท)</label></div>
								<div class="col-sm-2 no-padding"><span id="loanDuty">${loanBean.loanDutyTxt!"-"?html}</span></div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-3 no-padding"><label class="form-label">คงเหลือสุทธิ(บาท)</label></div>
								<div class="col-sm-9 no-padding"><span id="loanNet">-</span></div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 no-padding"><label class="form-label">เอกสารแนบ</label></div>
								<div class="col-sm-10 no-padding">
									<input type="file" name="file1" id="file1" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
									<span style="font-size: 17px;">(สำเนาบัตรประชาชน)</span>
									<span class="red-star">*</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 no-padding"><label class="form-label"></label></div>
								<div class="col-sm-10 no-padding">
									<input type="file" name="file2" id="file2" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
									<span style="font-size: 17px;">(เอกสารแนบอื่นๆ)</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 no-padding"><label class="form-label"></label></div>
								<div class="col-sm-10 no-padding">
									<input type="file" name="file3" id="file3" class="form-request" data-validate-required="ไฟล์" style="font-size: initial;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
									<span style="font-size: 17px; position: relative;">(เอกสารแนบอื่นๆ)</span><br>
									<span style="color: red; font-size: 16px;position: absolute;">รองรับไพล์ประเภท jpeg,png,gif หรือ pdf เท่านั้น</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-12 no-padding"><label class="form-label">ช่องทางการรับเงิน</label></div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 no-padding"></div>
								<div class="col-sm-10 no-padding"><label class="form-label"><input type="checkbox" id="chkClaimChannel" name="chkClaimChannel"> ต้องการแก้ไขช่องทางการรับเงิน</label></div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 col-xs-12 no-padding"><label class="form-label">โอนเข้าบัญชีธนาคาร</label></div>
								<div class="col-sm-10 col-xs-12 no-padding form-inline">
<!-- 									<input type="hidden" id="params.bankNameText" name="params.bankNameText"> -->
									<select id="params.bankName" name="params.bankName" class="bank-group-class form-control">
										<option value="">เลือกธนาคาร</option>
									</select>
									<span class="red-star">*</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 col-xs-12 no-padding"><label class="form-label">ชื่อบัญชี</label></div>
								<div class="col-sm-10 col-xs-12 no-padding">
									<input type="text" id="params.accountName" name="params.accountName" class="bank-group-class"/>
									<span class="red-star">*</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 col-xs-12 no-padding"><label class="form-label">เลขที่บัญชี</label></div>
								<div class="col-sm-4 col-xs-12 no-padding">
									<input type="text" id="params.accountNo" name="params.accountNo" class="bank-group-class"/>
									<span class="red-star">*</span>
								</div>
								<div class="col-sm-2 col-xs-12 no-padding"><label class="form-label">สาขา</label></div>
								<div class="col-sm-4 col-xs-12 no-padding">
									<input type="text" id="params.bankBranch" name="params.bankBranch" class="bank-group-class"/>
									<span class="red-star">*</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-2 col-xs-12 no-padding"><label class="form-label">เอกสารแนบ</label></div>
								<div class="col-sm-10 col-xs-12 no-padding">
									<input type="file" name="file4" id="file4" class="form-request bank-group-class" data-validate-required="ไฟล์" style="font-size: initial;border: 0px;box-shadow: none;" accept=".jpeg,.jpg,.png,.gif,.pdf" >
									<span style="font-size: 17px;">(เอกสารแนบบัญชีธนาคาร)</span>
									<span class="red-star">*</span><br>
									<span style="color: red; font-size: 16px;position: relative;">รองรับไพล์ประเภท jpeg,png,gif หรือ pdf เท่านั้น</span>
								</div>
							</fieldset>
							<fieldset class="form-group">
								<div class="col-sm-12 col-xs-12" style="background: #F89728;color: #FFF;">
									ข้อมูลที่แสดงเป็นข้อมูล ณ วันที่ ${currentDate!"-"?html} ข้อมูลอาจมีการเปลี่ยนแปลง<br>
									บริษัทของสงวนสิทธิ์ในการอนุมัติยอดเงินกู้ กรณีจำนวนเงินที่ขอกู้ไม่สามารถอนุมัติได้
								</div>
							</fieldset>
						<#else>
							<fieldset class="form-group">
								<div class="col-sm-12 col-xs-12 no-padding"><label class="form-label">ไม่พบข้อมูลกรมธรรม์</label></div>
							</fieldset>
						</#if>
					</div>
				</div>
			</div>
		</div>
		<fieldset class="form-group">
			<div class="col-lg-3 col-md-3 col-md-offset-6 col-sm-6 col-sm-offset-4 ">
<!-- 				<a class="btn btn-default oli-arrow-left xs-full" href="${contextPath}/secure/member/policy/policyinfo.html">ย้อนกลับ</a> -->
				<a class="btn btn-default oli-arrow-left xs-full" href="${contextPath}/${backPage}">ย้อนกลับ</a>
				<input type="button" name="easyloanSaveBtn" class="btn oli-notify-button xs-full" onClick="ga('send', 'event', { eventCategory: ‘easyloan', eventAction: ‘submit', eventLabel: ‘loanrequest'});" id="easyloanSaveBtn" value="บันทึก"/>
			</div>
		</fieldset>
	</div>
</form>
<div id="pronounment" style="display: none;">
	<div id="pronounment-content">
		<p style="font-size: 2em; text-align: center;"><strong>รายละเอียดส่วนข้อความยืนยันสัญญาการกู้</strong></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ซึ่งต่อไปในสัญญานี้เรียกว่า “ผู้กู้” ได้ทำประกันภัยไว้กับ บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ซึ่งต่อไปในสัญญานี้เรียกว่า “บริษัท” ตามกรมธรรม์เลขที่ (ระบบดึงข้อมูลอัตโนมัติจากที่ผู้ใช้ขอกู้ด้านบน) ซึ่งต่อไปในสัญญานี้เรียกว่า “กรมธรรม์” และในวันทำสัญญานี้กรมธรรม์มีมูลค่าเวนคืนกรมธรรม์เกิดขึ้นแล้ว ผู้กู้จึงประสงค์ขอกู้ยืมเงินจากบริษัทโดยโอนผลประโยชน์ตามกรมธรรม์เป็นประกันหนี้เงินกู้ยืมตามเงื่อนไขแห่งกรมธรรม์ จึงตกลงทำสัญญานี้ขึ้น โดยมีเงื่อนไขดังต่อไปนี้</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 1. ผู้กู้ตกลงกู้ยืมเงินจากบริษัท และบริษัทตกลงให้ผู้กู้ กู้ยืมเงินเป็นจำนวน(ระบบดึงข้อมูลอัตโนมัติตามที่ผู้ใช้ขอกู้ด้านบน)
(_ตัวอักษรภาษาไทย_) โดยผู้กู้ตกลงให้กรมธรรม์เป็นประกันการชำระหนี้ตามสัญญานี้ และผู้กู้ตกลงชำระค่าอากรแสตมป์ตามจำนวนที่กฎหมายกำหนด โดยผู้กู้ตกลงให้บริษัทหักเงินค่าอากรแสตมป์ที่ผู้กู้มีหน้าที่ต้องชำระจากจำนวนเงินกู้ยืม</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 2. ผู้กู้ประสงค์ขอรับเงินกู้ยืมตามสัญญานี้ โดยให้บริษัทโอนเงินกู้ยืมเข้าบัญชีเงินฝากธนาคารของผู้กู้ ตามสำเนาสมุดเงินฝากธนาคารของผู้กู้แนบท้ายสัญญานี้ โดยเมื่อบริษัทโอนเงินกู้ยืมเข้าบัญชีเงินฝากของผู้กู้แล้ว ให้ถือว่าผู้กู้ได้รับเงินกู้ยืมตามสัญญานี้แล้ว</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 3. ผู้กู้ตกลงชำระดอกเบี้ยของต้นเงินกู้ยืมตามสัญญานี้ให้แก่บริษัทในอัตราร้อยละ  xx   บาทต่อปี นับแต่วันที่ได้รับเงินกู้ยืม จนกว่าจะชำระคืนต้นเงินกู้ยืมให้แก่บริษัทครบถ้วน</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 4. ภายใต้เงื่อนไขข้อ 5. และข้อ 6. ผู้กู้จะชำระคืนต้นเงินกู้ยืมให้แก่บริษัทเมื่อใดก็ได้ และในกรณีที่ผู้กู้ยังไม่ได้ชำระคืนต้นเงินกู้ยืมให้แก่บริษัทครบถ้วน ผู้กู้ตกลงชำระดอกเบี้ยของต้นเงินกู้ยืมทุกทุกวันครบรอบปี นับแต่วันทำสัญญานี้ และหากตามเงื่อนไขกรมธรรม์กำหนดให้บริษัทคิดดอกเบี้ยทบต้นได้ ถ้าผู้กู้ค้างชำระดอกเบี้ยไม่น้อยกว่าปีหนึ่ง ผู้กู้ตกลงให้บริษัทนำดอกเบี้ยที่ด้างชำระนั้นทบเข้ากับต้นเงินกู้ยืม แล้วให้คิดดอกเบี้ยในจำนวนที่ทบเข้ากันนั้นได้ทันที</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 5. หากมีการชำระเงินผลประโยชน์ใดๆตามกรมธรรม์ ผู้กู้ตกลงให้บริษัทหักเงินผลประโยชน์นั้นชำระดอกเบี้ยและต้นเงินกู้ยืมตามสัญญานี้ได้ก่อนทันที ไม่ว่าดอกเบี้ยและต้นเงินกู้ยืมนั้นจะถึงกำหนดชำระแล้วหรือไม่ก็ตาม ทั้งนี้ บริษัทจะหักชำระดอกเบี้ยที่ค้างชำระทั้งหมดก่อนและหากมีเงินคงเหลือ จะนำมาหักชำระหนี้เงินกู้ยืม โดยไม่ต้องแจ้งให้ผู้กู้ทราบก่อน</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ข้อ 6. หากในเวลาหนึ่งเวลาใดต้นเงินกู้ยืมและดอกเบี้ยตามสัญญานี้ มีจำนวนเท่ากับหรือมากกว่ามูลค่าเวนคืนกรมธรรม์หลังหักด้วยหนี้สินอื่นตามกรมธรรม์ที่มีอยู่ในเวลานั้น ผู้กู้ตกลงให้ถือว่าผู้กู้ได้ขอเวนคืนกรมธรรม์ และตกลงให้นำเงินเวนคืนทั้งหมดนั้นไปชำระดอกเบี้ยและต้นเงินกู้ยืมตามสัญญานี้ได้ทันที ไม่ว่าดอกเบี้ยและต้นเงินกู้ยืมนั้นจะถึงกำหนดชำระแล้วหรือไม่ก็ตาม และกรมธรรม์ดังกล่าวเป็นอันสิ้นผลบังคับทันที โดยไม่ต้องแจ้งให้ผู้กู้ทราบก่อน</p>
	</div>
</div>
<script>
	var BANK = ${Request.bankList!'undefined'};
	var ClaimChannel = ${Request.claimChannel!'undefined'};
</script>
</body>
</html>