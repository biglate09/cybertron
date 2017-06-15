$("#confirmOTP").on('click', function() {
	if (CSSValidate($("#confirm-form"))) {
		//CSSDialog.confirm('<h1>ยืนยันการแก้ไขข้อมูล</h1>').on('confirm', function() {
			$.ajax({
				  method: "POST",
				url : ACTIONS.submit,
				data : $("#confirm-form").serialize(),
				success : function(result) {
					var data = result.data;
					if (data&&data.SUCCESS) {
						var msg = data.MESSAGE || 'บันทึกการแก้ไขข้อมูลของท่านเรียบร้อยแล้ว';
						CSSDialog.alert('บันทึกการแก้ไขข้อมูลของท่านเรียบร้อยแล้ว').on('ok', function(e) {
							window.location = ACTIONS.back;
						});
					} else if (data&&data.WARNING) {
						CSSDialog.alert(data.WARNING);
						//CSSDialog.warn('<h1>' + data.WARNING + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>')
					} else{
						CSSDialog.warn('<h1>' + "เกิดข้อผิดพลาด" + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
					}
				}
			});

		//});
	}

});
$("#resetOTP").on('click', function() {
	CSSDialog.confirm('ต้องการรหัส OTP ใหม่').on('confirm', function() {
		$.ajax({
			  method: "POST",
			url : ACTIONS.reset,
			data : $("#confirm-form").serialize(),
			success : function(result) {
				var data = result.data;
				if (data&&data.SUCCESS) {
					$("input[name='otp.token']").val(data.token);
					$("input[name='otp.ref']").val(data.ref);
				} else if (data&&data.WARNING) {
					CSSDialog.warn('<h1>' + data.WARNING + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>')
				} else{
					CSSDialog.warn('<h1>' + "ไม่สามารถขอรหัส OTP ใหม่ได้" + '</h1><p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 02 207 8888</p><p>เปิดทำการทุกวัน วันจันทร์-วันศุกร์</p><p>เวลา 8.00-18.00 น.</p>');
				}
			}
		});
	});
});
$(document).ready(function(e){
	
CSSDialog.alert('กรุณารอรับ OTP ทาง SMS สำหรับยืนยันการเปลี่ยนแปลงข้อมูล OceanLife iService');
});