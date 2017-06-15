package thaisamut.nbs.publicpage.action;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.model.CssUser;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.core.model.UserEntity;
import thaisamut.nbs.core.remote.UserService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.omailwsclient.component.OMailWsClientFactory;
import thaisamut.util.CssConvertUtils;

public class ForgotUserPasswordAction  extends CssJsonAction {
	private static final long serialVersionUID = 1651261421474390359L;

	private static final Logger LOG = LoggerFactory.getLogger(ForgotUserPasswordAction.class);
    
    @Autowired
    private CssMemberService cssMemberService;
    
    @Autowired
    private OMailWsClientFactory oMailWsClientFactory;
    
    @Autowired 
	private LDAPConnectionService ldapService;
    
    @Autowired private UserService userService;
    
    
	public Parameters params = new Parameters();
	public String forgetMethod = StringUtils.EMPTY;
	public String cardId = StringUtils.EMPTY;
	public String telNo = StringUtils.EMPTY;
	public String email = StringUtils.EMPTY;
	
	// For otp page
	public String tokenId = StringUtils.EMPTY;
	public String refNo = StringUtils.EMPTY;
	
	// For otp success page
	public String username = StringUtils.EMPTY;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String confirmByIdCard() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : confirmByIdCard ");
		}
		return SUCCESS;
	}
	
	public String confirmByEmail() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : confirmByEmail ");
		}
		CssMemberEntity memberByCardId = cssMemberService.findMemberByCardId(cardId);
		if(memberByCardId!=null){
			telNo = memberByCardId.getTelNo();
			email = memberByCardId.getEmail();
		}
		setCaptchaTimeStamp(new Date().toString());
