package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "BANK")
public class BankEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -862096064792696237L;
	@Id
	@Column(name = "bank_code")
	private String bankCode;
	@Column(name = "bank_name")
	private String bankName;
	@Column(name = "status")
	private String status;
	@Column(name = "bank_no")
	private String bankNo;
	@Column(name = "creditcard")
	private String creditcard;
	@Column(name = "transfer")
	private String transfer;
	@Column(name = "code_as400")
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
