package thaisamut.css.eform.service.pojo;

import java.io.Serializable;

public class CssConstantBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8921127409713515012L;
	private String key;
	private String allowDownloadCert;
	private String allowDownloadNoti;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAllowDownloadCert() {
		return allowDownloadCert;
	}
	public void setAllowDownloadCert(String allowDownloadCert) {
		this.allowDownloadCert = allowDownloadCert;
	}
	public String getAllowDownloadNoti() {
		return allowDownloadNoti;
	}
	public void setAllowDownloadNoti(String allowDownloadNoti) {
		this.allowDownloadNoti = allowDownloadNoti;
	}

}