//		if(StringUtils.isBlank(email)){
//			return "forwardOtp";
//		}
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
    public String validateByIdCard() throws Exception {
    	if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : validateByIdCard ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String errMsg = StringUtils.EMPTY;
				Boolean isHaveEmail = false;
				String email = StringUtils.EMPTY;
				CssMemberEntity memberByCardId = cssMemberService.findMemberByCardId(params.cardId);
				if((null != memberByCardId) && (memberByCardId.getBirthDate().equals(params.birthDate))){
					email = memberByCardId.getEmail();
					if(StringUtils.isNotBlank(email)&&CssConvertUtils.isValideEmail(email)){
						// กรณีมีอีเมล
						
						// Set email for param
						params.setEmail(email);
						
						Boolean sendEmailSuccess = false;
						isHaveEmail = true;
						if(params.forgetMethod.equalsIgnoreCase("user")){
							// get user id
							params.setUsername(memberByCardId.getUsername());
							sendEmailSuccess = sendEmail(CssEmailConnectionService.MAP_EMAIL_GET_USER_ID_METHOD, memberByCardId.getTelNo(), memberByCardId.getUsername());
						}else{
							// reset password
							Boolean resetPasswordSuccess = processResetPassword(memberByCardId.getUsername());
							if(resetPasswordSuccess){
								sendEmailSuccess = sendEmail(CssEmailConnectionService.MAP_EMAIL_RESET_PASSWORD_METHOD, memberByCardId.getTelNo(), memberByCardId.getUsername());
							}
						}
						data.put("sendEmailSuccess", sendEmailSuccess);
						if(sendEmailSuccess){
							data.put("sendEmail", email);
						}
					}else{
						// กรณีไม่มี อีเมล
						
						// เพิ่ม param ที่เป็น เบอร์โทรไว้สำหรับยืนยัน otp
//						data.put("telNo", memberByCardId.getTelNo());
					}
				}else{
					if((null != memberByCardId) && !memberByCardId.getBirthDate().equals(params.birthDate)){
						errMsg = "<h1>ไม่พบข้อมูลวันเกิดของท่าน</h1>"+
									"<p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 0 2207 8888</p>"+
									"<p>วันจันทร์ - วันศุกร์ เวลา 8.00 - 18.00 น.</p>"+
									"<p>วันเสาร์ เวลา 8.00 - 17.00 น.</p>";
					}else{
						errMsg = "<h1>ไม่พบข้อมูลเลขประจำตัวประชาชนของท่าน</h1>"+
									"<p>กรุณาติดต่อศูนย์ลูกค้าสัมพันธ์ 0 2207 8888</p>"+
									"<p>วันจันทร์ - วันศุกร์ เวลา 8.00 - 18.00 น.</p>"+
									"<p>วันเสาร์ เวลา 8.00 - 17.00 น.</p>";
					}
				}
				data.put("errMsg", errMsg);
				data.put("isHaveEmail", isHaveEmail);
				result.setData(data);
			}
		}).run();
	}
	
	@SuppressWarnings("unchecked")
    public String validateByEmail() throws Exception {
    	if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : validateByEmail ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				Boolean sendEmailSuccess = false;
				CssMemberEntity memberByCardId = cssMemberService.findMemberByCardId(params.cardId);
				String captchaParam = params.captchaTimeStamp+"|"+params.captchaCode;
				if(!ObjectUtils.defaultIfNull((Boolean)sessionAttributes.get(captchaParam), false)){
					data.put("sendEmailSuccess", sendEmailSuccess);
					data.put("errorMessage", "กรอก Security Code ไม่ถูกต้อง");
				}else if(null == memberByCardId){
					data.put("sendEmailSuccess", sendEmailSuccess);
					data.put("errorMessage", String.format("ไม่พบข้อมูลของท่าน(รหัสผู้ใช้ %s)ในระบบ",params.cardId));
				}else if(StringUtils.isBlank(memberByCardId.getEmail())){
					data.put("sendEmailSuccess", sendEmailSuccess);
					data.put("errorMessage", "ไม่พบอีเมลของท่านในระบบ");
				}else if(!StringUtils.equals(memberByCardId.getEmail(), params.email)){
					data.put("sendEmailSuccess", sendEmailSuccess);
					data.put("errorMessage", "อีเมลของท่านไม่ตรงกับข้อมูลในระบบ");
				}else{
					// remove captcha
					sessionAttributes.remove(captchaParam);
					// Add email for ldap
					ldapService.updateEmail(params.cardId, params.email);
					
					// Update email for member
					memberByCardId.setEmail(StringUtils.defaultString(params.email).toLowerCase());
					memberByCardId.setUpdateDate(new Date());
					cssMemberService.updateMember(memberByCardId);
					
					if(params.forgetMethod.equalsIgnoreCase("user")){
						// get user id
						params.setUsername(memberByCardId.getUsername());
						sendEmailSuccess = sendEmail(CssEmailConnectionService.MAP_EMAIL_GET_USER_ID_METHOD, memberByCardId.getTelNo(), memberByCardId.getUsername());
					}else{
						// reset password
						Boolean resetPasswordSuccess = processResetPassword(memberByCardId.getUsername());
						if(resetPasswordSuccess){
							sendEmailSuccess = sendEmail(CssEmailConnectionService.MAP_EMAIL_RESET_PASSWORD_METHOD, memberByCardId.getTelNo(), memberByCardId.getUsername());
						}
					}
					data.put("sendEmailSuccess", sendEmailSuccess);
					if(sendEmailSuccess){
						data.put("sendEmail", params.email);
					}
				}
				result.setData(data);
			}
		}).run();
	}
        
	public Boolean isValidEmail() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : isValidEmail ");
		}
		return cssMemberService.checkEmail(params.email);
	}
	
	public Boolean processResetPassword(String username){
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : processResetPassword ");
		}
		Boolean resetSuccess = false;
		try {
			// Add token(reset password info) for check
			String sessionId = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_TOKEN_MODE);
			CssResetPasswordEntity resetPassWordInfo = new CssResetPasswordEntity();
			resetPassWordInfo.setUsername(username);
			resetPassWordInfo.setToken(sessionId);
			params.setSessionId(sessionId);
			
			// Expire date in 7 days
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 7);
			resetPassWordInfo.setExpireDate(cal.getTime());
			cssMemberService.addResetPasswordInfo(resetPassWordInfo);
			
			resetSuccess = true;
		} catch (Exception e) {
			return resetSuccess;
		}
		return resetSuccess;
	}
	
	public Boolean sendEmail(String method, String telNo, String username){
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : sendEmail ");
		}
		Boolean sendEmailSucces = true;
		try {
			String content = cssMemberService.getEmailContent(params.sessionId, method, getBaseUrl(), username);
			if(method.equals(CssEmailConnectionService.MAP_EMAIL_RESET_PASSWORD_METHOD)){
				sendEmail(DEFAULT_EMAIL_SENDER_NAME, params.email, "แจ้งรีเซ็ตรหัสผ่านใหม่ (Password) บริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", content);
				sendSmsSuccess(telNo, CssSMSConnectionService.MAP_SMS_METHOD_FORGET_PASSWORD);
			}else{
				sendEmail(DEFAULT_EMAIL_SENDER_NAME, params.email, "แจ้งบัญชีผู้ใช้งาน (User ID) บริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", content);
				sendSmsSuccess(telNo, CssSMSConnectionService.MAP_SMS_METHOD_FORGET_USER);
			}
			sendEmailSucces = true;
		} catch (Exception ex) {
			LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", params.email) ,ex);
			return sendEmailSucces;
		}
		return sendEmailSucces;
	}
	
	public String confirmByOtp() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : confirmByOtp ");
		}
		String refNo = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_REF_MODE);
		String otp = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_OTP_MODE);
		String telNo = null;
		String cardId = request.getParameter("cardId");
		CssMemberEntity memberByCardId = cssMemberService.findMemberByCardId(cardId);
		if(memberByCardId!=null){
			telNo = memberByCardId.getTelNo();
		}
		String forgetMethod = String.valueOf(request.getParameter("forgetMethod"));
