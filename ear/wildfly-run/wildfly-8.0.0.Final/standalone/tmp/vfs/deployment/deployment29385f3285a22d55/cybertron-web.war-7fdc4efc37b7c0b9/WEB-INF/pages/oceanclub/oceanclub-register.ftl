<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={

		}
	</script>
	<style>
		.form-style {
		    font-size:20px;
		    padding: 1%;
		}
		.form-style-label {
			color: #00B5CC;
    		font-weight: bold;
    		
		}
		.question-remark {
			color: gray;
			font-style: italic;
		}
	</style>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/ocean-club/ocean-club.js"></script>
<body>
	<form id="form-ocean-club" method="post" action="${contextPath}/secure/ocean/club/saveRegister.html">
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">สมัครสมาชิก<br>Ocean Club</h1>
				</div>
			</div>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">แบบสอบถามข้อมูลกิจกรรม</h4>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">1. ท่านใช้โทรศัพท์มือถือของผู้ให้บริการเครือข่ายใด</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs1">
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c1" value="1"><span> AIS</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c1" value="2"><span> DTAC</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c1" value="3"><span> TRUEMOVE-H</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c1" value="4"><span> ไม่ทราบ</span></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">2. ท่านใช้บริการโทรศัพท์มือถือเป็นแพ็กเกจประเภทใด</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs2">
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c2" value="5"><span> แบบรายเดือน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c2" value="6"><span> แบบเติมเงิน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c2" value="7"><span> ไม่ทราบ</span></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">3. โทรศัพท์มือถือของท่านเป็นประเภทใด</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs3">
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c3" value="8"><span> Smart Phone</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c3" value="9"><span> Basic Phone</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c3" value="10"><span> ไม่ทราบ</span></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">4. ปกติแล้วคุณชอบท่องเที่ยวหรือไม่</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs4">
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c4" value="12"><span> ไม่ชอบเที่ยว</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c4" value="11"><span> ชอบเที่ยว</span></div>
						</fieldset>
						<fieldset class="form-style" id="subQ4">
							<div class="col-sm-12">คุณชอบท่องเที่ยวสถานที่ท่องเที่ยวประเภทใด</div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="13"><span> ทะเล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="14"><span> ภูเขา</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="15"><span> น้ำตก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="16"><span> พิพิธภัณฑ์</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="17"><span> วัด/โบราณสถาน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="18"><span> ต่างประเทศ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="19"><span> สวนสนุก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="20"><span> สวนสัตว์</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc4" value="21"><span> ห้างสรรพสินค้า</span></div>
							<div class="col-sm-3">
								<input type="checkbox" class="ocRegister" name="sc4" value="22"><span> สถานที่ท่องเที่ยวอื่นๆ</span>
							</div>
							<div class="col-sm-3"><input type="text" id="sc4a10other" name="sc4a10other" class="form-control" style="height: 25px;" readonly="readonly" ></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">5. ปกติแล้วคุณออกกำลังกายหรือไม่</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs5">
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c5" value="23"><span> ไม่ออกกำลังกาย</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c5" value="24"><span> ออกกำลังกาย</span></div>
						</fieldset>
						<fieldset class="form-style" id="subQ5">
							<div class="col-sm-12"><span>คุณออกกำลังกายประมาณกี่ครั้งต่อสัปดาห์</div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="25"><span> 1 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="26"><span> 2 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="27"><span> 3 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="28"><span> 4 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="29"><span> มากกว่า 4 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-12"><span>คุณออกกำลังกายประเภทใด</div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="30"><span> กระโดดเชือก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="31"><span> กอล์ฟ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="32"><span> กายบริหาร</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="33"><span> เครื่องออกกำลังกาย</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="34"><span> เดิน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="35"><span> ตะกร้อ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="36"><span> เต้นรำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="37"><span> เทนนิส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="38"><span> ไท่เก็ก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="39"><span> บาสเกตบอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="40"><span> แบดมินตัน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="41"><span> ปั่นจักรยาน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="42"><span> ปิงปอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="43"><span> เปตอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="44"><span> ฟิตเนส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="45"><span> ฟุตบอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="46"><span> มวย</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="47"><span> ไม้พลอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="48"><span> โยคะ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="49"><span> วอลเล่ย์บอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="50"><span> ว่ายน้ำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="51"><span> วิ่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="52"><span> แอโรบิค</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="53"><span> ฮูลาฮูป</span></div>
							<div class="col-sm-3">
								<input type="checkbox" class="ocRegister" name="sc5_2" value="54"><span> ออกกำลังกายประเภทอื่นๆ</span>
							</div>
							<div class="col-sm-3"><input type="text" id="sc5a25other" name="sc5a25other" class="form-control" style="height: 25px;" readonly="readonly" ></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">6. ปกติแล้วคุณชอบฟังเพลงหรือไม่</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs6">
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c6" value="55"><span> ไม่ชอบฟัง</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c6" value="56"><span> ชอบฟัง</span></div>
						</fieldset>
						<fieldset class="form-style" id="subQ6">
							<div class="col-sm-12">แนวเพลงใดที่คุณชอบฟัง</div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="57"><span> เพลงไทยสากล แนวป๊อป</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="58"><span> เพลงไทยสากล แนวร็อก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="59"><span> เพลงไทยสากล แนวแดนซ์</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="60"><span> เพลงลูกทุ่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="61"><span> เพลงลูกกรุง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="62"><span> เพลงหมอลำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="63"><span> เพลงเพื่อชีวิต</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="64"><span> เพลงไทยเดิม</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="65"><span> เพลงคลาสิก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="66"><span> เพลงแจ๊ส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="67"><span> เพลงเกาหลี</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="68"><span> เพลงญี่ปุ่น</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="69"><span> เพลงจีน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="70"><span> เพลงฝรั่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="71"><span> เพลงเด็ก/การ์ตูน</span></div>
							<div class="col-sm-3"><span>&nbsp;</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="72"><span> เพลงอื่นๆ</span></div>
							<div class="col-sm-3"><input type="text" id="sc6a16other" name="sc6a16other" class="form-control" style="height: 25px;" readonly="readonly" ></div>
