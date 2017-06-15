package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "campaign")
public class CampaignEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -517339654942091967L;
	@Id
	@Column(name = "campaign_id")
	private int campaignId;
	@Column(name = "campaign_name")
	private String campaignName;
	@Column(name = "campaign_place")
	private String campaignPlace;
	@Column(name = "province_id")
	private String provinceId;
	@Column(name = "campaign_desc")
	private String campaignDesc;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column(name = "event_start_date")
	private Date eventStartDate;
	@Column(name = "event_end_date")
	private Date eventEndDate;
	@Column(name = "status")
	private String status;
	@Column(name = "quota_total")
	private int quotaTotal;
	@Column(name = "quota_used")
	private int quotaUsed;
	@Column(name = "quota_remain")
	private int quotaRemain;
	@Column(name = "campaign_remark")
	private String campaignRemark;
	
	public void setCampaignId(int campaignId) {
		this.campaignId = campaignId;
	}
	public int getCampaignId() {
		return campaignId;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignPlace(String campaignPlace) {
		this.campaignPlace = campaignPlace;
	}
	public String getCampaignPlace() {
		return campaignPlace;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setCampaignDesc(String campaignDesc) {
		this.campaignDesc = campaignDesc;
	}
	public String getCampaignDesc() {
		return campaignDesc;
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
	public void setEventStartDate(Date eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	public Date getEventStartDate() {
		return eventStartDate;
	}
	public void setEventEndDate(Date eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	public Date getEventEndDate() {
		return eventEndDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setQuotaTotal(int quotaTotal) {
		this.quotaTotal = quotaTotal;
	}
	public int getQuotaTotal() {
		return quotaTotal;
	}
	public void setQuotaUsed(int quotaUsed) {
		this.quotaUsed = quotaUsed;
	}
	public int getQuotaUsed() {
		return quotaUsed;
	}
	public void setQuotaRemain(int quotaRemain) {
		this.quotaRemain = quotaRemain;
	}
	public int getQuotaRemain() {
		return quotaRemain;
	}
	public void setCampaignRemark(String campaignRemark) {
		this.campaignRemark = campaignRemark;
	}
	public String getCampaignRemark() {
		return campaignRemark;
	}
}
