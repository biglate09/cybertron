package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_PAYMENT")
@SequenceGenerator(name = "CssPaymentId", sequenceName = "seq_css_payment", allocationSize = 1)
public class CssPaymentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3829728817654447334L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssPaymentId")
	private Long id;
	@Column(name = "order_no")
	private String orderNo;
	@Column(name = "invoice_no")
	private String invoiceNo;
	@Column(name = "customer_id")
	private String customerId;
	@Column(name = "customer_id_no")
	private String customerIdNo;
	@Column(name = "customer_title")
	private String customerTitle;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_sname")
	private String customerSname;
	@Column(name = "customer_tel")
	private String customerTel;
	@Column(name = "customer_email")
	private String customerEmail;
	@Column(name = "payment_amount")
	private Double paymentAmount;
	@Column(name = "payment_fee")
	private Double paymentFee;
	@Column(name = "payment_discount")
	private Double paymentDiscount;
	@Column(name = "net_payment")
	private Double netPayment;
	@Column(name = "send_2c2p_status")
	private String send2c2pStatus;
	@Column(name = "send_hydra_status")
	private String sendHydraStatus;
	@Column(name = "policy_no")
	private String policyNo;
	@Column(name = "policy_type")
	private String policyType;
	@Column(name = "policy_year")
	private Integer policyYear;
	@Column(name = "plan_code")
	private String planCode;
	@Column(name = "plan_name")
	private String planName;
	@Column(name = "plan_description")
	private String planDescription;
	@Column(name = "policy_branch_code")
	private String policyBranchCode;
	@Column(name = "policy_branch_name")
	private String policyBranchName;
	@Column(name = "policy_branch_tel")
	private String policyBranchTel;
	@Column(name = "policy_branch_code7")
	private String policyBranchCode7;
	@Column(name = "premium")
	private Double premium;
	@Column(name = "premium_per_month")
	private Double premiunPerMonth;
	@Column(name = "agent_code7")
	private String agentCode7;
	@Column(name = "agent_code5")
	private String agentCode5;
	@Column(name = "version")
	private String version;
	@Column(name = "request_timestamp")
	private Date requestTimestamp;
	@Column(name = "merchant_id")
	private String merchantId;
	@Column(name = "payment_channel")
	private Integer paymentChannel;
	@Column(name = "payment_status")
	private Integer paymentStatus;
	@Column(name = "channel_response_code")
	private String channelResponseCode;
	@Column(name = "channel_response_desc")
	private String channelResponseDesc;
	@Column(name = "approval_code")
	private String approvalCode;
	@Column(name = "eci")
	private Integer eci;
	@Column(name = "transaction_datetime")
	private String transactionDatetime;
	@Column(name = "transaction_ref")
	private String transactionRef;
	@Column(name = "masked_pan")
	private String maskedPan;
	@Column(name = "paid_agent")
	private String paidAgent;
	@Column(name = "paid_channel")
	private String paidChannel;
	@Column(name = "amount")
	private String amount;
	@Column(name = "currency")
	private String currency;
	@Column(name = "user_defined_1")
	private String userDefined1;
	@Column(name = "user_defined_2")
	private String userDefined2;
	@Column(name = "user_defined_3")
	private String userDefined3;
	@Column(name = "user_defined_4")
	private String userDefined4;
	@Column(name = "user_defined_5")
	private String userDefined5;
	@Column(name = "browser_info")
	private String browserInfo;
	@Column(name = "stored_card_unique_id")
	private String storedCardUniqueId;
	@Column(name = "backend_invoice")
	private String backendInvoice;
	@Column(name = "recurring_unique_id")
	private Integer recurringUniqueId;
	@Column(name = "ipp_period")
	private String ippPeriod;
	@Column(name = "ipp_interest_type")
	private String ippInterestType;
	@Column(name = "ipp_interest_rate")
	private String ippInterestRate;
	@Column(name = "ipp_merchant_absorb_rate")
	private String ippMerchantAbsorbRate;
	@Column(name = "hash_value")
	private String hashValue;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "create_by")
	private String createBy;
	@Column(name = "create_system")
	private String createSystem;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "update_by")
	private String updateBy;
	@Column(name = "update_system")
	private String updateSystem;
	
	@Column(name = "letter_type")
	private String letterType;
	@Column(name = "payment_due_date")
	private Date paymentDueDate;
	
	//added 28/11/2559
	@Column(name = "from_year")
	private String fromYear;
	@Column(name = "from_period")
	private String fromPeriod;
	@Column(name = "to_year")
	private String toYear;
	@Column(name = "to_period")
	private String toPeriod;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerIdNo() {
		return customerIdNo;
	}
	public void setCustomerIdNo(String customerIdNo) {
		this.customerIdNo = customerIdNo;
	}
	public String getCustomerTitle() {
		return customerTitle;
	}
	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
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
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public Double getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(Double paymentFee) {
		this.paymentFee = paymentFee;
	}
	public Double getPaymentDiscount() {
		return paymentDiscount;
	}
	public void setPaymentDiscount(Double paymentDiscount) {
		this.paymentDiscount = paymentDiscount;
	}
	public Double getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(Double netPayment) {
		this.netPayment = netPayment;
	}
	public String getSend2c2pStatus() {
		return send2c2pStatus;
	}
	public void setSend2c2pStatus(String send2c2pStatus) {
		this.send2c2pStatus = send2c2pStatus;
	}
	public String getSendHydraStatus() {
		return sendHydraStatus;
	}
	public void setSendHydraStatus(String sendHydraStatus) {
		this.sendHydraStatus = sendHydraStatus;
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
	public Integer getPolicyYear() {
		return policyYear;
	}
	public void setPolicyYear(Integer policyYear) {
		this.policyYear = policyYear;
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
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	public String getPolicyBranchCode() {
		return policyBranchCode;
	}
	public void setPolicyBranchCode(String policyBranchCode) {
		this.policyBranchCode = policyBranchCode;
	}
	public String getPolicyBranchName() {
		return policyBranchName;
	}
	public void setPolicyBranchName(String policyBranchName) {
		this.policyBranchName = policyBranchName;
	}
	public String getPolicyBranchTel() {
		return policyBranchTel;
	}
	public void setPolicyBranchTel(String policyBranchTel) {
		this.policyBranchTel = policyBranchTel;
	}
	public String getPolicyBranchCode7() {
		return policyBranchCode7;
	}
	public void setPolicyBranchCode7(String policyBranchCode7) {
		this.policyBranchCode7 = policyBranchCode7;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Double getPremiunPerMonth() {
		return premiunPerMonth;
	}
	public void setPremiunPerMonth(Double premiunPerMonth) {
		this.premiunPerMonth = premiunPerMonth;
	}
	public String getAgentCode7() {
		return agentCode7;
	}
	public void setAgentCode7(String agentCode7) {
		this.agentCode7 = agentCode7;
	}
	public String getAgentCode5() {
		return agentCode5;
	}
	public void setAgentCode5(String agentCode5) {
		this.agentCode5 = agentCode5;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getRequestTimestamp() {
		return requestTimestamp;
	}
	public void setRequestTimestamp(Date requestTimestamp) {
		this.requestTimestamp = requestTimestamp;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getChannelResponseCode() {
		return channelResponseCode;
	}
	public void setChannelResponseCode(String channelResponseCode) {
		this.channelResponseCode = channelResponseCode;
	}
	public String getChannelResponseDesc() {
		return channelResponseDesc;
	}
	public void setChannelResponseDesc(String channelResponseDesc) {
		this.channelResponseDesc = channelResponseDesc;
	}
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public Integer getEci() {
		return eci;
	}
	public void setEci(Integer eci) {
		this.eci = eci;
	}
	public String getTransactionDatetime() {
		return transactionDatetime;
	}
	public void setTransactionDatetime(String transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}
	public String getTransactionRef() {
		return transactionRef;
	}
	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}
	public String getMaskedPan() {
		return maskedPan;
	}
	public void setMaskedPan(String maskedPan) {
		this.maskedPan = maskedPan;
	}
	public String getPaidAgent() {
		return paidAgent;
	}
	public void setPaidAgent(String paidAgent) {
		this.paidAgent = paidAgent;
	}
	public String getPaidChannel() {
		return paidChannel;
	}
	public void setPaidChannel(String paidChannel) {
		this.paidChannel = paidChannel;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getUserDefined1() {
		return userDefined1;
	}
	public void setUserDefined1(String userDefined1) {
		this.userDefined1 = userDefined1;
	}
	public String getUserDefined2() {
		return userDefined2;
	}
	public void setUserDefined2(String userDefined2) {
		this.userDefined2 = userDefined2;
	}
	public String getUserDefined3() {
		return userDefined3;
	}
	public void setUserDefined3(String userDefined3) {
		this.userDefined3 = userDefined3;
	}
	public String getUserDefined4() {
		return userDefined4;
	}
	public void setUserDefined4(String userDefined4) {
		this.userDefined4 = userDefined4;
	}
	public String getUserDefined5() {
		return userDefined5;
	}
	public void setUserDefined5(String userDefined5) {
		this.userDefined5 = userDefined5;
	}
	public String getBrowserInfo() {
		return browserInfo;
	}
	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}
	public String getStoredCardUniqueId() {
		return storedCardUniqueId;
	}
	public void setStoredCardUniqueId(String storedCardUniqueId) {
		this.storedCardUniqueId = storedCardUniqueId;
	}
	public String getBackendInvoice() {
		return backendInvoice;
	}
	public void setBackendInvoice(String backendInvoice) {
		this.backendInvoice = backendInvoice;
	}
	public Integer getRecurringUniqueId() {
		return recurringUniqueId;
	}
	public void setRecurringUniqueId(Integer recurringUniqueId) {
		this.recurringUniqueId = recurringUniqueId;
	}
	public String getIppPeriod() {
		return ippPeriod;
	}
	public void setIppPeriod(String ippPeriod) {
		this.ippPeriod = ippPeriod;
	}
	public String getIppInterestType() {
		return ippInterestType;
	}
	public void setIppInterestType(String ippInterestType) {
		this.ippInterestType = ippInterestType;
	}
	public String getIppInterestRate() {
		return ippInterestRate;
	}
	public void setIppInterestRate(String ippInterestRate) {
		this.ippInterestRate = ippInterestRate;
	}
	public String getIppMerchantAbsorbRate() {
		return ippMerchantAbsorbRate;
	}
	public void setIppMerchantAbsorbRate(String ippMerchantAbsorbRate) {
		this.ippMerchantAbsorbRate = ippMerchantAbsorbRate;
	}
	public String getHashValue() {
		return hashValue;
	}
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
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
	public String getCreateSystem() {
		return createSystem;
	}
	public void setCreateSystem(String createSystem) {
		this.createSystem = createSystem;
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
	public String getUpdateSystem() {
		return updateSystem;
	}
	public void setUpdateSystem(String updateSystem) {
		this.updateSystem = updateSystem;
	}
	public String getLetterType() {
		return letterType;
	}
	public void setLetterType(String letterType) {
		this.letterType = letterType;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public String getFromYear() {
		return fromYear;
	}
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}
	public String getFromPeriod() {
		return fromPeriod;
	}
	public void setFromPeriod(String fromPeriod) {
		this.fromPeriod = fromPeriod;
	}
	public String getToYear() {
		return toYear;
	}
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	public String getToPeriod() {
		return toPeriod;
	}
	public void setToPeriod(String toPeriod) {
		this.toPeriod = toPeriod;
	}
	
}
