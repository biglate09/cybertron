package thaisamut.css.email;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import thaisamut.css.exception.CssNotifyLimitException;
import thaisamut.css.model.CssUser;
import thaisamut.cybertron.ejbweb.model.CssConstantEntity;
import thaisamut.cybertron.ejbweb.model.CssNotififyLimitEntity;
import thaisamut.cybertron.ejbweb.remote.CssMasterService;
import thaisamut.omailwsclient.component.OMailWsClientFactory;
import thaisamut.osgi.targetbundles.dmsws.search.v1.DmsDocument;
import thaisamut.osgi.targetbundles.omail.v1.Email;
import thaisamut.osgi.targetbundles.omailws.sending.v2.Attachment;
import thaisamut.util.CssConvertUtils;

public class CssEmailConnectionService implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1820917024091488466L;
    private static final Logger LOG = LoggerFactory.getLogger(CssEmailConnectionService.class);
	@Autowired
	private CssMasterService cssMasterService;
	@Autowired
	private OMailWsClientFactory oMailWsClientFactory;
	
	public static final String MAP_EMAIL_CONFIRM_SIGNUP_METHOD = "0";
	public static final String MAP_EMAIL_RESET_PASSWORD_METHOD = "1";
	public static final String MAP_EMAIL_GET_USER_ID_METHOD = "2";
	public static final String MAP_EMAIL_CHANGE_ADDRESS_METHOD = "3";
	public static final String MAP_EMAIL_CHANGE_PASSWORD_METHOD = "4";

	
	
	public void sendLimitEmail(CssUser user, String sender, String recipient, String subject, String body) throws Exception {
		checkUnderEmailLimit(user, recipient);
		sendEmail(sender, recipient, subject, body);

	}
	public void sendEmail(String sender, String recipient, String subject, String body) throws Exception {
		Email m = new Email();
		m.setFrom(sender);
		m.getTo().add(recipient.toLowerCase());
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);
		oMailWsClientFactory.getoMailWsService().sendEmail(m);
	}
	public void sendEmail(String sender, String[] recipients, String subject, String body) throws Exception {
		Email m = new Email();
		m.setFrom(sender);
		if (recipients != null && recipients.length > 0) {
			for (String recipient : recipients) {
				m.getTo().add(recipient.toLowerCase());
			}
		}
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);
		oMailWsClientFactory.getoMailWsService().sendEmail(m);
	}
	public void sendEmail(String sender, String[] recipients, String[] cc, String[] bcc, String subject, String body) throws Exception {
		Email m = new Email();
		m.setFrom(sender);
		if (recipients != null && recipients.length > 0) {
			for (String recipient : recipients) {
				m.getTo().add(recipient.toLowerCase());
			}
		}
		if (cc != null && cc.length > 0) {
			for (String recipient : cc) {
				m.getCc().add(recipient);
			}
		}
		if (bcc != null && bcc.length > 0) {
			for (String recipient : bcc) {
				m.getBcc().add(recipient);
			}
		}
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);
		oMailWsClientFactory.getoMailWsService().sendEmail(m);
	}
	public void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, List<InputStream> attachFileList) throws Exception {
		InputStream[] array = new InputStream[0];
		if (attachFileList != null && !attachFileList.isEmpty()) {
			array = (InputStream[]) attachFileList.toArray();
		}
		sendEmailWithAttachFile(sender, recipient, subject, body, array);
	}
	public void sendLimitEmailWithAttachFile(CssUser user, String sender, String recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		checkUnderEmailLimit(user, recipient);
		sendEmailWithAttachFile(sender, recipient, subject, body, attachFileList);
	}
	public void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		thaisamut.osgi.targetbundles.omailws.sending.v2.Email m = new thaisamut.osgi.targetbundles.omailws.sending.v2.Email();
		m.setFrom(sender);
		m.getTo().add(recipient.toLowerCase());
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);

		String FILENAME = "report-petition-modify.pdf";
		String MIMETYPE = "text/plain";

		Attachment attach = null;
		ByteArrayInputStream inputStream = null;
		for (InputStream i : attachFileList) {
			if (i != null) {
				attach = new Attachment();
				attach.setFileName(FILENAME);
				attach.setMimeType(MIMETYPE);
				attach.setContent(IOUtils.toByteArray(i));
				m.getAttachments().add(attach);
			}
		}
		oMailWsClientFactory.getOmailWsSendingV2ESBService().sendEmail(m);
	}
	public void sendMultiEmailWithAttachFile(String sender, List<String> recipient, String subject, String body, InputStream... attachFileList) throws Exception {
		thaisamut.osgi.targetbundles.omailws.sending.v2.Email m = new thaisamut.osgi.targetbundles.omailws.sending.v2.Email();
		m.setFrom(sender);
		for (String email : recipient) {
			m.getTo().add(email.toLowerCase());
		}
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);

		String FILENAME = "report-petition-modify.pdf";
		String MIMETYPE = "text/plain";

		Attachment attach = null;
		ByteArrayInputStream inputStream = null;
		for (InputStream i : attachFileList) {
			if (i != null) {
				attach = new Attachment();
				attach.setFileName(FILENAME);
				attach.setMimeType(MIMETYPE);
				attach.setContent(IOUtils.toByteArray(i));
				m.getAttachments().add(attach);
			}
		}
		oMailWsClientFactory.getOmailWsSendingV2ESBService().sendEmail(m);
	}
	public void sendLimitEmailWithAttachFile(CssUser user, String sender, String recipient, String subject, String body, DmsDocument... attachFileList) throws Exception {
		checkUnderEmailLimit(user, recipient);
		sendEmailWithAttachFile(sender, recipient, subject, body, attachFileList);

	}

	public void sendEmailWithAttachFile(String sender, String recipient, String subject, String body, DmsDocument... attachFileList) throws Exception {
		thaisamut.osgi.targetbundles.omailws.sending.v2.Email m = new thaisamut.osgi.targetbundles.omailws.sending.v2.Email();
		m.setFrom(sender);
		m.getTo().add(recipient.toLowerCase());
		m.setIsHtmlMessage(true);
		m.setSubject(subject);
		m.setBody(body);

		Attachment attach = null;
		for (DmsDocument document : attachFileList) {
			if (document != null) {
				attach = new Attachment();
				attach.setFileName(document.getFileName());
				attach.setMimeType(document.getMimeType());
				attach.setContent(document.getContent());
				m.getAttachments().add(attach);
			}
		}
		oMailWsClientFactory.getOmailWsSendingV2ESBService().sendEmail(m);
	}
	public String sendEmailWithAttachFile(String sender, String recipient, String subject, String body, List<String> ccList, DmsDocument... attachFileList) throws Exception {
		try {
			thaisamut.osgi.targetbundles.omailws.sending.v2.Email m = new thaisamut.osgi.targetbundles.omailws.sending.v2.Email();
			m.setFrom(sender);
			m.getTo().add(recipient.toLowerCase());
			for (String cc : ccList) {
				m.getCc().add(cc);
			}
			m.setIsHtmlMessage(true);
			m.setSubject(subject);
			m.setBody(body);

			Attachment attach = null;
			for (DmsDocument document : attachFileList) {
				if (document != null) {
					attach = new Attachment();
					attach.setFileName(document.getFileName());
					attach.setMimeType(document.getMimeType());
					attach.setContent(document.getContent());
					m.getAttachments().add(attach);
				}
			}
			oMailWsClientFactory.getOmailWsSendingV2ESBService().sendEmail(m);
			return StringUtils.EMPTY;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public void checkUnderEmailLimit(CssUser user, String email) throws Exception {
		boolean isNewInstant = false;
		String userName = user != null ? user.getUsername() : "PUBLIC_EVENT";
		CssConstantEntity constain = cssMasterService.queryCssConstantEntityByKey("LIMIT_EMAIL");
		CssNotififyLimitEntity limit = cssMasterService.queryCssNotififyLimit(userName, null, email);
		if (limit == null) {
			limit = newLimit(userName, null, email);
			isNewInstant = true;
		}

		String strHrLimit = constain.getValue1();
		String strDiLimit = constain.getValue2();
		int hourlyLimit = Integer.parseInt(strHrLimit);
		int dailyLimit = Integer.parseInt(strDiLimit);

		Date noSet = new Date();
		if (isNewInstant || limit.getEmailLastSendDate() == null) {
			limit.setEmailCounter(1L);
			limit.setEmailDailyCounter(1L);
			limit.setEmailLastSendDate(noSet);
		} else {
			Date lastSendDate = limit.getEmailLastSendDate();
			long hrLimit = limit.getEmailCounter() == null ? 0 : limit.getEmailCounter();
			long diLimit = limit.getEmailDailyCounter() == null ? 0 : limit.getEmailDailyCounter();
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
				throw new CssNotifyLimitException("เกินจำนวนอีเมลที่กำหนด");
			}
			limit.setEmailCounter(hrLimit);
			limit.setEmailDailyCounter(diLimit);
		}

		limit.setEmail(email);
		limit.setEmailLastSendDate(new Date());
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
