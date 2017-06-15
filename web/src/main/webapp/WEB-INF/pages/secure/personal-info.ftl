<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
</head>
<body>
	<div class="container">
 	<#assign person = Session.sessionUser>
		<div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					ข้อมูล <br />ผู้เอาประกันภัย	
				</div>
			</div>
			<div class="col-md-9">
				<div class="card"> 
					<div class="card-block card-header"> 
					    <h4 class="card-title">${person.fullName!"-"?html}</h4>
					    <h6 class="card-subtitle">เลขประจำตัวประชาชน : <span class="form-value">${person.idNo!"-"?html}<span></h6>
				  	</div>
					<div class="card-block card-body">
					<fieldset class="form-group">
					
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="form-label">วันเดือนปีเกิด</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value">${person.birthDate!"-"?html}</span>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="sm-left-gap form-label">อายุ</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value age">${person.age!"-"?html} ปี</span>
					</div>
					</fieldset>
					
					<fieldset class="form-group">
						<div class="col-sm-3 col-xs-12 no-padding">
							<label class="form-label">เพศ</label>
						</div>
						<div class="col-sm-9 col-xs-12 no-padding">
							<span class="form-value">${person.genderDesc!"-"?html}</span>
						</div>
					</fieldset>
					<#--
					<fieldset class="form-group">
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="form-label">เชื้อชาติ</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value">${person.nationalityDesc!"-"?html}</span>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="sm-left-gap form-label">สัญชาติ</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value">${person.originDesc!"-"?html}</span>
					</div>
					</fieldset>
					<fieldset class="form-group">
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="form-label">ศาสนา</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value">${person.religionDesc!"-"?html}</span>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<label class="sm-left-gap form-label" for="religion">สถานภาพ</label>
					</div>
					<div class="col-sm-3 col-xs-12 no-padding">
						<span class="form-value">${person.maritalStatusDesc!"-"?html}</span>
					</div>
					</fieldset>
					<fieldset class="form-group">
						<div class="col-sm-3 col-xs-12 no-padding"><label class="form-label">ระดับการศึกษา</label></div>
						<div class="col-sm-9 col-xs-12 no-padding"><span class="form-value">${person.educationDesc!"-"?html}</span></div>
					</fieldset>
					<fieldset class="form-group">
						<div class="col-sm-3 col-xs-12 no-padding"><label class="form-label">อาชีพ</label></div>
						<div class="col-sm-9 col-xs-12 no-padding"><span class="form-value">${person.occupationDesc!"-"?html}</span></div>
					</fieldset>
					<fieldset class="form-group">
					<div class="col-sm-3 col-xs-12 no-padding"><label class="form-label">รายได้/เดือน</label></div>
						<#if (person.monthlyIncome!0) == 0>
							<div class="col-sm-9 col-xs-12 no-padding"><span class="form-value">-</span></div>
						<#else>
							<div class="col-sm-9 col-xs-12 no-padding"><span class="form-value">${person.monthlyIncome!"-"?html} บาท</span></div>
						</#if>
					</fieldset>
					<fieldset class="form-group">
						<div class="col-sm-9 col-xs-12 no-padding"></div>
						<div class="col-sm-3 col-xs-12 no-padding"><label class="form-label-notice">ข้อมูล ณ วันที่ ${yesterday!"-"?html}</label></div>
					</fieldset>
					-->
					</div>
				</div>
			</div>
		</div>
		<#assign sortByTime = 'thaisamut.util.CssSortPolicy'?new()>
		<#if Session.sessionPolicy??>
		<#assign policies = Session.sessionPolicy>
		
		<div class="row">
			<div class="col-md-3">
				<div class="section sec-title">
					<h1>ข้อมูล <br />ที่อยู่ในการติดต่อ <br/>ในแต่ละกรมธรรม์</h1>
				</div>
			</div>
			<div class="col-md-9" id="policy-container">
				<#list sortByTime(policies?values) as policy>
				<#if policy??>
				<div class="card">	
						<#if policy.temp >
						<div class="card-block card-header ${policy.group?html} edit-address">
						<#else>
						<div class="card-block card-header ${policy.group?html}">
						</#if>
						<h4 class="card-title">${(policy.prdName!"")?html}</h4>
						<h6 class="card-subtitle">เลขที่กรมธรรม์ <span class="form-value">${(policy.policyNo!"")?html}<span></h6>
					</div>
					<div class="card-block card-body row">
						<div class=" card-body ">
							<div class="input-group input-group-margin">
								<span class="address-label">ที่อยู่จัดส่งเอกสาร </span>
								<span>${(policy.fullAddress!'')?html}</span>
							</div>
							<div class="input-group input-group-margin">
								<span class="address-label">หมายเลขโทรศัพท์มือถือ </span>
								<span>${(policy.mobile1!"")?html}</span>
							</div>
						</div>
					</div>
					 <div class="card-block card-footer row border-top ">
  						<div class="md-left-border col-lg-2 col-lg-offset-8 col-sm-3 col-sm-offset-6 col-md-3 col-md-offset-6 col-xs-6">
  							<div>แก้ไขที่อยู่</div>	
  							<a href="${contextPath}/secure/member/personal/edit.html?policyNo=${(policy.policyNo!"")?html}" id="edit_${policy.policyNo?html}" class="mini-btn	glyphicon glyphicon-pencil" onclick="ga('send', 'event', { eventCategory:'personalinfo', eventAction: ‘click', eventLabel:'edit'});"></a>
						</div>
  						<div class="xs-left-border col-lg-2 col-sm-3 col-md-3 col-xs-6">
  							<div>รายละเอียดกรมธรรม์</div>
							<a id="detail-${(policy.policyNo!"")?html}" href="${contextPath}/secure/member/policy/policydetail.html?policyNo=${policy.policyNo?html}&ref=1" class="mini-btn btn-plan glyphicon glyphicon-search" title="รายละเอียดกรมธรรม์" onclick="ga('send', 'event', { eventCategory:'personalinfo', eventAction: ‘click', eventLabel:'viewdetail'});"> </a>
  						</div>
  					</div>
				</div>
				</#if>
				</#list>
			</div>
		</div>
		</#if>
	</div>
	<#if Session.sessionPolicyJson??>
	<script>
		policyList = ${Session.sessionPolicyJson!''};
	</script>
	</#if>
</body>
</html>