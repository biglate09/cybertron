<!doctype html>
<html lang="en">
<head>
<title>ลืมบัญชีผู้ใช้งาน</title>
	<meta charset="UTF-8" />
		<script>
		var ACTIONS={
			validateByIdCard: '${contextPath}/pub/page/forget/validateByIdCard.html',
			confirmByEmail: '${contextPath}/pub/page/forget/confirmByEmail.html',
			home:'${contextPath}/'
		}
		var REQ_PARAM={
			forgetMethod : '${forgetMethod?html}'
		}
	</script>
	<link rel="stylesheet" href="${contextPath}/datepicker/css/datepicker.css" />	<style type="text/css">
body{
	margin-bottom:22px !important;
} 

.footer {
    position: fixed;
    bottom: 0;
    width: 100%;
}
	</style>
</head>
<body>
<br />
	<div class="container">
		<div class="row ">
			<div class="col-md-4">
				<div class="section sec-title" id="forget-method">
					ลืมบัญชีผู้ใช้งาน
				</div>
			</div>
			<div class="col-md-8">
				<div class="section sec-content">
					<form id="form-forget-cardid" action="#" method="post">
						<fieldset class="form-group">
							<label>กรุณากรอกข้อมูลเพื่อยืนยันตัวตน</label>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-md-12">
								<label for="cardId" required>เลขประจำตัวประชาชน</label>
							</div>
						    <div class="col-md-12">
						    	<input type="text" class="form-control" maxlength="20" data-only-rule="number" id="cardId" name="cardId" placeholder="เลขประจำตัวประชาชน" data-validate-citizenid="เลขประจำตัวประชาชนไม่ถูกต้อง" data-validate-required="กรอกเลขประจำตัวประชาชน" />
							</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-md-12">
								<label for="bday" required>วันเดือนปีเกิด</label>
							</div>
						   <!--	<div id="datepicker" class="input-group date">
							    <input type="text" id="birthDate" class="form-control" name="birthDate" readonly placeholder="วว/ดด/ปปปป" data-validate-required="กรอกวันเดือนปีเกิด">
							    <div class="input-group-addon">
							        <span class="glyphicon glyphicon-th"></span>
							    </div>
							</div> -->
						 	<div class="col-md-4" >
								<select id="bday" class="form-control" data-validate-required="กรอกวัน">
										<option value="">วัน</option>
										<option value="01">01</option>
										<option value="02">02</option>
										<option value="03">03</option>
										<option value="04">04</option>
										<option value="05">05</option>
										<option value="06">06</option>
										<option value="07">07</option>
										<option value="08">08</option>
										<option value="09">09</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
										<option value="13">13</option>
										<option value="14">14</option>
										<option value="15">15</option>
										<option value="16">16</option>
										<option value="17">17</option>
										<option value="18">18</option>
										<option value="19">19</option>
										<option value="20">20</option>
										<option value="21">21</option>
										<option value="22">22</option>
										<option value="23">23</option>
										<option value="24">24</option>
										<option value="25">25</option>
										<option value="26">26</option>
										<option value="27">27</option>
										<option value="28">28</option>
										<option value="29">29</option>
										<option value="30">30</option>
										<option value="31">31</option>
									</select>
								</div>
								 <div class="col-md-4" >
									<select id="bmonth" class="form-control" data-validate-required="กรอกเดือน">
										<option value="">เดือน</option>
										<option value="01">มกราคม</option>
										<option value="02">กุมภาพันธ์</option>
										<option value="03">มีนาคม</option>
										<option value="04">เมษายน</option>
										<option value="05">พฤษภาคม</option>
										<option value="06">มิถุนายน</option>
										<option value="07">กรกฎาคม</option>
										<option value="08">สิงหาคม</option>
										<option value="09">กันยายน</option>
										<option value="10">ตุลาคม</option>
										<option value="11">พฤศจิกายน</option>
										<option value="12">ธันวาคม</option>
									</select>
								</div>
								 <div class="col-md-4" >
									<select id="byear" class="form-control" data-validate-required="กรอกปี">
										<option value="">ปี</option>
									</select>
								</div>
						</fieldset>
						<br/>
						<fieldset class="form-group">
							<div class="row">
								<div class="col-md-3 col-md-offset-3 col-sm-6 col-xs-6 text-right">
									<a href="${contextPath}/pub/page/forget/index.html" class="btn btn-default oli-arrow-left" >ย้อนกลับ</a>
								</div>
								<div class="col-md-3 col-sm-6 col-xs-6">
									<button type="submit" id="btn-card-submit" class="btn btn-default oli-default-button oli-arrow-right">ยืนยัน</button>
								</div>
							</div>
						</fieldset>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			if(REQ_PARAM.forgetMethod == 'user'){
				$('#forget-method')[0].innerHTML = "ลืมบัญชีผู้ใช้งาน";
			}else{
				$('#forget-method')[0].innerHTML = "ขอรหัสผ่านใหม่";
				$('.under-title-band h1')[0].innerHTML = "ลืมรหัสผ่าน";
			}
			
			/*$('#datepicker').datepicker({
			    format: "dd/mm/yyyy",
			    language: "th",
			    daysOfWeekHighlighted: "0,6",
			    autoclose: true,
			    maxViewMode: 3
			});*/
	
			$("#form-forget-cardid").on('submit',function(e){
				e.preventDefault();
		
				var verify = CSSValidate(this);
				
				//if(!verify)return;
				//validateByIdCard();
				
				if(!verify){
					var fieldSize = $(this).find(".form-control").size();
					var fieldEmpty = 0;
					$.each($(this).find(".form-control"),function(index,object){
						if($(object).val() == ''){
							fieldEmpty++;
						}
					});
					
					if(fieldSize == fieldEmpty){
						CSSDialog.warn('กรุณากรอกข้อมูลให้ถูกต้อง');
					}
					return;
				}else{
					validateByIdCard();
				}
			});
			
			// Set year for dropdown list
			var currentYearTh = new Date().getFullYear()+543;
			for(var i=0; i<100; i++){
				$('#byear').append($("<option></option>").attr("value",currentYearTh-i).text(currentYearTh-i)); 
			}
			
			$('#bday').on('change', function(){
				validateDateExp();
			});
			
			$('#bmonth').on('change', function(){
				validateDateExp();
			});
			
			$('#byear').on('change', function(){
				validateDateExp();
			});
		});
		
		function validateByIdCard(){
			var params = {};
			var cardId = $('#cardId').val();
			params['params.cardId'] = cardId;
			params['params.birthDate'] = $('#bday').val()+'/'+$('#bmonth').val()+'/'+$('#byear').val();
			params['params.forgetMethod'] = REQ_PARAM.forgetMethod;
			
			$.ajax({
  method: "POST",
				data : params,
				url : ACTIONS.validateByIdCard,
				success : function(json) {
					if(json.data.errMsg != ''){
						CSSDialog.warn(json.data.errMsg);
					}else{
						if(!json.data.isHaveEmail){
							//กรณีไม่มีอีเมล
							window.location = ACTIONS.confirmByEmail+"?forgetMethod="+REQ_PARAM.forgetMethod+"&cardId="+cardId;
						}else{
							if(json.data.sendEmailSuccess){
								var msg = '';
								if(REQ_PARAM.forgetMethod == 'user'){
									msg = 'ระบบได้จัดส่ง บัญชีผู้ใช้งาน<br/>ให้ท่านทางอีเมล<br/>'+json.data.sendEmail+'<br/>เรียบร้อยแล้ว';
								}else{
									msg = 'ระบบได้จัดส่ง ลิงค์สำหรับตั้ง<br/>รหัสผ่านใหม่ ให้ท่านทางอีเมล<br/>'+json.data.sendEmail+'<br/>เรียบร้อยแล้ว';
								}
								CSSDialog.alert(msg).on('ok', 
									function(){
										window.location = ACTIONS.home;
									}
								);
							}else{
								CSSDialog.alert('เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ');
							}
						}
					}
				},
				error : function(xhr, desc, exceptionobj) {
					if (xhr.responseText != "") {
						alert("ERROR :" + xhr.responseText);
					}
				}
			});
		}
		
		function validateDateExp(){
			if($('#bday').attr('class') == 'form-control rule-conflict'){
				$('#bday').removeClass('rule-conflict');
			}
			var day = $('#bday').val();
			var month = $('#bmonth').val();
			var year = $('#byear').val();
			var yearEn =  $('#byear').val() - 543;
			if(day !='' && month != '' && year != ''){
				if((day == 29 || day == 30 || day == 31) && month == 2){
					var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
					var firstDate = new Date(yearEn,01,01);
					var secondDate = new Date(yearEn,12,31);
					var diffDays = Math.round(Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay)))+1;
					if(diffDays == 365 || (day == 30 || day == 31)){
						$('#bday').find('option')[0].selected = true;
						$('#bday').addClass('form-control rule-conflict');
					}
				}else if((day == 31) && (month == 4 || month == 6 || month == 9 || month == 11)){
					$('#bday').find('option')[0].selected = true;
					$('#bday').addClass('form-control rule-conflict');
				}
			}
		}
		
	</script>
	<!--<script src="${contextPath}/datepicker/js/bootstrap-datepicker.js"></script>-->
</body>
</html>