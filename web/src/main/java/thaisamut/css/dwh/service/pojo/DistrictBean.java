package thaisamut.css.dwh.service.pojo;

import java.io.Serializable;

public class DistrictBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7102893142522351622L;
	private String pvCode;
	private String distCode;
	private String distDesc;
	
	public DistrictBean(){super();}
	
	public DistrictBean(String pvCode, String distCode, String distDesc){
		super();
		this.pvCode = pvCode;
		this.distCode = distCode;
		this.distDesc = distDesc;
	}
	
	public String getPvCode() {
		return pvCode;
	}
	public void setPvCode(String pvCode) {
		this.pvCode = pvCode;
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
	
}
