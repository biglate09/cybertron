package thaisamut.css.eform.service.pojo;

import java.io.Serializable;

public class BankBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7963263051446829412L;
	private String bankCode;
	private String bankName;
	private String status;
	private String bankNo;
	private String creditcard;
	private String transfer;
	private String codeAs400;
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	public String getCodeAs400() {
		return codeAs400;
	}
	public void setCodeAs400(String codeAs400) {
		this.codeAs400 = codeAs400;
	}
	
	
}
