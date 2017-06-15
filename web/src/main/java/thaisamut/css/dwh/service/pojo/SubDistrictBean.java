package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;

public class SubDistrictBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6633247680706208994L;
	private String distCode;
	private String subDistDesc;
	private String subDistCode;
	private String zipCode;
	public SubDistrictBean(Long districtNbs, String subdistrictCode, String subdistrict, Long zipcode) {
		this.distCode = Long.toString(districtNbs);
		this.subDistCode = subdistrictCode;
		this.subDistDesc = subdistrict; 
		this.zipCode = Long.toString(zipcode);
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getSubDistDesc() {
		return subDistDesc;
	}
	public void setSubDistDesc(String subDistDesc) {
		this.subDistDesc = subDistDesc;
	}
	public String getSubDistCode() {
		return subDistCode;
	}
	public void setSubDistCode(String subDistCode) {
		this.subDistCode = subDistCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
