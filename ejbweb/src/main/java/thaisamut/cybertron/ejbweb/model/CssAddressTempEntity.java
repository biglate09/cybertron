package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_ADDRESS_TEMP")
@SequenceGenerator(name = "CssAddressTempId", sequenceName = "seq_css_address_temp", allocationSize = 1)
public class CssAddressTempEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7660140387043388560L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssAddressTempId")
	private Long id;
	@Column(name = "policy_no")
	String policyNo;
	@Column(name = "pol_type")
	String polType;
	@Column(name = "addr")
	String addr;
	@Column(name = "sdt_desc")
	String sdtDesc;
	@Column(name = "dt_desc")
	String dtDesc;
	@Column(name = "dt_code")
	String dtCode;
	@Column(name = "pv_desc")
	String pvDesc;
	@Column(name = "pv_code")
	String pvCode;
	@Column(name = "zip_code")
	String zipCode;
	@Column(name = "mobile1")
	String mobile1;
	@Column(name = "mobile2")
	String mobile2;
	@Column(name = "phone1")
	String phone1;
	@Column(name = "ext1")
	String ext1;
	@Column(name = "phone2")
	String phone2;
	@Column(name = "ext2")
	String ext2;
	@Column(name = "email")
	String email;
	@Column(name = "token")
	String token;
	@Column(name = "send_status")
	String sendStatus;
	@Column(name = "create_date")
	Date createDate;
	@Column(name = "create_by")
	String createBy;
	@Column(name = "update_date")
	Date updateDate;
	@Column(name = "update_by")
	String updateBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getPolType() {
		return polType;
	}
	public void setPolType(String polType) {
		this.polType = polType;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getSdtDesc() {
		return sdtDesc;
	}
	public void setSdtDesc(String sdtDesc) {
		this.sdtDesc = sdtDesc;
	}
	public String getDtDesc() {
		return dtDesc;
	}
	public void setDtDesc(String dtDesc) {
		this.dtDesc = dtDesc;
	}
	public String getDtCode() {
		return dtCode;
	}
	public void setDtCode(String dtCode) {
		this.dtCode = dtCode;
	}
	public String getPvDesc() {
		return pvDesc;
	}
	public void setPvDesc(String pvDesc) {
		this.pvDesc = pvDesc;
	}
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
