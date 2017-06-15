package thaisamut.cybertron.ejbweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "choices")

public class ChoicesEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6984684143893182770L;
	@Id
	@Column(name = "choices_id")
	private int choicesId;
	@Column(name = "choices_no")
	private int choicesNo;
	@Column(name = "choices_name")
	private String choicesName;
	@Column(name = "choices_type")
	private String choicesType;
	
	public void setChoicesId(int choicesId) {
		this.choicesId = choicesId;
	}
	public int getChoicesId() {
		return choicesId;
	}
	public void setChoicesNo(int choicesNo) {
		this.choicesNo = choicesNo;
	}
	public int getChoicesNo() {
		return choicesNo;
	}
	public void setChoicesName(String choicesName) {
		this.choicesName = choicesName;
	}
	public String getChoicesName() {
		return choicesName;
	}
	public void setChoicesType(String choicesType) {
		this.choicesType = choicesType;
	}
	public String getChoicesType() {
		return choicesType;
	}
}