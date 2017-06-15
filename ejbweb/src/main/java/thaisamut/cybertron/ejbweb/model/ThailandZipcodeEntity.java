package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "THAILAND_ZIPCODE")
public class ThailandZipcodeEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -178927865311929733L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "district")
	private String district;
	@Column(name = "district_as400")
	private Long districtAs400;
	@Column(name = "district_nbs")
	private Long districtNbs;
	@Column(name = "map_as400")
	private String mapAs400;
	@Column(name = "map_nbs")
	private String mapNbs;
	@Column(name = "province")
	private String province;
	@Column(name = "province_as400")
	private Long provinceAs400;
	@Column(name = "province_nbs")
	private Long provinceNbs;
	@Column(name = "remark")
	private String remark;
	@Column(name = "status")
	private String status;
	@Column(name = "subdistrict")
	private String subdistrict;
	@Column(name = "subdistrict_code")
	private String subdistrictCode;
	@Column(name = "zipcode")
	private Long zipcode;
	@Column(name = "subdistrict_as400")
	private Long subdistrictAs400;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Long getDistrictAs400() {
		return districtAs400;
	}
	public void setDistrictAs400(Long districtAs400) {
		this.districtAs400 = districtAs400;
	}
	public Long getDistrictNbs() {
		return districtNbs;
	}
	public void setDistrictNbs(Long districtNbs) {
		this.districtNbs = districtNbs;
	}
	public String getMapAs400() {
		return mapAs400;
	}
	public void setMapAs400(String mapAs400) {
		this.mapAs400 = mapAs400;
	}
	public String getMapNbs() {
		return mapNbs;
	}
	public void setMapNbs(String mapNbs) {
		this.mapNbs = mapNbs;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Long getProvinceAs400() {
		return provinceAs400;
	}
	public void setProvinceAs400(Long provinceAs400) {
		this.provinceAs400 = provinceAs400;
	}
	public Long getProvinceNbs() {
		return provinceNbs;
	}
	public void setProvinceNbs(Long provinceNbs) {
		this.provinceNbs = provinceNbs;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubdistrict() {
		return subdistrict;
	}
	public void setSubdistrict(String subdistrict) {
		this.subdistrict = subdistrict;
	}
	public String getSubdistrictCode() {
		return subdistrictCode;
	}
	public void setSubdistrictCode(String subdistrictCode) {
		this.subdistrictCode = subdistrictCode;
	}
	public Long getZipcode() {
		return zipcode;
	}
	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}
	public Long getSubdistrictAs400() {
		return subdistrictAs400;
	}
	public void setSubdistrictAs400(Long subdistrictAs400) {
		this.subdistrictAs400 = subdistrictAs400;
	}
}
