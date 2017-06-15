package thaisamut.css.model;

import java.io.Serializable;

import thaisamut.css.dwh.service.pojo.PolicyBean;

public class Css2c2pRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184810211472419927L;
	private String version;
	private String merchantId;
	private String paymentDescription;
	private String orderId;
	private String invoiceNo;
	private String currency;
	private String amount;
	private String customerEmail;
	private String resultUrl1;
	private String resultUrl2;
	private String hashValue;
	private PolicyBean policy;
	private String premiumTemp;
	private String premiumPerMonthTemp;
	private Integer premium;
	private Integer premiumPerMonth;
	private String letterYear;
	private String letterMonth;
	private String fromYear;
	private String fromPeriod;
	private String toYear;
	private String toPeriod;
	private String nlChk;
	
	private Integer paymentMode;
	
	private String premiumShow;
	private String premiumPerMonthShow;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPaymentDescription() {
		return paymentDescription;
	}
	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getResultUrl1() {
		return resultUrl1;
	}
	public void setResultUrl1(String resultUrl1) {
		this.resultUrl1 = resultUrl1;
	}
	public String getResultUrl2() {
		return resultUrl2;
	}
	public void setResultUrl2(String resultUrl2) {
		this.resultUrl2 = resultUrl2;
	}
	public String getHashValue() {
		return hashValue;
	}
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}
	public PolicyBean getPolicy() {
		return policy;
	}
	public void setPolicy(PolicyBean policy) {
		this.policy = policy;
	}
	public Integer getPremium() {
		return premium;
	}
	public void setPremium(Integer premium) {
		this.premium = premium;
	}
	public Integer getPremiumPerMonth() {
		return premiumPerMonth;
	}
	public void setPremiumPerMonth(Integer premiumPerMonth) {
		this.premiumPerMonth = premiumPerMonth;
	}
	public Integer getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getLetterYear() {
		return letterYear;
	}
	public void setLetterYear(String letterYear) {
		this.letterYear = letterYear;
	}
	public String getLetterMonth() {
		return letterMonth;
	}
	public void setLetterMonth(String letterMonth) {
		this.letterMonth = letterMonth;
	}
	public String getPremiumTemp() {
		return premiumTemp;
	}
	public void setPremiumTemp(String premiumTemp) {
		this.premiumTemp = premiumTemp;
	}
	public String getPremiumPerMonthTemp() {
		return premiumPerMonthTemp;
	}
	public void setPremiumPerMonthTemp(String premiumPerMonthTemp) {
		this.premiumPerMonthTemp = premiumPerMonthTemp;
	}
	public String getPremiumShow() {
		return premiumShow;
	}
	public void setPremiumShow(String premiumShow) {
		this.premiumShow = premiumShow;
	}
	public String getPremiumPerMonthShow() {
		return premiumPerMonthShow;
	}
	public void setPremiumPerMonthShow(String premiumPerMonthShow) {
		this.premiumPerMonthShow = premiumPerMonthShow;
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
	public String getNlChk() {
		return nlChk;
	}
	public void setNlChk(String nlChk) {
		this.nlChk = nlChk;
	}
	
	
}
