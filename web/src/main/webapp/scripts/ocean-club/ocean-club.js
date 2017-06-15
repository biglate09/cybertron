$(document).ready(function(){	
	$("input[name='c1']").click(function () {
		if($(this).val() === '4'){
			$("input[value='1']").removeAttr('checked');
			$("input[value='2']").removeAttr('checked');
			$("input[value='3']").removeAttr('checked');
		}else{
			$("input[value='4']").removeAttr('checked');
		}
	});
	
	$("input[name='c2']").click(function () {
		if($(this).val() === '7'){
			$("input[value='5']").removeAttr('checked');
			$("input[value='6']").removeAttr('checked');
		}else{
			$("input[value='7']").removeAttr('checked');
		}
	});
	
	$("input[name='c3']").click(function () {
		if($(this).val() === '10'){
			$("input[value='8']").removeAttr('checked');
			$("input[value='9']").removeAttr('checked');
		}else{
			$("input[value='10']").removeAttr('checked');
		}
	});
	
	$("input[name='c4']").click(function () {
//	    $('#subQ4').css('display', ($(this).val() === 'a2') ? 'block':'none');
		if($(this).val() === '12'){
			$('#subQ4 input').attr('disabled', 'disabled');
			$('input[name=sc4]').removeAttr('checked');
			$('#sc4a10other').val("");
		}else{
			$('#subQ4 input').removeAttr('disabled');
			$('#sc4a10other').attr('readonly', 'readonly');
		}
	});
	
	$("input[name='sc4']").change(function() {
	     if(this.checked && $(this).val() === '22') {
	    	$('#sc4a10other').removeAttr('readonly');
	     }
	     if(!this.checked && $(this).val() === '22') {
	    	$('#sc4a10other').attr('readonly', 'readonly');
	    	$('#sc4a10other').val("");
	     }
	});
	
	$("input[name='c5']").click(function () {
		if($(this).val() === '22'){
			$('#subQ5 input').attr('disabled', 'disabled');
			$('input[name=sc5_1]').removeAttr('checked');
			$('input[name=sc5_2]').removeAttr('checked');
			$('#sc5a25other').val("");
		}else{
			$('#subQ5 input').removeAttr('disabled');
			$('#sc5a25other').attr('readonly', 'readonly');
		}
	});
	
	$("input[name='sc5_2']").change(function() {
	     if(this.checked && $(this).val() === '54') {
	    	$('#sc5a25other').removeAttr('readonly');
	     }
	     if(!this.checked && $(this).val() === '54') {
	    	 $('#sc5a25other').attr('readonly', 'readonly');
	    	 $('#sc5a25other').val("");
	     }
	});

	$("input[name='c6']").click(function () {
		if($(this).val() === '53'){
			$('#subQ6 input').attr('disabled', 'disabled');
			$('input[name=sc6]').removeAttr('checked');
			$('#sc6a16other').val("");
		}else{
			$('#subQ6 input').removeAttr('disabled');
			$('#sc6a16other').attr('readonly', 'readonly');
		}
	});

	$("input[name='sc6']").change(function() {
	     if(this.checked && $(this).val() === '72') {
	    	$('#sc6a16other').removeAttr('readonly');
	     }
	     if(!this.checked && $(this).val() === '72') {
	    	$('#sc6a16other').attr('readonly', 'readonly');
	    	$('#sc6a16other').val("");
	     }
	});

	$("input[name='c7']").change(function() {
	     if(this.checked && $(this).val() === '87') {
	    	$('#c7a14other').removeAttr('readonly');
	     }
	     if(!this.checked && $(this).val() === '87') {
	    	$('#c7a14other').attr('readonly', 'readonly');
	    	$('#c7a14other').val("");
	     }
	});

	$("input[name='c8']").change(function() {
	     if(this.checked && $(this).val() === '107') {
	    	$('#c8a20other').removeAttr('readonly');
	     }
	     if(!this.checked && $(this).val() === '107') {
	    	$('#c8a20other').attr('readonly', 'readonly');
	    	$('#c8a20other').val("");
	     }
	});
	
	$("#form-ocean-club").on('submit',function(e){
		var formname = $("#form-ocean-club").attr("name");
		e.preventDefault();
		$('#fs1').css("background-color", "");
		$('#fs2').css("background-color", "");
		$('#fs3').css("background-color", "");
		$('#fs4').css("background-color", "");
		$('#fs5').css("background-color", "");
		$('#fs6').css("background-color", "");
		$('#fs7').css("background-color", "");
		$('#fs8').css("background-color", "");
		$('#subQ4').css("background-color", "");
		$('#subQ5').css("background-color", "");
		$('#subQ6').css("background-color", "");
		var validate = 0;
		var g1 = $('.ocRegister[name=c1]:checked').length;
		var g2 = $('.ocRegister[name=c2]:checked').length;
	    var g3 = $('.ocRegister[name=c3]:checked').length;
	    var g4 = $('.ocRegister[name=c4]:checked').length;
	    var g5 = $('.ocRegister[name=c5]:checked').length;
		var g6 = $('.ocRegister[name=c6]:checked').length;
	    var g7 = $('.ocRegister[name=c7]:checked').length;
	    var g8 = $('.ocRegister[name=c8]:checked').length;
	    
	    if(g1 == 0){
	    	$('.ocRegister[name=c1]').focus();
	    	$('#fs1').css("background-color", "#FFCCCC");
	    	validate++;
	    }else if(g2 == 0){
	    	$('.ocRegister[name=c2]').focus();
	    	$('#fs2').css("background-color", "#FFCCCC");
	    	validate++;
	    }else if(g3 == 0){
	    	$('.ocRegister[name=c3]').focus();
	    	$('#fs3').css("background-color", "#FFCCCC");
	    	validate++;
	    }else if(g4 == 0){
	    	$('.ocRegister[name=c4]').focus();
	    	$('#fs4').css("background-color", "#FFCCCC");
	    	validate++;
	    }else if(g5 == 0){
	    	$('.ocRegister[name=c5]').focus();
	    	var sg4 = $('.ocRegister[name=sc4]:checked').length;
	    	if(sg4 > 0 || $("input[name='c4']:checked").val() === '12'){
	    		$('#fs5').css("background-color", "#FFCCCC");
	    	}
	    	validate++;
	    }else if(g6 == 0){
	    	$('.ocRegister[name=c6]').focus();
	    	var sg5 = $('.ocRegister[name=sc5_1]:checked').length;
	     	var sg5_2 = $('.ocRegister[name=sc5_2]:checked').length;
	    	if((sg5 > 0 && sg5_2 > 0) || $("input[name='c5']:checked").val() === '23'){
	    		$('#fs6').css("background-color", "#FFCCCC");
	    	}
	    	validate++;
	    }else if(g7 == 0){
	    	$('.ocRegister[name=c7]').focus();
	    	var sg6 = $('.ocRegister[name=sc6]:checked').length;
	    	if(sg6 > 0 || $("input[name='c6']:checked").val() === '55'){
	    		$('#fs7').css("background-color", "#FFCCCC");
	    	}
	    	validate++;
	    }else if(g8 == 0){
	    	$('.ocRegister[name=c8]').focus();
	    	$('#fs8').css("background-color", "#FFCCCC");
	    	validate++;
	    }
	    
	    if($("input[name='c4']").is(':checked') && $("input[name='c4']:checked").val() === '11'){
	    	var sg4 = $('.ocRegister[name=sc4]:checked').length;
	    	if(sg4 == 0){
	    		$('.ocRegister[name=sc4]').focus();
	    		$('#subQ4').css("background-color",  "#FFCCCC");
	    		validate++;
	    	}
	    	
	    	$('input[name="sc4"]:checked').each(function() {
    		   if(this.value == '22'){
    			   var c4other = $('#sc4a10other').val();
    		    	if(c4other.length == 0){
    		    		$('#sc4a10other').css("background-color", "#FFCCCC");
    		    		$('#sc4a10other').focus();
    		    		validate++;
    		    	}else{
    		    		$('#sc4a10other').css("background-color", "");
    		    	}
    		   }
    		});
	    }else{
	    	$('#sc4a10other').css("background-color", "");
	    }
	    
	    if($("input[name='c5']").is(':checked') && $("input[name='c5']:checked").val() === '24'){
	    	var sg5 = $('.ocRegister[name=sc5_1]:checked').length;
	    	var sg5_2 = $('.ocRegister[name=sc5_2]:checked').length;
	    	if(sg5 == 0){
	    		$('.ocRegister[name=sc5_1]').focus();
	    		$('#subQ5').css("background-color", "#FFCCCC");
	    		validate++;
	    	}else if(sg5_2 == 0){
	    		$('.ocRegister[name=sc5_2]').focus();
	    		$('#subQ5').css("background-color", "#FFCCCC");
	    		validate++;
	    	}
	    	
	    	$('input[name="sc5_2"]:checked').each(function() {
    		   if(this.value == '54'){
    			   var c4other = $('#sc5a25other').val();
    		    	if(c4other.length == 0){
    		    		$('#sc5a25other').css("background-color", "#FFCCCC");
    		    		$('#sc5a25other').focus();
    		    		validate++;
    		    	}else{
    		    		$('#sc5a25other').css("background-color", "");
    		    	}
    		   }
    		});
	    }else{
	    	$('#sc5a25other').css("background-color", "");
	    }
	    
	    if($("input[name='c6']").is(':checked') && $("input[name='c6']:checked").val() === '56'){
	    	var sg6 = $('.ocRegister[name=sc6]:checked').length;
	    	if(sg6 == 0){
	    		$('.ocRegister[name=sc6]').focus();
	    		$('#subQ6').css("background-color", "#FFCCCC");
	    		validate++;
	    	}
	    	
	    	$('input[name="sc6"]:checked').each(function() {
    		   if(this.value == '72'){
    			   var c4other = $('#sc6a16other').val();
    		    	if(c4other.length == 0){
    		    		$('#sc6a16other').css("background-color", "#FFCCCC");
    		    		$('#sc6a16other').focus();
    		    		validate++;
    		    	}else{
    		    		$('#sc6a16other').css("background-color", "");
    		    	}
    		   }
    		});
	    }else{
	    	$('#sc6a16other').css("background-color", "");
	    }
	    
	    if($("input[name='c7']").is(':checked')){
	    	$('input[name="c7"]:checked').each(function() {
	    		if(this.value == '87'){
	    			var c4other = $('#c7a14other').val();
    		    	if(c4other.length == 0){
    		    		$('#c7a14other').css("background-color", "#FFCCCC");
    		    		$('#c7a14other').focus();
    		    		validate++;
    		    	}else{
    		    		$('#c7a14other').css("background-color", "");
    		    	}
	    		}
	    	});
	    }else{
	    	$('#c7a14other').css("background-color", "");
	    }
	    
	    if($("input[name='c8']").is(':checked')){
	    	$('input[name="c8"]:checked').each(function() {
	    		if(this.value == '107'){
	    			var c4other = $('#c8a20other').val();
    		    	if(c4other.length == 0){
    		    		$('#c8a20other').css("background-color", "#FFCCCC");
    		    		$('#c8a20other').focus();
    		    		validate++;
    		    	}else{
    		    		$('#c8a20other').css("background-color", "");
    		    	}
	    		}
	    	});
	    }else{
	    	$('#c8a20other').css("background-color", "");
	    }

        if(validate == 0){
            if(formname == 'editmember'){
                var msg = "ยืนยันการแก้ไขความชอบสำหรับสมาชิก Ocean Club";
            }else {
                var msg = "ยืนยันการสมัครสมาชิก Ocean Club";
            }
            CSSDialog.confirm(msg).on('confirm', function () {
                $("#form-ocean-club")[0].submit();
            });
        }
		
	});
	
	
});