<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={

		}
		$(document).ready(function(){	
			$("a").click(function() {
			    $(this).parent().children().removeClass("active");
			    $(this).addClass("active");
			});
		});
	</script>
	<style>
		@media (max-width: 992px) {
			.list-group { 
				margin-top: 20px;
			}
		}
		.table {
			font-size: 15px;
		}
	</style>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<script src="${contextPath}/scripts/ocean-club/ocean-club.js"></script>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">ติดตามและดูประวัติ<br>การเรียกร้องสินไหม</h1>
				</div>
			</div>
			<div class="col-md-9">
				<#if Request.policy??>
				<#assign user = Session['thaisamut.css.model.CssUser']>
				<#assign person = Session.sessionUser>
				<#assign policy = Request.policy>
				<#assign isRider = Request.isRider>
				<#if isRider>
				<div class="col-md-3">
					<div class="list-group">
						<a href="#0" class="list-group-item active" data-toggle="tab"><span style="font-size: 15px;">กรมธรรม์หลัก</span></a>
						<#assign tab = 1>
						<#list policy.riders as rider>
							<a href="#${tab}" class="list-group-item" data-toggle="tab"><span style="font-size: 15px;">${rider.riderCode?html}</span></a>
							<#assign tab = tab+1>
						</#list>
					</div>
				</div>
				<div class="col-md-9">
					<div class="card">
						<div class="card-block card-header ${policy.group?html}">
					    	<h4 class="card-title">${policy.prdName?html}</h4>
					    	<h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${policy.policyNo?html}</span></h6>
					  	</div>
						<div class="card-block card-body">
							<div class="tab-content">
								<div class="tab-pane active" id="0">
									กรมธรรม์หลัก
								</div>
								<#assign index = 1>
								<#list policy.riders as rider>
									<div class="tab-pane" id="${index}">
										<table class="table">
											<thead>
												<tr>
													<th>ลำดับที่</th>
													<th>วันที่เกิดเหตุ/วันที่เข้ารับการรักษา</th>
													<th>โรงพยาบาลที่รักษา</th>
													<th>เหตุของการรักษา</th>
													<th>จำนวนสินไหมที่เรียกร้อง</th>
													<th>จำนวนสินไหมที่คุ้มครอง</th>
													<th>สถานะของการพิจารณา</th>
													<th>วันที่พิจารณา</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td align="center">${index}</td>
													<td></td>
													<td></td>
													<td></td>
													<td align="right">${rider.premiumAmout}</td>
													<td align="right">${rider.sumAssured}</td>
													<td></td>
													<td></td>
												</tr>																
											</tbody>
										</table>
									</div>
									<#assign index = index+1>
								</#list>
							</div>
						</div>
					</div>
				</div>
				<#else>
				<div class="col-md-12">
					<div class="card">
						<div class="card-block card-header ${policy.group?html}">
					    	<h4 class="card-title">${policy.prdName?html}</h4>
					    	<h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${policy.policyNo?html}</span></h6>
					  	</div>
						<div class="card-block card-body">
							
						</div>
					</div>
				</div>
				</#if>
				</#if>
			</div>
		</div>
	</div>
</body>
</html>