var handleClick=function(event){
	//Edit 20/09/2559
	var requestType =  $('input#requestType').val();

	if(requestType == "installment"){
		var installmentType = $("input[name='installmentType']:checked").val();
		var policies = $("input[name='policy']:checked").val();
		var fileName = document.getElementById("file1Name").innerHTML;
		if(fileName == ""){
			CSSDialog.warn('กรุณาอัพโหลดสำเนาบัตรประชาชน');
		}
		return false;
	}else if(requestType == "assured"){
		var titleName = $( "#titleName option:selected" ).text(); 
		var fileName1 = document.getElementById("file1Name").innerHTML;
		if(fileName1 == ""){
			CSSDialog.warn('กรุณาอัพโหลดสำเนาบัตรประชาชน');
		}
		var fileName2 = document.getElementById("file2Name").innerHTML;
		if(fileName2 == ""){
			CSSDialog.warn('กรุณาอัพโหลดสำเนาบัตรประชาชน');
		}
		return false;
	}else if(requestType == "address"){

	}else if(requestType == "receive"){

	}
}

$(document).ready(function(){
	
});