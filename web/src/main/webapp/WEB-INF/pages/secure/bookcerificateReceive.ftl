<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>

 <script type="text/javascript">
   
   $(document).ready(function(){
            btn = $('#sendMailBtn'); 
	        btn[0].onclick = function(){
	              CSSDialog.alert("ระบบได้รับ Email ของท่านเรียบร้อยแล้ว");          
	         }
   });    
 </script>	
</head>
<style>
.center-block{
 margin-top: 60px;
}

.section-page {
	min-height: 300px;
	padding:16px;
	margin-bottom: 20px;
}

.sec-content-page{
	background: #00B5CC;
	color: #FFF;
	font-weight: 500;
	text-align:center;
/*	font-size: 1.5em;*/
	vertical-align: middle;
}

.btn-content {
  font-size:1em !important;
  text-decoration: none;
}

.email-form-content{

}



</style>
<body>
		<div class="container">
		 <div class="row ">
			<div class="col-md-3">
				<div class="section sec-title">
					<div class="center-block">
						หนังสือรับรองชำระเบี้ยประกัน
					</div>
					
				</div>
			</div>
			<div class="col-md-9">	
				<div class="section-page sec-content-page">
					<div class="center-block">
						<div class="row">	 
						  <div class="col-md-6 col-md-offset-3"> <label for="ex3">กรุณากรอกอีเมลเพื่อรับเอกสาร </label><input class="form-control" id="ex3" type="text"  placeholder="กรุณากรอกอีเมล"></div>	 
						</div>
						<br>
						<div class="row">
						
						  <div class="col-md-6 col-md-offset-3"><bunton class="btn btn-default oli-default-button" id="sendMailBtn">ตกลง</bunton></div>	
						
						</div>
				
					</div>
					     
				</div> 
			
			</div>
			
		 </div>
	</div>
  
</body>
</html>