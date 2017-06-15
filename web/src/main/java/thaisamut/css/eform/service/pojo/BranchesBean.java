package thaisamut.css.eform.service.pojo;

import java.io.Serializable;

import javax.persistence.Column;

public class BranchesBean implements Serializable  {
	private static final long serialVersionUID = -7963263051446829412L;
	
	private String brnNo;
	private String brnName;
	private String status;
	
	public String getBrnNo() {
		return brnNo;
	}
	public void setBrnNo(String brnNo) {
		this.brnNo = brnNo;
	}
	public String getBrnName() {
		return brnName;
	}
	public void setBrnName(String brnName) {
		this.brnName = brnName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
