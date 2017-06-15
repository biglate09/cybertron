package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;


@Entity(name = "CSS_RESET_PASSWORD")
@SequenceGenerator(name = "CssResetPasswordId", sequenceName = "seq_css_reset_password", allocationSize = 1)
@NamedQueries({ 
	@NamedQuery(
			name = "CssResetPasswordEntity.findResetPasswordInfoByToken", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.token = :token"
	),
	@NamedQuery(
			name = "CssResetPasswordEntity.findTokenById", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.id = :id"
	), 
	@NamedQuery(
			name = "CssResetPasswordEntity.findExpiredToken", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssResetPasswordEntity o WHERE o.expireDate < 'now'"
	) 
})
public class CssResetPasswordEntity implements Serializable {
	private static final long serialVersionUID = 7730524838045515493L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssResetPasswordId")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "token")
	private String token;

	@Column(name = "expire_date")
	private Date expireDate;
	
	@Transient
    public String GEN_TOKEN_MODE = "1";
	@Transient
    public String GEN_REF_MODE = "2";
	@Transient
    public String GEN_OTP_MODE = "3";

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
