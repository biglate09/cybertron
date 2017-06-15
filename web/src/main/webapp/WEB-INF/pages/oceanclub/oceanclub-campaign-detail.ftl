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
        <h1 class="privilege-title">${camp.campaignName}</h1>
      </div>
      <div class="card-block ">
        <img class="privilege-thumbnail" src="${contextPath}/images/promo/promo1.jpg" />
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">

            <p class="privilege-detail">${camp.campaignDesc}</p>
            <span class="address-label">สถานที่ : </span> <span>${camp.campaignPlace}</span>
          </div>
        </div>
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">
            <span class="address-label">วันที่จัดกิจกรรม</span> </span>  : <span>${camp.eventStartDate}</span>
          </div>
        </div>
      </div>
      <div class="card-block card-body row">
        <div class="card-body" align="right">
         <span class="privilege-point"> จำนวนที่เหลือ : <span class="privilege-cost">${camp.quotaRemain}</span> จ้า </span>
         <button class="btn oli-more-button">จอง</button>

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