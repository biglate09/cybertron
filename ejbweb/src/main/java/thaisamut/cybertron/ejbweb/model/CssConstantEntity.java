package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "CSS_CONSTANT")
public class CssConstantEntity  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4063687906574452618L;

	@Id
	@Column(name = "key")
	private String key;

	@Column(name = "value1")
	private String value1;
	@Column(name = "value2")
	private String value2;
	@Column(name = "remark")
	private String remark;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
