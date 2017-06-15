$(document).ready(function(){
	$("#ocean-care-card").on('click',function(){
		$.ajax({
			url:  ACTIONS_MENU.checkOceanCareCard,
			method: "POST",
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					window.location = $("#contextPath")[0].value+'/secure/ocean/care/card/index.html';
				}else{
					CSSDialog.warn('กรมธรรม์ของท่านไม่ได้สมัครบัตร Ocean Care Card');
					window.location = $("#contextPath")[0].value+'/secure/member/personal/info.html';
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	});
	
	$("#ocean-club").on('click',function(){
		$.ajax({
			url:  ACTIONS_MENU.checkOceanClub,
			method: "POST",
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/member.html';
				}else{
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/index.html';
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	});
	
	$("#ocean-club-member").on('click',function(){
		$.ajax({
			url:  ACTIONS_MENU.checkOceanClub,
			method: "POST",
			cache: false,
			success: function(json){
				if(json.data && json.data.status){
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/member.html';
				}else{
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/preMember.html';
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	});
	
	$("#ocean-club-campaign").on('click',function(){
		$.ajax({
			url:  ACTIONS_MENU.checkOceanClubCampaign,
			method: "POST",
			cache: false,
			success: function(json){
				console.log(json.data);
				if(json.data && json.data.status){		
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/listdiscount.html';
				}else{
					window.location = $("#contextPath")[0].value+'/secure/ocean/club/preMember.html';
				}
			},
			error: function(err){
				CSSDialog.warn('เกิดข้อผิดพลาด');
			}
		});
	});
	
	
});