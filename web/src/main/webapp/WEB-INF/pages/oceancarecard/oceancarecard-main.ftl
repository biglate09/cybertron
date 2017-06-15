<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
			checkOceanCareCard:'${contextPath}/secure/ocean/care/card/checkOceanCareCard.html',
			report:'${contextPath}/secure/ocean/care/card/report.html',
			sendEmail:'${contextPath}/secure/ocean/care/card/sendEmail.html',
			checkEmail:'${contextPath}/secure/ocean/care/card/checkEmail.html',
		}
	</script>
	<style>
	
		@font-face {
			font-family: 'cordia-new';
			src: url(../fonts/cordianewbold.ttf);
		}
		
		h4 {
			text-align: justify;
		}	
	
	</style>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/ocean-care-card/ocean-care-card.js"></script>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">Ocean Care Card</h1>
				</div>
			</div>
			<div class="col-md-9 center">
				<div class="section"  style="padding:0px;">
					<div id="container">
						<img id="image" src="${contextPath}/images/ocean-care-card.png">
						<#if Session.occFullname??>
						<#assign fullname = Session.occFullname>
						<#assign toDay = Session.occDate>
						<p id="text" style="font-family: 'cordia-new'">
							${(fullname!"")?html}
					    </p>
					    <p id="sub-text" style="font-family: 'cordia-new'">
							วันที่ออกบัตร ${(toDay!"")?html}
					    </p>
					    </#if>
					</div>
					<div style="padding-top: 20px;padding-bottom: 20px;">
						<a href="${contextPath}/secure/member/personal/info.html" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>
						<button id="report" class="btn btn-default" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel:'carecard});">
<!-- 						<span class="glyphicon glyphicon-star" aria-hidden="true" style="vertical-align: sub;padding-right: 5px;"></span>ดาว์นโหลด -->
						ดาวน์โหลด
						</button>
						<button id="send-email" class="btn btn-default" onclick="ga('send', 'event', { eventCategory:'download', eventAction: 'click', eventLabel:'carecardemail'});">Send to Email</button>
