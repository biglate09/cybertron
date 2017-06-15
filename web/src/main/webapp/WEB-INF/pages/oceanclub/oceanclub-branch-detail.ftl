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
        <h1 class="branch-title">${branch.campaignName}</h1>
      </div>
      <div class="card-block card-body row privilege-detail-divide">
        <div class=" card-body ">
          <div class="input-group input-group-margin ">
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
    </div>
  </div>
</body>
</html>