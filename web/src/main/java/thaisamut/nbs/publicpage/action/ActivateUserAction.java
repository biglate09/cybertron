package thaisamut.nbs.publicpage.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.commons.security.UserSession;
import thaisamut.commons.struts2.SpringUtils;
import thaisamut.css.data.service.CssMasterDataService;
import thaisamut.css.email.CssEmailConnectionService;
import thaisamut.css.ldap.LDAPConnectionService;
import thaisamut.css.model.CssUser;
import thaisamut.css.sms.CssSMSConnectionService;
import thaisamut.css.struts.action.CssJsonAction;
import thaisamut.cybertron.ejbweb.model.CssMemberEntity;
import thaisamut.cybertron.ejbweb.remote.CssMemberService;
import thaisamut.nbs.struts.action.JsonAction;
import thaisamut.omailwsclient.component.OMailWsClientFactory;

public class ActivateUserAction extends CssJsonAction {
	private static final long serialVersionUID = -3553908004058249289L;
	private static final Logger LOG = LoggerFactory.getLogger(ActivateUserAction.class);

	@Autowired
	CssMemberService cssMemberService;
	@Autowired
	OMailWsClientFactory oMailWsClientFactory;
	@Autowired
	LDAPConnectionService ldapService;
	@Autowired
	CssMasterDataService cssMasterDataService;

	private String confirmMode;
	private String email;
	private Boolean flagSaveEmail;
	private String sessionId;
	private String userName;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String changePassword() throws Exception {
		CssUser u = getSessionUser();
		if (u != null) {
			userName = u.getUsername();
		}
		setSessionId(request.getSession().getId());
		checkTokenExpire();
		setCaptchaTimeStamp(new Date().toString());
		return SUCCESS;
	}
	public String activatedAccount() throws Exception {
		CssUser cssUser = (CssUser) request.getSession().getAttribute(CssUser.SESSION_CSS_USER);
		if (cssUser == null) {
			try {
				UserSession user = getUserSession(request.getSession());
				CssMemberEntity member = cssMemberService.findMemberByUserName(user.getLoginID());
				if (member != null) {
					cssUser = setUser(member);
					request.getSession().setAttribute(CssUser.SESSION_CSS_USER, cssUser);
				} else {

				}
			} catch (Exception e) {
				LOG.error(String.format("AuthorizationInterceptor inActivate copy cssUser fail : %1$s", e.getMessage()), e);
			}
		}
		if (cssUser != null && "A".equals(cssUser.getStatus())) {
			return "none";
		}

		sessionAttributes.remove(PRECESS_KEY_SIGNUP);
		return SUCCESS;

	}
	private UserSession getUserSession(HttpSession session) {
		UserSession user = (UserSession) session.getAttribute(UserSession.KEY);

		if (null == user) {
			session.setAttribute(UserSession.KEY, user = SpringUtils.getBean(UserSession.class));
		}

		return user;
	}
	private CssUser setUser(CssMemberEntity member) {
		CssUser user = null;
		if (member != null) {
			user = new CssUser();
			user.setUsername(member.getUsername());
			user.setFullname(member.getFullname());
			user.setCardNo(member.getCardNo());
			user.setBirthDate(member.getBirthDate());
			user.setTelNo(member.getTelNo());
			user.setEmail(member.getEmail());
			user.setStatus(member.getStatus());
			user.setCustCode(member.getCustCode());
			user.setTitleDesc(member.getTitleDesc());
			user.setFirstName(member.getFirstName());
			user.setLastName(member.getLastName());
		}
		return user;
	}
	public String performActivatedAccount() throws Exception {
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				Boolean success = false;
				try {
					LOG.debug("~ On ActivateUserAction : performActivatedAccount");
					String userName = getLoginName();
					CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
					if (confirmMode.equalsIgnoreCase("email")) {
						Boolean addMemberWaitingSuccess = false;
						Boolean isDuplicateEmail = false;

						if (StringUtils.isNotBlank(member.getEmail())) {

							// Set email เป็น Lower case
							email = member.getEmail().toLowerCase();

							// check duplicate email
							if (cssMasterDataService.isExistEmail(email, userName)) {
								isDuplicateEmail = true;
							} else {
								sessionId = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_TOKEN_MODE);
								addMemberWaitingSuccess = cssMemberService.processAddTokenForEmail(sessionId, getLoginName());
							}

							if (addMemberWaitingSuccess) {

								// กรณีไม่มีอีเมลในระบบ ให้ Update อีเมล
								// และเพิ่มใน ldap
								if (StringUtils.isBlank(member.getEmail()) && flagSaveEmail) {
									// Add email for ldap
									ldapService.updateEmail(member.getCardNo(), email);

									// Update email for member
									member.setEmail(email);
									member.setUpdateDate(new Date());
									cssMemberService.updateMember(member);
								}

								String emailContent = cssMemberService.getEmailContent(sessionId, CssEmailConnectionService.MAP_EMAIL_CONFIRM_SIGNUP_METHOD, getBaseUrl(), member.getUsername());

								try {
									sendEmail(DEFAULT_EMAIL_SENDER_NAME, email, "ยืนยันการสมัครสมาชิกบริการ OceanLife iService บมจ.ไทยสมุทรประกันชีวิต", emailContent);
								} catch (Exception ex) {
									LOG.error(String.format("ไม่สามารถส่งมีเมลยืนยันไปที่ %s", email), ex);
								}
								success = true;
								data.put("email", email);
							}
							data.put("isDuplicateEmail", isDuplicateEmail);

						}else{
							data.put("WARNING", "ไม่มีข้อมูลอีเมลในระบบ");
						}
					} else {
						String refNo = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_REF_MODE);
						String otp = cssMemberService.nextSessionId(CssSMSConnectionService.GEN_OTP_MODE);
						String telNo = member.getTelNo();
						Date expireDate = getNext15Minutes();
						String tokenId = cssMemberService.processAddTokenForOtp(refNo, otp, getLoginName(), expireDate);

						sessionAttributes.put("telNo", telNo);
						sessionAttributes.put("refNo", refNo);
						sessionAttributes.put("tokenId", tokenId);

						// Send otp SMS
						CssUser user = new CssUser();
						user.setUsername(member.getUsername());
						success = sendSmsOtpSuccess(user, refNo, otp, telNo, CssSMSConnectionService.MAP_SMS_METHOD_ACTIVATE, expireDate, true);
						if (success == false) {
							data.put("WARNING", "ไม่สามารถขอรหัส OTP ได้ เนื่องจากเกินจำนวนครั้งที่บริษัทกำหนด");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				data.put("success", success);
				result.setData(data);
			}
		}).run();
	}

	public String getEmailExist() throws Exception {
		return (new JsonAction.Processor() {
			@SuppressWarnings("unchecked")
			@Override
			protected void perform() throws Exception {
				Map<String, Object> data = new HashMap<String, Object>();
				String email = StringUtils.EMPTY;
				try {
					LOG.debug("~ On ActivateUserAction : getEmailExist");
					CssMemberEntity member = cssMemberService.findMemberByUserName(getLoginName());
					if (null != member && StringUtils.isNotBlank(member.getEmail())) {
						email = member.getEmail();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				data.put("email", email);
				result.setData(data);
			}
		}).run();
	}

	public String getConfirmMode() {
		return confirmMode;
	}
	public void setConfirmMode(String confirmMode) {
		this.confirmMode = confirmMode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getFlagSaveEmail() {
		return flagSaveEmail;
	}
	public void setFlagSaveEmail(Boolean flagSaveEmail) {
		this.flagSaveEmail = flagSaveEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
