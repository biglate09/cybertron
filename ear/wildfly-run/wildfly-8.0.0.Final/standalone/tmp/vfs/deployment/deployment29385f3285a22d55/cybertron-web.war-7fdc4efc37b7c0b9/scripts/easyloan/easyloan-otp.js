$(document).ready(function(){
	$('#btn_confirm').on('click',function(e){
		var params = {};
		params['params.telNo'] = $('#params\\.telNo').val();
		params['params.refNo'] = $('#params\\.refNo').val();
		params['params.otp'] = $('#params\\.otp').val();
		params['params.policyNo'] = $('#params\\.policyNo').val();
		$.ajax({
			method : "POST",
			url    : ACTIONS.validateOtp,
			data   : $.param(params),
            async  : true,
			success: function(result){
				var res = result.data;
				if(res.PROCESS){
					window.location = ACTIONS.successOtp;
				}else{
					CSSDialog.alert("รหัสยืนยัน OTP ไม่ถูกต้อง กรุณาตรวจสอบอีกครั้ง");
				}
			},
			error: function(err){},
			complete: function(data){}
		});
		
	});
	
	$('#btn_back').on('click',function(e){
		window.location = ACTIONS.back;
	});
	
	$('#btn_reset').on('click',function(e){
		CSSDialog.confirm("ยืนยันการขอรหัสใหม่ OTP").on('confirm',function(){
			var params = {};
			params['params.telNo'] = $('#params\\.telNo').val();
			params['params.refNo'] = $('#params\\.refNo').val();
			params['params.otp'] = $('#params\\.otp').val();
			params['params.policyNo'] = $('#params\\.policyNo').val();
			$.ajax({
				method : "POST",
				url    : ACTIONS.resetOtp,
				data   : $.param(params),
	            async  : true,
				success: function(result){
					var res = result.data;
					if(res.PROCESS){
						window.location = ACTIONS.confOtp;
					}else{
						CSSDialog.alert("ขอรหัสใหม่ OTP เกิดข้อผิดพลาดกรุณาลองใหม่อีกครั้ง");
					}
				},
				error: function(err){},
				complete: function(data){}
			});
		});
	});
	
});