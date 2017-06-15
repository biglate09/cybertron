package thaisamut.css.sms;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.exception.CssNotifyLimitException;
import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssNotififyLimitEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.osgi.targetbundles.smsgateway.message.v1.Message;
import thaisamut.smsfilegatewaywsclient.component.SmsFileGatewayWsClientFactory;
import thaisamut.util.CssConvertUtils;

public class CssSMSConnectionService  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CssSMSConnectionService.class);
	@Autowired
	private CssMasterService cssMasterService;
	@Autowired
	SmsFileGatewayWsClientFactory smsClientFactory;

	public static final String GEN_TOKEN_MODE = "1";
	public static final String GEN_REF_MODE = "2";
	public static final String GEN_OTP_MODE = "3";

	public static final String MAP_SMS_CODE = "PI_CSS_OTP";
	public static final String MAP_SMS_METHOD_ACTIVATE = "1";
	public static final String MAP_SMS_METHOD_FORGET_USER = "2";
	public static final String MAP_SMS_METHOD_FORGET_PASSWORD = "3";
	public static final String MAP_SMS_METHOD_CHANGE_ADDRESS = "4";
	public static final String MAP_SMS_METHOD_CHANGE_PASSWORD = "5";
	public static final String MAP_SMS_METHOD_EASY_LOAN = "6";

    
    public Boolean sendSmsOtpSuccess(CssUser user, String refNo, String otp, String telNo, String smsMethod, Date expireDate, boolean resetCounter) {
		try {
			checkUnderSMSLimit(user, telNo);
			return sendSmsOtpSuccess(refNo, otp, telNo, smsMethod, expireDate, resetCounter);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}
	private Boolean sendSmsOtpSuccess(String refNo, String otp, String telNo, String smsMethod, Date expireDate, boolean resetCounter) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssJsonAction : sendSmsOtpSuccess ");
		}
		try {
			Message message = new Message();
			StringBuilder sms = new StringBuilder();

			if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_ACTIVATE)) {
				sms.append("รหัส OTP สำหรับยืนยันการสมัครสมาชิกบริการ OceanLife iService คือ ");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_FORGET_USER)) {
				sms.append("รหัส OTP สำหรับขอรับบัญชีผู้ใช้งานบริการ OceanLife iService คือ ");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_FORGET_PASSWORD)) {
				sms.append("รหัส OTP สำหรับขอรับรหัสผ่านบริการ OceanLife iService คือ ");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_CHANGE_ADDRESS)) {
				sms.append("รหัส OTP สำหรับยืนยันแก้ไขข้อมูลส่วนตัวบริการ OceanLife iService คือ ");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_EASY_LOAN)) {
				sms.append("รหัส OTP สำหรับยืนยันการสมัคร Easy Loan(OceanLife iService) คือ ");
			} else {
				return false;
			}
			SimpleDateFormat DF_HHmm = new SimpleDateFormat("HH:mm", new Locale("TH", "th"));
			sms.append(otp).append(" (Ref:").append(refNo).append(") รหัสใช้ได้ภายใน 15 นาที ถึงเวลา ").append(DF_HHmm.format(expireDate));
			message.setMessage(sms.toString());
			/*
			 * LOG.debug(
			 * "=========================================================================="
			 * ); LOG.debug("[ Your sms content ] : "+sms.toString());
			 * LOG.debug("[ Send to ] : "+telNo); LOG.debug(
			 * "=========================================================================="
			 * );
			 */
			message.setMobileNo(telNo);
			smsClientFactory.getSmsGatewayMessageESBService().sendSmsNow(MAP_SMS_CODE, message);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}
	public Boolean sendSmsSuccess(String telNo, String smsMethod) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On CssJsonAction : sendSmsSuccess ");
		}
		try {
			Message message = new Message();
			StringBuilder sms = new StringBuilder();

			if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_ACTIVATE)) {
				sms.append("ยินดีต้อนรับท่านเข้าสู่บริการ OceanLife iService ท่านได้สมัครใช้บริการเรียบร้อยแล้ว");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_FORGET_USER)) {
				sms.append("OceanLife iService ได้แจ้งบัญชีผู้ใช้งานให้ท่านเรียบร้อยแล้ว");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_FORGET_PASSWORD)) {
				sms.append("OceanLife iService ได้แจ้งรหัสผ่านให้ท่านเรียบร้อยแล้ว");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_CHANGE_ADDRESS)) {
				sms.append("OceanLife iService ได้แก้ไขข้อมูลให้ท่านเรียบร้อยแล้ว");
			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_CHANGE_PASSWORD)) {
				sms.append("OceanLife iService ได้เปลี่ยนรหัสผ่านให้ท่านเรียบร้อยแล้ว");
//			} else if (smsMethod.equalsIgnoreCase(MAP_SMS_METHOD_EASY_LOAN)) {
//				sms.append("OceanLife iService ได้เปลี่ยนรหัสผ่านให้ท่านเรียบร้อยแล้ว");
			} else {
				sms.append(smsMethod);
			}
			message.setMessage(sms.toString());
			/*
			 * LOG.debug(
			 * "=========================================================================="
			 * ); LOG.debug("[ Your sms success ] : "+sms.toString());
			 * LOG.debug("[ Send to ] : "+telNo); LOG.debug(
			 * "=========================================================================="
			 * );
			 */
			message.setMobileNo(telNo);
			smsClientFactory.getSmsGatewayMessageESBService().sendSmsNow(MAP_SMS_CODE, message);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}
	public void checkUnderSMSLimit(CssUser user, String mobile) throws Exception {
		boolean isNewInstant = false;
		String userName = user != null ? user.getUsername() : "PUBLIC_EVENT";
		CssConstantEntity constain = cssMasterService.queryCssConstantEntityByKey("LIMIT_SMS");
		CssNotififyLimitEntity limit = cssMasterService.queryCssNotififyLimit(userName, mobile, null);
		if (limit == null) {
			limit = newLimit(userName, mobile, null);
			isNewInstant = true;
		}

		String strHrLimit = constain.getValue1();
		String strDiLimit = constain.getValue2();
		int hourlyLimit = Integer.parseInt(strHrLimit);
		int dailyLimit = Integer.parseInt(strDiLimit);

		Date noSet = new Date();
		if (isNewInstant || limit.getSmsLastSendDate() == null) {
			limit.setSmsCounter(1L);
			limit.setSmsDailyCounter(1L);
			limit.setSmsLastSendDate(noSet);
		} else {
			Date lastSendDate = limit.getSmsLastSendDate();
			long hrLimit = limit.getSmsCounter() == null ? 0 : limit.getSmsCounter();
			long diLimit = limit.getSmsDailyCounter() == null ? 0 : limit.getSmsDailyCounter();
			if (CssConvertUtils.isSameDay(lastSendDate, noSet)) {
				diLimit++;
				if (CssConvertUtils.diffMinute(lastSendDate, noSet) < 60) {
					hrLimit++;
				} else {
					hrLimit = 1;
				}
			} else {
				diLimit = 1;
				hrLimit = 1;
			}

			if (hrLimit > hourlyLimit || diLimit > dailyLimit) {
				throw new CssNotifyLimitException("เกินจำนวน SMS ที่กำหนด");
			}
			limit.setSmsCounter(hrLimit);
			limit.setSmsDailyCounter(diLimit);
		}

		limit.setMobile(mobile);
		limit.setSmsLastSendDate(noSet);
		if (isNewInstant) {
			cssMasterService.insertCssNotififyLimit(limit);
		} else {
			cssMasterService.updateCssNotififyLimit(limit);
		}

	}
	private CssNotififyLimitEntity newLimit(String userName, String mobile, String email) {
		CssNotififyLimitEntity limit = new CssNotififyLimitEntity();
		limit.setUsername(userName);
		limit.setMobile(mobile);
		limit.setEmail(email);
		limit.setEmailCounter(0L);
		limit.setEmailDailyCounter(0L);
		limit.setSmsCounter(0L);
		limit.setSmsDailyCounter(0L);
		return limit;
	}
}
