$(document).ready(function() {
	// $("#btnSave").on('click', function () {
	// CSSDialog.confirm('ยืนยันการชำระเบี้ย').on('confirm', function() {
	// var pVal = $('#policy').val();
	// $.ajax({
	// url: actions.paymentgeneratedata,
	// cache: false,
	// data: {policyNo:pVal},
	// success: function(json){
	// if(json.data){
	// $('#version').val(json.data.version);
	// $('#merchant_id').val(json.data.merchantId);
	// $('#payment_description').val(json.data.pDesc);
	// $('#order_id').val(json.data.orderId);
	// $('#invoice_no').val(json.data.invoiceNo);
	// $('#amount').val(json.data.amount);
	// $('#customer_email').val(json.data.currency);
	// $('#currency').val(json.data.cEmail);
	// $('#result_url_1').val(json.data.resultUrl1);
	// $('#result_url_2').val(json.data.resultUrl2);
	// $('#hash_value').val(json.data.hmac);
	// $('#form-credit-card').submit();
	// }
	// },
	// error: function(err){
	// CSSDialog.warn('เกิดข้อผิดพลาด');
	// }
	// });
	// });
	// });
	$("#paymentPeriod").on('change', function(e) {
		var params = {};
		
		var pp = $(this).val();
		var pa = $("#premiumPerMonthAmount").val().replace(",","");
		
		params['params.selectedPremiumAmount'] = pp;
		params['params.selectedPaymentPeriod'] = pa;
		
		$.ajax({
			  method: "POST",
			data : params,
			url : actions.recalculatepaymentcreditcard,
			success : function(json) {
				if(json.data.success){
					 $('#version').val(json.data.session.version);
					 $('#merchant_id').val(json.data.session.merchantId);
					 $('#payment_description').val(json.data.session.paymentDescription);
					 $('#order_id').val(json.data.session.orderId);
					 $('#invoice_no').val(json.data.session.invoiceNo);
					 $('#amount').val(json.data.session.amount);
					 $('#customer_email').val(json.data.session.customerEmail);
					 $('#currency').val(json.data.session.currency);
					 $('#result_url_1').val(json.data.session.resultUrl1);
					 $('#result_url_2').val(json.data.session.resultUrl2);
					 $('#hash_value').val(json.data.session.hashValue);
					 $('#premiumPerMonthTemp').val(json.data.session.premiumPerMonthTemp);
					 $('#premiumTemp').val(json.data.session.premiumTemp);
					
					 $('#paymentAmount-1').html(json.data.session.premiumTemp);
					 $('#paymentAmount-2').html(json.data.session.premiumTemp);
					 $('#paymentAmount-3').html(json.data.session.premiumTemp);
					 var text = json.data.session.fromYear + '/' + json.data.session.fromPeriod + '-' + json.data.session.toYear + '/' + json.data.session.toPeriod
					 $('#fromTo').html(text);
				}else{
					CSSDialog.alert(json.data.message);
				}
			},
			error : function(xhr, desc, exceptionobj) {
				if (xhr.responseText != "") {
					alert("ERROR :" + xhr.responseText);
				}
			}
		});
		
	});

	$("#form-credit-card").on('submit', function(e) {
		e.preventDefault();
		CSSDialog.confirm('ยืนยันการชำระเบี้ย').on('confirm', function() {
			$.ajax({
				  method: "POST",
				url : actions.paymentgeneratedata,
				cache : false,
				success : function(json) {
					if(json.data.success){
						$("#form-credit-card").off('submit');
						$('#form-credit-card').submit();
					}else{
						CSSDialog.warn(json.data.message);
					}
				},
				error : function(err) {
					CSSDialog.warn('เกิดข้อผิดพลาด');
				}
			});
		});
	});
});