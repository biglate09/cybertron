package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CSS_NOTIFY_LIMIT")
public class CssNotififyLimitEntity   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "email")
	private String email;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "sms_counter")
	private Long smsCounter;
	@Column(name = "sms_daily_counter")
	private Long smsDailyCounter;
	@Column(name = "email_counter")
	private Long emailCounter;
	@Column(name = "email_daily_counter")
	private Long emailDailyCounter;
	@Column(name = "sms_last_send_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date smsLastSendDate;
	@Column(name = "email_last_send_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date emailLastSendDate;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getSmsCounter() {
		return smsCounter;
	}
	public void setSmsCounter(Long smsCounter) {
		this.smsCounter = smsCounter;
	}
	public Long getSmsDailyCounter() {
		return smsDailyCounter;
	}
	public void setSmsDailyCounter(Long smsDailyCounter) {
		this.smsDailyCounter = smsDailyCounter;
	}
	public Long getEmailCounter() {
		return emailCounter;
	}
	public void setEmailCounter(Long emailCounter) {
		this.emailCounter = emailCounter;
	}
	public Long getEmailDailyCounter() {
		return emailDailyCounter;
	}
	public void setEmailDailyCounter(Long emailDailyCounter) {
		this.emailDailyCounter = emailDailyCounter;
	}
	public Date getSmsLastSendDate() {
		return smsLastSendDate;
	}
	public void setSmsLastSendDate(Date smsLastSendDate) {
		this.smsLastSendDate = smsLastSendDate;
	}
	public Date getEmailLastSendDate() {
		return emailLastSendDate;
	}
	public void setEmailLastSendDate(Date emailLastSendDate) {
		this.emailLastSendDate = emailLastSendDate;
	}

}
