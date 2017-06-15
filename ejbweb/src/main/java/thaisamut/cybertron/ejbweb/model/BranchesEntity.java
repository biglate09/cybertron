package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity(name = "BRANCHES")
public class BranchesEntity implements Serializable{
	
	@Id
	@Column(name = "brn_no")
	private String brnNo;
	@Column(name = "brn_name")
	private String brnName;
	@Column(name = "status")
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
}