//		String smsMethod = getSmsMethod(forgetMethod);
		
		//fix code
		Date expireDate = getNext3Minutes();
		//Date expireDate = getNext15Minutes();
		
		CssMemberEntity member = cssMemberService.findMemberByCardId(cardId);
		
		setTelNo(telNo);
		setRefNo(refNo);
		setForgetMethod(forgetMethod);
		
		String tokenId = cssMemberService.processAddTokenForOtp(refNo, otp, member.getUsername(), expireDate);
		setTokenId(tokenId);
//		
//		// Send otp SMS 
//		CssUser user = new CssUser();
//		user.setUsername(member.getUsername());
//		Boolean success = sendSmsOtpSuccess(user, refNo, otp, telNo, smsMethod, expireDate,true);
//		if(!success) {
//			setTelNo(null);
//			setRefNo(null);
//			setErrorMessage("ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
//		}
//		return success?SUCCESS:ERROR;
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String processConfirmByOtp() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : processConfirmByOtp ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String tokenForCheck = params.refNo.concat(":").concat(params.otp);
				CssResetPasswordEntity entity = cssMemberService.findResetPasswordByToken(tokenForCheck);
				String msg = StringUtils.EMPTY;
				if(null != entity){
					data.put("username", entity.getUsername());
					data.put("success", true);
					
					Date currentTime = new Date();
					if(currentTime.compareTo(entity.getExpireDate()) > 0){
						msg = "รหัส OTP ของท่านหมดอายุ กรุณาทำรายการใหม่";
					}else{
						// Case : sign up complete --> Set active status for member
						if(null != params.forgetMethod && params.forgetMethod.equalsIgnoreCase("completesignup")){
							CssMemberEntity member = cssMemberService.findMemberByUserName(entity.getUsername());
							member.setStatus("A");
							member.setUpdateBy(entity.getUsername());
							member.setUpdateDate(new Date());
							cssMemberService.updateMember(member);
							
							// Add User
						    UserEntity user = userService.find(member.getUsername());
					        boolean new_user = (null == user);
					        if(new_user){
					        	user = new UserEntity();
						        user.setAccessPermitTime("2147483647");
						        user.setPermissions("0");
						        user.setUsername(member.getUsername());
						        user.setEmail(member.getEmail());
						        user.setLastLoginTime(new Date());
						        userService.add(user);
					        }else{
					        	user.setPermissions("0");
					        	userService.merge(user);
					        }
					        data.put("success", true);
					        
					        // Send sms success
							sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_ACTIVATE);
							sessionAttributes.put(PRECESS_KEY_SIGNUP, null);
						}


						// Call logout
						request.logout();
				        request.getSession().invalidate();
					}
					// Remove token in DB
					cssMemberService.removeResetPasswordInfoByEntity(entity);
				}else{
					msg = "รหัส OTP ไม่ถูกต้อง กรุณาตรวจสอบใหม่";
					data.put("success", false);
				}
				data.put("msg", msg);
				result.setData(data);
			}
		}).run();
	}
	
	@SuppressWarnings("unchecked")
	public String processResetOtp() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : processResetOtp ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				CssResetPasswordEntity oldToken = cssMemberService.findTokenById(Long.valueOf(params.tokenId));
				if(null != oldToken){
					String refNo = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_REF_MODE);
					String otp = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_OTP_MODE);
					String forgetMethod = params.forgetMethod;
					String smsMethod = getSmsMethod(forgetMethod);
					Date expireDate = getNext15Minutes();
					oldToken.setToken(refNo.concat(":").concat(otp));
					oldToken.setExpireDate(expireDate);
					Boolean updateTokenSuccess = cssMemberService.updateToken(oldToken);
					CssUser user = new CssUser();
					user.setUsername(oldToken.getUsername());
					Boolean sendSmsSuccess = sendSmsOtpSuccess(user, refNo, otp, params.telNo, smsMethod, expireDate,false);
					
					if(updateTokenSuccess&&sendSmsSuccess){

						data.put("refNo", refNo);
						data.put("success",true);
					}else{
						data.put("success",false);
						if(!sendSmsSuccess){
							data.put("warning","ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
						}
					}
				}else{
					data.put("success", false);
				}
				result.setData(data);
			}
		}).run();
	}
	
	public String confirmByOtpSuccess(){
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : confirmByOtpSuccess ");
		}
		String forgetMethod = request.getParameter("forgetMethod");
		String telNo = request.getParameter("telNo");
		
		// Send sms success
		sendSmsSuccess(telNo, getSmsMethod(forgetMethod));
		
		setCaptchaTimeStamp(new Date().toString());
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String resetPasswordByOtp() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ForgotPasswordAction : resetPasswordByOtp ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String resultsMsg = StringUtils.EMPTY;
				Boolean success = false;
				String captchaParam = params.captchaTimeStamp+"|"+params.captchaCode;
				
				if(!params.newpassword.equalsIgnoreCase(params.confirmnewpassword)){
					resultsMsg = "รหัสผ่านใหม่กับยืนยันรหัสผ่านไม่ตรงกัน";
				}else if(!ObjectUtils.defaultIfNull((Boolean)sessionAttributes.get(captchaParam), false)){
					resultsMsg = "กรอก Security Code ไม่ถูกต้อง";
				}else{
					// remove captcha
					sessionAttributes.remove(captchaParam);
					success = ldapService.updatePassword(params.username, params.newpassword);
					if(success){
						resultsMsg = "เปลี่ยนรหัสผ่านเรียบร้อยแล้ว";
					}else{
						resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
					}
				}
				
				data.put("success", success);
				data.put("resultsMsg", resultsMsg);
				result.setData(data);
			}
		}).run();
	}
	
	public class Parameters implements Serializable {
		private static final long serialVersionUID = -1901792590984625684L;
		private String forgetMethod;
		
		// For --> Reset password page
		private String username;
		private String password;
		private String captchaCode;
		private String captchaTimeStamp;
		private String email;
		private String sessionId;
		
		// For --> Get userId page
		private String cardId;
		private String birthDate;
		
		// For --> Otp page
		private String refNo;
		private String otp;
		
		// For --> Reset Otp
		private String tokenId;
		private String telNo;
		
		// For --> Success Otp
		private String newpassword;
		private String confirmnewpassword;
		
		
		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getCaptchaCode() {
			return captchaCode;
		}

		public void setCaptchaCode(String captchaCode) {
			this.captchaCode = captchaCode;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCardId() {
			return cardId;
		}

		public void setCardId(String cardId) {
			this.cardId = cardId;
		}

		public String getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(String birthDate) {
			this.birthDate = birthDate;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getForgetMethod() {
			return forgetMethod;
		}

		public void setForgetMethod(String forgetMethod) {
			this.forgetMethod = forgetMethod;
		}

		public String getRefNo() {
			return refNo;
		}

		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public String getTokenId() {
			return tokenId;
		}

		public String getCaptchaTimeStamp() {
			return captchaTimeStamp;
		}

		public void setCaptchaTimeStamp(String captchaTimeStamp) {
			this.captchaTimeStamp = captchaTimeStamp;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}
		
		public String getNewpassword() {
			return newpassword;
		}

		public void setNewpassword(String newpassword) {
			this.newpassword = newpassword;
		}

		public String getConfirmnewpassword() {
			return confirmnewpassword;
		}

		public void setConfirmnewpassword(String confirmnewpassword) {
			this.confirmnewpassword = confirmnewpassword;
		}

		public String getTelNo() {
			return telNo;
		}

		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}
		
	}

	public String getForgetMethod() {
		return forgetMethod;
	}

	public void setForgetMethod(String forgetMethod) {
		this.forgetMethod = forgetMethod;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
