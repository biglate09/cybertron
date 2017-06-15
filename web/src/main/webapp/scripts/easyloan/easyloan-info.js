$(document).ready(function(){
	$('#pronounment-content').attr('style', 'height: '+(window.screen.height-200)+'px; overflow-y: auto;');
	$('#easyloanSaveBtn').on('click',function(e){
		e.preventDefault();
		
		var chk = $('#chkClaimChannel')[0].checked;
		if(!$('#params\\.loanAmount').val() || !$('#params\\.accountName').val() || !$('#params\\.accountNo').val() 
				|| !$('#params\\.bankBranch').val() || !$('#file1').val() || (chk && !$('#file4').val())){
			CSSDialog.alert("กรุณาระบุข้อมูลให้ครบถ้วน");
			return;
		}
		CSSDialog.confirm($('#pronounment').html().trim(),'signup-dialog').on('confirm', function(){
			$('.modal-content').removeClass('signup-dialog');
			$('.bank-group-class').each(function(i,o){
				$(o).attr('disabled',false);
			});
			$.ajax({
				method: "POST",
				url : ACTIONS.save,
				data : new FormData($("#form-easy-loan")[0]),
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				cache: false,
				success : function(result) {
					var data = result.data;
					if (data && data.PROCESS && !data.ERROR_MESSAGE) {
						var params = {};
						params['params.policyNo'] = $('#params\\.policyNo').val();
						var res = callAjax(ACTIONS.genOtp,params,false);
						if(res.PROCESS){
							window.location = ACTIONS.confOtp;
						}else{
							CSSDialog.alert("Generate OTP เกิดข้อผิดพลาด");
						}
					} else{
						if(data.ERROR_MESSAGE){
							CSSDialog.alert(data.ERROR_MESSAGE);
						}else{
							CSSDialog.alert("บันทึกข้อมูลเกิดข้อผิดพลาด กรุณาลองใหม่อีกครั้ง");
						}
					}
				},
				error: function(err){
					CSSDialog.alert(err);
				},
			});
		});
	});
	
	$.each(BANK,function(i,b){
		$('<option value="'+b.bankNameThai+'">'+b.bankNameThai+'</option>').appendTo($("#params\\.bankName"));
	});
	
	if(ClaimChannel && ClaimChannel != 'undefined'){
		$('#chkClaimChannel')[0].checked = false;
		
		$('#params\\.bankName').val(ClaimChannel.bankNameThai);
		$('#params\\.accountName').val(ClaimChannel.acName);
		$('#params\\.accountNo').val(ClaimChannel.acNo?ClaimChannel.acNo.replace(/\D/g,''):'');
		$('#params\\.bankBranch').val(ClaimChannel.brnName);
		$('.bank-group-class').each(function(i,o){
			$(o).attr('disabled',true);
		});
	}else{
		$('#chkClaimChannel')[0].checked = true;
		$('#chkClaimChannel')[0].disabled = true;
		$('.bank-group-class').each(function(i,o){
			$(o).attr('disabled',false).val('');
		});
	}
	
	$('#chkClaimChannel').on('click',function(e){
		var chk = this.checked;
		$('.bank-group-class').each(function(i,o){
			$(o).attr('disabled',!chk);
		});
		if(!chk && ClaimChannel && ClaimChannel != 'undefined'){
			$('#params\\.bankName').val(ClaimChannel.bankNameThai);
			$('#params\\.accountName').val(ClaimChannel.acName);
			$('#params\\.accountNo').val(ClaimChannel.acNo?ClaimChannel.acNo.replace(/\D/g,''):'');
			$('#params\\.bankBranch').val(ClaimChannel.brnName);
			$('#file4').val('');
		}
	});
	
	function callAjax(url,params,async){
		var res = null;
		$.ajax({
			method : "POST",
			url    : url,
			data   : $.param(params),
            async  : async,
			success: function(result){
				res = result.data;
			},
			error: function(err){},
			complete: function(data){}
		});
		return res;
	}
	
	$("#params\\.loanAmount").on('keydown', function(e){
		validateNumber(e);
	});
	
	$("#params\\.accountNo").on('keydown', function(e){
		validateNumber(e);
	});
	
	$("#params\\.loanAmount").on('blur', function(e){
		if(this.value){
			var loanAmountMax = $('#loanAmountMax').html() == '-'?0:parseFloat($('#loanAmountMax').html().replace(/(\.\d{2})$/,'').replace(/(\D)/g,''));
			var loanAmount = parseFloat(this.value);
			if(loanAmount > loanAmountMax){
				this.value = loanAmountMax.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
				loanAmount = loanAmountMax;
				CSSDialog.alert("จำนวนเงินที่ต้องการกู้ต้องไม่มากกว่ามูลค่าเงินสดที่สามารถกู้ได้");
			}else{
				this.value = loanAmount.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
			}
			var loanDuty = $('#loanDuty').html() == '-'?0:parseFloat($('#loanDuty').html());
			var loanNet = (loanAmount - loanDuty);
			if(loanNet < 4995){
				CSSDialog.alert("จำนวนเงินกู้ น้อยกว่า 5000 บาท โปรดระบุอีกครั้ง");
				loanAmount = loanAmountMax;
				this.value = loanAmount.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
			}
			var net = (loanAmount - loanDuty).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
			$('#loanNet').html(net);
		}
	});
	
	$("#params\\.loanAmount").on('focus', function(e){
		if(this.value){
			this.value = this.value.replace(/(\.\d{2})$/,'').replace(/(\D)/g,'');
		}
	});

	if($("#params\\.loanAmount").val() && $('#loanDuty').html()){
		var loanAmount = $("#params\\.loanAmount").val().replace(/(\.\d{2})$/,'').replace(/(\D)/g,'');
		var loanDuty = $('#loanDuty').html() == '-'?0:parseFloat($('#loanDuty').html());
		var loanNet = (loanAmount - loanDuty).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
		$('#loanNet').html(loanNet);
	}
	
	function validateNumber(e){
		// Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 190]) !== -1 ||
             // Allow: Ctrl+A, Command+A
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
             // Allow: home, end, left, right, down, up
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
	}
	
//	$('#params\\.bankName').on('change',function(e){
//		if(this.value){
//			$('#params\\.bankNameText').val($('#params\\.bankName [value='+this.value+']').text());
//		}else{
//			$('#params\\.bankNameText').val('');
//		}
//	});
	
//	$("#bank").on("change",function(){
//		$('#bankNo').val('');
//		var value = $('#bank :selected').val();
//		if(value == '33' || value == '30'){ // อาคารสงเคราะห์ 12 หลัก, ออมสิน 12 หลัก
//			$('#bankNo').attr('maxlength', '12');
//		}else if(value == '69'){ //เกียรนาคิน 14 หลัก
//			$('#bankNo').attr('maxlength', '14');
//		}else{
//			$('#bankNo').attr('maxlength', '10');
//		}
//		$("input[name='param.bankDesc']").val($(this).find("option:selected").text());
//	});
	
});