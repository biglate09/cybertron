<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<style>
		.payment-table thead td {
			background: #99E2EB;
			color: #FFF;
			border: 1px solid #FFF;
			text-align: center;
			font-size: 22px;
		}
		
		.payment-table  tbody td {
			background: #CCF0F5;
			color: #666;
			border: 1px solid #FFF;
		    vertical-align: middle;
		    padding: 2px;
		    font-size: 18px;
		}
		
		.payment-table tfoot td {
			background: #99E2EB;
			color: #FFF;
			border: 1px solid #FFF;
			text-align: center;
			font-size: 22px;
			padding: 2px;
		}
		
		.payment-sum-table tbody td {
			background: #99E2EB;
			color: #FFF;
			border: 1px solid #FFF;
			text-align: left;
			font-size: 22px;
			padding: 2px;
		}
	</style>
</head>
<script>
	var actions={
			paymentgeneratedata:'${contextPath}/secure/payment/paymentgeneratedata.html',
			recalculatepaymentcreditcard:'${contextPath}/secure/payment/recalculatepaymentcreditcard.html'
	}
</script>
<body>
<input type="hidden" id="contextPath" value="${contextPath}" />
<form id="form-credit-card" action="${action2c2p!"-"?html}" method="post">
	<div class="container">
		<div class="row ">
			<#if Request.policy??>
			<#assign person = Session.sessionUser>
			<#assign policy = Request.policy>
			<div class="col-md-12 md-right" style="padding-bottom: 30px;">
				<h1 style="margin-top: 0px;">สรุปจำนวนเบี้ยที่ต้องชำระ</h1>
				<table class="payment-table" style="width: 100%">
					<thead align="center">
						<tr>
							<td width="5%">ลำดับที่</td>
							<td width="31%">แบบประกัน</td>
							<td width="13%">จำนวนเงินเอาประกันภัย</td>
							<td width="6%">ชำระปี/งวด</td>
							<td width="13%">งวดการชำระเบี้ย (เดือน)</td>
							<td width="12%">เบี้ยประกันภัย</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="center">1</td>
							<td align="left">${policy.prdName!"-"}</td>
							<td align="right">${policy.sumAssured!"-"}</td>
							<#if (policy?? && policy.policyType?? && (policy.policyType='I'||policy.policyType='G'))>
								<td align="center"><span id="fromTo">${css2c2pRequest.fromYear!"-"}/${css2c2pRequest.fromPeriod!"-"}-${css2c2pRequest.toYear!"-"}/${css2c2pRequest.toPeriod!"-"}</span></td>
							<#else>
								<td align="center">${css2c2pRequest.letterYear!"-"}/${css2c2pRequest.letterMonth!"-"}</td>
							</#if>
							
							<#if (policy?? && policy.policyType?? && (policy.policyType='I'||policy.policyType='G') && css2c2pRequest.paymentMode<=5 && css2c2pRequest.nlChk='N')>
								<td align="center">
									<select id="paymentPeriod" name="paymentPeriod">
										<option value="1"<#if (css2c2pRequest.paymentMode = 1)> selected="selected"</#if>>1</option>
										<option value="2"<#if (css2c2pRequest.paymentMode = 2)> selected="selected"</#if>>2</option>
										<option value="3"<#if (css2c2pRequest.paymentMode = 3)> selected="selected"</#if>>3</option>
										<option value="4"<#if (css2c2pRequest.paymentMode = 4)> selected="selected"</#if>>4</option>
										<option value="5"<#if (css2c2pRequest.paymentMode = 5)> selected="selected"</#if>>5</option>
									</select>
								</td>
							<#else>
								<td align="center">${css2c2pRequest.paymentMode!"-"}</td>
							</#if>
							<td align="right" style="display: none;">${css2c2pRequest.premiumPerMonthTemp!''}</td>
<!-- 								<td align="right">${policy.premiumPerMonthTemp!''}</td> -->
							<td align="right"><span id="paymentAmount-1">${css2c2pRequest.premiumTemp!''}</span></td>
						</tr>
					</tbody>
