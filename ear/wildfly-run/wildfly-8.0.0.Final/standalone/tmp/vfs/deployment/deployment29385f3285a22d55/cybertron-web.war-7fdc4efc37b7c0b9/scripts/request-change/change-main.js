$(document).ready(function(){

	$("#btnChangePaymentPeriod").on('click', function() {
		$.ajax({
			url:  actions.checkPolicyTypeOrd,
			  method: "POST",
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					window.location = $("#contextPath")[0].value+'/secure/request/change/installment.html';
				}else{
					CSSDialog.warn("ไม่สามารถยื่นคำร้องขอเปลี่ยนงวดการชำระเบี้ยประกันได้ เนื่องจากไม่มีกรมธรรม์มีผลบังคับ");
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	   
	});
	
	$("#btnChangeAssured").on('click', function() {
		var params = {};
    	params['param.selectedRequestType'] = 'PTN02';
		$.ajax({
			url:  actions.checkPolicyTypeAll,
			  method: "POST",
			data:  params,
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					if(json.data.requested){
						CSSDialog.warn("ท่านได้ยื่นคำร้องขอเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกันเรียบร้อยแล้ว");
					}else{
						window.location = $("#contextPath")[0].value+'/secure/request/change/assured.html';
					}
				}else{
					CSSDialog.warn("ไม่สามารถยื่นคำร้องขอเปลี่ยนแปลงแก้ไขชื่อ-นามสกุล ผู้เอาประกันได้ เนื่องจากไม่มีกรมธรรม์มีผลบังคับ");
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	   
	});
	
	$("#btnChangeAddress").on('click', function() {
		var params = {};
    	params['param.selectedRequestType'] = 'PTN03';
		$.ajax({
			url:  actions.checkPolicyTypeAll,
			  method: "POST",
			data:  params,
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					if(json.data.requested){
						CSSDialog.warn("ท่านได้ยื่นคำร้องขอเปลี่ยนแปลงที่อยู่ตามทะเบียนบ้านเรียบร้อยแล้ว");
					}else{
						window.location = $("#contextPath")[0].value+'/secure/request/change/address.html';
					}
				}else{
					CSSDialog.warn("ไม่สามารถยื่นคำร้องขอเปลี่ยนแปลงที่อยู่ตามทะเบียนบ้านได้ เนื่องจากไม่มีกรมธรรม์มีผลบังคับ");
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	   
	});
	
	$("#btnChangeReceive").on('click', function() {
		$.ajax({
			url:  actions.checkPolicyTypeAll,
			  method: "POST",
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					window.location = $("#contextPath")[0].value+'/secure/request/change/receive.html';
				}else{
					CSSDialog.warn("ไม่สามารถยื่นคำร้องขอเปลี่ยนแปลงวิธีรับเงินผลประโยชน์ได้ เนื่องจากไม่มีกรมธรรม์มีผลบังคับ");
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	   
	});
});