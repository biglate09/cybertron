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

import org.apache.commons.lang3.StringUtils;

@Entity(name = "CSS_MEMBER")
@SequenceGenerator(name = "CssMemberId", sequenceName = "seq_css_member", allocationSize = 1)
@NamedQueries({ 
	@NamedQuery(
			name = "CssMemberEntity.findMemberByUsername", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE lower(o.username) = lower(:username) order by id desc"
	),
	@NamedQuery(
			name = "CssMemberEntity.findMemberByEmail", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE lower(o.email) = lower(:email)"
	),
	@NamedQuery(
			name = "CssMemberEntity.findMemberByCardId", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE o.cardNo = :cardNo"
	),
	@NamedQuery(
			name = "CssMemberEntity.findMemberByTelNo", 
			query = "SELECT o FROM thaisamut.cybertron.ejbweb.model.CssMemberEntity o WHERE o.telNo = :telNo"
	)
})
public class CssMemberEntity implements Serializable {
	private static final long serialVersionUID = 8954427276226964137L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssMemberId")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "card_no")
	private String cardNo;
	
	@Column(name = "title_desc")
	private String titleDesc;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "birth_date")
	private String birthDate;

	@Column(name = "tel_no")
	private String telNo;

	@Column(name = "email")
	private String email;

	@Column(name = "status")
	private String status;

	@Column(name = "count_login_fail")
	private Long countLoginFail;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "update_by")
	private String updateBy;
	
	@Column(name = "cust_code")
	private String custCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustCode(){
		return this.custCode;
	}
	public void setCustCode(String custCode){
		this.custCode = custCode;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCountLoginFail() {
		return countLoginFail;
	}

	public void setCountLoginFail(Long countLoginFail) {
		this.countLoginFail = countLoginFail;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullname() {
		return StringUtils
				.defaultIfEmpty(getTitleDesc(), StringUtils.EMPTY)
				.concat(StringUtils.defaultIfEmpty(getFirstName(),StringUtils.EMPTY))
				.concat(" ")
				.concat(StringUtils.defaultIfEmpty(getLastName(),StringUtils.EMPTY));
	}
}