<!-- 					<tfoot> -->
<!-- 						<tr> -->
<!-- 							<td colspan="4"></td> -->
<!-- 							<td colspan="2"><span id="paymentAmount-1">${css2c2pRequest.premiumTemp!''}</span></td> -->
<!-- 						<tr> -->
<!-- 					</tfoot> -->
				</table>
				<br/>
				<table class="payment-sum-table" style="width: 100%">
					<tbody>
						<tr>
							<td width="25%" style="background-color: #00B5CC">Credit Card</td>
							<td width="25%" style="background-color: #F89728; display: none;">เบี้ยประกันภัย ${css2c2pRequest.premiumPerMonthTemp!''} บาท</td>
							<td width="25%" style="background-color: #F89728">เบี้ยประกันภัย <span id="paymentAmount-3">${css2c2pRequest.premiumTemp!''}</span> บาท</td>
							<td width="25%" style="background-color: #00B5CC">ค่าธรรมเนียม 0 บาท</td>
							<td width="25%" style="background-color: #F89728">รวมชำระ <span id="paymentAmount-2">${css2c2pRequest.premiumTemp!''}</span> บาท</td>
						</tr>
					</tbody>
				</table>
				<br/>
				
				<input type="hidden" id="policy" name="policy" value="${policy.policyNo!''}"/>
				<input type="hidden" id="premiumAmount" name="premiumAmount" value="${css2c2pRequest.premiumShow!''}"/>
				<input type="hidden" id="premiumPerMonthAmount" name="premiumPerMonthAmount" value="${css2c2pRequest.premiumPerMonthShow!''}"/>
				<input type="hidden" id="premiumTemp" name="premiumTemp" value="${css2c2pRequest.premiumTemp!''}"/>
				<input type="hidden" id="premiumPerMonthTemp" name="premiumPerMonthTemp" value="${css2c2pRequest.premiumPerMonthTemp!''}"/>
				<!-- PARAMETER TO 2C2P -->
				<input type="hidden" id="version" name="version" value="${css2c2pRequest.version!''}"/>
				<input type="hidden" id="merchant_id" name="merchant_id" value="${css2c2pRequest.merchantId!''}"/>
				<input type="hidden" id="payment_description" name="payment_description" value="${css2c2pRequest.paymentDescription!''}"/>
				<input type="hidden" id="order_id" name="order_id" value="${css2c2pRequest.orderId!''}"/>
				<input type="hidden" id="invoice_no" name="invoice_no" value="${css2c2pRequest.invoiceNo!''}"/>
				<input type="hidden" id="amount" name="amount" value="${css2c2pRequest.amount!''}"/>
				<input type="hidden" id="customer_email" name="customer_email" value="${css2c2pRequest.customerEmail!''}">
				<input type="hidden" id="currency" name="currency" value="${css2c2pRequest.currency!''}"/>
				<input type="hidden" id="hash_value" name="hash_value" value="${css2c2pRequest.hashValue!''}"/>
				<input type="hidden" id="result_url_1" name="result_url_1" value="${css2c2pRequest.resultUrl1!''}"/>
				<input type="hidden" id="result_url_2" name="result_url_2" value="${css2c2pRequest.resultUrl2!''}"/>
				<input type="hidden" id="default_lang" name="default_lang" value="th"/>
				<!-- ----------------- -->
				

				<div class="row">
					<div class="row" align="right" style="margin-right: 15px;">
<!-- 							<a href="${contextPath}/secure/payment/paymentmain.html?policyNo=${policy.policyNo!''}&ref=2" class="btn btn-default oli-arrow-left">ย้อนกลับ</a> -->
							<a href="${contextPath}/secure/member/policy/policyinfo.html" class="btn btn-default oli-arrow-left">ย้อนกลับ</a>
							<button type="submit" class="btn btn-default oli-default-button">ชำระเงิน</button>
					</div>
				</div>
			</div>
			</#if>
		</div>
	</div>
</form>
<script src="/cybertron/scripts/payment/payment.js"></script>
</body>
</html>