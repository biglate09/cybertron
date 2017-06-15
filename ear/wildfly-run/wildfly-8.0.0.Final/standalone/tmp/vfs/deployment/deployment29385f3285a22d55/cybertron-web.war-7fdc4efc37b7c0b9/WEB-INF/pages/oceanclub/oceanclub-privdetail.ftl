<!doctype html>
<html lang="en">
<head>
  <meta charset=UTF-8">
  
  <style type="text/css">
	#map{
	display:none;
	}
  </style>
</head>
<body>
<div class="container">
  <div class="col-md-7">
    <div class="card">
      <div class="input-group input-group-margin">
        <h1 class="privilege-title">ชื่อสิทธิพิเศษ</h1>
      </div>
      <div class="card-block ">
        <img class="privilege-thumbnail" src="${contextPath}/images/promo/promo1.jpg" />
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">

            <p class="privilege-detail">รายละเอียดสิทธิพิเศษ</p>
            <span class="address-label">สถานที่ : </span> <span>place</span>
            <br><button class="btn oli-more-button" onclick="displayMap()>ดูแผนที่</button>
            <div id="map"></div>
          </div>
        </div>
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">

            <span class="address-label">ส่วนลด : </span> <span>100</span></br>
            <span class="address-label">ระยะเวลาการใช้สิทธิ </span>  :<span>100</span>
          </div>
        </div>
      </div>
      <div class="card-block card-body row">
        <div class="card-body" align="right">
          <span class="privilege-point"> จำนวนแต้มที่ใช้ : <span class="privilege-cost">50</span> แต้ม</span>
          <button class="btn oli-more-button">ใช้สิทธิ</button>

        </div>

      </div>
    </div>
  </div>
  <div class="col-md-1">

  </div>
  <div class="col-md-4" >
    <div class="card">


    <div class="section sec-title">
      <h1>สิทธิพิเศษที่คล้ายกัน</h1>
    </div>
    <div class="recommend-container">
      <div class="card">
        <div class="card-block ">
          <img class="privilege-thumbnail" src="${contextPath}/images/promo/promo1.jpg" />
        </div>
        <div class="card-block card-body row">
          <div class=" card-body" align="right">
              <span class="privilege-title-recommend" style="float:left;" >ชื่อสิทธิพิเศษ</span>
              <div class="btn oli-more-button">
              	<a class="btn-url" href="${contextPath}/secure/ocean/club/discountdetail.html"> รายละเอียดเพิ่มเติม</a>
              </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
  </div>
</body>
</html>