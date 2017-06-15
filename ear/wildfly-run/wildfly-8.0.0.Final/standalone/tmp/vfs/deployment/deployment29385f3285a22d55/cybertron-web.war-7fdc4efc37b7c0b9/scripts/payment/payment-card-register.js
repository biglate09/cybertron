$(document).ready(function(){
	
	$(".address-option-block input[name='param.addressType']").on('change',function(e){
		var block=$(this).parents('.address-option-block');
		$(".address-option-block").removeClass("active");
		if($(this).is(":checked")){
			block.addClass("active");
		}
	});
	$(".payment-option-block input[name='param.howto']").on('change',function(e){
		var block=$(this).parents('.payment-option-block');
		$(".payment-option-block").removeClass("active");
		if($(this).is(":checked")){
			block.addClass("active");
		}
	});
	
	//init province;
	$.each(Branches,function(i,p){
		$('<option value="'+p.brnNo+'">'+p.brnNo+' : '+p.brnName+'</option>').appendTo($("#select-branches"));
	});
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
		$.each(SUBDISTRICT[$(this).find("option:selected").attr('data-key')],function(i,p){
			$('<option value="'+p.subDistCode+'" data-zip="'+p.zipCode+'">'+p.subDistDesc+'</option>').appendTo($("#select-sub-district"));
		});
	});
	$("#select-sub-district").on("change",function(){
		var selected = $("#select-sub-district>option:selected");
		$("input[name='param.subDistDesc']").val(selected.text());
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

	$("#addressfull,#select-province,#select-district,#select-sub-district,#zipcode").attr("disabled",true);
	$("#select-branches").attr("disabled",true)

	$("#addressType1").on("click",function(){
		$("#addressfull,#select-province,#select-district,#select-sub-district,#zipcode").attr("disabled",true).val("");
		$("#select-branches").attr("disabled",true);
	});
	$("#addressType2").on("click",function(){
		$("#addressfull,#select-province,#select-district,#select-sub-district,#zipcode").attr("disabled",false);
		$("#select-branches").attr("disabled",true).val(""); 
	});
	$("#addressType3").on("click",function(){
		$("#addressfull,#select-province,#select-district,#select-sub-district,#zipcode").attr("disabled",true).val("");
		$("#select-branches").attr("disabled",false); 
	});
	//----------------------------
	
	$("#form-payment-card").on('submit',function(e){
		var form = $(this);
		var fail = false;
		var noChange = true;
		var message = "";
		var addressType = $("input[name='param.addressType']:checked").val();
		try{
			if(!addressType){
				message += 'เลือก ที่อยู่ที่ต้องการจัดส่ง Payment Card<br/>';
				fail=true;
			}
			if(!$("input[name='param.howto']:checked").val()){
				message +='เลือก วิธีการชำระเบี้ยปัจจุบัน<br/>';
				fail=true;
			}
			if($("input[name='param.mobile']").val()==''){
				message +='กรอกหมายเลขโทรศัพท์ในการรับ SMS แจ้งเตือนการชำระ';
				fail=true;
			}
			if(!message){
				if(addressType==='O'){
					fail = !CSSValidate($('#addressTypeO'));
				}else if(addressType==='B'){
					fail = !CSSValidate($('#addressTypeB'));
				}
			}
			
			if(fail){
				CSSDialog.warn(message)
				
				e.preventDefault();
				return false;
			}else{
				CSSDialog.confirm("ยืนยันการส่งข้อมูลสมัคร Payment Card").on('confirm',function(){
					$("#form-payment-card").off('submit').submit();
					$( "#ajax-loading" ).show();
					setTimeout(function(){

						$( "#ajax-loading" ).hide();
					},10000);
				});
			}
		}catch(e){
			console.error(e);
		}
		e.preventDefault();
		return false;

	});
});