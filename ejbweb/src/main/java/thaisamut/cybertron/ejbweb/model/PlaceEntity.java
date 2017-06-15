package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "place")

public class PlaceEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1495857080053245311L;
	@Id
	@Column(name = "place_id")
	private int placeId;
	@Column(name = "place_name")
	private String placeName;
	@Column(name = "place_desc")
	private String placeDesc;
	@Column(name = "place_contact")
	private String placeContact;
	@Column(name = "department")
	private String department;
	@Column(name = "province_id")
	private String provinceId;
	@Column(name = "place_type_id")
	private int placeTypeId;
	@Column(name = "place_address")
	private String placeAddress;
	@Column(name = "place_conditions")
	private String placeConditions;
	@Column(name = "status")
	private String status;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "latitude")
	private double latitude;
	@Column(name = "longitude")
	private double longitude;
	
	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}
	public int getPlaceId() {
		return placeId;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceDesc(String placeDesc) {
		this.placeDesc = placeDesc;
	}
	public String getPlaceDesc() {
		return placeDesc;
	}
	public void setPlaceContact(String placeContact) {
		this.placeContact = placeContact;
	}
	public String getPlaceContact() {
		return placeContact;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartment() {
		return department;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setPlaceTypeId(int placeTypeId) {
		this.placeTypeId = placeTypeId;
	}
	public int getPlaceTypeId() {
		return placeTypeId;
	}
	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}
	public String getPlaceAddress() {
		return placeAddress;
	}
	public void setPlaceConditions(String placeConditions) {
		this.placeConditions = placeConditions;
	}
	public String getPlaceConditions() {
		return placeConditions;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndDate() {
		return endDate;
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