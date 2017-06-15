<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={

		}
	</script>
	<style>
		.claim-label {
			color: #00B5CC;
			padding-left: 10%;
		}
	</style>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">ติดตามและดูประวัติ<br>การเรียกร้องสินไหม</h1>
				</div>
			</div>
			<div class="col-md-9">
				<#assign user = Session['thaisamut.css.model.CssUser']>
				<#assign person = Session.sessionUser>
				<div class="card"> 
					<div class="card-block card-header">
						<h4 class="card-title">${person.fullName!"-"?html}</h4>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-claim">
							<div class="col-sm-3"><label class="claim-label">เลขที่บัตรประชาชน</label></div>
							<div class="col-sm-9"><label class="claim-value">${user.cardNo!"-"?html}</label></div>
							<div class="col-sm-3"><label class="claim-label">ที่อยู่ปัจจุบัน</label></div>
							<div class="col-sm-9"><label class="claim-value">-</label></div>
							<div class="col-sm-3"><label class="claim-label">โทรศัพท์บ้าน</label></div>
							<div class="col-sm-9"><label class="claim-value">-</label></div>
							<div class="col-sm-3"><label class="claim-label">โทรศัพท์มือถือ</label></div>
							<div class="col-sm-9"><label class="claim-value">-</label></div>
							<div class="col-sm-3"><label class="claim-label">โทรศัพท์ที่ทำงาน</label></div>
							<div class="col-sm-9"><label class="claim-value">-</label></div>
							<div class="col-sm-3"><label class="claim-label">Email</label></div>
							<div class="col-sm-9"><label class="claim-value">-</label></div>
							<div class="col-sm-12"><label style="color: red;font-size: 16px;">หมายเหตุ : กรณีรายละเอียดข้อมูลไม่ถูกต้อง กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 0 2207 8888</label></div>
						</fieldset>
					</div>
				</div>
				<div class="card"> 
					<div class="card-block card-body">
						<fieldset class="form-claim">
							<div class="col-sm-12 center">
								<label class="mandatory" style="color: #00B5CC; font-size: 25px;">กรณีเคลมสินไหมสะดวกให้บริษัทติดต่อทาง</label>
								&nbsp;
								<select id="claim-channal">
									<option selected="selected" value="">โปรดระบุ</option>
								</select>
								&nbsp;
								<button class="btn oli-notify-button" type="submit">บันทึก</button>
							</div>
						</fieldset>
					</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>ลำดับ</th>
							<th>เลขที่กรมธรรม์</th>
							<th>แบบประกัน</th>
							<th>วันที่เริ่มสัญญา</th>
							<th>วันที่ครบสัญญา</th>
							<th>ชำระถึง</th>
							<th>รายละเอียด</th>
						</tr>
					</thead>
					<tbody>
						<#if Session.sessionPolicy??>
						<#assign sortByTime = 'thaisamut.util.CssSortPolicy'?new()>
						<#assign policies = Session.sessionPolicy>
						<#assign index = 1>
						<#list sortByTime(policies?values) as policy>
						<#if policy??>
							<tr>
								<td align="center">${index}</td>
								<td>${policy.policyNo?html}</td>
								<td>${policy.prdName?html}</td>
								<td align="center">${policy.commencementDate?html}</td>
								<td align="center">${policy.maturityDate?html}</td>
								<td align="center">${policy.fullyPaidDate?html}</td>
								<td align="center"><a href="${contextPath}/secure/claim/detail.html?policyNo=${(policy.policyNo!"")?html}" class="mini-btn	glyphicon glyphicon-search" title="ดาวน์โหลด"></a></td>
							</tr>
							<#assign index = index+1>
						</#if>
						</#list>
						</#if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>