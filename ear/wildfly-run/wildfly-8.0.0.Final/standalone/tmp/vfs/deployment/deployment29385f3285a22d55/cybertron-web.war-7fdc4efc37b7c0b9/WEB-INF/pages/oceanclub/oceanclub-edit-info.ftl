<html>
<head>
    <meta charset="UTF-8" />
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/ocean-club/ocean-club.js"></script>
<body>
	<#assign markChoices = Request.markChoices>
	<form id="form-ocean-club" name="editmember" method="post" action="${contextPath}/secure/ocean/club/editMember.html">
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">แก้ไขข้อมูล<br>Ocean Club</h1>
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
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">5. ปกติแล้วคุณออกกำลังกายหรือไม่</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs5">
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c5" value="22"><span> ไม่ออกกำลังกาย</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c5" value="23"><span> ออกกำลังกาย</span></div>
						</fieldset>
						<fieldset class="form-style" id="subQ5">
							<div class="col-sm-12"><span>คุณออกกำลังกายประมาณกี่ครั้งต่อสัปดาห์</div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="24"><span> 1 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="25"><span> 2 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="26"><span> 3 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="27"><span> 4 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="sc5_1" value="28"><span> มากกว่า 4 ครั้งต่อสัปดาห์</span></div>
							<div class="col-sm-12"><span>คุณออกกำลังกายประเภทใด</div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="29"><span> กระโดดเชือก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="30"><span> กอล์ฟ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="31"><span> กายบริหาร</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="32"><span> เครื่องออกกำลังกาย</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="33"><span> เดิน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="34"><span> ตะกร้อ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="35"><span> เต้นรำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="36"><span> เทนนิส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="37"><span> ไท่เก็ก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="38"><span> บาสเกตบอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="39"><span> แบดมินตัน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="40"><span> ปั่นจักรยาน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="41"><span> ปิงปอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="42"><span> เปตอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="43"><span> ฟิตเนส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="44"><span> ฟุตบอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="45"><span> มวย</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="46"><span> ไม้พลอง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="47"><span> โยคะ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="48"><span> วอลเล่ย์บอล</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="49"><span> ว่ายน้ำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="50"><span> วิ่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="51"><span> แอโรบิค</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc5_2" value="52"><span> ฮูลาฮูป</span></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">6. ปกติแล้วคุณชอบฟังเพลงหรือไม่</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs6">
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c6" value="53"><span> ไม่ชอบฟัง</span></div>
							<div class="col-sm-3"><input type="radio" class="ocRegister" name="c6" value="54"><span> ชอบฟัง</span></div>
						</fieldset>
						<fieldset class="form-style" id="subQ6">
							<div class="col-sm-12">แนวเพลงใดที่คุณชอบฟัง</div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="55"><span> เพลงไทยสากล แนวป๊อป</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="56"><span> เพลงไทยสากล แนวร็อก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="57"><span> เพลงไทยสากล แนวแดนซ์</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="58"><span> เพลงลูกทุ่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="59"><span> เพลงลูกกรุง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="60"><span> เพลงหมอลำ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="61"><span> เพลงเพื่อชีวิต</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="62"><span> เพลงไทยเดิม</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="63"><span> เพลงคลาสิก</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="64"><span> เพลงแจ๊ส</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="65"><span> เพลงเกาหลี</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="66"><span> เพลงญี่ปุ่น</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="67"><span> เพลงจีน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="68"><span> เพลงฝรั่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="69"><span> เพลงเด็ก/การ์ตูน</span></div>
							<div class="col-sm-3"><span>&nbsp;</span></div>
