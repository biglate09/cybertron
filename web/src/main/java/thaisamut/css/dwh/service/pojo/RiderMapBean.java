package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;

public class RiderMapBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6559310394537006734L;
	private String riderCode;
	private String riderGroup;
	private String policyType;
	private String mappingGroup;
	private Double sumAssured;
	private Double premiumAmout;
	
	public RiderMapBean(String riderCode, String riderGroup, String policyType,String mappingGroup, Double sumAssured, Double premiumAmout){
		super();
		this.riderCode = riderCode;
		this.riderGroup = riderGroup;
		this.policyType = policyType;
		this.setMappingGroup(mappingGroup);
		this.sumAssured = sumAssured;
		this.premiumAmout = premiumAmout;
	}
	public RiderMapBean(String riderCode, String riderGroup, String policyType){
		super();
		this.riderCode = riderCode;
		this.riderGroup = riderGroup;
		this.setPolicyType(policyType);
	}
	
	public String getRiderCode() {
		return riderCode;
	}
	public void setRiderCode(String riderCode) {
		this.riderCode = riderCode;
	}
	public String getRiderGroup() {
		return riderGroup;
	}
	public void setRiderGroup(String riderGroup) {
		this.riderGroup = riderGroup;
	}
	public Double getSumAssured() {
		return sumAssured;
	}
	public void setSumAssured(Double sumAssured) {
		this.sumAssured = sumAssured;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	public String getMappingGroup() {
		return mappingGroup;
	}
	public void setMappingGroup(String mappingGroup) {
		this.mappingGroup = mappingGroup;
	}
	public Double getPremiumAmout() {
		return premiumAmout;
	}
	public void setPremiumAmout(Double premiumAmout) {
		this.premiumAmout = premiumAmout;
	}
}
