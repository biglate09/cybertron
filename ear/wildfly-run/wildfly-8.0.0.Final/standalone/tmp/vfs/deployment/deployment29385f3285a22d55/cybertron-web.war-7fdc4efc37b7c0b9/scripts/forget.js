$(document).ready(function(){
	
	/*$('input[name=forget]').change(function(){
		$('#btn-submit').removeAttr('disabled');
	})*/
	
	$("#btn-submit").on('click',function(){
		var forget = $("input[name=forget]:checked").val();
		if(undefined == forget){
			CSSDialog.alert('กรุณาเลือกทำรายการที่ต้องการ');
		}else{
			window.location = ACTIONS.confirmByIdCard+"?forgetMethod="+forget;
		}
	});
});