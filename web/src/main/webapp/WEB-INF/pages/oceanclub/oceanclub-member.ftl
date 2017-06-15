<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<script>
		var actions={
			
		}
	</script>
	<style>
		#text-club-name {
		    z-index:0;
		    position:absolute;    
		    color:black;
		    font-size:24px;
		    font-weight:bold;
		    left:25%;
		    top:60%;
		}
		
		#text-club-expire {
		    z-index:0;
		    position:absolute;    
		    color:black;
		    font-size:24px;
		    font-weight:bold;
		    left:25%;
		    top:67%;
		}
		
		@media (max-width: 1200px) {
			#text-club-name { 
				top:59%;
			}
			#text-club-expire { 
				top:66%;
			}
			
		}
		
		
		@media (max-width: 750px) {
			#text-club-expire, #text-club-name { 
				font-size: 19px;
			}
			#text-club-name { 
				top:58%;
			}
			#text-club-expire { 
				top:65%;
			}
		}
		
		@media (max-width: 590px) {
			#text-club-expire, #text-club-name { 
				font-size: 16px;
			}
			#text-club-name { 
				top:57%;
			}
			#text-club-expire { 
				top:64%;
			}
		}
		
		@media (max-width: 500px) {
			#text-club-expire, #text-club-name { 
				font-size: 15px;
			}
			#text-club-name { 
				top:55%;
			}
			#text-club-expire { 
				top:62%;
			}
		}
		
		@media (max-width: 480px) {
			#text-club-expire, #text-club-name { 
				font-size: 14px;
			}
			#text-club-name { 
				top:53%;
			}
			#text-club-expire { 
				top:60%;
			}
		}
		@media (max-width: 400px) {
			#text-club-expire, #text-club-name { 
				font-size: 14px;
			}
			#text-club-name { 
				top:51%;
			}
			#text-club-expire { 
				top:58%;
			}
		}
	</style>
</head>
<input type="hidden" id="contextPath" value="${contextPath}" />
<body>
	<div class="container">
		<div class="row">
						<div class="col-md-3">
				<div class="section sec-title" id="fllowMeSaveBar">
					<h1 style="margin-bottom: 0px;">ข้อมูลสมาชิก</h1><h1 style="margin-bottom: 0px;">Ocean Club</h1>
				</div>
			</div>
			<div class="col-md-9 center">
				<div class="section"  style="padding:0px;">
					<div id="container-club">
						<img id="image-club" src="${contextPath}/images/ocean-club-card.jpg" style="width: 80%">
						<#if Session.ocFullname??>
						<#assign fullname = Session.ocFullname>
						<#assign expireDate = Session.ocExpireDate>
						<p id="text-club-name">
							${(fullname!"")?html}
					    </p>
					    <p id="text-club-expire">
							${(expireDate!"")?html}
					    </p>
					    </#if>
					</div>
					
				</div>

				<a class="btn btn-default" target="_blank" href="https://www.ocean.co.th/%E0%B8%9A%E0%B8%A3%E0%B8%B4%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B8%82%E0%B8%AD%E0%B8%87%E0%B9%80%E0%B8%A3%E0%B8%B2/%E0%B8%AA%E0%B8%B4%E0%B8%97%E0%B8%98%E0%B8%B4%E0%B8%9E%E0%B8%B4%E0%B9%80%E0%B8%A8%E0%B8%A9%E0%B8%AA%E0%B8%B3%E0%B8%AB%E0%B8%A3%E0%B8%B1%E0%B8%9A%E0%B8%A5%E0%B8%B9%E0%B8%81%E0%B8%84%E0%B9%89%E0%B8%B2/%E0%B8%AA%E0%B8%B4%E0%B8%97%E0%B8%98%E0%B8%B4%E0%B8%9B%E0%B8%A3%E0%B8%B0%E0%B9%82%E0%B8%A2%E0%B8%8A%E0%B8%99%E0%B9%8C%E0%B8%97%E0%B8%B1%E0%B9%89%E0%B8%87%E0%B8%AB%E0%B8%A1%E0%B8%94">สิทธิประโยชน์ทั้งหมด</a>
				<a class="btn btn-default" target="_blank" href="https://www.ocean.co.th/%E0%B8%9A%E0%B8%A3%E0%B8%B4%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B8%82%E0%B8%AD%E0%B8%87%E0%B9%80%E0%B8%A3%E0%B8%B2/%E0%B8%AA%E0%B8%B4%E0%B8%97%E0%B8%98%E0%B8%B4%E0%B8%9E%E0%B8%B4%E0%B9%80%E0%B8%A8%E0%B8%A9%E0%B8%AA%E0%B8%B3%E0%B8%AB%E0%B8%A3%E0%B8%B1%E0%B8%9A%E0%B8%A5%E0%B8%B9%E0%B8%81%E0%B8%84%E0%B9%89%E0%B8%B2/%E0%B8%81%E0%B8%B4%E0%B8%88%E0%B8%81%E0%B8%A3%E0%B8%A3%E0%B8%A1%E0%B9%82%E0%B8%AD%E0%B9%80%E0%B8%8A%E0%B8%B5%E0%B9%88%E0%B8%A2%E0%B8%99%E0%B8%84%E0%B8%A5%E0%B8%B1%E0%B8%9A%E0%B8%97%E0%B8%B1%E0%B9%89%E0%B8%87%E0%B8%AB%E0%B8%A1%E0%B8%94">กิจกรรม</a>
<!-- 				<a href="#" class="btn btn-default">ข่าวสาร</a> -->

			</div>
		</div>
	</div>
</body>
</html>