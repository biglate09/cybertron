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
          <h1>สิทธิพิเศษทั้งหมด</h1>
        </div>
        <div class="section">
          เรียงตาม
          <select>
            <option>ใกล้เคียง</option>
            <option>ชื่อ</option>
            <option>จังหวัด</option>
          </select>
        </div>
      </div>


      <!-- Priviledge card-->
    <#list branch as branch>
      <div class="col-md-4" id="policy-container">
        <div class="card">
          <div class="card-block card-body row">
            <div class=" card-body ">
              <div class="input-group input-group-margin">
                <h1 class="branch-title">${branch.branchName}</h1>
                <input type="hidden" name="branchId" value="${branch.branchId}">
              </div>
            </div>
          </div>
          <div class="card-block card-body row">
            <div class="card-body" align="right">
              <span class="privilege-point"> จำนวนแต้มที่ใช้ : <span class="privilege-cost">50</span> แต้ม</span>
              <div class="btn oli-more-button">
              	<a class="btn-url" href="${contextPath}/secure/ocean/club/branchDetail.html?branchId=${(branch.branchId!"")?html}">ดูแผนที่</a>
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