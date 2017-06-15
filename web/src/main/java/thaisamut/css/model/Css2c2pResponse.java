package thaisamut.css.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class Css2c2pResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6430867457092597126L;
	private String version;
	private Date requestTimestamp;
	private String merchantId;
	private String orderId;
	private String invoiceNo;
	private Integer paymentChannel;
	private Integer paymentStatus;
	private String channelResponseCode;
	private String channelResponseDesc;
	private String approvalCode;
	private Integer eci;
	private String transactionDatetime;
	private String transactionRef;
	private String maskedPan;
	private String paidAgent;
	private String paidChannel;
	private String amount;
	private String currency;
	private String userDefined1;
	private String userDefined2;
	private String userDefined3;
	private String userDefined4;
	private String userDefined5;
	private String browserInfo;
	private String storedCardUniqueId;
	private String backendInvoice;
	private Integer recurringUniqueId;
	private String ippPeriod;
	private String ippInterestType;
	private String ippInterestRate;
	private String ippMerchantAbsorbRate;
	private String hashValue;
	
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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
}
