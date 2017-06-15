<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<link rel="stylesheet" href="${contextPath}/css/payment-main.css" />
</head>
<body>
<form id="form-payment-card"  class="form-horizontal" method="post" action="${contextPath}/secure/payment/registerpaymentcardsuccess.html">
	<div class="container">
		<div class="card">
			<div class=" pay-option-block info">
				<div class="icon" align="center">
					<img id="payment-type-4" src="${contextPath}/images/payment/logo-payment-card.png"/>
				</div>
				<div class="header">
					<div class="font-head">เกี่ยวกับบัตรชำระเบี้ยประกันภัย</div>
					<div class="font-sub-head">บัตรชำระเบี้ยประกันภัย สะดวก รวดเร็ว สำหรับการชำระเบี้ยผ่านช่องทางบริการดังต่อไปนี้</div>
					<ul>
						<li>Counter Bank 
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_1.png"/>
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_2.png"/>
							<img class="img-guild smaller" src="${contextPath}/images/logo_list_payment_card_1_3.png"/> </li>
						<li>Counter Service 
							<img class="img-guild" src="${contextPath}/images/logo_list_payment_card_2_1.png"/>
							<img class="img-guild" src="${contextPath}/images/logo_list_payment_card_2_2.png"/></li>
					</ul>
					<div>
						<span class="font-sub-head">เพียงคุณลูกค้ายินยอมให้บริษัทฯ</span>
						<ul>
							<li>แจ้งผลการชำระเบี้ยประกันภัย ด้วยวิธีส่ง SMS (ข้อความ) ตามหมายเลขโทรศัพท์ที่ลูกค้าได้ให้ไว้ทุกครั้งที่มีการชำระเบี้ยประกันภัย</li>
							<li>จัดส่งใบเสร็จรับเงินเบี้ยประกันภัยภายในเดือนมกราคมของปีถัดไป ให้แก่ลูกค้า</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="card">
				<div class="register-table">
					<h1 class="register-head-text">Application Procedures</h1>
					<h3 class="register-head-text">สมัครผ่านเว็บไซต์ของเรา</h3>
					<h3 class="register-head-text-big mandatory ">ที่อยู่ที่ต้องการจัดส่ง Payment Card </h3>
					<input type="hidden" name="param.policyNo" value="${policy.policyNo!''}"/>
					<input type="hidden" name="param.povDesc"/>
					<input type="hidden" name="param.disDesc"/>
					<input type="hidden" name="param.subDistDesc"/>
					<div class="row address-option-block">
						<div class="col-sm-4 col-md-3 col-md-offset-1">
							<input type="radio" id="addressType1" class="fancy" name="param.addressType" value="C">
							<label for="addressType1" class="register-text">ที่อยู่ปัจจุบัน</label>
						</div>
						<div class="col-sm-8">
							<span class="register-yellow-text">(${policy.fullAddress?html!''})</span>
						</div>
					</div>
						<!-- end row -->
					<div id="addressTypeO" class="row address-option-block">
						<div class="col-md-3 col-md-offset-1">
							<input type="radio" id="addressType2" class="fancy" name="param.addressType" value="O">
							<label for="addressType2" class="register-text">อื่นๆ</label>
						</div>
						<div class="col-md-8"><!-- form -->
							<div class="row bottom-gap">
								<div class="col-sm-3">
									<span class="register-text">ที่อยู่ :</span>
								</div>
								<div class="col-sm-9">
									<input class="form-control" type="text" id="addressfull" name="param.addressLine1" width="100%" maxlength="50" data-validate-required="กรอกที่อยู่">
								</div>
							</div>
							<div class="row bottom-gap">
								<div class=" col-sm-3">	
									<label for="select-province" class="register-text">จังหวัด :</label>
								</div>
								<div class="col-sm-3">
									<select id="select-province" name="param.province" style="width:100%" class="form-control"  data-validate-required="เลือกจังหวัด">
										<option selected value="">เลือกจังหวัด</option>
									</select>
								</div>
								<div class="col-sm-3 ">
									<span class="register-text">อำเภอ/เขต :</span>
								</div>
								<div class="col-sm-3 ">
										<select id="select-district" name="param.district" style="width:100%"  class="form-control" data-validate-required="เลือกอำเภอ">
													<option selected value="">เลือกอำเภอ</option>
										</select>
								</div>
							</div>
							<div class="row bottom-gap">
								<div class="col-sm-3 ">
									<span class="register-text">ตำบล/แขวง :</span>
								</div>
								<div class="col-sm-3 ">
										<select name="select-sub-district" name="param.subDistrict" id="select-sub-district" style="width:100%" class="form-control" data-validate-required="กรอกตำบล">
													<option selected value="">เลือกตำบล</option>
										</select>
								</div>
								<div class="col-sm-3 ">
									<span class="register-text">รหัสไปรษณีย์ :</span>
								</div>
								<div class="col-sm-3 ">
									<input type="text" class="form-control" id="zipcode" name="param.postcode"  placeholder="รหัสไปรษณีย์" data-validate-required="กรอกรหัสไปรษณี" maxlength="5"  data-only-rule="number" disabled>
								</div>
							</div>
							<div class="register-yellow-text">ที่อยู่อื่นๆที่ท่านระบุจะใช้สำหรับการส่งเอกสารในครั้งนี้เท่านั้น หากท่านต้องการให้ที่อยู่ดังกล่าวเป็นที่อยู่ในการติดต่อทุกครั้ง กรุณาเข้าเมนูเปลี่ยนแปลงที่อยู่ที่ติดต่อ</div>
						</div><!-- end form -->
					</div> <!-- end row -->
					
					<div  id="addressTypeB" class="row  address-option-block">
						<div class="col-md-3 col-md-offset-1">
							<input type="radio" id="addressType3" class="fancy" name="param.addressType" value="B">
							<label for="addressType3" class="register-text">สาขา</label>
							</div>
						<div class="col-md-4">
							<select id="select-branches" style="width:100%" class="form-control" name="param.branch"  data-validate-required="เลือกสาขา">
								<option selected value="">เลือกสาขา</option>
							</select>
						</div>
					</div><!-- end row -->
					<div class="row bottom-gap">
						<div class="col-md-4">
							<div class="register-head-text-big" >หมายเลขโทรศัพท์บ้าน</div>
						</div>
						<div class="col-md-4 ">
							<input class="form-control" id="hometel" type="text"  width="100%" name="param.telno"  data-only-rule="number" data-validate-phone="รูปแบบเบอร์โทรศัพท์ไม่ถูกต้อง" maxlength="9">
						</div>
					</div><!-- end row -->
					<div class="row bottom-gap">
						<div class="col-md-4">
							<div class="register-head-text-big mandatory" >หมายเลขโทรศัพท์ในการรับ SMS แจ้งเตือนการชำระ</div>
						</div>
						<div class="col-md-4 ">
							<input class="form-control" id="smstel" type="text" value="${(policy.mobile1!"")?html}" width="100%" name="param.mobile"  data-only-rule="number" data-validate-mobile="รูปแบบเบอร์โทรศัพท์มือถือไม่ถูกต้อง" maxlength="10" data-validate-required="กรอกโทรศัพท์มือถือ">
						</div>
					</div><!-- end row -->
					<div class="row">
						<div class="col-md-12 ">
							<div class="register-head-text-big mandatory" >วิธีการชำระเบี้ยปัจจุบัน </div>
						</div>
						<div class="col-sm-5 col-sm-offset-1 payment-option-block">
							<input type="radio" id="howto_type_A" class="fancy" name="param.howto" value="A">
							<label for="howto_type_A" class="register-text">ชำระผ่านตัวแทนประกันชีวิต</label>
						</div>
						<div class="col-sm-5 col-sm-offset-1 payment-option-block">
							<input type="radio" id="howto_type_B" class="fancy" name="param.howto" value="B">
							<label for="howto_type_B" class="register-text">ชำระผ่านสาขาไทยสมุทร</label>
						</div>
						<div class="col-sm-5 col-sm-offset-1 payment-option-block">
							<input type="radio" id="howto_type_E" class="fancy" name="param.howto" value="E">
							<label for="howto_type_E" class="register-text">ชำระผ่านจุดให้บริการ Bill Payment</label>
						</div>
						<div class="col-sm-5 col-sm-offset-1 payment-option-block">
							<input type="radio" id="howto_type_G" class="fancy" name="param.howto" value="G">
							<label for="howto_type_G" class="register-text">ชำระผ่านการหักบัญชีธนาคารอัตโนมัติ</label>
						</div>
						<div class="col-sm-5 col-sm-offset-1 payment-option-block">
							<input type="radio" id="howto_type_C" class="fancy" name="param.howto" value="C">
							<label for="howto_type_C" class="register-text">ชำระผ่านการหักบัตรเครดิตอัตโนมัติ</label>
						</div>
					</div><!-- end row -->
					<!-- ----------------------------------- Line ---------------------------------------- -->
					<div class="row">
						<div class="col-sm-6 xs-bottom-gap sm-right">
							<button  class="btn btn-default oli-default-button xs-full" type="submit">Register Payment Card</button>
						</div>
						<div class="col-md-3 col-md-offset-3 col-sm-6 text-right xs-bottom-gap">
							<a class="btn btn-default oli-arrow-left xs-full" href="${contextPath}/cybertron/secure/payment/paymentmain.html?policyNo=${Request.parampolicyNo!''}" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>
						</div>
					</div><!-- end row -->
				</div>
			</div>
	</div>
</form>
<script>
	var PROVINCE = ${Request.province!'undefined'};
	var DISTRICT = ${Request.district!'undefined'};
	var SUBDISTRICT = ${Request.subdistrict!'undefined'};
	var Branches = ${Request.branches!'undefined'};
</script>
	<script src="/cybertron/scripts/payment/payment-card-register.js"></script>
</body>
</html>