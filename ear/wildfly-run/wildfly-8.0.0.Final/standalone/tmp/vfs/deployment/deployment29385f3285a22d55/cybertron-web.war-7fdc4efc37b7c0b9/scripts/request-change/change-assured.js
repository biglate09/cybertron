$(document).ready(function(){
	$.each(TITLE,function(i,t){
		$('<option value="'+t.titleCode+'">'+t.titleName+'</option>').appendTo($("#titleName"));
	});
	
	//default
	if($("#titleName").val()==""&&$("#titleName").data("default")!=''){
		$("#titleName").html('<option selected value="">เลือกคำนำหน้า</option>');
		$('<option value="อื่นๆ">อื่นๆ</option>').appendTo($("#titleName"));
		var tDefault = $("input[name='param.titleDesc']").val();
		var titleAbbrName = "";
		var titleName = "";
		$.each(TITLE,function(i,t){
			$('<option value="'+t.titleAbbrName+'"">'+t.titleName+'</option>').appendTo($("#titleName"));
			titleAbbrName = (tDefault == t.titleAbbrName)?t.titleAbbrName:titleAbbrName;
			titleName = (tDefault == t.titleAbbrName)?t.titleName:titleName;
		});
		$("#titleName").val(titleAbbrName);
		$("#titleName").data("value",titleAbbrName);
		$("#titleName").attr("data-value",titleAbbrName);
		
		$("input[name='param.titleDesc']").val(titleName);
	}
	
	$("#titleName").on("change",function(){
		var value = $('#titleName :selected').text();
		if(value == 'อื่นๆ'){
			$('#titleNameOther').prop('readonly', false);
			$('#titleNameOther').prop('class', 'form-control');
			$('#titleNameOther').attr('data-validate-required', 'กรอกคำนำหน้า');
		}else{
			$('#titleNameOther').prop('readonly', true);
			$('#titleNameOther').prop('class', 'form-control');
			$('#titleNameOther').removeAttr('data-validate-required');
			$('#titleNameOther').removeAttr('placeholder');
			
			$("input[name='param.titleDesc']").val(value);
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
	
	$("#form-change-assured").on('submit',function(e){
		e.preventDefault();
	
		var verify = !CSSValidate(this);
		var noChange = true;

		try{
			$.each($("[data-value]"),function(idx,obj){
				obj = $(obj);
				if(obj.data('value')!=obj.val()){
					noChange = false;
				}
			});
	
			if(verify||noChange){
				if(noChange){
					window.scrollTo(0, $(this).offset().top);
					CSSDialog.warn("ข้อมูลไม่มีการเปลี่ยนแปลง");
				}else{
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
				}
			}else{
				CSSDialog.confirm('ยืนยันการยื่นคำขอ').on('confirm', function() {
					$("#form-change-assured")[0].submit();
				});
			}
		}catch(e){
			console.error(e);
		}
	});
});