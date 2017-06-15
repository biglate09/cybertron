package thaisamut.nbs.publicpage.action;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.exception.CssIntegrityException;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.util.CssConvertUtils;

public class ChangePasswordAction extends CssJsonAction {
	private static final long serialVersionUID = 1333283036229733532L;
	private static final Logger LOG = LoggerFactory.getLogger(ChangePasswordAction.class);

	@Autowired
	private CssMemberService cssMemberService;

	@Autowired
	private LDAPConnectionService ldapService;

	public Parameters params = new Parameters();

	public String checkuser() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ChangePasswordAction : checkuser ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String username = params.username;
				String password = params.oldpassword;
				Boolean validatePassword = ldapService.validatePassword(username, password);
				String resultsMsg = StringUtils.EMPTY;
				String captchaTimeStamp = params.captchaTimeStamp;
				String captchaParam = captchaTimeStamp+"|"+params.captchaCode;
				Boolean success = false;

				if (validatePassword) {
					if (!params.newpassword.equalsIgnoreCase(params.confirmnewpassword)) {
						resultsMsg = "รหัสผ่านใหม่กับยืนยันรหัสผ่านไม่ตรงกัน";
					} else if(!ObjectUtils.defaultIfNull((Boolean)sessionAttributes.get(captchaParam), false)){
						resultsMsg = "กรอก Security Code ไม่ถูกต้อง";
					}else {
						// remove captcha
						sessionAttributes.remove(captchaParam);
						// ทำการเปลี่ยนรหัสผ่าน
						success = ldapService.updatePassword(username, params.newpassword);
						if (success) {
							CssMemberEntity member = cssMemberService.findMemberByUserName(username);
							String content = cssMemberService.getEmailContent(params.sessionId, CssEmailConnectionService.MAP_EMAIL_CHANGE_PASSWORD_METHOD, getBaseUrl(), params.username);

							// Send email success กรณีมีข้อมูลอีเมลในระบบ
							if (StringUtils.isNotBlank(member.getEmail())) {
								try {
									sendEmail(DEFAULT_EMAIL_SENDER_NAME, member.getEmail(), "ยืนยันการเปลี่ยนรหัสผ่าน (Password) บริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", content);
								} catch (Exception ex) {
									LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", member.getEmail()), ex);
								}
							}

							// Send sms success
							sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_PASSWORD);

							resultsMsg = "เปลี่ยนรหัสผ่านเรียบร้อยแล้ว";
						} else {
							resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
						}
					}
				} else {
					resultsMsg = "บัญชีผู้ใช้งานหรือรหัสผ่านเดิมไม่ถูกต้อง";
				}

				data.put("success", success);
				data.put("resultsMsg", resultsMsg);
				result.setData(data);
			}
		}).run();
	}

	public String resetpassword() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ChangePasswordAction : resetpassword ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String resultsMsg = StringUtils.EMPTY;
				Boolean success = false;
				String captchaTimeStamp = params.captchaTimeStamp;
				String captchaParam = captchaTimeStamp+"|"+params.captchaCode;
				if (!params.newpassword.equalsIgnoreCase(params.confirmnewpassword)) {
					resultsMsg = "รหัสผ่านใหม่กับยืนยันรหัสผ่านไม่ตรงกัน";
				} else if(!ObjectUtils.defaultIfNull((Boolean)sessionAttributes.get(captchaParam), false)){
					resultsMsg = "กรอก Security Code ไม่ถูกต้อง";
				}else{

					// remove captcha
					sessionAttributes.remove(captchaParam);
					// ทำการ reset รหัสผ่าน
					CssResetPasswordEntity reset = cssMemberService.findResetPasswordByToken(params.sessionId);

					success = ldapService.updatePassword(reset.getUsername(), params.newpassword);
					if (success) {
						// ลบข้อมูล Token ใน DB
						cssMemberService.removeResetPasswordInfoByEntity(reset);

						// Send sms success
						CssMemberEntity member = cssMemberService.findMemberByUserName(reset.getUsername());
						sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_PASSWORD);

						resultsMsg = "เปลี่ยนรหัสผ่านเรียบร้อยแล้ว";
					} else {
						resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
					}
				}

				data.put("success", success);
					data.put("resultsMsg", resultsMsg);
				result.setData(data);
			}
		}).run();
	}
	public String secureResetpassword() throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("~ On ChangePasswordAction : resetpassword ");
		}
		return (new JsonAction.Processor() {
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String resultsMsg = StringUtils.EMPTY;
				Boolean success = false;
				String username = params.username;
				String password = params.oldpassword;
				String newPassword = params.newpassword;
				String confirmPassword = params.confirmnewpassword;
				String captchaTimeStamp = params.captchaTimeStamp;
				String captchaParam = captchaTimeStamp+"|"+params.captchaCode;
				Boolean validatePassword = ldapService.validatePassword(username, password);
				if (validatePassword) {
					try {
						doubleCheckUp(username, password, newPassword, confirmPassword);
						if (!params.newpassword.equalsIgnoreCase(params.confirmnewpassword)) {
							resultsMsg = "รหัสผ่านใหม่กับยืนยันรหัสผ่านไม่ตรงกัน";
						}else if(!ObjectUtils.defaultIfNull((Boolean)sessionAttributes.get(captchaParam), false)){
							resultsMsg = "กรอก Security Code ไม่ถูกต้อง";
						} else {
							// remove captcha
							sessionAttributes.remove(captchaParam);
							// ทำการ reset รหัสผ่าน
							success = ldapService.updatePassword(params.username, params.newpassword);
							if (success) {

								CssMemberEntity member = cssMemberService.findMemberByUserName(params.username);
								String content = cssMemberService.getEmailContent(params.sessionId, CssEmailConnectionService.MAP_EMAIL_CHANGE_PASSWORD_METHOD, getBaseUrl(), params.username);

								// Send email success
								// กรณีมีข้อมูลอีเมลในระบบ
								if (StringUtils.isNotBlank(member.getEmail())) {
									try {
										sendEmail(DEFAULT_EMAIL_SENDER_NAME, member.getEmail(), "ยืนยันการเปลี่ยนรหัสผ่าน (Password) บริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", content);
									} catch (Exception ex) {
										LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", member.getEmail()), ex);
									}
								}

								// Send sms success
								sendSmsSuccess(member.getTelNo(), CssSMSConnectionService.MAP_SMS_METHOD_CHANGE_PASSWORD);
								resultsMsg = "เปลี่ยนรหัสผ่านเรียบร้อยแล้ว";
							} else {
								resultsMsg = "เกิดข้อผิดพลาดกรุณาติดต่อผู้ดูแลระบบ";
							}
						}

					} catch (CssIntegrityException css) {
						resultsMsg = css.getMessage();

					}
				} else {
					resultsMsg = "รหัสผ่านเดิมไม่ถูกต้อง";
				}

				data.put("success", success);
				data.put("resultsMsg", resultsMsg);
				result.setData(data);
			}
		}).run();
	}
	private boolean doubleCheckUp(String username, String password, String newPassword, String confirmPassword) throws CssIntegrityException {
		StringBuilder sb = new StringBuilder(2000);
		boolean isStrongPassword = CssConvertUtils.isStrongPassword(newPassword);

		if (StringUtils.isBlank(password))
			sb.append("กรอกรหัสผ่านเดิม").append("<br/>");
		if (StringUtils.isBlank(newPassword))
			sb.append("กรอกรหัสผ่านใหม่").append("<br/>");
		if (StringUtils.isBlank(confirmPassword))
			sb.append("กรอกยืนยันรหัสผ่านใหม่").append("<br/>");
		if (newPassword.length() < 8 )
			sb.append("รหัสผ่านอย่างน้อย 8 หลัก").append("<br/>");
		if (password.equals(newPassword))
			sb.append("รหัสผ่านเดิมและรหัสผ่านใหม่ต้องไม่ตรงกัน").append("<br/>");
		if (!confirmPassword.equals(newPassword))
			sb.append("รหัสผ่านไม่ตรงกัน").append("<br/>");
		if (!isStrongPassword)
			sb.append("รหัสผ่านคาดเดาง่ายเกินไป").append("<br/>");

		if (sb.length() > 0) {
			throw new CssIntegrityException(sb.toString());
		}
		return true;
	}

	public class Parameters implements Serializable {
		private static final long serialVersionUID = 7608370141686856955L;
		private String username;
		private String oldpassword;
		private String newpassword;
		private String confirmnewpassword;
		private String captchaCode;
		private String sessionId;
		private String captchaTimeStamp;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getOldpassword() {
			return oldpassword;
		}

		public void setOldpassword(String oldpassword) {
			this.oldpassword = oldpassword;
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

		public String getCaptchaCode() {
			return captchaCode;
		}

		public void setCaptchaCode(String captchaCode) {
			this.captchaCode = captchaCode;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getCaptchaTimeStamp() {
			return captchaTimeStamp;
		}

		public void setCaptchaTimeStamp(String captchaTimeStamp) {
			this.captchaTimeStamp = captchaTimeStamp;
		}

	}

}
