package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "province")
public class ProvinceEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6426218438012157998L;
	@Id
	@Column(name = "pv_code")
	private String pvCode;
	@Column(name = "input_date")
	private Date inputDate;
	@Column(name = "input_user")
	private String inputUser;
	@Column(name = "pv_desc")
	private String pvDesc;
	@Column(name = "status")
	private String status;
	
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getInputUser() {
		return inputUser;
	}
	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}
	public String getPvDesc() {
		return pvDesc;
	}
	public void setPvDesc(String pvDesc) {
		this.pvDesc = pvDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
