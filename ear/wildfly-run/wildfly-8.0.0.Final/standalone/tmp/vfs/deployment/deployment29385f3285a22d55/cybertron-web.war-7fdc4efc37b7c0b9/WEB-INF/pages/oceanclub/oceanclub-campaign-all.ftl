<!doctype html>
<html lang="en">
<head>
  <meta charset=UTF-8">
</head>

<body>
  <div class="container">
    <div class="row">
      <div class="col-md-4">
        <div class="section sec-title">
          <h1>สิทธิพิเศษ <br />ทั้งหมด</h1>
        </div>
        <div class="section">
          เรียงตาม
          <select>
            <option>วันที่</option>
            <option>ชื่อสิทธิพิเศษ</option>
            <option>แต้มที่ใช้</option>
          </select>
        </div>
        <div class="section">
          ดูเฉพาะ
          <select>
            <option>สิทธิที่ใช้ได้</option>
            <option>B</option>
            <option>C</option>
          </select>
        </div>
      </div>


      <!-- Priviledge card-->
    <#list camps as camp>
      <div class="col-md-4" id="policy-container">
        <div class="card">
          <div class="card-block ">
            <img class="privilege-thumbnail" src="${contextPath}/images/promo/promo1.jpg" />
          </div>
          <div class="card-block card-body row">
            <div class=" card-body ">
              <div class="input-group input-group-margin">
                <h1 class="privilege-title">${camp.campaignName}</h1>
                <span class="form-value">${camp.campaignDesc}</span>
                <input type="hidden" name="campaignId" value="${camp.campaignId}">
              </div>
            </div>
          </div>
          <div class="card-block card-body row">
            <div class="card-body" align="right">
              <span class="privilege-point"> จำนวนแต้มที่ใช้ : <span class="privilege-cost">50</span> แต้ม</span>
              <div class="btn oli-more-button">
              	<a class="btn-url" href="${contextPath}/secure/ocean/club/discountdetail.html?campaignId=${(camp.campaignId!"")?html}"> รายละเอียดเพิ่มเติม</a>
              </div>
            </div>

          </div>
        </div>
      </div>
      </#list>
  
      
      
    </div>
    <!-- End of priviledge card-->
  </div>
</body>
</html>