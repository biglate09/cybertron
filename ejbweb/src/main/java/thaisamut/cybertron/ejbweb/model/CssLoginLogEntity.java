package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_LOGIN_LOG")
@SequenceGenerator(name = "CssLoginLogId", sequenceName = "seq_css_login_log", allocationSize = 1)
@NamedQueries({ 
	@NamedQuery(
			name = "CssLoginLogEntity.findLoginLogByUsername", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssLoginLogEntity o WHERE o.username = :username ORDER BY o.loginDate DESC"
	)
})
public class CssLoginLogEntity implements Serializable {
	private static final long serialVersionUID = -6920862159779541621L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssLoginLogId")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "logout_date")
	private Date logoutDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

}
