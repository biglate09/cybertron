<!doctype html>
<html lang="en">
<head>
    <title>สมัครสมาชิก OceanLife iService</title>
    <meta charset="UTF-8"/>
    <script>
        var ACTIONS = {
            otp: '${contextPath}/pub/page/confirmOtp.html',
            confirmsignup: '${contextPath}/pub/page/confirmSignup.html',
            home: '${contextPath}/',
        }

        var SESSION = {
            captchaTimeStamp: '${(captchaTimeStamp!"")?html}'
        }

        var POLICY_CONTENT = '${policyContent}'
    </script>
    <link rel="stylesheet" href="${contextPath}/datepicker/css/datepicker.css"/>
    <link rel="stylesheet" href="${contextPath}/css/signup.css"/>
</head>
<body>
<br/>
<div class="container">
    <form id="form-signup" action="#" method="POST">
        <div class="row ">
            <div class="col-md-3">
                <div class="section sec-title">
                    ข้อมูลผู้ใช้
                </div>
            </div>
            <div class="col-md-9">
                <div class="section sec-content">
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="userId" required>บัญชีผู้ใช้งาน</label></div>
                        <div class="col-md-8">
                            <input type="text" class="form-control" maxlength="50" id="userId" name="signup.userId"
                                   placeholder="บัญชีผู้ใช้งาน" data-validate-email-or-cardid="บัญชีผู้ใช้งานไม่ถูกต้อง"
                                   data-validate-min-length-user="อย่างน้อย 6-50 หลัก"
                                   data-validate-required="กรอกบัญชีผู้ใช้งาน">
                            <div class="notic">กรุณาใส่เลขประจำตัวประชาชน หรืออีเมลเพื่อเป็นบัญชีผู้ใช้งาน</div>
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="password" required>รหัสผ่าน</label></div>
                        <div class="col-md-8">
                            <input type="password" class="form-control diff-password password-hint" id="password"
                                   name="signup.password" placeholder="รหัสผ่าน"
                                   data-validate-diff-password="รหัสผ่านคาดเดาง่ายเกินไป"
                                   data-validate-min-length-password="อย่างน้อย 8 หลัก"
                                   data-validate-required="กรอกรหัสผ่าน">
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="confirm-password" required>ยืนยันรหัสผ่าน</label></div>
                        <div class="col-md-8"><input type="password" class="form-control" id="confirm-password"
                                                     name="signup.confirmPassword" placeholder="ยืนยันรหัสผ่าน"
                                                     data-validate-match="รหัสผ่านไม่ตรงกัน"
                                                     data-validate-required="กรอกยืนยันรหัสผ่าน"></div>
                    </fieldset>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="section sec-title">
                    ข้อมูลผู้เอาประกันภัย
                </div>
            </div>
            <div class="col-md-9">
                <div class="section sec-content">
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="cardNumber" required>เลขประจำตัวประชาชน</label></div>
                        <div class="col-md-8"><input type="text" class="form-control" data-only-rule="number"
                                                     autocomplete="off" maxlength="20" id="cardNumber"
                                                     name="signup.idCard" placeholder="เลขประจำตัวประชาชน"
                                                     data-validate-citizenid="ข้อมูลไม่ถูกต้อง"
                                                     data-validate-match-idcard="ข้อมูลไม่ตรงกับบัญชีผู้ใช้งาน"
                                                     data-validate-required="กรอกเลขประจำตัวประชาชน"></div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="ิbirth-date" required>วันเดือนปีเกิด</label></div>
                        <div class="col-md-2 sm-bottom-gap">
                        <#--<div id="datepicker" class="input-group date">
                            <input type="text" id="birth-date" class="form-control" name="signup.birthDate" readonly placeholder="วว/ดด/ปปปป" data-validate-required="กรอกวันเดือนปีเกิด">
                            <div class="input-group-addon datepicker-button">
                                <span class="glyphicon glyphicon-th"></span>
                            </div>
                        </div>-->
                            <select id="bday" name="signup.bday" class="form-control" data-validate-required="กรอกวัน">
                                <option value="">วัน</option>
                                <option value="01">01</option>
                                <option value="02">02</option>
                                <option value="03">03</option>
                                <option value="04">04</option>
                                <option value="05">05</option>
                                <option value="06">06</option>
                                <option value="07">07</option>
                                <option value="08">08</option>
                                <option value="09">09</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>
                        </div>
                        <div class="col-md-4 sm-bottom-gap">
                            <select id="bmonth" name="signup.bmonth" class="form-control"
                                    data-validate-required="กรอกเดือน">
                                <option value="">เดือน</option>
                                <option value="01">มกราคม</option>
                                <option value="02">กุมภาพันธ์</option>
                                <option value="03">มีนาคม</option>
                                <option value="04">เมษายน</option>
                                <option value="05">พฤษภาคม</option>
                                <option value="06">มิถุนายน</option>
                                <option value="07">กรกฎาคม</option>
                                <option value="08">สิงหาคม</option>
                                <option value="09">กันยายน</option>
                                <option value="10">ตุลาคม</option>
                                <option value="11">พฤศจิกายน</option>
                                <option value="12">ธันวาคม</option>
                            </select>
                        </div>
                        <div class="col-md-2 sm-bottom-gap">
                            <select id="byear" name="signup.byear" class="form-control" data-validate-required="กรอกปี">
                                <option value="">ปี</option>
                            </select>
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="tel-no" required>หมายเลขโทรศัพท์มือถือ</label></div>
                        <div class="col-md-8"><input type="text" class="form-control" data-only-rule="number"
                                                     autocomplete="off" id="tel-no" name="signup.telNo" maxlength="10"
                                                     placeholder="หมายเลขโทรศัพท์มือถือ"
                                                     data-validate-required="กรอกหมายเลขโทรศัพท์มือถือ"
                                                     data-validate-mobile="ข้อมูลไม่ถูกต้อง"></div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label for="email">อีเมลผู้ใช้งาน</label></div>
                        <div class="col-md-8"><input type="text" class="form-control" maxlength="50" id="email"
                                                     autocomplete="off" name="signup.email" placeholder="อีเมล"
                                                     data-validate-email-depend-combo='รูปแบบอีเมลไม่ถูกต้อง'></div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4">
                            <label required>Security Code</label>
                        </div>
                        <div class="col-md-8">
                            <img id="img-captcha"
                                 src="${contextPath}/pub/page/generatecaptcha.html?captchaTimeStamp=${captchaTimeStamp?html}"/>
                            <img id="reloadcaptcha" src="${contextPath}/images/reload-captcha.gif" height="20"
                                 width="20" style="vertical-align:top" alt="captcha"/>
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4">
                        </div>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="captchaCode" name="captchaCode"
                                   autocomplete="off" placeholder="Security Code"
                                   data-validate-required="กรอก Security Code ตามภาพ"
                                   data-validate-captcha="กรุณากรอกข้อมูลให้ถูกต้อง">
                            <input type="hidden" name="captchaTimeStamp" value="${captchaTimeStamp?html}"/>
                            <button id="testcaptcha" type="button" class="btn btn-default" style="display:none">ทดสอบ
                                captcha
                            </button>
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-md-4"><label required>ช่องทางในการยืนยันการสมัคร</label></div>
                        <div class="col-md-8">
                            <div class="btn-group" role="group">
                                <input class="custom-radio" id="confirm-tel" type="radio" name="signup.confirmMode"
                                       value="tel" checked required/>
                                <label for="confirm-tel"
                                       class="custom-radio glyphicon glyphicon-phone">โทรศัพท์มือถือ</label>
                                <input class="custom-radio" id="confirm-mail" type="radio" name="signup.confirmMode"
                                       value="email"/>
                                <label for="confirm-mail"
                                       class="custom-radio glyphicon glyphicon-envelope">อีเมล</label>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="form-group">
                        <div class="col-sm-6 xs-bottom-gap sm-right">
                            <button type="button" id="registerBtn"
                                    class="btn btn-default oli-default-button oli-arrow-right xs-full"
                                    onclick="ga('send', 'event', { eventCategory:'registration', eventAction: 'submit',eventLabel: 'newuser'});">
                                ลงทะเบียน
                            </button>
                        </div>
                        <div class="col-md-3 col-md-offset-3 col-sm-6 text-right xs-bottom-gap">
                            <a href="${contextPath}/" class="btn btn-default oli-arrow-left xs-full"
                               onclick="ga('send', 'event', { eventCategory:'registration', eventAction: ‘click', eventLabel:'newuserBack'});">ย้อนกลับ</a>
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>
    </form>
</div>
<script src="${contextPath}/datepicker/js/bootstrap-datepicker.js"></script>
<script src="${contextPath}/scripts/signup.js"></script>
</body>
</html>