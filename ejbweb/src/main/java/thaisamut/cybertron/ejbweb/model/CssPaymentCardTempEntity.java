package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity(name = "CSS_PAYMENT_CARD_TEMP")
@SequenceGenerator(name = "CssPaymentCardTempSequenceId", sequenceName = "css_payment_card_sequence", allocationSize = 1)
public class CssPaymentCardTempEntity implements Serializable {
	
	private static final long serialVersionUID = -4767211067215001112L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssPaymentCardTempSequenceId")
	private Long id;
	
	@Column(name = "request_no")
	private String requestNo;
	
	@Column(name = "policy_no")
	private String policyNo;
	
	@Column(name = "policy_type")
	private String policyType;
	
	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "send_address_no")
	private String sendAddressNo;
	
	@Column(name = "send_sub_district")
	private String sendSubDistrict;
	
	@Column(name = "send_district")
	private String sendDistrict;
	
	@Column(name = "send_province")
	private String sendProvince;
	
	@Column(name = "send_zip_code")
	private String sendZipCode;
	
	@Column(name = "send_branch_name")
	private String sendBranchName;
	
	@Column(name = "tel_no")
	private String telNo;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "payment_channel")
	private String paymentChannel;
	
	@Column(name = "send_type")
	private String sendType;
	
	@Column(name = "send_status")
	private String sendStatus;
	
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "create_by")
	private String createBy;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "update_by")
	private String updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSendAddressNo() {
		return sendAddressNo;
	}

	public void setSendAddressNo(String sendAddressNo) {
		this.sendAddressNo = sendAddressNo;
	}

	public String getSendSubDistrict() {
		return sendSubDistrict;
	}

	public void setSendSubDistrict(String sendSubDistrict) {
		this.sendSubDistrict = sendSubDistrict;
	}

	public String getSendDistrict() {
		return sendDistrict;
	}

	public void setSendDistrict(String sendDistrict) {
		this.sendDistrict = sendDistrict;
	}

	public String getSendProvince() {
		return sendProvince;
	}

	public void setSendProvince(String sendProvince) {
		this.sendProvince = sendProvince;
	}

	public String getSendZipCode() {
		return sendZipCode;
	}

	public void setSendZipCode(String sendZipCode) {
		this.sendZipCode = sendZipCode;
	}

	public String getSendBranchName() {
		return sendBranchName;
	}

	public void setSendBranchName(String sendBranchName) {
		this.sendBranchName = sendBranchName;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


		
	
	
	
}
