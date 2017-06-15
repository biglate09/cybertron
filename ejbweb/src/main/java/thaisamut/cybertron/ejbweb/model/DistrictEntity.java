package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DISTRICT")
public class DistrictEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6241431830684654142L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "dist_code")
	private String distCode;
	@Column(name = "dist_desc")
	private String distDesc;
	@Column(name = "input_date")
	private Date inputDate;
	@Column(name = "input_user")
	private String inputUser;
	@Column(name = "status")
	private String status;
	@Column(name = "pv_code")
	private String pvCode;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getDistDesc() {
		return distDesc;
	}
	public void setDistDesc(String distDesc) {
		this.distDesc = distDesc;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
	}
	
}
