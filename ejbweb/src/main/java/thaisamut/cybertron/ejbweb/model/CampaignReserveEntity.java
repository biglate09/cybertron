package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "campaign_reserve")

public class CampaignReserveEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3634913785813084534L;
	
	@Id
	@Column(name = "reserve_id")
	private int reserveId;
	@Column(name = "reserve_date")
	private Date reserveDate;
	@Column(name = "reserve_no")
	private int reserveNo;
	@Column(name = "right_date")
	private int rightDate;
	@Column(name = "right_status")
	private String rightStatus;
	@Column(name = "campaign_id")
	private int campaignId;
	@Column(name = "id_no")
	private int idNo;
	
	public void setReserveId(int reserveId) {
		this.reserveId = reserveId;
	}
	public int getReserveId() {
		return reserveId;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveNo(int reserveNo) {
		this.reserveNo = reserveNo;
	}
	public int getReserveNo() {
		return reserveNo;
	}
	public void setRightDate(int rightDate) {
		this.rightDate = rightDate;
	}
	public int getRightDate() {
		return rightDate;
	}
	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}
	public String getRightStatus() {
		return rightStatus;
	}
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setIdNo(int idNo) {
		this.idNo = idNo;
	}
	public int getIdNo() {
		return idNo;
	}
}