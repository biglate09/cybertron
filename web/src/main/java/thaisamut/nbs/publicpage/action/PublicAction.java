package thaisamut.nbs.publicpage.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nl.captcha.Captcha;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PersonBean;
import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.ldap.LDAPUser;
import thaisamut.css.model.CssUser;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.omailwsclient.component.OMailWsClientFactory;

/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public class PublicAction extends CssJsonAction {
	private static final long serialVersionUID = 1809174851239990176L;

	private static final Logger LOG = LoggerFactory.getLogger(PublicAction.class);

	public Parameters params = new Parameters();
	private RegisterForm signup = new RegisterForm();
	public Captcha captcha = null;

	@Autowired
	LDAPConnectionService ldapService;
	@Autowired
	CssMemberService cssMemberService;
	@Autowired
	OMailWsClientFactory oMailWsClientFactory;
	@Autowired
	DataWarehouseService dataWarehouseService;

	public String sessionId = null;
	private String token;
	private String captchaCode;
	private String captchaTimeStamp;

	public String changepassword() throws Exception {
		setSessionId(null == request.getParameter("sessionId") ? StringUtils.EMPTY : request.getParameter("sessionId"));
		checkTokenExpire();
		setCaptchaTimeStamp(new Date().toString());
		return SUCCESS;
	}
	public String forgetpassword() throws Exception {
		return SUCCESS;
	}
	public String signup() throws Exception {
		sessionAttributes.put(PRECESS_KEY_SIGNUP, PROCESS_INIT);
		setCaptchaTimeStamp(new Date().toString());
		return SUCCESS;
	}
	public String policydetail() throws Exception {
		return SUCCESS;
	}
	public String confirmotp() throws Exception {
		telNo = (String) sessionAttributes.get("telNo");
		refNo = (String) sessionAttributes.get("refNo");
		tokenId = (String) sessionAttributes.get("tokenId");
		String signupKey = (String) sessionAttributes.get(PRECESS_KEY_SIGNUP);

		if (StringUtils.isBlank(telNo) || StringUtils.isBlank(refNo) || StringUtils.isBlank(tokenId)) {
			request.setAttribute("errorOTP", "ไม่สามารถส่ง OTP ได้");
		}
		if (PROCESS_INIT.equals(signupKey)) {
			return SINGUP_ZERO;
		}

		return SUCCESS;
	}
	public String checkforget() throws Exception {
		String mode = request.getParameter("forget");
		if ("user".equals(mode)) {
			return "cardid";
		} else {
			return "email";
		}
	}

	public Boolean isDuplicateEmailOrTelno(String telNo, String email) throws Exception {
		// Check duplicate telno
		CssMemberEntity memberByTelno = cssMemberService.findMemberByTelNo(telNo);

		// Check duplicate email
		CssMemberEntity memberByEmail = null;
		if (StringUtils.isNotBlank(email)) {
			memberByEmail = cssMemberService.findMemberByEmail(email);
		}
		return (null != memberByTelno || null != memberByEmail);
	}

	public String confirmsignup() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				Boolean ldapSuccess = false;
				Boolean success = false;
				// Fix code for test
				Boolean isDuplicateEmailOrTelno = false;
				Boolean isDuplicateUser = false;
				String captchaParam = captchaTimeStamp + "|" + captchaCode;
				// check captcha
				if (ObjectUtils.defaultIfNull((Boolean) sessionAttributes.get(captchaParam), false)) {
					// remove captcha
					sessionAttributes.remove(captchaParam);
					// Check duplicate user
					isDuplicateUser = cssMemberService.isDuplicateUser(signup.userId, signup.idCard);

					// Check duplicate email or telNo
					 isDuplicateEmailOrTelno = isDuplicateEmailOrTelno(signup.telNo, signup.email);
					

					if (!isDuplicateUser && !isDuplicateEmailOrTelno) {
						// Get user data from data warehouse
						CssMemberEntity cssMember = getUserFullNameFromDataWarehouse();

						if (null != cssMember) {
							LDAPUser ldapUser = createLDAPUser(cssMember.getFullname());
							ldapSuccess = ldapService.addEntry(ldapUser);
							Boolean addMemberSuccess = false;
							if (ldapSuccess) {
								cssMember = createCssMember(cssMember);
								addMemberSuccess = cssMemberService.addMember(cssMember);
								if (addMemberSuccess) {
									if ("email".equals(signup.getConfirmMode())) {
										String sessionId = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_TOKEN_MODE);
										Boolean addMemberWaitingSuccess = cssMemberService.processAddTokenForEmail(sessionId, signup.userId);
										if (addMemberWaitingSuccess) {
											String emailContent = cssMemberService.getEmailContent(sessionId, CssEmailConnectionService.MAP_EMAIL_CONFIRM_SIGNUP_METHOD, getBaseUrl(), signup.getUserId());
											try {
												sendEmail(DEFAULT_EMAIL_SENDER_NAME, cssMember.getEmail(), "ยืนยันการสมัครสมาชิกบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", emailContent);
											} catch (Exception ex) {
												LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", cssMember.getEmail()), ex);
											}
											success = true;
											data.put("email", cssMember.getEmail());
										}
									} else {
										String refNo = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_REF_MODE);
										String otp = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_OTP_MODE);
										String telNo = signup.getTelNo();
										Date expireDate = getNext15Minutes();
										String tokenId = cssMemberService.processAddTokenForOtp(refNo, otp, signup.getUserId(), expireDate);

										sessionAttributes.put("telNo", telNo);
										sessionAttributes.put("refNo", refNo);
										sessionAttributes.put("tokenId", tokenId);
										sessionAttributes.put(PRECESS_KEY_SIGNUP, PROCESS_CONFIRM);

										// Send otp SMS
										CssUser user = new CssUser();
										user.setUsername(signup.userId);
										success = sendSmsOtpSuccess(user, refNo, otp, telNo, CssSMSConnectionService.MAP_SMS_METHOD_ACTIVATE, expireDate, true);
									}
								}
							} else {
								isDuplicateUser = true;
							}
						}
					}
				}
				data.put("isDuplicateUser", isDuplicateUser);
				data.put("isDuplicateEmailOrTelno", isDuplicateEmailOrTelno);
				data.put("isHuman", isDuplicateEmailOrTelno);
				data.put("success", success);
				result.setData(data);
			}
		}).run();
	}

	private CssMemberEntity getUserFullNameFromDataWarehouse() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On PublicAction : getUserFullNameFromDataWarehouse ");
		}
		CssMemberEntity entity = null;
		SimpleDateFormat DF_ddMMyyyy_TH = new SimpleDateFormat("dd/MM/yyyy", new Locale("TH", "th"));
		SimpleDateFormat DF_yyyyMMdd_EN = new SimpleDateFormat("yyyy-MM-dd", new Locale("US", "us"));
		Date birthDateParams = DF_ddMMyyyy_TH.parse(signup.getBirthDate());
		String req_birthDate = DF_yyyyMMdd_EN.format(birthDateParams);
		String req_idCard = signup.idCard;
		String req_telNo = signup.telNo;

		Boolean isValidUser = dataWarehouseService.isValidUser(req_idCard, req_birthDate, req_telNo);
		if (isValidUser) {
			PersonBean personBean = dataWarehouseService.getPersonData(req_idCard);
			if (null != personBean) {
				entity = new CssMemberEntity();
				entity.setTitleDesc(personBean.getTitleDesc());
				entity.setFirstName(personBean.getFirstName());
				entity.setCustCode(personBean.getCustCode());
				entity.setLastName(personBean.getLastName());
				// fullName =
				// nullToEmpty(personBean.getTitleDesc()).concat(nullToEmpty(personBean.getFirstName())).concat("
				// ").concat(personBean.getLastName());
			}
		}
		return entity;
	}

	private String nullToEmpty(String obj) {
		if (StringUtils.isBlank(obj)) {
			return StringUtils.EMPTY;
		}
		return obj;
	}

	private LDAPUser createLDAPUser(String fullName) {
		LDAPUser ldapUser = new LDAPUser();
		ldapUser.setUserId(signup.getUserId());
		ldapUser.setFullName(fullName);
		ldapUser.setPassword(signup.getPassword());
		ldapUser.setCardNumber(signup.getIdCard());
		ldapUser.setBirthDate(signup.getBirthDate());
		ldapUser.setMobilePhone(signup.getTelNo());
		ldapUser.setEmail(StringUtils.isBlank(signup.getEmail()) ? null : signup.getEmail());
		return ldapUser;
	}

	private CssMemberEntity createCssMember(CssMemberEntity cssMember) {
		// CssMemberEntity cssMember = new CssMemberEntity();
		cssMember.setUsername(signup.getUserId());
		cssMember.setCardNo(signup.getIdCard());
		cssMember.setBirthDate(signup.getBirthDate());
		cssMember.setTelNo(signup.getTelNo());
		cssMember.setEmail(StringUtils.isBlank(signup.getEmail()) ? null : signup.getEmail().toLowerCase());
		cssMember.setCreateDate(new Date());
		cssMember.setStatus("N");
		cssMember.setCreateBy(signup.getUserId());
		return cssMember;
	}

	public RegisterForm getSignup() {
		return signup;
	}
	public void setSignup(RegisterForm signup) {
		this.signup = signup;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	public String getCaptchaTimeStamp() {
		return captchaTimeStamp;
	}
	public void setCaptchaTimeStamp(String captchaTimeStamp) {
		this.captchaTimeStamp = captchaTimeStamp;
	}

	public class Parameters implements Serializable {
		private static final long serialVersionUID = 8673609965676555837L;
		private String username;
		private String password;
		private String captchaCode;
		private String captchaTimeStamp;

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

		public String getCaptchaTimeStamp() {
			return captchaTimeStamp;
		}

		public void setCaptchaTimeStamp(String captchaTimeStamp) {
			this.captchaTimeStamp = captchaTimeStamp;
		}
	}
	public class RegisterForm implements Serializable {
		private static final long serialVersionUID = -5560107084797759761L;
		private String userId;
		private String password;
		private String confirmPassword;
		private String idCard;
		private String telNo;
		private String email;
		private String confirmMode;
		private String bday;
		private String bmonth;
		private String byear;

		public String getUserId() {
			return StringUtils.lowerCase(userId);
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
		public String getIdCard() {
			return idCard;
		}
		public void setIdCard(String idCard) {
			this.idCard = idCard;
		}
		public String getTelNo() {
			return telNo;
		}
		public void setTelNo(String telNo) {
			this.telNo = telNo;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getConfirmMode() {
			return confirmMode;
		}
		public void setConfirmMode(String confirmMode) {
			this.confirmMode = confirmMode;
		}
		public String getBday() {
			return bday;
		}
		public void setBday(String bday) {
			this.bday = bday;
		}
		public String getBmonth() {
			return bmonth;
		}
		public void setBmonth(String bmonth) {
			this.bmonth = bmonth;
		}
		public String getByear() {
			return byear;
		}
		public void setByear(String byear) {
			this.byear = byear;
		}
		public String getBirthDate() {
			return bday.concat("/").concat(bmonth).concat("/").concat(byear);
		}
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	// ========================== OTP Process ============================
	private String telNo;
	private String refNo;
	private String tokenId;
	private String backToActivatePage;

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
	public String getBackToActivatePage() {
		return backToActivatePage;
	}
	public void setBackToActivatePage(String backToActivatePage) {
		this.backToActivatePage = backToActivatePage;
	}
	
	//=====signup content


	public String getPolicyContent() {
		StringBuilder content = new StringBuilder();
		content.append("<div id=\"divPolicy\" style = \"overflow-y: auto; overflow-x: hidden\"><p style=\"font-size: 2em; text-align: center;\"><strong>ข้อตกลงและเงื่อนไขการใช้บริการ OceanLife iService ของไทยสมุทรประกันชีวิต</strong></p>");
		content.append(
				"<p>บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน) ซึ่งต่อไปนี้ จะเรียกว่า &ldquo;บริษัทฯ&rdquo; เป็นเจ้าของและผู้ดูแลจัดการเว็บไซต์นี้ การเข้าใช้เว็บไซต์ การเข้าใช้ระบบหรือการเข้าดูข้อมูลใดๆ บนเว็บไซต์นี้ ถือว่าผู้ใช้บริการยอมรับและผูกพันตามข้อตกลงและเงื่อนไขการใช้บริการทุกระบบของ<span style=\"white-space: nowrap;\">บริษัทฯ</span></p>");
		content.append("<p><strong>1. คำจำกัดความ</strong></p>");
		content.append("<ul>");
		content.append(
				"<li><span style=\"white-space: nowrap;width:80px;display: inline-block;\">ผู้ใช้บริการ</span>หมายถึง ผู้ลงทะเบียนขอเข้าใช้บริการ OceanLife iService ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> ตามวิธีที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> กำหนด</li>");
		content.append("<li><span style=\"white-space: nowrap;width:80px;display: inline-block;\">บริษัทฯ</span>หมายถึง บริษัท ไทยสมุทรประกันชีวิต จำกัด (มหาชน)</li>");
		content.append(
				"<li><span style=\"white-space: nowrap;width:80px;display: inline-block;\">บริการ</span>หมายถึง การให้บริการข้อมูลกรมธรรม์ผ่านทางเครือข่ายอินเทอร์เน็ต (Internet) หรือ เพื่อให้บริการข้อมูลกรมธรรม์ประกันภัย รายละเอียดผลิตภัณฑ์ บริการและสิทธิประโยชน์ของลูกค้า</li>");
		content.append("<li>รหัสผ่าน	 (Password)    	หมายถึง รหัสที่ผู้ใช้บริการกำหนดขึ้นตามเงื่อนไขการตั้งรหัสผ่าน (Password) ข้อแนะนำของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> เพื่อใช้ในการเข้าใช้บริการ OceanLife iService</li>");
		content.append("<li>บัญชีผู้ใช้งาน (User ID) 	หมายถึง หมายเลขในบัตรประจำตัวประชาชน 13 หลักของผู้ใช้บริการ หรือ ไปรษณีย์อิเล็กทรอนิกส์ (อีเมล) ที่ใช้ลงทะเบียนสมัครใช้ของผู้ใช้บริการ</li>");
		content.append("</ul>");
		content.append("<p><strong>2. เงื่อนไขทั่วไปที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> กำหนดไว้เพื่อใช้บริการ OceanLife iService มีดังนี้</strong></p>");
		content.append("<ul>");
		content.append(
				"<li>ผู้ใช้บริการสามารถเข้าใช้บริการ OceanLife iService โดยใช้บัญชีผู้ใช้งาน (User ID) และรหัสผ่าน (Password) ของผู้ใช้บริการตามวิธีการและเงื่อนไข ในการเข้าใช้บริการ OceanLife iService ที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> กำหนด </li>");
		content.append(
				"<li>เมื่อผู้ใช้บริการเข้าใช้บริการ OceanLife iService ไม่ว่าจะเกี่ยวกับกรมธรรม์ประเภทใด ๆ ของผู้ใช้บริการก็ตาม โดยใช้บัญชีผู้ใช้งาน (User ID)  และรหัสผ่าน (Password) ของผู้ใช้บริการแล้ว ให้ถือว่าการกระทำดังกล่าวนั้นมีผลสมบูรณ์ โดยที่ผู้ใช้บริการไม่ต้องลงลายมือชื่อเป็นหลักฐานในเอกสารใดๆ และให้ถือว่ามีผลผูกพันผู้ใช้บริการ และผู้ใช้บริการตกลงรับผิดชอบในการกระทำที่เกิดขึ้นในการใช้บริการ เว้นแต่ผู้ใช้บริการได้มีการพิสูจน์ว่าตนเองไม่ได้เป็นผู้กระทำหรือใช้บริการดังกล่าว อนึ่ง ผู้ใช้บริการตกลงและยอมรับว่าในกรณีที่เกิดขึ้นดังกล่าว<span style=\"white-space: nowrap;\">บริษัทฯ</span> ไม่ต้องรับผิดชอบในข้อผิดพลาดหรือบกพร่อง หรือความเสียหายใดๆ อันอาจเกิดขึ้น และผู้ใช้บริการตกลงจะไม่นำเหตุดังกล่าวไปเป็นข้ออ้าง ยืนยัน และ/หรือใช้เป็นหลักฐานโต้แย้ง หรือฟ้องร้องกับ<span style=\"white-space: nowrap;\">บริษัทฯ</span> แต่ประการใดทั้งสิ้น</li>");
		content.append(
				"<li>ผู้ใช้บริการสามารถเปลี่ยนแปลงรหัสผ่าน (Password) ได้ด้วยตนเองตลอดระยะเวลาการใช้บริการโดยไม่ต้องแจ้งให้<span style=\"white-space: nowrap;\">บริษัทฯ</span> ทราบ และตกลงให้รหัสผ่านที่เปลี่ยนแปลงไปในแต่ละขณะนั้นเป็นรหัสผ่านการใช้บริการที่มีผลผูกพันผู้ใช้บริการทุกประการ ซึ่งรหัสผ่านที่เปลี่ยนแปลงนั้นต้องเป็นไปตามเงื่อนไขที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> กำหนด </li>");
		content.append(
				"<li>ผู้ใช้บริการทราบดีว่าบัญชีผู้ใช้งาน (User ID)  และรหัสผ่าน(Password) นี้เป็นข้อมูลลับเฉพาะซึ่งผู้ใช้บริการจะต้องเก็บรักษาไว้และมิให้บุคคลอื่นใดรวมทั้ง<span style=\"white-space: nowrap;\">บริษัทฯ</span> ล่วงรู้โดยเด็ดขาด และในกรณีที่เกิดความเสียหายใดๆ ขึ้นอันเนื่องจากการเปิดเผยหรือมอบให้ซึ่งข้อมูลดังกล่าวแก่บุคคลอื่นนั้น <span style=\"white-space: nowrap;\">บริษัทฯ</span>ย่อมหลุดพ้นจากความรับผิดทั้งปวง</li>");
		content.append("<li>ในกรณีที่ผู้ใช้บริการต้องการยกเลิก รหัสผ่าน (Password) ไม่ว่าสาเหตุใดๆ ก็ตาม หรือยกเลิกการใช้บริการข้อมูลกรมธรรม์ ผู้ใช้บริการต้องยกเลิกโดยแจ้งมายัง<span style=\"white-space: nowrap;\">บริษัทฯ</span> เท่านั้น</li>");
		content.append("<li>ผู้ใช้บริการยืนยันว่าข้อมูลที่ให้ไว้กับ<span style=\"white-space: nowrap;\">บริษัทฯ</span> เป็นข้อมูลที่ถูกต้องแท้จริง ครบถ้วน สมบูรณ์ และเป็นปัจจุบัน</li>");
		content.append(
				"<li>ผู้ใช้บริการรับทราบและยอมรับว่าในการให้บริการ OceanLife iService ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> ต้องมีระยะเวลาในการดำเนินการ ซึ่งอาจไม่สามารถดำเนินการตามระยะเวลาที่ผู้ใช้บริการต้องการได้ ดังนั้นผู้ใช้บริการตกลงจะไม่เรียกร้องให้<span style=\"white-space: nowrap;\">บริษัทฯ</span>รับผิดชอบต่อความเสียหายใดๆ ที่เกิดขึ้นจากความล่าช้าใดๆ ในขั้นตอนของการดำเนินการ</li>");
		content.append(
				"<li>ในกรณีที่ระบบคอมพิวเตอร์หรือระบบการติดต่อสื่อสารของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> หรือผู้ให้บริการระบบอินเทอร์เน็ต (Internet Service Provider) หรือผู้ให้บริการเครือข่ายโทรศัพท์เคลื่อนที่ (Mobile Operator) ระบบไฟฟ้า ระบบการติดต่อสื่อสารโทรคมนาคม หรือระบบอื่นใดที่เกี่ยวข้องกับการให้บริการ OceanLife iService ชำรุดขัดข้องอยู่ระหว่างการซ่อมแซม หรือปิดระบบชั่วคราวเพื่อบำรุงรักษา หรือเหตุอื่นอันเป็นเหตุให้ผู้ใช้บริการไม่สามารถใช้บริการได้ ผู้ใช้บริการตกลงจะไม่ยกเอาเหตุขัดข้องดังกล่าวมาเป็นข้อเรียกร้องให้<span style=\"white-space: nowrap;\">บริษัทฯ</span> รับผิดชอบ หรือยกเป็นข้ออ้างใดๆ ต่อ<span style=\"white-space: nowrap;\">บริษัทฯ</span> แต่ประการใด</li>");
		content.append(
				"<li>เอกสารหรือหนังสือใดๆ ที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> ส่งไปยังผู้ใช้บริการตามที่อยู่ หรือที่ทำงาน หรือสถานศึกษา หรือไปรษณีย์อิเล็กทรอนิกส์ (อีเมล) ที่ผู้ใช้บริการได้แจ้งไว้ในใบคำขอใช้บริการนี้หรือที่ผู้ใช้บริการได้แจ้งเปลี่ยนในภายหลังให้ถือว่า ผู้ใช้บริการได้ทราบข้อความในเอกสารหรือหนังสือนั้นๆ แล้ว</li>");
		content.append(
				"<li>การเปลี่ยนแปลงข้อมูลของผู้ใช้บริการ เช่น การเปลี่ยนแปลงที่อยู่ หรือสถานที่ติดต่อ ที่ทำผ่านบริการ OceanLife iService โดยไม่มีการเปลี่ยนแปลงข้อมูลดังกล่าวผ่านช่องทางอื่นๆ หลังจากนี้ <span style=\"white-space: nowrap;\">บริษัทฯ</span> จะถือว่าข้อมูลที่มีการเปลี่ยนแปลงผ่านบริการ OceanLife iService  เป็นข้อมูลล่าสุดของผู้ใช้บริการ</li>");
		content.append(
				"<li>ผู้ใช้บริการยินยอมให้<span style=\"white-space: nowrap;\">บริษัทฯ</span> ติดต่อ สอบถาม เปิดเผยรายละเอียด หรือข้อมูลบางประการหรือทั้งหมดเกี่ยวกับผู้ใช้บริการที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> รับทราบมาเนื่องจากการใช้บริการใดๆ ของผู้ใช้บริการกับ<span style=\"white-space: nowrap;\">บริษัทฯ</span> กับบุคคล และ/หรือนิติบุคคลใดๆ ได้ตามความจำเป็นและเหมาะสม โดย<span style=\"white-space: nowrap;\">บริษัทฯ</span> ไม่ต้องขออนุญาตหรือได้รับความยินยอมจากผู้ใช้บริการอีกครั้ง แต่อย่างใด ทั้งนี้ให้ถือว่าความยินยอมนี้มีผลใช้บังคับตลอดไป แม้ว่าผู้ใช้บริการจะมิได้ใช้บริการตามคำขอใช้บริการนี้ หรือมิได้ใช้บริการใดๆ กับ<span style=\"white-space: nowrap;\">บริษัทฯ</span> แล้วก็ตาม</li>");
		content.append(
				"<li>ผู้ใช้บริการยอมรับและยินยอมที่จะปฏิบัติตามประกาศหรือข้อกำหนดหรือเงื่อนไขใดๆ ที่มีการเปลี่ยนแปลงหรือเพิ่มเติมที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> ประกาศผ่านทางออนไลน์ หรือทางช่องทางอื่นๆ ตามที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> เห็นสมควร</li>");
		content.append("<li>ผู้ใช้บริการยอมรับและยินยอมว่า<span style=\"white-space: nowrap;\">บริษัทฯ</span> อาจยกเลิกบัญชีผู้ใช้งาน (User ID)และรหัสผ่าน (Password) ของผู้ใช้บริการได้ตามที่เห็นสมควร</li>");
		content.append(
				"<li>ผู้ใช้บริการยอมรับว่า<span style=\"white-space: nowrap;\">บริษัทฯ</span> สามารถแก้ไข เปลี่ยนแปลงหรือเพิ่มเติมบริการต่างๆ ได้ โดยไม่ต้องแจ้งให้ทราบล่วงหน้า อาทิ การเพิ่มฟังก์ชั่นการใช้งาน ของระบบข้อมูลลูกค้า การแจ้งข่าวสารทางอีเมล และ/หรือหมายเลขโทรศัพท์มือถือ ซึ่ง<span style=\"white-space: nowrap;\">บริษัทฯ</span> จะแจ้งให้ผู้ใช้บริการทราบผ่านทางเว็บไซต์ หรือช่องทางอื่นๆ ตามที่<span style=\"white-space: nowrap;\">บริษัทฯ</span> เห็นสมควร</li>");
		content.append(
				"<li>ผู้ใช้บริการยินยอมให้<span style=\"white-space: nowrap;\">บริษัทฯ</span> เก็บ รวบรวม ใช้หรือทำการเปิดเผยข้อมูลต่างๆ ของผู้ใช้บริการ รวมทั้งการเปิดเผยข้อมูลส่วนบุคคลของผู้ใช้บริการเท่าที่กฎหมายอนุญาตให้กระทำได้ ให้แก่บุคคล นิติบุคคลหรือหน่วยงานต่างๆของรัฐบาล ไม่ว่าอยู่ในประเทศหรือต่างประเทศ ไม่ว่าประเทศดังกล่าวจะมีกฏหมายในลักษณะคุ้มครองข้อมูลส่วนบุคคล หรือไม่ก็ตาม เพื่อการให้บริการ การส่งเสริมการขาย หรือนำเสนอผลิตภัณฑ์ต่างๆ ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> หรือเหตุอื่นๆ ที่เป็นประโยชน์กับผู้ใช้บริการหรือที่เกี่ยวเนื่องกับผู้ใช้บริการ รวมทั้งสามารถติดต่อผู้ใช้บริการผ่านทาง ที่อยู่ หมายเลขโทรศัพท์บ้านและหมายเลขโทรศัพท์มือถือ หรือทางไปรษณีย์อิเล็กทรอนิกส์ (อีเมล) ได้ตามที่เห็นสมควร นอกจากนี้ ผู้ใช้บริการยินยอมให้มีการเปิดเผย หรือนำส่งข้อมูลของผู้ใช้บริการไปยังหน่วยงานต่างๆ ของรัฐ ไม่ว่าเป็นหน่วยงานของรัฐในประเทศไทยหรือหน่วยงานของรัฐต่างประเทศ เพื่อปฏิบัติตามกฎหมาย กฎ หรือระเบียบต่างๆ ที่เกี่ยวข้องกับผู้ใช้บริการ หรือ<span style=\"white-space: nowrap;\">บริษัทฯ</span></li>");
		content.append("<li>การใช้บริการ OceanLife iService  หรือ การตีความข้อตกลงและเงื่อนไขการใช้บริการ OceanLife iService ดังกล่าวให้เป็นไปตามกฎหมายไทย</li>");
		content.append("</ul>");
		content.append("<p><strong>3. หน้าที่และความรับผิดชอบของผู้ใช้บริการ</strong></p>");
		content.append("<ul style=\"text-align: justify;\">");
		content.append(
				"<li>ผู้ใช้บริการต้องไม่ทำสำเนา ทำซ้ำ คัดลอก ดัดแปลง แก้ไขเพิ่มเติมหรือกระทำการใดต่อข้อมูลในบริการ OceanLife iService รวมทั้งนำข้อมูลต่างๆ ที่เกี่ยวข้องกับ<span style=\"white-space: nowrap;\">บริษัทฯ</span> ซึ่งเป็นข้อมูลที่อาจก่อให้เกิดความเสียหาย ไปเปิดเผยหรือเผยแพร่หรือกระทำการใดที่อาจทำให้<span style=\"white-space: nowrap;\">บริษัทฯ</span> ได้รับความเสียหายได้ เว้นแต่ได้รับอนุญาตจาก<span style=\"white-space: nowrap;\">บริษัทฯ</span></li>");
		content.append("<li>ผู้ใช้บริการห้ามใช้บริการ OceanLife iService ในทางที่มิชอบ หรือหาประโยชน์อันหรือในทางที่ก่อให้เกิดความเสียหาย หรืออาจก่อความเสียหายต่อ<span style=\"white-space: nowrap;\">บริษัทฯ</span> หรือต่อผู้ใช้บริการรายอื่นๆ</li>");
		content.append(
				"<li>ผู้ใช้บริการตกลงไม่ใช้เครื่องหมายการค้า สัญลักษณ์ เครื่องหมายบริการ และชื่อทางการค้า หรือทรัพย์สินทางปัญญาอื่นใดของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> โดยไม่ได้รับความยินยอมเป็นลายลักษณ์อักษรล่วงหน้าจาก<span style=\"white-space: nowrap;\">บริษัทฯ</span></li>");
		content.append(
				"<li>เมื่อผู้ใช้บริการมีการเปลี่ยนแปลงข้อมูลใดๆ ของผู้ใช้บริการที่มีผลกระทบต่อการใช้งานหรือใช้บริการข้อมูลกรมธรรม์ผ่านออนไลน์ ผู้ใช้บริการต้องแจ้ง<span style=\"white-space: nowrap;\">บริษัทฯ</span> ทราบทันที ในกรณีที่ผู้ใช้บริการพบข้อผิดพลาดหรือการกระทำใดที่เป็นการละเมิดในบริการ OceanLife iService ผู้ใช้บริการจะต้องแจ้ง<span style=\"white-space: nowrap;\">บริษัทฯ</span> ในทันทีที่พบข้อผิดพลาดนั้นๆ ผ่านทางศูนย์ลูกค้าสัมพันธ์ โทร.0-2207-8888</li>");
		content.append("</ul>");
		content.append("<p><strong>4. ข้อจำกัดความรับผิดของ<span style=\"white-space: nowrap;\">บริษัทฯ</span></strong></p>");
		content.append("<ul>");
		content.append(
				"<li><span style=\"white-space: nowrap;\">บริษัทฯ</span> จะไม่รับผิดชอบต่อความเสียหายใดๆ รวมถึงความสูญเสีย ความเสียหายหรือค่าใช้จ่ายใดๆ ที่เกิดขึ้นไม่ว่าทั้งทางตรงและทางอ้อม โดยเฉพาะเจาะจงหรือ โดยบังเอิญหรือเป็นผลสืบเนื่อง ที่เกิดจากการที่ผู้ใช้บริการใช้บริการ OceanLife iService เว้นแต่ผู้ใช้บริการสามารถพิสูจน์ได้ว่าความเสียหาย หรือความสูญเสียนั้นเกิดจากความประมาทเลินเล่ออย่างร้ายแรงของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> อย่างไรก็ตามข้อจำกัดนี้ใช้สำหรับผู้ใช้บริการที่ปฏิบัติตามข้อกำหนดและเงื่อนไขของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> อย่างเคร่งครัดเท่านั้น</li>");
		content.append("<li><span style=\"white-space: nowrap;\">บริษัทฯ</span> ไม่ต้องรับผิดชอบในความเสียหายใดๆ ที่เกิดขึ้นในกรณีดังนี้");
		content.append("<ul><li>ผู้ใช้บริการทราบถึงข้อผิดพลาดในบริการ OceanLife iService </li></ul>");
		content.append("<ul><li>เมื่อเกิดสถานการณ์ที่ไม่สามารถควบคุมได้ เช่น ปัญหาด้านอุปกรณ์อิเล็กทรอนิกส์หรือซอฟต์แวร์หรือฮาร์ดแวร์ ระบบการสื่อสาร หรือการคุกคามจากไวรัส เป็นต้น หรือเหตุสุดวิสัยอื่นๆ</li></ul>");
		content.append("<ul><li>ผู้ใช้บริการไม่ปฏิบัติตามข้อกำหนด และ เงื่อนไขของ<span style=\"white-space: nowrap;\">บริษัทฯ</span></li></ul>");
		content.append(
				"<ul><li>ความล่าช้า หรือ ข้อผิดพลาดที่เกิดจาก อุปกรณ์อิเล็กทรอนิกส์ของผู้ใช้บริการ หรือระบบคอมพิวเตอร์ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> หรือเกิดจากผู้ให้บริการระบบอินเทอร์เน็ต (Internet Service Provider)</li></ul>");
		content.append("<ul><li>ในกรณีที่บริษัทต้องกระทำการใดๆ อันเป็นการป้องกันสมาชิกรายอื่นๆ หรือเพื่อรักษาความปลอดภัยของข้อมูลในเว็บไซต์ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> หรือบริการ OceanLife iService</li></ul>");
		content.append("</li></ul>");
		content.append(
				"<p>ข้าพเจ้าได้ทำการลงทะเบียนเพื่อขอใช้บริการ OceanLife iService ของ<span style=\"white-space: nowrap;\">บริษัทฯ</span> โดยข้าพเจ้าได้รับทราบ ยอมรับและตกลงผูกพันในข้อตกลงและเงื่อนไขการใช้บริการ OceanLife iService ตามที่ระบุ ข้างต้นทุกประการ</p></div>");
		return content.toString();
	}
}
