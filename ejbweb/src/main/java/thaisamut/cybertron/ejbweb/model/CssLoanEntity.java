package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the css_loan database table.
 * 
 */
@Entity
@Table(name="css_loan")
@NamedQuery(name="CssLoanEntity.findAll", query="SELECT c FROM CssLoanEntity c")
public class CssLoanEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3856673614854567860L;

	@Id
	@SequenceGenerator(name="CSS_LOAN_LOANID_GENERATOR", sequenceName="SEQ_CSS_LOAN_SEQUENCE",allocationSize=1,initialValue=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CSS_LOAN_LOANID_GENERATOR")
	@Column(name="loan_id")
	private Long loanId;

	@Column(name="create_date")
	private Date createDate;

	@Column(name="customer_card_no")
	private String customerCardNo;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="customer_sname")
	private String customerSname;

	@Column(name="customer_title")
	private String customerTitle;

	@Column(name="loan_amount")
	private Double loanAmount;

	@Column(name="net_balance")
	private Double netBalance;

	@Column(name="plan_code")
	private String planCode;

	@Column(name="plan_name")
	private String planName;

	@Column(name="policy_no")
	private String policyNo;

	@Column(name="policy_type")
	private String policyType;

	@Column(name="request_date")
	private Date requestDate;

	@Column(name="request_no")
	private String requestNo;
	
	@Column(name="account")
	private String account;

	@Column(name="account_no")
	private String accountNo;

	@Column(name="address")
	private String address;

	@Column(name="bank_name")
	private String bankName;

	@Column(name="birth_date")
	private Date birthDate;

	@Column(name="branch")
	private String branch;

	@Column(name="branch_code")
	private String branchCode;

	@Column(name="branch_name")
	private String branchName;

	@Column(name="district")
	private String district;

	@Column(name="duty")
	private Double duty;

	@Column(name="email")
	private String email;

	@Column(name="file_name_book_bank")
	private String fileNameBookBank;

	@Column(name="file_name_id_card")
	private String fileNameIdCard;

	@Column(name="file_name_other1")
	private String fileNameOther1;

	@Column(name="file_name_other2")
	private String fileNameOther2;

	@Column(name="interest_amount")
	private Double interestAmount;

	@Column(name="loan_rate")
	private Double loanRate;

	@Column(name="mobile_no")
	private String mobileNo;

	@Column(name="postcode")
	private String postcode;

	@Column(name="province")
	private String province;

	@Column(name="request_by")
	private String requestBy;

	@Column(name="status")
	private String status;

	@Column(name="sub_district")
	private String subDistrict;

	public CssLoanEntity() {
	}

	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCustomerCardNo() {
		return this.customerCardNo;
	}

	public void setCustomerCardNo(String customerCardNo) {
		this.customerCardNo = customerCardNo;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSname() {
		return this.customerSname;
	}

	public void setCustomerSname(String customerSname) {
		this.customerSname = customerSname;
	}

	public String getCustomerTitle() {
		return this.customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getNetBalance() {
		return this.netBalance;
	}

	public void setNetBalance(Double netBalance) {
		this.netBalance = netBalance;
	}

	public String getPlanCode() {
		return this.planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Double getDuty() {
		return duty;
	}

	public void setDuty(Double duty) {
		this.duty = duty;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileNameBookBank() {
		return fileNameBookBank;
	}

	public void setFileNameBookBank(String fileNameBookBank) {
		this.fileNameBookBank = fileNameBookBank;
	}

	public String getFileNameIdCard() {
		return fileNameIdCard;
	}

	public void setFileNameIdCard(String fileNameIdCard) {
		this.fileNameIdCard = fileNameIdCard;
	}

	public String getFileNameOther1() {
		return fileNameOther1;
	}

	public void setFileNameOther1(String fileNameOther1) {
		this.fileNameOther1 = fileNameOther1;
	}

	public String getFileNameOther2() {
		return fileNameOther2;
	}

	public void setFileNameOther2(String fileNameOther2) {
		this.fileNameOther2 = fileNameOther2;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubDistrict() {
		return subDistrict;
	}

	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
