package thaisamut.css.struts.action;

import java.awt.Color;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.TextProducer;
import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.dwh.service.DataWarehouseService;
import thaisamut.css.dwh.service.pojo.PolicyBean;
import thaisamut.css.eform.service.EformService;
import thaisamut.css.eform.service.pojo.CssConstantBean;
import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.model.CssUser;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.eform.ws.RegisterPaymentCardBean;
import thaisamut.cybertron.ejbweb.model.CssPaymentCardTempEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.cybertron.ejbweb.remote.CssPaymentCardTempService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.osgi.targetbundles.cssdwhws.policy.v1.HermesOtherChannelPayment;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.util.CssConvertUtils;

public class CssJsonAction extends JsonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5513957612245563645L;
	private static final Logger LOG = LoggerFactory.getLogger(CssJsonAction.class);
	protected String BLANK = "";

	public static final String SESSION_ATTRIBUTE_USER = "sessionUser";
	public static final String SESSION_ATTRIBUTE_POLICY_LIST = "sessionPolicy";
	public static final String SESSION_ATTRIBUTE_GRAPH = "sessionGraph";
	public static final String SESSION_ATTRIBUTE_GRAPH_JSON = "sessionGraphJson";
	private static final String SESSION_CAPTCHA_HUMAN_CUNTER = "session_captcha_humencounter";
	public static final String DEFAULT_EMAIL_SENDER_NAME =  "oceanlife@ocean.co.th";//"noreply@nbs-online.com";
	public static final String SPACE = " ";
	protected static final String SINGUP_ZERO = "signup_zero";
	protected static final String ADDRESS_ZERO = "address_zero";
	protected static final String PRECESS_KEY_SIGNUP = "process.key.signup";
	protected static final String PROCESS_KEY_ADDRESS = "process.key.address";
	protected static final String PROCESS_INIT = "process.init";
	protected static final String PROCESS_CONFIRM = "process.confirm";
	protected static final int LIMIT_CAPTCHA = 3;

	public String isTokenExpire;
	public String policyContent;

	protected Locale LOCAL_TH = new Locale("TH", "th");

	@Autowired 
	private DataWarehouseService dataWarehouseService;
	@Autowired
	CssSMSConnectionService cssSMSConnectionService;
	@Autowired
	CssEmailConnectionService cssEmailConnectionService;
	@Autowired
	private CssMemberService cssMemberService;
	@Autowired
	private CssPaymentCardTempService cssPaymentCardTempService;
	@Autowired
	private LDAPConnectionService ldapService;
	@Autowired
	private EformService eformService;
	@Autowired
	private DataWarehouseService dwhService;
	@Autowired
	private CssMasterDataService cssMasterDataService;

	public Parameters params = new Parameters();
	public String captchaTimeStamp = StringUtils.EMPTY;

	protected CssUser getSessionUser() {
		CssUser user = (CssUser) sessionAttributes.get(CssUser.SESSION_CSS_USER);

		if (LOG.isDebugEnabled() && user != null)
			LOG.debug(String.format("CssJsonAction getLoginUser : %1$s", user.getUsername()));
		else if (LOG.isDebugEnabled() && user == null)
			LOG.debug("CssJsonAction getLoginUser : user is null");

		return user;
	}
	public Boolean sendSmsOtpSuccess(CssUser user, String refNo, String otp, String telNo, String smsMethod, Date expireDate, boolean resetCounter) {
		try {
			return cssSMSConnectionService.sendSmsOtpSuccess(user,refNo, otp, telNo, smsMethod, expireDate, resetCounter);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public String getBaseUrl() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssJsonAction : getBaseUrl ");
		}
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath + "/";
	}
	public String getYesterday(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		SimpleDateFormat DF_ddMMyyyy_TH = new SimpleDateFormat("dd/MM/yyyy", new Locale("TH","th"));
		return DF_ddMMyyyy_TH.format(cal.getTime());
	}
	protected void sendLimitEmail(CssUser user, String sender, String recipient, String subject, String body) throws Exception {
		cssEmailConnectionService.sendEmail(sender, recipient, subject, body);

	}
	protected void sendEmail(String sender, String recipient, String subject, String body) throws Exception {
		cssEmailConnectionService.sendEmail(sender, recipient, subject, body);
	}
	protected void sendEmail(String sender, String[] recipients, String subject, String body) throws Exception {
		cssEmailConnectionService.sendEmail(sender, recipients, subject, body);
	}
	protected void sendEmail(String sender, String[] recipients, String[] cc, String[] bcc, String subject, String body) throws Exception {
		cssEmailConnectionService.sendEmail(sender, recipients, cc, bcc, subject, body);
	}
	protected void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, List<InputStream> attachFileList) throws Exception {
		cssEmailConnectionService.sendEmailWithAttachFile( sender, recipient, subject, body, attachFileList);
	}
	protected void sendLimitEmailWithAttachFile(CssUser user, String sender, String recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		cssEmailConnectionService.sendEmailWithAttachFile(sender, recipient, subject, body, attachFileList);
	}
	protected void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		cssEmailConnectionService.sendEmailWithAttachFile(sender, recipient, subject, body,attachFileList);
		
	}
	protected void sendMultiEmailWithAttachFile(String sender, List<String> recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		cssEmailConnectionService.sendMultiEmailWithAttachFile(sender, recipient, subject, body, attachFileList);
	}
	protected void sendLimitEmailWithAttachFile(CssUser user, String sender, String recipient, String subject, String body, DmsDocument... attachFileList) throws Exception {
		cssEmailConnectionService.sendLimitEmailWithAttachFile(user, sender, recipient, subject, body, attachFileList);

	}
	protected void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, DmsDocument... attachFileList) throws Exception {
		cssEmailConnectionService.sendEmailWithAttachFile(sender, recipient, subject, body, attachFileList);
	}
	protected String sendEmailWithAttachFile(String sender, String recipient, String subject, String body, List<String> ccList, DmsDocument... attachFileList) throws Exception {
		return cssEmailConnectionService.sendEmailWithAttachFile(sender, recipient, subject, body, ccList, attachFileList);
	}
	
	protected void checkTokenExpire() {
		String sessionId = request.getParameter("sessionId");
		String isExpire = "false";
		if (StringUtils.isNotBlank(sessionId)) {
			CssResetPasswordEntity entity = cssMemberService.findResetPasswordByToken(sessionId);
			if (null != entity) {
				if (new Date().compareTo(entity.getExpireDate()) > 0) {
					cssMemberService.removeResetPasswordInfoByEntity(entity);
					isExpire = "true";
				}
			} else {
				isExpire = "true";
			}
		}
		setIsTokenExpire(isExpire);
	}

	public String getIsTokenExpire() {
		return isTokenExpire;
	}

	public void setIsTokenExpire(String isTokenExpire) {
		this.isTokenExpire = isTokenExpire;
	}

	public Date getNext15Minutes() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 15);
		return cal.getTime();
	}

	// ==== Fix code for test expire Otp in 3 minutes ====
	public Date getNext3Minutes() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 3);
		return cal.getTime();
	}

	public String getSmsMethod(String method) {
		if (method.equalsIgnoreCase("user")) {
			return CssSMSConnectionService.MAP_SMS_METHOD_FORGET_USER;
		} else if (method.equalsIgnoreCase("password")) {
			return CssSMSConnectionService.MAP_SMS_METHOD_FORGET_PASSWORD;
		} else {
			return CssSMSConnectionService.MAP_SMS_METHOD_ACTIVATE;
		}
	}

	public Boolean sendSmsSuccess(String telNo, String smsMethod) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssJsonAction : sendSmsSuccess ");
		}
		try {
			return cssSMSConnectionService.sendSmsSuccess(telNo, smsMethod);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	public void generatecaptcha() {
		TextProducer tp = new TextProducer() {
			@Override
			public String getText() {
				StringBuilder sb = new StringBuilder();
				char[] chars = "0123456789".toCharArray();
				Random random = new Random();
				for (int i = 0; i < 6; i++) {
					char c = chars[random.nextInt(chars.length)];
					sb.append(c);
				}
				return sb.toString();
			}
		};
		nl.captcha.Captcha newCaptcha = new nl.captcha.Captcha.Builder(150, 40).addText(tp).addBackground(new FlatColorBackgroundProducer(Color.WHITE))
				// .gimp()
				.addNoise().addNoise().build();

		String timestamp = request.getParameter("captchaTimeStamp");
		String timeArray[] = timestamp.split(Pattern.quote("?"));
		timestamp = timeArray[0];
		// Delete old captcha session
		Map<String, Object> map = (Map<String, Object>) sessionAttributes;
		for (Entry<String, Object> e : map.entrySet()) {
			if (e.getKey().startsWith(timestamp)) {
				sessionAttributes.remove(e.getKey());
			}
		}

		sessionAttributes.put(timestamp + "|" + newCaptcha.getAnswer(), true);
		sessionAttributes.put(SESSION_CAPTCHA_HUMAN_CUNTER, 0);
		CaptchaServletUtil.writeImage(response, newCaptcha.getImage());
	}

	public String isvalidcaptcha() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				boolean isHuman = ObjectUtils.defaultIfNull((Boolean) sessionAttributes.get(params.captchaTimeStamp + "|" + params.captchaCode), false);
				if (!isHuman) {
					Integer counter = (Integer) sessionAttributes.get(SESSION_CAPTCHA_HUMAN_CUNTER);
					counter = counter == null ? 0 : counter;
					if (LIMIT_CAPTCHA < ++counter) {
						// remove captcha;
						generatecaptcha();
					}
				}
				result.setData(isHuman);
			}
		}).run();
	}
	public CssConstantBean getAllowDownload()throws Exception{
		return cssMasterDataService.getDowloadAllow();
	}
	public String isduplicateoldpassword() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				String username = params.username;
				String password = params.newpassword;
				boolean isDuplicate = ldapService.validatePassword(username, password);
				result.setData(isDuplicate);
			}
		}).run();
	}

	public String isvalidatepassword() throws Exception {
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				String username = params.username;
				String password = params.oldpassword;
				boolean isDuplicate = ldapService.validatePassword(username, password);
				result.setData(isDuplicate);
			}
		}).run();
	}
	protected void setErrorMessage(String message) {
		if(StringUtils.isNotBlank(message)) {
			sessionAttributes.put("error_message", message);
		}
	}
	public class Parameters implements Serializable {
		private static final long serialVersionUID = 725845548941186471L;
		private String captchaCode;
		private String captchaTimeStamp;
		private String username;
		private String oldpassword;
		private String newpassword;

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

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNewpassword() {
			return newpassword;
		}

		public void setNewpassword(String newpassword) {
			this.newpassword = newpassword;
		}

		public String getOldpassword() {
			return oldpassword;
		}

		public void setOldpassword(String oldpassword) {
			this.oldpassword = oldpassword;
		}

	}
	public String authorizePaymentCard(String policyNo){
		Map<String,PolicyBean> policies = (Map<String, PolicyBean>) sessionAttributes.get(SESSION_ATTRIBUTE_POLICY_LIST);
		
		PolicyBean policy = policies.get(policyNo);
		if(policy!=null){
			/*	
			 * ตรวจสอบ... เมื่อพบข้อมูล ให้ระบบแจ้งเตือนว่า "กรมธรรม์นี้ หากยืนยันสมัคร Payment Card จะถูกยกเลิกการสมัคร auto credit และ direct debit" มีปุ่ม OK และ Cancel
			 * OK ให้ระบบแสดงหน้าจอย่อย
			 * Cancel เมื่อคลิก ให้ปิดหน้าจอ popup และคงค่าข้อมูลหน้าจอเดิมไว้
			*/
			
			//กรณีกรมธรรม์ประเภทอุบัติเหตุส่วนบุคคล (PA) ให้ระบบแจ้งเตือนว่า "กรมธรรม์ประเภทอุบัติเหตุส่วนบุคคล(PA) ไม่สามารถสมัคร Payment Card ได้"
			if("P".equals(policy.getPolicyType())){
				return "กรมธรรม์ประเภทอุบัติเหตุส่วนบุคคล(PA) ไม่สามารถสมัคร Payment Card ได้";
			}
			//เมื่อคลิก ให้ระบบแจ้งเตือนว่า "กรมธรรม์ที่สามารถสมัคร Payment Card ได้ ต้องมีโหมดการชำระราย 1 หรือ 3 เดือนเท่านั้น" กรณีตรวจสอบที่ field ili_policy_master.payment_mode ไม่เท่ากับ (1 หรือ 3)
			if("1,3,".indexOf(String.format("%d",policy.getPaymentMode()))==-1){
				return "กรมธรรม์ที่สามารถสมัคร Payment Card ได้ ต้องมีโหมดการชำระราย 1 หรือ 3 เดือนเท่านั้น";
			}
			
			/*
			 * เมื่อคลิก ให้ระบบแจ้งเตือนว่า "กรมธรรม์นี้ ส่งคำขอสมัคร Payment Card แล้ว" 
			 *	กรณีที่นำเลขที่กรมธรรม์(POLICY_NO) ตรวจสอบที่ table: CSS_PAYMENT_CARD_TEMP >> พบว่ามีข้อมูล และ send_status เท่ากับ ('N') 
			 *	กรณี send_status เท่ากับ 'N' ให้ตรวจสอบต่อที่ table: E-Form - REGISTER_PAYMENT_CARD >> พบว่ามีข้อมูล และ send_status ไม่เท่ากับ 'C'
			 *
			*/
			try {
				CssPaymentCardTempEntity cssPaymentCardTempEntity = cssPaymentCardTempService.findCssPaymentCardTempByPolicyNo(policy.getPolicyNo());
				if(cssPaymentCardTempEntity!=null&&"N".equals(cssPaymentCardTempEntity.getSendStatus())){
					List<RegisterPaymentCardBean> list = eformService.inquiryEformRegisterPaymentCard(policy.getPolicyNo(), "C");
					if(list!=null&&!list.isEmpty()){
						return "กรมธรรม์นี้ ส่งคำขอสมัคร Payment Card แล้ว";
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
			
			/*
			 * เมื่อคลิก ให้ระบบแจ้งเตือนว่า "กรมธรรม์นี้ สมัคร Payment Card แล้ว" กรณีที่นำเลขที่กรมธรรม์(POLICY_NO) 
			 * ตรวจสอบที่ HERMES_OTHER_CHANNEL_PAYMENT โดยผ่าน Data warehouse >> พบว่ามีข้อมูล และ IS_PREMIUM_CARD = '1'
			 */
			try {
				HermesOtherChannelPayment hermesOtherChannelPayment = dataWarehouseService.inquiryOtherChannelPaymentChecking(policy.getPolicyNo(), policy.getPolicyType());
				if(hermesOtherChannelPayment!=null && "1".equals(hermesOtherChannelPayment.getIsPremiumCard())){
					return "กรมธรรม์นี้ ส่งคำขอสมัคร Payment Card แล้ว";
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
			/*
			 * กรณีกรมธรรม์ประเภทสามัญ
			 */
			if("O".equals(policy.getPolicyType())){
				/*
				 * กรณีกรมธรรม์ประเภทสามัญ ตรวจสอบสถานะกรมธรรม์ ต้องเท่ากับ(I) หากไม่ใช่ ให้ระบบแจ้งเตือนว่า 
				 * "กรมธรรม์รายนี้ไม่เข้าเงื่อนไขการสมัคร Payment Card"
				 */
				if(!"I".equals(policy.getPolicyStatus())){
					return "กรมธรรม์รายนี้ไม่เข้าเงื่อนไขการสมัคร Payment Card";
				}

				/*
				 * กรณีกรมธรรม์ประเภทสามัญ เมื่อคลิก ให้ระบบแจ้งเตือนว่า 
				 * "แปลนนี้ ไม่สามารถสมัคร Payment Card ได้" 
				 * กรณีที่นำแปลน(pcppln) ตรวจสอบตามคำสั่งด้านล่าง แล้วไม่พบข้อมูล
				 * select *
				 * from OLIS/OLPPLCPO
				 * where pcppln = [รหัสแบบประกัน]
				 * and [วันที่ปัจจุบัน เช่น 25590826] between PCPSTR and PCPEND;
				 */

				try {
					if(!dataWarehouseService.canThisPlanRegisterPaymentCard(policy.getPrdCode())){
						return "แปลนนี้ ไม่สามารถสมัคร Payment Card ได้";
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
				
				/*
				 * กรณีกรมธรรม์ประเภทสามัญ ตรวจสอบงวดชำระที่เหลือ ที่ field ili_policy_master.fully_paid_date(mmyyyy) 
				 * ลบ  ili_policy_master.next_paid_date(mmyyyy) มากกว่า 7 หากไม่ใช่ 
				 * ให้ระบบแจ้งเตือนว่า "กรมธรรม์รายนี้เหลืองวดชำระ น้อยกว่า 6 งวด ไม่สามารถสมัคร Payment Card ได้"
				 */
				Date fullPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getFullyPaidDate());
				Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
				DateTime yodaNextPaidDate = new DateTime(nextPaidDate);
				DateTime yodaFullPaidDate = new DateTime(fullPaidDate);
				Months m = Months.monthsBetween(yodaNextPaidDate, yodaFullPaidDate);
				if(m.getMonths()<7){
					return "กรมธรรม์รายนี้เหลืองวดชำระ น้อยกว่า 6 งวด ไม่สามารถสมัคร Payment Card ได้";
				}
			}
			/*
			 * กรณีกรมธรรม์ประเภทอุตสาหกรรม 
			 */
			if("I,G".indexOf(policy.getPolicyType())!=-1){
				
				/*
				 * กรณีกรมธรรม์ประเภทอุตสาหกรรม เมื่อคลิกให้ระบบแจ้งเตือนว่า 
				 * "กรมธรรม์นี้ไม่สามารถสมัคร Paymant Card ได้ เนื่องจากมี rider เพิ่มเติม" 
				 * กรณีตรวจสอบตามคำสั่งด้านล่าง แล้วพบข้อมูล
				 * select * from ili_rider_master where policy_no like '@policy_no';
				 */
				if(policy.getRiders()!=null&&!policy.getRiders().isEmpty()){
					return "กรมธรรม์นี้ไม่สามารถสมัคร Paymant Card ได้ เนื่องจากมี rider เพิ่มเติม";
				}
				
				/*
				 * กรณีกรมธรรม์ประเภทอุตสาหกรรม ตรวจสอบสถานะกรมธรรม์ ต้องเท่ากับ(0, 1, 2, 3, 4 หรือ 5) 
				 * และ วันที่ปัจจุบัน น้อยกว่าหรือเท่ากับ ili_policy_master.next_paid_date + 59 วัน 
				 * หากไม่ใช่ ให้ระบบแจ้งเตือนว่า "กรมธรรม์รายนี้ไม่เข้าเงื่อนไขการสมัคร Payment Card"
				 */
				if("0,1,2,3,4,5".indexOf(policy.getPolicyStatus())!=-1){
					Date nextPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getNextPaidDate());
					Date next59PaidDate = CssConvertUtils.addDate(nextPaidDate,59);
					DateTime yodaNext59PaidDate = new DateTime(next59PaidDate);
					if(yodaNext59PaidDate.isBeforeNow()){
						return "กรมธรรม์รายนี้ไม่เข้าเงื่อนไขการสมัคร Payment Card";
					}
					
					/*
					 * กรณีกรมธรรม์ประเภทอุตสาหกรรม ตรวจสอบงวดชำระที่เหลือ ที่ field ili_policy_master.fully_paid_date(mmyyyy) 
					 * ลบ  ili_policy_master.next_paid_date(mmyyyy) มากกว่า 3 หากไม่ใช่ 
					 * ให้ระบบแจ้งเตือนว่า "กรมธรรม์รายนี้เหลืองวดชำระ น้อยกว่า 3 งวด ไม่สามารถสมัคร Payment Card ได้"
					 */
					
					Date fullPaidDate = CssConvertUtils.toDateFromStringDateThai(policy.getFullyPaidDate());
					DateTime yodaNextPaidDate = new DateTime(nextPaidDate);
					DateTime yodaFullPaidDate = new DateTime(fullPaidDate);
					Months m = Months.monthsBetween(yodaNextPaidDate, yodaFullPaidDate);
					if(m.getMonths()<=3){
						return "กรมธรรม์รายนี้เหลืองวดชำระ น้อยกว่า 3 งวด ไม่สามารถสมัคร Payment Card ได้";
					}
				}else{
					return "กรมธรรม์รายนี้ไม่เข้าเงื่อนไขการสมัคร Payment Card";
				}
			}
			
		}else{
			return String.format("ไม่พบข้อมูลกรมธรรม์เลขที่ %s",policyNo);
		}
		return StringUtils.EMPTY;
	}

	public String getCaptchaTimeStamp() {
		return captchaTimeStamp;
	}
	public void setCaptchaTimeStamp(String captchaTimeStamp) {
		this.captchaTimeStamp = captchaTimeStamp;
	}

	private static final String SESSION_NONCE = "session.nonce";

	private Nonce nonce;

	protected Nonce generateNonce() {
		nonce = new Nonce();
		nonce.name = UUID.randomUUID().toString().replaceAll("-", "");
		nonce.value = UUID.randomUUID().toString().replaceAll("-", "");
		sessionAttributes.put(SESSION_NONCE, nonce);
		return nonce;
	}

	protected boolean validateNonce() {
		Nonce nonce = (Nonce) sessionAttributes.get(SESSION_NONCE);
		if (nonce != null) {
			String nonceValue = (String) requestParameters.get(nonce.name);
			if (nonce.value != null && nonce.value.equals(nonceValue)) {
				return true;
			}
			generateNonce();
		}
		return false;
	}

	public Nonce getNonce() {
		return nonce;
	}

	public void setNonce(Nonce nonce) {
		this.nonce = nonce;
	}

	public class Nonce {
		private String name;
		private String value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
