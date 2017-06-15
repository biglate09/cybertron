package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

public class IliReceiptBean implements Serializable {
	
	private String rcTerm;
	private String payFrom;
	private String rcPayDate;
	private String paymentChannel;
	private String totalPrem;
	private String rcNo;
	private String payTo;
	private String term;
	private int order = 0;
	
	public String getRcTerm() {
		return rcTerm;
	}
	public void setRcTerm(String rcTerm) {
		this.rcTerm = rcTerm;
	}
	public String getPayFrom() {
		return payFrom;
	}
	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}
	public String getRcPayDate() {
		return rcPayDate;
	}
	public void setRcPayDate(String rcPayDate) {
		this.rcPayDate = rcPayDate;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public String getTotalPrem() {
		return totalPrem;
	}
	public void setTotalPrem(String totalPrem) {
		this.totalPrem = totalPrem;
	}
	public String getRcNo() {
		return rcNo;
	}
	public void setRcNo(String rcNo) {
		this.rcNo = rcNo;
	}
	public String getPayTo() {
		return payTo;
	}
	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	

	

	
	
	
}