<!-- 							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="sc6" value="a17"> เพลงอื่นๆ</div> -->
<!-- 							<div class="col-sm-9"><input type="text" id="sc6a16other" name="sc6a16other" class="form-control" style="height: 25px;" readonly="readonly" ></div> -->
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">7. คุณชื่นชอบอาหารประเภทใด</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs7">
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="70"><span> อาหารไทย-ภาคเหนือ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="71"><span> อาหารไทย-ภาคอีสาน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="72"><span> อาหารไทย-ภาคกลาง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="73"><span> อาหารไทย-ภาคใต้</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="74"><span> อาหารเกาหลี</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="75"><span> อาหารจีน</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="76"><span> อาหารญี่ปุ่น</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="77"><span> อาหารฝรั่ง</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="78"><span> อาหารทะเล/ซีฟู๊ด</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="79"><span> อาหารเจ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="80"><span> มังสวิรัติ</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="81"><span> อาหารเพื่อสุขภาพ/ชีวจิต</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c7" value="82"><span> อาหารอิสลาม</span></div>
						</fieldset>
						<fieldset class="form-style">
							<div class="col-md-12 col-sm-12 col-xs-12 no-padding"><label class="mandatory">8. ปกติแล้วคุณซื้อสินค้าอุปโภคบริโภคที่ใช้ในชีวิตประจำวันจากสถานที่ใด</label><label class="question-remark">(เลือกตอบได้มากกว่า 1 ข้อ)</label></div>
						</fieldset>
						<fieldset class="form-style" id="fs8">
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="83"><span> ร้านโชห่วย/ร้านขายของชำ</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="84"><span> ตลาดสด</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="85"><span> ตลาดนัด</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="86"><span> ขายตรง</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="87"><span> ห้าง/ซุปเปอร์ในพื้นที่</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="88"><span> 7-11 (เซเว่นอีเลฟเว่น)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="89"><span> Family Mart (แฟมิลี่มาร์ท)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="90"><span> Lawson 108 (ลอว์สัน ร้อยแปด)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="91"><span> Lotus Express (โลตัสเอกซ์เพรส)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="92"><span> Big C Mini (บิ๊กซีมินิ)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="93"><span> Tops Supermarket (ท๊อปส์ ซูเปอร์มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="94"><span> Goumet Marget (กูร์เมต์ มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="95"><span> Home Fresh Mart (โฮมเพรสมาร์ท)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="96"><span> Villa Market (วิลล่า มาร์เก็ต)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="97"><span> Max Value (แม็กซ์แวลู)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="98"><span> Top Daily (ท็อปเดลี่)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="99"><span> Tesco Lotus (เทสโก้ โลตัส)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="100"><span> Big C (บิ๊กซี)</span></div>
							<div class="col-sm-6"><input type="checkbox" class="ocRegister" name="c8" value="101"><span> Makro (แม็คโคร)</span></div>
							<div class="col-sm-3"><input type="checkbox" class="ocRegister" name="c8" value="102"><span> สถานที่ซื้อสินค้าอื่นๆ</span></div>
						</fieldset>
					</div>
				</div>
				<fieldset class="form-group">
					<div class="row" align="center">
							<#--<a href="${contextPath}/secure/ocean/club/index.html" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>-->
							<button class="btn oli-notify-button" onclick="ga('send', 'event', { eventCategory: 'editoceanclub', eventAction: 'submit', eventLabel: 'editmember'});" type="submit">ดำเนินการแก้ไข</button>
					</div>
				</fieldset>
			</div>
		</div>
	</div>
	</form>
    <script>
		<#list markChoices as mc>
			$('input[value="'+${mc.choicesId}+'"]').attr('checked','true');
		</#list>

        $( document ).ready(function() {
            if($("input[value='12']").attr('checked')){
                $('#subQ4 input').attr('disabled', 'disabled');
                $('input[name=sc4]').removeAttr('checked');
                $('#sc4a10other').val("");
			}

            if($("input[value='22']").attr('checked')){
                $('#subQ5 input').attr('disabled', 'disabled');
                $('input[name=sc5_1]').removeAttr('checked');
                $('input[name=sc5_2]').removeAttr('checked');
                $('#sc5a25other').val("");
            }

            if($("input[value='53']").attr('checked')){
                $('#subQ6 input').attr('disabled', 'disabled');
                $('input[name=sc6]').removeAttr('checked');
            }
        });
    </script>
</body>
</html>