<!-- 						<a href="${contextPath}/secure/ocean/care/card/sendEmail.html" class="btn btn-default">Send to Email</a> -->
					</div>
				</div>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-9 left">
				<h2 style="color: #F89728; border-bottom: 1px solid;">บัตรประกันสุขภาพ OCEAN CARE CARD</h2>
				<h3>ขั้นตอนในการเข้ารับบริการในสถานพยาบาลที่เข้าร่วมโครงการ</h3>
				<h4>· แสดงบัตรประกันสุขภาพ (OCEAN CARE CARD) พร้อมบัตรประจำตัวประชาชนหรือบัตรอื่นที่ทางราชการออกให้ (กรณีเป็นเด็กต้องแสดงบัตรประจำตัวนักเรียนที่มีรูปถ่ายรวบคู่กับบัตรประจำตัวประชาชนของบิดาหรือมารดา) ถ้ามีการเปลี่ยนแปลงชื่อ-สกุล กรุณาแสดงหลักฐานการเปลี่ยนแปลงชื่อ-สกุลให้สถานพยาบาลที่เข้าร่วมโครงการทราบด้วย พร้อมรับรองสำเนาถูกต้อง</h4>
				<h4>· ต้องแจ้งให้สถานพยาบาลทราบทันทีที่เข้ารับการรักษา เพื่อที่สถานพยาบาลจะได้ประสานงานกับบริษัทฯ ในการตรวจสอบรายละเอียดถึงสิทธิประโยชน์ของท่าน</h4>
				<h4>· กรอกแบบแจ้งเรียกร้องค่ารักษาพยาบาล โดยรับได้ที่สถานพยาบาลที่เข้ารับการรักษา</h4>
				<h4>· ลงลายมือชื่อของท่านในใบสรุปค่าใช้จ่ายการรักษาพยาบาล (ตามลายมือชื่อในใบคำขอเอาประกันภัย)</h4>
				
				<h3>เงื่อนไขการใช้บัตรประกันสุขภาพ OCEAN CARE CARD</h3>
				<h4>· การใช้บัตรจะต้องอยู่ภายใต้ข้อกำหนดของบริษัทฯ เท่านั้น</h4>
				<h4>· กรณีเปลี่ยนสถานพยาบาลที่เข้ารักษา ต้องแจ้งให้บริษัทฯ ทราบทุกครั้ง</h4>
				<h4>· บัตรประกันสุขภาพ (OCEAN CARE CARD) เป็นของบริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ซึ่งบริษัทฯ ขอสงวนสิทธิ์ในการยกเลิกหรือเรียกคืนการใช้บัตรได้ทุกเวลา</h4>
				<h4>· บัตรนี้เป็นสิทธิพิเศษโดยเฉพาะของผู้เอาประกันภัยของบริษัทฯ จะโอนให้ผู้อื่นใช้ไม่ได้</h4>
				<h4>· กรณีที่มีการละเมิดข้อกำหนดการใช้บัตรประกันสุขภาพ (OCEAN CARE CARD) หรือเงื่อนไขอื่นใด ผู้เอาประกันภัยซึ่งเป็นเจ้าของบัตรนี้จะต้องรับผิดชอบต่อผลทั้งปวงที่ผู้อื่นได้ก่อขึ้นจากการใช้บัตรนี้</h4>
				<h4>· กรณีที่บริษัทฯ ได้ชำระค่ารักษาพยาบาลให้กับสถานพยาบาลไปแล้วหากปรากฏภายหลังว่า การรักษาพยาบาลดังกล่าวอยู่ในข้อยกเว้นหรือไม่อยู่ในเงื่อนไขกรมธรรม์หรือกรณีที่ผู้เอาประกันภัยไม่มีสิทธิ์ได้รับค่าทดแทนค่ารักษาพยาบาลดังกล่าวบริษัทฯ ขอสงวนสิทธิ์ที่จะเรียกคืนที่ชำระไปแล้วจากผู้เอาประกันภัย</h4>
				<h4>· บัตรประกันสุขภาพ (OCEAN CARE CARD) สามารถใช้สิทธิ์ได้เฉพาะ สถานพยาบาลที่เข้าร่วมโครงการและรับการรักษาเป็นสถานพยาบาลแห่งแรกกรณีที่รักษาในสถานพยาบาลที่ไม่ได้เข้าร่วมโครงการ หรือรับการรักษาจากสถานพยาบาลอื่นมาก่อนแล้ว ท่านสามารถส่งเรื่องเรียกร้องสินไหมทดแทนค่ารักษาพยาบาลได้ โดยส่งเอกสารประกอบการเรียกร้องมายังบริษัทฯ โดยตรง</h4>
				<h4>· บัตรประกันสุขภาพ (OCEAN CARE CARD) สามารถใช้ได้กับสัญญาประกันภัย และ/หรือสัญญาเพิ่มเติมที่คุ้มครองค่ารักษาพยาบาลที่มีผลบังคับ</h4>
				<h4>· บัตรนี้สามารถใช้แทนบัตรประกันสุขภาพเดิมที่ท่านถืออยู่แล้วได้ทันที</h4>
				<h4>· หมายเหตุ บัตรนี้สามารถใช้ได้จนกว่าความคุ้มครองตามสัญญาประกันภัย และ/หรือสัญญาเพิ่มเติมที่ท่านซื้อจะสิ้นสุดลง</h4>
				
				<h3>กรณีที่ไม่ได้เข้ารับบริการในสถานพยาบาลที่เข้าร่วมโครงการ</h3>
				<h4>ผู้เอาประกันกันภัยจะต้องสำรองจ่ายค่ารักษาพยาบาลกับสถานพยาบาลไปก่อน จากนั้นให้ยื่นเรื่องเรียกร้องค่ารักษาพยาบาลต่อบริษัทฯ โดยตรงพร้อมต้นฉบับเอกสารหลักฐาน ดังต่อไปนี้</h4>
				<h4>1. ใบเสร็จรับเงิน</h4>
      			<h4>-  กรณีที่ท่านต้องการเบิกจากสวัสดิการอื่นร่วมด้วย ให้แจ้งบริษัทฯ ทราบล่วงหน้า</h4>
      			<h4>-  กรณีเบิกส่วนเกินจากสวสัดิการอื่น ให้สถาบันที่ให้ความคุ้มครองรับรองการจ่ายสวัสดิการที่ได้รับแล้วบนใบเสร็จรับเงิน</h4>
      			<h4>2. ใบรับรองแพทย์ของบริษัทฯ ตามแบบฟอร์มที่บริษัทฯ กำหนด และต้องระบุสาเหตุการเกิดอุบัติเหตุ และการรักษาพยาบาลอย่างชัดเจน</h4>
      			<h4>3. แบบเรียกร้องสินไหมค่าทดแทนที่แนบมาพร้อมกรมธรรม์กรอกโดยผู้เอาประกันภัย</h4>
			</div>
		</div>
	</div>
</body>
</html>