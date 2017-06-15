$(document).ready(function(){
	$('#datepicker').datepicker({
	    format: "dd/mm/yyyy",
	    language: "th",
	    daysOfWeekHighlighted: "0,6",
	    autoclose: true,
	    maxViewMode: 3
	});
	$("#registerBtn").on('click',function(e){
		e.preventDefault();
		$( "#ajax-loading" ).show();
		var form = $("#form-signup");
		var verify = CSSValidate(form);
		
		if(!verify){
			$( "#ajax-loading" ).hide();
			var firstFieldInValid = $('#'+fieldInValid[0]);
			firstFieldInValid.focus();
			/*firstFieldInValid.on('focus',function(){
				$(this).removeClass('rule-conflict').attr('placeholder',$(this).data('placeholder'));
				$(this).unbind('focus');
			});*/
			firstFieldInValid.on('click',function(){
				$(this).removeClass('rule-conflict').attr('placeholder',$(this).data('placeholder'));
				$(this).unbind('focus');
			});
			fieldInValid = [];
			return;
		}
		confirmSignup();
	});
	$("#userId").on('change',function(e){
		validateEmailOrCardid();
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
	
	$("#reloadcaptcha").on('click',function(){
		$("#img-captcha").attr("src",$("#img-captcha").attr("src")+'?'+new Date().getTime());
	});
	
	$("#reloadcaptcha").hover(
		function(){
			$( this )[0].style.border = "1px solid #FFFFFF";
		}, function() {
				$( this )[0].style.border = "";
			}
	);
});
function confirmSignup(){
	/*CSSDialog
		.confirm('<h1>ยืนยัน</h1><p>เพื่อลงทะเบียน ใช้งานระบบ OceanLife iService</p>')
		.on('confirm',clickConfirmBtn);*/
	CSSDialog.confirm(POLICY_CONTENT,'signup-dialog').on('confirm', function(){
		$('.modal-content').removeClass('signup-dialog');
		clickConfirmBtn();
	});
	
	//ปรับความกว้าง/สูง popup policy confirm
	//$('#divPolicy').attr('style', 'height: '+(window.screen.height-280)+'px; overflow-y: auto;');
	
	$('.modal-footer').find('button')[1].disabled = true;
	$('#divPolicy').scroll(function() {
	   if(($('#divPolicy').scrollTop()+$('#divPolicy').height() + 10) >= $('#divPolicy')[0].scrollHeight) {
		   $('.modal-footer').find('button')[1].disabled = false;
	   }
	});
}
function clickConfirmBtn(){
	var form = $("#form-signup").serialize();
	
	$.ajax({
		  method: "POST",
		url : ACTIONS.confirmsignup,
		data : form,
		success: function(json){
			if(json.data && json.data.success){
				
				if($("input[name='signup.confirmMode']")[1].checked){
					CSSDialog.alert('บริษัทฯ ได้จัดส่งลิงค์สำหรับยืนยันการสมัครบริการ<br/>OceanLife iService ไปยังอีเมล '+json.data.email+'<br/>กรุณากดลิงค์ในอีเมลเพื่อยืนยันการสมัคร').on('ok', 
						function(){
							window.location = ACTIONS.home;
						}
					);
				}else{
					CSSDialog.alert('กรุณารอรับ OTP ทาง SMS สำหรับยืนยันการสมัครบริการ OceanLife iService').on('ok', 
							function(){
								$( "#ajax-loading" ).show();
								window.location = ACTIONS.otp+('?backToActivatePage=false');
							}
						);
				}
			}else{
				//clear captcha
				$("#img-captcha").attr("src",$("#img-captcha").attr("src")+'?'+new Date().getTime());
				$("#captchaCode").val("");
				if(json.data && json.data.isDuplicateUser){
					CSSDialog.warn('<h1>ขออภัย</h1><p>อีเมล หรือ รหัสเลขบัตรประชาชนของท่านเคยลงทะเบียนแล้ว</p>');
				}else if(json.data && json.data.isDuplicateEmailOrTelno){
					CSSDialog.warn('<h1>ขออภัย</h1><p>อีเมล หรือ หมายเลขโทรศัพท์ของท่านเคยลงทะเบียนแล้ว</p>');
				}else if(json.data && json.data.isHuman){
					CSSDialog.warn('<h1>ขออภัย</h1><p>กรอก Security Code ไม่ถูกต้อง</p>');
				}else{
					CSSDialog.warn('<h1>ขออภัย ข้อมูลของท่านไม่ตรงกับฐานข้อมูล</h1><p>กรุณาตรวจสอบข้อมูลอีกครั้ง</p><p>หรือติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวันจันทร์ – วันศุกร์ เวลา 8.00 – 18.00 น.</p><p>วันเสาร์ เวลา 8.00 – 17.00 น.</p>');
				}
			}
		},
		error: function(err){
			
		},
		complete: function(data){
			
		}
	});
}
function clearForm(){
	$("#form-signup")
	.find('input[type="text"],input[type="password"]')
	.each(function(index, obj){$(obj).val("");});
}
validateMatch=function(){
	if($("#password").val() == ''){
		$("#confirm-password").val('');
	}
	return $("#password").val() == $("#confirm-password").val();
}
validateMatchIdcard=function(){
	var userIdField = $("#userId");
	if(validateCitizenid(userIdField)){
		return userIdField.val() == $("#cardNumber").val();
	}
	return true;
}
validateEmailOrCardid=function(){
	var el = $("#userId");
	if(el.val() == ''){
		return true;
	}else if(el.val()){
		var emailMath = validateEmail(el);
		var idCardMath = validateCitizenid(el);
		if(emailMath){
			$("#email").val(el.val());
		}else if(idCardMath){
			$("#cardNumber").val(el.val());
		}
		return emailMath||idCardMath;
	}
	return false;
}
validateMinLengthUser=function(el){
	el = $(el);
	if(el.val()==''||(el.val().length>=6 && el.val().length<=50)){
		return true;
	}
	return false;
}
validateMinLengthPassword=function(el){
	el = $(el);
	if(el.val()==''||(el.val().length>=8 )){
		return true;
	}
	return false;
}
validateEmailDependCombo = function(el){
	if($("#confirm-mail").is(":checked")&&$(el).val()=="")return false;
	else return validateEmail(el);
};

validateCaptcha = function(el){
	var params = {};
	params['params.captchaTimeStamp'] = SESSION.captchaTimeStamp;
	params['params.captchaCode'] = $('#captchaCode').val();
	var res = $.ajax({	
		mothod : 'get',
		data : params,
		async : false,
		url : "/cybertron/pub/page/isvalidcaptcha.html"
	}).responseText;
	return JSON.parse(res).data;
}

validateDiffPassword = function(el){
	//var diffMedium =  $('div.password-diff.medium').length;
	var diffStrong =  $('div.password-diff.strong').length;
	return (diffStrong == 1);
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