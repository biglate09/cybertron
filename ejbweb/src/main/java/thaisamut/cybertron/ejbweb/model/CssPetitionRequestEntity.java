package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity(name = "CSS_PETITION_REQUEST")
@SequenceGenerator(name = "CssPetitionRequestId", sequenceName = "seq_css_petition_request", allocationSize = 1)
public class CssPetitionRequestEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6585497961459977593L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssPetitionRequestId")
	private Long id;
	
	@Column(name = "request_no")
	private String requestNo;
	
	@Column(name = "request_type")
	private String requestType;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "customer_card_no")
	private String customerCardNo;
	
	@Column(name = "customer_title")
	private String customerTitle;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_sname")
	private String customerSName;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "request_by")
	private String requestBy;
	
	@Transient
	private List<CssPetitionPolicyEntity> petitionPolicyList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCardNo() {
		return customerCardNo;
	}

	public void setCustomerCardNo(String customerCardNo) {
		this.customerCardNo = customerCardNo;
	}

	public String getCustomerTitle() {
		return customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSName() {
		return customerSName;
	}

	public void setCustomerSName(String customerSName) {
		this.customerSName = customerSName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRequestBy() {
		return requestBy;
	}

	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}

	public List<CssPetitionPolicyEntity> getPetitionPolicyList() {
		return petitionPolicyList;
	}

	public void setPetitionPolicyList(
			List<CssPetitionPolicyEntity> petitionPolicyList) {
		this.petitionPolicyList = petitionPolicyList;
	}
	
	
}
