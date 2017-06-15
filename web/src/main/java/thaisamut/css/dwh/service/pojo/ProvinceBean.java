package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;

public class ProvinceBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6320749777378027062L;
	
	private String pvCode;
	private String pvDesc;
	
	public ProvinceBean(){super();};
	
	public ProvinceBean(String pvCode,String pvDesc){
		super();
		this.pvCode = pvCode;
		this.pvDesc = pvDesc;
	}
	
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
	}
	public String getPvDesc() {
		return pvDesc;
	}
	public void setPvDesc(String pvDesc) {
		this.pvDesc = pvDesc;
	}
}
