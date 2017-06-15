$(document).ready(function(){
	
	
	//init province;
	
	
	$.each(PROVINCE,function(i,p){
		$('<option value="'+p.pvCode+'">'+p.pvDesc+'</option>').appendTo($("#select-province"));
	});
	
	$("#select-province").on("change",function(){

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
		$("input[name='param.postcode']").val(selected.data("zip"));
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
	$("#form-change-address").on('submit',function(e){
		var form = $("#form-change-address");
		var fail = false;
		var noChange = true;
		var message = "";
		try{
		$.each($("[data-value]"),function(idx,obj){
			obj = $(obj);
			if(obj.data('value')!=obj.val()){
				noChange = false;
			}
		});

		fail = !CSSValidate(form);
		
		if(noChange||fail){
			if(noChange){
				CSSDialog.warn("ข้อมูลไม่มีการเปลี่ยนแปลง");
			}else if(fail){
				CSSDialog.warn(message)
			}
		}else{
			var tel = $("#phone1").data('value');

			CSSDialog.confirm("<h2>ระบบจะทำการส่งรหัส OTP ไปยังโทรศัพท์มือถือหมายเลข "+mainTelReadOnly+"</h3><br/>กรุณา ยืนยัน หากหมายเลขนี้เป็นหมายเลขโทรศัพท์มือถือของท่าน<br/>หรือ ยกเลิก หากหมายเลขนี้เป็นหมายเลขโทรศัพท์ที่ท่านยกเลิกการใช้บริการหรือไม่ใช่หมายเลขของท่าน")
			.on('confirm',function(){
				$("#form-change-address").off('submit').submit();
				$( "#ajax-loading" ).show();
			})
			.on('cancel',function(){
				CSSDialog.warn("หากท่านต้องการปรับปรุงหมายเลขโทรศัพท์มือถือสำหรับรับรหัส OTP  กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ หมายเลข 0-2207-8888 ทุกวันจันทร์ – วันศุกร์ เวลา 8.00 – 18.00 น.")
				.on('ok',function(){});
			});
		}
		}catch(e){
			console.error(e);
		}
		e.preventDefault();
		return false;

	});
});