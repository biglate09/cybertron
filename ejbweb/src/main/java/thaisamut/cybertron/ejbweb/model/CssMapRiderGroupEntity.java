package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_MAP_RIDER_GROUP")
@SequenceGenerator(name = "CssMepRiderGroupId", sequenceName = "seq_css_map_rider_group", allocationSize = 1)
public class CssMapRiderGroupEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8169282776582361697L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssMepRiderGroupId")
	private Long id;

	@Column(name = "rider_code")
	private String riderCode;
	@Column(name = "rider_group")
	private String riderGroup;
	@Column(name = "policy_type")
	private String policyType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
	
}
