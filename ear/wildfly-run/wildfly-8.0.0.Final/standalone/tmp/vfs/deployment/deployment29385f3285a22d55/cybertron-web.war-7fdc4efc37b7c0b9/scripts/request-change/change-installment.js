$(document).ready(function(){
	$.each($('input[type=checkbox][name=policy]'),function(index,object){
		$(object).change(function(e){
		    if ($(this).is(":checked")){
		    	var params = {};
		    	params['param.selectedPolicy'] = $(this).val();
		    	
		    	var mode;
		    	var modeStr = $("input[name='installmentType']:checked").val();
		    	if(modeStr == 'year'){
		    		mode = '12';
		    	}else if(modeStr == 'sixMonth'){
		    		mode = '6';
		    	}else if(modeStr == 'threeMonth'){
		    		mode = '3';
		    	}else if(modeStr == 'month'){
		    		mode = '1';
		    	}
		    	
		    	params['param.selectedPaymentMode'] = mode;
		    	
		    	$.ajax({
		    		  method: "POST",
					url:  actions.changePaymentModeChecking,
					data:  params,
			        dataType:'json',
					cache: false,
					success: function(json){
						if(json.data && json.data.status){
							if(json.data.requested){
								$(e.currentTarget).attr("checked", false);
								CSSDialog.warn("ท่านได้ยื่นคำร้องขอเปลี่ยนงวดการชำระเบี้ยประกันภัย สำหรับกรมธรรม์นี้เรียบร้อยแล้ว");
							}
						}else{
							$(e.currentTarget).attr("checked", false);
							CSSDialog.warn(json.data.message);
						}
					},
					error: function(err){
						CSSDialog.warn('เกิดข้อผิดพลาด');
					}
		    	});
		    }
		});
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
	
	$("#form-change-installment").on('submit',function(e){
		e.preventDefault();
	
		var verify = CSSValidate(this);
	
		if(!verify){
			var fieldSize = $(this).find(".form").size();
			var fieldEmpty = 0;
			$.each($(this).find(".form"),function(index,object){
				if($(object).val() == ''){
					fieldEmpty++;
				}
			});
			
			if(fieldSize == fieldEmpty){
				//CSSDialog.warn('กรุณากรอกข้อมูลให้ถูกต้อง');
				window.scrollTo(0, $(this).offset().top);
			}
		}else{
			var radioValue = $("input[name='installmentType']:checked").val();
			if(typeof(radioValue) !== "undefined" && radioValue !== null){
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
						$("#form-change-installment")[0].submit();
					});
				}else{
					CSSDialog.warn('กรุณาเลือกกรมธรรม์ที่ต้องการเปลี่ยนแปลง');
				}
			}else{
				window.scrollTo(0, $(this).offset().top);
				CSSDialog.warn('กรุณาเลือกงวดการชำระเบี้ยประกันภัย');
			}
		}
	});
	
	var fileArr = [];

	$('#linkFile1').click(function(){
	    $('#file1').click();
	});
	var span = $('#file1Name');
	$('#file1').fileupload({
	    add: function (e, data) {
	    	var ext = data.files[0].name.split('.').pop().toLowerCase();
	    	if($.inArray(ext, ['gif','png','jpg','jpeg','pdf']) == -1) {
	    		CSSDialog.warn("ประเภทไฟล์ไม่ถูกต้อง");
	    	}else{
	    		$(span).html(data.files[0].name);
		    	fileArr.push(data);
	    	}
	    },
	    fail:function(e, data){
	        data.context.addClass('error');
	    }
	});
	$('#linkFile2').click(function(){
	    $('#file2').click();
	});
	var span2 = $('#file2Name');
	$('#file2').fileupload({
	    add: function (e, data) {
	    	var ext = data.files[0].name.split('.').pop().toLowerCase();
	    	if($.inArray(ext, ['gif','png','jpg','jpeg','pdf']) == -1) {
	    		CSSDialog.warn("ประเภทไฟล์ไม่ถูกต้อง");
	    	}else{
	    		$(span2).html(data.files[0].name);
		    	fileArr.push(data);
	    	}
	    },
	    fail:function(e, data){
	        data.context.addClass('error');
	    }
	});

	$('#linkFile3').click(function(){
	    $('#file3').click();
	});
	var span3 = $('#file3Name');
	$('#file3').fileupload({
	    add: function (e, data) {
	    	var ext = data.files[0].name.split('.').pop().toLowerCase();
	    	if($.inArray(ext, ['gif','png','jpg','jpeg','pdf']) == -1) {
	    		CSSDialog.warn("ประเภทไฟล์ไม่ถูกต้อง");
	    	}else{
	    		$(span3).html(data.files[0].name);
		    	fileArr.push(data);
	    	}
	    },
	    fail:function(e, data){
	        data.context.addClass('error');
	    }
	});

	$("#btnSave").on('click', function () {
		var verify = CSSValidate($("#form-change-installment"));
		if(verify){
			CSSDialog.confirm('ยืนยันการยื่นคำขอ').on('confirm', function() {
				var firstData;
				for (i = 0; i < fileArr.length; i++) { 
					firstData = fileArr[i];
				    break;
				}
				if(fileArr.length > 1){
					for (i = 1; i < fileArr.length; i++) { 
					    data = fileArr[i];
					    firstData.files[i] = data.files[0];
					}
				}
				firstData.submit();
				window.location =  "/cybertron/secure/request/change/choice.html";
	
			});
		}
	});

	function formatFileSize(bytes) {
	    if (typeof bytes !== 'number') {
	        return '';
	    }

	    if (bytes >= 1000000000) {
	        return (bytes / 1000000000).toFixed(2) + ' GB';
	    }

	    if (bytes >= 1000000) {
	        return (bytes / 1000000).toFixed(2) + ' MB';
	    }

	    return (bytes / 1000).toFixed(2) + ' KB';
	}

});