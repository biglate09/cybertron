$(document).ready(function(){
	$.each($('input[type=checkbox][name=policy]'),function(index,object){
		$(object).change(function(e){
		    if ($(this).is(":checked")){
		    	var params = {};
		    	params['param.selectedPolicy'] = $(this).val();
		    	
		    	$.ajax({
					url:  actions.changeReceiveChecking,
					data:  params,
					  method: "POST",
			        dataType:'json',
					cache: false,
					success: function(json){
						if(json.data && json.data.requested){
							$(e.currentTarget).attr("checked", false);
							CSSDialog.warn("ท่านได้ยื่นคำร้องขอเปลี่ยนแปลงวิธีรับเงินผลประโยชน์ สำหรับกรมธรรม์นี้เรียบร้อยแล้ว");	
						}
					},
					error: function(err){
						CSSDialog.warn('เกิดข้อผิดพลาด');
					}
		    	});
		    }
		});
	});
	
	$.each(BANK,function(i,b){
		$('<option value="'+b.bankCode+'">'+b.bankNameThai+'</option>').appendTo($("#bank"));
	});
	
	$("#bank").on("change",function(){
		$('#bankNo').val('');
		var value = $('#bank :selected').val();
		if(value == '33' || value == '30'){ // อาคารสงเคราะห์ 12 หลัก, ออมสิน 12 หลัก
			$('#bankNo').attr('maxlength', '12');
		}else if(value == '69'){ //เกียรนาคิน 14 หลัก
			$('#bankNo').attr('maxlength', '14');
		}else{
			$('#bankNo').attr('maxlength', '10');
		}
		$("input[name='param.bankDesc']").val($(this).find("option:selected").text());
	});
	
	$('input[name="param.accountNo"]').keyup(function(e) {
		if (/\D/g.test(this.value)){
			this.value = this.value.replace(/\D/g, '');
		}
	});
	
	$('input[type=file]').change(function () {
		if($(this).val() != ''){
			var ext = $(this).val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['gif','png','jpg','jpeg','pdf']) == -1) {
				$(this).val('');
	    		CSSDialog.warn("ประเภทไฟล์ไม่ถูกต้อง");
	    	}
		}
	});
	
	$("#form-change-receive").on('submit',function(e){
		e.preventDefault();
	
		var verify = CSSValidate(this);
	
		if(!verify){
			window.scrollTo(0, $(this).offset().top);
			var fieldSize = $(this).find(".form").size();
			var fieldEmpty = 0;
			$.each($(this).find(".form"),function(index,object){
				if($(object).val() == ''){
					fieldEmpty++;
				}
			});
			
			if(fieldSize == fieldEmpty){
				window.scrollTo(0, $(this).offset().top);
				CSSDialog.warn('กรุณากรอกข้อมูลให้ถูกต้อง');
			}
		}else{
			var valArr = [];
			var cboxes = document.getElementsByName("policy");
		    var length = cboxes.length;
		    for (var i=0; i<length; i++) {
		        if(cboxes[i].checked){
		        	valArr.push(cboxes[i].value)
		        }
		    }
			if(valArr.length > 0){
				CSSDialog.confirm('ยืนยันการยื่นคำขอ').on('confirm', function() {
					$("#form-change-receive")[0].submit();
				});
			}else{
				CSSDialog.warn('กรุณาเลือกกรมธรรม์ที่ต้องการเปลี่ยนแปลง');
			}
		}
	});
});