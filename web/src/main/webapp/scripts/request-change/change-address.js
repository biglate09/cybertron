$(document).ready(function(){
	//init province;
	$.each(PROVINCE,function(i,p){
		$('<option value="'+p.pvCode+'">'+p.pvDesc+'</option>').appendTo($("#select-province"));
	});
	
	$("#select-province").on("change",function(){
		
		var distCode =  "";
		$("#zipcode").val(distCode);
		
		$("input[name='param.povDesc']").val($(this).find("option:selected").text());
		
		$("#select-district").html('<option selected  value="">เลือกอำเภอ</option>');
		$("#select-sub-district").html('<option selected  value="">เลือกตำบล</option>');
		var pvCode = $(this).val();
		$.each(DISTRICT[$(this).val()],function(i,p){
			$('<option value="'+p.distCode+'" data-key="p'+pvCode+'d'+p.distCode+'">'+p.distDesc+'</option>').appendTo($("#select-district"));
		});
	});
	$("#select-district").on("change",function(){
		$("input[name='param.disDesc']").val($(this).find("option:selected").text());
		$("#select-sub-district").html('<option selected  value="">เลือกตำบล</option>');
		$.each(SUBDISTRICT[$(this).find("option:selected").data('key')],function(i,p){
			$('<option value="'+p.subDistCode+'" data-zip="'+p.zipCode+'">'+p.subDistDesc+'</option>').appendTo($("#select-sub-district"));
		});
	});
	$("#select-sub-district").on("change",function(){
		var selected = $("#select-sub-district>option:selected");
		$("input[name='param.subdistrictName']").val(selected.text());
		$("input[name='param.zipcode']").val(selected.data("zip"));
	});
	//default
	if($("#select-province").val()==""&&$("#select-province").data("default")!=''){
		var pDefault = $("input[name='param.povDesc']").val();
		$.each(PROVINCE,function(i,p){
			if(pDefault == p.pvDesc){
				$("#select-province").val(p.pvCode);
				$("#select-province").data("value",p.pvCode);
			}
		});
		var pvCode = $("#select-province").val();
		if(pvCode&&$("#select-district").val()==""&&$("#select-district").data("default")!=''){
			$("#select-district").html('<option selected  value="">เลือกอำเภอ</option>');
			var dDefault = $("input[name='param.disDesc']").val();
			var distCode = "";
			$.each(DISTRICT[pvCode],function(i,p){
				$('<option value="'+p.distCode+'" data-key="p'+pvCode+'d'+p.distCode+'">'+p.distDesc+'</option>').appendTo($("#select-district"));
				distCode = (dDefault == p.distDesc)?p.distCode:distCode;
			});
			$("#select-district").val(distCode);
			$("#select-district").data("value",distCode);
			
			if(distCode&&$($("#select-sub-district").val()==""&&$("#select-sub-district").data("default")!='')){
				$("#select-sub-district").html('<option selected  value="">เลือกตำบล</option>');
				var sDefault = $("input[name='param.subdistrictName']").val();
				var sDistCode = "";
				$.each(SUBDISTRICT['p'+pvCode+'d'+distCode],function(i,p){
					$('<option value="'+p.subDistCode+'" data-zip="'+p.zipCode+'">'+p.subDistDesc+'</option>').appendTo($("#select-sub-district"));
					sDistCode = (sDefault == p.subDistDesc)?p.subDistCode:sDistCode;
				});
				$("#select-sub-district").val(sDistCode);
				$("#select-sub-district").data("value",sDistCode);
				$("#select-sub-district").attr("data-value",sDistCode);
			}
		}
	}

	$('input[type=file]').change(function () {
		if($(this).val() != ''){
			var ext = $(this).val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['gif','png','jpg','jpeg','pdf']) == -1) {
				$(this).val('');
	    		CSSDialog.warn("ประเภทไฟล์ไม่ถูกต้อง");
	    	}
		}
	});
	
	$("#form-change-address").on('submit',function(e){
		e.preventDefault();
	
		var verify = !CSSValidate(this);
		var noChange = true;
		
		try{
			$.each($("[data-default]"),function(idx,obj){
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
						window.scrollTo(0,  $(this).offset().top);
						CSSDialog.warn('กรุณากรอกข้อมูลให้ถูกต้อง');
					}
				}
			}else{
				CSSDialog.confirm('ยืนยันการยื่นคำขอ').on('confirm', function() {
					$("#form-change-address")[0].submit();
				});
			}
		}catch(e){
			console.error(e);
		}
	});
});