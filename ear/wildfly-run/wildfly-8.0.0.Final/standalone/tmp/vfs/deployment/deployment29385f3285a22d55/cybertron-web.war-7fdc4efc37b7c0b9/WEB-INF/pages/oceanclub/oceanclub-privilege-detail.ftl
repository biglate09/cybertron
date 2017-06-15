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
        <h1 class="privilege-title">${place.placeName}</h1>
      </div>
      <div class="card-block ">
        <img class="privilege-thumbnail" src="${contextPath}/images/promo/promo1.jpg" />
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">

            <p class="privilege-detail">${place.placeDesc}</p>
            <span class="address-label">สถานที่ : </span> <span>${place.placeAddress}</span>
            <br><span>${place.placeContact}</span>
            <br><button class="btn">แสดงแผนที่</button>
            <br><input type="hidden" id="lat" value="${place.latitude}"/>
            <br><input type="hidden" id="long" value="${place.longitude}"/>
            
          </div>
        </div>
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">
            <span class="address-label">ใช้สิทธิได้จนถึง</span> </span>  : <span>${place.endDate}</span>
          </div>
        </div>
      </div>
      <div class="card-block card-body row">
        <div class="card-body" align="right">
         <span class="privilege-point"></span>
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