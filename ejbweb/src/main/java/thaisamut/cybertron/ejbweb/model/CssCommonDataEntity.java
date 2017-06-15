package thaisamut.cybertron.ejbweb.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CSS_COMMON_DATA")
@SequenceGenerator(name = "CssCommonDataId", sequenceName = "seq_css_common_data", allocationSize = 1)
public class CssCommonDataEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5287080356180977108L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = SEQUENCE, generator = "CssCommonDataId")
	private Long id;
	
	@Column(name = "key_group")
	private String keyGroup;

	@Column(name = "key_code")
	private String keyCode;
	
	@Column(name = "key_value_text")
	private String keyValueText;
	
	@Column(name = "key_value_number")
	private Long keyValueNumber;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "create_date")
	private Date createDate;
	
	@Column(name = "create_by")
	private String createBy;
	
	@Column(name = "update_date")
	private Date updateDate;
	
	@Column(name = "update_by")
	private String updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyGroup() {
		return keyGroup;
	}

	public void setKeyGroup(String keyGroup) {
		this.keyGroup = keyGroup;
	}

	public String getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}

	public String getKeyValueText() {
		return keyValueText;
	}

	public void setKeyValueText(String keyValueText) {
		this.keyValueText = keyValueText;
	}

	public Long getKeyValueNumber() {
		return keyValueNumber;
	}

	public void setKeyValueNumber(Long keyValueNumber) {
		this.keyValueNumber = keyValueNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
