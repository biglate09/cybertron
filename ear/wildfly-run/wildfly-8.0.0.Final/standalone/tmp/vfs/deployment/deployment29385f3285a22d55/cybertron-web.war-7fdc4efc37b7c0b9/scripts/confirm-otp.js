
$(document).ready(function(){
	$('#telNo').val(REQ_PARAM.telNo);
	$('#refNo').val(REQ_PARAM.refNo);
	
	
	
	$("#form-otp").on('submit',function(e){
		e.preventDefault();
		
		var verify = CSSValidate(this);
		
		if(!verify)return;
		confirmOTP();
		
	});
	
	$("#btn_reset").on('click',function(){
		resetOTP();
	});
	
	$("#btn_back").on('click',function(){
		if(!!REQ_PARAM.backToActivatePage && REQ_PARAM.backToActivatePage == 'true'){
			window.location = '/cybertron/secure/activatedUser.html';
		}else{
			window.location = '/cybertron/pub/page/signup.html';
		}
	});
	
	
});

function confirmOTP(){
	var params = {};
	params['params.refNo'] = REQ_PARAM.refNo;
	params['params.otp'] = $('#otp').val();
	params['params.forgetMethod']  = 'completesignup';
	
	$.ajax({
		  method: "POST",
		data : params,
		url : ACTIONS.processConfirmByOtp,
		success : function(json) {
			if(json.data.success){
				if(json.data.msg != ''){
					CSSDialog.warn(json.data.msg).on('ok',function(){
						window.location = ACTIONS.home;
					});
				}else{
					CSSDialog.alert('<h3>ท่านได้สมัครสมาชิกบริการ OceanLife iService เรียบร้อยแล้ว</h3>')
						.on('ok',function(){
							window.location = ACTIONS.home;
					});
				}
			}else{
				CSSDialog.alert(json.data.msg).on('ok', 
					function(){
						$('#otp').val('');
					}
				);
			}
		},
		error : function(xhr, desc, exceptionobj) {
			if (xhr.responseText != "") {
				alert("ERROR :" + xhr.responseText);
			}
		}
	});
}

function resetOTP(){
	var params = {};
	params['params.tokenId'] = REQ_PARAM.tokenId;
	params['params.telNo'] = REQ_PARAM.telNo;
	params['params.forgetMethod']  = 'completesignup';
	
	CSSDialog.confirm('ต้องการรหัส OTP ใหม่').on('confirm', function() {
		$.ajax({
			  method: "POST",
			data : params,
			url : ACTIONS.processResetOtp,
			success : function(json) {
				if(json.data.success){
					REQ_PARAM.refNo = json.data.refNo;
					$('#refNo').val(json.data.refNo);
				}else if(json.data.warning){
					CSSDialog.warn('<h1>' + json.data.warning + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
				}else{
					CSSDialog.warn('<h1>เกิดข้อผิดพลาด</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
				}
			},
			error : function(xhr, desc, exceptionobj) {
				if (xhr.responseText != "") {
					CSSDialog.warn("ERROR :" + xhr.responseText);
				}
			}
		});
	});
}
