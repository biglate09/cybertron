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
	</style>
</head>
<body>
	<div class="container">
		<h1>Ocean Life Insurance Payment History</h1>
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					ข้อมูล</br>กรมธรรม์
				</div>
			</div>
			<div class="col-md-9 md-right">
				<#if Request.policy??>
				<#assign person = Session.sessionUser>
				<#assign policy = Request.policy>
				<div class="card"> 
					<div class="card-block card-header"> 
						<h3 class="card-title">${person.fullName!"-"}</h3>
						<h5 class="card-subtitle">เลขประจำตัวประชาชน : <span class="form-value">${person.idNo!"-"}<span></h5>
						<h5 class="card-subtitle">วันเดือนปีเกิด : <span class="form-value">${person.birthDate!"-"}<span></h5>
					</div>
					<div class="card-block card-body">
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">กรมธรรม์</label></div>
							<div class="col-sm-3 no-padding">${policy.policyNo!"-"}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">แบบประกัน</label></div>
							<div class="col-sm-3 no-padding">${policy.prdName!"-"}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">สถานะ</label></div>
<#-- 							<div class="col-sm-9 no-padding ${policy.group}"> -->
<#-- 								<span class="card-status label no-float">${policy.groupDescription}</span> -->
<#-- 							</div>		 -->
							<div class="col-sm-9 no-padding">${policy.groupDescription!"-"}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">ช่องทางการชำระ</label></div>
							<div class="col-sm-3 no-padding">${policy.paymentChannelDesc!"-"}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">งวดชำระ</label></div>
							<div class="col-sm-3 no-padding">${policy.paymentModeDesc!"-"}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">เงินเอาประกันภัย</label></div>
							<div class="col-sm-3 no-padding replace-currency">${policy.sumAssured?string["#,##0.00"]} บาท</div>
							<div class="col-sm-3 no-padding"><label class="form-label">เบี้ยประกันต่องวด</label></div>
							<div class="col-sm-3 no-padding replace-currency">${policy.allPermium?string["#,##0.00"]} <label>บาท</label></div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">วันเริ่มสัญญา</label></div>
							<div class="col-sm-3 no-padding">${policy.commencementDate!"-"}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">วันครบสัญญา</label></div>
							<div class="col-sm-3 no-padding">${policy.maturityDate!"-"}</div>
						</fieldset>
						<fieldset class="form-group">
							<div class="col-sm-3 no-padding"><label class="form-label">ครบกำหนดชำระเบี้ยวันที่</label></div>
							<div class="col-sm-3 no-padding">${policy.nextPaidDate!"-"}</div>
							<div class="col-sm-3 no-padding"><label class="form-label">สิ้นสุดการชำระเบี้ยวันที่</label></div>
							<div class="col-sm-3 no-padding">${policy.fullyPaidDate!"-"}</div>
						</fieldset>
					</div>
				</div>
				<#else>
				<script>$(document).ready(function(){CSSDialog.warn('ไม่พบข้อมูลกรมธรรม์').on('ok',function(){window.location="${contextPath}/secure/member/policy/policyinfo.html"})});</script>
				</#if>
			</div>
		</div>
		
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					ข้อมูล</br>การชำระเบี้ยประกันภัย
				</div>
			</div>
			<div class="col-md-9 md-right">
				<div class="card"> 
					<div class="card-block card-header"> 
						<h3 class="card-title">ประวัติการชำระเบี้ยย้อนหลัง 1 ปี</h3>
						<#--<h5 class="card-subtitle">ข้อมูลการชำระเบี้ยที่แสดงเป็นข้อมูล ณ วันที่ 03 เดือนสิงหาคม พ.ศ. 2559 เท่านั้น</h5>
						<h5 class="card-subtitle">หากท่านมีการทำรายการหลังจากวันดังกล่าว กรุณาตรวจสอบอีกครั้งหลังจาก 2 วันนับจากวันที่ท่านทำรายการ</h5>-->
					</div>
					<div class="card-block card-body table-responsive" >
						<table class="table payment-table" style="width: 100%">
							<thead align="center">
								<tr>
								<#if "O" == policy.policyType>
									<td>ปี/งวด</td>
									<td>วันที่กำหนดชำระ</td>
									<td>วันที่ชำระถึง</td>
									<td>วันที่ชำระ</td>
									<td>จำนวน</td>
									<td>ช่องทาง</td>
								<#elseif "I" == policy.policyType || "G" == policy.policyType>
									<td>ปี/งวด - ปี/งวด</td>
									<td>วันที่กำหนดชำระ</td>
									<td>วันที่ชำระถึง</td>
									<td>วันที่ชำระ</td>
									<td>จำนวน</td>
									<td>ช่องทาง</td>
								<#elseif "P" == policy.policyType>
									<td>ปีที่</td>
									<td>วันที่ชำระ</td>
									<td>จำนวน</td>
									<td>ช่องทาง</td>
								</#if>
								</tr>
							</thead>
							<tbody>
							<#list history as invoice>
							
								<tr>
								<#if "O" == policy.policyType>
									<td align="center">${invoice.term!'-'}</td>
									<td align="center">${invoice.payFrom!'-'}</td>
									<td align="center">${invoice.payTo!'-'}</td>
									<td align="center">${invoice.rcPayDate!'-'}</td>
									<td align="right" style="padding-right:8px">${invoice.totalPrem!'-'}</td>
									<td align="left" style="padding-left:8px">${invoice.paymentChannel!'-'}</td>
								<#elseif "I" == policy.policyType || "G" == policy.policyType>
									<td align="center">${invoice.term!'-'}</td>
									<td align="center">${invoice.payFrom!'-'}</td>
									<td align="center">${invoice.payTo!'-'}</td>
									<td align="center">${invoice.rcPayDate!'-'}</td>
									<td align="right" style="padding-right:8px">${invoice.totalPrem!'-'}</td>
									<td align="left" style="padding-left:8px">${invoice.paymentChannel!'-'}</td>
								<#elseif "P" == policy.policyType>
									<td align="center">${invoice.term!'-'}</td>
									<td align="center">${invoice.rcPayDate!'-'}</td>
									<td align="right" style="padding-right:8px">${invoice.totalPrem!'-'}</td>
									<td align="left" style="padding-left:8px">${invoice.paymentChannel!'-'}</td>
								</#if>
								</tr>
							</#list>    
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
			
			<fieldset class="form-group">
				<div class="col-lg-3 col-md-3 col-md-offset-6 col-sm-6 col-sm-offset-4 ">
					<a class="btn btn-default oli-arrow-left xs-full" href="${contextPath}/${back}">ย้อนกลับ</a>
				</div>
			</fieldset>
	</div>
	
</body>
</html>