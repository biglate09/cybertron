package thaisamut.cybertron.ejbweb.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import thaisamut.cybertron.ejbweb.model.CssLoanEntity;

public class CssLoanBean implements Serializable {
	public CssLoanBean(){}
	public CssLoanBean(CssLoanEntity orig){
		try {PropertyUtils.copyProperties(this, orig);} catch (Exception e) {}
	}
	private static final long serialVersionUID = 655709828292922075L;
	
	private Long loanId;
	private Date createDate;
	private String customerCardNo;
	private String customerName;
	private String customerSname;
	private String customerTitle;
	private Double loanAmount;
	private Double netBalance;
	private String planCode;
	private String planName;
	private String policyNo;
	private String policyType;
	private Date requestDate;
	private String requestNo;
	private String account;
	private String accountNo;
	private String address;
	private String bankName;
	private Date birthDate;
	private String branch;
	private String branchCode;
	private String branchName;
	private String district;
	private Double duty;
	private String email;
	private String fileNameBookBank;
	private String fileNameIdCard;
	private String fileNameOther1;
	private String fileNameOther2;
	private Double interestAmount;
	private Double loanRate;
	private String mobileNo;
	private String postcode;
	private String province;
	private String requestBy;
	private String status;
	private String subDistrict;
	
	public Long getLoanId() {
		return loanId;
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
		return customerCardNo;
	}
	public void setCustomerCardNo(String customerCardNo) {
		this.customerCardNo = customerCardNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerSname() {
		return customerSname;
	}
	public void setCustomerSname(String customerSname) {
		this.customerSname = customerSname;
	}
	public String getCustomerTitle() {
		return customerTitle;
	}
	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Double getNetBalance() {
		return netBalance;
	}
	public void setNetBalance(Double netBalance) {
		this.netBalance = netBalance;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
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
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestNo() {
		return requestNo;
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
