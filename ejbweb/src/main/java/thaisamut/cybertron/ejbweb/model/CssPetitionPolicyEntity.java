package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_PETITION_POLICY")
@SequenceGenerator(name = "CssPetitionPolicyId", sequenceName = "seq_css_petition_policy", allocationSize = 1)
public class CssPetitionPolicyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4767211067215001112L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssPetitionPolicyId")
	private Long id;
	
	@Column(name = "petition_id")
	private Long petitionId;
	
	@Column(name = "policy_type")
	private String policyType;
	
	@Column(name = "policy_no")
	private String policyNo;
	
	@Column(name = "plan_code")
	private String planCode;
	
	@Column(name = "plan_name")
	private String planName;
	
	@Column(name = "create_date")
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPetitionId() {
		return petitionId;
	}

	public void setPetitionId(Long petitionId) {
		this.petitionId = petitionId;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}
