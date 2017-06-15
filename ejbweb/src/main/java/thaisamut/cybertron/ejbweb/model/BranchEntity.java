package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "branch")

public class BranchEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5269747742804439142L;
	@Id
	@Column(name = "branch_id")
	private int branchId;
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "latitude")
	private double latitude;
	@Column(name = "longitude")
	private double longitude;
	
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() {
		return longitude;
	}
}