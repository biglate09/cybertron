$(document).ready(function(){	
	$("#report").on('click',function() {		
//		var left  = ($(window).width()/2)-(900/2),
//	    	top   = ($(window).height()/2)-(600/2);
//	    	window.open($("#contextPath")[0].value+'/secure/ocean/care/card/report.html',"_blank", "width=595, height=391, top="+top+", left="+left);
	    	
	    window.open(
	    	actions.report
	    );
	});
	
	function validateEmail(email) {
	  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	  return re.test(email);
	}
	
	$("#send-email").on('click',function() {
		$.ajax({
			url:  actions.checkEmail,
			method: "POST",
			dataType:'json',
			cache: false,
			success: function(json){
				if(json.data && json.data.emailStatus){
					CSSDialog.confirm('<span>Email ผู้รับ : '+json.data.email+'</span>').on('confirm', function() {
						var params = {};
						params['parameter.email'] = json.data.email;
						$.ajax({
							url:  actions.sendEmail,
							method: "POST",
							data:  params,
					        dataType:'json',
							cache: false,
							success: function(json){
								if(json.data && json.data.result){
									CSSDialog.warn("ส่ง Email เรียบร้อยแล้ว");
								}else{
									CSSDialog.warn("เกิดข้อผิดพลาด ไม่สามารถส่ง Email ได้");
								}
							}
						});
					});
				}else{
					CSSDialog.confirm('<span>ขออภัยค่ะ ไม่พบอีเมลในระบบ กรุณาเพิ่มอีเมลในข้อมูลที่อยู่ติดต่อของท่าน โดยทำการคลิกตกลงเพื่อเพิ่มอีเมล</span>').on('confirm', function() {
						window.location = $("#contextPath")[0].value+'/secure/member/personal/edit.html?policyNo='+json.data.policyNo;
					});
				}
			},
			error: function(err){
				window.location = $("#contextPath")[0].value+'/secure/member/personal/info.html';
			},
		});
	});
});