<!-- 							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="a17"> เพลงอื่นๆ</div> -->
<!-- 							<div class="col-sm-9"><input type="text" id="sc6a16other" name="sc6a16other" class="form-control" style="height: 25px;" readonly="readonly" ></div> -->
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">7. คุณชื่นชอบอาหารประเภทใด</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs7">
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="74"><span> อาหารไทย-ภาคเหนือ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="75"><span> อาหารไทย-ภาคอีสาน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="76"><span> อาหารไทย-ภาคกลาง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="77"><span> อาหารไทย-ภาคใต้</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="78"><span> อาหารเกาหลี</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="79"><span> อาหารจีน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="80"><span> อาหารญี่ปุ่น</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="81"><span> อาหารฝรั่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="82"><span> อาหารทะเล/ซีฟู๊ด</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="83"><span> อาหารเจ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="84"><span> มังสวิรัติ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="85"><span> อาหารเพื่อสุขภาพ/ชีวจิต</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="86"><span> อาหารอิสลาม</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="87"><span> อาหารประเภทอื่นๆ</span></div>
							<div class="col-sm-3"><input type="text" id="c7a14other" name="c7a14other" class="form-control" style="height: 25px;" readonly="readonly" ></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">8. ปกติแล้วคุณซื้อสินค้าอุปโภคบริโภคที่ใช้ในชีวิตประจำวันจากสถานที่ใด</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs8">
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="88"><span> ร้านโชห่วย/ร้านขายของชำ</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="89"><span> ตลาดสด</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="90"><span> ตลาดนัด</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="91"><span> ขายตรง</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="92"><span> ห้าง/ซุปเปอร์ในพื้นที่</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="93"><span> 7-11 (เซเว่นอีเลฟเว่น)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="94"><span> Family Mart (แฟมิลี่มาร์ท)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="95"><span> Lawson 108 (ลอว์สัน ร้อยแปด)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="96"><span> Lotus Express (โลตัสเอกซ์เพรส)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="97"><span> Big C Mini (บิ๊กซีมินิ)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="98"><span> Tops Supermarket (ท๊อปส์ ซูเปอร์มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="99"><span> Goumet Marget (กูร์เมต์ มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="100"><span> Home Fresh Mart (โฮมเพรสมาร์ท)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="101"><span> Villa Market (วิลล่า มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="102"><span> Max Value (แม็กซ์แวลู)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="103"><span> Top Daily (ท็อปเดลี่)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="104"><span> Tesco Lotus (เทสโก้ โลตัส)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="105"><span> Big C (บิ๊กซี)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="106"><span> Makro (แม็คโคร)</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c8" value="107"><span> สถานที่ซื้อสินค้าอื่นๆ</span></div>
							<div class="col-sm-3"><input type="text" id="c8a20other" name="c8a20other" class="form-control" style="height: 25px;" readonly="readonly" ></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><span>9. ข้อเสนอแนะอื่นๆเกี่ยวกับ Ocean Club</span><label class="question-remark">(ถ้ามี)</label></div>
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><textarea id="remark" name="remark" style="width: 100%"></textarea></div>
						</fieldset>
					</div>
				</div>
				<fieldset class="form-group">
					<div class="row" align="center">
							<a href="${contextPath}/secure/ocean/club/index.html" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>
							<button class="btn oli-notify-button" onclick="ga('send', 'event', { eventCategory: 'regisoceanclub', eventAction: 'submit', eventLabel: 'newuser'});" type="submit">บันทึกแบบสอบถามและสมัครสมาชิก</button>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
	</form>
</body>
</html>